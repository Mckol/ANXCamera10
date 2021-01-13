package com.airbnb.lottie.a.a;

import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import androidx.annotation.Nullable;
import com.airbnb.lottie.C0027e;
import com.airbnb.lottie.Q;
import com.airbnb.lottie.W;
import com.airbnb.lottie.a.a;
import com.airbnb.lottie.a.b.b;
import com.airbnb.lottie.a.b.q;
import com.airbnb.lottie.d.g;
import com.airbnb.lottie.e.j;
import com.airbnb.lottie.model.content.i;
import com.airbnb.lottie.model.d;
import com.airbnb.lottie.model.layer.c;
import java.util.ArrayList;
import java.util.List;

/* compiled from: FillContent */
public class h implements f, b.a, l {
    private final Q La;
    private final List<p> Vb = new ArrayList();
    private final b<Integer, Integer> bc;
    @Nullable
    private b<ColorFilter, ColorFilter> fc;
    private final boolean hidden;
    private final b<Integer, Integer> kc;
    private final c layer;
    private final String name;
    private final Paint paint = new a(1);
    private final Path path = new Path();

    public h(Q q, c cVar, i iVar) {
        this.layer = cVar;
        this.name = iVar.getName();
        this.hidden = iVar.isHidden();
        this.La = q;
        if (iVar.getColor() == null || iVar.getOpacity() == null) {
            this.kc = null;
            this.bc = null;
            return;
        }
        this.path.setFillType(iVar.getFillType());
        this.kc = iVar.getColor().F();
        this.kc.b(this);
        cVar.a(this.kc);
        this.bc = iVar.getOpacity().F();
        this.bc.b(this);
        cVar.a(this.bc);
    }

    @Override // com.airbnb.lottie.a.b.b.a
    public void G() {
        this.La.invalidateSelf();
    }

    @Override // com.airbnb.lottie.a.a.f
    public void a(Canvas canvas, Matrix matrix, int i) {
        if (!this.hidden) {
            C0027e.beginSection("FillContent#draw");
            this.paint.setColor(((com.airbnb.lottie.a.b.c) this.kc).getIntValue());
            this.paint.setAlpha(g.clamp((int) ((((((float) i) / 255.0f) * ((float) this.bc.getValue().intValue())) / 100.0f) * 255.0f), 0, 255));
            b<ColorFilter, ColorFilter> bVar = this.fc;
            if (bVar != null) {
                this.paint.setColorFilter(bVar.getValue());
            }
            this.path.reset();
            for (int i2 = 0; i2 < this.Vb.size(); i2++) {
                this.path.addPath(this.Vb.get(i2).getPath(), matrix);
            }
            canvas.drawPath(this.path, this.paint);
            C0027e.D("FillContent#draw");
        }
    }

    @Override // com.airbnb.lottie.a.a.f
    public void a(RectF rectF, Matrix matrix, boolean z) {
        this.path.reset();
        for (int i = 0; i < this.Vb.size(); i++) {
            this.path.addPath(this.Vb.get(i).getPath(), matrix);
        }
        this.path.computeBounds(rectF, false);
        rectF.set(rectF.left - 1.0f, rectF.top - 1.0f, rectF.right + 1.0f, rectF.bottom + 1.0f);
    }

    @Override // com.airbnb.lottie.model.e
    public void a(d dVar, int i, List<d> list, d dVar2) {
        g.a(dVar, i, list, dVar2, this);
    }

    @Override // com.airbnb.lottie.model.e
    public <T> void a(T t, @Nullable j<T> jVar) {
        if (t == W.COLOR) {
            this.kc.a(jVar);
        } else if (t == W.Tj) {
            this.bc.a(jVar);
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
        for (int i = 0; i < list2.size(); i++) {
            d dVar = list2.get(i);
            if (dVar instanceof p) {
                this.Vb.add((p) dVar);
            }
        }
    }

    @Override // com.airbnb.lottie.a.a.d
    public String getName() {
        return this.name;
    }
}
