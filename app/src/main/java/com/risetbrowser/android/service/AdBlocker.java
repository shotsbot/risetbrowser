package com.risetbrowser.android.service;

import android.content.Context;
import java.util.HashSet;
import java.util.Set;

public class AdBlocker {
    private Set<String> adDomains;
    private Context context;

    public AdBlocker(Context context) {
        this.context = context;
        initializeAdPatterns();
    }

    private void initializeAdPatterns() {
        adDomains = new HashSet<>();
        // Comprehensive list of ad domains
        adDomains.addAll(java.util.Arrays.asList(
            "ads.google.com", "googleadservices.com", "googlesyndication.com",
            "adswizz.com", "amazon-adsystem.com", "doubleclick.net",
            "facebook.com", "facebook.net",
            "criteo.com", "criteo.net",
            "rubiconproject.com",
            "quantserve.com",
            "contextual.media.net",
            "contextual.intellimad.com",
            "advertising.com",
            "247realmedia.com",
            "adserver.com",
            "atdmt.com",
            "bluestreak.com",
            "buysellads.com",
            "casalemedia.com",
            "exoclick.com",
            "exponential.com",
            "fastclick.net",
            "fimserve.com",
            "fxnetworks.com",
            "gaming.youtube.com",
            "hitbox.com",
            "intelliad.com",
            "interclick.com",
            "invengo.com",
            "jivox.com",
            "kenshoo.com",
            "lycos-inc.com",
            "mediaplex.com",
            "mojo.com",
            "nextag.com",
            "omniture.com",
            "overture.com",
            "pegasus.com",
            "phpadsnew.com",
            "pubmatic.com",
            "pulsepoint.com",
            "realmedia.com",
            "revenuehits.com",
            "revinetworks.com",
            "romedia.com",
            "rottenads.com",
            "saymedia.com",
            "serving-sys.com",
            "smaato.com",
            "smartadserver.com",
            "specificmedia.com",
            "stg7.com",
            "threadlogic.com",
            "tlvmedia.com",
            "trafficjam.com",
            "tribalfusion.com",
            "turn.com",
            "undergroundcircus.com",
            "undertone.com",
            "valueclick.com",
            "videohub.com",
            "visualrevenue.com",
            "vodacom.co.za",
            "wahoonetworks.com",
            "webspectator.com",
            "x-click.com",
            "xads.zedo.com",
            "xboxlive.com",
            "yieldlab.de",
            "yieldmanager.com",
            "yieldoptimizer.com",
            "zedo.com"
        ));
    }

    public boolean shouldBlock(String url) {
        if (url == null || url.isEmpty()) {
            return false;
        }

        // Check if URL contains ad patterns
        for (String domain : adDomains) {
            if (url.contains(domain)) {
                return true;
            }
        }

        // Block common ad patterns
        if (url.contains("/ads/") || url.contains("/ad/") ||
            url.contains("/banner/") || url.contains("/popup/") ||
            url.contains("ad_") || url.contains("advertisement") ||
            url.contains("advertising") || url.contains("advert") ||
            url.contains("analytics") || url.contains("tracking")) {
            return true;
        }

        return false;
    }

    public void addCustomBlockList(String domain) {
        adDomains.add(domain);
    }

    public void removeCustomBlockList(String domain) {
        adDomains.remove(domain);
    }

    public Set<String> getBlockedDomains() {
        return new HashSet<>(adDomains);
    }
}
