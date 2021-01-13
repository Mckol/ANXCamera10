package com.airbnb.lottie.d;

import com.airbnb.lottie.U;

/* compiled from: Logger */
public class d {
    private static U INSTANCE = new c();

    public static void a(U u) {
        INSTANCE = u;
    }

    public static void b(String str, Throwable th) {
        INSTANCE.b(str, th);
    }

    public static void c(String str, Throwable th) {
        INSTANCE.c(str, th);
    }

    public static void debug(String str) {
        INSTANCE.debug(str);
    }

    public static void f(String str, Throwable th) {
        INSTANCE.f(str, th);
    }

    public static void warning(String str) {
        INSTANCE.warning(str);
    }
}
