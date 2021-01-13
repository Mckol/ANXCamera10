package com.airbnb.lottie;

import androidx.annotation.Nullable;
import java.util.Arrays;

/* compiled from: LottieResult */
public final class X<V> {
    @Nullable
    private final Throwable exception;
    @Nullable
    private final V value;

    public X(V v) {
        this.value = v;
        this.exception = null;
    }

    public X(Throwable th) {
        this.exception = th;
        this.value = null;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof X)) {
            return false;
        }
        X x = (X) obj;
        if (getValue() != null && getValue().equals(x.getValue())) {
            return true;
        }
        if (getException() == null || x.getException() == null) {
            return false;
        }
        return getException().toString().equals(getException().toString());
    }

    @Nullable
    public Throwable getException() {
        return this.exception;
    }

    @Nullable
    public V getValue() {
        return this.value;
    }

    public int hashCode() {
        return Arrays.hashCode(new Object[]{getValue(), getException()});
    }
}
