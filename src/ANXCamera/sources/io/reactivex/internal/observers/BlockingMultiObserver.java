package io.reactivex.internal.observers;

import io.reactivex.CompletableObserver;
import io.reactivex.MaybeObserver;
import io.reactivex.SingleObserver;
import io.reactivex.disposables.Disposable;
import io.reactivex.internal.util.BlockingHelper;
import io.reactivex.internal.util.ExceptionHelper;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public final class BlockingMultiObserver<T> extends CountDownLatch implements SingleObserver<T>, CompletableObserver, MaybeObserver<T> {
    volatile boolean cancelled;

    /* renamed from: d  reason: collision with root package name */
    Disposable f719d;
    Throwable error;
    T value;

    public BlockingMultiObserver() {
        super(1);
    }

    public boolean blockingAwait(long j, TimeUnit timeUnit) {
        if (getCount() != 0) {
            try {
                BlockingHelper.verifyNonBlocking();
                if (!await(j, timeUnit)) {
                    dispose();
                    return false;
                }
            } catch (InterruptedException e2) {
                dispose();
                throw ExceptionHelper.wrapOrThrow(e2);
            }
        }
        Throwable th = this.error;
        if (th == null) {
            return true;
        }
        throw ExceptionHelper.wrapOrThrow(th);
    }

    public T blockingGet() {
        if (getCount() != 0) {
            try {
                BlockingHelper.verifyNonBlocking();
                await();
            } catch (InterruptedException e2) {
                dispose();
                throw ExceptionHelper.wrapOrThrow(e2);
            }
        }
        Throwable th = this.error;
        if (th == null) {
            return this.value;
        }
        throw ExceptionHelper.wrapOrThrow(th);
    }

    public T blockingGet(T t) {
        if (getCount() != 0) {
            try {
                BlockingHelper.verifyNonBlocking();
                await();
            } catch (InterruptedException e2) {
                dispose();
                throw ExceptionHelper.wrapOrThrow(e2);
            }
        }
        Throwable th = this.error;
        if (th == null) {
            T t2 = this.value;
            return t2 != null ? t2 : t;
        }
        throw ExceptionHelper.wrapOrThrow(th);
    }

    public Throwable blockingGetError() {
        if (getCount() != 0) {
            try {
                BlockingHelper.verifyNonBlocking();
                await();
            } catch (InterruptedException e2) {
                dispose();
                return e2;
            }
        }
        return this.error;
    }

    public Throwable blockingGetError(long j, TimeUnit timeUnit) {
        if (getCount() != 0) {
            try {
                BlockingHelper.verifyNonBlocking();
                if (!await(j, timeUnit)) {
                    dispose();
                    throw ExceptionHelper.wrapOrThrow(new TimeoutException());
                }
            } catch (InterruptedException e2) {
                dispose();
                throw ExceptionHelper.wrapOrThrow(e2);
            }
        }
        return this.error;
    }

    /* access modifiers changed from: package-private */
    public void dispose() {
        this.cancelled = true;
        Disposable disposable = this.f719d;
        if (disposable != null) {
            disposable.dispose();
        }
    }

    @Override // io.reactivex.MaybeObserver, io.reactivex.CompletableObserver
    public void onComplete() {
        countDown();
    }

    @Override // io.reactivex.MaybeObserver, io.reactivex.SingleObserver, io.reactivex.CompletableObserver
    public void onError(Throwable th) {
        this.error = th;
        countDown();
    }

    @Override // io.reactivex.MaybeObserver, io.reactivex.SingleObserver, io.reactivex.CompletableObserver
    public void onSubscribe(Disposable disposable) {
        this.f719d = disposable;
        if (this.cancelled) {
            disposable.dispose();
        }
    }

    @Override // io.reactivex.MaybeObserver, io.reactivex.SingleObserver
    public void onSuccess(T t) {
        this.value = t;
        countDown();
    }
}
