package com.bumptech.glide.load.engine.bitmap_recycle;

import com.bumptech.glide.load.engine.bitmap_recycle.l;
import java.util.Queue;

/* compiled from: BaseKeyPool */
abstract class c<T extends l> {
    private static final int MAX_SIZE = 20;
    private final Queue<T> cg = com.bumptech.glide.util.l.createQueue(20);

    c() {
    }

    public void a(T t) {
        if (this.cg.size() < 20) {
            this.cg.offer(t);
        }
    }

    /* access modifiers changed from: package-private */
    public abstract T create();

    /* access modifiers changed from: package-private */
    public T get() {
        T t = (l) this.cg.poll();
        return t == null ? create() : t;
    }
}
