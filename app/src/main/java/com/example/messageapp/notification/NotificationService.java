package com.example.messageapp.notification;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.os.Build;

import androidx.core.app.NotificationCompat;

public class NotificationService {

    @SuppressLint("StaticFieldLeak")
    private static NotificationService instance;
    @SuppressLint("StaticFieldLeak")
    private static Context context;
    private final NotificationManager notificationManager;
    private static final String CHANNEL_ID = "default_channel_id";
    private static final String CHANNEL_NAME = "Default Channel";
    private static final String CHANNEL_DESCRIPTION = "Default Channel for App Notifications";

    // Private constructor to prevent instantiation
    private NotificationService(Context context) {
        notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        createNotificationChannel();
    }

    // Synchronized method to control simultaneous access
    public static synchronized void initialize(Context appContext) {
        if (instance == null) {
            context = appContext.getApplicationContext();  // Store context only once
            instance = new NotificationService(context);
        }
    }

    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, CHANNEL_NAME, NotificationManager.IMPORTANCE_DEFAULT);
            channel.setDescription(CHANNEL_DESCRIPTION);
            notificationManager.createNotificationChannel(channel);
        }
    }

    // Static method to send notification using stored context
    public static void show(int notificationId, String title, String message) {
        if (instance == null) {
            throw new IllegalStateException("NotificationService is not initialized. Call initInstance() first.");
        }

        Notification notification = new NotificationCompat.Builder(context, CHANNEL_ID)
                .setSmallIcon(android.R.drawable.ic_notification_overlay)
                .setContentTitle(title)
                .setContentText(message)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .build();

        instance.notificationManager.notify(notificationId, notification);
    }

    // Static method to cancel notification using stored context
    public static void cancel(int notificationId) {
        if (instance == null) {
            throw new IllegalStateException("NotificationService is not initialized. Call initInstance() first.");
        }

        instance.notificationManager.cancel(notificationId);
    }
}
