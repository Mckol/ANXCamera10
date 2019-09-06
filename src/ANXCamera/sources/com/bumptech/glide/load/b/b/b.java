package com.bumptech.glide.load.b.b;

import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.Drawable.ConstantState;
import android.support.annotation.NonNull;
import com.bumptech.glide.load.engine.A;
import com.bumptech.glide.load.engine.v;
import com.bumptech.glide.util.i;

/* compiled from: DrawableResource */
public abstract class b<T extends Drawable> implements A<T>, v {
    protected final T drawable;

    public b(T t) {
        i.checkNotNull(t);
        this.drawable = (Drawable) t;
    }

    @NonNull
    public final T get() {
        ConstantState constantState = this.drawable.getConstantState();
        return constantState == null ? this.drawable : constantState.newDrawable();
    }

    public void initialize() {
        T t = this.drawable;
        if (t instanceof BitmapDrawable) {
            ((BitmapDrawable) t).getBitmap().prepareToDraw();
        } else if (t instanceof com.bumptech.glide.load.resource.gif.b) {
            ((com.bumptech.glide.load.resource.gif.b) t).Z().prepareToDraw();
        }
    }
}
