package com.xiaomi.stat.c;

import android.content.Context;
import com.xiaomi.stat.ak;
import com.xiaomi.stat.b;
import com.xiaomi.stat.d.k;
import java.util.HashMap;
import java.util.Map;

public class a {

    /* renamed from: a  reason: collision with root package name */
    private static final String f536a = "ClientConfigMoniter";

    /* renamed from: b  reason: collision with root package name */
    private static final int f537b = 1;

    /* renamed from: c  reason: collision with root package name */
    private static final int f538c = 2;

    /* renamed from: d  reason: collision with root package name */
    private static final int f539d = 3;

    /* renamed from: e  reason: collision with root package name */
    private static final int f540e = 4;
    private static final int f = 5;
    private static Context g = ak.a();
    private static HashMap<Integer, Integer> h = new HashMap<>();

    static {
        h.put(1, 1);
        h.put(2, 2);
        h.put(3, 4);
        h.put(4, 8);
        h.put(5, 16);
    }

    public static int a(String str) {
        int i = 0;
        try {
            for (Map.Entry<Integer, Integer> entry : h.entrySet()) {
                int intValue = entry.getKey().intValue();
                int intValue2 = entry.getValue().intValue();
                if (a(intValue, str)) {
                    i |= intValue2;
                }
            }
        } catch (Exception e2) {
            k.d(f536a, "getClientConfiguration exception", e2);
        }
        return i;
    }

    private static boolean a(int i, String str) {
        if (i == 1) {
            return b.u();
        }
        if (i == 2) {
            return b.d(str);
        }
        if (i == 3) {
            return k.a();
        }
        if (i == 4 || i != 5) {
            return false;
        }
        try {
            return b.g();
        } catch (Exception e2) {
            k.d(f536a, "checkSetting exception", e2);
            return false;
        }
    }
}
