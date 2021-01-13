package com.xiaomi.camera.device;

import com.xiaomi.camera.device.callable.CameraCallable;
import java.util.concurrent.Callable;

/* compiled from: lambda */
public final /* synthetic */ class a implements Callable {
    private final /* synthetic */ CameraCallable tg;

    public /* synthetic */ a(CameraCallable cameraCallable) {
        this.tg = cameraCallable;
    }

    @Override // java.util.concurrent.Callable
    public final Object call() {
        return this.tg.call();
    }
}
