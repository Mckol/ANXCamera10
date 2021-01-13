package com.android.camera.module;

/* compiled from: lambda */
public final /* synthetic */ class xa implements Runnable {
    private final /* synthetic */ BaseModule tg;

    public /* synthetic */ xa(BaseModule baseModule) {
        this.tg = baseModule;
    }

    public final void run() {
        this.tg.onThermalConstrained();
    }
}
