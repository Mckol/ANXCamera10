package com.android.camera;

import android.os.SystemClock;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Interpolator;
import com.android.camera.log.Log;
import com.android.gallery3d.ui.GLCanvas;
import com.android.gallery3d.ui.RawTexture;

public class SwitchAnimManager {
    private static final float ROTATE_DURATION = 300.0f;
    private static final String TAG = "SwitchAnimManager";
    private static final float ZOOM_DELTA_PREVIEW = 0.2f;
    private boolean mAlphaAnimation;
    private long mAnimStartTime;
    private float mExtScale = 1.0f;
    private Interpolator mInterpolator = new AccelerateDecelerateInterpolator();
    private boolean mMoveBack;
    private boolean mNewPreview;
    private int mPreviewFrameLayoutWidth;
    private boolean mRecurBlur;
    private int mReviewDrawingHeight;
    private int mReviewDrawingWidth;
    private int mReviewDrawingX;
    private int mReviewDrawingY;
    private float mTimeOut = ROTATE_DURATION;

    public SwitchAnimManager() {
    }

    public SwitchAnimManager(float f) {
        this.mTimeOut = f;
    }

    private boolean drawAnimationBlend(GLCanvas gLCanvas, int i, int i2, int i3, int i4, CameraScreenNail cameraScreenNail, RawTexture rawTexture) {
        boolean z;
        if (this.mNewPreview) {
            return false;
        }
        long uptimeMillis = SystemClock.uptimeMillis() - this.mAnimStartTime;
        float f = this.mTimeOut;
        if (((float) uptimeMillis) > f) {
            uptimeMillis = (long) f;
            z = false;
        } else {
            z = true;
        }
        if (!z && this.mRecurBlur) {
            this.mRecurBlur = false;
        }
        drawBlurTexture(gLCanvas, i, i2, i3, i4, cameraScreenNail, this.mInterpolator.getInterpolation(((float) uptimeMillis) / this.mTimeOut));
        return z;
    }

    private void drawBlurTexture(GLCanvas gLCanvas, int i, int i2, int i3, int i4, CameraScreenNail cameraScreenNail, float f) {
        if (this.mRecurBlur) {
            cameraScreenNail.renderBlurTexture(gLCanvas);
        }
        gLCanvas.getState().pushState();
        if (this.mAlphaAnimation) {
            gLCanvas.getState().setBlendAlpha(1.0f - (f * 0.7f));
        }
        cameraScreenNail.drawBlurTexture(gLCanvas, this.mReviewDrawingX, this.mReviewDrawingY, this.mReviewDrawingWidth, this.mReviewDrawingHeight);
        gLCanvas.getState().popState();
    }

    private void drawCopiedTexture(GLCanvas gLCanvas, int i, int i2, RawTexture rawTexture, float f) {
        if (this.mMoveBack) {
            int i3 = this.mReviewDrawingWidth;
            float f2 = ((float) this.mReviewDrawingX) + (((float) i3) / 2.0f);
            int i4 = this.mReviewDrawingHeight;
            float f3 = 1.0f - (0.2f * f);
            float f4 = ((float) i3) * f3;
            float f5 = ((float) i4) * f3;
            rawTexture.draw(gLCanvas, Math.round(f2 - (f4 / 2.0f)), Math.round((((float) this.mReviewDrawingY) + (((float) i4) / 2.0f)) - (f5 / 2.0f)), Math.round(f4), Math.round(f5));
            return;
        }
        float width = (float) rawTexture.getWidth();
        float f6 = width / 2.0f;
        float height = (float) rawTexture.getHeight();
        float f7 = height / 2.0f;
        float f8 = 1.0f - (0.2f * f);
        float f9 = width * f8;
        float f10 = height * f8;
        rawTexture.draw(gLCanvas, Math.round(f6 - (f9 / 2.0f)), Math.round(f7 - (f10 / 2.0f)), Math.round(f9), Math.round(f10), this.mReviewDrawingX, this.mReviewDrawingY, this.mReviewDrawingWidth, this.mReviewDrawingHeight);
    }

    private void drawRealTimeTexture(GLCanvas gLCanvas, int i, int i2, int i3, int i4, CameraScreenNail cameraScreenNail, float f) {
        if (this.mNewPreview) {
            gLCanvas.getState().pushState();
            gLCanvas.getState().setAlpha(f);
            cameraScreenNail.directDraw(gLCanvas, i, i2, i3, i4);
            gLCanvas.getState().popState();
        }
    }

    public void clearAnimation() {
        this.mAnimStartTime = 0;
        this.mRecurBlur = false;
        this.mAlphaAnimation = false;
    }

    public boolean drawAnimation(GLCanvas gLCanvas, int i, int i2, int i3, int i4, CameraScreenNail cameraScreenNail, RawTexture rawTexture) {
        return drawAnimationBlend(gLCanvas, i, i2, i3, i4, cameraScreenNail, rawTexture);
    }

    public boolean drawPreview(GLCanvas gLCanvas, int i, int i2, int i3, int i4, RawTexture rawTexture) {
        float f;
        float f2 = (float) i3;
        float f3 = ((float) i) + (f2 / 2.0f);
        float f4 = ((float) i2) + (((float) i4) / 2.0f);
        int i5 = this.mPreviewFrameLayoutWidth;
        if (i5 != 0) {
            f = f2 / ((float) i5);
        } else {
            Log.e(TAG, "previewFrameLayoutWidth=0");
            f = 1.0f;
        }
        float f5 = ((float) this.mReviewDrawingWidth) * f;
        float f6 = ((float) this.mReviewDrawingHeight) * f;
        int round = Math.round(f3 - (f5 / 2.0f));
        int round2 = Math.round(f4 - (f6 / 2.0f));
        float alpha = gLCanvas.getState().getAlpha();
        rawTexture.draw(gLCanvas, round, round2, Math.round(f5), Math.round(f6));
        gLCanvas.getState().setAlpha(alpha);
        return true;
    }

    public float getExtScaleX() {
        return this.mExtScale;
    }

    public float getExtScaleY() {
        return this.mExtScale;
    }

    public void setPreviewFrameLayoutSize(int i, int i2) {
        this.mPreviewFrameLayoutWidth = i;
        if (i == 0) {
            Log.e(TAG, "invalid preview frame width", new RuntimeException());
        }
    }

    public void setReviewDrawingSize(int i, int i2, int i3, int i4) {
        this.mReviewDrawingX = i;
        this.mReviewDrawingY = i2;
        this.mReviewDrawingWidth = i3;
        this.mReviewDrawingHeight = i4;
        this.mMoveBack = CameraSettings.isBackCamera();
        this.mNewPreview = false;
    }

    public void startAnimation(boolean z) {
        if (z) {
            Log.v(TAG, "startAnimation with alpha animation");
        } else {
            Log.v(TAG, "startAnimation");
        }
        this.mAnimStartTime = SystemClock.uptimeMillis();
        this.mRecurBlur = true;
        this.mAlphaAnimation = z;
    }

    public void startResume() {
        Log.v(TAG, "startResume");
        this.mNewPreview = true;
        this.mRecurBlur = false;
    }
}
