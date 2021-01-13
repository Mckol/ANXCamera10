package com.android.camera.ui.drawable.snap;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import androidx.annotation.ColorInt;
import androidx.annotation.DrawableRes;
import com.android.camera.ui.drawable.CameraPaintBase;

public class CameraSnapPaintRound extends CameraPaintBase {
    private boolean isDown;
    public boolean isRecordingCircle;
    public boolean isRoundingCircle;
    private float mBaseRoundRectRadius;
    private float mCurrentRoundRectRadius = 0.65f;
    private PaintPattern mExternalPattern;
    private boolean mRapidly;
    private Paint mRecordingBgPaint;
    private RectF mRectF;
    private float mRoundingProgress = 1.0f;
    private boolean mShowCenterBitmap;
    private float mSrcRoundRectRadius = 0.65f;
    private float mSrcRoundingProgress = 1.0f;
    private float mTargetRoundRectRadius = 0.65f;
    private float mTargetRoundWidthPercent = 0.32f;
    private float mTargetRoundingProgress = 1.0f;

    public CameraSnapPaintRound(Context context) {
        super(context);
    }

    public void clearBitmap() {
        PaintPattern paintPattern = this.mExternalPattern;
        if (paintPattern != null) {
            paintPattern.recycle();
            this.mExternalPattern = null;
        }
    }

    /* access modifiers changed from: protected */
    @Override // com.android.camera.ui.drawable.CameraPaintBase
    public void draw(Canvas canvas) {
        if (!this.isRecording) {
            canvas.drawCircle(this.mMiddleX, this.mMiddleY, this.mBaseRadius * this.mCurrentWidthPercent, this.mPaint);
            PaintPattern paintPattern = this.mExternalPattern;
            if (paintPattern != null && this.mShowCenterBitmap) {
                paintPattern.draw(canvas);
                return;
            }
            return;
        }
        if (this.mRecordingBgPaint != null) {
            float f = this.mBaseRadius * this.mBaseWidthPercent;
            float f2 = this.mMiddleX;
            float f3 = this.mMiddleY;
            float f4 = f3 - f;
            this.mRectF.set(f2 - f, f4, f2 + f, f3 + f);
            RectF rectF = this.mRectF;
            float f5 = this.mBaseRadius;
            canvas.drawRoundRect(rectF, f5, f5, this.mRecordingBgPaint);
        }
        if (this.isRecordingCircle) {
            canvas.drawCircle(this.mMiddleX, this.mMiddleY, this.mBaseRadius * 0.55f, this.mPaint);
        } else if (this.isRoundingCircle) {
            canvas.drawCircle(this.mMiddleX, this.mMiddleY, this.mBaseRadius * this.mCurrentWidthPercent * this.mRoundingProgress, this.mPaint);
        } else {
            float f6 = this.mBaseRadius * this.mCurrentRoundRectRadius;
            float f7 = this.mMiddleX;
            float f8 = this.mMiddleY;
            RectF rectF2 = this.mRectF;
            rectF2.set(f7 - f6, f8 - f6, f7 + f6, f8 + f6);
            RectF rectF3 = this.mRectF;
            float f9 = this.mRoundingProgress;
            canvas.drawRoundRect(rectF3, f6 * f9, f6 * f9, this.mPaint);
        }
    }

    /* access modifiers changed from: protected */
    @Override // com.android.camera.ui.drawable.CameraPaintBase
    public void initPaint(Context context) {
        this.mPaint.setAntiAlias(true);
        this.mPaint.setStyle(Paint.Style.FILL);
        this.mRectF = new RectF();
    }

    public void prepareRecord(boolean z, boolean z2) {
        this.mRapidly = z;
        this.isDown = z2;
        if (z2) {
            this.mSrcRoundRectRadius = this.mRapidly ? this.mCurrentWidthPercent * this.mTargetRoundWidthPercent * 1.2f : this.mCurrentRoundRectRadius;
            this.mTargetRoundRectRadius = this.mCurrentWidthPercent * this.mTargetRoundWidthPercent;
        } else {
            this.mSrcRoundRectRadius = this.mCurrentRoundRectRadius;
            this.mTargetRoundRectRadius = this.mRapidly ? this.mCurrentWidthPercent * this.mTargetRoundWidthPercent * 0.8f : this.mCurrentWidthPercent;
        }
        if (z2) {
            this.mSrcRoundingProgress = this.mRapidly ? 0.42000002f : this.mRoundingProgress;
            this.mTargetRoundingProgress = 0.35f;
            return;
        }
        float f = this.mRoundingProgress;
        this.mSrcRoundingProgress = f;
        if (!this.mRapidly) {
            f = 1.0f;
        }
        this.mTargetRoundingProgress = f;
    }

    @Override // com.android.camera.ui.drawable.CameraPaintBase
    public void resetRecordingState() {
        super.resetRecordingState();
        this.isRecording = false;
        this.isRecordingCircle = false;
        this.isRoundingCircle = false;
        this.mRapidly = false;
    }

    public void setBitmapPatternTargetScale(float f) {
        PaintPattern paintPattern = this.mExternalPattern;
        if (paintPattern != null) {
            ((CameraSnapPaintRoundPatternBitmap) paintPattern).setTargetScale(f);
        }
    }

    public void setRapidly(boolean z) {
        this.mRapidly = z;
    }

    public void setRecordingBgColor(@ColorInt int i) {
        if (i == 0) {
            this.mRecordingBgPaint = null;
            return;
        }
        if (this.mRecordingBgPaint == null) {
            this.mRecordingBgPaint = new Paint();
            this.mRecordingBgPaint.setAntiAlias(true);
            this.mRecordingBgPaint.setStyle(Paint.Style.FILL);
        }
        this.mRecordingBgPaint.setColor(i);
    }

    public void setShowCenterBitmap(boolean z) {
        this.mShowCenterBitmap = z;
    }

    @Override // com.android.camera.ui.drawable.CameraPaintBase
    public CameraPaintBase setTargetWidthPercent(float f) {
        super.setTargetWidthPercent(f);
        if (this.isDown) {
            this.mSrcRoundRectRadius = this.mCurrentRoundRectRadius;
            this.mTargetRoundRectRadius = this.mDstWidthPercent * 0.28f;
        } else {
            this.mSrcRoundRectRadius = this.mCurrentRoundRectRadius;
            this.mTargetRoundRectRadius = this.mDstWidthPercent;
        }
        if (this.isDown) {
            this.mSrcRoundingProgress = this.mRoundingProgress;
            this.mTargetRoundingProgress = 0.35f;
        } else {
            this.mSrcRoundingProgress = this.mRoundingProgress;
            this.mTargetRoundingProgress = 1.0f;
        }
        return this;
    }

    public void showTargetBitmap(Context context, @DrawableRes int i) {
        this.mShowCenterBitmap = true;
        this.mExternalPattern = new CameraSnapPaintRoundPatternBitmap(this, BitmapFactory.decodeResource(context.getResources(), i));
    }

    @Override // com.android.camera.ui.drawable.CameraPaintBase
    public void updateValue(float f) {
        super.updateValue(f);
        PaintPattern paintPattern = this.mExternalPattern;
        if (paintPattern != null) {
            paintPattern.updateValue(f);
        }
        this.mRoundingProgress = calculateCurrentValue(this.mSrcRoundingProgress, this.mTargetRoundingProgress, f);
        this.mCurrentRoundRectRadius = calculateCurrentValue(this.mSrcRoundRectRadius, this.mTargetRoundRectRadius, f);
    }
}
