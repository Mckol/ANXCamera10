package com.xiaomi.stat.a;

import android.database.sqlite.SQLiteDatabase;
import android.text.TextUtils;
import com.xiaomi.stat.ak;
import com.xiaomi.stat.d.k;

class h implements Runnable {

    /* renamed from: a  reason: collision with root package name */
    final /* synthetic */ String f435a;

    /* renamed from: b  reason: collision with root package name */
    final /* synthetic */ c f436b;

    h(c cVar, String str) {
        this.f436b = cVar;
        this.f435a = str;
    }

    public void run() {
        String str;
        String[] strArr;
        try {
            SQLiteDatabase writableDatabase = this.f436b.l.getWritableDatabase();
            if (TextUtils.equals(this.f435a, ak.b())) {
                str = "sub is null";
                strArr = null;
            } else {
                String[] strArr2 = {this.f435a};
                str = "sub = ?";
                strArr = strArr2;
            }
            writableDatabase.delete(j.f439b, str, strArr);
        } catch (Exception e2) {
            k.b("EventManager", "removeAllEventsForApp exception: " + e2.toString());
        }
    }
}
