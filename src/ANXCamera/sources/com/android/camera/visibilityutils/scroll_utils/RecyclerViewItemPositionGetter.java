package com.android.camera.visibilityutils.scroll_utils;

import android.view.View;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class RecyclerViewItemPositionGetter implements ItemsPositionGetter {
    private static final boolean SHOW_LOGS = false;
    private static final String TAG = "RecyclerViewItemPositionGetter";
    private LinearLayoutManager mLayoutManager;
    private RecyclerView mRecyclerView;

    public RecyclerViewItemPositionGetter(LinearLayoutManager linearLayoutManager, RecyclerView recyclerView) {
        this.mLayoutManager = linearLayoutManager;
        this.mRecyclerView = recyclerView;
    }

    @Override // com.android.camera.visibilityutils.scroll_utils.ItemsPositionGetter
    public View getChildAt(int i) {
        return this.mLayoutManager.getChildAt(i);
    }

    @Override // com.android.camera.visibilityutils.scroll_utils.ItemsPositionGetter
    public int getChildCount() {
        return this.mRecyclerView.getChildCount();
    }

    @Override // com.android.camera.visibilityutils.scroll_utils.ItemsPositionGetter
    public int getFirstVisiblePosition() {
        return this.mLayoutManager.findFirstVisibleItemPosition();
    }

    @Override // com.android.camera.visibilityutils.scroll_utils.ItemsPositionGetter
    public int getLastVisiblePosition() {
        return this.mLayoutManager.findLastVisibleItemPosition();
    }

    @Override // com.android.camera.visibilityutils.scroll_utils.ItemsPositionGetter
    public int indexOfChild(View view) {
        return this.mRecyclerView.indexOfChild(view);
    }
}
