package com.miui.internal.view;

import android.content.res.Resources;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import com.miui.internal.R;
import com.miui.internal.view.CheckWidgetAnimatedStateListDrawable;

public class RadioButtonAnimatedStateListDrawable extends CheckBoxAnimatedStateListDrawable {
    private int mDrawPadding = 19;

    protected static class RadioButtonConstantState extends CheckWidgetAnimatedStateListDrawable.CheckWidgetConstantState {
        protected RadioButtonConstantState() {
        }

        /* access modifiers changed from: protected */
        @Override // com.miui.internal.view.CheckWidgetAnimatedStateListDrawable.CheckWidgetConstantState
        public Drawable newAnimatedStateListDrawable(Resources resources, Resources.Theme theme, CheckWidgetAnimatedStateListDrawable.CheckWidgetConstantState checkWidgetConstantState) {
            return new RadioButtonAnimatedStateListDrawable(resources, theme, checkWidgetConstantState);
        }
    }

    public RadioButtonAnimatedStateListDrawable() {
    }

    public RadioButtonAnimatedStateListDrawable(Resources resources, Resources.Theme theme, CheckWidgetAnimatedStateListDrawable.CheckWidgetConstantState checkWidgetConstantState) {
        super(resources, theme, checkWidgetConstantState);
        if (resources != null) {
            this.mDrawPadding = resources.getDimensionPixelSize(R.dimen.radio_button_drawable_padding);
        }
    }

    /* access modifiers changed from: protected */
    @Override // com.miui.internal.view.CheckBoxAnimatedStateListDrawable
    public int getCheckWidgetDrawableStyle() {
        return R.style.CheckWidgetDrawable_RadioButton;
    }

    /* access modifiers changed from: protected */
    @Override // com.miui.internal.view.CheckBoxAnimatedStateListDrawable
    public boolean isSingleSelectionWidget() {
        return true;
    }

    /* access modifiers changed from: protected */
    @Override // com.miui.internal.view.CheckWidgetAnimatedStateListDrawable, com.miui.internal.view.CheckBoxAnimatedStateListDrawable
    public CheckWidgetAnimatedStateListDrawable.CheckWidgetConstantState newCheckWidgetConstantState() {
        return new RadioButtonConstantState();
    }

    /* access modifiers changed from: protected */
    @Override // com.miui.internal.view.CheckBoxAnimatedStateListDrawable
    public void setCheckWidgetDrawableBounds(int i, int i2, int i3, int i4) {
        int i5 = this.mDrawPadding;
        super.setCheckWidgetDrawableBounds(i + i5, i2 + i5, i3 - i5, i4 - i5);
    }

    /* access modifiers changed from: protected */
    @Override // com.miui.internal.view.CheckBoxAnimatedStateListDrawable
    public void setCheckWidgetDrawableBounds(Rect rect) {
        int i = this.mDrawPadding;
        rect.inset(i, i);
        super.setCheckWidgetDrawableBounds(rect);
    }
}
