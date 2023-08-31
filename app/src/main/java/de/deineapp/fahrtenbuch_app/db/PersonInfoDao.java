package de.deineapp.fahrtenbuch_app.db;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;
@Dao
public interface PersonInfoDao {

    @Insert
    void insertInfo(PersonInfo personInfo);

    @Query("SELECT * FROM PersonInfo_table")
    List<PersonInfo> getAll();

}
