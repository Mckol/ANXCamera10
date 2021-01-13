package com.android.camera.module;

import android.animation.ValueAnimator;

/* renamed from: com.android.camera.module.d  reason: case insensitive filesystem */
/* compiled from: lambda */
public final /* synthetic */ class C0072d implements ValueAnimator.AnimatorUpdateListener {
    private final /* synthetic */ BaseModule tg;

    public /* synthetic */ C0072d(BaseModule baseModule) {
        this.tg = baseModule;
    }

    public final void onAnimationUpdate(ValueAnimator valueAnimator) {
        this.tg.d(valueAnimator);
    }
}
