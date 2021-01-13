package com.android.camera.fragment.clone;

import android.animation.ObjectAnimator;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.Rect;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.annotation.Nullable;
import androidx.core.view.ViewCompat;
import androidx.recyclerview.widget.RecyclerView;
import com.airbnb.lottie.LottieAnimationView;
import com.android.camera.Camera;
import com.android.camera.CameraSettings;
import com.android.camera.R;
import com.android.camera.Util;
import com.android.camera.animation.FolmeUtils;
import com.android.camera.animation.type.AlphaInOnSubscribe;
import com.android.camera.animation.type.AlphaOutOnSubscribe;
import com.android.camera.data.DataRepository;
import com.android.camera.fragment.BaseFragment;
import com.android.camera.fragment.BaseFragmentDelegate;
import com.android.camera.fragment.bottom.BottomAnimationConfig;
import com.android.camera.fragment.vv.VVShareAdapter;
import com.android.camera.fragment.vv.page.PageIndicatorView;
import com.android.camera.fragment.vv.page.PagerGridLayoutManager;
import com.android.camera.fragment.vv.page.PagerGridSnapHelper;
import com.android.camera.log.Log;
import com.android.camera.module.Module;
import com.android.camera.protocol.ModeCoordinatorImpl;
import com.android.camera.protocol.ModeProtocol;
import com.android.camera.statistic.CameraStatUtils;
import com.android.camera.statistic.MistatsConstants;
import com.android.camera.storage.MediaProviderUtil;
import com.android.camera.ui.CameraSnapView;
import com.android.camera.ui.drawable.snap.PaintConditionReferred;
import com.xiaomi.fenshen.FenShenCam;
import io.reactivex.Completable;
import java.util.List;
import miui.extension.target.ActivityTarget;

public class FragmentCloneProcess extends BaseFragment implements View.OnClickListener, ModeProtocol.HandleBackTrace, ModeProtocol.CloneProcess, CameraSnapView.SnapListener {
    private static final String TAG = "FragmentCloneProcess";
    private ViewGroup mBottomActionView;
    private ViewGroup mBottomLayout;
    private CameraSnapView mCameraSnapView;
    private ImageView mCancelCapture;
    private TextView mCaptureHint;
    private boolean mDetectedPersonInPreview;
    private View mExitDialog;
    private TextView mExitDialogCancel;
    private TextView mExitDialogConfirm;
    private TextView mExitDialogMessage;
    private boolean mExitToMimoji;
    private ImageView mGiveUpToPreview;
    private boolean mIsPendingShowComposeResult;
    private TextView mLandscapeHint;
    private FenShenCam.Mode mMode;
    private PageIndicatorView mPageIndicatorView;
    private PagerGridSnapHelper mPagerGridSnapHelper;
    private boolean mPaused;
    private boolean mPendingShare;
    private View mRootView;
    private ImageView mSaveAndShare;
    private LottieAnimationView mSaveButton;
    private ContentValues mSaveContentValues;
    private String mSavedPath;
    private VVShareAdapter mShareAdapter;
    private View mShareDialog;
    private TextView mShareMessage;
    private ProgressBar mShareProgress;
    private RecyclerView mShareRecyclerView;
    private ImageView mSnapViewProgress;
    private Status mStatus;
    private ImageView mStopCapture;

    private void alphaAnimateIn(View view) {
        if (view.getVisibility() != 0) {
            Completable.create(new AlphaInOnSubscribe(view)).subscribe();
        }
    }

    private void alphaAnimateOut(View view) {
        if (view.getVisibility() == 0) {
            Completable.create(new AlphaOutOnSubscribe(view)).subscribe();
        }
    }

    private boolean checkAndShare() {
        if (this.mSavedPath == null) {
            return false;
        }
        showShareSheet();
        return true;
    }

    /* access modifiers changed from: private */
    /* access modifiers changed from: public */
    private void enableUseGuideMenu(boolean z) {
        ModeProtocol.TopAlert topAlert = (ModeProtocol.TopAlert) ModeCoordinatorImpl.getInstance().getAttachProtocol(172);
        if (topAlert != null) {
            topAlert.enableMenuItem(z, 164);
        }
    }

    /* access modifiers changed from: private */
    /* access modifiers changed from: public */
    private boolean hideExitDialog() {
        if (this.mExitDialog.getVisibility() != 0) {
            return false;
        }
        setSnapButtonEnable(true, true);
        this.mExitDialog.setVisibility(8);
        return true;
    }

    /* access modifiers changed from: private */
    /* access modifiers changed from: public */
    private boolean hideShareSheet() {
        if (this.mShareDialog.getVisibility() != 0) {
            return false;
        }
        this.mShareDialog.setVisibility(8);
        return true;
    }

    static /* synthetic */ float i(float f) {
        if (f == 1.0f) {
            return 1.0f;
        }
        return (float) ((-Math.pow(2.0d, (double) (f * -10.0f))) + 1.0d);
    }

    private void initShutterButton(FenShenCam.Mode mode) {
        Log.d(TAG, "initShutterButton " + mode);
        if (mode == FenShenCam.Mode.VIDEO) {
            PaintConditionReferred create = PaintConditionReferred.create(this.mCurrentMode);
            create.setTargetFrameRatio(1.7777777f);
            this.mCameraSnapView.setParameters(create);
            this.mCameraSnapView.showCirclePaintItem();
            this.mCameraSnapView.showRoundPaintItem();
            this.mCameraSnapView.stopSpeech();
            return;
        }
        PaintConditionReferred create2 = PaintConditionReferred.create(163);
        create2.setTargetFrameRatio(1.7777777f);
        this.mCameraSnapView.setParameters(create2);
        this.mCameraSnapView.showCirclePaintItem();
        this.mCameraSnapView.showRoundPaintItem();
        if (CameraSettings.isSpeechShutterOpen()) {
            this.mCameraSnapView.startSpeech();
        } else {
            this.mCameraSnapView.stopSpeech();
        }
    }

    private void onSaveButtonClick() {
        if (this.mSavedPath != null) {
            resetToPreview(true);
            return;
        }
        ModeProtocol.CloneAction cloneAction = (ModeProtocol.CloneAction) ModeCoordinatorImpl.getInstance().getAttachProtocol(419);
        if (cloneAction == null) {
            Log.w(TAG, "onSaveButtonClick: no clone action");
            return;
        }
        Log.d(TAG, "onSaveButtonClick");
        if (!this.mPendingShare) {
            enableUseGuideMenu(false);
        }
        this.mSaveButton.setVisibility(8);
        setProgressBarVisible(true);
        cloneAction.onSaveClicked();
    }

    private void setProgressBarVisible(boolean z) {
        if (z && this.mSnapViewProgress.getVisibility() == 0) {
            return;
        }
        if (!z && this.mSnapViewProgress.getVisibility() != 0) {
            return;
        }
        if (z) {
            RotateAnimation rotateAnimation = new RotateAnimation(0.0f, 360.0f, (float) (this.mSnapViewProgress.getLayoutParams().width / 2), (float) (this.mSnapViewProgress.getLayoutParams().height / 2));
            rotateAnimation.setDuration((long) getResources().getInteger(R.integer.post_process_duration));
            rotateAnimation.setInterpolator(new LinearInterpolator());
            rotateAnimation.setRepeatMode(1);
            rotateAnimation.setRepeatCount(-1);
            this.mSnapViewProgress.setAnimation(rotateAnimation);
            this.mSnapViewProgress.setVisibility(0);
            return;
        }
        this.mSnapViewProgress.clearAnimation();
        this.mSnapViewProgress.setVisibility(8);
    }

    private void setSnapButtonEnable(boolean z, boolean z2) {
        if (!z || (isLandScape() && this.mDetectedPersonInPreview)) {
            Log.d(TAG, "setSnapButtonEnable " + z);
            CameraSnapView cameraSnapView = this.mCameraSnapView;
            if (cameraSnapView != null) {
                cameraSnapView.setSnapClickEnable(z);
                this.mCameraSnapView.setEnabled(z);
                if (z || z2) {
                    this.mCameraSnapView.setAlpha(z ? 1.0f : 0.5f);
                    return;
                }
                return;
            }
            return;
        }
        Log.w(TAG, "setSnapButtonEnable ignore, is not landScape");
    }

    private void showCaptureHint() {
        if (this.mCaptureHint.getVisibility() != 0) {
            Rect displayRect = Util.getDisplayRect();
            Context context = getContext();
            int dimensionPixelOffset = context.getResources().getDimensionPixelOffset(R.dimen.clone_capture_hint_width);
            int dimensionPixelOffset2 = context.getResources().getDimensionPixelOffset(R.dimen.clone_capture_hint_height) / 2;
            int screenWidth = (Util.getScreenWidth(context) - dimensionPixelOffset2) - (dimensionPixelOffset / 2);
            FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) this.mCaptureHint.getLayoutParams();
            layoutParams.leftMargin = screenWidth;
            layoutParams.topMargin = (displayRect.top + (displayRect.height() / 2)) - dimensionPixelOffset2;
            TextView textView = this.mCaptureHint;
            ObjectAnimator ofFloat = ObjectAnimator.ofFloat(textView, "translationX", (float) textView.getHeight(), 0.0f);
            ofFloat.setDuration(600L);
            ofFloat.setInterpolator(a.INSTANCE);
            ofFloat.start();
            this.mCaptureHint.setVisibility(0);
        }
    }

    private void showShareSheet() {
        this.mPendingShare = false;
        if (!this.mPaused) {
            Intent shareMediaIntent = Util.getShareMediaIntent(getContext(), this.mSavedPath, this.mMode == FenShenCam.Mode.VIDEO);
            PackageManager packageManager = getContext().getPackageManager();
            List<ResolveInfo> queryIntentActivities = packageManager.queryIntentActivities(shareMediaIntent, 65536);
            if (queryIntentActivities == null || queryIntentActivities.isEmpty()) {
                Log.d(TAG, "no IntentActivities");
                return;
            }
            int i = this.mShareRecyclerView.getLayoutParams().width / 4;
            VVShareAdapter vVShareAdapter = this.mShareAdapter;
            if (vVShareAdapter == null || vVShareAdapter.getItemCount() != queryIntentActivities.size()) {
                this.mShareAdapter = new VVShareAdapter(packageManager, queryIntentActivities, this, i);
                PagerGridLayoutManager pagerGridLayoutManager = new PagerGridLayoutManager(2, 4, 1);
                pagerGridLayoutManager.setPageListener(new PagerGridLayoutManager.PageListener() {
                    /* class com.android.camera.fragment.clone.FragmentCloneProcess.AnonymousClass3 */

                    @Override // com.android.camera.fragment.vv.page.PagerGridLayoutManager.PageListener
                    public void onPageSelect(int i) {
                        FragmentCloneProcess.this.mPageIndicatorView.setSelectedPage(i);
                    }

                    @Override // com.android.camera.fragment.vv.page.PagerGridLayoutManager.PageListener
                    public void onPageSizeChanged(int i) {
                    }
                });
                int ceil = (int) Math.ceil((double) (((float) queryIntentActivities.size()) / 8.0f));
                this.mPageIndicatorView.initIndicator(ceil);
                if (ceil <= 1) {
                    this.mPageIndicatorView.setVisibility(8);
                } else {
                    this.mPageIndicatorView.setVisibility(0);
                }
                if (this.mPagerGridSnapHelper == null) {
                    this.mPagerGridSnapHelper = new PagerGridSnapHelper();
                    this.mShareRecyclerView.setLayoutManager(pagerGridLayoutManager);
                    this.mPagerGridSnapHelper.attachToRecyclerView(this.mShareRecyclerView);
                }
                this.mShareRecyclerView.setAdapter(this.mShareAdapter);
                this.mShareMessage.setText(R.string.snap_cancel);
                this.mShareMessage.setOnClickListener(new View.OnClickListener() {
                    /* class com.android.camera.fragment.clone.FragmentCloneProcess.AnonymousClass4 */

                    public void onClick(View view) {
                        FragmentCloneProcess.this.hideShareSheet();
                    }
                });
            } else {
                this.mShareAdapter.setShareInfoList(queryIntentActivities);
                this.mShareAdapter.notifyDataSetChanged();
            }
            Completable.create(new AlphaInOnSubscribe(this.mShareDialog)).subscribe();
        }
    }

    private void updateCaptureHintBackground(boolean z) {
        if (z) {
            this.mCaptureHint.setBackgroundResource(R.drawable.clone_warning_textview_corner_bg);
        } else {
            this.mCaptureHint.setBackgroundResource(R.drawable.clone_textview_corner_bg);
        }
    }

    private void updateUiOnOrientationChanged() {
        Log.d(TAG, "updateUiOnOrientationChanged isLandScape = " + isLandScape());
        if (this.mIsPendingShowComposeResult || this.mSaveAndShare.getVisibility() == 0) {
            Log.d(TAG, "updateUiOnOrientationChanged when save button show");
        } else if (isLandScape()) {
            this.mLandscapeHint.setVisibility(8);
            if (this.mCaptureHint.getTag() instanceof Boolean) {
                updateCaptureHintBackground(((Boolean) this.mCaptureHint.getTag()).booleanValue());
            }
            if (!TextUtils.isEmpty(this.mCaptureHint.getText())) {
                showCaptureHint();
            }
            setSnapButtonEnable(true, true);
        } else {
            this.mLandscapeHint.setVisibility(0);
            this.mCaptureHint.setVisibility(8);
            setSnapButtonEnable(false, true);
        }
    }

    @Override // com.android.camera.ui.CameraSnapView.SnapListener, com.android.camera.protocol.ModeProtocol.CloneProcess
    public boolean canSnap() {
        CameraSnapView cameraSnapView;
        return isLandScape() && (cameraSnapView = this.mCameraSnapView) != null && cameraSnapView.isEnabled();
    }

    @Override // com.android.camera.fragment.BaseFragment
    public int getFragmentInto() {
        return BaseFragmentDelegate.FRAGMENT_CLONE_PROCESS;
    }

    /* access modifiers changed from: protected */
    @Override // com.android.camera.fragment.BaseFragment
    public int getLayoutResourceId() {
        return R.layout.fragment_clone_process;
    }

    @Override // com.android.camera.protocol.ModeProtocol.CloneProcess
    public FenShenCam.Mode getMode() {
        return this.mMode;
    }

    @Override // com.android.camera.protocol.ModeProtocol.CloneProcess
    public ContentValues getSaveContentValues() {
        return this.mSaveContentValues;
    }

    @Override // com.android.camera.protocol.ModeProtocol.CloneProcess
    public Status getStatus() {
        return this.mStatus;
    }

    /* access modifiers changed from: protected */
    @Override // com.android.camera.fragment.BaseFragment
    public void initView(View view) {
        this.mRootView = view;
        this.mSnapViewProgress = (ImageView) view.findViewById(R.id.clone_snap_view_progress);
        this.mShareProgress = (ProgressBar) view.findViewById(R.id.clone_share_progress);
        this.mCameraSnapView = (CameraSnapView) view.findViewById(R.id.clone_shutter_button);
        this.mCameraSnapView.setSnapListener(this);
        setSnapButtonEnable(false, true);
        this.mGiveUpToPreview = (ImageView) view.findViewById(R.id.clone_give_up_to_preview);
        this.mSaveAndShare = (ImageView) view.findViewById(R.id.clone_save_and_share);
        this.mStopCapture = (ImageView) view.findViewById(R.id.clone_stop_capture);
        this.mCancelCapture = (ImageView) view.findViewById(R.id.clone_cancel_capture);
        this.mSaveButton = (LottieAnimationView) view.findViewById(R.id.clone_save_button);
        this.mLandscapeHint = (TextView) view.findViewById(R.id.clone_landscape_hint);
        this.mCaptureHint = (TextView) view.findViewById(R.id.clone_capture_hint);
        this.mExitDialog = view.findViewById(R.id.vv_dialog);
        this.mExitDialogMessage = (TextView) this.mExitDialog.findViewById(R.id.vv_dialog_message);
        this.mExitDialogConfirm = (TextView) this.mExitDialog.findViewById(R.id.vv_dialog_positive);
        this.mExitDialogCancel = (TextView) this.mExitDialog.findViewById(R.id.vv_dialog_negative);
        this.mShareDialog = view.findViewById(R.id.vv_share);
        this.mShareMessage = (TextView) this.mShareDialog.findViewById(R.id.vv_share_message);
        this.mShareRecyclerView = (RecyclerView) this.mShareDialog.findViewById(R.id.vv_share_recyclerview);
        this.mShareRecyclerView.setFocusable(false);
        this.mPageIndicatorView = (PageIndicatorView) this.mShareDialog.findViewById(R.id.vv_share_recyclerview_indicator);
        Rect displayRect = Util.getDisplayRect();
        int i = displayRect.top;
        int height = (i - ((Util.sWindowHeight - i) - displayRect.height())) / 2;
        ViewCompat.setRotation(this.mCameraSnapView, 90.0f);
        ViewCompat.setRotation(this.mGiveUpToPreview, 90.0f);
        ViewCompat.setRotation(this.mSaveAndShare, 90.0f);
        ViewCompat.setRotation(this.mSaveButton, 90.0f);
        ViewCompat.setRotation(this.mStopCapture, 90.0f);
        ViewCompat.setRotation(this.mCancelCapture, 90.0f);
        ViewCompat.setRotation(this.mLandscapeHint, 90.0f);
        ViewCompat.setRotation(this.mCaptureHint, 90.0f);
        ViewCompat.setRotation(this.mExitDialog, 90.0f);
        ViewCompat.setRotation(this.mShareDialog, 90.0f);
        this.mCameraSnapView.setOnClickListener(this);
        this.mGiveUpToPreview.setOnClickListener(this);
        this.mSaveAndShare.setOnClickListener(this);
        this.mCancelCapture.setOnClickListener(this);
        this.mStopCapture.setOnClickListener(this);
        this.mSaveButton.setOnClickListener(this);
        FolmeUtils.touchScaleTint(this.mCancelCapture, this.mStopCapture, this.mGiveUpToPreview, this.mSaveAndShare);
        FolmeUtils.touchDialogButtonTint(this.mExitDialogConfirm, this.mExitDialogCancel, this.mShareMessage);
        FolmeUtils.touchScale(this.mSaveButton);
        this.mBottomActionView = (FrameLayout) view.findViewById(R.id.vv_preview_bottom_action);
        ((ViewGroup.MarginLayoutParams) this.mBottomActionView.getLayoutParams()).height = Util.getBottomHeight();
        this.mBottomLayout = (RelativeLayout) view.findViewById(R.id.clone_preview_bottom_parent);
        ((ViewGroup.MarginLayoutParams) this.mBottomLayout.getLayoutParams()).bottomMargin = Util.sBottomMargin;
        if (this.mCurrentMode != 185) {
            quit();
        }
    }

    @Override // com.android.camera.fragment.BaseFragment, com.android.camera.animation.AnimationDelegate.AnimationResource
    public void notifyAfterFrameAvailable(int i) {
        super.notifyAfterFrameAvailable(i);
        if (this.mExitToMimoji) {
            this.mExitToMimoji = false;
            resetToPreview(true);
        } else if (this.mMode == FenShenCam.Mode.PHOTO && DataRepository.dataItemGlobal().isFirstUseClonePhoto()) {
            Config.setOpenUseGuideFromFirstUse(true);
            ((ModeProtocol.BaseDelegate) ModeCoordinatorImpl.getInstance().getAttachProtocol(160)).delegateEvent(23);
            DataRepository.dataItemGlobal().setFirstUseClonePhoto(false);
        } else if (this.mMode == FenShenCam.Mode.VIDEO && DataRepository.dataItemGlobal().isFirstUseCloneVideo()) {
            Config.setOpenUseGuideFromFirstUse(true);
            ((ModeProtocol.BaseDelegate) ModeCoordinatorImpl.getInstance().getAttachProtocol(160)).delegateEvent(23);
            DataRepository.dataItemGlobal().setFirstUseCloneVideo(false);
        }
    }

    @Override // com.android.camera.protocol.ModeProtocol.HandleBackTrace
    public boolean onBackEvent(int i) {
        return hideExitDialog() || hideShareSheet();
    }

    public void onClick(View view) {
        if (this.mSnapViewProgress.getVisibility() == 0 || this.mShareProgress.getVisibility() == 0) {
            Log.w(TAG, "ignore onClick, progress show " + this.mSnapViewProgress.getVisibility());
        } else if (view.getId() == R.id.live_share_item || !(this.mShareDialog.getVisibility() == 0 || this.mExitDialog.getVisibility() == 0)) {
            boolean z = true;
            switch (view.getId()) {
                case R.id.clone_cancel_capture:
                    if (this.mIsPendingShowComposeResult) {
                        Log.w(TAG, "ignore cancel capture");
                        return;
                    }
                    CameraStatUtils.trackCloneClick(MistatsConstants.CloneAttr.VALUE_CANCEL_CLICK);
                    enableUseGuideMenu(true);
                    ModeProtocol.CloneAction cloneAction = (ModeProtocol.CloneAction) ModeCoordinatorImpl.getInstance().getAttachProtocol(419);
                    if (cloneAction != null) {
                        cloneAction.onCancelClicked();
                    }
                    prepare(this.mMode, false);
                    return;
                case R.id.clone_give_up_to_preview:
                    CameraStatUtils.trackCloneClick(MistatsConstants.CloneAttr.VALUE_GIVEUP_CLICK);
                    showExitConfirm(false);
                    return;
                case R.id.clone_save_and_share:
                    CameraStatUtils.trackCloneClick(MistatsConstants.CloneAttr.VALUE_SHARE_CLICK);
                    if (!checkAndShare()) {
                        this.mPendingShare = true;
                        onSaveButtonClick();
                        return;
                    }
                    return;
                case R.id.clone_save_button:
                    onSaveButtonClick();
                    return;
                case R.id.clone_stop_capture:
                    if (this.mIsPendingShowComposeResult) {
                        Log.w(TAG, "ignore stop capture");
                        return;
                    }
                    CameraStatUtils.trackCloneClick(MistatsConstants.CloneAttr.VALUE_STOP_CAPTURE_CLICK);
                    stopCaptureToPreviewResult(false);
                    return;
                case R.id.live_share_item:
                    hideShareSheet();
                    this.mExitToMimoji = true;
                    ResolveInfo resolveInfo = (ResolveInfo) view.getTag();
                    if (this.mMode != FenShenCam.Mode.VIDEO) {
                        z = false;
                    }
                    Util.startShareMedia(resolveInfo.activityInfo.packageName, resolveInfo.activityInfo.name, getContext(), this.mSavedPath, z);
                    return;
                default:
                    return;
            }
        } else {
            Log.w(TAG, "ignore onClick, dialog show");
        }
    }

    @Override // androidx.fragment.app.Fragment
    public void onCreate(@Nullable Bundle bundle) {
        Log.d(TAG, ActivityTarget.ACTION_ON_CREATE);
        super.onCreate(bundle);
    }

    @Override // androidx.fragment.app.Fragment
    public void onDestroy() {
        super.onDestroy();
        ImageView imageView = this.mSnapViewProgress;
        if (imageView != null) {
            imageView.clearAnimation();
        }
    }

    @Override // com.android.camera.protocol.ModeProtocol.CloneProcess
    public void onFirstSubjectFound() {
        this.mLandscapeHint.setVisibility(8);
        this.mCaptureHint.setVisibility(0);
    }

    @Override // androidx.fragment.app.Fragment
    public void onPause() {
        Log.d(TAG, "onPause");
        super.onPause();
        this.mPaused = true;
        hideExitDialog();
        hideShareSheet();
        ModeProtocol.CloneChooser cloneChooser = (ModeProtocol.CloneChooser) ModeCoordinatorImpl.getInstance().getAttachProtocol(416);
        if (cloneChooser == null || !cloneChooser.isShow()) {
            FenShenCam.Mode mode = this.mMode;
            if (mode != null) {
                prepare(mode, false);
                return;
            }
            return;
        }
        Log.d(TAG, "skip prepare when show choose dialog");
    }

    @Override // com.android.camera.protocol.ModeProtocol.CloneProcess
    public void onPreviewPrepare(ContentValues contentValues) {
        this.mSaveContentValues = contentValues;
    }

    @Override // com.android.camera.fragment.BaseFragment, androidx.fragment.app.Fragment
    public void onResume() {
        Log.d(TAG, "onResume");
        super.onResume();
        this.mPaused = false;
        FenShenCam.Mode cloneMode = Config.getCloneMode();
        if (cloneMode != null && this.mCurrentMode == 185) {
            prepare(cloneMode, true);
        }
        ModeProtocol.CloneAction cloneAction = (ModeProtocol.CloneAction) ModeCoordinatorImpl.getInstance().getAttachProtocol(419);
        if (cloneAction != null) {
            cloneAction.onFragmentResume();
        }
    }

    @Override // com.android.camera.protocol.ModeProtocol.CloneProcess
    public void onSaveFinish(Uri uri) {
        Log.d(TAG, "onSaveFinish " + uri);
        if (this.mMode == FenShenCam.Mode.PHOTO) {
            this.mSavedPath = MediaProviderUtil.getPathFromUri(getContext(), uri);
        } else {
            ContentValues contentValues = this.mSaveContentValues;
            if (contentValues != null) {
                this.mSavedPath = contentValues.getAsString("_data");
            }
        }
        if (this.mPendingShare) {
            setProgressBarVisible(false);
            this.mShareProgress.setVisibility(8);
            this.mSaveButton.setVisibility(0);
            this.mSaveAndShare.setVisibility(0);
            showShareSheet();
            return;
        }
        resetToPreview(true);
    }

    @Override // com.android.camera.ui.CameraSnapView.SnapListener
    public void onSnapClick() {
        Log.d(TAG, "onSnapClick");
        if (this.mSnapViewProgress.getVisibility() == 0 || this.mShareProgress.getVisibility() == 0) {
            Log.d(TAG, "onSnapClick ignore click case 1");
        } else if (this.mExitDialog.getVisibility() == 0) {
            Log.d(TAG, "onSnapClick ignore click case 2");
        } else {
            Camera camera = (Camera) getContext();
            if (camera != null) {
                Module currentModule = camera.getCurrentModule();
                if (currentModule != null && currentModule.isIgnoreTouchEvent() && !currentModule.isFrameAvailable()) {
                    Log.d(TAG, "onSnapClick ignore click case 3");
                } else if (this.mSaveButton.getVisibility() == 0) {
                    Log.d(TAG, "onSnapClick performClick mSaveButton");
                    this.mSaveButton.performClick();
                } else if (this.mIsPendingShowComposeResult) {
                    Log.d(TAG, "onSnapClick ignore click case 4");
                } else {
                    ModeProtocol.CameraAction cameraAction = (ModeProtocol.CameraAction) ModeCoordinatorImpl.getInstance().getAttachProtocol(161);
                    if (cameraAction != null && this.mCurrentMode == 185) {
                        cameraAction.onShutterButtonClick(10);
                    }
                }
            }
        }
    }

    @Override // com.android.camera.ui.CameraSnapView.SnapListener
    public void onSnapLongPress() {
    }

    @Override // com.android.camera.ui.CameraSnapView.SnapListener
    public void onSnapLongPressCancelIn() {
    }

    @Override // com.android.camera.ui.CameraSnapView.SnapListener
    public void onSnapLongPressCancelOut() {
    }

    @Override // com.android.camera.ui.CameraSnapView.SnapListener
    public void onSnapPrepare() {
    }

    @Override // com.android.camera.ui.CameraSnapView.SnapListener
    public void onTrackSnapMissTaken(long j) {
    }

    @Override // com.android.camera.ui.CameraSnapView.SnapListener
    public void onTrackSnapTaken(long j) {
    }

    @Override // com.android.camera.protocol.ModeProtocol.CloneProcess
    public void prepare(FenShenCam.Mode mode, boolean z) {
        Log.d(TAG, "prepare E " + mode.toString() + ", isLandScape " + isLandScape());
        this.mSavedPath = null;
        this.mPendingShare = false;
        this.mRootView.setVisibility(0);
        this.mCurrentMode = 185;
        this.mMode = mode;
        initShutterButton(mode);
        setProgressBarVisible(false);
        this.mShareProgress.setVisibility(8);
        this.mSaveAndShare.setVisibility(8);
        this.mSaveButton.setVisibility(8);
        this.mGiveUpToPreview.setVisibility(8);
        this.mStopCapture.setVisibility(8);
        this.mCancelCapture.setVisibility(8);
        if (!z) {
            this.mCaptureHint.setText("");
            this.mCaptureHint.setVisibility(8);
        }
        enableUseGuideMenu(true);
        this.mIsPendingShowComposeResult = false;
        if (!isLandScape()) {
            this.mLandscapeHint.setVisibility(0);
            setSnapButtonEnable(false, true);
        } else {
            this.mLandscapeHint.setVisibility(8);
            setSnapButtonEnable(true, true);
        }
        Log.d(TAG, "prepare X " + mode.toString());
        this.mStatus = Status.CAPTURING;
    }

    @Override // com.android.camera.protocol.ModeProtocol.CloneProcess
    public void processingFinish() {
        this.mCameraSnapView.triggerAnimation(BottomAnimationConfig.generate(false, this.mCurrentMode, false, false, false).configVariables());
    }

    @Override // com.android.camera.protocol.ModeProtocol.CloneProcess
    public void processingPrepare() {
        this.mCameraSnapView.prepareRecording(BottomAnimationConfig.generate(false, this.mCurrentMode, true, false, false).configVariables());
    }

    @Override // com.android.camera.protocol.ModeProtocol.CloneProcess
    public void processingStart() {
        Log.d(TAG, "processingResume");
        this.mLandscapeHint.setVisibility(8);
        BottomAnimationConfig configVariables = BottomAnimationConfig.generate(false, this.mCurrentMode, true, false, false).configVariables();
        configVariables.setSpecifiedDuration(5000);
        this.mCameraSnapView.triggerAnimation(configVariables);
    }

    @Override // com.android.camera.fragment.BaseFragment, com.android.camera.animation.AnimationDelegate.AnimationResource
    public void provideAnimateElement(int i, List<Completable> list, int i2) {
        super.provideAnimateElement(i, list, i2);
        this.mCurrentMode = i;
        this.mResetType = i2;
        Log.d(TAG, "provideAnimateElement mCurrentMode " + this.mCurrentMode + ", mIsPendingShowComposeResult " + this.mIsPendingShowComposeResult);
        if (this.mCurrentMode == 185) {
            if (this.mIsPendingShowComposeResult) {
                this.mIsPendingShowComposeResult = false;
                if (this.mMode != null) {
                    Log.d(TAG, "provideAnimateElement restore ui");
                    prepare(this.mMode, false);
                }
            }
            updateUiOnOrientationChanged();
            return;
        }
        this.mLandscapeHint.setVisibility(8);
        this.mCaptureHint.setVisibility(8);
        this.mSaveButton.setVisibility(8);
    }

    @Override // com.android.camera.fragment.BaseFragment, com.android.camera.animation.AnimationDelegate.AnimationResource
    public void provideRotateItem(List<View> list, int i) {
        super.provideRotateItem(list, i);
        ModeProtocol.CameraAction cameraAction = (ModeProtocol.CameraAction) ModeCoordinatorImpl.getInstance().getAttachProtocol(161);
        if (cameraAction == null || !cameraAction.isDoingAction()) {
            updateUiOnOrientationChanged();
            return;
        }
        Log.d(TAG, "ignore provideRotateItem newDegree " + i);
    }

    @Override // com.android.camera.protocol.ModeProtocol.CloneProcess
    public void quit() {
        Log.d(TAG, "quit");
        this.mRootView.setVisibility(8);
    }

    /* access modifiers changed from: protected */
    @Override // com.android.camera.fragment.BaseFragment
    public void register(ModeProtocol.ModeCoordinator modeCoordinator) {
        super.register(modeCoordinator);
        modeCoordinator.attachProtocol(418, this);
        registerBackStack(modeCoordinator, this);
    }

    public void resetToPreview(boolean z) {
        Log.d(TAG, "resetToPreview toModeSelect " + z);
        if (this.mSnapViewProgress.getVisibility() == 0) {
            setProgressBarVisible(false);
        }
        if (this.mShareProgress.getVisibility() == 0) {
            this.mShareProgress.setVisibility(8);
        }
        ModeProtocol.CameraAction cameraAction = (ModeProtocol.CameraAction) ModeCoordinatorImpl.getInstance().getAttachProtocol(161);
        if (cameraAction == null) {
            Log.d(TAG, "resetToPreview error, action null");
        } else if (z) {
            cameraAction.onReviewDoneClicked();
        } else {
            cameraAction.onReviewCancelClicked();
        }
    }

    @Override // com.android.camera.protocol.ModeProtocol.CloneProcess
    public void setDetectedPersonInPreview(boolean z) {
        this.mDetectedPersonInPreview = z;
        if (isLandScape()) {
            setSnapButtonEnable(z, true);
        }
    }

    @Override // com.android.camera.protocol.ModeProtocol.CloneProcess
    public void showExitConfirm(final boolean z) {
        if (this.mExitDialog.getVisibility() != 0) {
            Log.d(TAG, "showExitConfirm");
            setSnapButtonEnable(false, false);
            if (z) {
                this.mExitDialogMessage.setText(R.string.live_edit_exit_message);
                this.mExitDialogConfirm.setText(R.string.live_edit_exit_confirm);
                this.mExitDialogCancel.setText(R.string.snap_cancel);
            } else {
                this.mExitDialogMessage.setText(R.string.clone_recapture_alert);
                this.mExitDialogConfirm.setText(R.string.dialog_ok);
                this.mExitDialogCancel.setText(R.string.snap_cancel);
            }
            this.mExitDialogConfirm.setOnClickListener(new View.OnClickListener() {
                /* class com.android.camera.fragment.clone.FragmentCloneProcess.AnonymousClass1 */

                public void onClick(View view) {
                    if (z) {
                        FragmentCloneProcess.this.enableUseGuideMenu(true);
                        ModeProtocol.CloneAction cloneAction = (ModeProtocol.CloneAction) ModeCoordinatorImpl.getInstance().getAttachProtocol(419);
                        if (cloneAction != null) {
                            cloneAction.onExitClicked();
                        }
                        FragmentCloneProcess.this.resetToPreview(true);
                    } else {
                        FragmentCloneProcess fragmentCloneProcess = FragmentCloneProcess.this;
                        fragmentCloneProcess.prepare(fragmentCloneProcess.mMode, false);
                        ModeProtocol.CloneAction cloneAction2 = (ModeProtocol.CloneAction) ModeCoordinatorImpl.getInstance().getAttachProtocol(419);
                        if (cloneAction2 != null) {
                            cloneAction2.onGiveUpClicked();
                        }
                    }
                    FragmentCloneProcess.this.hideExitDialog();
                }
            });
            this.mExitDialogCancel.setOnClickListener(new View.OnClickListener() {
                /* class com.android.camera.fragment.clone.FragmentCloneProcess.AnonymousClass2 */

                public void onClick(View view) {
                    FragmentCloneProcess.this.hideExitDialog();
                }
            });
            Completable.create(new AlphaInOnSubscribe(this.mExitDialog)).subscribe();
        }
    }

    @Override // com.android.camera.protocol.ModeProtocol.CloneProcess
    public void showSaveAndGiveUp() {
        if (!this.mIsPendingShowComposeResult) {
            Log.w(TAG, "ignore showSaveAndGiveUp");
        } else if (this.mStatus != Status.STOP) {
            Log.w(TAG, "showSaveAndGiveUp ignore, not stop");
        } else {
            Log.d(TAG, "showSaveAndGiveUp");
            this.mStatus = Status.SAVE;
            this.mCameraSnapView.hideCirclePaintItem();
            this.mCameraSnapView.hideRoundPaintItem();
            AlphaInOnSubscribe.directSetResult(this.mSaveButton);
            this.mSaveButton.setScale(0.38f);
            this.mSaveButton.k(R.raw.vv_save);
            this.mSaveButton.T();
            this.mStopCapture.setVisibility(8);
            this.mCancelCapture.setVisibility(8);
            this.mCaptureHint.setVisibility(8);
            setProgressBarVisible(false);
            alphaAnimateIn(this.mSaveAndShare);
            alphaAnimateIn(this.mGiveUpToPreview);
            this.mIsPendingShowComposeResult = false;
            setSnapButtonEnable(true, true);
        }
    }

    @Override // com.android.camera.protocol.ModeProtocol.CloneProcess
    public void showStopAndCancel() {
        Log.d(TAG, "showFinishAndCancel");
        alphaAnimateIn(this.mStopCapture);
        alphaAnimateIn(this.mCancelCapture);
    }

    @Override // com.android.camera.protocol.ModeProtocol.CloneProcess
    public void stopCaptureToPreviewResult(boolean z) {
        ModeProtocol.CloneAction cloneAction = (ModeProtocol.CloneAction) ModeCoordinatorImpl.getInstance().getAttachProtocol(419);
        if (cloneAction == null) {
            Log.w(TAG, "stopCaptureToPreviewResult cloneAction is null");
        } else if (this.mIsPendingShowComposeResult) {
            Log.w(TAG, "stopCaptureToPreviewResult ignore, pending show composeResult");
        } else if (this.mStatus != Status.CAPTURING) {
            Log.w(TAG, "stopCaptureToPreviewResult ignore, not capturing");
        } else {
            Log.d("stopCaptureToPreviewResult", "stopCaptureToPreviewResult");
            this.mStatus = Status.STOP;
            if (!z) {
                this.mCaptureHint.setVisibility(8);
            }
            cloneAction.onStopClicked();
            this.mIsPendingShowComposeResult = true;
            if (this.mMode == FenShenCam.Mode.VIDEO) {
                showSaveAndGiveUp();
                return;
            }
            this.mCameraSnapView.hideRoundPaintItem();
            setSnapButtonEnable(false, false);
            setProgressBarVisible(true);
        }
    }

    /* access modifiers changed from: protected */
    @Override // com.android.camera.fragment.BaseFragment
    public void unRegister(ModeProtocol.ModeCoordinator modeCoordinator) {
        super.unRegister(modeCoordinator);
        modeCoordinator.detachProtocol(418, this);
        unRegisterBackStack(modeCoordinator, this);
    }

    @Override // com.android.camera.protocol.ModeProtocol.CloneProcess
    public void updateCaptureMessage(int i, boolean z) {
        if (this.mPaused && i == R.string.clone_offset_is_too_large) {
            Log.w(TAG, "ignore updateCaptureMessage, paused");
        } else if (!isAdded()) {
            Log.w(TAG, "ignore updateCaptureMessage, fragment not added");
        } else if (isLandScape()) {
            this.mCaptureHint.setTag(Boolean.valueOf(z));
            updateCaptureHintBackground(z);
            this.mCaptureHint.setText(i);
            showCaptureHint();
        } else {
            this.mCaptureHint.setTag(Boolean.valueOf(z));
            this.mCaptureHint.setText(i);
        }
    }
}
