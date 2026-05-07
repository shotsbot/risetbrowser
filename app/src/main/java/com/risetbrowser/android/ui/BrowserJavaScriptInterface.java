package com.risetbrowser.android.ui;

import android.content.Context;
import android.webkit.JavascriptInterface;
import android.widget.Toast;

public class BrowserJavaScriptInterface {
    private Context context;

    public BrowserJavaScriptInterface(Context context) {
        this.context = context;
    }

    @JavascriptInterface
    public void showToast(String message) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }

    @JavascriptInterface
    public String getDeviceInfo() {
        return "Android " + android.os.Build.VERSION.RELEASE +
               " - " + android.os.Build.DEVICE;
    }

    @JavascriptInterface
    public String getAppName() {
        return "RiSETBrowser";
    }

    @JavascriptInterface
    public String getAppVersion() {
        return "1.0.0";
    }

    @JavascriptInterface
    public boolean isNightMode() {
        return false; // Can be extended to check actual night mode setting
    }

    @JavascriptInterface
    public void closeWindow() {
        // Could trigger back button or close action
    }

    @JavascriptInterface
    public void openNewTab(String url) {
        Toast.makeText(context, "New tab: " + url, Toast.LENGTH_SHORT).show();
    }

    @JavascriptInterface
    public void shareLink(String url, String title) {
        // Share functionality
    }

    @JavascriptInterface
    public void log(String message) {
        android.util.Log.d("BrowserJS", message);
    }
}
