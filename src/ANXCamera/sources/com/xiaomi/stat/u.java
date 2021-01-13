package com.xiaomi.stat;

import com.xiaomi.stat.a.l;

/* access modifiers changed from: package-private */
public class u implements Runnable {

    /* renamed from: a  reason: collision with root package name */
    final /* synthetic */ String f696a;

    /* renamed from: b  reason: collision with root package name */
    final /* synthetic */ long f697b;

    /* renamed from: c  reason: collision with root package name */
    final /* synthetic */ long f698c;

    /* renamed from: d  reason: collision with root package name */
    final /* synthetic */ e f699d;

    u(e eVar, String str, long j, long j2) {
        this.f699d = eVar;
        this.f696a = str;
        this.f697b = j;
        this.f698c = j2;
    }

    public void run() {
        if (b.a() && (this.f699d.g()) && b.z()) {
            this.f699d.a((e) l.a(this.f696a, this.f697b, this.f698c));
        }
    }
}
