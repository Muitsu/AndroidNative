package com.example.messageapp;

import android.app.Application;

import com.example.messageapp.shared_pref.SharedPrefManager;

public class MainApp extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        AppLog.log("Initialize Application....");
        SharedPrefManager.initialize(this);
    }
}
