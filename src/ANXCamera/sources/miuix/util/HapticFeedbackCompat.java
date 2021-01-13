package miuix.util;

import android.content.Context;
import android.net.Uri;
import android.util.Log;
import androidx.annotation.RequiresPermission;
import miui.util.HapticFeedbackUtil;
import miuix.view.PlatformConstants;

public class HapticFeedbackCompat {
    private static final String TAG = "HapticFeedbackCompat";
    private static boolean mAvailable;
    private HapticFeedbackUtil hapticFeedbackUtil;

    public HapticFeedbackCompat(Context context) {
        this(context, false);
    }

    public HapticFeedbackCompat(Context context, boolean z) {
        if (PlatformConstants.VERSION < 1) {
            Log.w(TAG, "MiuiHapticFeedbackConstants not found or not compatible for LinearVibrator.");
            return;
        }
        try {
            mAvailable = HapticFeedbackUtil.isSupportLinearMotorVibrate();
        } catch (Throwable th) {
            Log.w(TAG, "MIUI Haptic Implementation is not available", th);
            mAvailable = false;
        }
        if (!mAvailable) {
            Log.w(TAG, "linear motor is not supported in this platform.");
        } else {
            this.hapticFeedbackUtil = new HapticFeedbackUtil(context, z);
        }
    }

    @RequiresPermission("android.permission.VIBRATE")
    public boolean performExtHapticFeedback(int i) {
        HapticFeedbackUtil hapticFeedbackUtil2 = this.hapticFeedbackUtil;
        if (hapticFeedbackUtil2 != null) {
            return hapticFeedbackUtil2.performExtHapticFeedback(i);
        }
        return false;
    }

    @RequiresPermission("android.permission.VIBRATE")
    public boolean performExtHapticFeedback(Uri uri) {
        HapticFeedbackUtil hapticFeedbackUtil2 = this.hapticFeedbackUtil;
        if (hapticFeedbackUtil2 != null) {
            return hapticFeedbackUtil2.performExtHapticFeedback(uri);
        }
        return false;
    }

    @RequiresPermission("android.permission.VIBRATE")
    public boolean performHapticFeedback(int i, int i2) {
        HapticFeedbackUtil hapticFeedbackUtil2 = this.hapticFeedbackUtil;
        if (hapticFeedbackUtil2 != null) {
            return hapticFeedbackUtil2.performHapticFeedback(i, false, i2);
        }
        return false;
    }

    @RequiresPermission("android.permission.VIBRATE")
    public boolean performHapticFeedback(int i, int i2, boolean z) {
        HapticFeedbackUtil hapticFeedbackUtil2 = this.hapticFeedbackUtil;
        if (hapticFeedbackUtil2 != null) {
            return hapticFeedbackUtil2.performHapticFeedback(i, z, i2);
        }
        return false;
    }

    public void release() {
        HapticFeedbackUtil hapticFeedbackUtil2 = this.hapticFeedbackUtil;
        if (hapticFeedbackUtil2 != null) {
            hapticFeedbackUtil2.release();
        }
    }
}
