package io.reactivex.internal.operators.observable;

import io.reactivex.Observer;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.disposables.Disposables;
import io.reactivex.functions.Consumer;
import io.reactivex.internal.disposables.DisposableHelper;
import io.reactivex.observables.ConnectableObservable;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.locks.ReentrantLock;

public final class ObservableRefCount<T> extends AbstractObservableWithUpstream<T, T> {
    volatile CompositeDisposable baseDisposable = new CompositeDisposable();
    final ReentrantLock lock = new ReentrantLock();
    final ConnectableObservable<? extends T> source;
    final AtomicInteger subscriptionCount = new AtomicInteger();

    /* access modifiers changed from: package-private */
    public final class ConnectionObserver extends AtomicReference<Disposable> implements Observer<T>, Disposable {
        private static final long serialVersionUID = 3813126992133394324L;
        final CompositeDisposable currentBase;
        final Disposable resource;
        final Observer<? super T> subscriber;

        ConnectionObserver(Observer<? super T> observer, CompositeDisposable compositeDisposable, Disposable disposable) {
            this.subscriber = observer;
            this.currentBase = compositeDisposable;
            this.resource = disposable;
        }

        /* access modifiers changed from: package-private */
        public void cleanup() {
            ObservableRefCount.this.lock.lock();
            try {
                if (ObservableRefCount.this.baseDisposable == this.currentBase) {
                    if (ObservableRefCount.this.source instanceof Disposable) {
                        ((Disposable) ObservableRefCount.this.source).dispose();
                    }
                    ObservableRefCount.this.baseDisposable.dispose();
                    ObservableRefCount.this.baseDisposable = new CompositeDisposable();
                    ObservableRefCount.this.subscriptionCount.set(0);
                }
            } finally {
                ObservableRefCount.this.lock.unlock();
            }
        }

        @Override // io.reactivex.disposables.Disposable
        public void dispose() {
            DisposableHelper.dispose(this);
            this.resource.dispose();
        }

        @Override // io.reactivex.disposables.Disposable
        public boolean isDisposed() {
            return DisposableHelper.isDisposed((Disposable) get());
        }

        @Override // io.reactivex.Observer
        public void onComplete() {
            cleanup();
            this.subscriber.onComplete();
        }

        @Override // io.reactivex.Observer
        public void onError(Throwable th) {
            cleanup();
            this.subscriber.onError(th);
        }

        @Override // io.reactivex.Observer
        public void onNext(T t) {
            this.subscriber.onNext(t);
        }

        @Override // io.reactivex.Observer
        public void onSubscribe(Disposable disposable) {
            DisposableHelper.setOnce(this, disposable);
        }
    }

    /* access modifiers changed from: package-private */
    public final class DisposeConsumer implements Consumer<Disposable> {
        private final Observer<? super T> observer;
        private final AtomicBoolean writeLocked;

        DisposeConsumer(Observer<? super T> observer2, AtomicBoolean atomicBoolean) {
            this.observer = observer2;
            this.writeLocked = atomicBoolean;
        }

        public void accept(Disposable disposable) {
            try {
                ObservableRefCount.this.baseDisposable.add(disposable);
                ObservableRefCount.this.doSubscribe(this.observer, ObservableRefCount.this.baseDisposable);
            } finally {
                ObservableRefCount.this.lock.unlock();
                this.writeLocked.set(false);
            }
        }
    }

    /* access modifiers changed from: package-private */
    public final class DisposeTask implements Runnable {
        private final CompositeDisposable current;

        DisposeTask(CompositeDisposable compositeDisposable) {
            this.current = compositeDisposable;
        }

        public void run() {
            ObservableRefCount.this.lock.lock();
            try {
                if (ObservableRefCount.this.baseDisposable == this.current && ObservableRefCount.this.subscriptionCount.decrementAndGet() == 0) {
                    if (ObservableRefCount.this.source instanceof Disposable) {
                        ((Disposable) ObservableRefCount.this.source).dispose();
                    }
                    ObservableRefCount.this.baseDisposable.dispose();
                    ObservableRefCount.this.baseDisposable = new CompositeDisposable();
                }
            } finally {
                ObservableRefCount.this.lock.unlock();
            }
        }
    }

    public ObservableRefCount(ConnectableObservable<T> connectableObservable) {
        super(connectableObservable);
        this.source = connectableObservable;
    }

    private Disposable disconnect(CompositeDisposable compositeDisposable) {
        return Disposables.fromRunnable(new DisposeTask(compositeDisposable));
    }

    private Consumer<Disposable> onSubscribe(Observer<? super T> observer, AtomicBoolean atomicBoolean) {
        return new DisposeConsumer(observer, atomicBoolean);
    }

    /* access modifiers changed from: package-private */
    public void doSubscribe(Observer<? super T> observer, CompositeDisposable compositeDisposable) {
        ConnectionObserver connectionObserver = new ConnectionObserver(observer, compositeDisposable, disconnect(compositeDisposable));
        observer.onSubscribe(connectionObserver);
        this.source.subscribe(connectionObserver);
    }

    @Override // io.reactivex.Observable
    public void subscribeActual(Observer<? super T> observer) {
        this.lock.lock();
        if (this.subscriptionCount.incrementAndGet() == 1) {
            AtomicBoolean atomicBoolean = new AtomicBoolean(true);
            try {
                this.source.connect(onSubscribe(observer, atomicBoolean));
            } finally {
                if (atomicBoolean.get()) {
                    this.lock.unlock();
                }
            }
        } else {
            try {
                doSubscribe(observer, this.baseDisposable);
            } finally {
                this.lock.unlock();
            }
        }
    }
}
