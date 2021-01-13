package com.miui.internal.view.menu;

import android.content.Context;
import android.view.View;
import com.miui.internal.R;

public class ImmersionMenuAdapterHelper {
    public static void calcAndSetPadding(Context context, int i, int i2, View view) {
        int i3;
        int i4;
        int paddingStart = view.getPaddingStart();
        view.getPaddingTop();
        int paddingEnd = view.getPaddingEnd();
        view.getPaddingBottom();
        if (i == 1) {
            i3 = context.getResources().getDimensionPixelSize(R.dimen.drop_down_menu_padding_small);
            i4 = context.getResources().getDimensionPixelSize(R.dimen.drop_down_menu_padding_small);
        } else if (i2 == 0) {
            i3 = context.getResources().getDimensionPixelSize(R.dimen.drop_down_menu_padding_large);
            i4 = context.getResources().getDimensionPixelSize(R.dimen.drop_down_menu_padding_small);
        } else if (i2 == i - 1) {
            i3 = context.getResources().getDimensionPixelSize(R.dimen.drop_down_menu_padding_small);
            i4 = context.getResources().getDimensionPixelSize(R.dimen.drop_down_menu_padding_large);
        } else {
            i3 = context.getResources().getDimensionPixelSize(R.dimen.drop_down_menu_padding_small);
            i4 = context.getResources().getDimensionPixelSize(R.dimen.drop_down_menu_padding_small);
        }
        view.setPaddingRelative(paddingStart, i3, paddingEnd, i4);
    }
}