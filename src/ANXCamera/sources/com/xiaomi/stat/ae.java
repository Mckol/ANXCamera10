package com.xiaomi.stat;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.text.TextUtils;
import com.xiaomi.stat.ab;
import com.xiaomi.stat.d.k;

class ae implements Runnable {

    /* renamed from: a  reason: collision with root package name */
    final /* synthetic */ String f471a;

    /* renamed from: b  reason: collision with root package name */
    final /* synthetic */ String f472b;

    /* renamed from: c  reason: collision with root package name */
    final /* synthetic */ ab f473c;

    ae(ab abVar, String str, String str2) {
        this.f473c = abVar;
        this.f471a = str;
        this.f472b = str2;
    }

    /* JADX WARNING: Removed duplicated region for block: B:29:0x0088  */
    /* JADX WARNING: Removed duplicated region for block: B:31:0x008e  */
    /* JADX WARNING: Removed duplicated region for block: B:36:? A[RETURN, SYNTHETIC] */
    public void run() {
        Throwable th;
        Exception e2;
        Cursor cursor = null;
        try {
            SQLiteDatabase writableDatabase = ab.a(this.f473c).getWritableDatabase();
            if (TextUtils.isEmpty(this.f471a)) {
                writableDatabase.delete(ab.a.f465b, "pref_key=?", new String[]{this.f472b});
                return;
            }
            Cursor query = writableDatabase.query(ab.a.f465b, null, "pref_key=?", new String[]{this.f472b}, null, null, null);
            try {
                boolean z = query.getCount() <= 0;
                ContentValues contentValues = new ContentValues();
                contentValues.put(ab.a.f466c, this.f472b);
                contentValues.put(ab.a.f467d, this.f471a);
                if (z) {
                    writableDatabase.insert(ab.a.f465b, null, contentValues);
                } else {
                    writableDatabase.update(ab.a.f465b, contentValues, "pref_key=?", new String[]{this.f472b});
                }
                if (query != null) {
                    query.close();
                }
            } catch (Exception e3) {
                e2 = e3;
                cursor = query;
                try {
                    k.c("MiStatPref", "update pref db failed with " + e2);
                    if (cursor == null) {
                    }
                } catch (Throwable th2) {
                    th = th2;
                    if (cursor != null) {
                    }
                    throw th;
                }
            } catch (Throwable th3) {
                th = th3;
                cursor = query;
                if (cursor != null) {
                    cursor.close();
                }
                throw th;
            }
        } catch (Exception e4) {
            e2 = e4;
            k.c("MiStatPref", "update pref db failed with " + e2);
            if (cursor == null) {
                cursor.close();
            }
        }
    }
}
