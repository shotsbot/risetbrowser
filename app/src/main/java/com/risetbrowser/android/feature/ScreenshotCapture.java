package com.risetbrowser.android.feature;

import android.webkit.WebView;
import android.graphics.Bitmap;
import android.content.Context;
import android.os.Environment;
import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class ScreenshotCapture {
    private WebView webView;
    private Context context;

    public interface ScreenshotCallback {
        void onScreenshotSaved(String filepath);
        void onScreenshotFailed(String error);
    }

    public ScreenshotCapture(WebView webView, Context context) {
        this.webView = webView;
        this.context = context;
    }

    public void captureScreenshot(ScreenshotCallback callback) {
        try {
            // Get WebView content as bitmap
            Bitmap bitmap = Bitmap.createBitmap(
                webView.getWidth(),
                webView.getHeight(),
                Bitmap.Config.ARGB_8888
            );

            webView.draw(bitmap);

            // Save to disk
            String filename = "screenshot_" + getCurrentTimestamp() + ".png";
            String filepath = saveScreenshot(bitmap, filename);

            if (callback != null) {
                callback.onScreenshotSaved(filepath);
            }
        } catch (Exception e) {
            if (callback != null) {
                callback.onScreenshotFailed(e.getMessage());
            }
        }
    }

    public void captureFullPage(ScreenshotCallback callback) {
        try {
            // Capture entire page (including below fold content)
            webView.evaluateJavascript("(" +
                "function() {" +
                "  var height = document.documentElement.scrollHeight;" +
                "  window.pageHeight = height;" +
                "})();", new android.webkit.ValueCallback<String>() {
                @Override
                public void onReceiveValue(String value) {
                    captureScreenshot(callback);
                }
            });
        } catch (Exception e) {
            if (callback != null) {
                callback.onScreenshotFailed(e.getMessage());
            }
        }
    }

    private String saveScreenshot(Bitmap bitmap, String filename) throws Exception {
        File pictureDir = new File(Environment.getExternalStoragePublicDirectory(
            Environment.DIRECTORY_PICTURES), "RiSETBrowser");

        if (!pictureDir.exists()) {
            pictureDir.mkdirs();
        }

        File screenshotFile = new File(pictureDir, filename);
        FileOutputStream fos = new FileOutputStream(screenshotFile);
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, fos);
        fos.flush();
        fos.close();

        return screenshotFile.getAbsolutePath();
    }

    private String getCurrentTimestamp() {
        return new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(new Date());
    }
}
