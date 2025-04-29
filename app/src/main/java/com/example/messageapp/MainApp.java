package com.example.messageapp;

import android.app.Application;

import com.example.messageapp.notification.NotificationService;
import com.example.messageapp.shared_pref.SharedPrefManager;
import com.example.messageapp.widgtes.AppLog;

public class MainApp extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        AppLog.log("Initialize Application....");
        SharedPrefManager.initialize(this);
        NotificationService.initialize(this);
    }
}
