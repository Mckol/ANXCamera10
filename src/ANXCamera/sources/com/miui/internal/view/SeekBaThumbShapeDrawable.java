package com.miui.internal.view;

import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import com.miui.internal.R;
import com.miui.internal.view.SeekBarGradientDrawable;
import miui.animation.physics.DynamicAnimation;
import miui.animation.physics.SpringAnimation;
import miui.animation.property.FloatProperty;

public class SeekBaThumbShapeDrawable extends SeekBarGradientDrawable {
    private static final int FULL_ALPHA = 255;
    private static final String TAG = "SeekBaThumbShape";
    private static Drawable mShadowDrawable;
    private DynamicAnimation.OnAnimationUpdateListener mInvalidateUpdateListener = new DynamicAnimation.OnAnimationUpdateListener() {
        /* class com.miui.internal.view.SeekBaThumbShapeDrawable.AnonymousClass2 */

        @Override // miui.animation.physics.DynamicAnimation.OnAnimationUpdateListener
        public void onAnimationUpdate(DynamicAnimation dynamicAnimation, float f, float f2) {
            SeekBaThumbShapeDrawable.this.invalidateSelf();
        }
    };
    private SpringAnimation mPressedScaleAnim;
    private SpringAnimation mPressedShadowShowAnim;
    private float mScale = 1.0f;
    private FloatProperty<SeekBaThumbShapeDrawable> mScaleFloatProperty = new FloatProperty<SeekBaThumbShapeDrawable>("Scale") {
        /* class com.miui.internal.view.SeekBaThumbShapeDrawable.AnonymousClass3 */

        public float getValue(SeekBaThumbShapeDrawable seekBaThumbShapeDrawable) {
            return seekBaThumbShapeDrawable.getScale();
        }

        public void setValue(SeekBaThumbShapeDrawable seekBaThumbShapeDrawable, float f) {
            seekBaThumbShapeDrawable.setScale(f);
        }
    };
    private float mShadowAlpha = 0.0f;
    private FloatProperty<SeekBaThumbShapeDrawable> mShadowAlphaProperty = new FloatProperty<SeekBaThumbShapeDrawable>("ShadowAlpha") {
        /* class com.miui.internal.view.SeekBaThumbShapeDrawable.AnonymousClass1 */

        public float getValue(SeekBaThumbShapeDrawable seekBaThumbShapeDrawable) {
            return seekBaThumbShapeDrawable.getShadowAlpha();
        }

        public void setValue(SeekBaThumbShapeDrawable seekBaThumbShapeDrawable, float f) {
            float f2 = 1.0f;
            if (f <= 1.0f) {
                f2 = f;
            }
            if (f2 < 0.0f) {
                f2 = 0.0f;
            }
            seekBaThumbShapeDrawable.setShadowAlpha(f2);
        }
    };
    private SpringAnimation mUnPressedScaleAnim;
    private SpringAnimation mUnPressedShadowHideAnim;

    protected static class SeekBaThumbShapeDrawableState extends SeekBarGradientDrawable.SeekBarGradientState {
        protected SeekBaThumbShapeDrawableState() {
        }

        /* access modifiers changed from: protected */
        @Override // com.miui.internal.view.SeekBarGradientDrawable.SeekBarGradientState
        public Drawable newSeekBarGradientDrawable(Resources resources, Resources.Theme theme, SeekBarGradientDrawable.SeekBarGradientState seekBarGradientState) {
            return new SeekBaThumbShapeDrawable(resources, theme, seekBarGradientState);
        }
    }

    public SeekBaThumbShapeDrawable() {
        initAnim();
    }

    public SeekBaThumbShapeDrawable(Resources resources, Resources.Theme theme, SeekBarGradientDrawable.SeekBarGradientState seekBarGradientState) {
        super(resources, theme, seekBarGradientState);
        initAnim();
        if (resources != null && mShadowDrawable == null) {
            mShadowDrawable = resources.getDrawable(R.drawable.sliding_btn_slider_shadow);
        }
    }

    private void drawShadow(Canvas canvas) {
        Rect bounds = getBounds();
        Drawable drawable = mShadowDrawable;
        if (drawable != null) {
            int intrinsicWidth = drawable.getIntrinsicWidth();
            int intrinsicHeight = mShadowDrawable.getIntrinsicHeight();
            int intrinsicWidth2 = intrinsicWidth - getIntrinsicWidth();
            int i = intrinsicWidth2 / 2;
            int intrinsicHeight2 = (intrinsicHeight - getIntrinsicHeight()) / 2;
            mShadowDrawable.setBounds(bounds.left - i, bounds.top - intrinsicHeight2, bounds.right + i, bounds.bottom + intrinsicHeight2);
            mShadowDrawable.setAlpha((int) (this.mShadowAlpha * 255.0f));
            mShadowDrawable.draw(canvas);
        }
    }

    private void initAnim() {
        this.mPressedScaleAnim = new SpringAnimation(this, this.mScaleFloatProperty, 3.19f);
        this.mPressedScaleAnim.getSpring().setStiffness(986.96f);
        this.mPressedScaleAnim.getSpring().setDampingRatio(0.7f);
        this.mPressedScaleAnim.setMinimumVisibleChange(0.002f);
        this.mPressedScaleAnim.addUpdateListener(this.mInvalidateUpdateListener);
        this.mUnPressedScaleAnim = new SpringAnimation(this, this.mScaleFloatProperty, 1.0f);
        this.mUnPressedScaleAnim.getSpring().setStiffness(986.96f);
        this.mUnPressedScaleAnim.getSpring().setDampingRatio(0.8f);
        this.mUnPressedScaleAnim.setMinimumVisibleChange(0.002f);
        this.mUnPressedScaleAnim.addUpdateListener(this.mInvalidateUpdateListener);
        this.mPressedShadowShowAnim = new SpringAnimation(this, this.mShadowAlphaProperty, 1.0f);
        this.mPressedShadowShowAnim.getSpring().setStiffness(986.96f);
        this.mPressedShadowShowAnim.getSpring().setDampingRatio(0.99f);
        this.mPressedShadowShowAnim.setMinimumVisibleChange(0.00390625f);
        this.mPressedShadowShowAnim.addUpdateListener(this.mInvalidateUpdateListener);
        this.mUnPressedShadowHideAnim = new SpringAnimation(this, this.mShadowAlphaProperty, 0.0f);
        this.mUnPressedShadowHideAnim.getSpring().setStiffness(986.96f);
        this.mUnPressedShadowHideAnim.getSpring().setDampingRatio(0.99f);
        this.mUnPressedShadowHideAnim.setMinimumVisibleChange(0.00390625f);
        this.mUnPressedShadowHideAnim.addUpdateListener(this.mInvalidateUpdateListener);
    }

    public void draw(Canvas canvas) {
        Rect bounds = getBounds();
        drawShadow(canvas);
        canvas.save();
        float f = this.mScale;
        canvas.scale(f, f, (float) ((bounds.right + bounds.left) / 2), (float) ((bounds.top + bounds.bottom) / 2));
        super.draw(canvas);
        canvas.restore();
    }

    public float getScale() {
        return this.mScale;
    }

    public float getShadowAlpha() {
        return this.mShadowAlpha;
    }

    /* access modifiers changed from: protected */
    @Override // com.miui.internal.view.SeekBarGradientDrawable
    public SeekBarGradientDrawable.SeekBarGradientState newSeekBarGradientState() {
        return new SeekBaThumbShapeDrawableState();
    }

    public void setScale(float f) {
        this.mScale = f;
    }

    public void setShadowAlpha(float f) {
        this.mShadowAlpha = f;
    }

    /* access modifiers changed from: protected */
    @Override // com.miui.internal.view.SeekBarGradientDrawable
    public void startPressedAnim() {
        if (this.mUnPressedScaleAnim.isRunning()) {
            this.mUnPressedScaleAnim.cancel();
        }
        if (!this.mPressedScaleAnim.isRunning()) {
            this.mPressedScaleAnim.start();
        }
        if (this.mUnPressedShadowHideAnim.isRunning()) {
            this.mUnPressedShadowHideAnim.cancel();
        }
        if (!this.mPressedShadowShowAnim.isRunning()) {
            this.mPressedShadowShowAnim.start();
        }
    }

    /* access modifiers changed from: protected */
    @Override // com.miui.internal.view.SeekBarGradientDrawable
    public void startUnPressedAnim() {
        if (this.mPressedScaleAnim.isRunning()) {
            this.mPressedScaleAnim.cancel();
        }
        if (!this.mUnPressedScaleAnim.isRunning()) {
            this.mUnPressedScaleAnim.start();
        }
        if (this.mPressedShadowShowAnim.isRunning()) {
            this.mPressedShadowShowAnim.cancel();
        }
        if (!this.mUnPressedShadowHideAnim.isRunning()) {
            this.mUnPressedShadowHideAnim.start();
        }
    }
}
