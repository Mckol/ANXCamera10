package com.xiaomi.stat;

import android.text.TextUtils;
import com.xiaomi.stat.b.g;
import com.xiaomi.stat.d.m;

/* access modifiers changed from: package-private */
public class l implements Runnable {

    /* renamed from: a  reason: collision with root package name */
    final /* synthetic */ boolean f676a;

    /* renamed from: b  reason: collision with root package name */
    final /* synthetic */ String f677b;

    /* renamed from: c  reason: collision with root package name */
    final /* synthetic */ e f678c;

    l(e eVar, boolean z, String str) {
        this.f678c = eVar;
        this.f676a = z;
        this.f677b = str;
    }

    public void run() {
        if (!m.a()) {
            b.c(this.f676a);
            g.a().a(this.f676a);
        }
        if (b.e() && !TextUtils.isEmpty(this.f677b)) {
            b.a(this.f677b);
            g.a().a(this.f677b);
        }
    }
}
