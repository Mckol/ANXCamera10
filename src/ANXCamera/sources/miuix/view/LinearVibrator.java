package miuix.view;

import android.util.Log;
import android.view.View;
import androidx.annotation.Keep;
import androidx.collection.SparseArrayCompat;
import miui.util.HapticFeedbackUtil;
import miui.view.MiuiHapticFeedbackConstants;

@Keep
class LinearVibrator implements HapticFeedbackProvider {
    private static final String TAG = "LinearVibrator";
    private final SparseArrayCompat<Integer> mIds = new SparseArrayCompat<>();

    static {
        initialize();
    }

    private LinearVibrator() {
        buildIds();
    }

    private void buildIds() {
        this.mIds.append(HapticFeedbackConstants.MIUI_TAP_NORMAL, 268435456);
        this.mIds.append(HapticFeedbackConstants.MIUI_TAP_LIGHT, Integer.valueOf((int) MiuiHapticFeedbackConstants.FLAG_MIUI_HAPTIC_TAP_LIGHT));
        this.mIds.append(HapticFeedbackConstants.MIUI_FLICK, Integer.valueOf((int) MiuiHapticFeedbackConstants.FLAG_MIUI_HAPTIC_FLICK));
        this.mIds.append(HapticFeedbackConstants.MIUI_SWITCH, Integer.valueOf((int) MiuiHapticFeedbackConstants.FLAG_MIUI_HAPTIC_SWITCH));
        this.mIds.append(HapticFeedbackConstants.MIUI_MESH_HEAVY, Integer.valueOf((int) MiuiHapticFeedbackConstants.FLAG_MIUI_HAPTIC_MESH_HEAVY));
        this.mIds.append(HapticFeedbackConstants.MIUI_MESH_NORMAL, Integer.valueOf((int) MiuiHapticFeedbackConstants.FLAG_MIUI_HAPTIC_MESH_NORMAL));
        this.mIds.append(HapticFeedbackConstants.MIUI_MESH_LIGHT, Integer.valueOf((int) MiuiHapticFeedbackConstants.FLAG_MIUI_HAPTIC_MESH_LIGHT));
        this.mIds.append(HapticFeedbackConstants.MIUI_LONG_PRESS, Integer.valueOf((int) MiuiHapticFeedbackConstants.FLAG_MIUI_HAPTIC_LONG_PRESS));
        this.mIds.append(HapticFeedbackConstants.MIUI_POPUP_NORMAL, Integer.valueOf((int) MiuiHapticFeedbackConstants.FLAG_MIUI_HAPTIC_POPUP_NORMAL));
        this.mIds.append(HapticFeedbackConstants.MIUI_POPUP_LIGHT, Integer.valueOf((int) MiuiHapticFeedbackConstants.FLAG_MIUI_HAPTIC_POPUP_LIGHT));
        if (PlatformConstants.VERSION >= 2) {
            this.mIds.append(HapticFeedbackConstants.MIUI_PICK_UP, Integer.valueOf((int) MiuiHapticFeedbackConstants.FLAG_MIUI_HAPTIC_PICK_UP));
            this.mIds.append(HapticFeedbackConstants.MIUI_SCROLL_EDGE, Integer.valueOf((int) MiuiHapticFeedbackConstants.FLAG_MIUI_HAPTIC_SCROLL_EDGE));
            this.mIds.append(HapticFeedbackConstants.MIUI_TRIGGER_DRAWER, Integer.valueOf((int) MiuiHapticFeedbackConstants.FLAG_MIUI_HAPTIC_TRIGGER_DRAWER));
            if (PlatformConstants.VERSION >= 3) {
                this.mIds.append(HapticFeedbackConstants.MIUI_FLICK_LIGHT, 268435469);
                if (PlatformConstants.VERSION >= 4) {
                    this.mIds.append(HapticFeedbackConstants.MIUI_HOLD, 268435470);
                }
            }
        }
    }

    private static void initialize() {
        boolean z;
        if (PlatformConstants.VERSION < 1) {
            Log.w(TAG, "MiuiHapticFeedbackConstants not found or not compatible for LinearVibrator.");
            return;
        }
        try {
            z = HapticFeedbackUtil.isSupportLinearMotorVibrate();
        } catch (Throwable th) {
            Log.w(TAG, "MIUI Haptic Implementation is not available", th);
            z = false;
        }
        if (!z) {
            Log.w(TAG, "linear motor is not supported in this platform.");
            return;
        }
        HapticCompat.registerProvider(new LinearVibrator());
        Log.i(TAG, "setup LinearVibrator success.");
    }

    @Override // miuix.view.HapticFeedbackProvider
    public boolean performHapticFeedback(View view, int i) {
        int indexOfKey = this.mIds.indexOfKey(i);
        if (indexOfKey < 0) {
            Log.w(TAG, String.format("feedback(0x%08x-%s) is not found in current platform(v%d)", Integer.valueOf(i), HapticFeedbackConstants.nameOf(i), Integer.valueOf(PlatformConstants.VERSION)));
            return false;
        }
        int intValue = this.mIds.valueAt(indexOfKey).intValue();
        if (!HapticFeedbackUtil.isSupportLinearMotorVibrate(intValue)) {
            Log.w(TAG, String.format("unsupported feedback: 0x%08x. platform version: %d", Integer.valueOf(intValue), Integer.valueOf(PlatformConstants.VERSION)));
            return false;
        }
        Log.w(TAG, String.format("0x%08x,version: %d", Integer.valueOf(intValue), Integer.valueOf(PlatformConstants.VERSION)));
        return view.performHapticFeedback(intValue);
    }
}
