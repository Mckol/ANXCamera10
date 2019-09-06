package com.bumptech.glide.load.resource.bitmap;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import com.bumptech.glide.c;
import com.bumptech.glide.load.engine.A;
import com.bumptech.glide.load.engine.bitmap_recycle.d;
import com.bumptech.glide.load.engine.v;
import com.bumptech.glide.util.i;

/* compiled from: LazyBitmapDrawableResource */
public final class u implements A<BitmapDrawable>, v {
    private final A<Bitmap> pj;
    private final Resources resources;

    private u(@NonNull Resources resources2, @NonNull A<Bitmap> a2) {
        i.checkNotNull(resources2);
        this.resources = resources2;
        i.checkNotNull(a2);
        this.pj = a2;
    }

    @Nullable
    public static A<BitmapDrawable> a(@NonNull Resources resources2, @Nullable A<Bitmap> a2) {
        if (a2 == null) {
            return null;
        }
        return new u(resources2, a2);
    }

    @Deprecated
    public static u a(Context context, Bitmap bitmap) {
        return (u) a(context.getResources(), (A<Bitmap>) f.a(bitmap, c.get(context).Cf()));
    }

    @Deprecated
    public static u a(Resources resources2, d dVar, Bitmap bitmap) {
        return (u) a(resources2, (A<Bitmap>) f.a(bitmap, dVar));
    }

    @NonNull
    public BitmapDrawable get() {
        return new BitmapDrawable(this.resources, (Bitmap) this.pj.get());
    }

    public int getSize() {
        return this.pj.getSize();
    }

    public void initialize() {
        A<Bitmap> a2 = this.pj;
        if (a2 instanceof v) {
            ((v) a2).initialize();
        }
    }

    public void recycle() {
        this.pj.recycle();
    }

    @NonNull
    public Class<BitmapDrawable> z() {
        return BitmapDrawable.class;
    }
}
