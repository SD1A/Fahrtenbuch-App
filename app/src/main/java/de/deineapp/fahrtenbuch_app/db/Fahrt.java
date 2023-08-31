package de.deineapp.fahrtenbuch_app.db;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "TFahrt")
public class Fahrt {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private String abfahrt;
    private String abfahrtOrt;
    private String abfahrtStrasse;
    private String abfahrtPLZ;

    private String ankunft;
    private String ankunftOrt;
    private String ankunftPLZ;
    private String ankunftStrasse;

    private int Kilometerstand;
    private int fahrstrecke;

    private int art;

    public static final int ART_PRIVAT = 0;
    public static final int ART_GESCHAEFTLICH = 1;

    public Fahrt(String abfahrt, String abfahrtOrt, String abfahrtStrasse, String abfahrtPLZ, String ankunft, String ankunftOrt, String ankunftPLZ, String ankunftStrasse, int kilometerstand, int art, int fahrstrecke) {
        this.abfahrt = abfahrt;
        this.abfahrtOrt = abfahrtOrt;
        this.abfahrtStrasse = abfahrtStrasse;
        this.abfahrtPLZ = abfahrtPLZ;
        this.ankunft = ankunft;
        this.ankunftOrt = ankunftOrt;
        this.ankunftPLZ = ankunftPLZ;
        this.ankunftStrasse = ankunftStrasse;
        Kilometerstand = kilometerstand;
        this.fahrstrecke = fahrstrecke;
        this.art = art;
    }

    public Fahrt() {

    }

    public void setAbfahrt(String abfahrt) {
        this.abfahrt = abfahrt;
    }

    public void setAbfahrtOrt(String abfahrtOrt) {
        this.abfahrtOrt = abfahrtOrt;
    }

    public void setAbfahrtStrasse(String abfahrtStrasse) {
        this.abfahrtStrasse = abfahrtStrasse;
    }

    public void setAbfahrtPLZ(String abfahrtPLZ) {
        this.abfahrtPLZ = abfahrtPLZ;
    }

    public void setAnkunft(String ankunft) {
        this.ankunft = ankunft;
    }

    public void setAnkunftOrt(String ankunftOrt) {
        this.ankunftOrt = ankunftOrt;
    }

    public void setAnkunftPLZ(String ankunftPLZ) {
        this.ankunftPLZ = ankunftPLZ;
    }

    public void setAnkunftStrasse(String ankunftStrasse) {
        this.ankunftStrasse = ankunftStrasse;
    }

    public void setKilometerstand(int kilometerstand) {
        Kilometerstand = kilometerstand;
    }

    public void setFahrstrecke(int fahrstrecke) {
        this.fahrstrecke = fahrstrecke;
    }

    public void setArt(int art) {
        this.art = art;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAbfahrt() {
        return abfahrt;
    }

    public String getAbfahrtOrt() {
        return abfahrtOrt;
    }

    public String getAbfahrtStrasse() {
        return abfahrtStrasse;
    }

    public String getAbfahrtPLZ() {
        return abfahrtPLZ;
    }

    public String getAnkunft() {
        return ankunft;
    }

    public String getAnkunftOrt() {
        return ankunftOrt;
    }

    public String getAnkunftPLZ() {
        return ankunftPLZ;
    }

    public String getAnkunftStrasse() {
        return ankunftStrasse;
    }

    public int getKilometerstand() {
        return Kilometerstand;
    }

    public int getFahrstrecke() {
        return fahrstrecke;
    }

    public int getArt() {
        return art;
    }
}

