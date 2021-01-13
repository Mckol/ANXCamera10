package com.miui.internal.app;

import android.app.Activity;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.view.ActionMode;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import com.miui.internal.R;
import com.miui.internal.variable.Android_View_Window_class;
import com.miui.internal.view.menu.ImmersionMenuPopupWindow;
import com.miui.internal.view.menu.ImmersionMenuPopupWindowImpl;
import com.miui.internal.view.menu.MenuBuilder;
import com.miui.internal.view.menu.MenuPresenter;
import com.miui.internal.widget.ActionBarView;
import miui.app.ActionBar;

public abstract class ActionBarDelegateImpl implements ActionBarDelegate, MenuPresenter.Callback, MenuBuilder.Callback {
    static final String METADATA_UI_OPTIONS = "android.support.UI_OPTIONS";
    private static final String TAG = "ActionBarDelegate";
    static final String UI_OPTION_SPLIT_ACTION_BAR_WHEN_NARROW = "splitActionBarWhenNarrow";
    private ActionBar mActionBar;
    protected ActionBarView mActionBarView;
    protected ActionMode mActionMode;
    final Activity mActivity;
    protected boolean mFeatureIndeterminateProgress;
    protected boolean mFeatureProgress;
    boolean mHasActionBar;
    protected int mImmersionLayoutResourceId;
    private MenuBuilder mImmersionMenu;
    private boolean mImmersionMenuEnabled;
    protected MenuBuilder mMenu;
    private MenuInflater mMenuInflater;
    private ImmersionMenuPopupWindow mMenuPopupWindow;
    boolean mOverlayActionBar;
    protected boolean mSubDecorInstalled;
    private int mTranslucentStatus = 0;

    ActionBarDelegateImpl(Activity activity) {
        this.mActivity = activity;
    }

    /* access modifiers changed from: protected */
    public MenuBuilder createMenu() {
        MenuBuilder menuBuilder = new MenuBuilder(getActionBarThemedContext());
        menuBuilder.setCallback(this);
        return menuBuilder;
    }

    public void dismissImmersionMenu(boolean z) {
        ImmersionMenuPopupWindow immersionMenuPopupWindow = this.mMenuPopupWindow;
        if (immersionMenuPopupWindow != null) {
            immersionMenuPopupWindow.dismiss(z);
        }
    }

    public final ActionBar getActionBar() {
        if (!this.mHasActionBar && !this.mOverlayActionBar) {
            this.mActionBar = null;
        } else if (this.mActionBar == null) {
            this.mActionBar = createActionBar();
        }
        return this.mActionBar;
    }

    /* access modifiers changed from: protected */
    public final Context getActionBarThemedContext() {
        Activity activity = this.mActivity;
        ActionBar actionBar = getActionBar();
        return actionBar != null ? actionBar.getThemedContext() : activity;
    }

    public Activity getActivity() {
        return this.mActivity;
    }

    public MenuInflater getMenuInflater() {
        if (this.mMenuInflater == null) {
            ActionBar actionBar = getActionBar();
            if (actionBar != null) {
                this.mMenuInflater = new MenuInflater(actionBar.getThemedContext());
            } else {
                this.mMenuInflater = new MenuInflater(this.mActivity);
            }
        }
        return this.mMenuInflater;
    }

    public abstract Context getThemedContext();

    public int getTranslucentStatus() {
        return this.mTranslucentStatus;
    }

    /* access modifiers changed from: protected */
    public final String getUiOptionsFromMetadata() {
        try {
            ActivityInfo activityInfo = this.mActivity.getPackageManager().getActivityInfo(this.mActivity.getComponentName(), 128);
            if (activityInfo.metaData != null) {
                return activityInfo.metaData.getString(METADATA_UI_OPTIONS);
            }
            return null;
        } catch (PackageManager.NameNotFoundException unused) {
            Log.e(TAG, "getUiOptionsFromMetadata: Activity '" + this.mActivity.getClass().getSimpleName() + "' not in manifest");
            return null;
        }
    }

    public boolean isImmersionMenuEnabled() {
        return this.mImmersionMenuEnabled;
    }

    @Override // com.miui.internal.app.ActionBarDelegate
    public void onActionModeFinished(ActionMode actionMode) {
    }

    @Override // com.miui.internal.app.ActionBarDelegate
    public void onActionModeStarted(ActionMode actionMode) {
    }

    @Override // com.miui.internal.view.menu.MenuPresenter.Callback
    public void onCloseMenu(MenuBuilder menuBuilder, boolean z) {
        this.mActivity.closeOptionsMenu();
    }

    @Override // com.miui.internal.app.ActionBarDelegate
    public void onConfigurationChanged(Configuration configuration) {
        if (this.mHasActionBar && this.mSubDecorInstalled) {
            ((ActionBarImpl) getActionBar()).onConfigurationChanged(configuration);
        }
    }

    public void onCreate(Bundle bundle) {
    }

    /* access modifiers changed from: protected */
    public abstract boolean onCreateImmersionMenu(MenuBuilder menuBuilder);

    @Override // com.miui.internal.app.ActionBarDelegate
    public abstract /* synthetic */ boolean onMenuItemSelected(int i, MenuItem menuItem);

    @Override // com.miui.internal.view.menu.MenuBuilder.Callback
    public void onMenuModeChange(MenuBuilder menuBuilder) {
        reopenMenu(menuBuilder, true);
    }

    @Override // com.miui.internal.view.menu.MenuPresenter.Callback
    public boolean onOpenSubMenu(MenuBuilder menuBuilder) {
        return false;
    }

    @Override // com.miui.internal.app.ActionBarDelegate
    public void onPostResume() {
        ActionBarImpl actionBarImpl;
        if (this.mHasActionBar && this.mSubDecorInstalled && (actionBarImpl = (ActionBarImpl) getActionBar()) != null) {
            actionBarImpl.setShowHideAnimationEnabled(true);
        }
    }

    /* access modifiers changed from: protected */
    public abstract boolean onPrepareImmersionMenu(MenuBuilder menuBuilder);

    @Override // com.miui.internal.app.ActionBarDelegate
    public void onStop() {
        ActionBarImpl actionBarImpl;
        dismissImmersionMenu(false);
        if (this.mHasActionBar && this.mSubDecorInstalled && (actionBarImpl = (ActionBarImpl) getActionBar()) != null) {
            actionBarImpl.setShowHideAnimationEnabled(false);
        }
    }

    @Override // com.miui.internal.app.ActionBarDelegate
    public ActionMode onWindowStartingActionMode(ActionMode.Callback callback) {
        return null;
    }

    /* access modifiers changed from: protected */
    public void reopenMenu(MenuBuilder menuBuilder, boolean z) {
        ActionBarView actionBarView = this.mActionBarView;
        if (actionBarView == null || !actionBarView.isOverflowReserved()) {
            menuBuilder.close();
        } else if (this.mActionBarView.isOverflowMenuShowing() && z) {
            this.mActionBarView.hideOverflowMenu();
        } else if (this.mActionBarView.getVisibility() == 0) {
            this.mActionBarView.showOverflowMenu();
        }
    }

    @Override // com.miui.internal.app.ActionBarDelegate
    public boolean requestWindowFeature(int i) {
        if (i == 2) {
            this.mFeatureProgress = true;
            return true;
        } else if (i == 5) {
            this.mFeatureIndeterminateProgress = true;
            return true;
        } else if (i == 8) {
            this.mHasActionBar = true;
            return true;
        } else if (i != 9) {
            return this.mActivity.requestWindowFeature(i);
        } else {
            this.mOverlayActionBar = true;
            return true;
        }
    }

    public void setImmersionMenuEnabled(boolean z) {
        this.mImmersionMenuEnabled = z;
        if (this.mSubDecorInstalled && this.mHasActionBar) {
            if (!z) {
                this.mActionBarView.hideImmersionMore();
            } else if (!this.mActionBarView.showImmersionMore()) {
                this.mActionBarView.initImmersionMore(this.mImmersionLayoutResourceId, this);
            }
            invalidateOptionsMenu();
        }
    }

    /* access modifiers changed from: protected */
    public void setMenu(MenuBuilder menuBuilder) {
        if (menuBuilder != this.mMenu) {
            this.mMenu = menuBuilder;
            ActionBarView actionBarView = this.mActionBarView;
            if (actionBarView != null) {
                actionBarView.setMenu(menuBuilder, this);
            }
        }
    }

    public void setTranslucentStatus(int i) {
        int integer = this.mActivity.getResources().getInteger(R.integer.window_translucent_status);
        if (integer >= 0 && integer <= 2) {
            i = integer;
        }
        if (this.mTranslucentStatus != i && Android_View_Window_class.Factory.getInstance().get().setTranslucentStatus(this.mActivity.getWindow(), i)) {
            this.mTranslucentStatus = i;
        }
    }

    public void showImmersionMenu() {
        View findViewById;
        ActionBarView actionBarView = this.mActionBarView;
        if (actionBarView == null || (findViewById = actionBarView.findViewById(R.id.more)) == null) {
            throw new IllegalStateException("Can't find anchor view in actionbar. Do you use default actionbar and immersion menu is enabled?");
        }
        showImmersionMenu(findViewById, this.mActionBarView);
    }

    public void showImmersionMenu(View view, ViewGroup viewGroup) {
        if (!this.mImmersionMenuEnabled) {
            Log.w(TAG, "Try to show immersion menu when immersion menu disabled");
        } else if (view != null) {
            if (this.mImmersionMenu == null) {
                this.mImmersionMenu = createMenu();
                onCreateImmersionMenu(this.mImmersionMenu);
            }
            if (onPrepareImmersionMenu(this.mImmersionMenu) && this.mImmersionMenu.hasVisibleItems()) {
                ImmersionMenuPopupWindow immersionMenuPopupWindow = this.mMenuPopupWindow;
                if (immersionMenuPopupWindow == null) {
                    this.mMenuPopupWindow = new ImmersionMenuPopupWindowImpl(this, this.mImmersionMenu);
                } else {
                    immersionMenuPopupWindow.update(this.mImmersionMenu);
                }
                if (!this.mMenuPopupWindow.isShowing()) {
                    this.mMenuPopupWindow.show(view, viewGroup);
                }
            }
        } else {
            throw new IllegalArgumentException("You must specify a valid anchor view");
        }
    }

    @Override // com.miui.internal.app.ActionBarDelegate
    public ActionMode startActionMode(ActionMode.Callback callback) {
        return null;
    }
}
