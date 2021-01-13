package com.android.camera.module;

/* compiled from: lambda */
public final /* synthetic */ class sa implements Runnable {
    private final /* synthetic */ VideoModule tg;
    private final /* synthetic */ boolean ug;

    public /* synthetic */ sa(VideoModule videoModule, boolean z) {
        this.tg = videoModule;
        this.ug = z;
    }

    public final void run() {
        this.tg.I(this.ug);
    }
}
