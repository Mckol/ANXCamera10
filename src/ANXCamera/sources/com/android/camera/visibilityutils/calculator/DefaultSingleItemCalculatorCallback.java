package com.android.camera.visibilityutils.calculator;

import android.view.View;
import com.android.camera.visibilityutils.calculator.SingleListViewItemActiveCalculator;
import com.android.camera.visibilityutils.items.ListItem;

public class DefaultSingleItemCalculatorCallback implements SingleListViewItemActiveCalculator.Callback<ListItem> {
    private static final boolean SHOW_LOGS = false;
    private static final String TAG = "DefaultSingleItemCalculatorCallback";

    @Override // com.android.camera.visibilityutils.calculator.SingleListViewItemActiveCalculator.Callback
    public void activateNewCurrentItem(ListItem listItem, View view, int i) {
        listItem.setActive(view, i);
    }

    @Override // com.android.camera.visibilityutils.calculator.SingleListViewItemActiveCalculator.Callback
    public void deactivateCurrentItem(ListItem listItem, View view, int i) {
        listItem.deactivate(view, i);
    }
}
