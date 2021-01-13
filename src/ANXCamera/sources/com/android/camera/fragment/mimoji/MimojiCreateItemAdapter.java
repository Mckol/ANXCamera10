package com.android.camera.fragment.mimoji;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import androidx.recyclerview.widget.RecyclerView;
import com.android.camera.R;
import com.android.camera.animation.FolmeUtils;
import com.android.camera.data.DataRepository;
import com.android.camera.fragment.CommonRecyclerViewHolder;
import com.android.camera.fragment.music.RoundedCornersTransformation;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.BaseRequestOptions;
import com.bumptech.glide.request.RequestOptions;
import java.util.ArrayList;
import java.util.List;

public class MimojiCreateItemAdapter extends RecyclerView.Adapter<MimojiItemHolder> {
    private String adapterSelectState;
    private List<MimojiInfo> datas = new ArrayList();
    private Context mContext;
    LayoutInflater mLayoutInflater;
    private View mSelectItemView;
    private OnItemClickListener onItemClickListener;

    /* access modifiers changed from: package-private */
    public class MimojiItemHolder extends CommonRecyclerViewHolder implements View.OnClickListener {
        public MimojiItemHolder(View view) {
            super(view);
            view.setOnClickListener(this);
            FolmeUtils.touchItemScale(view);
        }

        public void onClick(View view) {
            int adapterPosition = getAdapterPosition();
            if (adapterPosition != -1) {
                MimojiInfo mimojiInfo = (MimojiInfo) MimojiCreateItemAdapter.this.datas.get(adapterPosition);
                if (MimojiCreateItemAdapter.this.onItemClickListener != null) {
                    MimojiCreateItemAdapter.this.onItemClickListener.onItemClick(mimojiInfo, adapterPosition, view);
                }
            }
        }
    }

    public interface OnItemClickListener {
        void onItemClick(MimojiInfo mimojiInfo, int i, View view);
    }

    public MimojiCreateItemAdapter(Context context, String str) {
        this.mContext = context;
        this.adapterSelectState = str;
        this.mLayoutInflater = LayoutInflater.from(context);
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public int getItemCount() {
        List<MimojiInfo> list = this.datas;
        if (list == null) {
            return 0;
        }
        return list.size();
    }

    public MimojiInfo getMimojiInfoSelected() {
        if (this.datas == null || TextUtils.isEmpty(this.adapterSelectState)) {
            return null;
        }
        for (MimojiInfo mimojiInfo : this.datas) {
            if (this.adapterSelectState.equals(mimojiInfo.mConfigPath)) {
                return mimojiInfo;
            }
        }
        return null;
    }

    public OnItemClickListener getOnItemClickListener() {
        return this.onItemClickListener;
    }

    public void onBindViewHolder(MimojiItemHolder mimojiItemHolder, int i) {
        ImageView imageView = (ImageView) mimojiItemHolder.getView(R.id.mimoji_item_image);
        this.mSelectItemView = mimojiItemHolder.getView(R.id.mimoji_item_selected_indicator);
        View view = mimojiItemHolder.getView(R.id.mimoji_long_item_selected_indicator);
        MimojiInfo mimojiInfo = this.datas.get(i);
        mimojiItemHolder.itemView.setTag(mimojiInfo);
        if (mimojiInfo != null && mimojiInfo.mConfigPath != null) {
            ViewGroup.MarginLayoutParams marginLayoutParams = (ViewGroup.MarginLayoutParams) imageView.getLayoutParams();
            boolean z = true;
            if ("close_state".equals(mimojiInfo.mConfigPath)) {
                marginLayoutParams.width = -2;
                marginLayoutParams.height = -2;
                imageView.setImageResource(R.drawable.ic_mimoji_off);
            } else if ("add_state".equals(mimojiInfo.mConfigPath)) {
                marginLayoutParams.width = -2;
                marginLayoutParams.height = -2;
                imageView.setImageResource(R.drawable.mimoji_add);
            } else {
                marginLayoutParams.width = this.mContext.getResources().getDimensionPixelSize(R.dimen.mimoji_item_bottom_list);
                marginLayoutParams.height = this.mContext.getResources().getDimensionPixelSize(R.dimen.mimoji_item_bottom_list);
                Glide.with(this.mContext).load(mimojiInfo.mThumbnailUrl).apply((BaseRequestOptions<?>) RequestOptions.bitmapTransform(new RoundedCornersTransformation(10, 1))).into(imageView);
            }
            int i2 = 0;
            boolean z2 = !"add_state".equals(mimojiInfo.mConfigPath) && !TextUtils.isEmpty(this.adapterSelectState) && this.adapterSelectState.equals(mimojiInfo.mConfigPath);
            if (!z2 || "close_state".equals(mimojiInfo.mConfigPath) || AvatarEngineManager.isPrefabModel(mimojiInfo.mConfigPath)) {
                z = false;
            }
            this.mSelectItemView.setVisibility(z2 ? 0 : 8);
            if (!z) {
                i2 = 8;
            }
            view.setVisibility(i2);
            if (!z2) {
                return;
            }
            if (z) {
                this.mSelectItemView.setBackground(this.mContext.getResources().getDrawable(R.drawable.bg_mimoji_selected));
            } else {
                this.mSelectItemView.setBackground(this.mContext.getResources().getDrawable(R.drawable.bg_mimoji_animal_selected));
            }
        }
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public MimojiItemHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        return new MimojiItemHolder(this.mLayoutInflater.inflate(R.layout.fragment_mimoji_item, viewGroup, false));
    }

    public void setMimojiInfoList(List<MimojiInfo> list) {
        this.datas.clear();
        this.datas.addAll(list);
        notifyDataSetChanged();
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener2) {
        this.onItemClickListener = onItemClickListener2;
    }

    public void updateSelect() {
        this.adapterSelectState = DataRepository.dataItemLive().getMimojiStatusManager().getCurrentMimojiState();
        notifyDataSetChanged();
    }
}
