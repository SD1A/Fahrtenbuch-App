package de.deineapp.fahrtenbuch_app;

import android.app.Application;

import de.deineapp.fahrtenbuch_app.db.FahrtenDatabase;

public class FahrtenApp extends Application {

    private AppExecutors _executors;

    @Override
    public void onCreate() {

        super.onCreate();
        _executors = new AppExecutors();
    }

    public AppExecutors getExecutors(){
        return (AppExecutors) _executors;
    }
    public FahrtenDatabase getDb() {
        return FahrtenDatabase.getInstance(this.getApplicationContext());
    }


}
