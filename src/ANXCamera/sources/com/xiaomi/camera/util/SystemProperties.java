package com.xiaomi.camera.util;

import android.util.Log;

public class SystemProperties {
    private static final Class<?> SP = getSystemPropertiesClass();
    private static final String TAG = "SystemProperties";

    private SystemProperties() {
    }

    public static String get(String str) {
        try {
            return (String) SP.getMethod("get", String.class).invoke(null, str);
        } catch (Exception e2) {
            Log.e(TAG, "Exception while getting system property: ", e2);
            return null;
        }
    }

    public static String get(String str, String str2) {
        try {
            return (String) SP.getMethod("get", String.class, String.class).invoke(null, str, str2);
        } catch (Exception e2) {
            Log.e(TAG, "Exception while getting system property: ", e2);
            return str2;
        }
    }

    public static boolean getBoolean(String str, boolean z) {
        try {
            return ((Boolean) SP.getMethod("getBoolean", String.class, Boolean.TYPE).invoke(null, str, Boolean.valueOf(z))).booleanValue();
        } catch (Exception e2) {
            Log.e(TAG, "Exception while getting system property: ", e2);
            return z;
        }
    }

    public static int getInt(String str, int i) {
        try {
            return ((Integer) SP.getMethod("getInt", String.class, Integer.TYPE).invoke(null, str, Integer.valueOf(i))).intValue();
        } catch (Exception e2) {
            Log.e(TAG, "Exception while getting system property: ", e2);
            return i;
        }
    }

    public static long getLong(String str, long j) {
        try {
            return ((Long) SP.getMethod("getLong", String.class, Long.TYPE).invoke(null, str, Long.valueOf(j))).longValue();
        } catch (Exception e2) {
            Log.e(TAG, "Exception while getting system property: ", e2);
            return j;
        }
    }

    private static Class<?> getSystemPropertiesClass() {
        try {
            return Class.forName("android.os.SystemProperties");
        } catch (ClassNotFoundException unused) {
            Log.e(TAG, "'android.os.SystemProperties' not found");
            return null;
        }
    }
}
