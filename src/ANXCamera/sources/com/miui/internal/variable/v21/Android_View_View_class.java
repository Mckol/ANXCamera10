package com.miui.internal.variable.v21;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import com.miui.internal.util.TaggingDrawableHelper;
import com.miui.internal.variable.api.Overridable;
import com.miui.internal.variable.api.v29.Android_View_View;

public class Android_View_View_class extends com.miui.internal.variable.v19.Android_View_View_class implements Overridable<Android_View_View.Interface> {
    private Android_View_View.Interface mImpl = new Android_View_View.Interface() {
        /* class com.miui.internal.variable.v21.Android_View_View_class.AnonymousClass1 */

        @Override // com.miui.internal.variable.api.v29.Android_View_View.Interface
        public void init(View view, Context context, AttributeSet attributeSet, int i, int i2) {
            Android_View_View_class.this.handle_init_(0, view, context, attributeSet, i, i2);
        }

        @Override // com.miui.internal.variable.api.v29.Android_View_View.Interface
        public int[] onCreateDrawableState(View view, int i) {
            return Android_View_View_class.this.handleOnCreateDrawableState(0, view, i);
        }

        @Override // com.miui.internal.variable.api.v29.Android_View_View.Interface
        public void refreshDrawableState(View view) {
            Android_View_View_class.this.handleRefreshDrawableState(0, view);
        }
    };
    private Android_View_View.Interface mOriginal;

    @Override // com.miui.internal.variable.api.Overridable
    public Android_View_View.Interface asInterface() {
        return this.mImpl;
    }

    public void bind(Android_View_View.Interface r1) {
        this.mOriginal = r1;
    }

    @Override // com.miui.internal.variable.v16.Android_View_View_class_common, com.miui.internal.variable.IManagedClassProxy
    public void buildProxy() {
        super.buildProxy();
        attachConstructor("(Landroid/content/Context;Landroid/util/AttributeSet;II)V");
    }

    /* access modifiers changed from: protected */
    @Override // com.miui.internal.variable.v16.Android_View_View_class_common
    public int[] callOriginalOnCreateDrawableState(long j, View view, int i) {
        Android_View_View.Interface r0 = this.mOriginal;
        return r0 != null ? r0.onCreateDrawableState(view, i) : super.callOriginalOnCreateDrawableState(j, view, i);
    }

    /* access modifiers changed from: protected */
    @Override // com.miui.internal.variable.v16.Android_View_View_class_common
    public void callOriginalRefreshDrawableState(long j, View view) {
        Android_View_View.Interface r0 = this.mOriginal;
        if (r0 != null) {
            r0.refreshDrawableState(view);
        } else {
            super.callOriginalRefreshDrawableState(j, view);
        }
    }

    /* access modifiers changed from: protected */
    public void callOriginal_init_(long j, View view, Context context, AttributeSet attributeSet, int i, int i2) {
        Android_View_View.Interface r0 = this.mOriginal;
        if (r0 != null) {
            r0.init(view, context, attributeSet, i, i2);
        } else {
            original_init_(j, view, context, attributeSet, i, i2);
        }
    }

    /* access modifiers changed from: protected */
    @Override // com.miui.internal.variable.v16.Android_View_View_class_common, com.miui.internal.util.ClassProxy
    public void handle() {
        handle_init_(0, null, null, null, 0, 0);
        super.handle();
    }

    /* access modifiers changed from: protected */
    public void handle_init_(long j, View view, Context context, AttributeSet attributeSet, int i, int i2) {
        callOriginal_init_(j, view, context, attributeSet, i, i2);
        TaggingDrawableHelper.initViewSequenceStates(view, attributeSet);
    }

    /* access modifiers changed from: protected */
    @Override // com.miui.internal.util.ClassProxy
    public void onClassProxyDisabled() {
        Android_View_View.Extension.get().setExtension(this);
    }

    /* access modifiers changed from: protected */
    public void original_init_(long j, View view, Context context, AttributeSet attributeSet, int i, int i2) {
        throw new IllegalStateException("com.miui.internal.variable.v21.Android_View_View_class.original_init_(long, View, Context, AttributeSet, int, int)");
    }
}
