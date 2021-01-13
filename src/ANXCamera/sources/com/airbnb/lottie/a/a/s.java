package com.airbnb.lottie.a.a;

import android.graphics.Path;
import android.graphics.PointF;
import android.graphics.RectF;
import androidx.annotation.Nullable;
import com.airbnb.lottie.Q;
import com.airbnb.lottie.W;
import com.airbnb.lottie.a.b.b;
import com.airbnb.lottie.d.g;
import com.airbnb.lottie.e.j;
import com.airbnb.lottie.model.content.ShapeTrimPath;
import com.airbnb.lottie.model.content.f;
import com.airbnb.lottie.model.d;
import com.airbnb.lottie.model.layer.c;
import java.util.List;

/* compiled from: RectangleContent */
public class s implements b.a, l, p {
    private final Q La;
    private final b<?, Float> Mc;
    private final boolean hidden;
    private final String name;
    private final Path path = new Path();
    private final RectF rect = new RectF();
    private final b<?, PointF> uc;
    private final b<?, PointF> vc;
    private c xc = new c();
    private boolean yc;

    public s(Q q, c cVar, f fVar) {
        this.name = fVar.getName();
        this.hidden = fVar.isHidden();
        this.La = q;
        this.vc = fVar.getPosition().F();
        this.uc = fVar.getSize().F();
        this.Mc = fVar.getCornerRadius().F();
        cVar.a(this.vc);
        cVar.a(this.uc);
        cVar.a(this.Mc);
        this.vc.b(this);
        this.uc.b(this);
        this.Mc.b(this);
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
        g.a(dVar, i, list, dVar2, this);
    }

    @Override // com.airbnb.lottie.model.e
    public <T> void a(T t, @Nullable j<T> jVar) {
        if (t == W.Xj) {
            this.uc.a(jVar);
        } else if (t == W.POSITION) {
            this.vc.a(jVar);
        } else if (t == W.Yj) {
            this.Mc.a(jVar);
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
        if (this.hidden) {
            this.yc = true;
            return this.path;
        }
        PointF value = this.uc.getValue();
        float f = value.x / 2.0f;
        float f2 = value.y / 2.0f;
        b<?, Float> bVar = this.Mc;
        float floatValue = bVar == null ? 0.0f : ((com.airbnb.lottie.a.b.d) bVar).getFloatValue();
        float min = Math.min(f, f2);
        if (floatValue > min) {
            floatValue = min;
        }
        PointF value2 = this.vc.getValue();
        this.path.moveTo(value2.x + f, (value2.y - f2) + floatValue);
        this.path.lineTo(value2.x + f, (value2.y + f2) - floatValue);
        int i = (floatValue > 0.0f ? 1 : (floatValue == 0.0f ? 0 : -1));
        if (i > 0) {
            RectF rectF = this.rect;
            float f3 = value2.x;
            float f4 = floatValue * 2.0f;
            float f5 = value2.y;
            rectF.set((f3 + f) - f4, (f5 + f2) - f4, f3 + f, f5 + f2);
            this.path.arcTo(this.rect, 0.0f, 90.0f, false);
        }
        this.path.lineTo((value2.x - f) + floatValue, value2.y + f2);
        if (i > 0) {
            RectF rectF2 = this.rect;
            float f6 = value2.x;
            float f7 = value2.y;
            float f8 = floatValue * 2.0f;
            rectF2.set(f6 - f, (f7 + f2) - f8, (f6 - f) + f8, f7 + f2);
            this.path.arcTo(this.rect, 90.0f, 90.0f, false);
        }
        this.path.lineTo(value2.x - f, (value2.y - f2) + floatValue);
        if (i > 0) {
            RectF rectF3 = this.rect;
            float f9 = value2.x;
            float f10 = value2.y;
            float f11 = floatValue * 2.0f;
            rectF3.set(f9 - f, f10 - f2, (f9 - f) + f11, (f10 - f2) + f11);
            this.path.arcTo(this.rect, 180.0f, 90.0f, false);
        }
        this.path.lineTo((value2.x + f) - floatValue, value2.y - f2);
        if (i > 0) {
            RectF rectF4 = this.rect;
            float f12 = value2.x;
            float f13 = floatValue * 2.0f;
            float f14 = value2.y;
            rectF4.set((f12 + f) - f13, f14 - f2, f12 + f, (f14 - f2) + f13);
            this.path.arcTo(this.rect, 270.0f, 90.0f, false);
        }
        this.path.close();
        this.xc.a(this.path);
        this.yc = true;
        return this.path;
    }
}
