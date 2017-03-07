package com.hackspace.alex.awesomenotes;

import com.hackspace.alex.awesomenotes.di.component.AppComponent;
import com.hackspace.alex.awesomenotes.di.component.DaggerAppComponent;
import com.hackspace.alex.worklibrary.BaseApplication;
import com.hackspace.alex.worklibrary.utils.ImageLoaderManager;

import uk.co.chrisjenx.calligraphy.CalligraphyConfig;

public class AwesomeNotes extends BaseApplication {
    private static AppComponent sComponent;
    private static AwesomeNotes sInstance;

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
}
