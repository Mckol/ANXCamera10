package com.miui.internal.variable;

import android.os.Build;
import java.util.Locale;

public abstract class AbsClassFactory {
    protected static final int CURRENT_SUPPORT_SDK_VERSION = Build.VERSION.SDK_INT;
    protected static final int MIN_SUPPORT_SDK_VERSION = 16;

    /* access modifiers changed from: protected */
    public Object create(String str) {
        int i = CURRENT_SUPPORT_SDK_VERSION;
        while (i >= 16) {
            String format = String.format(Locale.US, "com.miui.internal.variable.v%d.%s", Integer.valueOf(i), str);
            try {
                return Class.forName(format).newInstance();
            } catch (ClassNotFoundException unused) {
            } catch (Exception e2) {
                VariableExceptionHandler.getInstance().onThrow(format, e2);
            }
        }
        VariableExceptionHandler.getInstance().onThrow(str, new ClassNotFoundException("Cannot found class " + str));
        return null;
        i--;
    }

    public abstract Object get();
}
