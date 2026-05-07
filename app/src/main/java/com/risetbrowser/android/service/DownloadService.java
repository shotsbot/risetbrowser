package com.risetbrowser.android.service;

import android.app.DownloadManager;
import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;

import androidx.annotation.Nullable;

public class DownloadService extends IntentService {
    public static final String CHANNEL_ID = "download_channel";

    public DownloadService() {
        super("DownloadService");
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        if (intent == null) return;

        String url = intent.getStringExtra("url");
        String fileName = intent.getStringExtra("fileName");

        if (url == null || url.isEmpty()) {
            return;
        }

        downloadFile(url, fileName);
    }

    private void downloadFile(String url, String fileName) {
        try {
            DownloadManager downloadManager =
                    (DownloadManager) getSystemService(Context.DOWNLOAD_SERVICE);

            Uri uri = Uri.parse(url);
            DownloadManager.Request request = new DownloadManager.Request(uri);

            // Set download directory
            request.setDestinationInExternalPublicDir(
                    Environment.DIRECTORY_DOWNLOADS,
                    fileName
            );

            // Set title and description
            request.setTitle(fileName);
            request.setDescription("Downloading from RiSETBrowser");

            // Set notification
            request.setNotificationVisibility(
                    DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED
            );

            // Allow over metered network
            request.setAllowedNetworkTypes(
                    DownloadManager.Request.NETWORK_WIFI | DownloadManager.Request.NETWORK_MOBILE
            );

            // Set user agent
            request.addRequestHeader("User-Agent", "RiSETBrowser/1.0");

            if (downloadManager != null) {
                downloadManager.enqueue(request);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void startDownload(Context context, String url, String fileName) {
        Intent intent = new Intent(context, DownloadService.class);
        intent.putExtra("url", url);
        intent.putExtra("fileName", fileName);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            context.startForegroundService(intent);
        } else {
            context.startService(intent);
        }
    }
}
