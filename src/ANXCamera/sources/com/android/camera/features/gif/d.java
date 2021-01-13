package com.android.camera.features.gif;

/* compiled from: lambda */
public final /* synthetic */ class d implements Runnable {
    private final /* synthetic */ GifMediaPlayer tg;
    private final /* synthetic */ boolean ug;

    public /* synthetic */ d(GifMediaPlayer gifMediaPlayer, boolean z) {
        this.tg = gifMediaPlayer;
        this.ug = z;
    }

    public final void run() {
        this.tg.E(this.ug);
    }
}
