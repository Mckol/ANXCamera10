package com.miui.internal.variable.v16;

import android.widget.GridView;
import com.miui.internal.util.TaggingDrawableHelper;
import com.miui.internal.variable.api.Overridable;
import com.miui.internal.variable.api.v29.Android_Widget_GridView;

public class Android_Widget_GridView_class extends com.miui.internal.variable.Android_Widget_GridView_class implements Overridable<Android_Widget_GridView.Interface> {
    private Android_Widget_GridView.Interface mImpl = new Android_Widget_GridView.Interface() {
        /* class com.miui.internal.variable.v16.Android_Widget_GridView_class.AnonymousClass1 */

        @Override // com.miui.internal.variable.api.v29.Android_Widget_GridView.Interface
        public void fillGap(GridView gridView, boolean z) {
            Android_Widget_GridView_class.this.handleFillGap(0, gridView, z);
        }

        @Override // com.miui.internal.variable.api.v29.Android_Widget_GridView.Interface
        public void layoutChildren(GridView gridView) {
            Android_Widget_GridView_class.this.handleLayoutChildren(0, gridView);
        }
    };
    private Android_Widget_GridView.Interface mOriginal;

    @Override // com.miui.internal.variable.api.Overridable
    public Android_Widget_GridView.Interface asInterface() {
        return this.mImpl;
    }

    public void bind(Android_Widget_GridView.Interface r1) {
        this.mOriginal = r1;
    }

    @Override // com.miui.internal.variable.IManagedClassProxy
    public void buildProxy() {
        attachMethod("layoutChildren", "()V");
        attachMethod("fillGap", "(Z)V");
    }

    /* access modifiers changed from: protected */
    public void callOriginalFillGap(long j, GridView gridView, boolean z) {
        Android_Widget_GridView.Interface r0 = this.mOriginal;
        if (r0 != null) {
            r0.fillGap(gridView, z);
        } else {
            originalFillGap(j, gridView, z);
        }
    }

    /* access modifiers changed from: protected */
    public void callOriginalLayoutChildren(long j, GridView gridView) {
        Android_Widget_GridView.Interface r0 = this.mOriginal;
        if (r0 != null) {
            r0.layoutChildren(gridView);
        } else {
            originalLayoutChildren(j, gridView);
        }
    }

    /* access modifiers changed from: protected */
    @Override // com.miui.internal.util.ClassProxy
    public void handle() {
        handleLayoutChildren(0, null);
        handleFillGap(0, null, false);
    }

    /* access modifiers changed from: protected */
    public void handleFillGap(long j, GridView gridView, boolean z) {
        callOriginalFillGap(j, gridView, z);
        TaggingDrawableHelper.tagChildSequenceState(gridView);
    }

    /* access modifiers changed from: protected */
    public void handleLayoutChildren(long j, GridView gridView) {
        callOriginalLayoutChildren(j, gridView);
        TaggingDrawableHelper.tagChildSequenceState(gridView);
    }

    /* access modifiers changed from: protected */
    @Override // com.miui.internal.util.ClassProxy
    public void onClassProxyDisabled() {
        Android_Widget_GridView.Extension.get().setExtension(this);
    }

    /* access modifiers changed from: protected */
    public void originalFillGap(long j, GridView gridView, boolean z) {
        throw new IllegalStateException("com.miui.internal.variable.v16.Android_Widget_GridView_class.originalFillGap(long, GridView, boolean)");
    }

    /* access modifiers changed from: protected */
    public void originalLayoutChildren(long j, GridView gridView) {
        throw new IllegalStateException("com.miui.internal.variable.v16.Android_Widget_GridView_class.originalLayoutChildren(long, GridView)");
    }
}
