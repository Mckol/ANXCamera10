package com.xiaomi.stat;

import com.xiaomi.stat.a.c;
import com.xiaomi.stat.b.g;
import com.xiaomi.stat.d.e;

/* access modifiers changed from: package-private */
public class f implements Runnable {

    /* renamed from: a  reason: collision with root package name */
    final /* synthetic */ String f664a;

    /* renamed from: b  reason: collision with root package name */
    final /* synthetic */ boolean f665b;

    /* renamed from: c  reason: collision with root package name */
    final /* synthetic */ e f666c;

    f(e eVar, String str, boolean z) {
        this.f666c = eVar;
        this.f664a = str;
        this.f665b = z;
    }

    public void run() {
        e.a();
        if (this.f666c.f659a) {
            b.h(this.f664a);
        }
        b.d();
        g.a().a(b.f());
        b.a(this.f666c.f661c, this.f665b);
        b.n();
        if (!(this.f666c.f659a)) {
            b.f(this.f666c.f660b);
        }
        this.f666c.f();
        c.a().b();
        com.xiaomi.stat.b.e.a().execute(new g(this));
    }
}
