package de.deineapp.fahrtenbuch_app;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import java.util.List;

import de.deineapp.fahrtenbuch_app.db.Fahrt;

public class FahrtenActivity extends AppCompatActivity {
    private TextView textViewAbfahrt;
    private TextView textViewOrt;
    private TextView textViewAPlz;
    private TextView textViewStrasse;
    private int art;

    private List<Fahrt> items;
    private Adapter baseAdapter;
    GridView gridView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fahrten);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);


        if (getIntent().getBooleanExtra("isPrivat", false)) {
            art = Fahrt.ART_PRIVAT;
            actionBar.setTitle("Private Fahrten");
        } else {
            art = Fahrt.ART_GESCHAEFTLICH;
            actionBar.setTitle("Geschäftliche Fahrten");
        }

         gridView = findViewById(R.id.gridviewP);

        baseAdapter = new Adapter(this);
        gridView.setAdapter(baseAdapter);

        FahrtenApp app1 = (FahrtenApp) getApplication();
        app1.getExecutors().diskIO().execute(new Runnable() {
            @Override
            public void run() {
                items = app1.getDb().fahrtDao().getFahrten(art);
                baseAdapter.notifyDataSetChanged();
            }
        });

    }


    public boolean onOptionsItemSelected(MenuItem item){
        finish();
        return true;
    }

    private class Adapter extends BaseAdapter {
        private Context c;
        public Adapter(Context c) {
            this.c = c;
        }
        @Override
        public int getCount() {
            if (items == null) {
                return 0;
            }
            return items.size();
        }
        @Override
        public Object getItem(int position) {
            if (items == null) {
                return null;
            }
            return items.get(position);
        }
        @Override
        public long getItemId(int position) {
            return position;
        }
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if(convertView == null){
                convertView = LayoutInflater.from(c).inflate(R.layout.fahrten_item, parent, false);
            }
            Fahrt fahrt = items.get(position);
            TextView textView = convertView.findViewById(R.id.ViewZeit);
            textView.setText(fahrt.getAbfahrt() + " -\n" + fahrt.getAnkunft());
            TextView textView1 = convertView.findViewById(R.id.ViewAbfahrtsort);
            textView1.setText(fahrt.getAbfahrtPLZ() + " " + fahrt.getAbfahrtOrt() + " " + fahrt.getAbfahrtStrasse());
            TextView textView2 = convertView.findViewById(R.id.ViewAnkunftssort);
            textView2.setText(fahrt.getAnkunftPLZ() + " " + fahrt.getAnkunftOrt() + " " + fahrt.getAnkunftStrasse());
            TextView textView3 = convertView.findViewById(R.id.ViewStrecke);
            textView3.setText(fahrt.getFahrstrecke() + " km");

            return convertView;
        }
    }




}



