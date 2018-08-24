package uk.co.taniakolesnik.adn_bakingapp.Utils;

import android.app.Application;

import timber.log.Timber;

/**
 * Created by tetianakolesnik on 23/08/2018.
 */

public class TimberApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        Timber.plant(new Timber.DebugTree());
    }
}
