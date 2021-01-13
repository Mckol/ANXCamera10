package com.airbnb.lottie.e;

import android.graphics.PointF;
import android.view.animation.Interpolator;
import com.airbnb.lottie.d.g;

/* compiled from: LottieInterpolatedPointValue */
public class e extends f<PointF> {
    private final PointF hd = new PointF();

    public e(PointF pointF, PointF pointF2) {
        super(pointF, pointF2);
    }

    public e(PointF pointF, PointF pointF2, Interpolator interpolator) {
        super(pointF, pointF2, interpolator);
    }

    /* access modifiers changed from: package-private */
    public PointF a(PointF pointF, PointF pointF2, float f) {
        this.hd.set(g.lerp(pointF.x, pointF2.x, f), g.lerp(pointF.y, pointF2.y, f));
        return this.hd;
    }
}
