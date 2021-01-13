package com.miui.internal.variable.v19;

import android.view.accessibility.AccessibilityEvent;
import miui.reflect.Method;

public class Android_View_Accessibility_AccessibilityEvent_class extends com.miui.internal.variable.v16.Android_View_Accessibility_AccessibilityEvent_class {
    protected static Method setContentChangeTypes;

    static {
        try {
            setContentChangeTypes = Method.of(AccessibilityEvent.class, "setContentChangeTypes", "(I)V");
        } catch (Exception unused) {
            setContentChangeTypes = null;
        }
    }

    @Override // com.miui.internal.variable.v16.Android_View_Accessibility_AccessibilityEvent_class, com.miui.internal.variable.Android_View_Accessibility_AccessibilityEvent_class
    public void setContentChangeTypes(AccessibilityEvent accessibilityEvent, int i) {
        Method method = setContentChangeTypes;
        if (method != null) {
            method.invoke(null, accessibilityEvent, Integer.valueOf(i));
        }
    }
}
