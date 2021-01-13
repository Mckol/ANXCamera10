package com.android.camera.module;

import android.view.ViewGroup;

/* compiled from: lambda */
public final /* synthetic */ class Y implements Runnable {
    private final /* synthetic */ DualVideoModule tg;
    private final /* synthetic */ ViewGroup ug;

    public /* synthetic */ Y(DualVideoModule dualVideoModule, ViewGroup viewGroup) {
        this.tg = dualVideoModule;
        this.ug = viewGroup;
    }

    public final void run() {
        this.tg.a(this.ug);
    }
}
