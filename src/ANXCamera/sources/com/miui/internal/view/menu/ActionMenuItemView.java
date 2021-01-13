package com.miui.internal.view.menu;

import android.content.Context;
import android.content.res.Configuration;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.miui.internal.R;
import com.miui.internal.util.FolmeAnimHelper;
import com.miui.internal.view.menu.MenuBuilder;
import com.miui.internal.view.menu.MenuView;

public class ActionMenuItemView extends LinearLayout implements MenuView.ItemView {
    private ImageView mImageView;
    private boolean mIsCheckable;
    private MenuItemImpl mItemData;
    private MenuBuilder.ItemInvoker mItemInvoker;
    private TextView mTextView;

    public ActionMenuItemView(Context context) {
        this(context, null, 0);
    }

    public ActionMenuItemView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public ActionMenuItemView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        FolmeAnimHelper.addAlphaPressAnim(this);
        setOrientation(1);
        setGravity(1);
        this.mImageView = new ImageView(context, null, R.attr.actionBarButtonIconViewStyle);
        addView(this.mImageView);
        this.mTextView = new TextView(context, null, R.attr.actionBarButtonTextViewStyle);
        addView(this.mTextView);
        if (getContext().getResources().getConfiguration().orientation == 1) {
            this.mTextView.setVisibility(0);
        } else {
            this.mTextView.setVisibility(8);
        }
    }

    @Override // com.miui.internal.view.menu.MenuView.ItemView
    public MenuItemImpl getItemData() {
        return this.mItemData;
    }

    @Override // com.miui.internal.view.menu.MenuView.ItemView
    public void initialize(MenuItemImpl menuItemImpl, int i) {
        this.mItemData = menuItemImpl;
        setSelected(false);
        setTitle(menuItemImpl.getTitle());
        setIcon(menuItemImpl.getIcon());
        setCheckable(menuItemImpl.isCheckable());
        setChecked(menuItemImpl.isChecked());
        setEnabled(menuItemImpl.isEnabled());
        setClickable(true);
    }

    /* access modifiers changed from: protected */
    public void onConfigurationChanged(Configuration configuration) {
        super.onConfigurationChanged(configuration);
        if (getContext().getResources().getConfiguration().orientation == 1) {
            this.mTextView.setVisibility(0);
        } else {
            this.mTextView.setVisibility(8);
        }
        setPaddingRelative(getPaddingStart(), getResources().getDimensionPixelSize(R.dimen.action_button_bg_top_padding), getPaddingEnd(), getResources().getDimensionPixelSize(R.dimen.action_button_bg_bottom_padding));
    }

    public boolean performClick() {
        if (super.performClick()) {
            return true;
        }
        MenuBuilder.ItemInvoker itemInvoker = this.mItemInvoker;
        if (itemInvoker == null || !itemInvoker.invokeItem(this.mItemData)) {
            return false;
        }
        playSoundEffect(0);
        return true;
    }

    @Override // com.miui.internal.view.menu.MenuView.ItemView
    public boolean prefersCondensedTitle() {
        return false;
    }

    @Override // com.miui.internal.view.menu.MenuView.ItemView
    public void setCheckable(boolean z) {
        this.mIsCheckable = z;
    }

    @Override // com.miui.internal.view.menu.MenuView.ItemView
    public void setChecked(boolean z) {
        if (this.mIsCheckable) {
            setSelected(z);
        }
    }

    @Override // com.miui.internal.view.menu.MenuView.ItemView
    public void setEnabled(boolean z) {
        super.setEnabled(z);
        this.mImageView.setEnabled(z);
        this.mTextView.setEnabled(z);
    }

    @Override // com.miui.internal.view.menu.MenuView.ItemView
    public void setIcon(Drawable drawable) {
        if (this.mImageView.getDrawable() != drawable) {
            this.mImageView.setImageDrawable(drawable);
        }
    }

    @Override // com.miui.internal.view.menu.MenuView.ItemView
    public void setItemInvoker(MenuBuilder.ItemInvoker itemInvoker) {
        this.mItemInvoker = itemInvoker;
    }

    @Override // com.miui.internal.view.menu.MenuView.ItemView
    public void setShortcut(boolean z, char c2) {
    }

    @Override // com.miui.internal.view.menu.MenuView.ItemView
    public void setTitle(CharSequence charSequence) {
        this.mTextView.setText(charSequence);
    }

    @Override // com.miui.internal.view.menu.MenuView.ItemView
    public boolean showsIcon() {
        return true;
    }
}
