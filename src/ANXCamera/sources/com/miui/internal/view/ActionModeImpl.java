package com.miui.internal.view;

import android.content.Context;
import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import com.miui.internal.view.menu.MenuBuilder;
import com.miui.internal.widget.ActionModeView;
import java.lang.ref.WeakReference;
import miui.view.ActionModeAnimationListener;

public class ActionModeImpl extends ActionMode implements MenuBuilder.Callback, ActionModeAnimationListener {
    public static final int TYPE_FLOATING = 1;
    public static final int TYPE_PRIMARY = 0;
    private ActionModeCallback mActionModeCallback;
    protected WeakReference<ActionModeView> mActionModeView;
    private ActionMode.Callback mCallback;
    protected Context mContext;
    boolean mFinishing = false;
    private MenuBuilder mMenu;

    public interface ActionModeCallback {
        void onActionModeFinish(ActionMode actionMode);
    }

    public ActionModeImpl(Context context, ActionMode.Callback callback) {
        this.mContext = context;
        this.mCallback = callback;
        this.mMenu = new MenuBuilder(context).setDefaultShowAsAction(1);
        this.mMenu.setCallback(this);
    }

    public boolean dispatchOnCreate() {
        this.mMenu.stopDispatchingItemsChanged();
        try {
            return this.mCallback.onCreateActionMode(this, this.mMenu);
        } finally {
            this.mMenu.startDispatchingItemsChanged();
        }
    }

    public void finish() {
        if (!this.mFinishing) {
            this.mFinishing = true;
            this.mActionModeView.get().closeMode();
            ActionModeCallback actionModeCallback = this.mActionModeCallback;
            if (actionModeCallback != null) {
                actionModeCallback.onActionModeFinish(this);
            }
        }
    }

    public View getCustomView() {
        throw new UnsupportedOperationException("getCustomView not supported");
    }

    public Menu getMenu() {
        return this.mMenu;
    }

    public MenuInflater getMenuInflater() {
        return new MenuInflater(this.mContext);
    }

    public CharSequence getSubtitle() {
        throw new UnsupportedOperationException("getSubtitle not supported");
    }

    public CharSequence getTitle() {
        throw new UnsupportedOperationException("getTitle not supported");
    }

    public void invalidate() {
        this.mMenu.stopDispatchingItemsChanged();
        try {
            this.mCallback.onPrepareActionMode(this, this.mMenu);
        } finally {
            this.mMenu.startDispatchingItemsChanged();
        }
    }

    @Override // com.miui.internal.view.menu.MenuBuilder.Callback
    public boolean onMenuItemSelected(MenuBuilder menuBuilder, MenuItem menuItem) {
        ActionMode.Callback callback = this.mCallback;
        return callback != null && callback.onActionItemClicked(this, menuItem);
    }

    @Override // com.miui.internal.view.menu.MenuBuilder.Callback
    public void onMenuModeChange(MenuBuilder menuBuilder) {
        if (this.mCallback != null) {
            invalidate();
        }
    }

    @Override // miui.view.ActionModeAnimationListener
    public void onStart(boolean z) {
    }

    @Override // miui.view.ActionModeAnimationListener
    public void onStop(boolean z) {
        if (!z) {
            this.mCallback.onDestroyActionMode(this);
            this.mCallback = null;
        }
    }

    @Override // miui.view.ActionModeAnimationListener
    public void onUpdate(boolean z, float f) {
    }

    public void setActionModeCallback(ActionModeCallback actionModeCallback) {
        this.mActionModeCallback = actionModeCallback;
    }

    public void setActionModeView(ActionModeView actionModeView) {
        actionModeView.addAnimationListener(this);
        this.mActionModeView = new WeakReference<>(actionModeView);
    }

    public void setCustomView(View view) {
        throw new UnsupportedOperationException("setCustomView not supported");
    }

    @Override // android.view.ActionMode
    public void setSubtitle(int i) {
        throw new UnsupportedOperationException("setSubTitle not supported");
    }

    @Override // android.view.ActionMode
    public void setSubtitle(CharSequence charSequence) {
        throw new UnsupportedOperationException("setSubTitle not supported");
    }

    @Override // android.view.ActionMode
    public void setTitle(int i) {
        throw new UnsupportedOperationException("setTitle not supported");
    }

    @Override // android.view.ActionMode
    public void setTitle(CharSequence charSequence) {
        throw new UnsupportedOperationException("setTitle not supported");
    }
}
