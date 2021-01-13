package com.airbnb.lottie.a.a;

import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.DashPathEffect;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathMeasure;
import android.graphics.RectF;
import androidx.annotation.CallSuper;
import androidx.annotation.Nullable;
import com.airbnb.lottie.C0027e;
import com.airbnb.lottie.Q;
import com.airbnb.lottie.W;
import com.airbnb.lottie.a.b.b;
import com.airbnb.lottie.a.b.f;
import com.airbnb.lottie.a.b.q;
import com.airbnb.lottie.d.g;
import com.airbnb.lottie.d.h;
import com.airbnb.lottie.e.j;
import com.airbnb.lottie.model.a.d;
import com.airbnb.lottie.model.content.ShapeTrimPath;
import com.airbnb.lottie.model.layer.c;
import java.util.ArrayList;
import java.util.List;

/* compiled from: BaseStrokeContent */
public abstract class b implements b.a, l, f {
    private final Q La;
    private final Path Xb = new Path();
    private final List<a> Yb = new ArrayList();
    private final float[] Zb;
    private final com.airbnb.lottie.a.b.b<?, Float> _b;
    private final com.airbnb.lottie.a.b.b<?, Integer> bc;
    private final List<com.airbnb.lottie.a.b.b<?, Float>> dc;
    @Nullable
    private final com.airbnb.lottie.a.b.b<?, Float> ec;
    @Nullable
    private com.airbnb.lottie.a.b.b<ColorFilter, ColorFilter> fc;
    protected final c layer;
    final Paint paint = new com.airbnb.lottie.a.a(1);
    private final Path path = new Path();
    private final PathMeasure pm = new PathMeasure();
    private final RectF rect = new RectF();

    /* access modifiers changed from: private */
    /* compiled from: BaseStrokeContent */
    public static final class a {
        private final List<p> Vb;
        @Nullable
        private final w Wb;

        private a(@Nullable w wVar) {
            this.Vb = new ArrayList();
            this.Wb = wVar;
        }
    }

    b(Q q, c cVar, Paint.Cap cap, Paint.Join join, float f, d dVar, com.airbnb.lottie.model.a.b bVar, List<com.airbnb.lottie.model.a.b> list, com.airbnb.lottie.model.a.b bVar2) {
        this.La = q;
        this.layer = cVar;
        this.paint.setStyle(Paint.Style.STROKE);
        this.paint.setStrokeCap(cap);
        this.paint.setStrokeJoin(join);
        this.paint.setStrokeMiter(f);
        this.bc = dVar.F();
        this._b = bVar.F();
        if (bVar2 == null) {
            this.ec = null;
        } else {
            this.ec = bVar2.F();
        }
        this.dc = new ArrayList(list.size());
        this.Zb = new float[list.size()];
        for (int i = 0; i < list.size(); i++) {
            this.dc.add(list.get(i).F());
        }
        cVar.a(this.bc);
        cVar.a(this._b);
        for (int i2 = 0; i2 < this.dc.size(); i2++) {
            cVar.a(this.dc.get(i2));
        }
        com.airbnb.lottie.a.b.b<?, Float> bVar3 = this.ec;
        if (bVar3 != null) {
            cVar.a(bVar3);
        }
        this.bc.b(this);
        this._b.b(this);
        for (int i3 = 0; i3 < list.size(); i3++) {
            this.dc.get(i3).b(this);
        }
        com.airbnb.lottie.a.b.b<?, Float> bVar4 = this.ec;
        if (bVar4 != null) {
            bVar4.b(this);
        }
    }

    private void a(Canvas canvas, a aVar, Matrix matrix) {
        C0027e.beginSection("StrokeContent#applyTrimPath");
        if (aVar.Wb == null) {
            C0027e.D("StrokeContent#applyTrimPath");
            return;
        }
        this.path.reset();
        for (int size = aVar.Vb.size() - 1; size >= 0; size--) {
            this.path.addPath(((p) aVar.Vb.get(size)).getPath(), matrix);
        }
        this.pm.setPath(this.path, false);
        float length = this.pm.getLength();
        while (this.pm.nextContour()) {
            length += this.pm.getLength();
        }
        float floatValue = (aVar.Wb.getOffset().getValue().floatValue() * length) / 360.0f;
        float floatValue2 = ((aVar.Wb.getStart().getValue().floatValue() * length) / 100.0f) + floatValue;
        float floatValue3 = ((aVar.Wb.getEnd().getValue().floatValue() * length) / 100.0f) + floatValue;
        float f = 0.0f;
        for (int size2 = aVar.Vb.size() - 1; size2 >= 0; size2--) {
            this.Xb.set(((p) aVar.Vb.get(size2)).getPath());
            this.Xb.transform(matrix);
            this.pm.setPath(this.Xb, false);
            float length2 = this.pm.getLength();
            float f2 = 1.0f;
            if (floatValue3 > length) {
                float f3 = floatValue3 - length;
                if (f3 < f + length2 && f < f3) {
                    h.a(this.Xb, floatValue2 > length ? (floatValue2 - length) / length2 : 0.0f, Math.min(f3 / length2, 1.0f), 0.0f);
                    canvas.drawPath(this.Xb, this.paint);
                    f += length2;
                }
            }
            float f4 = f + length2;
            if (f4 >= floatValue2 && f <= floatValue3) {
                if (f4 > floatValue3 || floatValue2 >= f) {
                    float f5 = floatValue2 < f ? 0.0f : (floatValue2 - f) / length2;
                    if (floatValue3 <= f4) {
                        f2 = (floatValue3 - f) / length2;
                    }
                    h.a(this.Xb, f5, f2, 0.0f);
                    canvas.drawPath(this.Xb, this.paint);
                } else {
                    canvas.drawPath(this.Xb, this.paint);
                }
            }
            f += length2;
        }
        C0027e.D("StrokeContent#applyTrimPath");
    }

    private void c(Matrix matrix) {
        C0027e.beginSection("StrokeContent#applyDashPattern");
        if (this.dc.isEmpty()) {
            C0027e.D("StrokeContent#applyDashPattern");
            return;
        }
        float a2 = h.a(matrix);
        for (int i = 0; i < this.dc.size(); i++) {
            this.Zb[i] = this.dc.get(i).getValue().floatValue();
            if (i % 2 == 0) {
                float[] fArr = this.Zb;
                if (fArr[i] < 1.0f) {
                    fArr[i] = 1.0f;
                }
            } else {
                float[] fArr2 = this.Zb;
                if (fArr2[i] < 0.1f) {
                    fArr2[i] = 0.1f;
                }
            }
            float[] fArr3 = this.Zb;
            fArr3[i] = fArr3[i] * a2;
        }
        com.airbnb.lottie.a.b.b<?, Float> bVar = this.ec;
        this.paint.setPathEffect(new DashPathEffect(this.Zb, bVar == null ? 0.0f : a2 * bVar.getValue().floatValue()));
        C0027e.D("StrokeContent#applyDashPattern");
    }

    @Override // com.airbnb.lottie.a.b.b.a
    public void G() {
        this.La.invalidateSelf();
    }

    @Override // com.airbnb.lottie.a.a.f
    public void a(Canvas canvas, Matrix matrix, int i) {
        C0027e.beginSection("StrokeContent#draw");
        if (h.b(matrix)) {
            C0027e.D("StrokeContent#draw");
            return;
        }
        this.paint.setAlpha(g.clamp((int) ((((((float) i) / 255.0f) * ((float) ((f) this.bc).getIntValue())) / 100.0f) * 255.0f), 0, 255));
        this.paint.setStrokeWidth(((com.airbnb.lottie.a.b.d) this._b).getFloatValue() * h.a(matrix));
        if (this.paint.getStrokeWidth() <= 0.0f) {
            C0027e.D("StrokeContent#draw");
            return;
        }
        c(matrix);
        com.airbnb.lottie.a.b.b<ColorFilter, ColorFilter> bVar = this.fc;
        if (bVar != null) {
            this.paint.setColorFilter(bVar.getValue());
        }
        for (int i2 = 0; i2 < this.Yb.size(); i2++) {
            a aVar = this.Yb.get(i2);
            if (aVar.Wb != null) {
                a(canvas, aVar, matrix);
            } else {
                C0027e.beginSection("StrokeContent#buildPath");
                this.path.reset();
                for (int size = aVar.Vb.size() - 1; size >= 0; size--) {
                    this.path.addPath(((p) aVar.Vb.get(size)).getPath(), matrix);
                }
                C0027e.D("StrokeContent#buildPath");
                C0027e.beginSection("StrokeContent#drawPath");
                canvas.drawPath(this.path, this.paint);
                C0027e.D("StrokeContent#drawPath");
            }
        }
        C0027e.D("StrokeContent#draw");
    }

    @Override // com.airbnb.lottie.a.a.f
    public void a(RectF rectF, Matrix matrix, boolean z) {
        C0027e.beginSection("StrokeContent#getBounds");
        this.path.reset();
        for (int i = 0; i < this.Yb.size(); i++) {
            a aVar = this.Yb.get(i);
            for (int i2 = 0; i2 < aVar.Vb.size(); i2++) {
                this.path.addPath(((p) aVar.Vb.get(i2)).getPath(), matrix);
            }
        }
        this.path.computeBounds(this.rect, false);
        float floatValue = ((com.airbnb.lottie.a.b.d) this._b).getFloatValue();
        RectF rectF2 = this.rect;
        float f = floatValue / 2.0f;
        rectF2.set(rectF2.left - f, rectF2.top - f, rectF2.right + f, rectF2.bottom + f);
        rectF.set(this.rect);
        rectF.set(rectF.left - 1.0f, rectF.top - 1.0f, rectF.right + 1.0f, rectF.bottom + 1.0f);
        C0027e.D("StrokeContent#getBounds");
    }

    @Override // com.airbnb.lottie.model.e
    public void a(com.airbnb.lottie.model.d dVar, int i, List<com.airbnb.lottie.model.d> list, com.airbnb.lottie.model.d dVar2) {
        g.a(dVar, i, list, dVar2, this);
    }

    @Override // com.airbnb.lottie.model.e
    @CallSuper
    public <T> void a(T t, @Nullable j<T> jVar) {
        if (t == W.Tj) {
            this.bc.a(jVar);
        } else if (t == W.STROKE_WIDTH) {
            this._b.a(jVar);
        } else if (t != W.qk) {
        } else {
            if (jVar == null) {
                this.fc = null;
                return;
            }
            this.fc = new q(jVar);
            this.fc.b(this);
            this.layer.a(this.fc);
        }
    }

    @Override // com.airbnb.lottie.a.a.d
    public void a(List<d> list, List<d> list2) {
        w wVar = null;
        for (int size = list.size() - 1; size >= 0; size--) {
            d dVar = list.get(size);
            if (dVar instanceof w) {
                w wVar2 = (w) dVar;
                if (wVar2.getType() == ShapeTrimPath.Type.INDIVIDUALLY) {
                    wVar = wVar2;
                }
            }
        }
        if (wVar != null) {
            wVar.a(this);
        }
        a aVar = null;
        for (int size2 = list2.size() - 1; size2 >= 0; size2--) {
            d dVar2 = list2.get(size2);
            if (dVar2 instanceof w) {
                w wVar3 = (w) dVar2;
                if (wVar3.getType() == ShapeTrimPath.Type.INDIVIDUALLY) {
                    if (aVar != null) {
                        this.Yb.add(aVar);
                    }
                    aVar = new a(wVar3);
                    wVar3.a(this);
                }
            }
            if (dVar2 instanceof p) {
                if (aVar == null) {
                    aVar = new a(wVar);
                }
                aVar.Vb.add((p) dVar2);
            }
        }
        if (aVar != null) {
            this.Yb.add(aVar);
        }
    }
}
