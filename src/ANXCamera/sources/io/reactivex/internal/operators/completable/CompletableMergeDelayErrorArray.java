package io.reactivex.internal.operators.completable;

import io.reactivex.Completable;
import io.reactivex.CompletableObserver;
import io.reactivex.CompletableSource;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.internal.util.AtomicThrowable;
import io.reactivex.plugins.RxJavaPlugins;
import java.util.concurrent.atomic.AtomicInteger;

public final class CompletableMergeDelayErrorArray extends Completable {
    final CompletableSource[] sources;

    static final class MergeInnerCompletableObserver implements CompletableObserver {
        final CompletableObserver actual;
        final AtomicThrowable error;
        final CompositeDisposable set;
        final AtomicInteger wip;

        MergeInnerCompletableObserver(CompletableObserver completableObserver, CompositeDisposable compositeDisposable, AtomicThrowable atomicThrowable, AtomicInteger atomicInteger) {
            this.actual = completableObserver;
            this.set = compositeDisposable;
            this.error = atomicThrowable;
            this.wip = atomicInteger;
        }

        @Override // io.reactivex.CompletableObserver
        public void onComplete() {
            tryTerminate();
        }

        @Override // io.reactivex.CompletableObserver
        public void onError(Throwable th) {
            if (this.error.addThrowable(th)) {
                tryTerminate();
            } else {
                RxJavaPlugins.onError(th);
            }
        }

        @Override // io.reactivex.CompletableObserver
        public void onSubscribe(Disposable disposable) {
            this.set.add(disposable);
        }

        /* access modifiers changed from: package-private */
        public void tryTerminate() {
            if (this.wip.decrementAndGet() == 0) {
                Throwable terminate = this.error.terminate();
                if (terminate == null) {
                    this.actual.onComplete();
                } else {
                    this.actual.onError(terminate);
                }
            }
        }
    }

    public CompletableMergeDelayErrorArray(CompletableSource[] completableSourceArr) {
        this.sources = completableSourceArr;
    }

    @Override // io.reactivex.Completable
    public void subscribeActual(CompletableObserver completableObserver) {
        CompositeDisposable compositeDisposable = new CompositeDisposable();
        AtomicInteger atomicInteger = new AtomicInteger(this.sources.length + 1);
        AtomicThrowable atomicThrowable = new AtomicThrowable();
        completableObserver.onSubscribe(compositeDisposable);
        CompletableSource[] completableSourceArr = this.sources;
        for (CompletableSource completableSource : completableSourceArr) {
            if (!compositeDisposable.isDisposed()) {
                if (completableSource == null) {
                    atomicThrowable.addThrowable(new NullPointerException("A completable source is null"));
                    atomicInteger.decrementAndGet();
                } else {
                    completableSource.subscribe(new MergeInnerCompletableObserver(completableObserver, compositeDisposable, atomicThrowable, atomicInteger));
                }
            } else {
                return;
            }
        }
        if (atomicInteger.decrementAndGet() == 0) {
            Throwable terminate = atomicThrowable.terminate();
            if (terminate == null) {
                completableObserver.onComplete();
            } else {
                completableObserver.onError(terminate);
            }
        }
    }
}
