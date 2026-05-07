package com.risetbrowser.android.feature;

import android.webkit.WebView;

public class PageTranslator {
    private WebView webView;
    private String currentLanguage = "en";

    public PageTranslator(WebView webView) {
        this.webView = webView;
    }

    public void translatePage(String targetLanguage) {
        currentLanguage = targetLanguage;
        injectTranslationScript(targetLanguage);
    }

    private void injectTranslationScript(String language) {
        String script = "(" +
            "function() {" +
            "  if (window.google && window.google.translate) {" +
            "    var googleTranslateElement = document.getElementById('google_translate_element');" +
            "    if (googleTranslateElement) {" +
            "      new google.translate.TranslateElement({" +
            "        pageLanguage: 'en'," +
            "        includedLanguages: 'en,es,fr,de,it,pt,ru,ja,ko,zh-CN,ar'" +
            "      }, 'google_translate_element');" +
            "    }" +
            "  }" +
            "})();";

        webView.evaluateJavascript(script, null);
    }

    public void resetTranslation() {
        currentLanguage = "en";
        String script = "(" +
            "function() {" +
            "  location.reload();" +
            "})();";
        webView.evaluateJavascript(script, null);
    }

    public String getCurrentLanguage() {
        return currentLanguage;
    }

    public boolean isTranslationEnabled() {
        return !currentLanguage.equals("en");
    }
}
