package com.android.camera.module;

import com.xiaomi.fenshen.FenShenCam;

/* compiled from: lambda */
public final /* synthetic */ class E implements Runnable {
    public static final /* synthetic */ E INSTANCE = new E();

    private /* synthetic */ E() {
    }

    public final void run() {
        FenShenCam.release();
    }
}
