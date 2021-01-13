package com.xiaomi.stat;

import com.xiaomi.stat.a.l;
import com.xiaomi.stat.d.r;

/* access modifiers changed from: package-private */
public class k implements Runnable {

    /* renamed from: a  reason: collision with root package name */
    final /* synthetic */ e f675a;

    k(e eVar) {
        this.f675a = eVar;
    }

    public void run() {
        if (b.a() && (this.f675a.g()) && b.z()) {
            long b2 = r.b();
            if (this.f675a.a(b.r(), b2)) {
                b.a(b2);
                this.f675a.a((e) l.a());
            }
        }
    }
}
