package com.airbnb.lottie.a.b;

import android.graphics.Path;
import android.graphics.PathMeasure;
import android.graphics.PointF;
import com.airbnb.lottie.e.a;
import java.util.List;

/* compiled from: PathKeyframeAnimation */
public class j extends g<PointF> {
    private final PointF hd = new PointF();
    private i jd;
    private PathMeasure kd = new PathMeasure();
    private final float[] pos = new float[2];

    public j(List<? extends a<PointF>> list) {
        super(list);
    }

    /* JADX WARN: Type inference failed for: r5v0, types: [T, java.lang.Object] */
    /* JADX WARN: Type inference failed for: r6v0, types: [T, java.lang.Object] */
    @Override // com.airbnb.lottie.a.b.b
    public PointF a(a<PointF> aVar, float f) {
        A b2;
        i iVar = (i) aVar;
        Path path = iVar.getPath();
        if (path == null) {
            return aVar.startValue;
        }
        com.airbnb.lottie.e.j<A> jVar = this.bd;
        if (jVar != null && (b2 = jVar.b(iVar.lb, iVar.mb.floatValue(), iVar.startValue, iVar.endValue, sb(), f, getProgress())) != null) {
            return b2;
        }
        if (this.jd != iVar) {
            this.kd.setPath(path, false);
            this.jd = iVar;
        }
        PathMeasure pathMeasure = this.kd;
        pathMeasure.getPosTan(f * pathMeasure.getLength(), this.pos, null);
        PointF pointF = this.hd;
        float[] fArr = this.pos;
        pointF.set(fArr[0], fArr[1]);
        return this.hd;
    }
}
