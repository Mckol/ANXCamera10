package com.miui.internal.variable;

import android.content.res.AssetManager;
import android.content.res.ResourcesImpl;
import com.miui.internal.util.ClassProxy;

public abstract class Android_Content_Res_ResourcesImpl_class extends ClassProxy<ResourcesImpl> implements IManagedClassProxy {

    public static class Factory extends AbsClassFactory {
        private Android_Content_Res_ResourcesImpl_class Android_Content_Res_ResourcesImpl_class;

        /* access modifiers changed from: private */
        public static final class Holder {
            static final Factory INSTANCE = new Factory();

            private Holder() {
            }
        }

        private Factory() {
            this.Android_Content_Res_ResourcesImpl_class = (Android_Content_Res_ResourcesImpl_class) create("Android_Content_Res_ResourcesImpl_class");
        }

        public static Factory getInstance() {
            return Holder.INSTANCE;
        }

        @Override // com.miui.internal.variable.AbsClassFactory
        public Android_Content_Res_ResourcesImpl_class get() {
            return this.Android_Content_Res_ResourcesImpl_class;
        }
    }

    public Android_Content_Res_ResourcesImpl_class() {
        super(ResourcesImpl.class);
    }

    public abstract AssetManager getAssets(ResourcesImpl resourcesImpl);
}
