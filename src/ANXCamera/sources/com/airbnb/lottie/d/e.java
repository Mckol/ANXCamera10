package com.airbnb.lottie.d;

import android.view.Choreographer;
import androidx.annotation.FloatRange;
import androidx.annotation.MainThread;
import androidx.annotation.Nullable;
import androidx.annotation.VisibleForTesting;
import com.airbnb.lottie.C0027e;
import com.airbnb.lottie.C0035m;

/* compiled from: LottieValueAnimator */
public class e extends a implements Choreographer.FrameCallback {
    private boolean ca = false;
    private long da = 0;
    private float ea = 0.0f;
    private float fa = -2.14748365E9f;
    private float ga = 2.14748365E9f;
    @Nullable
    private C0035m ha;
    private int repeatCount = 0;
    @VisibleForTesting
    protected boolean running = false;
    private float speed = 1.0f;

    private float Fo() {
        C0035m mVar = this.ha;
        if (mVar == null) {
            return Float.MAX_VALUE;
        }
        return (1.0E9f / mVar.getFrameRate()) / Math.abs(this.speed);
    }

    private void Go() {
        if (this.ha != null) {
            float f = this.ea;
            if (f < this.fa || f > this.ga) {
                throw new IllegalStateException(String.format("Frame must be [%f,%f]. It is %f", Float.valueOf(this.fa), Float.valueOf(this.ga), Float.valueOf(this.ea)));
            }
        }
    }

    private boolean isReversed() {
        return getSpeed() < 0.0f;
    }

    public void O() {
        this.ha = null;
        this.fa = -2.14748365E9f;
        this.ga = 2.14748365E9f;
    }

    @FloatRange(from = 0.0d, to = 1.0d)
    public float P() {
        C0035m mVar = this.ha;
        if (mVar == null) {
            return 0.0f;
        }
        return (this.ea - mVar.hb()) / (this.ha.db() - this.ha.hb());
    }

    public float Q() {
        C0035m mVar = this.ha;
        if (mVar == null) {
            return 0.0f;
        }
        float f = this.ga;
        return f == 2.14748365E9f ? mVar.db() : f;
    }

    public float R() {
        C0035m mVar = this.ha;
        if (mVar == null) {
            return 0.0f;
        }
        float f = this.fa;
        return f == -2.14748365E9f ? mVar.hb() : f;
    }

    @MainThread
    public void S() {
        U();
    }

    @MainThread
    public void T() {
        this.running = true;
        m(isReversed());
        e((float) ((int) (isReversed() ? Q() : R())));
        this.da = 0;
        this.repeatCount = 0;
        postFrameCallback();
    }

    /* access modifiers changed from: protected */
    @MainThread
    public void U() {
        n(true);
    }

    @MainThread
    public void V() {
        this.running = true;
        postFrameCallback();
        this.da = 0;
        if (isReversed() && getFrame() == R()) {
            this.ea = Q();
        } else if (!isReversed() && getFrame() == Q()) {
            this.ea = R();
        }
    }

    public void W() {
        setSpeed(-getSpeed());
    }

    public void a(float f, float f2) {
        if (f <= f2) {
            C0035m mVar = this.ha;
            float hb = mVar == null ? -3.4028235E38f : mVar.hb();
            C0035m mVar2 = this.ha;
            float db = mVar2 == null ? Float.MAX_VALUE : mVar2.db();
            this.fa = g.clamp(f, hb, db);
            this.ga = g.clamp(f2, hb, db);
            e((float) ((int) g.clamp(this.ea, f, f2)));
            return;
        }
        throw new IllegalArgumentException(String.format("minFrame (%s) must be <= maxFrame (%s)", Float.valueOf(f), Float.valueOf(f2)));
    }

    public void c(C0035m mVar) {
        boolean z = this.ha == null;
        this.ha = mVar;
        if (z) {
            a((float) ((int) Math.max(this.fa, mVar.hb())), (float) ((int) Math.min(this.ga, mVar.db())));
        } else {
            a((float) ((int) mVar.hb()), (float) ((int) mVar.db()));
        }
        float f = this.ea;
        this.ea = 0.0f;
        e((float) ((int) f));
    }

    @MainThread
    public void cancel() {
        L();
        U();
    }

    public void doFrame(long j) {
        postFrameCallback();
        if (this.ha != null && isRunning()) {
            C0027e.beginSection("LottieValueAnimator#doFrame");
            long j2 = this.da;
            long j3 = 0;
            if (j2 != 0) {
                j3 = j - j2;
            }
            float Fo = ((float) j3) / Fo();
            float f = this.ea;
            if (isReversed()) {
                Fo = -Fo;
            }
            this.ea = f + Fo;
            boolean z = !g.a(this.ea, R(), Q());
            this.ea = g.clamp(this.ea, R(), Q());
            this.da = j;
            N();
            if (z) {
                if (getRepeatCount() == -1 || this.repeatCount < getRepeatCount()) {
                    M();
                    this.repeatCount++;
                    if (getRepeatMode() == 2) {
                        this.ca = !this.ca;
                        W();
                    } else {
                        this.ea = isReversed() ? Q() : R();
                    }
                    this.da = j;
                } else {
                    this.ea = this.speed < 0.0f ? R() : Q();
                    U();
                    l(isReversed());
                }
            }
            Go();
            C0027e.D("LottieValueAnimator#doFrame");
        }
    }

    public void e(float f) {
        if (this.ea != f) {
            this.ea = g.clamp(f, R(), Q());
            this.da = 0;
            N();
        }
    }

    @MainThread
    public void endAnimation() {
        U();
        l(isReversed());
    }

    public void f(float f) {
        a(this.fa, f);
    }

    public void f(int i) {
        a((float) i, (float) ((int) this.ga));
    }

    @FloatRange(from = 0.0d, to = 1.0d)
    public float getAnimatedFraction() {
        float R;
        float Q;
        float R2;
        if (this.ha == null) {
            return 0.0f;
        }
        if (isReversed()) {
            R = Q() - this.ea;
            Q = Q();
            R2 = R();
        } else {
            R = this.ea - R();
            Q = Q();
            R2 = R();
        }
        return R / (Q - R2);
    }

    public Object getAnimatedValue() {
        return Float.valueOf(P());
    }

    public long getDuration() {
        C0035m mVar = this.ha;
        if (mVar == null) {
            return 0;
        }
        return (long) mVar.getDuration();
    }

    public float getFrame() {
        return this.ea;
    }

    public float getSpeed() {
        return this.speed;
    }

    public boolean isRunning() {
        return this.running;
    }

    /* access modifiers changed from: protected */
    @MainThread
    public void n(boolean z) {
        Choreographer.getInstance().removeFrameCallback(this);
        if (z) {
            this.running = false;
        }
    }

    /* access modifiers changed from: protected */
    public void postFrameCallback() {
        if (isRunning()) {
            n(false);
            Choreographer.getInstance().postFrameCallback(this);
        }
    }

    public void setRepeatMode(int i) {
        super.setRepeatMode(i);
        if (i != 2 && this.ca) {
            this.ca = false;
            W();
        }
    }

    public void setSpeed(float f) {
        this.speed = f;
    }
}
