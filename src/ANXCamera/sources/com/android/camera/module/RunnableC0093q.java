package com.android.camera.module;

/* renamed from: com.android.camera.module.q  reason: case insensitive filesystem */
/* compiled from: lambda */
public final /* synthetic */ class RunnableC0093q implements Runnable {
    private final /* synthetic */ Camera2Module tg;

    public /* synthetic */ RunnableC0093q(Camera2Module camera2Module) {
        this.tg = camera2Module;
    }

    public final void run() {
        this.tg.startLensActivity();
    }
}
