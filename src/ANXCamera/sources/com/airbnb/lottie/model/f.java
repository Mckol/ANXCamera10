package com.airbnb.lottie.model;

import androidx.annotation.Nullable;
import androidx.annotation.RestrictTo;
import androidx.annotation.VisibleForTesting;
import androidx.collection.LruCache;
import com.airbnb.lottie.C0035m;

@RestrictTo({RestrictTo.Scope.LIBRARY})
/* compiled from: LottieCompositionCache */
public class f {
    private static final f INSTANCE = new f();
    private final LruCache<String, C0035m> cache = new LruCache<>(20);

    @VisibleForTesting
    f() {
    }

    public static f getInstance() {
        return INSTANCE;
    }

    public void a(@Nullable String str, C0035m mVar) {
        if (str != null) {
            this.cache.put(str, mVar);
        }
    }

    public void clear() {
        this.cache.evictAll();
    }

    @Nullable
    public C0035m get(@Nullable String str) {
        if (str == null) {
            return null;
        }
        return this.cache.get(str);
    }

    public void resize(int i) {
        this.cache.resize(i);
    }
}
