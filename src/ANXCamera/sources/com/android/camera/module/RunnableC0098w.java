package com.android.camera.module;

import com.android.camera2.CameraHardwareFace;

/* renamed from: com.android.camera.module.w  reason: case insensitive filesystem */
/* compiled from: lambda */
public final /* synthetic */ class RunnableC0098w implements Runnable {
    private final /* synthetic */ Camera2Module tg;
    private final /* synthetic */ CameraHardwareFace[] ug;

    public /* synthetic */ RunnableC0098w(Camera2Module camera2Module, CameraHardwareFace[] cameraHardwareFaceArr) {
        this.tg = camera2Module;
        this.ug = cameraHardwareFaceArr;
    }

    public final void run() {
        this.tg.a(this.ug);
    }
}
