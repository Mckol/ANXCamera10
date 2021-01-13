package com.miui.internal.view.menu.context;

import android.content.Context;
import android.view.Menu;
import android.view.MenuItem;
import com.miui.internal.view.menu.ImmersionMenuAdapter;

public class ContextMenuAdapter extends ImmersionMenuAdapter {
    private MenuItem mLastCategorySystemOrderMenuItem;

    protected ContextMenuAdapter(Context context, Menu menu) {
        super(context, menu);
    }

    /* access modifiers changed from: protected */
    @Override // com.miui.internal.view.menu.ImmersionMenuAdapter
    public boolean checkMenuItem(MenuItem menuItem) {
        boolean checkMenuItem = super.checkMenuItem(menuItem);
        if (!checkMenuItem || menuItem.getOrder() != 131072) {
            return checkMenuItem;
        }
        if (this.mLastCategorySystemOrderMenuItem == null) {
            this.mLastCategorySystemOrderMenuItem = menuItem;
            return false;
        }
        throw new IllegalStateException("Only one menu item is allowed to have CATEGORY_SYSTEM order!");
    }

    /* access modifiers changed from: package-private */
    public MenuItem getLastCategorySystemOrderMenuItem() {
        return this.mLastCategorySystemOrderMenuItem;
    }
}
