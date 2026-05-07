package com.risetbrowser.android.service;

import android.webkit.WebView;
import java.util.regex.Pattern;

public class PopupBlocker {
    private WebView webView;
    private Pattern popupPattern;

    public PopupBlocker(WebView webView) {
        this.webView = webView;
        initializePatterns();
    }

    private void initializePatterns() {
        // Pattern to detect common popup-opening patterns
        popupPattern = Pattern.compile(
            "window\\.(open|showModalDialog|alert).*?\\)|onclick.*?window\\.open",
            Pattern.CASE_INSENSITIVE | Pattern.DOTALL
        );
    }

    public void blockPopups() {
        injectBlockerScript();
    }

    private void injectBlockerScript() {
        String script = "(" +
            "function() {" +
            "  window.open = function(url, target, features) {" +
            "    console.log('Popup blocked: ' + url);" +
            "    return window;" +
            "  };" +
            "  " +
            "  window.showModalDialog = function() {" +
            "    console.log('Modal dialog blocked');" +
            "    return null;" +
            "  };" +
            "  " +
            "  // Block onclick popups" +
            "  document.addEventListener('click', function(e) {" +
            "    if (e.target.onclick && e.target.onclick.toString().includes('window.open')) {" +
            "      e.preventDefault();" +
            "    }" +
            "  }, true);" +
            "  " +
            "  // Block form submissions to popup windows" +
            "  document.addEventListener('submit', function(e) {" +
            "    var target = e.target.getAttribute('target');" +
            "    if (target && target !== '_self' && target !== '_top' && target !== '_parent') {" +
            "      e.target.setAttribute('target', '_self');" +
            "    }" +
            "  }, true);" +
            "})();";

        webView.evaluateJavascript(script, null);
    }

    public boolean isPopupUrl(String url) {
        return url != null && (url.contains("pop") || url.contains("ad") || url.contains("click"));
    }

    public void disablePopupByTarget() {
        String script = "document.addEventListener('click', function(event) {" +
            "  var target = event.target.getAttribute('target');" +
            "  if (target && target !== '_self') {" +
            "    event.target.removeAttribute('target');" +
            "  }" +
            "}, true);";

        webView.evaluateJavascript(script, null);
    }
}
