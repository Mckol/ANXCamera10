package miui.view.animation;

import android.view.animation.Interpolator;

public class QuadraticEaseInOutInterpolator implements Interpolator {
    public float getInterpolation(float f2) {
        float f3 = f2 * 2.0f;
        if (f3 < 1.0f) {
            return 0.5f * f3 * f3;
        }
        float f4 = f3 - 1.0f;
        return (((f4 - 2.0f) * f4) - 1.0f) * -0.5f;
    }
}
