package com.android.camera.fragment.top;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import androidx.recyclerview.widget.RecyclerView;
import com.android.camera.R;
import com.android.camera.Util;
import com.android.camera.animation.FolmeUtils;
import com.android.camera.data.DataRepository;
import com.android.camera.data.data.ComponentData;
import com.android.camera.data.data.ComponentDataItem;
import com.android.camera.data.data.global.DataItemGlobal;
import com.android.camera.fragment.CommonRecyclerViewHolder;
import java.util.ArrayList;
import java.util.List;

public class TopExpandAdapter extends RecyclerView.Adapter<CommonRecyclerViewHolder> implements View.OnClickListener {
    private int mAnchorViewX;
    private ComponentData mComponentData;
    private int mCurrentMode;
    private String mCurrentValue;
    private List<ComponentDataItem> mDatas;
    private int mDefaultSelectPosition;
    private ExpandListener mExpandListener;
    private int mSelectPosition = -1;

    public interface ExpandListener {
        ImageView getTopImage(int i);

        void onExpandValueChange(ComponentData componentData, String str, String str2);
    }

    public TopExpandAdapter(ComponentData componentData, ExpandListener expandListener) {
        this.mComponentData = componentData;
        this.mDatas = new ArrayList(componentData.getItems());
        this.mExpandListener = expandListener;
        this.mCurrentMode = ((DataItemGlobal) DataRepository.provider().dataGlobal()).getCurrentMode();
        this.mCurrentValue = this.mComponentData.getComponentValue(this.mCurrentMode);
    }

    static /* synthetic */ void a(LabelItemView labelItemView, String str, String str2) {
        labelItemView.setContentDescription(str + ", " + str2);
        labelItemView.sendAccessibilityEvent(128);
    }

    public int getAnchorViewX() {
        return this.mAnchorViewX;
    }

    public ComponentData getComponentData() {
        return this.mComponentData;
    }

    public int getCount() {
        return this.mDatas.size();
    }

    public int getDefaultSelectPosition() {
        return this.mDefaultSelectPosition;
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public int getItemCount() {
        List<ComponentDataItem> list = this.mDatas;
        if (list == null) {
            return 0;
        }
        return list.size();
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public int getItemViewType(int i) {
        if (this.mDatas == null) {
            return 0;
        }
        return this.mComponentData.getDisplayTitleString();
    }

    public ComponentDataItem getSelectComponentDataItem() {
        return this.mDatas.get(getSelectPosition());
    }

    public int getSelectPosition() {
        int i = this.mSelectPosition;
        return i == -1 ? this.mDefaultSelectPosition : i;
    }

    public void onBindViewHolder(CommonRecyclerViewHolder commonRecyclerViewHolder, int i) {
        ComponentDataItem componentDataItem = this.mDatas.get(i);
        LabelItemView labelItemView = (LabelItemView) commonRecyclerViewHolder.itemView;
        String string = labelItemView.getResources().getString(componentDataItem.mDisplayNameRes);
        labelItemView.setTag(R.integer.expend_view_value_tag, componentDataItem.mValue);
        labelItemView.setTag(R.integer.expend_view_select_position_tag, Integer.valueOf(i));
        labelItemView.setMainImageResource(componentDataItem.mIconRes);
        labelItemView.setMainImageShadowResource(componentDataItem.mIconShadowRes);
        labelItemView.setMainViewVisibility(0);
        if (componentDataItem.mIconLabelRes > 0) {
            labelItemView.setLabelViewVisibility(0);
            labelItemView.setLabelImageResource(componentDataItem.mIconLabelRes);
            labelItemView.setLabelImageShadowResource(componentDataItem.mIconShadowLabelRes);
        } else {
            labelItemView.setLabelViewVisibility(8);
        }
        labelItemView.setOnClickListener(this);
        FolmeUtils.touchTint(labelItemView);
        if (this.mCurrentValue.equals(componentDataItem.mValue)) {
            this.mDefaultSelectPosition = i;
        }
        if (!this.mCurrentValue.equals(componentDataItem.mValue)) {
            labelItemView.setContentDescription(string);
        } else if (Util.isAccessible()) {
            labelItemView.postDelayed(new o(labelItemView, string, commonRecyclerViewHolder.itemView.getResources().getString(R.string.accessibility_selected)), 100);
        }
    }

    public void onClick(View view) {
        String str = (String) view.getTag(R.integer.expend_view_value_tag);
        Integer num = (Integer) view.getTag(R.integer.expend_view_select_position_tag);
        if (num != null) {
            this.mSelectPosition = num.intValue();
        }
        if (str != null && !str.equals(this.mCurrentValue)) {
            this.mComponentData.setComponentValue(this.mCurrentMode, str);
        }
        ExpandListener expandListener = this.mExpandListener;
        if (expandListener != null) {
            expandListener.onExpandValueChange(this.mComponentData, this.mCurrentValue, str);
            this.mExpandListener = null;
        }
        this.mCurrentValue = str;
        notifyDataSetChanged();
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public CommonRecyclerViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        return new CommonRecyclerViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.top_expanded_item, viewGroup, false));
    }

    public void setAnchorViewX(int i) {
        this.mAnchorViewX = i;
    }
}
