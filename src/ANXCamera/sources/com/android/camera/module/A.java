package com.android.camera.module;

import android.graphics.Bitmap;

/* compiled from: lambda */
public final /* synthetic */ class A implements Runnable {
    private final /* synthetic */ Camera2Module tg;
    private final /* synthetic */ String ug;
    private final /* synthetic */ Bitmap vg;

    public /* synthetic */ A(Camera2Module camera2Module, String str, Bitmap bitmap) {
        this.tg = camera2Module;
        this.ug = str;
        this.vg = bitmap;
    }

    public final void run() {
        this.tg.b(this.ug, this.vg);
    }
}
