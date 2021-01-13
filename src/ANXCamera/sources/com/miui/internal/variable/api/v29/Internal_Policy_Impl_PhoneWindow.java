package com.miui.internal.variable.api.v29;

import com.miui.internal.variable.api.AbstractExtension;

public interface Internal_Policy_Impl_PhoneWindow {

    public static class Extension extends AbstractExtension<Interface> {
        private static final Extension INSTANCE = new Extension();

        public static Extension get() {
            return INSTANCE;
        }
    }

    public interface Interface {
        void installDecor(Object obj);
    }
}
