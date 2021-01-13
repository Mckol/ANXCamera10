package com.android.camera.ui;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.util.Log;
import android.util.Pair;
import android.util.Size;
import android.view.View;
import androidx.annotation.Nullable;
import com.android.camera.R;
import com.android.camera.Util;
import com.android.camera.constant.ColorConstant;
import com.xiaomi.ocr.sdk.imgprocess.DocumentProcess;
import java.util.Arrays;

public class DocumentView extends View {
    private static final String TAG = "DocumentView";
    private static final int THRESHOLD = 12;
    private Size displaySize;
    private boolean isHidePath;
    AnimatorSet mFlickerAnimatorSet = new AnimatorSet();
    private Paint mLayerPaint = new Paint();
    private Paint mPaint;
    private Path mPath = new Path();

    public DocumentView(Context context) {
        super(context);
        initPaint();
    }

    public DocumentView(Context context, @Nullable AttributeSet attributeSet) {
        super(context, attributeSet);
        initPaint();
    }

    private void initPaint() {
        this.mPaint = new Paint();
        this.mPaint.setAntiAlias(true);
        this.mPaint.setStrokeJoin(Paint.Join.ROUND);
        this.mPaint.setStyle(Paint.Style.STROKE);
        this.mPaint.setStrokeWidth((float) getResources().getDimensionPixelOffset(R.dimen.document_preview_rect_width));
        this.mPaint.setColor(ColorConstant.COLOR_COMMON_SELECTED);
        this.mLayerPaint = new Paint();
        this.mLayerPaint.setStyle(Paint.Style.FILL);
        this.mLayerPaint.setColor(268422678);
    }

    public void clear() {
        this.mPath.reset();
        postInvalidate();
    }

    public void hideOrShowPath(boolean z) {
        this.isHidePath = !z;
        clear();
    }

    /* access modifiers changed from: protected */
    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawPath(this.mPath, this.mPaint);
        canvas.drawPath(this.mPath, this.mLayerPaint);
    }

    public void setDisplaySize(int i, int i2) {
        this.displaySize = new Size(i, i2);
    }

    public void updateDocument(Pair<DocumentProcess.QuadStatus, float[]> pair) {
        Object obj;
        if (pair == null || ((float[]) pair.second).length != 8 || this.isHidePath || (obj = pair.first) == DocumentProcess.QuadStatus.QUAD_NOSHOW) {
            clear();
            return;
        }
        if (obj != DocumentProcess.QuadStatus.QUAD_UPDATE) {
            this.mFlickerAnimatorSet.end();
        } else if (!this.mFlickerAnimatorSet.isRunning()) {
            this.mFlickerAnimatorSet.playSequentially(ObjectAnimator.ofPropertyValuesHolder(this, PropertyValuesHolder.ofFloat("alpha", 0.0f)), ObjectAnimator.ofPropertyValuesHolder(this, PropertyValuesHolder.ofFloat("alpha", 1.0f)));
            this.mFlickerAnimatorSet.setDuration(Util.getFlickerDuration());
            this.mFlickerAnimatorSet.start();
        }
        float[] fArr = (float[]) pair.second;
        Size size = this.displaySize;
        int measuredWidth = size == null ? getMeasuredWidth() : size.getWidth();
        Size size2 = this.displaySize;
        int measuredHeight = size2 == null ? getMeasuredHeight() : size2.getHeight();
        Log.d(TAG, "updateDocument: width = " + measuredWidth + ", height = " + measuredHeight + ", points = " + Arrays.toString(fArr));
        if (fArr[0] < 12.0f && fArr[1] < 12.0f) {
            float f = (float) measuredWidth;
            if (f - fArr[2] < 12.0f && fArr[3] < 12.0f && f - fArr[4] < 12.0f) {
                float f2 = (float) measuredHeight;
                if (f2 - fArr[5] < 12.0f && fArr[6] < 12.0f && f2 - fArr[7] < 12.0f) {
                    Log.d(TAG, "updateDocument: reset path");
                    this.mPath.reset();
                    postInvalidate();
                    return;
                }
            }
        }
        this.mPath.reset();
        this.mPath.moveTo(fArr[0], fArr[1]);
        int length = fArr.length / 2;
        for (int i = 1; i < length; i++) {
            int i2 = i * 2;
            this.mPath.lineTo(fArr[i2], fArr[i2 + 1]);
        }
        this.mPath.close();
        postInvalidate();
    }
}
