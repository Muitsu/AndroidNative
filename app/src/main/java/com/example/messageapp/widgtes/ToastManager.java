package com.example.messageapp.widgtes;

import android.content.Context;
import android.widget.Toast;

public class ToastManager {
    private static Toast currentToast;

    public static void showToast(Context context, String message) {
        if (currentToast != null) {
            currentToast.cancel();
        }
        currentToast = Toast.makeText(context, message, Toast.LENGTH_SHORT);
        currentToast.show();
    }
}
