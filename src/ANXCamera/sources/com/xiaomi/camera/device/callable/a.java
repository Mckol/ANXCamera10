package com.xiaomi.camera.device.callable;

/* compiled from: lambda */
public final /* synthetic */ class a implements Runnable {
    private final /* synthetic */ CameraCallable tg;
    private final /* synthetic */ CallableReturn ug;

    public /* synthetic */ a(CameraCallable cameraCallable, CallableReturn callableReturn) {
        this.tg = cameraCallable;
        this.ug = callableReturn;
    }

    public final void run() {
        this.tg.a(this.ug);
    }
}
