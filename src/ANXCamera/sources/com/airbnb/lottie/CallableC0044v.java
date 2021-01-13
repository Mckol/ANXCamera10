package com.airbnb.lottie;

import com.airbnb.lottie.parser.moshi.JsonReader;
import java.util.concurrent.Callable;

/* access modifiers changed from: package-private */
/* renamed from: com.airbnb.lottie.v  reason: case insensitive filesystem */
/* compiled from: LottieCompositionFactory */
public class CallableC0044v implements Callable<X<C0035m>> {
    final /* synthetic */ String val$cacheKey;
    final /* synthetic */ JsonReader val$reader;

    CallableC0044v(JsonReader jsonReader, String str) {
        this.val$reader = jsonReader;
        this.val$cacheKey = str;
    }

    @Override // java.util.concurrent.Callable
    public X<C0035m> call() {
        return C0047y.b(this.val$reader, this.val$cacheKey);
    }
}
