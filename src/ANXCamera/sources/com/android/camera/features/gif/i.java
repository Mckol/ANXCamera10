package com.android.camera.features.gif;

/* compiled from: lambda */
public final /* synthetic */ class i implements Runnable {
    private final /* synthetic */ GifMediaPlayer tg;
    private final /* synthetic */ boolean ug;

    public /* synthetic */ i(GifMediaPlayer gifMediaPlayer, boolean z) {
        this.tg = gifMediaPlayer;
        this.ug = z;
    }

    public final void run() {
        this.tg.C(this.ug);
    }
}
