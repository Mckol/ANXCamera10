package io.reactivex.internal.operators.observable;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.exceptions.Exceptions;
import io.reactivex.internal.disposables.DisposableHelper;
import io.reactivex.internal.functions.ObjectHelper;
import io.reactivex.internal.fuseable.SimplePlainQueue;
import io.reactivex.internal.observers.QueueDrainObserver;
import io.reactivex.internal.queue.MpscLinkedQueue;
import io.reactivex.internal.util.NotificationLite;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.observers.SerializedObserver;
import io.reactivex.plugins.RxJavaPlugins;
import io.reactivex.subjects.UnicastSubject;
import java.util.concurrent.Callable;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicReference;

public final class ObservableWindowBoundarySupplier<T, B> extends AbstractObservableWithUpstream<T, Observable<T>> {
    final int bufferSize;
    final Callable<? extends ObservableSource<B>> other;

    /* access modifiers changed from: package-private */
    public static final class WindowBoundaryInnerObserver<T, B> extends DisposableObserver<B> {
        boolean done;
        final WindowBoundaryMainObserver<T, B> parent;

        WindowBoundaryInnerObserver(WindowBoundaryMainObserver<T, B> windowBoundaryMainObserver) {
            this.parent = windowBoundaryMainObserver;
        }

        @Override // io.reactivex.Observer
        public void onComplete() {
            if (!this.done) {
                this.done = true;
                this.parent.onComplete();
            }
        }

        @Override // io.reactivex.Observer
        public void onError(Throwable th) {
            if (this.done) {
                RxJavaPlugins.onError(th);
                return;
            }
            this.done = true;
            this.parent.onError(th);
        }

        @Override // io.reactivex.Observer
        public void onNext(B b2) {
            if (!this.done) {
                this.done = true;
                dispose();
                this.parent.next();
            }
        }
    }

    static final class WindowBoundaryMainObserver<T, B> extends QueueDrainObserver<T, Object, Observable<T>> implements Disposable {
        static final Object NEXT = new Object();
        final AtomicReference<Disposable> boundary = new AtomicReference<>();
        final int bufferSize;
        final Callable<? extends ObservableSource<B>> other;
        Disposable s;
        UnicastSubject<T> window;
        final AtomicLong windows = new AtomicLong();

        WindowBoundaryMainObserver(Observer<? super Observable<T>> observer, Callable<? extends ObservableSource<B>> callable, int i) {
            super(observer, new MpscLinkedQueue());
            this.other = callable;
            this.bufferSize = i;
            this.windows.lazySet(1);
        }

        @Override // io.reactivex.disposables.Disposable
        public void dispose() {
            this.cancelled = true;
        }

        /* JADX DEBUG: Failed to insert an additional move for type inference into block B:33:0x000a */
        /* JADX DEBUG: Failed to insert an additional move for type inference into block B:40:0x000a */
        /* JADX WARN: Multi-variable type inference failed */
        /* JADX WARN: Type inference failed for: r2v1, types: [io.reactivex.subjects.UnicastSubject] */
        /* JADX WARN: Type inference failed for: r2v2 */
        /* access modifiers changed from: package-private */
        public void drainLoop() {
            MpscLinkedQueue mpscLinkedQueue = (MpscLinkedQueue) this.queue;
            Observer<? super V> observer = this.actual;
            UnicastSubject<T> unicastSubject = this.window;
            int i = 1;
            while (true) {
                boolean z = this.done;
                Object poll = mpscLinkedQueue.poll();
                boolean z2 = poll == null;
                if (z && z2) {
                    DisposableHelper.dispose(this.boundary);
                    Throwable th = this.error;
                    if (th != null) {
                        unicastSubject.onError(th);
                        return;
                    } else {
                        unicastSubject.onComplete();
                        return;
                    }
                } else if (z2) {
                    i = leave(-i);
                    if (i == 0) {
                        return;
                    }
                } else if (poll == NEXT) {
                    unicastSubject.onComplete();
                    if (this.windows.decrementAndGet() == 0) {
                        DisposableHelper.dispose(this.boundary);
                        return;
                    } else if (!this.cancelled) {
                        try {
                            Object call = this.other.call();
                            ObjectHelper.requireNonNull(call, "The ObservableSource supplied is null");
                            ObservableSource observableSource = (ObservableSource) call;
                            UnicastSubject<T> create = UnicastSubject.create(this.bufferSize);
                            this.windows.getAndIncrement();
                            this.window = create;
                            observer.onNext(create);
                            WindowBoundaryInnerObserver windowBoundaryInnerObserver = new WindowBoundaryInnerObserver(this);
                            AtomicReference<Disposable> atomicReference = this.boundary;
                            if (atomicReference.compareAndSet(atomicReference.get(), windowBoundaryInnerObserver)) {
                                observableSource.subscribe(windowBoundaryInnerObserver);
                            }
                            unicastSubject = create;
                        } catch (Throwable th2) {
                            Exceptions.throwIfFatal(th2);
                            DisposableHelper.dispose(this.boundary);
                            observer.onError(th2);
                            return;
                        }
                    }
                } else {
                    NotificationLite.getValue(poll);
                    unicastSubject.onNext(poll);
                }
            }
        }

        @Override // io.reactivex.disposables.Disposable
        public boolean isDisposed() {
            return this.cancelled;
        }

        /* access modifiers changed from: package-private */
        public void next() {
            this.queue.offer((U) NEXT);
            if (enter()) {
                drainLoop();
            }
        }

        @Override // io.reactivex.Observer
        public void onComplete() {
            if (!this.done) {
                this.done = true;
                if (enter()) {
                    drainLoop();
                }
                if (this.windows.decrementAndGet() == 0) {
                    DisposableHelper.dispose(this.boundary);
                }
                this.actual.onComplete();
            }
        }

        @Override // io.reactivex.Observer
        public void onError(Throwable th) {
            if (this.done) {
                RxJavaPlugins.onError(th);
                return;
            }
            this.error = th;
            this.done = true;
            if (enter()) {
                drainLoop();
            }
            if (this.windows.decrementAndGet() == 0) {
                DisposableHelper.dispose(this.boundary);
            }
            this.actual.onError(th);
        }

        @Override // io.reactivex.Observer
        public void onNext(T t) {
            if (fastEnter()) {
                this.window.onNext(t);
                if (leave(-1) == 0) {
                    return;
                }
            } else {
                SimplePlainQueue<U> simplePlainQueue = this.queue;
                NotificationLite.next(t);
                simplePlainQueue.offer(t);
                if (!enter()) {
                    return;
                }
            }
            drainLoop();
        }

        @Override // io.reactivex.Observer
        public void onSubscribe(Disposable disposable) {
            if (DisposableHelper.validate(this.s, disposable)) {
                this.s = disposable;
                Observer<? super V> observer = this.actual;
                observer.onSubscribe(this);
                if (!this.cancelled) {
                    try {
                        Object call = this.other.call();
                        ObjectHelper.requireNonNull(call, "The first window ObservableSource supplied is null");
                        ObservableSource observableSource = (ObservableSource) call;
                        UnicastSubject<T> create = UnicastSubject.create(this.bufferSize);
                        this.window = create;
                        observer.onNext(create);
                        WindowBoundaryInnerObserver windowBoundaryInnerObserver = new WindowBoundaryInnerObserver(this);
                        if (this.boundary.compareAndSet(null, windowBoundaryInnerObserver)) {
                            this.windows.getAndIncrement();
                            observableSource.subscribe(windowBoundaryInnerObserver);
                        }
                    } catch (Throwable th) {
                        Exceptions.throwIfFatal(th);
                        disposable.dispose();
                        observer.onError(th);
                    }
                }
            }
        }
    }

    public ObservableWindowBoundarySupplier(ObservableSource<T> observableSource, Callable<? extends ObservableSource<B>> callable, int i) {
        super(observableSource);
        this.other = callable;
        this.bufferSize = i;
    }

    @Override // io.reactivex.Observable
    public void subscribeActual(Observer<? super Observable<T>> observer) {
        this.source.subscribe(new WindowBoundaryMainObserver(new SerializedObserver(observer), this.other, this.bufferSize));
    }
}
