package com.miui.internal.variable;

import android.widget.PopupWindow;
import com.miui.internal.util.ClassProxy;
import miui.reflect.Method;
import miui.util.SoftReferenceSingleton;

public abstract class Android_Widget_PopupWindow_class extends ClassProxy<PopupWindow> implements IManagedClassProxy {
    protected static Method setLayoutInScreenEnabled;
    protected static Method setLayoutInsetDecor;

    public static class Factory extends AbsClassFactory {
        private static final SoftReferenceSingleton<Factory> INSTANCE = new SoftReferenceSingleton<Factory>() {
            /* class com.miui.internal.variable.Android_Widget_PopupWindow_class.Factory.AnonymousClass1 */

            /* access modifiers changed from: protected */
            @Override // miui.util.SoftReferenceSingleton
            public Factory createInstance() {
                return new Factory();
            }
        };
        private Android_Widget_PopupWindow_class Android_Widget_PopupWindow_class;

        private Factory() {
            this.Android_Widget_PopupWindow_class = (Android_Widget_PopupWindow_class) create("Android_Widget_PopupWindow_class");
        }

        public static Factory getInstance() {
            return INSTANCE.get();
        }

        @Override // com.miui.internal.variable.AbsClassFactory
        public Android_Widget_PopupWindow_class get() {
            return this.Android_Widget_PopupWindow_class;
        }
    }

    static {
        try {
            setLayoutInScreenEnabled = Method.of(PopupWindow.class, "setLayoutInScreenEnabled", "(Z)V");
            setLayoutInsetDecor = Method.of(PopupWindow.class, "setLayoutInsetDecor", "(Z)V");
        } catch (Exception e2) {
            VariableExceptionHandler.getInstance().onThrow("no such method", e2);
        }
    }

    public Android_Widget_PopupWindow_class() {
        super(PopupWindow.class);
    }

    @Override // com.miui.internal.variable.IManagedClassProxy
    public void buildProxy() {
    }

    public abstract void setLayoutInScreenEnabled(PopupWindow popupWindow, boolean z);

    public abstract void setLayoutInsetDecor(PopupWindow popupWindow, boolean z);
}
