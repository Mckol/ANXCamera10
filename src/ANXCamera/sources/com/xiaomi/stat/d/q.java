package com.xiaomi.stat.d;

import android.content.Context;
import android.content.pm.PackageManager;
import android.text.TextUtils;
import com.android.camera.statistic.MistatsConstants;
import java.lang.reflect.Field;

public class q {

    /* renamed from: a  reason: collision with root package name */
    public static final String f649a = "tv";

    /* renamed from: b  reason: collision with root package name */
    private static final String f650b = "SystemUtil";

    /* renamed from: c  reason: collision with root package name */
    private static final String f651c = "box";

    /* renamed from: d  reason: collision with root package name */
    private static final String f652d = "tvbox";

    /* renamed from: e  reason: collision with root package name */
    private static final String f653e = "projector";

    private static <T> T a(Class<?> cls, String str) {
        try {
            Field declaredField = cls.getDeclaredField(str);
            declaredField.setAccessible(true);
            return (T) declaredField.get(null);
        } catch (Exception e2) {
            k.d(f650b, "getStaticVariableValue exception", e2);
            return null;
        }
    }

    public static String a() {
        try {
            Class<?> cls = Class.forName("mitv.common.ConfigurationManager");
            int parseInt = Integer.parseInt(String.valueOf(cls.getMethod("getProductCategory", new Class[0]).invoke(cls.getMethod("getInstance", new Class[0]).invoke(cls, new Object[0]), new Object[0])));
            Class<?> cls2 = Class.forName("mitv.tv.TvContext");
            return parseInt == Integer.parseInt(String.valueOf(a(cls2, "PRODUCT_CATEGORY_MITV"))) ? f649a : parseInt == Integer.parseInt(String.valueOf(a(cls2, "PRODUCT_CATEGORY_MIBOX"))) ? f651c : parseInt == Integer.parseInt(String.valueOf(a(cls2, "PRODUCT_CATEGORY_MITVBOX"))) ? f652d : parseInt == Integer.parseInt(String.valueOf(a(cls2, "PRODUCT_CATEGORY_MIPROJECTOR"))) ? f653e : "";
        } catch (Exception e2) {
            k.d(f650b, "getMiTvProductCategory exception", e2);
            return "";
        }
    }

    public static String a(String str) {
        try {
            return (String) Class.forName("android.os.SystemProperties").getMethod("get", String.class).invoke(null, str);
        } catch (Exception e2) {
            k.d(f650b, "reflectGetSystemProperties exception", e2);
            return "";
        }
    }

    public static boolean a(Context context) {
        try {
            return (context.getPackageManager().getPackageInfo("com.xiaomi.mitv.services", 0).applicationInfo.flags & 1) != 0;
        } catch (PackageManager.NameNotFoundException unused) {
            k.d("Is not Mi Tv system!");
            return false;
        }
    }

    public static boolean b(Context context) {
        try {
            return a(context) && TextUtils.equals(a("ro.mitv.product.overseas"), MistatsConstants.BaseEvent.VALUE_TRUE);
        } catch (Exception e2) {
            k.d(f650b, "isMiTvIntlBuild", e2);
            return false;
        }
    }
}
