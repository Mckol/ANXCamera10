package com.miui.internal.variable.v21;

import android.content.Context;
import android.view.WindowManager;
import android.widget.PopupWindow;
import com.miui.internal.R;
import miui.reflect.Field;
import miui.util.AttributeResolver;

public class Android_Widget_PopupWindow_class extends com.miui.internal.variable.v16.Android_Widget_PopupWindow_class {
    private static final int EXTRA_FLAG_STATUS_BAR_DARK_MODE = 16;
    private static final int FLAG_TRANSLUCENT_STATUS = 67108864;
    private static Field mExtraFlags;

    @Override // com.miui.internal.variable.IManagedClassProxy, com.miui.internal.variable.Android_Widget_PopupWindow_class
    public void buildProxy() {
        attachMethod("invokePopup", "(Landroid/view/WindowManager$LayoutParams;)V");
        try {
            mExtraFlags = Field.of(WindowManager.LayoutParams.class, "extraFlags", Field.INT_SIGNATURE_PRIMITIVE);
        } catch (Exception unused) {
            mExtraFlags = null;
        }
    }

    /* access modifiers changed from: protected */
    public void callOriginalInvokePopup(long j, PopupWindow popupWindow, WindowManager.LayoutParams layoutParams) {
        originalInvokePopup(j, popupWindow, layoutParams);
    }

    /* access modifiers changed from: protected */
    @Override // com.miui.internal.variable.v16.Android_Widget_PopupWindow_class, com.miui.internal.util.ClassProxy
    public void handle() {
        handleInvokePopup(0, null, null);
    }

    /* access modifiers changed from: protected */
    public void handleInvokePopup(long j, PopupWindow popupWindow, WindowManager.LayoutParams layoutParams) {
        if (layoutParams.x == 0 && layoutParams.y == 0 && layoutParams.width == -1 && layoutParams.height == -1) {
            Context context = popupWindow.getContentView().getContext();
            if (AttributeResolver.resolveBoolean(context, 16843277, false)) {
                layoutParams.flags |= 1024;
            } else if (mExtraFlags != null) {
                layoutParams.flags |= FLAG_TRANSLUCENT_STATUS;
                if (AttributeResolver.resolveInt(context, R.attr.windowTranslucentStatus, 1) == 1) {
                    mExtraFlags.set((Object) layoutParams, ((Integer) mExtraFlags.get(layoutParams)).intValue() | 16);
                }
            }
        }
        callOriginalInvokePopup(j, popupWindow, layoutParams);
    }

    /* access modifiers changed from: protected */
    public void originalInvokePopup(long j, PopupWindow popupWindow, WindowManager.LayoutParams layoutParams) {
        throw new IllegalStateException("com.miui.internal.variable.v21.Android_Widget_PopupWindow_class.originalInvokePopup(long, PopupWindow, WindowManager.LayoutParams)");
    }
}
