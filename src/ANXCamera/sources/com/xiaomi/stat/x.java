package com.xiaomi.stat;

import com.xiaomi.stat.a.l;

/* access modifiers changed from: package-private */
public class x implements Runnable {

    /* renamed from: a  reason: collision with root package name */
    final /* synthetic */ String f704a;

    /* renamed from: b  reason: collision with root package name */
    final /* synthetic */ long f705b;

    /* renamed from: c  reason: collision with root package name */
    final /* synthetic */ long f706c;

    /* renamed from: d  reason: collision with root package name */
    final /* synthetic */ MiStatParams f707d;

    /* renamed from: e  reason: collision with root package name */
    final /* synthetic */ e f708e;

    x(e eVar, String str, long j, long j2, MiStatParams miStatParams) {
        this.f708e = eVar;
        this.f704a = str;
        this.f705b = j;
        this.f706c = j2;
        this.f707d = miStatParams;
    }

    public void run() {
        if (b.a() && (this.f708e.g(false)) && b.z()) {
            e eVar = this.f708e;
            String str = this.f704a;
            long j = this.f705b;
            eVar.a((e) l.a(str, j - this.f706c, j, this.f707d, eVar.f660b));
        }
    }
}
