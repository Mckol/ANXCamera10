package com.android.camera.zoommap;

import com.android.camera.ui.GLTextureView;
import javax.microedition.khronos.egl.EGLContext;

/* compiled from: lambda */
public final /* synthetic */ class b implements GLTextureView.EGLShareContextGetter {
    private final /* synthetic */ ZoomMapController tg;

    public /* synthetic */ b(ZoomMapController zoomMapController) {
        this.tg = zoomMapController;
    }

    @Override // com.android.camera.ui.GLTextureView.EGLShareContextGetter
    public final EGLContext getShareContext() {
        return this.tg.Xd();
    }
}
