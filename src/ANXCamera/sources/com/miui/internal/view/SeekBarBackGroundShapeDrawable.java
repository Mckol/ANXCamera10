package com.miui.internal.view;

import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import androidx.core.view.ViewCompat;
import com.miui.internal.view.SeekBarGradientDrawable;
import miui.animation.physics.DynamicAnimation;
import miui.animation.physics.SpringAnimation;
import miui.animation.property.FloatProperty;

public class SeekBarBackGroundShapeDrawable extends SeekBarGradientDrawable {
    private static final int FULL_ALPHA = 255;
    private float mBlackAlpha = 0.0f;
    private FloatProperty<SeekBarBackGroundShapeDrawable> mBlackAlphaFloatProperty = new FloatProperty<SeekBarBackGroundShapeDrawable>("BlackAlpha") {
        /* class com.miui.internal.view.SeekBarBackGroundShapeDrawable.AnonymousClass1 */

        public float getValue(SeekBarBackGroundShapeDrawable seekBarBackGroundShapeDrawable) {
            return seekBarBackGroundShapeDrawable.getBlackAlpha();
        }

        public void setValue(SeekBarBackGroundShapeDrawable seekBarBackGroundShapeDrawable, float f) {
            seekBarBackGroundShapeDrawable.setBlackAlpha(f);
        }
    };
    private GradientDrawable mMaskDrawable;
    private SpringAnimation mPressedBlackAnim;
    private SpringAnimation mUnPressedBlackAnim;

    protected static class SeekBarBackGroundShapeDrawableState extends SeekBarGradientDrawable.SeekBarGradientState {
        protected SeekBarBackGroundShapeDrawableState() {
        }

        /* access modifiers changed from: protected */
        @Override // com.miui.internal.view.SeekBarGradientDrawable.SeekBarGradientState
        public Drawable newSeekBarGradientDrawable(Resources resources, Resources.Theme theme, SeekBarGradientDrawable.SeekBarGradientState seekBarGradientState) {
            return new SeekBarBackGroundShapeDrawable(resources, theme, seekBarGradientState);
        }
    }

    public SeekBarBackGroundShapeDrawable() {
        initAnim();
        initMaskDrawable();
    }

    public SeekBarBackGroundShapeDrawable(Resources resources, Resources.Theme theme, SeekBarGradientDrawable.SeekBarGradientState seekBarGradientState) {
        super(resources, theme, seekBarGradientState);
        initAnim();
        initMaskDrawable();
    }

    private void drawMask(Canvas canvas) {
        this.mMaskDrawable.setBounds(getBounds());
        this.mMaskDrawable.setAlpha((int) (this.mBlackAlpha * 255.0f));
        this.mMaskDrawable.draw(canvas);
    }

    private void initAnim() {
        this.mPressedBlackAnim = new SpringAnimation(this, this.mBlackAlphaFloatProperty, 0.05f);
        this.mPressedBlackAnim.getSpring().setStiffness(986.96f);
        this.mPressedBlackAnim.getSpring().setDampingRatio(0.99f);
        this.mPressedBlackAnim.setMinimumVisibleChange(0.00390625f);
        this.mPressedBlackAnim.addUpdateListener(new DynamicAnimation.OnAnimationUpdateListener() {
            /* class com.miui.internal.view.SeekBarBackGroundShapeDrawable.AnonymousClass2 */

            @Override // miui.animation.physics.DynamicAnimation.OnAnimationUpdateListener
            public void onAnimationUpdate(DynamicAnimation dynamicAnimation, float f, float f2) {
                SeekBarBackGroundShapeDrawable.this.invalidateSelf();
            }
        });
        this.mUnPressedBlackAnim = new SpringAnimation(this, this.mBlackAlphaFloatProperty, 0.0f);
        this.mUnPressedBlackAnim.getSpring().setStiffness(986.96f);
        this.mUnPressedBlackAnim.getSpring().setDampingRatio(0.99f);
        this.mUnPressedBlackAnim.setMinimumVisibleChange(0.00390625f);
        this.mUnPressedBlackAnim.addUpdateListener(new DynamicAnimation.OnAnimationUpdateListener() {
            /* class com.miui.internal.view.SeekBarBackGroundShapeDrawable.AnonymousClass3 */

            @Override // miui.animation.physics.DynamicAnimation.OnAnimationUpdateListener
            public void onAnimationUpdate(DynamicAnimation dynamicAnimation, float f, float f2) {
                SeekBarBackGroundShapeDrawable.this.invalidateSelf();
            }
        });
    }

    private void initMaskDrawable() {
        this.mMaskDrawable = new GradientDrawable(getOrientation(), getColors());
        this.mMaskDrawable.setCornerRadius(getCornerRadius());
        this.mMaskDrawable.setShape(getShape());
        this.mMaskDrawable.setColor(ViewCompat.MEASURED_STATE_MASK);
    }

    public void draw(Canvas canvas) {
        super.draw(canvas);
        drawMask(canvas);
    }

    public float getBlackAlpha() {
        return this.mBlackAlpha;
    }

    /* access modifiers changed from: protected */
    @Override // com.miui.internal.view.SeekBarGradientDrawable
    public SeekBarGradientDrawable.SeekBarGradientState newSeekBarGradientState() {
        return new SeekBarBackGroundShapeDrawableState();
    }

    public void setBlackAlpha(float f) {
        this.mBlackAlpha = f;
    }

    /* access modifiers changed from: protected */
    @Override // com.miui.internal.view.SeekBarGradientDrawable
    public void startPressedAnim() {
        this.mPressedBlackAnim.start();
    }

    /* access modifiers changed from: protected */
    @Override // com.miui.internal.view.SeekBarGradientDrawable
    public void startUnPressedAnim() {
        this.mUnPressedBlackAnim.start();
    }
}
