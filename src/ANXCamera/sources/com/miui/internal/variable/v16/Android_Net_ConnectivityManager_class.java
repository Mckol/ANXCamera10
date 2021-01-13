package com.miui.internal.variable.v16;

import android.net.ConnectivityManager;
import com.miui.internal.variable.VariableExceptionHandler;
import miui.reflect.Method;

public class Android_Net_ConnectivityManager_class extends com.miui.internal.variable.Android_Net_ConnectivityManager_class {
    private static final Method isNetworkSupported = Method.of(ConnectivityManager.class, "isNetworkSupported", "(I)Z");

    @Override // com.miui.internal.variable.Android_Net_ConnectivityManager_class
    public boolean isNetworkSupported(ConnectivityManager connectivityManager, int i) {
        try {
            return isNetworkSupported.invokeBoolean(null, connectivityManager, Integer.valueOf(i));
        } catch (RuntimeException e2) {
            VariableExceptionHandler.getInstance().onThrow("android.os.Process.getTotalMemory", e2);
            return false;
        }
    }
}
