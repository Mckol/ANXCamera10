package com.miui.internal.variable;

import miui.util.SoftReferenceSingleton;

public abstract class Android_Os_Process_class {

    public static class Factory extends AbsClassFactory {
        private static final SoftReferenceSingleton<Factory> INSTANCE = new SoftReferenceSingleton<Factory>() {
            /* class com.miui.internal.variable.Android_Os_Process_class.Factory.AnonymousClass1 */

            /* access modifiers changed from: protected */
            @Override // miui.util.SoftReferenceSingleton
            public Factory createInstance() {
                return new Factory();
            }
        };
        private Android_Os_Process_class Android_Os_Process_class;

        private Factory() {
            this.Android_Os_Process_class = (Android_Os_Process_class) create("Android_Os_Process_class");
        }

        public static Factory getInstance() {
            return INSTANCE.get();
        }

        @Override // com.miui.internal.variable.AbsClassFactory
        public Android_Os_Process_class get() {
            return this.Android_Os_Process_class;
        }
    }

    public abstract long getFreeMemory();

    public abstract long getTotalMemory();
}
