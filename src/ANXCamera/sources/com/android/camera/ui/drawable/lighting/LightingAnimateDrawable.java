package com.android.camera.ui.drawable.lighting;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.drawable.Drawable;
import android.view.animation.PathInterpolator;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.android.camera.CameraSettings;
import com.android.camera.Util;
import com.android.camera.customization.TintColor;
import com.android.camera.data.DataRepository;
import miui.view.animation.CubicEaseInOutInterpolator;
import miui.view.animation.SineEaseInOutInterpolator;

public class LightingAnimateDrawable extends Drawable {
    public static final float CENTER_FOCUSING_BIG = 0.576f;
    public static final float CENTER_FOCUSING_SMALL = 0.548f;
    private boolean isShown;
    private ValueAnimator mAlphaAnimator;
    private boolean mAnimateFocusDoing = false;
    private float mCircleHeightRatio = 1.0f;
    private float mCircleRatio = 1.0f;
    private ValueAnimator mExitAnimator;
    private ValueAnimator mFocusingAnimator;
    private int mHeight;
    private float mMiddleHorizontalY;
    private float mMiddleVerticalY;
    private float mMiddleX;
    private LightingPaintCircle mPaintCircle;
    private LightingPaintMask mPaintMask;
    private ValueAnimator mStartAnimator;
    private int mTopMargin;
    private ValueAnimator mUpdateDegreeAnimator;
    private int mWidth;
    private int pendingState;

    public LightingAnimateDrawable(Context context) {
        this.mPaintMask = new LightingPaintMask(context);
        this.mPaintCircle = new LightingPaintCircle(context);
        this.mTopMargin = Util.getDisplayRect(1).top;
    }

    /* access modifiers changed from: private */
    /* access modifiers changed from: public */
    private void animateFocusing() {
        this.mAnimateFocusDoing = true;
        this.pendingState = -1;
        this.mPaintCircle.setCurrentColor(-1).setTargetColor(-1);
        cancelAllAnimation();
        this.mFocusingAnimator = ValueAnimator.ofFloat(0.548f, 0.576f);
        this.mFocusingAnimator.setInterpolator(new SineEaseInOutInterpolator());
        this.mFocusingAnimator.setDuration(600L);
        this.mFocusingAnimator.setRepeatMode(2);
        this.mFocusingAnimator.setRepeatCount(-1);
        this.mFocusingAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            /* class com.android.camera.ui.drawable.lighting.LightingAnimateDrawable.AnonymousClass7 */

            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                float floatValue = ((Float) valueAnimator.getAnimatedValue()).floatValue();
                LightingAnimateDrawable.this.mPaintMask.mCurrentWidthPercent = floatValue;
                LightingAnimateDrawable.this.mPaintCircle.mCurrentWidthPercent = floatValue;
                LightingAnimateDrawable.this.invalidateSelf();
            }
        });
        this.mFocusingAnimator.addListener(new Animator.AnimatorListener() {
            /* class com.android.camera.ui.drawable.lighting.LightingAnimateDrawable.AnonymousClass8 */

            public void onAnimationCancel(Animator animator) {
            }

            public void onAnimationEnd(Animator animator) {
            }

            public void onAnimationRepeat(Animator animator) {
                if (0.576f - LightingAnimateDrawable.this.mPaintCircle.mCurrentWidthPercent <= 0.02f && LightingAnimateDrawable.this.pendingState == 2) {
                    LightingAnimateDrawable.this.animateSuccess();
                }
            }

            public void onAnimationStart(Animator animator) {
                LightingAnimateDrawable.this.mAnimateFocusDoing = false;
            }
        });
        this.mFocusingAnimator.start();
    }

    private void animateMiddleChange(int i) {
        cancelAllAnimation();
        if (i == 90 || i == 270) {
            this.mPaintMask.setTargetMiddle(this.mMiddleX, this.mMiddleHorizontalY, (((((float) Math.min(this.mWidth, this.mHeight)) * this.mCircleRatio) * 1.33f) / 2.0f) / 1.15f);
            this.mPaintCircle.setTargetMiddle(this.mMiddleX, this.mMiddleHorizontalY, (((((float) Math.min(this.mWidth, this.mHeight)) * this.mCircleRatio) * 1.33f) / 2.0f) / 1.15f);
        } else {
            this.mPaintMask.setTargetMiddle(this.mMiddleX, this.mMiddleVerticalY, ((((float) Math.min(this.mWidth, this.mHeight)) * this.mCircleRatio) * 1.33f) / 2.0f);
            this.mPaintCircle.setTargetMiddle(this.mMiddleX, this.mMiddleVerticalY, ((((float) Math.min(this.mWidth, this.mHeight)) * this.mCircleRatio) * 1.33f) / 2.0f);
        }
        this.mUpdateDegreeAnimator = ValueAnimator.ofFloat(0.0f, 1.0f);
        this.mUpdateDegreeAnimator.setDuration(300L);
        this.mUpdateDegreeAnimator.setInterpolator(new PathInterpolator(0.25f, 0.1f, 0.25f, 1.0f));
        this.mUpdateDegreeAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            /* class com.android.camera.ui.drawable.lighting.LightingAnimateDrawable.AnonymousClass1 */

            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                float floatValue = ((Float) valueAnimator.getAnimatedValue()).floatValue();
                LightingAnimateDrawable.this.mPaintMask.updateValue(floatValue);
                LightingAnimateDrawable.this.mPaintCircle.updateValue(floatValue);
                LightingAnimateDrawable.this.invalidateSelf();
            }
        });
        this.mUpdateDegreeAnimator.addListener(new Animator.AnimatorListener() {
            /* class com.android.camera.ui.drawable.lighting.LightingAnimateDrawable.AnonymousClass2 */

            public void onAnimationCancel(Animator animator) {
            }

            public void onAnimationEnd(Animator animator) {
                if (LightingAnimateDrawable.this.isShown && !LightingAnimateDrawable.this.mAnimateFocusDoing) {
                    LightingAnimateDrawable.this.animateFocusing();
                }
            }

            public void onAnimationRepeat(Animator animator) {
            }

            public void onAnimationStart(Animator animator) {
            }
        });
        this.mUpdateDegreeAnimator.start();
    }

    private void cancelAllAnimation() {
        ValueAnimator valueAnimator = this.mStartAnimator;
        if (valueAnimator != null && valueAnimator.isRunning()) {
            this.mStartAnimator.cancel();
            this.mStartAnimator = null;
        }
        ValueAnimator valueAnimator2 = this.mAlphaAnimator;
        if (valueAnimator2 != null && valueAnimator2.isRunning()) {
            this.mAlphaAnimator.cancel();
            this.mAlphaAnimator = null;
        }
        ValueAnimator valueAnimator3 = this.mFocusingAnimator;
        if (valueAnimator3 != null && valueAnimator3.isRunning()) {
            this.mFocusingAnimator.cancel();
            this.mFocusingAnimator = null;
        }
        ValueAnimator valueAnimator4 = this.mExitAnimator;
        if (valueAnimator4 != null && valueAnimator4.isRunning()) {
            this.mExitAnimator.cancel();
            this.mExitAnimator = null;
        }
        ValueAnimator valueAnimator5 = this.mUpdateDegreeAnimator;
        if (valueAnimator5 != null && valueAnimator5.isRunning()) {
            this.mUpdateDegreeAnimator.cancel();
            this.mUpdateDegreeAnimator = null;
        }
    }

    public void animateAlphaIn() {
        this.mPaintCircle.setVisible(0);
        this.mPaintCircle.setCurrentWidthPercent(0.548f).setTargetWidthPercent(0.548f);
        this.mPaintCircle.setCurrentColor(-1).setTargetColor(-1);
        this.mPaintCircle.setCurrentAlpha(0).setTargetAlpha(255);
        this.mAlphaAnimator = ValueAnimator.ofFloat(0.0f, 1.0f);
        this.mAlphaAnimator.setDuration(200L);
        this.mAlphaAnimator.setInterpolator(new CubicEaseInOutInterpolator());
        this.mAlphaAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            /* class com.android.camera.ui.drawable.lighting.LightingAnimateDrawable.AnonymousClass5 */

            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                LightingAnimateDrawable.this.mPaintCircle.updateValue(((Float) valueAnimator.getAnimatedValue()).floatValue());
                LightingAnimateDrawable.this.invalidateSelf();
            }
        });
        this.mAlphaAnimator.addListener(new Animator.AnimatorListener() {
            /* class com.android.camera.ui.drawable.lighting.LightingAnimateDrawable.AnonymousClass6 */

            public void onAnimationCancel(Animator animator) {
            }

            public void onAnimationEnd(Animator animator) {
                LightingAnimateDrawable.this.triggerFocusing();
            }

            public void onAnimationRepeat(Animator animator) {
            }

            public void onAnimationStart(Animator animator) {
            }
        });
        this.mAlphaAnimator.start();
    }

    public void animateExit() {
        cancelAllAnimation();
        this.mPaintCircle.setVisible(8);
        this.mPaintMask.setTargetWidthPercent(1.5f);
        this.mPaintMask.setTargetAlpha(114);
        this.mExitAnimator = ValueAnimator.ofFloat(0.0f, 1.0f);
        this.mExitAnimator.setDuration(200L);
        this.mExitAnimator.setInterpolator(new PathInterpolator(0.25f, 0.1f, 0.25f, 1.0f));
        this.mExitAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            /* class com.android.camera.ui.drawable.lighting.LightingAnimateDrawable.AnonymousClass10 */

            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                LightingAnimateDrawable.this.mPaintMask.updateValue(((Float) valueAnimator.getAnimatedValue()).floatValue());
                LightingAnimateDrawable.this.invalidateSelf();
            }
        });
        this.mExitAnimator.addListener(new Animator.AnimatorListener() {
            /* class com.android.camera.ui.drawable.lighting.LightingAnimateDrawable.AnonymousClass11 */

            public void onAnimationCancel(Animator animator) {
            }

            public void onAnimationEnd(Animator animator) {
                LightingAnimateDrawable.this.isShown = false;
            }

            public void onAnimationRepeat(Animator animator) {
            }

            public void onAnimationStart(Animator animator) {
            }
        });
        this.mExitAnimator.start();
    }

    public void animateStart() {
        cancelAllAnimation();
        this.isShown = true;
        this.mPaintCircle.setVisible(8);
        this.mPaintMask.setCurrentWidthPercent(1.0f).setTargetWidthPercent(0.548f);
        this.mPaintMask.setTargetAlpha(114);
        this.mStartAnimator = ValueAnimator.ofFloat(0.0f, 1.0f);
        this.mStartAnimator.setDuration(380L);
        this.mStartAnimator.setInterpolator(new PathInterpolator(0.25f, 0.1f, 0.25f, 1.0f));
        this.mStartAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            /* class com.android.camera.ui.drawable.lighting.LightingAnimateDrawable.AnonymousClass3 */

            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                LightingAnimateDrawable.this.mPaintMask.updateValue(((Float) valueAnimator.getAnimatedValue()).floatValue());
                LightingAnimateDrawable.this.invalidateSelf();
            }
        });
        this.mStartAnimator.addListener(new Animator.AnimatorListener() {
            /* class com.android.camera.ui.drawable.lighting.LightingAnimateDrawable.AnonymousClass4 */

            public void onAnimationCancel(Animator animator) {
            }

            public void onAnimationEnd(Animator animator) {
                LightingAnimateDrawable.this.animateAlphaIn();
            }

            public void onAnimationRepeat(Animator animator) {
            }

            public void onAnimationStart(Animator animator) {
            }
        });
        this.mStartAnimator.start();
    }

    public void animateSuccess() {
        cancelAllAnimation();
        this.mPaintCircle.setCurrentColor(-1).setTargetColor(TintColor.tintColor());
        this.mPaintCircle.setCurrentWidthPercent(0.576f).setTargetWidthPercent(0.548f);
        this.mPaintMask.setCurrentWidthPercent(0.576f).setTargetWidthPercent(0.548f);
        ValueAnimator ofFloat = ValueAnimator.ofFloat(0.0f, 1.0f);
        ofFloat.setInterpolator(new CubicEaseInOutInterpolator());
        ofFloat.setDuration(250L);
        ofFloat.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            /* class com.android.camera.ui.drawable.lighting.LightingAnimateDrawable.AnonymousClass9 */

            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                float floatValue = ((Float) valueAnimator.getAnimatedValue()).floatValue();
                LightingAnimateDrawable.this.mPaintMask.updateValue(floatValue);
                LightingAnimateDrawable.this.mPaintCircle.updateValue(floatValue);
                LightingAnimateDrawable.this.invalidateSelf();
            }
        });
        ofFloat.start();
    }

    public void clear() {
        this.isShown = false;
        cancelAllAnimation();
        invalidateSelf();
    }

    public void draw(@NonNull Canvas canvas) {
        if (this.isShown && canvas != null) {
            int saveLayer = canvas.saveLayer(0.0f, 0.0f, (float) this.mWidth, (float) this.mHeight, null, 31);
            canvas.save();
            this.mPaintMask.drawCanvas(canvas);
            canvas.restore();
            canvas.save();
            this.mPaintCircle.drawCanvas(canvas);
            canvas.restore();
            canvas.restoreToCount(saveLayer);
        }
    }

    public int getOpacity() {
        return -1;
    }

    public void setAlpha(int i) {
    }

    public void setCircleHeightRatio(float f) {
        this.mCircleHeightRatio = f;
    }

    public void setCircleRatio(float f) {
        this.mCircleRatio = f;
    }

    public void setColorFilter(@Nullable ColorFilter colorFilter) {
    }

    public void setWidthHeight(int i, int i2, int i3) {
        this.mWidth = i;
        this.mHeight = i2;
        float f = (float) i;
        this.mMiddleX = f / 2.0f;
        this.mMiddleVerticalY = (((f * 1.33f) / 2.0f) * 0.8f) + ((float) Util.dpToPixel(24.0f));
        if (DataRepository.dataItemGlobal().getDisplayMode() == 1) {
            this.mMiddleHorizontalY = (((float) Math.min(this.mWidth, this.mHeight)) * 1.33f) / 2.0f;
        } else {
            this.mMiddleHorizontalY = (((float) Math.min(this.mWidth, this.mHeight)) * 1.77f) / 2.0f;
        }
        if (CameraSettings.isGifOn()) {
            this.mMiddleHorizontalY = ((float) Math.min(this.mWidth, this.mHeight)) / 2.0f;
            this.mMiddleVerticalY = ((float) Math.min(this.mWidth, this.mHeight)) / 2.0f;
        } else {
            float f2 = this.mMiddleVerticalY;
            float f3 = this.mCircleHeightRatio;
            this.mMiddleVerticalY = f2 * f3;
            this.mMiddleHorizontalY *= f3;
        }
        if (DataRepository.dataItemRunning().getUiStyle() == 3) {
            float f4 = this.mMiddleVerticalY;
            int i4 = this.mTopMargin;
            this.mMiddleVerticalY = f4 + ((float) i4);
            this.mMiddleHorizontalY += (float) i4;
        }
        this.mPaintMask.setData(this.mWidth, this.mHeight);
        updateMiddleValue(i3, false);
    }

    public void triggerFocusing() {
        if (this.isShown && !this.mAnimateFocusDoing) {
            animateFocusing();
        }
    }

    public void triggerSuccess() {
        this.pendingState = 2;
    }

    public void updateMiddleValue(int i, boolean z) {
        if (this.isShown && z) {
            animateMiddleChange(i);
        } else if (i == 90 || i == 270) {
            this.mPaintMask.setMiddle(this.mMiddleX, this.mMiddleHorizontalY, (((((float) Math.min(this.mWidth, this.mHeight)) * this.mCircleRatio) * 1.33f) / 2.0f) / 1.15f);
            this.mPaintCircle.setMiddle(this.mMiddleX, this.mMiddleHorizontalY, (((((float) Math.min(this.mWidth, this.mHeight)) * this.mCircleRatio) * 1.33f) / 2.0f) / 1.15f);
        } else {
            this.mPaintMask.setMiddle(this.mMiddleX, this.mMiddleVerticalY, ((((float) Math.min(this.mWidth, this.mHeight)) * this.mCircleRatio) * 1.33f) / 2.0f);
            this.mPaintCircle.setMiddle(this.mMiddleX, this.mMiddleVerticalY, ((((float) Math.min(this.mWidth, this.mHeight)) * this.mCircleRatio) * 1.33f) / 2.0f);
        }
    }
}
