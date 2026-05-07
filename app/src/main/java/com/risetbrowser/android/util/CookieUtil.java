package com.risetbrowser.android.util;

import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;

public class CookieUtil {
    public static void enableCookies() {
        CookieManager cookieManager = CookieManager.getInstance();
        cookieManager.setAcceptCookie(true);
        cookieManager.setAcceptThirdPartyCookies(null);
    }

    public static void disableCookies() {
        CookieManager cookieManager = CookieManager.getInstance();
        cookieManager.setAcceptCookie(false);
    }

    public static void clearAllCookies() {
        CookieManager cookieManager = CookieManager.getInstance();
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            cookieManager.removeAllCookie();
        } else {
            CookieSyncManager.createInstance(null);
            cookieManager.removeAllCookie();
            CookieSyncManager.getInstance().sync();
        }
    }

    public static boolean isCookieEnabled() {
        return CookieManager.getInstance().acceptCookie();
    }

    public static String getCookie(String url) {
        return CookieManager.getInstance().getCookie(url);
    }

    public static void setCookie(String url, String cookie) {
        CookieManager cookieManager = CookieManager.getInstance();
        cookieManager.setCookie(url, cookie);
    }
}
