package com.android.camera.features.gif;

/* compiled from: lambda */
public final /* synthetic */ class g implements Runnable {
    private final /* synthetic */ GifMediaPlayer tg;
    private final /* synthetic */ String ug;

    public /* synthetic */ g(GifMediaPlayer gifMediaPlayer, String str) {
        this.tg = gifMediaPlayer;
        this.ug = str;
    }

    public final void run() {
        this.tg.R(this.ug);
    }
}
