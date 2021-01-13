package com.miui.internal.hybrid;

import android.util.Log;
import android.webkit.JavascriptInterface;

public class JsInterface {
    public static final String INTERFACE_NAME = "MiuiJsBridge";
    private HybridManager mManager;

    public JsInterface(HybridManager hybridManager) {
        this.mManager = hybridManager;
    }

    @JavascriptInterface
    public String config(String str) {
        String config = this.mManager.config(str);
        if (Log.isLoggable(HybridManager.TAG, 3)) {
            Log.d(HybridManager.TAG, "config response is " + config);
        }
        return config;
    }

    @JavascriptInterface
    public String invoke(String str, String str2, String str3, String str4) {
        String invoke = this.mManager.invoke(str, str2, str3, str4);
        if (Log.isLoggable(HybridManager.TAG, 3)) {
            Log.d(HybridManager.TAG, "blocking response is " + invoke);
        }
        return invoke;
    }

    @JavascriptInterface
    public String lookup(String str, String str2) {
        String lookup = this.mManager.lookup(str, str2);
        if (Log.isLoggable(HybridManager.TAG, 3)) {
            Log.d(HybridManager.TAG, "lookup response is " + lookup);
        }
        return lookup;
    }
}
