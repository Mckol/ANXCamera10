package com.android.camera.effect.renders;

import android.graphics.Color;
import com.android.camera.data.DataRepository;
import com.android.camera.data.data.runing.ComponentRunningTiltValue;
import com.android.camera.data.data.runing.DataItemRunning;
import com.android.camera.effect.EffectController;
import com.android.camera.effect.FilterInfo;
import com.android.camera.effect.draw_mode.DrawAttribute;
import com.android.camera.effect.draw_mode.DrawExtTexAttribute;
import com.android.camera.effect.draw_mode.FillRectAttribute;
import com.android.camera.log.Log;
import com.android.gallery3d.ui.GLCanvas;

public class EffectRenderGroup extends RenderGroup implements EffectController.EffectChangedListener {
    private static final String TAG = "EffectRenderGroup";
    private int mEffectId = FilterInfo.FILTER_ID_NONE;
    private boolean mFirstFrameDrawn;
    private boolean mIsFocusPeakEnabled;
    private boolean mIsKaleidoscopeEnabled;
    private boolean mIsMakeupEnabled;
    private boolean mIsStickerEnabled;
    private boolean mIsZebraEnabled;
    private PipeRenderPair mPreviewPipeRender;
    private PipeRender mPreviewSecondRender;
    private RenderGroup mRenderCaches;
    private Boolean mRenderChanged = new Boolean(false);
    private final Object mRenderChangedLock = new Object();
    private String mTiltShiftMode;

    public EffectRenderGroup(GLCanvas gLCanvas) {
        super(gLCanvas);
        this.mRenderCaches = gLCanvas.getEffectRenderGroup();
        this.mPreviewPipeRender = new PipeRenderPair(gLCanvas);
        this.mPreviewPipeRender.setFirstRender(new SurfaceTextureRender(gLCanvas));
        this.mPreviewSecondRender = new PipeRender(gLCanvas);
        addRender(this.mPreviewPipeRender);
    }

    private void drawAnimationMask(DrawAttribute drawAttribute) {
        int blurAnimationValue = EffectController.getInstance().getBlurAnimationValue();
        if (blurAnimationValue > 0) {
            DrawExtTexAttribute drawExtTexAttribute = (DrawExtTexAttribute) drawAttribute;
            this.mGLCanvas.draw(new FillRectAttribute(0.0f, 0.0f, (float) drawExtTexAttribute.mWidth, (float) drawExtTexAttribute.mHeight, Color.argb(blurAnimationValue, 0, 0, 0)));
        }
    }

    private boolean drawPreview(DrawAttribute drawAttribute) {
        if (!this.mFirstFrameDrawn) {
            this.mFirstFrameDrawn = true;
            setViewportSize(this.mViewportWidth, this.mViewportHeight);
            setPreviewSize(this.mPreviewWidth, this.mPreviewHeight);
        }
        if (updatePreviewSecondRender(((DrawExtTexAttribute) drawAttribute).mEffectPopup)) {
            if (this.mPreviewSecondRender.getRenderSize() == 0) {
                this.mPreviewPipeRender.setSecondRender(null);
            } else if (this.mPreviewPipeRender.getRenderSize() == 1) {
                this.mPreviewPipeRender.setSecondRender(this.mPreviewSecondRender);
            }
        }
        this.mPreviewPipeRender.setUsedMiddleBuffer(EffectController.getInstance().isBackGroundBlur());
        this.mPreviewPipeRender.draw(drawAttribute);
        drawAnimationMask(drawAttribute);
        return true;
    }

    private Render fetchRender(int i) {
        Render render = this.mRenderCaches.getRender(i);
        if (render != null) {
            return render;
        }
        this.mGLCanvas.prepareEffectRenders(false, i);
        return this.mRenderCaches.getRender(i);
    }

    private Render getTiltShitRender() {
        String str = this.mTiltShiftMode;
        if (str == null) {
            return null;
        }
        if (ComponentRunningTiltValue.TILT_CIRCLE.equals(str)) {
            return fetchRender(FilterInfo.FILTER_ID_GAUSSIAN);
        }
        if (ComponentRunningTiltValue.TILT_PARALLEL.equals(this.mTiltShiftMode)) {
            return fetchRender(FilterInfo.FILTER_ID_TILTSHIFT);
        }
        return null;
    }

    private void removeCache(int i) {
        this.mRenderCaches.removeRender(i);
    }

    private boolean updatePreviewSecondRender(boolean z) {
        Render render;
        Render render2;
        Render fetchRender;
        Render fetchRender2;
        if (!this.mRenderChanged.booleanValue()) {
            return false;
        }
        synchronized (this.mRenderChangedLock) {
            this.mPreviewSecondRender.clearRenders();
            if (EffectController.getInstance().needDestroyMakeup()) {
                removeCache(FilterInfo.RENDER_ID_MAKEUP);
                EffectController.getInstance().setDestroyMakeup(false);
            }
            if (this.mIsStickerEnabled) {
                if (!(this.mEffectId == FilterInfo.FILTER_ID_NONE || (fetchRender2 = fetchRender(this.mEffectId)) == null)) {
                    this.mPreviewSecondRender.addRender(fetchRender2);
                }
                if (!z && (fetchRender = fetchRender(FilterInfo.FILTER_ID_STICKER)) != null) {
                    this.mPreviewSecondRender.addRender(fetchRender);
                }
            } else {
                Render render3 = null;
                Render fetchRender3 = this.mEffectId != FilterInfo.FILTER_ID_NONE ? fetchRender(this.mEffectId) : null;
                if (!z) {
                    Render fetchRender4 = this.mIsMakeupEnabled ? fetchRender(FilterInfo.RENDER_ID_MAKEUP) : null;
                    Render fetchRender5 = this.mIsKaleidoscopeEnabled ? fetchRender(FilterInfo.FILTER_ID_KALEIDOSCOPE) : null;
                    render2 = getTiltShitRender();
                    render = this.mIsFocusPeakEnabled ? fetchRender(FilterInfo.FILTER_ID_PEAKINGMF) : null;
                    if (this.mIsZebraEnabled) {
                        render3 = fetchRender(FilterInfo.FILTER_ID_ZEBRA);
                    }
                    if (fetchRender4 != null && EffectController.getInstance().isBeautyFrameReady()) {
                        this.mPreviewSecondRender.addRender(fetchRender4);
                    }
                    if (fetchRender5 != null) {
                        this.mPreviewSecondRender.addRender(fetchRender5);
                        fetchRender5.setKaleidoscope(EffectController.getInstance().getCurrentKaleidoscope());
                    }
                } else {
                    render2 = null;
                    render = null;
                }
                if (!z && render3 != null) {
                    this.mPreviewSecondRender.addRender(render3);
                }
                if (fetchRender3 != null) {
                    this.mPreviewSecondRender.addRender(fetchRender3);
                }
                if (!z) {
                    if (render2 != null) {
                        this.mPreviewSecondRender.addRender(render2);
                    } else if (render != null) {
                        this.mPreviewSecondRender.addRender(render);
                    }
                }
            }
            this.mPreviewSecondRender.setFrameBufferSize(this.mPreviewWidth, this.mPreviewHeight);
            this.mRenderChanged = false;
        }
        return true;
    }

    @Override // com.android.camera.effect.renders.Render, com.android.camera.effect.renders.RenderGroup
    public boolean draw(DrawAttribute drawAttribute) {
        if (EffectController.getInstance().getEffectForPreview(true) != this.mEffectId && EffectController.getInstance().isBackGroundBlur()) {
            this.mPreviewPipeRender.prepareCopyBlurTexture();
        }
        if (drawAttribute.getTarget() != 8) {
            return false;
        }
        return drawPreview(drawAttribute);
    }

    @Override // com.android.camera.effect.EffectController.EffectChangedListener
    public void onEffectChanged(int... iArr) {
        synchronized (this.mRenderChangedLock) {
            EffectController instance = EffectController.getInstance();
            DataItemRunning dataItemRunning = DataRepository.dataItemRunning();
            for (int i : iArr) {
                switch (i) {
                    case 1:
                        this.mEffectId = instance.getEffectForPreview(true);
                        Log.d(TAG, "onEffectChanged: EFFECT_CHANGE_FILTER mEffectId = " + this.mEffectId);
                        break;
                    case 2:
                        this.mIsStickerEnabled = instance.isStickerEnable();
                        break;
                    case 3:
                        this.mIsMakeupEnabled = instance.isMakeupEnable();
                        break;
                    case 4:
                        this.mIsFocusPeakEnabled = instance.isNeedDrawPeaking();
                        break;
                    case 5:
                        if (instance.isDrawTilt()) {
                            this.mTiltShiftMode = dataItemRunning.getComponentRunningTiltValue().getComponentValue(160);
                            break;
                        } else {
                            this.mTiltShiftMode = null;
                            break;
                        }
                    case 7:
                        this.mIsZebraEnabled = instance.isNeedDrawExposure();
                        break;
                    case 8:
                        this.mIsKaleidoscopeEnabled = instance.isKaleidoscopeEnable();
                        break;
                }
            }
            this.mRenderChanged = true;
        }
    }

    @Override // com.android.camera.effect.EffectController.EffectChangedListener
    public void onRealtimePreviewFilterChanged(int i) {
        synchronized (this.mRenderChangedLock) {
            this.mEffectId = i;
            this.mRenderChanged = true;
        }
    }
}
