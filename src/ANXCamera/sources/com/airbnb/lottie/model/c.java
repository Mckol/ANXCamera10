package com.airbnb.lottie.model;

import androidx.annotation.RestrictTo;
import com.airbnb.lottie.model.content.j;
import java.util.List;

@RestrictTo({RestrictTo.Scope.LIBRARY})
/* compiled from: FontCharacter */
public class c {
    private final List<j> Vd;
    private final char Wd;
    private final String fontFamily;
    private final double size;
    private final String style;
    private final double width;

    public c(List<j> list, char c2, double d2, double d3, String str, String str2) {
        this.Vd = list;
        this.Wd = c2;
        this.size = d2;
        this.width = d3;
        this.style = str;
        this.fontFamily = str2;
    }

    public static int a(char c2, String str, String str2) {
        return ((((0 + c2) * 31) + str.hashCode()) * 31) + str2.hashCode();
    }

    public List<j> Eb() {
        return this.Vd;
    }

    /* access modifiers changed from: package-private */
    public double getSize() {
        return this.size;
    }

    /* access modifiers changed from: package-private */
    public String getStyle() {
        return this.style;
    }

    public double getWidth() {
        return this.width;
    }

    public int hashCode() {
        return a(this.Wd, this.fontFamily, this.style);
    }
}
