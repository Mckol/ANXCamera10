package com.airbnb.lottie;

import android.animation.ValueAnimator;

/* access modifiers changed from: package-private */
/* compiled from: LottieDrawable */
public class H implements ValueAnimator.AnimatorUpdateListener {
    final /* synthetic */ Q this$0;

    H(Q q) {
        this.this$0 = q;
    }

    public void onAnimationUpdate(ValueAnimator valueAnimator) {
        if (this.this$0.va != null) {
            this.this$0.va.setProgress(this.this$0.animator.P());
        }
    }
}
