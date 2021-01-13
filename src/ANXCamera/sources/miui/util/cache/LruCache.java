package miui.util.cache;

import android.app.ActivityManager;
import java.lang.ref.SoftReference;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.locks.ReentrantLock;
import miui.util.AppConstants;

public class LruCache<K, V> implements Cache<K, V> {
    private static final int BASE_MEMORY_SIZE = ((((ActivityManager) AppConstants.getCurrentApplication().getSystemService("activity")).getMemoryClass() * 1024) * 1024);
    private final LinkedHashMap<K, CacheItem<K, V>> mCacheItems;
    private final ReentrantLock mLock;
    private long mMaxSize;
    private long mTotalSize;

    /* access modifiers changed from: private */
    public static class CacheItem<K, T> {
        public T cacheItem;
        public K key;
        public int size;
        public SoftReference<T> softCacheItem;

        private CacheItem() {
        }
    }

    public LruCache(int i) {
        i = i < 0 ? BASE_MEMORY_SIZE / 8 : i;
        this.mLock = new ReentrantLock();
        this.mMaxSize = (long) i;
        this.mTotalSize = 0;
        this.mCacheItems = new LinkedHashMap<>(0, 0.75f, true);
    }

    private void trim() {
        SoftReference<T> softReference;
        Iterator<Map.Entry<K, CacheItem<K, V>>> it = this.mCacheItems.entrySet().iterator();
        while (it.hasNext()) {
            CacheItem<K, V> value = it.next().getValue();
            if (value.cacheItem == null && ((softReference = value.softCacheItem) == null || softReference.get() == null)) {
                it.remove();
            }
        }
        Iterator<Map.Entry<K, CacheItem<K, V>>> it2 = this.mCacheItems.entrySet().iterator();
        while (this.mTotalSize > this.mMaxSize && it2.hasNext()) {
            CacheItem<K, V> value2 = it2.next().getValue();
            if (value2 != null) {
                if (value2.softCacheItem == null) {
                    value2.softCacheItem = new SoftReference<>(value2.cacheItem);
                }
                this.mTotalSize -= (long) value2.size;
                value2.cacheItem = null;
            }
        }
    }

    @Override // miui.util.cache.Cache
    public void clear() {
        this.mLock.lock();
        try {
            for (Map.Entry<K, CacheItem<K, V>> entry : this.mCacheItems.entrySet()) {
                CacheItem<K, V> value = entry.getValue();
                if (value.softCacheItem != null) {
                    value.softCacheItem.clear();
                }
            }
            this.mCacheItems.clear();
        } finally {
            this.mLock.unlock();
        }
    }

    @Override // miui.util.cache.Cache
    public V get(K k) {
        T t;
        this.mLock.lock();
        try {
            trim();
            CacheItem<K, V> cacheItem = this.mCacheItems.get(k);
            if (cacheItem != null) {
                if (cacheItem.cacheItem != null) {
                    t = cacheItem.cacheItem;
                } else {
                    if (cacheItem.softCacheItem != null) {
                        cacheItem.cacheItem = cacheItem.softCacheItem.get();
                        if (cacheItem.cacheItem != null) {
                            this.mTotalSize += (long) cacheItem.size;
                            if (this.mTotalSize > this.mMaxSize) {
                                trim();
                            }
                            t = cacheItem.cacheItem;
                        }
                    }
                    this.mCacheItems.remove(k);
                }
                this.mLock.unlock();
                return (V) t;
            }
            return null;
        } finally {
            this.mLock.unlock();
        }
    }

    @Override // miui.util.cache.Cache
    public int getCount() {
        this.mLock.lock();
        try {
            trim();
            return this.mCacheItems.size();
        } finally {
            this.mLock.unlock();
        }
    }

    /* JADX DEBUG: Multi-variable search result rejected for r4v0, resolved type: V */
    /* JADX WARN: Multi-variable type inference failed */
    @Override // miui.util.cache.Cache
    public void put(K k, V v, int i) {
        if (k != null && v != 0) {
            CacheItem<K, V> cacheItem = new CacheItem<>();
            cacheItem.key = k;
            cacheItem.cacheItem = v;
            if (i < 0) {
                i = 0;
            }
            cacheItem.size = i;
            this.mLock.lock();
            try {
                this.mCacheItems.put(k, cacheItem);
                this.mTotalSize += (long) cacheItem.size;
                trim();
            } finally {
                this.mLock.unlock();
            }
        }
    }

    @Override // miui.util.cache.Cache
    public void setMaxSize(int i) {
        if (i < 0) {
            i = BASE_MEMORY_SIZE / 8;
        }
        this.mMaxSize = (long) i;
        try {
            this.mLock.lock();
            if (this.mTotalSize < this.mMaxSize) {
                trim();
            }
        } finally {
            this.mLock.unlock();
        }
    }
}
