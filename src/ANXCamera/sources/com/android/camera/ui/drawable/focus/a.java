package com.android.camera.ui.drawable.focus;

import android.animation.ValueAnimator;

/* compiled from: lambda */
public final /* synthetic */ class a implements ValueAnimator.AnimatorUpdateListener {
    private final /* synthetic */ CameraFocusAnimateDrawable tg;

    public /* synthetic */ a(CameraFocusAnimateDrawable cameraFocusAnimateDrawable) {
        this.tg = cameraFocusAnimateDrawable;
    }

    public final void onAnimationUpdate(ValueAnimator valueAnimator) {
        this.tg.a(valueAnimator);
    }
}
