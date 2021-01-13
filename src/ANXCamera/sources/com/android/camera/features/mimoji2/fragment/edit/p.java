package com.android.camera.features.mimoji2.fragment.edit;

import android.content.DialogInterface;

/* compiled from: lambda */
public final /* synthetic */ class p implements DialogInterface.OnClickListener {
    private final /* synthetic */ FragmentMimojiEmoticon tg;

    public /* synthetic */ p(FragmentMimojiEmoticon fragmentMimojiEmoticon) {
        this.tg = fragmentMimojiEmoticon;
    }

    public final void onClick(DialogInterface dialogInterface, int i) {
        this.tg.g(dialogInterface, i);
    }
}
