package com.miui.internal.view.menu;

import android.graphics.drawable.Drawable;
import com.miui.internal.view.menu.MenuBuilder;

public interface MenuView {

    public interface ItemView {
        MenuItemImpl getItemData();

        void initialize(MenuItemImpl menuItemImpl, int i);

        boolean prefersCondensedTitle();

        void setCheckable(boolean z);

        void setChecked(boolean z);

        void setEnabled(boolean z);

        void setIcon(Drawable drawable);

        void setItemInvoker(MenuBuilder.ItemInvoker itemInvoker);

        void setShortcut(boolean z, char c2);

        void setTitle(CharSequence charSequence);

        boolean showsIcon();
    }

    boolean filterLeftoverView(int i);

    int getWindowAnimations();

    boolean hasBackgroundView();

    boolean hasBlurBackgroundView();

    void initialize(MenuBuilder menuBuilder);
}
