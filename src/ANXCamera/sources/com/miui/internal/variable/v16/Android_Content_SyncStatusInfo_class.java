package com.miui.internal.variable.v16;

import com.miui.internal.variable.VariableExceptionHandler;
import miui.reflect.Field;
import miui.reflect.Method;

public class Android_Content_SyncStatusInfo_class extends com.miui.internal.variable.Android_Content_SyncStatusInfo_class {
    private static final Class<?> CLASS;
    private static final Method GET_LAST_FAILURE_MESG_AS_INT = Method.of(CLASS, "getLastFailureMesgAsInt", "(I)I");
    private static final Field INITIALIZE = Field.of(CLASS, "initialize", Field.BOOLEAN_SIGNATURE_PRIMITIVE);
    private static final Field LAST_FAILURE_TIME = Field.of(CLASS, "lastFailureTime", Field.LONG_SIGNATURE_PRIMITIVE);
    private static final Field LAST_SUCCESS_TIME = Field.of(CLASS, "lastSuccessTime", Field.LONG_SIGNATURE_PRIMITIVE);
    private static final Field PENDING = Field.of(CLASS, "pending", Field.BOOLEAN_SIGNATURE_PRIMITIVE);

    static {
        Class<?> cls;
        try {
            cls = Class.forName(com.miui.internal.variable.Android_Content_SyncStatusInfo_class.NAME);
        } catch (ClassNotFoundException e2) {
            VariableExceptionHandler.getInstance().onThrow(com.miui.internal.variable.Android_Content_SyncStatusInfo_class.NAME, e2);
            cls = null;
        }
        CLASS = cls;
    }

    @Override // com.miui.internal.variable.Android_Content_SyncStatusInfo_class
    public boolean getInitialize(Object obj) {
        try {
            return INITIALIZE.getBoolean(obj);
        } catch (RuntimeException e2) {
            VariableExceptionHandler.getInstance().onThrow("android.content.SyncStatusInfo.initialize", e2);
            return false;
        }
    }

    @Override // com.miui.internal.variable.Android_Content_SyncStatusInfo_class
    public int getLastFailureMesgAsInt(Object obj, int i) {
        try {
            return GET_LAST_FAILURE_MESG_AS_INT.invokeInt(null, obj, Integer.valueOf(i));
        } catch (RuntimeException e2) {
            VariableExceptionHandler.getInstance().onThrow("android.content.SyncStatusInfo.getLastFailureMesgAsInt", e2);
            return i;
        }
    }

    @Override // com.miui.internal.variable.Android_Content_SyncStatusInfo_class
    public long getLastFailureTime(Object obj) {
        try {
            return LAST_FAILURE_TIME.getLong(obj);
        } catch (RuntimeException e2) {
            VariableExceptionHandler.getInstance().onThrow("android.content.SyncStatusInfo.lastFailureTime", e2);
            return 0;
        }
    }

    @Override // com.miui.internal.variable.Android_Content_SyncStatusInfo_class
    public long getLastSuccessTime(Object obj) {
        try {
            return LAST_SUCCESS_TIME.getLong(obj);
        } catch (RuntimeException e2) {
            VariableExceptionHandler.getInstance().onThrow("android.content.SyncStatusInfo.lastSuccessTime", e2);
            return 0;
        }
    }

    @Override // com.miui.internal.variable.Android_Content_SyncStatusInfo_class
    public boolean getPending(Object obj) {
        try {
            return PENDING.getBoolean(obj);
        } catch (RuntimeException e2) {
            VariableExceptionHandler.getInstance().onThrow("android.content.SyncStatusInfo.pending", e2);
            return false;
        }
    }
}
