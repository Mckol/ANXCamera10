package com.ss.android.vesdk;

import androidx.lifecycle.GeneratedAdapter;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.MethodCallsLogger;

public class VEEditor_LifecycleAdapter implements GeneratedAdapter {
    final VEEditor mReceiver;

    VEEditor_LifecycleAdapter(VEEditor vEEditor) {
        this.mReceiver = vEEditor;
    }

    @Override // androidx.lifecycle.GeneratedAdapter
    public void callMethods(LifecycleOwner lifecycleOwner, Lifecycle.Event event, boolean z, MethodCallsLogger methodCallsLogger) {
        boolean z2 = methodCallsLogger != null;
        if (z || event != Lifecycle.Event.ON_DESTROY) {
            return;
        }
        if (!z2 || methodCallsLogger.approveCall("destroy", 1)) {
            this.mReceiver.destroy();
        }
    }
}
