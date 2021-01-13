package com.xiaomi.stat.c;

import android.os.IBinder;
import android.os.RemoteException;
import com.xiaomi.a.a.a.a;
import com.xiaomi.stat.b;
import com.xiaomi.stat.d.k;

class e implements Runnable {

    /* renamed from: a  reason: collision with root package name */
    final /* synthetic */ IBinder f552a;

    /* renamed from: b  reason: collision with root package name */
    final /* synthetic */ d f553b;

    e(d dVar, IBinder iBinder) {
        this.f553b = dVar;
        this.f552a = iBinder;
    }

    public void run() {
        a a2 = a.AbstractBinderC0006a.a(this.f552a);
        try {
            if (!b.e()) {
                this.f553b.f549a[0] = a2.a(this.f553b.f550b, this.f553b.f551c);
            } else if (b.x()) {
                this.f553b.f549a[0] = a2.b(this.f553b.f550b, this.f553b.f551c);
            } else {
                this.f553b.f549a[0] = null;
            }
            k.b("UploadMode", " connected, do remote http post " + this.f553b.f549a[0]);
            synchronized (i.class) {
                try {
                    i.class.notify();
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
            }
        } catch (RemoteException e3) {
            k.e("UploadMode", " error while uploading the data by IPC." + e3.toString());
            this.f553b.f549a[0] = null;
        }
    }
}
