package com.android.camera.fragment.dialog;

import android.content.DialogInterface;
import android.view.KeyEvent;

/* compiled from: lambda */
public final /* synthetic */ class l implements DialogInterface.OnKeyListener {
    private final /* synthetic */ ThermalDialogFragment tg;

    public /* synthetic */ l(ThermalDialogFragment thermalDialogFragment) {
        this.tg = thermalDialogFragment;
    }

    public final boolean onKey(DialogInterface dialogInterface, int i, KeyEvent keyEvent) {
        return this.tg.a(dialogInterface, i, keyEvent);
    }
}
