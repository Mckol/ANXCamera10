package com.airbnb.lottie.a.a;

import android.graphics.Path;
import com.airbnb.lottie.Q;
import com.airbnb.lottie.a.b.b;
import com.airbnb.lottie.model.content.ShapeTrimPath;
import com.airbnb.lottie.model.content.k;
import com.airbnb.lottie.model.layer.c;
import java.util.List;

/* compiled from: ShapeContent */
public class u implements p, b.a {
    private final Q La;
    private final b<?, Path> Qc;
    private final boolean hidden;
    private final String name;
    private final Path path = new Path();
    private c xc = new c();
    private boolean yc;

    public u(Q q, c cVar, k kVar) {
        this.name = kVar.getName();
        this.hidden = kVar.isHidden();
        this.La = q;
        this.Qc = kVar.bc().F();
        cVar.a(this.Qc);
        this.Qc.b(this);
    }

    private void invalidate() {
        this.yc = false;
        this.La.invalidateSelf();
    }

    @Override // com.airbnb.lottie.a.b.b.a
    public void G() {
        invalidate();
    }

    @Override // com.airbnb.lottie.a.a.d
    public void a(List<d> list, List<d> list2) {
        for (int i = 0; i < list.size(); i++) {
            d dVar = list.get(i);
            if (dVar instanceof w) {
                w wVar = (w) dVar;
                if (wVar.getType() == ShapeTrimPath.Type.SIMULTANEOUSLY) {
                    this.xc.a(wVar);
                    wVar.a(this);
                }
            }
        }
    }

    @Override // com.airbnb.lottie.a.a.d
    public String getName() {
        return this.name;
    }

    @Override // com.airbnb.lottie.a.a.p
    public Path getPath() {
        if (this.yc) {
            return this.path;
        }
        this.path.reset();
        if (this.hidden) {
            this.yc = true;
            return this.path;
        }
        this.path.set(this.Qc.getValue());
        this.path.setFillType(Path.FillType.EVEN_ODD);
        this.xc.a(this.path);
        this.yc = true;
        return this.path;
    }
}
