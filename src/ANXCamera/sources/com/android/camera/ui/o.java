package com.android.camera.ui;

import android.animation.ValueAnimator;

/* compiled from: lambda */
public final /* synthetic */ class o implements ValueAnimator.AnimatorUpdateListener {
    private final /* synthetic */ VideoTagView tg;

    public /* synthetic */ o(VideoTagView videoTagView) {
        this.tg = videoTagView;
    }

    public final void onAnimationUpdate(ValueAnimator valueAnimator) {
        this.tg.g(valueAnimator);
    }
}
