package com.airbnb.lottie.a.a;

import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Path;
import android.graphics.RectF;
import androidx.annotation.Nullable;
import com.airbnb.lottie.Q;
import com.airbnb.lottie.W;
import com.airbnb.lottie.a.b.b;
import com.airbnb.lottie.a.b.p;
import com.airbnb.lottie.e.j;
import com.airbnb.lottie.model.content.g;
import com.airbnb.lottie.model.d;
import com.airbnb.lottie.model.layer.c;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.ListIterator;

/* compiled from: RepeaterContent */
public class t implements f, p, k, b.a, l {
    private final Q La;
    private final b<Float, Float> Nc;
    private final p Oc;
    private e Pc;
    private final boolean hidden;
    private final c layer;
    private final Matrix matrix = new Matrix();
    private final String name;
    private final b<Float, Float> offset;
    private final Path path = new Path();

    public t(Q q, c cVar, g gVar) {
        this.La = q;
        this.layer = cVar;
        this.name = gVar.getName();
        this.hidden = gVar.isHidden();
        this.Nc = gVar.getCopies().F();
        cVar.a(this.Nc);
        this.Nc.b(this);
        this.offset = gVar.getOffset().F();
        cVar.a(this.offset);
        this.offset.b(this);
        this.Oc = gVar.getTransform().F();
        this.Oc.a(cVar);
        this.Oc.a(this);
    }

    @Override // com.airbnb.lottie.a.b.b.a
    public void G() {
        this.La.invalidateSelf();
    }

    @Override // com.airbnb.lottie.a.a.f
    public void a(Canvas canvas, Matrix matrix2, int i) {
        float floatValue = this.Nc.getValue().floatValue();
        float floatValue2 = this.offset.getValue().floatValue();
        float floatValue3 = this.Oc.zb().getValue().floatValue() / 100.0f;
        float floatValue4 = this.Oc.yb().getValue().floatValue() / 100.0f;
        for (int i2 = ((int) floatValue) - 1; i2 >= 0; i2--) {
            this.matrix.set(matrix2);
            float f = (float) i2;
            this.matrix.preConcat(this.Oc.j(f + floatValue2));
            this.Pc.a(canvas, this.matrix, (int) (((float) i) * com.airbnb.lottie.d.g.lerp(floatValue3, floatValue4, f / floatValue)));
        }
    }

    @Override // com.airbnb.lottie.a.a.f
    public void a(RectF rectF, Matrix matrix2, boolean z) {
        this.Pc.a(rectF, matrix2, z);
    }

    @Override // com.airbnb.lottie.model.e
    public void a(d dVar, int i, List<d> list, d dVar2) {
        com.airbnb.lottie.d.g.a(dVar, i, list, dVar2, this);
    }

    @Override // com.airbnb.lottie.model.e
    public <T> void a(T t, @Nullable j<T> jVar) {
        if (!this.Oc.b(t, jVar)) {
            if (t == W.ek) {
                this.Nc.a(jVar);
            } else if (t == W.fk) {
                this.offset.a(jVar);
            }
        }
    }

    @Override // com.airbnb.lottie.a.a.d
    public void a(List<d> list, List<d> list2) {
        this.Pc.a(list, list2);
    }

    /* JADX WARNING: Removed duplicated region for block: B:3:0x0005 A[LOOP:0: B:3:0x0005->B:6:0x000f, LOOP_START] */
    @Override // com.airbnb.lottie.a.a.k
    public void a(ListIterator<d> listIterator) {
        if (this.Pc == null) {
            while (listIterator.hasPrevious() && listIterator.previous() != this) {
            }
            ArrayList arrayList = new ArrayList();
            while (listIterator.hasPrevious()) {
                arrayList.add(listIterator.previous());
                listIterator.remove();
            }
            Collections.reverse(arrayList);
            this.Pc = new e(this.La, this.layer, "Repeater", this.hidden, arrayList, null);
        }
    }

    @Override // com.airbnb.lottie.a.a.d
    public String getName() {
        return this.name;
    }

    @Override // com.airbnb.lottie.a.a.p
    public Path getPath() {
        Path path2 = this.Pc.getPath();
        this.path.reset();
        float floatValue = this.Nc.getValue().floatValue();
        float floatValue2 = this.offset.getValue().floatValue();
        for (int i = ((int) floatValue) - 1; i >= 0; i--) {
            this.matrix.set(this.Oc.j(((float) i) + floatValue2));
            this.path.addPath(path2, this.matrix);
        }
        return this.path;
    }
}
