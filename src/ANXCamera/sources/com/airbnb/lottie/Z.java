package com.airbnb.lottie;

import android.os.Handler;
import android.os.Looper;
import androidx.annotation.Nullable;
import androidx.annotation.RestrictTo;
import com.airbnb.lottie.d.d;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;

/* compiled from: LottieTask */
public class Z<T> {
    public static Executor Ob = Executors.newCachedThreadPool();
    private final Set<T<T>> Mb;
    private final Set<T<Throwable>> Nb;
    private final Handler handler;
    @Nullable
    private volatile X<T> result;

    /* compiled from: LottieTask */
    private class a extends FutureTask<X<T>> {
        a(Callable<X<T>> callable) {
            super(callable);
        }

        /* access modifiers changed from: protected */
        public void done() {
            if (!isCancelled()) {
                try {
                    Z.this.a((Z) ((X) get()));
                } catch (InterruptedException | ExecutionException e2) {
                    Z.this.a((Z) new X(e2));
                }
            }
        }
    }

    @RestrictTo({RestrictTo.Scope.LIBRARY})
    public Z(Callable<X<T>> callable) {
        this(callable, false);
    }

    @RestrictTo({RestrictTo.Scope.LIBRARY})
    Z(Callable<X<T>> callable, boolean z) {
        this.Mb = new LinkedHashSet(1);
        this.Nb = new LinkedHashSet(1);
        this.handler = new Handler(Looper.getMainLooper());
        this.result = null;
        if (z) {
            try {
                a(callable.call());
            } catch (Throwable th) {
                a(new X<>(th));
            }
        } else {
            Ob.execute(new a(callable));
        }
    }

    /* access modifiers changed from: private */
    /* access modifiers changed from: public */
    private void a(@Nullable X<T> x) {
        if (this.result == null) {
            this.result = x;
            tb();
            return;
        }
        throw new IllegalStateException("A task may only be set once.");
    }

    /* access modifiers changed from: private */
    public synchronized void d(T t) {
        for (T t2 : new ArrayList(this.Mb)) {
            t2.a(t);
        }
    }

    /* access modifiers changed from: private */
    public synchronized void j(Throwable th) {
        ArrayList<T> arrayList = new ArrayList(this.Nb);
        if (arrayList.isEmpty()) {
            d.c("Lottie encountered an error but no failure listener was added:", th);
            return;
        }
        for (T t : arrayList) {
            t.a(th);
        }
    }

    private void tb() {
        this.handler.post(new Y(this));
    }

    public synchronized Z<T> b(T<Throwable> t) {
        if (!(this.result == null || this.result.getException() == null)) {
            t.a(this.result.getException());
        }
        this.Nb.add(t);
        return this;
    }

    public synchronized Z<T> c(T<T> t) {
        if (!(this.result == null || this.result.getValue() == null)) {
            t.a(this.result.getValue());
        }
        this.Mb.add(t);
        return this;
    }

    public synchronized Z<T> d(T<Throwable> t) {
        this.Nb.remove(t);
        return this;
    }

    public synchronized Z<T> e(T<T> t) {
        this.Mb.remove(t);
        return this;
    }
}
