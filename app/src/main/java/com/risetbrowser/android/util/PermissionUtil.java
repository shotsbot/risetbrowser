package com.risetbrowser.android.util;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;

import androidx.core.content.ContextCompat;

public class PermissionUtil {
    public static final String[] REQUIRED_PERMISSIONS = {
        Manifest.permission.INTERNET,
        Manifest.permission.WRITE_EXTERNAL_STORAGE,
        Manifest.permission.READ_EXTERNAL_STORAGE
    };

    public static final String[] OPTIONAL_PERMISSIONS = {
        Manifest.permission.CAMERA,
        Manifest.permission.RECORD_AUDIO,
        Manifest.permission.ACCESS_FINE_LOCATION,
        Manifest.permission.READ_CONTACTS
    };

    public static boolean hasPermission(Context context, String permission) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            return ContextCompat.checkSelfPermission(context, permission)
                    == PackageManager.PERMISSION_GRANTED;
        }
        return true;
    }

    public static boolean hasAllPermissions(Context context, String[] permissions) {
        for (String permission : permissions) {
            if (!hasPermission(context, permission)) {
                return false;
            }
        }
        return true;
    }

    public static String[] getMissingPermissions(Context context, String[] permissions) {
        java.util.List<String> missing = new java.util.ArrayList<>();
        for (String permission : permissions) {
            if (!hasPermission(context, permission)) {
                missing.add(permission);
            }
        }
        return missing.toArray(new String[0]);
    }

    public static boolean isStoragePermissionGranted(Context context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            return hasPermission(context, Manifest.permission.MANAGE_EXTERNAL_STORAGE);
        } else {
            return hasPermission(context, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        }
    }

    public static String getPermissionLabel(String permission) {
        switch (permission) {
            case Manifest.permission.INTERNET:
                return "Internet Access";
            case Manifest.permission.CAMERA:
                return "Camera Access";
            case Manifest.permission.RECORD_AUDIO:
                return "Microphone Access";
            case Manifest.permission.ACCESS_FINE_LOCATION:
                return "Location Access";
            case Manifest.permission.WRITE_EXTERNAL_STORAGE:
            case Manifest.permission.READ_EXTERNAL_STORAGE:
            case Manifest.permission.MANAGE_EXTERNAL_STORAGE:
                return "File Access";
            default:
                return permission;
        }
    }
}
