package de.deineapp.fahrtenbuch_app.db;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "PersonInfo_table")
public class PersonInfo {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private String kennzeichen;
    private String Name;
    private int kilometerStand;

    public PersonInfo(String kennzeichen, String name, int kilometerStand) {
        this.kennzeichen = kennzeichen;
        Name = name;
        this.kilometerStand = kilometerStand;
    }

    public int getKilometerStand() {
        return kilometerStand;
    }

    public void setKilometerStand(int kilometerStand) {
        this.kilometerStand = kilometerStand;
    }

    public PersonInfo(int id, String kennzeichen) {
        this.id = id;
        this.kennzeichen = kennzeichen;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setKennzeichen(String kennzeichen) {
        this.kennzeichen = kennzeichen;
    }

    public void setName(String name) {
        Name = name;
    }

    public int getId() {
        return id;
    }

    public String getKennzeichen() {
        return kennzeichen;
    }

    public String getName() {
        return Name;
    }
}
