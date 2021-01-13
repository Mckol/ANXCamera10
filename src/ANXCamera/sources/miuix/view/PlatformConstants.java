package miuix.view;

import android.util.Log;
import androidx.annotation.Keep;
import miui.util.HapticFeedbackUtil;
import miui.view.MiuiHapticFeedbackConstants;

@Keep
public class PlatformConstants {
    public static final int VERSION;

    static {
        int i;
        try {
            try {
                i = (Class.forName("miui.util.HapticFeedbackUtil").getMethod("isSupportLinearMotorVibrate", new Class[]{Integer.TYPE}) == null || Class.forName("miui.view.MiuiHapticFeedbackConstants").getDeclaredField("FLAG_MIUI_HAPTIC_VERSION") == null) ? 0 : 4;
            } catch (ClassNotFoundException | NoSuchMethodException e2) {
                Log.w("HapticCompat", "MIUI Haptic Implementation not found.", e2);
                i = -1;
            }
        } catch (NoSuchFieldException e3) {
            Log.w("HapticCompat", "error when getting FLAG_MIUI_HAPTIC_VERSION.", e3);
            i = checkVersion();
        }
        VERSION = i;
        Log.i("HapticCompat", String.format("Platform version: %d.", Integer.valueOf(VERSION)));
    }

    static int checkVersion() {
        if (HapticFeedbackUtil.isSupportLinearMotorVibrate(268435470)) {
            return 4;
        }
        if (HapticFeedbackUtil.isSupportLinearMotorVibrate(268435469)) {
            return 3;
        }
        if (HapticFeedbackUtil.isSupportLinearMotorVibrate(MiuiHapticFeedbackConstants.FLAG_MIUI_HAPTIC_TRIGGER_DRAWER)) {
            return 2;
        }
        return HapticFeedbackUtil.isSupportLinearMotorVibrate(MiuiHapticFeedbackConstants.FLAG_MIUI_HAPTIC_POPUP_LIGHT) ? 1 : 0;
    }
}
