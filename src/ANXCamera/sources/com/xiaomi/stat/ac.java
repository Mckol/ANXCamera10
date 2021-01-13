package com.xiaomi.stat;

import android.database.Cursor;
import com.xiaomi.stat.ab;
import java.util.concurrent.Callable;

class ac implements Callable<Cursor> {

    /* renamed from: a  reason: collision with root package name */
    final /* synthetic */ ab f469a;

    ac(ab abVar) {
        this.f469a = abVar;
    }

    /* renamed from: a */
    public Cursor call() throws Exception {
        try {
            return ab.a(this.f469a).getWritableDatabase().query(ab.a.f465b, null, null, null, null, null, null);
        } catch (Exception unused) {
            return null;
        }
    }
}
