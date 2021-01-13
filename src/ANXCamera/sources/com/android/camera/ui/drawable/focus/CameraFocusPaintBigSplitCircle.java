package com.android.camera.ui.drawable.focus;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import com.android.camera.Util;
import com.android.camera.ui.drawable.CameraPaintBase;

public class CameraFocusPaintBigSplitCircle extends CameraPaintBase {
    private static final int SPLIT_HEIGHT = 10;
    private Paint mDownPaint;
    private Paint mUpPaint;

    public CameraFocusPaintBigSplitCircle(Context context) {
        super(context);
    }

    /* access modifiers changed from: protected */
    @Override // com.android.camera.ui.drawable.CameraPaintBase
    public void draw(Canvas canvas) {
        this.mUpPaint.setAlpha(this.mCurrentAlpha);
        canvas.save();
        float strokeWidth = this.mUpPaint.getStrokeWidth();
        float f = this.mBaseRadius * this.mCurrentWidthPercent;
        float f2 = this.mMiddleX;
        float f3 = this.mMiddleY;
        canvas.clipRect((f2 - f) - strokeWidth, (f3 - f) - strokeWidth, f2 + f + strokeWidth, f3 - 5.0f);
        canvas.drawCircle(this.mMiddleX, this.mMiddleY, f, this.mUpPaint);
        canvas.restore();
        this.mDownPaint.setAlpha(this.mCurrentAlpha);
        canvas.save();
        float strokeWidth2 = this.mDownPaint.getStrokeWidth();
        float f4 = this.mMiddleX;
        float f5 = this.mMiddleY;
        canvas.clipRect((f4 - f) - strokeWidth2, 5.0f + f5, f4 + f + strokeWidth2, f5 + f + strokeWidth2);
        canvas.drawCircle(this.mMiddleX, this.mMiddleY, f, this.mDownPaint);
        canvas.restore();
    }

    /* access modifiers changed from: protected */
    @Override // com.android.camera.ui.drawable.CameraPaintBase
    public void initPaint(Context context) {
        this.mUpPaint = new Paint();
        this.mUpPaint.setAntiAlias(true);
        this.mUpPaint.setStrokeWidth((float) Util.dpToPixel(1.33f));
        this.mUpPaint.setStyle(Paint.Style.STROKE);
        this.mUpPaint.setColor(-1);
        this.mDownPaint = new Paint();
        this.mDownPaint.setAntiAlias(true);
        this.mDownPaint.setStrokeWidth((float) Util.dpToPixel(1.33f));
        this.mDownPaint.setStyle(Paint.Style.STROKE);
        this.mDownPaint.setColor(Color.rgb(255, 204, 0));
    }
}
