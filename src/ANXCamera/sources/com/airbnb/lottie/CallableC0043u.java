package com.airbnb.lottie;

import java.util.concurrent.Callable;

/* access modifiers changed from: package-private */
/* renamed from: com.airbnb.lottie.u  reason: case insensitive filesystem */
/* compiled from: LottieCompositionFactory */
public class CallableC0043u implements Callable<X<C0035m>> {
    final /* synthetic */ String ub;
    final /* synthetic */ String val$cacheKey;

    CallableC0043u(String str, String str2) {
        this.ub = str;
        this.val$cacheKey = str2;
    }

    @Override // java.util.concurrent.Callable
    public X<C0035m> call() {
        return C0047y.o(this.ub, this.val$cacheKey);
    }
}
