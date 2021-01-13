package com.android.camera.module.impl.component;

import com.android.camera.module.Camera2Module;

/* compiled from: lambda */
public final /* synthetic */ class x implements Runnable {
    private final /* synthetic */ Camera2Module tg;
    private final /* synthetic */ boolean ug;
    private final /* synthetic */ int vg;

    public /* synthetic */ x(Camera2Module camera2Module, boolean z, int i) {
        this.tg = camera2Module;
        this.ug = z;
        this.vg = i;
    }

    public final void run() {
        MiAsdDetectImpl.a(this.tg, this.ug, this.vg);
    }
}
