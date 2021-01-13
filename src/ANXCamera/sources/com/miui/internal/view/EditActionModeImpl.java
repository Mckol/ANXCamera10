package com.miui.internal.view;

import android.content.Context;
import android.view.ActionMode;
import android.view.View;
import com.miui.internal.widget.ActionBarContextView;
import miui.view.ActionModeAnimationListener;
import miui.view.EditActionMode;

public class EditActionModeImpl extends ActionModeImpl implements EditActionMode {
    public EditActionModeImpl(Context context, ActionMode.Callback callback) {
        super(context, callback);
    }

    @Override // miui.view.EditActionMode
    public void addAnimationListener(ActionModeAnimationListener actionModeAnimationListener) {
        this.mActionModeView.get().addAnimationListener(actionModeAnimationListener);
    }

    @Override // com.miui.internal.view.ActionModeImpl
    public CharSequence getTitle() {
        return ((ActionBarContextView) this.mActionModeView.get()).getTitle();
    }

    @Override // miui.view.EditActionMode
    public void removeAnimationListener(ActionModeAnimationListener actionModeAnimationListener) {
        this.mActionModeView.get().removeAnimationListener(actionModeAnimationListener);
    }

    @Override // miui.view.EditActionMode
    public void setButton(int i, int i2) {
        setButton(i, this.mContext.getResources().getString(i2));
    }

    @Override // miui.view.EditActionMode
    public void setButton(int i, int i2, int i3) {
        setButton(i, this.mContext.getResources().getString(i2), i3);
    }

    @Override // miui.view.EditActionMode
    public void setButton(int i, CharSequence charSequence) {
        ((ActionBarContextView) this.mActionModeView.get()).setButton(i, charSequence);
    }

    @Override // miui.view.EditActionMode
    public void setButton(int i, CharSequence charSequence, int i2) {
        ((ActionBarContextView) this.mActionModeView.get()).setButton(i, charSequence, i2);
    }

    @Override // com.miui.internal.view.ActionModeImpl
    public void setCustomView(View view) {
    }

    @Override // com.miui.internal.view.ActionModeImpl, android.view.ActionMode
    public void setSubtitle(int i) {
    }

    @Override // com.miui.internal.view.ActionModeImpl, android.view.ActionMode
    public void setSubtitle(CharSequence charSequence) {
    }

    @Override // com.miui.internal.view.ActionModeImpl, android.view.ActionMode
    public void setTitle(int i) {
        setTitle(this.mContext.getResources().getString(i));
    }

    @Override // com.miui.internal.view.ActionModeImpl, android.view.ActionMode
    public void setTitle(CharSequence charSequence) {
        ((ActionBarContextView) this.mActionModeView.get()).setTitle(charSequence);
    }
}
