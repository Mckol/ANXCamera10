package com.miui.internal.variable.v23;

import android.view.View;
import android.view.WindowManager;
import android.widget.PopupWindow;
import com.miui.internal.R;
import com.miui.internal.variable.api.Overridable;
import com.miui.internal.variable.api.v29.Android_Widget_PopupWindow;
import miui.reflect.Field;
import miui.util.AttributeResolver;

public class Android_Widget_PopupWindow_class extends com.miui.internal.variable.v21.Android_Widget_PopupWindow_class implements Overridable<Android_Widget_PopupWindow.Interface> {
    private static final int SYSTEM_UI_FLAG_LIGHT_STATUS_BAR = 8192;
    private static final Field mBackgroundViewField = Field.of(PopupWindow.class, "mBackgroundView", "Landroid/view/View;");
    private static final Field mDecorViewField = Field.of(PopupWindow.class, "mDecorView", "Landroid/widget/PopupWindow$PopupDecorView;");
    private static final Field mLayoutInsetDecorField = Field.of(PopupWindow.class, "mLayoutInsetDecor", Field.BOOLEAN_SIGNATURE_PRIMITIVE);
    private Android_Widget_PopupWindow.Interface mImpl = new Android_Widget_PopupWindow.Interface() {
        /* class com.miui.internal.variable.v23.Android_Widget_PopupWindow_class.AnonymousClass1 */

        @Override // com.miui.internal.variable.api.v29.Android_Widget_PopupWindow.Interface
        public void invokePopup(PopupWindow popupWindow, WindowManager.LayoutParams layoutParams) {
            Android_Widget_PopupWindow_class.this.handleInvokePopup(0, popupWindow, layoutParams);
        }
    };
    private Android_Widget_PopupWindow.Interface mOriginal;

    @Override // com.miui.internal.variable.api.Overridable
    public Android_Widget_PopupWindow.Interface asInterface() {
        return this.mImpl;
    }

    public void bind(Android_Widget_PopupWindow.Interface r1) {
        this.mOriginal = r1;
    }

    /* access modifiers changed from: protected */
    @Override // com.miui.internal.variable.v21.Android_Widget_PopupWindow_class
    public void callOriginalInvokePopup(long j, PopupWindow popupWindow, WindowManager.LayoutParams layoutParams) {
        Android_Widget_PopupWindow.Interface r0 = this.mOriginal;
        if (r0 != null) {
            r0.invokePopup(popupWindow, layoutParams);
        } else {
            super.callOriginalInvokePopup(j, popupWindow, layoutParams);
        }
    }

    /* access modifiers changed from: protected */
    @Override // com.miui.internal.variable.v21.Android_Widget_PopupWindow_class
    public void handleInvokePopup(long j, PopupWindow popupWindow, WindowManager.LayoutParams layoutParams) {
        super.handleInvokePopup(j, popupWindow, layoutParams);
        int resolveInt = AttributeResolver.resolveInt(popupWindow.getContentView().getContext(), R.attr.windowTranslucentStatus, 1);
        boolean z = mLayoutInsetDecorField.getBoolean(popupWindow);
        View view = (View) mBackgroundViewField.get(popupWindow);
        if (resolveInt != 0 && z && view != null) {
            ((View) mDecorViewField.get(popupWindow)).setFitsSystemWindows(!z);
            view.setFitsSystemWindows(z);
            if (resolveInt == 1) {
                view.setSystemUiVisibility(8192);
            } else {
                view.setSystemUiVisibility(view.getSystemUiVisibility() & -8193);
            }
        }
    }

    /* access modifiers changed from: protected */
    @Override // com.miui.internal.util.ClassProxy
    public void onClassProxyDisabled() {
        Android_Widget_PopupWindow.Extension.get().setExtension(this);
    }
}
