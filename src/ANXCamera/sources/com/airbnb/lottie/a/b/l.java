package com.airbnb.lottie.a.b;

import com.airbnb.lottie.d.g;
import com.airbnb.lottie.e.a;
import com.airbnb.lottie.e.j;
import com.airbnb.lottie.e.k;
import java.util.List;

/* compiled from: ScaleKeyframeAnimation */
public class l extends g<k> {
    private final k ld = new k();

    public l(List<a<k>> list) {
        super(list);
    }

    @Override // com.airbnb.lottie.a.b.b
    public k a(a<k> aVar, float f) {
        T t;
        A b2;
        T t2 = aVar.startValue;
        if (t2 == null || (t = aVar.endValue) == null) {
            throw new IllegalStateException("Missing values for keyframe.");
        }
        T t3 = t2;
        T t4 = t;
        j<A> jVar = this.bd;
        if (jVar != null && (b2 = jVar.b(aVar.lb, aVar.mb.floatValue(), t3, t4, f, sb(), getProgress())) != null) {
            return b2;
        }
        this.ld.set(g.lerp(t3.getScaleX(), t4.getScaleX(), f), g.lerp(t3.getScaleY(), t4.getScaleY(), f));
        return this.ld;
    }
}
