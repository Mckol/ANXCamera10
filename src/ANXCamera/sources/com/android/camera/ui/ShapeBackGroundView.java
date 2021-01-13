package com.android.camera.ui;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.widget.FrameLayout;
import androidx.core.view.ViewCompat;
import com.android.camera.Util;
import miui.view.animation.CubicEaseOutInterpolator;

public class ShapeBackGroundView extends FrameLayout {
    public static final int SHAPE_HINT_ALPHA = 153;
    private int mBlackMaskHeight;
    private Paint mBlackMaskPaint;
    private int mBlackOriginHeight;
    private int mCurrentAlpha;
    private int mCurrentHeight;
    private int mCurrentRadius;
    public int mGravity;
    private Paint mPaint;
    private int mTotalHeight;
    private int mTotalWidth;
    private ValueAnimator mValueAnimator;

    public ShapeBackGroundView(Context context) {
        this(context, null, -1, -1);
    }

    public ShapeBackGroundView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, -1, -1);
        init(context);
    }

    public ShapeBackGroundView(Context context, AttributeSet attributeSet, int i) {
        this(context, attributeSet, i, -1);
        init(context);
    }

    public ShapeBackGroundView(Context context, AttributeSet attributeSet, int i, int i2) {
        super(context, attributeSet, i, i2);
        this.mGravity = 48;
        init(context);
    }

    private void cancelAnimation() {
        ValueAnimator valueAnimator = this.mValueAnimator;
        if (valueAnimator != null && valueAnimator.isRunning()) {
            this.mValueAnimator.cancel();
            this.mValueAnimator = null;
        }
    }

    private void init(Context context) {
        this.mBlackMaskHeight = 0;
        setWillNotDraw(false);
        this.mTotalWidth = Util.sWindowWidth;
        this.mPaint = new Paint(1);
        this.mPaint.setStyle(Paint.Style.FILL);
        this.mPaint.setColor(ViewCompat.MEASURED_STATE_MASK);
        this.mBlackMaskPaint = new Paint(1);
        this.mBlackMaskPaint.setStyle(Paint.Style.FILL);
        this.mBlackMaskPaint.setColor(ViewCompat.MEASURED_STATE_MASK);
    }

    public boolean animationRunning() {
        ValueAnimator valueAnimator = this.mValueAnimator;
        return valueAnimator != null && valueAnimator.isRunning();
    }

    public int getBlackOriginHeight() {
        return this.mBlackOriginHeight;
    }

    public int getCurrentHeight() {
        return this.mCurrentHeight;
    }

    public int getCurrentMaskHeight() {
        return this.mBlackMaskHeight;
    }

    public void initHeight(int i) {
        this.mCurrentHeight = i;
        this.mTotalHeight = i;
    }

    public boolean maskVisible() {
        return this.mBlackMaskHeight != 0;
    }

    /* access modifiers changed from: protected */
    public void onDraw(Canvas canvas) {
        int i = this.mGravity;
        if (i == 48) {
            int i2 = this.mCurrentRadius;
            canvas.drawRoundRect(0.0f, (float) (-i2), (float) this.mTotalWidth, (float) this.mCurrentHeight, (float) i2, (float) i2, this.mPaint);
            int i3 = this.mCurrentRadius;
            canvas.drawRoundRect(0.0f, (float) (-i3), (float) this.mTotalWidth, (float) this.mBlackMaskHeight, (float) i3, (float) i3, this.mBlackMaskPaint);
        } else if (i == 80) {
            int i4 = this.mTotalHeight;
            float f = (float) i4;
            int i5 = this.mCurrentRadius;
            canvas.drawRoundRect(0.0f, (float) (i4 - this.mBlackMaskHeight), (float) this.mTotalWidth, f, (float) i5, (float) i5, this.mBlackMaskPaint);
        }
        super.onDraw(canvas);
    }

    public void setBackgroundAlpha(int i) {
        this.mPaint.setAlpha(i);
    }

    public void setBackgroundAlphaAndRadius(int i, int i2, int i3, int i4) {
        if (i != 255) {
            this.mPaint.setAlpha(i);
        }
        this.mCurrentRadius = i2;
        this.mCurrentHeight = i3;
        this.mBlackMaskHeight = i4;
        invalidate();
    }

    public void setBlackOriginHeight(int i) {
        this.mBlackOriginHeight = i;
    }

    public void setCurrentHeight(int i) {
        this.mCurrentHeight = i;
    }

    public void setCurrentRadius(int i) {
        this.mCurrentRadius = i;
    }

    public void setGravity(int i) {
        this.mGravity = i;
    }

    public void setMaskSpecificHeight(final int i, boolean z) {
        cancelAnimation();
        final int i2 = this.mBlackMaskHeight;
        if (i2 != i) {
            if (!z) {
                this.mBlackMaskHeight = i;
                invalidate();
                return;
            }
            this.mValueAnimator = ValueAnimator.ofFloat(0.0f, 1.0f);
            this.mValueAnimator.setDuration(300L);
            this.mValueAnimator.setInterpolator(new CubicEaseOutInterpolator());
            this.mValueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                /* class com.android.camera.ui.ShapeBackGroundView.AnonymousClass2 */

                public void onAnimationUpdate(ValueAnimator valueAnimator) {
                    float floatValue = ((Float) valueAnimator.getAnimatedValue()).floatValue();
                    ShapeBackGroundView shapeBackGroundView = ShapeBackGroundView.this;
                    int i = i2;
                    shapeBackGroundView.mBlackMaskHeight = (int) (((float) i) + (((float) (i - i)) * floatValue));
                    ShapeBackGroundView.this.invalidate();
                }
            });
            this.mValueAnimator.start();
        }
    }

    public void startBackGroundAnimator(final int i, final int i2, final int i3, final int i4, final int i5, final int i6, int i7, Animator.AnimatorListener animatorListener) {
        cancelAnimation();
        this.mValueAnimator = ValueAnimator.ofFloat(0.0f, 1.0f);
        this.mValueAnimator.setDuration((long) i7);
        this.mValueAnimator.setInterpolator(new CubicEaseOutInterpolator());
        if (animatorListener != null) {
            this.mValueAnimator.addListener(animatorListener);
        }
        this.mValueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            /* class com.android.camera.ui.ShapeBackGroundView.AnonymousClass1 */

            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                float floatValue = ((Float) valueAnimator.getAnimatedValue()).floatValue();
                ShapeBackGroundView shapeBackGroundView = ShapeBackGroundView.this;
                int i = i3;
                shapeBackGroundView.mCurrentHeight = (int) (((float) i) + (((float) (i4 - i)) * floatValue));
                ShapeBackGroundView shapeBackGroundView2 = ShapeBackGroundView.this;
                int i2 = i5;
                shapeBackGroundView2.mCurrentRadius = (int) (((float) i2) + (((float) (i6 - i2)) * floatValue));
                int i3 = i2;
                if (i3 == 255) {
                    ShapeBackGroundView shapeBackGroundView3 = ShapeBackGroundView.this;
                    shapeBackGroundView3.mBlackMaskHeight = shapeBackGroundView3.mCurrentHeight;
                } else {
                    ShapeBackGroundView shapeBackGroundView4 = ShapeBackGroundView.this;
                    int i4 = i;
                    shapeBackGroundView4.mCurrentAlpha = (int) (((float) i4) + (((float) (i3 - i4)) * floatValue));
                    ShapeBackGroundView.this.mPaint.setAlpha(ShapeBackGroundView.this.mCurrentAlpha);
                    ShapeBackGroundView.this.mBlackMaskHeight = 0;
                }
                ShapeBackGroundView.this.invalidate();
            }
        });
        this.mValueAnimator.start();
    }
}
