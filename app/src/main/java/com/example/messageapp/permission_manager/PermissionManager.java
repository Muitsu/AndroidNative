package com.example.messageapp.permission_manager;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.pm.PackageManager;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PermissionManager {
    public static final int PERMISSIONS_REQUEST_CODE = 1;
    private static final Map<String, String> permissionDescriptions = new HashMap<>();

    // Store permissions in an array
    private static final String[] permissions = {
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };

    static {
        permissionDescriptions.put(Manifest.permission.ACCESS_FINE_LOCATION, "Fine Location");
        permissionDescriptions.put(Manifest.permission.ACCESS_COARSE_LOCATION, "Coarse Location");
        permissionDescriptions.put(Manifest.permission.READ_EXTERNAL_STORAGE, "Read External Storage");
        permissionDescriptions.put(Manifest.permission.WRITE_EXTERNAL_STORAGE, "Write External Storage");
    }

    private final Context context;

    public PermissionManager(Context context) {
        this.context = context;
    }

    public boolean checkPermissions() {
        for (String permission : permissions) {
            if (ContextCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) {
                return false;
            }
        }
        return true;
    }

    public void requestPermissions(Activity activity) {
        ActivityCompat.requestPermissions(activity, permissions, PERMISSIONS_REQUEST_CODE);
    }

    public boolean handlePermissionsResult(int requestCode, int[] grantResults) {
        if (requestCode == PERMISSIONS_REQUEST_CODE) {
            for (int grantResult : grantResults) {
                if (grantResult != PackageManager.PERMISSION_GRANTED) {
                    return false;
                }
            }
            return true;
        }
        return false;
    }

    public void showPermissionsDeniedDialog(final Activity activity) {
        List<String> deniedPermissions = getDeniedPermissions();
        if (!deniedPermissions.isEmpty()) {
            StringBuilder message = new StringBuilder("The following permissions are required:\n");
            for (String permission : deniedPermissions) {
                message.append(permissionDescriptions.get(permission)).append("\n");
            }

            new AlertDialog.Builder(activity)
                    .setTitle("Permissions Required")
                    .setMessage(message.toString())
                    .setPositiveButton("Grant Permissions", (dialog, which) -> requestPermissions(activity))
                    .setNegativeButton("Cancel", null)
                    .create()
                    .show();
        }
    }

    private List<String> getDeniedPermissions() {
        List<String> deniedPermissions = new ArrayList<>();
        for (String permission : permissions) {
            if (ContextCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) {
                deniedPermissions.add(permission);
            }
        }
        return deniedPermissions;
    }
}
