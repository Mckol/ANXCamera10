package com.airbnb.lottie;

import java.util.concurrent.Callable;

/* access modifiers changed from: package-private */
/* renamed from: com.airbnb.lottie.x  reason: case insensitive filesystem */
/* compiled from: LottieCompositionFactory */
public class CallableC0046x implements Callable<X<C0035m>> {
    final /* synthetic */ C0035m wb;

    CallableC0046x(C0035m mVar) {
        this.wb = mVar;
    }

    @Override // java.util.concurrent.Callable
    public X<C0035m> call() {
        return new X<>(this.wb);
    }
}
