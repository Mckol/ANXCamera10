package com.android.camera.dualvideo;

import android.media.ImageReader;

/* compiled from: lambda */
public final /* synthetic */ class u implements ImageReader.OnImageAvailableListener {
    private final /* synthetic */ DualVideoControler tg;

    public /* synthetic */ u(DualVideoControler dualVideoControler) {
        this.tg = dualVideoControler;
    }

    public final void onImageAvailable(ImageReader imageReader) {
        this.tg.a(imageReader);
    }
}
