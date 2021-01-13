package com.xiaomi.stat;

import com.xiaomi.stat.a.l;

/* access modifiers changed from: package-private */
public class m implements Runnable {

    /* renamed from: a  reason: collision with root package name */
    final /* synthetic */ HttpEvent f679a;

    /* renamed from: b  reason: collision with root package name */
    final /* synthetic */ e f680b;

    m(e eVar, HttpEvent httpEvent) {
        this.f680b = eVar;
        this.f679a = httpEvent;
    }

    public void run() {
        if (b.a() && (this.f680b.g(false))) {
            e eVar = this.f680b;
            eVar.a((e) l.a(this.f679a, eVar.f660b));
        }
    }
}
