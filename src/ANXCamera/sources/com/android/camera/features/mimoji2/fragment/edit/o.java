package com.android.camera.features.mimoji2.fragment.edit;

/* compiled from: lambda */
public final /* synthetic */ class o implements Runnable {
    private final /* synthetic */ FragmentMimojiEmoticon tg;
    private final /* synthetic */ boolean ug;

    public /* synthetic */ o(FragmentMimojiEmoticon fragmentMimojiEmoticon, boolean z) {
        this.tg = fragmentMimojiEmoticon;
        this.ug = z;
    }

    public final void run() {
        this.tg.u(this.ug);
    }
}
