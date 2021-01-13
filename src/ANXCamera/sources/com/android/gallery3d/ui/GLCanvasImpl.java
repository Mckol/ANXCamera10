package com.android.gallery3d.ui;

import android.opengl.GLES20;
import com.android.camera.Util;
import com.android.camera.effect.EffectController;
import com.android.camera.effect.FilterInfo;
import com.android.camera.effect.draw_mode.DrawAttribute;
import com.android.camera.effect.renders.BasicRender;
import com.android.camera.effect.renders.EffectRenderGroup;
import com.android.camera.effect.renders.Render;
import com.android.camera.effect.renders.RenderGroup;
import com.android.camera.log.Log;

public class GLCanvasImpl extends BaseGLCanvas {
    private static final String TAG = "GLCanvasImpl";

    public GLCanvasImpl() {
        this.mRenderCaches = new RenderGroup(this);
        this.mRenderGroup = new RenderGroup(this);
        EffectRenderGroup effectRenderGroup = new EffectRenderGroup(this);
        EffectController.getInstance().addChangeListener(effectRenderGroup);
        this.mRenderGroup.addRender(effectRenderGroup);
        this.mRenderGroup.addRender(new BasicRender(this));
        initialize();
    }

    @Override // com.android.gallery3d.ui.GLCanvas, com.android.gallery3d.ui.BaseGLCanvas
    public void deleteProgram() {
        super.deleteProgram();
        this.mRenderCaches.destroy();
        this.mRenderGroup.destroy();
    }

    @Override // com.android.gallery3d.ui.GLCanvas, com.android.gallery3d.ui.BaseGLCanvas
    public void draw(DrawAttribute drawAttribute) {
        this.mRenderGroup.draw(drawAttribute);
    }

    /* access modifiers changed from: protected */
    @Override // com.android.gallery3d.ui.BaseGLCanvas
    public void initialize() {
        super.initialize();
        GLES20.glEnable(3024);
        GLES20.glLineWidth(1.0f);
    }

    @Override // com.android.gallery3d.ui.GLCanvas, com.android.gallery3d.ui.BaseGLCanvas
    public void prepareBlurRenders() {
        if (this.mRenderGroup.getRender(FilterInfo.FILTER_ID_BLUR) == null) {
            if (this.mRenderCaches.getRender(FilterInfo.FILTER_ID_BLUR) == null) {
                prepareEffectRenders(false, FilterInfo.FILTER_ID_BLUR);
            }
            this.mRenderGroup.addRender(this.mRenderCaches.getRender(FilterInfo.FILTER_ID_BLUR));
        }
    }

    @Override // com.android.gallery3d.ui.GLCanvas, com.android.gallery3d.ui.BaseGLCanvas
    public void prepareEffectRenders(boolean z, int i) {
        if (this.mRenderCaches.isNeedInit(i)) {
            EffectController.getInstance().getEffectGroup(this, this.mRenderCaches, z, false, i);
        }
    }

    @Override // com.android.gallery3d.ui.GLCanvas, com.android.gallery3d.ui.BaseGLCanvas
    public void prepareYuvRenders() {
        if (this.mRenderGroup.getRender(FilterInfo.FILTER_ID_YUV4442RGB) == null) {
            if (this.mRenderCaches.getRender(FilterInfo.FILTER_ID_YUV4442RGB) == null) {
                prepareEffectRenders(false, FilterInfo.FILTER_ID_YUV4442RGB);
            }
            this.mRenderGroup.addRender(this.mRenderCaches.getRender(FilterInfo.FILTER_ID_YUV4442RGB));
        }
    }

    public void setFrameBufferCallback(Render.FrameBufferCallback frameBufferCallback, int i) {
        this.mRenderGroup.setFrameBufferCallback(frameBufferCallback, i);
    }

    public void setKaleidoscope(String str) {
        this.mRenderGroup.setKaleidoscope(str);
    }

    @Override // com.android.gallery3d.ui.GLCanvas, com.android.gallery3d.ui.BaseGLCanvas
    public void setSize(int i, int i2) {
        super.setSize(i, i2);
        Log.c(TAG, "setSize: size=" + i + "x" + i2 + " modelMatrix=" + Util.dumpMatrix(this.mState.getModelMatrix()));
    }

    public void setSticker(String str) {
        this.mRenderGroup.setSticker(str);
    }
}
