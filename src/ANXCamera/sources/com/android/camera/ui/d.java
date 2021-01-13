package com.android.camera.ui;

import android.animation.ValueAnimator;
import android.view.View;

/* compiled from: lambda */
public final /* synthetic */ class d implements ValueAnimator.AnimatorUpdateListener {
    private final /* synthetic */ View tg;

    public /* synthetic */ d(View view) {
        this.tg = view;
    }

    public final void onAnimationUpdate(ValueAnimator valueAnimator) {
        this.tg.setAlpha(((Float) valueAnimator.getAnimatedValue()).floatValue());
    }
}
