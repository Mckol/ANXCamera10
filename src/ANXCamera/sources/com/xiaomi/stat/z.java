package com.xiaomi.stat;

import com.xiaomi.stat.a.l;

/* access modifiers changed from: package-private */
public class z implements Runnable {

    /* renamed from: a  reason: collision with root package name */
    final /* synthetic */ Throwable f714a;

    /* renamed from: b  reason: collision with root package name */
    final /* synthetic */ String f715b;

    /* renamed from: c  reason: collision with root package name */
    final /* synthetic */ boolean f716c;

    /* renamed from: d  reason: collision with root package name */
    final /* synthetic */ e f717d;

    z(e eVar, Throwable th, String str, boolean z) {
        this.f717d = eVar;
        this.f714a = th;
        this.f715b = str;
        this.f716c = z;
    }

    public void run() {
        if (b.a() && (this.f717d.g(false))) {
            e eVar = this.f717d;
            eVar.a((e) l.a(this.f714a, this.f715b, this.f716c, eVar.f660b));
        }
    }
}
