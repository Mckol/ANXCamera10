package com.miui.internal.variable.v24;

import android.content.res.AssetManager;
import android.content.res.ResourcesImpl;
import android.content.res.ResourcesKey;
import com.miui.internal.variable.Android_Content_Res_AssetManager_class;
import com.miui.internal.variable.Android_Content_Res_ResourcesImpl_class;
import com.miui.internal.variable.api.Overridable;
import com.miui.internal.variable.api.v29.Android_App_ResourcesManager;

public class Android_App_ResourcesManager_class extends com.miui.internal.variable.Android_App_ResourcesManager_class implements Overridable<Android_App_ResourcesManager.Interface> {
    private static final Android_Content_Res_AssetManager_class AssetManagerClass = Android_Content_Res_AssetManager_class.Factory.getInstance().get();
    private static final Android_Content_Res_ResourcesImpl_class ResourcesImplClass = Android_Content_Res_ResourcesImpl_class.Factory.getInstance().get();
    private Android_App_ResourcesManager.Interface mImpl = new Android_App_ResourcesManager.Interface() {
        /* class com.miui.internal.variable.v24.Android_App_ResourcesManager_class.AnonymousClass1 */

        @Override // com.miui.internal.variable.api.v29.Android_App_ResourcesManager.Interface
        public ResourcesImpl createResourcesImpl(Object obj, ResourcesKey resourcesKey) {
            return Android_App_ResourcesManager_class.this.handleCreateResourcesImpl(0, obj, resourcesKey);
        }
    };
    private Android_App_ResourcesManager.Interface mOriginal;

    public Android_App_ResourcesManager_class() {
        try {
            Class.forName("android.app.ResourcesManager", true, getClass().getClassLoader());
        } catch (ClassNotFoundException e2) {
            throw new IllegalStateException(e2);
        }
    }

    @Override // com.miui.internal.variable.api.Overridable
    public Android_App_ResourcesManager.Interface asInterface() {
        return this.mImpl;
    }

    public void bind(Android_App_ResourcesManager.Interface r1) {
        this.mOriginal = r1;
    }

    @Override // com.miui.internal.variable.IManagedClassProxy
    public void buildProxy() {
        attachMethod("createResourcesImpl", "(Landroid/content/res/ResourcesKey;)Landroid/content/res/ResourcesImpl;");
    }

    /* access modifiers changed from: protected */
    public ResourcesImpl callOriginalCreateResourcesImpl(long j, Object obj, ResourcesKey resourcesKey) {
        Android_App_ResourcesManager.Interface r0 = this.mOriginal;
        return r0 != null ? r0.createResourcesImpl(obj, resourcesKey) : originalCreateResourcesImpl(j, obj, resourcesKey);
    }

    /* access modifiers changed from: protected */
    @Override // com.miui.internal.util.ClassProxy
    public void handle() {
        handleCreateResourcesImpl(0, null, null);
    }

    /* access modifiers changed from: protected */
    public ResourcesImpl handleCreateResourcesImpl(long j, Object obj, ResourcesKey resourcesKey) {
        ResourcesImpl callOriginalCreateResourcesImpl = callOriginalCreateResourcesImpl(j, obj, resourcesKey);
        if (callOriginalCreateResourcesImpl == null || com.miui.internal.variable.Android_App_ResourcesManager_class.mAppendedAssetPaths == null) {
            return callOriginalCreateResourcesImpl;
        }
        AssetManager assets = ResourcesImplClass.getAssets(callOriginalCreateResourcesImpl);
        for (String str : com.miui.internal.variable.Android_App_ResourcesManager_class.mAppendedAssetPaths) {
            AssetManagerClass.addAssetPath(assets, str);
        }
        return callOriginalCreateResourcesImpl;
    }

    /* access modifiers changed from: protected */
    @Override // com.miui.internal.util.ClassProxy
    public void onClassProxyDisabled() {
        Android_App_ResourcesManager.Extension.get().setExtension(this);
    }

    /* access modifiers changed from: protected */
    public ResourcesImpl originalCreateResourcesImpl(long j, Object obj, ResourcesKey resourcesKey) {
        throw new IllegalStateException("com.miui.internal.variable.v24.Android_App_ResourcesManager_class.originalCreateResourcesImpl(long, Object, ResourcesKey)");
    }
}
