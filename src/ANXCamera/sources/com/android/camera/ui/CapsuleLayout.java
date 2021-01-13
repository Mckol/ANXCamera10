package com.android.camera.ui;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.android.camera.R;
import miuix.view.animation.CubicEaseOutInterpolator;

public class CapsuleLayout extends FrameLayout {
    private static final String TAG = "CapsuleLayout";
    private int mOffset;
    private float mProgress;
    private ValueAnimator mShowAnimator;
    private View mTarget;
    private int mTargetId;

    public CapsuleLayout(@NonNull Context context) {
        this(context, null);
    }

    public CapsuleLayout(@NonNull Context context, @Nullable AttributeSet attributeSet) {
        this(context, attributeSet, -1);
    }

    public CapsuleLayout(@NonNull Context context, @Nullable AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.mProgress = 1.0f;
        TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R.styleable.CapsuleLayout);
        this.mTargetId = obtainStyledAttributes.getResourceId(0, -1);
        obtainStyledAttributes.recycle();
    }

    private void ensureTarget() {
        if (this.mTarget == null) {
            int i = this.mTargetId;
            if (i != -1) {
                this.mTarget = findViewById(i);
            } else {
                throw new IllegalArgumentException("invalid target Id");
            }
        }
        if (this.mTarget == null) {
            throw new IllegalArgumentException("fail to get target");
        }
    }

    /* access modifiers changed from: protected */
    public void onLayout(boolean z, int i, int i2, int i3, int i4) {
        this.mOffset = (int) ((((float) this.mTarget.getMeasuredWidth()) * this.mProgress) / 4.0f);
        View view = this.mTarget;
        view.layout((view.getMeasuredWidth() / 4) - this.mOffset, 0, ((this.mTarget.getMeasuredWidth() * 3) / 4) + this.mOffset, this.mTarget.getMeasuredHeight());
    }

    /* access modifiers changed from: protected */
    public void onMeasure(int i, int i2) {
        ensureTarget();
        int mode = View.MeasureSpec.getMode(i);
        int mode2 = View.MeasureSpec.getMode(i2);
        int size = View.MeasureSpec.getSize(i);
        int size2 = View.MeasureSpec.getSize(i2);
        measureChild(this.mTarget, i, i2);
        if (size > this.mTarget.getMeasuredWidth()) {
            size = this.mTarget.getMeasuredWidth();
        }
        if (size2 > this.mTarget.getMeasuredHeight()) {
            size2 = this.mTarget.getMeasuredHeight();
        }
        if (mode != 1073741824) {
            size = this.mTarget.getMeasuredWidth();
        }
        if (mode2 != 1073741824) {
            size2 = this.mTarget.getMeasuredHeight();
        }
        setMeasuredDimension(size, size2);
    }

    public void setTarget(@NonNull View view) {
        this.mTarget = view;
    }

    public void start() {
        if (this.mShowAnimator == null) {
            this.mShowAnimator = ValueAnimator.ofFloat(0.0f, 1.0f);
            this.mShowAnimator.setDuration(300L);
            this.mShowAnimator.setInterpolator(new CubicEaseOutInterpolator() {
                /* class com.android.camera.ui.CapsuleLayout.AnonymousClass1 */

                @Override // miuix.view.animation.CubicEaseOutInterpolator
                public float getInterpolation(float f) {
                    CapsuleLayout.this.mProgress = super.getInterpolation(f);
                    CapsuleLayout capsuleLayout = CapsuleLayout.this;
                    capsuleLayout.setAlpha(capsuleLayout.mProgress);
                    CapsuleLayout.this.invalidate();
                    CapsuleLayout.this.requestLayout();
                    return CapsuleLayout.this.mProgress;
                }
            });
            this.mShowAnimator.addListener(new AnimatorListenerAdapter() {
                /* class com.android.camera.ui.CapsuleLayout.AnonymousClass2 */

                public void onAnimationEnd(Animator animator) {
                    super.onAnimationEnd(animator);
                    CapsuleLayout.this.setAlpha(1.0f);
                }

                public void onAnimationStart(Animator animator) {
                    super.onAnimationStart(animator);
                    CapsuleLayout.this.setVisibility(0);
                }
            });
        }
        this.mShowAnimator.start();
    }
}
