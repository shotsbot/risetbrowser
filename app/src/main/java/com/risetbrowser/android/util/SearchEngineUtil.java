package com.risetbrowser.android.util;

import android.content.Context;
import android.content.SharedPreferences;

public class SearchEngineUtil {
    private static final String PREF_SEARCH_ENGINE = "search_engine";

    public static final int GOOGLE = 0;
    public static final int BING = 1;
    public static final int DUCKDUCKGO = 2;
    public static final int YAHOO = 3;
    public static final int BAIDU = 4;
    public static final int YANDEX = 5;

    public static String getSearchUrl(Context context, String query) {
        SharedPreferences prefs = context.getSharedPreferences("browser_prefs", Context.MODE_PRIVATE);
        int searchEngine = prefs.getInt(PREF_SEARCH_ENGINE, GOOGLE);

        String encodedQuery = query.replace(" ", "%20");

        switch (searchEngine) {
            case BING:
                return "https://www.bing.com/search?q=" + encodedQuery;
            case DUCKDUCKGO:
                return "https://duckduckgo.com/?q=" + encodedQuery;
            case YAHOO:
                return "https://search.yahoo.com/search?p=" + encodedQuery;
            case BAIDU:
                return "https://www.baidu.com/s?wd=" + encodedQuery;
            case YANDEX:
                return "https://yandex.com/search/?text=" + encodedQuery;
            case GOOGLE:
            default:
                return "https://www.google.com/search?q=" + encodedQuery;
        }
    }

    public static void setSearchEngine(Context context, int engine) {
        SharedPreferences prefs = context.getSharedPreferences("browser_prefs", Context.MODE_PRIVATE);
        prefs.edit().putInt(PREF_SEARCH_ENGINE, engine).apply();
    }

    public static int getSearchEngine(Context context) {
        SharedPreferences prefs = context.getSharedPreferences("browser_prefs", Context.MODE_PRIVATE);
        return prefs.getInt(PREF_SEARCH_ENGINE, GOOGLE);
    }

    public static String getSearchEngineName(int engine) {
        switch (engine) {
            case BING:
                return "Bing";
            case DUCKDUCKGO:
                return "DuckDuckGo";
            case YAHOO:
                return "Yahoo";
            case BAIDU:
                return "Baidu";
            case YANDEX:
                return "Yandex";
            case GOOGLE:
            default:
                return "Google";
        }
    }
}
