package com.bumptech.glide.request.transition;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import androidx.annotation.NonNull;

public class BitmapTransitionFactory extends BitmapContainerTransitionFactory<Bitmap> {
    public BitmapTransitionFactory(@NonNull TransitionFactory<Drawable> transitionFactory) {
        super(transitionFactory);
    }

    /* access modifiers changed from: protected */
    @NonNull
    public Bitmap getBitmap(@NonNull Bitmap bitmap) {
        return bitmap;
    }
}
