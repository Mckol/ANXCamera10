package com.android.camera.animation.type;

import android.view.View;
import androidx.core.view.ViewCompat;
import androidx.core.view.ViewPropertyAnimatorCompat;

public class AlphaInOnSubscribe extends BaseOnSubScribe {
    private float targetAlpha = 1.0f;

    public AlphaInOnSubscribe(View view) {
        super(view);
    }

    public static void directSetResult(View view) {
        ViewCompat.setAlpha(view, 1.0f);
        BaseOnSubScribe.setAnimateViewVisible(view, 0);
    }

    public static boolean matchState(View view) {
        return ViewCompat.getAlpha(view) == 1.0f && view.getVisibility() == 0;
    }

    /* access modifiers changed from: protected */
    @Override // com.android.camera.animation.type.BaseOnSubScribe
    public ViewPropertyAnimatorCompat getAnimation() {
        BaseOnSubScribe.setAnimateViewVisible(this.mAniView, 0);
        ViewCompat.setAlpha(this.mAniView, 0.0f);
        return ViewCompat.animate(this.mAniView).alpha(this.targetAlpha);
    }

    public void setTargetAlpha(float f) {
        this.targetAlpha = f;
    }
}
