package com.android.camera.fragment.manually.adapter;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import com.android.camera.R;
import com.android.camera.data.data.ComponentDataItem;
import com.android.camera.fragment.CommonRecyclerViewHolder;
import java.util.List;

public class ParameterDescriptionAdapter extends RecyclerView.Adapter<CommonRecyclerViewHolder> {
    private Context mContext;
    private List<ComponentDataItem> mParameterDescriptionList;

    public ParameterDescriptionAdapter(Context context, List<ComponentDataItem> list) {
        this.mContext = context;
        this.mParameterDescriptionList = list;
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public int getItemCount() {
        return this.mParameterDescriptionList.size();
    }

    public void onBindViewHolder(CommonRecyclerViewHolder commonRecyclerViewHolder, int i) {
        ComponentDataItem componentDataItem = this.mParameterDescriptionList.get(i);
        ((ImageView) commonRecyclerViewHolder.getView(R.id.parameter_description_icon)).setImageResource(componentDataItem.mIconRes);
        ((TextView) commonRecyclerViewHolder.getView(R.id.parameter_description_title)).setText(componentDataItem.mDisplayNameRes);
        ((TextView) commonRecyclerViewHolder.getView(R.id.parameter_description_content)).setText(componentDataItem.mValue);
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public CommonRecyclerViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View inflate = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.fragment_manually_description_adapter, viewGroup, false);
        ((TextView) inflate.findViewById(R.id.parameter_description_title)).setTypeface(Typeface.create("mipro-medium", 0));
        ((TextView) inflate.findViewById(R.id.parameter_description_content)).setTypeface(Typeface.create("mipro-regular", 0));
        return new CommonRecyclerViewHolder(inflate);
    }
}
