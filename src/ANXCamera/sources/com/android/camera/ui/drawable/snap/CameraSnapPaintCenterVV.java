package com.android.camera.ui.drawable.snap;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.Align;
import android.graphics.Paint.Style;
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
        this.mTextPaint.setStyle(Style.FILL);
        this.mTextPaint.setTextSize((float) context.getResources().getDimensionPixelSize(R.dimen.vv_progress_center_text));
        this.mTextPaint.setTextAlign(Align.LEFT);
        this.mTextPaint.setAntiAlias(true);
        this.mTextPaint.setAlpha(255);
        this.mTextBound = new Rect();
    }

    /* access modifiers changed from: protected */
    public void draw(Canvas canvas) {
        float f2 = this.mBaseRadius * this.mCurrentRoundRectRadius;
        float f3 = this.mMiddleX;
        float f4 = f3 - f2;
        float f5 = f3 + f2;
        float f6 = this.mMiddleY;
        this.mRectF.set(f4, f6 - f2, f5, f6 + f2);
        this.mPaint.setColor(-1417668);
        if (!this.isRecording) {
            RectF rectF = this.mRectF;
            float f7 = this.mRoundingProgress;
            canvas.drawRoundRect(rectF, f2 * f7, f2 * f7, this.mPaint);
        }
        if (!TextUtils.isEmpty(this.mDurationText)) {
            Paint paint = this.mTextPaint;
            String str = this.mDurationText;
            paint.getTextBounds(str, 0, str.length(), this.mTextBound);
            canvas.drawText(this.mDurationText, this.mMiddleX - ((float) (this.mTextBound.width() / 2)), this.mMiddleY + ((float) (this.mTextBound.height() / 2)), this.mTextPaint);
        }
    }

    /* access modifiers changed from: protected */
    public void initPaint(Context context) {
        this.mPaint.setAntiAlias(true);
        this.mPaint.setStyle(Style.FILL);
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

    public void updateRecordValue(float f2, boolean z) {
        if (z) {
            this.mRoundingProgress = 1.0f - (0.65f * f2);
            float f3 = this.mCurrentWidthPercent;
            this.mCurrentRoundRectRadius = f3 - ((f3 - RECORDING_ROUND_WIDTH) * f2);
            return;
        }
        this.mRoundingProgress = (0.65f * f2) + 0.35f;
        this.mCurrentRoundRectRadius = ((this.mCurrentWidthPercent - RECORDING_ROUND_WIDTH) * f2) + RECORDING_ROUND_WIDTH;
    }
}
