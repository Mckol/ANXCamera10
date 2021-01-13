package com.airbnb.lottie.e;

import android.view.animation.Interpolator;
import com.airbnb.lottie.d.g;

/* compiled from: LottieInterpolatedFloatValue */
public class c extends f<Float> {
    public c(Float f, Float f2) {
        super(f, f2);
    }

    public c(Float f, Float f2, Interpolator interpolator) {
        super(f, f2, interpolator);
    }

    /* access modifiers changed from: package-private */
    public Float a(Float f, Float f2, float f3) {
        return Float.valueOf(g.lerp(f.floatValue(), f2.floatValue(), f3));
    }
}
