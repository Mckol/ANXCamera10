package com.android.camera.fragment.manually.adapter;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.StringRes;
import androidx.recyclerview.widget.RecyclerView;
import com.android.camera.CameraAppImpl;
import com.android.camera.R;
import com.android.camera.animation.FolmeUtils;
import com.android.camera.customization.TintColor;
import com.android.camera.data.data.ComponentData;
import com.android.camera.fragment.CommonRecyclerViewHolder;
import com.android.camera.ui.ColorImageView;
import java.util.List;

public class ManuallyAdapter extends RecyclerView.Adapter<CommonRecyclerViewHolder> {
    private List<ComponentData> mComponentDataList;
    private int mCurrentMode;
    private float mDegree;
    private int mItemWidth;
    private View.OnClickListener mOnClickListener;
    @StringRes
    private int mSelectedTitle;

    public ManuallyAdapter(int i, View.OnClickListener onClickListener, List<ComponentData> list, int i2) {
        this.mCurrentMode = i;
        this.mOnClickListener = onClickListener;
        this.mComponentDataList = list;
        this.mItemWidth = i2;
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public int getItemCount() {
        return this.mComponentDataList.size();
    }

    public void onBindViewHolder(CommonRecyclerViewHolder commonRecyclerViewHolder, int i) {
        String str;
        ComponentData componentData = this.mComponentDataList.get(i);
        commonRecyclerViewHolder.itemView.setOnClickListener(this.mOnClickListener);
        FolmeUtils.touchTint(commonRecyclerViewHolder.itemView);
        commonRecyclerViewHolder.itemView.setTag(componentData);
        commonRecyclerViewHolder.itemView.setRotation(this.mDegree);
        TextView textView = (TextView) commonRecyclerViewHolder.getView(R.id.manually_item_key);
        TextView textView2 = (TextView) commonRecyclerViewHolder.getView(R.id.manually_item_value);
        ImageView imageView = (ImageView) commonRecyclerViewHolder.getView(R.id.manually_item_value_image);
        boolean z = true;
        textView.setSelected(true);
        textView2.setSelected(true);
        if (componentData.getDisplayTitleString() > 0) {
            textView.setText(componentData.getDisplayTitleString());
            if (!componentData.disableUpdate() || componentData.mIsKeepValueWhenDisabled) {
                commonRecyclerViewHolder.itemView.setEnabled(true);
            } else {
                commonRecyclerViewHolder.itemView.setEnabled(false);
                textView2.setVisibility(0);
                textView2.setText(componentData.getDefaultValueDisplayString(this.mCurrentMode));
                imageView.setVisibility(8);
                textView.setTextColor(-1);
                textView2.setTextColor(-1);
                ColorImageView colorImageView = (ColorImageView) imageView;
                colorImageView.setColor(-1);
                textView.setAlpha(0.5f);
                textView2.setAlpha(0.5f);
                colorImageView.setAlpha(0.5f);
                return;
            }
        }
        if (componentData.getDisplayTitleString() != this.mSelectedTitle) {
            z = false;
        }
        int tintColor = z ? TintColor.tintColor() : -1;
        if (componentData.disableUpdate()) {
            commonRecyclerViewHolder.itemView.setEnabled(false);
            textView.setTextColor(-1);
            textView2.setTextColor(-1);
            ColorImageView colorImageView2 = (ColorImageView) imageView;
            colorImageView2.setColor(-1);
            textView.setAlpha(0.5f);
            textView2.setAlpha(0.5f);
            colorImageView2.setAlpha(0.5f);
        } else {
            textView.setTextColor(tintColor);
            textView2.setTextColor(tintColor);
            ColorImageView colorImageView3 = (ColorImageView) imageView;
            colorImageView3.setColor(tintColor);
            textView.setAlpha(1.0f);
            textView2.setAlpha(1.0f);
            colorImageView3.setAlpha(1.0f);
        }
        if (componentData.mIsDisplayStringFromResourceId) {
            str = componentData.getValueDisplayStringNotFromResource(this.mCurrentMode);
        } else {
            int valueDisplayString = componentData.getValueDisplayString(this.mCurrentMode);
            str = valueDisplayString == -1 ? null : CameraAppImpl.getAndroidContext().getString(valueDisplayString);
        }
        if (!TextUtils.isEmpty(str)) {
            textView2.setVisibility(0);
            textView2.setText(str);
            imageView.setVisibility(8);
            return;
        }
        textView2.setVisibility(8);
        int valueSelectedDrawable = componentData.getValueSelectedDrawable(this.mCurrentMode);
        if (valueSelectedDrawable != -1) {
            imageView.setImageResource(valueSelectedDrawable);
            int valueSelectedShadowDrawable = componentData.getValueSelectedShadowDrawable(this.mCurrentMode);
            if (valueSelectedShadowDrawable != -1) {
                imageView.setBackgroundResource(valueSelectedShadowDrawable);
            } else {
                imageView.setBackground(null);
            }
            imageView.setVisibility(0);
        }
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public CommonRecyclerViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View inflate = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.fragment_manually_item, viewGroup, false);
        inflate.getLayoutParams().width = this.mItemWidth;
        return new CommonRecyclerViewHolder(inflate);
    }

    public void setRotate(int i) {
        this.mDegree = (float) i;
    }

    public void setSelectedTitle(@StringRes int i) {
        this.mSelectedTitle = i;
        notifyDataSetChanged();
    }
}
