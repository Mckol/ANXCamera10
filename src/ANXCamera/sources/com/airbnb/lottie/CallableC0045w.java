package com.airbnb.lottie;

import java.util.concurrent.Callable;
import java.util.zip.ZipInputStream;

/* renamed from: com.airbnb.lottie.w  reason: case insensitive filesystem */
/* compiled from: LottieCompositionFactory */
class CallableC0045w implements Callable<X<C0035m>> {
    final /* synthetic */ String val$cacheKey;
    final /* synthetic */ ZipInputStream vb;

    CallableC0045w(ZipInputStream zipInputStream, String str) {
        this.vb = zipInputStream;
        this.val$cacheKey = str;
    }

    @Override // java.util.concurrent.Callable
    public X<C0035m> call() {
        return C0047y.b(this.vb, this.val$cacheKey);
    }
}
