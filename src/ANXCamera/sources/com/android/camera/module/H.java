package com.android.camera.module;

import com.xiaomi.fenshen.FenShenCam;

/* compiled from: lambda */
public final /* synthetic */ class H implements Runnable {
    public static final /* synthetic */ H INSTANCE = new H();

    private /* synthetic */ H() {
    }

    public final void run() {
        FenShenCam.start();
    }
}
