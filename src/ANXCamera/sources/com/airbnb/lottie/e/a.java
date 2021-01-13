package com.airbnb.lottie.e;

import android.graphics.PointF;
import android.view.animation.Interpolator;
import androidx.annotation.FloatRange;
import androidx.annotation.Nullable;
import com.airbnb.lottie.C0035m;

/* compiled from: Keyframe */
public class a<T> {
    private static final float ng = -3987645.8f;
    private static final int og = 784923401;
    @Nullable
    public T endValue;
    private float fg;
    private float gg;
    @Nullable
    private final C0035m ha;
    private int hg;
    private int ig;
    @Nullable
    public final Interpolator interpolator;
    private float jg;
    private float kg;
    public final float lb;
    public PointF lg;
    @Nullable
    public Float mb;
    public PointF mg;
    @Nullable
    public final T startValue;

    public a(C0035m mVar, @Nullable T t, @Nullable T t2, @Nullable Interpolator interpolator2, float f, @Nullable Float f2) {
        this.fg = ng;
        this.gg = ng;
        this.hg = og;
        this.ig = og;
        this.jg = Float.MIN_VALUE;
        this.kg = Float.MIN_VALUE;
        this.lg = null;
        this.mg = null;
        this.ha = mVar;
        this.startValue = t;
        this.endValue = t2;
        this.interpolator = interpolator2;
        this.lb = f;
        this.mb = f2;
    }

    public a(T t) {
        this.fg = ng;
        this.gg = ng;
        this.hg = og;
        this.ig = og;
        this.jg = Float.MIN_VALUE;
        this.kg = Float.MIN_VALUE;
        this.lg = null;
        this.mg = null;
        this.ha = null;
        this.startValue = t;
        this.endValue = t;
        this.interpolator = null;
        this.lb = Float.MIN_VALUE;
        this.mb = Float.valueOf(Float.MAX_VALUE);
    }

    public float K() {
        if (this.ha == null) {
            return 1.0f;
        }
        if (this.kg == Float.MIN_VALUE) {
            if (this.mb == null) {
                this.kg = 1.0f;
            } else {
                this.kg = nc() + ((this.mb.floatValue() - this.lb) / this.ha.bb());
            }
        }
        return this.kg;
    }

    public boolean isStatic() {
        return this.interpolator == null;
    }

    public boolean k(@FloatRange(from = 0.0d, to = 1.0d) float f) {
        return f >= nc() && f < K();
    }

    public float nc() {
        C0035m mVar = this.ha;
        if (mVar == null) {
            return 0.0f;
        }
        if (this.jg == Float.MIN_VALUE) {
            this.jg = (this.lb - mVar.hb()) / this.ha.bb();
        }
        return this.jg;
    }

    public String toString() {
        return "Keyframe{startValue=" + ((Object) this.startValue) + ", endValue=" + ((Object) this.endValue) + ", startFrame=" + this.lb + ", endFrame=" + this.mb + ", interpolator=" + this.interpolator + '}';
    }

    public float vc() {
        if (this.gg == ng) {
            this.gg = this.endValue.floatValue();
        }
        return this.gg;
    }

    public int wc() {
        if (this.ig == og) {
            this.ig = this.endValue.intValue();
        }
        return this.ig;
    }

    public float xc() {
        if (this.fg == ng) {
            this.fg = this.startValue.floatValue();
        }
        return this.fg;
    }

    public int yc() {
        if (this.hg == og) {
            this.hg = this.startValue.intValue();
        }
        return this.hg;
    }
}
