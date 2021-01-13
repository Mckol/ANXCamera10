package com.xiaomi.stat;

import android.text.TextUtils;
import com.xiaomi.stat.a.l;

/* access modifiers changed from: package-private */
public class h implements Runnable {

    /* renamed from: a  reason: collision with root package name */
    final /* synthetic */ String f668a;

    /* renamed from: b  reason: collision with root package name */
    final /* synthetic */ e f669b;

    h(e eVar, String str) {
        this.f669b = eVar;
        this.f668a = str;
    }

    public void run() {
        if (b.a() && !TextUtils.equals(b.h(), this.f668a)) {
            b.b(this.f668a);
            if (this.f669b.g()) {
                this.f669b.a((e) l.a(this.f668a));
            }
        }
    }
}
