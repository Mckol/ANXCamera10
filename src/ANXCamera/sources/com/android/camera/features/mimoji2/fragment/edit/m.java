package com.android.camera.features.mimoji2.fragment.edit;

import com.arcsoft.avatar2.emoticon.EmoInfo;

/* compiled from: lambda */
public final /* synthetic */ class m implements Runnable {
    private final /* synthetic */ FragmentMimojiEmoticon tg;
    private final /* synthetic */ EmoInfo ug;
    private final /* synthetic */ int vg;

    public /* synthetic */ m(FragmentMimojiEmoticon fragmentMimojiEmoticon, EmoInfo emoInfo, int i) {
        this.tg = fragmentMimojiEmoticon;
        this.ug = emoInfo;
        this.vg = i;
    }

    public final void run() {
        this.tg.a(this.ug, this.vg);
    }
}
