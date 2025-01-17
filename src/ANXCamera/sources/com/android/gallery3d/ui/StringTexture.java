package com.android.gallery3d.ui;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.text.TextPaint;
import android.text.TextUtils;
import androidx.core.view.ViewCompat;
import com.android.camera.Util;
import com.android.camera.data.DataRepository;

public class StringTexture extends CanvasTexture {
    private static final String TAG = "StringTexture";
    public static final int TYPE_NORMAL = 0;
    public static final int TYPE_WATER_MARK = 1;
    public static final int TYPE_WATER_MARK_NEW_STYLE = 2;
    public static final float WATER_MARK_SHADOW_D = 5.0f;
    public static final float WATER_MARK_SHADOW_Y = 3.0f;
    public static final int WATER_MARK_SHADOW_Y_COLOR = 771751936;
    private final Paint.FontMetricsInt mMetrics;
    private final TextPaint mPaint;
    private final String mText;

    private StringTexture(String str, TextPaint textPaint, Paint.FontMetricsInt fontMetricsInt, int i, int i2) {
        super(i, i2);
        this.mText = str;
        this.mPaint = textPaint;
        this.mMetrics = fontMetricsInt;
    }

    public static TextPaint getDefaultPaint(float f, int i, int i2) {
        TextPaint textPaint = new TextPaint();
        textPaint.setTextSize(f);
        textPaint.setAntiAlias(true);
        textPaint.setColor(i);
        if (i2 == 1) {
            textPaint.setTypeface(Util.getMiuiTypeface());
            textPaint.setShadowLayer(0.1f, 5.0f, 5.0f, ViewCompat.MEASURED_STATE_MASK);
            textPaint.setLetterSpacing(0.1f);
        } else if (i2 == 2) {
            textPaint.setTypeface(Util.getMiuiTypeface());
            textPaint.setShadowLayer(0.1f, 0.0f, 3.0f, WATER_MARK_SHADOW_Y_COLOR);
            textPaint.setLetterSpacing(0.1f);
        } else {
            textPaint.setShadowLayer(2.0f, 0.0f, 0.0f, ViewCompat.MEASURED_STATE_MASK);
        }
        return textPaint;
    }

    public static TextPaint getDefaultPaintGen2(float f) {
        TextPaint textPaint = new TextPaint(1);
        textPaint.setTextSize(f);
        textPaint.setColor(-1);
        textPaint.setTypeface(Typeface.create("mipro-regular", 0));
        textPaint.setShadowLayer(1.0f, 0.0f, 0.0f, Integer.MIN_VALUE);
        textPaint.setStyle(Paint.Style.FILL);
        textPaint.setLetterSpacing(0.06f);
        return textPaint;
    }

    public static StringTexture newInstance(String str, float f, int i, float f2, boolean z) {
        return newInstance(str, f, i, f2, z, 0);
    }

    public static StringTexture newInstance(String str, float f, int i, float f2, boolean z, int i2) {
        TextPaint defaultPaint = getDefaultPaint(f, i, i2);
        if (z) {
            defaultPaint.setTypeface(Typeface.defaultFromStyle(1));
        }
        if (f2 > 0.0f) {
            str = TextUtils.ellipsize(str, defaultPaint, f2, TextUtils.TruncateAt.END).toString();
        }
        return newInstance(str, defaultPaint, i2);
    }

    public static StringTexture newInstance(String str, float f, int i, int i2) {
        return newInstance(str, DataRepository.dataItemFeature().lj() ? getDefaultPaintGen2(f) : getDefaultPaint(f, i, i2), i2);
    }

    private static StringTexture newInstance(String str, TextPaint textPaint, int i) {
        Paint.FontMetricsInt fontMetricsInt = textPaint.getFontMetricsInt();
        int ceil = ((int) Math.ceil((double) textPaint.measureText(str))) + (i == 1 ? 5 : 0);
        int i2 = fontMetricsInt.descent - fontMetricsInt.ascent;
        return new StringTexture(str, textPaint, fontMetricsInt, ceil <= 0 ? 1 : ceil, i2 <= 0 ? 1 : i2);
    }

    /* access modifiers changed from: protected */
    @Override // com.android.gallery3d.ui.CanvasTexture
    public void onDraw(Canvas canvas, Bitmap bitmap) {
        canvas.translate(0.0f, (float) (-this.mMetrics.ascent));
        canvas.drawText(this.mText, 0.0f, 0.0f, this.mPaint);
    }
}
