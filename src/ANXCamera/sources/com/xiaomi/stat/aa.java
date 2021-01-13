package com.xiaomi.stat;

import com.xiaomi.stat.a.l;

/* access modifiers changed from: package-private */
public class aa implements Runnable {

    /* renamed from: a  reason: collision with root package name */
    final /* synthetic */ boolean f456a;

    /* renamed from: b  reason: collision with root package name */
    final /* synthetic */ MiStatParams f457b;

    /* renamed from: c  reason: collision with root package name */
    final /* synthetic */ e f458c;

    aa(e eVar, boolean z, MiStatParams miStatParams) {
        this.f458c = eVar;
        this.f456a = z;
        this.f457b = miStatParams;
    }

    public void run() {
        if (b.a() && (this.f458c.g(this.f456a))) {
            e eVar = this.f458c;
            eVar.a((e) l.a(this.f457b, this.f456a, eVar.f660b));
        }
    }
}
