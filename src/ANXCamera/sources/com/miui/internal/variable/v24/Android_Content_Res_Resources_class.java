package com.miui.internal.variable.v24;

import android.content.res.AssetManager;
import android.content.res.Resources;
import com.miui.internal.variable.VariableExceptionHandler;
import miui.reflect.Field;

public class Android_Content_Res_Resources_class extends com.miui.internal.variable.v21.Android_Content_Res_Resources_class {
    protected static Field mResourcesImpl;

    @Override // com.miui.internal.variable.v16.Android_Content_Res_Resources_class, com.miui.internal.variable.v21.Android_Content_Res_Resources_class, com.miui.internal.variable.IManagedClassProxy
    public void buildProxy() {
    }

    /* access modifiers changed from: protected */
    @Override // com.miui.internal.variable.v16.Android_Content_Res_Resources_class, com.miui.internal.variable.v21.Android_Content_Res_Resources_class, com.miui.internal.util.ClassProxy
    public void handle() {
    }

    @Override // com.miui.internal.variable.v16.Android_Content_Res_Resources_class, com.miui.internal.variable.Android_Content_Res_Resources_class
    public void setAssetManager(Resources resources, AssetManager assetManager) {
        try {
            if (mResourcesImpl == null) {
                mResourcesImpl = Field.of(Resources.class, "mResourcesImpl", "Landroid/content/res/ResourcesImpl;");
                com.miui.internal.variable.Android_Content_Res_Resources_class.mAssets = Field.of(Class.forName("android/content/res/ResourcesImpl"), "mAssets", "Landroid/content/res/AssetManager;");
            }
            com.miui.internal.variable.Android_Content_Res_Resources_class.mAssets.set(mResourcesImpl.get(resources), assetManager);
        } catch (RuntimeException e2) {
            VariableExceptionHandler.getInstance().onThrow("mAssets", e2);
        } catch (ClassNotFoundException e3) {
            VariableExceptionHandler.getInstance().onThrow("mAssets", e3);
        }
    }
}
