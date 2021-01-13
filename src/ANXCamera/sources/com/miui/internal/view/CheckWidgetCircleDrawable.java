package com.miui.internal.view;

import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;

public class CheckWidgetCircleDrawable extends Drawable {
    private static final int PADDING = 3;
    private static final int STROKEWIDTH = 3;
    private boolean mHasStroke;
    private Paint mPaint;
    private float mScale;
    private int mStrokeDisableAlpha;
    private int mStrokeNormalAlpha;
    private Paint mStrokePaint;
    private int mUncheckedDisableAlpha;
    private int mUncheckedNormalAlpha;

    public CheckWidgetCircleDrawable(int i, int i2, int i3) {
        this(i, i2, i3, 0, 0, 0);
    }

    public CheckWidgetCircleDrawable(int i, int i2, int i3, int i4, int i5, int i6) {
        this.mPaint = new Paint();
        this.mStrokePaint = new Paint();
        this.mScale = 1.0f;
        this.mPaint.setAntiAlias(true);
        this.mPaint.setColor(i);
        this.mUncheckedNormalAlpha = i2;
        this.mUncheckedDisableAlpha = i3;
        this.mHasStroke = i4 != 0;
        if (this.mHasStroke) {
            this.mStrokePaint.setAntiAlias(true);
            this.mStrokePaint.setColor(i4);
            this.mStrokePaint.setStrokeWidth(3.0f);
            this.mStrokePaint.setStyle(Paint.Style.STROKE);
        }
        this.mStrokeNormalAlpha = i5;
        this.mStrokeDisableAlpha = i6;
    }

    public void draw(Canvas canvas) {
        Rect bounds = getBounds();
        int i = bounds.right;
        int i2 = bounds.left;
        int i3 = (i + i2) / 2;
        int i4 = bounds.top;
        int i5 = bounds.bottom;
        int i6 = (i4 + i5) / 2;
        int min = Math.min(i - i2, i5 - i4) / 2;
        if (this.mHasStroke) {
            float f = (float) i3;
            float f2 = (float) i6;
            float f3 = (float) min;
            canvas.drawCircle(f, f2, ((this.mScale * f3) - 3.0f) - 3.0f, this.mPaint);
            canvas.drawCircle(f, f2, ((f3 * this.mScale) - 3.0f) - 1.5f, this.mStrokePaint);
            return;
        }
        canvas.drawCircle((float) i3, (float) i6, (((float) min) * this.mScale) - 3.0f, this.mPaint);
    }

    public int getAlpha() {
        return this.mPaint.getAlpha();
    }

    public int getOpacity() {
        return -3;
    }

    public float getScale() {
        return this.mScale;
    }

    public void setAlpha(int i) {
        this.mPaint.setAlpha(i);
        if (!this.mHasStroke) {
            return;
        }
        if (i == this.mUncheckedNormalAlpha) {
            this.mStrokePaint.setAlpha(this.mStrokeNormalAlpha);
        } else if (i == this.mUncheckedDisableAlpha) {
            this.mStrokePaint.setAlpha(this.mStrokeDisableAlpha);
        }
    }

    public void setColorFilter(ColorFilter colorFilter) {
        this.mPaint.setColorFilter(colorFilter);
    }

    public void setScale(float f) {
        this.mScale = f;
    }
}
