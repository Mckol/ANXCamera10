package com.xiaomi.stat.d;

import android.content.Context;
import android.os.SystemClock;
import com.xiaomi.stat.ak;
import com.xiaomi.stat.b.e;
import java.util.Calendar;

public class r {

    /* renamed from: a  reason: collision with root package name */
    public static final long f654a = 86400000;

    /* renamed from: b  reason: collision with root package name */
    private static final String f655b = "TimeUtil";

    /* renamed from: c  reason: collision with root package name */
    private static final long f656c = 300000;

    /* renamed from: d  reason: collision with root package name */
    private static long f657d;

    /* renamed from: e  reason: collision with root package name */
    private static long f658e;
    private static long f;

    public static void a() {
        boolean z;
        Context a2 = ak.a();
        long f2 = p.f(a2);
        long g = p.g(a2);
        long h = p.h(a2);
        if (f2 == 0 || g == 0 || h == 0 || Math.abs((System.currentTimeMillis() - g) - (SystemClock.elapsedRealtime() - h)) > 300000) {
            z = true;
        } else {
            f657d = f2;
            f = g;
            f658e = h;
            z = false;
        }
        if (z) {
            e.a().execute(new s());
        }
        k.b(f655b, "syncTimeIfNeeded sync: " + z);
    }

    public static void a(long j) {
        if (j > 0) {
            k.b("MI_STAT_TEST", "update server time:" + j);
            f657d = j;
            f658e = SystemClock.elapsedRealtime();
            f = System.currentTimeMillis();
            Context a2 = ak.a();
            p.a(a2, f657d);
            p.b(a2, f);
            p.c(a2, f658e);
        }
    }

    public static boolean a(long j, long j2) {
        return Math.abs(b() - j) >= j2;
    }

    public static long b() {
        long j = f657d;
        return (j == 0 || f658e == 0) ? System.currentTimeMillis() : (j + SystemClock.elapsedRealtime()) - f658e;
    }

    public static boolean b(long j) {
        k.b("MI_STAT_TEST", "inToday,current ts :" + b());
        Calendar instance = Calendar.getInstance();
        instance.setTimeInMillis(b());
        instance.set(11, 0);
        instance.set(12, 0);
        instance.set(13, 0);
        instance.set(14, 0);
        long timeInMillis = instance.getTimeInMillis();
        long j2 = timeInMillis + 86400000;
        k.b("MI_STAT_TEST", "[start]:" + timeInMillis + "\n[end]:" + j2 + "duration" + ((j2 - timeInMillis) - 86400000));
        StringBuilder sb = new StringBuilder();
        sb.append("is in today:");
        int i = (timeInMillis > j ? 1 : (timeInMillis == j ? 0 : -1));
        sb.append(i <= 0 && j < j2);
        k.b("MI_STAT_TEST", sb.toString());
        return i <= 0 && j < j2;
    }
}
