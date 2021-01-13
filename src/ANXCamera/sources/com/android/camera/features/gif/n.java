package com.android.camera.features.gif;

/* compiled from: lambda */
public final /* synthetic */ class n implements Runnable {
    private final /* synthetic */ GifMediaPlayer tg;
    private final /* synthetic */ String ug;

    public /* synthetic */ n(GifMediaPlayer gifMediaPlayer, String str) {
        this.tg = gifMediaPlayer;
        this.ug = str;
    }

    public final void run() {
        this.tg.Q(this.ug);
    }
}
