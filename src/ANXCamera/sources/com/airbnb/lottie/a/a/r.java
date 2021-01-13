package com.airbnb.lottie.a.a;

import android.graphics.Path;
import android.graphics.PointF;
import androidx.annotation.Nullable;
import com.airbnb.lottie.Q;
import com.airbnb.lottie.W;
import com.airbnb.lottie.a.b.b;
import com.airbnb.lottie.d.g;
import com.airbnb.lottie.e.j;
import com.airbnb.lottie.model.content.PolystarShape;
import com.airbnb.lottie.model.content.ShapeTrimPath;
import com.airbnb.lottie.model.d;
import com.airbnb.lottie.model.layer.c;
import java.util.List;

/* compiled from: PolystarContent */
public class r implements p, b.a, l {
    private static final float Kc = 0.47829f;
    private static final float Lc = 0.25f;
    private final b<?, Float> Fc;
    @Nullable
    private final b<?, Float> Gc;
    private final b<?, Float> Hc;
    @Nullable
    private final b<?, Float> Ic;
    private final b<?, Float> Jc;
    private final Q La;
    private final boolean hidden;
    private final String name;
    private final Path path = new Path();
    private final b<?, Float> rotationAnimation;
    private final PolystarShape.Type type;
    private final b<?, PointF> vc;
    private c xc = new c();
    private boolean yc;

    public r(Q q, c cVar, PolystarShape polystarShape) {
        this.La = q;
        this.name = polystarShape.getName();
        this.type = polystarShape.getType();
        this.hidden = polystarShape.isHidden();
        this.Fc = polystarShape.getPoints().F();
        this.vc = polystarShape.getPosition().F();
        this.rotationAnimation = polystarShape.getRotation().F();
        this.Hc = polystarShape.Yb().F();
        this.Jc = polystarShape.Zb().F();
        if (this.type == PolystarShape.Type.STAR) {
            this.Gc = polystarShape.getInnerRadius().F();
            this.Ic = polystarShape.Xb().F();
        } else {
            this.Gc = null;
            this.Ic = null;
        }
        cVar.a(this.Fc);
        cVar.a(this.vc);
        cVar.a(this.rotationAnimation);
        cVar.a(this.Hc);
        cVar.a(this.Jc);
        if (this.type == PolystarShape.Type.STAR) {
            cVar.a(this.Gc);
            cVar.a(this.Ic);
        }
        this.Fc.b(this);
        this.vc.b(this);
        this.rotationAnimation.b(this);
        this.Hc.b(this);
        this.Jc.b(this);
        if (this.type == PolystarShape.Type.STAR) {
            this.Gc.b(this);
            this.Ic.b(this);
        }
    }

    private void So() {
        double d2;
        double d3;
        double d4;
        int i;
        int floor = (int) Math.floor((double) this.Fc.getValue().floatValue());
        b<?, Float> bVar = this.rotationAnimation;
        double radians = Math.toRadians((bVar == null ? 0.0d : (double) bVar.getValue().floatValue()) - 90.0d);
        double d5 = (double) floor;
        float floatValue = this.Jc.getValue().floatValue() / 100.0f;
        float floatValue2 = this.Hc.getValue().floatValue();
        double d6 = (double) floatValue2;
        float cos = (float) (Math.cos(radians) * d6);
        float sin = (float) (Math.sin(radians) * d6);
        this.path.moveTo(cos, sin);
        double d7 = (double) ((float) (6.283185307179586d / d5));
        double d8 = radians + d7;
        double ceil = Math.ceil(d5);
        int i2 = 0;
        while (((double) i2) < ceil) {
            float cos2 = (float) (Math.cos(d8) * d6);
            float sin2 = (float) (d6 * Math.sin(d8));
            if (floatValue != 0.0f) {
                d4 = d6;
                i = i2;
                d3 = d8;
                double atan2 = (double) ((float) (Math.atan2((double) sin, (double) cos) - 1.5707963267948966d));
                float cos3 = (float) Math.cos(atan2);
                d2 = d7;
                double atan22 = (double) ((float) (Math.atan2((double) sin2, (double) cos2) - 1.5707963267948966d));
                float f = floatValue2 * floatValue * Lc;
                this.path.cubicTo(cos - (cos3 * f), sin - (((float) Math.sin(atan2)) * f), cos2 + (((float) Math.cos(atan22)) * f), sin2 + (f * ((float) Math.sin(atan22))), cos2, sin2);
            } else {
                d3 = d8;
                d4 = d6;
                d2 = d7;
                i = i2;
                this.path.lineTo(cos2, sin2);
            }
            d8 = d3 + d2;
            i2 = i + 1;
            sin = sin2;
            cos = cos2;
            ceil = ceil;
            d6 = d4;
            d7 = d2;
        }
        PointF value = this.vc.getValue();
        this.path.offset(value.x, value.y);
        this.path.close();
    }

    private void To() {
        double d2;
        int i;
        float f;
        float f2;
        float f3;
        double d3;
        float f4;
        float f5;
        float f6;
        float f7;
        float f8;
        float f9;
        float f10;
        float f11;
        float f12;
        float floatValue = this.Fc.getValue().floatValue();
        b<?, Float> bVar = this.rotationAnimation;
        double radians = Math.toRadians((bVar == null ? 0.0d : (double) bVar.getValue().floatValue()) - 90.0d);
        double d4 = (double) floatValue;
        float f13 = (float) (6.283185307179586d / d4);
        float f14 = f13 / 2.0f;
        float f15 = floatValue - ((float) ((int) floatValue));
        int i2 = (f15 > 0.0f ? 1 : (f15 == 0.0f ? 0 : -1));
        if (i2 != 0) {
            radians += (double) ((1.0f - f15) * f14);
        }
        float floatValue2 = this.Hc.getValue().floatValue();
        float floatValue3 = this.Gc.getValue().floatValue();
        b<?, Float> bVar2 = this.Ic;
        float floatValue4 = bVar2 != null ? bVar2.getValue().floatValue() / 100.0f : 0.0f;
        b<?, Float> bVar3 = this.Jc;
        float floatValue5 = bVar3 != null ? bVar3.getValue().floatValue() / 100.0f : 0.0f;
        if (i2 != 0) {
            f = ((floatValue2 - floatValue3) * f15) + floatValue3;
            i = i2;
            double d5 = (double) f;
            d2 = d4;
            f3 = (float) (d5 * Math.cos(radians));
            f2 = (float) (d5 * Math.sin(radians));
            this.path.moveTo(f3, f2);
            d3 = radians + ((double) ((f13 * f15) / 2.0f));
        } else {
            d2 = d4;
            i = i2;
            double d6 = (double) floatValue2;
            float cos = (float) (Math.cos(radians) * d6);
            float sin = (float) (d6 * Math.sin(radians));
            this.path.moveTo(cos, sin);
            d3 = radians + ((double) f14);
            f3 = cos;
            f2 = sin;
            f = 0.0f;
        }
        double ceil = Math.ceil(d2) * 2.0d;
        boolean z = false;
        double d7 = d3;
        float f16 = f14;
        int i3 = 0;
        while (true) {
            double d8 = (double) i3;
            if (d8 < ceil) {
                float f17 = z ? floatValue2 : floatValue3;
                int i4 = (f > 0.0f ? 1 : (f == 0.0f ? 0 : -1));
                if (i4 == 0 || d8 != ceil - 2.0d) {
                    f4 = f16;
                } else {
                    f4 = f16;
                    f16 = (f13 * f15) / 2.0f;
                }
                if (i4 == 0 || d8 != ceil - 1.0d) {
                    f6 = f13;
                    f5 = floatValue3;
                    f8 = f17;
                    f7 = floatValue2;
                } else {
                    f6 = f13;
                    f7 = floatValue2;
                    f5 = floatValue3;
                    f8 = f;
                }
                double d9 = (double) f8;
                float cos2 = (float) (d9 * Math.cos(d7));
                float sin2 = (float) (d9 * Math.sin(d7));
                if (floatValue4 == 0.0f && floatValue5 == 0.0f) {
                    this.path.lineTo(cos2, sin2);
                    f12 = sin2;
                    f9 = floatValue4;
                    f10 = floatValue5;
                    f11 = f;
                } else {
                    f9 = floatValue4;
                    f10 = floatValue5;
                    double atan2 = (double) ((float) (Math.atan2((double) f2, (double) f3) - 1.5707963267948966d));
                    float cos3 = (float) Math.cos(atan2);
                    float sin3 = (float) Math.sin(atan2);
                    f11 = f;
                    f12 = sin2;
                    double atan22 = (double) ((float) (Math.atan2((double) sin2, (double) cos2) - 1.5707963267948966d));
                    float cos4 = (float) Math.cos(atan22);
                    float sin4 = (float) Math.sin(atan22);
                    float f18 = z ? f9 : f10;
                    float f19 = z ? f10 : f9;
                    float f20 = z ? f5 : f7;
                    float f21 = z ? f7 : f5;
                    float f22 = f20 * f18 * Kc;
                    float f23 = cos3 * f22;
                    float f24 = f22 * sin3;
                    float f25 = f21 * f19 * Kc;
                    float f26 = cos4 * f25;
                    float f27 = f25 * sin4;
                    if (i != 0) {
                        if (i3 == 0) {
                            f23 *= f15;
                            f24 *= f15;
                        } else if (d8 == ceil - 1.0d) {
                            f26 *= f15;
                            f27 *= f15;
                        }
                    }
                    this.path.cubicTo(f3 - f23, f2 - f24, cos2 + f26, f12 + f27, cos2, f12);
                }
                d7 += (double) f16;
                z = !z;
                i3++;
                f3 = cos2;
                f = f11;
                floatValue2 = f7;
                f13 = f6;
                f16 = f4;
                floatValue3 = f5;
                floatValue4 = f9;
                floatValue5 = f10;
                f2 = f12;
            } else {
                PointF value = this.vc.getValue();
                this.path.offset(value.x, value.y);
                this.path.close();
                return;
            }
        }
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
        b<?, Float> bVar;
        b<?, Float> bVar2;
        if (t == W.gk) {
            this.Fc.a(jVar);
        } else if (t == W.hk) {
            this.rotationAnimation.a(jVar);
        } else if (t == W.POSITION) {
            this.vc.a(jVar);
        } else if (t == W.ik && (bVar2 = this.Gc) != null) {
            bVar2.a(jVar);
        } else if (t == W.jk) {
            this.Hc.a(jVar);
        } else if (t == W.kk && (bVar = this.Ic) != null) {
            bVar.a(jVar);
        } else if (t == W.lk) {
            this.Jc.a(jVar);
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
        int i = q.Ec[this.type.ordinal()];
        if (i == 1) {
            To();
        } else if (i == 2) {
            So();
        }
        this.path.close();
        this.xc.a(this.path);
        this.yc = true;
        return this.path;
    }
}
