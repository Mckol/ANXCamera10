package com.miui.internal.view;

import android.graphics.Canvas;
import android.graphics.Rect;
import android.os.Handler;
import android.os.Looper;
import miui.animation.physics.DynamicAnimation;
import miui.animation.physics.SpringAnimation;
import miui.animation.property.FloatProperty;

public class CheckWidgetDrawableAnims {
    private static final int FULL_ALPHA = 255;
    private static final float HIGH_STIFFNESS = 986.96f;
    private int mBackgroundDisableAlpha;
    private int mBackgroundNormalAlpha;
    private CheckWidgetCircleDrawable mBlackDrawable;
    private CheckWidgetCircleDrawable mBlueDrawable;
    private FloatProperty<CheckWidgetCircleDrawable> mCircleAlphaFloatProperty = new FloatProperty<CheckWidgetCircleDrawable>("Alpha") {
        /* class com.miui.internal.view.CheckWidgetDrawableAnims.AnonymousClass6 */

        public float getValue(CheckWidgetCircleDrawable checkWidgetCircleDrawable) {
            return (float) (checkWidgetCircleDrawable.getAlpha() / 255);
        }

        public void setValue(CheckWidgetCircleDrawable checkWidgetCircleDrawable, float f) {
            float f2 = 1.0f;
            if (f <= 1.0f) {
                f2 = f;
            }
            if (f2 < 0.0f) {
                f2 = 0.0f;
            }
            checkWidgetCircleDrawable.setAlpha((int) (f2 * 255.0f));
        }
    };
    private CheckWidgetCircleDrawable mGrayDrawable;
    private boolean mIsSingleSelection = false;
    private CheckBoxAnimatedStateListDrawable mParent;
    private SpringAnimation mParentCheckedUnPressScaleAnim;
    private FloatProperty<CheckBoxAnimatedStateListDrawable> mParentContentAlphaFloatProperty = new FloatProperty<CheckBoxAnimatedStateListDrawable>("ContentAlpha") {
        /* class com.miui.internal.view.CheckWidgetDrawableAnims.AnonymousClass4 */

        public float getValue(CheckBoxAnimatedStateListDrawable checkBoxAnimatedStateListDrawable) {
            return checkBoxAnimatedStateListDrawable.getContentAlpha();
        }

        public void setValue(CheckBoxAnimatedStateListDrawable checkBoxAnimatedStateListDrawable, float f) {
            float f2 = 1.0f;
            if (f <= 1.0f) {
                f2 = f;
            }
            if (f2 < 0.0f) {
                f2 = 0.0f;
            }
            checkBoxAnimatedStateListDrawable.setContentAlpha(f2);
        }
    };
    private DynamicAnimation.OnAnimationUpdateListener mParentInvalidListener = new DynamicAnimation.OnAnimationUpdateListener() {
        /* class com.miui.internal.view.CheckWidgetDrawableAnims.AnonymousClass1 */

        @Override // miui.animation.physics.DynamicAnimation.OnAnimationUpdateListener
        public void onAnimationUpdate(DynamicAnimation dynamicAnimation, float f, float f2) {
            CheckWidgetDrawableAnims.this.mParent.invalidateSelf();
        }
    };
    private SpringAnimation mParentPressAnim;
    private FloatProperty<CheckBoxAnimatedStateListDrawable> mParentScaleFloatProperty = new FloatProperty<CheckBoxAnimatedStateListDrawable>("Scale") {
        /* class com.miui.internal.view.CheckWidgetDrawableAnims.AnonymousClass3 */

        public float getValue(CheckBoxAnimatedStateListDrawable checkBoxAnimatedStateListDrawable) {
            return CheckWidgetDrawableAnims.this.mParent.getScale();
        }

        public void setValue(CheckBoxAnimatedStateListDrawable checkBoxAnimatedStateListDrawable, float f) {
            CheckWidgetDrawableAnims.this.mParent.setScale(f);
        }
    };
    private DynamicAnimation.OnAnimationUpdateListener mParentScaleInvalidListener = new DynamicAnimation.OnAnimationUpdateListener() {
        /* class com.miui.internal.view.CheckWidgetDrawableAnims.AnonymousClass2 */

        @Override // miui.animation.physics.DynamicAnimation.OnAnimationUpdateListener
        public void onAnimationUpdate(DynamicAnimation dynamicAnimation, float f, float f2) {
            CheckWidgetDrawableAnims.this.mParent.setScale(CheckWidgetDrawableAnims.this.getScale());
            CheckWidgetDrawableAnims.this.mParent.invalidateSelf();
        }
    };
    private SpringAnimation mParentUnCheckedUnPressScaleAnim;
    private SpringAnimation mParentUnPressAlphaAnim;
    private SpringAnimation mPressedBlackAnim;
    private SpringAnimation mPressedScaleAnim;
    private float mScale = 1.0f;
    private SpringAnimation mUnPressedBlackAnim;
    private SpringAnimation mUnPressedBlueHideAnim;
    private SpringAnimation mUnPressedBlueShowAnim;
    private FloatProperty<CheckWidgetDrawableAnims> scaleFloatProperty = new FloatProperty<CheckWidgetDrawableAnims>("Scale") {
        /* class com.miui.internal.view.CheckWidgetDrawableAnims.AnonymousClass5 */

        public float getValue(CheckWidgetDrawableAnims checkWidgetDrawableAnims) {
            return CheckWidgetDrawableAnims.this.getScale();
        }

        public void setValue(CheckWidgetDrawableAnims checkWidgetDrawableAnims, float f) {
            CheckWidgetDrawableAnims.this.setScale(f);
        }
    };
    private SpringAnimation unPressedScaleAnim;

    public CheckWidgetDrawableAnims(CheckBoxAnimatedStateListDrawable checkBoxAnimatedStateListDrawable, boolean z, int i, int i2, int i3, int i4, int i5, int i6, int i7, int i8) {
        this.mBackgroundNormalAlpha = i4;
        this.mBackgroundDisableAlpha = i5;
        this.mIsSingleSelection = z;
        this.mGrayDrawable = new CheckWidgetCircleDrawable(i, i4, i5, i6, i7, i8);
        this.mGrayDrawable.setAlpha(this.mBackgroundNormalAlpha);
        this.mBlackDrawable = new CheckWidgetCircleDrawable(i2, i4, i5);
        this.mBlackDrawable.setAlpha(0);
        this.mBlueDrawable = new CheckWidgetCircleDrawable(i3, i4, i5);
        this.mBlueDrawable.setAlpha(255);
        this.mParent = checkBoxAnimatedStateListDrawable;
        initAnim();
    }

    private void initAnim() {
        this.mPressedScaleAnim = new SpringAnimation(this, this.scaleFloatProperty, 0.6f);
        this.mPressedScaleAnim.getSpring().setStiffness(HIGH_STIFFNESS);
        this.mPressedScaleAnim.getSpring().setDampingRatio(0.99f);
        this.mPressedScaleAnim.getSpring().setFinalPosition(0.6f);
        this.mPressedScaleAnim.setMinimumVisibleChange(0.002f);
        this.mPressedScaleAnim.addUpdateListener(this.mParentScaleInvalidListener);
        this.unPressedScaleAnim = new SpringAnimation(this, this.scaleFloatProperty, 1.0f);
        this.unPressedScaleAnim.getSpring().setStiffness(HIGH_STIFFNESS);
        this.unPressedScaleAnim.getSpring().setDampingRatio(0.6f);
        this.unPressedScaleAnim.setMinimumVisibleChange(0.002f);
        this.unPressedScaleAnim.addUpdateListener(new DynamicAnimation.OnAnimationUpdateListener() {
            /* class com.miui.internal.view.CheckWidgetDrawableAnims.AnonymousClass7 */

            @Override // miui.animation.physics.DynamicAnimation.OnAnimationUpdateListener
            public void onAnimationUpdate(DynamicAnimation dynamicAnimation, float f, float f2) {
                CheckWidgetDrawableAnims.this.mParent.invalidateSelf();
            }
        });
        this.mParentPressAnim = new SpringAnimation(this.mParent, this.mParentContentAlphaFloatProperty, 0.5f);
        this.mParentPressAnim.getSpring().setStiffness(HIGH_STIFFNESS);
        this.mParentPressAnim.getSpring().setDampingRatio(0.99f);
        this.mParentPressAnim.setMinimumVisibleChange(0.00390625f);
        this.mParentPressAnim.addUpdateListener(this.mParentInvalidListener);
        this.mPressedBlackAnim = new SpringAnimation(this.mBlackDrawable, this.mCircleAlphaFloatProperty, 0.1f);
        this.mPressedBlackAnim.getSpring().setStiffness(HIGH_STIFFNESS);
        this.mPressedBlackAnim.getSpring().setDampingRatio(0.99f);
        this.mPressedBlackAnim.setMinimumVisibleChange(0.00390625f);
        this.mPressedBlackAnim.addUpdateListener(this.mParentInvalidListener);
        this.mUnPressedBlackAnim = new SpringAnimation(this.mBlackDrawable, this.mCircleAlphaFloatProperty, 0.0f);
        this.mUnPressedBlackAnim.getSpring().setStiffness(HIGH_STIFFNESS);
        this.mUnPressedBlackAnim.getSpring().setDampingRatio(0.99f);
        this.mUnPressedBlackAnim.setMinimumVisibleChange(0.00390625f);
        this.mUnPressedBlackAnim.addUpdateListener(this.mParentInvalidListener);
        this.mUnPressedBlueShowAnim = new SpringAnimation(this.mBlueDrawable, this.mCircleAlphaFloatProperty, 1.0f);
        this.mUnPressedBlueShowAnim.getSpring().setStiffness(HIGH_STIFFNESS);
        this.mUnPressedBlueShowAnim.getSpring().setDampingRatio(0.7f);
        this.mUnPressedBlueShowAnim.setMinimumVisibleChange(0.00390625f);
        this.mUnPressedBlueShowAnim.addUpdateListener(this.mParentInvalidListener);
        this.mParentUnPressAlphaAnim = new SpringAnimation(this.mParent, this.mParentContentAlphaFloatProperty, 1.0f);
        this.mParentUnPressAlphaAnim.getSpring().setStiffness(438.64f);
        this.mParentUnPressAlphaAnim.getSpring().setDampingRatio(0.6f);
        this.mParentUnPressAlphaAnim.setMinimumVisibleChange(0.00390625f);
        this.mParentUnPressAlphaAnim.addUpdateListener(this.mParentInvalidListener);
        this.mUnPressedBlueHideAnim = new SpringAnimation(this.mBlueDrawable, this.mCircleAlphaFloatProperty, 0.0f);
        this.mUnPressedBlueHideAnim.getSpring().setStiffness(HIGH_STIFFNESS);
        this.mUnPressedBlueHideAnim.getSpring().setDampingRatio(0.99f);
        this.mUnPressedBlueHideAnim.setMinimumVisibleChange(0.00390625f);
        this.mUnPressedBlueHideAnim.addUpdateListener(this.mParentInvalidListener);
        this.mParentCheckedUnPressScaleAnim = new SpringAnimation(this.mParent, this.mParentScaleFloatProperty, 1.0f);
        this.mParentCheckedUnPressScaleAnim.getSpring().setStiffness(438.64f);
        this.mParentCheckedUnPressScaleAnim.getSpring().setDampingRatio(0.6f);
        this.mParentCheckedUnPressScaleAnim.setMinimumVisibleChange(0.002f);
        this.mParentCheckedUnPressScaleAnim.addUpdateListener(this.mParentInvalidListener);
        if (this.mIsSingleSelection) {
            this.mParentCheckedUnPressScaleAnim.setStartVelocity(5.0f);
        } else {
            this.mParentCheckedUnPressScaleAnim.setStartVelocity(10.0f);
        }
        this.mParentUnCheckedUnPressScaleAnim = new SpringAnimation(this.mParent, this.mParentScaleFloatProperty, 0.3f);
        this.mParentUnCheckedUnPressScaleAnim.getSpring().setStiffness(HIGH_STIFFNESS);
        this.mParentUnCheckedUnPressScaleAnim.getSpring().setDampingRatio(0.99f);
        this.mParentUnCheckedUnPressScaleAnim.setMinimumVisibleChange(0.002f);
        this.mParentUnCheckedUnPressScaleAnim.addUpdateListener(this.mParentScaleInvalidListener);
    }

    public void draw(Canvas canvas) {
        this.mGrayDrawable.draw(canvas);
        this.mBlackDrawable.draw(canvas);
        this.mBlueDrawable.draw(canvas);
    }

    public float getScale() {
        return this.mScale;
    }

    public void setBounds(int i, int i2, int i3, int i4) {
        this.mGrayDrawable.setBounds(i, i2, i3, i4);
        this.mBlackDrawable.setBounds(i, i2, i3, i4);
        this.mBlueDrawable.setBounds(i, i2, i3, i4);
    }

    public void setBounds(Rect rect) {
        this.mGrayDrawable.setBounds(rect);
        this.mBlackDrawable.setBounds(rect);
        this.mBlueDrawable.setBounds(rect);
    }

    public void setScale(float f) {
        this.mGrayDrawable.setScale(f);
        this.mBlackDrawable.setScale(f);
        this.mBlueDrawable.setScale(f);
        this.mScale = f;
    }

    /* access modifiers changed from: protected */
    public void startPressedAnim(boolean z, boolean z2) {
        if (z2 && Thread.currentThread() == Looper.getMainLooper().getThread()) {
            if (!this.mPressedScaleAnim.isRunning()) {
                this.mPressedScaleAnim.start();
            }
            if (!this.mParentPressAnim.isRunning()) {
                this.mParentPressAnim.start();
            }
            if (!z && !this.mPressedBlackAnim.isRunning()) {
                this.mPressedBlackAnim.start();
            }
            if (this.mUnPressedBlackAnim.isRunning()) {
                this.mUnPressedBlackAnim.cancel();
            }
            if (this.unPressedScaleAnim.isRunning()) {
                this.unPressedScaleAnim.cancel();
            }
            if (this.mParentUnPressAlphaAnim.isRunning()) {
                this.mParentUnPressAlphaAnim.cancel();
            }
            if (this.mParentCheckedUnPressScaleAnim.isRunning()) {
                this.mParentCheckedUnPressScaleAnim.cancel();
            }
            if (this.mParentUnCheckedUnPressScaleAnim.isRunning()) {
                this.mParentUnCheckedUnPressScaleAnim.cancel();
            }
            if (this.mUnPressedBlueHideAnim.isRunning()) {
                this.mUnPressedBlueHideAnim.cancel();
            }
            if (this.mUnPressedBlueShowAnim.isRunning()) {
                this.mUnPressedBlueShowAnim.cancel();
            }
        }
    }

    /* access modifiers changed from: protected */
    public void startUnPressedAnim(boolean z, boolean z2) {
        if (z2 && Thread.currentThread() == Looper.getMainLooper().getThread()) {
            if (this.mPressedScaleAnim.isRunning()) {
                this.mPressedScaleAnim.cancel();
            }
            if (this.mParentPressAnim.isRunning()) {
                this.mParentPressAnim.cancel();
            }
            if (this.mPressedBlackAnim.isRunning()) {
                this.mPressedBlackAnim.cancel();
            }
            if (!this.mUnPressedBlackAnim.isRunning()) {
                this.mUnPressedBlackAnim.start();
            }
            if (z) {
                if (this.mUnPressedBlueHideAnim.isRunning()) {
                    this.mUnPressedBlueHideAnim.cancel();
                }
                if (!this.mUnPressedBlueShowAnim.isRunning()) {
                    this.mUnPressedBlueShowAnim.start();
                }
                new Handler().postDelayed(new Runnable() {
                    /* class com.miui.internal.view.CheckWidgetDrawableAnims.AnonymousClass8 */

                    public void run() {
                        if (!CheckWidgetDrawableAnims.this.mParentUnPressAlphaAnim.isRunning()) {
                            CheckWidgetDrawableAnims.this.mParentUnPressAlphaAnim.start();
                        }
                        if (!CheckWidgetDrawableAnims.this.mParentCheckedUnPressScaleAnim.isRunning()) {
                            CheckWidgetDrawableAnims.this.mParentCheckedUnPressScaleAnim.start();
                        }
                    }
                }, 50);
                if (this.mIsSingleSelection) {
                    this.unPressedScaleAnim.setStartVelocity(10.0f);
                } else {
                    this.unPressedScaleAnim.setStartVelocity(5.0f);
                }
            } else {
                if (this.mUnPressedBlueShowAnim.isRunning()) {
                    this.mUnPressedBlueShowAnim.cancel();
                }
                if (!this.mUnPressedBlueHideAnim.isRunning()) {
                    this.mUnPressedBlueHideAnim.start();
                }
                if (!this.mParentUnCheckedUnPressScaleAnim.isRunning()) {
                    this.mParentUnCheckedUnPressScaleAnim.start();
                }
            }
            this.unPressedScaleAnim.start();
        } else if (z) {
            this.mBlueDrawable.setAlpha((int) (this.mUnPressedBlueShowAnim.getSpring().getFinalPosition() * 255.0f));
        } else {
            this.mBlueDrawable.setAlpha((int) (this.mUnPressedBlueHideAnim.getSpring().getFinalPosition() * 255.0f));
        }
    }

    /* access modifiers changed from: protected */
    public void verifyChecked(boolean z, boolean z2) {
        if (z2) {
            if (z) {
                this.mBlueDrawable.setAlpha(255);
                this.mBlackDrawable.setAlpha(25);
            } else {
                this.mBlueDrawable.setAlpha(0);
                this.mBlackDrawable.setAlpha(0);
            }
            this.mGrayDrawable.setAlpha(this.mBackgroundNormalAlpha);
            return;
        }
        this.mBlueDrawable.setAlpha(0);
        this.mBlackDrawable.setAlpha(0);
        this.mGrayDrawable.setAlpha(this.mBackgroundDisableAlpha);
    }
}
