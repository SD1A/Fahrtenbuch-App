package de.deineapp.fahrtenbuch_app;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import de.deineapp.fahrtenbuch_app.db.Fahrt;
public class ActivityAdd extends AppCompatActivity {
    private Button _speicher;
    private EditText _EditAbfahrt;
    private EditText _EditOrtAbfahrt;
    private EditText _EditPLZAbfahrt;
    private EditText _EditstrsseAbfahrt;

    private EditText _EditAnkunft;
    private EditText _EditOrtAnkunft;
    private EditText _EditPLZAnkunft;
    private EditText _EditStrasseAnkunft;

    private EditText _kilometerstand;

    public RadioButton radiobtnP;
    public RadioButton radiobtnG;
    public RadioGroup radioGroup1;

     public String ortAnfahrt, strasseAnfahrt, plzAnfahrt,  abfahrt, ankunft, ortAnkunft, strasseAnkunft, plzAnkunft, Rgroup;
     public int art, kiloMeterStand;
     private Button buttonAbbrechen;
     private ImageButton buttonAnkunft;
     private DateTimeDialog.OnDateTimeDialogListener abfahrtListener;
    private DateTimeDialog.OnDateTimeDialogListener ankunftListener;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle("Neue Fahrt");

        //Speicher Button
        _speicher = findViewById(R.id.buttonSpeichern);

        //Edit Text Abfahrt
        _EditAbfahrt =  findViewById(R.id.editTextAbfahrt);
        _EditOrtAbfahrt = findViewById(R.id.editTextAbfahrtOrt);
        //_EditOrtAbfahrt.setSingleLine(true); // Allow just one lines
        _EditPLZAbfahrt = findViewById(R.id.editTextAbfahrtPLZ);
        _EditPLZAbfahrt.setSingleLine(true);
        _EditstrsseAbfahrt = findViewById(R.id.editTextAbfahrtStrasse);
        _EditstrsseAbfahrt.setSingleLine(true);
        _kilometerstand = findViewById(R.id.editTextKilometer);
        _kilometerstand.setSingleLine(true);

        //Edit Felder Abfahrt mit letztem Ort setzen
        ((FahrtenApp) getApplication()).getExecutors().diskIO().execute(() -> {
            Fahrt letzteFahrt = ((FahrtenApp) getApplication()).getDb().fahrtDao().getLetzteFahrt();
            if (letzteFahrt != null) {
                runOnUiThread(() -> {
                    _EditOrtAbfahrt.setText(letzteFahrt.getAnkunftOrt());
                    _EditPLZAbfahrt.setText(letzteFahrt.getAnkunftPLZ());
                    _EditstrsseAbfahrt.setText(letzteFahrt.getAnkunftStrasse());
                    actionBar.setSubtitle("Letzter Stand: " + letzteFahrt.getKilometerstand() + " km");
                });
            }
        });

        //Edit Text Ankunft
        _EditAnkunft = findViewById(R.id.edittextAnkunft);
        _EditOrtAnkunft = findViewById(R.id.editTextAnkunftOrt);
        _EditPLZAnkunft = findViewById(R.id.editTextAnkunftPLZ);
        _EditStrasseAnkunft = findViewById(R.id.editTextAnkunftStrasse);

        //Radiobutton
        radiobtnP = findViewById(R.id.radioButtonp);
        radiobtnG = findViewById(R.id.radioButtonG);
        radioGroup1 = findViewById(R.id.radioGroup);

        buttonAbbrechen = findViewById(R.id.buttonAbbrechen);
        buttonAnkunft = findViewById(R.id.buttonAnkunft);
        buttonAbbrechen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        buttonAnkunft.setOnClickListener(v -> {
            DateTimeDialog dialog = new DateTimeDialog(this, ankunftListener, null, null, null, "dd.MM.yyyy HH:mm");
            dialog.show();

        });

        abfahrtListener = new DateTimeDialog.OnDateTimeDialogListener() {
            @Override
            public void dateTimeEvent(Calendar calendar) {
                Date date = calendar.getTime();
                SimpleDateFormat formatter = new SimpleDateFormat("dd.MM.yyyy HH:mm");
                _EditAbfahrt.setText(formatter.format(date));
            }

            @Override
            public void canceled() {

            }
        };

        ankunftListener = new DateTimeDialog.OnDateTimeDialogListener() {
            @Override
            public void dateTimeEvent(Calendar calendar) {
                Date date = calendar.getTime();
                SimpleDateFormat formatter = new SimpleDateFormat("dd.MM.yyyy HH:mm");
                _EditAnkunft.setText(formatter.format(date));
            }

            @Override
            public void canceled() {

            }
        };

        ImageButton buttonAbfahrt = findViewById(R.id.buttonAbfahrt);
        buttonAbfahrt.setOnClickListener(v -> {
            DateTimeDialog dialog = new DateTimeDialog(this, abfahrtListener, null, null, null, "dd.MM.yyyy HH:mm");
            dialog.show();
        });

        _speicher.setOnClickListener(v -> {
            FahrtenApp app = (FahrtenApp) getApplication();
            ortAnfahrt  = _EditOrtAbfahrt.getText().toString();
            strasseAnfahrt = _EditstrsseAbfahrt.getText().toString();
            plzAnfahrt = _EditPLZAbfahrt.getText().toString();
            abfahrt =  _EditAbfahrt.getText().toString();
            try {
                kiloMeterStand = Integer.parseInt(_kilometerstand.getText().toString());
            } catch (Exception e) {
                new AlertDialog.Builder(ActivityAdd.this)
                        .setTitle("Fehler")
                        .setMessage("Falscheingabe Kilometerstand")
                        .setNegativeButton("Ok", null)
                        .show();
                return;
            }
            ankunft = _EditAnkunft.getText().toString();
            ortAnkunft = _EditOrtAnkunft.getText().toString();
            strasseAnkunft = _EditStrasseAnkunft.getText().toString();
            plzAnkunft = _EditPLZAnkunft.getText().toString();
            Rgroup = radioGroup1.getCheckedRadioButtonId() +"";

           if(radiobtnG.isChecked()){
               art = Fahrt.ART_GESCHAEFTLICH;

           }else {
               art = Fahrt.ART_PRIVAT;
           }


            if (TextUtils.isEmpty(ortAnfahrt)|| TextUtils.isEmpty(strasseAnfahrt) || TextUtils.isEmpty(plzAnfahrt) || TextUtils.isEmpty(abfahrt)
                    || kiloMeterStand == 0 || TextUtils.isEmpty(ankunft) || TextUtils.isEmpty(ortAnfahrt)
                    || TextUtils.isEmpty(strasseAnkunft) || TextUtils.isEmpty(plzAnkunft) || Rgroup.isEmpty())
            {
                new AlertDialog.Builder(ActivityAdd.this)
                        .setTitle("Fehler")
                        .setMessage("Nicht alle Felder ausgefüllt")
                        .setNegativeButton("Ok", null)
                        .show();
                return;
            }

            app.getExecutors().diskIO().execute(new Runnable() {
                @Override
                public void run() {
                    int letzterKmStand;
                    Fahrt letzteFahrt = app.getDb().fahrtDao().getLetzteFahrt();
                    if (letzteFahrt != null) {
                        letzterKmStand = letzteFahrt.getKilometerstand();
                    } else {
                        letzterKmStand = app.getDb().personInfoDao().getAll().get(0).getKilometerStand();
                    }
                    int strecke = kiloMeterStand - letzterKmStand;
                    if (strecke < 0) {
                        runOnUiThread(() -> {
                            new AlertDialog.Builder(ActivityAdd.this)
                                    .setTitle("Fehler")
                                    .setMessage("Kilometerstand zu klein")
                                    .setNegativeButton("Ok", null)
                                    .show();
                        });
                        return;
                    }

                    Fahrt fahrt = new Fahrt(abfahrt, ortAnfahrt, strasseAnfahrt, plzAnfahrt, ankunft, ortAnkunft, plzAnkunft, strasseAnkunft, kiloMeterStand, art, strecke);
                    app.getDb().fahrtDao().insertFahrt(fahrt);

                    runOnUiThread(() -> {
                        finish();
                    });
                }
            });
        });
    }

    public boolean onOptionsItemSelected(MenuItem item){
        finish();
        return true;
    }
}
