package com.airbnb.lottie;

import java.util.concurrent.Callable;
import org.json.JSONObject;

/* renamed from: com.airbnb.lottie.t  reason: case insensitive filesystem */
/* compiled from: LottieCompositionFactory */
class CallableC0042t implements Callable<X<C0035m>> {
    final /* synthetic */ JSONObject ub;
    final /* synthetic */ String val$cacheKey;

    CallableC0042t(JSONObject jSONObject, String str) {
        this.ub = jSONObject;
        this.val$cacheKey = str;
    }

    @Override // java.util.concurrent.Callable
    public X<C0035m> call() {
        return C0047y.b(this.ub, this.val$cacheKey);
    }
}
