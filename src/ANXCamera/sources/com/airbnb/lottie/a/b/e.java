package com.airbnb.lottie.a.b;

import com.airbnb.lottie.e.a;
import com.airbnb.lottie.model.content.c;
import java.util.List;

/* compiled from: GradientColorKeyframeAnimation */
public class e extends g<c> {
    private final c gd;

    public e(List<a<c>> list) {
        super(list);
        int i = 0;
        T t = list.get(0).startValue;
        i = t != null ? t.getSize() : i;
        this.gd = new c(new float[i], new int[i]);
    }

    /* access modifiers changed from: package-private */
    @Override // com.airbnb.lottie.a.b.b
    public c a(a<c> aVar, float f) {
        this.gd.a(aVar.startValue, aVar.endValue, f);
        return this.gd;
    }
}
