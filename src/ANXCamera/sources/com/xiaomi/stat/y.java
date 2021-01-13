package com.xiaomi.stat;

import com.xiaomi.stat.a.l;

/* access modifiers changed from: package-private */
public class y implements Runnable {

    /* renamed from: a  reason: collision with root package name */
    final /* synthetic */ boolean f709a;

    /* renamed from: b  reason: collision with root package name */
    final /* synthetic */ String f710b;

    /* renamed from: c  reason: collision with root package name */
    final /* synthetic */ String f711c;

    /* renamed from: d  reason: collision with root package name */
    final /* synthetic */ MiStatParams f712d;

    /* renamed from: e  reason: collision with root package name */
    final /* synthetic */ e f713e;

    y(e eVar, boolean z, String str, String str2, MiStatParams miStatParams) {
        this.f713e = eVar;
        this.f709a = z;
        this.f710b = str;
        this.f711c = str2;
        this.f712d = miStatParams;
    }

    public void run() {
        if (b.a() && (this.f713e.g(this.f709a)) && b.A()) {
            e eVar = this.f713e;
            eVar.a((e) l.a(this.f710b, this.f711c, this.f712d, eVar.f660b, this.f709a));
        }
    }
}
