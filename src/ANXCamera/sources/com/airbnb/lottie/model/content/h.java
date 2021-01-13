package com.airbnb.lottie.model.content;

import android.graphics.PointF;
import androidx.annotation.FloatRange;
import com.airbnb.lottie.d.d;
import com.airbnb.lottie.d.g;
import com.airbnb.lottie.model.a;
import java.util.ArrayList;
import java.util.List;

/* compiled from: ShapeData */
public class h {
    private boolean closed;
    private final List<a> se;
    private PointF te;

    public h() {
        this.se = new ArrayList();
    }

    public h(PointF pointF, boolean z, List<a> list) {
        this.te = pointF;
        this.closed = z;
        this.se = new ArrayList(list);
    }

    private void g(float f, float f2) {
        if (this.te == null) {
            this.te = new PointF();
        }
        this.te.set(f, f2);
    }

    public List<a> _b() {
        return this.se;
    }

    public void a(h hVar, h hVar2, @FloatRange(from = 0.0d, to = 1.0d) float f) {
        if (this.te == null) {
            this.te = new PointF();
        }
        this.closed = hVar.isClosed() || hVar2.isClosed();
        if (hVar._b().size() != hVar2._b().size()) {
            d.warning("Curves must have the same number of control points. Shape 1: " + hVar._b().size() + "\tShape 2: " + hVar2._b().size());
        }
        int min = Math.min(hVar._b().size(), hVar2._b().size());
        if (this.se.size() < min) {
            for (int size = this.se.size(); size < min; size++) {
                this.se.add(new a());
            }
        } else if (this.se.size() > min) {
            for (int size2 = this.se.size() - 1; size2 >= min; size2--) {
                List<a> list = this.se;
                list.remove(list.size() - 1);
            }
        }
        PointF ac = hVar.ac();
        PointF ac2 = hVar2.ac();
        g(g.lerp(ac.x, ac2.x, f), g.lerp(ac.y, ac2.y, f));
        for (int size3 = this.se.size() - 1; size3 >= 0; size3--) {
            a aVar = hVar._b().get(size3);
            a aVar2 = hVar2._b().get(size3);
            PointF Ab = aVar.Ab();
            PointF Bb = aVar.Bb();
            PointF Cb = aVar.Cb();
            PointF Ab2 = aVar2.Ab();
            PointF Bb2 = aVar2.Bb();
            PointF Cb2 = aVar2.Cb();
            this.se.get(size3).c(g.lerp(Ab.x, Ab2.x, f), g.lerp(Ab.y, Ab2.y, f));
            this.se.get(size3).d(g.lerp(Bb.x, Bb2.x, f), g.lerp(Bb.y, Bb2.y, f));
            this.se.get(size3).e(g.lerp(Cb.x, Cb2.x, f), g.lerp(Cb.y, Cb2.y, f));
        }
    }

    public PointF ac() {
        return this.te;
    }

    public boolean isClosed() {
        return this.closed;
    }

    public String toString() {
        return "ShapeData{numCurves=" + this.se.size() + "closed=" + this.closed + '}';
    }
}
