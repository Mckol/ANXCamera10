package io.reactivex.internal.operators.maybe;

import io.reactivex.MaybeObserver;
import io.reactivex.MaybeSource;
import io.reactivex.disposables.Disposable;
import io.reactivex.exceptions.CompositeException;
import io.reactivex.exceptions.Exceptions;
import io.reactivex.functions.Function;
import io.reactivex.internal.disposables.DisposableHelper;
import io.reactivex.internal.functions.ObjectHelper;
import java.util.concurrent.atomic.AtomicReference;

public final class MaybeOnErrorNext<T> extends AbstractMaybeWithUpstream<T, T> {
    final boolean allowFatal;
    final Function<? super Throwable, ? extends MaybeSource<? extends T>> resumeFunction;

    static final class OnErrorNextMaybeObserver<T> extends AtomicReference<Disposable> implements MaybeObserver<T>, Disposable {
        private static final long serialVersionUID = 2026620218879969836L;
        final MaybeObserver<? super T> actual;
        final boolean allowFatal;
        final Function<? super Throwable, ? extends MaybeSource<? extends T>> resumeFunction;

        static final class NextMaybeObserver<T> implements MaybeObserver<T> {
            final MaybeObserver<? super T> actual;

            /* renamed from: d  reason: collision with root package name */
            final AtomicReference<Disposable> f751d;

            NextMaybeObserver(MaybeObserver<? super T> maybeObserver, AtomicReference<Disposable> atomicReference) {
                this.actual = maybeObserver;
                this.f751d = atomicReference;
            }

            @Override // io.reactivex.MaybeObserver
            public void onComplete() {
                this.actual.onComplete();
            }

            @Override // io.reactivex.MaybeObserver
            public void onError(Throwable th) {
                this.actual.onError(th);
            }

            @Override // io.reactivex.MaybeObserver
            public void onSubscribe(Disposable disposable) {
                DisposableHelper.setOnce(this.f751d, disposable);
            }

            @Override // io.reactivex.MaybeObserver
            public void onSuccess(T t) {
                this.actual.onSuccess(t);
            }
        }

        OnErrorNextMaybeObserver(MaybeObserver<? super T> maybeObserver, Function<? super Throwable, ? extends MaybeSource<? extends T>> function, boolean z) {
            this.actual = maybeObserver;
            this.resumeFunction = function;
            this.allowFatal = z;
        }

        @Override // io.reactivex.disposables.Disposable
        public void dispose() {
            DisposableHelper.dispose(this);
        }

        @Override // io.reactivex.disposables.Disposable
        public boolean isDisposed() {
            return DisposableHelper.isDisposed((Disposable) get());
        }

        @Override // io.reactivex.MaybeObserver
        public void onComplete() {
            this.actual.onComplete();
        }

        @Override // io.reactivex.MaybeObserver
        public void onError(Throwable th) {
            if (this.allowFatal || (th instanceof Exception)) {
                try {
                    Object apply = this.resumeFunction.apply(th);
                    ObjectHelper.requireNonNull(apply, "The resumeFunction returned a null MaybeSource");
                    MaybeSource maybeSource = (MaybeSource) apply;
                    DisposableHelper.replace(this, null);
                    maybeSource.subscribe(new NextMaybeObserver(this.actual, this));
                } catch (Throwable th2) {
                    Exceptions.throwIfFatal(th2);
                    this.actual.onError(new CompositeException(th, th2));
                }
            } else {
                this.actual.onError(th);
            }
        }

        @Override // io.reactivex.MaybeObserver
        public void onSubscribe(Disposable disposable) {
            if (DisposableHelper.setOnce(this, disposable)) {
                this.actual.onSubscribe(this);
            }
        }

        @Override // io.reactivex.MaybeObserver
        public void onSuccess(T t) {
            this.actual.onSuccess(t);
        }
    }

    public MaybeOnErrorNext(MaybeSource<T> maybeSource, Function<? super Throwable, ? extends MaybeSource<? extends T>> function, boolean z) {
        super(maybeSource);
        this.resumeFunction = function;
        this.allowFatal = z;
    }

    /* access modifiers changed from: protected */
    @Override // io.reactivex.Maybe
    public void subscribeActual(MaybeObserver<? super T> maybeObserver) {
        this.source.subscribe(new OnErrorNextMaybeObserver(maybeObserver, this.resumeFunction, this.allowFatal));
    }
}
