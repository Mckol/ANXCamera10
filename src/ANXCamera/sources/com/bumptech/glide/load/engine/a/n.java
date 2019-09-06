package com.bumptech.glide.load.engine.a;

import android.annotation.SuppressLint;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import com.bumptech.glide.load.c;
import com.bumptech.glide.load.engine.A;
import com.bumptech.glide.load.engine.a.o.a;
import com.bumptech.glide.util.f;

/* compiled from: LruResourceCache */
public class n extends f<c, A<?>> implements o {
    private a listener;

    public n(long j) {
        super(j);
    }

    @Nullable
    public /* bridge */ /* synthetic */ A a(@NonNull c cVar) {
        return (A) super.remove(cVar);
    }

    @Nullable
    public /* bridge */ /* synthetic */ A a(@NonNull c cVar, @Nullable A a2) {
        return (A) super.put(cVar, a2);
    }

    public void a(@NonNull a aVar) {
        this.listener = aVar;
    }

    /* access modifiers changed from: protected */
    public void b(@NonNull c cVar, @Nullable A<?> a2) {
        a aVar = this.listener;
        if (aVar != null && a2 != null) {
            aVar.b(a2);
        }
    }

    /* access modifiers changed from: protected */
    /* renamed from: h */
    public int r(@Nullable A<?> a2) {
        return a2 == null ? super.r(null) : a2.getSize();
    }

    @SuppressLint({"InlinedApi"})
    public void trimMemory(int i) {
        if (i >= 40) {
            G();
        } else if (i >= 20 || i == 15) {
            h(getMaxSize() / 2);
        }
    }
}
