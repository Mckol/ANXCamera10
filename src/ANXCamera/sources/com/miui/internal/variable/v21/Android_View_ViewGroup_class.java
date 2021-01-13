package com.miui.internal.variable.v21;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import com.miui.internal.variable.api.Overridable;
import com.miui.internal.variable.api.v29.Android_View_ViewGroup;
import miui.reflect.Field;
import miui.reflect.Method;

public class Android_View_ViewGroup_class extends com.miui.internal.variable.v19.Android_View_ViewGroup_class implements Overridable<Android_View_ViewGroup.Interface> {
    protected static int R_styleable_ViewGroup_touchscreenBlocksFocus;
    protected static final int R_styleable_ViewGroup_transitionGroup = Field.of(com.miui.internal.variable.v16.Android_View_ViewGroup_class.com_android_internal_R_styleable, "ViewGroup_transitionGroup", Field.INT_SIGNATURE_PRIMITIVE).getInt(null);
    protected static Method setTouchscreenBlocksFocus;
    protected static final Method setTransitionGroup = Method.of(ViewGroup.class, "setTransitionGroup", "(Z)V");
    private Android_View_ViewGroup.Interface mImpl = new Android_View_ViewGroup.Interface() {
        /* class com.miui.internal.variable.v21.Android_View_ViewGroup_class.AnonymousClass1 */

        @Override // com.miui.internal.variable.api.v29.Android_View_ViewGroup.Interface
        public void addInArray(ViewGroup viewGroup, View view, int i) {
            Android_View_ViewGroup_class.this.handleAddInArray(0, viewGroup, view, i);
        }

        @Override // com.miui.internal.variable.api.v29.Android_View_ViewGroup.Interface
        public void init(ViewGroup viewGroup, Context context, AttributeSet attributeSet, int i, int i2) {
            Android_View_ViewGroup_class.this.handle_init_(0, viewGroup, context, attributeSet, i, i2);
        }

        @Override // com.miui.internal.variable.api.v29.Android_View_ViewGroup.Interface
        public void onChildVisibilityChanged(ViewGroup viewGroup, View view, int i, int i2) {
            Android_View_ViewGroup_class.this.handleOnChildVisibilityChanged(0, viewGroup, view, i, i2);
        }

        @Override // com.miui.internal.variable.api.v29.Android_View_ViewGroup.Interface
        public void removeFromArray(ViewGroup viewGroup, int i) {
            Android_View_ViewGroup_class.this.handleRemoveFromArray(0, viewGroup, i);
        }

        @Override // com.miui.internal.variable.api.v29.Android_View_ViewGroup.Interface
        public void removeFromArray(ViewGroup viewGroup, int i, int i2) {
            Android_View_ViewGroup_class.this.handleRemoveFromArray(0, viewGroup, i, i2);
        }

        @Override // com.miui.internal.variable.api.v29.Android_View_ViewGroup.Interface
        public boolean resolveLayoutDirection(ViewGroup viewGroup) {
            return Android_View_ViewGroup_class.this.handleResolveLayoutDirection(0, viewGroup);
        }
    };
    private Android_View_ViewGroup.Interface mOriginal;

    static {
        R_styleable_ViewGroup_touchscreenBlocksFocus = 0;
        setTouchscreenBlocksFocus = null;
        try {
            R_styleable_ViewGroup_touchscreenBlocksFocus = Field.of(com.miui.internal.variable.v16.Android_View_ViewGroup_class.com_android_internal_R_styleable, "ViewGroup_touchscreenBlocksFocus", Field.INT_SIGNATURE_PRIMITIVE).getInt(null);
            setTouchscreenBlocksFocus = Method.of(ViewGroup.class, "setTouchscreenBlocksFocus", "(Z)V");
        } catch (Exception unused) {
        }
    }

    @Override // com.miui.internal.variable.api.Overridable
    public Android_View_ViewGroup.Interface asInterface() {
        return this.mImpl;
    }

    /* access modifiers changed from: protected */
    @Override // com.miui.internal.variable.v16.Android_View_ViewGroup_class
    public void attachPrivateMethods() {
        attachMethod("initFromAttributes", "(Landroid/content/Context;Landroid/util/AttributeSet;II)V");
        attachConstructor("(Landroid/content/Context;Landroid/util/AttributeSet;II)V");
    }

    public void bind(Android_View_ViewGroup.Interface r1) {
        this.mOriginal = r1;
    }

    /* access modifiers changed from: protected */
    @Override // com.miui.internal.variable.v16.Android_View_ViewGroup_class
    public void callOriginalAddInArray(long j, ViewGroup viewGroup, View view, int i) {
        Android_View_ViewGroup.Interface r0 = this.mOriginal;
        if (r0 != null) {
            r0.addInArray(viewGroup, view, i);
        } else {
            super.callOriginalAddInArray(j, viewGroup, view, i);
        }
    }

    /* access modifiers changed from: protected */
    @Override // com.miui.internal.variable.v16.Android_View_ViewGroup_class
    public void callOriginalOnChildVisibilityChanged(long j, ViewGroup viewGroup, View view, int i, int i2) {
        Android_View_ViewGroup.Interface r0 = this.mOriginal;
        if (r0 != null) {
            r0.onChildVisibilityChanged(viewGroup, view, i, i2);
        } else {
            super.callOriginalOnChildVisibilityChanged(j, viewGroup, view, i, i2);
        }
    }

    /* access modifiers changed from: protected */
    @Override // com.miui.internal.variable.v16.Android_View_ViewGroup_class
    public void callOriginalRemoveFromArray(long j, ViewGroup viewGroup, int i) {
        Android_View_ViewGroup.Interface r0 = this.mOriginal;
        if (r0 != null) {
            r0.removeFromArray(viewGroup, i);
        } else {
            super.callOriginalRemoveFromArray(j, viewGroup, i);
        }
    }

    /* access modifiers changed from: protected */
    @Override // com.miui.internal.variable.v16.Android_View_ViewGroup_class
    public void callOriginalRemoveFromArray(long j, ViewGroup viewGroup, int i, int i2) {
        Android_View_ViewGroup.Interface r0 = this.mOriginal;
        if (r0 != null) {
            r0.removeFromArray(viewGroup, i, i2);
        } else {
            super.callOriginalRemoveFromArray(j, viewGroup, i, i2);
        }
    }

    /* access modifiers changed from: protected */
    @Override // com.miui.internal.variable.v16.Android_View_ViewGroup_class
    public boolean callOriginalResolveLayoutDirection(long j, ViewGroup viewGroup) {
        Android_View_ViewGroup.Interface r0 = this.mOriginal;
        return r0 != null ? r0.resolveLayoutDirection(viewGroup) : super.callOriginalResolveLayoutDirection(j, viewGroup);
    }

    /* access modifiers changed from: protected */
    @Override // com.miui.internal.variable.v16.Android_View_ViewGroup_class
    public void callOriginal_init_(long j, ViewGroup viewGroup, Context context, AttributeSet attributeSet, int i) {
        if (this.mOriginal == null) {
            super.callOriginal_init_(j, viewGroup, context, attributeSet, i);
        }
    }

    /* access modifiers changed from: protected */
    public void callOriginal_init_(long j, ViewGroup viewGroup, Context context, AttributeSet attributeSet, int i, int i2) {
        Android_View_ViewGroup.Interface r0 = this.mOriginal;
        if (r0 != null) {
            r0.init(viewGroup, context, attributeSet, i, i2);
        } else {
            original_init_(j, viewGroup, context, attributeSet, i, i2);
        }
    }

    /* access modifiers changed from: protected */
    @Override // com.miui.internal.variable.v16.Android_View_ViewGroup_class, com.miui.internal.util.ClassProxy
    public void handle() {
        handle_init_(0, null, null, null, 0, 0);
        handleInitFromAttributes(0, null, null, null, 0, 0);
        super.handle();
    }

    /* access modifiers changed from: protected */
    public void handleInitFromAttributes(long j, ViewGroup viewGroup, Context context, AttributeSet attributeSet, int i, int i2) {
    }

    /* access modifiers changed from: protected */
    public void handle_init_(long j, ViewGroup viewGroup, Context context, AttributeSet attributeSet, int i, int i2) {
        callOriginal_init_(j, viewGroup, context, attributeSet, i, i2);
        initFromAttributes(viewGroup, context, attributeSet, i, i2);
    }

    /* access modifiers changed from: protected */
    @Override // com.miui.internal.util.ClassProxy
    public void onClassProxyDisabled() {
        Android_View_ViewGroup.Extension.get().setExtension(this);
    }

    /* access modifiers changed from: protected */
    public void originalInitFromAttributes(long j, ViewGroup viewGroup, Context context, AttributeSet attributeSet, int i, int i2) {
        throw new IllegalStateException("com.miui.internal.variable.v21.Android_View_ViewGroup_class.originalInitFromAttributes(long, ViewGroup, Context, AttributeSet, int, int)");
    }

    /* access modifiers changed from: protected */
    public void original_init_(long j, ViewGroup viewGroup, Context context, AttributeSet attributeSet, int i, int i2) {
        throw new IllegalStateException("com.miui.internal.variable.v21.Android_View_ViewGroup_class.original_init_(long, ViewGroup, Context, AttributeSet, int, int)");
    }

    /* access modifiers changed from: protected */
    @Override // com.miui.internal.variable.v16.Android_View_ViewGroup_class, com.miui.internal.variable.v19.Android_View_ViewGroup_class
    public void processAttribute(ViewGroup viewGroup, int i, TypedArray typedArray, Context context) {
        if (i == R_styleable_ViewGroup_transitionGroup) {
            setTransitionGroup.invoke(ViewGroup.class, viewGroup, Boolean.valueOf(typedArray.getBoolean(i, false)));
        }
        if (i == R_styleable_ViewGroup_touchscreenBlocksFocus) {
            setTouchscreenBlocksFocus.invoke(ViewGroup.class, viewGroup, Boolean.valueOf(typedArray.getBoolean(i, false)));
            return;
        }
        super.processAttribute(viewGroup, i, typedArray, context);
    }
}
