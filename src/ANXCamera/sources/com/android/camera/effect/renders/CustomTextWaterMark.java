package com.android.camera.effect.renders;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.text.TextPaint;
import android.text.TextUtils;
import com.android.camera.data.DataRepository;
import com.android.camera.log.Log;
import com.xiaomi.stat.d;

public class CustomTextWaterMark {
    private static final int BACK_TEXT_START_MARGIN = 64;
    private static final int BACK_WATER_MARK_HEIGHT = 195;
    public static final String DEVICE_FONT_NAME = "mipro-medium";
    private static final String DEVICE_PREFIX_GEN2 = "SHOT ON ";
    public static final String FONT_NAME = "mipro-regular";
    private static final int FRONT_TEXT_START_MARGIN = 50;
    private static final int FRONT_WATER_MARK_HEIGHT = 120;
    private static final int NO_ICON_DESCRIPTION_TEXT_SIZE = 68;
    private static final int NO_ICON_TITLE_TEXT_SIZE = 118;
    private static final int NO_ICON_VERTICAL_MARGIN = 5;
    private static final String TAG = "CustomTextWaterMark";
    public static final int TEXT_COLOR = -1;
    private static final int TEXT_SIZE = 70;
    private static final int TEXT_TOP_MARGIN_ROW1_GEN2 = 34;
    private static final int TEXT_TOP_MARGIN_ROW2_GEN2 = 20;
    private static final int VERTICAL_MARGIN = 23;
    private static final int WATER_MARK_CINEMATIC_MAX_LENGTH = 1100;
    private static final int WATER_MARK_MAX_LENGTH = 1600;
    private static final int WATER_MARK_MAX_PRODUCT_NAME = 12;
    private static final float WATER_MARK_SHADOW_Y = 2.0f;
    private static final int WATER_MARK_SHADOW_Y_COLOR = 771751936;

    public static String capitalize(CharSequence charSequence) {
        if (charSequence.length() == 0) {
            return "";
        }
        return "" + Character.toUpperCase(charSequence.charAt(0)) + ((Object) charSequence.subSequence(1, charSequence.length()));
    }

    public static String capitalize(String str) {
        return capitalize((CharSequence) str);
    }

    public static Bitmap drawBackWaterMark(Bitmap bitmap, String str, String str2, boolean z) {
        return DataRepository.dataItemFeature().lj() ? drawBackWaterMarkGen2(bitmap, str, str2, z) : bitmap == null ? drawNoIcon(str, str2) : drawWithIcon(bitmap, str, str2);
    }

    private static Bitmap drawBackWaterMarkGen2(Bitmap bitmap, String str, String str2, boolean z) {
        int i;
        String str3;
        Bitmap bitmap2;
        String str4 = str;
        long currentTimeMillis = System.currentTimeMillis();
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        Log.i(TAG, "drawBackWaterMarkGen2: icon size = " + width + "x" + height);
        if (str.length() < 12 && !str4.startsWith(DEVICE_PREFIX_GEN2)) {
            str4 = DEVICE_PREFIX_GEN2 + str4;
        }
        TextPaint deviceTextPaintGen2 = getDeviceTextPaintGen2();
        Paint.FontMetricsInt fontMetricsInt = deviceTextPaintGen2.getFontMetricsInt();
        int ceil = (int) Math.ceil((double) deviceTextPaintGen2.measureText(str4));
        Log.i(TAG, "drawBackWaterMarkGen2: deviceNameSize = " + ceil + "x" + (fontMetricsInt.descent - fontMetricsInt.ascent));
        TextPaint textPaintGen2 = getTextPaintGen2();
        if (TextUtils.isEmpty(str2)) {
            i = 0;
        } else {
            i = (int) Math.ceil((double) textPaintGen2.measureText(" | " + str2));
        }
        Paint.FontMetricsInt fontMetricsInt2 = textPaintGen2.getFontMetricsInt();
        int i2 = i + ceil;
        int i3 = fontMetricsInt2.descent - fontMetricsInt2.ascent;
        Log.i(TAG, "drawBackWaterMarkGen2: size = " + i2 + "x" + i3);
        if (TextUtils.isEmpty(str2) || ((z && i2 <= WATER_MARK_CINEMATIC_MAX_LENGTH) || (!z && i2 <= WATER_MARK_MAX_LENGTH))) {
            str3 = TAG;
            int max = Math.max(width, i2);
            int i4 = height + 34;
            int i5 = i3 + i4;
            Bitmap createBitmap = Bitmap.createBitmap(max, i5, Bitmap.Config.ARGB_8888);
            createBitmap.setPremultiplied(true);
            Canvas canvas = new Canvas(createBitmap);
            canvas.drawBitmap(bitmap, 0.0f, 0.0f, (Paint) null);
            bitmap.recycle();
            float f = (float) (i4 - fontMetricsInt2.ascent);
            canvas.drawText(str4, 0.0f, f, deviceTextPaintGen2);
            if (!TextUtils.isEmpty(str2)) {
                canvas.drawText(" | " + str2, (float) ceil, f, textPaintGen2);
            }
            Log.d(str3, "drawBackWaterMarkGen2: size = " + max + "x" + i5);
            bitmap2 = createBitmap;
        } else {
            int ceil2 = (int) Math.ceil((double) textPaintGen2.measureText(str4));
            int ceil3 = (int) Math.ceil((double) textPaintGen2.measureText(str2));
            int max2 = Math.max(ceil2, ceil3);
            int i6 = height + 34;
            int i7 = i6 + i3 + 20;
            int i8 = i3 + i7;
            Bitmap createBitmap2 = Bitmap.createBitmap(max2, i8, Bitmap.Config.ARGB_8888);
            createBitmap2.setPremultiplied(true);
            Canvas canvas2 = new Canvas(createBitmap2);
            canvas2.drawBitmap(bitmap, 0.0f, 0.0f, (Paint) null);
            bitmap.recycle();
            canvas2.drawText(str4, 0.0f, (float) (i6 - fontMetricsInt2.ascent), deviceTextPaintGen2);
            canvas2.drawText(str2, 0.0f, (float) (i7 - fontMetricsInt2.ascent), textPaintGen2);
            String str5 = "drawBackWaterMarkGen2: firstLineWidth = " + ceil2 + ", secondLineWidth = " + ceil3 + ", size = " + max2 + "x" + i8;
            str3 = TAG;
            Log.d(str3, str5);
            bitmap2 = createBitmap2;
        }
        Log.v(str3, "drawBackWaterMarkGen2: cost time = " + (System.currentTimeMillis() - currentTimeMillis) + d.H);
        return bitmap2;
    }

    public static Bitmap drawFrontWaterMark(Bitmap bitmap, String str) {
        long currentTimeMillis = System.currentTimeMillis();
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        Log.i(TAG, "drawFrontWaterMark onDraw: icon = " + width + "x" + height);
        TextPaint textPaint = getTextPaint();
        textPaint.setLetterSpacing(0.03f);
        Rect rect = new Rect();
        textPaint.getTextBounds(str, 0, str.length(), rect);
        int i = width + 50;
        Bitmap createBitmap = Bitmap.createBitmap(rect.width() + i, 120, Bitmap.Config.ARGB_8888);
        createBitmap.setPremultiplied(true);
        Canvas canvas = new Canvas(createBitmap);
        canvas.drawBitmap(bitmap, 0.0f, ((float) (120 - height)) / 2.0f, (Paint) null);
        bitmap.recycle();
        float f = (float) (i - rect.left);
        float height2 = (((float) (120 - rect.height())) / 2.0f) - ((float) rect.top);
        canvas.drawText(str, f, height2, textPaint);
        Log.d(TAG, "drawFrontWaterMark: productBounds = " + rect + ", height = " + rect.height() + ", y = " + height2);
        StringBuilder sb = new StringBuilder();
        sb.append("drawFrontWaterMark: Custom watermark cost time = ");
        sb.append(System.currentTimeMillis() - currentTimeMillis);
        sb.append(d.H);
        Log.v(TAG, sb.toString());
        return createBitmap;
    }

    private static Bitmap drawNoIcon(String str, String str2) {
        long currentTimeMillis = System.currentTimeMillis();
        TextPaint textPaint = getTextPaint();
        Rect rect = new Rect();
        textPaint.setTypeface(Typeface.create("mipro-regular", 1));
        textPaint.setTextSize(118.0f);
        textPaint.getTextBounds(str, 0, str.length(), rect);
        TextPaint textPaint2 = getTextPaint();
        Rect rect2 = new Rect();
        textPaint2.setTypeface(Typeface.create("mipro-regular", 0));
        textPaint2.setTextSize(68.0f);
        textPaint2.getTextBounds(str2, 0, str2.length(), rect2);
        Bitmap createBitmap = Bitmap.createBitmap(Math.max(rect.width(), rect2.width()), 195, Bitmap.Config.ARGB_8888);
        createBitmap.setPremultiplied(true);
        Canvas canvas = new Canvas(createBitmap);
        canvas.drawText(str, (float) (-rect.left), (float) (rect.height() + 5), textPaint);
        Log.d(TAG, "drawNoIcon: productBounds = " + rect);
        canvas.drawText(str2, (float) (-rect2.left), (float) ((createBitmap.getHeight() + -5) - rect.bottom), textPaint2);
        Log.d(TAG, "drawNoIcon: customBounds = " + rect2);
        Log.v(TAG, "drawNoIcon: Custom watermark cost time = " + (System.currentTimeMillis() - currentTimeMillis) + d.H);
        return createBitmap;
    }

    private static Bitmap drawWithIcon(Bitmap bitmap, String str, String str2) {
        long currentTimeMillis = System.currentTimeMillis();
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        Log.i(TAG, "drawWithIcon: icon = " + width + "x" + height);
        TextPaint textPaint = getTextPaint();
        Rect rect = new Rect();
        textPaint.getTextBounds(str, 0, str.length(), rect);
        Rect rect2 = new Rect();
        textPaint.getTextBounds(str2, 0, str2.length(), rect2);
        int i = width + 64;
        Bitmap createBitmap = Bitmap.createBitmap(Math.max(rect.width(), rect2.width()) + i, 195, Bitmap.Config.ARGB_8888);
        createBitmap.setPremultiplied(true);
        Canvas canvas = new Canvas(createBitmap);
        canvas.drawBitmap(bitmap, 0.0f, 0.0f, (Paint) null);
        bitmap.recycle();
        canvas.drawText(str, (float) (i - rect.left), (float) (rect.height() + 23), textPaint);
        Log.d(TAG, "drawWithIcon: productBounds = " + rect);
        canvas.drawText(str2, (float) ((i - rect2.left) + -2), (float) ((createBitmap.getHeight() + -23) - rect.bottom), textPaint);
        Log.d(TAG, "drawWithIcon: customBounds = " + rect2);
        Log.v(TAG, "drawWithIcon: Custom watermark cost time = " + (System.currentTimeMillis() - currentTimeMillis) + d.H);
        return createBitmap;
    }

    private static TextPaint getDeviceTextPaintGen2() {
        TextPaint textPaint = new TextPaint(1);
        textPaint.setTextSize(72.0f);
        textPaint.setColor(-1);
        textPaint.setTypeface(Typeface.create("mipro-medium", 0));
        textPaint.setShadowLayer(1.0f, 0.0f, 0.0f, Integer.MIN_VALUE);
        textPaint.setStyle(Paint.Style.FILL);
        textPaint.setLetterSpacing(0.06f);
        return textPaint;
    }

    private static TextPaint getTextPaint() {
        TextPaint textPaint = new TextPaint(1);
        textPaint.setTextSize(70.0f);
        textPaint.setAntiAlias(true);
        textPaint.setColor(-1);
        textPaint.setTypeface(Typeface.create("mipro-regular", 0));
        textPaint.setShadowLayer(0.1f, 0.0f, 2.0f, 771751936);
        textPaint.setStrokeWidth(1.0f);
        textPaint.setStyle(Paint.Style.FILL_AND_STROKE);
        textPaint.setLetterSpacing(0.06f);
        textPaint.setTextScaleX(0.95f);
        return textPaint;
    }

    private static TextPaint getTextPaintGen2() {
        TextPaint textPaint = new TextPaint(1);
        textPaint.setTextSize(73.0f);
        textPaint.setColor(-1);
        textPaint.setTypeface(Typeface.create("mipro-regular", 0));
        textPaint.setShadowLayer(1.0f, 0.0f, 0.0f, Integer.MIN_VALUE);
        textPaint.setStyle(Paint.Style.FILL);
        textPaint.setLetterSpacing(0.06f);
        return textPaint;
    }
}
