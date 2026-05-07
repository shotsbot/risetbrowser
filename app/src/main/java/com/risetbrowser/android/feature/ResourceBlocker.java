package com.risetbrowser.android.feature;

import android.webkit.WebView;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;

import java.util.HashSet;
import java.util.Set;

public class ResourceBlocker {
    private Set<String> blockedResources;
    private boolean blockImages = false;
    private boolean blockCSS = false;
    private boolean blockJavaScript = false;
    private boolean blockFonts = false;

    public ResourceBlocker() {
        this.blockedResources = new HashSet<>();
    }

    public void blockImages(boolean block) {
        this.blockImages = block;
    }

    public void blockCSS(boolean block) {
        this.blockCSS = block;
    }

    public void blockJavaScript(boolean block) {
        this.blockJavaScript = block;
    }

    public void blockFonts(boolean block) {
        this.blockFonts = block;
    }

    public void addResourceToBlock(String url) {
        blockedResources.add(url);
    }

    public void removeResourceFromBlock(String url) {
        blockedResources.remove(url);
    }

    public boolean shouldBlock(String url, String resourceType) {
        // Check custom blocked resources
        for (String blocked : blockedResources) {
            if (url.contains(blocked)) {
                return true;
            }
        }

        // Check resource type
        if (blockImages && isImageResource(url)) {
            return true;
        }

        if (blockCSS && isStyleResource(url)) {
            return true;
        }

        if (blockJavaScript && isScriptResource(url)) {
            return true;
        }

        if (blockFonts && isFontResource(url)) {
            return true;
        }

        return false;
    }

    private boolean isImageResource(String url) {
        return url.endsWith(".jpg") || url.endsWith(".png") || url.endsWith(".gif") ||
               url.endsWith(".webp") || url.contains("image");
    }

    private boolean isStyleResource(String url) {
        return url.endsWith(".css");
    }

    private boolean isScriptResource(String url) {
        return url.endsWith(".js");
    }

    private boolean isFontResource(String url) {
        return url.endsWith(".ttf") || url.endsWith(".woff") || url.endsWith(".woff2") ||
               url.contains("fonts");
    }

    public void resetBlockList() {
        blockedResources.clear();
        blockImages = false;
        blockCSS = false;
        blockJavaScript = false;
        blockFonts = false;
    }
}
