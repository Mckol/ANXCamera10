package com.android.camera.module;

import com.xiaomi.fenshen.FenShenCam;

/* compiled from: lambda */
public final /* synthetic */ class C implements Runnable {
    private final /* synthetic */ CloneModule tg;
    private final /* synthetic */ FenShenCam.Message ug;
    private final /* synthetic */ int vg;

    public /* synthetic */ C(CloneModule cloneModule, FenShenCam.Message message, int i) {
        this.tg = cloneModule;
        this.ug = message;
        this.vg = i;
    }

    public final void run() {
        this.tg.a(this.ug, this.vg);
    }
}
