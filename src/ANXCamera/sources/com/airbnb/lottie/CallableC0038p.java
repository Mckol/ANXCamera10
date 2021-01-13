package com.airbnb.lottie;

import android.content.Context;
import com.airbnb.lottie.network.b;
import java.util.concurrent.Callable;

/* access modifiers changed from: package-private */
/* renamed from: com.airbnb.lottie.p  reason: case insensitive filesystem */
/* compiled from: LottieCompositionFactory */
public class CallableC0038p implements Callable<X<C0035m>> {
    final /* synthetic */ String pb;
    final /* synthetic */ Context val$context;

    CallableC0038p(Context context, String str) {
        this.val$context = context;
        this.pb = str;
    }

    @Override // java.util.concurrent.Callable
    public X<C0035m> call() {
        return b.l(this.val$context, this.pb);
    }
}
