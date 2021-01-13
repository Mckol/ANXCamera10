package com.android.camera.ui;

import android.animation.ValueAnimator;

/* compiled from: lambda */
public final /* synthetic */ class b implements ValueAnimator.AnimatorUpdateListener {
    private final /* synthetic */ DragLayout tg;

    public /* synthetic */ b(DragLayout dragLayout) {
        this.tg = dragLayout;
    }

    public final void onAnimationUpdate(ValueAnimator valueAnimator) {
        this.tg.b(valueAnimator);
    }
}
