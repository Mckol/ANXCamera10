package com.miui.internal.variable;

import android.content.res.AssetManager;
import android.content.res.Resources;
import com.miui.internal.util.ClassProxy;
import com.miui.internal.variable.Android_Graphics_Drawable_Drawable_class;
import miui.reflect.Field;
import miui.util.SoftReferenceSingleton;

public abstract class Android_Content_Res_Resources_class extends ClassProxy<Resources> implements IManagedClassProxy {
    protected static final Android_Graphics_Drawable_Drawable_class DrawableClass = Android_Graphics_Drawable_Drawable_class.Factory.getInstance().get();
    protected static Field mAssets = null;

    public static class Factory extends AbsClassFactory {
        private static final SoftReferenceSingleton<Factory> INSTANCE = new SoftReferenceSingleton<Factory>() {
            /* class com.miui.internal.variable.Android_Content_Res_Resources_class.Factory.AnonymousClass1 */

            /* access modifiers changed from: protected */
            @Override // miui.util.SoftReferenceSingleton
            public Factory createInstance() {
                return new Factory();
            }
        };
        private Android_Content_Res_Resources_class Android_Content_Res_Resources_class;

        private Factory() {
            this.Android_Content_Res_Resources_class = (Android_Content_Res_Resources_class) create("Android_Content_Res_Resources_class");
        }

        public static Factory getInstance() {
            return INSTANCE.get();
        }

        @Override // com.miui.internal.variable.AbsClassFactory
        public Android_Content_Res_Resources_class get() {
            return this.Android_Content_Res_Resources_class;
        }
    }

    public Android_Content_Res_Resources_class() {
        super(Resources.class);
    }

    public abstract void setAssetManager(Resources resources, AssetManager assetManager);
}
