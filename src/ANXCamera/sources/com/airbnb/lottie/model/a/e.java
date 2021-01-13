package com.airbnb.lottie.model.a;

import android.graphics.PointF;
import com.airbnb.lottie.a.b.b;
import com.airbnb.lottie.a.b.j;
import com.airbnb.lottie.a.b.k;
import com.airbnb.lottie.e.a;
import java.util.Collections;
import java.util.List;

/* compiled from: AnimatablePathValue */
public class e implements m<PointF, PointF> {
    private final List<a<PointF>> Uc;

    public e() {
        this.Uc = Collections.singletonList(new a(new PointF(0.0f, 0.0f)));
    }

    public e(List<a<PointF>> list) {
        this.Uc = list;
    }

    @Override // com.airbnb.lottie.model.a.m
    public b<PointF, PointF> F() {
        return this.Uc.get(0).isStatic() ? new k(this.Uc) : new j(this.Uc);
    }

    @Override // com.airbnb.lottie.model.a.m
    public List<a<PointF>> getKeyframes() {
        return this.Uc;
    }

    @Override // com.airbnb.lottie.model.a.m
    public boolean isStatic() {
        return this.Uc.size() == 1 && this.Uc.get(0).isStatic();
    }
}
