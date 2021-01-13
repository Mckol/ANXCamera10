package com.android.camera.fragment.vv;

import android.view.View;
import androidx.core.view.ViewCompat;
import androidx.viewpager.widget.ViewPager;

public class VVPreviewTransformer implements ViewPager.PageTransformer {
    private static final float MAX_ALPHA = 1.0f;
    private static final float MAX_SCALE = 1.0f;
    private static final float MIN_ALPHA = 1.0f;
    private static final float MIN_SCALE = 1.0f;
    private static final float alphaSlope = 0.0f;
    private static final float scaleSlope = 0.0f;

    @Override // androidx.viewpager.widget.ViewPager.PageTransformer
    public void transformPage(View view, float f) {
        float f2 = -1.0f;
        if (f >= -1.0f) {
            f2 = f > 1.0f ? 1.0f : f;
        }
        ViewCompat.setAlpha(view, ((f2 < 0.0f ? f2 + 1.0f : 1.0f - f2) * 0.0f) + 1.0f);
    }
}
