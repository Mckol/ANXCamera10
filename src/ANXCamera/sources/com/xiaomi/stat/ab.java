package com.xiaomi.stat;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.FileObserver;
import android.text.TextUtils;
import com.xiaomi.stat.d.k;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class ab {

    /* renamed from: a */
    private static final String f459a;

    /* renamed from: b */
    private static final String f460b;

    /* renamed from: c */
    private static final String f461c;

    /* renamed from: e */
    private static ab f462e;

    /* renamed from: d */
    private FileObserver f463d;
    private Map<String, String> f = new HashMap();
    private SQLiteOpenHelper g;

    /* access modifiers changed from: private */
    public static class a extends SQLiteOpenHelper {

        /* renamed from: a */
        public static final String f464a;

        /* renamed from: b */
        public static final String f465b;

        /* renamed from: c */
        public static final String f466c;

        /* renamed from: d */
        public static final String f467d;

        /* renamed from: e */
        private static final int f468e;
        private static final String f;
        private static final String g;

        public a(Context context) {
            super(context, f464a, (SQLiteDatabase.CursorFactory) null, 1);
        }

        public void onCreate(SQLiteDatabase sQLiteDatabase) {
            sQLiteDatabase.execSQL(g);
        }

        public void onUpgrade(SQLiteDatabase sQLiteDatabase, int i, int i2) {
        }
    }

    private ab() {
        Context a2 = ak.a();
        this.g = new a(a2);
        b();
        c(a2.getDatabasePath(a.f464a).getAbsolutePath());
    }

    public static ab a() {
        if (f462e == null) {
            synchronized (ab.class) {
                if (f462e == null) {
                    f462e = new ab();
                }
            }
        }
        return f462e;
    }

    /* access modifiers changed from: public */
    private void b() {
        FutureTask futureTask = new FutureTask(new ac(this));
        try {
            c.a(futureTask);
            Cursor cursor = null;
            try {
                cursor = (Cursor) futureTask.get(2, TimeUnit.SECONDS);
            } catch (InterruptedException | ExecutionException | TimeoutException unused) {
            }
            if (cursor != null) {
                this.f.clear();
                try {
                    k.c(f459a, "load pref from db");
                    int columnIndex = cursor.getColumnIndex(a.f466c);
                    int columnIndex2 = cursor.getColumnIndex(a.f467d);
                    while (cursor.moveToNext()) {
                        String string = cursor.getString(columnIndex);
                        String string2 = cursor.getString(columnIndex2);
                        this.f.put(string, string2);
                        k.c(f459a, "key=" + string + " ,value=" + string2);
                    }
                } catch (Exception unused2) {
                } catch (Throwable th) {
                    cursor.close();
                    throw th;
                }
                cursor.close();
            }
        } catch (RejectedExecutionException e2) {
            k.c(f459a, "load data execute failed with " + e2);
        }
    }

    private void c(String str) {
        this.f463d = new ad(this, str);
        this.f463d.startWatching();
    }

    private void c(String str, String str2) {
        synchronized (this) {
            boolean z = true;
            if (!TextUtils.isEmpty(str2)) {
                this.f.put(str, str2);
            } else if (this.f.containsKey(str)) {
                this.f.remove(str);
            } else {
                z = false;
            }
            k.c(f459a, "put value: key=" + str + " ,value=" + str2);
            if (z) {
                FutureTask futureTask = new FutureTask(new ae(this, str2, str), null);
                try {
                    c.a(futureTask);
                    try {
                        futureTask.get();
                    } catch (InterruptedException | ExecutionException unused) {
                    }
                } catch (RejectedExecutionException e2) {
                    k.c(f459a, "execute failed with " + e2);
                }
            }
        }
    }

    public float a(String str, float f2) {
        synchronized (this) {
            if (this.f.containsKey(str)) {
                try {
                    return Float.valueOf(this.f.get(str)).floatValue();
                } catch (NumberFormatException unused) {
                }
            }
            return f2;
        }
    }

    public int a(String str, int i) {
        synchronized (this) {
            if (this.f.containsKey(str)) {
                try {
                    return Integer.valueOf(this.f.get(str)).intValue();
                } catch (NumberFormatException unused) {
                }
            }
            return i;
        }
    }

    public long a(String str, long j) {
        synchronized (this) {
            if (this.f.containsKey(str)) {
                try {
                    return Long.valueOf(this.f.get(str)).longValue();
                } catch (NumberFormatException unused) {
                }
            }
            return j;
        }
    }

    public String a(String str, String str2) {
        synchronized (this) {
            if (!this.f.containsKey(str)) {
                return str2;
            }
            return this.f.get(str);
        }
    }

    public boolean a(String str) {
        boolean containsKey;
        synchronized (this) {
            containsKey = this.f.containsKey(str);
        }
        return containsKey;
    }

    public boolean a(String str, boolean z) {
        synchronized (this) {
            if (this.f.containsKey(str)) {
                String str2 = this.f.get(str);
                if ("true".equalsIgnoreCase(str2)) {
                    return true;
                }
                if ("false".equalsIgnoreCase(str2)) {
                    return false;
                }
            }
            return z;
        }
    }

    public void b(String str) {
        b(str, (String) null);
    }

    public void b(String str, float f2) {
        c(str, Float.toString(f2));
    }

    public void b(String str, int i) {
        c(str, Integer.toString(i));
    }

    public void b(String str, long j) {
        c(str, Long.toString(j));
    }

    public void b(String str, String str2) {
        c(str, str2);
    }

    public void b(String str, boolean z) {
        c(str, Boolean.toString(z));
    }
}
