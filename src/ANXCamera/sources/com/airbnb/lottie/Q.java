package com.airbnb.lottie;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Matrix;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.graphics.drawable.Animatable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.view.View;
import android.widget.ImageView;
import androidx.annotation.FloatRange;
import androidx.annotation.IntRange;
import androidx.annotation.MainThread;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.airbnb.lottie.c.t;
import com.airbnb.lottie.d.d;
import com.airbnb.lottie.d.e;
import com.airbnb.lottie.d.g;
import com.airbnb.lottie.e.j;
import com.airbnb.lottie.e.l;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

/* compiled from: LottieDrawable */
public class Q extends Drawable implements Drawable.Callback, Animatable {
    public static final int INFINITE = -1;
    public static final int RESTART = 1;
    public static final int REVERSE = 2;
    private static final String TAG = "Q";
    private int alpha = 255;
    private final e animator = new e();
    private C0035m ha;
    private boolean ia = true;
    private boolean isDirty = false;
    private boolean ja = false;
    private final Set<a> ka = new HashSet();
    private final ArrayList<b> la = new ArrayList<>();
    private final ValueAnimator.AnimatorUpdateListener ma = new H(this);
    private final Matrix matrix = new Matrix();
    @Nullable
    private com.airbnb.lottie.b.b na;
    @Nullable
    private String oa;
    @Nullable
    private AbstractC0026d pa;
    @Nullable
    private com.airbnb.lottie.b.a qa;
    @Nullable
    C0012c ra;
    private float scale = 1.0f;
    @Nullable
    private ImageView.ScaleType scaleType;
    @Nullable
    ea ta;
    private boolean ua;
    @Nullable
    private com.airbnb.lottie.model.layer.e va;
    private boolean wa;
    private boolean ya;
    private boolean za = true;

    /* compiled from: LottieDrawable */
    private static class a {
        final String Jb;
        @Nullable
        final String Kb;
        @Nullable
        final ColorFilter colorFilter;

        a(@Nullable String str, @Nullable String str2, @Nullable ColorFilter colorFilter2) {
            this.Jb = str;
            this.Kb = str2;
            this.colorFilter = colorFilter2;
        }

        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof a)) {
                return false;
            }
            a aVar = (a) obj;
            return hashCode() == aVar.hashCode() && this.colorFilter == aVar.colorFilter;
        }

        public int hashCode() {
            String str = this.Jb;
            int hashCode = str != null ? 527 * str.hashCode() : 17;
            String str2 = this.Kb;
            return str2 != null ? hashCode * 31 * str2.hashCode() : hashCode;
        }
    }

    /* access modifiers changed from: private */
    /* compiled from: LottieDrawable */
    public interface b {
        void b(C0035m mVar);
    }

    @Retention(RetentionPolicy.SOURCE)
    /* compiled from: LottieDrawable */
    public @interface c {
    }

    public Q() {
        this.animator.addUpdateListener(this.ma);
    }

    private void Ho() {
        this.va = new com.airbnb.lottie.model.layer.e(this, t.e(this.ha), this.ha.getLayers(), this.ha);
    }

    private com.airbnb.lottie.b.a Io() {
        if (getCallback() == null) {
            return null;
        }
        if (this.qa == null) {
            this.qa = new com.airbnb.lottie.b.a(getCallback(), this.ra);
        }
        return this.qa;
    }

    private com.airbnb.lottie.b.b Jo() {
        if (getCallback() == null) {
            return null;
        }
        com.airbnb.lottie.b.b bVar = this.na;
        if (bVar != null && !bVar.E(getContext())) {
            this.na = null;
        }
        if (this.na == null) {
            this.na = new com.airbnb.lottie.b.b(getCallback(), this.oa, this.pa, this.ha.eb());
        }
        return this.na;
    }

    private void Ko() {
        if (this.ha != null) {
            float scale2 = getScale();
            setBounds(0, 0, (int) (((float) this.ha.getBounds().width()) * scale2), (int) (((float) this.ha.getBounds().height()) * scale2));
        }
    }

    private void a(@NonNull Canvas canvas) {
        if (ImageView.ScaleType.FIT_XY == this.scaleType) {
            b(canvas);
        } else {
            c(canvas);
        }
    }

    private void b(Canvas canvas) {
        float f;
        if (this.va != null) {
            int i = -1;
            Rect bounds = getBounds();
            float width = ((float) bounds.width()) / ((float) this.ha.getBounds().width());
            float height = ((float) bounds.height()) / ((float) this.ha.getBounds().height());
            if (this.za) {
                float min = Math.min(width, height);
                if (min < 1.0f) {
                    f = 1.0f / min;
                    width /= f;
                    height /= f;
                } else {
                    f = 1.0f;
                }
                if (f > 1.0f) {
                    i = canvas.save();
                    float width2 = ((float) bounds.width()) / 2.0f;
                    float height2 = ((float) bounds.height()) / 2.0f;
                    float f2 = width2 * min;
                    float f3 = min * height2;
                    canvas.translate(width2 - f2, height2 - f3);
                    canvas.scale(f, f, f2, f3);
                }
            }
            this.matrix.reset();
            this.matrix.preScale(width, height);
            this.va.a(canvas, this.matrix, this.alpha);
            if (i > 0) {
                canvas.restoreToCount(i);
            }
        }
    }

    private void c(Canvas canvas) {
        float f;
        if (this.va != null) {
            float f2 = this.scale;
            float d2 = d(canvas);
            if (f2 > d2) {
                f = this.scale / d2;
            } else {
                d2 = f2;
                f = 1.0f;
            }
            int i = -1;
            if (f > 1.0f) {
                i = canvas.save();
                float width = ((float) this.ha.getBounds().width()) / 2.0f;
                float height = ((float) this.ha.getBounds().height()) / 2.0f;
                float f3 = width * d2;
                float f4 = height * d2;
                canvas.translate((getScale() * width) - f3, (getScale() * height) - f4);
                canvas.scale(f, f, f3, f4);
            }
            this.matrix.reset();
            this.matrix.preScale(d2, d2);
            this.va.a(canvas, this.matrix, this.alpha);
            if (i > 0) {
                canvas.restoreToCount(i);
            }
        }
    }

    private float d(@NonNull Canvas canvas) {
        return Math.min(((float) canvas.getWidth()) / ((float) this.ha.getBounds().width()), ((float) canvas.getHeight()) / ((float) this.ha.getBounds().height()));
    }

    @Nullable
    private Context getContext() {
        Drawable.Callback callback = getCallback();
        if (callback != null && (callback instanceof View)) {
            return ((View) callback).getContext();
        }
        return null;
    }

    public void O() {
        if (this.animator.isRunning()) {
            this.animator.cancel();
        }
        this.ha = null;
        this.va = null;
        this.na = null;
        this.animator.O();
        invalidateSelf();
    }

    public float Q() {
        return this.animator.Q();
    }

    public float R() {
        return this.animator.R();
    }

    public void S() {
        this.la.clear();
        this.animator.S();
    }

    @MainThread
    public void T() {
        if (this.va == null) {
            this.la.add(new I(this));
            return;
        }
        if (this.ia || getRepeatCount() == 0) {
            this.animator.T();
        }
        if (!this.ia) {
            setFrame((int) (getSpeed() < 0.0f ? R() : Q()));
            this.animator.endAnimation();
        }
    }

    @MainThread
    public void V() {
        if (this.va == null) {
            this.la.add(new J(this));
            return;
        }
        if (this.ia || getRepeatCount() == 0) {
            this.animator.V();
        }
        if (!this.ia) {
            setFrame((int) (getSpeed() < 0.0f ? R() : Q()));
            this.animator.endAnimation();
        }
    }

    public void W() {
        this.animator.W();
    }

    public void Y() {
        this.za = false;
    }

    public boolean Z() {
        return this.ua;
    }

    @Nullable
    public Bitmap a(String str, @Nullable Bitmap bitmap) {
        com.airbnb.lottie.b.b Jo = Jo();
        if (Jo == null) {
            d.warning("Cannot update bitmap. Most likely the drawable is not added to a View which prevents Lottie from getting a Context.");
            return null;
        }
        Bitmap a2 = Jo.a(str, bitmap);
        invalidateSelf();
        return a2;
    }

    public List<com.airbnb.lottie.model.d> a(com.airbnb.lottie.model.d dVar) {
        if (this.va == null) {
            d.warning("Cannot resolve KeyPath. Composition is not set yet.");
            return Collections.emptyList();
        }
        ArrayList arrayList = new ArrayList();
        this.va.a(dVar, 0, arrayList, new com.airbnb.lottie.model.d(new String[0]));
        return arrayList;
    }

    public void a(Animator.AnimatorListener animatorListener) {
        this.animator.addListener(animatorListener);
    }

    public void a(ValueAnimator.AnimatorUpdateListener animatorUpdateListener) {
        this.animator.addUpdateListener(animatorUpdateListener);
    }

    public void a(C0012c cVar) {
        this.ra = cVar;
        com.airbnb.lottie.b.a aVar = this.qa;
        if (aVar != null) {
            aVar.b(cVar);
        }
    }

    public void a(AbstractC0026d dVar) {
        this.pa = dVar;
        com.airbnb.lottie.b.b bVar = this.na;
        if (bVar != null) {
            bVar.b(dVar);
        }
    }

    public void a(ea eaVar) {
        this.ta = eaVar;
    }

    public <T> void a(com.airbnb.lottie.model.d dVar, T t, j<T> jVar) {
        if (this.va == null) {
            this.la.add(new F(this, dVar, t, jVar));
            return;
        }
        boolean z = true;
        if (dVar.Fb() != null) {
            dVar.Fb().a(t, jVar);
        } else {
            List<com.airbnb.lottie.model.d> a2 = a(dVar);
            for (int i = 0; i < a2.size(); i++) {
                a2.get(i).Fb().a(t, jVar);
            }
            z = true ^ a2.isEmpty();
        }
        if (z) {
            invalidateSelf();
            if (t == W.pk) {
                setProgress(getProgress());
            }
        }
    }

    public <T> void a(com.airbnb.lottie.model.d dVar, T t, l<T> lVar) {
        a(dVar, t, new G(this, lVar));
    }

    public C0035m aa() {
        return this.ha;
    }

    public void b(@FloatRange(from = 0.0d, to = 1.0d) float f, @FloatRange(from = 0.0d, to = 1.0d) float f2) {
        C0035m mVar = this.ha;
        if (mVar == null) {
            this.la.add(new C(this, f, f2));
        } else {
            b((int) g.lerp(mVar.hb(), this.ha.db(), f), (int) g.lerp(this.ha.hb(), this.ha.db(), f2));
        }
    }

    public void b(int i, int i2) {
        if (this.ha == null) {
            this.la.add(new B(this, i, i2));
        } else {
            this.animator.a((float) i, ((float) i2) + 0.99f);
        }
    }

    public void b(Animator.AnimatorListener animatorListener) {
        this.animator.removeListener(animatorListener);
    }

    public void b(ValueAnimator.AnimatorUpdateListener animatorUpdateListener) {
        this.animator.removeUpdateListener(animatorUpdateListener);
    }

    /* access modifiers changed from: package-private */
    public void b(Boolean bool) {
        this.ia = bool.booleanValue();
    }

    public void b(String str, String str2, boolean z) {
        C0035m mVar = this.ha;
        if (mVar == null) {
            this.la.add(new A(this, str, str2, z));
            return;
        }
        com.airbnb.lottie.model.g G = mVar.G(str);
        if (G != null) {
            int i = (int) G.lb;
            com.airbnb.lottie.model.g G2 = this.ha.G(str2);
            if (str2 != null) {
                b(i, (int) (G2.lb + (z ? 1.0f : 0.0f)));
                return;
            }
            throw new IllegalArgumentException("Cannot find marker with name " + str2 + ".");
        }
        throw new IllegalArgumentException("Cannot find marker with name " + str + ".");
    }

    @Nullable
    public String ba() {
        return this.oa;
    }

    public boolean c(C0035m mVar) {
        if (this.ha == mVar) {
            return false;
        }
        this.isDirty = false;
        O();
        this.ha = mVar;
        Ho();
        this.animator.c(mVar);
        setProgress(this.animator.getAnimatedFraction());
        setScale(this.scale);
        Ko();
        Iterator it = new ArrayList(this.la).iterator();
        while (it.hasNext()) {
            ((b) it.next()).b(mVar);
            it.remove();
        }
        this.la.clear();
        mVar.r(this.wa);
        return true;
    }

    @Nullable
    public ca ca() {
        C0035m mVar = this.ha;
        if (mVar != null) {
            return mVar.ca();
        }
        return null;
    }

    public void cancelAnimation() {
        this.la.clear();
        this.animator.cancel();
    }

    @Nullable
    public ea da() {
        return this.ta;
    }

    public void draw(@NonNull Canvas canvas) {
        this.isDirty = false;
        C0027e.beginSection("Drawable#draw");
        if (this.ja) {
            try {
                a(canvas);
            } catch (Throwable th) {
                d.b("Lottie crashed in draw!", th);
            }
        } else {
            a(canvas);
        }
        C0027e.D("Drawable#draw");
    }

    public boolean ea() {
        com.airbnb.lottie.model.layer.e eVar = this.va;
        return eVar != null && eVar.ea();
    }

    @MainThread
    public void endAnimation() {
        this.la.clear();
        this.animator.endAnimation();
    }

    public void f(int i) {
        if (this.ha == null) {
            this.la.add(new K(this, i));
        } else {
            this.animator.f(i);
        }
    }

    public boolean fa() {
        com.airbnb.lottie.model.layer.e eVar = this.va;
        return eVar != null && eVar.fa();
    }

    public void g(@FloatRange(from = 0.0d, to = 1.0d) float f) {
        C0035m mVar = this.ha;
        if (mVar == null) {
            this.la.add(new N(this, f));
        } else {
            j((int) g.lerp(mVar.hb(), this.ha.db(), f));
        }
    }

    public boolean ga() {
        return this.ya;
    }

    public int getAlpha() {
        return this.alpha;
    }

    public int getFrame() {
        return (int) this.animator.getFrame();
    }

    public int getIntrinsicHeight() {
        C0035m mVar = this.ha;
        if (mVar == null) {
            return -1;
        }
        return (int) (((float) mVar.getBounds().height()) * getScale());
    }

    public int getIntrinsicWidth() {
        C0035m mVar = this.ha;
        if (mVar == null) {
            return -1;
        }
        return (int) (((float) mVar.getBounds().width()) * getScale());
    }

    public int getOpacity() {
        return -3;
    }

    @FloatRange(from = 0.0d, to = 1.0d)
    public float getProgress() {
        return this.animator.P();
    }

    public int getRepeatCount() {
        return this.animator.getRepeatCount();
    }

    public int getRepeatMode() {
        return this.animator.getRepeatMode();
    }

    public float getScale() {
        return this.scale;
    }

    public float getSpeed() {
        return this.animator.getSpeed();
    }

    public void h(float f) {
        C0035m mVar = this.ha;
        if (mVar == null) {
            this.la.add(new L(this, f));
        } else {
            f((int) g.lerp(mVar.hb(), this.ha.db(), f));
        }
    }

    public boolean ha() {
        return this.ua;
    }

    public void ia() {
        this.animator.removeAllListeners();
    }

    public void invalidateDrawable(@NonNull Drawable drawable) {
        Drawable.Callback callback = getCallback();
        if (callback != null) {
            callback.invalidateDrawable(this);
        }
    }

    public void invalidateSelf() {
        if (!this.isDirty) {
            this.isDirty = true;
            Drawable.Callback callback = getCallback();
            if (callback != null) {
                callback.invalidateDrawable(this);
            }
        }
    }

    public boolean isAnimating() {
        e eVar = this.animator;
        if (eVar == null) {
            return false;
        }
        return eVar.isRunning();
    }

    public boolean isLooping() {
        return this.animator.getRepeatCount() == -1;
    }

    public boolean isRunning() {
        return isAnimating();
    }

    public void j(int i) {
        if (this.ha == null) {
            this.la.add(new M(this, i));
        } else {
            this.animator.f(((float) i) + 0.99f);
        }
    }

    public boolean ja() {
        return this.ta == null && this.ha.getCharacters().size() > 0;
    }

    @Nullable
    public Bitmap k(String str) {
        com.airbnb.lottie.b.b Jo = Jo();
        if (Jo != null) {
            return Jo.L(str);
        }
        return null;
    }

    @Nullable
    public Typeface l(String str, String str2) {
        com.airbnb.lottie.b.a Io = Io();
        if (Io != null) {
            return Io.l(str, str2);
        }
        return null;
    }

    public void l(@Nullable String str) {
        this.oa = str;
    }

    public void m(String str) {
        C0035m mVar = this.ha;
        if (mVar == null) {
            this.la.add(new P(this, str));
            return;
        }
        com.airbnb.lottie.model.g G = mVar.G(str);
        if (G != null) {
            j((int) (G.lb + G.Yd));
            return;
        }
        throw new IllegalArgumentException("Cannot find marker with name " + str + ".");
    }

    public void n(String str) {
        C0035m mVar = this.ha;
        if (mVar == null) {
            this.la.add(new C0048z(this, str));
            return;
        }
        com.airbnb.lottie.model.g G = mVar.G(str);
        if (G != null) {
            int i = (int) G.lb;
            b(i, ((int) G.Yd) + i);
            return;
        }
        throw new IllegalArgumentException("Cannot find marker with name " + str + ".");
    }

    public void o(String str) {
        C0035m mVar = this.ha;
        if (mVar == null) {
            this.la.add(new O(this, str));
            return;
        }
        com.airbnb.lottie.model.g G = mVar.G(str);
        if (G != null) {
            f((int) G.lb);
            return;
        }
        throw new IllegalArgumentException("Cannot find marker with name " + str + ".");
    }

    public void o(boolean z) {
        if (this.ua != z) {
            if (Build.VERSION.SDK_INT < 19) {
                d.warning("Merge paths are not supported pre-Kit Kat.");
                return;
            }
            this.ua = z;
            if (this.ha != null) {
                Ho();
            }
        }
    }

    @Deprecated
    public void p(boolean z) {
        this.animator.setRepeatCount(z ? -1 : 0);
    }

    public void q(boolean z) {
        this.ya = z;
    }

    public void r(boolean z) {
        this.wa = z;
        C0035m mVar = this.ha;
        if (mVar != null) {
            mVar.r(z);
        }
    }

    public void removeAllUpdateListeners() {
        this.animator.removeAllUpdateListeners();
        this.animator.addUpdateListener(this.ma);
    }

    public void s(boolean z) {
        this.ja = z;
    }

    public void scheduleDrawable(@NonNull Drawable drawable, @NonNull Runnable runnable, long j) {
        Drawable.Callback callback = getCallback();
        if (callback != null) {
            callback.scheduleDrawable(this, runnable, j);
        }
    }

    public void setAlpha(@IntRange(from = 0, to = 255) int i) {
        this.alpha = i;
        invalidateSelf();
    }

    public void setColorFilter(@Nullable ColorFilter colorFilter) {
        d.warning("Use addColorFilter instead.");
    }

    public void setFrame(int i) {
        if (this.ha == null) {
            this.la.add(new D(this, i));
        } else {
            this.animator.e((float) i);
        }
    }

    public void setProgress(@FloatRange(from = 0.0d, to = 1.0d) float f) {
        if (this.ha == null) {
            this.la.add(new E(this, f));
            return;
        }
        C0027e.beginSection("Drawable#setProgress");
        this.animator.e(g.lerp(this.ha.hb(), this.ha.db(), f));
        C0027e.D("Drawable#setProgress");
    }

    public void setRepeatCount(int i) {
        this.animator.setRepeatCount(i);
    }

    public void setRepeatMode(int i) {
        this.animator.setRepeatMode(i);
    }

    public void setScale(float f) {
        this.scale = f;
        Ko();
    }

    /* access modifiers changed from: package-private */
    public void setScaleType(ImageView.ScaleType scaleType2) {
        this.scaleType = scaleType2;
    }

    public void setSpeed(float f) {
        this.animator.setSpeed(f);
    }

    @MainThread
    public void start() {
        T();
    }

    @MainThread
    public void stop() {
        endAnimation();
    }

    public void unscheduleDrawable(@NonNull Drawable drawable, @NonNull Runnable runnable) {
        Drawable.Callback callback = getCallback();
        if (callback != null) {
            callback.unscheduleDrawable(this, runnable);
        }
    }
}
