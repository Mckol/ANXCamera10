package com.android.camera.ui;

import android.animation.ValueAnimator;

/* compiled from: lambda */
public final /* synthetic */ class n implements ValueAnimator.AnimatorUpdateListener {
    private final /* synthetic */ VideoTagView tg;

    public /* synthetic */ n(VideoTagView videoTagView) {
        this.tg = videoTagView;
    }

    public final void onAnimationUpdate(ValueAnimator valueAnimator) {
        this.tg.h(valueAnimator);
    }
}
