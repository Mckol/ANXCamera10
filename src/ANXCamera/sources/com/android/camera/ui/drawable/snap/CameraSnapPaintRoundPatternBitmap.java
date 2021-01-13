package com.android.camera.ui.drawable.snap;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;

public class CameraSnapPaintRoundPatternBitmap extends PaintPattern<CameraSnapPaintRound> {
    private Bitmap mBitmap;
    private float mOffsetX;
    private float mOffsetY;
    private Paint mPaint = new Paint();
    private Matrix matrix;
    protected float scaleCurrent;
    protected float scaleDst;
    protected float scaleSrc;

    public CameraSnapPaintRoundPatternBitmap(CameraSnapPaintRound cameraSnapPaintRound, Bitmap bitmap) {
        super(cameraSnapPaintRound);
        this.mPaint.setAntiAlias(true);
        this.mPaint.setStyle(Paint.Style.STROKE);
        this.mBitmap = bitmap;
    }

    private void drawCenterBitmap(Canvas canvas) {
        Matrix matrix2 = this.matrix;
        if (matrix2 == null) {
            this.matrix = new Matrix();
        } else {
            matrix2.reset();
        }
        Bitmap bitmap = this.mBitmap;
        if (bitmap != null && !bitmap.isRecycled()) {
            Matrix matrix3 = this.matrix;
            if (matrix3 == null) {
                this.matrix = new Matrix();
            } else {
                matrix3.reset();
            }
            if (!this.mBitmap.isRecycled()) {
                Matrix matrix4 = this.matrix;
                float f = this.scaleCurrent;
                matrix4.postScale(f, f, (float) (this.mBitmap.getWidth() / 2), (float) (this.mBitmap.getHeight() / 2));
                this.matrix.postTranslate((((CameraSnapPaintRound) this.paintBase).mMiddleX - ((float) (this.mBitmap.getWidth() / 2))) + (this.mOffsetX * this.scaleCurrent), (((CameraSnapPaintRound) this.paintBase).mMiddleY - ((float) (this.mBitmap.getHeight() / 2))) + (this.mOffsetY * this.scaleCurrent));
                canvas.drawBitmap(this.mBitmap, this.matrix, this.mPaint);
            }
        }
    }

    @Override // com.android.camera.ui.drawable.snap.PaintPattern
    public void directlyResult() {
        this.scaleSrc = 1.0f;
        this.scaleCurrent = 1.0f;
        this.scaleDst = 1.0f;
    }

    @Override // com.android.camera.ui.drawable.snap.PaintPattern
    public void draw(Canvas canvas) {
        super.draw(canvas);
        drawCenterBitmap(canvas);
    }

    @Override // com.android.camera.ui.drawable.snap.PaintPattern
    public boolean interceptDraw() {
        return true;
    }

    @Override // com.android.camera.ui.drawable.snap.PaintPattern
    public void prepareTargetPattern() {
        this.scaleSrc = 1.0f;
        this.scaleCurrent = 1.0f;
        this.scaleDst = 1.0f;
    }

    @Override // com.android.camera.ui.drawable.snap.PaintPattern
    public void recycle() {
        super.recycle();
        Bitmap bitmap = this.mBitmap;
        if (bitmap != null && !bitmap.isRecycled()) {
            this.mBitmap.recycle();
        }
    }

    public void setOffset(float f, float f2) {
        this.mOffsetX = f;
        this.mOffsetY = f2;
    }

    public void setTargetScale(float f) {
        this.scaleSrc = this.scaleCurrent;
        this.scaleDst = f;
    }

    @Override // com.android.camera.ui.drawable.snap.PaintPattern
    public void updateValue(float f) {
        float f2 = this.scaleSrc;
        float f3 = this.scaleDst;
        if (f2 != f3) {
            this.scaleCurrent = calculateCurrentValue(f2, f3, f);
        }
    }
}
