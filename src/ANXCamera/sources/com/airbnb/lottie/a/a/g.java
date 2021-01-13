package com.airbnb.lottie.a.a;

import android.graphics.Path;
import android.graphics.PointF;
import androidx.annotation.Nullable;
import com.airbnb.lottie.Q;
import com.airbnb.lottie.W;
import com.airbnb.lottie.a.b.b;
import com.airbnb.lottie.e.j;
import com.airbnb.lottie.model.content.ShapeTrimPath;
import com.airbnb.lottie.model.content.a;
import com.airbnb.lottie.model.d;
import com.airbnb.lottie.model.layer.c;
import java.util.List;

/* compiled from: EllipseContent */
public class g implements p, b.a, l {
    private static final float zc = 0.55228f;
    private final Q La;
    private final String name;
    private final Path path = new Path();
    private final b<?, PointF> uc;
    private final b<?, PointF> vc;
    private final a wc;
    private c xc = new c();
    private boolean yc;

    public g(Q q, c cVar, a aVar) {
        this.name = aVar.getName();
        this.La = q;
        this.uc = aVar.getSize().F();
        this.vc = aVar.getPosition().F();
        this.wc = aVar;
        cVar.a(this.uc);
        cVar.a(this.vc);
        this.uc.b(this);
        this.vc.b(this);
    }

    private void invalidate() {
        this.yc = false;
        this.La.invalidateSelf();
    }

    @Override // com.airbnb.lottie.a.b.b.a
    public void G() {
        invalidate();
    }

    @Override // com.airbnb.lottie.model.e
    public void a(d dVar, int i, List<d> list, d dVar2) {
        com.airbnb.lottie.d.g.a(dVar, i, list, dVar2, this);
    }

    @Override // com.airbnb.lottie.model.e
    public <T> void a(T t, @Nullable j<T> jVar) {
        if (t == W.Wj) {
            this.uc.a(jVar);
        } else if (t == W.POSITION) {
            this.vc.a(jVar);
        }
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
        if (this.wc.isHidden()) {
            this.yc = true;
            return this.path;
        }
        PointF value = this.uc.getValue();
        float f = value.x / 2.0f;
        float f2 = value.y / 2.0f;
        float f3 = f * zc;
        float f4 = zc * f2;
        this.path.reset();
        if (this.wc.isReversed()) {
            float f5 = -f2;
            this.path.moveTo(0.0f, f5);
            float f6 = 0.0f - f3;
            float f7 = -f;
            float f8 = 0.0f - f4;
            this.path.cubicTo(f6, f5, f7, f8, f7, 0.0f);
            float f9 = f4 + 0.0f;
            this.path.cubicTo(f7, f9, f6, f2, 0.0f, f2);
            float f10 = f3 + 0.0f;
            this.path.cubicTo(f10, f2, f, f9, f, 0.0f);
            this.path.cubicTo(f, f8, f10, f5, 0.0f, f5);
        } else {
            float f11 = -f2;
            this.path.moveTo(0.0f, f11);
            float f12 = f3 + 0.0f;
            float f13 = 0.0f - f4;
            this.path.cubicTo(f12, f11, f, f13, f, 0.0f);
            float f14 = f4 + 0.0f;
            this.path.cubicTo(f, f14, f12, f2, 0.0f, f2);
            float f15 = 0.0f - f3;
            float f16 = -f;
            this.path.cubicTo(f15, f2, f16, f14, f16, 0.0f);
            this.path.cubicTo(f16, f13, f15, f11, 0.0f, f11);
        }
        PointF value2 = this.vc.getValue();
        this.path.offset(value2.x, value2.y);
        this.path.close();
        this.xc.a(this.path);
        this.yc = true;
        return this.path;
    }
}
