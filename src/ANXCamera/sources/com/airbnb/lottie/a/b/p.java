package com.airbnb.lottie.a.b;

import android.graphics.Matrix;
import android.graphics.PointF;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.airbnb.lottie.W;
import com.airbnb.lottie.a.b.b;
import com.airbnb.lottie.e.a;
import com.airbnb.lottie.e.j;
import com.airbnb.lottie.e.k;
import com.airbnb.lottie.model.a.l;
import com.airbnb.lottie.model.layer.c;
import java.util.Collections;

/* compiled from: TransformKeyframeAnimation */
public class p {
    @NonNull
    private b<PointF, PointF> Ad;
    @Nullable
    private d Bd;
    @Nullable
    private d Cd;
    @Nullable
    private b<?, Float> Dd;
    @Nullable
    private b<?, Float> Ed;
    private final Matrix matrix = new Matrix();
    @NonNull
    private b<Integer, Integer> opacity;
    @NonNull
    private b<?, PointF> position;
    @NonNull
    private b<Float, Float> rotation;
    @NonNull
    private b<k, k> scale;
    private final Matrix wd;
    private final Matrix xd;
    private final Matrix yd;
    private final float[] zd;

    public p(l lVar) {
        this.Ad = lVar.Hb() == null ? null : lVar.Hb().F();
        this.position = lVar.getPosition() == null ? null : lVar.getPosition().F();
        this.scale = lVar.getScale() == null ? null : lVar.getScale().F();
        this.rotation = lVar.getRotation() == null ? null : lVar.getRotation().F();
        this.Bd = lVar.Ib() == null ? null : (d) lVar.Ib().F();
        if (this.Bd != null) {
            this.wd = new Matrix();
            this.xd = new Matrix();
            this.yd = new Matrix();
            this.zd = new float[9];
        } else {
            this.wd = null;
            this.xd = null;
            this.yd = null;
            this.zd = null;
        }
        this.Cd = lVar.Jb() == null ? null : (d) lVar.Jb().F();
        if (lVar.getOpacity() != null) {
            this.opacity = lVar.getOpacity().F();
        }
        if (lVar.zb() != null) {
            this.Dd = lVar.zb().F();
        } else {
            this.Dd = null;
        }
        if (lVar.yb() != null) {
            this.Ed = lVar.yb().F();
        } else {
            this.Ed = null;
        }
    }

    private void Uo() {
        for (int i = 0; i < 9; i++) {
            this.zd[i] = 0.0f;
        }
    }

    public void a(b.a aVar) {
        b<Integer, Integer> bVar = this.opacity;
        if (bVar != null) {
            bVar.b(aVar);
        }
        b<?, Float> bVar2 = this.Dd;
        if (bVar2 != null) {
            bVar2.b(aVar);
        }
        b<?, Float> bVar3 = this.Ed;
        if (bVar3 != null) {
            bVar3.b(aVar);
        }
        b<PointF, PointF> bVar4 = this.Ad;
        if (bVar4 != null) {
            bVar4.b(aVar);
        }
        b<?, PointF> bVar5 = this.position;
        if (bVar5 != null) {
            bVar5.b(aVar);
        }
        b<k, k> bVar6 = this.scale;
        if (bVar6 != null) {
            bVar6.b(aVar);
        }
        b<Float, Float> bVar7 = this.rotation;
        if (bVar7 != null) {
            bVar7.b(aVar);
        }
        d dVar = this.Bd;
        if (dVar != null) {
            dVar.b(aVar);
        }
        d dVar2 = this.Cd;
        if (dVar2 != null) {
            dVar2.b(aVar);
        }
    }

    public void a(c cVar) {
        cVar.a(this.opacity);
        cVar.a(this.Dd);
        cVar.a(this.Ed);
        cVar.a(this.Ad);
        cVar.a(this.position);
        cVar.a(this.scale);
        cVar.a(this.rotation);
        cVar.a(this.Bd);
        cVar.a(this.Cd);
    }

    public <T> boolean b(T t, @Nullable j<T> jVar) {
        d dVar;
        d dVar2;
        b<?, Float> bVar;
        b<?, Float> bVar2;
        if (t == W.Uj) {
            b<PointF, PointF> bVar3 = this.Ad;
            if (bVar3 == null) {
                this.Ad = new q(jVar, new PointF());
                return true;
            }
            bVar3.a(jVar);
            return true;
        } else if (t == W.Vj) {
            b<?, PointF> bVar4 = this.position;
            if (bVar4 == null) {
                this.position = new q(jVar, new PointF());
                return true;
            }
            bVar4.a(jVar);
            return true;
        } else if (t == W.Zj) {
            b<k, k> bVar5 = this.scale;
            if (bVar5 == null) {
                this.scale = new q(jVar, new k());
                return true;
            }
            bVar5.a(jVar);
            return true;
        } else if (t == W._j) {
            b<Float, Float> bVar6 = this.rotation;
            if (bVar6 == null) {
                this.rotation = new q(jVar, Float.valueOf(0.0f));
                return true;
            }
            bVar6.a(jVar);
            return true;
        } else if (t == W.Sj) {
            b<Integer, Integer> bVar7 = this.opacity;
            if (bVar7 == null) {
                this.opacity = new q(jVar, 100);
                return true;
            }
            bVar7.a(jVar);
            return true;
        } else if (t != W.mk || (bVar2 = this.Dd) == null) {
            if (t != W.nk || (bVar = this.Ed) == null) {
                if (t == W.bk && (dVar2 = this.Bd) != null) {
                    if (dVar2 == null) {
                        this.Bd = new d(Collections.singletonList(new a(Float.valueOf(0.0f))));
                    }
                    this.Bd.a(jVar);
                    return true;
                } else if (t != W.ck || (dVar = this.Cd) == null) {
                    return false;
                } else {
                    if (dVar == null) {
                        this.Cd = new d(Collections.singletonList(new a(Float.valueOf(0.0f))));
                    }
                    this.Cd.a(jVar);
                    return true;
                }
            } else if (bVar == null) {
                this.Ed = new q(jVar, 100);
                return true;
            } else {
                bVar.a(jVar);
                return true;
            }
        } else if (bVar2 == null) {
            this.Dd = new q(jVar, 100);
            return true;
        } else {
            bVar2.a(jVar);
            return true;
        }
    }

    public Matrix getMatrix() {
        this.matrix.reset();
        b<?, PointF> bVar = this.position;
        if (bVar != null) {
            PointF value = bVar.getValue();
            if (!(value.x == 0.0f && value.y == 0.0f)) {
                this.matrix.preTranslate(value.x, value.y);
            }
        }
        b<Float, Float> bVar2 = this.rotation;
        if (bVar2 != null) {
            float floatValue = bVar2 instanceof q ? bVar2.getValue().floatValue() : ((d) bVar2).getFloatValue();
            if (floatValue != 0.0f) {
                this.matrix.preRotate(floatValue);
            }
        }
        if (this.Bd != null) {
            d dVar = this.Cd;
            float cos = dVar == null ? 0.0f : (float) Math.cos(Math.toRadians((double) ((-dVar.getFloatValue()) + 90.0f)));
            d dVar2 = this.Cd;
            float sin = dVar2 == null ? 1.0f : (float) Math.sin(Math.toRadians((double) ((-dVar2.getFloatValue()) + 90.0f)));
            Uo();
            float[] fArr = this.zd;
            fArr[0] = cos;
            fArr[1] = sin;
            float f = -sin;
            fArr[3] = f;
            fArr[4] = cos;
            fArr[8] = 1.0f;
            this.wd.setValues(fArr);
            Uo();
            float[] fArr2 = this.zd;
            fArr2[0] = 1.0f;
            fArr2[3] = (float) Math.tan(Math.toRadians((double) this.Bd.getFloatValue()));
            fArr2[4] = 1.0f;
            fArr2[8] = 1.0f;
            this.xd.setValues(fArr2);
            Uo();
            float[] fArr3 = this.zd;
            fArr3[0] = cos;
            fArr3[1] = f;
            fArr3[3] = sin;
            fArr3[4] = cos;
            fArr3[8] = 1.0f;
            this.yd.setValues(fArr3);
            this.xd.preConcat(this.wd);
            this.yd.preConcat(this.xd);
            this.matrix.preConcat(this.yd);
        }
        b<k, k> bVar3 = this.scale;
        if (bVar3 != null) {
            k value2 = bVar3.getValue();
            if (!(value2.getScaleX() == 1.0f && value2.getScaleY() == 1.0f)) {
                this.matrix.preScale(value2.getScaleX(), value2.getScaleY());
            }
        }
        b<PointF, PointF> bVar4 = this.Ad;
        if (bVar4 != null) {
            PointF value3 = bVar4.getValue();
            if (!(value3.x == 0.0f && value3.y == 0.0f)) {
                this.matrix.preTranslate(-value3.x, -value3.y);
            }
        }
        return this.matrix;
    }

    @Nullable
    public b<?, Integer> getOpacity() {
        return this.opacity;
    }

    public Matrix j(float f) {
        b<?, PointF> bVar = this.position;
        PointF pointF = null;
        PointF value = bVar == null ? null : bVar.getValue();
        b<k, k> bVar2 = this.scale;
        k value2 = bVar2 == null ? null : bVar2.getValue();
        this.matrix.reset();
        if (value != null) {
            this.matrix.preTranslate(value.x * f, value.y * f);
        }
        if (value2 != null) {
            double d2 = (double) f;
            this.matrix.preScale((float) Math.pow((double) value2.getScaleX(), d2), (float) Math.pow((double) value2.getScaleY(), d2));
        }
        b<Float, Float> bVar3 = this.rotation;
        if (bVar3 != null) {
            float floatValue = bVar3.getValue().floatValue();
            b<PointF, PointF> bVar4 = this.Ad;
            if (bVar4 != null) {
                pointF = bVar4.getValue();
            }
            Matrix matrix2 = this.matrix;
            float f2 = floatValue * f;
            float f3 = 0.0f;
            float f4 = pointF == null ? 0.0f : pointF.x;
            if (pointF != null) {
                f3 = pointF.y;
            }
            matrix2.preRotate(f2, f4, f3);
        }
        return this.matrix;
    }

    public void setProgress(float f) {
        b<Integer, Integer> bVar = this.opacity;
        if (bVar != null) {
            bVar.setProgress(f);
        }
        b<?, Float> bVar2 = this.Dd;
        if (bVar2 != null) {
            bVar2.setProgress(f);
        }
        b<?, Float> bVar3 = this.Ed;
        if (bVar3 != null) {
            bVar3.setProgress(f);
        }
        b<PointF, PointF> bVar4 = this.Ad;
        if (bVar4 != null) {
            bVar4.setProgress(f);
        }
        b<?, PointF> bVar5 = this.position;
        if (bVar5 != null) {
            bVar5.setProgress(f);
        }
        b<k, k> bVar6 = this.scale;
        if (bVar6 != null) {
            bVar6.setProgress(f);
        }
        b<Float, Float> bVar7 = this.rotation;
        if (bVar7 != null) {
            bVar7.setProgress(f);
        }
        d dVar = this.Bd;
        if (dVar != null) {
            dVar.setProgress(f);
        }
        d dVar2 = this.Cd;
        if (dVar2 != null) {
            dVar2.setProgress(f);
        }
    }

    @Nullable
    public b<?, Float> yb() {
        return this.Ed;
    }

    @Nullable
    public b<?, Float> zb() {
        return this.Dd;
    }
}
