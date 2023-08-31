package de.deineapp.fahrtenbuch_app.db;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface FahrtDao {
    @Insert
    void insertFahrt(Fahrt fahrten);

    @Query("SELECT * FROM TFahrt WHERE art = :art")
    List<Fahrt> getFahrten(int art);

    @Query("SELECT * FROM TFahrt ORDER BY id DESC LIMIT 1")
    Fahrt getLetzteFahrt();
}
