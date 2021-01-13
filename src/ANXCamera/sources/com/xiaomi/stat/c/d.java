package com.xiaomi.stat.c;

import android.content.ComponentName;
import android.content.ServiceConnection;
import android.os.IBinder;
import com.xiaomi.stat.b.e;
import com.xiaomi.stat.d.k;
import java.util.Map;

/* access modifiers changed from: package-private */
public final class d implements ServiceConnection {

    /* renamed from: a  reason: collision with root package name */
    final /* synthetic */ String[] f549a;

    /* renamed from: b  reason: collision with root package name */
    final /* synthetic */ String f550b;

    /* renamed from: c  reason: collision with root package name */
    final /* synthetic */ Map f551c;

    d(String[] strArr, String str, Map map) {
        this.f549a = strArr;
        this.f550b = str;
        this.f551c = map;
    }

    /* JADX WARNING: Exception block dominator not found, dom blocks: [] */
    /* JADX WARNING: Missing exception handler attribute for start block: B:6:0x000b */
    public void onBindingDied(ComponentName componentName) {
        synchronized (i.class) {
            i.class.notify();
        }
    }

    public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
        e.a().execute(new e(this, iBinder));
    }

    /* JADX WARNING: Exception block dominator not found, dom blocks: [] */
    /* JADX WARNING: Missing exception handler attribute for start block: B:6:0x0021 */
    public void onServiceDisconnected(ComponentName componentName) {
        k.b("UploadMode", "onServiceDisconnected " + componentName);
        synchronized (i.class) {
            i.class.notify();
        }
    }
}
