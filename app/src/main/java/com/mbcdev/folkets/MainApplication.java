package com.mbcdev.folkets;

import android.app.Application;

import timber.log.Timber;

/**
 * Main application class. Installs a debug timber tree
 *
 * Created by barry on 20/08/2016.
 */
public class MainApplication extends Application {

    private static MainApplication instance;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        Timber.plant(new Timber.DebugTree());
    }

    public static MainApplication getInstance() {
        return instance;
    }
}
