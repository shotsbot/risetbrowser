package com.risetbrowser.android;

import android.app.Application;
import android.webkit.WebView;

public class BrowserApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        
        // Setup WebView for debugging in debug builds
        if (BuildConfig.DEBUG) {
            WebView.setWebContentsDebuggingEnabled(true);
        }
        
        // Initialize other components
        initializeBrowser();
    }

    private void initializeBrowser() {
        // Initialize third-party components
        // Initialize analytics (optional)
        // Initialize crash reporting (optional)
    }
}
