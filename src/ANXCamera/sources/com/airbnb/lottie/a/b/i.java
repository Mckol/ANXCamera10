package com.airbnb.lottie.a.b;

import android.graphics.Path;
import android.graphics.PointF;
import androidx.annotation.Nullable;
import com.airbnb.lottie.C0035m;
import com.airbnb.lottie.d.h;
import com.airbnb.lottie.e.a;

/* compiled from: PathKeyframe */
public class i extends a<PointF> {
    @Nullable
    private Path path;
    private final a<PointF> pg;

    public i(C0035m mVar, a<PointF> aVar) {
        super(mVar, aVar.startValue, aVar.endValue, aVar.interpolator, aVar.lb, aVar.mb);
        this.pg = aVar;
        zc();
    }

    /* access modifiers changed from: package-private */
    @Nullable
    public Path getPath() {
        return this.path;
    }

    public void zc() {
        T t;
        T t2 = this.endValue;
        boolean z = (t2 == null || (t = this.startValue) == null || !t.equals(((PointF) t2).x, ((PointF) t2).y)) ? false : true;
        T t3 = this.endValue;
        if (t3 != null && !z) {
            a<PointF> aVar = this.pg;
            this.path = h.a(this.startValue, t3, aVar.lg, aVar.mg);
        }
    }
}
