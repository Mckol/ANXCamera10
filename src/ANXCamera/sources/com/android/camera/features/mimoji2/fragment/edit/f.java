package com.android.camera.features.mimoji2.fragment.edit;

import android.content.DialogInterface;

/* compiled from: lambda */
public final /* synthetic */ class f implements DialogInterface.OnClickListener {
    private final /* synthetic */ FragmentMimojiEmoticon tg;

    public /* synthetic */ f(FragmentMimojiEmoticon fragmentMimojiEmoticon) {
        this.tg = fragmentMimojiEmoticon;
    }

    public final void onClick(DialogInterface dialogInterface, int i) {
        this.tg.e(dialogInterface, i);
    }
}
