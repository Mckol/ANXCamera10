package com.android.camera.fragment.bottom.action;

import android.animation.ValueAnimator;
import com.android.camera.ui.AnimationView;

/* compiled from: lambda */
public final /* synthetic */ class a implements ValueAnimator.AnimatorUpdateListener {
    private final /* synthetic */ AnimationView tg;

    public /* synthetic */ a(AnimationView animationView) {
        this.tg = animationView;
    }

    public final void onAnimationUpdate(ValueAnimator valueAnimator) {
        FragmentBottomAction.a(this.tg, valueAnimator);
    }
}
