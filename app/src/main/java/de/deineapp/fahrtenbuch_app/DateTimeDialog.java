package de.deineapp.fahrtenbuch_app;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import android.annotation.TargetApi;
import android.app.Dialog;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.DatePicker.OnDateChangedListener;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.TimePicker.OnTimeChangedListener;

public class DateTimeDialog extends Dialog
{
    private OnDateTimeDialogListener onDateTimeDialogListener;
    private DatePicker datePicker;
    private TimePicker timePicker;
    private Calendar minCalendar;
    private Calendar maxCalendar;
    private Calendar startCal;
    private boolean onlyDate;

    public DateTimeDialog(Context context, OnDateTimeDialogListener listener, String startDatumUhrzeit, Date min, Date max, String dateFormat) {
        super(context);

        setCanceledOnTouchOutside(false);
        onlyDate = false;

        String format;
        if (dateFormat == null || dateFormat.equals("")) {
            format = "HH:mm (dd.MM.yyyy)";
        } else {
            format = dateFormat;
        }

        startCal = null;
        if (startDatumUhrzeit != null && !startDatumUhrzeit.trim().equals("")) {
            SimpleDateFormat formatter = new SimpleDateFormat(format);
            try {
                Date date = formatter.parse(startDatumUhrzeit);
                startCal = Calendar.getInstance();
                startCal.setTime(date);
            } catch (Exception e) {
                startCal = null;
            }
        }
        if (startCal == null) startCal = Calendar.getInstance();

        minCalendar = null;
        maxCalendar = null;
        if (min != null) {
            minCalendar = Calendar.getInstance();
            minCalendar.setTime(min);
            if (startCal.before(minCalendar)) {
                startCal = minCalendar;
            }
        }
        if (max != null) {
            maxCalendar = Calendar.getInstance();
            maxCalendar.setTime(max);
            if (startCal.after(maxCalendar)) {
                startCal = maxCalendar;
            }
        }
        setContentView(R.layout.dialog_datetime);
        onDateTimeDialogListener = listener;
    }

    public interface OnDateTimeDialogListener {
        public void dateTimeEvent(Calendar calendar);
        public void canceled();
    }

    public void setIsOnlyDate(boolean _onlyDate) {
        onlyDate = _onlyDate;
    }


    @Override
    public void setTitle(CharSequence title) {
        super.setTitle(title);
        TextView tvTitel = (TextView)findViewById(R.id.dialog_datetime_tvTitel);
        if (tvTitel != null)
            tvTitel.setText(title);
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        Button btnUebernehmen = (Button)findViewById(R.id.dialog_datetime_btnUebernehmen);
        btnUebernehmen.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v) {
                uebernehmen();
            }
        });

        Button btnAbbrechen = (Button)findViewById(R.id.dialog_datetime_btnAbbrechen);
        btnAbbrechen.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v) {
                abbrechen();
            }
        });

        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            TextView tvTitel = (TextView)findViewById(R.id.dialog_datetime_tvTitel);
            tvTitel.setVisibility(View.GONE);
        }

        timePicker = (TimePicker)findViewById(R.id.dialog_datetime_timePicker);
        if (onlyDate) {
            timePicker.setVisibility(View.GONE);
        }
        timePicker.setIs24HourView(true);
        timePicker.setCurrentHour(startCal.get(Calendar.HOUR_OF_DAY));
        timePicker.setCurrentMinute(startCal.get(Calendar.MINUTE));
        timePicker.setOnTimeChangedListener(new OnTimeChangedListener() {
            public void onTimeChanged(TimePicker view, int hourOfDay, int minute) {
                Calendar newDate = Calendar.getInstance();
                if (minCalendar != null) {
                    newDate.set(minCalendar.get(Calendar.YEAR), minCalendar.get(Calendar.MONTH), minCalendar.get(Calendar.DAY_OF_MONTH), hourOfDay, minute, 0);
                    if (newDate.before(minCalendar)) {
                        view.setCurrentHour(minCalendar.get(Calendar.HOUR_OF_DAY));
                        view.setCurrentMinute(minCalendar.get(Calendar.MINUTE));
                    }
                }
                if (maxCalendar != null) {
                    newDate.set(datePicker.getYear(), datePicker.getMonth(), datePicker.getDayOfMonth(), hourOfDay, minute, 0);
                    if (newDate.after(maxCalendar)) {
                        view.setCurrentHour(maxCalendar.get(Calendar.HOUR_OF_DAY));
                        view.setCurrentMinute(maxCalendar.get(Calendar.MINUTE));
                    }
                }
            }
        });

        datePicker = (DatePicker)findViewById(R.id.dialog_datetime_datePicker);
        if (android.os.Build.VERSION.SDK_INT >= 11) {
            datePicker.setCalendarViewShown(false);
            datePicker.setSpinnersShown(true);
        }

        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.ICE_CREAM_SANDWICH) {
            //Android kleiner Android 4 => Negative Margins (aus dialog_datetime.xml) der Picker entfernen
            LinearLayout.LayoutParams params = (LinearLayout.LayoutParams)datePicker.getLayoutParams();
            params.setMargins(0,0,0,0);
            datePicker.setLayoutParams(params);

            params = (LinearLayout.LayoutParams)timePicker.getLayoutParams();
            params.setMargins(0,0,0,0);
            timePicker.setLayoutParams(params);

            LinearLayout layButtons = (LinearLayout)findViewById(R.id.dialog_datetime_layButtons);
            params = (LinearLayout.LayoutParams)layButtons.getLayoutParams();
            params.setMargins(0,0,0,0);
            layButtons.setLayoutParams(params);
        }

        datePicker.init(startCal.get(Calendar.YEAR), startCal.get(Calendar.MONTH), startCal.get(Calendar.DAY_OF_MONTH), new OnDateChangedListener() {
            public void onDateChanged(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                if (minCalendar != null) {
                    newDate.set(year, monthOfYear, dayOfMonth, minCalendar.get(Calendar.HOUR_OF_DAY), minCalendar.get(Calendar.MINUTE), minCalendar.get(Calendar.SECOND));
                    if (newDate.before(minCalendar)) {
                        view.init(minCalendar.get(Calendar.YEAR), minCalendar.get(Calendar.MONTH), minCalendar.get(Calendar.DAY_OF_MONTH), this);
                    }
                }
                if (maxCalendar != null) {
                    int hour;
                    int minute;
                    if (Build.VERSION.SDK_INT < 23) {
                        hour = timePicker.getCurrentHour();
                        minute = timePicker.getCurrentMinute();
                    } else {
                        hour = timePicker.getHour();
                        minute = timePicker.getMinute();
                    }
                    newDate.set(year, monthOfYear, dayOfMonth, hour, minute, 0);
                    if (newDate.after(maxCalendar)) {
                        view.init(maxCalendar.get(Calendar.YEAR), maxCalendar.get(Calendar.MONTH), maxCalendar.get(Calendar.DAY_OF_MONTH), this);
                        timePicker.setCurrentHour(maxCalendar.get(Calendar.HOUR_OF_DAY));
                        timePicker.setCurrentMinute(maxCalendar.get(Calendar.MINUTE));
                    }
                }
            }
        });
    }

    private void abbrechen() {
        onDateTimeDialogListener.canceled();
        dismiss();
    }

    private void uebernehmen() {
        datePicker.clearFocus(); //wichtig! sonst werden Änderungen per Tastatur nicht sicher übernommen
        timePicker.clearFocus();
        Calendar calendar = Calendar.getInstance();
        calendar.set(datePicker.getYear(), datePicker.getMonth(), datePicker.getDayOfMonth(), timePicker.getCurrentHour(), timePicker.getCurrentMinute(), 0);
        onDateTimeDialogListener.dateTimeEvent(calendar);
        dismiss();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        uebernehmen();
    }
}



