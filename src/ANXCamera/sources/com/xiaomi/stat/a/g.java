package com.xiaomi.stat.a;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.text.TextUtils;
import com.xiaomi.stat.a.l;
import com.xiaomi.stat.aj;
import com.xiaomi.stat.d.k;
import com.xiaomi.stat.d.r;
import java.util.Calendar;

class g implements Runnable {

    /* renamed from: a  reason: collision with root package name */
    final /* synthetic */ c f434a;

    g(c cVar) {
        this.f434a = cVar;
    }

    /* JADX WARNING: Removed duplicated region for block: B:32:0x0138  */
    /* JADX WARNING: Removed duplicated region for block: B:36:0x0140  */
    /* JADX WARNING: Removed duplicated region for block: B:44:? A[RETURN, SYNTHETIC] */
    public void run() {
        Cursor cursor;
        Throwable th;
        Cursor cursor2;
        Exception e2;
        try {
            SQLiteDatabase writableDatabase = this.f434a.l.getWritableDatabase();
            Calendar instance = Calendar.getInstance();
            instance.setTimeInMillis(r.b());
            instance.set(6, instance.get(6) - 7);
            instance.set(11, 0);
            instance.set(12, 0);
            instance.set(13, 0);
            String[] strArr = {Long.toString(instance.getTimeInMillis()), l.a.m};
            int i = 1;
            int i2 = 2;
            cursor = writableDatabase.query(j.f439b, new String[]{"ts"}, "ts < ? and e != ?", strArr, null, null, "ts ASC");
            try {
                int count = cursor.getCount();
                if (count != 0) {
                    aj ajVar = new aj();
                    ajVar.putInt(l.a.x, count);
                    k.c("EventManager", "delete obsolete events total number " + count);
                    int columnIndex = cursor.getColumnIndex("ts");
                    String str = null;
                    int i3 = 0;
                    while (cursor.moveToNext()) {
                        instance.setTimeInMillis(cursor.getLong(columnIndex));
                        String format = String.format("%4d%02d%02d", Integer.valueOf(instance.get(i)), Integer.valueOf(instance.get(i2) + i), Integer.valueOf(instance.get(5)));
                        if (!TextUtils.equals(str, format)) {
                            if (str != null) {
                                ajVar.putInt(l.a.y + str, i3);
                            }
                            str = format;
                            i3 = 1;
                        } else {
                            i3++;
                        }
                        i = 1;
                        i2 = 2;
                    }
                    if (str != null) {
                        ajVar.putInt(l.a.y + str, i3);
                    }
                    this.f434a.b((c) l.a(ajVar));
                    writableDatabase.delete(j.f439b, "ts < ? and e != ?", strArr);
                }
                if (cursor != null) {
                    cursor.close();
                }
            } catch (Exception e3) {
                e2 = e3;
                cursor2 = cursor;
                try {
                    k.c("EventManager", "remove obsolete events failed with " + e2);
                    if (cursor2 == null) {
                    }
                } catch (Throwable th2) {
                    th = th2;
                    cursor = cursor2;
                    if (cursor != null) {
                    }
                    throw th;
                }
            } catch (Throwable th3) {
                th = th3;
                if (cursor != null) {
                }
                throw th;
            }
        } catch (Exception e4) {
            e2 = e4;
            cursor2 = null;
            k.c("EventManager", "remove obsolete events failed with " + e2);
            if (cursor2 == null) {
                cursor2.close();
            }
        } catch (Throwable th4) {
            th = th4;
            cursor = null;
            if (cursor != null) {
                cursor.close();
            }
            throw th;
        }
    }
}
