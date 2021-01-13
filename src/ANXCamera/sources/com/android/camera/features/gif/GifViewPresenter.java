package com.android.camera.features.gif;

import android.content.Context;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import com.android.camera.R;
import com.android.camera.animation.FolmeUtils;
import com.android.camera.data.DataRepository;
import com.android.camera.statistic.CameraStatUtils;
import com.android.camera.statistic.MistatsConstants;

public class GifViewPresenter implements View.OnClickListener {
    public static final int GIF_EDIT_SHOW = 303;
    public static final int Gif_EDIT_HIDE = 202;
    public static final String TAG = "MimojiFragmentGifEdit";
    public Context mContext;
    private GifMediaPlayer mGifMediaPlayer;
    public boolean mIsAccelerateOpen = false;
    public boolean mIsClearBgOpen = false;
    public boolean mIsReverseOpen = false;
    private LinearLayout mLlAccelerate;
    private LinearLayout mLlAccelerate2;
    private LinearLayout mLlClearBg;
    private LinearLayout mLlClearBgOut;
    private LinearLayout mLlReverse;
    private LinearLayout mLlReverse2;
    private LinearLayout mLlThreeButton;
    private LinearLayout mLlTwoButton;
    private RelativeLayout mRlGifBottomOperate;
    private RelativeLayout rlGifConsume;

    public GifViewPresenter(Context context) {
        this.mContext = context;
    }

    private void doAccelerateGif() {
        this.mGifMediaPlayer.enableSpeedFilter(this.mIsAccelerateOpen);
    }

    private void doClearGifBackgroud() {
        this.mGifMediaPlayer.enableVideoSegmentFilter(this.mIsClearBgOpen);
    }

    private void doReverseGif() {
        this.mGifMediaPlayer.enableReverseFilter(this.mIsReverseOpen);
    }

    private void initGifView(View view) {
        this.mRlGifBottomOperate = (RelativeLayout) view.findViewById(R.id.rl_operate_gif_bottom_panel);
        this.rlGifConsume = (RelativeLayout) view.findViewById(R.id.rl_gif_full_screen_consume_click);
        this.rlGifConsume.setVisibility(0);
        this.mLlThreeButton = (LinearLayout) view.findViewById(R.id.ll_fuc_opera);
        this.mLlTwoButton = (LinearLayout) view.findViewById(R.id.ll_fuc_opera2);
        this.mLlClearBg = (LinearLayout) view.findViewById(R.id.ll_clear_bg);
        this.mLlClearBgOut = (LinearLayout) view.findViewById(R.id.ll_clear_bg_out);
        this.mLlClearBg.setOnClickListener(this);
        this.mLlAccelerate = (LinearLayout) view.findViewById(R.id.ll_accelerate);
        this.mLlAccelerate.setOnClickListener(this);
        this.mLlAccelerate2 = (LinearLayout) view.findViewById(R.id.ll_accelerate2);
        this.mLlAccelerate2.setOnClickListener(this);
        this.mLlReverse = (LinearLayout) view.findViewById(R.id.ll_reverse);
        this.mLlReverse.setOnClickListener(this);
        this.mLlReverse2 = (LinearLayout) view.findViewById(R.id.ll_reverse2);
        this.mLlReverse2.setOnClickListener(this);
        FolmeUtils.touchScale(this.mLlClearBg, this.mLlAccelerate, this.mLlAccelerate2, this.mLlReverse, this.mLlReverse2);
        updateOperateState();
    }

    public void initView(View view) {
        initGifView(view);
    }

    public void onClick(View view) {
        GifMediaPlayer gifMediaPlayer = this.mGifMediaPlayer;
        if (gifMediaPlayer != null && gifMediaPlayer.isEnable() && !this.mGifMediaPlayer.isComposing()) {
            switch (view.getId()) {
                case R.id.ll_accelerate:
                case R.id.ll_accelerate2:
                    this.mIsAccelerateOpen = !this.mIsAccelerateOpen;
                    updateOperateState();
                    doAccelerateGif();
                    CameraStatUtils.trackMimoji2Click(null, MistatsConstants.Mimoji.GIF_SPEED);
                    return;
                case R.id.ll_clear_bg:
                    this.mIsClearBgOpen = !this.mIsClearBgOpen;
                    updateOperateState();
                    doClearGifBackgroud();
                    CameraStatUtils.trackMimoji2Click(null, MistatsConstants.Mimoji.GIF_REMOVE_BG);
                    return;
                case R.id.ll_reverse:
                case R.id.ll_reverse2:
                    this.mIsReverseOpen = !this.mIsReverseOpen;
                    updateOperateState();
                    doReverseGif();
                    CameraStatUtils.trackMimoji2Click(null, MistatsConstants.Mimoji.GIF_REMOVE_REVERSE);
                    return;
                default:
                    return;
            }
        }
    }

    public void operateGifPannelVisibleState(int i) {
        if (i == 202) {
            this.mRlGifBottomOperate.setVisibility(8);
        } else if (i == 303) {
            this.mRlGifBottomOperate.setVisibility(0);
        }
    }

    public void setGifMediaPlayer(GifMediaPlayer gifMediaPlayer) {
        this.mGifMediaPlayer = gifMediaPlayer;
    }

    public void updateOperateState() {
        if (this.mIsAccelerateOpen) {
            this.mLlAccelerate.setBackground(this.mContext.getResources().getDrawable(R.drawable.gif_round_corner_selected));
            this.mLlAccelerate2.setBackground(this.mContext.getResources().getDrawable(R.drawable.gif_round_corner_selected));
        } else {
            this.mLlAccelerate.setBackground(this.mContext.getResources().getDrawable(R.drawable.gif_round_corner_default));
            this.mLlAccelerate2.setBackground(this.mContext.getResources().getDrawable(R.drawable.gif_round_corner_default));
        }
        if (!DataRepository.dataItemFeature().Al() || DataRepository.dataItemLive().getMimojiStatusManager2().getCurrentMimojiInfo() != null) {
            this.mLlThreeButton.setVisibility(8);
            this.mLlTwoButton.setVisibility(0);
        } else {
            this.mLlThreeButton.setVisibility(0);
            this.mLlTwoButton.setVisibility(8);
            if (this.mIsClearBgOpen) {
                this.mLlClearBg.setBackground(this.mContext.getResources().getDrawable(R.drawable.gif_round_corner_selected));
            } else {
                this.mLlClearBg.setBackground(this.mContext.getResources().getDrawable(R.drawable.gif_round_corner_default));
            }
        }
        if (this.mIsReverseOpen) {
            this.mLlReverse.setBackground(this.mContext.getResources().getDrawable(R.drawable.gif_round_corner_selected));
            this.mLlReverse2.setBackground(this.mContext.getResources().getDrawable(R.drawable.gif_round_corner_selected));
        } else {
            this.mLlReverse.setBackground(this.mContext.getResources().getDrawable(R.drawable.gif_round_corner_default));
            this.mLlReverse2.setBackground(this.mContext.getResources().getDrawable(R.drawable.gif_round_corner_default));
        }
        this.mRlGifBottomOperate.invalidate();
    }
}
