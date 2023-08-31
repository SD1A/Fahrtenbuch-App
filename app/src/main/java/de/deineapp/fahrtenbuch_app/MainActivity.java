package de.deineapp.fahrtenbuch_app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import de.deineapp.fahrtenbuch_app.db.Fahrt;
import de.deineapp.fahrtenbuch_app.db.PersonInfo;

public class MainActivity extends AppCompatActivity {
    private Button g_button;
    private Button p_button;
    private ImageButton Add_button;

    private Button speichern_btn;

    private EditText Edit_name;
    private EditText Edit_kilometer;
    private EditText EditK;
    private TextView txtViwe1;
    private TextView TxtViwe2;
    private TextView txtView3;
    private TextView txtView4;
    private TextView txtView5;
    LinearLayout linearLayout;

    String kennzeichen, name ;
    int kilometerstand;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle("Fahrtenbuch\nApp");
        txtViwe1 = findViewById(R.id.viewKennzeichen);
        TxtViwe2 = findViewById(R.id.viewName);
        txtView3 = findViewById(R.id.viewKilometer);
        txtView4 = findViewById(R.id.viewPrivat);
        txtView5 = findViewById(R.id.viewGeschaeftlich);
        g_button = findViewById(R.id.g_btn);
        p_button = findViewById(R.id.p_btn);
        Add_button = findViewById(R.id.add_btn);
        speichern_btn = findViewById(R.id.speicher_btn);
        linearLayout = findViewById(R.id.startViews);
        Edit_name = findViewById(R.id.editName);
        Edit_kilometer = findViewById(R.id.editKilometer);
        EditK = findViewById(R.id.EditKennzeichen);
        EditK.setAllCaps(true);
        FahrtenApp app = (FahrtenApp) getApplication();

        speichern_btn.setOnClickListener(v -> {
            app.getExecutors().diskIO().execute(new Runnable() {
                @Override
                public void run() {
                    kennzeichen = EditK.getText().toString();
                    name = Edit_name.getText().toString();
                    kilometerstand = Integer.parseInt(Edit_kilometer.getText().toString());

                    PersonInfo personInfo = new PersonInfo(kennzeichen, name, kilometerstand);
                    app.getDb().personInfoDao().insertInfo(personInfo);

                    runOnUiThread(() -> {
                        linearLayout.setVisibility(View.INVISIBLE);
                    });
                }
            });

        });

        app.getExecutors().diskIO().execute(new Runnable() {
            @Override
            public void run() {
                List<PersonInfo> dbList = app.getDb().personInfoDao().getAll();
                Fahrt letzteFahrt = app.getDb().fahrtDao().getLetzteFahrt();
                List<Fahrt> fahrtenPrivat = app.getDb().fahrtDao().getFahrten(Fahrt.ART_PRIVAT);
                List<Fahrt> fahrtenGesch = app.getDb().fahrtDao().getFahrten(Fahrt.ART_GESCHAEFTLICH);
                runOnUiThread(() -> {
                    if (dbList.size() == 0){
                        linearLayout.setVisibility(View.VISIBLE);
                    }else{
                        linearLayout.setVisibility(View.INVISIBLE);
                        PersonInfo info = dbList.get(0);
                        txtViwe1.setText(info.getKennzeichen());
                        TxtViwe2.setText(info.getName());

                        int kmStand;
                        if (letzteFahrt != null) {
                            kmStand = letzteFahrt.getKilometerstand();
                        } else {
                            kmStand = info.getKilometerStand();
                        }
                        txtView3.setText(kmStand + "");

                        int kmP = 0;
                        int kmG = 0;
                        if (fahrtenPrivat.size() > 0) {
                            for (int i = 0; i < fahrtenPrivat.size(); i++) {
                                Fahrt fahrt = fahrtenPrivat.get(i);
                                kmP = kmP + fahrt.getFahrstrecke();
                            }
                        }
                        if (fahrtenGesch.size() > 0) {
                            for (int i = 0; i < fahrtenGesch.size(); i++) {
                                Fahrt fahrt = fahrtenGesch.get(i);
                                kmG = kmG + fahrt.getFahrstrecke();
                            }
                        }
                        int gesKm = kmP + kmG;
                        int prozP = (int)Math.round((100.0 / gesKm) * kmP);
                        int prozG = 100 - prozP;


                        txtView4.setText(kmP + " km / " + prozP + " %");
                        txtView5.setText(kmG + " km / " + prozG + " %");
                    }
                });
            }
        });

        p_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, FahrtenActivity.class);
                intent.putExtra("isPrivat", true);
                startActivity(intent);
            }
        });

        g_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, FahrtenActivity.class);
                intent.putExtra("isPrivat", false);
                startActivity(intent);

            }
        });

        Add_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent (MainActivity.this, ActivityAdd.class);
                startActivity(intent);
            }
        });
    }
}
