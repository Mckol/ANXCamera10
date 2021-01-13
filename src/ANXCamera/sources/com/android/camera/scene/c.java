package com.android.camera.scene;

import com.android.camera.module.Camera2Module;

/* compiled from: lambda */
public final /* synthetic */ class c implements Runnable {
    private final /* synthetic */ Camera2Module tg;

    public /* synthetic */ c(Camera2Module camera2Module) {
        this.tg = camera2Module;
    }

    public final void run() {
        this.tg.showBacklightTip();
    }
}
