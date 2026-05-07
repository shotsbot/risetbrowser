package com.risetbrowser.android.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import androidx.annotation.Nullable;

public class BlockerService extends Service {
    private static AdBlocker adBlocker;
    private static PopupBlocker popupBlocker;

    @Override
    public void onCreate() {
        super.onCreate();
        // Initialize blockers
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return START_STICKY;
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    public static AdBlocker getAdBlocker() {
        return adBlocker;
    }

    public static PopupBlocker getPopupBlocker() {
        return popupBlocker;
    }
}
