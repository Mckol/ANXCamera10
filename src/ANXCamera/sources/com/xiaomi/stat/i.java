package com.xiaomi.stat;

import com.xiaomi.stat.a.c;

/* access modifiers changed from: package-private */
public class i implements Runnable {

    /* renamed from: a  reason: collision with root package name */
    final /* synthetic */ boolean f670a;

    /* renamed from: b  reason: collision with root package name */
    final /* synthetic */ e f671b;

    i(e eVar, boolean z) {
        this.f671b = eVar;
        this.f670a = z;
    }

    public void run() {
        if (b.d(this.f671b.f661c)) {
            int i = 2;
            if (!this.f670a && b.e(this.f671b.f661c) != 2) {
                c.a().a(this.f671b.f661c);
            }
            String str = this.f671b.f661c;
            if (this.f670a) {
                i = 1;
            }
            b.a(str, i);
        }
    }
}
