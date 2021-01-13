package com.android.camera.features.gif;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.TextureView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import androidx.annotation.Nullable;
import com.android.camera.ActivityBase;
import com.android.camera.R;
import com.android.camera.Util;
import com.android.camera.data.DataRepository;
import com.android.camera.features.mimoji2.module.protocol.MimojiModeProtocol;
import com.android.camera.features.mimoji2.utils.MimojiViewUtil;
import com.android.camera.fragment.BaseFragment;
import com.android.camera.log.Log;
import com.android.camera.protocol.ModeCoordinatorImpl;
import com.android.camera.protocol.ModeProtocol;
import com.android.camera.statistic.CameraStatUtils;
import com.android.camera.statistic.MistatsConstants;
import com.android.camera.storage.Storage;
import io.reactivex.Completable;
import java.util.List;

public class FragmentGifEdit extends BaseFragment implements View.OnClickListener, ModeProtocol.HandleBackTrace, ModeProtocol.MimojiGifEditor {
    public static final int FRAGMENT_INFO = 65524;
    public static final String TAG = "MimojiFragmentGifEdit";
    private ImageView mBackBtn;
    private ProgressBar mConcatProgressBar;
    private ImageView mConfirmBtn;
    private Context mContext;
    private long mDuration;
    private Bitmap mGifBitmap;
    private GifEditLayout mGifEditLayout;
    private GifMediaPlayer mGifMediaPlayer;
    private View mGifViewLayout;
    private GifViewPresenter mGifViewPresenter;
    private LinearLayout mIlShare;
    private boolean mIsNeedCombine = true;
    private boolean mIsShare;
    private FrameLayout mLlTextureContainer;
    private ImageView mPreviewCoverView;
    private TextureView mTextureView;
    private String mVideoPath;

    private void backToPreview() {
        if (getActivity() != null) {
            getActivity().runOnUiThread(new a(this));
        }
    }

    private void initGifMediaPlayer() {
        if (this.mGifMediaPlayer == null) {
            this.mGifMediaPlayer = new GifMediaPlayer(this.mContext, !DataRepository.dataItemFeature().Al() || DataRepository.dataItemLive().getMimojiStatusManager2().getCurrentMimojiInfo() != null);
        }
        this.mGifViewPresenter.setGifMediaPlayer(this.mGifMediaPlayer);
        this.mGifMediaPlayer.setTextureView(this.mTextureView);
        this.mGifMediaPlayer.setPreviewCoverView(this.mPreviewCoverView);
        this.mGifMediaPlayer.setConcatProgressBar(this.mConcatProgressBar);
        this.mGifMediaPlayer.setGifEditLayout(this.mGifEditLayout);
        if (this.mIsNeedCombine) {
            this.mIsNeedCombine = false;
            combineVideoAudio(this.mVideoPath, this.mDuration);
        }
    }

    private void release() {
        GifMediaPlayer gifMediaPlayer = this.mGifMediaPlayer;
        if (gifMediaPlayer != null) {
            gifMediaPlayer.releaseMedia(false);
            this.mGifMediaPlayer = null;
        }
        Bitmap bitmap = this.mGifBitmap;
        if (bitmap != null && !bitmap.isRecycled()) {
            this.mGifBitmap.recycle();
            this.mGifBitmap = null;
        }
    }

    private void saveGif() {
        GifMediaPlayer gifMediaPlayer = this.mGifMediaPlayer;
        if (gifMediaPlayer != null) {
            gifMediaPlayer.startVideo2Gif();
        }
    }

    @Override // com.android.camera.protocol.ModeProtocol.MimojiGifEditor
    public void combineVideoAudio(String str, long j) {
        this.mVideoPath = str;
        this.mDuration = j;
        GifMediaPlayer gifMediaPlayer = this.mGifMediaPlayer;
        if (gifMediaPlayer == null || gifMediaPlayer.getSurface() == null) {
            Log.e("MimojiFragmentGifEdit", "mimoji void combineVideoAudio[savePath] mGifMediaPlayer null");
            this.mIsNeedCombine = true;
            GifMediaPlayer gifMediaPlayer2 = this.mGifMediaPlayer;
            if (gifMediaPlayer2 != null) {
                gifMediaPlayer2.setVideoUrl(this.mVideoPath, this.mDuration);
                return;
            }
            return;
        }
        this.mIsNeedCombine = false;
        this.mGifMediaPlayer.openVideoUrl(this.mVideoPath, this.mDuration);
    }

    @Override // com.android.camera.protocol.ModeProtocol.MimojiGifEditor
    public void coverGifError() {
        backToPreview();
    }

    @Override // com.android.camera.protocol.ModeProtocol.MimojiGifEditor
    public void coverGifSuccess(String str) {
        if (this.mIsShare) {
            Uri addGifSync = ((ActivityBase) getActivity()).getImageSaver().addGifSync(str, 300, 300);
            Intent intent = new Intent("android.intent.action.SEND");
            intent.putExtra("android.intent.extra.STREAM", addGifSync);
            intent.setType(Storage.MIME_GIF);
            startActivity(Intent.createChooser(intent, getString(R.string.share)));
            return;
        }
        ((ActivityBase) getActivity()).getImageSaver().addGif(str, 300, 300);
        backToPreview();
    }

    @Override // com.android.camera.fragment.BaseFragment
    public int getFragmentInto() {
        return 65524;
    }

    /* access modifiers changed from: protected */
    @Override // com.android.camera.fragment.BaseFragment
    public int getLayoutResourceId() {
        return R.layout.fragment_gif_bottom;
    }

    /* access modifiers changed from: protected */
    @Override // com.android.camera.fragment.BaseFragment
    public void initView(View view) {
        if (view == null) {
            Log.e("MimojiFragmentGifEdit", " rootview null");
            return;
        }
        this.mGifViewLayout = view;
        this.mContext = getContext();
        this.mLlTextureContainer = (FrameLayout) view.findViewById(R.id.ll_operate_gif_words);
        ViewGroup.MarginLayoutParams marginLayoutParams = (ViewGroup.MarginLayoutParams) this.mLlTextureContainer.getLayoutParams();
        marginLayoutParams.height = Util.getDisplayRect(4).right;
        marginLayoutParams.width = Util.getDisplayRect(4).right;
        marginLayoutParams.topMargin = Util.getDisplayRect(4).top;
        this.mLlTextureContainer.setLayoutParams(marginLayoutParams);
        RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) view.findViewById(R.id.rv_bottom_navigation_layout).getLayoutParams();
        if (layoutParams != null) {
            if (!Util.isFullScreenNavBarHidden(getContext())) {
                layoutParams.bottomMargin = getResources().getDimensionPixelOffset(R.dimen.dimen_gif_full_marginBottom);
            } else {
                layoutParams.bottomMargin = 0;
            }
        }
        this.mGifViewPresenter = new GifViewPresenter(getContext());
        this.mGifViewPresenter.initView(this.mGifViewLayout);
        this.mIlShare = (LinearLayout) view.findViewById(R.id.ll_gif_share);
        this.mIlShare.setOnClickListener(this);
        this.mTextureView = (TextureView) view.findViewById(R.id.gif_texture_view);
        this.mTextureView.setOnClickListener(this);
        this.mPreviewCoverView = (ImageView) view.findViewById(R.id.image_mimoji_cover);
        this.mConcatProgressBar = (ProgressBar) view.findViewById(R.id.concat_mimoji_progress);
        this.mGifEditLayout = (GifEditLayout) view.findViewById(R.id.fl_edit_gif);
        this.mBackBtn = (ImageView) view.findViewById(R.id.iv_gif_back);
        this.mBackBtn.setOnClickListener(this);
        this.mConfirmBtn = (ImageView) view.findViewById(R.id.iv_gif_confirm);
        this.mConfirmBtn.setOnClickListener(this);
        MimojiModeProtocol.MimojiAvatarEngine2 mimojiAvatarEngine2 = (MimojiModeProtocol.MimojiAvatarEngine2) ModeCoordinatorImpl.getInstance().getAttachProtocol(246);
        if (mimojiAvatarEngine2 == null || mimojiAvatarEngine2.getGifBitmap() == null || mimojiAvatarEngine2.getGifBitmap().isRecycled()) {
            MimojiViewUtil.setViewVisible(this.mPreviewCoverView, false);
        } else {
            this.mGifBitmap = mimojiAvatarEngine2.getGifBitmap();
            this.mPreviewCoverView.setImageBitmap(this.mGifBitmap);
            MimojiViewUtil.setViewVisible(this.mPreviewCoverView, true);
        }
        MimojiViewUtil.setViewVisible(this.mConcatProgressBar, true);
        initGifMediaPlayer();
    }

    @Override // com.android.camera.protocol.ModeProtocol.HandleBackTrace
    public boolean onBackEvent(int i) {
        if (i != 1) {
            return false;
        }
        if (!TextUtils.isEmpty(this.mVideoPath) && this.mGifMediaPlayer != null) {
            backToPreview();
        }
        return true;
    }

    public void onClick(View view) {
        GifMediaPlayer gifMediaPlayer = this.mGifMediaPlayer;
        if (gifMediaPlayer != null && gifMediaPlayer.isEnable() && !this.mGifMediaPlayer.isComposing()) {
            switch (view.getId()) {
                case R.id.gif_texture_view:
                    this.mGifEditLayout.hideBorder(false);
                    return;
                case R.id.iv_gif_back:
                    backToPreview();
                    return;
                case R.id.iv_gif_confirm:
                    CameraStatUtils.trackMimoji2Click(null, MistatsConstants.Mimoji.GIF_SAVE);
                    this.mIsShare = false;
                    saveGif();
                    return;
                case R.id.ll_gif_share:
                    CameraStatUtils.trackMimoji2Click(null, MistatsConstants.Mimoji.GIF_SHARE);
                    this.mIsShare = true;
                    saveGif();
                    return;
                default:
                    return;
            }
        }
    }

    @Override // com.android.camera.fragment.BaseFragment, androidx.fragment.app.Fragment
    @Nullable
    public View onCreateView(LayoutInflater layoutInflater, @Nullable ViewGroup viewGroup, Bundle bundle) {
        View inflate = layoutInflater.inflate(R.layout.fragment_gif_bottom, viewGroup, false);
        initView(inflate);
        return inflate;
    }

    @Override // com.android.camera.fragment.BaseFragment, androidx.fragment.app.Fragment
    public void onDestroyView() {
        release();
        super.onDestroyView();
        Log.i("MimojiFragmentGifEdit", "onDestroy  ReleaseMedia");
    }

    @Override // androidx.fragment.app.Fragment
    public void onPause() {
        super.onPause();
        GifMediaPlayer gifMediaPlayer = this.mGifMediaPlayer;
        if (gifMediaPlayer != null && !this.mIsNeedCombine) {
            gifMediaPlayer.pausePlay();
        }
        GifEditLayout gifEditLayout = this.mGifEditLayout;
        if (gifEditLayout != null) {
            gifEditLayout.hideBorder(true);
        }
    }

    @Override // com.android.camera.fragment.BaseFragment, androidx.fragment.app.Fragment
    public void onResume() {
        super.onResume();
        if (this.mIsNeedCombine) {
            initView(getView());
            return;
        }
        GifMediaPlayer gifMediaPlayer = this.mGifMediaPlayer;
        if (gifMediaPlayer != null) {
            gifMediaPlayer.resumePlay();
        }
    }

    @Override // com.android.camera.fragment.BaseFragment, androidx.fragment.app.Fragment
    public void onStop() {
        super.onStop();
    }

    @Override // com.android.camera.protocol.ModeProtocol.MimojiGifEditor
    public void operateGifPannelVisibleState(int i) {
        this.mGifViewPresenter.operateGifPannelVisibleState(i);
    }

    @Override // com.android.camera.fragment.BaseFragment, com.android.camera.animation.AnimationDelegate.AnimationResource
    public void provideAnimateElement(int i, List<Completable> list, int i2) {
        super.provideAnimateElement(i, list, i2);
        Log.d("MimojiFragmentGifEdit", "provideAnimateElement, animateInElements" + list + "resetType = " + i2);
        if (MimojiViewUtil.getViewIsVisible(this.mGifViewLayout) && i2 == 3) {
            backToPreview();
        }
    }

    public /* synthetic */ void qa() {
        MimojiViewUtil.setViewVisible(this.mGifViewLayout, false);
        ModeProtocol.BaseDelegate baseDelegate = (ModeProtocol.BaseDelegate) ModeCoordinatorImpl.getInstance().getAttachProtocol(160);
        if (baseDelegate != null) {
            baseDelegate.delegateEvent(20);
        }
    }

    public /* synthetic */ void ra() {
        MimojiModeProtocol.MimojiAvatarEngine2 mimojiAvatarEngine2 = (MimojiModeProtocol.MimojiAvatarEngine2) ModeCoordinatorImpl.getInstance().getAttachProtocol(246);
        if (mimojiAvatarEngine2 != null) {
            mimojiAvatarEngine2.deleteMimojiCache(3);
        }
        ModeProtocol.CameraAction cameraAction = (ModeProtocol.CameraAction) ModeCoordinatorImpl.getInstance().getAttachProtocol(161);
        if (cameraAction != null) {
            cameraAction.onReviewCancelClicked();
        } else {
            Log.d("MimojiFragmentGifEdit", "mimoji void onCombineError[] cameraAction null");
            ModeProtocol.RecordState recordState = (ModeProtocol.RecordState) ModeCoordinatorImpl.getInstance().getAttachProtocol(212);
            if (recordState != null) {
                recordState.onFinish();
            } else {
                Log.d("MimojiFragmentGifEdit", "mimoji void onCombineError[] recordState null");
            }
        }
        this.mGifViewLayout.postDelayed(new b(this), 200);
    }

    @Override // com.android.camera.protocol.ModeProtocol.MimojiGifEditor
    public void reconfigPreviewRadio(int i) {
    }

    /* access modifiers changed from: protected */
    @Override // com.android.camera.fragment.BaseFragment
    public void register(ModeProtocol.ModeCoordinator modeCoordinator) {
        super.register(modeCoordinator);
        registerBackStack(modeCoordinator, this);
        ModeCoordinatorImpl.getInstance().attachProtocol(251, this);
    }

    @Override // com.android.camera.protocol.ModeProtocol.MimojiGifEditor
    public void setPreviewCover(Bitmap bitmap) {
    }

    @Override // com.android.camera.protocol.ModeProtocol.MimojiGifEditor
    public void showPreviewCover(boolean z) {
        MimojiViewUtil.setViewVisible(this.mPreviewCoverView, z);
    }

    /* access modifiers changed from: protected */
    @Override // com.android.camera.fragment.BaseFragment
    public void unRegister(ModeProtocol.ModeCoordinator modeCoordinator) {
        super.unRegister(modeCoordinator);
        unRegisterBackStack(modeCoordinator, this);
        ModeCoordinatorImpl.getInstance().detachProtocol(251, this);
    }
}
