package com.xiaomi.stat;

import android.os.FileObserver;

class ad extends FileObserver {

    /* renamed from: a  reason: collision with root package name */
    final /* synthetic */ ab f470a;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    ad(ab abVar, String str) {
        super(str);
        this.f470a = abVar;
    }

    public void onEvent(int i, String str) {
        if (i == 2) {
            synchronized (this.f470a) {
                ab.b(this.f470a);
            }
            b.n();
        }
    }
}
