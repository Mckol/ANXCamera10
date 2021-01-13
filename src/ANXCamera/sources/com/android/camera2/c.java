package com.android.camera2;

import com.android.camera2.Camera2Proxy;

/* compiled from: lambda */
public final /* synthetic */ class c implements Runnable {
    private final /* synthetic */ Camera2Proxy.ScreenLightCallback tg;

    public /* synthetic */ c(Camera2Proxy.ScreenLightCallback screenLightCallback) {
        this.tg = screenLightCallback;
    }

    public final void run() {
        this.tg.stopScreenLight();
    }
}
