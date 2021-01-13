package com.airbnb.lottie.e;

import android.view.animation.Interpolator;
import android.view.animation.LinearInterpolator;

/* compiled from: LottieInterpolatedValue */
abstract class f<T> extends j<T> {
    private final T endValue;
    private final Interpolator interpolator;
    private final T startValue;

    f(T t, T t2) {
        this(t, t2, new LinearInterpolator());
    }

    f(T t, T t2, Interpolator interpolator2) {
        this.startValue = t;
        this.endValue = t2;
        this.interpolator = interpolator2;
    }

    @Override // com.airbnb.lottie.e.j
    public T a(b<T> bVar) {
        return a(this.startValue, this.endValue, this.interpolator.getInterpolation(bVar.Cc()));
    }

    /* access modifiers changed from: package-private */
    public abstract T a(T t, T t2, float f);
}
