package com.arcsoft.avatar2.util;

import android.util.Log;

public class CodecLog {

    /* renamed from: a  reason: collision with root package name */
    private static boolean f283a;

    public static void d(String str, String str2) {
        if (f283a) {
            Log.d(str, str2);
        }
    }

    public static void e(String str, String str2) {
        if (f283a) {
            Log.e(str, str2);
        }
    }

    public static void enableLog(boolean z) {
        f283a = z;
    }

    public static void i(String str, String str2) {
        if (f283a) {
            Log.i(str, str2);
        }
    }
}
