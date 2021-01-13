package com.xiaomi.stat.a;

import com.xiaomi.stat.d.k;

class d implements Runnable {

    /* renamed from: a  reason: collision with root package name */
    final /* synthetic */ l f428a;

    /* renamed from: b  reason: collision with root package name */
    final /* synthetic */ c f429b;

    d(c cVar, l lVar) {
        this.f429b = cVar;
        this.f428a = lVar;
    }

    public void run() {
        try {
            c.a(this.f429b, this.f428a);
        } catch (Exception e2) {
            k.e("EventManager", "addEvent exception: " + e2.toString());
        }
    }
}
