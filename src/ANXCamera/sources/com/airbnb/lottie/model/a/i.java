package com.airbnb.lottie.model.a;

import android.graphics.PointF;
import com.airbnb.lottie.a.b.b;
import com.airbnb.lottie.a.b.n;
import com.airbnb.lottie.e.a;
import java.util.List;

/* compiled from: AnimatableSplitDimensionPathValue */
public class i implements m<PointF, PointF> {
    private final b Zd;
    private final b _d;

    public i(b bVar, b bVar2) {
        this.Zd = bVar;
        this._d = bVar2;
    }

    @Override // com.airbnb.lottie.model.a.m
    public b<PointF, PointF> F() {
        return new n(this.Zd.F(), this._d.F());
    }

    @Override // com.airbnb.lottie.model.a.m
    public List<a<PointF>> getKeyframes() {
        throw new UnsupportedOperationException("Cannot call getKeyframes on AnimatableSplitDimensionPathValue.");
    }

    @Override // com.airbnb.lottie.model.a.m
    public boolean isStatic() {
        return this.Zd.isStatic() && this._d.isStatic();
    }
}
