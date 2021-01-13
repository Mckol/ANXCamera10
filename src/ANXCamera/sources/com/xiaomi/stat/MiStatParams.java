package com.xiaomi.stat;

import com.xiaomi.stat.d.j;
import com.xiaomi.stat.d.k;
import com.xiaomi.stat.d.n;
import java.io.Reader;
import java.io.StringReader;
import org.json.JSONException;
import org.json.JSONObject;

public class MiStatParams {

    /* renamed from: a  reason: collision with root package name */
    private static final String f398a = "MiStatParams";

    /* renamed from: b  reason: collision with root package name */
    private JSONObject f399b;

    public MiStatParams() {
        this.f399b = new JSONObject();
    }

    MiStatParams(MiStatParams miStatParams) {
        JSONObject jSONObject;
        if (miStatParams == null || (jSONObject = miStatParams.f399b) == null) {
            this.f399b = new JSONObject();
        } else {
            this.f399b = a(jSONObject);
        }
    }

    private JSONObject a(JSONObject jSONObject) {
        Throwable th;
        StringReader stringReader;
        Exception e2;
        try {
            stringReader = new StringReader(jSONObject.toString());
            try {
                StringBuilder sb = new StringBuilder();
                while (true) {
                    int read = stringReader.read();
                    if (read != -1) {
                        sb.append((char) read);
                    } else {
                        JSONObject jSONObject2 = new JSONObject(sb.toString());
                        j.a((Reader) stringReader);
                        return jSONObject2;
                    }
                }
            } catch (Exception e3) {
                e2 = e3;
                try {
                    k.e(" deepCopy " + e2);
                    j.a((Reader) stringReader);
                    return jSONObject;
                } catch (Throwable th2) {
                    th = th2;
                    j.a((Reader) stringReader);
                    throw th;
                }
            }
        } catch (Exception e4) {
            stringReader = null;
            e2 = e4;
            k.e(" deepCopy " + e2);
            j.a((Reader) stringReader);
            return jSONObject;
        } catch (Throwable th3) {
            stringReader = null;
            th = th3;
            j.a((Reader) stringReader);
            throw th;
        }
    }

    private boolean c(String str) {
        return a() && !this.f399b.has(str) && this.f399b.length() == 30;
    }

    /* access modifiers changed from: package-private */
    public boolean a() {
        return true;
    }

    /* access modifiers changed from: package-private */
    public boolean a(String str) {
        return n.a(str);
    }

    /* access modifiers changed from: package-private */
    public boolean b(String str) {
        return n.b(str);
    }

    public int getParamsNumber() {
        return this.f399b.length();
    }

    public boolean isEmpty() {
        return this.f399b.length() == 0;
    }

    public void putBoolean(String str, boolean z) {
        if (!a(str)) {
            n.e(str);
        } else if (c(str)) {
            n.a();
        } else {
            try {
                this.f399b.put(str, z);
            } catch (JSONException e2) {
                k.c(f398a, "put value error " + e2);
            }
        }
    }

    public void putDouble(String str, double d2) {
        if (!a(str)) {
            n.e(str);
        } else if (c(str)) {
            n.a();
        } else {
            try {
                this.f399b.put(str, d2);
            } catch (JSONException e2) {
                k.c(f398a, "put value error " + e2);
            }
        }
    }

    public void putInt(String str, int i) {
        if (!a(str)) {
            n.e(str);
        } else if (c(str)) {
            n.a();
        } else {
            try {
                this.f399b.put(str, i);
            } catch (JSONException e2) {
                k.c(f398a, "put value error " + e2);
            }
        }
    }

    public void putLong(String str, long j) {
        if (!a(str)) {
            n.e(str);
        } else if (c(str)) {
            n.a();
        } else {
            try {
                this.f399b.put(str, j);
            } catch (JSONException e2) {
                k.c(f398a, "put value error " + e2);
            }
        }
    }

    public void putString(String str, String str2) {
        if (!a(str)) {
            n.e(str);
        } else if (!b(str2)) {
            n.f(str2);
        } else if (c(str)) {
            n.a();
        } else {
            try {
                this.f399b.put(str, n.c(str2));
            } catch (JSONException e2) {
                k.c(f398a, "put value error " + e2);
            }
        }
    }

    public String toJsonString() {
        return this.f399b.toString();
    }
}
