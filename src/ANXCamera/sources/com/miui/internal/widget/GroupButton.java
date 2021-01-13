package com.miui.internal.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import com.miui.internal.R;
import miui.util.ViewUtils;

public class GroupButton extends Button {
    private static final int[] STATE_FIRST_H = {R.attr.state_first_h};
    private static final int[] STATE_FIRST_V = {R.attr.state_first_v};
    private static final int[] STATE_LAST_H = {R.attr.state_last_h};
    private static final int[] STATE_LAST_V = {R.attr.state_last_v};
    private static final int[] STATE_MIDDLE_H = {R.attr.state_middle_h};
    private static final int[] STATE_MIDDLE_V = {R.attr.state_middle_v};
    private static final int[] STATE_SINGLE_H = {R.attr.state_single_h};

    public GroupButton(Context context) {
        super(context);
    }

    public GroupButton(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    public GroupButton(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
    }

    /* access modifiers changed from: protected */
    public int[] onCreateDrawableState(int i) {
        ViewGroup viewGroup = (ViewGroup) getParent();
        if (viewGroup == null) {
            return super.onCreateDrawableState(i);
        }
        if (!(viewGroup instanceof LinearLayout)) {
            return super.onCreateDrawableState(i);
        }
        int orientation = ((LinearLayout) viewGroup).getOrientation();
        int indexOfChild = viewGroup.indexOfChild(this);
        boolean z = false;
        int i2 = 0;
        boolean z2 = true;
        boolean z3 = true;
        for (int i3 = 0; i3 < viewGroup.getChildCount(); i3++) {
            if (viewGroup.getChildAt(i3).getVisibility() == 0) {
                i2++;
                if (i3 < indexOfChild) {
                    z2 = false;
                }
                if (i3 > indexOfChild) {
                    z3 = false;
                }
            }
        }
        if (i2 == 1) {
            z = true;
        }
        if (orientation == 1) {
            int[] onCreateDrawableState = super.onCreateDrawableState(i + 2);
            Button.mergeDrawableStates(onCreateDrawableState, STATE_SINGLE_H);
            if (!z) {
                if (z2) {
                    Button.mergeDrawableStates(onCreateDrawableState, STATE_FIRST_V);
                } else if (z3) {
                    Button.mergeDrawableStates(onCreateDrawableState, STATE_LAST_V);
                } else {
                    Button.mergeDrawableStates(onCreateDrawableState, STATE_MIDDLE_V);
                }
            }
            return onCreateDrawableState;
        }
        boolean isLayoutRtl = ViewUtils.isLayoutRtl(this);
        int[] onCreateDrawableState2 = super.onCreateDrawableState(i + 1);
        if (z) {
            Button.mergeDrawableStates(onCreateDrawableState2, STATE_SINGLE_H);
        } else if (z2) {
            Button.mergeDrawableStates(onCreateDrawableState2, isLayoutRtl ? STATE_LAST_H : STATE_FIRST_H);
        } else if (z3) {
            Button.mergeDrawableStates(onCreateDrawableState2, isLayoutRtl ? STATE_FIRST_H : STATE_LAST_H);
        } else {
            Button.mergeDrawableStates(onCreateDrawableState2, STATE_MIDDLE_H);
        }
        return onCreateDrawableState2;
    }
}
