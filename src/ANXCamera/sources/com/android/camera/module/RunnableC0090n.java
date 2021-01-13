package com.android.camera.module;

import android.graphics.Bitmap;
import com.android.camera.protocol.ModeProtocol;

/* renamed from: com.android.camera.module.n  reason: case insensitive filesystem */
/* compiled from: lambda */
public final /* synthetic */ class RunnableC0090n implements Runnable {
    private final /* synthetic */ ModeProtocol.ActionProcessing tg;
    private final /* synthetic */ Bitmap ug;
    private final /* synthetic */ float[] vg;

    public /* synthetic */ RunnableC0090n(ModeProtocol.ActionProcessing actionProcessing, Bitmap bitmap, float[] fArr) {
        this.tg = actionProcessing;
        this.ug = bitmap;
        this.vg = fArr;
    }

    public final void run() {
        Camera2Module.a(this.tg, this.ug, this.vg);
    }
}
