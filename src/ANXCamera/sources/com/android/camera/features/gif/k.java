package com.android.camera.features.gif;

/* compiled from: lambda */
public final /* synthetic */ class k implements Runnable {
    private final /* synthetic */ GifMediaPlayer tg;
    private final /* synthetic */ boolean ug;

    public /* synthetic */ k(GifMediaPlayer gifMediaPlayer, boolean z) {
        this.tg = gifMediaPlayer;
        this.ug = z;
    }

    public final void run() {
        this.tg.B(this.ug);
    }
}
