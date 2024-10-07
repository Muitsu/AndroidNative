package com.example.messageapp.widgtes;

import android.app.AlertDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;

import com.example.messageapp.R;

public class ImageViewDialog {

    private final Context context;

    public ImageViewDialog(Context context) {
        this.context = context;
    }

    public void showImageDialog(String base64Image) {
        // Inflate the custom dialog layout
        LayoutInflater inflater = LayoutInflater.from(context);
        View dialogView = inflater.inflate(R.layout.dialog_image_view, null);

        // Initialize the ImageView in the dialog
        ImageView imageView = dialogView.findViewById(R.id.dialog_image_view);

        // Decode base64 string to Bitmap
        Bitmap bitmap = decodeBase64ToBitmap(base64Image);
        if (bitmap != null) {
            imageView.setImageBitmap(bitmap);
        }

        // Create and show the AlertDialog
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setView(dialogView)
                .setPositiveButton("Close", (dialog, which) -> dialog.dismiss())
                .create()
                .show();
    }

    // New method to show the dialog with a drawable resource
    public void showDrawableDialog(int drawableResId) {
        // Inflate the custom dialog layout
        LayoutInflater inflater = LayoutInflater.from(context);
        View dialogView = inflater.inflate(R.layout.dialog_image_view, null);

        // Initialize the ImageView in the dialog
        ImageView imageView = dialogView.findViewById(R.id.dialog_image_view);

        // Set the drawable resource to the ImageView
        imageView.setImageResource(drawableResId);

        // Create and show the AlertDialog
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setView(dialogView)
                .setPositiveButton("Close", (dialog, which) -> dialog.dismiss())
                .create()
                .show();
    }

    // Helper method to decode a base64 string to Bitmap
    private Bitmap decodeBase64ToBitmap(String base64Image) {
        try {
            byte[] decodedString = Base64.decode(base64Image, Base64.DEFAULT);
            return BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
