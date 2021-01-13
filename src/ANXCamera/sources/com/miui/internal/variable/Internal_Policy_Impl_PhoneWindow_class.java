package com.miui.internal.variable;

import android.os.Build;
import com.miui.internal.util.ClassProxy;

public abstract class Internal_Policy_Impl_PhoneWindow_class extends ClassProxy<Object> implements IManagedClassProxy {
    private static final String PhoneWindow_ClassName = (Build.VERSION.SDK_INT >= 23 ? "com.android.internal.policy.PhoneWindow" : "com.android.internal.policy.impl.PhoneWindow");
    protected static final Class<? extends Object> TARGET_CLASS = getTargetClass();

    public static class Factory extends AbsClassFactory {
        private Internal_Policy_Impl_PhoneWindow_class Internal_Policy_Impl_PhoneWindow_class;

        private static class Holder {
            static final Factory INSTANCE = new Factory();

            private Holder() {
            }
        }

        private Factory() {
            this.Internal_Policy_Impl_PhoneWindow_class = (Internal_Policy_Impl_PhoneWindow_class) create("Internal_Policy_Impl_PhoneWindow_class");
        }

        public static Factory getInstance() {
            return Holder.INSTANCE;
        }

        @Override // com.miui.internal.variable.AbsClassFactory
        public Internal_Policy_Impl_PhoneWindow_class get() {
            return this.Internal_Policy_Impl_PhoneWindow_class;
        }
    }

    public Internal_Policy_Impl_PhoneWindow_class() {
        super(TARGET_CLASS);
    }

    /* JADX DEBUG: Type inference failed for r0v3. Raw type applied. Possible types: java.lang.Class<?>, java.lang.Class<? extends java.lang.Object> */
    private static Class<? extends Object> getTargetClass() {
        try {
            return Class.forName(PhoneWindow_ClassName);
        } catch (ClassNotFoundException e2) {
            VariableExceptionHandler.getInstance().onThrow("com.android.internal.policy.impl.PhoneWindow", e2);
            return null;
        }
    }
}
