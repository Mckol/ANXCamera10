package com.airbnb.lottie.a.a;

import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import androidx.annotation.Nullable;
import com.airbnb.lottie.Q;
import com.airbnb.lottie.a.a;
import com.airbnb.lottie.a.b.b;
import com.airbnb.lottie.a.b.p;
import com.airbnb.lottie.d.h;
import com.airbnb.lottie.model.a.l;
import com.airbnb.lottie.model.content.j;
import com.airbnb.lottie.model.d;
import com.airbnb.lottie.model.layer.c;
import java.util.ArrayList;
import java.util.List;

/* compiled from: ContentGroup */
public class e implements f, p, b.a, com.airbnb.lottie.model.e {
    private final Q La;
    private final List<d> contents;
    private final boolean hidden;
    private final Matrix matrix;
    private final String name;
    private final Path path;
    private Paint qc;
    private RectF rc;
    private final RectF rect;
    @Nullable
    private List<p> sc;
    @Nullable
    private p tc;

    public e(Q q, c cVar, j jVar) {
        this(q, cVar, jVar.getName(), jVar.isHidden(), a(q, cVar, jVar.getItems()), d(jVar.getItems()));
    }

    e(Q q, c cVar, String str, boolean z, List<d> list, @Nullable l lVar) {
        this.qc = new a();
        this.rc = new RectF();
        this.matrix = new Matrix();
        this.path = new Path();
        this.rect = new RectF();
        this.name = str;
        this.La = q;
        this.hidden = z;
        this.contents = list;
        if (lVar != null) {
            this.tc = lVar.F();
            this.tc.a(cVar);
            this.tc.a(this);
        }
        ArrayList arrayList = new ArrayList();
        for (int size = list.size() - 1; size >= 0; size--) {
            d dVar = list.get(size);
            if (dVar instanceof k) {
                arrayList.add((k) dVar);
            }
        }
        for (int size2 = arrayList.size() - 1; size2 >= 0; size2--) {
            ((k) arrayList.get(size2)).a(list.listIterator(list.size()));
        }
    }

    private boolean Qo() {
        int i = 0;
        for (int i2 = 0; i2 < this.contents.size(); i2++) {
            if ((this.contents.get(i2) instanceof f) && (i = i + 1) >= 2) {
                return true;
            }
        }
        return false;
    }

    private static List<d> a(Q q, c cVar, List<com.airbnb.lottie.model.content.b> list) {
        ArrayList arrayList = new ArrayList(list.size());
        for (int i = 0; i < list.size(); i++) {
            d a2 = list.get(i).a(q, cVar);
            if (a2 != null) {
                arrayList.add(a2);
            }
        }
        return arrayList;
    }

    @Nullable
    static l d(List<com.airbnb.lottie.model.content.b> list) {
        for (int i = 0; i < list.size(); i++) {
            com.airbnb.lottie.model.content.b bVar = list.get(i);
            if (bVar instanceof l) {
                return (l) bVar;
            }
        }
        return null;
    }

    @Override // com.airbnb.lottie.a.b.b.a
    public void G() {
        this.La.invalidateSelf();
    }

    @Override // com.airbnb.lottie.a.a.f
    public void a(Canvas canvas, Matrix matrix2, int i) {
        if (!this.hidden) {
            this.matrix.set(matrix2);
            p pVar = this.tc;
            if (pVar != null) {
                this.matrix.preConcat(pVar.getMatrix());
                i = (int) ((((((float) (this.tc.getOpacity() == null ? 100 : this.tc.getOpacity().getValue().intValue())) / 100.0f) * ((float) i)) / 255.0f) * 255.0f);
            }
            boolean z = this.La.ga() && Qo() && i != 255;
            if (z) {
                this.rc.set(0.0f, 0.0f, 0.0f, 0.0f);
                a(this.rc, this.matrix, true);
                this.qc.setAlpha(i);
                h.a(canvas, this.rc, this.qc);
            }
            if (z) {
                i = 255;
            }
            for (int size = this.contents.size() - 1; size >= 0; size--) {
                d dVar = this.contents.get(size);
                if (dVar instanceof f) {
                    ((f) dVar).a(canvas, this.matrix, i);
                }
            }
            if (z) {
                canvas.restore();
            }
        }
    }

    @Override // com.airbnb.lottie.a.a.f
    public void a(RectF rectF, Matrix matrix2, boolean z) {
        this.matrix.set(matrix2);
        p pVar = this.tc;
        if (pVar != null) {
            this.matrix.preConcat(pVar.getMatrix());
        }
        this.rect.set(0.0f, 0.0f, 0.0f, 0.0f);
        for (int size = this.contents.size() - 1; size >= 0; size--) {
            d dVar = this.contents.get(size);
            if (dVar instanceof f) {
                ((f) dVar).a(this.rect, this.matrix, z);
                rectF.union(this.rect);
            }
        }
    }

    @Override // com.airbnb.lottie.model.e
    public void a(d dVar, int i, List<d> list, d dVar2) {
        if (dVar.f(getName(), i)) {
            if (!"__container".equals(getName())) {
                dVar2 = dVar2.M(getName());
                if (dVar.d(getName(), i)) {
                    list.add(dVar2.a(this));
                }
            }
            if (dVar.g(getName(), i)) {
                int e2 = i + dVar.e(getName(), i);
                for (int i2 = 0; i2 < this.contents.size(); i2++) {
                    d dVar3 = this.contents.get(i2);
                    if (dVar3 instanceof com.airbnb.lottie.model.e) {
                        ((com.airbnb.lottie.model.e) dVar3).a(dVar, e2, list, dVar2);
                    }
                }
            }
        }
    }

    @Override // com.airbnb.lottie.model.e
    public <T> void a(T t, @Nullable com.airbnb.lottie.e.j<T> jVar) {
        p pVar = this.tc;
        if (pVar != null) {
            pVar.b(t, jVar);
        }
    }

    @Override // com.airbnb.lottie.a.a.d
    public void a(List<d> list, List<d> list2) {
        ArrayList arrayList = new ArrayList(list.size() + this.contents.size());
        arrayList.addAll(list);
        for (int size = this.contents.size() - 1; size >= 0; size--) {
            d dVar = this.contents.get(size);
            dVar.a(arrayList, this.contents.subList(0, size));
            arrayList.add(dVar);
        }
    }

    @Override // com.airbnb.lottie.a.a.d
    public String getName() {
        return this.name;
    }

    @Override // com.airbnb.lottie.a.a.p
    public Path getPath() {
        this.matrix.reset();
        p pVar = this.tc;
        if (pVar != null) {
            this.matrix.set(pVar.getMatrix());
        }
        this.path.reset();
        if (this.hidden) {
            return this.path;
        }
        for (int size = this.contents.size() - 1; size >= 0; size--) {
            d dVar = this.contents.get(size);
            if (dVar instanceof p) {
                this.path.addPath(((p) dVar).getPath(), this.matrix);
            }
        }
        return this.path;
    }

    /* access modifiers changed from: package-private */
    public List<p> pb() {
        if (this.sc == null) {
            this.sc = new ArrayList();
            for (int i = 0; i < this.contents.size(); i++) {
                d dVar = this.contents.get(i);
                if (dVar instanceof p) {
                    this.sc.add((p) dVar);
                }
            }
        }
        return this.sc;
    }

    /* access modifiers changed from: package-private */
    public Matrix qb() {
        p pVar = this.tc;
        if (pVar != null) {
            return pVar.getMatrix();
        }
        this.matrix.reset();
        return this.matrix;
    }
}
