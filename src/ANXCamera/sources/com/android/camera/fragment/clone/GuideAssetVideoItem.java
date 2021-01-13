package com.android.camera.fragment.clone;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.res.AssetFileDescriptor;
import android.text.TextUtils;
import android.widget.TextView;
import com.android.camera.videoplayer.manager.VideoPlayerManager;
import com.android.camera.videoplayer.meta.MetaData;
import miui.view.animation.CubicEaseOutInterpolator;

public class GuideAssetVideoItem extends AssetVideoItem {
    private boolean showFooter;
    private String tip1;
    private String tip2;
    private String tip3;
    private String tip4;
    private String title1;
    private String title2;

    public GuideAssetVideoItem(AssetFileDescriptor assetFileDescriptor, VideoPlayerManager<MetaData> videoPlayerManager, int i, String str, String str2, String str3, String str4, String str5, String str6, boolean z) {
        super(assetFileDescriptor, videoPlayerManager, i);
        this.title1 = str;
        this.title2 = str2;
        this.tip1 = str3;
        this.tip2 = str4;
        this.tip3 = str5;
        this.tip4 = str6;
        this.showFooter = z;
    }

    private void setText(TextView textView, String str) {
        if (!TextUtils.isEmpty(str)) {
            textView.setVisibility(0);
            textView.setText(str);
            return;
        }
        textView.setVisibility(8);
    }

    private void showEnterAnimator(VideoViewHolder videoViewHolder) {
        ObjectAnimator duration = ObjectAnimator.ofFloat(videoViewHolder.mTextureViewContainer, "translationY", 200.0f, 0.0f).setDuration(500L);
        duration.setInterpolator(new CubicEaseOutInterpolator());
        ObjectAnimator duration2 = ObjectAnimator.ofFloat(videoViewHolder.mTextureViewContainer, "alpha", 0.0f, 1.0f).setDuration(500L);
        duration2.setInterpolator(new CubicEaseOutInterpolator());
        ObjectAnimator duration3 = ObjectAnimator.ofFloat(videoViewHolder.mTipsContainer, "translationY", 200.0f, 0.0f).setDuration(500L);
        duration3.setInterpolator(new CubicEaseOutInterpolator());
        ObjectAnimator duration4 = ObjectAnimator.ofFloat(videoViewHolder.mTipsContainer, "alpha", 0.0f, 1.0f).setDuration(500L);
        duration4.setInterpolator(new CubicEaseOutInterpolator());
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playTogether(duration, duration2, duration3, duration4);
        animatorSet.play(duration).with(duration2).after(100).with(duration3).with(duration4);
        animatorSet.start();
    }

    @Override // com.android.camera.fragment.clone.AssetVideoItem, com.android.camera.fragment.clone.BaseVideoItem
    public void update(int i, VideoViewHolder videoViewHolder, VideoPlayerManager videoPlayerManager) {
        super.update(i, videoViewHolder, videoPlayerManager);
        videoViewHolder.mCover.setImageResource(this.mCoverResource);
        setText(videoViewHolder.mTextViewTitle1, this.title1);
        setText(videoViewHolder.mTextViewTitle2, this.title2);
        setText(videoViewHolder.mTextViewTip1, this.tip1);
        setText(videoViewHolder.mTextViewTip2, this.tip2);
        setText(videoViewHolder.mTextViewTip3, this.tip3);
        setText(videoViewHolder.mTextViewTip4, this.tip4);
        showEnterAnimator(videoViewHolder);
        if (this.showFooter) {
            videoViewHolder.mFooter.setVisibility(0);
        } else {
            videoViewHolder.mFooter.setVisibility(8);
        }
    }
}
