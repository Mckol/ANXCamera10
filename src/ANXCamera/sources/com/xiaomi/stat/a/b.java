package com.xiaomi.stat.a;

import android.text.TextUtils;
import com.xiaomi.stat.a.l;

public class b {

    /* renamed from: a  reason: collision with root package name */
    public static final int f418a = 0;

    /* renamed from: b  reason: collision with root package name */
    public static final int f419b = 1;

    /* renamed from: c  reason: collision with root package name */
    private String f420c;

    /* renamed from: d  reason: collision with root package name */
    private int f421d;

    /* renamed from: e  reason: collision with root package name */
    private boolean f422e;
    private boolean f;

    public b(String str, int i, boolean z) {
        this.f420c = str;
        this.f421d = i;
        this.f422e = z;
        this.f = TextUtils.isEmpty(str);
    }

    public String a() {
        StringBuilder sb = new StringBuilder();
        sb.append(j.i);
        if (this.f) {
            sb.append(" is null");
        } else {
            sb.append(" = \"");
            sb.append(this.f420c);
            sb.append("\"");
        }
        if (this.f421d != 0) {
            sb.append(" and ");
            sb.append("eg");
            sb.append(" = \"");
            sb.append(l.a.h);
            sb.append("\"");
        }
        sb.append(" and ");
        sb.append(j.j);
        sb.append(" = ");
        sb.append(this.f422e ? 1 : 0);
        return sb.toString();
    }

    public boolean a(String str, String str2, boolean z) {
        if (TextUtils.equals(str, this.f420c) && this.f422e == z) {
            if (this.f421d == 0) {
                return true;
            }
            return this.f && TextUtils.equals(str2, l.a.h);
        }
    }
}
