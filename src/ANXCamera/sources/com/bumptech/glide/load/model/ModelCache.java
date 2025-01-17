package com.bumptech.glide.load.model;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.VisibleForTesting;
import com.bumptech.glide.util.LruCache;
import com.bumptech.glide.util.Util;
import java.util.Queue;

public class ModelCache<A, B> {
    private static final int DEFAULT_SIZE = 250;
    private final LruCache<ModelKey<A>, B> cache;

    /* access modifiers changed from: package-private */
    @VisibleForTesting
    public static final class ModelKey<A> {
        private static final Queue<ModelKey<?>> KEY_QUEUE = Util.createQueue(0);
        private int height;
        private A model;
        private int width;

        private ModelKey() {
        }

        static <A> ModelKey<A> get(A a2, int i, int i2) {
            ModelKey<A> modelKey;
            synchronized (KEY_QUEUE) {
                modelKey = (ModelKey<A>) KEY_QUEUE.poll();
            }
            if (modelKey == null) {
                modelKey = new ModelKey<>();
            }
            modelKey.init(a2, i, i2);
            return modelKey;
        }

        private void init(A a2, int i, int i2) {
            this.model = a2;
            this.width = i;
            this.height = i2;
        }

        public boolean equals(Object obj) {
            if (!(obj instanceof ModelKey)) {
                return false;
            }
            ModelKey modelKey = (ModelKey) obj;
            return this.width == modelKey.width && this.height == modelKey.height && this.model.equals(modelKey.model);
        }

        public int hashCode() {
            return (((this.height * 31) + this.width) * 31) + this.model.hashCode();
        }

        public void release() {
            synchronized (KEY_QUEUE) {
                KEY_QUEUE.offer(this);
            }
        }
    }

    public ModelCache() {
        this(250);
    }

    public ModelCache(long j) {
        this.cache = new LruCache<ModelKey<A>, B>(j) {
            /* class com.bumptech.glide.load.model.ModelCache.AnonymousClass1 */

            /* access modifiers changed from: protected */
            public void onItemEvicted(@NonNull ModelKey<A> modelKey, @Nullable B b2) {
                modelKey.release();
            }

            /* access modifiers changed from: protected */
            @Override // com.bumptech.glide.util.LruCache
            public /* bridge */ /* synthetic */ void onItemEvicted(@NonNull Object obj, @Nullable Object obj2) {
                onItemEvicted((ModelKey) ((ModelKey) obj), obj2);
            }
        };
    }

    public void clear() {
        this.cache.clearMemory();
    }

    @Nullable
    public B get(A a2, int i, int i2) {
        ModelKey<A> modelKey = ModelKey.get(a2, i, i2);
        B b2 = this.cache.get(modelKey);
        modelKey.release();
        return b2;
    }

    public void put(A a2, int i, int i2, B b2) {
        this.cache.put(ModelKey.get(a2, i, i2), b2);
    }
}
