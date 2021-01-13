package com.miui.internal.variable;

import android.view.Window;
import miui.reflect.Method;

public abstract class Android_View_Window_class {
    protected static Method setExtraFlags;

    public static class Factory extends AbsClassFactory {
        private Android_View_Window_class Android_View_Window_class;

        /* access modifiers changed from: private */
        public static final class Holder {
            static final Factory INSTANCE = new Factory();

            private Holder() {
            }
        }

        private Factory() {
            this.Android_View_Window_class = (Android_View_Window_class) create("Android_View_Window_class");
        }

        public static Factory getInstance() {
            return Holder.INSTANCE;
        }

        @Override // com.miui.internal.variable.AbsClassFactory
        public Android_View_Window_class get() {
            return this.Android_View_Window_class;
        }
    }

    static {
        try {
            setExtraFlags = Method.of(Window.class, "setExtraFlags", "(II)V");
        } catch (Exception unused) {
            setExtraFlags = null;
        }
    }

    public abstract boolean setTranslucentStatus(Window window, int i);
}
