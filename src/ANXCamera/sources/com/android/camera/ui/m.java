package com.android.camera.ui;

import android.animation.ValueAnimator;

/* compiled from: lambda */
public final /* synthetic */ class m implements ValueAnimator.AnimatorUpdateListener {
    private final /* synthetic */ VideoTagView tg;

    public /* synthetic */ m(VideoTagView videoTagView) {
        this.tg = videoTagView;
    }

    public final void onAnimationUpdate(ValueAnimator valueAnimator) {
        this.tg.f(valueAnimator);
    }
}
