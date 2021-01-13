package miui.util.concurrent;

import java.util.Iterator;
import miui.util.concurrent.Queue;

public class ConcurrentLinkedQueue<T> implements Queue<T> {
    private final java.util.concurrent.ConcurrentLinkedQueue<T> mQueue = new java.util.concurrent.ConcurrentLinkedQueue<>();

    @Override // miui.util.concurrent.Queue
    public int clear() {
        int size = this.mQueue.size();
        this.mQueue.clear();
        return size;
    }

    @Override // miui.util.concurrent.Queue
    public T get() {
        return this.mQueue.poll();
    }

    @Override // miui.util.concurrent.Queue
    public int getCapacity() {
        return -1;
    }

    @Override // miui.util.concurrent.Queue
    public boolean isEmpty() {
        return this.mQueue.isEmpty();
    }

    @Override // miui.util.concurrent.Queue
    public boolean put(T t) {
        return this.mQueue.offer(t);
    }

    @Override // miui.util.concurrent.Queue
    public int remove(Queue.Predicate<T> predicate) {
        Iterator<T> it = this.mQueue.iterator();
        int i = 0;
        while (it.hasNext()) {
            if (predicate.apply(it.next())) {
                it.remove();
                i++;
            }
        }
        return i;
    }

    @Override // miui.util.concurrent.Queue
    public boolean remove(T t) {
        return this.mQueue.remove(t);
    }
}
