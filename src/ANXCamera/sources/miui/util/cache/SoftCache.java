package miui.util.cache;

import java.lang.ref.SoftReference;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class SoftCache<K, T> implements Cache<K, T> {
    private ConcurrentHashMap<K, SoftReference<T>> mCaches = new ConcurrentHashMap<>();

    private void trim() {
        Iterator<Map.Entry<K, SoftReference<T>>> it = this.mCaches.entrySet().iterator();
        while (it.hasNext()) {
            if (it.next().getValue().get() == null) {
                it.remove();
            }
        }
    }

    @Override // miui.util.cache.Cache
    public void clear() {
        for (Map.Entry<K, SoftReference<T>> entry : this.mCaches.entrySet()) {
            entry.getValue().clear();
        }
        this.mCaches.clear();
    }

    @Override // miui.util.cache.Cache
    public T get(K k) {
        trim();
        SoftReference<T> softReference = this.mCaches.get(k);
        if (softReference == null) {
            return null;
        }
        return softReference.get();
    }

    @Override // miui.util.cache.Cache
    public int getCount() {
        trim();
        return this.mCaches.size();
    }

    public void put(K k, T t) {
        put(k, t, 0);
    }

    @Override // miui.util.cache.Cache
    public void put(K k, T t, int i) {
        trim();
        this.mCaches.put(k, new SoftReference<>(t));
    }

    @Override // miui.util.cache.Cache
    public void setMaxSize(int i) {
    }
}
