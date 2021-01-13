package com.miui.internal.variable.v21;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ListView;
import com.miui.internal.variable.api.Overridable;
import com.miui.internal.variable.api.v29.Android_Widget_ListView;

public class Android_Widget_ListView_class extends com.miui.internal.variable.v16.Android_Widget_ListView_class implements Overridable<Android_Widget_ListView.Interface> {
    private Android_Widget_ListView.Interface mImpl = new Android_Widget_ListView.Interface() {
        /* class com.miui.internal.variable.v21.Android_Widget_ListView_class.AnonymousClass1 */

        @Override // com.miui.internal.variable.api.v29.Android_Widget_ListView.Interface
        public void fillGap(ListView listView, boolean z) {
            Android_Widget_ListView_class.this.handleFillGap(0, listView, z);
        }

        @Override // com.miui.internal.variable.api.v29.Android_Widget_ListView.Interface
        public void init(ListView listView, Context context, AttributeSet attributeSet, int i, int i2) {
            Android_Widget_ListView_class.this.handle_init_(0, listView, context, attributeSet, i, i2);
        }

        @Override // com.miui.internal.variable.api.v29.Android_Widget_ListView.Interface
        public void layoutChildren(ListView listView) {
            Android_Widget_ListView_class.this.handleLayoutChildren(0, listView);
        }
    };
    private Android_Widget_ListView.Interface mOriginal;

    @Override // com.miui.internal.variable.api.Overridable
    public Android_Widget_ListView.Interface asInterface() {
        return this.mImpl;
    }

    /* access modifiers changed from: protected */
    @Override // com.miui.internal.variable.v16.Android_Widget_ListView_class
    public void attachSpecialMethod() {
        attachConstructor("(Landroid/content/Context;Landroid/util/AttributeSet;II)V");
    }

    public void bind(Android_Widget_ListView.Interface r1) {
        this.mOriginal = r1;
    }

    /* access modifiers changed from: protected */
    @Override // com.miui.internal.variable.v16.Android_Widget_ListView_class
    public void callOriginalFillGap(long j, ListView listView, boolean z) {
        Android_Widget_ListView.Interface r0 = this.mOriginal;
        if (r0 != null) {
            r0.fillGap(listView, z);
        } else {
            super.callOriginalFillGap(j, listView, z);
        }
    }

    /* access modifiers changed from: protected */
    @Override // com.miui.internal.variable.v16.Android_Widget_ListView_class
    public void callOriginalLayoutChildren(long j, ListView listView) {
        Android_Widget_ListView.Interface r0 = this.mOriginal;
        if (r0 != null) {
            r0.layoutChildren(listView);
        } else {
            super.callOriginalLayoutChildren(j, listView);
        }
    }

    /* access modifiers changed from: protected */
    public void callOriginal_init_(long j, ListView listView, Context context, AttributeSet attributeSet, int i, int i2) {
        if (this.mOriginal == null) {
            original_init_(j, listView, context, attributeSet, i, i2);
        }
    }

    /* access modifiers changed from: protected */
    @Override // com.miui.internal.variable.v16.Android_Widget_ListView_class, com.miui.internal.util.ClassProxy
    public void handle() {
        handle_init_(0, null, null, null, 0);
        super.handle();
    }

    /* access modifiers changed from: protected */
    public void handle_init_(long j, ListView listView, Context context, AttributeSet attributeSet, int i, int i2) {
        callOriginal_init_(j, listView, context, attributeSet, i, i2);
        doInit(listView, context);
    }

    /* access modifiers changed from: protected */
    @Override // com.miui.internal.util.ClassProxy
    public void onClassProxyDisabled() {
        Android_Widget_ListView.Extension.get().setExtension(this);
    }

    /* access modifiers changed from: protected */
    public void original_init_(long j, ListView listView, Context context, AttributeSet attributeSet, int i, int i2) {
        throw new IllegalStateException("com.miui.internal.variable.v21.Android_Widget_ListView_class.original_init_(long, ListView, Context, AttributeSet, int, int)");
    }
}
