package com.miui.internal.hybrid.webkit;

import android.net.Uri;
import android.webkit.GeolocationPermissions;
import android.webkit.JsResult;
import android.webkit.ValueCallback;
import android.webkit.WebView;
import com.miui.internal.hybrid.provider.AbsWebChromeClient;
import miui.hybrid.GeolocationPermissions;
import miui.hybrid.HybridChromeClient;
import miui.hybrid.HybridView;

public class WebChromeClient extends AbsWebChromeClient {

    class InternalWebChromeClient extends android.webkit.WebChromeClient {
        InternalWebChromeClient() {
        }

        public void onGeolocationPermissionsShowPrompt(String str, GeolocationPermissions.Callback callback) {
            WebChromeClient.this.onGeolocationPermissionsShowPrompt(str, new GeolocationPermissionsCallback(callback));
        }

        public boolean onJsAlert(WebView webView, String str, String str2, JsResult jsResult) {
            JsResult jsResult2 = new JsResult(jsResult);
            WebChromeClient webChromeClient = WebChromeClient.this;
            return webChromeClient.onJsAlert(((AbsWebChromeClient) webChromeClient).mHybridView, str, str2, jsResult2);
        }

        public boolean onJsConfirm(WebView webView, String str, String str2, JsResult jsResult) {
            JsResult jsResult2 = new JsResult(jsResult);
            WebChromeClient webChromeClient = WebChromeClient.this;
            return webChromeClient.onJsConfirm(((AbsWebChromeClient) webChromeClient).mHybridView, str, str2, jsResult2);
        }

        public void onProgressChanged(WebView webView, int i) {
            WebChromeClient webChromeClient = WebChromeClient.this;
            webChromeClient.onProgressChanged(((AbsWebChromeClient) webChromeClient).mHybridView, i);
        }

        public void onReceivedTitle(WebView webView, String str) {
            WebChromeClient webChromeClient = WebChromeClient.this;
            webChromeClient.onReceivedTitle(((AbsWebChromeClient) webChromeClient).mHybridView, str);
        }

        public void openFileChooser(ValueCallback<Uri> valueCallback, String str, String str2) {
            WebChromeClient.this.openFileChooser(new ValueCallback(valueCallback), str, str2);
        }
    }

    public WebChromeClient(HybridChromeClient hybridChromeClient, HybridView hybridView) {
        super(hybridChromeClient, hybridView);
    }

    @Override // com.miui.internal.hybrid.provider.AbsWebChromeClient
    public Object getWebChromeClient() {
        return new InternalWebChromeClient();
    }

    @Override // com.miui.internal.hybrid.provider.AbsWebChromeClient
    public void onGeolocationPermissionsShowPrompt(String str, GeolocationPermissions.Callback callback) {
        this.mHybridChromeClient.onGeolocationPermissionsShowPrompt(str, callback);
    }

    @Override // com.miui.internal.hybrid.provider.AbsWebChromeClient
    public boolean onJsAlert(HybridView hybridView, String str, String str2, miui.hybrid.JsResult jsResult) {
        return this.mHybridChromeClient.onJsAlert(hybridView, str, str2, jsResult);
    }

    @Override // com.miui.internal.hybrid.provider.AbsWebChromeClient
    public boolean onJsConfirm(HybridView hybridView, String str, String str2, miui.hybrid.JsResult jsResult) {
        return this.mHybridChromeClient.onJsConfirm(hybridView, str, str2, jsResult);
    }

    @Override // com.miui.internal.hybrid.provider.AbsWebChromeClient
    public void onProgressChanged(HybridView hybridView, int i) {
        this.mHybridChromeClient.onProgressChanged(hybridView, i);
    }

    @Override // com.miui.internal.hybrid.provider.AbsWebChromeClient
    public void onReceivedTitle(HybridView hybridView, String str) {
        this.mHybridChromeClient.onReceivedTitle(hybridView, str);
    }

    @Override // com.miui.internal.hybrid.provider.AbsWebChromeClient
    public void openFileChooser(miui.hybrid.ValueCallback<Uri> valueCallback, String str, String str2) {
        this.mHybridChromeClient.openFileChooser(valueCallback, str, str2);
    }
}
