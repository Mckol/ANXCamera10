package com.bumptech.glide.load.engine;

import android.support.annotation.NonNull;

/* compiled from: Resource */
public interface A<Z> {
    @NonNull
    Z get();

    int getSize();

    void recycle();

    @NonNull
    Class<Z> z();
}
