package com.airbnb.lottie.model;

import android.graphics.PointF;
import androidx.annotation.RestrictTo;

@RestrictTo({RestrictTo.Scope.LIBRARY})
/* compiled from: CubicCurveData */
public class a {
    private final PointF Nd;
    private final PointF Od;
    private final PointF Pd;

    public a() {
        this.Nd = new PointF();
        this.Od = new PointF();
        this.Pd = new PointF();
    }

    public a(PointF pointF, PointF pointF2, PointF pointF3) {
        this.Nd = pointF;
        this.Od = pointF2;
        this.Pd = pointF3;
    }

    public PointF Ab() {
        return this.Nd;
    }

    public PointF Bb() {
        return this.Od;
    }

    public PointF Cb() {
        return this.Pd;
    }

    public void c(float f, float f2) {
        this.Nd.set(f, f2);
    }

    public void d(float f, float f2) {
        this.Od.set(f, f2);
    }

    public void e(float f, float f2) {
        this.Pd.set(f, f2);
    }
}
