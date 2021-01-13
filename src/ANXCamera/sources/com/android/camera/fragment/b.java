package com.android.camera.fragment;

import android.content.DialogInterface;

/* compiled from: lambda */
public final /* synthetic */ class b implements DialogInterface.OnClickListener {
    private final /* synthetic */ CtaNoticeFragment tg;

    public /* synthetic */ b(CtaNoticeFragment ctaNoticeFragment) {
        this.tg = ctaNoticeFragment;
    }

    public final void onClick(DialogInterface dialogInterface, int i) {
        this.tg.b(dialogInterface, i);
    }
}
