package miui.animation.utils;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import java.lang.reflect.Constructor;
import java.util.Collection;
import java.util.LinkedList;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class ObjectPool {
    private static final long DELAY = 5000;
    private static final int MAX_POOL_SIZE = 5;
    private static final ConcurrentHashMap<Class<?>, Cache> sCacheMap = new ConcurrentHashMap<>();
    private static final Handler sMainHandler = new Handler(Looper.getMainLooper());

    /* access modifiers changed from: private */
    public static class Cache {
        final LinkedList<Object> pool;
        final Runnable shrinkTask;

        private Cache() {
            this.pool = new LinkedList<>();
            this.shrinkTask = new Runnable() {
                /* class miui.animation.utils.ObjectPool.Cache.AnonymousClass1 */

                public void run() {
                    Cache.this.shrink();
                }
            };
        }

        /* access modifiers changed from: package-private */
        public synchronized <T> T acquireObject(Class<T> cls, Object... objArr) {
            T t;
            t = (T) this.pool.poll();
            if (t == null) {
                t = (T) ObjectPool.createObject(cls, objArr);
            }
            return t;
        }

        /* access modifiers changed from: package-private */
        public synchronized void releaseObject(Object obj) {
            this.pool.add(obj);
            ObjectPool.sMainHandler.removeCallbacks(this.shrinkTask);
            if (this.pool.size() > 5) {
                ObjectPool.sMainHandler.postDelayed(this.shrinkTask, ObjectPool.DELAY);
            }
        }

        /* access modifiers changed from: package-private */
        public synchronized void shrink() {
            while (this.pool.size() > 5) {
                this.pool.poll();
            }
        }
    }

    public interface IPoolObject {
        void clear();
    }

    private ObjectPool() {
    }

    public static <T> T acquire(Class<T> cls, Object... objArr) {
        return (T) getObjectCache(cls, true).acquireObject(cls, objArr);
    }

    /* access modifiers changed from: private */
    public static Object createObject(Class<?> cls, Object... objArr) {
        try {
            Constructor<?>[] declaredConstructors = cls.getDeclaredConstructors();
            for (Constructor<?> constructor : declaredConstructors) {
                if (constructor.getParameterTypes().length == objArr.length) {
                    constructor.setAccessible(true);
                    return constructor.newInstance(objArr);
                }
            }
            return null;
        } catch (Exception e2) {
            Log.w(CommonUtils.TAG, "ObjectPool.createObject failed, clz = " + cls, e2);
            return null;
        }
    }

    private static Cache getObjectCache(Class<?> cls, boolean z) {
        Cache cache = sCacheMap.get(cls);
        if (cache != null || !z) {
            return cache;
        }
        Cache cache2 = new Cache();
        Cache putIfAbsent = sCacheMap.putIfAbsent(cls, cache2);
        return putIfAbsent != null ? putIfAbsent : cache2;
    }

    public static void release(Object obj) {
        if (obj != null) {
            Class<?> cls = obj.getClass();
            if (obj instanceof IPoolObject) {
                ((IPoolObject) obj).clear();
            } else if (obj instanceof Collection) {
                ((Collection) obj).clear();
            } else if (obj instanceof Map) {
                ((Map) obj).clear();
            }
            Cache objectCache = getObjectCache(cls, false);
            if (objectCache != null) {
                objectCache.releaseObject(obj);
            }
        }
    }
}
