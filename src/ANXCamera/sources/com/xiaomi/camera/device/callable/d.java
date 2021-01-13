package com.xiaomi.camera.device.callable;

import com.xiaomi.camera.device.callable.OpenCameraCallable;

/* compiled from: lambda */
public final /* synthetic */ class d implements Runnable {
    private final /* synthetic */ OpenCameraCallable.AnonymousClass1 tg;
    private final /* synthetic */ String ug;
    private final /* synthetic */ int vg;

    public /* synthetic */ d(OpenCameraCallable.AnonymousClass1 r1, String str, int i) {
        this.tg = r1;
        this.ug = str;
        this.vg = i;
    }

    public final void run() {
        this.tg.c(this.ug, this.vg);
    }
}
