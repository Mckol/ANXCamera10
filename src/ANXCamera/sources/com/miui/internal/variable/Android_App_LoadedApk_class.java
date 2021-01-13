package com.miui.internal.variable;

import android.content.pm.ApplicationInfo;
import android.content.res.Resources;
import miui.util.SoftReferenceSingleton;

public interface Android_App_LoadedApk_class {

    public static class Factory extends AbsClassFactory {
        private static final SoftReferenceSingleton<Factory> INSTANCE = new SoftReferenceSingleton<Factory>() {
            /* class com.miui.internal.variable.Android_App_LoadedApk_class.Factory.AnonymousClass1 */

            /* access modifiers changed from: protected */
            @Override // miui.util.SoftReferenceSingleton
            public Factory createInstance() {
                return new Factory();
            }
        };
        private Android_App_LoadedApk_class Android_App_LoadedApk_class;

        private Factory() {
            this.Android_App_LoadedApk_class = (Android_App_LoadedApk_class) create("Android_App_LoadedApk_class");
        }

        public static Factory getInstance() {
            return INSTANCE.get();
        }

        @Override // com.miui.internal.variable.AbsClassFactory
        public Android_App_LoadedApk_class get() {
            return this.Android_App_LoadedApk_class;
        }
    }

    ApplicationInfo getApplicationInfo(Object obj);

    Resources getResources(Object obj);
}
