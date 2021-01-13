package com.miui.internal.util;

import android.content.Context;
import android.os.Build;
import android.provider.Settings;
import android.text.TextUtils;
import miui.os.SystemProperties;

public class DeviceHelper {
    public static final boolean FEATURE_WHOLE_ANIM = SystemProperties.getBoolean("ro.sys.ft_whole_anim", true);
    public static final boolean HAS_CUST_PARTITION = SystemProperties.getBoolean("ro.miui.has_cust_partition", false);
    public static final boolean IS_ALPHA_BUILD = SystemProperties.get("ro.product.mod_device", "").endsWith("_alpha");
    public static final boolean IS_DEBUGGABLE = (SystemProperties.getInt("ro.debuggable", 0) == 1);
    public static final boolean IS_DEVELOPMENT_VERSION = (!TextUtils.isEmpty(Build.VERSION.INCREMENTAL) && Build.VERSION.INCREMENTAL.matches(REGULAR_EXPRESSION_FOR_DEVELOPMENT));
    public static final boolean IS_INTERNATIONAL_BUILD = SystemProperties.get("ro.product.mod_device", "").endsWith("_global");
    public static final boolean IS_MIFOUR = ("cancro".equals(Build.DEVICE) && Build.MODEL.startsWith("MI 4"));
    public static final boolean IS_OLED;
    public static final boolean IS_STABLE_VERSION = ("user".equals(Build.TYPE) && !IS_DEVELOPMENT_VERSION);
    public static final boolean IS_TABLET = isTablet();
    private static final String REGULAR_EXPRESSION_FOR_DEVELOPMENT = "\\d+.\\d+.\\d+(-internal)?";

    static {
        boolean z = true;
        if (!"oled".equals(SystemProperties.get("ro.vendor.display.type")) && !"oled".equals(SystemProperties.get("ro.display.type"))) {
            z = false;
        }
        IS_OLED = z;
    }

    public static String getDebugEnable() {
        return SystemProperties.get("persist.sys.miui.sdk.dbg", "");
    }

    public static String getRegion() {
        return SystemProperties.get("ro.miui.region", "CN");
    }

    public static boolean isDeviceProvisioned(Context context) {
        return Settings.Global.getInt(context.getContentResolver(), "device_provisioned", 0) != 0;
    }

    public static boolean isHideGestureLine(Context context) {
        return Settings.Global.getInt(context.getContentResolver(), "hide_gesture_line", 0) != 0;
    }

    private static boolean isTablet() {
        return SystemProperties.get("ro.build.characteristics", "default").contains("tablet");
    }
}
