package com.android.camera.features.gif;

/* compiled from: lambda */
public final /* synthetic */ class h implements Runnable {
    private final /* synthetic */ GifMediaPlayer tg;
    private final /* synthetic */ boolean ug;

    public /* synthetic */ h(GifMediaPlayer gifMediaPlayer, boolean z) {
        this.tg = gifMediaPlayer;
        this.ug = z;
    }

    public final void run() {
        this.tg.D(this.ug);
    }
}
