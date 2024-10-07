package com.example.messageapp.widgtes;

import android.content.Context;
import android.content.DialogInterface;

import androidx.appcompat.app.AlertDialog;

public class ConfirmationDialog {

    private final AlertDialog dialog;

    public ConfirmationDialog(Context context, String title, String subtitle,
                              String positiveButtonText, String negativeButtonText,
                              DialogInterface.OnClickListener onYesClick,
                              DialogInterface.OnClickListener onNoClick) {

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(title);
        builder.setMessage(subtitle);
        builder.setCancelable(false);

        // Set custom button names
        builder.setPositiveButton(positiveButtonText, onYesClick);
        builder.setNegativeButton(negativeButtonText, onNoClick);

        dialog = builder.create();
    }

    public void show() {
        if (dialog != null) {
            dialog.show();
        }
    }

    public void dismiss() {
        if (dialog != null && dialog.isShowing()) {
            dialog.dismiss();
        }
    }
}
