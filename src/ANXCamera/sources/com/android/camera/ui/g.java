package com.android.camera.ui;

import android.animation.ValueAnimator;

/* compiled from: lambda */
public final /* synthetic */ class g implements ValueAnimator.AnimatorUpdateListener {
    private final /* synthetic */ MutiStateButton tg;
    private final /* synthetic */ float ug;
    private final /* synthetic */ float vg;
    private final /* synthetic */ float wg;
    private final /* synthetic */ float xg;

    public /* synthetic */ g(MutiStateButton mutiStateButton, float f, float f2, float f3, float f4) {
        this.tg = mutiStateButton;
        this.ug = f;
        this.vg = f2;
        this.wg = f3;
        this.xg = f4;
    }

    public final void onAnimationUpdate(ValueAnimator valueAnimator) {
        this.tg.a(this.ug, this.vg, this.wg, this.xg, valueAnimator);
    }
}
