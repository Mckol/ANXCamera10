package com.miui.internal.variable;

import android.view.accessibility.AccessibilityEvent;
import miui.util.SoftReferenceSingleton;

public abstract class Android_View_Accessibility_AccessibilityEvent_class {

    public static class Factory extends AbsClassFactory {
        private static final SoftReferenceSingleton<Factory> INSTANCE = new SoftReferenceSingleton<Factory>() {
            /* class com.miui.internal.variable.Android_View_Accessibility_AccessibilityEvent_class.Factory.AnonymousClass1 */

            /* access modifiers changed from: protected */
            @Override // miui.util.SoftReferenceSingleton
            public Factory createInstance() {
                return new Factory();
            }
        };
        private Android_View_Accessibility_AccessibilityEvent_class Android_View_Accessibility_AccessibilityEvent_class;

        private Factory() {
            this.Android_View_Accessibility_AccessibilityEvent_class = (Android_View_Accessibility_AccessibilityEvent_class) create("Android_View_Accessibility_AccessibilityEvent_class");
        }

        public static Factory getInstance() {
            return INSTANCE.get();
        }

        @Override // com.miui.internal.variable.AbsClassFactory
        public Android_View_Accessibility_AccessibilityEvent_class get() {
            return this.Android_View_Accessibility_AccessibilityEvent_class;
        }
    }

    public abstract void setContentChangeTypes(AccessibilityEvent accessibilityEvent, int i);
}
