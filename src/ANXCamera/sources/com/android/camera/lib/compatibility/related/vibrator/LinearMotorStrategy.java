package com.android.camera.lib.compatibility.related.vibrator;

import android.content.Context;
import android.util.Log;
import miui.util.HapticFeedbackUtil;

public class LinearMotorStrategy implements IViberatorStrategy {
    private static final String TAG = "LinearMotorStrategy";
    private final HapticFeedbackUtil mHapticFeedbackUtil;

    LinearMotorStrategy(Context context) {
        this.mHapticFeedbackUtil = new HapticFeedbackUtil(context, false);
    }

    @Override // com.android.camera.lib.compatibility.related.vibrator.IViberatorStrategy
    public void performBokehAdjust() {
        Log.d(TAG, "performBokehAdjust: ");
        this.mHapticFeedbackUtil.performHapticFeedback(HapticFeedbackUtil.EFFECT_KEY_MESH_LIGHT, false, 0);
    }

    @Override // com.android.camera.lib.compatibility.related.vibrator.IViberatorStrategy
    public void performBurstCapture() {
        Log.d(TAG, "performBurstCapture: ");
        this.mHapticFeedbackUtil.performHapticFeedback("flick_light", false, 0);
    }

    @Override // com.android.camera.lib.compatibility.related.vibrator.IViberatorStrategy
    public void performEVChange() {
        Log.d(TAG, "performEVChange: ignore...");
    }

    @Override // com.android.camera.lib.compatibility.related.vibrator.IViberatorStrategy
    public void performFocusValueLargeChangedInManual() {
        Log.d(TAG, "performFocusValueLargeChangedInManual: ");
        this.mHapticFeedbackUtil.performHapticFeedback(HapticFeedbackUtil.EFFECT_KEY_MESH_NORMAL, false, 0);
    }

    @Override // com.android.camera.lib.compatibility.related.vibrator.IViberatorStrategy
    public void performFocusValueLightChangedInManual() {
        Log.d(TAG, "performFocusValueLightChangedInManual: ");
        this.mHapticFeedbackUtil.performHapticFeedback(HapticFeedbackUtil.EFFECT_KEY_MESH_LIGHT, false, 0);
    }

    @Override // com.android.camera.lib.compatibility.related.vibrator.IViberatorStrategy
    public void performModeSwitch() {
        Log.d(TAG, "performModeSwitch: ");
        this.mHapticFeedbackUtil.performHapticFeedback(HapticFeedbackUtil.EFFECT_KEY_MESH_NORMAL, false);
    }

    @Override // com.android.camera.lib.compatibility.related.vibrator.IViberatorStrategy
    public void performSelectZoomLight() {
        Log.d(TAG, "performSelectZoomLight: ");
        this.mHapticFeedbackUtil.performHapticFeedback(HapticFeedbackUtil.EFFECT_KEY_MESH_LIGHT, false, 0);
    }

    @Override // com.android.camera.lib.compatibility.related.vibrator.IViberatorStrategy
    public void performSelectZoomNormal() {
        Log.d(TAG, "performSelectZoomNormal: ");
        this.mHapticFeedbackUtil.performHapticFeedback(HapticFeedbackUtil.EFFECT_KEY_MESH_NORMAL, false, 0);
    }

    @Override // com.android.camera.lib.compatibility.related.vibrator.IViberatorStrategy
    public void performSlideScaleNormal() {
        Log.d(TAG, "performSlideScaleNormal: ");
        this.mHapticFeedbackUtil.performHapticFeedback(HapticFeedbackUtil.EFFECT_KEY_MESH_NORMAL, false, 0);
    }

    @Override // com.android.camera.lib.compatibility.related.vibrator.IViberatorStrategy
    public void performSnapClick() {
        Log.d(TAG, "performSnapClick: ");
        this.mHapticFeedbackUtil.performHapticFeedback("flick_light", false, 2);
    }
}
