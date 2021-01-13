package com.android.camera.features.mimoji2.fragment.edit;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import androidx.annotation.NonNull;
import com.android.camera.R;
import com.android.camera.features.mimoji2.widget.baseview.BaseRecyclerAdapter;
import com.android.camera.features.mimoji2.widget.baseview.BaseRecyclerViewHolder;
import com.android.camera.features.mimoji2.widget.helper.AvatarEngineManager2;
import com.android.camera.fragment.music.RoundedCornersTransformation;
import com.arcsoft.avatar2.AvatarConfig;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.BaseRequestOptions;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.transition.DrawableCrossFadeFactory;
import java.util.ArrayList;

public class MimojiThumbnailRecyclerAdapter2 extends BaseRecyclerAdapter<AvatarConfig.ASAvatarConfigInfo> {
    private Context mContext;
    private float mSelectIndex;

    static class ThumbnailViewViewHolder extends BaseRecyclerViewHolder<AvatarConfig.ASAvatarConfigInfo> {
        ImageView imageView;

        public ThumbnailViewViewHolder(@NonNull View view) {
            super(view);
            this.imageView = (ImageView) view.findViewById(R.id.thumbnail_image_view);
        }

        public void setData(AvatarConfig.ASAvatarConfigInfo aSAvatarConfigInfo, int i) {
            Bitmap bitmap;
            new DrawableCrossFadeFactory.Builder(300).setCrossFadeEnabled(true).build();
            if (aSAvatarConfigInfo == null || (bitmap = aSAvatarConfigInfo.thum) == null || bitmap.isRecycled()) {
                Log.e(ThumbnailViewViewHolder.class.getSimpleName(), "fmoji bitmap isRecycled");
            } else {
                Glide.with(this.itemView.getContext()).load(aSAvatarConfigInfo.thum).apply(new RequestOptions().placeholder(this.imageView.getDrawable())).apply((BaseRequestOptions<?>) RequestOptions.bitmapTransform(new RoundedCornersTransformation(20, 1))).into(this.imageView);
            }
            this.imageView.setBackground(MimojiThumbnailRecyclerAdapter2.getSelectItem(aSAvatarConfigInfo.configType) == ((float) aSAvatarConfigInfo.configID) ? this.itemView.getContext().getDrawable(R.drawable.bg_mimoji_thumbnail_selected) : null);
        }
    }

    public MimojiThumbnailRecyclerAdapter2(Context context, int i) {
        this(null);
        this.mContext = context;
        this.mSelectIndex = getSelectItem(i);
    }

    public MimojiThumbnailRecyclerAdapter2(ArrayList<AvatarConfig.ASAvatarConfigInfo> arrayList) {
        super(arrayList);
        this.mSelectIndex = -1.0f;
    }

    public static float getSelectItem(int i) {
        return AvatarEngineManager2.getInstance().getInnerConfigSelectIndex(i);
    }

    /* access modifiers changed from: protected */
    @Override // com.android.camera.features.mimoji2.widget.baseview.BaseRecyclerAdapter
    public BaseRecyclerViewHolder<AvatarConfig.ASAvatarConfigInfo> onCreateBaseRecyclerViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new ThumbnailViewViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.mimoji_thumbnail_view, viewGroup, false));
    }

    public void setSelectItem(int i, int i2) {
        AvatarEngineManager2 instance = AvatarEngineManager2.getInstance();
        if (instance != null) {
            instance.setInnerConfigSelectIndex(i, (float) i2);
        }
    }
}