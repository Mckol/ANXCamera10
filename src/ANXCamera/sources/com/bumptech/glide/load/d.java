package com.bumptech.glide.load;

import android.content.Context;
import android.support.annotation.NonNull;
import com.bumptech.glide.load.engine.A;
import java.security.MessageDigest;
import java.util.Arrays;
import java.util.Collection;

/* compiled from: MultiTransformation */
public class d<T> implements j<T> {
    private final Collection<? extends j<T>> Ld;

    public d(@NonNull Collection<? extends j<T>> collection) {
        if (!collection.isEmpty()) {
            this.Ld = collection;
            return;
        }
        throw new IllegalArgumentException("MultiTransformation must contain at least one Transformation");
    }

    @SafeVarargs
    public d(@NonNull j<T>... jVarArr) {
        if (jVarArr.length != 0) {
            this.Ld = Arrays.asList(jVarArr);
            return;
        }
        throw new IllegalArgumentException("MultiTransformation must contain at least one Transformation");
    }

    public boolean equals(Object obj) {
        if (obj instanceof d) {
            return this.Ld.equals(((d) obj).Ld);
        }
        return false;
    }

    public int hashCode() {
        return this.Ld.hashCode();
    }

    @NonNull
    public A<T> transform(@NonNull Context context, @NonNull A<T> a2, int i, int i2) {
        A<T> a3 = a2;
        for (j transform : this.Ld) {
            A<T> transform2 = transform.transform(context, a3, i, i2);
            if (a3 != null && !a3.equals(a2) && !a3.equals(transform2)) {
                a3.recycle();
            }
            a3 = transform2;
        }
        return a3;
    }

    public void updateDiskCacheKey(@NonNull MessageDigest messageDigest) {
        for (j updateDiskCacheKey : this.Ld) {
            updateDiskCacheKey.updateDiskCacheKey(messageDigest);
        }
    }
}
