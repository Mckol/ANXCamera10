package com.android.camera.module;

/* compiled from: lambda */
public final /* synthetic */ class va implements Runnable {
    private final /* synthetic */ Camera2Module tg;

    public /* synthetic */ va(Camera2Module camera2Module) {
        this.tg = camera2Module;
    }

    public final void run() {
        this.tg.handlePendingScreenSlide();
    }
}
