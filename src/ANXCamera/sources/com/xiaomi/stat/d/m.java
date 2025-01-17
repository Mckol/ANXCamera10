package com.xiaomi.stat.d;

import android.content.ContentResolver;
import android.content.Context;
import android.os.Build;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.Log;
import java.lang.reflect.Method;
import java.util.Locale;
import java.util.TimeZone;
import miui.reflect.Field;

public class m {

    /* renamed from: a  reason: collision with root package name */
    public static final int f630a = 28;

    /* renamed from: b  reason: collision with root package name */
    private static final String f631b = "OSUtil";

    /* renamed from: c  reason: collision with root package name */
    private static final String f632c = "";

    /* renamed from: d  reason: collision with root package name */
    private static Method f633d;

    /* renamed from: e  reason: collision with root package name */
    private static Class f634e;
    private static Method f;
    private static Boolean g;

    static {
        try {
            f633d = Class.forName("android.os.SystemProperties").getMethod("get", String.class);
        } catch (Exception unused) {
        }
        try {
            f634e = Class.forName("miui.os.Build");
        } catch (Exception unused2) {
        }
        try {
            f = Class.forName("android.provider.MiuiSettings$Secure").getDeclaredMethod("isUserExperienceProgramEnable", ContentResolver.class);
            f.setAccessible(true);
        } catch (Exception unused3) {
        }
    }

    public static String a(int i) {
        try {
            int i2 = i / 60000;
            char c2 = '+';
            if (i2 < 0) {
                c2 = '-';
                i2 = -i2;
            }
            StringBuilder sb = new StringBuilder(9);
            sb.append("GMT");
            sb.append(c2);
            a(sb, i2 / 60);
            sb.append(':');
            a(sb, i2 % 60);
            return sb.toString();
        } catch (Exception unused) {
            return null;
        }
    }

    public static String a(Context context) {
        String a2 = a("gsm.operator.numeric");
        StringBuilder sb = new StringBuilder();
        if (!TextUtils.isEmpty(a2)) {
            String[] split = a2.split(",");
            for (String str : split) {
                if (!TextUtils.isEmpty(str) && !"00000".equals(str)) {
                    if (sb.length() > 0) {
                        sb.append(",");
                    }
                    sb.append(str);
                }
            }
        }
        String sb2 = sb.toString();
        if (TextUtils.isEmpty(sb2)) {
            sb2 = ((TelephonyManager) context.getSystemService("phone")).getNetworkOperator();
        }
        return sb2 == null ? "" : sb2;
    }

    private static String a(String str) {
        try {
            if (f633d != null) {
                return String.valueOf(f633d.invoke(null, str));
            }
        } catch (Exception e2) {
            k.b(f631b, "getProp failed ex: " + e2.getMessage());
        }
        return null;
    }

    private static void a(StringBuilder sb, int i) {
        String num = Integer.toString(i);
        for (int i2 = 0; i2 < 2 - num.length(); i2++) {
            sb.append('0');
        }
        sb.append(num);
    }

    public static boolean a() {
        Boolean bool = g;
        if (bool != null) {
            return bool.booleanValue();
        }
        if (!TextUtils.isEmpty(a("ro.miui.ui.version.code"))) {
            g = true;
        } else {
            g = false;
        }
        return g.booleanValue();
    }

    public static String b() {
        return "Android";
    }

    public static boolean b(Context context) {
        Method method = f;
        if (method == null) {
            return true;
        }
        try {
            return ((Boolean) method.invoke(null, context.getContentResolver())).booleanValue();
        } catch (Exception e2) {
            Log.e(f631b, "isUserExperiencePlanEnabled failed: " + e2.toString());
            return true;
        }
    }

    public static String c() {
        return Build.VERSION.RELEASE;
    }

    public static String d() {
        return Build.VERSION.INCREMENTAL;
    }

    public static String e() {
        try {
            return TimeZone.getDefault().getDisplayName(false, 0);
        } catch (AssertionError e2) {
            e2.printStackTrace();
            return a(TimeZone.getDefault().getRawOffset());
        } catch (Exception e3) {
            e3.printStackTrace();
            return a(TimeZone.getDefault().getRawOffset());
        }
    }

    public static String f() {
        Locale locale = Locale.getDefault();
        return locale.getLanguage() + "_" + locale.getCountry();
    }

    public static String g() {
        String a2 = a("ro.miui.region");
        if (TextUtils.isEmpty(a2)) {
            a2 = Locale.getDefault().getCountry();
        }
        return a2 == null ? "" : a2;
    }

    public static String h() {
        Class cls = f634e;
        if (cls == null) {
            return "";
        }
        try {
            return ((Boolean) cls.getField("IS_ALPHA_BUILD").get(null)).booleanValue() ? "A" : ((Boolean) f634e.getField("IS_DEVELOPMENT_VERSION").get(null)).booleanValue() ? Field.DOUBLE_SIGNATURE_PRIMITIVE : ((Boolean) f634e.getField("IS_STABLE_VERSION").get(null)).booleanValue() ? "S" : "";
        } catch (Exception e2) {
            Log.e(f631b, "getRomBuildCode failed: " + e2.toString());
            return "";
        }
    }

    public static boolean i() {
        Class cls = f634e;
        if (cls == null) {
            return false;
        }
        try {
            return ((Boolean) cls.getField("IS_INTERNATIONAL_BUILD").get(null)).booleanValue();
        } catch (Exception unused) {
            return false;
        }
    }
}
