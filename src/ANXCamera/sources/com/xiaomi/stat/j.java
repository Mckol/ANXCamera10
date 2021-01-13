package com.xiaomi.stat;

import com.xiaomi.stat.a.l;

/* access modifiers changed from: package-private */
public class j implements Runnable {

    /* renamed from: a  reason: collision with root package name */
    final /* synthetic */ int f672a;

    /* renamed from: b  reason: collision with root package name */
    final /* synthetic */ int f673b;

    /* renamed from: c  reason: collision with root package name */
    final /* synthetic */ e f674c;

    j(e eVar, int i, int i2) {
        this.f674c = eVar;
        this.f672a = i;
        this.f673b = i2;
    }

    public void run() {
        if (b.a() && (this.f674c.g())) {
            b.e(this.f672a);
            this.f674c.a((e) l.a(this.f673b));
        }
    }
}
