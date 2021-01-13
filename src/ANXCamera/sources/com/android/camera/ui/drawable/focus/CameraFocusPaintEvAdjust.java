package com.android.camera.ui.drawable.focus;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import com.android.camera.Util;
import com.android.camera.ui.FocusView;
import com.android.camera.ui.drawable.CameraPaintBase;

public class CameraFocusPaintEvAdjust extends CameraPaintBase {
    private static final int MARGIN = Util.dpToPixel(12.0f);
    private static final int TRIANGLE_BASE_DIS = Util.dpToPixel(3.0f);
    private static final int TRIANGLE_BASE_HEIGHT = Util.dpToPixel(5.0f);
    private static final int TRIANGLE_BASE_LEN = Util.dpToPixel(8.0f);
    private static final int TRIANGLE_MIN_MARGIN = Util.dpToPixel(25.0f);
    private float mCurrentDistanceY;
    private float mCurrentOffsetY;
    private Rect mDisplayRect;
    private float mEvValue = -1.0f;
    private int mLineAlpha = 204;
    private int mLineHeight = FocusView.MAX_SLIDE_DISTANCE;
    private int mLineMargin = Util.dpToPixel(2.0f);
    private Paint mLinePaint;
    private int mRotation;
    private boolean mRtl = false;
    private boolean mShowLine = true;
    private float mStartOffsetY;

    public CameraFocusPaintEvAdjust(Context context) {
        super(context);
    }

    private boolean isNearlyOutOfEdge() {
        int width = this.mDisplayRect.width();
        int height = this.mDisplayRect.height();
        int i = this.mRotation;
        if ((i / 90) % 2 == 0) {
            return this.mRtl ? (this.mMiddleX - ((float) CameraFocusAnimateDrawable.BIG_RADIUS)) - ((float) MARGIN) >= ((float) TRIANGLE_MIN_MARGIN) : ((((float) width) - this.mMiddleX) - ((float) CameraFocusAnimateDrawable.BIG_RADIUS)) - ((float) MARGIN) < ((float) TRIANGLE_MIN_MARGIN);
        }
        if (i == 90) {
            return ((((float) height) - this.mMiddleY) - ((float) CameraFocusAnimateDrawable.BIG_RADIUS)) - ((float) MARGIN) < ((float) TRIANGLE_MIN_MARGIN);
        }
        if (i == 270) {
            return (this.mMiddleY - ((float) CameraFocusAnimateDrawable.BIG_RADIUS)) - ((float) MARGIN) < ((float) TRIANGLE_MIN_MARGIN);
        }
        return false;
    }

    /* access modifiers changed from: protected */
    @Override // com.android.camera.ui.drawable.CameraPaintBase
    public void draw(Canvas canvas) {
        int i;
        float f;
        Path path = new Path();
        if (isNearlyOutOfEdge()) {
            f = (this.mMiddleX - ((float) CameraFocusAnimateDrawable.BIG_RADIUS)) - ((float) MARGIN);
            i = TRIANGLE_BASE_LEN / 2;
        } else {
            f = this.mMiddleX + ((float) CameraFocusAnimateDrawable.BIG_RADIUS) + ((float) MARGIN);
            i = TRIANGLE_BASE_LEN / 2;
        }
        float f2 = f - ((float) i);
        float f3 = ((this.mMiddleY - this.mCurrentOffsetY) + this.mCurrentDistanceY) - ((float) (TRIANGLE_BASE_DIS / 2));
        path.moveTo(f2, f3);
        path.lineTo(((float) TRIANGLE_BASE_LEN) + f2, f3);
        path.lineTo(((float) (TRIANGLE_BASE_LEN / 2)) + f2, f3 - ((float) TRIANGLE_BASE_HEIGHT));
        path.lineTo(f2, f3);
        int i2 = TRIANGLE_BASE_LEN;
        float f4 = ((float) (i2 / 2)) + f2;
        float f5 = ((float) (i2 / 2)) + f2;
        float f6 = this.mMiddleY - ((float) (this.mLineHeight / 2));
        if (this.mShowLine) {
            int i3 = TRIANGLE_BASE_HEIGHT;
            int i4 = this.mLineMargin;
            if ((f3 - ((float) i3)) - f6 > ((float) i4)) {
                this.mLinePaint.setColor(this.mCurrentColor);
                this.mLinePaint.setAlpha(this.mLineAlpha);
                this.mLinePaint.setStrokeWidth(2.0f);
                canvas.drawLine(f4, f6, f5, (f3 - ((float) i3)) - ((float) i4), this.mLinePaint);
            }
        }
        float f7 = this.mMiddleY + this.mCurrentOffsetY + this.mCurrentDistanceY + ((float) (TRIANGLE_BASE_DIS / 2));
        path.moveTo(f2, f7);
        path.lineTo(((float) TRIANGLE_BASE_LEN) + f2, f7);
        path.lineTo(((float) (TRIANGLE_BASE_LEN / 2)) + f2, ((float) TRIANGLE_BASE_HEIGHT) + f7);
        path.lineTo(f2, f7);
        canvas.drawPath(path, this.mPaint);
        if (this.mShowLine) {
            float f8 = this.mMiddleY + ((float) (this.mLineHeight / 2));
            int i5 = this.mLineMargin;
            int i6 = TRIANGLE_BASE_HEIGHT;
            if (f8 - ((float) i5) > ((float) i6) + f7) {
                this.mLinePaint.setColor(this.mCurrentColor);
                this.mLinePaint.setAlpha(this.mLineAlpha);
                this.mLinePaint.setStrokeWidth(2.0f);
                canvas.drawLine(f4, f7 + ((float) i6) + ((float) i5), f5, f8, this.mLinePaint);
            }
        }
    }

    public float getEvValue() {
        return this.mEvValue;
    }

    /* access modifiers changed from: protected */
    @Override // com.android.camera.ui.drawable.CameraPaintBase
    public void initPaint(Context context) {
        this.mPaint.setStyle(Paint.Style.FILL);
        this.mPaint.setAntiAlias(true);
        this.mLinePaint = new Paint();
        this.mLinePaint.setAntiAlias(true);
        this.mLinePaint.setStyle(Paint.Style.FILL);
        this.mLinePaint.setColor(Color.argb(102, 255, 255, 255));
    }

    public void setDistanceY(float f) {
        this.mCurrentDistanceY = f;
    }

    public void setEvValue(float f) {
        this.mEvValue = f;
    }

    public void setLineAlpha(int i) {
        this.mLineAlpha = i;
    }

    public void setOrientation(int i) {
        this.mRotation = i;
    }

    public void setRtlAndDisplayRect(boolean z, Rect rect) {
        this.mRtl = z;
        this.mDisplayRect = rect;
    }

    public void setShowLine(boolean z) {
        this.mShowLine = z;
    }

    public void setStartOffsetY(float f) {
        this.mStartOffsetY = f;
    }

    @Override // com.android.camera.ui.drawable.CameraPaintBase
    public void updateValue(float f) {
        super.updateValue(f);
        float f2 = this.mStartOffsetY;
        this.mCurrentOffsetY = f2 - (f * f2);
    }
}
