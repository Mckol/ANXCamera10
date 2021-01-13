package com.android.camera;

/* compiled from: lambda */
public final /* synthetic */ class j implements Runnable {
    private final /* synthetic */ Camera tg;

    public /* synthetic */ j(Camera camera) {
        this.tg = camera;
    }

    public final void run() {
        this.tg.showGuide();
    }
}
