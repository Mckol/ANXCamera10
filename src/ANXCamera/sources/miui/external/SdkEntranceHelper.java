package miui.external;

import android.util.Log;

/* access modifiers changed from: package-private */
/* compiled from: SdkConstants */
public class SdkEntranceHelper implements SdkConstants {
    private static final String SDK_ENTRANCE_CLASS = "miui.core.SdkManager";
    private static final String SDK_ENTRANCE_FALLBACK_CLASS = "com.miui.internal.core.SdkManager";

    SdkEntranceHelper() {
    }

    public static Class<?> getSdkEntrance() throws ClassNotFoundException {
        try {
            return Class.forName(SDK_ENTRANCE_CLASS);
        } catch (ClassNotFoundException unused) {
            try {
                Class<?> cls = Class.forName(SDK_ENTRANCE_FALLBACK_CLASS);
                Log.w("miuisdk", "using legacy sdk");
                return cls;
            } catch (ClassNotFoundException e2) {
                Log.e("miuisdk", "no sdk found");
                throw e2;
            }
        }
    }
}
