package com.airbnb.lottie.a.b;

import com.airbnb.lottie.d.g;
import com.airbnb.lottie.e.a;
import com.airbnb.lottie.e.j;
import java.util.List;

/* compiled from: IntegerKeyframeAnimation */
public class f extends g<Integer> {
    public f(List<a<Integer>> list) {
        super(list);
    }

    /* access modifiers changed from: package-private */
    @Override // com.airbnb.lottie.a.b.b
    public Integer a(a<Integer> aVar, float f) {
        return Integer.valueOf(b(aVar, f));
    }

    /* JADX WARN: Type inference failed for: r4v0, types: [T, java.lang.Object] */
    /* JADX WARN: Type inference failed for: r5v0, types: [T, java.lang.Object] */
    /* access modifiers changed from: package-private */
    public int b(a<Integer> aVar, float f) {
        A b2;
        if (aVar.startValue == null || aVar.endValue == null) {
            throw new IllegalStateException("Missing values for keyframe.");
        }
        j<A> jVar = this.bd;
        return (jVar == null || (b2 = jVar.b(aVar.lb, aVar.mb.floatValue(), aVar.startValue, aVar.endValue, f, sb(), getProgress())) == null) ? g.a(aVar.yc(), aVar.wc(), f) : b2.intValue();
    }

    public int getIntValue() {
        return b(C(), rb());
    }
}
