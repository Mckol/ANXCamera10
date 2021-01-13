package miui.preference;

import android.content.Context;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.ActionMode;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import com.miui.internal.R;
import com.miui.internal.app.FragmentDelegate;
import miui.app.ActionBar;
import miui.app.IFragment;
import miui.util.AttributeResolver;

public class PreferenceFragment extends android.preference.PreferenceFragment implements IFragment {
    private FragmentDelegate mDelegate;
    private boolean mHasMenu = true;
    private boolean mMenuVisible = true;

    @Override // miui.app.IImmersionMenu
    public void dismissImmersionMenu(boolean z) {
        this.mDelegate.dismissImmersionMenu(z);
    }

    public ActionBar getActionBar() {
        return this.mDelegate.getActionBar();
    }

    public MenuInflater getMenuInflater() {
        return this.mDelegate.getMenuInflater();
    }

    @Override // miui.app.IFragment
    public Context getThemedContext() {
        return this.mDelegate.getThemedContext();
    }

    public View getView() {
        return this.mDelegate.getView();
    }

    public void invalidateOptionsMenu() {
        FragmentDelegate fragmentDelegate = this.mDelegate;
        if (fragmentDelegate != null) {
            fragmentDelegate.updateOptionsMenu(1);
            if (!isHidden() && this.mHasMenu && this.mMenuVisible && isAdded()) {
                this.mDelegate.invalidateOptionsMenu();
            }
        }
    }

    @Override // miui.app.IFragment
    public final void onActionModeFinished(ActionMode actionMode) {
    }

    @Override // miui.app.IFragment
    public final void onActionModeStarted(ActionMode actionMode) {
    }

    public void onConfigurationChanged(Configuration configuration) {
        super.onConfigurationChanged(configuration);
        this.mDelegate.onConfigurationChanged(configuration);
    }

    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        this.mDelegate = new FragmentDelegate(this);
        this.mDelegate.onCreate(bundle);
    }

    @Override // miui.app.IFragment
    public boolean onCreateOptionsMenu(Menu menu) {
        return true;
    }

    @Override // miui.app.IFragment
    public boolean onCreatePanelMenu(int i, Menu menu) {
        if (i != 0 || !this.mHasMenu || !this.mMenuVisible || isHidden() || !isAdded()) {
            return false;
        }
        return onCreateOptionsMenu(menu);
    }

    public final View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        View onCreateView = this.mDelegate.onCreateView(viewGroup, bundle);
        ListView listView = (ListView) onCreateView.findViewById(16908298);
        if (listView != null) {
            listView.setClipToPadding(false);
            listView.setPadding(0, 0, 0, (int) AttributeResolver.resolveDimension(onCreateView.getContext(), R.attr.preferenceScreenPaddingBottom));
        }
        return onCreateView;
    }

    public void onDestroy() {
        super.onDestroy();
        this.mDelegate.dismissImmersionMenu(false);
    }

    public final void onHiddenChanged(boolean z) {
        FragmentDelegate fragmentDelegate;
        super.onHiddenChanged(z);
        if (!z && (fragmentDelegate = this.mDelegate) != null) {
            fragmentDelegate.invalidateOptionsMenu();
        }
        onVisibilityChanged(!z);
    }

    @Override // miui.app.IFragment
    public View onInflateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        return super.onCreateView(layoutInflater, viewGroup, bundle);
    }

    @Override // miui.app.IFragment
    public void onPreparePanel(int i, View view, Menu menu) {
        if (i == 0 && this.mHasMenu && this.mMenuVisible && !isHidden() && isAdded()) {
            onPrepareOptionsMenu(menu);
        }
    }

    public void onResume() {
        super.onResume();
        this.mDelegate.onPostResume();
    }

    public void onStop() {
        super.onStop();
        this.mDelegate.onStop();
    }

    public void onVisibilityChanged(boolean z) {
    }

    public boolean requestWindowFeature(int i) {
        return this.mDelegate.requestWindowFeature(i);
    }

    public void setHasOptionsMenu(boolean z) {
        FragmentDelegate fragmentDelegate;
        super.setHasOptionsMenu(z);
        if (this.mHasMenu != z) {
            this.mHasMenu = z;
            if (!isHidden() && isAdded() && (fragmentDelegate = this.mDelegate) != null) {
                fragmentDelegate.invalidateOptionsMenu();
            }
        }
    }

    @Override // miui.app.IImmersionMenu
    public void setImmersionMenuEnabled(boolean z) {
        this.mDelegate.setImmersionMenuEnabled(z);
    }

    public void setMenuVisibility(boolean z) {
        FragmentDelegate fragmentDelegate;
        super.setMenuVisibility(z);
        if (this.mMenuVisible != z) {
            this.mMenuVisible = z;
            if (!isHidden() && isAdded() && (fragmentDelegate = this.mDelegate) != null) {
                fragmentDelegate.invalidateOptionsMenu();
            }
        }
    }

    @Override // miui.app.IFragment
    public void setThemeRes(int i) {
        this.mDelegate.setExtraThemeRes(i);
    }

    @Override // miui.app.IImmersionMenu
    public void showImmersionMenu() {
        this.mDelegate.showImmersionMenu();
    }

    @Override // miui.app.IImmersionMenu
    public void showImmersionMenu(View view, ViewGroup viewGroup) {
        this.mDelegate.showImmersionMenu(view, viewGroup);
    }

    @Override // miui.app.IFragment
    public ActionMode startActionMode(ActionMode.Callback callback) {
        return this.mDelegate.startActionMode(callback);
    }

    public void updateOptionsMenuContent() {
        if (this.mDelegate != null && !isHidden() && this.mHasMenu && this.mMenuVisible && isAdded()) {
            this.mDelegate.invalidateOptionsMenu();
        }
    }
}
