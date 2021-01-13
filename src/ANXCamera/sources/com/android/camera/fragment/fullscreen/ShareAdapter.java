package com.android.camera.fragment.fullscreen;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import com.android.camera.R;
import com.android.camera.fragment.CommonRecyclerViewHolder;
import java.util.List;

public class ShareAdapter extends RecyclerView.Adapter<CommonRecyclerViewHolder> {
    private int mItemWidth;
    private View.OnClickListener mOnClickListener;
    private List<ShareInfo> mShareInfoList;

    public ShareAdapter(List<ShareInfo> list, View.OnClickListener onClickListener, int i) {
        this.mShareInfoList = list;
        this.mOnClickListener = onClickListener;
        this.mItemWidth = i;
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public int getItemCount() {
        return this.mShareInfoList.size();
    }

    public void onBindViewHolder(CommonRecyclerViewHolder commonRecyclerViewHolder, int i) {
        ShareInfo shareInfo = this.mShareInfoList.get(i);
        commonRecyclerViewHolder.itemView.setTag(shareInfo);
        commonRecyclerViewHolder.itemView.setOnClickListener(this.mOnClickListener);
        ((ImageView) commonRecyclerViewHolder.getView(R.id.live_share_icon)).setImageResource(shareInfo.drawableRes);
        ((TextView) commonRecyclerViewHolder.getView(R.id.live_share_name)).setText(shareInfo.textRes);
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public CommonRecyclerViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View inflate = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.fragment_live_share_item, viewGroup, false);
        inflate.getLayoutParams().width = this.mItemWidth;
        return new CommonRecyclerViewHolder(inflate);
    }

    public void setShareInfoList(List<ShareInfo> list) {
        this.mShareInfoList = list;
    }
}
