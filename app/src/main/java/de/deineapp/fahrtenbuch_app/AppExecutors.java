package de.deineapp.fahrtenbuch_app;

import android.app.Application;
import android.os.Handler;
import android.os.Looper;

import androidx.annotation.NonNull;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class AppExecutors extends Application {

    private final Executor _diskIO;
    private final Executor _mainThread;

    private AppExecutors(Executor diskIO, Executor mainThread){
        _diskIO = diskIO;
        _mainThread = mainThread;

    }
    public AppExecutors(){
        this(Executors.newSingleThreadExecutor(),
                new MainThreadExecutor());
    }

    public Executor diskIO(){
        return _diskIO;
    }

    public Executor mainThread(){
        return _mainThread;
    }

    private static class MainThreadExecutor implements Executor{
        private Handler mainThreadHandler = new
                Handler(Looper.getMainLooper());

        @Override
        public void execute(@NonNull Runnable command){
            mainThreadHandler.post(command);
        }
    }
}
