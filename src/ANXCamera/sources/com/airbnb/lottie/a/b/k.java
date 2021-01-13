package com.airbnb.lottie.a.b;

import android.graphics.PointF;
import com.airbnb.lottie.e.a;
import com.airbnb.lottie.e.j;
import java.util.List;

/* compiled from: PointKeyframeAnimation */
public class k extends g<PointF> {
    private final PointF hd = new PointF();

    public k(List<a<PointF>> list) {
        super(list);
    }

    @Override // com.airbnb.lottie.a.b.b
    public PointF a(a<PointF> aVar, float f) {
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
        PointF pointF = this.hd;
        float f2 = ((PointF) t3).x;
        float f3 = ((PointF) t3).y;
        pointF.set(f2 + ((((PointF) t4).x - f2) * f), f3 + (f * (((PointF) t4).y - f3)));
        return this.hd;
    }
}
