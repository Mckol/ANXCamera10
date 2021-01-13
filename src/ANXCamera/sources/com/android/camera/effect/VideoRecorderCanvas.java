package com.android.camera.effect;

import com.android.camera.Util;
import com.android.camera.effect.renders.RenderGroup;
import com.android.camera.effect.renders.VideoRecorderRender;
import com.android.camera.log.Log;
import com.android.gallery3d.ui.BaseGLCanvas;

public class VideoRecorderCanvas extends BaseGLCanvas {
    public VideoRecorderCanvas() {
        this.mRenderCaches = new RenderGroup(this);
        this.mRenderGroup = new VideoRecorderRender(this);
        initialize();
    }

    public void applyFilterId(int i) {
        ((VideoRecorderRender) this.mRenderGroup).setFilterId(i);
    }

    @Override // com.android.gallery3d.ui.GLCanvas, com.android.gallery3d.ui.BaseGLCanvas
    public void deleteProgram() {
        super.deleteProgram();
        this.mRenderCaches.deleteBuffer();
        this.mRenderGroup.deleteBuffer();
        this.mRenderCaches.destroy();
        this.mRenderGroup.destroy();
    }

    @Override // com.android.gallery3d.ui.GLCanvas, com.android.gallery3d.ui.BaseGLCanvas
    public void setSize(int i, int i2) {
        super.setSize(i, i2);
        Log.d("VideoRecorderCanvas", "setSize: size=" + i + "x" + i2 + " modelMatrix=" + Util.dumpMatrix(this.mState.getModelMatrix()));
    }

    public void updateCinematic() {
        RenderGroup renderGroup = this.mRenderGroup;
        if (renderGroup != null) {
            ((VideoRecorderRender) renderGroup).updateCinematic();
        }
    }
}
