package io.reactivex.internal.util;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.exceptions.Exceptions;
import io.reactivex.exceptions.MissingBackpressureException;
import io.reactivex.functions.BooleanSupplier;
import io.reactivex.internal.fuseable.SimplePlainQueue;
import io.reactivex.internal.fuseable.SimpleQueue;
import io.reactivex.internal.queue.SpscArrayQueue;
import io.reactivex.internal.queue.SpscLinkedArrayQueue;
import java.util.Queue;
import java.util.concurrent.atomic.AtomicLong;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

public final class QueueDrainHelper {
    static final long COMPLETED_MASK = Long.MIN_VALUE;
    static final long REQUESTED_MASK = Long.MAX_VALUE;

    private QueueDrainHelper() {
        throw new IllegalStateException("No instances!");
    }

    public static <T, U> boolean checkTerminated(boolean z, boolean z2, Observer<?> observer, boolean z3, SimpleQueue<?> simpleQueue, Disposable disposable, ObservableQueueDrain<T, U> observableQueueDrain) {
        if (observableQueueDrain.cancelled()) {
            simpleQueue.clear();
            disposable.dispose();
            return true;
        } else if (!z) {
            return false;
        } else {
            if (!z3) {
                Throwable error = observableQueueDrain.error();
                if (error != null) {
                    simpleQueue.clear();
                    if (disposable != null) {
                        disposable.dispose();
                    }
                    observer.onError(error);
                    return true;
                } else if (!z2) {
                    return false;
                } else {
                    if (disposable != null) {
                        disposable.dispose();
                    }
                    observer.onComplete();
                    return true;
                }
            } else if (!z2) {
                return false;
            } else {
                if (disposable != null) {
                    disposable.dispose();
                }
                Throwable error2 = observableQueueDrain.error();
                if (error2 != null) {
                    observer.onError(error2);
                } else {
                    observer.onComplete();
                }
                return true;
            }
        }
    }

    public static <T, U> boolean checkTerminated(boolean z, boolean z2, Subscriber<?> subscriber, boolean z3, SimpleQueue<?> simpleQueue, QueueDrain<T, U> queueDrain) {
        if (queueDrain.cancelled()) {
            simpleQueue.clear();
            return true;
        } else if (!z) {
            return false;
        } else {
            if (!z3) {
                Throwable error = queueDrain.error();
                if (error != null) {
                    simpleQueue.clear();
                    subscriber.onError(error);
                    return true;
                } else if (!z2) {
                    return false;
                } else {
                    subscriber.onComplete();
                    return true;
                }
            } else if (!z2) {
                return false;
            } else {
                Throwable error2 = queueDrain.error();
                if (error2 != null) {
                    subscriber.onError(error2);
                } else {
                    subscriber.onComplete();
                }
                return true;
            }
        }
    }

    public static <T> SimpleQueue<T> createQueue(int i) {
        return i < 0 ? new SpscLinkedArrayQueue(-i) : new SpscArrayQueue(i);
    }

    public static <T, U> void drainLoop(SimplePlainQueue<T> simplePlainQueue, Observer<? super U> observer, boolean z, Disposable disposable, ObservableQueueDrain<T, U> observableQueueDrain) {
        int i = 1;
        while (!checkTerminated(observableQueueDrain.done(), simplePlainQueue.isEmpty(), observer, z, simplePlainQueue, disposable, observableQueueDrain)) {
            while (true) {
                boolean done = observableQueueDrain.done();
                T poll = simplePlainQueue.poll();
                boolean z2 = poll == null;
                if (!checkTerminated(done, z2, observer, z, simplePlainQueue, disposable, observableQueueDrain)) {
                    if (z2) {
                        i = observableQueueDrain.leave(-i);
                        if (i == 0) {
                            return;
                        }
                    } else {
                        observableQueueDrain.accept(observer, poll);
                    }
                } else {
                    return;
                }
            }
        }
    }

    public static <T, U> void drainMaxLoop(SimplePlainQueue<T> simplePlainQueue, Subscriber<? super U> subscriber, boolean z, Disposable disposable, QueueDrain<T, U> queueDrain) {
        int i = 1;
        while (true) {
            boolean done = queueDrain.done();
            T poll = simplePlainQueue.poll();
            boolean z2 = poll == null;
            if (checkTerminated(done, z2, subscriber, z, simplePlainQueue, queueDrain)) {
                if (disposable != null) {
                    disposable.dispose();
                    return;
                }
                return;
            } else if (z2) {
                i = queueDrain.leave(-i);
                if (i == 0) {
                    return;
                }
            } else {
                long requested = queueDrain.requested();
                if (requested == 0) {
                    simplePlainQueue.clear();
                    if (disposable != null) {
                        disposable.dispose();
                    }
                    subscriber.onError(new MissingBackpressureException("Could not emit value due to lack of requests."));
                    return;
                } else if (queueDrain.accept(subscriber, poll) && requested != REQUESTED_MASK) {
                    queueDrain.produced(1);
                }
            }
        }
    }

    static boolean isCancelled(BooleanSupplier booleanSupplier) {
        try {
            return booleanSupplier.getAsBoolean();
        } catch (Throwable th) {
            Exceptions.throwIfFatal(th);
            return true;
        }
    }

    public static <T> void postComplete(Subscriber<? super T> subscriber, Queue<T> queue, AtomicLong atomicLong, BooleanSupplier booleanSupplier) {
        long j;
        long j2;
        if (queue.isEmpty()) {
            subscriber.onComplete();
        } else if (!postCompleteDrain(atomicLong.get(), subscriber, queue, atomicLong, booleanSupplier)) {
            do {
                j = atomicLong.get();
                if ((j & COMPLETED_MASK) == 0) {
                    j2 = j | COMPLETED_MASK;
                } else {
                    return;
                }
            } while (!atomicLong.compareAndSet(j, j2));
            if (j != 0) {
                postCompleteDrain(j2, subscriber, queue, atomicLong, booleanSupplier);
            }
        }
    }

    static <T> boolean postCompleteDrain(long j, Subscriber<? super T> subscriber, Queue<T> queue, AtomicLong atomicLong, BooleanSupplier booleanSupplier) {
        long j2 = j & COMPLETED_MASK;
        while (true) {
            if (j2 != j) {
                if (isCancelled(booleanSupplier)) {
                    return true;
                }
                T poll = queue.poll();
                if (poll == null) {
                    subscriber.onComplete();
                    return true;
                }
                subscriber.onNext(poll);
                j2++;
            } else if (isCancelled(booleanSupplier)) {
                return true;
            } else {
                if (queue.isEmpty()) {
                    subscriber.onComplete();
                    return true;
                }
                j = atomicLong.get();
                if (j == j2) {
                    long addAndGet = atomicLong.addAndGet(-(j2 & REQUESTED_MASK));
                    if ((REQUESTED_MASK & addAndGet) == 0) {
                        return false;
                    }
                    j = addAndGet;
                    j2 = addAndGet & COMPLETED_MASK;
                } else {
                    continue;
                }
            }
        }
    }

    public static <T> boolean postCompleteRequest(long j, Subscriber<? super T> subscriber, Queue<T> queue, AtomicLong atomicLong, BooleanSupplier booleanSupplier) {
        long j2;
        long j3;
        do {
            j2 = atomicLong.get();
            j3 = REQUESTED_MASK & j2;
        } while (!atomicLong.compareAndSet(j2, BackpressureHelper.addCap(j3, j) | (j2 & COMPLETED_MASK)));
        if (j2 != COMPLETED_MASK) {
            return false;
        }
        postCompleteDrain(j | COMPLETED_MASK, subscriber, queue, atomicLong, booleanSupplier);
        return true;
    }

    public static void request(Subscription subscription, int i) {
        subscription.request(i < 0 ? REQUESTED_MASK : (long) i);
    }
}
