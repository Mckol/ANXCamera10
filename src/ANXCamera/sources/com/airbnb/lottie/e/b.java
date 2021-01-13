package com.airbnb.lottie.e;

import androidx.annotation.RestrictTo;

/* compiled from: LottieFrameInfo */
public class b<T> {
    private T endValue;
    private float lb;
    private float mb;
    private float qg;
    private float rg;
    private float sg;
    private T startValue;

    public float Ac() {
        return this.rg;
    }

    public float Bc() {
        return this.qg;
    }

    public float Cc() {
        return this.sg;
    }

    @RestrictTo({RestrictTo.Scope.LIBRARY})
    public b<T> a(float f, float f2, T t, T t2, float f3, float f4, float f5) {
        this.lb = f;
        this.mb = f2;
        this.startValue = t;
        this.endValue = t2;
        this.qg = f3;
        this.rg = f4;
        this.sg = f5;
        return this;
    }

    public float db() {
        return this.mb;
    }

    public T getEndValue() {
        return this.endValue;
    }

    public T getStartValue() {
        return this.startValue;
    }

    public float hb() {
        return this.lb;
    }
}
