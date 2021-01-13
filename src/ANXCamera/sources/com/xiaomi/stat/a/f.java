package com.xiaomi.stat.a;

import java.util.ArrayList;

class f implements Runnable {

    /* renamed from: a  reason: collision with root package name */
    final /* synthetic */ ArrayList f432a;

    /* renamed from: b  reason: collision with root package name */
    final /* synthetic */ c f433b;

    f(c cVar, ArrayList arrayList) {
        this.f433b = cVar;
        this.f432a = arrayList;
    }

    public void run() {
        c.a(this.f433b, this.f432a);
    }
}
