package com.miui.internal.variable;

import miui.util.SoftReferenceSingleton;

public abstract class Android_Content_SyncStatusInfo_class {
    public static final String NAME = "android.content.SyncStatusInfo";

    public static class Factory extends AbsClassFactory {
        private static final SoftReferenceSingleton<Factory> INSTANCE = new SoftReferenceSingleton<Factory>() {
            /* class com.miui.internal.variable.Android_Content_SyncStatusInfo_class.Factory.AnonymousClass1 */

            /* access modifiers changed from: protected */
            @Override // miui.util.SoftReferenceSingleton
            public Factory createInstance() {
                return new Factory();
            }
        };
        private Android_Content_SyncStatusInfo_class Android_Content_SyncStatusInfo_class;

        private Factory() {
            this.Android_Content_SyncStatusInfo_class = (Android_Content_SyncStatusInfo_class) create("Android_Content_SyncStatusInfo_class");
        }

        public static Factory getInstance() {
            return INSTANCE.get();
        }

        @Override // com.miui.internal.variable.AbsClassFactory
        public Android_Content_SyncStatusInfo_class get() {
            return this.Android_Content_SyncStatusInfo_class;
        }
    }

    public abstract boolean getInitialize(Object obj);

    public abstract int getLastFailureMesgAsInt(Object obj, int i);

    public abstract long getLastFailureTime(Object obj);

    public abstract long getLastSuccessTime(Object obj);

    public abstract boolean getPending(Object obj);
}
