package com.android.camera.ui;

import android.content.DialogInterface;

/* compiled from: lambda */
public final /* synthetic */ class h implements DialogInterface.OnClickListener {
    private final /* synthetic */ ScreenHint tg;

    public /* synthetic */ h(ScreenHint screenHint) {
        this.tg = screenHint;
    }

    public final void onClick(DialogInterface dialogInterface, int i) {
        this.tg.j(dialogInterface, i);
    }
}
