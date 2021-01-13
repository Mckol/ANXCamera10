package com.xiaomi.stat;

import com.xiaomi.stat.a.l;

/* access modifiers changed from: package-private */
public class p implements Runnable {

    /* renamed from: a  reason: collision with root package name */
    final /* synthetic */ int f684a;

    /* renamed from: b  reason: collision with root package name */
    final /* synthetic */ int f685b;

    /* renamed from: c  reason: collision with root package name */
    final /* synthetic */ long f686c;

    /* renamed from: d  reason: collision with root package name */
    final /* synthetic */ long f687d;

    /* renamed from: e  reason: collision with root package name */
    final /* synthetic */ e f688e;

    p(e eVar, int i, int i2, long j, long j2) {
        this.f688e = eVar;
        this.f684a = i;
        this.f685b = i2;
        this.f686c = j;
        this.f687d = j2;
    }

    public void run() {
        if (b.a() && (this.f688e.g())) {
            this.f688e.a((e) l.a(this.f684a, this.f685b, this.f686c, this.f687d));
        }
    }
}
