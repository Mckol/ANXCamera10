package com.android.camera.ui;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ArgbEvaluator;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Rect;
import android.graphics.drawable.ColorDrawable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import com.android.camera.R;
import com.android.camera.Util;
import com.android.camera.data.data.runing.ComponentRunningDocument;

public class AdjustAnimationView extends View {
    private static final ArgbEvaluator ARGB_EVALUATOR = new ArgbEvaluator();
    private static final int POINTS_COUNT = 8;
    private static final String TAG = "AdjustAnimationView";
    private Bitmap mBmp;
    private float[] mBmpPoints = new float[8];
    private float[] mCurrentPoints = new float[8];
    private float[] mEndPoints = new float[8];
    private int mEndTopMargin;
    private Matrix mMatrix = new Matrix();
    private float[] mStartPoints = new float[8];

    public AdjustAnimationView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        Rect displayRect = Util.getDisplayRect(0);
        this.mEndTopMargin = displayRect.top + Math.round(((float) (displayRect.height() - getResources().getDimensionPixelOffset(R.dimen.document_preview_max_height))) / 2.0f);
    }

    private void initBmpPoints() {
        float[] fArr = this.mBmpPoints;
        fArr[0] = 0.0f;
        fArr[1] = 0.0f;
        fArr[2] = (float) this.mBmp.getWidth();
        float[] fArr2 = this.mBmpPoints;
        fArr2[3] = 0.0f;
        fArr2[4] = (float) this.mBmp.getWidth();
        this.mBmpPoints[5] = (float) this.mBmp.getHeight();
        float[] fArr3 = this.mBmpPoints;
        fArr3[6] = 0.0f;
        fArr3[7] = (float) this.mBmp.getHeight();
    }

    private void initEndPoints(int i, int i2, int i3, int i4) {
        float f;
        float f2;
        int i5 = (Util.sWindowWidth - i) / 2;
        int i6 = this.mEndTopMargin;
        float f3 = (float) i;
        float f4 = (float) i2;
        float f5 = (float) i3;
        float f6 = (float) i4;
        if (f3 / f4 > f5 / f6) {
            f2 = (f5 * f4) / f6;
            float[] fArr = this.mEndPoints;
            float f7 = ((float) i5) + ((f3 - f2) / 2.0f);
            fArr[0] = f7;
            float f8 = (float) i6;
            fArr[1] = f8;
            float f9 = f7 + f2;
            fArr[2] = f9;
            fArr[3] = f8;
            fArr[4] = f9;
            float f10 = (float) (i6 + i2);
            fArr[5] = f10;
            fArr[6] = f7;
            fArr[7] = f10;
            f = f4;
        } else {
            f = (f6 * f3) / f5;
            float[] fArr2 = this.mEndPoints;
            float f11 = (float) i5;
            fArr2[0] = f11;
            float f12 = ((float) i6) + ((f4 - f) / 2.0f);
            fArr2[1] = f12;
            float f13 = (float) (i5 + i);
            fArr2[2] = f13;
            fArr2[3] = f12;
            fArr2[4] = f13;
            float f14 = f12 + f;
            fArr2[5] = f14;
            fArr2[6] = f11;
            fArr2[7] = f14;
            f2 = f3;
        }
        String str = TAG;
        Log.d(str, "endWidth:" + f2 + ", endHeight:" + f);
    }

    private void updateMatrix() {
        this.mMatrix.setPolyToPoly(this.mBmpPoints, 0, this.mCurrentPoints, 0, 4);
    }

    public /* synthetic */ void c(ValueAnimator valueAnimator) {
        for (int i = 0; i < 8; i++) {
            this.mCurrentPoints[i] = ((Float) valueAnimator.getAnimatedValue("point_" + i)).floatValue();
        }
        updateMatrix();
        invalidate();
    }

    public void clearBitmap() {
        this.mBmp = null;
        postInvalidate();
    }

    public Rect getImageRect() {
        Rect rect = new Rect(Math.round(this.mEndPoints[0]), Math.round(this.mEndPoints[1]), Math.round(this.mEndPoints[4]), Math.round(this.mEndPoints[5]));
        String str = TAG;
        Log.d(str, "getImageRect: " + rect);
        return rect;
    }

    /* access modifiers changed from: protected */
    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Bitmap bitmap = this.mBmp;
        if (bitmap == null || bitmap.isRecycled()) {
            canvas.drawColor(0);
        } else {
            canvas.drawBitmap(this.mBmp, this.mMatrix, null);
        }
    }

    public void setBitmap(Bitmap bitmap, float[] fArr) {
        Bitmap bitmap2 = this.mBmp;
        if (bitmap2 != null && !bitmap2.isRecycled()) {
            this.mBmp.recycle();
        }
        this.mBmp = bitmap;
        Rect displayRect = Util.getDisplayRect();
        for (int i = 0; i < 8; i++) {
            if (i % 2 == 0) {
                this.mStartPoints[i] = fArr[i];
            } else {
                this.mStartPoints[i] = fArr[i] + ((float) displayRect.top);
            }
        }
        System.arraycopy(this.mStartPoints, 0, this.mCurrentPoints, 0, 8);
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        int dimensionPixelOffset = getResources().getDimensionPixelOffset(R.dimen.document_preview_max_width);
        int dimensionPixelOffset2 = getResources().getDimensionPixelOffset(R.dimen.document_preview_max_height);
        Log.d(TAG, "width:" + dimensionPixelOffset + ", height:" + dimensionPixelOffset2 + ", bmpWidth:" + width + ", bmpHeight:" + height);
        initBmpPoints();
        initEndPoints(dimensionPixelOffset, dimensionPixelOffset2, width, height);
        updateMatrix();
    }

    public void startAnim(Animator.AnimatorListener animatorListener, long j) {
        AnimatorSet animatorSet = new AnimatorSet();
        ColorDrawable colorDrawable = new ColorDrawable(0);
        setBackground(colorDrawable);
        ObjectAnimator ofObject = ObjectAnimator.ofObject(colorDrawable, ComponentRunningDocument.DOCUMENT_STRENGTHEN, ARGB_EVALUATOR, 1912602624);
        ofObject.setDuration(Util.getEnterDuration() / 2);
        ValueAnimator valueAnimator = new ValueAnimator();
        PropertyValuesHolder[] propertyValuesHolderArr = new PropertyValuesHolder[8];
        for (int i = 0; i < 8; i++) {
            propertyValuesHolderArr[i] = PropertyValuesHolder.ofFloat("point_" + i, this.mStartPoints[i], this.mEndPoints[i]);
        }
        valueAnimator.setValues(propertyValuesHolderArr);
        valueAnimator.addUpdateListener(new a(this));
        valueAnimator.setDuration(j);
        if (animatorListener != null) {
            valueAnimator.addListener(animatorListener);
        }
        animatorSet.playTogether(ofObject, valueAnimator);
        animatorSet.start();
    }

    public void startBackgroundAnimator() {
        ObjectAnimator ofObject = ObjectAnimator.ofObject((ColorDrawable) getBackground(), ComponentRunningDocument.DOCUMENT_STRENGTHEN, ARGB_EVALUATOR, 0);
        ofObject.setDuration(Util.getExitDuration());
        ofObject.start();
    }
}
