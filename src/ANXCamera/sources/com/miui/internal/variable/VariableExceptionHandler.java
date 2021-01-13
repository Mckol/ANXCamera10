package com.miui.internal.variable;

import android.util.Log;
import java.lang.reflect.InvocationTargetException;

public class VariableExceptionHandler {
    private static final String TAG = "ExceptionHandler";

    /* access modifiers changed from: private */
    public static class Holder {
        static final VariableExceptionHandler INSTANCE = new VariableExceptionHandler();

        private Holder() {
        }
    }

    public static VariableExceptionHandler getInstance() {
        return Holder.INSTANCE;
    }

    public void onThrow(String str, Throwable th) {
        String str2;
        if (th instanceof InvocationTargetException) {
            th = th.getCause();
        }
        Log.e(TAG, "Something thrown - " + str, th);
        if (str == null || str.length() == 0) {
            str2 = "Something thrown when using version/device dependent code";
        } else {
            str2 = "Something thrown when using version/device dependent code - " + str;
        }
        throw new RuntimeException(str2 + " - " + th.getMessage(), th);
    }
}
