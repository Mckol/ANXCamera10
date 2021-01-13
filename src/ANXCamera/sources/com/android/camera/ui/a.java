package com.android.camera.ui;

import android.animation.ValueAnimator;

/* compiled from: lambda */
public final /* synthetic */ class a implements ValueAnimator.AnimatorUpdateListener {
    private final /* synthetic */ AdjustAnimationView tg;

    public /* synthetic */ a(AdjustAnimationView adjustAnimationView) {
        this.tg = adjustAnimationView;
    }

    public final void onAnimationUpdate(ValueAnimator valueAnimator) {
        this.tg.c(valueAnimator);
    }
}
