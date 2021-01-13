package com.airbnb.lottie.a.a;

import android.graphics.Canvas;
import android.graphics.LinearGradient;
import android.graphics.Matrix;
import android.graphics.PointF;
import android.graphics.RadialGradient;
import android.graphics.RectF;
import android.graphics.Shader;
import androidx.annotation.Nullable;
import androidx.collection.LongSparseArray;
import com.airbnb.lottie.Q;
import com.airbnb.lottie.W;
import com.airbnb.lottie.a.b.b;
import com.airbnb.lottie.a.b.q;
import com.airbnb.lottie.model.content.GradientType;
import com.airbnb.lottie.model.content.c;
import com.airbnb.lottie.model.content.e;

/* compiled from: GradientStrokeContent */
public class j extends b {
    private static final int oc = 32;
    private final LongSparseArray<LinearGradient> gc = new LongSparseArray<>();
    private final LongSparseArray<RadialGradient> hc = new LongSparseArray<>();
    private final boolean hidden;
    private final RectF ic = new RectF();
    private final int jc;
    private final b<c, c> kc;
    private final b<PointF, PointF> lc;
    private final b<PointF, PointF> mc;
    private final String name;
    @Nullable
    private q nc;
    private final GradientType type;

    public j(Q q, com.airbnb.lottie.model.layer.c cVar, e eVar) {
        super(q, cVar, eVar.Pb().Bo(), eVar.Rb().Co(), eVar.Tb(), eVar.getOpacity(), eVar.getWidth(), eVar.Sb(), eVar.Qb());
        this.name = eVar.getName();
        this.type = eVar.getGradientType();
        this.hidden = eVar.isHidden();
        this.jc = (int) (q.aa().getDuration() / 32.0f);
        this.kc = eVar.Lb().F();
        this.kc.b(this);
        cVar.a(this.kc);
        this.lc = eVar.Ob().F();
        this.lc.b(this);
        cVar.a(this.lc);
        this.mc = eVar.Kb().F();
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
        RadialGradient radialGradient2 = new RadialGradient(f, f2, (float) Math.hypot((double) (value2.x - f), (double) (value2.y - f2)), b2, positions, Shader.TileMode.CLAMP);
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

    @Override // com.airbnb.lottie.a.a.b, com.airbnb.lottie.a.a.f
    public void a(Canvas canvas, Matrix matrix, int i) {
        if (!this.hidden) {
            a(this.ic, matrix, false);
            Shader Oo = this.type == GradientType.LINEAR ? Oo() : Po();
            Oo.setLocalMatrix(matrix);
            this.paint.setShader(Oo);
            super.a(canvas, matrix, i);
        }
    }

    /* JADX DEBUG: Multi-variable search result rejected for r0v0, resolved type: java.lang.Integer[] */
    /* JADX WARN: Multi-variable type inference failed */
    @Override // com.airbnb.lottie.model.e, com.airbnb.lottie.a.a.b
    public <T> void a(T t, @Nullable com.airbnb.lottie.e.j<T> jVar) {
        super.a(t, jVar);
        if (t != W.rk) {
            return;
        }
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

    @Override // com.airbnb.lottie.a.a.d
    public String getName() {
        return this.name;
    }
}
