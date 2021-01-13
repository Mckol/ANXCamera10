package com.miui.internal.util;

import miui.util.Log;

public abstract class ClassProxy<T> {
    public ClassProxy(Class<? extends T> cls) {
        onClassProxyDisabled();
    }

    private void addCookie(long j) {
    }

    public static String getProcName() {
        return "";
    }

    public static void setDebugModules(String[] strArr) {
    }

    public static boolean setupClassHook(int i, String str) {
        return true;
    }

    public static boolean setupInterpreterHook() {
        return true;
    }

    public static void setupResourceHook() {
    }

    /* access modifiers changed from: protected */
    public final long attachConstructor(String str) {
        return 0;
    }

    /* access modifiers changed from: protected */
    public final long attachMethod(String str, String str2) {
        return 0;
    }

    /* access modifiers changed from: protected */
    public final void detachMethod(long j) {
    }

    /* access modifiers changed from: protected */
    public final void dispose() {
    }

    /* access modifiers changed from: protected */
    public abstract void handle();

    /* access modifiers changed from: protected */
    public void onClassProxyDisabled() {
        Log.w("miuisdk", "no hook applied for class " + getClass().getName());
    }
}
