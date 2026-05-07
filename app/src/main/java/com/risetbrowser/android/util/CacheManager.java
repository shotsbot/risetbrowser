package com.risetbrowser.android.util;

import android.content.Context;
import android.os.Build;
import android.os.Environment;
import android.webkit.WebView;

import java.io.File;

public class CacheManager {
    private Context context;
    private static final String CACHE_DIR = "browser_cache";
    private static final String IMAGES_CACHE = "images";
    private static final String PAGES_CACHE = "pages";

    public CacheManager(Context context) {
        this.context = context;
    }

    public File getCacheDirectory() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            File cacheDir = new File(context.getCacheDir(), CACHE_DIR);
            if (!cacheDir.exists()) {
                cacheDir.mkdirs();
            }
            return cacheDir;
        } else {
            return context.getCacheDir();
        }
    }

    public File getImageCacheDirectory() {
        File imageCache = new File(getCacheDirectory(), IMAGES_CACHE);
        if (!imageCache.exists()) {
            imageCache.mkdirs();
        }
        return imageCache;
    }

    public File getPagesCacheDirectory() {
        File pagesCache = new File(getCacheDirectory(), PAGES_CACHE);
        if (!pagesCache.exists()) {
            pagesCache.mkdirs();
        }
        return pagesCache;
    }

    public long getCacheSize() {
        return BrowserUtil.getDirectorySize(getCacheDirectory());
    }

    public void clearCache(WebView webView) {
        if (webView != null) {
            webView.clearCache(true);
        }

        // Clear disk cache
        File cacheDir = getCacheDirectory();
        BrowserUtil.clearDirectoryCache(cacheDir);
    }

    public void clearImageCache() {
        File imageCache = getImageCacheDirectory();
        BrowserUtil.clearDirectoryCache(imageCache);
    }

    public void clearPageCache() {
        File pagesCache = getPagesCacheDirectory();
        BrowserUtil.clearDirectoryCache(pagesCache);
    }

    public void setCacheSize(int size) {
        // Size in MB, configure WebView cache size
    }

    public boolean isCacheFull() {
        long cacheSize = getCacheSize();
        long maxSize = 500 * 1024 * 1024; // 500MB default
        return cacheSize > maxSize;
    }

    public void trimCache() {
        if (isCacheFull()) {
            // Delete oldest files first
            File cacheDir = getCacheDirectory();
            deleteOldestFiles(cacheDir, (long) (300 * 1024 * 1024)); // Keep 300MB
        }
    }

    private void deleteOldestFiles(File dir, long targetSize) {
        File[] files = dir.listFiles();
        if (files == null) return;

        // Sort by last modified time
        java.util.Arrays.sort(files, (f1, f2) -> Long.compare(f1.lastModified(), f2.lastModified()));

        long currentSize = BrowserUtil.getDirectorySize(dir);

        for (File file : files) {
            if (currentSize < targetSize) {
                break;
            }

            if (file.isFile()) {
                currentSize -= file.length();
                file.delete();
            } else if (file.isDirectory()) {
                deleteOldestFiles(file, targetSize);
                if (file.listFiles().length == 0) {
                    file.delete();
                }
            }
        }
    }
}
