package com.miui.internal.hybrid.webkit;

import android.content.Context;
import com.miui.internal.hybrid.provider.AbsCookieSyncManager;
import com.miui.internal.hybrid.provider.AbsWebChromeClient;
import com.miui.internal.hybrid.provider.AbsWebView;
import com.miui.internal.hybrid.provider.AbsWebViewClient;
import com.miui.internal.hybrid.provider.WebViewFactoryProvider;
import miui.hybrid.CookieManager;
import miui.hybrid.HybridChromeClient;
import miui.hybrid.HybridView;
import miui.hybrid.HybridViewClient;

public class WebkitFactoryProvider extends WebViewFactoryProvider {
    private CookieManager mCookieManager;
    private AbsCookieSyncManager mCookieSyncManager;

    @Override // com.miui.internal.hybrid.provider.WebViewFactoryProvider
    public AbsWebChromeClient createWebChromeClient(HybridChromeClient hybridChromeClient, HybridView hybridView) {
        return new WebChromeClient(hybridChromeClient, hybridView);
    }

    @Override // com.miui.internal.hybrid.provider.WebViewFactoryProvider
    public AbsWebView createWebView(Context context, HybridView hybridView) {
        return new WebView(context, hybridView);
    }

    @Override // com.miui.internal.hybrid.provider.WebViewFactoryProvider
    public AbsWebViewClient createWebViewClient(HybridViewClient hybridViewClient, HybridView hybridView) {
        return new WebViewClient(hybridViewClient, hybridView);
    }

    @Override // com.miui.internal.hybrid.provider.WebViewFactoryProvider
    public CookieManager getCookieManager() {
        if (this.mCookieManager == null) {
            this.mCookieManager = new CookieManagerAdapter(android.webkit.CookieManager.getInstance());
        }
        return this.mCookieManager;
    }

    @Override // com.miui.internal.hybrid.provider.WebViewFactoryProvider
    public AbsCookieSyncManager getCookieSyncManager() {
        if (this.mCookieSyncManager == null) {
            this.mCookieSyncManager = new CookieSyncManagerDelegate();
        }
        return this.mCookieSyncManager;
    }
}
