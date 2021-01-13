package com.miui.internal.hybrid.webkit;

public class JsResult extends miui.hybrid.JsResult {
    private android.webkit.JsResult mJsResult;

    public JsResult(android.webkit.JsResult jsResult) {
        this.mJsResult = jsResult;
    }

    @Override // miui.hybrid.JsResult
    public void cancel() {
        this.mJsResult.cancel();
    }

    @Override // miui.hybrid.JsResult
    public void confirm() {
        this.mJsResult.confirm();
    }
}
