package com.airbnb.lottie.e;

import androidx.annotation.Nullable;
import androidx.annotation.RestrictTo;
import com.airbnb.lottie.a.b.b;

/* compiled from: LottieValueCallback */
public class j<T> {
    @Nullable
    private b<?, ?> animation;
    private final b<T> frameInfo = new b<>();
    @Nullable
    protected T value = null;

    public j() {
    }

    public j(@Nullable T t) {
        this.value = t;
    }

    @Nullable
    public T a(b<T> bVar) {
        return this.value;
    }

    @Nullable
    @RestrictTo({RestrictTo.Scope.LIBRARY})
    public final T b(float f, float f2, T t, T t2, float f3, float f4, float f5) {
        return a(this.frameInfo.a(f, f2, t, t2, f3, f4, f5));
    }

    @RestrictTo({RestrictTo.Scope.LIBRARY})
    public final void c(@Nullable b<?, ?> bVar) {
        this.animation = bVar;
    }

    public final void setValue(@Nullable T t) {
        this.value = t;
        b<?, ?> bVar = this.animation;
        if (bVar != null) {
            bVar.tb();
        }
    }
}
