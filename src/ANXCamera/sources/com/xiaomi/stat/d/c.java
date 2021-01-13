package com.xiaomi.stat.d;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import com.xiaomi.stat.ak;

public class c {

    /* renamed from: a  reason: collision with root package name */
    private static boolean f585a;

    /* renamed from: b  reason: collision with root package name */
    private static int f586b;

    /* renamed from: c  reason: collision with root package name */
    private static String f587c;

    public static int a() {
        if (!f585a) {
            c();
        }
        return f586b;
    }

    public static String b() {
        if (!f585a) {
            c();
        }
        return f587c;
    }

    private static void c() {
        if (!f585a) {
            f585a = true;
            Context a2 = ak.a();
            try {
                PackageInfo packageInfo = a2.getPackageManager().getPackageInfo(a2.getPackageName(), 0);
                f586b = packageInfo.versionCode;
                f587c = packageInfo.versionName;
            } catch (PackageManager.NameNotFoundException unused) {
            }
        }
    }
}
