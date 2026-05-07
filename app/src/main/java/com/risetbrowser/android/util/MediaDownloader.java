package com.risetbrowser.android.util;

import android.webkit.URLUtil;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

public class MediaDownloader {
    public interface DownloadListener {
        void onProgress(long downloaded, long total);
        void onComplete(String filePath);
        void onError(String errorMessage);
    }

    public static void downloadMedia(String url, String savePath, DownloadListener listener) {
        new Thread(() -> {
            try {
                URL urlObj = new URL(url);
                URLConnection connection = urlObj.openConnection();
                connection.setConnectTimeout(10000);

                int contentLength = connection.getContentLength();
                InputStream input = connection.getInputStream();

                File file = new File(savePath);
                File parent = file.getParentFile();
                if (parent != null && !parent.exists()) {
                    parent.mkdirs();
                }

                FileOutputStream output = new FileOutputStream(file);
                byte[] buffer = new byte[4096];
                int bytesRead;
                long totalRead = 0;

                while ((bytesRead = input.read(buffer)) != -1) {
                    output.write(buffer, 0, bytesRead);
                    totalRead += bytesRead;

                    if (listener != null && contentLength > 0) {
                        listener.onProgress(totalRead, contentLength);
                    }
                }

                input.close();
                output.close();

                if (listener != null) {
                    listener.onComplete(savePath);
                }
            } catch (Exception e) {
                if (listener != null) {
                    listener.onError(e.getMessage());
                }
            }
        }).start();
    }

    public static String getMediaFileName(String url) {
        return URLUtil.guessFileName(url, null, null);
    }

    public static boolean isMediaUrl(String url) {
        String mimeType = BrowserUtil.getMimeType(url);
        return isImageUrl(url) || isVideoUrl(url) || isAudioUrl(url);
    }

    public static boolean isImageUrl(String url) {
        return BrowserUtil.isImageUrl(url);
    }

    public static boolean isVideoUrl(String url) {
        return BrowserUtil.isVideoUrl(url);
    }

    public static boolean isAudioUrl(String url) {
        return BrowserUtil.isAudioUrl(url);
    }
}
