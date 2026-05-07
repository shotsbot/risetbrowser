package com.risetbrowser.android.feature;

import android.webkit.WebView;

public class CustomScriptInjector {
    private WebView webView;

    public CustomScriptInjector(WebView webView) {
        this.webView = webView;
    }

    public void injectCustomCSS(String cssCode) {
        String script = "(" +
            "function() {" +
            "  var style = document.createElement('style');" +
            "  style.innerHTML = `" + cssCode + "`;" +
            "  document.head.appendChild(style);" +
            "})();";

        webView.evaluateJavascript(script, null);
    }

    public void injectCustomJavaScript(String jsCode) {
        webView.evaluateJavascript(jsCode, null);
    }

    public void injectUserAgent(String userAgent) {
        webView.getSettings().setUserAgentString(userAgent);
    }

    public void blockImages() {
        String css = "img { display: none !important; }";
        injectCustomCSS(css);
    }

    public void blockVideos() {
        String css = "video { display: none !important; } iframe { display: none !important; }";
        injectCustomCSS(css);
    }

    public void increaseLineHeight() {
        String css = "* { line-height: 2 !important; }";
        injectCustomCSS(css);
    }

    public void highlightCode() {
        String script = "(" +
            "function() {" +
            "  var codes = document.querySelectorAll('code, pre');" +
            "  codes.forEach(function(code) {" +
            "    code.style.backgroundColor = '#f0f0f0';" +
            "    code.style.padding = '10px';" +
            "    code.style.borderRadius = '5px';" +
            "    code.style.fontFamily = 'monospace';" +
            "  });" +
            "})();";

        webView.evaluateJavascript(script, null);
    }

    public void removeAds() {
        String script = "(" +
            "function() {" +
            "  var ads = document.querySelectorAll('[class*=\"ad\"], [id*=\"ad\"], .advertisement');" +
            "  ads.forEach(function(ad) {" +
            "    ad.style.display = 'none';" +
            "  });" +
            "})();";

        webView.evaluateJavascript(script, null);
    }

    public void disableRightClick() {
        String script = "(" +
            "function() {" +
            "  document.addEventListener('contextmenu', function(e) {" +
            "    e.preventDefault();" +
            "  });" +
            "})();";

        webView.evaluateJavascript(script, null);
    }

    public void enableRightClick() {
        String script = "(" +
            "function() {" +
            "  document.removeEventListener('contextmenu', function(e) {" +
            "    e.preventDefault();" +
            "  });" +
            "})();";

        webView.evaluateJavascript(script, null);
    }
}
