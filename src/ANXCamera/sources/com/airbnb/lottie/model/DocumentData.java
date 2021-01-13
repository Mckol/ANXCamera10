package com.airbnb.lottie.model;

import androidx.annotation.ColorInt;
import androidx.annotation.RestrictTo;

@RestrictTo({RestrictTo.Scope.LIBRARY})
public class DocumentData {
    public final String Qd;
    public final Justification Rd;
    public final int Sd;
    public final boolean Td;
    public final float baselineShift;
    @ColorInt
    public final int color;
    public final float lineHeight;
    public final float size;
    @ColorInt
    public final int strokeColor;
    public final float strokeWidth;
    public final String text;

    public enum Justification {
        LEFT_ALIGN,
        RIGHT_ALIGN,
        CENTER
    }

    public DocumentData(String str, String str2, float f, Justification justification, int i, float f2, float f3, @ColorInt int i2, @ColorInt int i3, float f4, boolean z) {
        this.text = str;
        this.Qd = str2;
        this.size = f;
        this.Rd = justification;
        this.Sd = i;
        this.lineHeight = f2;
        this.baselineShift = f3;
        this.color = i2;
        this.strokeColor = i3;
        this.strokeWidth = f4;
        this.Td = z;
    }

    public int hashCode() {
        int hashCode = (((((int) (((float) (((this.text.hashCode() * 31) + this.Qd.hashCode()) * 31)) + this.size)) * 31) + this.Rd.ordinal()) * 31) + this.Sd;
        long floatToRawIntBits = (long) Float.floatToRawIntBits(this.lineHeight);
        return (((hashCode * 31) + ((int) (floatToRawIntBits ^ (floatToRawIntBits >>> 32)))) * 31) + this.color;
    }
}
