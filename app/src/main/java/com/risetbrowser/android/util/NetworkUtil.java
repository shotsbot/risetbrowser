package com.risetbrowser.android.util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class NetworkUtil {
    public static final int NETWORK_TYPE_NONE = 0;
    public static final int NETWORK_TYPE_MOBILE = 1;
    public static final int NETWORK_TYPE_WIFI = 2;
    public static final int NETWORK_TYPE_ETHERNET = 3;

    public static int getNetworkType(Context context) {
        ConnectivityManager connectivityManager =
                (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        if (connectivityManager == null) {
            return NETWORK_TYPE_NONE;
        }

        NetworkInfo activeNetwork = connectivityManager.getActiveNetworkInfo();
        if (activeNetwork == null || !activeNetwork.isConnected()) {
            return NETWORK_TYPE_NONE;
        }

        int type = activeNetwork.getType();
        switch (type) {
            case ConnectivityManager.TYPE_MOBILE:
                return NETWORK_TYPE_MOBILE;
            case ConnectivityManager.TYPE_WIFI:
                return NETWORK_TYPE_WIFI;
            case ConnectivityManager.TYPE_ETHERNET:
                return NETWORK_TYPE_ETHERNET;
            default:
                return NETWORK_TYPE_MOBILE;
        }
    }

    public static boolean isOnline(Context context) {
        return getNetworkType(context) != NETWORK_TYPE_NONE;
    }

    public static boolean isWifiConnected(Context context) {
        return getNetworkType(context) == NETWORK_TYPE_WIFI;
    }

    public static boolean isMobileConnected(Context context) {
        return getNetworkType(context) == NETWORK_TYPE_MOBILE;
    }

    public static String getNetworkTypeName(int type) {
        switch (type) {
            case NETWORK_TYPE_MOBILE:
                return "Mobile";
            case NETWORK_TYPE_WIFI:
                return "WiFi";
            case NETWORK_TYPE_ETHERNET:
                return "Ethernet";
            case NETWORK_TYPE_NONE:
            default:
                return "None";
        }
    }
}
