package com.android.camera.features.mimoji2.fragment.bottomlist;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import androidx.annotation.NonNull;
import com.android.camera.R;
import com.android.camera.Util;
import com.android.camera.animation.FolmeUtils;
import com.android.camera.features.mimoji2.bean.MimojiTimbreInfo;
import com.android.camera.features.mimoji2.widget.baseview.BaseItemAnimator;
import com.android.camera.features.mimoji2.widget.baseview.BaseRecyclerAdapter;
import com.android.camera.features.mimoji2.widget.baseview.BaseRecyclerViewHolder;
import java.util.List;

public class MimojiTimbreAdapter extends BaseRecyclerAdapter<MimojiTimbreInfo> {
    private int mLastSelectedPosition = -1;

    class TimbreViewViewHolder extends BaseRecyclerViewHolder<MimojiTimbreInfo> {
        private ImageView imageView;
        private ProgressBar mProgressTimbreView;
        private ImageView mSelectItemView;

        public TimbreViewViewHolder(@NonNull View view) {
            super(view);
            FolmeUtils.handleListItemTouch(view, BaseItemAnimator.DEFAULT_LIST_DURATION);
            this.imageView = (ImageView) view.findViewById(R.id.mimoji_item_image);
            this.mSelectItemView = (ImageView) view.findViewById(R.id.mimoji_item_selected_indicator);
            this.mProgressTimbreView = (ProgressBar) view.findViewById(R.id.mimoji_timbre_progress);
        }

        @Override // com.android.camera.features.mimoji2.widget.baseview.BaseRecyclerViewHolder
        public View[] getRotateViews() {
            return new View[]{this.itemView};
        }

        public void setData(MimojiTimbreInfo mimojiTimbreInfo, int i) {
            if (mimojiTimbreInfo.getResourceId() > 0) {
                this.itemView.setBackgroundResource(R.color.transparent);
                this.imageView.setImageResource(mimojiTimbreInfo.getResourceId());
            } else {
                View view = this.itemView;
                view.setBackground(view.getContext().getResources().getDrawable(R.drawable.bg_mimoji_item_off_gray));
                this.imageView.setImageResource(R.drawable.ic_mimoji_off);
            }
            Util.correctionSelectView(this.imageView, mimojiTimbreInfo.isSelected());
            int i2 = 8;
            if (mimojiTimbreInfo.isSelected()) {
                ProgressBar progressBar = this.mProgressTimbreView;
                if (mimojiTimbreInfo.isCompsing()) {
                    i2 = 0;
                }
                progressBar.setVisibility(i2);
                this.mSelectItemView.setBackground(this.itemView.getContext().getResources().getDrawable(R.drawable.bg_mimoji_animal_selected));
                this.mSelectItemView.setVisibility(0);
                return;
            }
            this.mSelectItemView.setVisibility(8);
            this.mProgressTimbreView.setVisibility(8);
        }
    }

    public MimojiTimbreAdapter(List<MimojiTimbreInfo> list) {
        super(list);
    }

    public void clearState() {
        this.mLastSelectedPosition = -1;
        if (getDataList() != null) {
            for (int i = 0; i < getItemCount(); i++) {
                MimojiTimbreInfo mimojiTimbreInfo = (MimojiTimbreInfo) getDataList().get(i);
                if (mimojiTimbreInfo.isSelected()) {
                    mimojiTimbreInfo.setSelected(false);
                    mimojiTimbreInfo.setIsCompsing(false);
                    notifyItemChanged(i);
                }
            }
        }
    }

    public void hideProgress() {
        if (this.mLastSelectedPosition >= 0 && ((MimojiTimbreInfo) getDataList().get(this.mLastSelectedPosition)).isCompsing()) {
            ((MimojiTimbreInfo) getDataList().get(this.mLastSelectedPosition)).setIsCompsing(false);
            notifyItemChanged(this.mLastSelectedPosition);
        }
    }

    /* access modifiers changed from: protected */
    @Override // com.android.camera.features.mimoji2.widget.baseview.BaseRecyclerAdapter
    public BaseRecyclerViewHolder<MimojiTimbreInfo> onCreateBaseRecyclerViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new TimbreViewViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.fragment_mimoji_item_timbre, viewGroup, false));
    }

    public boolean setSelectState(int i, boolean z) {
        if (this.mLastSelectedPosition != i || !isAvailablePosion(i)) {
            this.mLastSelectedPosition = -1;
            if (getDataList() != null) {
                for (int i2 = 0; i2 < getItemCount(); i2++) {
                    MimojiTimbreInfo mimojiTimbreInfo = (MimojiTimbreInfo) getDataList().get(i2);
                    if (mimojiTimbreInfo.isSelected()) {
                        mimojiTimbreInfo.setSelected(false);
                        mimojiTimbreInfo.setIsCompsing(false);
                        notifyItemChanged(i2);
                    }
                }
            }
            if (isAvailablePosion(i)) {
                ((MimojiTimbreInfo) getDataList().get(i)).setSelected(true);
                ((MimojiTimbreInfo) getDataList().get(i)).setIsCompsing(z);
                notifyItemChanged(i, false);
                this.mLastSelectedPosition = i;
            }
            return true;
        }
        notifyItemChanged(i, false);
        return false;
    }
}
