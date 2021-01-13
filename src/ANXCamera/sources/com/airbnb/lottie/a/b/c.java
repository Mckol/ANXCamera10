package com.airbnb.lottie.a.b;

import com.airbnb.lottie.d.b;
import com.airbnb.lottie.d.g;
import com.airbnb.lottie.e.a;
import com.airbnb.lottie.e.j;
import java.util.List;

/* compiled from: ColorKeyframeAnimation */
public class c extends g<Integer> {
    public c(List<a<Integer>> list) {
        super(list);
    }

    /* access modifiers changed from: package-private */
    @Override // com.airbnb.lottie.a.b.b
    public Integer a(a<Integer> aVar, float f) {
        return Integer.valueOf(b(aVar, f));
    }

    public int b(a<Integer> aVar, float f) {
        A b2;
        T t = aVar.startValue;
        if (t == null || aVar.endValue == null) {
            throw new IllegalStateException("Missing values for keyframe.");
        }
        int intValue = t.intValue();
        int intValue2 = aVar.endValue.intValue();
        j<A> jVar = this.bd;
        return (jVar == null || (b2 = jVar.b(aVar.lb, aVar.mb.floatValue(), (A) Integer.valueOf(intValue), (A) Integer.valueOf(intValue2), f, sb(), getProgress())) == null) ? b.a(g.clamp(f, 0.0f, 1.0f), intValue, intValue2) : b2.intValue();
    }

    public int getIntValue() {
        return b(C(), rb());
    }
}
