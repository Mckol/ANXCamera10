package miui.util;

import com.miui.internal.JniNames;

public class Patcher {
    static {
        System.loadLibrary(JniNames.LIB_PATCHER);
    }

    public native int applyPatch(String str, String str2, String str3);
}
