package miui.util.concurrent;

import java.util.concurrent.atomic.AtomicInteger;
import miui.util.concurrent.Queue;

public class ConcurrentRingQueue<T> implements Queue<T> {
    private volatile int mAdditional;
    private final boolean mAllowExtendCapacity;
    private final boolean mAutoReleaseCapacity;
    private int mCapacity;
    private volatile Node<T> mReadCursor = new Node<>();
    private final AtomicInteger mReadLock = new AtomicInteger(0);
    private volatile Node<T> mWriteCursor = this.mReadCursor;
    private final AtomicInteger mWriteLock = new AtomicInteger(0);

    /* access modifiers changed from: private */
    public static class Node<T> {
        T element;
        Node<T> next;

        private Node() {
        }
    }

    public ConcurrentRingQueue(int i, boolean z, boolean z2) {
        this.mCapacity = i;
        this.mAllowExtendCapacity = z;
        this.mAutoReleaseCapacity = z2;
        Node<T> node = this.mReadCursor;
        for (int i2 = 0; i2 < i; i2++) {
            node.next = new Node<>();
            node = node.next;
        }
        node.next = this.mReadCursor;
    }

    @Override // miui.util.concurrent.Queue
    public int clear() {
        while (true) {
            if (this.mReadLock.get() == 0 && this.mReadLock.compareAndSet(0, -1)) {
                break;
            }
            Thread.yield();
        }
        Node<T> node = this.mReadCursor;
        int i = 0;
        while (node != this.mWriteCursor) {
            node.element = null;
            i++;
            node = node.next;
        }
        this.mReadCursor = node;
        this.mReadLock.set(0);
        return i;
    }

    public void decreaseCapacity(int i) {
        if (this.mAutoReleaseCapacity && i > 0) {
            while (true) {
                if (this.mWriteLock.get() != 0 || !this.mWriteLock.compareAndSet(0, -1)) {
                    Thread.yield();
                } else {
                    this.mCapacity -= i;
                    this.mAdditional = i;
                    this.mWriteLock.set(0);
                    return;
                }
            }
        }
    }

    @Override // miui.util.concurrent.Queue
    public T get() {
        while (true) {
            if (this.mReadLock.get() == 0 && this.mReadLock.compareAndSet(0, -1)) {
                break;
            }
            Thread.yield();
        }
        Node<T> node = this.mReadCursor;
        Node<T> node2 = this.mWriteCursor;
        Node<T> node3 = node;
        T t = null;
        while (t == null && node3 != node2) {
            t = node3.element;
            node3.element = null;
            node3 = node3.next;
            node2 = this.mWriteCursor;
        }
        if (t != null) {
            this.mReadCursor = node3;
        }
        this.mReadLock.set(0);
        return t;
    }

    @Override // miui.util.concurrent.Queue
    public int getCapacity() {
        int i = this.mAdditional;
        int i2 = this.mCapacity;
        return i > 0 ? i2 + i : i2;
    }

    public void increaseCapacity(int i) {
        if (!this.mAllowExtendCapacity && i > 0) {
            while (true) {
                if (this.mWriteLock.get() != 0 || !this.mWriteLock.compareAndSet(0, -1)) {
                    Thread.yield();
                } else {
                    this.mAdditional = -i;
                    this.mCapacity += i;
                    this.mWriteLock.set(0);
                    return;
                }
            }
        }
    }

    @Override // miui.util.concurrent.Queue
    public boolean isEmpty() {
        return this.mWriteCursor == this.mReadCursor;
    }

    @Override // miui.util.concurrent.Queue
    public boolean put(T t) {
        if (t == null) {
            return false;
        }
        while (true) {
            if (this.mWriteLock.get() == 0 && this.mWriteLock.compareAndSet(0, -1)) {
                break;
            }
            Thread.yield();
        }
        Node<T> node = this.mReadCursor;
        Node<T> node2 = this.mWriteCursor;
        int i = this.mAdditional;
        Node<T> node3 = node2.next;
        boolean z = true;
        if (node3 != node) {
            node2.element = t;
            Node<T> node4 = node3.next;
            if (node4 != node && this.mAutoReleaseCapacity && i > 0) {
                node2.next = node4;
                this.mAdditional = i - 1;
            }
            this.mWriteCursor = node2.next;
        } else if (this.mAllowExtendCapacity || i < 0) {
            node2.next = new Node<>();
            node2.next.next = node;
            node2.element = t;
            this.mAdditional = i + 1;
            this.mWriteCursor = node2.next;
        } else {
            z = false;
        }
        this.mWriteLock.set(0);
        return z;
    }

    @Override // miui.util.concurrent.Queue
    public int remove(Queue.Predicate<T> predicate) {
        if (predicate == null) {
            return 0;
        }
        while (true) {
            if (this.mReadLock.get() != 0 || !this.mReadLock.compareAndSet(0, -1)) {
                Thread.yield();
            } else {
                try {
                    break;
                } finally {
                    this.mReadLock.set(0);
                }
            }
        }
        int i = 0;
        for (Node<T> node = this.mReadCursor; node != this.mWriteCursor; node = node.next) {
            if (predicate.apply(node.element)) {
                node.element = null;
                i++;
            }
        }
        return i;
    }

    @Override // miui.util.concurrent.Queue
    public boolean remove(T t) {
        boolean z;
        if (t == null) {
            return false;
        }
        while (true) {
            if (this.mReadLock.get() == 0 && this.mReadLock.compareAndSet(0, -1)) {
                break;
            }
            Thread.yield();
        }
        Node<T> node = this.mReadCursor;
        while (true) {
            if (node == this.mWriteCursor) {
                z = false;
                break;
            } else if (t.equals(node.element)) {
                node.element = null;
                z = true;
                break;
            } else {
                node = node.next;
            }
        }
        this.mReadLock.set(0);
        return z;
    }
}
