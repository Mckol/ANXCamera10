package com.xiaomi.stat.c;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

class h extends BroadcastReceiver {

    /* renamed from: a  reason: collision with root package name */
    final /* synthetic */ g f564a;

    h(g gVar) {
        this.f564a = gVar;
    }

    public void onReceive(Context context, Intent intent) {
        this.f564a.sendEmptyMessage(3);
    }
}
