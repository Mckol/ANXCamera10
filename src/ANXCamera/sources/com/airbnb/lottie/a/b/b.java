package com.airbnb.lottie.a.b;

import androidx.annotation.FloatRange;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.airbnb.lottie.C0027e;
import com.airbnb.lottie.e.j;
import java.util.ArrayList;
import java.util.List;

/* compiled from: BaseKeyframeAnimation */
public abstract class b<K, A> {
    private boolean Zc = false;
    private final c<K> _c;
    @Nullable
    protected j<A> bd;
    @Nullable
    private A cd = null;
    private float dd = -1.0f;
    private float ed = -1.0f;
    final List<a> listeners = new ArrayList(1);
    private float progress = 0.0f;

    /* compiled from: BaseKeyframeAnimation */
    public interface a {
        void G();
    }

    /* access modifiers changed from: private */
    /* renamed from: com.airbnb.lottie.a.b.b$b  reason: collision with other inner class name */
    /* compiled from: BaseKeyframeAnimation */
    public static final class C0004b<T> implements c<T> {
        private C0004b() {
        }

        @Override // com.airbnb.lottie.a.b.b.c
        public com.airbnb.lottie.e.a<T> C() {
            throw new IllegalStateException("not implemented");
        }

        @Override // com.airbnb.lottie.a.b.b.c
        public float E() {
            return 0.0f;
        }

        @Override // com.airbnb.lottie.a.b.b.c
        public float K() {
            return 1.0f;
        }

        @Override // com.airbnb.lottie.a.b.b.c
        public boolean b(float f) {
            throw new IllegalStateException("not implemented");
        }

        @Override // com.airbnb.lottie.a.b.b.c
        public boolean c(float f) {
            return false;
        }

        @Override // com.airbnb.lottie.a.b.b.c
        public boolean isEmpty() {
            return true;
        }
    }

    /* access modifiers changed from: private */
    /* compiled from: BaseKeyframeAnimation */
    public interface c<T> {
        com.airbnb.lottie.e.a<T> C();

        @FloatRange(from = 0.0d, to = 1.0d)
        float E();

        @FloatRange(from = 0.0d, to = 1.0d)
        float K();

        boolean b(float f);

        boolean c(float f);

        boolean isEmpty();
    }

    /* access modifiers changed from: private */
    /* compiled from: BaseKeyframeAnimation */
    public static final class d<T> implements c<T> {
        private final List<? extends com.airbnb.lottie.e.a<T>> Uc;
        @NonNull
        private com.airbnb.lottie.e.a<T> Vc;
        private com.airbnb.lottie.e.a<T> Wc = null;
        private float Xc = -1.0f;

        d(List<? extends com.airbnb.lottie.e.a<T>> list) {
            this.Uc = list;
            this.Vc = l(0.0f);
        }

        private com.airbnb.lottie.e.a<T> l(float f) {
            List<? extends com.airbnb.lottie.e.a<T>> list = this.Uc;
            com.airbnb.lottie.e.a<T> aVar = (com.airbnb.lottie.e.a) list.get(list.size() - 1);
            if (f >= aVar.nc()) {
                return aVar;
            }
            for (int size = this.Uc.size() - 2; size >= 1; size--) {
                com.airbnb.lottie.e.a<T> aVar2 = (com.airbnb.lottie.e.a) this.Uc.get(size);
                if (this.Vc != aVar2 && aVar2.k(f)) {
                    return aVar2;
                }
            }
            return (com.airbnb.lottie.e.a) this.Uc.get(0);
        }

        @Override // com.airbnb.lottie.a.b.b.c
        @NonNull
        public com.airbnb.lottie.e.a<T> C() {
            return this.Vc;
        }

        @Override // com.airbnb.lottie.a.b.b.c
        public float E() {
            return ((com.airbnb.lottie.e.a) this.Uc.get(0)).nc();
        }

        @Override // com.airbnb.lottie.a.b.b.c
        public float K() {
            List<? extends com.airbnb.lottie.e.a<T>> list = this.Uc;
            return ((com.airbnb.lottie.e.a) list.get(list.size() - 1)).K();
        }

        @Override // com.airbnb.lottie.a.b.b.c
        public boolean b(float f) {
            if (this.Wc == this.Vc && this.Xc == f) {
                return true;
            }
            this.Wc = this.Vc;
            this.Xc = f;
            return false;
        }

        @Override // com.airbnb.lottie.a.b.b.c
        public boolean c(float f) {
            if (this.Vc.k(f)) {
                return !this.Vc.isStatic();
            }
            this.Vc = l(f);
            return true;
        }

        @Override // com.airbnb.lottie.a.b.b.c
        public boolean isEmpty() {
            return false;
        }
    }

    /* access modifiers changed from: private */
    /* compiled from: BaseKeyframeAnimation */
    public static final class e<T> implements c<T> {
        private float Xc = -1.0f;
        @NonNull
        private final com.airbnb.lottie.e.a<T> Yc;

        e(List<? extends com.airbnb.lottie.e.a<T>> list) {
            this.Yc = (com.airbnb.lottie.e.a) list.get(0);
        }

        @Override // com.airbnb.lottie.a.b.b.c
        public com.airbnb.lottie.e.a<T> C() {
            return this.Yc;
        }

        @Override // com.airbnb.lottie.a.b.b.c
        public float E() {
            return this.Yc.nc();
        }

        @Override // com.airbnb.lottie.a.b.b.c
        public float K() {
            return this.Yc.K();
        }

        @Override // com.airbnb.lottie.a.b.b.c
        public boolean b(float f) {
            if (this.Xc == f) {
                return true;
            }
            this.Xc = f;
            return false;
        }

        @Override // com.airbnb.lottie.a.b.b.c
        public boolean c(float f) {
            return !this.Yc.isStatic();
        }

        @Override // com.airbnb.lottie.a.b.b.c
        public boolean isEmpty() {
            return false;
        }
    }

    b(List<? extends com.airbnb.lottie.e.a<K>> list) {
        this._c = f(list);
    }

    @FloatRange(from = 0.0d, to = 1.0d)
    private float E() {
        if (this.dd == -1.0f) {
            this.dd = this._c.E();
        }
        return this.dd;
    }

    private static <T> c<T> f(List<? extends com.airbnb.lottie.e.a<T>> list) {
        return list.isEmpty() ? new C0004b() : list.size() == 1 ? new e(list) : new d(list);
    }

    /* access modifiers changed from: protected */
    public com.airbnb.lottie.e.a<K> C() {
        C0027e.beginSection("BaseKeyframeAnimation#getCurrentKeyframe");
        com.airbnb.lottie.e.a<K> C = this._c.C();
        C0027e.D("BaseKeyframeAnimation#getCurrentKeyframe");
        return C;
    }

    /* access modifiers changed from: package-private */
    @FloatRange(from = 0.0d, to = 1.0d)
    public float K() {
        if (this.ed == -1.0f) {
            this.ed = this._c.K();
        }
        return this.ed;
    }

    /* access modifiers changed from: package-private */
    public abstract A a(com.airbnb.lottie.e.a<K> aVar, float f);

    public void a(@Nullable j<A> jVar) {
        j<A> jVar2 = this.bd;
        if (jVar2 != null) {
            jVar2.c(null);
        }
        this.bd = jVar;
        if (jVar != null) {
            jVar.c(this);
        }
    }

    public void b(a aVar) {
        this.listeners.add(aVar);
    }

    public float getProgress() {
        return this.progress;
    }

    public A getValue() {
        float rb = rb();
        if (this.bd == null && this._c.b(rb)) {
            return this.cd;
        }
        A a2 = a(C(), rb);
        this.cd = a2;
        return a2;
    }

    /* access modifiers changed from: protected */
    public float rb() {
        com.airbnb.lottie.e.a<K> C = C();
        if (C.isStatic()) {
            return 0.0f;
        }
        return C.interpolator.getInterpolation(sb());
    }

    /* access modifiers changed from: package-private */
    public float sb() {
        if (this.Zc) {
            return 0.0f;
        }
        com.airbnb.lottie.e.a<K> C = C();
        if (C.isStatic()) {
            return 0.0f;
        }
        return (this.progress - C.nc()) / (C.K() - C.nc());
    }

    public void setProgress(@FloatRange(from = 0.0d, to = 1.0d) float f) {
        if (!this._c.isEmpty()) {
            if (f < E()) {
                f = E();
            } else if (f > K()) {
                f = K();
            }
            if (f != this.progress) {
                this.progress = f;
                if (this._c.c(f)) {
                    tb();
                }
            }
        }
    }

    public void tb() {
        for (int i = 0; i < this.listeners.size(); i++) {
            this.listeners.get(i).G();
        }
    }

    public void ub() {
        this.Zc = true;
    }
}
