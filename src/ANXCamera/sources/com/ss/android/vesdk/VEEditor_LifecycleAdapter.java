package com.ss.android.vesdk;

import android.arch.lifecycle.GeneratedAdapter;
import android.arch.lifecycle.Lifecycle.Event;
import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.MethodCallsLogger;

public class VEEditor_LifecycleAdapter implements GeneratedAdapter {
    final VEEditor mReceiver;

    VEEditor_LifecycleAdapter(VEEditor vEEditor) {
        this.mReceiver = vEEditor;
    }

    public void callMethods(LifecycleOwner lifecycleOwner, Event event, boolean z, MethodCallsLogger methodCallsLogger) {
        boolean z2 = methodCallsLogger != null;
        if (!z && event == Event.ON_DESTROY && (!z2 || methodCallsLogger.approveCall("destroy", 1))) {
            this.mReceiver.destroy();
        }
    }
}
