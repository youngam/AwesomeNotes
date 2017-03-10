package com.hackspace.alex.awesomenotes;

import android.support.v7.app.AppCompatDelegate;

import com.hackspace.alex.awesomenotes.di.component.AppComponent;
import com.hackspace.alex.awesomenotes.di.component.DaggerAppComponent;
import com.hackspace.alex.worklibrary.BaseApplication;
import com.hackspace.alex.worklibrary.utils.ImageLoaderManager;

import uk.co.chrisjenx.calligraphy.CalligraphyConfig;

public class AwesomeNotes extends BaseApplication {
    private static AppComponent sComponent;
    private static AwesomeNotes sInstance;

    static {
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
    }

    public static AppComponent getComponent() {
        return sComponent;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        sInstance = this;
        ImageLoaderManager.getInstance().init(getApplicationContext(), R.drawable.horse,
                R.drawable.horse);
        sComponent = buildComponent();

        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder().build());
    }

    public AppComponent buildComponent() {
        return DaggerAppComponent.builder()
                .build();
    }

    public static AwesomeNotes getInstance() {
        return sInstance;
    }

    public void setNightMode(boolean nightMode) {
        int mode = nightMode ? AppCompatDelegate.MODE_NIGHT_YES : AppCompatDelegate.MODE_NIGHT_NO;
        AppCompatDelegate.setDefaultNightMode(mode);
    }

    public boolean isNightMode() {
        return AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_YES;
    }
}
