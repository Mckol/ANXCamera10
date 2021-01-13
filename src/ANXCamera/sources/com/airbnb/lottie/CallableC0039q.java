package com.airbnb.lottie;

import android.content.Context;
import java.util.concurrent.Callable;

/* access modifiers changed from: package-private */
/* renamed from: com.airbnb.lottie.q  reason: case insensitive filesystem */
/* compiled from: LottieCompositionFactory */
public class CallableC0039q implements Callable<X<C0035m>> {
    final /* synthetic */ String qb;
    final /* synthetic */ Context val$appContext;
    final /* synthetic */ String val$cacheKey;

    CallableC0039q(Context context, String str, String str2) {
        this.val$appContext = context;
        this.qb = str;
        this.val$cacheKey = str2;
    }

    @Override // java.util.concurrent.Callable
    public X<C0035m> call() {
        return C0047y.d(this.val$appContext, this.qb, this.val$cacheKey);
    }
}
