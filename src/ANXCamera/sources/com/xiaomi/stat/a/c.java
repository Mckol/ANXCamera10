package com.xiaomi.stat.a;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.text.TextUtils;
import com.xiaomi.stat.MiStatParams;
import com.xiaomi.stat.a;
import com.xiaomi.stat.a.l;
import com.xiaomi.stat.ak;
import com.xiaomi.stat.d.k;
import com.xiaomi.stat.d.m;
import java.io.File;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class c {

    /* renamed from: a */
    private static final String f423a;

    /* renamed from: b */
    private static final int f424b;

    /* renamed from: c */
    private static final int f425c;

    /* renamed from: d */
    private static final int f426d;

    /* renamed from: e */
    private static final int f427e;
    private static final int f;
    private static final int g;
    private static final String h;
    private static final int i;
    private static final long j;
    private static c k;
    private a l;
    private File m;

    private c() {
        Context a2 = ak.a();
        this.l = new a(a2);
        this.m = a2.getDatabasePath(j.f438a);
    }

    public static c a() {
        if (k == null) {
            synchronized (c.class) {
                if (k == null) {
                    k = new c();
                }
            }
        }
        return k;
    }

    private void a(MiStatParams miStatParams) {
        miStatParams.putString(l.a.n, com.xiaomi.stat.d.c.b());
        miStatParams.putString(l.a.o, a.g);
        miStatParams.putString(l.a.p, m.c());
        miStatParams.putString(l.a.q, m.d());
        miStatParams.putString(l.a.r, com.xiaomi.stat.d.l.b(ak.a()));
        miStatParams.putString(l.a.s, m.a(ak.a()));
        miStatParams.putString(l.a.t, Build.MANUFACTURER);
        miStatParams.putString(l.a.u, Build.MODEL);
        miStatParams.putString(l.a.v, m.b());
    }

    private boolean a(b[] bVarArr, String str, String str2, boolean z) {
        for (b bVar : bVarArr) {
            if (bVar.a(str, str2, z)) {
                return true;
            }
        }
        return false;
    }

    /* access modifiers changed from: public */
    /* JADX WARNING: Removed duplicated region for block: B:62:0x014a  */
    /* JADX WARNING: Removed duplicated region for block: B:84:0x0198  */
    /* JADX WARNING: Removed duplicated region for block: B:88:0x01a1  */
    /* JADX WARNING: Removed duplicated region for block: B:90:0x0144 A[SYNTHETIC] */
    /* JADX WARNING: Removed duplicated region for block: B:98:? A[RETURN, SYNTHETIC] */
    private k b(b[] bVarArr) {
        Cursor cursor;
        Throwable th;
        Cursor cursor2;
        Exception e2;
        String str;
        boolean z;
        ArrayList arrayList;
        JSONArray jSONArray;
        boolean z2;
        String str2;
        String str3;
        String str4;
        c cVar = this;
        b[] bVarArr2 = bVarArr;
        String str5 = "tp";
        try {
            if (bVarArr2.length == 1) {
                str = bVarArr2[0].a();
                z = false;
            } else {
                z = true;
                str = null;
            }
            cursor = cVar.l.getReadableDatabase().query(j.f439b, null, str, null, null, null, h);
            try {
                int columnIndex = cursor.getColumnIndex("_id");
                int columnIndex2 = cursor.getColumnIndex("e");
                int columnIndex3 = cursor.getColumnIndex("eg");
                int columnIndex4 = cursor.getColumnIndex(str5);
                int columnIndex5 = cursor.getColumnIndex("ts");
                int columnIndex6 = cursor.getColumnIndex("ps");
                int columnIndex7 = cursor.getColumnIndex(j.i);
                String str6 = "ps";
                int columnIndex8 = cursor.getColumnIndex(j.j);
                String str7 = "ts";
                JSONArray jSONArray2 = new JSONArray();
                ArrayList arrayList2 = new ArrayList();
                int i2 = 0;
                while (true) {
                    if (!cursor.moveToNext()) {
                        arrayList = arrayList2;
                        jSONArray = jSONArray2;
                        z2 = true;
                        break;
                    }
                    long j2 = cursor.getLong(columnIndex);
                    String string = cursor.getString(columnIndex2);
                    String string2 = cursor.getString(columnIndex3);
                    String string3 = cursor.getString(columnIndex4);
                    long j3 = cursor.getLong(columnIndex5);
                    String string4 = cursor.getString(columnIndex6);
                    String string5 = cursor.getString(columnIndex7);
                    boolean z3 = cursor.getInt(columnIndex8) == 1;
                    if (z) {
                        if (!cVar.a(bVarArr2, string5, string2, z3)) {
                            str4 = str6;
                            str2 = str7;
                            jSONArray = jSONArray2;
                            str3 = str5;
                            arrayList = arrayList2;
                            cVar = this;
                            jSONArray2 = jSONArray;
                            str6 = str4;
                            str5 = str3;
                            str7 = str2;
                            columnIndex = columnIndex;
                            columnIndex2 = columnIndex2;
                            columnIndex3 = columnIndex3;
                            columnIndex5 = columnIndex5;
                            columnIndex6 = columnIndex6;
                            columnIndex7 = columnIndex7;
                            columnIndex8 = columnIndex8;
                            arrayList2 = arrayList;
                            bVarArr2 = bVarArr;
                        }
                    }
                    int length = i2 + (string4.length() * 2) + 55;
                    if (!TextUtils.isEmpty(string)) {
                        length += string.length() * 2;
                    }
                    if (!TextUtils.isEmpty(string2)) {
                        length += string2.length() * 2;
                    }
                    if (length > f427e) {
                        jSONArray = jSONArray2;
                        arrayList = arrayList2;
                        z2 = false;
                        break;
                    }
                    JSONObject jSONObject = new JSONObject();
                    try {
                        jSONObject.put("e", string);
                        jSONObject.put("eg", string2);
                        str3 = str5;
                        try {
                            jSONObject.put(str3, string3);
                            str2 = str7;
                            try {
                                jSONObject.put(str2, j3);
                                jSONObject.put(l.a.g, j2);
                                str4 = str6;
                                try {
                                    jSONObject.put(str4, new JSONObject(string4));
                                    jSONArray = jSONArray2;
                                    try {
                                        jSONArray.put(jSONObject);
                                        arrayList = arrayList2;
                                        try {
                                            arrayList.add(Long.valueOf(j2));
                                        } catch (JSONException unused) {
                                        }
                                    } catch (JSONException unused2) {
                                        arrayList = arrayList2;
                                        if (arrayList.size() < 300) {
                                        }
                                    }
                                } catch (JSONException unused3) {
                                    jSONArray = jSONArray2;
                                    arrayList = arrayList2;
                                    if (arrayList.size() < 300) {
                                    }
                                }
                            } catch (JSONException unused4) {
                                str4 = str6;
                                jSONArray = jSONArray2;
                                arrayList = arrayList2;
                                if (arrayList.size() < 300) {
                                }
                            }
                        } catch (JSONException unused5) {
                            str4 = str6;
                            str2 = str7;
                            jSONArray = jSONArray2;
                            arrayList = arrayList2;
                            if (arrayList.size() < 300) {
                            }
                        }
                    } catch (JSONException unused6) {
                        str4 = str6;
                        str2 = str7;
                        jSONArray = jSONArray2;
                        str3 = str5;
                        arrayList = arrayList2;
                        if (arrayList.size() < 300) {
                        }
                    }
                    if (arrayList.size() < 300) {
                        z2 = cursor.isLast();
                        break;
                    }
                    i2 = length;
                    cVar = this;
                    jSONArray2 = jSONArray;
                    str6 = str4;
                    str5 = str3;
                    str7 = str2;
                    columnIndex = columnIndex;
                    columnIndex2 = columnIndex2;
                    columnIndex3 = columnIndex3;
                    columnIndex5 = columnIndex5;
                    columnIndex6 = columnIndex6;
                    columnIndex7 = columnIndex7;
                    columnIndex8 = columnIndex8;
                    arrayList2 = arrayList;
                    bVarArr2 = bVarArr;
                }
                if (arrayList.size() > 0) {
                    k kVar = new k(jSONArray, arrayList, z2);
                    if (cursor != null) {
                        cursor.close();
                    }
                    return kVar;
                } else if (cursor == null) {
                    return null;
                } else {
                    cursor.close();
                    return null;
                }
            } catch (Exception e3) {
                e2 = e3;
                cursor2 = cursor;
                try {
                    k.b(f423a, e2.toString());
                    if (cursor2 != null) {
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
            k.b(f423a, e2.toString());
            if (cursor2 != null) {
                return null;
            }
            cursor2.close();
            return null;
        } catch (Throwable th4) {
            th = th4;
            cursor = null;
            if (cursor != null) {
                cursor.close();
            }
            throw th;
        }
    }

    private void b(l lVar) {
        d();
        SQLiteDatabase writableDatabase = this.l.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("e", lVar.f446a);
        contentValues.put("eg", lVar.f447b);
        contentValues.put("tp", lVar.f448c);
        contentValues.put("ts", Long.valueOf(lVar.f450e));
        if (c(lVar)) {
            a(lVar.f449d);
        }
        contentValues.put("ps", lVar.f449d.toJsonString());
        contentValues.put(j.i, lVar.f);
        contentValues.put(j.j, Integer.valueOf(lVar.g ? 1 : 0));
        contentValues.put(j.k, Integer.valueOf(TextUtils.equals(lVar.f447b, l.a.h) ? 10 : 0));
        writableDatabase.insert(j.f439b, null, contentValues);
    }

    /* access modifiers changed from: public */
    private void b(ArrayList<Long> arrayList) {
        if (arrayList != null && arrayList.size() != 0) {
            try {
                SQLiteDatabase writableDatabase = this.l.getWritableDatabase();
                StringBuilder sb = new StringBuilder(((Long.toString(arrayList.get(0).longValue()).length() + 1) * arrayList.size()) + 16);
                sb.append("_id");
                sb.append(" in (");
                sb.append(arrayList.get(0));
                int size = arrayList.size();
                for (int i2 = 1; i2 < size; i2++) {
                    sb.append(",");
                    sb.append(arrayList.get(i2));
                }
                sb.append(")");
                int delete = writableDatabase.delete(j.f439b, sb.toString(), null);
                k.c(f423a, "deleted events number " + delete);
            } catch (Exception unused) {
            }
        }
    }

    private boolean c(l lVar) {
        return !lVar.f448c.startsWith(l.a.w);
    }

    private void d() {
        if (this.m.exists() && this.m.length() >= j) {
            k.e(f423a, "database too big: " + this.m.length());
            this.l.getWritableDatabase().delete(j.f439b, null, null);
        }
    }

    public k a(b[] bVarArr) {
        FutureTask futureTask = new FutureTask(new e(this, bVarArr));
        com.xiaomi.stat.c.a(futureTask);
        try {
            return (k) futureTask.get();
        } catch (InterruptedException | ExecutionException unused) {
            return null;
        }
    }

    public void a(l lVar) {
        com.xiaomi.stat.c.a(new d(this, lVar));
        k.c(f423a, "add event: name=" + lVar.f446a);
    }

    public void a(String str) {
        com.xiaomi.stat.c.a(new h(this, str));
    }

    public void a(ArrayList<Long> arrayList) {
        FutureTask futureTask = new FutureTask(new f(this, arrayList), null);
        com.xiaomi.stat.c.a(futureTask);
        try {
            futureTask.get();
        } catch (InterruptedException | ExecutionException unused) {
        }
    }

    public void b() {
        com.xiaomi.stat.c.a(new g(this));
    }

    public long c() {
        FutureTask futureTask = new FutureTask(new i(this));
        com.xiaomi.stat.c.a(futureTask);
        try {
            return ((Long) futureTask.get()).longValue();
        } catch (InterruptedException | ExecutionException unused) {
            return -1;
        }
    }
}
