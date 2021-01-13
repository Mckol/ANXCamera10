package com.miui.internal.view;

import android.content.Context;
import android.view.ActionMode;
import android.view.View;
import android.widget.EditText;
import com.miui.internal.widget.SearchActionModeView;
import miui.view.ActionModeAnimationListener;
import miui.view.SearchActionMode;

public class SearchActionModeImpl extends ActionModeImpl implements SearchActionMode {
    public SearchActionModeImpl(Context context, ActionMode.Callback callback) {
        super(context, callback);
    }

    @Override // miui.view.SearchActionMode
    public void addAnimationListener(ActionModeAnimationListener actionModeAnimationListener) {
        this.mActionModeView.get().addAnimationListener(actionModeAnimationListener);
    }

    @Override // miui.view.SearchActionMode
    public EditText getSearchInput() {
        return ((SearchActionModeView) this.mActionModeView.get()).getSearchInput();
    }

    @Override // miui.view.SearchActionMode
    public void removeAnimationListener(ActionModeAnimationListener actionModeAnimationListener) {
        this.mActionModeView.get().removeAnimationListener(actionModeAnimationListener);
    }

    @Override // miui.view.SearchActionMode
    public void setAnchorView(View view) {
        ((SearchActionModeView) this.mActionModeView.get()).setAnchorView(view);
    }

    @Override // miui.view.SearchActionMode
    public void setAnimateView(View view) {
        ((SearchActionModeView) this.mActionModeView.get()).setAnimateView(view);
    }

    @Override // miui.view.SearchActionMode
    public void setResultView(View view) {
        ((SearchActionModeView) this.mActionModeView.get()).setResultView(view);
    }
}
