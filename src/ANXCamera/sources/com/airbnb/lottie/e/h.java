package com.airbnb.lottie.e;

import com.airbnb.lottie.d.g;

/* compiled from: LottieRelativeIntegerValueCallback */
public class h extends j<Integer> {
    @Override // com.airbnb.lottie.e.j
    public Integer a(b<Integer> bVar) {
        return Integer.valueOf(g.a(bVar.getStartValue().intValue(), bVar.getEndValue().intValue(), bVar.Ac()) + b(bVar).intValue());
    }

    public Integer b(b<Integer> bVar) {
        T t = this.value;
        if (t != null) {
            return t;
        }
        throw new IllegalArgumentException("You must provide a static value in the constructor , call setValue, or override getValue.");
    }
}
