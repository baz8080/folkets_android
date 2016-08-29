package com.mbcdev.folkets;

import android.app.Application;

import timber.log.Timber;

/**
 * Application class.
 *
 * Created by barry on 20/08/2016.
 */
public class MainApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        Timber.plant(new Timber.DebugTree());
    }
}
