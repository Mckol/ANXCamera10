package com.airbnb.lottie.e;

import androidx.annotation.NonNull;

/* compiled from: LottieRelativeFloatValueCallback */
public class g extends j<Float> {
    public g() {
    }

    public g(@NonNull Float f) {
        super(f);
    }

    @Override // com.airbnb.lottie.e.j
    public Float a(b<Float> bVar) {
        return Float.valueOf(com.airbnb.lottie.d.g.lerp(bVar.getStartValue().floatValue(), bVar.getEndValue().floatValue(), bVar.Ac()) + b(bVar).floatValue());
    }

    public Float b(b<Float> bVar) {
        T t = this.value;
        if (t != null) {
            return t;
        }
        throw new IllegalArgumentException("You must provide a static value in the constructor , call setValue, or override getValue.");
    }
}
