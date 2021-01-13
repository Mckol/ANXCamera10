package com.airbnb.lottie.model.layer;

import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.RectF;
import androidx.annotation.FloatRange;
import androidx.annotation.Nullable;
import androidx.collection.LongSparseArray;
import com.airbnb.lottie.C0027e;
import com.airbnb.lottie.C0035m;
import com.airbnb.lottie.Q;
import com.airbnb.lottie.W;
import com.airbnb.lottie.a.b.b;
import com.airbnb.lottie.a.b.q;
import com.airbnb.lottie.d.h;
import com.airbnb.lottie.e.j;
import com.airbnb.lottie.model.d;
import java.util.ArrayList;
import java.util.List;

/* compiled from: CompositionLayer */
public class e extends c {
    @Nullable
    private b<Float, Float> Qe;
    private final RectF Re = new RectF();
    private Paint Se = new Paint();
    @Nullable
    private Boolean Te;
    @Nullable
    private Boolean Ue;
    private final List<c> layers = new ArrayList();
    private final RectF rect = new RectF();

    public e(Q q, Layer layer, List<Layer> list, C0035m mVar) {
        super(q, layer);
        int i;
        c cVar;
        com.airbnb.lottie.model.a.b pc = layer.pc();
        if (pc != null) {
            this.Qe = pc.F();
            a(this.Qe);
            this.Qe.b(this);
        } else {
            this.Qe = null;
        }
        LongSparseArray longSparseArray = new LongSparseArray(mVar.getLayers().size());
        int size = list.size() - 1;
        c cVar2 = null;
        while (true) {
            if (size < 0) {
                break;
            }
            Layer layer2 = list.get(size);
            c a2 = c.a(layer2, q, mVar);
            if (a2 != null) {
                longSparseArray.put(a2.cc().getId(), a2);
                if (cVar2 != null) {
                    cVar2.b(a2);
                    cVar2 = null;
                } else {
                    this.layers.add(0, a2);
                    int i2 = d.gf[layer2.hc().ordinal()];
                    if (i2 == 1 || i2 == 2) {
                        cVar2 = a2;
                    }
                }
            }
            size--;
        }
        for (i = 0; i < longSparseArray.size(); i++) {
            c cVar3 = (c) longSparseArray.get(longSparseArray.keyAt(i));
            if (!(cVar3 == null || (cVar = (c) longSparseArray.get(cVar3.cc().getParentId())) == null)) {
                cVar3.c(cVar);
            }
        }
    }

    @Override // com.airbnb.lottie.model.layer.c, com.airbnb.lottie.a.a.f
    public void a(RectF rectF, Matrix matrix, boolean z) {
        super.a(rectF, matrix, z);
        for (int size = this.layers.size() - 1; size >= 0; size--) {
            this.rect.set(0.0f, 0.0f, 0.0f, 0.0f);
            this.layers.get(size).a(this.rect, this.Je, true);
            rectF.union(this.rect);
        }
    }

    @Override // com.airbnb.lottie.model.layer.c, com.airbnb.lottie.model.e
    public <T> void a(T t, @Nullable j<T> jVar) {
        super.a(t, jVar);
        if (t != W.pk) {
            return;
        }
        if (jVar == null) {
            this.Qe = null;
            return;
        }
        this.Qe = new q(jVar);
        a(this.Qe);
    }

    /* access modifiers changed from: package-private */
    @Override // com.airbnb.lottie.model.layer.c
    public void b(Canvas canvas, Matrix matrix, int i) {
        C0027e.beginSection("CompositionLayer#draw");
        this.Re.set(0.0f, 0.0f, (float) this.Ke.jc(), (float) this.Ke.ic());
        matrix.mapRect(this.Re);
        boolean z = this.La.ga() && this.layers.size() > 1 && i != 255;
        if (z) {
            this.Se.setAlpha(i);
            h.a(canvas, this.Re, this.Se);
        } else {
            canvas.save();
        }
        if (z) {
            i = 255;
        }
        for (int size = this.layers.size() - 1; size >= 0; size--) {
            if (!this.Re.isEmpty() ? canvas.clipRect(this.Re) : true) {
                this.layers.get(size).a(canvas, matrix, i);
            }
        }
        canvas.restore();
        C0027e.D("CompositionLayer#draw");
    }

    /* access modifiers changed from: protected */
    @Override // com.airbnb.lottie.model.layer.c
    public void b(d dVar, int i, List<d> list, d dVar2) {
        for (int i2 = 0; i2 < this.layers.size(); i2++) {
            this.layers.get(i2).a(dVar, i, list, dVar2);
        }
    }

    public boolean ea() {
        if (this.Ue == null) {
            for (int size = this.layers.size() - 1; size >= 0; size--) {
                c cVar = this.layers.get(size);
                if (cVar instanceof h) {
                    if (cVar.dc()) {
                        this.Ue = true;
                        return true;
                    }
                } else if ((cVar instanceof e) && ((e) cVar).ea()) {
                    this.Ue = true;
                    return true;
                }
            }
            this.Ue = false;
        }
        return this.Ue.booleanValue();
    }

    public boolean fa() {
        if (this.Te == null) {
            if (ec()) {
                this.Te = true;
                return true;
            }
            for (int size = this.layers.size() - 1; size >= 0; size--) {
                if (this.layers.get(size).ec()) {
                    this.Te = true;
                    return true;
                }
            }
            this.Te = false;
        }
        return this.Te.booleanValue();
    }

    @Override // com.airbnb.lottie.model.layer.c
    public void setProgress(@FloatRange(from = 0.0d, to = 1.0d) float f) {
        super.setProgress(f);
        if (this.Qe != null) {
            f = ((this.Qe.getValue().floatValue() * this.Ke.aa().getFrameRate()) - this.Ke.aa().hb()) / (this.La.aa().bb() + 0.01f);
        }
        if (this.Ke.qc() != 0.0f) {
            f /= this.Ke.qc();
        }
        if (this.Qe == null) {
            f -= this.Ke.nc();
        }
        for (int size = this.layers.size() - 1; size >= 0; size--) {
            this.layers.get(size).setProgress(f);
        }
    }
}
