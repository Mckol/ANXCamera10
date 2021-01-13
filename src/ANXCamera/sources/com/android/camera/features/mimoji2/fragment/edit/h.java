package com.android.camera.features.mimoji2.fragment.edit;

/* compiled from: lambda */
public final /* synthetic */ class h implements Runnable {
    private final /* synthetic */ FragmentMimojiEmoticon tg;
    private final /* synthetic */ int ug;

    public /* synthetic */ h(FragmentMimojiEmoticon fragmentMimojiEmoticon, int i) {
        this.tg = fragmentMimojiEmoticon;
        this.ug = i;
    }

    public final void run() {
        this.tg.m(this.ug);
    }
}
