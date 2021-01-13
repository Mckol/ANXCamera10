package com.xiaomi.stat;

import com.xiaomi.stat.a.l;

/* access modifiers changed from: package-private */
public class n implements Runnable {

    /* renamed from: a  reason: collision with root package name */
    final /* synthetic */ NetAvailableEvent f681a;

    /* renamed from: b  reason: collision with root package name */
    final /* synthetic */ e f682b;

    n(e eVar, NetAvailableEvent netAvailableEvent) {
        this.f682b = eVar;
        this.f681a = netAvailableEvent;
    }

    public void run() {
        if (b.a() && (this.f682b.g(false)) && b.y()) {
            e eVar = this.f682b;
            eVar.a((e) l.a(this.f681a, eVar.f660b));
        }
    }
}
