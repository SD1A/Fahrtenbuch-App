package de.deineapp.fahrtenbuch_app.db;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {Fahrt.class, PersonInfo.class},version = 1)
public  abstract class FahrtenDatabase extends RoomDatabase {
    private static FahrtenDatabase _instance;
    public abstract FahrtDao fahrtDao();

    public abstract PersonInfoDao personInfoDao();

    public static  FahrtenDatabase getInstance(Context context){
        //wenn die instance = 0 dann bekommt man die instance züruck
        if (_instance == null){
            synchronized(FahrtenDatabase.class) {
                if(_instance == null){
                   _instance = Room.databaseBuilder(
                           context.getApplicationContext(),// Context, FahrtenDatabase.class, "Fahrten_database")
                           FahrtenDatabase.class,
                           "de.deineapp.fahrtenbuch_app.db"//Datenbank
                   ) .build();
                }
            }
        }
        return _instance;

    }
}
