package com.android.camera.fragment.fastmotion;

import android.content.Context;
import android.media.MediaPlayer;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.Surface;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.android.camera.R;
import com.android.camera.ui.FastmotionTextureVideoView;
import java.util.List;

public class FastMotionDescriptionAdapter extends RecyclerView.Adapter<FastMotionViewHolder> {
    private static final String TAG = "FastMotionDescriptionAdapter";
    private static final int TYPE_TEXT = 1;
    private static final int TYPE_VIDEO = 2;
    private boolean isInit = false;
    private Context mContext;
    private List<FastMotionDescriptionItem> mLists;
    private FastMotionViewHolder mPlayingHolder;
    private int mPlayingPosition = 1;
    private RecyclerView mRecyclerView;

    public static class FastMotionViewHolder extends RecyclerView.ViewHolder {
        public View mCoverView;
        public TextView mTextDesc;
        public TextView mTextTitle;
        public TextView mVideoDesc;
        public FastmotionTextureVideoView mVideoView;

        public FastMotionViewHolder(@NonNull View view) {
            super(view);
            if (view.getId() == R.id.fastmotion_description_item_text) {
                this.mTextTitle = (TextView) view.findViewById(R.id.fastmotion_description_item_text_title);
                this.mTextDesc = (TextView) view.findViewById(R.id.fastmotion_description_item_text_description);
            } else if (view.getId() == R.id.fastmotion_description_item_video) {
                this.mVideoDesc = (TextView) view.findViewById(R.id.fastmotion_description_item_video_desc);
                this.mVideoView = (FastmotionTextureVideoView) view.findViewById(R.id.fastmotion_description_item_video_vv);
                this.mCoverView = view.findViewById(R.id.fastmotion_description_item_video_cover);
            }
        }
    }

    public FastMotionDescriptionAdapter(RecyclerView recyclerView, Context context, List<FastMotionDescriptionItem> list) {
        this.mLists = list;
        this.mContext = context;
        this.mRecyclerView = recyclerView;
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public int getItemCount() {
        return this.mLists.size();
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public int getItemViewType(int i) {
        return (i == 0 || i == this.mLists.size() - 1) ? 1 : 2;
    }

    public void onBindViewHolder(@NonNull final FastMotionViewHolder fastMotionViewHolder, int i) {
        FastMotionDescriptionItem fastMotionDescriptionItem = this.mLists.get(i);
        if (fastMotionViewHolder.getItemViewType() == 1) {
            fastMotionViewHolder.mTextTitle.setText(fastMotionDescriptionItem.mType);
            fastMotionViewHolder.mTextDesc.setText(fastMotionDescriptionItem.mTypeDesc);
            return;
        }
        fastMotionViewHolder.itemView.setTag(this.mLists.get(i));
        fastMotionViewHolder.mVideoDesc.setText(fastMotionDescriptionItem.mVideoDesc);
        fastMotionViewHolder.mCoverView.setVisibility(0);
        fastMotionViewHolder.mCoverView.setBackgroundResource(fastMotionDescriptionItem.mVideoCover);
        this.mPlayingPosition = i;
        this.mPlayingHolder = fastMotionViewHolder;
        fastMotionViewHolder.mVideoView.setVisibility(0);
        fastMotionViewHolder.mVideoView.setLoop(true);
        FastmotionTextureVideoView fastmotionTextureVideoView = fastMotionViewHolder.mVideoView;
        fastmotionTextureVideoView.setVideoURI(Uri.parse("android.resource://" + this.mContext.getPackageName() + "/" + fastMotionDescriptionItem.mVideoId));
        fastMotionViewHolder.mVideoView.setMediaPlayerCallback(new FastmotionTextureVideoView.MediaPlayerCallback() {
            /* class com.android.camera.fragment.fastmotion.FastMotionDescriptionAdapter.AnonymousClass1 */

            @Override // com.android.camera.ui.FastmotionTextureVideoView.MediaPlayerCallback
            public void onBufferingUpdate(MediaPlayer mediaPlayer, int i) {
            }

            @Override // com.android.camera.ui.FastmotionTextureVideoView.MediaPlayerCallback
            public void onCompletion(MediaPlayer mediaPlayer) {
            }

            @Override // com.android.camera.ui.FastmotionTextureVideoView.MediaPlayerCallback
            public boolean onError(MediaPlayer mediaPlayer, int i, int i2) {
                return false;
            }

            @Override // com.android.camera.ui.FastmotionTextureVideoView.MediaPlayerCallback
            public boolean onInfo(MediaPlayer mediaPlayer, int i, int i2) {
                if (i != 3) {
                    return false;
                }
                fastMotionViewHolder.mCoverView.setVisibility(8);
                return false;
            }

            @Override // com.android.camera.ui.FastmotionTextureVideoView.MediaPlayerCallback
            public void onPrepared(MediaPlayer mediaPlayer) {
            }

            @Override // com.android.camera.ui.FastmotionTextureVideoView.MediaPlayerCallback
            public void onSurfaceReady(Surface surface) {
            }

            @Override // com.android.camera.ui.FastmotionTextureVideoView.MediaPlayerCallback
            public void onVideoSizeChanged(MediaPlayer mediaPlayer, int i, int i2) {
            }

            @Override // com.android.camera.ui.FastmotionTextureVideoView.MediaPlayerCallback
            public void stop() {
                fastMotionViewHolder.mCoverView.setVisibility(0);
            }
        });
        fastMotionViewHolder.mVideoView.start();
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    @NonNull
    public FastMotionViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return i == 1 ? new FastMotionViewHolder(LayoutInflater.from(this.mContext).inflate(R.layout.fastmotion_description_item_text, viewGroup, false)) : new FastMotionViewHolder(LayoutInflater.from(this.mContext).inflate(R.layout.fastmotion_description_item_video, viewGroup, false));
    }
}
