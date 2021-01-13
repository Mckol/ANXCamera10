package com.android.camera.ui.drawable.snap;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import com.android.camera.ui.drawable.CameraPaintBase;

public class CameraSnapPaintText extends CameraPaintBase {
    private static final float RECORDING_ROUND_WIDTH = 0.265f;
    public boolean isRecordingCircle;
    public boolean isRoundingCircle;
    private float mCurrentRoundRectRadius;
    private RectF mRectF;
    private float mRoundingProgress = 1.0f;

    public CameraSnapPaintText(Context context) {
        super(context);
    }

    /* access modifiers changed from: protected */
    @Override // com.android.camera.ui.drawable.CameraPaintBase
    public void draw(Canvas canvas) {
        if (!this.isRecording) {
            canvas.drawCircle(this.mMiddleX, this.mMiddleY, this.mBaseRadius * this.mCurrentWidthPercent, this.mPaint);
        } else if (this.isRecordingCircle) {
            canvas.drawCircle(this.mMiddleX, this.mMiddleY, this.mBaseRadius * 0.55f, this.mPaint);
        } else if (this.isRoundingCircle) {
            canvas.drawCircle(this.mMiddleX, this.mMiddleY, this.mBaseRadius * this.mCurrentWidthPercent * this.mRoundingProgress, this.mPaint);
        } else {
            float f = this.mBaseRadius * this.mCurrentRoundRectRadius;
            float f2 = this.mMiddleX;
            float f3 = this.mMiddleY;
            RectF rectF = this.mRectF;
            rectF.set(f2 - f, f3 - f, f2 + f, f3 + f);
            RectF rectF2 = this.mRectF;
            float f4 = this.mRoundingProgress;
            canvas.drawRoundRect(rectF2, f * f4, f * f4, this.mPaint);
        }
    }

    /* access modifiers changed from: protected */
    @Override // com.android.camera.ui.drawable.CameraPaintBase
    public void initPaint(Context context) {
        this.mPaint.setAntiAlias(true);
        this.mPaint.setStyle(Paint.Style.FILL);
        this.mRectF = new RectF();
    }

    public void prepareRecord() {
        this.mCurrentRoundRectRadius = this.mCurrentWidthPercent;
        this.mRoundingProgress = 1.0f;
    }

    @Override // com.android.camera.ui.drawable.CameraPaintBase
    public void resetRecordingState() {
        super.resetRecordingState();
        this.isRecordingCircle = false;
        this.isRoundingCircle = false;
    }

    public void updateRecordValue(float f, boolean z) {
        if (z) {
            if (this.isRoundingCircle) {
                this.mRoundingProgress = 1.0f - (0.45f * f);
            } else {
                this.mRoundingProgress = 1.0f - (0.65f * f);
            }
            float f2 = this.mCurrentWidthPercent;
            this.mCurrentRoundRectRadius = f2 - ((f2 - RECORDING_ROUND_WIDTH) * f);
            return;
        }
        if (this.isRoundingCircle) {
            this.mRoundingProgress = (0.45f * f) + 0.55f;
        } else {
            this.mRoundingProgress = (0.65f * f) + 0.35f;
        }
        this.mCurrentRoundRectRadius = ((this.mCurrentWidthPercent - RECORDING_ROUND_WIDTH) * f) + RECORDING_ROUND_WIDTH;
    }
}
