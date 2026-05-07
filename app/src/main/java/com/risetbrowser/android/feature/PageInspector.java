package com.risetbrowser.android.feature;

import android.webkit.WebView;

public class PageInspector {
    private WebView webView;

    public PageInspector(WebView webView) {
        this.webView = webView;
    }

    public void getPageMetadata(ValueCallback callback) {
        String script = "(" +
            "function() {" +
            "  return {" +
            "    title: document.title," +
            "    url: window.location.href," +
            "    charset: document.charset," +
            "    language: document.documentElement.lang," +
            "    images: document.images.length," +
            "    links: document.links.length," +
            "    scripts: document.scripts.length," +
            "    stylesheets: document.styleSheets.length," +
            "    forms: document.forms.length," +
            "    iframes: document.iframes ? document.iframes.length : 0," +
            "    viewportWidth: window.innerWidth," +
            "    viewportHeight: window.innerHeight," +
            "    pageLoadTime: performance.timing.loadEventEnd - performance.timing.navigationStart" +
            "  };" +
            "})();";

        webView.evaluateJavascript(script, result -> {
            if (callback != null) {
                callback.onResult(result);
            }
        });
    }

    public void getExternalLinks(ValueCallback callback) {
        String script = "(" +
            "function() {" +
            "  var links = [];" +
            "  document.querySelectorAll('a[href^=\"http\"]').forEach(function(link) {" +
            "    if (link.hostname !== window.location.hostname) {" +
            "      links.push({" +
            "        text: link.textContent," +
            "        href: link.href" +
            "      });" +
            "    }" +
            "  });" +
            "  return JSON.stringify(links);" +
            "})();";

        webView.evaluateJavascript(script, result -> {
            if (callback != null) {
                callback.onResult(result);
            }
        });
    }

    public void getAllImages(ValueCallback callback) {
        String script = "(" +
            "function() {" +
            "  var images = [];" +
            "  document.querySelectorAll('img').forEach(function(img) {" +
            "    images.push({" +
            "      src: img.src," +
            "      alt: img.alt," +
            "      width: img.width," +
            "      height: img.height" +
            "    });" +
            "  });" +
            "  return JSON.stringify(images);" +
            "})();";

        webView.evaluateJavascript(script, result -> {
            if (callback != null) {
                callback.onResult(result);
            }
        });
    }

    public void analyzePagePerformance(ValueCallback callback) {
        String script = "(" +
            "function() {" +
            "  var perf = performance.getEntriesByType('navigation')[0];" +
            "  return {" +
            "    dnsLookup: perf.domainLookupEnd - perf.domainLookupStart," +
            "    tcpConnection: perf.connectEnd - perf.connectStart," +
            "    requestTime: perf.responseStart - perf.requestStart," +
            "    responseTime: perf.responseEnd - perf.responseStart," +
            "    domInteractive: perf.domInteractive," +
            "    domComplete: perf.domComplete," +
            "    loadComplete: perf.loadEventEnd" +
            "  };" +
            "})();";

        webView.evaluateJavascript(script, result -> {
            if (callback != null) {
                callback.onResult(result);
            }
        });
    }

    public interface ValueCallback {
        void onResult(String result);
    }

    public void highlightAllLinks() {
        String script = "(" +
            "function() {" +
            "  document.querySelectorAll('a').forEach(function(link) {" +
            "    link.style.outline = '2px solid red';" +
            "    link.style.outlineOffset = '2px';" +
            "  });" +
            "})();";

        webView.evaluateJavascript(script, null);
    }

    public void showPageStructure() {
        String script = "(" +
            "function() {" +
            "  document.querySelectorAll('*').forEach(function(element) {" +
            "    element.style.outline = '1px solid rgba(0,0,255,0.3)';" +
            "  });" +
            "})();";

        webView.evaluateJavascript(script, null);
    }
}
