package com.android.camera.fragment.vv;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.core.view.ViewCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.android.camera.R;
import com.android.camera.Util;
import com.android.camera.animation.FolmeUtils;
import com.android.camera.fragment.CommonRecyclerViewHolder;
import com.android.camera.fragment.vv.FragmentVVGallery;
import com.android.camera.statistic.CameraStatUtils;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.BaseRequestOptions;
import com.bumptech.glide.request.RequestOptions;
import java.util.List;
import java.util.Locale;

public class VVGalleryAdapter extends RecyclerView.Adapter<CommonRecyclerViewHolder> implements View.OnClickListener {
    private FragmentVVGallery.EffectItemPadding mEffectItemPadding;
    private RequestOptions mGlideOptions;
    private LinearLayoutManager mLayoutManager;
    private View.OnClickListener mParentClickListener;
    private ResourceSelectedListener mResourceSelectedListener;
    private int mSelectedIndex;
    private VVList mVVList;

    public VVGalleryAdapter(VVList vVList, LinearLayoutManager linearLayoutManager, int i, View.OnClickListener onClickListener, ResourceSelectedListener resourceSelectedListener, FragmentVVGallery.EffectItemPadding effectItemPadding) {
        this.mVVList = vVList;
        this.mLayoutManager = linearLayoutManager;
        if (i >= 0) {
            this.mSelectedIndex = i;
        }
        this.mParentClickListener = onClickListener;
        this.mResourceSelectedListener = resourceSelectedListener;
        this.mEffectItemPadding = effectItemPadding;
        this.mGlideOptions = new RequestOptions();
        this.mGlideOptions.skipMemoryCache(false);
        this.mGlideOptions.diskCacheStrategy(DiskCacheStrategy.NONE);
    }

    private String getDurationString(long j) {
        return String.format(Locale.ENGLISH, "00:%02d", Integer.valueOf(Math.abs((int) Math.floor((double) (((float) j) / 1000.0f)))));
    }

    private void notifyItemChanged(int i, int i2) {
        if (i > -1) {
            notifyItemChanged(i, (Object) false);
        }
        if (i2 > -1) {
            notifyItemChanged(i2, (Object) true);
        }
    }

    private void scrollIfNeed(int i) {
        if (i == this.mLayoutManager.findFirstVisibleItemPosition() || i == this.mLayoutManager.findFirstCompletelyVisibleItemPosition()) {
            int i2 = this.mEffectItemPadding.mHorizontalPadding;
            View findViewByPosition = this.mLayoutManager.findViewByPosition(i);
            if (i > 0 && findViewByPosition != null) {
                i2 = (this.mEffectItemPadding.mHorizontalPadding * 2) + findViewByPosition.getWidth();
            }
            this.mLayoutManager.scrollToPositionWithOffset(Math.max(0, i), i2);
        } else if (i == this.mLayoutManager.findLastVisibleItemPosition() || i == this.mLayoutManager.findLastCompletelyVisibleItemPosition()) {
            this.mLayoutManager.scrollToPosition(Math.min(i + 1, getItemCount() - 1));
        }
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public int getItemCount() {
        VVList vVList = this.mVVList;
        if (vVList != null) {
            return vVList.getSize();
        }
        return 0;
    }

    /* JADX DEBUG: Method arguments types fixed to match base method, original types: [androidx.recyclerview.widget.RecyclerView$ViewHolder, int, java.util.List] */
    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public /* bridge */ /* synthetic */ void onBindViewHolder(@NonNull CommonRecyclerViewHolder commonRecyclerViewHolder, int i, @NonNull List list) {
        onBindViewHolder(commonRecyclerViewHolder, i, (List<Object>) list);
    }

    public void onBindViewHolder(@NonNull CommonRecyclerViewHolder commonRecyclerViewHolder, int i) {
        VVItem vVItem = (VVItem) this.mVVList.getItem(i);
        View findViewById = commonRecyclerViewHolder.itemView.findViewById(R.id.vv_gallery_item_root);
        findViewById.setTag(Integer.valueOf(i));
        findViewById.setOnClickListener(this);
        FolmeUtils.touchItemScale(findViewById);
        ImageView imageView = (ImageView) commonRecyclerViewHolder.getView(R.id.vv_gallery_item_image);
        ImageView imageView2 = (ImageView) commonRecyclerViewHolder.getView(R.id.vv_gallery_item_play);
        ImageView imageView3 = (ImageView) commonRecyclerViewHolder.getView(R.id.vv_gallery_item_indicator);
        TextView textView = (TextView) commonRecyclerViewHolder.getView(R.id.vv_gallery_item_text);
        TextView textView2 = (TextView) commonRecyclerViewHolder.getView(R.id.vv_gallery_item_duration);
        textView.setText(vVItem.name);
        boolean z = true;
        textView.setSelected(true);
        if (this.mSelectedIndex != i) {
            z = false;
        }
        Util.correctionSelectView(imageView, z);
        if (z) {
            textView2.setVisibility(0);
            textView2.setText(getDurationString(vVItem.getTotalDuration()));
            imageView2.setVisibility(0);
            imageView3.setVisibility(0);
        } else {
            textView2.setVisibility(8);
            imageView2.setVisibility(8);
            imageView3.setVisibility(8);
        }
        ViewCompat.setTransitionName(imageView, vVItem.name);
        Glide.with(commonRecyclerViewHolder.itemView).load(vVItem.coverPath).apply((BaseRequestOptions<?>) this.mGlideOptions).into(imageView);
    }

    public void onBindViewHolder(@NonNull CommonRecyclerViewHolder commonRecyclerViewHolder, int i, @NonNull List<Object> list) {
        if (list.isEmpty()) {
            onBindViewHolder(commonRecyclerViewHolder, i);
        } else if (list.get(0) instanceof Boolean) {
            ImageView imageView = (ImageView) commonRecyclerViewHolder.getView(R.id.vv_gallery_item_play);
            TextView textView = (TextView) commonRecyclerViewHolder.getView(R.id.vv_gallery_item_duration);
            ((TextView) commonRecyclerViewHolder.getView(R.id.vv_gallery_item_text)).setSelected(true);
            VVItem vVItem = (VVItem) this.mVVList.getItem(i);
            boolean booleanValue = ((Boolean) list.get(0)).booleanValue();
            commonRecyclerViewHolder.itemView.setActivated(booleanValue);
            Util.updateSelectIndicator((ImageView) commonRecyclerViewHolder.getView(R.id.vv_gallery_item_indicator), booleanValue, true);
            Util.correctionSelectView((ImageView) commonRecyclerViewHolder.getView(R.id.vv_gallery_item_image), booleanValue);
            if (booleanValue) {
                textView.setVisibility(0);
                textView.setText(getDurationString(vVItem.getTotalDuration()));
                imageView.setVisibility(0);
                return;
            }
            textView.setVisibility(8);
            imageView.setVisibility(8);
        }
    }

    public void onClick(View view) {
        int intValue = ((Integer) view.getTag()).intValue();
        CameraStatUtils.trackVVTemplateThumbnailClick(((VVItem) this.mVVList.getItem(intValue)).name);
        onSelected(intValue, view, true);
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    @NonNull
    public CommonRecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new CommonRecyclerViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.fragment_vv_gallery_item, viewGroup, false));
    }

    public void onSelected(int i, View view, boolean z) {
        int i2 = this.mSelectedIndex;
        if (i2 != i) {
            this.mSelectedIndex = i;
            this.mResourceSelectedListener.onResourceSelected((VVItem) this.mVVList.getItem(i));
            if (z) {
                scrollIfNeed(i);
                notifyItemChanged(i2, this.mSelectedIndex);
                return;
            }
            notifyDataSetChanged();
        } else if (view != null) {
            this.mParentClickListener.onClick(view);
        }
    }
}
