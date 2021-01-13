package com.xiaomi.stat;

import com.xiaomi.stat.a.l;

/* access modifiers changed from: package-private */
public class q implements Runnable {

    /* renamed from: a  reason: collision with root package name */
    final /* synthetic */ String f689a;

    /* renamed from: b  reason: collision with root package name */
    final /* synthetic */ String f690b;

    /* renamed from: c  reason: collision with root package name */
    final /* synthetic */ e f691c;

    q(e eVar, String str, String str2) {
        this.f691c = eVar;
        this.f689a = str;
        this.f690b = str2;
    }

    public void run() {
        if (b.a() && (this.f691c.g(false))) {
            e eVar = this.f691c;
            eVar.a((e) l.a(this.f689a, this.f690b, eVar.f660b));
        }
    }
}
