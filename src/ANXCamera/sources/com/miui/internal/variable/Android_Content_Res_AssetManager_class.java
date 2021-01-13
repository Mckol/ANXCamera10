package com.miui.internal.variable;

import android.content.res.AssetManager;
import miui.util.SoftReferenceSingleton;

public interface Android_Content_Res_AssetManager_class extends IManagedClassProxy {

    public static class Factory extends AbsClassFactory {
        private static final SoftReferenceSingleton<Factory> INSTANCE = new SoftReferenceSingleton<Factory>() {
            /* class com.miui.internal.variable.Android_Content_Res_AssetManager_class.Factory.AnonymousClass1 */

            /* access modifiers changed from: protected */
            @Override // miui.util.SoftReferenceSingleton
            public Factory createInstance() {
                return new Factory();
            }
        };
        private Android_Content_Res_AssetManager_class Android_Content_Res_AssetManager_class;

        private Factory() {
            this.Android_Content_Res_AssetManager_class = (Android_Content_Res_AssetManager_class) create("Android_Content_Res_AssetManager_class");
        }

        public static Factory getInstance() {
            return INSTANCE.get();
        }

        @Override // com.miui.internal.variable.AbsClassFactory
        public Android_Content_Res_AssetManager_class get() {
            return this.Android_Content_Res_AssetManager_class;
        }
    }

    int addAssetPath(AssetManager assetManager, String str);

    AssetManager newInstance();
}
