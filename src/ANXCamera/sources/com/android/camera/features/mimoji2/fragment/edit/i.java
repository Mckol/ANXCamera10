package com.android.camera.features.mimoji2.fragment.edit;

import com.android.camera.features.mimoji2.fragment.edit.MimojiEmoticonAdapter;

/* compiled from: lambda */
public final /* synthetic */ class i implements MimojiEmoticonAdapter.OnAllSelectStateChangeListener {
    private final /* synthetic */ FragmentMimojiEmoticon tg;

    public /* synthetic */ i(FragmentMimojiEmoticon fragmentMimojiEmoticon) {
        this.tg = fragmentMimojiEmoticon;
    }

    @Override // com.android.camera.features.mimoji2.fragment.edit.MimojiEmoticonAdapter.OnAllSelectStateChangeListener
    public final void onAllSelectStateChange(boolean z) {
        this.tg.v(z);
    }
}
