package com.android.camera.animation.type;

import android.view.View;
import androidx.core.view.ViewCompat;
import androidx.core.view.ViewPropertyAnimatorCompat;

public class ScaleXOnSubscribe extends BaseOnSubScribe {
    private float mOriginScaleX;
    private float mTargetScaleX;

    public ScaleXOnSubscribe(View view) {
        super(view);
    }

    /* access modifiers changed from: protected */
    @Override // com.android.camera.animation.type.BaseOnSubScribe
    public ViewPropertyAnimatorCompat getAnimation() {
        this.mAniView.setScaleX(this.mOriginScaleX);
        return ViewCompat.animate(this.mAniView).scaleX(this.mTargetScaleX);
    }

    public ScaleXOnSubscribe setScaleX(float f, float f2) {
        this.mOriginScaleX = f;
        this.mTargetScaleX = f2;
        return this;
    }
}
