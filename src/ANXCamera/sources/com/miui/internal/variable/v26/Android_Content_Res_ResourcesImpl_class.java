package com.miui.internal.variable.v26;

import android.content.res.Resources;
import android.content.res.ResourcesImpl;
import android.graphics.drawable.Drawable;
import android.util.TypedValue;
import com.miui.internal.variable.Android_Graphics_Drawable_Drawable_class;
import com.miui.internal.variable.api.Overridable;
import com.miui.internal.variable.api.v29.Android_Content_Res_ResourcesImpl;

public class Android_Content_Res_ResourcesImpl_class extends com.miui.internal.variable.v24.Android_Content_Res_ResourcesImpl_class implements Overridable<Android_Content_Res_ResourcesImpl.Interface> {
    private static final Android_Graphics_Drawable_Drawable_class DrawableClass = Android_Graphics_Drawable_Drawable_class.Factory.getInstance().get();
    private Android_Content_Res_ResourcesImpl.Interface mImpl = new Android_Content_Res_ResourcesImpl.Interface() {
        /* class com.miui.internal.variable.v26.Android_Content_Res_ResourcesImpl_class.AnonymousClass1 */

        @Override // com.miui.internal.variable.api.v29.Android_Content_Res_ResourcesImpl.Interface
        public Drawable loadDrawable(ResourcesImpl resourcesImpl, Resources resources, TypedValue typedValue, int i, int i2, Resources.Theme theme) {
            return Android_Content_Res_ResourcesImpl_class.this.handleLoadDrawable(0, resourcesImpl, resources, typedValue, i, i2, theme);
        }
    };
    private Android_Content_Res_ResourcesImpl.Interface mOriginal;

    @Override // com.miui.internal.variable.api.Overridable
    public Android_Content_Res_ResourcesImpl.Interface asInterface() {
        return this.mImpl;
    }

    public void bind(Android_Content_Res_ResourcesImpl.Interface r1) {
        this.mOriginal = r1;
    }

    @Override // com.miui.internal.variable.v24.Android_Content_Res_ResourcesImpl_class, com.miui.internal.variable.IManagedClassProxy
    public void buildProxy() {
        attachMethod("loadDrawable", "(Landroid/content/res/Resources;Landroid/util/TypedValue;IILandroid/content/res/Resources$Theme;)Landroid/graphics/drawable/Drawable;");
    }

    /* access modifiers changed from: protected */
    public Drawable callOriginalLoadDrawable(long j, ResourcesImpl resourcesImpl, Resources resources, TypedValue typedValue, int i, int i2, Resources.Theme theme) {
        Android_Content_Res_ResourcesImpl.Interface r0 = this.mOriginal;
        return r0 != null ? r0.loadDrawable(resourcesImpl, resources, typedValue, i, i2, theme) : originalLoadDrawable(j, resourcesImpl, resources, typedValue, i, i2, theme);
    }

    /* access modifiers changed from: protected */
    @Override // com.miui.internal.variable.v24.Android_Content_Res_ResourcesImpl_class, com.miui.internal.util.ClassProxy
    public void handle() {
        handleLoadDrawable(0, (ResourcesImpl) null, (Resources) null, (TypedValue) null, 0, 0, (Resources.Theme) null);
    }

    /* access modifiers changed from: protected */
    public Drawable handleLoadDrawable(long j, ResourcesImpl resourcesImpl, Resources resources, TypedValue typedValue, int i, int i2, Resources.Theme theme) {
        Drawable callOriginalLoadDrawable = callOriginalLoadDrawable(j, resourcesImpl, resources, typedValue, i, i2, theme);
        if (callOriginalLoadDrawable != null && callOriginalLoadDrawable.isStateful()) {
            DrawableClass.setId(callOriginalLoadDrawable, i);
        }
        return callOriginalLoadDrawable;
    }

    /* access modifiers changed from: protected */
    @Override // com.miui.internal.util.ClassProxy
    public void onClassProxyDisabled() {
        Android_Content_Res_ResourcesImpl.Extension.get().setExtension(this);
    }

    /* access modifiers changed from: protected */
    public Drawable originalLoadDrawable(long j, ResourcesImpl resourcesImpl, Resources resources, TypedValue typedValue, int i, int i2, Resources.Theme theme) {
        throw new IllegalStateException("com.miui.internal.variable.v26.Android_Content_Res_ResourcesImpl_class.originalLoadDrawable(long, ResourcesImpl, Resources, TypedValue, int, int, Resources.Theme)");
    }
}
