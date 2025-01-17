package com.android.camera.ui;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.util.AttributeSet;
import androidx.annotation.ColorInt;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.core.view.ViewCompat;

public class ColorImageView extends AppCompatImageView {
    private boolean isNeedTransparent = true;
    private int mColor;
    private Paint mPaint;

    public ColorImageView(Context context) {
        super(context);
        init();
    }

    public ColorImageView(Context context, @Nullable AttributeSet attributeSet) {
        super(context, attributeSet);
        init();
    }

    public ColorImageView(Context context, @Nullable AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        init();
    }

    private void init() {
        ViewCompat.setLayerType(this, 2, null);
        this.mPaint = new Paint();
        this.mPaint.setAntiAlias(true);
        this.mPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        this.mPaint.setColor(0);
    }

    public int getColor() {
        return this.mColor;
    }

    /* access modifiers changed from: protected */
    public void onDraw(Canvas canvas) {
        if (!this.isNeedTransparent || this.mColor != 0) {
            int saveLayer = canvas.saveLayer(0.0f, 0.0f, (float) getWidth(), (float) getHeight(), null, 31);
            super.onDraw(canvas);
            canvas.drawPaint(this.mPaint);
            canvas.restoreToCount(saveLayer);
            return;
        }
        super.onDraw(canvas);
    }

    public void setColor(@ColorInt int i) {
        this.mColor = i;
        this.mPaint.setColor(i);
    }

    public void setColorAndRefresh(@ColorInt int i) {
        setColor(i);
        invalidate();
    }

    public void setIsNeedTransparent(boolean z, boolean z2) {
        this.isNeedTransparent = z;
        if (z2) {
            invalidate();
        }
    }
}
