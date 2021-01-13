package com.xiaomi.stat;

import android.os.Handler;
import android.os.HandlerThread;
import android.os.Message;
import com.xiaomi.stat.d.r;

public class ah {

    /* renamed from: a  reason: collision with root package name */
    public static final int f479a = 1;

    /* renamed from: b  reason: collision with root package name */
    private static final int f480b = 10000;

    /* renamed from: c  reason: collision with root package name */
    private static final int f481c = 3;

    /* renamed from: d  reason: collision with root package name */
    private Handler f482d;

    /* renamed from: e  reason: collision with root package name */
    private Runnable f483e;
    private HandlerThread f;
    private int g = 3;
    private int h = 10000;
    private int i = 0;
    private boolean j = false;

    /* access modifiers changed from: package-private */
    public class a implements Handler.Callback {

        /* renamed from: b  reason: collision with root package name */
        private Handler f485b;

        private a() {
            this.f485b = null;
        }

        /* access modifiers changed from: private */
        /* access modifiers changed from: public */
        private void a(Handler handler) {
            this.f485b = handler;
        }

        public boolean handleMessage(Message message) {
            if (message.what == 1) {
                int intValue = ((Integer) message.obj).intValue();
                if (intValue < ah.this.g) {
                    ah.this.f483e.run();
                    if (ah.this.j) {
                        Message obtainMessage = this.f485b.obtainMessage(1);
                        obtainMessage.obj = Integer.valueOf(intValue + 1);
                        this.f485b.sendMessageDelayed(obtainMessage, (long) ah.this.h);
                    }
                } else {
                    ah.this.b();
                }
            }
            return true;
        }
    }

    public ah(Runnable runnable) {
        this.f483e = runnable;
    }

    private void d() {
        a aVar = new a();
        this.f = new HandlerThread("".concat("_").concat(String.valueOf(r.b())));
        this.f.start();
        this.f482d = new Handler(this.f.getLooper(), aVar);
        aVar.a(this.f482d);
    }

    public void a() {
        Handler handler = this.f482d;
        if (handler == null || !handler.hasMessages(1)) {
            d();
            Message obtainMessage = this.f482d.obtainMessage(1);
            obtainMessage.obj = 0;
            this.j = true;
            this.f482d.sendMessageDelayed(obtainMessage, (long) this.i);
        }
    }

    public void a(int i2) {
        this.i = i2;
    }

    public void b() {
        this.f482d.removeMessages(1);
        this.f482d.getLooper().quit();
        this.j = false;
    }

    public void b(int i2) {
        this.g = i2;
    }

    public void c(int i2) {
        this.h = i2;
    }

    public boolean c() {
        return this.j;
    }
}
