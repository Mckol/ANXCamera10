package c.a.b;

import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.Align;
import android.graphics.Paint.FontMetrics;
import android.graphics.Paint.Style;
import android.graphics.Rect;
import android.os.Build.VERSION;
import android.text.TextPaint;
import java.util.regex.Pattern;

/* compiled from: TextMeshUtils */
public class a {
    private static int a(Canvas canvas, String str, float f2, float f3, Paint paint, int i) {
        int i2;
        int i3;
        Rect rect = new Rect();
        System.out.printf("the text space is: %d\n", new Object[]{Integer.valueOf(i)});
        System.out.printf("the text length is: %d\n", new Object[]{Integer.valueOf(str.length())});
        float f4 = f2;
        int i4 = 0;
        int i5 = 0;
        while (i4 < str.length()) {
            char charAt = str.charAt(i4);
            String valueOf = String.valueOf(charAt);
            Math.round(paint.measureText(String.valueOf(charAt)));
            if (charAt > 55296) {
                i4++;
                StringBuilder sb = new StringBuilder();
                sb.append(String.valueOf(charAt));
                sb.append(str.charAt(i4));
                String sb2 = sb.toString();
                if (i4 != 1) {
                    paint.getTextBounds(str, i4, i4 + 1, rect);
                    rect.width();
                }
                int width = rect.width() + (i / 2);
                f4 += (float) width;
                i5 += width;
                if (canvas != null) {
                    canvas.drawText(sb2, f4, f3, paint);
                }
            } else {
                if (charAt == ' ') {
                    i2 = Math.round(paint.measureText(String.valueOf(str.charAt(i4)))) + i;
                } else {
                    paint.getTextBounds(str, i4, i4 + 1, rect);
                    if (charAt >= 128) {
                        i3 = rect.width() + (i / 2);
                        System.out.printf("%s is not NumOrLetters\n", new Object[]{Character.valueOf(charAt)});
                    } else if (h(valueOf)) {
                        i3 = rect.width() + i;
                        System.out.printf("%s is NumOrLetters\n", new Object[]{Character.valueOf(charAt)});
                    } else {
                        i2 = rect.width() + (i * 2);
                        System.out.printf("%s is: %d, space is: %d, text is %d\n", new Object[]{Character.valueOf(str.charAt(i4)), Integer.valueOf(i2), Integer.valueOf(i), Integer.valueOf(rect.width())});
                    }
                    i2 = i3;
                    System.out.printf("%s is: %d, space is: %d, text is %d\n", new Object[]{Character.valueOf(str.charAt(i4)), Integer.valueOf(i2), Integer.valueOf(i), Integer.valueOf(rect.width())});
                }
                f4 += (float) i2;
                i5 += i2;
                if (canvas != null) {
                    canvas.drawText(String.valueOf(str.charAt(i4)), f4, f3, paint);
                }
            }
            i4++;
        }
        return i5;
    }

    private static Bitmap a(byte[] bArr, String str, int i, int i2, int i3, float f2, int i4, float f3, int i5, float f4, float f5, float f6, int i6, int i7, int i8, int i9) {
        int i10 = i;
        int i11 = i3;
        int i12 = i7;
        int i13 = i8;
        int i14 = i9;
        String str2 = new String(bArr);
        Rect rect = new Rect(0, 0, i13, i14);
        Bitmap createBitmap = Bitmap.createBitmap(i13, i14, Config.ARGB_8888);
        Paint paint = new Paint();
        paint.setColor(0);
        paint.setStyle(Style.FILL);
        Canvas canvas = new Canvas(createBitmap);
        TextPaint textPaint = new TextPaint();
        textPaint.setColor(i2);
        float f7 = (float) i10;
        textPaint.setTextSize(f7);
        textPaint.setAntiAlias(true);
        textPaint.setStyle(Style.FILL);
        if (i12 == 0) {
            textPaint.setTextAlign(Align.LEFT);
        } else if (i12 == 1) {
            textPaint.setTextAlign(Align.CENTER);
        } else {
            textPaint.setTextAlign(Align.RIGHT);
        }
        if ((i4 & 16) == 16) {
            textPaint.setUnderlineText(true);
        }
        if ((i4 & 32) == 32) {
            textPaint.setStrikeThruText(true);
        }
        if ((i4 & 4) == 4) {
            textPaint.setTextSkewX((-f2) / 90.0f);
        }
        if ((i4 & 8) == 8) {
            textPaint.setFakeBoldText(true);
        }
        if ((i4 & 2) == 2) {
            textPaint.setShadowLayer(f4, f5, f6, i6);
        }
        FontMetrics fontMetrics = textPaint.getFontMetrics();
        int centerY = (int) ((((float) rect.centerY()) - (fontMetrics.top / 2.0f)) - (fontMetrics.bottom / 2.0f));
        if ((i4 & 1) == 1) {
            TextPaint textPaint2 = new TextPaint();
            textPaint2.setColor(i5);
            textPaint2.setTextSize(textPaint.getTextSize());
            textPaint2.setAntiAlias(textPaint.isAntiAlias());
            textPaint2.setStyle(Style.STROKE);
            textPaint2.setStrokeWidth((5.0f * f3) / f7);
            textPaint2.setTextAlign(textPaint.getTextAlign());
            textPaint2.setTextSkewX(textPaint.getTextSkewX());
            textPaint.setFakeBoldText(false);
            textPaint2.setFakeBoldText(true);
            float f8 = ((float) i11) / f7;
            if (VERSION.SDK_INT >= 21) {
                textPaint2.setLetterSpacing(f8);
                if (i12 == 0) {
                    canvas.drawText(str2, (float) rect.left, (float) centerY, textPaint2);
                } else if (i12 == 1) {
                    canvas.drawText(str2, (float) rect.centerX(), (float) centerY, textPaint2);
                } else {
                    canvas.drawText(str2, (float) rect.right, (float) centerY, textPaint2);
                }
            } else {
                Rect rect2 = new Rect();
                textPaint.getTextBounds(str2, 0, str2.length(), rect2);
                int i15 = 0;
                int i16 = 0;
                while (i15 < str2.length()) {
                    if (str2.charAt(i15) > 55296) {
                        i15++;
                        i16++;
                    }
                    i15++;
                }
                if (i12 == 0) {
                    a(canvas, str2, 0.0f, (float) centerY, textPaint2, i3);
                } else if (i12 == 1) {
                    a(canvas, str2, (float) ((((rect.width() - rect2.width()) + (i16 * i10)) - ((str2.length() - 1) * i11)) / 2), (float) centerY, textPaint2, i3);
                } else {
                    a(canvas, str2, (float) (((rect.width() - rect2.width()) + (i16 * i10)) - ((str2.length() - 1) * i11)), (float) centerY, textPaint2, i3);
                }
            }
        }
        float f9 = ((float) i11) / f7;
        if (VERSION.SDK_INT >= 21) {
            textPaint.setLetterSpacing(f9);
            if (i12 == 0) {
                canvas.drawText(str2, (float) rect.left, (float) centerY, textPaint);
            } else if (i12 == 1) {
                canvas.drawText(str2, (float) rect.centerX(), (float) centerY, textPaint);
            } else {
                canvas.drawText(str2, (float) rect.right, (float) centerY, textPaint);
            }
        } else {
            Rect rect3 = new Rect();
            int i17 = 0;
            textPaint.getTextBounds(str2, 0, str2.length(), rect3);
            int i18 = 0;
            while (i17 < str2.length()) {
                if (str2.charAt(i17) > 55296) {
                    i17++;
                    i18++;
                }
                i17++;
            }
            if (i12 == 0) {
                a(canvas, str2, 0.0f, (float) centerY, textPaint, i3);
            } else if (i12 == 1) {
                a(canvas, str2, (float) ((((rect.width() - rect3.width()) + (i18 * i10)) - ((str2.length() - 1) * i11)) / 2), (float) centerY, textPaint, i3);
            } else {
                a(canvas, str2, (float) (((rect.width() - rect3.width()) + (i18 * i10)) - ((str2.length() - 1) * i11)), (float) centerY, textPaint, i3);
            }
        }
        canvas.save(31);
        canvas.restore();
        return createBitmap;
    }

    public static boolean h(String str) {
        return Pattern.compile("^[A-Za-z0-9_]+$").matcher(str).matches();
    }
}
