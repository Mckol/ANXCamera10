package com.xiaomi.stat.b;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class e {

    /* access modifiers changed from: private */
    public static class a {

        /* renamed from: a  reason: collision with root package name */
        private static final ExecutorService f509a = Executors.newCachedThreadPool();

        private a() {
        }
    }

    private e() {
    }

    public static ExecutorService a() {
        return a.f509a;
    }
}
