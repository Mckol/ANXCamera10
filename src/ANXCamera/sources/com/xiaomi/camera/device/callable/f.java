package com.xiaomi.camera.device.callable;

import com.xiaomi.camera.device.callable.OpenCameraCallable;

/* compiled from: lambda */
public final /* synthetic */ class f implements Runnable {
    private final /* synthetic */ OpenCameraCallable.AnonymousClass1 tg;
    private final /* synthetic */ String ug;

    public /* synthetic */ f(OpenCameraCallable.AnonymousClass1 r1, String str) {
        this.tg = r1;
        this.ug = str;
    }

    public final void run() {
        this.tg.r(this.ug);
    }
}
