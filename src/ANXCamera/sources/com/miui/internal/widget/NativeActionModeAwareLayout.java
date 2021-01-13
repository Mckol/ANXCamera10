package com.miui.internal.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.ActionMode;
import android.view.View;
import android.widget.LinearLayout;

public class NativeActionModeAwareLayout extends LinearLayout {
    private OnActionModeForChildListener mActionModeForChildListener;

    public interface OnActionModeForChildListener {
        ActionMode.Callback onActionModeForChild(ActionMode.Callback callback);
    }

    public NativeActionModeAwareLayout(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    public void setActionModeForChildListener(OnActionModeForChildListener onActionModeForChildListener) {
        this.mActionModeForChildListener = onActionModeForChildListener;
    }

    public ActionMode startActionModeForChild(View view, ActionMode.Callback callback) {
        OnActionModeForChildListener onActionModeForChildListener = this.mActionModeForChildListener;
        if (onActionModeForChildListener != null) {
            callback = onActionModeForChildListener.onActionModeForChild(callback);
        }
        return super.startActionModeForChild(view, callback);
    }
}
