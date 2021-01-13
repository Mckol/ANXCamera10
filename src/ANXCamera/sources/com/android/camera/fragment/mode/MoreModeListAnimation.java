package com.android.camera.fragment.mode;

import android.view.View;
import android.view.animation.Interpolator;
import androidx.core.view.ViewCompat;
import androidx.core.view.ViewPropertyAnimatorCompat;
import com.android.camera.log.Log;
import miuix.view.animation.CubicEaseOutInterpolator;

public class MoreModeListAnimation {
    public static final int DIRECTION_B2T = 3;
    public static final int DIRECTION_L2R = 0;
    public static final int DIRECTION_R2L = 1;
    public static final int DIRECTION_T2B = 2;
    public static final int DIRECTION_UNKNOWN = -1;
    private static final int DISTANCE = 300;
    private static final String TAG = "MoreModeAnimation";
    private static MoreModeListAnimation sAnimation;
    private Interpolator mInterpolator = new CubicEaseOutInterpolator();

    private int getCol(int i) {
        return 0;
    }

    public static MoreModeListAnimation getInstance() {
        if (sAnimation == null) {
            sAnimation = new MoreModeListAnimation();
        }
        return sAnimation;
    }

    private int getRow(int i) {
        return 0;
    }

    public void startAlphaAnimation(View view, int i) {
        view.setAlpha(0.0f);
        ViewCompat.animate(view).setInterpolator(this.mInterpolator).alpha(1.0f).setDuration(200).start();
    }

    public void startTranAnimation(View view, int i, int i2) {
        boolean z = false;
        boolean z2 = true;
        if (i2 == 0) {
            getCol(i);
            z2 = false;
            z = true;
        } else if (i2 == 1) {
            getCol(i);
            z = true;
        } else if (i2 == 2) {
            getRow(i);
            z2 = false;
        } else if (i2 == 3) {
            getRow(i);
        } else {
            throw new IllegalArgumentException("unknown direction.");
        }
        Log.d(TAG, "position = " + i);
        ViewPropertyAnimatorCompat duration = ViewCompat.animate(view).setInterpolator(this.mInterpolator).setDuration(200);
        float f = 300.0f;
        if (z) {
            if (!z2) {
                f = -300.0f;
            }
            view.setTranslationX(f);
            duration.translationX(0.0f);
        } else {
            if (!z2) {
                f = -300.0f;
            }
            view.setTranslationY(f);
            duration.translationY(0.0f);
        }
        duration.start();
    }
}
