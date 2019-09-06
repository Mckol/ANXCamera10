package com.bumptech.glide.load.resource.bitmap;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.support.annotation.NonNull;
import com.bumptech.glide.load.EncodeStrategy;
import com.bumptech.glide.load.engine.A;
import com.bumptech.glide.load.engine.bitmap_recycle.d;
import com.bumptech.glide.load.g;
import com.bumptech.glide.load.i;
import java.io.File;

/* renamed from: com.bumptech.glide.load.resource.bitmap.b reason: case insensitive filesystem */
/* compiled from: BitmapDrawableEncoder */
public class C0105b implements i<BitmapDrawable> {
    private final d Bb;
    private final i<Bitmap> encoder;

    public C0105b(d dVar, i<Bitmap> iVar) {
        this.Bb = dVar;
        this.encoder = iVar;
    }

    @NonNull
    public EncodeStrategy a(@NonNull g gVar) {
        return this.encoder.a(gVar);
    }

    public boolean a(@NonNull A<BitmapDrawable> a2, @NonNull File file, @NonNull g gVar) {
        return this.encoder.a(new f(((BitmapDrawable) a2.get()).getBitmap(), this.Bb), file, gVar);
    }
}
