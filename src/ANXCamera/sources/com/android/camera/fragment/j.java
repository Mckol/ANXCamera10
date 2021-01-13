package com.android.camera.fragment;

import android.content.DialogInterface;

/* compiled from: lambda */
public final /* synthetic */ class j implements DialogInterface.OnClickListener {
    private final /* synthetic */ GoogleLensFragment tg;

    public /* synthetic */ j(GoogleLensFragment googleLensFragment) {
        this.tg = googleLensFragment;
    }

    public final void onClick(DialogInterface dialogInterface, int i) {
        this.tg.c(dialogInterface, i);
    }
}
