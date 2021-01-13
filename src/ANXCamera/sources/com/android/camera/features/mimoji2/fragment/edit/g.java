package com.android.camera.features.mimoji2.fragment.edit;

import android.content.DialogInterface;
import android.view.KeyEvent;

/* compiled from: lambda */
public final /* synthetic */ class g implements DialogInterface.OnKeyListener {
    private final /* synthetic */ FragmentMimojiEmoticon tg;

    public /* synthetic */ g(FragmentMimojiEmoticon fragmentMimojiEmoticon) {
        this.tg = fragmentMimojiEmoticon;
    }

    public final boolean onKey(DialogInterface dialogInterface, int i, KeyEvent keyEvent) {
        return this.tg.b(dialogInterface, i, keyEvent);
    }
}
