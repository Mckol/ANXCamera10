package com.miui.internal.variable.v24;

import android.content.res.AssetManager;
import android.content.res.Resources;
import android.content.res.ResourcesImpl;
import android.graphics.drawable.Drawable;
import android.util.TypedValue;
import com.miui.internal.variable.Android_Graphics_Drawable_Drawable_class;
import com.miui.internal.variable.VariableExceptionHandler;
import miui.reflect.Method;

public class Android_Content_Res_ResourcesImpl_class extends com.miui.internal.variable.Android_Content_Res_ResourcesImpl_class {
    private static final Android_Graphics_Drawable_Drawable_class DrawableClass = Android_Graphics_Drawable_Drawable_class.Factory.getInstance().get();
    private static final Method mGetAssets = Method.of(ResourcesImpl.class, "getAssets", "()Landroid/content/res/AssetManager;");

    @Override // com.miui.internal.variable.IManagedClassProxy
    public void buildProxy() {
        attachMethod("loadDrawable", "(Landroid/content/res/Resources;Landroid/util/TypedValue;ILandroid/content/res/Resources$Theme;Z)Landroid/graphics/drawable/Drawable;");
    }

    @Override // com.miui.internal.variable.Android_Content_Res_ResourcesImpl_class
    public AssetManager getAssets(ResourcesImpl resourcesImpl) {
        try {
            return (AssetManager) mGetAssets.invokeObject(ResourcesImpl.class, resourcesImpl, new Object[0]);
        } catch (Exception e2) {
            VariableExceptionHandler.getInstance().onThrow("android.content.res.ResourcesImpl.getAssets", e2);
            return null;
        }
    }

    /* access modifiers changed from: protected */
    @Override // com.miui.internal.util.ClassProxy
    public void handle() {
        handleLoadDrawable(0, null, null, null, 0, null, false);
    }

    /* access modifiers changed from: protected */
    public Drawable handleLoadDrawable(long j, ResourcesImpl resourcesImpl, Resources resources, TypedValue typedValue, int i, Resources.Theme theme, boolean z) {
        Drawable originalLoadDrawable = originalLoadDrawable(j, resourcesImpl, resources, typedValue, i, theme, z);
        if (originalLoadDrawable != null && originalLoadDrawable.isStateful()) {
            DrawableClass.setId(originalLoadDrawable, i);
        }
        return originalLoadDrawable;
    }

    /* access modifiers changed from: protected */
    public Drawable originalLoadDrawable(long j, ResourcesImpl resourcesImpl, Resources resources, TypedValue typedValue, int i, Resources.Theme theme, boolean z) {
        throw new IllegalStateException("com.miui.internal.variable.v24.Android_Content_Res_ResourcesImpl_class.originalLoadDrawable(long, ResourcesImpl, Resources, TypedValue, int, Resources.Theme, boolean)");
    }
}
