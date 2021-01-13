package com.xiaomi.stat.b;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import com.xiaomi.stat.d.k;

class b extends BroadcastReceiver {

    /* renamed from: a  reason: collision with root package name */
    final /* synthetic */ a f502a;

    b(a aVar) {
        this.f502a = aVar;
    }

    public void onReceive(Context context, Intent intent) {
        try {
            if (a.a(this.f502a) != 1) {
                context.unregisterReceiver(a.b(this.f502a));
            } else {
                e.a().execute(new c(this));
            }
        } catch (Exception e2) {
            k.d("ConfigManager", "mNetReceiver exception", e2);
        }
    }
}
