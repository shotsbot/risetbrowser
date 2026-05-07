package com.risetbrowser.android.util;

import android.webkit.WebStorage;

public class SessionManager {
    public static void clearAllSessions() {
        WebStorage.getInstance().deleteAllData();
    }

    public static void clearDOMStorage() {
        WebStorage.getInstance().deleteAllData();
    }

    public static void clearAppCache() {
        try {
            WebStorage.getInstance().deleteAllData();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void getUsage() {
        WebStorage.getInstance().getUsage(usage -> {
            // Current usage in bytes
        });
    }

    public static void setQuota(long newQuota) {
        WebStorage.getInstance().setQuotaForOrigin(null, newQuota);
    }
}
