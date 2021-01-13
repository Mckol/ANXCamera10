package com.airbnb.lottie;

import com.airbnb.lottie.d.d;
import com.airbnb.lottie.d.h;

/* renamed from: com.airbnb.lottie.f  reason: case insensitive filesystem */
/* compiled from: LottieAnimationView */
class C0028f implements T<Throwable> {
    C0028f() {
    }

    /* renamed from: e */
    public void a(Throwable th) {
        if (h.isNetworkException(th)) {
            d.c("Unable to load composition.", th);
            return;
        }
        throw new IllegalStateException("Unable to parse composition", th);
    }
}
