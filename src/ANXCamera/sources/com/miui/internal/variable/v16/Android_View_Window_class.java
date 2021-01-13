package com.miui.internal.variable.v16;

import android.view.Window;
import com.miui.internal.variable.VariableExceptionHandler;
import miui.reflect.Method;

public class Android_View_Window_class extends com.miui.internal.variable.Android_View_Window_class {
    private static final int EXTRA_FLAG_STATUS_BAR_DARK_MODE = 16;
    private static final int EXTRA_FLAG_STATUS_BAR_TRANSPARENT = 1;
    private static final int EXTRA_FLAG_STATUS_BAR_TRANSPARENT_MASK = 17;

    @Override // com.miui.internal.variable.Android_View_Window_class
    public boolean setTranslucentStatus(Window window, int i) {
        Method method = com.miui.internal.variable.Android_View_Window_class.setExtraFlags;
        if (method == null) {
            return false;
        }
        if (i == 0) {
            try {
                method.invoke(null, window, 0, 17);
            } catch (Exception e2) {
                VariableExceptionHandler.getInstance().onThrow("clearExtraFlags failed", e2);
                return false;
            }
        } else {
            try {
                com.miui.internal.variable.Android_View_Window_class.setExtraFlags.invoke(null, window, Integer.valueOf(i == 1 ? 17 : 1), 17);
            } catch (Exception e3) {
                VariableExceptionHandler.getInstance().onThrow("addExtraFlags failed", e3);
                return false;
            }
        }
        return true;
    }
}
