package com.miui.internal.view.menu;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import com.miui.internal.view.menu.ActionMenuView;

public class TabletActionMenuView extends ActionMenuView {
    public TabletActionMenuView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    @Override // com.miui.internal.view.menu.ActionMenuView, com.miui.internal.view.menu.MenuView
    public boolean filterLeftoverView(int i) {
        ActionMenuView.LayoutParams layoutParams = (ActionMenuView.LayoutParams) getChildAt(i).getLayoutParams();
        if (layoutParams == null || !layoutParams.isOverflowButton) {
            return super.filterLeftoverView(i);
        }
        return false;
    }

    @Override // com.miui.internal.view.menu.ActionMenuView
    public int getCollapsedHeight() {
        return getMeasuredHeight();
    }

    public boolean onTouchEvent(MotionEvent motionEvent) {
        return true;
    }
}
