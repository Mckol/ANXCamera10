package com.miui.internal.hybrid.webkit;

public class SslErrorHandler extends miui.hybrid.SslErrorHandler {
    private android.webkit.SslErrorHandler mSslErrorHandler;

    public SslErrorHandler(android.webkit.SslErrorHandler sslErrorHandler) {
        this.mSslErrorHandler = sslErrorHandler;
    }

    @Override // miui.hybrid.SslErrorHandler
    public void cancel() {
        this.mSslErrorHandler.cancel();
    }

    @Override // miui.hybrid.SslErrorHandler
    public void proceed() {
        this.mSslErrorHandler.proceed();
    }
}
