package com.airbnb.lottie.a.b;

import androidx.annotation.Nullable;
import com.airbnb.lottie.e.a;
import com.airbnb.lottie.e.b;
import com.airbnb.lottie.e.j;
import java.util.Collections;

/* compiled from: ValueCallbackKeyframeAnimation */
public class q<K, A> extends b<K, A> {
    private final b<A> frameInfo;
    private final A rd;

    public q(j<A> jVar) {
        this(jVar, null);
    }

    public q(j<A> jVar, @Nullable A a2) {
        super(Collections.emptyList());
        this.frameInfo = new b<>();
        a(jVar);
        this.rd = a2;
    }

    /* access modifiers changed from: package-private */
    @Override // com.airbnb.lottie.a.b.b
    public float K() {
        return 1.0f;
    }

    /* access modifiers changed from: package-private */
    @Override // com.airbnb.lottie.a.b.b
    public A a(a<K> aVar, float f) {
        return getValue();
    }

    @Override // com.airbnb.lottie.a.b.b
    public A getValue() {
        j<A> jVar = this.bd;
        A a2 = this.rd;
        return jVar.b(0.0f, 0.0f, a2, a2, getProgress(), getProgress(), getProgress());
    }

    @Override // com.airbnb.lottie.a.b.b
    public void tb() {
        if (this.bd != null) {
            super.tb();
        }
    }
}
