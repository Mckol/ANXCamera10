package com.xiaomi.stat.a;

import java.util.concurrent.Callable;

class e implements Callable<k> {

    /* renamed from: a  reason: collision with root package name */
    final /* synthetic */ b[] f430a;

    /* renamed from: b  reason: collision with root package name */
    final /* synthetic */ c f431b;

    e(c cVar, b[] bVarArr) {
        this.f431b = cVar;
        this.f430a = bVarArr;
    }

    /* renamed from: a */
    public k call() throws Exception {
        return c.a(this.f431b, this.f430a);
    }
}
