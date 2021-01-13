package com.android.camera.ui.drawable.snap;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import com.android.camera.ui.drawable.CameraPaintBase;

@Deprecated
public class CameraSnapPaintMotion extends CameraPaintBase {
    private boolean mIsOutstandingRound = false;
    private float mLastAngle = 0.0f;

    public CameraSnapPaintMotion(Context context) {
        super(context);
    }

    /* access modifiers changed from: protected */
    @Override // com.android.camera.ui.drawable.CameraPaintBase
    public void draw(Canvas canvas) {
        float f = this.mBaseRadius * this.mCurrentWidthPercent;
        if (this.timeAngle - this.mLastAngle < 0.0f) {
            this.mIsOutstandingRound = !this.mIsOutstandingRound;
        }
        for (int i = 0; i < 40; i++) {
            canvas.save();
            float f2 = (float) (i * 9);
            canvas.rotate(f2, this.mMiddleX, this.mMiddleY);
            int i2 = this.mCurrentAlpha;
            int i3 = 19;
            if (this.isRecording) {
                if (f2 == 0.0f && this.needZero) {
                    this.mPaint.setAlpha(CameraPaintBase.ALPHA_OUTSTANDING);
                } else if (f2 < this.timeAngle) {
                    this.mPaint.setAlpha(this.mIsOutstandingRound ? CameraPaintBase.ALPHA_OUTSTANDING : CameraPaintBase.ALPHA_HINT);
                } else {
                    this.mPaint.setAlpha(this.mIsOutstandingRound ? CameraPaintBase.ALPHA_HINT : CameraPaintBase.ALPHA_OUTSTANDING);
                }
                if (f2 % 90.0f == 0.0f) {
                    float f3 = this.mMiddleX;
                    float f4 = this.mMiddleY;
                    canvas.drawLine(f3, f4 - f, f3, (f4 - f) + ((float) i3), this.mPaint);
                    this.mPaint.setAlpha(i2);
                    canvas.restore();
                }
            } else if (f2 % 90.0f == 0.0f) {
                float f32 = this.mMiddleX;
                float f42 = this.mMiddleY;
                canvas.drawLine(f32, f42 - f, f32, (f42 - f) + ((float) i3), this.mPaint);
                this.mPaint.setAlpha(i2);
                canvas.restore();
            }
            i3 = 12;
            float f322 = this.mMiddleX;
            float f422 = this.mMiddleY;
            canvas.drawLine(f322, f422 - f, f322, (f422 - f) + ((float) i3), this.mPaint);
            this.mPaint.setAlpha(i2);
            canvas.restore();
        }
        float f5 = this.timeAngle;
        this.mLastAngle = f5;
        if (this.isRecording) {
            canvas.rotate(f5, this.mMiddleX, this.mMiddleY);
            int i4 = this.mCurrentAlpha;
            this.mPaint.setAlpha(CameraPaintBase.ALPHA_OUTSTANDING);
            float f6 = this.mMiddleX;
            float f7 = this.mMiddleY;
            canvas.drawLine(f6, f7 - f, f6, (f7 - f) + 19.0f, this.mPaint);
            this.mPaint.setAlpha(i4);
        }
    }

    /* access modifiers changed from: protected */
    @Override // com.android.camera.ui.drawable.CameraPaintBase
    public void initPaint(Context context) {
        this.mPaint.setAntiAlias(true);
        this.mPaint.setStyle(Paint.Style.STROKE);
        this.mPaint.setStrokeWidth(3.0f);
    }
}
