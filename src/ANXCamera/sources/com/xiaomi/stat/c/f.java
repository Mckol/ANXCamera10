package com.xiaomi.stat.c;

import com.xiaomi.stat.ak;
import com.xiaomi.stat.b;
import com.xiaomi.stat.d.k;
import com.xiaomi.stat.d.l;
import com.xiaomi.stat.d.m;

public class f {

    /* renamed from: a  reason: collision with root package name */
    public static final int f554a = 1;

    /* renamed from: b  reason: collision with root package name */
    public static final int f555b = 2;

    /* renamed from: c  reason: collision with root package name */
    public static final int f556c = 3;

    /* renamed from: e  reason: collision with root package name */
    private static final String f557e = "UploadPolicy";

    /* renamed from: d  reason: collision with root package name */
    boolean f558d;
    private String f;

    public f(String str, boolean z) {
        this.f558d = z;
        this.f = str;
    }

    public f(boolean z) {
        this.f558d = z;
        this.f = ak.b();
    }

    private boolean a(int i) {
        return (i & -32) == 0;
    }

    private int b() {
        boolean b2 = m.b(ak.a());
        k.b(f557e, " getExperiencePlanPolicy: " + b2 + " isInternationalVersion= " + b.e() + " isAnonymous= " + this.f558d);
        if (b2) {
            return 3;
        }
        return (!b.e() || !this.f558d) ? 2 : 3;
    }

    private int c() {
        int e2 = b.e(this.f);
        k.b(f557e, " getCustomPrivacyPolicy: state=" + e2);
        return e2 == 1 ? 3 : 1;
    }

    private int d() {
        return b.d(this.f) ? c() : b();
    }

    private int e() {
        int a2 = l.a(ak.a());
        int l = a(b.l()) ? b.l() : b.i();
        StringBuilder sb = new StringBuilder();
        sb.append(" getHttpServicePolicy: currentNet= ");
        sb.append(a2);
        sb.append(" Config.getServerNetworkType= ");
        sb.append(b.l());
        sb.append(" Config.getUserNetworkType()= ");
        sb.append(b.i());
        sb.append(" (configNet & currentNet) == currentNet ");
        int i = l & a2;
        sb.append(i == a2);
        k.b(f557e, sb.toString());
        return i == a2 ? 3 : 1;
    }

    public int a() {
        return Math.min(d(), e());
    }
}
