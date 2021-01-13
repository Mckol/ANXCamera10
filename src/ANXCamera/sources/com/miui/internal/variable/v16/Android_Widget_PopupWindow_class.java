package com.miui.internal.variable.v16;

import android.widget.PopupWindow;
import com.miui.internal.variable.VariableExceptionHandler;
import miui.reflect.Method;

public class Android_Widget_PopupWindow_class extends com.miui.internal.variable.Android_Widget_PopupWindow_class {
    /* access modifiers changed from: protected */
    @Override // com.miui.internal.util.ClassProxy
    public void handle() {
    }

    @Override // com.miui.internal.variable.Android_Widget_PopupWindow_class
    public void setLayoutInScreenEnabled(PopupWindow popupWindow, boolean z) {
        Method method = com.miui.internal.variable.Android_Widget_PopupWindow_class.setLayoutInScreenEnabled;
        if (method != null) {
            try {
                method.invoke(null, popupWindow, Boolean.valueOf(z));
            } catch (Exception e2) {
                VariableExceptionHandler.getInstance().onThrow("invoke setLayoutInScreenEnabled failed", e2);
            }
        }
    }

    @Override // com.miui.internal.variable.Android_Widget_PopupWindow_class
    public void setLayoutInsetDecor(PopupWindow popupWindow, boolean z) {
        Method method = com.miui.internal.variable.Android_Widget_PopupWindow_class.setLayoutInsetDecor;
        if (method != null) {
            try {
                method.invoke(null, popupWindow, Boolean.valueOf(z));
            } catch (Exception e2) {
                VariableExceptionHandler.getInstance().onThrow("invoke setLayoutInsetDecor failed", e2);
            }
        }
    }
}
