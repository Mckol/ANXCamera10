package com.airbnb.lottie;

import android.content.Context;
import java.lang.ref.WeakReference;
import java.util.concurrent.Callable;

/* access modifiers changed from: package-private */
/* renamed from: com.airbnb.lottie.r  reason: case insensitive filesystem */
/* compiled from: LottieCompositionFactory */
public class CallableC0040r implements Callable<X<C0035m>> {
    final /* synthetic */ WeakReference rb;
    final /* synthetic */ int sb;
    final /* synthetic */ Context val$appContext;

    CallableC0040r(WeakReference weakReference, Context context, int i) {
        this.rb = weakReference;
        this.val$appContext = context;
        this.sb = i;
    }

    @Override // java.util.concurrent.Callable
    public X<C0035m> call() {
        Context context = (Context) this.rb.get();
        if (context == null) {
            context = this.val$appContext;
        }
        return C0047y.b(context, this.sb);
    }
}
