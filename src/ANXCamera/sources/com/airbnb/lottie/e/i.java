package com.airbnb.lottie.e;

import android.graphics.PointF;
import androidx.annotation.NonNull;
import com.airbnb.lottie.d.g;

/* compiled from: LottieRelativePointValueCallback */
public class i extends j<PointF> {
    private final PointF hd = new PointF();

    public i() {
    }

    public i(@NonNull PointF pointF) {
        super(pointF);
    }

    @Override // com.airbnb.lottie.e.j
    public final PointF a(b<PointF> bVar) {
        this.hd.set(g.lerp(bVar.getStartValue().x, bVar.getEndValue().x, bVar.Ac()), g.lerp(bVar.getStartValue().y, bVar.getEndValue().y, bVar.Ac()));
        PointF b2 = b(bVar);
        this.hd.offset(b2.x, b2.y);
        return this.hd;
    }

    public PointF b(b<PointF> bVar) {
        T t = this.value;
        if (t != null) {
            return t;
        }
        throw new IllegalArgumentException("You must provide a static value in the constructor , call setValue, or override getValue.");
    }
}
