package com.miui.internal.hybrid.webkit;

public class ValueCallback<T> implements miui.hybrid.ValueCallback<T> {
    private android.webkit.ValueCallback<T> mValueCallback;

    public ValueCallback(android.webkit.ValueCallback<T> valueCallback) {
        this.mValueCallback = valueCallback;
    }

    @Override // miui.hybrid.ValueCallback
    public void onReceiveValue(T t) {
        this.mValueCallback.onReceiveValue(t);
    }
}
