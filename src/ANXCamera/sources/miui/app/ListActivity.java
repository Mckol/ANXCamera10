package miui.app;

import android.content.res.Configuration;
import android.os.Bundle;
import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import com.miui.internal.app.ActivityDelegate;

public class ListActivity extends android.app.ListActivity implements IActivity {
    private ActivityDelegate mDelegate = new ActivityDelegate(this, android.app.ListActivity.class);

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

    @Override // miui.app.IActivity
    public int getTranslucentStatus() {
        return this.mDelegate.getTranslucentStatus();
    }

    public void invalidateOptionsMenu() {
        this.mDelegate.invalidateOptionsMenu();
    }

    public void onActionModeFinished(ActionMode actionMode) {
        this.mDelegate.onActionModeFinished(actionMode);
    }

    public void onActionModeStarted(ActionMode actionMode) {
        this.mDelegate.onActionModeStarted(actionMode);
    }

    public void onBackPressed() {
        if (!this.mDelegate.onBackPressed()) {
            super.onBackPressed();
        }
    }

    public void onConfigurationChanged(Configuration configuration) {
        super.onConfigurationChanged(configuration);
        this.mDelegate.onConfigurationChanged(configuration);
    }

    /* access modifiers changed from: protected */
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        this.mDelegate.onCreate(bundle);
    }

    public boolean onCreatePanelMenu(int i, Menu menu) {
        return this.mDelegate.onCreatePanelMenu(i, menu);
    }

    public View onCreatePanelView(int i) {
        return i == 0 ? this.mDelegate.onCreatePanelView(i) : super.onCreatePanelView(i);
    }

    /* access modifiers changed from: protected */
    public void onDestroy() {
        super.onDestroy();
        this.mDelegate.dismissImmersionMenu(false);
    }

    public boolean onMenuItemSelected(int i, MenuItem menuItem) {
        return this.mDelegate.onMenuItemSelected(i, menuItem);
    }

    /* access modifiers changed from: protected */
    public void onPostResume() {
        super.onPostResume();
        this.mDelegate.onPostResume();
    }

    public boolean onPreparePanel(int i, View view, Menu menu) {
        return this.mDelegate.onPreparePanel(i, view, menu);
    }

    /* access modifiers changed from: protected */
    public void onRestoreInstanceState(Bundle bundle) {
        super.onRestoreInstanceState(bundle);
        this.mDelegate.onRestoreInstanceState(bundle);
    }

    /* access modifiers changed from: protected */
    public void onSaveInstanceState(Bundle bundle) {
        super.onSaveInstanceState(bundle);
        this.mDelegate.onSaveInstanceState(bundle);
    }

    /* access modifiers changed from: protected */
    public void onStop() {
        super.onStop();
        this.mDelegate.onStop();
    }

    /* access modifiers changed from: protected */
    public void onTitleChanged(CharSequence charSequence, int i) {
        super.onTitleChanged(charSequence, i);
        this.mDelegate.onTitleChanged(charSequence);
    }

    public ActionMode onWindowStartingActionMode(ActionMode.Callback callback) {
        return this.mDelegate.onWindowStartingActionMode(callback);
    }

    public boolean requestExtraWindowFeature(int i) {
        return this.mDelegate.requestWindowFeature(i);
    }

    @Override // miui.app.IImmersionMenu
    public void setImmersionMenuEnabled(boolean z) {
        this.mDelegate.setImmersionMenuEnabled(z);
    }

    @Override // miui.app.IActivity
    public void setTranslucentStatus(int i) {
        this.mDelegate.setTranslucentStatus(i);
    }

    @Override // miui.app.IImmersionMenu
    public void showImmersionMenu() {
        this.mDelegate.showImmersionMenu();
    }

    @Override // miui.app.IImmersionMenu
    public void showImmersionMenu(View view, ViewGroup viewGroup) {
        this.mDelegate.showImmersionMenu(view, viewGroup);
    }

    public ActionMode startActionMode(ActionMode.Callback callback) {
        return this.mDelegate.startActionMode(callback);
    }
}
