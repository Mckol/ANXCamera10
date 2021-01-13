package com.android.camera.features.mimoji2.fragment;

/* compiled from: lambda */
public final /* synthetic */ class e implements Runnable {
    private final /* synthetic */ FragmentMimojiFullScreen tg;
    private final /* synthetic */ int ug;
    private final /* synthetic */ String vg;

    public /* synthetic */ e(FragmentMimojiFullScreen fragmentMimojiFullScreen, int i, String str) {
        this.tg = fragmentMimojiFullScreen;
        this.ug = i;
        this.vg = str;
    }

    public final void run() {
        this.tg.b(this.ug, this.vg);
    }
}
