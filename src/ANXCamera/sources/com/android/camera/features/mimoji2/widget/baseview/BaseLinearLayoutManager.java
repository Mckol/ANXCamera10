package com.android.camera.features.mimoji2.widget.baseview;

import android.content.Context;
import android.util.AttributeSet;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.android.camera.log.Log;

public class BaseLinearLayoutManager extends LinearLayoutManager {
    private BaseLinearSmoothScroller baseLinearSmoothScroller;

    public BaseLinearLayoutManager(Context context) {
        super(context);
    }

    public BaseLinearLayoutManager(Context context, int i, boolean z) {
        super(context, i, z);
    }

    public BaseLinearLayoutManager(Context context, AttributeSet attributeSet, int i, int i2) {
        super(context, attributeSet, i, i2);
    }

    @Override // androidx.recyclerview.widget.RecyclerView.LayoutManager, androidx.recyclerview.widget.LinearLayoutManager
    public void onLayoutChildren(RecyclerView.Recycler recycler, RecyclerView.State state) {
        try {
            super.onLayoutChildren(recycler, state);
        } catch (IndexOutOfBoundsException unused) {
            Log.i("BaseLinearLayoutManager", "IndexOutOfBoundsException ");
        }
    }

    @Override // androidx.recyclerview.widget.RecyclerView.LayoutManager, androidx.recyclerview.widget.LinearLayoutManager
    public void smoothScrollToPosition(RecyclerView recyclerView, RecyclerView.State state, int i) {
        if (this.baseLinearSmoothScroller == null) {
            this.baseLinearSmoothScroller = new BaseLinearSmoothScroller(recyclerView.getContext());
        }
        this.baseLinearSmoothScroller.setTargetPosition(i);
        startSmoothScroll(this.baseLinearSmoothScroller);
    }
}
