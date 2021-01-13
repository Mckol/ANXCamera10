package com.miui.internal.smooth;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PointF;
import android.graphics.RectF;

public class SmoothPathProvider {
    public static final float DEFAULT_SMOOTH = 0.7f;
    private SmoothData mAllData = null;

    public static class CornerData {
        public static final int BOTTOM_LEFT = 3;
        public static final int BOTTOM_RIGHT = 2;
        public static final int TOP_LEFT = 0;
        public static final int TOP_RIGHT = 1;
        public PointF[] bezierAnchorHorizontal = new PointF[4];
        public PointF[] bezierAnchorVertical = new PointF[4];
        public float radius;
        public RectF rect;
        public double smoothForHorizontal;
        public double smoothForVertical;
        public float swapAngle;
        public double thetaForHorizontal;
        public double thetaForVertical;

        public void build(float f, int i, int i2, double d2, int i3) {
            this.radius = f;
            this.smoothForHorizontal = SmoothPathProvider.smoothForWidth(i, this.radius, d2);
            this.smoothForVertical = SmoothPathProvider.smoothForHeight(i2, this.radius, d2);
            this.thetaForHorizontal = SmoothPathProvider.thetaForWidth(this.smoothForHorizontal);
            this.thetaForVertical = SmoothPathProvider.thetaForHeight(this.smoothForVertical);
            this.swapAngle = (float) SmoothPathProvider.radToAngle((1.5707963267948966d - this.thetaForVertical) - this.thetaForHorizontal);
            double kForWidth = SmoothPathProvider.kForWidth(this.smoothForHorizontal, this.thetaForHorizontal);
            double mForWidth = SmoothPathProvider.mForWidth(this.radius, this.thetaForHorizontal);
            double nForWidth = SmoothPathProvider.nForWidth(this.radius, this.thetaForHorizontal);
            double pForWidth = SmoothPathProvider.pForWidth(this.radius, this.thetaForHorizontal);
            double xForWidth = SmoothPathProvider.xForWidth(this.radius, this.thetaForHorizontal);
            double yForWidth = SmoothPathProvider.yForWidth(kForWidth, xForWidth);
            double kForHeight = SmoothPathProvider.kForHeight(this.smoothForVertical, this.thetaForVertical);
            double mForHeight = SmoothPathProvider.mForHeight(this.radius, this.thetaForVertical);
            double nForHeight = SmoothPathProvider.nForHeight(this.radius, this.thetaForVertical);
            double pForHeight = SmoothPathProvider.pForHeight(this.radius, this.thetaForVertical);
            double xForHeight = SmoothPathProvider.xForHeight(this.radius, this.thetaForVertical);
            double yForHeight = SmoothPathProvider.yForHeight(kForHeight, xForHeight);
            if (i3 == 0) {
                float f2 = this.radius;
                this.rect = new RectF(0.0f, 0.0f, f2 * 2.0f, f2 * 2.0f);
                this.bezierAnchorHorizontal[0] = new PointF((float) mForWidth, (float) nForWidth);
                this.bezierAnchorHorizontal[1] = new PointF((float) pForWidth, 0.0f);
                double d3 = pForWidth + xForWidth;
                this.bezierAnchorHorizontal[2] = new PointF((float) d3, 0.0f);
                this.bezierAnchorHorizontal[3] = new PointF((float) (d3 + yForWidth), 0.0f);
                double d4 = pForHeight + xForHeight;
                this.bezierAnchorVertical[0] = new PointF(0.0f, (float) (d4 + yForHeight));
                this.bezierAnchorVertical[1] = new PointF(0.0f, (float) d4);
                this.bezierAnchorVertical[2] = new PointF(0.0f, (float) pForHeight);
                this.bezierAnchorVertical[3] = new PointF((float) mForHeight, (float) nForHeight);
            } else if (i3 == 1) {
                float f3 = (float) i;
                float f4 = this.radius;
                this.rect = new RectF(f3 - (f4 * 2.0f), 0.0f, f3, f4 * 2.0f);
                double d5 = (double) i;
                double d6 = d5 - pForWidth;
                double d7 = d6 - xForWidth;
                this.bezierAnchorHorizontal[0] = new PointF((float) (d7 - yForWidth), 0.0f);
                this.bezierAnchorHorizontal[1] = new PointF((float) d7, 0.0f);
                this.bezierAnchorHorizontal[2] = new PointF((float) d6, 0.0f);
                this.bezierAnchorHorizontal[3] = new PointF((float) (d5 - mForWidth), (float) nForWidth);
                this.bezierAnchorVertical[0] = new PointF((float) (d5 - mForHeight), (float) nForHeight);
                this.bezierAnchorVertical[1] = new PointF(f3, (float) pForHeight);
                double d8 = pForHeight + xForHeight;
                this.bezierAnchorVertical[2] = new PointF(f3, (float) d8);
                this.bezierAnchorVertical[3] = new PointF(f3, (float) (d8 + yForHeight));
            } else if (i3 == 2) {
                float f5 = (float) i;
                float f6 = this.radius;
                float f7 = (float) i2;
                this.rect = new RectF(f5 - (f6 * 2.0f), f7 - (f6 * 2.0f), f5, f7);
                double d9 = (double) i;
                double d10 = (double) i2;
                this.bezierAnchorHorizontal[0] = new PointF((float) (d9 - mForWidth), (float) (d10 - nForWidth));
                double d11 = d9 - pForWidth;
                this.bezierAnchorHorizontal[1] = new PointF((float) d11, f7);
                double d12 = d11 - xForWidth;
                this.bezierAnchorHorizontal[2] = new PointF((float) d12, f7);
                this.bezierAnchorHorizontal[3] = new PointF((float) (d12 - yForWidth), f7);
                double d13 = d10 - pForHeight;
                double d14 = d13 - xForHeight;
                this.bezierAnchorVertical[0] = new PointF(f5, (float) (d14 - yForHeight));
                this.bezierAnchorVertical[1] = new PointF(f5, (float) d14);
                this.bezierAnchorVertical[2] = new PointF(f5, (float) d13);
                this.bezierAnchorVertical[3] = new PointF((float) (d9 - mForHeight), (float) (d10 - nForHeight));
            } else if (i3 == 3) {
                float f8 = (float) i2;
                float f9 = this.radius;
                this.rect = new RectF(0.0f, f8 - (f9 * 2.0f), f9 * 2.0f, f8);
                double d15 = pForWidth + xForWidth;
                this.bezierAnchorHorizontal[0] = new PointF((float) (d15 + yForWidth), f8);
                this.bezierAnchorHorizontal[1] = new PointF((float) d15, f8);
                this.bezierAnchorHorizontal[2] = new PointF((float) pForWidth, f8);
                double d16 = (double) i2;
                this.bezierAnchorHorizontal[3] = new PointF((float) mForWidth, (float) (d16 - nForWidth));
                this.bezierAnchorVertical[0] = new PointF((float) mForHeight, (float) (d16 - nForHeight));
                double d17 = d16 - pForHeight;
                this.bezierAnchorVertical[1] = new PointF(0.0f, (float) d17);
                double d18 = d17 - xForHeight;
                this.bezierAnchorVertical[2] = new PointF(0.0f, (float) d18);
                this.bezierAnchorVertical[3] = new PointF(0.0f, (float) (d18 - yForHeight));
            }
        }
    }

    public static class SmoothData {
        public CornerData bottomLeft = null;
        public CornerData bottomRight = null;
        public int height;
        public double smooth;
        public CornerData topLeft = null;
        public CornerData topRight = null;
        public int width;

        public SmoothData(int i, int i2, double d2) {
            this.width = i;
            this.height = i2;
            this.smooth = d2;
        }
    }

    private void ensureFourCornerData() {
        SmoothData smoothData = this.mAllData;
        if (smoothData.topLeft == null) {
            smoothData.topLeft = new CornerData();
        }
        SmoothData smoothData2 = this.mAllData;
        if (smoothData2.topRight == null) {
            smoothData2.topRight = new CornerData();
        }
        SmoothData smoothData3 = this.mAllData;
        if (smoothData3.bottomRight == null) {
            smoothData3.bottomRight = new CornerData();
        }
        SmoothData smoothData4 = this.mAllData;
        if (smoothData4.bottomLeft == null) {
            smoothData4.bottomLeft = new CornerData();
        }
    }

    private boolean isFourCornerDataValid() {
        SmoothData smoothData = this.mAllData;
        return smoothData.topLeft == null || smoothData.topRight == null || smoothData.bottomRight == null || smoothData.bottomLeft == null;
    }

    private static boolean isHeightCollapsed(int i, float f, float f2, double d2) {
        return ((double) i) <= ((double) (f + f2)) * (d2 + 1.0d);
    }

    private static boolean isWidthCollapsed(int i, float f, float f2, double d2) {
        return ((double) i) <= ((double) (f + f2)) * (d2 + 1.0d);
    }

    /* access modifiers changed from: private */
    public static double kForHeight(double d2, double d3) {
        if (d3 == 0.0d) {
            return 0.0d;
        }
        double d4 = d3 / 2.0d;
        return ((((d2 + Math.tan(d4)) * 2.0d) * (Math.cos(d3) + 1.0d)) / (Math.tan(d4) * 3.0d)) - 1.0d;
    }

    /* access modifiers changed from: private */
    public static double kForWidth(double d2, double d3) {
        if (d3 == 0.0d) {
            return 0.0d;
        }
        double d4 = d3 / 2.0d;
        return ((((d2 + Math.tan(d4)) * 2.0d) * (Math.cos(d3) + 1.0d)) / (Math.tan(d4) * 3.0d)) - 1.0d;
    }

    /* access modifiers changed from: private */
    public static double mForHeight(float f, double d2) {
        return ((double) f) * (1.0d - Math.cos(d2));
    }

    /* access modifiers changed from: private */
    public static double mForWidth(float f, double d2) {
        return ((double) f) * (1.0d - Math.sin(d2));
    }

    /* access modifiers changed from: private */
    public static double nForHeight(float f, double d2) {
        return ((double) f) * (1.0d - Math.sin(d2));
    }

    /* access modifiers changed from: private */
    public static double nForWidth(float f, double d2) {
        return ((double) f) * (1.0d - Math.cos(d2));
    }

    /* access modifiers changed from: private */
    public static double pForHeight(float f, double d2) {
        return ((double) f) * (1.0d - Math.tan(d2 / 2.0d));
    }

    /* access modifiers changed from: private */
    public static double pForWidth(float f, double d2) {
        return ((double) f) * (1.0d - Math.tan(d2 / 2.0d));
    }

    /* access modifiers changed from: private */
    public static double radToAngle(double d2) {
        return (d2 * 180.0d) / 3.141592653589793d;
    }

    /* access modifiers changed from: private */
    public static double smoothForHeight(int i, float f, double d2) {
        return isHeightCollapsed(i, f, f, d2) ? (double) Math.max(Math.min((((float) i) / (f * 2.0f)) - 1.0f, 1.0f), 0.0f) : d2;
    }

    /* access modifiers changed from: private */
    public static double smoothForWidth(int i, float f, double d2) {
        return isWidthCollapsed(i, f, f, d2) ? (double) Math.max(Math.min((((float) i) / (f * 2.0f)) - 1.0f, 1.0f), 0.0f) : d2;
    }

    /* access modifiers changed from: private */
    public static double thetaForHeight(double d2) {
        return (d2 * 3.141592653589793d) / 4.0d;
    }

    /* access modifiers changed from: private */
    public static double thetaForWidth(double d2) {
        return (d2 * 3.141592653589793d) / 4.0d;
    }

    /* access modifiers changed from: private */
    public static double xForHeight(float f, double d2) {
        return ((((double) f) * 1.5d) * Math.tan(d2 / 2.0d)) / (Math.cos(d2) + 1.0d);
    }

    /* access modifiers changed from: private */
    public static double xForWidth(float f, double d2) {
        return ((((double) f) * 1.5d) * Math.tan(d2 / 2.0d)) / (Math.cos(d2) + 1.0d);
    }

    /* access modifiers changed from: private */
    public static double yForHeight(double d2, double d3) {
        return d2 * d3;
    }

    /* access modifiers changed from: private */
    public static double yForWidth(double d2, double d3) {
        return d2 * d3;
    }

    public void buildSmoothData(int i, int i2, float f, double d2) {
        buildSmoothData(i, i2, new float[]{f, f, f, f, f, f, f, f}, d2);
    }

    public void buildSmoothData(int i, int i2, float[] fArr, double d2) {
        this.mAllData = new SmoothData(i, i2, d2);
        if (fArr != null) {
            float[] fArr2 = {0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f};
            for (int i3 = 0; i3 < Math.min(fArr2.length, fArr.length); i3++) {
                fArr2[i3] = fArr[i3];
            }
            float f = fArr2[0];
            float f2 = fArr2[1];
            float f3 = fArr2[2];
            float f4 = fArr2[3];
            float f5 = fArr2[4];
            float f6 = fArr2[5];
            float f7 = fArr2[6];
            float f8 = fArr2[7];
            float f9 = (float) i;
            if (f + f3 > f9) {
                f = (fArr2[0] * f9) / (fArr2[0] + fArr2[2]);
                f3 = (fArr2[2] * f9) / (fArr2[0] + fArr2[2]);
            }
            float f10 = (float) i2;
            if (f4 + f6 > f10) {
                f4 = (fArr2[3] * f10) / (fArr2[3] + fArr2[5]);
                f6 = (fArr2[5] * f10) / (fArr2[3] + fArr2[5]);
            }
            if (f5 + f7 > f9) {
                f5 = (fArr2[4] * f9) / (fArr2[4] + fArr2[6]);
                f7 = (f9 * fArr2[6]) / (fArr2[4] + fArr2[6]);
            }
            if (f8 + f2 > f10) {
                f8 = (fArr2[7] * f10) / (fArr2[7] + fArr2[1]);
                f2 = (f10 * fArr2[1]) / (fArr2[7] + fArr2[1]);
            }
            ensureFourCornerData();
            this.mAllData.topLeft.build(Math.min(f, f2), i, i2, d2, 0);
            this.mAllData.topRight.build(Math.min(f3, f4), i, i2, d2, 1);
            this.mAllData.bottomRight.build(Math.min(f5, f6), i, i2, d2, 2);
            this.mAllData.bottomLeft.build(Math.min(f7, f8), i, i2, d2, 3);
        }
    }

    public void drawPath(Canvas canvas, Paint paint, int i, int i2, int i3, int i4, boolean z) {
        if (isFourCornerDataValid()) {
            paint.setColor(i);
            SmoothData smoothData = this.mAllData;
            canvas.drawRect(new RectF(0.0f, 0.0f, (float) smoothData.width, (float) smoothData.height), paint);
            return;
        }
        if (z) {
            paint.setColor(i4);
            canvas.drawArc(this.mAllData.topLeft.rect, 180.0f, 90.0f, false, paint);
            SmoothData smoothData2 = this.mAllData;
            canvas.drawLine(smoothData2.topLeft.radius, 0.0f, ((float) smoothData2.width) - smoothData2.topRight.radius, 0.0f, paint);
            canvas.drawArc(this.mAllData.topRight.rect, 270.0f, 90.0f, false, paint);
            SmoothData smoothData3 = this.mAllData;
            int i5 = smoothData3.width;
            canvas.drawLine((float) i5, smoothData3.topRight.radius, (float) i5, ((float) smoothData3.height) - smoothData3.bottomRight.radius, paint);
            canvas.drawArc(this.mAllData.bottomRight.rect, 0.0f, 90.0f, false, paint);
            SmoothData smoothData4 = this.mAllData;
            float f = ((float) smoothData4.width) - smoothData4.bottomRight.radius;
            int i6 = smoothData4.height;
            canvas.drawLine(f, (float) i6, smoothData4.bottomLeft.radius, (float) i6, paint);
            canvas.drawArc(this.mAllData.bottomLeft.rect, 90.0f, 90.0f, false, paint);
            SmoothData smoothData5 = this.mAllData;
            canvas.drawLine(0.0f, ((float) smoothData5.height) - smoothData5.bottomLeft.radius, 0.0f, smoothData5.topLeft.radius, paint);
        }
        PointF pointF = new PointF();
        paint.setColor(i2);
        CornerData cornerData = this.mAllData.topLeft;
        canvas.drawArc(cornerData.rect, (float) radToAngle(cornerData.thetaForVertical + 3.141592653589793d), this.mAllData.topLeft.swapAngle, false, paint);
        CornerData cornerData2 = this.mAllData.topLeft;
        PointF[] pointFArr = cornerData2.bezierAnchorHorizontal;
        pointF.x = pointFArr[0].x;
        pointF.y = pointFArr[0].y;
        if (cornerData2.smoothForHorizontal != 0.0d) {
            Path path = new Path();
            path.moveTo(pointF.x, pointF.y);
            PointF[] pointFArr2 = this.mAllData.topLeft.bezierAnchorHorizontal;
            path.cubicTo(pointFArr2[1].x, pointFArr2[1].y, pointFArr2[2].x, pointFArr2[2].y, pointFArr2[3].x, pointFArr2[3].y);
            paint.setColor(i3);
            canvas.drawPath(path, paint);
            PointF[] pointFArr3 = this.mAllData.topLeft.bezierAnchorHorizontal;
            pointF.x = pointFArr3[3].x;
            pointF.y = pointFArr3[3].y;
        }
        SmoothData smoothData6 = this.mAllData;
        if (!isWidthCollapsed(smoothData6.width, smoothData6.topLeft.radius, smoothData6.topRight.radius, smoothData6.smooth)) {
            paint.setColor(i);
            float f2 = pointF.x;
            float f3 = pointF.y;
            PointF[] pointFArr4 = this.mAllData.topRight.bezierAnchorHorizontal;
            canvas.drawLine(f2, f3, pointFArr4[0].x, pointFArr4[0].y, paint);
            PointF[] pointFArr5 = this.mAllData.topRight.bezierAnchorHorizontal;
            pointF.x = pointFArr5[0].x;
            pointF.y = pointFArr5[0].y;
        }
        if (this.mAllData.topRight.smoothForHorizontal != 0.0d) {
            Path path2 = new Path();
            path2.moveTo(pointF.x, pointF.y);
            PointF[] pointFArr6 = this.mAllData.topRight.bezierAnchorHorizontal;
            path2.cubicTo(pointFArr6[1].x, pointFArr6[1].y, pointFArr6[2].x, pointFArr6[2].y, pointFArr6[3].x, pointFArr6[3].y);
            paint.setColor(i3);
            canvas.drawPath(path2, paint);
            PointF[] pointFArr7 = this.mAllData.topRight.bezierAnchorHorizontal;
            pointF.x = pointFArr7[3].x;
            pointF.y = pointFArr7[3].y;
        }
        paint.setColor(i2);
        CornerData cornerData3 = this.mAllData.topRight;
        canvas.drawArc(cornerData3.rect, (float) radToAngle(cornerData3.thetaForHorizontal + 4.71238898038469d), this.mAllData.topRight.swapAngle, false, paint);
        CornerData cornerData4 = this.mAllData.topRight;
        PointF[] pointFArr8 = cornerData4.bezierAnchorVertical;
        pointF.x = pointFArr8[0].x;
        pointF.y = pointFArr8[0].y;
        if (cornerData4.smoothForVertical != 0.0d) {
            Path path3 = new Path();
            path3.moveTo(pointF.x, pointF.y);
            PointF[] pointFArr9 = this.mAllData.topRight.bezierAnchorVertical;
            path3.cubicTo(pointFArr9[1].x, pointFArr9[1].y, pointFArr9[2].x, pointFArr9[2].y, pointFArr9[3].x, pointFArr9[3].y);
            paint.setColor(i3);
            canvas.drawPath(path3, paint);
            PointF[] pointFArr10 = this.mAllData.topRight.bezierAnchorVertical;
            pointF.x = pointFArr10[3].x;
            pointF.y = pointFArr10[3].y;
        }
        SmoothData smoothData7 = this.mAllData;
        if (!isHeightCollapsed(smoothData7.height, smoothData7.topRight.radius, smoothData7.bottomRight.radius, smoothData7.smooth)) {
            paint.setColor(i);
            float f4 = pointF.x;
            float f5 = pointF.y;
            PointF[] pointFArr11 = this.mAllData.bottomRight.bezierAnchorVertical;
            canvas.drawLine(f4, f5, pointFArr11[0].x, pointFArr11[0].y, paint);
            PointF[] pointFArr12 = this.mAllData.bottomRight.bezierAnchorVertical;
            pointF.x = pointFArr12[0].x;
            pointF.y = pointFArr12[0].y;
        }
        if (this.mAllData.bottomRight.smoothForVertical != 0.0d) {
            Path path4 = new Path();
            path4.moveTo(pointF.x, pointF.y);
            PointF[] pointFArr13 = this.mAllData.bottomRight.bezierAnchorVertical;
            path4.cubicTo(pointFArr13[1].x, pointFArr13[1].y, pointFArr13[2].x, pointFArr13[2].y, pointFArr13[3].x, pointFArr13[3].y);
            paint.setColor(i3);
            canvas.drawPath(path4, paint);
            PointF[] pointFArr14 = this.mAllData.bottomRight.bezierAnchorVertical;
            pointF.x = pointFArr14[3].x;
            pointF.y = pointFArr14[3].y;
        }
        paint.setColor(i2);
        CornerData cornerData5 = this.mAllData.bottomRight;
        canvas.drawArc(cornerData5.rect, (float) radToAngle(cornerData5.thetaForVertical), this.mAllData.bottomRight.swapAngle, false, paint);
        CornerData cornerData6 = this.mAllData.bottomRight;
        PointF[] pointFArr15 = cornerData6.bezierAnchorHorizontal;
        pointF.x = pointFArr15[0].x;
        pointF.y = pointFArr15[0].y;
        if (cornerData6.smoothForHorizontal != 0.0d) {
            Path path5 = new Path();
            path5.moveTo(pointF.x, pointF.y);
            PointF[] pointFArr16 = this.mAllData.bottomRight.bezierAnchorHorizontal;
            path5.cubicTo(pointFArr16[1].x, pointFArr16[1].y, pointFArr16[2].x, pointFArr16[2].y, pointFArr16[3].x, pointFArr16[3].y);
            paint.setColor(i3);
            canvas.drawPath(path5, paint);
            PointF[] pointFArr17 = this.mAllData.bottomRight.bezierAnchorHorizontal;
            pointF.x = pointFArr17[3].x;
            pointF.y = pointFArr17[3].y;
        }
        SmoothData smoothData8 = this.mAllData;
        if (!isWidthCollapsed(smoothData8.width, smoothData8.bottomRight.radius, smoothData8.bottomLeft.radius, smoothData8.smooth)) {
            paint.setColor(i);
            float f6 = pointF.x;
            float f7 = pointF.y;
            PointF[] pointFArr18 = this.mAllData.bottomLeft.bezierAnchorHorizontal;
            canvas.drawLine(f6, f7, pointFArr18[0].x, pointFArr18[0].y, paint);
            PointF[] pointFArr19 = this.mAllData.bottomLeft.bezierAnchorHorizontal;
            pointF.x = pointFArr19[0].x;
            pointF.y = pointFArr19[0].y;
        }
        if (this.mAllData.bottomLeft.smoothForHorizontal != 0.0d) {
            Path path6 = new Path();
            path6.moveTo(pointF.x, pointF.y);
            PointF[] pointFArr20 = this.mAllData.bottomLeft.bezierAnchorHorizontal;
            path6.cubicTo(pointFArr20[1].x, pointFArr20[1].y, pointFArr20[2].x, pointFArr20[2].y, pointFArr20[3].x, pointFArr20[3].y);
            paint.setColor(i3);
            canvas.drawPath(path6, paint);
            PointF[] pointFArr21 = this.mAllData.bottomLeft.bezierAnchorHorizontal;
            pointF.x = pointFArr21[3].x;
            pointF.y = pointFArr21[3].y;
        }
        paint.setColor(i2);
        CornerData cornerData7 = this.mAllData.bottomLeft;
        canvas.drawArc(cornerData7.rect, (float) radToAngle(cornerData7.thetaForHorizontal + 1.5707963267948966d), this.mAllData.bottomLeft.swapAngle, false, paint);
        CornerData cornerData8 = this.mAllData.bottomLeft;
        PointF[] pointFArr22 = cornerData8.bezierAnchorVertical;
        pointF.x = pointFArr22[0].x;
        pointF.y = pointFArr22[0].y;
        if (cornerData8.smoothForVertical != 0.0d) {
            Path path7 = new Path();
            path7.moveTo(pointF.x, pointF.y);
            PointF[] pointFArr23 = this.mAllData.bottomLeft.bezierAnchorVertical;
            path7.cubicTo(pointFArr23[1].x, pointFArr23[1].y, pointFArr23[2].x, pointFArr23[2].y, pointFArr23[3].x, pointFArr23[3].y);
            paint.setColor(i3);
            canvas.drawPath(path7, paint);
            PointF[] pointFArr24 = this.mAllData.bottomLeft.bezierAnchorVertical;
            pointF.x = pointFArr24[3].x;
            pointF.y = pointFArr24[3].y;
        }
        SmoothData smoothData9 = this.mAllData;
        if (!isHeightCollapsed(smoothData9.height, smoothData9.bottomLeft.radius, smoothData9.topLeft.radius, smoothData9.smooth)) {
            paint.setColor(i);
            float f8 = pointF.x;
            float f9 = pointF.y;
            PointF[] pointFArr25 = this.mAllData.topLeft.bezierAnchorVertical;
            canvas.drawLine(f8, f9, pointFArr25[0].x, pointFArr25[0].y, paint);
            PointF[] pointFArr26 = this.mAllData.topLeft.bezierAnchorVertical;
            pointF.x = pointFArr26[0].x;
            pointF.y = pointFArr26[0].y;
        }
        if (this.mAllData.topLeft.smoothForVertical != 0.0d) {
            Path path8 = new Path();
            path8.moveTo(pointF.x, pointF.y);
            PointF[] pointFArr27 = this.mAllData.topLeft.bezierAnchorVertical;
            path8.cubicTo(pointFArr27[1].x, pointFArr27[1].y, pointFArr27[2].x, pointFArr27[2].y, pointFArr27[3].x, pointFArr27[3].y);
            paint.setColor(i3);
            canvas.drawPath(path8, paint);
            PointF[] pointFArr28 = this.mAllData.topLeft.bezierAnchorVertical;
            pointF.x = pointFArr28[3].x;
            pointF.y = pointFArr28[3].y;
        }
    }

    public Path getSmoothPath() {
        if (isFourCornerDataValid()) {
            Path path = new Path();
            SmoothData smoothData = this.mAllData;
            path.addRect(new RectF(0.0f, 0.0f, (float) smoothData.width, (float) smoothData.height), Path.Direction.CCW);
            return path;
        }
        Path path2 = new Path();
        path2.reset();
        CornerData cornerData = this.mAllData.topLeft;
        path2.arcTo(cornerData.rect, (float) radToAngle(cornerData.thetaForVertical + 3.141592653589793d), this.mAllData.topLeft.swapAngle);
        CornerData cornerData2 = this.mAllData.topLeft;
        if (cornerData2.smoothForHorizontal != 0.0d) {
            PointF[] pointFArr = cornerData2.bezierAnchorHorizontal;
            path2.cubicTo(pointFArr[1].x, pointFArr[1].y, pointFArr[2].x, pointFArr[2].y, pointFArr[3].x, pointFArr[3].y);
        }
        SmoothData smoothData2 = this.mAllData;
        if (!isWidthCollapsed(smoothData2.width, smoothData2.topLeft.radius, smoothData2.topRight.radius, smoothData2.smooth)) {
            PointF[] pointFArr2 = this.mAllData.topRight.bezierAnchorHorizontal;
            path2.lineTo(pointFArr2[0].x, pointFArr2[0].y);
        }
        CornerData cornerData3 = this.mAllData.topRight;
        if (cornerData3.smoothForHorizontal != 0.0d) {
            PointF[] pointFArr3 = cornerData3.bezierAnchorHorizontal;
            path2.cubicTo(pointFArr3[1].x, pointFArr3[1].y, pointFArr3[2].x, pointFArr3[2].y, pointFArr3[3].x, pointFArr3[3].y);
        }
        CornerData cornerData4 = this.mAllData.topRight;
        path2.arcTo(cornerData4.rect, (float) radToAngle(cornerData4.thetaForHorizontal + 4.71238898038469d), this.mAllData.topRight.swapAngle);
        CornerData cornerData5 = this.mAllData.topRight;
        if (cornerData5.smoothForVertical != 0.0d) {
            PointF[] pointFArr4 = cornerData5.bezierAnchorVertical;
            path2.cubicTo(pointFArr4[1].x, pointFArr4[1].y, pointFArr4[2].x, pointFArr4[2].y, pointFArr4[3].x, pointFArr4[3].y);
        }
        SmoothData smoothData3 = this.mAllData;
        if (!isHeightCollapsed(smoothData3.height, smoothData3.topRight.radius, smoothData3.bottomRight.radius, smoothData3.smooth)) {
            PointF[] pointFArr5 = this.mAllData.bottomRight.bezierAnchorVertical;
            path2.lineTo(pointFArr5[0].x, pointFArr5[0].y);
        }
        CornerData cornerData6 = this.mAllData.bottomRight;
        if (cornerData6.smoothForVertical != 0.0d) {
            PointF[] pointFArr6 = cornerData6.bezierAnchorVertical;
            path2.cubicTo(pointFArr6[1].x, pointFArr6[1].y, pointFArr6[2].x, pointFArr6[2].y, pointFArr6[3].x, pointFArr6[3].y);
        }
        CornerData cornerData7 = this.mAllData.bottomRight;
        path2.arcTo(cornerData7.rect, (float) radToAngle(cornerData7.thetaForVertical), this.mAllData.bottomRight.swapAngle);
        CornerData cornerData8 = this.mAllData.bottomRight;
        if (cornerData8.smoothForHorizontal != 0.0d) {
            PointF[] pointFArr7 = cornerData8.bezierAnchorHorizontal;
            path2.cubicTo(pointFArr7[1].x, pointFArr7[1].y, pointFArr7[2].x, pointFArr7[2].y, pointFArr7[3].x, pointFArr7[3].y);
        }
        SmoothData smoothData4 = this.mAllData;
        if (!isWidthCollapsed(smoothData4.width, smoothData4.bottomRight.radius, smoothData4.bottomLeft.radius, smoothData4.smooth)) {
            PointF[] pointFArr8 = this.mAllData.bottomLeft.bezierAnchorHorizontal;
            path2.lineTo(pointFArr8[0].x, pointFArr8[0].y);
        }
        CornerData cornerData9 = this.mAllData.bottomLeft;
        if (cornerData9.smoothForHorizontal != 0.0d) {
            PointF[] pointFArr9 = cornerData9.bezierAnchorHorizontal;
            path2.cubicTo(pointFArr9[1].x, pointFArr9[1].y, pointFArr9[2].x, pointFArr9[2].y, pointFArr9[3].x, pointFArr9[3].y);
        }
        CornerData cornerData10 = this.mAllData.bottomLeft;
        path2.arcTo(cornerData10.rect, (float) radToAngle(cornerData10.thetaForHorizontal + 1.5707963267948966d), this.mAllData.bottomLeft.swapAngle);
        CornerData cornerData11 = this.mAllData.bottomLeft;
        if (cornerData11.smoothForVertical != 0.0d) {
            PointF[] pointFArr10 = cornerData11.bezierAnchorVertical;
            path2.cubicTo(pointFArr10[1].x, pointFArr10[1].y, pointFArr10[2].x, pointFArr10[2].y, pointFArr10[3].x, pointFArr10[3].y);
        }
        SmoothData smoothData5 = this.mAllData;
        if (!isHeightCollapsed(smoothData5.height, smoothData5.bottomLeft.radius, smoothData5.topLeft.radius, smoothData5.smooth)) {
            PointF[] pointFArr11 = this.mAllData.topLeft.bezierAnchorVertical;
            path2.lineTo(pointFArr11[0].x, pointFArr11[0].y);
        }
        CornerData cornerData12 = this.mAllData.topLeft;
        if (cornerData12.smoothForVertical != 0.0d) {
            PointF[] pointFArr12 = cornerData12.bezierAnchorVertical;
            path2.cubicTo(pointFArr12[1].x, pointFArr12[1].y, pointFArr12[2].x, pointFArr12[2].y, pointFArr12[3].x, pointFArr12[3].y);
        }
        path2.close();
        return path2;
    }
}
