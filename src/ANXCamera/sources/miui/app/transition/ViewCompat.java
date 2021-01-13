package miui.app.transition;

import android.animation.ValueAnimator;
import android.view.View;

class ViewCompat {
    private ViewCompat() {
    }

    public static ValueAnimator createTransitionAlphaAnim(final View view, float f, float f2) {
        ValueAnimator ofFloat = ValueAnimator.ofFloat(f, f2);
        ofFloat.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            /* class miui.app.transition.ViewCompat.AnonymousClass1 */

            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                view.setTransitionAlpha(((Float) valueAnimator.getAnimatedValue()).floatValue());
            }
        });
        return ofFloat;
    }
}
