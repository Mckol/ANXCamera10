package com.airbnb.lottie.model;

import androidx.annotation.RestrictTo;

@RestrictTo({RestrictTo.Scope.LIBRARY})
/* compiled from: Font */
public class b {
    private final String Ud;
    private final float ascent;
    private final String name;
    private final String style;

    public b(String str, String str2, String str3, float f) {
        this.Ud = str;
        this.name = str2;
        this.style = str3;
        this.ascent = f;
    }

    /* access modifiers changed from: package-private */
    public float Db() {
        return this.ascent;
    }

    public String getFamily() {
        return this.Ud;
    }

    public String getName() {
        return this.name;
    }

    public String getStyle() {
        return this.style;
    }
}
