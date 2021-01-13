package miui.widget;

import android.content.Context;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import com.miui.internal.view.menu.MenuBuilder;
import com.miui.internal.view.menu.MenuPopupHelper;
import com.miui.internal.view.menu.MenuPresenter;
import com.miui.internal.view.menu.SubMenuBuilder;

public class PopupMenu {
    private View mAnchor;
    private Context mContext;
    private OnDismissListener mDismissListener;
    private MenuBuilder mMenu;
    private OnMenuItemClickListener mMenuItemClickListener;
    private MenuPopupHelper mPopup;

    public interface OnDismissListener {
        void onDismiss(PopupMenu popupMenu);
    }

    public interface OnMenuItemClickListener {
        boolean onMenuItemClick(MenuItem menuItem);
    }

    public PopupMenu(Context context, View view) {
        this.mContext = context;
        this.mMenu = new MenuBuilder(context);
        this.mMenu.setCallback(new MenuBuilder.Callback() {
            /* class miui.widget.PopupMenu.AnonymousClass1 */

            @Override // com.miui.internal.view.menu.MenuBuilder.Callback
            public boolean onMenuItemSelected(MenuBuilder menuBuilder, MenuItem menuItem) {
                return PopupMenu.this.onMenuItemSelected(menuBuilder, menuItem);
            }

            @Override // com.miui.internal.view.menu.MenuBuilder.Callback
            public void onMenuModeChange(MenuBuilder menuBuilder) {
                PopupMenu.this.onMenuModeChange(menuBuilder);
            }
        });
        this.mAnchor = view;
        this.mPopup = new MenuPopupHelper(context, this.mMenu, view);
        this.mPopup.setCallback(new MenuPresenter.Callback() {
            /* class miui.widget.PopupMenu.AnonymousClass2 */

            @Override // com.miui.internal.view.menu.MenuPresenter.Callback
            public void onCloseMenu(MenuBuilder menuBuilder, boolean z) {
                PopupMenu.this.onCloseMenu(menuBuilder, z);
            }

            @Override // com.miui.internal.view.menu.MenuPresenter.Callback
            public boolean onOpenSubMenu(MenuBuilder menuBuilder) {
                return PopupMenu.this.onOpenSubMenu(menuBuilder);
            }
        });
    }

    public void dismiss() {
        this.mPopup.dismiss(false);
    }

    public Menu getMenu() {
        return this.mMenu;
    }

    public MenuInflater getMenuInflater() {
        return new MenuInflater(this.mContext);
    }

    public void inflate(int i) {
        getMenuInflater().inflate(i, this.mMenu);
    }

    public void onCloseMenu(MenuBuilder menuBuilder, boolean z) {
        OnDismissListener onDismissListener = this.mDismissListener;
        if (onDismissListener != null) {
            onDismissListener.onDismiss(this);
        }
    }

    public void onCloseSubMenu(SubMenuBuilder subMenuBuilder) {
    }

    public boolean onMenuItemSelected(MenuBuilder menuBuilder, MenuItem menuItem) {
        OnMenuItemClickListener onMenuItemClickListener = this.mMenuItemClickListener;
        if (onMenuItemClickListener != null) {
            return onMenuItemClickListener.onMenuItemClick(menuItem);
        }
        return false;
    }

    public void onMenuModeChange(MenuBuilder menuBuilder) {
    }

    public boolean onOpenSubMenu(MenuBuilder menuBuilder) {
        if (menuBuilder == null) {
            return false;
        }
        if (!menuBuilder.hasVisibleItems()) {
            return true;
        }
        new MenuPopupHelper(this.mContext, menuBuilder, this.mAnchor).show();
        return true;
    }

    public void setOnDismissListener(OnDismissListener onDismissListener) {
        this.mDismissListener = onDismissListener;
    }

    public void setOnMenuItemClickListener(OnMenuItemClickListener onMenuItemClickListener) {
        this.mMenuItemClickListener = onMenuItemClickListener;
    }

    public void show() {
        this.mPopup.show();
    }
}
