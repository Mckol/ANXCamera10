package com.airbnb.lottie.a.b;

import android.graphics.PointF;
import com.airbnb.lottie.e.a;
import java.util.Collections;

/* compiled from: SplitDimensionPathKeyframeAnimation */
public class n extends b<PointF, PointF> {
    private final PointF hd = new PointF();
    private final b<Float, Float> od;
    private final b<Float, Float> pd;

    public n(b<Float, Float> bVar, b<Float, Float> bVar2) {
        super(Collections.emptyList());
        this.od = bVar;
        this.pd = bVar2;
        setProgress(getProgress());
    }

    /* access modifiers changed from: package-private */
    @Override // com.airbnb.lottie.a.b.b
    public PointF a(a<PointF> aVar, float f) {
        return this.hd;
    }

    @Override // com.airbnb.lottie.a.b.b
    public PointF getValue() {
        return a((a<PointF>) null, 0.0f);
    }

    @Override // com.airbnb.lottie.a.b.b
    public void setProgress(float f) {
        this.od.setProgress(f);
        this.pd.setProgress(f);
        this.hd.set(this.od.getValue().floatValue(), this.pd.getValue().floatValue());
        for (int i = 0; i < this.listeners.size(); i++) {
            this.listeners.get(i).G();
        }
    }
}
