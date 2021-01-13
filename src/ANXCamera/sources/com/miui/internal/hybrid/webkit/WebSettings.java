package com.miui.internal.hybrid.webkit;

import miui.hybrid.HybridSettings;

public class WebSettings extends HybridSettings {
    private android.webkit.WebSettings mWebSettings;

    public WebSettings(android.webkit.WebSettings webSettings) {
        this.mWebSettings = webSettings;
    }

    @Override // miui.hybrid.HybridSettings
    public String getUserAgentString() {
        return this.mWebSettings.getUserAgentString();
    }

    @Override // miui.hybrid.HybridSettings
    public void setAllowFileAccessFromFileURLs(boolean z) {
        this.mWebSettings.setAllowFileAccessFromFileURLs(z);
    }

    @Override // miui.hybrid.HybridSettings
    public void setAllowUniversalAccessFromFileURLs(boolean z) {
        this.mWebSettings.setAllowUniversalAccessFromFileURLs(z);
    }

    @Override // miui.hybrid.HybridSettings
    public void setAppCacheEnabled(boolean z) {
        this.mWebSettings.setAppCacheEnabled(z);
    }

    @Override // miui.hybrid.HybridSettings
    public void setAppCachePath(String str) {
        this.mWebSettings.setAppCachePath(str);
    }

    @Override // miui.hybrid.HybridSettings
    public void setCacheMode(int i) {
        this.mWebSettings.setCacheMode(i);
    }

    @Override // miui.hybrid.HybridSettings
    public void setDatabaseEnabled(boolean z) {
        this.mWebSettings.setDatabaseEnabled(z);
    }

    @Override // miui.hybrid.HybridSettings
    public void setDomStorageEnabled(boolean z) {
        this.mWebSettings.setDomStorageEnabled(z);
    }

    @Override // miui.hybrid.HybridSettings
    public void setGeolocationDatabasePath(String str) {
        this.mWebSettings.setGeolocationDatabasePath(str);
    }

    @Override // miui.hybrid.HybridSettings
    public void setGeolocationEnabled(boolean z) {
        this.mWebSettings.setGeolocationEnabled(z);
    }

    @Override // miui.hybrid.HybridSettings
    public void setJavaScriptCanOpenWindowsAutomatically(boolean z) {
        this.mWebSettings.setJavaScriptCanOpenWindowsAutomatically(z);
    }

    @Override // miui.hybrid.HybridSettings
    public void setJavaScriptEnabled(boolean z) {
        this.mWebSettings.setJavaScriptEnabled(z);
    }

    @Override // miui.hybrid.HybridSettings
    public void setLoadWithOverviewMode(boolean z) {
        this.mWebSettings.setLoadWithOverviewMode(z);
    }

    @Override // miui.hybrid.HybridSettings
    public void setSupportMultipleWindows(boolean z) {
        this.mWebSettings.setSupportMultipleWindows(z);
    }

    @Override // miui.hybrid.HybridSettings
    public void setTextZoom(int i) {
        this.mWebSettings.setTextZoom(i);
    }

    @Override // miui.hybrid.HybridSettings
    public void setUseWideViewPort(boolean z) {
        this.mWebSettings.setUseWideViewPort(z);
    }

    @Override // miui.hybrid.HybridSettings
    public void setUserAgentString(String str) {
        this.mWebSettings.setUserAgentString(str);
    }
}
