package com.android.camera.ui.drawable.snap;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.text.TextUtils;
import com.android.camera.R;
import com.android.camera.ui.drawable.CameraPaintBase;

public class CameraSnapPaintCenterVV extends CameraPaintBase {
    private static final float RECORDING_ROUND_WIDTH = 0.265f;
    private float mCurrentRoundRectRadius;
    private String mDurationText;
    private RectF mRectF;
    private float mRoundingProgress = 1.0f;
    private Rect mTextBound;
    private Paint mTextPaint;

    public CameraSnapPaintCenterVV(Context context) {
        super(context);
        TypedArray obtainStyledAttributes = context.obtainStyledAttributes(R.style.SettingStatusBarText, new int[]{16842901, 16842904});
        int color = obtainStyledAttributes.getColor(obtainStyledAttributes.getIndex(1), -1);
        obtainStyledAttributes.getDimensionPixelSize(obtainStyledAttributes.getIndex(0), 0);
        obtainStyledAttributes.recycle();
        this.mTextPaint = new Paint();
        this.mTextPaint.setColor(color);
        this.mTextPaint.setStyle(Paint.Style.FILL);
        this.mTextPaint.setTextSize((float) context.getResources().getDimensionPixelSize(R.dimen.vv_progress_center_text));
        this.mTextPaint.setTextAlign(Paint.Align.LEFT);
        this.mTextPaint.setAntiAlias(true);
        this.mTextPaint.setAlpha(255);
        this.mTextBound = new Rect();
    }

    /* access modifiers changed from: protected */
    public void draw(Canvas canvas) {
        float f = this.mBaseRadius * this.mCurrentRoundRectRadius;
        float f2 = this.mMiddleY - f;
        this.mRectF.set(this.mMiddleX - f, f2, this.mMiddleX + f, this.mMiddleY + f);
        this.mPaint.setColor(-1417668);
        if (!this.isRecording) {
            canvas.drawRoundRect(this.mRectF, this.mRoundingProgress * f, f * this.mRoundingProgress, this.mPaint);
        }
        if (!TextUtils.isEmpty(this.mDurationText)) {
            this.mTextPaint.getTextBounds(this.mDurationText, 0, this.mDurationText.length(), this.mTextBound);
            canvas.drawText(this.mDurationText, this.mMiddleX - ((float) (this.mTextBound.width() / 2)), this.mMiddleY + ((float) (this.mTextBound.height() / 2)), this.mTextPaint);
        }
    }

    /* access modifiers changed from: protected */
    public void initPaint(Context context) {
        this.mPaint.setAntiAlias(true);
        this.mPaint.setStyle(Paint.Style.FILL);
        this.mRectF = new RectF();
    }

    public void prepareRecord() {
        this.mCurrentRoundRectRadius = this.mCurrentWidthPercent;
        this.mRoundingProgress = 1.0f;
    }

    public void resetRecordingState() {
        super.resetRecordingState();
        this.mDurationText = null;
    }

    public void setDurationText(String str) {
        this.mDurationText = str;
    }

    public void updateRecordValue(float f, boolean z) {
        if (z) {
            this.mRoundingProgress = 1.0f - (0.65f * f);
            this.mCurrentRoundRectRadius = this.mCurrentWidthPercent - ((this.mCurrentWidthPercent - RECORDING_ROUND_WIDTH) * f);
            return;
        }
        this.mRoundingProgress = 0.35f + (0.65f * f);
        this.mCurrentRoundRectRadius = RECORDING_ROUND_WIDTH + ((this.mCurrentWidthPercent - RECORDING_ROUND_WIDTH) * f);
    }
}
