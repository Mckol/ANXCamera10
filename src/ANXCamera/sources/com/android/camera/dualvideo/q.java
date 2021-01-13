package com.android.camera.dualvideo;

import android.graphics.SurfaceTexture;

/* compiled from: lambda */
public final /* synthetic */ class q implements SurfaceTexture.OnFrameAvailableListener {
    private final /* synthetic */ DualVideoControler tg;

    public /* synthetic */ q(DualVideoControler dualVideoControler) {
        this.tg = dualVideoControler;
    }

    public final void onFrameAvailable(SurfaceTexture surfaceTexture) {
        this.tg.a(surfaceTexture);
    }
}
