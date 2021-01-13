package com.android.camera.ui;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Path;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.widget.FrameLayout;

public class RoundCornerFrameLayout extends FrameLayout {
    private Path mPath = new Path();
    private float mRadius;
    private RectF mRect = new RectF();

    public RoundCornerFrameLayout(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mRadius = dipToPixels(context, attributeSet.getAttributeFloatValue(null, "corner_radius", 1.0f));
    }

    public static float dipToPixels(Context context, float f) {
        return TypedValue.applyDimension(1, f, context.getResources().getDisplayMetrics());
    }

    /* access modifiers changed from: protected */
    public void dispatchDraw(Canvas canvas) {
        int save = canvas.save();
        canvas.clipPath(this.mPath);
        super.dispatchDraw(canvas);
        canvas.restoreToCount(save);
    }

    /* access modifiers changed from: protected */
    public void onDraw(Canvas canvas) {
        int save = canvas.save();
        this.mPath.reset();
        this.mRect.set(0.0f, 0.0f, (float) getWidth(), (float) getHeight());
        Path path = this.mPath;
        RectF rectF = this.mRect;
        float f = this.mRadius;
        path.addRoundRect(rectF, f, f, Path.Direction.CCW);
        this.mPath.close();
        canvas.clipPath(this.mPath);
        super.onDraw(canvas);
        canvas.restoreToCount(save);
    }

    /* access modifiers changed from: protected */
    public void onSizeChanged(int i, int i2, int i3, int i4) {
        super.onSizeChanged(i, i2, i3, i4);
        this.mPath.reset();
        float f = this.mRadius;
        this.mPath.addRoundRect(0.0f, 0.0f, (float) i, (float) i2, f, f, Path.Direction.CW);
        this.mPath.close();
    }
}
