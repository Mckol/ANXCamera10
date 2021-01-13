package com.miui.internal.hybrid.provider;

import android.net.Uri;
import miui.hybrid.GeolocationPermissions;
import miui.hybrid.HybridChromeClient;
import miui.hybrid.HybridView;
import miui.hybrid.JsResult;
import miui.hybrid.ValueCallback;

public abstract class AbsWebChromeClient {
    protected HybridChromeClient mHybridChromeClient;
    protected HybridView mHybridView;

    public AbsWebChromeClient(HybridChromeClient hybridChromeClient, HybridView hybridView) {
        this.mHybridChromeClient = hybridChromeClient;
        this.mHybridView = hybridView;
    }

    public Object getWebChromeClient() {
        return null;
    }

    public void onGeolocationPermissionsShowPrompt(String str, GeolocationPermissions.Callback callback) {
    }

    public boolean onJsAlert(HybridView hybridView, String str, String str2, JsResult jsResult) {
        return false;
    }

    public boolean onJsConfirm(HybridView hybridView, String str, String str2, JsResult jsResult) {
        return false;
    }

    public void onProgressChanged(HybridView hybridView, int i) {
    }

    public void onReceivedTitle(HybridView hybridView, String str) {
    }

    public void openFileChooser(ValueCallback<Uri> valueCallback, String str, String str2) {
    }
}
