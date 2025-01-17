package io.reactivex.internal.queue;

import io.reactivex.annotations.Nullable;
import io.reactivex.internal.fuseable.SimplePlainQueue;
import io.reactivex.internal.util.Pow2;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicReferenceArray;

public final class SpscLinkedArrayQueue<T> implements SimplePlainQueue<T> {
    private static final Object HAS_NEXT = new Object();
    static final int MAX_LOOK_AHEAD_STEP = Integer.getInteger("jctools.spsc.max.lookahead.step", 4096).intValue();
    AtomicReferenceArray<Object> consumerBuffer;
    final AtomicLong consumerIndex = new AtomicLong();
    final int consumerMask;
    AtomicReferenceArray<Object> producerBuffer;
    final AtomicLong producerIndex = new AtomicLong();
    long producerLookAhead;
    int producerLookAheadStep;
    final int producerMask;

    public SpscLinkedArrayQueue(int i) {
        int roundToPowerOfTwo = Pow2.roundToPowerOfTwo(Math.max(8, i));
        int i2 = roundToPowerOfTwo - 1;
        AtomicReferenceArray<Object> atomicReferenceArray = new AtomicReferenceArray<>(roundToPowerOfTwo + 1);
        this.producerBuffer = atomicReferenceArray;
        this.producerMask = i2;
        adjustLookAheadStep(roundToPowerOfTwo);
        this.consumerBuffer = atomicReferenceArray;
        this.consumerMask = i2;
        this.producerLookAhead = (long) (i2 - 1);
        soProducerIndex(0);
    }

    private void adjustLookAheadStep(int i) {
        this.producerLookAheadStep = Math.min(i / 4, MAX_LOOK_AHEAD_STEP);
    }

    private static int calcDirectOffset(int i) {
        return i;
    }

    private static int calcWrappedOffset(long j, int i) {
        return ((int) j) & i;
    }

    private long lpConsumerIndex() {
        return this.consumerIndex.get();
    }

    private long lpProducerIndex() {
        return this.producerIndex.get();
    }

    private long lvConsumerIndex() {
        return this.consumerIndex.get();
    }

    private static <E> Object lvElement(AtomicReferenceArray<Object> atomicReferenceArray, int i) {
        return atomicReferenceArray.get(i);
    }

    private AtomicReferenceArray<Object> lvNextBufferAndUnlink(AtomicReferenceArray<Object> atomicReferenceArray, int i) {
        AtomicReferenceArray<Object> atomicReferenceArray2 = (AtomicReferenceArray) lvElement(atomicReferenceArray, i);
        soElement(atomicReferenceArray, i, null);
        return atomicReferenceArray2;
    }

    private long lvProducerIndex() {
        return this.producerIndex.get();
    }

    private T newBufferPeek(AtomicReferenceArray<Object> atomicReferenceArray, long j, int i) {
        this.consumerBuffer = atomicReferenceArray;
        return (T) lvElement(atomicReferenceArray, calcWrappedOffset(j, i));
    }

    private T newBufferPoll(AtomicReferenceArray<Object> atomicReferenceArray, long j, int i) {
        this.consumerBuffer = atomicReferenceArray;
        int calcWrappedOffset = calcWrappedOffset(j, i);
        T t = (T) lvElement(atomicReferenceArray, calcWrappedOffset);
        if (t != null) {
            soElement(atomicReferenceArray, calcWrappedOffset, null);
            soConsumerIndex(j + 1);
        }
        return t;
    }

    private void resize(AtomicReferenceArray<Object> atomicReferenceArray, long j, int i, T t, long j2) {
        AtomicReferenceArray<Object> atomicReferenceArray2 = new AtomicReferenceArray<>(atomicReferenceArray.length());
        this.producerBuffer = atomicReferenceArray2;
        this.producerLookAhead = (j2 + j) - 1;
        soElement(atomicReferenceArray2, i, t);
        soNext(atomicReferenceArray, atomicReferenceArray2);
        soElement(atomicReferenceArray, i, HAS_NEXT);
        soProducerIndex(j + 1);
    }

    private void soConsumerIndex(long j) {
        this.consumerIndex.lazySet(j);
    }

    private static void soElement(AtomicReferenceArray<Object> atomicReferenceArray, int i, Object obj) {
        atomicReferenceArray.lazySet(i, obj);
    }

    private void soNext(AtomicReferenceArray<Object> atomicReferenceArray, AtomicReferenceArray<Object> atomicReferenceArray2) {
        soElement(atomicReferenceArray, atomicReferenceArray.length() - 1, atomicReferenceArray2);
    }

    private void soProducerIndex(long j) {
        this.producerIndex.lazySet(j);
    }

    private boolean writeToQueue(AtomicReferenceArray<Object> atomicReferenceArray, T t, long j, int i) {
        soElement(atomicReferenceArray, i, t);
        soProducerIndex(j + 1);
        return true;
    }

    @Override // io.reactivex.internal.fuseable.SimpleQueue
    public void clear() {
        while (true) {
            if (poll() == null && isEmpty()) {
                return;
            }
        }
    }

    @Override // io.reactivex.internal.fuseable.SimpleQueue
    public boolean isEmpty() {
        return lvProducerIndex() == lvConsumerIndex();
    }

    @Override // io.reactivex.internal.fuseable.SimpleQueue
    public boolean offer(T t) {
        if (t != null) {
            AtomicReferenceArray<Object> atomicReferenceArray = this.producerBuffer;
            long lpProducerIndex = lpProducerIndex();
            int i = this.producerMask;
            int calcWrappedOffset = calcWrappedOffset(lpProducerIndex, i);
            if (lpProducerIndex < this.producerLookAhead) {
                return writeToQueue(atomicReferenceArray, t, lpProducerIndex, calcWrappedOffset);
            }
            long j = ((long) this.producerLookAheadStep) + lpProducerIndex;
            if (lvElement(atomicReferenceArray, calcWrappedOffset(j, i)) == null) {
                this.producerLookAhead = j - 1;
                return writeToQueue(atomicReferenceArray, t, lpProducerIndex, calcWrappedOffset);
            } else if (lvElement(atomicReferenceArray, calcWrappedOffset(1 + lpProducerIndex, i)) == null) {
                return writeToQueue(atomicReferenceArray, t, lpProducerIndex, calcWrappedOffset);
            } else {
                resize(atomicReferenceArray, lpProducerIndex, calcWrappedOffset, t, (long) i);
                return true;
            }
        } else {
            throw new NullPointerException("Null is not a valid element");
        }
    }

    @Override // io.reactivex.internal.fuseable.SimpleQueue
    public boolean offer(T t, T t2) {
        AtomicReferenceArray<Object> atomicReferenceArray = this.producerBuffer;
        long lvProducerIndex = lvProducerIndex();
        int i = this.producerMask;
        long j = 2 + lvProducerIndex;
        if (lvElement(atomicReferenceArray, calcWrappedOffset(j, i)) == null) {
            int calcWrappedOffset = calcWrappedOffset(lvProducerIndex, i);
            soElement(atomicReferenceArray, calcWrappedOffset + 1, t2);
            soElement(atomicReferenceArray, calcWrappedOffset, t);
            soProducerIndex(j);
            return true;
        }
        AtomicReferenceArray<Object> atomicReferenceArray2 = new AtomicReferenceArray<>(atomicReferenceArray.length());
        this.producerBuffer = atomicReferenceArray2;
        int calcWrappedOffset2 = calcWrappedOffset(lvProducerIndex, i);
        soElement(atomicReferenceArray2, calcWrappedOffset2 + 1, t2);
        soElement(atomicReferenceArray2, calcWrappedOffset2, t);
        soNext(atomicReferenceArray, atomicReferenceArray2);
        soElement(atomicReferenceArray, calcWrappedOffset2, HAS_NEXT);
        soProducerIndex(j);
        return true;
    }

    public T peek() {
        AtomicReferenceArray<Object> atomicReferenceArray = this.consumerBuffer;
        long lpConsumerIndex = lpConsumerIndex();
        int i = this.consumerMask;
        T t = (T) lvElement(atomicReferenceArray, calcWrappedOffset(lpConsumerIndex, i));
        return t == HAS_NEXT ? newBufferPeek(lvNextBufferAndUnlink(atomicReferenceArray, i + 1), lpConsumerIndex, i) : t;
    }

    @Override // io.reactivex.internal.fuseable.SimpleQueue, io.reactivex.internal.fuseable.SimplePlainQueue
    @Nullable
    public T poll() {
        AtomicReferenceArray<Object> atomicReferenceArray = this.consumerBuffer;
        long lpConsumerIndex = lpConsumerIndex();
        int i = this.consumerMask;
        int calcWrappedOffset = calcWrappedOffset(lpConsumerIndex, i);
        T t = (T) lvElement(atomicReferenceArray, calcWrappedOffset);
        boolean z = t == HAS_NEXT;
        if (t != null && !z) {
            soElement(atomicReferenceArray, calcWrappedOffset, null);
            soConsumerIndex(lpConsumerIndex + 1);
            return t;
        } else if (z) {
            return newBufferPoll(lvNextBufferAndUnlink(atomicReferenceArray, i + 1), lpConsumerIndex, i);
        } else {
            return null;
        }
    }

    public int size() {
        long lvConsumerIndex = lvConsumerIndex();
        while (true) {
            long lvProducerIndex = lvProducerIndex();
            long lvConsumerIndex2 = lvConsumerIndex();
            if (lvConsumerIndex == lvConsumerIndex2) {
                return (int) (lvProducerIndex - lvConsumerIndex2);
            }
            lvConsumerIndex = lvConsumerIndex2;
        }
    }
}
