package com.android.camera.fragment.clone;

import android.graphics.drawable.Drawable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import com.android.camera.R;
import com.android.camera.Util;
import com.android.camera.animation.FolmeUtils;
import com.android.camera.animation.type.AlphaInOnSubscribe;
import com.android.camera.fragment.BaseFragment;
import com.android.camera.fragment.BaseFragmentDelegate;
import com.android.camera.fragment.FragmentUtils;
import com.android.camera.log.Log;
import com.android.camera.protocol.ModeCoordinatorImpl;
import com.android.camera.protocol.ModeProtocol;
import com.android.camera.statistic.CameraStatUtils;
import com.android.camera.statistic.MistatsConstants;
import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestBuilder;
import com.bumptech.glide.load.resource.gif.GifDrawable;
import com.xiaomi.fenshen.FenShenCam;
import io.reactivex.Completable;
import java.util.List;
import miui.view.animation.CubicEaseOutInterpolator;

public class FragmentCloneGallery extends BaseFragment implements View.OnClickListener, ModeProtocol.CloneChooser, ModeProtocol.HandleBackTrace {
    public static final String TAG = "FragmentCloneGallery";
    private ModeSelectedListener mModeSelectedListener;
    private ImageView mPhotoButton;
    private ImageView mPhotoIndicator;
    private ViewGroup mPhotoLayout;
    private FenShenCam.Mode mSelectedMode;
    private ImageView mVideoButton;
    private ImageView mVideoIndicator;
    private ViewGroup mVideoLayout;

    @Override // com.android.camera.fragment.BaseFragment
    public int getFragmentInto() {
        return BaseFragmentDelegate.FRAGMENT_CLONE_GALLERY;
    }

    /* access modifiers changed from: protected */
    @Override // com.android.camera.fragment.BaseFragment
    public int getLayoutResourceId() {
        return R.layout.fragment_clone_gallery;
    }

    @Override // com.android.camera.protocol.ModeProtocol.CloneChooser
    public void hide() {
        FragmentUtils.removeFragmentByTag(getFragmentManager(), String.valueOf((int) BaseFragmentDelegate.FRAGMENT_CLONE_GALLERY));
    }

    /* access modifiers changed from: protected */
    @Override // com.android.camera.fragment.BaseFragment
    public void initView(View view) {
        Util.alignPopupBottom(view);
        this.mPhotoLayout = (ViewGroup) view.findViewById(R.id.clone_photo_layout);
        this.mVideoLayout = (ViewGroup) view.findViewById(R.id.clone_video_layout);
        this.mPhotoLayout.setOnClickListener(this);
        this.mVideoLayout.setOnClickListener(this);
        FolmeUtils.touchItemScale(this.mPhotoLayout);
        FolmeUtils.touchItemScale(this.mVideoLayout);
        this.mPhotoButton = (ImageView) this.mPhotoLayout.findViewById(R.id.clone_photo_mode);
        this.mPhotoIndicator = (ImageView) this.mPhotoLayout.findViewById(R.id.clone_photo_mode_indicator);
        this.mVideoButton = (ImageView) this.mVideoLayout.findViewById(R.id.clone_video_mode);
        this.mVideoIndicator = (ImageView) this.mVideoLayout.findViewById(R.id.clone_video_mode_indicator);
    }

    @Override // com.android.camera.protocol.ModeProtocol.CloneChooser
    public boolean isShow() {
        return isAdded() && getView().getVisibility() == 0;
    }

    @Override // com.android.camera.protocol.ModeProtocol.HandleBackTrace
    public boolean onBackEvent(int i) {
        return false;
    }

    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.clone_photo_layout) {
            ModeSelectedListener modeSelectedListener = this.mModeSelectedListener;
            if (modeSelectedListener != null) {
                modeSelectedListener.onModeSelected(FenShenCam.Mode.PHOTO);
            }
            Completable.create(new AlphaInOnSubscribe(this.mPhotoIndicator).setDurationTime(200).setInterpolator(new CubicEaseOutInterpolator())).subscribe();
            this.mVideoIndicator.setVisibility(8);
            Drawable drawable = this.mVideoButton.getDrawable();
            if (drawable != null && (drawable instanceof GifDrawable)) {
                ((GifDrawable) drawable).stop();
            }
            this.mSelectedMode = FenShenCam.Mode.PHOTO;
        } else if (id == R.id.clone_video_layout) {
            ModeSelectedListener modeSelectedListener2 = this.mModeSelectedListener;
            if (modeSelectedListener2 != null) {
                modeSelectedListener2.onModeSelected(FenShenCam.Mode.VIDEO);
            }
            Drawable drawable2 = this.mVideoButton.getDrawable();
            if (drawable2 == null || !(drawable2 instanceof GifDrawable)) {
                ((RequestBuilder) Glide.with(this).asGif().placeholder((int) R.drawable.clone_video_mode_bg)).load("file:///android_asset/clone_video_mode_background.gif").into(this.mVideoButton);
            } else {
                ((GifDrawable) drawable2).start();
            }
            Completable.create(new AlphaInOnSubscribe(this.mVideoIndicator).setDurationTime(200).setInterpolator(new CubicEaseOutInterpolator())).subscribe();
            this.mPhotoIndicator.setVisibility(8);
            this.mSelectedMode = FenShenCam.Mode.VIDEO;
        }
    }

    @Override // com.android.camera.fragment.BaseFragment, androidx.fragment.app.Fragment
    public void onResume() {
        super.onResume();
        ModeSelectedListener modeSelectedListener = this.mModeSelectedListener;
        if (modeSelectedListener != null) {
            modeSelectedListener.onModeReady();
        }
        this.mPhotoLayout.performClick();
    }

    @Override // com.android.camera.fragment.BaseFragment, com.android.camera.animation.AnimationDelegate.AnimationResource
    public void provideAnimateElement(int i, List<Completable> list, int i2) {
        super.provideAnimateElement(i, list, i2);
        if (i != 210) {
            hide();
        }
    }

    /* access modifiers changed from: protected */
    @Override // com.android.camera.fragment.BaseFragment
    public void register(ModeProtocol.ModeCoordinator modeCoordinator) {
        super.register(modeCoordinator);
        modeCoordinator.attachProtocol(416, this);
        registerBackStack(modeCoordinator, this);
    }

    public void setResourceSelectedListener(ModeSelectedListener modeSelectedListener) {
        this.mModeSelectedListener = modeSelectedListener;
    }

    @Override // com.android.camera.protocol.ModeProtocol.CloneChooser
    public void show(int i) {
    }

    @Override // com.android.camera.protocol.ModeProtocol.CloneChooser
    public void startShot() {
        if (this.mSelectedMode == null) {
            Log.w(TAG, "startShot ignore, mSelectedMode is null");
            return;
        }
        ModeProtocol.ConfigChanges configChanges = (ModeProtocol.ConfigChanges) ModeCoordinatorImpl.getInstance().getAttachProtocol(164);
        if (configChanges == null) {
            Log.w(TAG, "startShot ignore, configChanges is null");
            return;
        }
        Config.setCloneMode(this.mSelectedMode);
        if (this.mSelectedMode == FenShenCam.Mode.PHOTO) {
            CameraStatUtils.trackCloneClick(MistatsConstants.CloneAttr.VALUE_START_PHOTO_CLICK);
        } else {
            CameraStatUtils.trackCloneClick(MistatsConstants.CloneAttr.VALUE_START_VIDEO_CLICK);
        }
        configChanges.configClone(this.mSelectedMode, true);
    }

    /* access modifiers changed from: protected */
    @Override // com.android.camera.fragment.BaseFragment
    public void unRegister(ModeProtocol.ModeCoordinator modeCoordinator) {
        super.unRegister(modeCoordinator);
        modeCoordinator.detachProtocol(416, this);
        unRegisterBackStack(modeCoordinator, this);
    }
}
