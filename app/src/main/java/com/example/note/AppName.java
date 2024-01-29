package com.example.note;

import android.app.Application;

import com.google.android.material.color.DynamicColors;

public class AppName extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        DynamicColors.applyToActivitiesIfAvailable(this);
    }
}
