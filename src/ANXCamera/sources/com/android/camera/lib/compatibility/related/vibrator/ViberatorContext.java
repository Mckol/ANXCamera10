package com.android.camera.lib.compatibility.related.vibrator;

import android.content.Context;
import android.util.Log;
import com.android.camera.lib.compatibility.util.CompatibilityUtils;

public class ViberatorContext implements IViberatorStrategy {
    private static final String TAG = "ViberatorContext";
    private static volatile ViberatorContext mInstance;
    private boolean mIsSnapClickViberatorEnable = true;
    IViberatorStrategy mViberator;

    private ViberatorContext(Context context) {
        if (CompatibilityUtils.isSupportLinearMotorVibrate()) {
            this.mViberator = new LinearMotorStrategy(context.getApplicationContext());
            Log.d(TAG, "ViberatorContext: init LinearMotorStrategy");
        }
    }

    public static ViberatorContext getInstance(Context context) {
        if (mInstance == null) {
            synchronized (ViberatorContext.class) {
                if (mInstance == null) {
                    mInstance = new ViberatorContext(context);
                }
            }
        }
        return mInstance;
    }

    private boolean isViberatorReady() {
        return this.mViberator != null;
    }

    @Override // com.android.camera.lib.compatibility.related.vibrator.IViberatorStrategy
    public void performBokehAdjust() {
        if (isViberatorReady()) {
            this.mViberator.performBokehAdjust();
        }
    }

    @Override // com.android.camera.lib.compatibility.related.vibrator.IViberatorStrategy
    public void performBurstCapture() {
        if (isViberatorReady()) {
            this.mViberator.performBurstCapture();
        }
    }

    @Override // com.android.camera.lib.compatibility.related.vibrator.IViberatorStrategy
    public void performEVChange() {
        if (isViberatorReady()) {
            this.mViberator.performEVChange();
        }
    }

    @Override // com.android.camera.lib.compatibility.related.vibrator.IViberatorStrategy
    public void performFocusValueLargeChangedInManual() {
        if (isViberatorReady()) {
            this.mViberator.performFocusValueLargeChangedInManual();
        }
    }

    @Override // com.android.camera.lib.compatibility.related.vibrator.IViberatorStrategy
    public void performFocusValueLightChangedInManual() {
        if (isViberatorReady()) {
            this.mViberator.performFocusValueLightChangedInManual();
        }
    }

    @Override // com.android.camera.lib.compatibility.related.vibrator.IViberatorStrategy
    public void performModeSwitch() {
        Log.d(TAG, "performModeSwitch: ");
        if (isViberatorReady()) {
            this.mViberator.performModeSwitch();
        }
    }

    @Override // com.android.camera.lib.compatibility.related.vibrator.IViberatorStrategy
    public void performSelectZoomLight() {
        if (isViberatorReady()) {
            this.mViberator.performSelectZoomLight();
        }
    }

    @Override // com.android.camera.lib.compatibility.related.vibrator.IViberatorStrategy
    public void performSelectZoomNormal() {
        if (isViberatorReady()) {
            this.mViberator.performSelectZoomNormal();
        }
    }

    @Override // com.android.camera.lib.compatibility.related.vibrator.IViberatorStrategy
    public void performSlideScaleNormal() {
        if (isViberatorReady()) {
            this.mViberator.performSlideScaleNormal();
        }
    }

    @Override // com.android.camera.lib.compatibility.related.vibrator.IViberatorStrategy
    public void performSnapClick() {
        if (this.mIsSnapClickViberatorEnable && isViberatorReady()) {
            this.mViberator.performSnapClick();
        }
    }

    public void setSnapClickVibratorEnable(boolean z) {
        this.mIsSnapClickViberatorEnable = z;
    }
}
