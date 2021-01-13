package com.airbnb.lottie.a.a;

import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.LinearGradient;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PointF;
import android.graphics.RadialGradient;
import android.graphics.RectF;
import android.graphics.Shader;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.collection.LongSparseArray;
import com.airbnb.lottie.C0027e;
import com.airbnb.lottie.Q;
import com.airbnb.lottie.W;
import com.airbnb.lottie.a.a;
import com.airbnb.lottie.a.b.b;
import com.airbnb.lottie.a.b.q;
import com.airbnb.lottie.d.g;
import com.airbnb.lottie.e.j;
import com.airbnb.lottie.model.content.GradientType;
import com.airbnb.lottie.model.content.c;
import com.airbnb.lottie.model.content.d;
import java.util.ArrayList;
import java.util.List;

/* compiled from: GradientFillContent */
public class i implements f, b.a, l {
    private static final int oc = 32;
    private final Q La;
    private final List<p> Vb = new ArrayList();
    private final b<Integer, Integer> bc;
    @Nullable
    private b<ColorFilter, ColorFilter> fc;
    private final LongSparseArray<LinearGradient> gc = new LongSparseArray<>();
    private final LongSparseArray<RadialGradient> hc = new LongSparseArray<>();
    private final boolean hidden;
    private final RectF ic = new RectF();
    private final int jc;
    private final b<c, c> kc;
    private final com.airbnb.lottie.model.layer.c layer;
    private final b<PointF, PointF> lc;
    private final b<PointF, PointF> mc;
    @NonNull
    private final String name;
    @Nullable
    private q nc;
    private final Paint paint = new a(1);
    private final Path path = new Path();
    private final GradientType type;

    public i(Q q, com.airbnb.lottie.model.layer.c cVar, d dVar) {
        this.layer = cVar;
        this.name = dVar.getName();
        this.hidden = dVar.isHidden();
        this.La = q;
        this.type = dVar.getGradientType();
        this.path.setFillType(dVar.getFillType());
        this.jc = (int) (q.aa().getDuration() / 32.0f);
        this.kc = dVar.Lb().F();
        this.kc.b(this);
        cVar.a(this.kc);
        this.bc = dVar.getOpacity().F();
        this.bc.b(this);
        cVar.a(this.bc);
        this.lc = dVar.Ob().F();
        this.lc.b(this);
        cVar.a(this.lc);
        this.mc = dVar.Kb().F();
        this.mc.b(this);
        cVar.a(this.mc);
    }

    private int No() {
        int round = Math.round(this.lc.getProgress() * ((float) this.jc));
        int round2 = Math.round(this.mc.getProgress() * ((float) this.jc));
        int round3 = Math.round(this.kc.getProgress() * ((float) this.jc));
        int i = round != 0 ? 527 * round : 17;
        if (round2 != 0) {
            i = i * 31 * round2;
        }
        return round3 != 0 ? i * 31 * round3 : i;
    }

    private LinearGradient Oo() {
        long No = (long) No();
        LinearGradient linearGradient = this.gc.get(No);
        if (linearGradient != null) {
            return linearGradient;
        }
        PointF value = this.lc.getValue();
        PointF value2 = this.mc.getValue();
        c value3 = this.kc.getValue();
        LinearGradient linearGradient2 = new LinearGradient(value.x, value.y, value2.x, value2.y, b(value3.getColors()), value3.getPositions(), Shader.TileMode.CLAMP);
        this.gc.put(No, linearGradient2);
        return linearGradient2;
    }

    private RadialGradient Po() {
        long No = (long) No();
        RadialGradient radialGradient = this.hc.get(No);
        if (radialGradient != null) {
            return radialGradient;
        }
        PointF value = this.lc.getValue();
        PointF value2 = this.mc.getValue();
        c value3 = this.kc.getValue();
        int[] b2 = b(value3.getColors());
        float[] positions = value3.getPositions();
        float f = value.x;
        float f2 = value.y;
        float hypot = (float) Math.hypot((double) (value2.x - f), (double) (value2.y - f2));
        if (hypot <= 0.0f) {
            hypot = 0.001f;
        }
        RadialGradient radialGradient2 = new RadialGradient(f, f2, hypot, b2, positions, Shader.TileMode.CLAMP);
        this.hc.put(No, radialGradient2);
        return radialGradient2;
    }

    private int[] b(int[] iArr) {
        q qVar = this.nc;
        if (qVar != null) {
            Integer[] numArr = (Integer[]) qVar.getValue();
            int i = 0;
            if (iArr.length == numArr.length) {
                while (i < iArr.length) {
                    iArr[i] = numArr[i].intValue();
                    i++;
                }
            } else {
                iArr = new int[numArr.length];
                while (i < numArr.length) {
                    iArr[i] = numArr[i].intValue();
                    i++;
                }
            }
        }
        return iArr;
    }

    @Override // com.airbnb.lottie.a.b.b.a
    public void G() {
        this.La.invalidateSelf();
    }

    @Override // com.airbnb.lottie.a.a.f
    public void a(Canvas canvas, Matrix matrix, int i) {
        if (!this.hidden) {
            C0027e.beginSection("GradientFillContent#draw");
            this.path.reset();
            for (int i2 = 0; i2 < this.Vb.size(); i2++) {
                this.path.addPath(this.Vb.get(i2).getPath(), matrix);
            }
            this.path.computeBounds(this.ic, false);
            Shader Oo = this.type == GradientType.LINEAR ? Oo() : Po();
            Oo.setLocalMatrix(matrix);
            this.paint.setShader(Oo);
            b<ColorFilter, ColorFilter> bVar = this.fc;
            if (bVar != null) {
                this.paint.setColorFilter(bVar.getValue());
            }
            this.paint.setAlpha(g.clamp((int) ((((((float) i) / 255.0f) * ((float) this.bc.getValue().intValue())) / 100.0f) * 255.0f), 0, 255));
            canvas.drawPath(this.path, this.paint);
            C0027e.D("GradientFillContent#draw");
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
    public void a(com.airbnb.lottie.model.d dVar, int i, List<com.airbnb.lottie.model.d> list, com.airbnb.lottie.model.d dVar2) {
        g.a(dVar, i, list, dVar2, this);
    }

    /* JADX DEBUG: Multi-variable search result rejected for r0v2, resolved type: java.lang.Integer[] */
    /* JADX WARN: Multi-variable type inference failed */
    @Override // com.airbnb.lottie.model.e
    public <T> void a(T t, @Nullable j<T> jVar) {
        if (t == W.Tj) {
            this.bc.a(jVar);
        } else if (t == W.qk) {
            if (jVar == null) {
                this.fc = null;
                return;
            }
            this.fc = new q(jVar);
            this.fc.b(this);
            this.layer.a(this.fc);
        } else if (t != W.rk) {
        } else {
            if (jVar == null) {
                q qVar = this.nc;
                if (qVar != null) {
                    this.layer.b(qVar);
                }
                this.nc = null;
                return;
            }
            this.nc = new q(jVar);
            this.nc.b(this);
            this.layer.a(this.nc);
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
