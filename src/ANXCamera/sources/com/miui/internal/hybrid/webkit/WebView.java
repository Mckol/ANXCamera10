package com.miui.internal.hybrid.webkit;

import android.content.Context;
import android.graphics.Canvas;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebViewClient;
import com.miui.internal.hybrid.provider.AbsWebChromeClient;
import com.miui.internal.hybrid.provider.AbsWebView;
import com.miui.internal.hybrid.provider.AbsWebViewClient;
import miui.hybrid.HybridBackForwardList;
import miui.hybrid.HybridSettings;
import miui.hybrid.HybridView;

public class WebView extends AbsWebView {
    protected android.webkit.WebView mWebView = new android.webkit.WebView(this.mContext);

    public WebView(Context context, HybridView hybridView) {
        super(context, hybridView);
    }

    @Override // com.miui.internal.hybrid.provider.AbsWebView
    public void addJavascriptInterface(Object obj, String str) {
        this.mWebView.addJavascriptInterface(obj, str);
    }

    @Override // com.miui.internal.hybrid.provider.AbsWebView
    public boolean canGoBack() {
        return this.mWebView.canGoBack();
    }

    @Override // com.miui.internal.hybrid.provider.AbsWebView
    public boolean canGoForward() {
        return this.mWebView.canGoForward();
    }

    @Override // com.miui.internal.hybrid.provider.AbsWebView
    public void clearCache(boolean z) {
        this.mWebView.clearCache(z);
    }

    @Override // com.miui.internal.hybrid.provider.AbsWebView
    public HybridBackForwardList copyBackForwardList() {
        return new WebBackForwardList(this.mWebView.copyBackForwardList());
    }

    @Override // com.miui.internal.hybrid.provider.AbsWebView
    public void destroy() {
        this.mWebView.destroy();
    }

    @Override // com.miui.internal.hybrid.provider.AbsWebView
    public void draw(Canvas canvas) {
        this.mWebView.draw(canvas);
    }

    @Override // com.miui.internal.hybrid.provider.AbsWebView
    public View getBaseWebView() {
        return this.mWebView;
    }

    @Override // com.miui.internal.hybrid.provider.AbsWebView
    public int getContentHeight() {
        return this.mWebView.getContentHeight();
    }

    @Override // com.miui.internal.hybrid.provider.AbsWebView
    public Context getContext() {
        return this.mWebView.getContext();
    }

    @Override // com.miui.internal.hybrid.provider.AbsWebView
    public View getRootView() {
        return this.mWebView.getRootView();
    }

    @Override // com.miui.internal.hybrid.provider.AbsWebView
    public float getScale() {
        return this.mWebView.getScale();
    }

    @Override // com.miui.internal.hybrid.provider.AbsWebView
    public HybridSettings getSettings() {
        return new WebSettings(this.mWebView.getSettings());
    }

    @Override // com.miui.internal.hybrid.provider.AbsWebView
    public String getTitle() {
        return this.mWebView.getTitle();
    }

    @Override // com.miui.internal.hybrid.provider.AbsWebView
    public String getUrl() {
        return this.mWebView.getUrl();
    }

    @Override // com.miui.internal.hybrid.provider.AbsWebView
    public void goBack() {
        this.mWebView.goBack();
    }

    @Override // com.miui.internal.hybrid.provider.AbsWebView
    public void loadUrl(String str) {
        this.mWebView.loadUrl(str);
    }

    @Override // com.miui.internal.hybrid.provider.AbsWebView
    public void reload() {
        this.mWebView.reload();
    }

    @Override // com.miui.internal.hybrid.provider.AbsWebView
    public void setVisibility(int i) {
        this.mWebView.setVisibility(i);
    }

    @Override // com.miui.internal.hybrid.provider.AbsWebView
    public void setWebChromeClient(AbsWebChromeClient absWebChromeClient) {
        this.mWebView.setWebChromeClient((WebChromeClient) absWebChromeClient.getWebChromeClient());
    }

    @Override // com.miui.internal.hybrid.provider.AbsWebView
    public void setWebViewClient(AbsWebViewClient absWebViewClient) {
        this.mWebView.setWebViewClient((WebViewClient) absWebViewClient.getWebViewClient());
    }
}
