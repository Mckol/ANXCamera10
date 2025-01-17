package com.bumptech.glide.load.resource.drawable;

import android.graphics.drawable.Drawable;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.bumptech.glide.load.Options;
import com.bumptech.glide.load.ResourceDecoder;
import com.bumptech.glide.load.engine.Resource;

public class UnitDrawableDecoder implements ResourceDecoder<Drawable, Drawable> {
    @Nullable
    public Resource<Drawable> decode(@NonNull Drawable drawable, int i, int i2, @NonNull Options options) {
        return NonOwnedDrawableResource.newInstance(drawable);
    }

    public boolean handles(@NonNull Drawable drawable, @NonNull Options options) {
        return true;
    }
}
