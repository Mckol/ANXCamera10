package com.android.camera.features.mimoji2.fragment.edit;

import android.content.DialogInterface;

/* compiled from: lambda */
public final /* synthetic */ class n implements DialogInterface.OnClickListener {
    private final /* synthetic */ FragmentMimojiEmoticon tg;

    public /* synthetic */ n(FragmentMimojiEmoticon fragmentMimojiEmoticon) {
        this.tg = fragmentMimojiEmoticon;
    }

    public final void onClick(DialogInterface dialogInterface, int i) {
        this.tg.h(dialogInterface, i);
    }
}
