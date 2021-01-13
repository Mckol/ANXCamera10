package com.android.camera.zoommap;

import android.graphics.SurfaceTexture;

/* compiled from: lambda */
public final /* synthetic */ class d implements SurfaceTexture.OnFrameAvailableListener {
    private final /* synthetic */ ZoomMapController tg;

    public /* synthetic */ d(ZoomMapController zoomMapController) {
        this.tg = zoomMapController;
    }

    public final void onFrameAvailable(SurfaceTexture surfaceTexture) {
        this.tg.b(surfaceTexture);
    }
}
