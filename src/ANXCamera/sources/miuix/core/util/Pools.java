package miuix.core.util;

import java.lang.ref.SoftReference;
import java.util.HashMap;
import miuix.core.util.concurrent.ConcurrentRingQueue;

public final class Pools {
    private static final HashMap<Class<?>, InstanceHolder<?>> mInstanceHolderMap = new HashMap<>();
    private static final HashMap<Class<?>, SoftReferenceInstanceHolder<?>> mSoftReferenceInstanceHolderMap = new HashMap<>();
    private static final Pool<StringBuilder> mStringBuilderPool = createSoftReferencePool(new Manager<StringBuilder>() {
        /* class miuix.core.util.Pools.AnonymousClass1 */

        @Override // miuix.core.util.Pools.Manager
        public StringBuilder createInstance() {
            return new StringBuilder();
        }

        public void onRelease(StringBuilder sb) {
            sb.setLength(0);
        }
    }, 4);

    static abstract class BasePool<T> implements Pool<T> {
        private final Object mFinalizeGuardian = new Object() {
            /* class miuix.core.util.Pools.BasePool.AnonymousClass1 */

            /* access modifiers changed from: protected */
            public void finalize() throws Throwable {
                try {
                    BasePool.this.close();
                } finally {
                    super.finalize();
                }
            }
        };
        private IInstanceHolder<T> mInstanceHolder;
        private final Manager<T> mManager;
        private final int mSize;

        public BasePool(Manager<T> manager, int i) {
            if (manager == null || i < 1) {
                this.mSize = this.mFinalizeGuardian.hashCode();
                throw new IllegalArgumentException("manager cannot be null and size cannot less then 1");
            }
            this.mManager = manager;
            this.mSize = i;
            T createInstance = this.mManager.createInstance();
            if (createInstance != null) {
                this.mInstanceHolder = createInstanceHolder(createInstance.getClass(), i);
                doRelease(createInstance);
                return;
            }
            throw new IllegalStateException("manager create instance cannot return null");
        }

        @Override // miuix.core.util.Pools.Pool
        public T acquire() {
            return doAcquire();
        }

        @Override // miuix.core.util.Pools.Pool
        public void close() {
            IInstanceHolder<T> iInstanceHolder = this.mInstanceHolder;
            if (iInstanceHolder != null) {
                destroyInstanceHolder(iInstanceHolder, this.mSize);
                this.mInstanceHolder = null;
            }
        }

        /* access modifiers changed from: package-private */
        public abstract IInstanceHolder<T> createInstanceHolder(Class<T> cls, int i);

        /* access modifiers changed from: package-private */
        public abstract void destroyInstanceHolder(IInstanceHolder<T> iInstanceHolder, int i);

        /* access modifiers changed from: protected */
        public final T doAcquire() {
            IInstanceHolder<T> iInstanceHolder = this.mInstanceHolder;
            if (iInstanceHolder != null) {
                T t = iInstanceHolder.get();
                if (t == null && (t = this.mManager.createInstance()) == null) {
                    throw new IllegalStateException("manager create instance cannot return null");
                }
                this.mManager.onAcquire(t);
                return t;
            }
            throw new IllegalStateException("Cannot acquire object after close()");
        }

        /* access modifiers changed from: protected */
        public final void doRelease(T t) {
            if (this.mInstanceHolder == null) {
                throw new IllegalStateException("Cannot release object after close()");
            } else if (t != null) {
                this.mManager.onRelease(t);
                if (!this.mInstanceHolder.put(t)) {
                    this.mManager.onDestroy(t);
                }
            }
        }

        @Override // miuix.core.util.Pools.Pool
        public int getSize() {
            if (this.mInstanceHolder == null) {
                return 0;
            }
            return this.mSize;
        }

        @Override // miuix.core.util.Pools.Pool
        public void release(T t) {
            doRelease(t);
        }
    }

    /* access modifiers changed from: private */
    public interface IInstanceHolder<T> {
        T get();

        Class<T> getElementClass();

        int getSize();

        boolean put(T t);

        void resize(int i);
    }

    /* access modifiers changed from: private */
    public static class InstanceHolder<T> implements IInstanceHolder<T> {
        private final Class<T> mClazz;
        private final ConcurrentRingQueue<T> mQueue;

        InstanceHolder(Class<T> cls, int i) {
            this.mClazz = cls;
            this.mQueue = new ConcurrentRingQueue<>(i, false, true);
        }

        @Override // miuix.core.util.Pools.IInstanceHolder
        public T get() {
            return this.mQueue.get();
        }

        @Override // miuix.core.util.Pools.IInstanceHolder
        public Class<T> getElementClass() {
            return this.mClazz;
        }

        @Override // miuix.core.util.Pools.IInstanceHolder
        public int getSize() {
            return this.mQueue.getCapacity();
        }

        @Override // miuix.core.util.Pools.IInstanceHolder
        public boolean put(T t) {
            return this.mQueue.put(t);
        }

        @Override // miuix.core.util.Pools.IInstanceHolder
        public synchronized void resize(int i) {
            int capacity = i + this.mQueue.getCapacity();
            if (capacity <= 0) {
                synchronized (Pools.mInstanceHolderMap) {
                    Pools.mInstanceHolderMap.remove(getElementClass());
                }
                return;
            }
            if (capacity > 0) {
                this.mQueue.increaseCapacity(capacity);
            } else {
                this.mQueue.decreaseCapacity(-capacity);
            }
        }
    }

    public static abstract class Manager<T> {
        public abstract T createInstance();

        public void onAcquire(T t) {
        }

        public void onDestroy(T t) {
        }

        public void onRelease(T t) {
        }
    }

    public interface Pool<T> {
        T acquire();

        void close();

        int getSize();

        void release(T t);
    }

    public static class SimplePool<T> extends BasePool<T> {
        SimplePool(Manager<T> manager, int i) {
            super(manager, i);
        }

        @Override // miuix.core.util.Pools.Pool, miuix.core.util.Pools.BasePool
        public /* bridge */ /* synthetic */ Object acquire() {
            return super.acquire();
        }

        @Override // miuix.core.util.Pools.Pool, miuix.core.util.Pools.BasePool
        public /* bridge */ /* synthetic */ void close() {
            super.close();
        }

        /* access modifiers changed from: package-private */
        @Override // miuix.core.util.Pools.BasePool
        public final IInstanceHolder<T> createInstanceHolder(Class<T> cls, int i) {
            return Pools.onPoolCreate(cls, i);
        }

        /* access modifiers changed from: package-private */
        @Override // miuix.core.util.Pools.BasePool
        public final void destroyInstanceHolder(IInstanceHolder<T> iInstanceHolder, int i) {
            Pools.onPoolClose((InstanceHolder) iInstanceHolder, i);
        }

        @Override // miuix.core.util.Pools.Pool, miuix.core.util.Pools.BasePool
        public /* bridge */ /* synthetic */ int getSize() {
            return super.getSize();
        }

        /* JADX DEBUG: Multi-variable search result rejected for r1v0, resolved type: java.lang.Object */
        /* JADX WARN: Multi-variable type inference failed */
        @Override // miuix.core.util.Pools.Pool, miuix.core.util.Pools.BasePool
        public /* bridge */ /* synthetic */ void release(Object obj) {
            super.release(obj);
        }
    }

    /* access modifiers changed from: private */
    public static class SoftReferenceInstanceHolder<T> implements IInstanceHolder<T> {
        private final Class<T> mClazz;
        private volatile SoftReference<T>[] mElements;
        private volatile int mIndex = 0;
        private volatile int mSize;

        SoftReferenceInstanceHolder(Class<T> cls, int i) {
            this.mClazz = cls;
            this.mSize = i;
            this.mElements = new SoftReference[i];
        }

        @Override // miuix.core.util.Pools.IInstanceHolder
        public synchronized T get() {
            int i = this.mIndex;
            SoftReference<T>[] softReferenceArr = this.mElements;
            while (i != 0) {
                i--;
                if (softReferenceArr[i] != null) {
                    T t = softReferenceArr[i].get();
                    softReferenceArr[i] = null;
                    if (t != null) {
                        this.mIndex = i;
                        return t;
                    }
                }
            }
            return null;
        }

        @Override // miuix.core.util.Pools.IInstanceHolder
        public Class<T> getElementClass() {
            return this.mClazz;
        }

        @Override // miuix.core.util.Pools.IInstanceHolder
        public int getSize() {
            return this.mSize;
        }

        @Override // miuix.core.util.Pools.IInstanceHolder
        public synchronized boolean put(T t) {
            int i = this.mIndex;
            SoftReference<T>[] softReferenceArr = this.mElements;
            if (i >= this.mSize) {
                for (int i2 = 0; i2 < i; i2++) {
                    if (softReferenceArr[i2] != null) {
                        if (softReferenceArr[i2].get() != null) {
                        }
                    }
                    softReferenceArr[i2] = new SoftReference<>(t);
                    return true;
                }
                return false;
            }
            softReferenceArr[i] = new SoftReference<>(t);
            this.mIndex = i + 1;
            return true;
        }

        @Override // miuix.core.util.Pools.IInstanceHolder
        public synchronized void resize(int i) {
            int i2 = i + this.mSize;
            if (i2 <= 0) {
                synchronized (Pools.mSoftReferenceInstanceHolderMap) {
                    Pools.mSoftReferenceInstanceHolderMap.remove(getElementClass());
                }
                return;
            }
            this.mSize = i2;
            SoftReference<T>[] softReferenceArr = this.mElements;
            int i3 = this.mIndex;
            if (i2 > softReferenceArr.length) {
                SoftReference<T>[] softReferenceArr2 = new SoftReference[i2];
                System.arraycopy(softReferenceArr, 0, softReferenceArr2, 0, i3);
                this.mElements = softReferenceArr2;
            }
        }
    }

    public static class SoftReferencePool<T> extends BasePool<T> {
        SoftReferencePool(Manager<T> manager, int i) {
            super(manager, i);
        }

        @Override // miuix.core.util.Pools.Pool, miuix.core.util.Pools.BasePool
        public /* bridge */ /* synthetic */ Object acquire() {
            return super.acquire();
        }

        @Override // miuix.core.util.Pools.Pool, miuix.core.util.Pools.BasePool
        public /* bridge */ /* synthetic */ void close() {
            super.close();
        }

        /* access modifiers changed from: package-private */
        @Override // miuix.core.util.Pools.BasePool
        public final IInstanceHolder<T> createInstanceHolder(Class<T> cls, int i) {
            return Pools.onSoftReferencePoolCreate(cls, i);
        }

        /* access modifiers changed from: package-private */
        @Override // miuix.core.util.Pools.BasePool
        public final void destroyInstanceHolder(IInstanceHolder<T> iInstanceHolder, int i) {
            Pools.onSoftReferencePoolClose((SoftReferenceInstanceHolder) iInstanceHolder, i);
        }

        @Override // miuix.core.util.Pools.Pool, miuix.core.util.Pools.BasePool
        public /* bridge */ /* synthetic */ int getSize() {
            return super.getSize();
        }

        /* JADX DEBUG: Multi-variable search result rejected for r1v0, resolved type: java.lang.Object */
        /* JADX WARN: Multi-variable type inference failed */
        @Override // miuix.core.util.Pools.Pool, miuix.core.util.Pools.BasePool
        public /* bridge */ /* synthetic */ void release(Object obj) {
            super.release(obj);
        }
    }

    public static <T> SimplePool<T> createSimplePool(Manager<T> manager, int i) {
        return new SimplePool<>(manager, i);
    }

    public static <T> SoftReferencePool<T> createSoftReferencePool(Manager<T> manager, int i) {
        return new SoftReferencePool<>(manager, i);
    }

    public static Pool<StringBuilder> getStringBuilderPool() {
        return mStringBuilderPool;
    }

    static <T> void onPoolClose(InstanceHolder<T> instanceHolder, int i) {
        synchronized (mInstanceHolderMap) {
            instanceHolder.resize(-i);
        }
    }

    static <T> InstanceHolder<T> onPoolCreate(Class<T> cls, int i) {
        InstanceHolder<T> instanceHolder;
        synchronized (mInstanceHolderMap) {
            instanceHolder = (InstanceHolder<T>) mInstanceHolderMap.get(cls);
            if (instanceHolder == null) {
                instanceHolder = new InstanceHolder<>(cls, i);
                mInstanceHolderMap.put(cls, instanceHolder);
            } else {
                instanceHolder.resize(i);
            }
        }
        return instanceHolder;
    }

    static <T> void onSoftReferencePoolClose(SoftReferenceInstanceHolder<T> softReferenceInstanceHolder, int i) {
        synchronized (mSoftReferenceInstanceHolderMap) {
            softReferenceInstanceHolder.resize(-i);
        }
    }

    static <T> SoftReferenceInstanceHolder<T> onSoftReferencePoolCreate(Class<T> cls, int i) {
        SoftReferenceInstanceHolder<T> softReferenceInstanceHolder;
        synchronized (mSoftReferenceInstanceHolderMap) {
            softReferenceInstanceHolder = (SoftReferenceInstanceHolder<T>) mSoftReferenceInstanceHolderMap.get(cls);
            if (softReferenceInstanceHolder == null) {
                softReferenceInstanceHolder = new SoftReferenceInstanceHolder<>(cls, i);
                mSoftReferenceInstanceHolderMap.put(cls, softReferenceInstanceHolder);
            } else {
                softReferenceInstanceHolder.resize(i);
            }
        }
        return softReferenceInstanceHolder;
    }
}
