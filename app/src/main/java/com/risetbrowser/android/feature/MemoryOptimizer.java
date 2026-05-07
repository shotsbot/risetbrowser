package com.risetbrowser.android.feature;

import android.webkit.WebView;

public class MemoryOptimizer {
    private WebView webView;

    public MemoryOptimizer(WebView webView) {
        this.webView = webView;
    }

    public void optimizeMemory() {
        // Lazy load images
        lazyLoadImages();

        // Defer off-screen content
        deferOffscreenImages();

        // Remove unused CSS
        removeUnusedCSS();

        // Minimize reflows
        minimizeReflows();
    }

    private void lazyLoadImages() {
        String script = "(" +
            "function() {" +
            "  if ('IntersectionObserver' in window) {" +
            "    var imageObserver = new IntersectionObserver(function(entries, observer) {" +
            "      entries.forEach(function(entry) {" +
            "        if (entry.isIntersecting) {" +
            "          var img = entry.target;" +
            "          if (img.dataset.src) {" +
            "            img.src = img.dataset.src;" +
            "            observer.unobserve(img);" +
            "          }" +
            "        }" +
            "      });" +
            "    });" +
            "    document.querySelectorAll('img[data-src]').forEach(function(img) {" +
            "      imageObserver.observe(img);" +
            "    });" +
            "  }" +
            "})();";

        webView.evaluateJavascript(script, null);
    }

    private void deferOffscreenImages() {
        String script = "(" +
            "function() {" +
            "  window.addEventListener('scroll', function() {" +
            "    var images = document.querySelectorAll('img');" +
            "    images.forEach(function(img) {" +
            "      var rect = img.getBoundingClientRect();" +
            "      if (rect.top > window.innerHeight) {" +
            "        img.loading = 'lazy';" +
            "      }" +
            "    });" +
            "  });" +
            "})();";

        webView.evaluateJavascript(script, null);
    }

    private void removeUnusedCSS() {
        String script = "(" +
            "function() {" +
            "  var stylesheets = document.styleSheets;" +
            "  for (var i = 0; i < stylesheets.length; i++) {" +
            "    try {" +
            "      var rules = stylesheets[i].cssRules;" +
            "      for (var j = 0; j < rules.length; j++) {" +
            "        var rule = rules[j];" +
            "        var selector = rule.selectorText;" +
            "        if (selector && !document.querySelector(selector)) {" +
            "          stylesheets[i].deleteRule(j);" +
            "        }" +
            "      }" +
            "    } catch(e) {}" +
            "  }" +
            "})();";

        webView.evaluateJavascript(script, null);
    }

    private void minimizeReflows() {
        String script = "(" +
            "function() {" +
            "  var fragment = document.createDocumentFragment();" +
            "  document.addEventListener('DOMContentLoaded', function() {" +
            "    var operations = [];" +
            "    function batchUpdate(callback) {" +
            "      operations.push(callback);" +
            "      requestAnimationFrame(function() {" +
            "        operations.forEach(function(op) { op(); });" +
            "        operations = [];" +
            "      });" +
            "    }" +
            "    window.batchUpdate = batchUpdate;" +
            "  });" +
            "})();";

        webView.evaluateJavascript(script, null);
    }

    public void clearUnusedCache() {
        webView.clearCache(false);
    }

    public void enableCompression() {
        String script = "(" +
            "function() {" +
            "  document.querySelectorAll('img').forEach(function(img) {" +
            "    var canvas = document.createElement('canvas');" +
            "    canvas.width = img.width;" +
            "    canvas.height = img.height;" +
            "    var ctx = canvas.getContext('2d');" +
            "    ctx.drawImage(img, 0, 0);" +
            "    img.src = canvas.toDataURL('image/jpeg', 0.7);" +
            "  });" +
            "})();";

        webView.evaluateJavascript(script, null);
    }
}
