package com.android.camera.fragment.dialog;

import android.content.DialogInterface;

/* compiled from: lambda */
public final /* synthetic */ class i implements DialogInterface.OnClickListener {
    private final /* synthetic */ LongPressLiveFragment tg;

    public /* synthetic */ i(LongPressLiveFragment longPressLiveFragment) {
        this.tg = longPressLiveFragment;
    }

    public final void onClick(DialogInterface dialogInterface, int i) {
        this.tg.d(dialogInterface, i);
    }
}
