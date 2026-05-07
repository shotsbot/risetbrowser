package com.risetbrowser.android.feature;

import android.webkit.WebView;
import android.content.Context;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ReaderMode {
    private WebView webView;
    private Context context;
    private boolean isEnabled = false;

    public ReaderMode(WebView webView, Context context) {
        this.webView = webView;
        this.context = context;
    }

    public void enableReaderMode() {
        isEnabled = true;
        injectReaderModeCSS();
    }

    public void disableReaderMode() {
        isEnabled = false;
        removeReaderModeCSS();
    }

    private void injectReaderModeCSS() {
        String readerCSS = "(" +
            "function() {" +
            "  var style = document.createElement('style');" +
            "  style.innerHTML = `" +
            "    body {" +
            "      background-color: #f5f5f5;" +
            "      color: #333;" +
            "      font-family: 'Georgia', serif;" +
            "      font-size: 18px;" +
            "      line-height: 1.8;" +
            "      max-width: 800px;" +
            "      margin: 0 auto;" +
            "      padding: 20px;" +
            "    }" +
            "    article, main, .content, .post {" +
            "      background: white;" +
            "      padding: 30px;" +
            "      border-radius: 8px;" +
            "      box-shadow: 0 2px 10px rgba(0,0,0,0.1);" +
            "    }" +
            "    img { max-width: 100%; height: auto; }" +
            "    code { background: #f0f0f0; padding: 2px 6px; border-radius: 3px; }" +
            "    `;" +
            "  document.head.appendChild(style);" +
            "})();";

        webView.evaluateJavascript(readerCSS, null);
    }

    private void removeReaderModeCSS() {
        String removeCSS = "(" +
            "function() {" +
            "  location.reload();" +
            "})();";

        webView.evaluateJavascript(removeCSS, null);
    }

    public int getEstimatedReadingTime(String htmlContent) {
        // Average reading speed: 200 words per minute
        Pattern pattern = Pattern.compile("\\b\\w+\\b");
        Matcher matcher = pattern.matcher(htmlContent);
        int wordCount = 0;
        while (matcher.find()) {
            wordCount++;
        }
        return Math.max(1, wordCount / 200);
    }

    public boolean isEnabled() {
        return isEnabled;
    }
}
