package com.airbnb.lottie.e;

import android.view.animation.Interpolator;
import com.airbnb.lottie.d.g;

/* compiled from: LottieInterpolatedIntegerValue */
public class d extends f<Integer> {
    public d(Integer num, Integer num2) {
        super(num, num2);
    }

    public d(Integer num, Integer num2, Interpolator interpolator) {
        super(num, num2, interpolator);
    }

    /* access modifiers changed from: package-private */
    public Integer a(Integer num, Integer num2, float f) {
        return Integer.valueOf(g.a(num.intValue(), num2.intValue(), f));
    }
}
