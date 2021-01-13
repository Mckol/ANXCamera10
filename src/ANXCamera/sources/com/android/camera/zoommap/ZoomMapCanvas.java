package com.android.camera.zoommap;

import com.android.camera.effect.renders.BasicRender;
import com.android.camera.effect.renders.RenderGroup;
import com.android.camera.effect.renders.SurfaceTextureRender;
import com.android.gallery3d.ui.BaseGLCanvas;

public class ZoomMapCanvas extends BaseGLCanvas {
    public ZoomMapCanvas() {
        this.mRenderCaches = new RenderGroup(this);
        this.mRenderGroup = new RenderGroup(this);
        this.mRenderGroup.addRender(new SurfaceTextureRender(this));
        this.mRenderGroup.addRender(new BasicRender(this));
        initialize();
    }

    @Override // com.android.gallery3d.ui.GLCanvas, com.android.gallery3d.ui.BaseGLCanvas
    public void deleteProgram() {
        super.deleteProgram();
        this.mRenderCaches.deleteBuffer();
        this.mRenderGroup.deleteBuffer();
        this.mRenderCaches.destroy();
        this.mRenderGroup.destroy();
    }
}
