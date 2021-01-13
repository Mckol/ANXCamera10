package com.miui.internal.log.message;

import android.util.Log;
import java.lang.reflect.Array;
import java.lang.reflect.Constructor;
import java.util.HashMap;

public class MessageFactory {
    private static final int MAX_RECYCLED = 10;
    private static final String TAG = "MessageFactory";
    private static HashMap<Class<?>, MessageCache<?>> mCacheMap = new HashMap<>();

    /* access modifiers changed from: private */
    public static class MessageCache<T extends Message> {
        private T[] iCache;
        private Constructor<T> iConstructor;
        private int iPointer = 0;

        private MessageCache(Constructor<T> constructor, T[] tArr) {
            this.iConstructor = constructor;
            this.iCache = tArr;
        }

        private T create() {
            try {
                return this.iConstructor.newInstance(new Object[0]);
            } catch (Exception e2) {
                Log.e(MessageFactory.TAG, "Fail to construct new instance of class: " + this.iConstructor.getDeclaringClass().getName(), e2);
                return null;
            }
        }

        public static <T extends Message> MessageCache<T> createInstance(Class<T> cls, int i) {
            try {
                return new MessageCache<>(cls.getConstructor(new Class[0]), (Message[]) Array.newInstance((Class<?>) cls, i));
            } catch (NoSuchMethodException unused) {
                throw new IllegalArgumentException("Class " + cls.getName() + " must have a public empty constructor");
            }
        }

        public synchronized T obtain() {
            if (this.iPointer > 0) {
                this.iPointer--;
                T t = this.iCache[this.iPointer];
                t.prepareForReuse();
                return t;
            }
            return create();
        }

        public synchronized void recycle(T t) {
            if (this.iPointer < this.iCache.length) {
                this.iCache[this.iPointer] = t;
                this.iPointer++;
            }
        }
    }

    public static <T extends Message> T obtain(Class<T> cls) {
        MessageCache<?> messageCache = mCacheMap.get(cls);
        if (messageCache == null) {
            messageCache = MessageCache.createInstance(cls, 10);
            mCacheMap.put(cls, messageCache);
        }
        return (T) messageCache.obtain();
    }

    static <T extends Message> void recycle(T t) {
        MessageCache<?> messageCache = mCacheMap.get(t.getClass());
        if (messageCache != null) {
            messageCache.recycle(t);
        }
    }
}
