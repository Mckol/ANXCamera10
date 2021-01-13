package com.android.camera.dualvideo.render;

import android.graphics.Rect;
import android.opengl.Matrix;
import com.android.camera.effect.draw_mode.DrawExtTexAttribute;
import com.android.camera.log.Log;
import com.android.gallery3d.ui.ExtTexture;
import miuix.animation.Folme;
import miuix.animation.base.AnimConfig;
import miuix.animation.listener.TransitionListener;
import miuix.animation.property.IIntValueProperty;

public class RenderItem implements IRenderable {
    private static final int ANIM_DURATION_100 = 100;
    private static final int ANIM_DURATION_200 = 200;
    private static final int ANIM_DURATION_400 = 400;
    private static final String TAG = "RenderItem";
    private float m6PatchTagAlpha;
    private EaseOutAnim m6PatchTagAnim;
    private float m6PatchTagDstAlpha;
    private int m6patchComposeType;
    private float mAlpha;
    private float[] mBasicPreviewTransMatrix;
    private boolean mFacing;
    private boolean mIsAnimating;
    private boolean mIsPressedIn6Patch = false;
    private boolean mIsVisible = true;
    private int mLastPreviewComposeType;
    private DrawExtTexAttribute mPreviewAttri;
    private int mRenderComposeType;
    private float mSelectedFrameAlpha;
    private EaseOutAnim mSelectedFrameAnim;
    private float mSelectedFrameDstAlpha;
    private int mSelectedType;
    private ShrinkAnimListener mShrinkListener;

    class ComposeChangeAnimListener extends TransitionListener {
        private Rect dstRenderArea;
        private float[] dstRenderTrans;
        private Rect srcRenderArea;
        private float[] srcRenderTrans;

        public ComposeChangeAnimListener(RegionHelper regionHelper) {
            this.srcRenderArea = new Rect(RenderItem.this.getRenderAttri().mX, RenderItem.this.getRenderAttri().mY, RenderItem.this.getRenderAttri().mWidth + RenderItem.this.getRenderAttri().mX, RenderItem.this.getRenderAttri().mHeight + RenderItem.this.getRenderAttri().mY);
            this.srcRenderTrans = (float[]) RenderItem.this.getRenderAttri().mTextureTransform.clone();
            DrawExtTexAttribute genPreviewAttri = RenderItem.this.genPreviewAttri(regionHelper);
            int i = genPreviewAttri.mX;
            int i2 = genPreviewAttri.mY;
            this.dstRenderArea = new Rect(i, i2, genPreviewAttri.mWidth + i, genPreviewAttri.mHeight + i2);
            this.dstRenderTrans = (float[]) genPreviewAttri.mTextureTransform.clone();
        }

        private float getTransRatio(int i) {
            int height = RenderItem.this.getRenderAttri().mExtTexture.getHeight();
            int width = RenderItem.this.getRenderAttri().mExtTexture.getWidth();
            float f = ((float) i) / 1000.0f;
            float f2 = 1.0f - f;
            float f3 = 1.0f - f2;
            float height2 = ((((float) this.srcRenderArea.height()) * f2) + (((float) this.dstRenderArea.height()) * f3)) / ((((float) this.srcRenderArea.width()) * f2) + (((float) this.dstRenderArea.width()) * f3));
            float cropRatio = DualVideoUtil.getCropRatio(RenderItem.this.getLastRenderComposeType());
            float cropRatio2 = DualVideoUtil.getCropRatio(RenderItem.this.getRenderComposeType());
            float f4 = cropRatio - cropRatio2;
            return Math.abs(f4) > 0.1f ? 1.0f - ((((height2 * ((float) height)) / ((float) width)) - cropRatio2) / f4) : f;
        }

        public void animatePreview(int i) {
            Rect mixArea = DualVideoUtil.mixArea(this.srcRenderArea, this.dstRenderArea, ((float) i) / 1000.0f);
            float[] mixMatrix = DualVideoUtil.mixMatrix(this.srcRenderTrans, this.dstRenderTrans, getTransRatio(i));
            RenderItem renderItem = RenderItem.this;
            renderItem.setRenderAttri(new DrawExtTexAttribute(renderItem.mPreviewAttri.mExtTexture, mixMatrix, mixArea.left, mixArea.top, mixArea.width(), mixArea.height()));
        }

        @Override // miuix.animation.listener.TransitionListener
        public void onUpdate(Object obj, IIntValueProperty iIntValueProperty, int i, float f, boolean z) {
            animatePreview(i);
            if (z) {
                RenderItem.this.mIsAnimating = false;
            }
        }
    }

    /* access modifiers changed from: package-private */
    public class ShrinkAnimListener extends TransitionListener {
        private Rect srcRenderArea;

        ShrinkAnimListener() {
            this.srcRenderArea = new Rect(RenderItem.this.getRenderAttri().mX, RenderItem.this.getRenderAttri().mY, RenderItem.this.getRenderAttri().mWidth + RenderItem.this.getRenderAttri().mX, RenderItem.this.getRenderAttri().mHeight + RenderItem.this.getRenderAttri().mY);
        }

        public void animatePreview(int i) {
            Rect shrinkRect = DualVideoUtil.shrinkRect(this.srcRenderArea, 1.0f - ((((float) i) / 1000.0f) * 0.05f));
            RenderItem renderItem = RenderItem.this;
            renderItem.setRenderAttri(new DrawExtTexAttribute(renderItem.mPreviewAttri.mExtTexture, RenderItem.this.mPreviewAttri.mTextureTransform, shrinkRect.left, shrinkRect.top, shrinkRect.width(), shrinkRect.height()));
        }

        @Override // miuix.animation.listener.TransitionListener
        public void onUpdate(Object obj, IIntValueProperty iIntValueProperty, int i, float f, boolean z) {
            animatePreview(i);
            if (z && i == 0) {
                Log.d(RenderItem.TAG, "onUpdate: isCompleted");
                RenderItem.this.mIsAnimating = false;
                RenderItem.this.mIsPressedIn6Patch = false;
            }
        }
    }

    public RenderItem(int i, boolean z) {
        this.m6patchComposeType = i;
        this.mRenderComposeType = i;
        this.mLastPreviewComposeType = i;
        this.mFacing = z;
        this.mAlpha = 1.0f;
    }

    private void animShrink() {
        Log.d(TAG, "animShrink: " + hashCode());
        if (this.mIsAnimating) {
            Folme.useValue("animShrink" + hashCode()).to(1000, new AnimConfig().setEase(6, 200.0f).addListeners(this.mShrinkListener));
            return;
        }
        this.mIsAnimating = true;
        this.mShrinkListener = new ShrinkAnimListener();
        Folme.useValue("animShrink" + hashCode()).setTo((Object) 0).to(1000, new AnimConfig().setEase(6, 200.0f).addListeners(this.mShrinkListener));
    }

    /* access modifiers changed from: private */
    /* access modifiers changed from: public */
    private DrawExtTexAttribute genPreviewAttri(RegionHelper regionHelper) {
        Rect renderAreaFor = regionHelper.getRenderAreaFor(getRenderComposeType());
        float[] fArr = (float[]) this.mBasicPreviewTransMatrix.clone();
        if (!isFacingFront()) {
            Matrix.translateM(fArr, 0, 0.0f, 1.0f, 0.0f);
            Matrix.scaleM(fArr, 0, 1.0f, -1.0f, 1.0f);
        }
        ExtTexture extTexture = this.mPreviewAttri.mExtTexture;
        DualVideoUtil.centerScaleMatrix(fArr, getRenderComposeType());
        return new DrawExtTexAttribute(extTexture, fArr, renderAreaFor.left, renderAreaFor.top, renderAreaFor.width(), renderAreaFor.height());
    }

    private int getAnimationDuration() {
        return (getRenderComposeType() == 10 || getRenderComposeType() >= 20 || getLastRenderComposeType() >= 20) ? 400 : 200;
    }

    private void restoreAnimShrink() {
        Log.d(TAG, "restoreAnimShrink: " + hashCode());
        Folme.useValue("animShrink" + hashCode()).to(0, new AnimConfig().setEase(6, 200.0f).addListeners(this.mShrinkListener));
    }

    @Override // com.android.camera.dualvideo.render.IRenderable
    public void alphaIn6PatchTag(boolean z) {
        this.m6PatchTagAnim = new EaseOutAnim(z ? 200 : 100);
        if (z) {
            this.m6PatchTagDstAlpha = 1.0f;
        } else {
            this.m6PatchTagDstAlpha = 0.0f;
        }
    }

    @Override // com.android.camera.dualvideo.render.IRenderable
    public void alphaInSelectedFrame(boolean z) {
        if (getSelectedType() != 0 || !z) {
            this.mSelectedFrameAnim = new EaseOutAnim(z ? 200 : 100);
            if (z) {
                this.mSelectedFrameDstAlpha = 1.0f;
            } else {
                this.mSelectedFrameDstAlpha = 0.0f;
            }
        }
    }

    @Override // com.android.camera.dualvideo.render.IRenderable
    public float get6PatchTagAlpha() {
        EaseOutAnim easeOutAnim = this.m6PatchTagAnim;
        if (easeOutAnim == null) {
            return this.m6PatchTagAlpha;
        }
        if (easeOutAnim.isFinished()) {
            this.m6PatchTagAlpha = this.m6PatchTagDstAlpha;
            this.m6PatchTagAnim = null;
            return this.m6PatchTagAlpha;
        }
        float ratio = this.m6PatchTagAnim.getRatio();
        return (this.m6PatchTagAlpha * (1.0f - ratio)) + (this.m6PatchTagDstAlpha * ratio);
    }

    @Override // com.android.camera.dualvideo.render.IRenderable
    public int get6patchComposeType() {
        return this.m6patchComposeType;
    }

    @Override // com.android.camera.dualvideo.render.IRenderable
    public float getAlpha() {
        return this.mAlpha;
    }

    @Override // com.android.camera.dualvideo.render.IRenderable
    public int getLastRenderComposeType() {
        return this.mLastPreviewComposeType;
    }

    @Override // com.android.camera.dualvideo.render.IRenderable
    public DrawExtTexAttribute getRenderAttri() {
        return this.mPreviewAttri;
    }

    @Override // com.android.camera.dualvideo.render.IRenderable
    public int getRenderComposeType() {
        return this.mRenderComposeType;
    }

    @Override // com.android.camera.dualvideo.render.IRenderable
    public float getSelectFrameAlpha() {
        EaseOutAnim easeOutAnim = this.mSelectedFrameAnim;
        if (easeOutAnim == null) {
            return this.mSelectedFrameAlpha;
        }
        if (easeOutAnim.isFinished()) {
            this.mSelectedFrameAlpha = this.mSelectedFrameDstAlpha;
            this.mSelectedFrameAnim = null;
            return this.mSelectedFrameAlpha;
        }
        float ratio = this.mSelectedFrameAnim.getRatio();
        return (this.mSelectedFrameAlpha * (1.0f - ratio)) + (this.mSelectedFrameDstAlpha * ratio);
    }

    @Override // com.android.camera.dualvideo.render.IRenderable
    public int getSelectedType() {
        return this.mSelectedType;
    }

    @Override // com.android.camera.dualvideo.render.IRenderable
    public boolean isAnimating() {
        return this.mIsAnimating || isPressedIn6Patch();
    }

    @Override // com.android.camera.dualvideo.render.IRenderable
    public boolean isFacingFront() {
        return this.mFacing;
    }

    @Override // com.android.camera.dualvideo.render.IRenderable
    public boolean isPressedIn6Patch() {
        return this.mIsPressedIn6Patch;
    }

    @Override // com.android.camera.dualvideo.render.IRenderable
    public boolean isVisible() {
        return this.mIsVisible;
    }

    @Override // com.android.camera.dualvideo.render.IRenderable
    public void onKeyDown() {
        if (!this.mIsAnimating || this.mIsPressedIn6Patch) {
            this.mIsPressedIn6Patch = true;
            animShrink();
            Log.d(TAG, "onKeyDown: ");
        }
    }

    @Override // com.android.camera.dualvideo.render.IRenderable
    public void onKeyUp() {
        if (this.mIsPressedIn6Patch) {
            restoreAnimShrink();
        }
        Log.d(TAG, "onKeyUp: ");
    }

    @Override // com.android.camera.dualvideo.render.IRenderable
    public void setBasicPreviewTransMatrix(float[] fArr) {
        this.mBasicPreviewTransMatrix = fArr;
    }

    @Override // com.android.camera.dualvideo.render.IRenderable
    public void setComposeTypeWithAnimation(int i, RegionHelper regionHelper, boolean z) {
        if (!this.mIsAnimating) {
            this.mLastPreviewComposeType = this.mRenderComposeType;
            this.mRenderComposeType = i;
            Log.d(TAG, "setComposeTypeWithAnimation: from: " + this.mLastPreviewComposeType + " to: " + this.mRenderComposeType);
            AnimConfig animConfig = new AnimConfig();
            animConfig.setEase(6, (float) getAnimationDuration());
            ComposeChangeAnimListener composeChangeAnimListener = new ComposeChangeAnimListener(regionHelper);
            animConfig.addListeners(composeChangeAnimListener);
            if (z) {
                this.mIsAnimating = true;
                Folme.useValue(new Object[0]).setTo((Object) 0).to(1000, animConfig);
                return;
            }
            composeChangeAnimListener.animatePreview(1000);
        }
    }

    @Override // com.android.camera.dualvideo.render.IRenderable
    public void setRenderAttri(DrawExtTexAttribute drawExtTexAttribute) {
        this.mPreviewAttri = drawExtTexAttribute;
    }

    @Override // com.android.camera.dualvideo.render.IRenderable
    public void setSelectedtypeWithAnim(int i, boolean z) {
        Log.d(TAG, "setSelectedtypeWithAnim: " + i);
        int i2 = this.mSelectedType;
        if (i2 != i) {
            if (i2 == 0 || i == 0) {
                this.mSelectedType = i;
                if (!z) {
                    return;
                }
                if (this.mSelectedType == 0) {
                    alphaInSelectedFrame(false);
                } else {
                    alphaInSelectedFrame(true);
                }
            } else {
                this.mSelectedType = i;
            }
        }
    }

    @Override // com.android.camera.dualvideo.render.IRenderable
    public void setVisibilityWithAnim(boolean z, boolean z2) {
        if (this.mIsVisible != z) {
            if (z2) {
                this.mIsVisible = true;
                int i = z ? 200 : 100;
                int i2 = 1000;
                int i3 = z ? 0 : 1000;
                if (!z) {
                    i2 = 0;
                }
                Folme.useValue(new Object[0]).setTo(Integer.valueOf(i3)).to(Integer.valueOf(i2), new AnimConfig().setEase(6, (float) i).addListeners(new TransitionListener() {
                    /* class com.android.camera.dualvideo.render.RenderItem.AnonymousClass1 */

                    @Override // miuix.animation.listener.TransitionListener
                    public void onUpdate(Object obj, IIntValueProperty iIntValueProperty, int i, float f, boolean z) {
                        RenderItem.this.mAlpha = ((float) i) / 1000.0f;
                        if (!z) {
                            return;
                        }
                        if (i == 1000) {
                            RenderItem.this.mIsVisible = true;
                        } else {
                            RenderItem.this.mIsVisible = false;
                        }
                    }
                }));
                return;
            }
            this.mIsVisible = z;
            this.mAlpha = z ? 1.0f : 0.0f;
        }
    }

    public String toString() {
        return "RenderItem{mLastPreviewComposeType=" + this.mLastPreviewComposeType + ", mRenderComposeType=" + this.mRenderComposeType + ", m6patchComposeType=" + this.m6patchComposeType + ", mFacing=" + this.mFacing + ", mIsAnimating=" + this.mIsAnimating + ", mIsVisible=" + this.mIsVisible + ", mSelectedType=" + this.mSelectedType + '}';
    }

    @Override // com.android.camera.dualvideo.render.IRenderable
    public void updateRenderAttri(RegionHelper regionHelper) {
        setRenderAttri(genPreviewAttri(regionHelper));
    }
}
