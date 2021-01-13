package com.miui.internal.hybrid.webkit;

import miui.hybrid.CookieManager;

public class CookieManagerAdapter extends CookieManager {
    private android.webkit.CookieManager mCookieManager;

    public CookieManagerAdapter(android.webkit.CookieManager cookieManager) {
        this.mCookieManager = cookieManager;
    }

    @Override // miui.hybrid.CookieManager
    public boolean acceptCookie() {
        return android.webkit.CookieManager.getInstance().acceptCookie();
    }

    /* access modifiers changed from: protected */
    @Override // miui.hybrid.CookieManager
    public boolean allowFileSchemeCookiesImpl() {
        return android.webkit.CookieManager.allowFileSchemeCookies();
    }

    @Override // miui.hybrid.CookieManager
    public String getCookie(String str) {
        return this.mCookieManager.getCookie(str);
    }

    @Override // miui.hybrid.CookieManager
    public boolean hasCookies() {
        return this.mCookieManager.hasCookies();
    }

    @Override // miui.hybrid.CookieManager
    public void removeAllCookie() {
        this.mCookieManager.removeAllCookie();
    }

    @Override // miui.hybrid.CookieManager
    public void removeExpiredCookie() {
        this.mCookieManager.removeExpiredCookie();
    }

    @Override // miui.hybrid.CookieManager
    public void removeSessionCookie() {
        this.mCookieManager.removeSessionCookie();
    }

    @Override // miui.hybrid.CookieManager
    public void setAcceptCookie(boolean z) {
        this.mCookieManager.setAcceptCookie(z);
    }

    /* access modifiers changed from: protected */
    @Override // miui.hybrid.CookieManager
    public void setAcceptFileSchemeCookiesImpl(boolean z) {
        android.webkit.CookieManager.setAcceptFileSchemeCookies(z);
    }

    @Override // miui.hybrid.CookieManager
    public void setCookie(String str, String str2) {
        this.mCookieManager.setCookie(str, str2);
    }
}
