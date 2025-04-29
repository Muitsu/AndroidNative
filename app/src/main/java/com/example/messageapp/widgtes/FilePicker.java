package com.example.messageapp.widgtes;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Handler;
import android.os.Looper;
import android.provider.OpenableColumns;
import android.util.Base64;
import android.webkit.MimeTypeMap;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class FilePicker {

    public static final int PICK_FILE_REQUEST_CODE = 1;

    private static OnFilePicked onFilePicked;
    private static OnError onError;

    // Functional interface for file picked callback
    @FunctionalInterface
    public interface OnFilePicked {
        void onFilePicked(String fileName, String mimeType, String base64, byte[] fileBytes);
    }


    // Functional interface for error callback
    @FunctionalInterface
    public interface OnError {
        void onError(String errorMessage);
    }

    private static final ExecutorService executorService = Executors.newSingleThreadExecutor();  // For background tasks
    private static final Handler mainHandler = new Handler(Looper.getMainLooper());  // To post results back to the main thread

    // Method to open file picker
    public static void open(Activity activity, OnFilePicked filePickedCallback, OnError errorCallback) {
        onFilePicked = filePickedCallback;
        onError = errorCallback;

        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("*/*");
        String[] mimeTypes = {"application/pdf", "image/jpeg", "image/png"};
        intent.putExtra(Intent.EXTRA_MIME_TYPES, mimeTypes);
        activity.startActivityForResult(intent, PICK_FILE_REQUEST_CODE);
    }

    // Handle the result of file picking
    public static void handleActivityResult(Activity activity, int requestCode, int resultCode, Intent data) {
        if (requestCode == PICK_FILE_REQUEST_CODE && resultCode == Activity.RESULT_OK && data != null) {
            Uri uri = data.getData();
            if (uri != null) {
                // Perform file processing in a background thread
                executorService.execute(() -> {
                    try {
                        String fileName = getFileName(activity, uri);
                        String mimeType = getMimeType(activity, uri);
                        byte[] fileBytes = getFileBytes(activity, uri);  // Retrieve raw bytes
                        String base64 = convertToBase64(fileBytes);  // Convert bytes to Base64

                        // Post result back to the main thread
                        mainHandler.post(() -> onFilePicked.onFilePicked(fileName, mimeType, base64, fileBytes));
                    } catch (Exception e) {
                        mainHandler.post(() -> onError.onError("Failed to pick file: " + e.getMessage()));
                    }
                });
            } else {
                onError.onError("No file selected.");
            }
        } else {
            onError.onError("Operation canceled or failed.");
        }
    }


    // Get the file name from Uri
    @SuppressLint("Range")
    private static String getFileName(Activity activity, Uri uri) {
        String displayName = null;
        Cursor cursor = activity.getContentResolver().query(uri, null, null, null, null);
        if (cursor != null && cursor.moveToFirst()) {
            displayName = cursor.getString(cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME));
            cursor.close();
        }
        return displayName;
    }

    // Get MIME type from Uri
    private static String getMimeType(Activity activity, Uri uri) {
        String mimeType = null;
        if (uri.getScheme().equals("content")) {
            mimeType = activity.getContentResolver().getType(uri);
        } else {
            String fileExtension = MimeTypeMap.getFileExtensionFromUrl(uri.toString());
            mimeType = MimeTypeMap.getSingleton().getMimeTypeFromExtension(fileExtension.toLowerCase());
        }
        return mimeType;
    }

    public static String convertToBase64(byte[] fileBytes) {
        if (fileBytes == null) {
            return "";
        }
        return Base64.encodeToString(fileBytes, Base64.NO_WRAP);
    }

    private static byte[] getFileBytes(Activity activity, Uri uri) throws IOException {
        InputStream inputStream = activity.getContentResolver().openInputStream(uri);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        byte[] buffer = new byte[8192];
        int bytesRead;

        while ((bytesRead = inputStream.read(buffer)) != -1) {
            outputStream.write(buffer, 0, bytesRead);
        }

        inputStream.close();
        return outputStream.toByteArray();
    }
}


//********************** HOW TO USE **********************

// private void openFilePicker(int index) {
//    FilePicker.open(this,
//            (fileName, mimeType, base64, fileBytes) -> {
//                // Use both base64 and byte array
//                if (dlyProvider.isEdit()) {
//                    int docId = ApplicationHistoryVM.getInstance().getDailyDetail(this).getDocuments().get(index - 1).getId();
//                    dlyProvider.addUpdateAttachment(index, docId, fileName, base64, mimeType, fileBytes);
//                } else {
//                    dlyProvider.addAttachment(index, fileName, base64, mimeType, fileBytes);
//                }
//            },
//            errorMessage -> {
//                Toast.makeText(this, "Error: " + errorMessage, Toast.LENGTH_SHORT).show();
//            }
//    );
//}
//
//@Override
//protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//    super.onActivityResult(requestCode, resultCode, data);
//
//    // Handle file picker result
//    FilePicker.handleActivityResult(this, requestCode, resultCode, data);
//}