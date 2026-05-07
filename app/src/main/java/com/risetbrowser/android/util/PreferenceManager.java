package com.risetbrowser.android.util;

import android.content.Context;
import android.content.SharedPreferences;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class PreferenceManager {
    private Context context;
    private static final String PREFS_NAME = "browser_prefs";
    private SharedPreferences preferences;

    public PreferenceManager(Context context) {
        this.context = context;
        this.preferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
    }

    // Homepage
    public void setHomepage(String url) {
        preferences.edit().putString("homepage", url).apply();
    }

    public String getHomepage() {
        return preferences.getString("homepage", "https://www.google.com");
    }

    // Search Engine
    public void setSearchEngine(int engine) {
        preferences.edit().putInt("search_engine", engine).apply();
    }

    public int getSearchEngine() {
        return preferences.getInt("search_engine", 0);
    }

    // Features
    public void setAdBlockingEnabled(boolean enabled) {
        preferences.edit().putBoolean("ad_blocking", enabled).apply();
    }

    public boolean isAdBlockingEnabled() {
        return preferences.getBoolean("ad_blocking", true);
    }

    public void setPopupBlockingEnabled(boolean enabled) {
        preferences.edit().putBoolean("popup_blocking", enabled).apply();
    }

    public boolean isPopupBlockingEnabled() {
        return preferences.getBoolean("popup_blocking", true);
    }

    public void setJavaScriptEnabled(boolean enabled) {
        preferences.edit().putBoolean("javascript", enabled).apply();
    }

    public boolean isJavaScriptEnabled() {
        return preferences.getBoolean("javascript", true);
    }

    public void setNightModeEnabled(boolean enabled) {
        preferences.edit().putBoolean("night_mode", enabled).apply();
    }

    public boolean isNightModeEnabled() {
        return preferences.getBoolean("night_mode", false);
    }

    public void setDesktopModeEnabled(boolean enabled) {
        preferences.edit().putBoolean("desktop_mode", enabled).apply();
    }

    public boolean isDesktopModeEnabled() {
        return preferences.getBoolean("desktop_mode", false);
    }

    public void setDoNotTrackEnabled(boolean enabled) {
        preferences.edit().putBoolean("do_not_track", enabled).apply();
    }

    public boolean isDoNotTrackEnabled() {
        return preferences.getBoolean("do_not_track", true);
    }

    // Custom block lists
    public void addBlockedDomain(String domain) {
        Set<String> blocked = new HashSet<>(getBlockedDomains());
        blocked.add(domain);
        preferences.edit().putStringSet("blocked_domains", blocked).apply();
    }

    public void removeBlockedDomain(String domain) {
        Set<String> blocked = new HashSet<>(getBlockedDomains());
        blocked.remove(domain);
        preferences.edit().putStringSet("blocked_domains", blocked).apply();
    }

    public Set<String> getBlockedDomains() {
        return preferences.getStringSet("blocked_domains", new HashSet<>());
    }

    // Cache settings
    public void setCacheSize(int sizeMB) {
        preferences.edit().putInt("cache_size", sizeMB).apply();
    }

    public int getCacheSize() {
        return preferences.getInt("cache_size", 100);
    }

    // Color scheme
    public void setColorScheme(String scheme) {
        preferences.edit().putString("color_scheme", scheme).apply();
    }

    public String getColorScheme() {
        return preferences.getString("color_scheme", "auto");
    }

    // Text size
    public void setTextSize(int size) {
        preferences.edit().putInt("text_size", size).apply();
    }

    public int getTextSize() {
        return preferences.getInt("text_size", 100);
    }

    // Last opened tab
    public void setLastUrl(String url) {
        preferences.edit().putString("last_url", url).apply();
    }

    public String getLastUrl() {
        return preferences.getString("last_url", "https://www.google.com");
    }

    // Clear all preferences
    public void clearAll() {
        preferences.edit().clear().apply();
    }
}
