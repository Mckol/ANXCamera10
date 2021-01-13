package com.android.camera.features.mimoji2.fragment.bottomlist;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import androidx.annotation.NonNull;
import androidx.core.view.ViewCompat;
import com.android.camera.R;
import com.android.camera.animation.FolmeUtils;
import com.android.camera.data.DataRepository;
import com.android.camera.features.mimoji2.bean.MimojiInfo2;
import com.android.camera.features.mimoji2.widget.baseview.BaseItemAnimator;
import com.android.camera.features.mimoji2.widget.baseview.BaseRecyclerAdapter;
import com.android.camera.features.mimoji2.widget.baseview.BaseRecyclerViewHolder;
import com.android.camera.features.mimoji2.widget.helper.AvatarEngineManager2;
import com.android.camera.features.mimoji2.widget.helper.MimojiStatusManager2;
import com.android.camera.fragment.music.RoundedCornersTransformation;
import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestBuilder;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.BaseRequestOptions;
import com.bumptech.glide.request.RequestOptions;
import java.util.List;

public class MimojiAvatarAdapter extends BaseRecyclerAdapter<MimojiInfo2> {
    private int mLastSelectPosition = 0;
    private int mResourceBg;

    class AvatarViewViewHolder extends BaseRecyclerViewHolder<MimojiInfo2> {
        private String mAdapterSelectState;
        private View mCartoonSwitchView;
        private ImageView mItemImageView;
        private View mLongSelectedView;
        private MimojiStatusManager2 mMimojiStatusManager2 = DataRepository.dataItemLive().getMimojiStatusManager2();
        private View mSelectItemView;

        public AvatarViewViewHolder(@NonNull View view) {
            super(view);
            FolmeUtils.handleListItemTouch(view, BaseItemAnimator.DEFAULT_LIST_DURATION);
            this.mItemImageView = (ImageView) view.findViewById(R.id.mimoji_item_image);
            this.mSelectItemView = view.findViewById(R.id.mimoji_item_selected_indicator);
            this.mLongSelectedView = view.findViewById(R.id.mimoji_long_item_selected_indicator);
            this.mCartoonSwitchView = view.findViewById(R.id.mimoji_item_cartoon_switch);
            view.setBackgroundResource(MimojiAvatarAdapter.this.mResourceBg);
        }

        @Override // com.android.camera.features.mimoji2.widget.baseview.BaseRecyclerViewHolder
        public View[] getRotateViews() {
            return new View[]{this.mCartoonSwitchView, this.mItemImageView, this.mLongSelectedView};
        }

        public void setData(MimojiInfo2 mimojiInfo2, int i) {
            MimojiInfo2 currentMimojiInfo;
            this.mAdapterSelectState = this.mMimojiStatusManager2.getCurrentMimojiState();
            this.mCartoonSwitchView.setVisibility(8);
            if (mimojiInfo2 != null && !TextUtils.isEmpty(mimojiInfo2.mConfigPath)) {
                if ("close_state".equals(mimojiInfo2.mConfigPath)) {
                    this.mItemImageView.setImageResource(R.drawable.ic_mimoji_off);
                } else if ("add_state".equals(mimojiInfo2.mConfigPath)) {
                    this.mItemImageView.setImageResource(R.drawable.ic_mimoji_add);
                } else {
                    if (mimojiInfo2.getFrame() > 0) {
                        ((RequestBuilder) ((RequestBuilder) Glide.with(this.itemView.getContext()).load(mimojiInfo2.mThumbnailUrl2).skipMemoryCache(false)).diskCacheStrategy(DiskCacheStrategy.ALL)).apply((BaseRequestOptions<?>) RequestOptions.bitmapTransform(new RoundedCornersTransformation(10, 1))).into(this.mItemImageView);
                    } else {
                        ((RequestBuilder) ((RequestBuilder) Glide.with(this.itemView.getContext()).load(mimojiInfo2.mThumbnailUrl).skipMemoryCache(false)).diskCacheStrategy(DiskCacheStrategy.ALL)).apply((BaseRequestOptions<?>) RequestOptions.bitmapTransform(new RoundedCornersTransformation(10, 1))).into(this.mItemImageView);
                    }
                    if (mimojiInfo2.getDefaultFrame() > 0 && (currentMimojiInfo = this.mMimojiStatusManager2.getCurrentMimojiInfo()) != null && !TextUtils.isEmpty(currentMimojiInfo.mAvatarTemplatePath) && currentMimojiInfo.mAvatarTemplatePath.equals(mimojiInfo2.mAvatarTemplatePath)) {
                        this.mCartoonSwitchView.setVisibility(0);
                    }
                }
                if (TextUtils.isEmpty(this.mAdapterSelectState) || !this.mAdapterSelectState.equals(mimojiInfo2.mConfigPath) || mimojiInfo2.mConfigPath.equals("add_state")) {
                    alphaGone(BaseItemAnimator.DEFAULT_LIST_DURATION, this.mSelectItemView);
                    this.mLongSelectedView.setVisibility(8);
                    this.mCartoonSwitchView.setVisibility(8);
                    return;
                }
                if (AvatarEngineManager2.isPrefabModel(mimojiInfo2.mConfigPath) || this.mAdapterSelectState.equals("close_state") || mimojiInfo2.mConfigPath.equals("add_state")) {
                    alphaGone(BaseItemAnimator.DEFAULT_LIST_DURATION, this.mLongSelectedView);
                    this.mSelectItemView.setBackground(this.itemView.getContext().getResources().getDrawable(R.drawable.bg_mimoji_animal_selected));
                } else {
                    showView(this.mLongSelectedView);
                    this.mSelectItemView.setBackground(this.itemView.getContext().getResources().getDrawable(R.drawable.bg_mimoji_selected));
                }
                if (mimojiInfo2.getDefaultFrame() > 0 && this.mAdapterSelectState.equals(mimojiInfo2.mConfigPath)) {
                    this.mCartoonSwitchView.setVisibility(0);
                    if (mimojiInfo2.isIsNeedAnim()) {
                        ViewCompat.animate(this.mCartoonSwitchView).rotationBy(mimojiInfo2.getFrame() == 0 ? -180.0f : 180.0f).setDuration((long) BaseItemAnimator.DEFAULT_LIST_DURATION).start();
                        mimojiInfo2.setIsNeedAnim(false);
                    }
                    this.mSelectItemView.setBackground(this.itemView.getContext().getResources().getDrawable(R.drawable.bg_mimoji_selected));
                }
                showView(this.mSelectItemView);
            }
        }
    }

    public MimojiAvatarAdapter(List<MimojiInfo2> list) {
        super(list);
    }

    /* access modifiers changed from: protected */
    @Override // com.android.camera.features.mimoji2.widget.baseview.BaseRecyclerAdapter
    public BaseRecyclerViewHolder<MimojiInfo2> onCreateBaseRecyclerViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new AvatarViewViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.fragment_mimoji_item, viewGroup, false));
    }

    public void setLastSelectPosition(int i) {
        this.mLastSelectPosition = i;
    }

    public void setResourceBg(int i) {
        this.mResourceBg = i;
    }

    public void setSelectState(int i) {
        if (this.mLastSelectPosition != i || !isAvailablePosion(i)) {
            if (isAvailablePosion(this.mLastSelectPosition)) {
                notifyItemChanged(this.mLastSelectPosition, false);
            }
            if (isAvailablePosion(i)) {
                notifyItemChanged(i, false);
                this.mLastSelectPosition = i;
                return;
            }
            return;
        }
        notifyItemChanged(i, false);
    }
}
