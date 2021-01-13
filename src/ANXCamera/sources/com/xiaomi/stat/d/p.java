package com.xiaomi.stat.d;

import android.content.Context;
import android.content.SharedPreferences;

public class p {

    /* renamed from: a  reason: collision with root package name */
    public static final String f644a = "netSpeed";

    /* renamed from: b  reason: collision with root package name */
    public static final String f645b = "net_speed_time_stamp";

    /* renamed from: c  reason: collision with root package name */
    private static final String f646c = "mi_stat_pref";

    /* renamed from: d  reason: collision with root package name */
    private static SharedPreferences f647d = null;

    /* renamed from: e  reason: collision with root package name */
    private static SharedPreferences.Editor f648e = null;
    private static final String f = "imei1";
    private static final String g = "imei2";
    private static final String h = "meid";
    private static final String i = "mac";
    private static final String j = "serial";
    private static final String k = "s_t";
    private static final String l = "l_t";
    private static final String m = "e_t";
    private static final String n = "od_checked";
    private static final String o = "od_v";
    private static final String p = "resued_old_instanced_id";
    private static final String q = "netSpeedTotalRxBytes";

    private static long a(Context context, String str, long j2) {
        o(context);
        return f647d.getLong(str, j2);
    }

    public static String a(Context context) {
        return a(context, f, "");
    }

    private static String a(Context context, String str, String str2) {
        o(context);
        return f647d.getString(str, str2);
    }

    public static void a(Context context, float f2) {
        a(context, f644a, f2);
    }

    public static void a(Context context, long j2) {
        b(context, k, j2);
    }

    public static void a(Context context, String str) {
        b(context, f, str);
    }

    private static void a(Context context, String str, float f2) {
        o(context);
        f648e.putFloat(str, f2).apply();
    }

    public static void a(Context context, boolean z) {
        b(context, n, z);
    }

    private static boolean a(Context context, String str, boolean z) {
        o(context);
        return f647d.getBoolean(str, z);
    }

    private static float b(Context context, String str, float f2) {
        o(context);
        return f647d.getFloat(str, f2);
    }

    public static String b(Context context) {
        return a(context, g, "");
    }

    public static void b(Context context, long j2) {
        b(context, l, j2);
    }

    public static void b(Context context, String str) {
        b(context, g, str);
    }

    private static void b(Context context, String str, long j2) {
        o(context);
        f648e.putLong(str, j2).apply();
    }

    private static void b(Context context, String str, String str2) {
        o(context);
        f648e.putString(str, str2).apply();
    }

    private static void b(Context context, String str, boolean z) {
        o(context);
        f648e.putBoolean(str, z).apply();
    }

    public static void b(Context context, boolean z) {
        b(context, p, z);
    }

    public static String c(Context context) {
        return a(context, h, "");
    }

    public static void c(Context context, long j2) {
        b(context, m, j2);
    }

    public static void c(Context context, String str) {
        b(context, h, str);
    }

    public static String d(Context context) {
        return a(context, i, "");
    }

    public static void d(Context context, long j2) {
        b(context, q, j2);
    }

    public static void d(Context context, String str) {
        b(context, i, str);
    }

    public static String e(Context context) {
        return a(context, j, "");
    }

    public static void e(Context context, long j2) {
        b(context, f645b, j2);
    }

    public static void e(Context context, String str) {
        b(context, j, str);
    }

    public static long f(Context context) {
        return a(context, k, 0L);
    }

    public static void f(Context context, String str) {
        b(context, o, str);
    }

    public static long g(Context context) {
        return a(context, l, 0L);
    }

    public static long h(Context context) {
        return a(context, m, 0L);
    }

    public static boolean i(Context context) {
        return a(context, n, false);
    }

    public static String j(Context context) {
        return a(context, o, (String) null);
    }

    public static boolean k(Context context) {
        return a(context, p, false);
    }

    public static float l(Context context) {
        return b(context, f644a, 0.0f);
    }

    public static long m(Context context) {
        return a(context, q, 0L);
    }

    public static long n(Context context) {
        return a(context, f645b, 0L);
    }

    private static void o(Context context) {
        if (f648e == null) {
            synchronized (p.class) {
                if (f648e == null) {
                    f647d = context.getSharedPreferences(f646c, 0);
                    f648e = f647d.edit();
                }
            }
        }
    }
}
