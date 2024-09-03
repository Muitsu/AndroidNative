package com.example.messageapp.notification;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class NotificationReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        int notificationId = intent.getIntExtra("notification_id", 0);
        String title = intent.getStringExtra("title");
        String message = intent.getStringExtra("message");

        // Show the notification when the alarm is triggered
        NotificationService.show(notificationId, title, message);
    }
}