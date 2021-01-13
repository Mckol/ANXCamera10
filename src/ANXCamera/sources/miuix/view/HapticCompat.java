package miuix.view;

import android.util.Log;
import android.view.View;
import androidx.annotation.Keep;
import androidx.annotation.NonNull;
import java.util.ArrayList;
import java.util.List;

public class HapticCompat {
    static final String TAG = "HapticCompat";
    private static List<HapticFeedbackProvider> sProviders = new ArrayList();

    static {
        loadProviders("miuix.view.LinearVibrator", "miuix.view.ExtendedVibrator");
    }

    private static void loadProviders(String... strArr) {
        for (String str : strArr) {
            Log.i(TAG, "loading provider: " + str);
            try {
                Class.forName(str, true, HapticCompat.class.getClassLoader());
            } catch (ClassNotFoundException e2) {
                Log.w(TAG, String.format("load provider %s failed.", str), e2);
            }
        }
    }

    @Keep
    public static boolean performHapticFeedback(@NonNull View view, int i) {
        if (i < 268435456) {
            Log.i(TAG, String.format("perform haptic: 0x%08x", Integer.valueOf(i)));
            return view.performHapticFeedback(i);
        } else if (i > HapticFeedbackConstants.MIUI_HAPTIC_END) {
            Log.w(TAG, String.format("illegal feedback constant, should be in range [0x%08x..0x%08x]", 268435456, Integer.valueOf(HapticFeedbackConstants.MIUI_HAPTIC_END)));
            return false;
        } else {
            for (HapticFeedbackProvider hapticFeedbackProvider : sProviders) {
                if (hapticFeedbackProvider.performHapticFeedback(view, i)) {
                    return true;
                }
            }
            return false;
        }
    }

    static void registerProvider(HapticFeedbackProvider hapticFeedbackProvider) {
        sProviders.add(hapticFeedbackProvider);
    }
}
