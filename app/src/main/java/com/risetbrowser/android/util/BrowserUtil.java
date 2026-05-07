package com.risetbrowser.android.util;

import android.content.Context;
import android.media.MediaPlayer;
import android.net.Uri;
import android.webkit.MimeTypeMap;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class BrowserUtil {
    public static String getFormattedFileSize(long size) {
        if (size <= 0) return "0 B";
        final String[] units = new String[]{"B", "KB", "MB", "GB", "TB"};
        int digitGroups = (int) (Math.log10(size) / Math.log10(1024));
        return String.format("%.1f %s", size / Math.pow(1024, digitGroups), units[digitGroups]);
    }

    public static String getFormattedDate(long timestamp) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault());
        return sdf.format(new Date(timestamp));
    }

    public static String getFormattedTime(long timestamp) {
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm", Locale.getDefault());
        return sdf.format(new Date(timestamp));
    }

    public static String getFormattedDateTime(long timestamp) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd MMM yyyy HH:mm", Locale.getDefault());
        return sdf.format(new Date(timestamp));
    }

    public static String getMimeType(String url) {
        String extension = MimeTypeMap.getFileExtensionFromUrl(url);
        return MimeTypeMap.getSingleton().getMimeTypeFromExtension(extension);
    }

    public static boolean isImageUrl(String url) {
        String mimeType = getMimeType(url);
        return mimeType != null && mimeType.startsWith("image/");
    }

    public static boolean isVideoUrl(String url) {
        String mimeType = getMimeType(url);
        return mimeType != null && mimeType.startsWith("video/");
    }

    public static boolean isAudioUrl(String url) {
        String mimeType = getMimeType(url);
        return mimeType != null && mimeType.startsWith("audio/");
    }

    public static boolean isPdfUrl(String url) {
        return url.endsWith(".pdf");
    }

    public static String getDomainFromUrl(String url) {
        try {
            Uri uri = Uri.parse(url);
            String host = uri.getHost();
            if (host != null) {
                return host.replace("www.", "");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return url;
    }

    public static String getUrlTitle(String url) {
        return url.replace("https://", "").replace("http://", "").split("/")[0];
    }

    public static boolean isValidUrl(String url) {
        return url != null && (url.startsWith("http://") || url.startsWith("https://") ||
                url.startsWith("file://") || url.startsWith("about:"));
    }

    public static String normalizeUrl(String url) {
        if (url == null || url.isEmpty()) return "";
        if (!url.startsWith("http://") && !url.startsWith("https://")) {
            url = "https://" + url;
        }
        return url;
    }

    public static long getDirectorySize(File file) {
        long size = 0;
        if (file.isDirectory()) {
            File[] files = file.listFiles();
            if (files != null) {
                for (File f : files) {
                    size += getDirectorySize(f);
                }
            }
        } else {
            size = file.length();
        }
        return size;
    }

    public static void clearDirectoryCache(File directory) {
        if (directory.isDirectory()) {
            File[] files = directory.listFiles();
            if (files != null) {
                for (File file : files) {
                    if (file.isDirectory()) {
                        clearDirectoryCache(file);
                    } else {
                        file.delete();
                    }
                }
            }
        }
    }
}
