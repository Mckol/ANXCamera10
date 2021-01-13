package com.airbnb.lottie;

import java.io.InputStream;
import java.util.concurrent.Callable;

/* access modifiers changed from: package-private */
/* renamed from: com.airbnb.lottie.s  reason: case insensitive filesystem */
/* compiled from: LottieCompositionFactory */
public class CallableC0041s implements Callable<X<C0035m>> {
    final /* synthetic */ InputStream tb;
    final /* synthetic */ String val$cacheKey;

    CallableC0041s(InputStream inputStream, String str) {
        this.tb = inputStream;
        this.val$cacheKey = str;
    }

    @Override // java.util.concurrent.Callable
    public X<C0035m> call() {
        return C0047y.c(this.tb, this.val$cacheKey);
    }
}
