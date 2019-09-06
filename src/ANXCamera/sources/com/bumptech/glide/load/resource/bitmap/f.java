package com.bumptech.glide.load.resource.bitmap;

import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import com.bumptech.glide.load.engine.A;
import com.bumptech.glide.load.engine.bitmap_recycle.d;
import com.bumptech.glide.load.engine.v;
import com.bumptech.glide.util.i;
import com.bumptech.glide.util.l;

/* compiled from: BitmapResource */
public class f implements A<Bitmap>, v {
    private final d Bb;
    private final Bitmap bitmap;

    public f(@NonNull Bitmap bitmap2, @NonNull d dVar) {
        i.a(bitmap2, "Bitmap must not be null");
        this.bitmap = bitmap2;
        i.a(dVar, "BitmapPool must not be null");
        this.Bb = dVar;
    }

    @Nullable
    public static f a(@Nullable Bitmap bitmap2, @NonNull d dVar) {
        if (bitmap2 == null) {
            return null;
        }
        return new f(bitmap2, dVar);
    }

    @NonNull
    public Bitmap get() {
        return this.bitmap;
    }

    public int getSize() {
        return l.j(this.bitmap);
    }

    public void initialize() {
        this.bitmap.prepareToDraw();
    }

    public void recycle() {
        this.Bb.a(this.bitmap);
    }

    @NonNull
    public Class<Bitmap> z() {
        return Bitmap.class;
    }
}
