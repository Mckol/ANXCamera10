package com.android.camera.fragment.beauty;

import android.content.Context;
import android.util.AttributeSet;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.android.camera.log.Log;

public class LinearLayoutManagerWrapper extends LinearLayoutManager {
    private String mRecycleViewFrom;

    public LinearLayoutManagerWrapper(Context context, int i, boolean z, String str) {
        super(context, i, z);
        this.mRecycleViewFrom = str;
    }

    public LinearLayoutManagerWrapper(Context context, AttributeSet attributeSet, int i, int i2, String str) {
        super(context, attributeSet, i, i2);
        this.mRecycleViewFrom = str;
    }

    public LinearLayoutManagerWrapper(Context context, String str) {
        super(context);
        this.mRecycleViewFrom = str;
    }

    @Override // androidx.recyclerview.widget.RecyclerView.LayoutManager, androidx.recyclerview.widget.LinearLayoutManager
    public void onLayoutChildren(RecyclerView.Recycler recycler, RecyclerView.State state) {
        try {
            super.onLayoutChildren(recycler, state);
        } catch (IndexOutOfBoundsException unused) {
            Log.i("LinearLayoutManagerWrapper", "IndexOutOfBoundsException from bussiness:" + this.mRecycleViewFrom);
        }
    }
}
