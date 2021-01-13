package com.android.camera.effect.renders;

import com.android.camera.CameraSettings;
import com.android.camera.data.DataRepository;
import com.android.camera.effect.EffectController;
import com.android.camera.effect.FilterInfo;
import com.android.camera.effect.draw_mode.DrawAttribute;
import com.android.camera.log.Log;
import com.android.gallery3d.ui.GLCanvas;
import java.util.Locale;

public class VideoRecorderRender extends RenderGroup {
    private static final String TAG = "VideoRecorderRender";
    private boolean mCinematicEnabled;
    private int mEffectId = FilterInfo.FILTER_ID_NONE;
    private boolean mFirstFrameDrawn;
    private boolean mKaleidoscopeEnabled;
    private PipeRenderPair mRenderPipe;
    private PipeRender mSecondRender;
    private boolean mStickerEnabled;

    public VideoRecorderRender(GLCanvas gLCanvas) {
        super(gLCanvas);
        this.mRenderPipe = new PipeRenderPair(gLCanvas);
        this.mRenderPipe.setFirstRender(new SurfaceTextureRender(gLCanvas));
        updateSecondRender();
        addRender(this.mRenderPipe);
        addRender(new BasicRender(gLCanvas));
        addCustomRender();
    }

    private void addCustomRender() {
        if (DataRepository.dataItemFeature().sm() && CameraSettings.isProAmbilightOpen()) {
            addRender(new Yuv444ToRgbRender(this.mGLCanvas));
        }
    }

    private boolean drawPreview(DrawAttribute drawAttribute) {
        if (!this.mFirstFrameDrawn) {
            this.mFirstFrameDrawn = true;
            setViewportSize(this.mViewportWidth, this.mViewportHeight);
            setPreviewSize(this.mPreviewWidth, this.mPreviewHeight);
            PipeRender pipeRender = this.mSecondRender;
            if (pipeRender != null) {
                pipeRender.setFrameBufferSize(this.mPreviewWidth, this.mPreviewHeight);
            }
        }
        synchronized (this) {
            super.draw(drawAttribute);
        }
        return true;
    }

    private PipeRender getSecondPipeRender() {
        PipeRender pipeRender = this.mSecondRender;
        if (pipeRender != null) {
            return pipeRender;
        }
        this.mSecondRender = new PipeRender(this.mGLCanvas);
        return this.mSecondRender;
    }

    private Render getSecondRender(int i, boolean z, boolean z2, boolean z3) {
        Render render;
        Render render2;
        Render render3;
        Render render4;
        if (i != FilterInfo.FILTER_ID_NONE) {
            Render render5 = this.mGLCanvas.getEffectRenderGroup().getRender(i);
            if (render5 == null) {
                this.mGLCanvas.prepareEffectRenders(false, i);
                render = this.mGLCanvas.getEffectRenderGroup().getRender(i);
            } else {
                render = render5;
            }
        } else {
            render = null;
        }
        if (z) {
            render2 = this.mGLCanvas.getEffectRenderGroup().getRender(FilterInfo.FILTER_ID_STICKER);
            if (render2 == null) {
                this.mGLCanvas.prepareEffectRenders(false, FilterInfo.FILTER_ID_STICKER);
                render2 = this.mGLCanvas.getEffectRenderGroup().getRender(FilterInfo.FILTER_ID_STICKER);
            }
        } else {
            render2 = null;
        }
        if (z2) {
            render3 = this.mGLCanvas.getEffectRenderGroup().getRender(FilterInfo.FILTER_ID_KALEIDOSCOPE);
            if (render3 == null) {
                this.mGLCanvas.prepareEffectRenders(false, FilterInfo.FILTER_ID_KALEIDOSCOPE);
                render3 = this.mGLCanvas.getEffectRenderGroup().getRender(FilterInfo.FILTER_ID_KALEIDOSCOPE);
            }
            render3.setKaleidoscope(EffectController.getInstance().getCurrentKaleidoscope());
        } else {
            render3 = null;
        }
        if (z3) {
            render4 = this.mGLCanvas.getEffectRenderGroup().getRender(FilterInfo.FILTER_ID_CINEMATIC);
            if (render4 == null) {
                this.mGLCanvas.prepareEffectRenders(false, FilterInfo.FILTER_ID_CINEMATIC);
                render4 = this.mGLCanvas.getEffectRenderGroup().getRender(FilterInfo.FILTER_ID_CINEMATIC);
            }
        } else {
            render4 = null;
        }
        PipeRender pipeRender = this.mSecondRender;
        if (pipeRender != null) {
            pipeRender.clearRenders();
        }
        if (render != null) {
            getSecondPipeRender().addRender(render);
        }
        if (render2 != null) {
            getSecondPipeRender().addRender(render2);
        }
        if (render3 != null) {
            getSecondPipeRender().addRender(render3);
        }
        if (render4 != null) {
            getSecondPipeRender().addRender(render4);
        }
        if (getSecondPipeRender().getRenderSize() > 0) {
            return this.mSecondRender;
        }
        return null;
    }

    private void updateSecondRender() {
        int i = this.mEffectId;
        this.mEffectId = EffectController.getInstance().getEffectForPreview(false);
        boolean z = this.mStickerEnabled;
        this.mStickerEnabled = EffectController.getInstance().isStickerEnable();
        boolean z2 = this.mKaleidoscopeEnabled;
        this.mKaleidoscopeEnabled = EffectController.getInstance().isKaleidoscopeEnable();
        Log.d(TAG, String.format(Locale.ENGLISH, "effectId: 0x%x->0x%x stickerEnabled: %b->%b KaleidoscopeEnabled: %b->%b", Integer.valueOf(i), Integer.valueOf(this.mEffectId), Boolean.valueOf(z), Boolean.valueOf(this.mStickerEnabled), Boolean.valueOf(z2), Boolean.valueOf(this.mKaleidoscopeEnabled)));
        boolean z3 = this.mCinematicEnabled;
        this.mCinematicEnabled = EffectController.getInstance().isCinematicEnable();
        if (this.mEffectId != i || this.mStickerEnabled != z || this.mKaleidoscopeEnabled != z2 || z3 != this.mCinematicEnabled) {
            this.mFirstFrameDrawn = false;
            this.mRenderPipe.setSecondRender(getSecondRender(this.mEffectId, this.mStickerEnabled, this.mKaleidoscopeEnabled, this.mCinematicEnabled));
        }
    }

    @Override // com.android.camera.effect.renders.Render, com.android.camera.effect.renders.RenderGroup
    public void deleteBuffer() {
        PipeRender pipeRender = this.mSecondRender;
        if (pipeRender != null) {
            pipeRender.deleteBuffer();
        }
        super.deleteBuffer();
    }

    @Override // com.android.camera.effect.renders.Render, com.android.camera.effect.renders.RenderGroup
    public void destroy() {
        PipeRender pipeRender = this.mSecondRender;
        if (pipeRender != null) {
            pipeRender.destroy();
        }
        super.destroy();
    }

    @Override // com.android.camera.effect.renders.Render, com.android.camera.effect.renders.RenderGroup
    public boolean draw(DrawAttribute drawAttribute) {
        int target = drawAttribute.getTarget();
        if (target == 4 || target == 5 || target == 8 || target == 11) {
            return drawPreview(drawAttribute);
        }
        String str = TAG;
        Log.e(str, "unsupported target " + drawAttribute.getTarget());
        return false;
    }

    public void setFilterId(int i) {
        if (i != this.mEffectId) {
            synchronized (this) {
                updateSecondRender();
            }
        }
    }

    public void updateCinematic() {
        updateSecondRender();
    }
}
