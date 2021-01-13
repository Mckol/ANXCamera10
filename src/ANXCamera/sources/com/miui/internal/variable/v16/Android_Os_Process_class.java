package com.miui.internal.variable.v16;

import android.os.Process;
import com.miui.internal.variable.VariableExceptionHandler;
import miui.reflect.Method;

public class Android_Os_Process_class extends com.miui.internal.variable.Android_Os_Process_class {
    private static final Method getFreeMemory = Method.of(Process.class, "getFreeMemory", "()J");
    private static final Method getTotalMemory = Method.of(Process.class, "getTotalMemory", "()J");

    @Override // com.miui.internal.variable.Android_Os_Process_class
    public long getFreeMemory() {
        try {
            return getFreeMemory.invokeLong(null, null, new Object[0]);
        } catch (RuntimeException e2) {
            VariableExceptionHandler.getInstance().onThrow("android.os.Process.getFreeMemory", e2);
            return 0;
        }
    }

    @Override // com.miui.internal.variable.Android_Os_Process_class
    public long getTotalMemory() {
        try {
            return getTotalMemory.invokeLong(null, null, new Object[0]);
        } catch (RuntimeException e2) {
            VariableExceptionHandler.getInstance().onThrow("android.os.Process.getTotalMemory", e2);
            return 0;
        }
    }
}
