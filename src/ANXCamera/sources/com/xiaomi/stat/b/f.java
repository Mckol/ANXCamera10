package com.xiaomi.stat.b;

import android.content.Context;
import com.xiaomi.stat.d.k;
import java.lang.reflect.Method;

public class f {

    /* renamed from: a  reason: collision with root package name */
    private static final String f510a = "IdentifierManager";

    /* renamed from: b  reason: collision with root package name */
    private static Object f511b;

    /* renamed from: c  reason: collision with root package name */
    private static Class<?> f512c;

    /* renamed from: d  reason: collision with root package name */
    private static Method f513d;

    /* renamed from: e  reason: collision with root package name */
    private static Method f514e;
    private static Method f;
    private static Method g;

    static {
        try {
            f512c = Class.forName("com.android.id.impl.IdProviderImpl");
            f511b = f512c.newInstance();
            f513d = f512c.getMethod("getUDID", Context.class);
            f514e = f512c.getMethod("getOAID", Context.class);
            f = f512c.getMethod("getVAID", Context.class);
            g = f512c.getMethod("getAAID", Context.class);
        } catch (Exception e2) {
            k.d(f510a, "reflect exception!", e2);
        }
    }

    public static String a(Context context) {
        return a(context, f513d);
    }

    private static String a(Context context, Method method) {
        Object obj = f511b;
        if (obj == null || method == null) {
            return "";
        }
        try {
            Object invoke = method.invoke(obj, context);
            return invoke != null ? (String) invoke : "";
        } catch (Exception e2) {
            k.d(f510a, "invoke exception!", e2);
            return "";
        }
    }

    public static boolean a() {
        return (f512c == null || f511b == null) ? false : true;
    }

    public static String b(Context context) {
        return a(context, f514e);
    }

    public static String c(Context context) {
        return a(context, f);
    }

    public static String d(Context context) {
        return a(context, g);
    }
}
