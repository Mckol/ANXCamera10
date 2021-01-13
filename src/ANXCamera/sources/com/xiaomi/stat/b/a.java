package com.xiaomi.stat.b;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.IntentFilter;
import android.os.Build;
import android.text.TextUtils;
import com.xiaomi.stat.ab;
import com.xiaomi.stat.ak;
import com.xiaomi.stat.b;
import com.xiaomi.stat.d;
import com.xiaomi.stat.d.c;
import com.xiaomi.stat.d.k;
import com.xiaomi.stat.d.l;
import com.xiaomi.stat.d.m;
import com.xiaomi.stat.d.r;
import java.util.Map;
import java.util.Random;
import java.util.TreeMap;
import org.json.JSONObject;

public class a {

    /* renamed from: a  reason: collision with root package name */
    public static final int f497a = 1;

    /* renamed from: b  reason: collision with root package name */
    public static final int f498b = 2;

    /* renamed from: c  reason: collision with root package name */
    public static final int f499c = 4;

    /* renamed from: d  reason: collision with root package name */
    private static final String f500d = "ConfigManager";

    /* renamed from: e  reason: collision with root package name */
    private static final String f501e = "-";
    private static int f = 0;
    private static int g = 1;
    private static int h = 2;
    private static final String i = "config_request_count";
    private static final String j = "config_request_time";
    private static final String k = "config_success_requested";
    private static final int l = 1;
    private static final int m = 2;
    private static final int n = 0;
    private static final int o = -1;
    private static final int p = 0;
    private static final int q = 12;
    private static final String r = "t";
    private static final int s = 0;
    private static volatile a t;
    private int u = 0;
    private Context v = ak.a();
    private String w;
    private BroadcastReceiver x = new b(this);

    private a() {
    }

    static /* synthetic */ int a(a aVar) {
        return aVar.u;
    }

    public static a a() {
        if (t == null) {
            synchronized (a.class) {
                if (t == null) {
                    t = new a();
                }
            }
        }
        return t;
    }

    private void a(int i2) {
        if (i2 > 0) {
            try {
                Thread.sleep((long) (i2 * 1000));
            } catch (InterruptedException e2) {
                e2.printStackTrace();
            }
        }
        b();
    }

    private void a(String str) {
        try {
            k.b(f500d, String.format("config result:%s", str));
            d();
            if (!TextUtils.isEmpty(str)) {
                JSONObject jSONObject = new JSONObject(str);
                if (jSONObject.getInt("errorCode") == 0) {
                    long optLong = jSONObject.optLong("time", 0);
                    ab.a().b(k, optLong);
                    b.c(jSONObject.optString(b.i, b.n));
                    b.c(jSONObject.optInt(b.h, -1));
                    b.d(jSONObject.optInt(b.g, 0) / 1000);
                    b.h(jSONObject.optBoolean(b.k));
                    a(jSONObject);
                    r.a(optLong);
                    g.a().a(jSONObject);
                    if (this.u == 1) {
                        this.v.unregisterReceiver(this.x);
                    }
                    this.u = 2;
                }
            }
        } catch (Exception e2) {
            k.d(f500d, "processResult exception", e2);
        }
    }

    private void a(JSONObject jSONObject) {
        try {
            int optInt = jSONObject.optInt(b.l);
            if (optInt > 0) {
                boolean z = false;
                b.k((optInt & 1) == 1);
                b.i((optInt & 2) == 2);
                if ((optInt & 4) == 4) {
                    z = true;
                }
                b.j(z);
            }
        } catch (Exception e2) {
            k.e(f500d, "updateConfig: " + e2);
        }
    }

    static /* synthetic */ BroadcastReceiver b(a aVar) {
        return aVar.x;
    }

    private String b() {
        k.b(f500d, "requestConfigInner");
        this.w = g.a().b();
        if (k.b()) {
            this.w = k.f615a;
        }
        String str = null;
        try {
            TreeMap treeMap = new TreeMap();
            treeMap.put(r, String.valueOf(h));
            treeMap.put("ai", ak.b());
            treeMap.put("rc", m.h());
            treeMap.put(d.f574d, m.g());
            treeMap.put("m", Build.MODEL);
            treeMap.put(d.f572b, com.xiaomi.stat.a.g);
            treeMap.put(d.j, c.b());
            str = com.xiaomi.stat.c.c.a(this.w, (Map<String, String>) treeMap, false);
            a(str);
            return str;
        } catch (Exception e2) {
            k.b(f500d, "requestConfigInner exception ", e2);
            return str;
        }
    }

    private boolean c() {
        long b2 = r.b();
        ab a2 = ab.a();
        try {
            if (!ab.a().a(j)) {
                a2.b(j, b2);
                a2.b(i, 1);
                return false;
            } else if (r.b(a2.a(j, 0L))) {
                return a2.a(i, 0) >= 12;
            } else {
                a2.b(j, b2);
                a2.b(i, 0);
                return false;
            }
        } catch (Exception e2) {
            k.d(f500d, "isRequestCountReachMax exception", e2);
            return false;
        }
    }

    private void d() {
        try {
            ab a2 = ab.a();
            a2.b(i, a2.a(i, 0) + 1);
        } catch (Exception e2) {
            k.d(f500d, "addRequestCount exception", e2);
        }
    }

    public synchronized void a(boolean z, boolean z2) {
        int parseInt;
        int parseInt2;
        if (b.a()) {
            if (b.b()) {
                if (!l.a()) {
                    k.b(f500d, "network is not connected!");
                    try {
                        IntentFilter intentFilter = new IntentFilter();
                        intentFilter.addAction("android.net.conn.CONNECTIVITY_CHANGE");
                        this.v.registerReceiver(this.x, intentFilter);
                        this.u = 1;
                    } catch (Exception e2) {
                        k.e(f500d, "updateConfig registerReceiver error:" + e2);
                    }
                    return;
                }
                k.b(f500d, "updateConfig");
                if (!z2) {
                    k.b("MI_STAT_TEST", "updateConfig-InToday");
                    if (r.b(ab.a().a(k, 0L))) {
                        k.b(f500d, "Today has successfully requested key.");
                        return;
                    } else if (c()) {
                        k.d(f500d, "config request to max count skip..");
                        return;
                    }
                }
                int i2 = 0;
                if (!z || z2) {
                    a(0);
                } else {
                    String[] split = b.k().split(f501e);
                    if (split.length > 1 && (parseInt2 = Integer.parseInt(split[1])) > (parseInt = Integer.parseInt(split[0]))) {
                        i2 = new Random().nextInt(parseInt2 - parseInt) + parseInt;
                    }
                    a(i2);
                }
                return;
            }
        }
        k.c(f500d, "update abort: statistic or network is not enabled");
    }
}
