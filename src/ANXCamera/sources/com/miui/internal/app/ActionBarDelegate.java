package com.miui.internal.app;

import android.content.res.Configuration;
import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import miui.app.ActionBar;

/* access modifiers changed from: package-private */
public interface ActionBarDelegate {
    ActionBar createActionBar();

    void invalidateOptionsMenu();

    void onActionModeFinished(ActionMode actionMode);

    void onActionModeStarted(ActionMode actionMode);

    void onConfigurationChanged(Configuration configuration);

    boolean onCreatePanelMenu(int i, Menu menu);

    View onCreatePanelView(int i);

    boolean onMenuItemSelected(int i, MenuItem menuItem);

    void onPostResume();

    boolean onPreparePanel(int i, View view, Menu menu);

    void onStop();

    ActionMode onWindowStartingActionMode(ActionMode.Callback callback);

    boolean requestWindowFeature(int i);

    ActionMode startActionMode(ActionMode.Callback callback);
}
