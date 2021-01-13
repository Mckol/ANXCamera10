package com.xiaomi.stat;

import java.lang.Thread;

public class al implements Thread.UncaughtExceptionHandler {

    /* renamed from: a  reason: collision with root package name */
    private e f489a;

    /* renamed from: b  reason: collision with root package name */
    private Thread.UncaughtExceptionHandler f490b;

    /* renamed from: c  reason: collision with root package name */
    private boolean f491c = true;

    public al(e eVar) {
        this.f489a = eVar;
    }

    public void a() {
        Thread.UncaughtExceptionHandler defaultUncaughtExceptionHandler = Thread.getDefaultUncaughtExceptionHandler();
        if (!(defaultUncaughtExceptionHandler instanceof al)) {
            this.f490b = defaultUncaughtExceptionHandler;
            Thread.setDefaultUncaughtExceptionHandler(this);
        }
    }

    public void a(boolean z) {
        this.f491c = z;
    }

    public void uncaughtException(Thread thread, Throwable th) {
        if (this.f491c) {
            this.f489a.a(th, (String) null, false);
        }
        Thread.UncaughtExceptionHandler uncaughtExceptionHandler = this.f490b;
        if (uncaughtExceptionHandler != null) {
            uncaughtExceptionHandler.uncaughtException(thread, th);
        }
    }
}
