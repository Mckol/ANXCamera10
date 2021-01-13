package com.xiaomi.stat.b;

import android.content.Context;
import android.text.TextUtils;
import com.android.camera.module.loader.FunctionParseBeautyBodySlimCount;
import com.xiaomi.stat.a;
import com.xiaomi.stat.ab;
import com.xiaomi.stat.ak;
import com.xiaomi.stat.b;
import com.xiaomi.stat.c.c;
import com.xiaomi.stat.d.e;
import com.xiaomi.stat.d.g;
import com.xiaomi.stat.d.k;
import com.xiaomi.stat.d.l;
import com.xiaomi.stat.d.r;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;
import org.json.JSONException;
import org.json.JSONObject;

public class d {

    /* renamed from: a  reason: collision with root package name */
    private static final Object f504a = new Object();

    /* renamed from: b  reason: collision with root package name */
    private static String f505b = " https://data.mistat.xiaomi.com/idservice/deviceid_get";

    /* renamed from: c  reason: collision with root package name */
    private static final String f506c = "DeviceIdManager";

    /* renamed from: d  reason: collision with root package name */
    private static final String f507d = "ia";

    /* renamed from: e  reason: collision with root package name */
    private static final String f508e = "ib";
    private static final String f = "md";
    private static final String g = "mm";
    private static final String h = "bm";
    private static final String i = "aa";
    private static final String j = "ai";
    private static final String k = "oa";
    private static final int l = 0;
    private static final int m = 1;
    private static final int n = 2;
    private static final int o = 3;
    private static final int p = 4;
    private static final int q = 5;
    private static final int r = 6;
    private static final int s = 7;
    private static final int t = 1;
    private static final String u = "pref_key_device_id";
    private static final String v = "pref_key_restore_ts";
    private static d w;
    private String x = ab.a().a(u, "");
    private Context y = ak.a();

    private d() {
    }

    public static d a() {
        if (w == null) {
            synchronized (f504a) {
                if (w == null) {
                    w = new d();
                }
            }
        }
        return w;
    }

    private void e() {
        if (!b.a() || !b.b()) {
            k.c(f506c, "request abort: statistic or network is not enabled");
        } else if (l.a()) {
            int i2 = 1;
            while (i2 <= 3 && TextUtils.isEmpty(f()) && i2 != 3) {
                try {
                    Thread.sleep(FunctionParseBeautyBodySlimCount.TIP_INTERVAL_TIME);
                } catch (InterruptedException e2) {
                    e2.printStackTrace();
                }
                i2++;
            }
        } else {
            k.b(f506c, "network is not connected!");
        }
    }

    private String f() {
        try {
            if (k.b()) {
                f505b = k.f616b;
            }
            String a2 = c.a(f505b, (Map<String, String>) h(), true);
            k.b(f506c, a2);
            if (!TextUtils.isEmpty(a2)) {
                JSONObject jSONObject = new JSONObject(a2);
                long optLong = jSONObject.optLong("timestamp");
                int optInt = jSONObject.optInt("code");
                String optString = jSONObject.optString("device_id");
                if (optInt == 1) {
                    this.x = optString;
                    ab a3 = ab.a();
                    if (!TextUtils.isEmpty(this.x)) {
                        a3.b(u, optString);
                        a3.b(v, optLong);
                    }
                    r.a(optLong);
                    return this.x;
                }
            }
        } catch (IOException e2) {
            k.b(f506c, "[getDeviceIdLocal IOException]:", e2);
        } catch (JSONException e3) {
            k.b(f506c, "[getDeviceIdLocal JSONException]:", e3);
        }
        return this.x;
    }

    private String[] g() {
        return new String[]{e.b(this.y), e.e(this.y), e.h(this.y), e.k(this.y), e.n(this.y), e.q(this.y), e.p(this.y), f.b(this.y)};
    }

    private HashMap<String, String> h() {
        HashMap<String, String> hashMap = new HashMap<>();
        String[] g2 = g();
        JSONObject jSONObject = new JSONObject();
        try {
            jSONObject.put("ia", g2[0]);
            jSONObject.put("ib", g2[1]);
            jSONObject.put("md", g2[2]);
            jSONObject.put("mm", g2[3]);
            jSONObject.put("bm", g2[4]);
            jSONObject.put("aa", g2[5]);
            jSONObject.put("ai", g2[6]);
            jSONObject.put(k, g2[7]);
        } catch (JSONException e2) {
            e2.printStackTrace();
        }
        k.b(f506c, "[pay-load]:" + jSONObject.toString());
        byte[] bArr = new byte[0];
        try {
            bArr = i.a().a(jSONObject.toString().getBytes("utf-8"));
        } catch (UnsupportedEncodingException e3) {
            e3.printStackTrace();
        }
        String str = null;
        if (bArr != null) {
            str = com.xiaomi.stat.d.d.a(g.a(bArr, true).getBytes());
        }
        hashMap.put(com.xiaomi.stat.d.f572b, a.g);
        if (str == null) {
            str = "";
        }
        hashMap.put("p", str);
        hashMap.put("ai", ak.b());
        hashMap.put("gzip", "0");
        hashMap.put(com.xiaomi.stat.d.ak, i.a().c());
        hashMap.put(com.xiaomi.stat.d.g, i.a().b());
        return hashMap;
    }

    public String a(boolean z) {
        if (z) {
            return e.d();
        }
        String r2 = e.r(ak.a());
        return !TextUtils.isEmpty(r2) ? r2 : e.d();
    }

    public synchronized String b() {
        if (b.e()) {
            this.x = e.d();
        } else if (!d()) {
            e();
        }
        return this.x;
    }

    public String c() {
        return TextUtils.isEmpty(this.x) ? f() : this.x;
    }

    public boolean d() {
        String a2 = ab.a().a(u, (String) null);
        return !TextUtils.isEmpty(a2) && !TextUtils.isEmpty(this.x) && this.x.equals(a2);
    }
}
