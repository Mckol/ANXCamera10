package com.android.camera.fragment;

import android.util.SparseArray;
import android.view.View;
import androidx.recyclerview.widget.RecyclerView;

public class CommonRecyclerViewHolder extends RecyclerView.ViewHolder {
    private final SparseArray<View> mViews = new SparseArray<>();

    public CommonRecyclerViewHolder(View view) {
        super(view);
    }

    public <T extends View> T getView(int i) {
        T t = (T) this.mViews.get(i);
        if (t != null) {
            return t;
        }
        T t2 = (T) this.itemView.findViewById(i);
        this.mViews.put(i, t2);
        return t2;
    }
}
