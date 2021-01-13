package com.miui.internal.variable;

import android.net.ConnectivityManager;
import miui.util.SoftReferenceSingleton;

public abstract class Android_Net_ConnectivityManager_class {

    public static class Factory extends AbsClassFactory {
        private static final SoftReferenceSingleton<Factory> INSTANCE = new SoftReferenceSingleton<Factory>() {
            /* class com.miui.internal.variable.Android_Net_ConnectivityManager_class.Factory.AnonymousClass1 */

            /* access modifiers changed from: protected */
            @Override // miui.util.SoftReferenceSingleton
            public Factory createInstance() {
                return new Factory();
            }
        };
        private Android_Net_ConnectivityManager_class Android_Net_ConnectivityManager_class;

        private Factory() {
            this.Android_Net_ConnectivityManager_class = (Android_Net_ConnectivityManager_class) create("Android_Net_ConnectivityManager_class");
        }

        public static Factory getInstance() {
            return INSTANCE.get();
        }

        @Override // com.miui.internal.variable.AbsClassFactory
        public Android_Net_ConnectivityManager_class get() {
            return this.Android_Net_ConnectivityManager_class;
        }
    }

    public abstract boolean isNetworkSupported(ConnectivityManager connectivityManager, int i);
}
