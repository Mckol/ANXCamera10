package com.android.camera.dualvideo.render;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.VectorDrawable;
import android.opengl.GLES20;
import android.opengl.Matrix;
import android.text.Layout;
import android.text.StaticLayout;
import android.text.TextPaint;
import android.util.Size;
import com.android.camera.CameraSettings;
import com.android.camera.Util;
import com.android.camera.dualvideo.UserSelectData;
import com.android.camera.effect.FrameBuffer;
import com.android.camera.effect.draw_mode.DrawBlurTexAttribute;
import com.android.camera.effect.draw_mode.DrawExtTexAttribute;
import com.android.camera.log.Log;
import com.android.gallery3d.ui.GLCanvas;
import com.android.gallery3d.ui.RawTexture;
import com.xiaomi.stat.d;

public class DualVideoUtil {
    public static Size BACK_PREVIE = new Size(1920, 1080);
    public static Size FRONT_PREVIE = new Size(1280, Util.LIMIT_SURFACE_WIDTH);
    public static final int LINE_COLOR = 1785358954;
    public static Size OUTPUT_SIZE = new Size(1080, 1920);

    private DualVideoUtil() {
    }

    static /* synthetic */ boolean b(int i, UserSelectData userSelectData) {
        return userSelectData.m6PatchType == i;
    }

    public static void blurTex(GLCanvas gLCanvas, RawTexture rawTexture) {
        long currentTimeMillis = System.currentTimeMillis();
        for (int i = 0; i < 8; i++) {
            renderBlurTexture(gLCanvas, rawTexture);
        }
        Log.d("DualVideoUtil", "blur tex  cost time = " + (System.currentTimeMillis() - currentTimeMillis) + d.H);
    }

    public static void centerCropHorizontal(float[] fArr, float f) {
        Matrix.translateM(fArr, 0, (1.0f - f) / 2.0f, 0.0f, 0.0f);
        Matrix.scaleM(fArr, 0, f, 1.0f, 1.0f);
    }

    public static void centerCropVertical(float[] fArr, float f) {
        Matrix.translateM(fArr, 0, 0.0f, (1.0f - f) / 2.0f, 0.0f);
        Matrix.scaleM(fArr, 0, 1.0f, f, 1.0f);
    }

    public static float[] centerScaleMatrix(float[] fArr, int i) {
        if (i != 11 && i != 12) {
            switch (i) {
                case 20:
                case 25:
                    centerCropVertical(fArr, getCropRatio(i));
                    break;
                case 21:
                    Matrix.translateM(fArr, 0, 0.2f, 0.2f, 0.0f);
                    Matrix.scaleM(fArr, 0, 0.6f, 0.6f, 1.0f);
                    centerCropVertical(fArr, getCropRatio(i));
                    break;
                case 22:
                    Matrix.translateM(fArr, 0, 0.35f, 0.35f, 0.0f);
                    Matrix.scaleM(fArr, 0, 0.3f, 0.3f, 1.0f);
                    centerCropVertical(fArr, getCropRatio(i));
                    break;
                case 23:
                    Matrix.translateM(fArr, 0, 0.44f, 0.44f, 0.0f);
                    Matrix.scaleM(fArr, 0, 0.12f, 0.12f, 1.0f);
                    centerCropVertical(fArr, getCropRatio(i));
                    break;
                case 24:
                    Matrix.translateM(fArr, 0, 0.47f, 0.47f, 0.0f);
                    Matrix.scaleM(fArr, 0, 0.06f, 0.06f, 1.0f);
                    centerCropVertical(fArr, getCropRatio(i));
                    break;
            }
        } else {
            centerCropVertical(fArr, getCropRatio(i));
        }
        return fArr;
    }

    public static void copyPreviewTexture(GLCanvas gLCanvas, RawTexture rawTexture, DrawExtTexAttribute drawExtTexAttribute) {
        int width = rawTexture.getWidth();
        int height = rawTexture.getHeight();
        FrameBuffer frameBuffer = new FrameBuffer(gLCanvas, rawTexture, 0);
        gLCanvas.beginBindFrameBuffer(frameBuffer);
        gLCanvas.draw(new DrawExtTexAttribute(drawExtTexAttribute.mExtTexture, drawExtTexAttribute.mTextureTransform, 0, 0, width, height));
        GLES20.glFinish();
        gLCanvas.endBindFrameBuffer();
        frameBuffer.delete();
    }

    public static Bitmap getBitmap(Resources resources, int i) {
        Drawable drawable = resources.getDrawable(i, null);
        if (drawable instanceof BitmapDrawable) {
            return BitmapFactory.decodeResource(resources, i);
        }
        if (drawable instanceof VectorDrawable) {
            return getBitmap((VectorDrawable) drawable);
        }
        throw new IllegalArgumentException("unsupported drawable type");
    }

    private static Bitmap getBitmap(VectorDrawable vectorDrawable) {
        Bitmap createBitmap = Bitmap.createBitmap(vectorDrawable.getIntrinsicWidth(), vectorDrawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(createBitmap);
        vectorDrawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
        vectorDrawable.draw(canvas);
        createBitmap.setPremultiplied(true);
        return createBitmap;
    }

    public static float getCropRatio(int i) {
        if (i == 11 || i == 12) {
            return 0.5f;
        }
        switch (i) {
            case 20:
            case 21:
            case 22:
            case 23:
            case 24:
            case 25:
                return 0.5f;
            default:
                return 1.0f;
        }
    }

    public static int getSelectedIndexBy6PathchType(int i) {
        return ((Integer) CameraSettings.getDualVideoConfig().getSelectedData().stream().filter(new N(i)).findFirst().map(O.INSTANCE).orElse(0)).intValue();
    }

    private static TextPaint getTextPaint(int i) {
        TextPaint textPaint = new TextPaint(1);
        textPaint.setTextSize(36.0f);
        textPaint.setAntiAlias(true);
        textPaint.setColor(i);
        textPaint.setTypeface(Typeface.create("mipro-regular", 0));
        textPaint.setShadowLayer(1.0f, 0.0f, 0.0f, Integer.MIN_VALUE);
        textPaint.setStrokeWidth(1.0f);
        textPaint.setStyle(Paint.Style.FILL_AND_STROKE);
        return textPaint;
    }

    public static Rect mixArea(Rect rect, Rect rect2, float f) {
        float f2 = 1.0f - f;
        return new Rect((int) ((((float) rect.left) * f2) + (((float) rect2.left) * f)), (int) ((((float) rect.top) * f2) + (((float) rect2.top) * f)), (int) ((((float) rect.right) * f2) + (((float) rect2.right) * f)), (int) ((((float) rect.bottom) * f2) + (((float) rect2.bottom) * f)));
    }

    public static float[] mixMatrix(float[] fArr, float[] fArr2, float f) {
        float f2 = 1.0f - f;
        float[] fArr3 = (float[]) fArr.clone();
        for (int i = 0; i < fArr.length; i++) {
            fArr3[i] = (fArr[i] * f2) + (fArr2[i] * f);
        }
        return fArr3;
    }

    private static void renderBlurTexture(GLCanvas gLCanvas, RawTexture rawTexture) {
        int width = rawTexture.getWidth();
        int height = rawTexture.getHeight();
        FrameBuffer frameBuffer = new FrameBuffer(gLCanvas, rawTexture, 0);
        gLCanvas.prepareBlurRenders();
        gLCanvas.beginBindFrameBuffer(frameBuffer);
        gLCanvas.draw(new DrawBlurTexAttribute(rawTexture, 0, 0, width, height));
        GLES20.glFinish();
        gLCanvas.endBindFrameBuffer();
        frameBuffer.delete();
    }

    public static Rect shrinkRect(Rect rect, float f) {
        float width = (((float) rect.width()) * f) / 2.0f;
        float height = (((float) rect.height()) * f) / 2.0f;
        float exactCenterX = rect.exactCenterX();
        float exactCenterY = rect.exactCenterY();
        return new Rect((int) (exactCenterX - width), (int) (exactCenterY - height), (int) (exactCenterX + width), (int) (exactCenterY + height));
    }

    public static Bitmap textAsBitmap(String str, int i) {
        TextPaint textPaint = getTextPaint(i);
        textPaint.setAntiAlias(true);
        StaticLayout staticLayout = new StaticLayout(str, textPaint, (int) textPaint.measureText(str), Layout.Alignment.ALIGN_NORMAL, 1.0f, 0.0f, true);
        Bitmap createBitmap = Bitmap.createBitmap((int) textPaint.measureText(str), Util.dpToPixel(17.45f), Bitmap.Config.ARGB_8888);
        staticLayout.draw(new Canvas(createBitmap));
        return createBitmap;
    }
}
