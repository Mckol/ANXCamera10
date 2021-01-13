package com.miui.internal.util.async;

import java.lang.ref.Reference;
import java.lang.ref.ReferenceQueue;
import java.lang.ref.WeakReference;
import java.util.Collection;
import java.util.concurrent.ConcurrentHashMap;

public class ConcurrentWeakHashMap<K, V> {
    private final ConcurrentHashMap<WeakKey<K>, V> mMap = new ConcurrentHashMap<>();
    private final ReferenceQueue<Object> mRefQueue = new ReferenceQueue<>();

    /* access modifiers changed from: private */
    public static class WeakKey<T> extends WeakReference<Object> {
        private int mHashCode;

        public WeakKey(Object obj) {
            super(obj);
            setHashCode(obj);
        }

        public WeakKey(Object obj, ReferenceQueue<? super Object> referenceQueue) {
            super(obj, referenceQueue);
            setHashCode(obj);
        }

        private void setHashCode(Object obj) {
            this.mHashCode = obj != null ? obj.hashCode() : 0;
        }

        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof WeakKey)) {
                return false;
            }
            T t = get();
            return t != null && t.equals(((WeakKey) obj).get());
        }

        public int hashCode() {
            return this.mHashCode;
        }
    }

    private void clean() {
        while (true) {
            Reference<? extends Object> poll = this.mRefQueue.poll();
            if (poll != null) {
                this.mMap.remove((WeakKey) poll);
            } else {
                return;
            }
        }
    }

    public boolean containsKey(K k) {
        if (k == null) {
            return false;
        }
        clean();
        return this.mMap.containsKey(new WeakKey(k));
    }

    public V get(K k) {
        clean();
        return this.mMap.get(new WeakKey(k));
    }

    public Collection<K> getKeys(Collection<K> collection) {
        clean();
        for (WeakKey<K> weakKey : this.mMap.keySet()) {
            K k = weakKey.get();
            if (k != null) {
                collection.add(k);
            }
        }
        return collection;
    }

    public V put(K k, V v) {
        if (k == null || v == null) {
            return null;
        }
        clean();
        return this.mMap.put(new WeakKey<>(k, this.mRefQueue), v);
    }

    public V remove(K k) {
        if (k == null) {
            return null;
        }
        clean();
        return this.mMap.remove(new WeakKey(k));
    }
}
