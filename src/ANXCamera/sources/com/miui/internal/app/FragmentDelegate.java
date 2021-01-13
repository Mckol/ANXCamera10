package com.miui.internal.app;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.view.ActionMode;
import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import com.miui.internal.R;
import com.miui.internal.view.SimpleWindowCallback;
import com.miui.internal.view.menu.MenuBuilder;
import com.miui.internal.widget.ActionBarContainer;
import com.miui.internal.widget.ActionBarContextView;
import com.miui.internal.widget.ActionBarOverlayLayout;
import com.miui.internal.widget.ActionBarView;
import miui.app.ActionBar;
import miui.app.IFragment;
import miui.app.OnStatusBarChangeListener;
import miui.util.AttributeResolver;

public class FragmentDelegate extends ActionBarDelegateImpl {
    private static final int INVALIDATE_MENU_POSTED = 16;
    public static final int MENU_INVALIDATE = 1;
    private int mExtraThemeRes;
    private Fragment mFragment;
    private byte mInvalidateMenuFlags;
    private final Runnable mInvalidateMenuRunnable = new Runnable() {
        /* class com.miui.internal.app.FragmentDelegate.AnonymousClass1 */

        public void run() {
            boolean z = true;
            if ((FragmentDelegate.this.mInvalidateMenuFlags & 1) == 1) {
                FragmentDelegate.this.mMenu = null;
            }
            if (FragmentDelegate.this.mMenu == null) {
                FragmentDelegate fragmentDelegate = FragmentDelegate.this;
                fragmentDelegate.mMenu = fragmentDelegate.createMenu();
                FragmentDelegate fragmentDelegate2 = FragmentDelegate.this;
                z = fragmentDelegate2.onCreatePanelMenu(0, fragmentDelegate2.mMenu);
            }
            if (z) {
                FragmentDelegate fragmentDelegate3 = FragmentDelegate.this;
                z = fragmentDelegate3.onPreparePanel(0, null, fragmentDelegate3.mMenu);
            }
            if (z) {
                FragmentDelegate fragmentDelegate4 = FragmentDelegate.this;
                fragmentDelegate4.setMenu(fragmentDelegate4.mMenu);
            } else {
                FragmentDelegate.this.setMenu(null);
                FragmentDelegate.this.mMenu = null;
            }
            FragmentDelegate.access$072(FragmentDelegate.this, -18);
        }
    };
    private MenuBuilder mMenu;
    private View mSubDecor;
    private Context mThemedContext;
    private final Window.Callback mWindowCallback = new SimpleWindowCallback() {
        /* class com.miui.internal.app.FragmentDelegate.AnonymousClass2 */

        @Override // com.miui.internal.view.SimpleWindowCallback
        public void onActionModeFinished(ActionMode actionMode) {
            ((IFragment) FragmentDelegate.this.mFragment).onActionModeFinished(actionMode);
        }

        @Override // com.miui.internal.view.SimpleWindowCallback
        public void onActionModeStarted(ActionMode actionMode) {
            ((IFragment) FragmentDelegate.this.mFragment).onActionModeStarted(actionMode);
        }

        @Override // com.miui.internal.view.SimpleWindowCallback
        public boolean onMenuItemSelected(int i, MenuItem menuItem) {
            return FragmentDelegate.this.onMenuItemSelected(i, menuItem);
        }

        @Override // com.miui.internal.view.SimpleWindowCallback
        public ActionMode onWindowStartingActionMode(ActionMode.Callback callback) {
            return FragmentDelegate.this.onWindowStartingActionMode(callback);
        }
    };

    public FragmentDelegate(Fragment fragment) {
        super(fragment.getActivity());
        this.mFragment = fragment;
    }

    static /* synthetic */ byte access$072(FragmentDelegate fragmentDelegate, int i) {
        byte b2 = (byte) (i & fragmentDelegate.mInvalidateMenuFlags);
        fragmentDelegate.mInvalidateMenuFlags = b2;
        return b2;
    }

    @Override // com.miui.internal.app.ActionBarDelegate
    public ActionBar createActionBar() {
        return new ActionBarImpl(this.mFragment);
    }

    @Override // com.miui.internal.app.ActionBarDelegateImpl
    public Context getThemedContext() {
        if (this.mThemedContext == null) {
            this.mThemedContext = this.mActivity;
            int i = this.mExtraThemeRes;
            if (i != 0) {
                this.mThemedContext = new ContextThemeWrapper(this.mThemedContext, i);
            }
        }
        return this.mThemedContext;
    }

    public View getView() {
        return this.mSubDecor;
    }

    /* access modifiers changed from: package-private */
    public final void installSubDecor(Context context, ViewGroup viewGroup, LayoutInflater layoutInflater) {
        boolean z;
        if (!this.mSubDecorInstalled) {
            this.mSubDecorInstalled = true;
            ActionBarOverlayLayout actionBarOverlayLayout = (ActionBarOverlayLayout) layoutInflater.inflate(R.layout.screen_action_bar, viewGroup, false);
            actionBarOverlayLayout.setCallback(this.mWindowCallback);
            actionBarOverlayLayout.setRootSubDecor(false);
            actionBarOverlayLayout.setOverlayMode(this.mOverlayActionBar);
            actionBarOverlayLayout.setTranslucentStatus(getTranslucentStatus());
            if (this.mExtraThemeRes != 0) {
                actionBarOverlayLayout.setBackground(AttributeResolver.resolveDrawable(context, 16842836));
            }
            this.mActionBarView = (ActionBarView) actionBarOverlayLayout.findViewById(R.id.action_bar);
            this.mActionBarView.setWindowCallback(this.mWindowCallback);
            if (this.mFeatureProgress) {
                this.mActionBarView.initProgress();
            }
            if (this.mFeatureIndeterminateProgress) {
                this.mActionBarView.initIndeterminateProgress();
            }
            if (isImmersionMenuEnabled()) {
                this.mActionBarView.initImmersionMore(this.mImmersionLayoutResourceId, this);
            }
            boolean equals = "splitActionBarWhenNarrow".equals(getUiOptionsFromMetadata());
            if (equals) {
                z = context.getResources().getBoolean(R.bool.abc_split_action_bar_is_narrow);
            } else {
                TypedArray obtainStyledAttributes = context.obtainStyledAttributes(miui.R.styleable.Window);
                boolean z2 = obtainStyledAttributes.getBoolean(miui.R.styleable.Window_windowSplitActionBar, false);
                obtainStyledAttributes.recycle();
                z = z2;
            }
            ActionBarContainer actionBarContainer = (ActionBarContainer) actionBarOverlayLayout.findViewById(R.id.split_action_bar);
            if (actionBarContainer != null) {
                this.mActionBarView.setSplitView(actionBarContainer);
                this.mActionBarView.setSplitActionBar(z);
                this.mActionBarView.setSplitWhenNarrow(equals);
                ActionBarContextView actionBarContextView = (ActionBarContextView) actionBarOverlayLayout.findViewById(R.id.action_context_bar);
                actionBarContainer.setActionBarContextView(actionBarContextView);
                actionBarContextView.setSplitView(actionBarContainer);
                actionBarContextView.setSplitActionBar(z);
                actionBarContextView.setSplitWhenNarrow(equals);
            }
            updateOptionsMenu(1);
            invalidateOptionsMenu();
            this.mSubDecor = actionBarOverlayLayout;
        } else if (this.mSubDecor.getParent() != null && (this.mSubDecor.getParent() instanceof ViewGroup)) {
            ViewGroup viewGroup2 = (ViewGroup) this.mSubDecor.getParent();
            if (viewGroup2.getChildCount() == 0) {
                viewGroup2.endViewTransition(this.mSubDecor);
            }
        }
    }

    @Override // com.miui.internal.app.ActionBarDelegate
    public void invalidateOptionsMenu() {
        Activity activity = this.mFragment.getActivity();
        if (activity != null) {
            byte b2 = this.mInvalidateMenuFlags;
            if ((b2 & 16) == 0) {
                this.mInvalidateMenuFlags = (byte) (b2 | 16);
                activity.getWindow().getDecorView().post(this.mInvalidateMenuRunnable);
            }
        }
    }

    /* access modifiers changed from: protected */
    @Override // com.miui.internal.app.ActionBarDelegateImpl
    public boolean onCreateImmersionMenu(MenuBuilder menuBuilder) {
        Fragment fragment = this.mFragment;
        if (fragment instanceof IFragment) {
            return ((IFragment) fragment).onCreateOptionsMenu(menuBuilder);
        }
        return false;
    }

    @Override // com.miui.internal.app.ActionBarDelegate
    public boolean onCreatePanelMenu(int i, Menu menu) {
        if (i == 0) {
            return ((IFragment) this.mFragment).onCreatePanelMenu(i, menu);
        }
        return false;
    }

    @Override // com.miui.internal.app.ActionBarDelegate
    public View onCreatePanelView(int i) {
        return null;
    }

    public View onCreateView(ViewGroup viewGroup, Bundle bundle) {
        TypedArray obtainStyledAttributes = getThemedContext().obtainStyledAttributes(miui.R.styleable.Window);
        if (obtainStyledAttributes.hasValue(miui.R.styleable.Window_windowActionBar)) {
            if (obtainStyledAttributes.getBoolean(miui.R.styleable.Window_windowActionBar, false)) {
                requestWindowFeature(8);
            }
            if (obtainStyledAttributes.getBoolean(miui.R.styleable.Window_windowActionBarOverlay, false)) {
                requestWindowFeature(9);
            }
            setTranslucentStatus(obtainStyledAttributes.getInt(R.styleable.Window_windowTranslucentStatus, 0));
            setImmersionMenuEnabled(obtainStyledAttributes.getBoolean(R.styleable.Window_immersionMenuEnabled, false));
            this.mImmersionLayoutResourceId = obtainStyledAttributes.getResourceId(R.styleable.Window_immersionMenuLayout, 0);
            obtainStyledAttributes.recycle();
            LayoutInflater from = LayoutInflater.from(getThemedContext());
            if (this.mHasActionBar) {
                installSubDecor(getThemedContext(), viewGroup, from);
                ViewGroup viewGroup2 = (ViewGroup) this.mSubDecor.findViewById(16908290);
                View onInflateView = ((IFragment) this.mFragment).onInflateView(from, viewGroup2, bundle);
                if (!(onInflateView == null || onInflateView.getParent() == viewGroup2)) {
                    if (onInflateView.getParent() != null) {
                        ((ViewGroup) onInflateView.getParent()).removeView(onInflateView);
                    }
                    viewGroup2.removeAllViews();
                    viewGroup2.addView(onInflateView);
                }
            } else {
                this.mSubDecor = ((IFragment) this.mFragment).onInflateView(from, viewGroup, bundle);
            }
            return this.mSubDecor;
        }
        obtainStyledAttributes.recycle();
        throw new IllegalStateException("You need to use a miui theme (or descendant) with this fragment.");
    }

    @Override // com.miui.internal.app.ActionBarDelegateImpl, com.miui.internal.app.ActionBarDelegate
    public boolean onMenuItemSelected(int i, MenuItem menuItem) {
        if (i == 0) {
            return this.mFragment.onOptionsItemSelected(menuItem);
        }
        return false;
    }

    @Override // com.miui.internal.view.menu.MenuBuilder.Callback
    public boolean onMenuItemSelected(MenuBuilder menuBuilder, MenuItem menuItem) {
        return onMenuItemSelected(0, menuItem);
    }

    /* access modifiers changed from: protected */
    @Override // com.miui.internal.app.ActionBarDelegateImpl
    public boolean onPrepareImmersionMenu(MenuBuilder menuBuilder) {
        Fragment fragment = this.mFragment;
        if (!(fragment instanceof IFragment)) {
            return false;
        }
        fragment.onPrepareOptionsMenu(menuBuilder);
        return true;
    }

    @Override // com.miui.internal.app.ActionBarDelegate
    public boolean onPreparePanel(int i, View view, Menu menu) {
        if (i != 0) {
            return false;
        }
        ((IFragment) this.mFragment).onPreparePanel(i, null, menu);
        return true;
    }

    @Override // com.miui.internal.app.ActionBarDelegateImpl, com.miui.internal.app.ActionBarDelegate
    public ActionMode onWindowStartingActionMode(ActionMode.Callback callback) {
        if (getActionBar() != null) {
            return ((ActionBarImpl) getActionBar()).startActionMode(callback);
        }
        return null;
    }

    public void setExtraThemeRes(int i) {
        this.mExtraThemeRes = i;
    }

    public void setOnStatusBarChangeListener(OnStatusBarChangeListener onStatusBarChangeListener) {
        View view = this.mSubDecor;
        if (view != null && (view instanceof ActionBarOverlayLayout)) {
            ((ActionBarOverlayLayout) view).setOnStatusBarChangeListener(onStatusBarChangeListener);
        }
    }

    @Override // com.miui.internal.app.ActionBarDelegateImpl, com.miui.internal.app.ActionBarDelegate
    public ActionMode startActionMode(ActionMode.Callback callback) {
        return this.mSubDecor.startActionMode(callback);
    }

    public void updateOptionsMenu(int i) {
        this.mInvalidateMenuFlags = (byte) ((i & 1) | this.mInvalidateMenuFlags);
    }
}
