package com.android.camera.features.mimoji2.widget.baseview;

import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.android.camera.log.Log;
import java.util.ArrayList;
import java.util.List;
import miui.view.animation.CubicEaseOutInterpolator;

public abstract class BaseRecyclerAdapter<T> extends RecyclerView.Adapter<BaseRecyclerViewHolder> {
    public static final String TAG = "BaseRecyclerAdapter";
    private CubicEaseOutInterpolator mCubicEaseOut = new CubicEaseOutInterpolator();
    private List<T> mDdataList;
    private int mDegree;
    private OnRecyclerItemClickListener<T> onRecyclerItemClickListener;

    public BaseRecyclerAdapter(List<T> list) {
        this.mDdataList = list;
    }

    public synchronized void addData(T t) {
        if (this.mDdataList == null) {
            this.mDdataList = new ArrayList();
        }
        int size = this.mDdataList.size();
        if (this.mDdataList.add(t)) {
            notifyItemInserted(size);
        }
    }

    public synchronized void addData(T t, int i) {
        if (this.mDdataList != null) {
            if (i <= this.mDdataList.size()) {
                this.mDdataList.add(i, t);
                notifyItemInserted(i);
            }
        }
    }

    public CubicEaseOutInterpolator getCubicEaseOut() {
        return this.mCubicEaseOut;
    }

    public List<T> getDataList() {
        return this.mDdataList;
    }

    public int getDegree() {
        return this.mDegree;
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public int getItemCount() {
        List<T> list = this.mDdataList;
        if (list == null) {
            return 0;
        }
        return list.size();
    }

    public boolean isAvailablePosion(int i) {
        return getDataList() != null && i < getItemCount() && i >= 0;
    }

    /* JADX DEBUG: Method arguments types fixed to match base method, original types: [androidx.recyclerview.widget.RecyclerView$ViewHolder, int, java.util.List] */
    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public /* bridge */ /* synthetic */ void onBindViewHolder(@NonNull BaseRecyclerViewHolder baseRecyclerViewHolder, int i, @NonNull List list) {
        onBindViewHolder(baseRecyclerViewHolder, i, (List<Object>) list);
    }

    public void onBindViewHolder(@NonNull BaseRecyclerViewHolder baseRecyclerViewHolder, int i) {
    }

    public void onBindViewHolder(@NonNull BaseRecyclerViewHolder baseRecyclerViewHolder, int i, @NonNull List<Object> list) {
        List<T> list2 = this.mDdataList;
        if (list2 != null && i <= list2.size() - 1) {
            T t = this.mDdataList.get(i);
            if (t == null) {
                Log.e(TAG, "data null error");
            }
            if ((list == null || list.isEmpty()) && baseRecyclerViewHolder.getRotateViews() != null) {
                for (View view : baseRecyclerViewHolder.getRotateViews()) {
                    view.setRotation((float) getDegree());
                }
            }
            baseRecyclerViewHolder.setData(t, i);
            OnRecyclerItemClickListener<T> onRecyclerItemClickListener2 = this.onRecyclerItemClickListener;
            if (onRecyclerItemClickListener2 != null) {
                baseRecyclerViewHolder.setClickListener(onRecyclerItemClickListener2, t, i, baseRecyclerViewHolder.itemView);
            }
        }
    }

    /* access modifiers changed from: protected */
    public abstract BaseRecyclerViewHolder<T> onCreateBaseRecyclerViewHolder(@NonNull ViewGroup viewGroup, int i);

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    @NonNull
    public BaseRecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return onCreateBaseRecyclerViewHolder(viewGroup, i);
    }

    public synchronized void removeData(int i) {
        if (this.mDdataList != null && i >= 0) {
            if (i <= this.mDdataList.size() - 1) {
                this.mDdataList.remove(i);
                notifyItemRemoved(i);
            }
        }
    }

    public synchronized void setDataList(List<T> list) {
        this.mDdataList = list;
        notifyDataSetChanged();
    }

    public void setOnRecyclerItemClickListener(OnRecyclerItemClickListener<T> onRecyclerItemClickListener2) {
        this.onRecyclerItemClickListener = onRecyclerItemClickListener2;
    }

    public void setRotation(int i) {
        this.mDegree = i;
    }

    public synchronized void updateData(int i, T t) {
        if (this.mDdataList == null) {
            if (i == 0) {
                addData(t);
            }
        } else if (i >= 0 && i <= this.mDdataList.size()) {
            if (i == this.mDdataList.size()) {
                addData(t);
                return;
            }
            this.mDdataList.set(i, t);
            notifyItemChanged(i);
        }
    }
}
