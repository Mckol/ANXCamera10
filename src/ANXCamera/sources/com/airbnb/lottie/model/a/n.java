package com.airbnb.lottie.model.a;

import com.airbnb.lottie.e.a;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/* access modifiers changed from: package-private */
/* compiled from: BaseAnimatableValue */
public abstract class n<V, O> implements m<V, O> {
    final List<a<V>> Uc;

    n(V v) {
        this(Collections.singletonList(new a(v)));
    }

    n(List<a<V>> list) {
        this.Uc = list;
    }

    @Override // com.airbnb.lottie.model.a.m
    public List<a<V>> getKeyframes() {
        return this.Uc;
    }

    @Override // com.airbnb.lottie.model.a.m
    public boolean isStatic() {
        return this.Uc.isEmpty() || (this.Uc.size() == 1 && this.Uc.get(0).isStatic());
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        if (!this.Uc.isEmpty()) {
            sb.append("values=");
            sb.append(Arrays.toString(this.Uc.toArray()));
        }
        return sb.toString();
    }
}
