package com.airbnb.lottie.a.b;

import com.airbnb.lottie.d.g;
import com.airbnb.lottie.e.a;
import com.airbnb.lottie.e.j;
import java.util.List;

/* compiled from: FloatKeyframeAnimation */
public class d extends g<Float> {
    public d(List<a<Float>> list) {
        super(list);
    }

    /* access modifiers changed from: package-private */
    @Override // com.airbnb.lottie.a.b.b
    public Float a(a<Float> aVar, float f) {
        return Float.valueOf(c(aVar, f));
    }

    /* JADX WARN: Type inference failed for: r4v0, types: [T, java.lang.Object] */
    /* JADX WARN: Type inference failed for: r5v0, types: [T, java.lang.Object] */
    /* access modifiers changed from: package-private */
    public float c(a<Float> aVar, float f) {
        A b2;
        if (aVar.startValue == null || aVar.endValue == null) {
            throw new IllegalStateException("Missing values for keyframe.");
        }
        j<A> jVar = this.bd;
        return (jVar == null || (b2 = jVar.b(aVar.lb, aVar.mb.floatValue(), aVar.startValue, aVar.endValue, f, sb(), getProgress())) == null) ? g.lerp(aVar.xc(), aVar.vc(), f) : b2.floatValue();
    }

    public float getFloatValue() {
        return c(C(), rb());
    }
}
