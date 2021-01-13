package com.xiaomi.stat;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;
import android.os.SystemClock;

/* access modifiers changed from: package-private */
public class r implements Application.ActivityLifecycleCallbacks {

    /* renamed from: a  reason: collision with root package name */
    final /* synthetic */ e f692a;

    /* renamed from: b  reason: collision with root package name */
    private int f693b;

    r(e eVar) {
        this.f692a = eVar;
    }

    public void onActivityCreated(Activity activity, Bundle bundle) {
    }

    public void onActivityDestroyed(Activity activity) {
    }

    public void onActivityPaused(Activity activity) {
        e.j(this.f692a);
        if (this.f693b == System.identityHashCode(activity)) {
            long elapsedRealtime = SystemClock.elapsedRealtime() - this.f692a.f;
            long j = this.f692a.d();
            this.f692a.a((e) activity.getClass().getName(), (String) (j - elapsedRealtime), j);
            this.f692a.h();
        }
    }

    public void onActivityResumed(Activity activity) {
        e.h(this.f692a);
        this.f693b = System.identityHashCode(activity);
        this.f692a.f = SystemClock.elapsedRealtime();
        this.f692a.h();
    }

    public void onActivitySaveInstanceState(Activity activity, Bundle bundle) {
    }

    public void onActivityStarted(Activity activity) {
        if (this.f692a.i == 0) {
            this.f692a.l = SystemClock.elapsedRealtime();
            this.f692a.j = 0;
            this.f692a.k = 0;
            this.f692a.f663e.execute(new s(this));
        }
        e.g(this.f692a);
    }

    public void onActivityStopped(Activity activity) {
        e.m(this.f692a);
        if (this.f692a.i == 0) {
            long elapsedRealtime = SystemClock.elapsedRealtime() - this.f692a.l;
            long b2 = com.xiaomi.stat.d.r.b();
            e eVar = this.f692a;
            eVar.a((e) eVar.j, this.f692a.k, (int) (b2 - elapsedRealtime), b2);
            this.f692a.f663e.execute(new t(this));
        }
    }
}
