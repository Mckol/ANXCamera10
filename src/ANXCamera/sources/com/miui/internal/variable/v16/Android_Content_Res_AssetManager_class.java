package com.miui.internal.variable.v16;

import android.content.res.AssetManager;
import com.miui.internal.util.ClassProxy;
import com.miui.internal.util.PackageConstants;
import com.miui.internal.variable.VariableExceptionHandler;
import com.miui.internal.variable.api.Overridable;
import com.miui.internal.variable.api.v29.Android_Content_Res_AssetManager;
import miui.reflect.Constructor;
import miui.reflect.Method;

public class Android_Content_Res_AssetManager_class extends ClassProxy<AssetManager> implements com.miui.internal.variable.Android_Content_Res_AssetManager_class, Overridable<Android_Content_Res_AssetManager.Interface> {
    private static final Method mAddAssetPath = Method.of(AssetManager.class, "addAssetPath", "(Ljava/lang/String;)I");
    private Android_Content_Res_AssetManager.Interface mImpl = new Android_Content_Res_AssetManager.Interface() {
        /* class com.miui.internal.variable.v16.Android_Content_Res_AssetManager_class.AnonymousClass1 */

        @Override // com.miui.internal.variable.api.v29.Android_Content_Res_AssetManager.Interface
        public int addAssetPath(AssetManager assetManager, String str) {
            return Android_Content_Res_AssetManager_class.this.handleAddAssetPath(0, assetManager, str);
        }
    };
    private Android_Content_Res_AssetManager.Interface mOriginal;

    public Android_Content_Res_AssetManager_class() {
        super(AssetManager.class);
    }

    @Override // com.miui.internal.variable.Android_Content_Res_AssetManager_class
    public int addAssetPath(AssetManager assetManager, String str) {
        try {
            return mAddAssetPath.invokeInt(null, assetManager, str);
        } catch (Exception e2) {
            VariableExceptionHandler.getInstance().onThrow("android.content.res.AssetManager.addAssetPath", e2);
            return 0;
        }
    }

    @Override // com.miui.internal.variable.api.Overridable
    public Android_Content_Res_AssetManager.Interface asInterface() {
        return this.mImpl;
    }

    public void bind(Android_Content_Res_AssetManager.Interface r1) {
        this.mOriginal = r1;
    }

    @Override // com.miui.internal.variable.IManagedClassProxy
    public void buildProxy() {
        try {
            attachMethod("addAssetPath", "(Ljava/lang/String;)I");
        } catch (Exception e2) {
            VariableExceptionHandler.getInstance().onThrow("android.content.res.AssetManager.addAssetPath", e2);
        }
    }

    /* access modifiers changed from: protected */
    public int callOriginalAddAssetPath(long j, AssetManager assetManager, String str) {
        Android_Content_Res_AssetManager.Interface r0 = this.mOriginal;
        return r0 != null ? r0.addAssetPath(assetManager, str) : originalAddAssetPath(j, assetManager, str);
    }

    /* access modifiers changed from: protected */
    @Override // com.miui.internal.util.ClassProxy
    public void handle() {
        handleAddAssetPath(0, null, null);
    }

    /* access modifiers changed from: protected */
    public int handleAddAssetPath(long j, AssetManager assetManager, String str) {
        if (!PackageConstants.RESOURCE_PATH.equals(str)) {
            callOriginalAddAssetPath(j, assetManager, PackageConstants.RESOURCE_PATH);
        }
        return callOriginalAddAssetPath(j, assetManager, str);
    }

    @Override // com.miui.internal.variable.Android_Content_Res_AssetManager_class
    public AssetManager newInstance() {
        return (AssetManager) Constructor.of(AssetManager.class, "()V").newInstance(new Object[0]);
    }

    /* access modifiers changed from: protected */
    @Override // com.miui.internal.util.ClassProxy
    public void onClassProxyDisabled() {
        Android_Content_Res_AssetManager.Extension.get().setExtension(this);
    }

    /* access modifiers changed from: protected */
    public int originalAddAssetPath(long j, AssetManager assetManager, String str) {
        throw new IllegalStateException("com.miui.internal.variable.v16.Android_Content_Res_AssetManager_class.originalAddAssetPath(long, AssetManager, String)");
    }
}
