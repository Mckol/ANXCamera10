package com.bumptech.glide.load.resource.bitmap;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import com.bumptech.glide.load.b.b.e;
import com.bumptech.glide.load.engine.A;
import com.bumptech.glide.load.engine.bitmap_recycle.d;
import com.bumptech.glide.load.g;
import com.bumptech.glide.load.h;

/* compiled from: ResourceBitmapDecoder */
public class v implements h<Uri, Bitmap> {
    private final d Bb;
    private final e qj;

    public v(e eVar, d dVar) {
        this.qj = eVar;
        this.Bb = dVar;
    }

    public boolean a(@NonNull Uri uri, @NonNull g gVar) {
        return "android.resource".equals(uri.getScheme());
    }

    @Nullable
    public A<Bitmap> b(@NonNull Uri uri, int i, int i2, @NonNull g gVar) {
        A b2 = this.qj.b(uri, i, i2, gVar);
        if (b2 == null) {
            return null;
        }
        return q.a(this.Bb, (Drawable) b2.get(), i, i2);
    }
}
