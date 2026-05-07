package com.risetbrowser.android.util;

import android.webkit.WebView;
import com.risetbrowser.android.model.Tab;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TabManager {
    private List<Tab> tabs;
    private Map<Integer, WebView> tabWebViews;
    private int activeTabId;

    public TabManager() {
        this.tabs = new ArrayList<>();
        this.tabWebViews = new HashMap<>();
        
        // Create default tab
        Tab defaultTab = new Tab();
        defaultTab.setTitle("New Tab");
        defaultTab.setUrl("https://www.google.com");
        defaultTab.setActive(true);
        tabs.add(defaultTab);
        activeTabId = defaultTab.getId();
    }

    public Tab createNewTab(String url) {
        Tab newTab = new Tab(url, "Loading...");
        tabs.add(newTab);
        return newTab;
    }

    public Tab createNewIncognitoTab() {
        Tab incognitoTab = new Tab();
        incognitoTab.setTitle("Incognito Tab");
        incognitoTab.setUrl("about:blank");
        tabs.add(incognitoTab);
        return incognitoTab;
    }

    public void setActiveTab(int tabId) {
        for (Tab tab : tabs) {
            tab.setActive(tab.getId() == tabId);
        }
        activeTabId = tabId;
    }

    public Tab getActiveTab() {
        for (Tab tab : tabs) {
            if (tab.isActive()) {
                return tab;
            }
        }
        return tabs.isEmpty() ? null : tabs.get(0);
    }

    public Tab getTabById(int tabId) {
        for (Tab tab : tabs) {
            if (tab.getId() == tabId) {
                return tab;
            }
        }
        return null;
    }

    public void closeTab(int tabId) {
        Tab tabToRemove = null;
        for (Tab tab : tabs) {
            if (tab.getId() == tabId) {
                tabToRemove = tab;
                break;
            }
        }

        if (tabToRemove != null) {
            tabs.remove(tabToRemove);
            tabWebViews.remove(tabId);

            // If closed tab was active, activate another
            if (tabToRemove.isActive() && !tabs.isEmpty()) {
                setActiveTab(tabs.get(0).getId());
            }
        }
    }

    public void closeAllTabs() {
        tabs.clear();
        tabWebViews.clear();
        Tab defaultTab = new Tab();
        defaultTab.setActive(true);
        tabs.add(defaultTab);
    }

    public List<Tab> getAllTabs() {
        return new ArrayList<>(tabs);
    }

    public int getTabCount() {
        return tabs.size();
    }

    public void registerWebView(int tabId, WebView webView) {
        tabWebViews.put(tabId, webView);
    }

    public WebView getWebView(int tabId) {
        return tabWebViews.get(tabId);
    }

    public void updateTabTitle(int tabId, String title) {
        Tab tab = getTabById(tabId);
        if (tab != null) {
            tab.setTitle(title);
        }
    }

    public void updateTabUrl(int tabId, String url) {
        Tab tab = getTabById(tabId);
        if (tab != null) {
            tab.setUrl(url);
        }
    }
}
