package com.android.camera.module;

import android.view.ViewGroup;

/* compiled from: lambda */
public final /* synthetic */ class Q implements Runnable {
    private final /* synthetic */ ViewGroup tg;

    public /* synthetic */ Q(ViewGroup viewGroup) {
        this.tg = viewGroup;
    }

    public final void run() {
        DualVideoModule.b(this.tg);
    }
}
