package com.android.camera.fragment.manually.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import com.android.camera.R;
import com.android.camera.constant.ColorConstant;
import com.android.camera.data.data.ComponentData;
import com.android.camera.data.data.ComponentDataItem;
import com.android.camera.fragment.CommonRecyclerViewHolder;
import com.android.camera.fragment.manually.ManuallyListener;

public class ManuallySingleAdapter extends RecyclerView.Adapter<CommonRecyclerViewHolder> implements View.OnClickListener {
    private ComponentData mComponentData;
    private int mCurrentMode;
    private String mCurrentValue;
    private ManuallyListener mManuallyListener;

    public ManuallySingleAdapter(ComponentData componentData, int i, ManuallyListener manuallyListener, int i2) {
        this.mComponentData = componentData;
        this.mCurrentMode = i;
        this.mManuallyListener = manuallyListener;
        this.mCurrentValue = componentData.getComponentValue(this.mCurrentMode);
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public int getItemCount() {
        return this.mComponentData.getItems().size();
    }

    public int getValuePosition() {
        int itemCount = getItemCount();
        for (int i = 0; i < itemCount; i++) {
            if (this.mCurrentValue.equals(this.mComponentData.getItems().get(i).mValue)) {
                return i;
            }
        }
        return -1;
    }

    public void onBindViewHolder(CommonRecyclerViewHolder commonRecyclerViewHolder, int i) {
        ComponentDataItem componentDataItem = this.mComponentData.getItems().get(i);
        String str = componentDataItem.mValue;
        commonRecyclerViewHolder.itemView.setOnClickListener(this);
        commonRecyclerViewHolder.itemView.setTag(str);
        TextView textView = (TextView) commonRecyclerViewHolder.getView(R.id.manually_item_key);
        commonRecyclerViewHolder.getView(R.id.manually_item_value).setVisibility(8);
        commonRecyclerViewHolder.getView(R.id.manually_item_value_image).setVisibility(8);
        textView.setText(componentDataItem.mDisplayNameRes);
        textView.setTextColor(str.equals(this.mCurrentValue) ? ColorConstant.COLOR_COMMON_SELECTED : -1);
    }

    public void onClick(View view) {
        String str = (String) view.getTag();
        if (str != null && !str.equals(this.mCurrentValue)) {
            this.mComponentData.setComponentValue(this.mCurrentMode, str);
            notifyDataSetChanged();
            ManuallyListener manuallyListener = this.mManuallyListener;
            if (manuallyListener != null) {
                manuallyListener.onManuallyDataChanged(this.mComponentData, this.mCurrentValue, str, false, this.mCurrentMode);
            }
            this.mCurrentValue = str;
        }
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public CommonRecyclerViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View inflate = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.fragment_manually_item, viewGroup, false);
        inflate.getLayoutParams().width = inflate.getResources().getDisplayMetrics().widthPixels / getItemCount();
        return new CommonRecyclerViewHolder(inflate);
    }
}
