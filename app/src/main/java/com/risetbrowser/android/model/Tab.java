package com.risetbrowser.android.model;

import java.io.Serializable;

public class Tab implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private int id;
    private String title;
    private String url;
    private long timestamp;
    private boolean isActive;
    private String favicon;

    public Tab() {
        this.id = (int) System.currentTimeMillis();
        this.url = "https://www.google.com";
        this.title = "New Tab";
        this.timestamp = System.currentTimeMillis();
        this.isActive = false;
    }

    public Tab(String url, String title) {
        this.id = (int) System.currentTimeMillis();
        this.url = url;
        this.title = title;
        this.timestamp = System.currentTimeMillis();
        this.isActive = false;
    }

    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public String getFavicon() {
        return favicon;
    }

    public void setFavicon(String favicon) {
        this.favicon = favicon;
    }

    @Override
    public String toString() {
        return "Tab{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", url='" + url + '\'' +
                ", isActive=" + isActive +
                '}';
    }
}
