package com.kevinpelgrims.pillreminder2;

import android.app.Application;
import android.content.Context;

import com.kevinpelgrims.pillreminder2.di.AppComponent;
import com.kevinpelgrims.pillreminder2.di.DaggerAppComponent;

public class PillReminderApplication extends Application {
    private AppComponent appComponent;

    public static AppComponent getComponent(Context context) {
        return ((PillReminderApplication) context.getApplicationContext()).appComponent;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        initComponent();
    }

    private void initComponent() {
        appComponent = DaggerAppComponent.create();
    }
}
