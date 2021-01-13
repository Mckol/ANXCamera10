package com.android.camera.fragment.bottom.action;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.PathInterpolator;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.core.graphics.drawable.RoundedBitmapDrawable;
import androidx.core.graphics.drawable.RoundedBitmapDrawableFactory;
import androidx.core.view.ViewCompat;
import androidx.fragment.app.FragmentManager;
import androidx.interpolator.view.animation.FastOutSlowInInterpolator;
import b.c.a.c;
import com.airbnb.lottie.LottieAnimationView;
import com.android.camera.ActivityBase;
import com.android.camera.Camera;
import com.android.camera.CameraSettings;
import com.android.camera.HybridZoomingSystem;
import com.android.camera.R;
import com.android.camera.RotateDialogController;
import com.android.camera.Thumbnail;
import com.android.camera.ThumbnailUpdater;
import com.android.camera.Util;
import com.android.camera.animation.FolmeUtils;
import com.android.camera.animation.FragmentAnimationFactory;
import com.android.camera.animation.type.AlphaInOnSubscribe;
import com.android.camera.animation.type.AlphaOutOnSubscribe;
import com.android.camera.animation.type.RotateOnSubscribe;
import com.android.camera.data.DataRepository;
import com.android.camera.data.data.global.ComponentModuleList;
import com.android.camera.data.data.global.DataItemGlobal;
import com.android.camera.data.data.runing.ComponentRunningAIWatermark;
import com.android.camera.data.data.runing.ComponentRunningFastMotion;
import com.android.camera.data.data.runing.ComponentRunningShine;
import com.android.camera.features.mimoji2.module.protocol.MimojiModeProtocol;
import com.android.camera.features.mimoji2.widget.helper.MimojiStatusManager2;
import com.android.camera.fragment.BaseFragment;
import com.android.camera.fragment.FragmentUtils;
import com.android.camera.fragment.bottom.BottomActionMenu;
import com.android.camera.fragment.bottom.BottomAnimationConfig;
import com.android.camera.fragment.mode.FragmentMoreModePopup;
import com.android.camera.lib.compatibility.related.vibrator.ViberatorContext;
import com.android.camera.log.Log;
import com.android.camera.module.FunModule;
import com.android.camera.module.ILiveModule;
import com.android.camera.module.LiveModule;
import com.android.camera.module.MiLiveModule;
import com.android.camera.module.Module;
import com.android.camera.module.VideoModule;
import com.android.camera.module.impl.component.MimojiStatusManager;
import com.android.camera.module.loader.StartControl;
import com.android.camera.permission.PermissionManager;
import com.android.camera.protocol.ModeCoordinatorImpl;
import com.android.camera.protocol.ModeProtocol;
import com.android.camera.statistic.CameraStatUtils;
import com.android.camera.statistic.MistatsConstants;
import com.android.camera.statistic.ScenarioTrackUtil;
import com.android.camera.timerburst.TimerBurstController;
import com.android.camera.ui.AdjustAnimationView;
import com.android.camera.ui.AnimationView;
import com.android.camera.ui.CameraSnapView;
import com.android.camera.ui.CapsuleLayout;
import com.android.camera.ui.DragLayout;
import com.android.camera.ui.EdgeHorizonScrollView;
import com.android.camera.ui.ModeSelectView;
import com.android.camera.ui.PopupMenuLayout;
import com.android.camera.ui.ShapeBackGroundView;
import com.android.camera.ui.drawable.snap.PaintConditionReferred;
import com.xiaomi.stat.d;
import io.reactivex.Completable;
import java.util.List;
import java.util.Locale;
import miui.view.animation.CubicEaseInInterpolator;
import miui.view.animation.CubicEaseOutInterpolator;
import miui.view.animation.QuinticEaseOutInterpolator;
import miui.view.animation.SineEaseOutInterpolator;

public class FragmentBottomAction extends BaseFragment implements View.OnClickListener, ModeProtocol.ModeChangeController, ModeProtocol.ActionProcessing, ModeProtocol.HandleBeautyRecording, ModeProtocol.HandlerSwitcher, ModeProtocol.HandleBackTrace, ModeSelectView.onModeSelectedListener, CameraSnapView.SnapListener, ModeProtocol.BottomMenuProtocol, ModeProtocol.MoreModePopupController, DragLayout.OnDragListener {
    public static final int FRAGMENT_INFO = 241;
    private static final int MSG_SHOW_PROGRESS = 1;
    private static final String TAG = "FragmentBottomAction";
    private boolean mBackEnable;
    private FrameLayout mBottomActionView;
    private ValueAnimator mBottomAnimator;
    private View mBottomMenuLayout;
    private ImageView mBottomRecordingCameraPicker;
    private TextView mBottomRecordingTime;
    private int mBottomRollDownHeight;
    private boolean mCameraPickEnable;
    private ImageView mCameraPicker;
    private int mCaptureProgressDelay;
    private ComponentModuleList mComponentModuleList;
    private CubicEaseOutInterpolator mCubicEaseOut;
    private int mCurrentBeautyActionMenuType;
    private int mCurrentLiveActionMenuType;
    private View mDocumentContainer;
    private DragLayout mDragUpLayout;
    private EdgeHorizonScrollView mEdgeHorizonScrollView;
    private ImageView mExternalModeTipIcon;
    private CapsuleLayout mExternalModeTipLayout;
    private TextView mExternalModeTipText;
    private int mFilterListHeight;
    private FragmentActionMimoji mFragmentActionMimoji;
    private View mFragmentLayoutExtra;
    private FragmentLighting mFragmentLighting;
    @SuppressLint({"HandlerLeak"})
    private Handler mHandler = new Handler() {
        /* class com.android.camera.fragment.bottom.action.FragmentBottomAction.AnonymousClass1 */

        public void handleMessage(Message message) {
            if (message.what == 1) {
                FragmentBottomAction.this.mThumbnailProgress.setVisibility(0);
            }
        }
    };
    private boolean mInLoading;
    private boolean mIsBottomRollDown = false;
    private boolean mIsIntentAction;
    private boolean mIsShowLighting = false;
    private boolean mIsShowMiMoji = false;
    private long mLastPauseTime;
    private boolean mLongPressBurst;
    private ImageView mMimojiBack;
    private ShapeBackGroundView mModeSelectBackgroundLayout;
    private BottomActionMenu mModeSelectLayout;
    private ModeSelectView mModeSelectView;
    private FragmentMoreModePopup mMoreModePopup;
    private int mMoreModeStyle = -1;
    private PopupMenuLayout mPopupMenuLayout;
    private ProgressBar mPostProcess;
    private boolean mPreGifStatus;
    private int mRecordProgressDelay;
    private LottieAnimationView mRecordingPause;
    private ImageView mRecordingReverse;
    private ImageView mRecordingSnap;
    private LottieAnimationView mRecordingSwitch;
    private AlertDialog mReverseDialog;
    private CameraSnapView mShutterButton;
    private SineEaseOutInterpolator mSineEaseOut;
    private ImageView mThumbnailImage;
    private ViewGroup mThumbnailImageLayout;
    private ProgressBar mThumbnailProgress;
    private RelativeLayout mV9bottomParentLayout;
    private boolean mVideoCaptureEnable;
    private boolean mVideoPauseSupported;
    private boolean mVideoRecordingPaused;
    private boolean mVideoRecordingStarted;
    private boolean mVideoReverseEnable;

    static /* synthetic */ void a(AdjustAnimationView adjustAnimationView, View view) {
        adjustAnimationView.startBackgroundAnimator();
        view.setVisibility(4);
    }

    static /* synthetic */ void a(AdjustAnimationView adjustAnimationView, AnimationView animationView, View view, float f, float f2) {
        adjustAnimationView.setVisibility(4);
        animationView.setVisibility(4);
        view.setVisibility(0);
        animationView.clear();
        animationView.setScaleX(1.0f);
        animationView.setScaleY(1.0f);
        animationView.setX(f);
        animationView.setY(f2);
    }

    static /* synthetic */ void a(AnimationView animationView, ValueAnimator valueAnimator) {
        Log.d(TAG, "onAnimationUpdate: " + valueAnimator.getAnimatedFraction());
        animationView.setProgress(valueAnimator.getAnimatedFraction());
    }

    private void adjustViewBackground(View view, int i) {
        int uiStyle = DataRepository.dataItemRunning().getUiStyle();
        if (uiStyle == 1 || uiStyle == 3) {
            this.mCameraPicker.setBackgroundResource(R.drawable.bg_thumbnail_background_full);
            this.mRecordingSwitch.setBackgroundResource(R.drawable.bg_thumbnail_background_full);
            this.mMimojiBack.setBackgroundResource(R.drawable.bg_thumbnail_background_full);
            if (this.mCurrentMode != 204 || !DataRepository.dataItemRunning().getComponentRunningDualVideo().ismShouldDraw6Patch()) {
                this.mThumbnailImage.setBackgroundResource(R.drawable.bg_thumbnail_background_full);
            } else {
                this.mThumbnailImage.setBackgroundResource(R.drawable.bg_thumbnail_background_half);
            }
        }
    }

    private void changeModeByNewMode(int i, int i2, boolean z) {
        ModeProtocol.ConfigChanges configChanges;
        if (i == 166 && CameraSettings.isFrontCamera() && DataRepository.dataItemFeature().vk()) {
            i = 176;
        }
        if (i == 163 || i == 165) {
            i = DataRepository.dataItemConfig().getComponentConfigRatio().getMappingModeByRatio(163);
        }
        if (i == 205 && this.mCurrentMode != 188) {
            DataRepository.dataItemRunning().getComponentRunningAIWatermark().resetAIWatermark(true);
        }
        if (i != this.mCurrentMode) {
            if (i == 167 && CameraSettings.isFromProVideoMudule()) {
                i = 180;
            }
            int i3 = this.mCurrentMode;
            if (i3 == 180) {
                CameraSettings.setIsFromProVideoMudule(true);
            } else if (i3 == 167) {
                CameraSettings.setIsFromProVideoMudule(false);
            }
            if (this.mCurrentMode == 163 && CameraSettings.isUltraPixelOn() && (configChanges = (ModeProtocol.ConfigChanges) ModeCoordinatorImpl.getInstance().getAttachProtocol(164)) != null) {
                configChanges.switchOffElementsSilent(209);
            }
            if ((i == 174 || i == 183) && !CameraSettings.isLiveModuleClicked()) {
                CameraSettings.setLiveModuleClicked(true);
            }
            DataItemGlobal dataItemGlobal = (DataItemGlobal) DataRepository.provider().dataGlobal();
            if (!isThumbLoading()) {
                ModeProtocol.CameraAction cameraAction = (ModeProtocol.CameraAction) ModeCoordinatorImpl.getInstance().getAttachProtocol(161);
                if (cameraAction == null || !cameraAction.isDoingAction()) {
                    dataItemGlobal.setCurrentMode(i);
                    if (!z) {
                        ViberatorContext.getInstance(getActivity().getApplicationContext()).performModeSwitch();
                    }
                    ((Camera) getContext()).onModeSelected(StartControl.create(i).setStartDelay(i2).setResetType(4).setViewConfigType(2).setNeedBlurAnimation(true));
                }
            }
        }
    }

    private void hideModeOrExternalTipLayout() {
        View view;
        if (this.mExternalModeTipLayout.getTag() == null || ((Integer) this.mExternalModeTipLayout.getTag()).intValue() != 1) {
            ModeSelectView modeSelectView = this.mModeSelectView;
            this.mDragUpLayout.setDragEnable(false);
            view = modeSelectView;
        } else {
            view = this.mExternalModeTipLayout;
        }
        if (view.getVisibility() != 8) {
            view.setVisibility(8);
            view.setEnabled(false);
        }
    }

    /* access modifiers changed from: private */
    /* access modifiers changed from: public */
    private void initAnimation(AdjustAnimationView adjustAnimationView, AnimationView animationView, View view) {
        animationView.clearAnimation();
        RectF locationOnScreen = Util.getLocationOnScreen(animationView);
        float dimension = getResources().getDimension(R.dimen.bottom_picker_width);
        float dimension2 = getResources().getDimension(R.dimen.bottom_picker_margin);
        int i = Util.sBottomBarHeight;
        float bottomHeight = ((float) (Util.sWindowHeight - Util.getBottomHeight())) + (((float) i) * 0.3f) + (((((float) i) * 0.7f) - dimension) / 2.0f);
        RectF rectF = new RectF(dimension2, bottomHeight, dimension2 + dimension, dimension + bottomHeight);
        animationView.setUp(locationOnScreen);
        float width = rectF.width() / Math.min(locationOnScreen.width(), locationOnScreen.height());
        float x = animationView.getX();
        float y = animationView.getY();
        float width2 = locationOnScreen.left + (locationOnScreen.width() / 2.0f);
        float height = locationOnScreen.top + (locationOnScreen.height() / 2.0f);
        float width3 = rectF.left + (rectF.width() / 2.0f);
        float height2 = rectF.top + (rectF.height() / 2.0f);
        Log.d(TAG, "showDocumentReviewViews: startViewBounds = " + locationOnScreen + ", endViewBounds = " + rectF + ", scale = " + width + ", startCenter = " + width2 + "x" + height + ", endCenter = " + width3 + "x" + height2);
        animationView.animate().scaleX(width).scaleY(width).translationX(width3 - width2).translationY(height2 - height).setDuration(Util.getExitDuration()).setInterpolator(new FastOutSlowInInterpolator()).setStartDelay(Util.getSuspendDuration()).setUpdateListener(new a(animationView)).withStartAction(new c(adjustAnimationView, view)).withEndAction(new b(adjustAnimationView, animationView, view, x, y)).start();
    }

    private void initThumbLayout() {
        if (!shouldUseThumbnialAsExit()) {
            initThumbnialAsThumbnail();
        } else {
            initThumbnailAsExit();
        }
    }

    private void initThumbnailAsExit() {
        Log.d(TAG, "initThumbnailAsExit: ");
        ((ViewGroup.MarginLayoutParams) this.mThumbnailImage.getLayoutParams()).setMargins(0, 0, 0, 0);
        this.mThumbnailImage.setScaleType(ImageView.ScaleType.CENTER);
        this.mThumbnailImage.setImageResource(R.drawable.ic_vector_close_fill);
    }

    private void initThumbnialAsThumbnail() {
        Log.d(TAG, "initThumbnialAsThumbnail: ");
        this.mThumbnailImage.setScaleType(ImageView.ScaleType.CENTER_CROP);
        this.mThumbnailImage.setImageResource(0);
        ActivityBase activityBase = (ActivityBase) getContext();
        if ((activityBase.startFromSecureKeyguard() || activityBase.isGalleryLocked()) && !activityBase.isJumpBack()) {
            activityBase.getThumbnailUpdater().setThumbnail(null, true, false);
        } else if (PermissionManager.checkStoragePermissions()) {
            activityBase.getThumbnailUpdater().getLastThumbnail();
        }
    }

    private boolean isFPS960() {
        if (this.mCurrentMode != 172 || !DataRepository.dataItemFeature().yk()) {
            return false;
        }
        return DataRepository.dataItemConfig().getComponentConfigSlowMotion().isSlowMotionFps960();
    }

    private boolean isFocusOrZoomMoving() {
        ModeProtocol.MainContentProtocol mainContentProtocol = (ModeProtocol.MainContentProtocol) ModeCoordinatorImpl.getInstance().getAttachProtocol(166);
        return mainContentProtocol != null && (mainContentProtocol.isFocusViewMoving() || mainContentProtocol.isZoomViewMoving());
    }

    private boolean isThumbLoading() {
        return this.mInLoading;
    }

    private void setPausePlaySwitchTarget(boolean z) {
        this.mRecordingPause.setScale(0.38f);
        this.mRecordingPause.k(z ? R.raw.switch_pause_play : R.raw.switch_play_pause);
        this.mRecordingPause.setProgress(1.0f);
    }

    private void setProgressBarVisible(int i) {
        if (this.mPostProcess.getVisibility() != i) {
            if (i == 0) {
                this.mPostProcess.setAlpha(0.0f);
                Drawable drawable = this.mShutterButton.isBottomVisible() ? getResources().getDrawable(R.drawable.post_process_progress_gray) : getResources().getDrawable(R.drawable.post_process_progress_white);
                if (this.mPostProcess.getIndeterminateDrawable() != null) {
                    drawable.setBounds(this.mPostProcess.getIndeterminateDrawable().getBounds());
                }
                this.mPostProcess.setIndeterminateDrawable(drawable);
                this.mPostProcess.setVisibility(0);
                ValueAnimator ofFloat = ValueAnimator.ofFloat(0.0f, 1.0f);
                ofFloat.setDuration(300L);
                ofFloat.setStartDelay(160);
                ofFloat.setInterpolator(new PathInterpolator(0.25f, 0.1f, 0.25f, 1.0f));
                ofFloat.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                    /* class com.android.camera.fragment.bottom.action.FragmentBottomAction.AnonymousClass4 */

                    public void onAnimationUpdate(ValueAnimator valueAnimator) {
                        Float f = (Float) valueAnimator.getAnimatedValue();
                        FragmentBottomAction.this.mPostProcess.setAlpha(f.floatValue());
                        FragmentBottomAction.this.mPostProcess.setScaleX((f.floatValue() * 0.1f) + 0.9f);
                        FragmentBottomAction.this.mPostProcess.setScaleY((f.floatValue() * 0.1f) + 0.9f);
                    }
                });
                ofFloat.start();
                return;
            }
            ValueAnimator ofFloat2 = ValueAnimator.ofFloat(1.0f, 0.0f);
            ofFloat2.setDuration(300L);
            ofFloat2.setInterpolator(new CubicEaseInInterpolator());
            ofFloat2.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                /* class com.android.camera.fragment.bottom.action.FragmentBottomAction.AnonymousClass5 */

                public void onAnimationUpdate(ValueAnimator valueAnimator) {
                    FragmentBottomAction.this.mPostProcess.setAlpha(((Float) valueAnimator.getAnimatedValue()).floatValue());
                }
            });
            ofFloat2.addListener(new Animator.AnimatorListener() {
                /* class com.android.camera.fragment.bottom.action.FragmentBottomAction.AnonymousClass6 */

                public void onAnimationCancel(Animator animator) {
                    FragmentBottomAction.this.mPostProcess.setVisibility(8);
                }

                public void onAnimationEnd(Animator animator) {
                    FragmentBottomAction.this.mPostProcess.setVisibility(8);
                }

                public void onAnimationRepeat(Animator animator) {
                }

                public void onAnimationStart(Animator animator) {
                }
            });
            ofFloat2.start();
        }
    }

    private void setRecordingSwitchTarget(boolean z) {
        this.mRecordingSwitch.setScale(0.35f);
        this.mRecordingSwitch.k(z ? R.raw.switch_capture_record : R.raw.switch_record_capture);
        this.mRecordingSwitch.setProgress(1.0f);
    }

    private void setRecordingTimeState(int i) {
        if (i == 1) {
            Completable.create(new AlphaInOnSubscribe(this.mBottomRecordingTime)).subscribe();
        } else if (i == 2) {
            if (this.mBottomRecordingTime.getVisibility() == 0) {
                Completable.create(new AlphaOutOnSubscribe(this.mBottomRecordingTime)).subscribe();
            }
            if (this.mBottomRecordingCameraPicker.getVisibility() == 0) {
                Completable.create(new AlphaOutOnSubscribe(this.mBottomRecordingCameraPicker)).subscribe();
            }
        } else if (i == 3) {
            Completable.create(new AlphaInOnSubscribe(this.mBottomRecordingCameraPicker)).subscribe();
        } else if (i == 4 && this.mBottomRecordingTime.getVisibility() != 0) {
            this.mBottomRecordingTime.setVisibility(0);
        }
    }

    private boolean shouldUseThumbnialAsExit() {
        return this.mIsIntentAction || (this.mCurrentMode == 204 && !DataRepository.dataItemRunning().getComponentRunningDualVideo().ismShouldDraw6Patch() && DataRepository.dataItemFeature().tj());
    }

    private void showModeOrExternalTipLayout(boolean z) {
        View view;
        if (this.mExternalModeTipLayout.getTag() == null || ((Integer) this.mExternalModeTipLayout.getTag()).intValue() != 1) {
            ModeSelectView modeSelectView = this.mModeSelectView;
            switchMoreMode(true);
            view = modeSelectView;
        } else {
            view = this.mExternalModeTipLayout;
        }
        view.setEnabled(true);
        if (view.getVisibility() != 0) {
            if (z) {
                Completable.create(new AlphaInOnSubscribe(view).setStartDelayTime(140).setInterpolator(new QuinticEaseOutInterpolator())).subscribe();
            } else {
                AlphaInOnSubscribe.directSetResult(view);
            }
        }
    }

    private void showNormalMimoji2Bottom() {
        ModeProtocol.BottomPopupTips bottomPopupTips;
        MimojiStatusManager2 mimojiStatusManager2 = DataRepository.dataItemLive().getMimojiStatusManager2();
        if (!mimojiStatusManager2.isInMimojiPreviewPlay()) {
            this.mShutterButton.onForceVideoStateChange(PaintConditionReferred.create(184));
            if (CameraSettings.isGifOn()) {
                mimojiStatusManager2.setMimojiRecordState(1);
                animateViews(-1, (List<Completable>) null, this.mRecordingSwitch);
                animateViews(-1, (List<Completable>) null, this.mCameraPicker);
            } else {
                animateViews(1, (List<Completable>) null, this.mRecordingSwitch);
                animateViews(-1, (List<Completable>) null, this.mCameraPicker);
            }
            animateViews(1, (List<Completable>) null, this.mModeSelectLayout.getView());
            animateViews(1, (List<Completable>) null, this.mShutterButton);
            animateViews(-1, (List<Completable>) null, this.mMimojiBack);
            if (this.mIsShowMiMoji && (bottomPopupTips = (ModeProtocol.BottomPopupTips) ModeCoordinatorImpl.getInstance().getAttachProtocol(175)) != null) {
                bottomPopupTips.showMimojiPanel(0);
            }
        }
    }

    private void showReverseConfirmDialog() {
        if (this.mReverseDialog == null) {
            if (this.mCurrentMode == 174) {
                CameraStatUtils.trackLiveClick(MistatsConstants.Live.VALUE_LIVE_CLICK_REVERSE);
            }
            this.mReverseDialog = RotateDialogController.showSystemAlertDialog(getContext(), null, getString(R.string.live_reverse_message), getString(R.string.live_reverse_confirm), new Runnable() {
                /* class com.android.camera.fragment.bottom.action.FragmentBottomAction.AnonymousClass7 */

                public void run() {
                    FragmentBottomAction.this.mShutterButton.removeLastSegment();
                    if (((BaseFragment) FragmentBottomAction.this).mCurrentMode == 174) {
                        CameraStatUtils.trackLiveClick(MistatsConstants.Live.VALUE_LIVE_CLICK_REVERSE_CONFIRM);
                    }
                    Log.d(FragmentBottomAction.TAG, "reverse click.");
                    ActivityBase activityBase = (ActivityBase) FragmentBottomAction.this.getContext();
                    if (activityBase == null || (!(activityBase.getCurrentModule() instanceof LiveModule) && !(activityBase.getCurrentModule() instanceof MiLiveModule))) {
                        Log.w(FragmentBottomAction.TAG, "showReverseConfirmDialog skip!!!");
                    } else {
                        ((ILiveModule) activityBase.getCurrentModule()).doReverse();
                    }
                }
            }, null, null, getString(R.string.snap_cancel), null);
            this.mReverseDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                /* class com.android.camera.fragment.bottom.action.FragmentBottomAction.AnonymousClass8 */

                public void onDismiss(DialogInterface dialogInterface) {
                    FragmentBottomAction.this.mReverseDialog = null;
                }
            });
        }
    }

    private void switchModeSelectViewStyle(int i) {
        int moreModeStyle;
        if (getContext() != null && (moreModeStyle = CameraSettings.getMoreModeStyle()) != this.mMoreModeStyle) {
            this.mMoreModeStyle = moreModeStyle;
            FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) this.mModeSelectView.getLayoutParams();
            ViewGroup.MarginLayoutParams marginLayoutParams = (ViewGroup.MarginLayoutParams) this.mBottomMenuLayout.getLayoutParams();
            if (moreModeStyle == 0) {
                layoutParams.topMargin = getContext().getResources().getDimensionPixelOffset(R.dimen.mode_select_margin_top);
                marginLayoutParams.height = Math.round(((float) Util.sBottomBarHeight) * 0.3f);
            } else if (moreModeStyle == 1) {
                layoutParams.topMargin = getContext().getResources().getDimensionPixelOffset(R.dimen.mode_select_popup_style_margin_top);
                marginLayoutParams.height = Math.round(((float) Util.sBottomBarHeight) * 0.3f) + (getContext().getResources().getDimensionPixelOffset(R.dimen.mode_select_popup_style_margin_top) - getContext().getResources().getDimensionPixelOffset(R.dimen.mode_select_margin_top));
            }
            if (this.mModeSelectView.getAdapter() != null) {
                this.mModeSelectView.getAdapter().notifyDataSetChanged();
            }
            Log.d(TAG, "switchModeSelectViewStyle f = " + this.mComponentModuleList.getCommonItems().size() + ", m = " + this.mComponentModuleList.getMoreItems().size());
        }
    }

    /* access modifiers changed from: private */
    /* access modifiers changed from: public */
    private void switchMoreMode(boolean z) {
        DragLayout.getAnimationConfig().calDragLayoutHeight(getContext(), this.mComponentModuleList.getMoreItems().size());
        ((ViewGroup.MarginLayoutParams) this.mDragUpLayout.getLayoutParams()).height = Util.getBottomHeight() + ((int) DragLayout.getAnimationConfig().getTotalDragDistance());
        int moreModeStyle = CameraSettings.getMoreModeStyle();
        if (moreModeStyle != 1 || !this.mComponentModuleList.isCommonMode(this.mCurrentMode) || !z) {
            FragmentMoreModePopup fragmentMoreModePopup = this.mMoreModePopup;
            if (fragmentMoreModePopup != null && fragmentMoreModePopup.isAdded()) {
                this.mMoreModePopup.unRegisterProtocol();
                ((ModeProtocol.BaseDelegate) ModeCoordinatorImpl.getInstance().getAttachProtocol(160)).getAnimationComposite().remove(this.mMoreModePopup.getFragmentInto());
                FragmentUtils.removeFragmentByTag(getChildFragmentManager(), this.mMoreModePopup.getFragmentTag());
                this.mDragUpLayout.removeOnDragListener(this.mMoreModePopup);
            }
            this.mDragUpLayout.setDragEnable(false);
        } else {
            if (this.mMoreModePopup == null) {
                this.mMoreModePopup = new FragmentMoreModePopup();
            }
            if (!this.mMoreModePopup.isAdded()) {
                this.mMoreModePopup.registerProtocol();
                FragmentManager childFragmentManager = getChildFragmentManager();
                FragmentMoreModePopup fragmentMoreModePopup2 = this.mMoreModePopup;
                FragmentUtils.addFragmentWithTag(childFragmentManager, (int) R.id.popup_more_mode_layout, fragmentMoreModePopup2, fragmentMoreModePopup2.getFragmentTag());
                ((ModeProtocol.BaseDelegate) ModeCoordinatorImpl.getInstance().getAttachProtocol(160)).getAnimationComposite().put(this.mMoreModePopup.getFragmentInto(), this.mMoreModePopup);
                this.mDragUpLayout.addOnDragListener(this.mMoreModePopup);
            }
            this.mDragUpLayout.setDragEnable(true);
        }
        if (moreModeStyle != 1) {
            this.mMoreModePopup = null;
        }
    }

    private void switchVideoCapture(View view) {
        int i = this.mCurrentMode;
        if (i == 167) {
            DataRepository.dataItemGlobal().setCurrentMode(180);
            ((Camera) getContext()).onModeSelected(StartControl.create(180).setResetType(5).setNeedBlurAnimation(true).setViewConfigType(2));
        } else if (i == 180) {
            DataRepository.dataItemGlobal().setCurrentMode(167);
            ((Camera) getContext()).onModeSelected(StartControl.create(167).setResetType(5).setNeedBlurAnimation(true).setViewConfigType(2));
        } else if (i == 184) {
            MimojiStatusManager2 mimojiStatusManager2 = DataRepository.dataItemLive().getMimojiStatusManager2();
            if (mimojiStatusManager2.getMimojiRecordState() == 0) {
                mimojiStatusManager2.setMimojiRecordState(1);
                mimojiStatusManager2.setMimojiRecordStateFromGif(1);
            } else {
                mimojiStatusManager2.setMimojiRecordState(0);
                mimojiStatusManager2.setMimojiRecordStateFromGif(0);
            }
            ((Camera) getContext()).onModeSelected(StartControl.create(this.mCurrentMode).setResetType(2).setViewConfigType(2).setNeedBlurAnimation(true));
        }
        this.mRecordingSwitch.T();
    }

    private void updateBottomInRecording(final boolean z, boolean z2) {
        int i = -1;
        if (z) {
            this.mHandler.removeMessages(1);
            if (this.mThumbnailProgress.getVisibility() != 8) {
                this.mThumbnailProgress.setVisibility(8);
            }
            this.mRecordingSwitch.cancelAnimation();
            animateViews(-1, (List<Completable>) null, this.mRecordingSwitch);
        }
        final boolean z3 = this.mCurrentMode == 165 && CameraSettings.isTimerBurstEnable() && DataRepository.dataItemLive().getTimerBurstController().isInTimerBurstShotting();
        int i2 = this.mCurrentMode;
        if (i2 != 161) {
            if (i2 != 162) {
                if (i2 == 169 || i2 == 172) {
                    this.mVideoReverseEnable = false;
                    this.mVideoCaptureEnable = false;
                    this.mVideoPauseSupported = false;
                    this.mBackEnable = false;
                } else {
                    if (i2 != 174) {
                        if (i2 != 204) {
                            if (i2 != 179) {
                                if (i2 != 180) {
                                    if (i2 == 183) {
                                        if (DataRepository.dataItemFeature().Qk()) {
                                            this.mVideoCaptureEnable = true;
                                        } else {
                                            this.mVideoCaptureEnable = false;
                                        }
                                        this.mVideoPauseSupported = true;
                                        this.mVideoReverseEnable = true;
                                        this.mBackEnable = false;
                                    } else if (i2 != 184) {
                                        this.mVideoPauseSupported = false;
                                        this.mVideoCaptureEnable = false;
                                        this.mVideoReverseEnable = false;
                                        this.mBackEnable = false;
                                    } else {
                                        this.mCameraPickEnable = false;
                                        this.mVideoPauseSupported = false;
                                        this.mVideoCaptureEnable = false;
                                        this.mVideoReverseEnable = false;
                                        if (DataRepository.dataItemLive().getMimojiStatusManager2().isInMimojiCreate()) {
                                            this.mBackEnable = true;
                                            animateViews(-1, (List<Completable>) null, this.mRecordingSwitch);
                                        } else {
                                            this.mBackEnable = false;
                                            this.mCameraPickEnable = false;
                                        }
                                    }
                                }
                            }
                        }
                    }
                    this.mVideoCaptureEnable = false;
                    this.mVideoPauseSupported = true;
                    this.mVideoReverseEnable = true;
                    this.mBackEnable = false;
                }
            }
            if (!DataRepository.dataItemGlobal().isIntentAction()) {
                this.mVideoCaptureEnable = CameraSettings.isVideoCaptureVisible() && !CameraSettings.isHdr10Alive(this.mCurrentMode);
            }
            this.mVideoPauseSupported = c._n() && !CameraSettings.isVideoBokehOn();
            this.mVideoReverseEnable = false;
            this.mBackEnable = false;
        } else {
            this.mVideoPauseSupported = false;
            this.mVideoReverseEnable = false;
            this.mBackEnable = false;
            if (DataRepository.dataItemFeature().Qk()) {
                this.mVideoCaptureEnable = true;
            } else {
                this.mVideoCaptureEnable = false;
            }
        }
        if (z) {
            if (this.mVideoCaptureEnable) {
                this.mRecordingSnap.setImageResource(R.drawable.ic_vector_recording_snap);
                this.mRecordingSnap.setSoundEffectsEnabled(false);
                this.mRecordingSnap.setVisibility(0);
                ViewCompat.setAlpha(this.mRecordingSnap, 0.0f);
            }
            if (this.mVideoPauseSupported) {
                setPausePlaySwitchTarget(false);
                this.mRecordingPause.setSoundEffectsEnabled(false);
                this.mRecordingPause.setVisibility(0);
                ViewCompat.setAlpha(this.mRecordingPause, 0.0f);
            }
            if (this.mVideoPauseSupported) {
                this.mRecordingReverse.setImageResource(R.drawable.ic_vector_reverse);
                this.mRecordingReverse.setSoundEffectsEnabled(false);
                this.mRecordingReverse.setVisibility(8);
            }
        }
        if (!z) {
            i = 1;
        }
        animateViews(i, (List<Completable>) null, this.mModeSelectLayout.getView());
        ValueAnimator valueAnimator = this.mBottomAnimator;
        if (valueAnimator != null && valueAnimator.isRunning()) {
            this.mBottomAnimator.cancel();
        }
        this.mBottomAnimator = ValueAnimator.ofFloat(0.0f, 1.0f);
        this.mBottomAnimator.setDuration(z2 ? 200 : 0);
        this.mBottomAnimator.setInterpolator(new CubicEaseOutInterpolator() {
            /* class com.android.camera.fragment.bottom.action.FragmentBottomAction.AnonymousClass9 */

            @Override // miui.view.animation.CubicEaseOutInterpolator
            public float getInterpolation(float f) {
                float interpolation = super.getInterpolation(f);
                float f2 = 0.0f;
                if (!z3) {
                    ViewCompat.setAlpha(FragmentBottomAction.this.mModeSelectBackgroundLayout, z ? 1.0f - interpolation : FragmentBottomAction.this.mModeSelectBackgroundLayout.getAlpha() == 0.0f ? interpolation : 1.0f);
                }
                ViewCompat.setAlpha(FragmentBottomAction.this.mThumbnailImageLayout, z ? 1.0f - interpolation : FragmentBottomAction.this.mThumbnailImageLayout.getAlpha() == 0.0f ? interpolation : 1.0f);
                if (FragmentBottomAction.this.mVideoPauseSupported) {
                    ViewCompat.setAlpha(FragmentBottomAction.this.mRecordingPause, z ? interpolation : 1.0f - interpolation);
                }
                if (FragmentBottomAction.this.mCameraPickEnable) {
                    ViewCompat.setAlpha(FragmentBottomAction.this.mCameraPicker, z ? 0.0f : 1.0f);
                }
                if (FragmentBottomAction.this.mVideoCaptureEnable) {
                    ImageView imageView = FragmentBottomAction.this.mRecordingSnap;
                    if (z) {
                        f2 = 1.0f;
                    }
                    ViewCompat.setAlpha(imageView, f2);
                }
                if (FragmentBottomAction.this.mVideoReverseEnable) {
                    ViewCompat.setAlpha(FragmentBottomAction.this.mRecordingReverse, z ? interpolation : 1.0f - interpolation);
                }
                if (FragmentBottomAction.this.mBackEnable) {
                    ViewCompat.setAlpha(FragmentBottomAction.this.mMimojiBack, z ? interpolation : 1.0f - interpolation);
                }
                return interpolation;
            }
        });
        this.mBottomAnimator.addListener(new AnimatorListenerAdapter() {
            /* class com.android.camera.fragment.bottom.action.FragmentBottomAction.AnonymousClass10 */

            public void onAnimationEnd(Animator animator) {
                if (FragmentBottomAction.this.canProvide()) {
                    if (FragmentBottomAction.this.mVideoPauseSupported) {
                        FragmentBottomAction.this.mRecordingPause.setVisibility(z ? 0 : 8);
                    }
                    if (FragmentBottomAction.this.mVideoCaptureEnable) {
                        FragmentBottomAction.this.mRecordingSnap.setVisibility(z ? 0 : 8);
                    }
                    if (FragmentBottomAction.this.mVideoReverseEnable && !z) {
                        FragmentBottomAction.this.mRecordingReverse.setVisibility(8);
                    }
                    if (FragmentBottomAction.this.mBackEnable) {
                        FragmentBottomAction.this.animateViews((FragmentBottomAction) (z ? 1 : -1), (int) null, (List) FragmentBottomAction.this.mMimojiBack);
                    }
                    if (z) {
                        FragmentBottomAction fragmentBottomAction = FragmentBottomAction.this;
                        fragmentBottomAction.animateViews((FragmentBottomAction) -1, (int) null, (List) fragmentBottomAction.mThumbnailImageLayout);
                        if (FragmentBottomAction.this.mCameraPickEnable) {
                            FragmentBottomAction fragmentBottomAction2 = FragmentBottomAction.this;
                            fragmentBottomAction2.animateViews((FragmentBottomAction) -1, (int) null, (List) fragmentBottomAction2.mCameraPicker);
                        }
                    }
                    if (z) {
                        FragmentBottomAction.this.mDragUpLayout.setDragEnable(false);
                    } else {
                        FragmentBottomAction.this.switchMoreMode(true);
                    }
                }
            }

            public void onAnimationStart(Animator animator) {
                if (!z) {
                    if (FragmentBottomAction.this.mCameraPickEnable) {
                        FragmentBottomAction fragmentBottomAction = FragmentBottomAction.this;
                        fragmentBottomAction.animateViews((FragmentBottomAction) 1, (int) null, (List) fragmentBottomAction.mCameraPicker);
                    }
                    FragmentBottomAction fragmentBottomAction2 = FragmentBottomAction.this;
                    fragmentBottomAction2.animateViews((FragmentBottomAction) 1, (int) null, (List) fragmentBottomAction2.mThumbnailImageLayout);
                }
            }
        });
        this.mBottomAnimator.start();
    }

    public /* synthetic */ void Ga() {
        if (this.mModeSelectView.getAdapter() != null) {
            this.mModeSelectView.getAdapter().notifyDataSetChanged();
            this.mModeSelectView.moveToPosition(this.mCurrentMode);
        }
    }

    @Override // com.android.camera.protocol.ModeProtocol.BottomMenuProtocol
    public void animateShineBeauty(boolean z) {
        hideModeOrExternalTipLayout();
        this.mModeSelectLayout.animateShineBeauty(z);
    }

    @Override // com.android.camera.ui.CameraSnapView.SnapListener
    public boolean canSnap() {
        ModeProtocol.CameraAction cameraAction = (ModeProtocol.CameraAction) ModeCoordinatorImpl.getInstance().getAttachProtocol(161);
        return cameraAction != null && !cameraAction.isBlockSnap();
    }

    @Override // com.android.camera.protocol.ModeProtocol.ModeChangeController
    public boolean canSwipeChangeMode() {
        ComponentModuleList componentModuleList;
        return !this.mVideoRecordingStarted && (DataRepository.dataItemLive().getMimojiStatusManager().IsInMimojiPreview() || DataRepository.dataItemLive().getMimojiStatusManager2().isInMimojiPreview()) && isVisible() && (componentModuleList = this.mComponentModuleList) != null && componentModuleList.isCommonMode(this.mCurrentMode) && !isThumbLoading() && !isShowLightingView() && !isFocusOrZoomMoving();
    }

    @Override // com.android.camera.ui.DragLayout.OnDragListener
    public boolean catchDrag(int i, int i2) {
        ModeProtocol.CameraAction cameraAction = (ModeProtocol.CameraAction) ModeCoordinatorImpl.getInstance().getAttachProtocol(161);
        if (cameraAction == null || cameraAction.isDoingAction()) {
            return true;
        }
        if (this.mBottomActionView.getVisibility() != 0) {
            return false;
        }
        if (this.mBottomActionView.getVisibility() == 0 && this.mShutterButton.inRegion(i, i2)) {
            return true;
        }
        if (this.mThumbnailImageLayout.getVisibility() == 0 && Util.isInViewRegion(this.mThumbnailImageLayout, i, i2)) {
            return true;
        }
        if (this.mCameraPicker.getVisibility() != 0 || !Util.isInViewRegion(this.mCameraPicker, i, i2)) {
            return this.mRecordingSwitch.getVisibility() == 0 && Util.isInViewRegion(this.mRecordingSwitch, i, i2);
        }
        return true;
    }

    @Override // com.android.camera.protocol.ModeProtocol.ModeChangeController
    public void changeCamera(View... viewArr) {
        DataItemGlobal dataItemGlobal = (DataItemGlobal) DataRepository.provider().dataGlobal();
        int currentCameraId = dataItemGlobal.getCurrentCameraId();
        int i = currentCameraId == 0 ? 1 : 0;
        if (this.mCurrentMode == 163 && CameraSettings.isUltraPixelOn()) {
            ((ModeProtocol.ConfigChanges) ModeCoordinatorImpl.getInstance().getAttachProtocol(164)).switchOffElementsSilent(209);
        }
        HybridZoomingSystem.clearZoomRatioHistory();
        dataItemGlobal.setCameraId(i);
        if (viewArr != null && viewArr.length > 0) {
            for (View view : viewArr) {
                if (i == 1) {
                    ViewCompat.animate(view).rotationBy(-180.0f).setDuration(300).start();
                } else {
                    ViewCompat.animate(view).rotationBy(180.0f).setDuration(300).start();
                }
            }
        }
        Log.d(TAG, String.format(Locale.ENGLISH, "switch camera from %d to %d, for module 0x%x", Integer.valueOf(currentCameraId), Integer.valueOf(i), Integer.valueOf(this.mCurrentMode)));
        ScenarioTrackUtil.trackSwitchCameraStart(currentCameraId == 1, i == 1, this.mCurrentMode);
        ModeProtocol.TopAlert topAlert = (ModeProtocol.TopAlert) ModeCoordinatorImpl.getInstance().getAttachProtocol(172);
        topAlert.removeExtraMenu(4);
        int i2 = this.mCurrentMode;
        if (i2 == 162) {
            DataRepository.dataItemGlobal().setCurrentMode(162);
            ((Camera) getContext()).onModeSelected(StartControl.create(162).setResetType(5).setNeedBlurAnimation(true).setViewConfigType(2));
        } else if (i2 == 166) {
            DataRepository.dataItemGlobal().setCurrentMode(176);
            ((Camera) getContext()).onModeSelected(StartControl.create(176).setResetType(4).setViewConfigType(2).setNeedBlurAnimation(true));
        } else if (i2 == 169) {
            DataRepository.dataItemGlobal().setCurrentMode(169);
            ((Camera) getContext()).onModeSelected(StartControl.create(169).setResetType(5).setViewConfigType(2).setNeedBlurAnimation(true));
        } else if (i2 == 184) {
            MimojiModeProtocol.MimojiAvatarEngine2 mimojiAvatarEngine2 = (MimojiModeProtocol.MimojiAvatarEngine2) ModeCoordinatorImpl.getInstance().getAttachProtocol(246);
            if (mimojiAvatarEngine2 != null && mimojiAvatarEngine2.isOnCreateMimoji()) {
                ((ModeProtocol.MainContentProtocol) ModeCoordinatorImpl.getInstance().getAttachProtocol(166)).mimojiStart();
                topAlert.disableMenuItem(true, 197, 193, 162);
            }
            ((Camera) getContext()).onModeSelected(StartControl.create(this.mCurrentMode).setResetType(5).setNeedBlurAnimation(true).setViewConfigType(2));
        } else if (i2 == 176) {
            DataRepository.dataItemGlobal().setCurrentMode(166);
            ((Camera) getContext()).onModeSelected(StartControl.create(166).setResetType(4).setViewConfigType(2).setNeedBlurAnimation(true));
        } else if (i2 != 177) {
            if (i2 == 163 || i2 == 165) {
                i2 = DataRepository.dataItemConfig().getComponentConfigRatio().getMappingModeByRatio(163);
                DataRepository.dataItemGlobal().setCurrentMode(i2);
                if (viewArr != null) {
                    for (View view2 : viewArr) {
                        if (view2 != null && view2.getId() == R.id.v9_camera_picker) {
                            ComponentRunningAIWatermark componentRunningAIWatermark = DataRepository.dataItemRunning().getComponentRunningAIWatermark();
                            if (currentCameraId != 0 ? currentCameraId != 1 ? false : componentRunningAIWatermark.getEnable(0) : componentRunningAIWatermark.getEnable(1)) {
                                i2 = 163;
                            }
                        }
                    }
                }
            }
            ((Camera) getContext()).onModeSelected(StartControl.create(i2).setResetType(5).setViewConfigType(2).setNeedBlurAnimation(true));
        } else {
            ModeProtocol.MimojiAvatarEngine mimojiAvatarEngine = (ModeProtocol.MimojiAvatarEngine) ModeCoordinatorImpl.getInstance().getAttachProtocol(217);
            if (mimojiAvatarEngine != null && mimojiAvatarEngine.isOnCreateMimoji()) {
                ((ModeProtocol.MainContentProtocol) ModeCoordinatorImpl.getInstance().getAttachProtocol(166)).mimojiStart();
                topAlert.disableMenuItem(true, 197, 193);
            }
            ((Camera) getContext()).onModeSelected(StartControl.create(this.mCurrentMode).setResetType(5).setNeedBlurAnimation(true).setViewConfigType(2));
        }
        if (Util.isAccessible()) {
            this.mEdgeHorizonScrollView.setContentDescription(getString(R.string.accessibility_camera_picker_finish));
            this.mEdgeHorizonScrollView.sendAccessibilityEvent(32768);
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:10:0x0021, code lost:
        if (r8 == 5) goto L_0x0018;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:7:0x001a, code lost:
        if (r8 == 5) goto L_0x001f;
     */
    /* JADX WARNING: Removed duplicated region for block: B:13:0x0038  */
    /* JADX WARNING: Removed duplicated region for block: B:18:0x0047 A[ADDED_TO_REGION] */
    /* JADX WARNING: Removed duplicated region for block: B:22:0x0051  */
    /* JADX WARNING: Removed duplicated region for block: B:26:0x0044 A[SYNTHETIC] */
    @Override // com.android.camera.protocol.ModeProtocol.ModeChangeController
    public void changeModeByGravity(int i, int i2) {
        int size;
        int i3;
        if (i != -1) {
            if (!Util.isLayoutRTL(getContext())) {
                if (i != 3) {
                }
                i = 8388611;
                int transferredMode = ComponentModuleList.getTransferredMode(this.mCurrentMode);
                size = this.mComponentModuleList.getCommonItems().size();
                i3 = 0;
                while (true) {
                    if (i3 < size) {
                        i3 = 0;
                        break;
                    } else if (this.mComponentModuleList.getMode(i3) == transferredMode) {
                        break;
                    } else {
                        i3++;
                    }
                }
                if (i == 8388611) {
                    if (i == 8388613 && i3 < size - 1) {
                        i3++;
                    }
                } else if (i3 > 0) {
                    i3--;
                }
                changeModeByNewMode(this.mComponentModuleList.getMode(i3), i2, false);
            } else if (i != 3) {
            }
            i = 8388613;
            int transferredMode2 = ComponentModuleList.getTransferredMode(this.mCurrentMode);
            size = this.mComponentModuleList.getCommonItems().size();
            i3 = 0;
            while (true) {
                if (i3 < size) {
                }
                i3++;
            }
            if (i == 8388611) {
            }
            changeModeByNewMode(this.mComponentModuleList.getMode(i3), i2, false);
        }
    }

    @Override // com.android.camera.protocol.ModeProtocol.ModeChangeController
    public void changeModeByNewMode(int i, int i2) {
        if (this.mComponentModuleList.isCommonMode(i)) {
            this.mModeSelectView.moveToPosition(i);
        }
        changeModeByNewMode(i, i2, false);
    }

    @Override // com.android.camera.protocol.ModeProtocol.ActionProcessing
    public void enableStopButton() {
        this.mShutterButton.setStopButtonEnable(true, true);
    }

    @Override // com.android.camera.protocol.ModeProtocol.ActionProcessing
    public void entryOrExitMiMojiGif(boolean z) {
        this.mPreGifStatus = z;
    }

    @Override // com.android.camera.protocol.ModeProtocol.BottomMenuProtocol
    public void expandAIWatermarkBottomMenu(ComponentRunningAIWatermark componentRunningAIWatermark) {
        if (this.mBottomRecordingTime.getVisibility() == 0) {
            this.mBottomRecordingTime.setVisibility(8);
        }
        hideModeOrExternalTipLayout();
        animateViews(1, (List<Completable>) null, this.mModeSelectLayout.getView());
        this.mModeSelectLayout.expandAIWatermark(componentRunningAIWatermark, this.mCurrentMode);
    }

    @Override // com.android.camera.protocol.ModeProtocol.BottomMenuProtocol
    public void expandShineBottomMenu(ComponentRunningShine componentRunningShine) {
        if (this.mBottomRecordingTime.getVisibility() == 0) {
            this.mBottomRecordingTime.setVisibility(8);
        }
        hideModeOrExternalTipLayout();
        animateViews(1, (List<Completable>) null, this.mModeSelectLayout.getView());
        this.mModeSelectLayout.expandShine(componentRunningShine, this.mCurrentMode);
    }

    @Override // com.android.camera.protocol.ModeProtocol.ActionProcessing
    public void filterUiChange() {
    }

    @Override // com.android.camera.protocol.ModeProtocol.ActionProcessing
    public boolean forceSwitchFront() {
        if (((DataItemGlobal) DataRepository.provider().dataGlobal()).getCurrentCameraId() == 1) {
            return false;
        }
        onClick(this.mCameraPicker);
        return true;
    }

    @Override // com.android.camera.protocol.ModeProtocol.BottomMenuProtocol
    public int getBeautyActionMenuType() {
        return this.mCurrentBeautyActionMenuType;
    }

    @Override // com.android.camera.fragment.BaseFragment
    public int getFragmentInto() {
        return 241;
    }

    /* access modifiers changed from: protected */
    @Override // com.android.camera.fragment.BaseFragment
    public int getLayoutResourceId() {
        return R.layout.fragment_bottom_action;
    }

    @Override // com.android.camera.protocol.ModeProtocol.ActionProcessing
    public void hideDocumentReviewViews() {
    }

    @Override // com.android.camera.protocol.ModeProtocol.ActionProcessing
    public void hideExtra() {
        ModeProtocol.ConfigChanges configChanges;
        if (this.mIsShowMiMoji && (configChanges = (ModeProtocol.ConfigChanges) ModeCoordinatorImpl.getInstance().getAttachProtocol(164)) != null) {
            configChanges.showOrHideMimoji();
        }
    }

    /* access modifiers changed from: protected */
    @Override // com.android.camera.fragment.BaseFragment
    public void initView(View view) {
        this.mBottomActionView = (FrameLayout) view.findViewById(R.id.bottom_action);
        ((ViewGroup.MarginLayoutParams) this.mBottomActionView.getLayoutParams()).height = Util.getBottomHeight();
        this.mBottomRollDownHeight = Math.round(((float) Util.sCenterDisplayHeight) * 0.026f);
        this.mV9bottomParentLayout = (RelativeLayout) view.findViewById(R.id.v9_bottom_parent);
        ViewGroup.MarginLayoutParams marginLayoutParams = (ViewGroup.MarginLayoutParams) this.mV9bottomParentLayout.getLayoutParams();
        marginLayoutParams.height = Math.round(((float) Util.sBottomBarHeight) * 0.7f);
        marginLayoutParams.bottomMargin = Util.sBottomMargin;
        marginLayoutParams.topMargin = Math.round(((float) Util.sBottomBarHeight) * 0.3f);
        this.mComponentModuleList = DataRepository.dataItemGlobal().getComponentModuleList();
        this.mModeSelectBackgroundLayout = (ShapeBackGroundView) view.findViewById(R.id.mode_select_bg_layout);
        this.mModeSelectBackgroundLayout.setGravity(80);
        ViewGroup.MarginLayoutParams marginLayoutParams2 = (ViewGroup.MarginLayoutParams) this.mModeSelectBackgroundLayout.getLayoutParams();
        marginLayoutParams2.height = Util.getBottomHeight() + Util.getSquareBottomCoverHeight();
        this.mModeSelectBackgroundLayout.initHeight(marginLayoutParams2.height);
        FrameLayout frameLayout = (FrameLayout) view.findViewById(R.id.mode_select_layout);
        this.mEdgeHorizonScrollView = (EdgeHorizonScrollView) frameLayout.findViewById(R.id.mode_select_scrollview);
        this.mBottomMenuLayout = frameLayout.findViewById(R.id.bottom_menu_layout);
        this.mModeSelectLayout = new BottomActionMenu(getContext(), frameLayout);
        this.mExternalModeTipLayout = (CapsuleLayout) view.findViewById(R.id.bottom_external_mode_root);
        this.mExternalModeTipText = (TextView) this.mExternalModeTipLayout.findViewById(R.id.bottom_external_mode_text);
        this.mExternalModeTipIcon = (ImageView) this.mExternalModeTipLayout.findViewById(R.id.bottom_external_mode_close);
        this.mExternalModeTipLayout.setOnClickListener(this);
        FolmeUtils.touchTint(this.mExternalModeTipLayout);
        ViewGroup.MarginLayoutParams marginLayoutParams3 = (ViewGroup.MarginLayoutParams) this.mBottomMenuLayout.getLayoutParams();
        marginLayoutParams3.height = Math.round(((float) Util.sBottomBarHeight) * 0.3f);
        marginLayoutParams3.bottomMargin = Util.sBottomMargin;
        this.mModeSelectView = (ModeSelectView) frameLayout.findViewById(R.id.mode_select_view);
        this.mModeSelectView.init(this.mComponentModuleList, this.mCurrentMode, this);
        switchModeSelectViewStyle(this.mCurrentMode);
        this.mPopupMenuLayout = (PopupMenuLayout) getActivity().findViewById(R.id.popup_menu);
        this.mDragUpLayout = (DragLayout) view.findViewById(R.id.drag_layout);
        this.mDragUpLayout.addOnDragListener(this);
        this.mDragUpLayout.addOnDragListener(this.mPopupMenuLayout);
        ViewGroup.MarginLayoutParams marginLayoutParams4 = (ViewGroup.MarginLayoutParams) this.mDragUpLayout.getLayoutParams();
        marginLayoutParams4.topMargin = Util.getTopHeight() + Util.getDisplayRect(0).height();
        marginLayoutParams4.height = Util.getBottomHeight() + ((int) DragLayout.getAnimationConfig().getTotalDragDistance());
        this.mThumbnailImageLayout = (ViewGroup) view.findViewById(R.id.v9_thumbnail_layout);
        this.mThumbnailImage = (ImageView) this.mThumbnailImageLayout.findViewById(R.id.v9_thumbnail_image);
        this.mThumbnailProgress = (ProgressBar) view.findViewById(R.id.v9_recording_progress);
        this.mRecordingPause = (LottieAnimationView) view.findViewById(R.id.v9_recording_pause);
        this.mShutterButton = (CameraSnapView) view.findViewById(R.id.v9_shutter_button_internal);
        this.mCameraPicker = (ImageView) view.findViewById(R.id.v9_camera_picker);
        this.mRecordingSnap = (ImageView) view.findViewById(R.id.v9_recording_snap);
        this.mRecordingReverse = (ImageView) view.findViewById(R.id.v9_recording_reverse);
        this.mRecordingSwitch = (LottieAnimationView) view.findViewById(R.id.v9_capture_recording_switch);
        this.mFragmentLayoutExtra = view.findViewById(R.id.fragment_bottom_extra);
        this.mPostProcess = (ProgressBar) view.findViewById(R.id.v9_post_processing);
        view.findViewById(R.id.bottom_control_bar).setMinimumHeight(Math.round(((float) Util.sBottomBarHeight) * 0.3f));
        this.mBottomRecordingTime = (TextView) view.findViewById(R.id.bottom_recording_time_view);
        this.mBottomRecordingCameraPicker = (ImageView) view.findViewById(R.id.bottom_recording_camera_picker);
        this.mBottomRecordingCameraPicker.setOnClickListener(this);
        this.mShutterButton.setSnapListener(this);
        this.mShutterButton.setSnapClickEnable(false);
        this.mCaptureProgressDelay = getResources().getInteger(R.integer.capture_progress_delay_time);
        this.mRecordProgressDelay = getResources().getInteger(R.integer.record_progress_delay_time);
        this.mThumbnailImageLayout.setOnClickListener(this);
        this.mCameraPicker.setOnClickListener(this);
        this.mRecordingPause.setOnClickListener(this);
        this.mRecordingSnap.setOnClickListener(this);
        this.mRecordingReverse.setOnClickListener(this);
        this.mRecordingSwitch.setOnClickListener(this);
        this.mMimojiBack = (ImageView) view.findViewById(R.id.mimoji_create_back);
        this.mMimojiBack.setOnClickListener(this);
        FolmeUtils.touchScaleTint(this.mBottomRecordingCameraPicker, this.mThumbnailImageLayout, this.mCameraPicker, this.mRecordingPause, this.mRecordingSnap, this.mRecordingReverse, this.mRecordingSwitch, this.mMimojiBack);
        this.mIsIntentAction = DataRepository.dataItemGlobal().isIntentAction();
        provideAnimateElement(this.mCurrentMode, null, 2);
        this.mCubicEaseOut = new CubicEaseOutInterpolator();
        this.mSineEaseOut = new SineEaseOutInterpolator();
        this.mFilterListHeight = getContext().getResources().getDimensionPixelSize(R.dimen.filter_still_height);
        if (Util.isAccessible()) {
            Util.setAccessibilityFocusable(this.mV9bottomParentLayout, false);
        }
    }

    @Override // com.android.camera.protocol.ModeProtocol.MoreModePopupController
    public boolean isExpanded() {
        DragLayout dragLayout = this.mDragUpLayout;
        if (dragLayout != null) {
            return dragLayout.isExpanded();
        }
        return false;
    }

    @Override // com.android.camera.protocol.ModeProtocol.ActionProcessing
    public boolean isShowFilterView() {
        return false;
    }

    @Override // com.android.camera.protocol.ModeProtocol.ActionProcessing
    public boolean isShowLightingView() {
        return this.mIsShowLighting;
    }

    @Override // com.android.camera.protocol.ModeProtocol.ModeChangeController
    public boolean modeChanging() {
        return (!this.mDragUpLayout.isShrink() && !this.mDragUpLayout.isExpanded()) || this.mModeSelectView.getScrollState() != 0;
    }

    /* JADX WARNING: Removed duplicated region for block: B:15:0x0057  */
    /* JADX WARNING: Removed duplicated region for block: B:18:0x006a  */
    @Override // com.android.camera.fragment.BaseFragment, com.android.camera.animation.AnimationDelegate.AnimationResource
    public void notifyAfterFrameAvailable(int i) {
        int i2;
        super.notifyAfterFrameAvailable(i);
        FragmentLighting fragmentLighting = this.mFragmentLighting;
        if (fragmentLighting != null && fragmentLighting.isAdded()) {
            this.mFragmentLighting.reInitAdapterBgMode(true);
        }
        if (getActivity() != null && (getActivity() instanceof Camera)) {
            Camera camera = (Camera) getActivity();
            if (camera.getCurrentModule() != null) {
                i2 = camera.getCurrentModule().getPortraitLightingVersion();
                this.mModeSelectLayout.initBeautyMenuView(i2);
                adjustViewBackground(this.mModeSelectBackgroundLayout, this.mCurrentMode);
                if (((Integer) this.mModeSelectBackgroundLayout.getTag()).intValue() != this.mModeSelectBackgroundLayout.getCurrentMaskHeight()) {
                    ShapeBackGroundView shapeBackGroundView = this.mModeSelectBackgroundLayout;
                    shapeBackGroundView.setMaskSpecificHeight(((Integer) shapeBackGroundView.getTag()).intValue(), true);
                }
                if (this.mShutterButton != null) {
                    if (this.mCurrentMode != 184 || DataRepository.dataItemLive().getMimojiStatusManager2().isInMimojiCreate()) {
                        ProgressBar progressBar = this.mPostProcess;
                        if (progressBar != null && progressBar.getVisibility() == 0) {
                            Log.w(TAG, "notifyAfterFrameAvailable: shutter process bar is showing");
                            processingFinish();
                            this.mShutterButton.setParameters(PaintConditionReferred.create(this.mCurrentMode).setIsFPS960(isFPS960()));
                        }
                    } else {
                        showNormalMimoji2Bottom();
                    }
                }
                if (!DataRepository.dataItemLive().getMimojiStatusManager().IsInMimojiCreate() || DataRepository.dataItemLive().getMimojiStatusManager2().isInMimojiCreate()) {
                    ((ModeProtocol.MainContentProtocol) ModeCoordinatorImpl.getInstance().getAttachProtocol(166)).mimojiStart();
                }
                return;
            }
        }
        i2 = 1;
        this.mModeSelectLayout.initBeautyMenuView(i2);
        adjustViewBackground(this.mModeSelectBackgroundLayout, this.mCurrentMode);
        if (((Integer) this.mModeSelectBackgroundLayout.getTag()).intValue() != this.mModeSelectBackgroundLayout.getCurrentMaskHeight()) {
        }
        if (this.mShutterButton != null) {
        }
        if (!DataRepository.dataItemLive().getMimojiStatusManager().IsInMimojiCreate()) {
        }
        ((ModeProtocol.MainContentProtocol) ModeCoordinatorImpl.getInstance().getAttachProtocol(166)).mimojiStart();
    }

    @Override // com.android.camera.fragment.BaseFragment, com.android.camera.animation.AnimationDelegate.AnimationResource
    public void notifyDataChanged(int i, int i2) {
        int i3;
        super.notifyDataChanged(i, i2);
        boolean isIntentAction = DataRepository.dataItemGlobal().isIntentAction();
        if (isIntentAction != this.mIsIntentAction) {
            this.mIsIntentAction = isIntentAction;
            this.mModeSelectView.setItems(this.mComponentModuleList.getCommonItems());
            if (this.mModeSelectView.getAdapter() != null) {
                this.mModeSelectView.getAdapter().notifyDataSetChanged();
                this.mModeSelectView.moveToPosition(this.mCurrentMode);
            }
            initThumbLayout();
        }
        this.mComponentModuleList.runChangeResetCb(new d(this));
        if (!this.mVideoRecordingStarted || !((i3 = this.mCurrentMode) == 174 || i3 == 183)) {
            switchMoreMode(this.mModeSelectView.getVisibility() == 0);
        } else {
            Log.d(TAG, "onRecording dataChanged");
        }
        this.mInLoading = false;
        FragmentLighting fragmentLighting = this.mFragmentLighting;
        if (fragmentLighting != null && fragmentLighting.isAdded()) {
            this.mFragmentLighting.reInit();
        }
        FragmentActionMimoji fragmentActionMimoji = this.mFragmentActionMimoji;
        if (fragmentActionMimoji != null && fragmentActionMimoji.isAdded()) {
            this.mFragmentActionMimoji.reInit(this.mCurrentMode);
        }
        if (!Util.isAccessible()) {
            return;
        }
        if (162 == this.mCurrentMode) {
            this.mShutterButton.setContentDescription(getString(R.string.accessibility_record_button));
        } else {
            this.mShutterButton.setContentDescription(getString(R.string.accessibility_shutter_button));
        }
    }

    @Override // com.android.camera.protocol.ModeProtocol.HandleBeautyRecording
    public void onAngleChanged(float f) {
    }

    @Override // com.android.camera.protocol.ModeProtocol.HandleBackTrace
    public boolean onBackEvent(int i) {
        return false;
    }

    @Override // com.android.camera.protocol.ModeProtocol.HandleBeautyRecording
    public void onBeautyRecordingStart() {
        ViewCompat.animate(this.mModeSelectView).alpha(0.0f).start();
    }

    @Override // com.android.camera.protocol.ModeProtocol.HandleBeautyRecording
    public void onBeautyRecordingStop() {
        ViewCompat.animate(this.mModeSelectView).alpha(1.0f).start();
    }

    @Override // com.android.camera.protocol.ModeProtocol.BottomMenuProtocol
    public void onBottomMenuAnimate(int i, int i2) {
        this.mModeSelectLayout.bottomMenuAnimate(i, i2);
    }

    public void onClick(View view) {
        if (!isEnableClick()) {
            Log.d(TAG, "onClick: disabled");
            return;
        }
        ModeProtocol.CameraAction cameraAction = (ModeProtocol.CameraAction) ModeCoordinatorImpl.getInstance().getAttachProtocol(161);
        if (cameraAction == null) {
            Log.d(TAG, "onClick: null action");
            return;
        }
        Module currentModule = ((ActivityBase) getContext()).getCurrentModule();
        if (!currentModule.isIgnoreTouchEvent() || (currentModule.isShot2GalleryOrEnableParallel() && view.getId() == R.id.v9_thumbnail_layout)) {
            switch (view.getId()) {
                case R.id.bottom_external_mode_root:
                    if (!cameraAction.isDoingAction() && !cameraAction.isRecording() && !cameraAction.isRecordingPaused()) {
                        this.mIsShowMiMoji = false;
                        resetToCommonMode();
                        return;
                    }
                    return;
                case R.id.bottom_recording_camera_picker:
                    if (this.mVideoRecordingPaused) {
                        int i = this.mCurrentMode;
                        if (i == 174) {
                            CameraStatUtils.trackLiveClick(MistatsConstants.Live.VALUE_LIVE_CLICK_SWITCH);
                        } else if (i == 183) {
                            CameraStatUtils.trackMiLiveClick(MistatsConstants.MiLive.VALUE_MI_LIVE_CLICK_SWITCH);
                        }
                        changeCamera(view);
                        return;
                    }
                    return;
                case R.id.mimoji_create_back:
                    animateViews(-1, (List<Completable>) null, this.mMimojiBack);
                    if (this.mCurrentMode == 184) {
                        DataRepository.dataItemLive().getMimojiStatusManager2().setMode(2);
                    } else {
                        DataRepository.dataItemLive().getMimojiStatusManager().setMode(MimojiStatusManager.MIMOJI_PREVIEW);
                    }
                    ((ModeProtocol.RecordState) ModeCoordinatorImpl.getInstance().getAttachProtocol(212)).onMimojiCreateBack();
                    CameraStatUtils.trackMimojiClick(MistatsConstants.Mimoji.MIMOJI_CLICK_CREATE_BACK, MistatsConstants.BaseEvent.CREATE);
                    return;
                case R.id.v9_camera_picker:
                    if (!cameraAction.isDoingAction() && !cameraAction.isRecording() && !isThumbLoading()) {
                        hideExtra();
                        if (DataRepository.dataItemLive().getMimojiStatusManager().IsInMimojiCreate() || DataRepository.dataItemLive().getMimojiStatusManager2().isInMimojiCreate()) {
                            CameraStatUtils.trackMimojiClick(MistatsConstants.Mimoji.MIMOJI_CLICK_CREATE_SWITCH, MistatsConstants.BaseEvent.CREATE);
                        }
                        if (!cameraAction.onCameraPickerClicked(view)) {
                            changeCamera(view);
                            return;
                        }
                        return;
                    }
                    return;
                case R.id.v9_capture_recording_switch:
                    if (!cameraAction.isDoingAction() && !this.mVideoRecordingStarted && !CameraSettings.isGifOn()) {
                        switchVideoCapture(view);
                        return;
                    }
                    return;
                case R.id.v9_recording_pause:
                    pauseRecording();
                    return;
                case R.id.v9_recording_reverse:
                    if (this.mVideoReverseEnable && this.mVideoRecordingStarted && this.mShutterButton.hasSegments()) {
                        showReverseConfirmDialog();
                        return;
                    }
                    return;
                case R.id.v9_recording_snap:
                    if (this.mVideoCaptureEnable && this.mVideoRecordingStarted) {
                        ActivityBase activityBase = (ActivityBase) getContext();
                        if (activityBase == null || (!(activityBase.getCurrentModule() instanceof VideoModule) && !(activityBase.getCurrentModule() instanceof FunModule) && !(activityBase.getCurrentModule() instanceof MiLiveModule))) {
                            Log.w(TAG, "onClick: recording snap is not allowed!!!");
                            return;
                        }
                        Module currentModule2 = activityBase.getCurrentModule();
                        if (currentModule2 instanceof VideoModule) {
                            ((VideoModule) currentModule2).takeVideoSnapShoot();
                            return;
                        } else if (currentModule2 instanceof FunModule) {
                            ((FunModule) currentModule2).takePreviewSnapShoot();
                            return;
                        } else if (currentModule2 instanceof MiLiveModule) {
                            ((MiLiveModule) currentModule2).takePreviewSnapShoot();
                            return;
                        } else {
                            return;
                        }
                    } else {
                        return;
                    }
                case R.id.v9_thumbnail_layout:
                    if (isThumbLoading()) {
                        Log.w(TAG, "onClick: ignore thumbnail click event as loading thumbnail");
                        return;
                    } else if (!shouldUseThumbnialAsExit()) {
                        cameraAction.onThumbnailClicked(null);
                        return;
                    } else {
                        cameraAction.onReviewCancelClicked();
                        return;
                    }
                default:
                    return;
            }
        } else {
            Log.w(TAG, "onClick: ignore click event, because module isn't ready");
        }
    }

    @Override // com.android.camera.ui.DragLayout.OnDragListener
    public void onDragDone(boolean z) {
        Log.d(TAG, "onDragDone " + z);
        if (z) {
            this.mBottomActionView.setAlpha(0.0f);
            this.mBottomActionView.setVisibility(8);
            CameraStatUtils.trackEnterMoreMode(MistatsConstants.MoreMode.VALUE_ENTER_MORE_MODE_BY_POP);
            return;
        }
        this.mBottomActionView.setAlpha(1.0f);
        this.mBottomActionView.setVisibility(0);
    }

    @Override // com.android.camera.ui.DragLayout.OnDragListener
    public void onDragProgress(int i) {
        Log.d(TAG, "onDragProgress " + i);
        if (this.mBottomActionView != null) {
            this.mBottomActionView.setAlpha(1.0f - Math.min(1.0f, ((float) Math.abs(i)) / DragLayout.getAnimationConfig().getDisappearDistance()));
        }
    }

    @Override // com.android.camera.ui.DragLayout.OnDragListener
    public void onDragStart(boolean z) {
        Log.d(TAG, "onDragStart " + z);
        ModeProtocol.TopAlert topAlert = (ModeProtocol.TopAlert) ModeCoordinatorImpl.getInstance().getAttachProtocol(172);
        if (topAlert != null && topAlert.isExtraMenuShowing()) {
            topAlert.hideExtraMenu();
        }
        if (!z) {
            this.mBottomActionView.setAlpha(0.0f);
            this.mBottomActionView.setVisibility(0);
        }
    }

    @Override // com.android.camera.protocol.ModeProtocol.HandlerSwitcher
    public boolean onHandleSwitcher(int i) {
        if (!this.mIsShowLighting) {
            return false;
        }
        ModeProtocol.CameraAction cameraAction = (ModeProtocol.CameraAction) ModeCoordinatorImpl.getInstance().getAttachProtocol(161);
        if (cameraAction != null && !cameraAction.isDoingAction() && this.mIsShowLighting) {
            this.mFragmentLighting.switchLighting(i);
        }
        return true;
    }

    @Override // com.android.camera.ui.DragLayout.OnDragListener
    public boolean onInterceptDrag() {
        return true;
    }

    @Override // com.android.camera.ui.ModeSelectView.onModeSelectedListener
    public void onModeSelected(int i) {
        if (!isShowFilterView() && !isShowLightingView()) {
            if (this.mCurrentMode == 177 && !DataRepository.dataItemLive().getMimojiStatusManager().IsInMimojiPreview()) {
                return;
            }
            if (this.mCurrentMode == 184 && !DataRepository.dataItemLive().getMimojiStatusManager2().isInMimojiPreview()) {
                return;
            }
            if (this.mCurrentMode != 180 || i != 167) {
                if (i == 254) {
                    CameraStatUtils.trackEnterMoreMode(MistatsConstants.MoreMode.VALUE_ENTER_MORE_MODE_BY_TAB);
                }
                changeModeByNewMode(i, 0, true);
            }
        }
    }

    @Override // androidx.fragment.app.Fragment
    public void onPause() {
        super.onPause();
        DragLayout dragLayout = this.mDragUpLayout;
        if (dragLayout != null) {
            dragLayout.reset();
        }
    }

    @Override // com.android.camera.protocol.ModeProtocol.BottomMenuProtocol
    public void onRestoreCameraActionMenu(int i) {
        showModeOrExternalTipLayout(true);
        if (this.mIsShowMiMoji) {
            showOrHideMiMojiView();
        }
        this.mModeSelectLayout.switchMenuMode(0, true);
        if (this.mVideoRecordingStarted) {
            int i2 = this.mCurrentMode;
            if (i2 == 174 || i2 == 183) {
                this.mDragUpLayout.setDragEnable(false);
                animateViews(-1, (List<Completable>) null, this.mModeSelectLayout.getView());
            }
        }
    }

    @Override // com.android.camera.fragment.BaseFragment, androidx.fragment.app.Fragment
    public void onResume() {
        super.onResume();
        initThumbLayout();
        this.mModeSelectView.refresh();
    }

    /* JADX WARNING: Removed duplicated region for block: B:77:0x00f5  */
    /* JADX WARNING: Removed duplicated region for block: B:86:? A[RETURN, SYNTHETIC] */
    @Override // com.android.camera.ui.CameraSnapView.SnapListener
    public void onSnapClick() {
        if (!isEnableClick()) {
            Log.w(TAG, "onSnapClick: disabled");
        } else if (getContext() == null) {
            Log.w(TAG, "onSnapClick: no context");
        } else {
            Camera camera = (Camera) getContext();
            Module currentModule = camera.getCurrentModule();
            if (currentModule != null && currentModule.isIgnoreTouchEvent()) {
                Log.w(TAG, "onSnapClick: ignore onSnapClick event, because module isn't ready");
            } else if (!CameraSettings.isFrontCamera() || !camera.isScreenSlideOff()) {
                ModeProtocol.CameraAction cameraAction = (ModeProtocol.CameraAction) ModeCoordinatorImpl.getInstance().getAttachProtocol(161);
                if (cameraAction == null) {
                    Log.w(TAG, "onSnapClick: no camera action");
                    return;
                }
                Log.d(TAG, "onSnapClick");
                int i = this.mCurrentMode;
                if (!(i == 161 || i == 162)) {
                    if (i != 166) {
                        if (!(i == 169 || i == 172 || i == 174 || i == 187)) {
                            if (i == 204) {
                                if (!DataRepository.dataItemRunning().getComponentRunningDualVideo().ismShouldDraw6Patch() && !this.mVideoRecordingStarted) {
                                    this.mVideoRecordingStarted = true;
                                }
                                cameraAction.onShutterButtonClick(10);
                            } else if (!(i == 176 || i == 177)) {
                                if (!(i == 179 || i == 180)) {
                                    if (i == 183) {
                                        cameraAction.onShutterButtonClick(10);
                                    } else if (i != 184) {
                                        TimerBurstController timerBurstController = DataRepository.dataItemLive().getTimerBurstController();
                                        if (!cameraAction.isBlockSnap() || timerBurstController.isInTimerBurstShotting()) {
                                            cameraAction.onShutterButtonClick(10);
                                        } else {
                                            Log.w(TAG, "onSnapClick: block snap");
                                            return;
                                        }
                                    }
                                }
                            }
                            if (Util.isAccessible()) {
                                if (162 != this.mCurrentMode) {
                                    this.mEdgeHorizonScrollView.setContentDescription(getString(R.string.accessibility_camera_shutter_finish));
                                } else if (((Camera) getActivity()).isRecording()) {
                                    this.mEdgeHorizonScrollView.setContentDescription(getString(R.string.accessibility_camera_record_start));
                                } else {
                                    this.mEdgeHorizonScrollView.setContentDescription(getString(R.string.accessibility_camera_record_stop));
                                }
                                this.mEdgeHorizonScrollView.sendAccessibilityEvent(32768);
                                return;
                            }
                            return;
                        }
                    }
                    if (!cameraAction.isDoingAction() || this.mCurrentMode == 184) {
                        cameraAction.onShutterButtonClick(10);
                        if (Util.isAccessible()) {
                        }
                    } else {
                        Log.w(TAG, "onSnapClick: doing action");
                        return;
                    }
                }
                if (!this.mVideoRecordingStarted) {
                    this.mVideoRecordingStarted = true;
                }
                cameraAction.onShutterButtonClick(10);
                if (Util.isAccessible()) {
                }
            } else {
                Log.w(TAG, "onSnapClick: ignore onSnapClick event, because screen slide is off");
            }
        }
    }

    @Override // com.android.camera.ui.CameraSnapView.SnapListener
    public void onSnapLongPress() {
        ModeProtocol.CameraAction cameraAction;
        if (isEnableClick() && (cameraAction = (ModeProtocol.CameraAction) ModeCoordinatorImpl.getInstance().getAttachProtocol(161)) != null && !cameraAction.isDoingAction()) {
            if (getContext() != null) {
                Camera camera = (Camera) getContext();
                if (CameraSettings.isFrontCamera() && camera.isScreenSlideOff()) {
                    return;
                }
            }
            Log.d(TAG, "onSnapLongPress");
            if (cameraAction.onShutterButtonLongClick()) {
                this.mLongPressBurst = true;
            }
        }
    }

    @Override // com.android.camera.ui.CameraSnapView.SnapListener
    public void onSnapLongPressCancelIn() {
        ModeProtocol.CameraAction cameraAction;
        if (isEnableClick() && (cameraAction = (ModeProtocol.CameraAction) ModeCoordinatorImpl.getInstance().getAttachProtocol(161)) != null) {
            Log.d(TAG, "onSnapLongPressCancelIn");
            cameraAction.onShutterButtonLongClickCancel(true);
            int i = this.mCurrentMode;
            if (i != 163) {
                if (i == 165 || i == 167 || i == 171) {
                    onSnapClick();
                }
            } else if (this.mLongPressBurst) {
                this.mLongPressBurst = false;
            }
        }
    }

    @Override // com.android.camera.ui.CameraSnapView.SnapListener
    public void onSnapLongPressCancelOut() {
        ModeProtocol.CameraAction cameraAction;
        if (isEnableClick() && (cameraAction = (ModeProtocol.CameraAction) ModeCoordinatorImpl.getInstance().getAttachProtocol(161)) != null) {
            Log.d(TAG, "onSnapLongPressCancelOut");
            cameraAction.onShutterButtonLongClickCancel(false);
            if (this.mLongPressBurst) {
                this.mLongPressBurst = false;
            }
        }
    }

    @Override // com.android.camera.ui.CameraSnapView.SnapListener
    public void onSnapPrepare() {
        ModeProtocol.CameraAction cameraAction;
        if (isEnableClick() && (cameraAction = (ModeProtocol.CameraAction) ModeCoordinatorImpl.getInstance().getAttachProtocol(161)) != null) {
            Log.d(TAG, "onSnapPrepare");
            cameraAction.onShutterButtonFocus(true, 2);
        }
    }

    @Override // com.android.camera.protocol.ModeProtocol.BottomMenuProtocol
    public void onSwitchCameraPicker() {
        ImageView imageView = this.mCameraPicker;
        if (imageView != null) {
            changeCamera(imageView);
        }
    }

    @Override // com.android.camera.protocol.ModeProtocol.BottomMenuProtocol
    public void onSwitchFastMotionAction(ComponentRunningFastMotion componentRunningFastMotion) {
        if (this.mBottomRecordingTime.getVisibility() == 0) {
            this.mBottomRecordingTime.setVisibility(8);
        }
        hideModeOrExternalTipLayout();
        this.mModeSelectLayout.switchFastMotion(componentRunningFastMotion);
    }

    @Override // com.android.camera.protocol.ModeProtocol.BottomMenuProtocol
    public void onSwitchLiveActionMenu(int i) {
        if (this.mBottomRecordingTime.getVisibility() == 0) {
            this.mBottomRecordingTime.setVisibility(8);
        }
        hideModeOrExternalTipLayout();
        this.mCurrentLiveActionMenuType = i;
        animateViews(1, (List<Completable>) null, this.mModeSelectLayout.getView());
        this.mModeSelectLayout.switchMenuMode(2, i, true);
    }

    @Override // com.android.camera.ui.CameraSnapView.SnapListener
    public void onTrackSnapMissTaken(long j) {
        ModeProtocol.CameraActionTrack cameraActionTrack;
        if (isEnableClick() && (cameraActionTrack = (ModeProtocol.CameraActionTrack) ModeCoordinatorImpl.getInstance().getAttachProtocol(186)) != null) {
            Log.d(TAG, "onTrackSnapMissTaken " + j + d.H);
            cameraActionTrack.onTrackShutterButtonMissTaken(j);
        }
    }

    @Override // com.android.camera.ui.CameraSnapView.SnapListener
    public void onTrackSnapTaken(long j) {
        ModeProtocol.CameraActionTrack cameraActionTrack;
        if (isEnableClick() && (cameraActionTrack = (ModeProtocol.CameraActionTrack) ModeCoordinatorImpl.getInstance().getAttachProtocol(186)) != null) {
            Log.d(TAG, "onTrackSnapTaken " + j + d.H);
            cameraActionTrack.onTrackShutterButtonTaken(j);
        }
    }

    @Override // com.android.camera.protocol.ModeProtocol.BottomMenuProtocol
    public void pauseRecording() {
        if (this.mVideoPauseSupported && this.mVideoRecordingStarted) {
            int i = this.mCurrentMode;
            if (!(i == 162 || i == 169)) {
                if (i != 174) {
                    if (i != 180) {
                        if (i != 183) {
                            if (i != 204) {
                                return;
                            }
                        }
                    }
                } else if (this.mVideoRecordingPaused) {
                    CameraStatUtils.trackLiveClick(MistatsConstants.Live.LIVE_CLICK_RESUME);
                } else {
                    CameraStatUtils.trackLiveClick(MistatsConstants.Live.LIVE_CLICK_PAUSE);
                }
                long currentTimeMillis = System.currentTimeMillis() - this.mLastPauseTime;
                if (currentTimeMillis <= 0 || currentTimeMillis >= 500) {
                    this.mLastPauseTime = System.currentTimeMillis();
                    ActivityBase activityBase = (ActivityBase) getContext();
                    if (activityBase == null || (!(activityBase.getCurrentModule() instanceof LiveModule) && !(activityBase.getCurrentModule() instanceof MiLiveModule))) {
                        Log.w(TAG, "onClick: recording pause is not allowed!!!");
                        return;
                    } else {
                        ((ILiveModule) activityBase.getCurrentModule()).onPauseButtonClick();
                        return;
                    }
                } else {
                    return;
                }
            }
            ((VideoModule) ((ActivityBase) getContext()).getCurrentModule()).onPauseButtonClick();
        }
    }

    @Override // com.android.camera.protocol.ModeProtocol.ActionProcessing
    public void processingAudioCapture(boolean z) {
        if (z) {
            this.mShutterButton.startRing();
        } else {
            this.mShutterButton.stopRing();
        }
    }

    @Override // com.android.camera.protocol.ModeProtocol.ActionProcessing
    public void processingFailed() {
        updateLoading(true);
    }

    @Override // com.android.camera.protocol.ModeProtocol.ActionProcessing
    public void processingFinish() {
        if (isAdded()) {
            animateViews(1, (List<Completable>) null, this.mShutterButton);
            this.mEdgeHorizonScrollView.setVisibility(0);
            this.mModeSelectView.setEnabled(true);
            this.mVideoRecordingStarted = false;
            this.mVideoRecordingPaused = false;
            setProgressBarVisible(8);
            this.mShutterButton.showRoundPaintItem();
            int i = this.mCurrentMode;
            if (i != 163) {
                if (i != 167) {
                    if (i != 174) {
                        if (i == 177) {
                            animateViews(-1, (List<Completable>) null, this.mMimojiBack);
                        } else if (i == 180) {
                            setRecordingSwitchTarget(false);
                            animateViews(1, (List<Completable>) null, this.mRecordingSwitch);
                        } else if (i != 183) {
                            if (i == 184) {
                                showNormalMimoji2Bottom();
                            }
                        }
                    }
                    if (this.mBottomRecordingCameraPicker.getVisibility() == 0) {
                        Completable.create(new AlphaOutOnSubscribe(this.mBottomRecordingCameraPicker)).subscribe();
                    }
                } else if (DataRepository.dataItemFeature().Yl()) {
                    setRecordingSwitchTarget(true);
                    animateViews(1, (List<Completable>) null, this.mRecordingSwitch);
                } else {
                    animateViews(-1, (List<Completable>) null, this.mRecordingSwitch);
                }
            }
            BottomAnimationConfig configVariables = BottomAnimationConfig.generate(false, this.mCurrentMode, false, isFPS960(), CameraSettings.isVideoBokehOn()).configVariables();
            if (this.mCurrentMode == 184) {
                configVariables.mIsInMimojiCreate = DataRepository.dataItemLive().getMimojiStatusManager2().isInMimojiCreate();
            } else {
                configVariables.mIsInMimojiCreate = DataRepository.dataItemLive().getMimojiStatusManager().IsInMimojiCreate();
            }
            this.mShutterButton.triggerAnimation(configVariables);
            updateBottomInRecording(false, false);
        }
    }

    @Override // com.android.camera.protocol.ModeProtocol.ActionProcessing
    public void processingLongExposePrepare() {
        this.mShutterButton.longExposePrepare(BottomAnimationConfig.generate(false, this.mCurrentMode, true, isFPS960(), CameraSettings.isVideoBokehOn()).configVariables());
    }

    @Override // com.android.camera.protocol.ModeProtocol.ActionProcessing
    public void processingLongExposeStart() {
        this.mShutterButton.longExposeStart(BottomAnimationConfig.generate(false, this.mCurrentMode, true, isFPS960(), CameraSettings.isVideoBokehOn()).configVariables());
    }

    @Override // com.android.camera.protocol.ModeProtocol.ActionProcessing
    public void processingMimojiBack() {
        DataRepository.dataItemLive().getMimojiStatusManager().setMode(MimojiStatusManager.MIMOJI_PREVIEW);
        DataRepository.dataItemLive().getMimojiStatusManager2().setMode(2);
        animateViews(-1, (List<Completable>) null, this.mCameraPicker);
        animateViews(-1, (List<Completable>) null, this.mMimojiBack);
        animateViews(1, (List<Completable>) null, this.mThumbnailImageLayout);
        showModeOrExternalTipLayout(false);
        animateViews(1, (List<Completable>) null, this.mModeSelectLayout.getView());
        if (this.mCurrentMode == 184) {
            showNormalMimoji2Bottom();
            return;
        }
        animateViews(1, (List<Completable>) null, this.mCameraPicker);
        animateViews(-1, (List<Completable>) null, this.mRecordingSwitch);
    }

    @Override // com.android.camera.protocol.ModeProtocol.ActionProcessing
    public void processingMimojiPrepare() {
        hideModeOrExternalTipLayout();
        this.mDragUpLayout.setDragEnable(false);
        animateViews(-1, (List<Completable>) null, this.mThumbnailImageLayout);
        animateViews(1, (List<Completable>) null, this.mMimojiBack);
        if (this.mCurrentMode == 184) {
            DataRepository.dataItemLive().getMimojiStatusManager2().setMode(4);
            animateViews(1, (List<Completable>) null, this.mCameraPicker);
            this.mShutterButton.onForceVideoStateChange(PaintConditionReferred.create(184));
            animateViews(-1, (List<Completable>) null, this.mRecordingSwitch);
        }
    }

    @Override // com.android.camera.protocol.ModeProtocol.ActionProcessing
    public void processingPause() {
        this.mVideoRecordingPaused = true;
        this.mShutterButton.pauseRecording();
        setPausePlaySwitchTarget(true);
        this.mRecordingPause.T();
        this.mRecordingPause.setContentDescription(getString(R.string.accessibility_video_resume_button));
        int i = this.mCurrentMode;
        if (i == 174) {
            this.mShutterButton.addSegmentNow();
            Completable.create(new AlphaInOnSubscribe(this.mBottomRecordingCameraPicker)).subscribe();
            Completable.create(new AlphaInOnSubscribe(this.mRecordingReverse)).subscribe();
        } else if (i == 183) {
            AnonymousClass3 r0 = new AnimatorListenerAdapter() {
                /* class com.android.camera.fragment.bottom.action.FragmentBottomAction.AnonymousClass3 */

                public void onAnimationEnd(Animator animator) {
                    FragmentBottomAction.this.mShutterButton.addSegmentNow();
                    Completable.create(new AlphaInOnSubscribe(FragmentBottomAction.this.mRecordingReverse)).subscribe();
                    if (((BaseFragment) FragmentBottomAction.this).mCurrentMode == 183 && DataRepository.dataItemFeature().Qk()) {
                        Completable.create(new AlphaOutOnSubscribe(FragmentBottomAction.this.mRecordingSnap)).subscribe();
                    }
                    FragmentBottomAction.this.mBottomAnimator.removeListener(this);
                }
            };
            ValueAnimator valueAnimator = this.mBottomAnimator;
            if (valueAnimator == null || !valueAnimator.isRunning()) {
                r0.onAnimationEnd(null);
                return;
            }
            this.mBottomAnimator.addListener(r0);
            this.mBottomAnimator.end();
        }
    }

    @Override // com.android.camera.protocol.ModeProtocol.ActionProcessing
    public void processingPostAction() {
        if (this.mShutterButton.getVisibility() != 0) {
            this.mShutterButton.setVisibility(0);
        }
        this.mShutterButton.hideRoundPaintItem();
        this.mShutterButton.triggerAnimation(BottomAnimationConfig.generate(true, this.mCurrentMode, false, isFPS960(), CameraSettings.isVideoBokehOn()).configVariables());
        this.mEdgeHorizonScrollView.setVisibility(0);
        updateBottomInRecording(false, true);
        setProgressBarVisible(0);
    }

    @Override // com.android.camera.protocol.ModeProtocol.ActionProcessing
    public void processingPrepare() {
        BottomAnimationConfig configVariables = BottomAnimationConfig.generate(false, this.mCurrentMode, true, isFPS960(), CameraSettings.isVideoBokehOn()).configVariables();
        updateBottomInRecording(true, true);
        this.mShutterButton.prepareRecording(configVariables);
    }

    @Override // com.android.camera.protocol.ModeProtocol.ActionProcessing
    public void processingResume() {
        this.mVideoRecordingPaused = false;
        this.mShutterButton.resumeRecording();
        setPausePlaySwitchTarget(false);
        this.mRecordingPause.T();
        this.mRecordingPause.setContentDescription(getString(R.string.accessibility_video_pause_button));
        this.mDragUpLayout.setDragEnable(false);
        animateViews(-1, (List<Completable>) null, this.mModeSelectLayout.getView());
        int i = this.mCurrentMode;
        if (i == 174) {
            Completable.create(new AlphaOutOnSubscribe(this.mRecordingReverse)).subscribe();
            Completable.create(new AlphaOutOnSubscribe(this.mBottomRecordingCameraPicker)).subscribe();
        } else if (i == 183) {
            Completable.create(new AlphaOutOnSubscribe(this.mRecordingReverse)).subscribe();
            if (DataRepository.dataItemFeature().Qk()) {
                Completable.create(new AlphaInOnSubscribe(this.mRecordingSnap)).subscribe();
            }
        }
    }

    @Override // com.android.camera.protocol.ModeProtocol.ActionProcessing
    public void processingSpeechShutter(boolean z) {
        if (z) {
            this.mShutterButton.startSpeech();
        } else {
            this.mShutterButton.stopSpeech();
        }
    }

    @Override // com.android.camera.protocol.ModeProtocol.ActionProcessing
    public void processingStart() {
        processingStart(0, false, false, false);
    }

    @Override // com.android.camera.protocol.ModeProtocol.ActionProcessing
    public void processingStart(int i, boolean z, boolean z2, boolean z3) {
        this.mEdgeHorizonScrollView.setVisibility(8);
        this.mModeSelectView.setEnabled(false);
        this.mModeSelectView.stopScroll();
        this.mVideoRecordingStarted = true;
        BottomAnimationConfig configVariables = BottomAnimationConfig.generate(false, this.mCurrentMode, true, isFPS960(), CameraSettings.isVideoBokehOn()).configVariables();
        if (i != 0) {
            configVariables.setSpecifiedDuration(i, z, z2);
        }
        this.mShutterButton.setStopButtonEnable(!z3, false);
        this.mShutterButton.triggerAnimation(configVariables);
    }

    @Override // com.android.camera.protocol.ModeProtocol.ActionProcessing
    public void processingWorkspace(boolean z) {
        if (z) {
            animateViews(1, (List<Completable>) null, this.mShutterButton);
            this.mBottomRecordingTime.setVisibility(0);
            if (this.mRecordingPause.getVisibility() == 8) {
                setPausePlaySwitchTarget(true);
                Completable.create(new AlphaInOnSubscribe(this.mRecordingPause).targetGone()).subscribe();
            }
            if (this.mRecordingReverse.getVisibility() == 8) {
                Completable.create(new AlphaInOnSubscribe(this.mRecordingReverse).targetGone()).subscribe();
            }
            if (this.mBottomRecordingCameraPicker.getVisibility() == 8) {
                Completable.create(new AlphaInOnSubscribe(this.mBottomRecordingCameraPicker).targetGone()).subscribe();
                return;
            }
            return;
        }
        if (!(this.mCurrentMode == 184 && DataRepository.dataItemLive().getMimojiStatusManager2().getMimojiRecordState() == 1)) {
            this.mShutterButton.pauseRecording();
            this.mShutterButton.addSegmentNow();
        }
        animateViews(-1, (List<Completable>) null, this.mShutterButton);
        this.mBottomRecordingTime.setVisibility(8);
        if (this.mRecordingPause.getVisibility() == 0) {
            AlphaOutOnSubscribe.directSetResult(this.mRecordingPause);
        }
        if (this.mRecordingReverse.getVisibility() == 0) {
            AlphaOutOnSubscribe.directSetResult(this.mRecordingReverse);
        }
        if (this.mBottomRecordingCameraPicker.getVisibility() == 0 && this.mCurrentMode != 183) {
            AlphaOutOnSubscribe.directSetResult(this.mBottomRecordingCameraPicker);
        }
        if (this.mRecordingSnap.getVisibility() == 0) {
            AlphaOutOnSubscribe.directSetResult(this.mRecordingSnap);
        }
    }

    /* JADX INFO: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARNING: Code restructure failed: missing block: B:116:0x01c2, code lost:
        if (com.android.camera.data.DataRepository.dataItemFeature().Dk() != false) goto L_0x0165;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:121:0x01de, code lost:
        if (com.android.camera.data.DataRepository.dataItemFeature().zl() == false) goto L_0x01c5;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:123:0x01e9, code lost:
        if (com.android.camera.data.DataRepository.dataItemFeature().Ak() != false) goto L_0x0165;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:125:0x01f5, code lost:
        if (com.android.camera.data.DataRepository.dataItemFeature().Bk() != false) goto L_0x0165;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:127:0x0201, code lost:
        if (com.android.camera.data.DataRepository.dataItemFeature().vk() != false) goto L_0x0165;
     */
    /* JADX WARNING: Removed duplicated region for block: B:150:0x026a  */
    /* JADX WARNING: Removed duplicated region for block: B:151:0x026c  */
    /* JADX WARNING: Removed duplicated region for block: B:154:0x0271  */
    /* JADX WARNING: Removed duplicated region for block: B:155:0x0273  */
    /* JADX WARNING: Removed duplicated region for block: B:158:0x0278  */
    @Override // com.android.camera.fragment.BaseFragment, com.android.camera.animation.AnimationDelegate.AnimationResource
    public void provideAnimateElement(int i, List<Completable> list, int i2) {
        int i3;
        int i4;
        int i5;
        int i6;
        int i7;
        int i8;
        int i9;
        int i10;
        int i11 = this.mCurrentMode;
        boolean z = i2 == 3;
        if (z || i11 != i) {
            View view = getView();
            if (!(view == null || ViewCompat.getTranslationY(view) == 0.0f)) {
                ViewCompat.setTranslationY(view, 0.0f);
            }
            AlertDialog alertDialog = this.mReverseDialog;
            if (alertDialog != null) {
                alertDialog.dismiss();
                this.mReverseDialog = null;
            }
            if (this.mIsShowMiMoji) {
                showOrHideMiMojiView();
            }
            this.mShutterButton.onTimeOut();
            if (this.mVideoRecordingStarted) {
                this.mVideoRecordingStarted = false;
                AlphaInOnSubscribe.directSetResult(this.mModeSelectBackgroundLayout);
                this.mRecordingReverse.setVisibility(8);
                this.mRecordingPause.setVisibility(8);
                this.mRecordingSnap.setVisibility(8);
                this.mBottomRecordingCameraPicker.setVisibility(8);
            }
        }
        if (i2 != 2 && isShowLightingView()) {
            showOrHideLightingView();
        }
        if ((i11 != 174 && i11 != 183) || !this.mVideoRecordingStarted || z) {
            switchModeSelectViewStyle(i);
            super.provideAnimateElement(i, list, i2);
            processingSpeechShutter(false);
            PaintConditionReferred createModeChange = PaintConditionReferred.createModeChange(this.mCurrentMode, isFPS960(), list != null);
            if (i != 185) {
                if (i == 254) {
                    createModeChange.setNeedSnapButtonAnimation(false);
                }
                this.mShutterButton.setParameters(createModeChange);
            }
            int bottomMaskTargetHeight = createModeChange.getBottomMaskTargetHeight(i);
            if (createModeChange.bottomHalfScreen()) {
                this.mCameraPicker.setBackgroundResource(R.drawable.bg_thumbnail_background_half);
                this.mRecordingSwitch.setBackgroundResource(R.drawable.bg_thumbnail_background_half);
                this.mMimojiBack.setBackgroundResource(R.drawable.bg_thumbnail_background_half);
                this.mThumbnailImage.setBackgroundResource(R.drawable.bg_thumbnail_background_half);
            }
            Integer num = (Integer) this.mModeSelectBackgroundLayout.getTag();
            if (num == null || num.intValue() != bottomMaskTargetHeight) {
                this.mModeSelectBackgroundLayout.setTag(Integer.valueOf(bottomMaskTargetHeight));
                if (bottomMaskTargetHeight > this.mModeSelectBackgroundLayout.getCurrentMaskHeight()) {
                    this.mModeSelectBackgroundLayout.setMaskSpecificHeight(bottomMaskTargetHeight, list != null);
                } else if (list == null) {
                    this.mModeSelectBackgroundLayout.setMaskSpecificHeight(bottomMaskTargetHeight, list != null);
                } else if (i11 == 165 && i == 254) {
                    this.mModeSelectBackgroundLayout.setMaskSpecificHeight(bottomMaskTargetHeight, list != null);
                }
            }
            Log.d(TAG, "provideAnimateElement: newMode = " + i + ", mCurrentMode = " + this.mCurrentMode + ", animateInElements = " + list);
            if (i != 163) {
                if (i != 169) {
                    if (i == 182) {
                        i9 = 1;
                        i5 = 1;
                        i3 = -1;
                        i8 = -1;
                        i7 = -1;
                        i6 = -1;
                        i4 = -1;
                    } else if (i != 254) {
                        if (i != 179) {
                            if (i != 180) {
                                if (i != 204) {
                                    if (i != 205) {
                                        if (!(i == 209 || i == 210)) {
                                            switch (i) {
                                                case 165:
                                                    break;
                                                case 166:
                                                    break;
                                                case 167:
                                                    break;
                                                default:
                                                    switch (i) {
                                                        case 171:
                                                            break;
                                                        case 172:
                                                            break;
                                                        case 173:
                                                            if (!DataRepository.dataItemFeature().yl()) {
                                                                break;
                                                            }
                                                            break;
                                                        default:
                                                            switch (i) {
                                                                case 175:
                                                                    break;
                                                                case 176:
                                                                    break;
                                                                case 177:
                                                                    if (DataRepository.dataItemLive().getMimojiStatusManager().IsInMimojiCreate()) {
                                                                        i3 = 1;
                                                                        i9 = 1;
                                                                        i7 = 1;
                                                                        i6 = 1;
                                                                        i8 = -1;
                                                                        i5 = -1;
                                                                        i4 = -1;
                                                                        break;
                                                                    }
                                                                    break;
                                                                default:
                                                                    switch (i) {
                                                                        case 184:
                                                                            MimojiStatusManager2 mimojiStatusManager2 = DataRepository.dataItemLive().getMimojiStatusManager2();
                                                                            if (mimojiStatusManager2.isInMimojiCreate()) {
                                                                                i7 = 1;
                                                                                i10 = 1;
                                                                                i8 = -1;
                                                                                i5 = -1;
                                                                                i4 = -1;
                                                                            } else {
                                                                                if (CameraSettings.isGifOn()) {
                                                                                    i5 = 1;
                                                                                    i4 = 1;
                                                                                    i8 = -1;
                                                                                    i7 = -1;
                                                                                } else {
                                                                                    i8 = 1;
                                                                                    i5 = 1;
                                                                                    i4 = 1;
                                                                                    i7 = -1;
                                                                                }
                                                                                i10 = i7;
                                                                            }
                                                                            if (i8 == 1 && i2 != 5) {
                                                                                setRecordingSwitchTarget(mimojiStatusManager2.isInMimojiPhoto());
                                                                            }
                                                                            i9 = 1;
                                                                            i3 = i10;
                                                                            i6 = 1;
                                                                            break;
                                                                    }
                                                            }
                                                    }
                                            }
                                        }
                                    }
                                } else if (DataRepository.dataItemFeature().tj()) {
                                    if (!DataRepository.dataItemRunning().getComponentRunningDualVideo().ismShouldDraw6Patch()) {
                                        i3 = 1;
                                        i9 = 1;
                                        i6 = 1;
                                        i4 = 1;
                                        i8 = -1;
                                        i7 = -1;
                                        i5 = -1;
                                    }
                                }
                            }
                            int i12 = DataRepository.dataItemFeature().Yl() ? 1 : -1;
                            setRecordingSwitchTarget(i == 167);
                            i8 = i12;
                            i9 = 1;
                            i6 = 1;
                            i5 = 1;
                            i4 = 1;
                            i3 = -1;
                            i7 = -1;
                        }
                        i9 = 1;
                        i6 = 1;
                        i5 = 1;
                        i4 = 1;
                        i3 = -1;
                        i8 = -1;
                        i7 = i8;
                    } else {
                        i6 = 1;
                        i5 = 1;
                        i3 = -1;
                        i9 = -1;
                        i8 = -1;
                        i7 = -1;
                        i4 = -1;
                    }
                    this.mCameraPickEnable = i3 == 1;
                    this.mBackEnable = i7 == 1;
                    if (i5 == 1) {
                        this.mModeSelectLayout.clearBottomMenu();
                        AlphaInOnSubscribe.directSetResult(this.mEdgeHorizonScrollView);
                        if (!this.mComponentModuleList.isCommonMode(i)) {
                            this.mDragUpLayout.setDragEnable(false);
                            AlphaOutOnSubscribe.directSetResult(this.mModeSelectView);
                            String geMoreItemName = DataRepository.dataItemGlobal().getComponentModuleList().geMoreItemName(i);
                            if (!TextUtils.isEmpty(geMoreItemName)) {
                                this.mExternalModeTipLayout.setEnabled(true);
                                this.mExternalModeTipText.setText(geMoreItemName);
                                if (list == null || ((Integer) this.mExternalModeTipLayout.getTag()).intValue() == 1) {
                                    AlphaInOnSubscribe.directSetResult(this.mExternalModeTipLayout);
                                } else {
                                    list.add(Completable.create(new RotateOnSubscribe(this.mExternalModeTipIcon).setRotateDegree(-90, 0).setInterpolator(new miuix.view.animation.CubicEaseOutInterpolator()).setDurationTime(300)));
                                    this.mExternalModeTipLayout.start();
                                }
                                this.mExternalModeTipLayout.setTag(1);
                            } else {
                                this.mExternalModeTipLayout.setTag(-1);
                                AlphaOutOnSubscribe.directSetResult(this.mExternalModeTipLayout);
                            }
                        } else {
                            this.mExternalModeTipLayout.setTag(-1);
                            AlphaOutOnSubscribe.directSetResult(this.mExternalModeTipLayout);
                            if (!this.mIsShowLighting || !this.mIsShowMiMoji) {
                                this.mModeSelectView.setEnabled(true);
                                AlphaInOnSubscribe.directSetResult(this.mModeSelectView);
                            }
                        }
                        if (z) {
                            this.mModeSelectView.moveToPosition(i);
                        }
                    }
                    animateViews(i9, list, this.mShutterButton);
                    animateViews(i3, list, this.mCameraPicker);
                    animateViews(i6, list, this.mModeSelectLayout.getView());
                    animateViews(i4, list, this.mThumbnailImageLayout);
                    animateViews(i8, list, this.mRecordingSwitch);
                    animateViews(i7, list, this.mMimojiBack);
                }
                i3 = DataRepository.dataItemFeature().Dj() ? 1 : -1;
                i9 = 1;
                i6 = i9;
                i5 = i6;
                i4 = i5;
                i8 = -1;
                i7 = i8;
                this.mCameraPickEnable = i3 == 1;
                this.mBackEnable = i7 == 1;
                if (i5 == 1) {
                }
                animateViews(i9, list, this.mShutterButton);
                animateViews(i3, list, this.mCameraPicker);
                animateViews(i6, list, this.mModeSelectLayout.getView());
                animateViews(i4, list, this.mThumbnailImageLayout);
                animateViews(i8, list, this.mRecordingSwitch);
                animateViews(i7, list, this.mMimojiBack);
            }
            i3 = 1;
            i9 = 1;
            i6 = i9;
            i5 = i6;
            i4 = i5;
            i8 = -1;
            i7 = i8;
            this.mCameraPickEnable = i3 == 1;
            this.mBackEnable = i7 == 1;
            if (i5 == 1) {
            }
            animateViews(i9, list, this.mShutterButton);
            animateViews(i3, list, this.mCameraPicker);
            animateViews(i6, list, this.mModeSelectLayout.getView());
            animateViews(i4, list, this.mThumbnailImageLayout);
            animateViews(i8, list, this.mRecordingSwitch);
            animateViews(i7, list, this.mMimojiBack);
        }
    }

    /* access modifiers changed from: protected */
    @Override // com.android.camera.fragment.BaseFragment
    public Animation provideEnterAnimation(int i) {
        if (i == 240) {
            return null;
        }
        if (i != 65530) {
            Animation wrapperAnimation = FragmentAnimationFactory.wrapperAnimation(161);
            wrapperAnimation.setStartOffset(150);
            return wrapperAnimation;
        }
        ViewCompat.setTranslationY(getView(), 0.0f);
        Animation wrapperAnimation2 = FragmentAnimationFactory.wrapperAnimation(161);
        wrapperAnimation2.setStartOffset(150);
        return wrapperAnimation2;
    }

    /* access modifiers changed from: protected */
    @Override // com.android.camera.fragment.BaseFragment
    public Animation provideExitAnimation(int i) {
        if (i == 65530) {
            ViewCompat.setTranslationY(getView(), (float) (((getResources().getDimensionPixelSize(R.dimen.vv_start_layout_height_extra) + Util.sBottomMargin) + getResources().getDimensionPixelSize(R.dimen.vv_list_height)) - getView().getHeight()));
        }
        return FragmentAnimationFactory.wrapperAnimation(162);
    }

    @Override // com.android.camera.fragment.BaseFragment, com.android.camera.animation.AnimationDelegate.AnimationResource
    public void provideRotateItem(List<View> list, int i) {
        super.provideRotateItem(list, i);
        list.add(this.mThumbnailImageLayout);
        list.add(this.mShutterButton);
        list.add(this.mCameraPicker);
        list.add(this.mPostProcess);
        list.add(this.mRecordingPause);
        list.add(this.mBottomRecordingCameraPicker);
        list.add(this.mRecordingReverse);
        list.add(this.mRecordingSwitch);
    }

    /* access modifiers changed from: protected */
    @Override // com.android.camera.fragment.BaseFragment
    public void register(ModeProtocol.ModeCoordinator modeCoordinator) {
        super.register(modeCoordinator);
        modeCoordinator.attachProtocol(2561, this);
        modeCoordinator.attachProtocol(179, this);
        modeCoordinator.attachProtocol(162, this);
        modeCoordinator.attachProtocol(183, this);
        modeCoordinator.attachProtocol(197, this);
        registerBackStack(modeCoordinator, this);
    }

    @Override // com.android.camera.protocol.ModeProtocol.ModeChangeController
    public void resetToCommonMode() {
        if (CameraSettings.getMoreModeStyle() == 1) {
            int curSelectMode = this.mModeSelectView.getCurSelectMode();
            if (!this.mComponentModuleList.isCommonMode(curSelectMode)) {
                curSelectMode = 163;
            }
            changeModeByNewMode(curSelectMode, 0);
        } else if (CameraSettings.getMoreModeStyle() == 0) {
            this.mModeSelectView.moveToPosition(163);
            changeModeByNewMode(163, 0);
        }
    }

    @Override // com.android.camera.fragment.BaseFragment, com.android.camera.animation.AnimationDelegate.AnimationResource
    public void setClickEnable(boolean z) {
        super.setClickEnable(z);
        this.mShutterButton.setSnapClickEnable(z);
    }

    @Override // com.android.camera.protocol.ModeProtocol.ActionProcessing
    public void setLightingViewStatus(boolean z) {
        this.mIsShowLighting = z;
    }

    @Override // com.android.camera.protocol.ModeProtocol.BottomMenuProtocol
    public void setModeLayoutVisibility(int i, boolean z) {
        ModeSelectView modeSelectView;
        View view = (this.mExternalModeTipLayout.getTag() == null || ((Integer) this.mExternalModeTipLayout.getTag()).intValue() != 1) ? this.mModeSelectView : this.mExternalModeTipLayout;
        if (view.getVisibility() != i) {
            view.setVisibility(i);
            if (z && view == (modeSelectView = this.mModeSelectView)) {
                ViewCompat.setScaleX(modeSelectView, 1.1f);
                ViewCompat.animate(this.mModeSelectView).scaleX(1.0f).setDuration(400).setInterpolator(new CubicEaseOutInterpolator()).start();
            }
            if (i == 0) {
                switchMoreMode(true);
            } else {
                this.mDragUpLayout.setDragEnable(false);
            }
        }
    }

    @Override // com.android.camera.protocol.ModeProtocol.ActionProcessing
    public void showCameraPicker(boolean z) {
        animateViews(z ? 1 : -1, (List<Completable>) null, this.mCameraPicker);
    }

    @Override // com.android.camera.protocol.ModeProtocol.ActionProcessing
    public void showDocumentReviewViews(Bitmap bitmap, float[] fArr) {
        if (getActivity() != null && bitmap != null && !bitmap.isRecycled() && fArr.length >= 8) {
            if (this.mDocumentContainer == null) {
                this.mDocumentContainer = LayoutInflater.from(getContext()).inflate(R.layout.layout_document_preview, (ViewGroup) null);
                ((ViewGroup) getActivity().findViewById(R.id.camera_app_root)).addView(this.mDocumentContainer);
            }
            final AdjustAnimationView adjustAnimationView = (AdjustAnimationView) this.mDocumentContainer.findViewById(R.id.document_review_image);
            adjustAnimationView.setVisibility(0);
            adjustAnimationView.setBitmap(bitmap, fArr);
            Rect imageRect = adjustAnimationView.getImageRect();
            final AnimationView animationView = (AnimationView) this.mDocumentContainer.findViewById(R.id.document_anim_image);
            animationView.setImageBitmap(bitmap);
            FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) animationView.getLayoutParams();
            layoutParams.topMargin = imageRect.top;
            layoutParams.leftMargin = imageRect.left;
            layoutParams.width = imageRect.width();
            layoutParams.height = imageRect.height();
            animationView.setLayoutParams(layoutParams);
            this.mThumbnailImageLayout.setVisibility(4);
            final ViewGroup viewGroup = this.mThumbnailImageLayout;
            adjustAnimationView.startAnim(new AnimatorListenerAdapter() {
                /* class com.android.camera.fragment.bottom.action.FragmentBottomAction.AnonymousClass2 */

                public void onAnimationEnd(Animator animator) {
                    super.onAnimationEnd(animator);
                    animationView.setVisibility(0);
                    adjustAnimationView.clearBitmap();
                    FragmentBottomAction.this.initAnimation(adjustAnimationView, animationView, viewGroup);
                }
            }, Util.getEnterDuration());
        }
    }

    @Override // com.android.camera.ui.DragLayout.OnDragListener
    public boolean showDragAnimation(int i, int i2) {
        if (this.mBottomActionView.getVisibility() == 0 && this.mShutterButton.inRegion(i, i2)) {
            return false;
        }
        if (this.mThumbnailImageLayout.getVisibility() == 0 && Util.isInViewRegion(this.mThumbnailImageLayout, i, i2)) {
            return false;
        }
        if (this.mCameraPicker.getVisibility() == 0 && Util.isInViewRegion(this.mCameraPicker, i, i2)) {
            return false;
        }
        if (this.mRecordingSwitch.getVisibility() != 0 || !Util.isInViewRegion(this.mRecordingSwitch, i, i2)) {
            return (this.mModeSelectView.getVisibility() != 0 || !Util.isInViewRegion(this.mModeSelectView, i, i2)) && this.mBottomActionView.getVisibility() == 0;
        }
        return false;
    }

    @Override // com.android.camera.protocol.ModeProtocol.ActionProcessing
    public void showOrHideBottom(boolean z) {
        if (z) {
            Completable.create(new AlphaInOnSubscribe(this.mV9bottomParentLayout)).subscribe();
        } else {
            Completable.create(new AlphaOutOnSubscribe(this.mV9bottomParentLayout)).subscribe();
        }
    }

    @Override // com.android.camera.protocol.ModeProtocol.ActionProcessing
    public void showOrHideBottomViewWithAnim(boolean z) {
    }

    @Override // com.android.camera.protocol.ModeProtocol.ActionProcessing
    public boolean showOrHideFilterView() {
        return false;
    }

    @Override // com.android.camera.protocol.ModeProtocol.ActionProcessing
    public boolean showOrHideLightingView() {
        this.mIsShowLighting = !this.mIsShowLighting;
        return this.mIsShowLighting;
    }

    @Override // com.android.camera.protocol.ModeProtocol.ActionProcessing
    public void showOrHideLoadingProgress(boolean z, boolean z2) {
        if (z2) {
            if (z) {
                this.mShutterButton.hideRoundPaintItem();
            } else {
                this.mShutterButton.showRoundPaintItem();
            }
        }
        setProgressBarVisible(z ? 0 : 8);
    }

    @Override // com.android.camera.protocol.ModeProtocol.ActionProcessing
    public boolean showOrHideMiMojiView() {
        this.mIsShowMiMoji = !this.mIsShowMiMoji;
        return this.mIsShowMiMoji;
    }

    @Override // com.android.camera.protocol.ModeProtocol.ActionProcessing
    public void showOrHideMimojiProgress(boolean z) {
        int i = 0;
        BottomAnimationConfig configVariables = BottomAnimationConfig.generate(true, this.mCurrentMode, false, isFPS960(), CameraSettings.isVideoBokehOn()).configVariables();
        configVariables.mIsInMimojiCreate = true;
        this.mShutterButton.triggerAnimation(configVariables);
        if (!z) {
            i = 8;
        }
        setProgressBarVisible(i);
    }

    @Override // com.android.camera.protocol.ModeProtocol.MoreModePopupController
    public boolean shrink(boolean z) {
        DragLayout dragLayout = this.mDragUpLayout;
        if (dragLayout != null) {
            return dragLayout.shrink(z);
        }
        return false;
    }

    @Override // com.android.camera.protocol.ModeProtocol.ActionProcessing
    public void switchModeOrExternalTipLayout(boolean z) {
        if (isAdded()) {
            if (z) {
                showModeOrExternalTipLayout(true);
            } else {
                hideModeOrExternalTipLayout();
            }
        }
    }

    @Override // com.android.camera.protocol.ModeProtocol.ActionProcessing
    public void switchThumbnailFunction(boolean z) {
        if (z) {
            this.mThumbnailImage.setBackgroundResource(R.drawable.bg_thumbnail_background_half);
            initThumbnialAsThumbnail();
            return;
        }
        this.mThumbnailImage.setBackgroundResource(R.drawable.bg_thumbnail_background_full);
        initThumbnailAsExit();
    }

    /* access modifiers changed from: protected */
    @Override // com.android.camera.fragment.BaseFragment
    public void unRegister(ModeProtocol.ModeCoordinator modeCoordinator) {
        super.unRegister(modeCoordinator);
        this.mHandler.removeCallbacksAndMessages(null);
        modeCoordinator.detachProtocol(2561, this);
        modeCoordinator.detachProtocol(179, this);
        modeCoordinator.detachProtocol(162, this);
        modeCoordinator.detachProtocol(183, this);
        modeCoordinator.detachProtocol(197, this);
        unRegisterBackStack(modeCoordinator, this);
    }

    @Override // com.android.camera.protocol.ModeProtocol.ActionProcessing
    public void updateLoading(boolean z) {
        if (z) {
            this.mInLoading = false;
            this.mHandler.removeCallbacksAndMessages(null);
            this.mThumbnailProgress.setVisibility(8);
        } else if (!Util.isSaveToHidenFolder(this.mCurrentMode) && !DataRepository.dataItemLive().getTimerBurstController().isInTimerBurstShotting() && !this.mIsIntentAction) {
            this.mInLoading = true;
            int i = this.mCurrentMode;
            if (i == 161 || i == 162 || i == 166 || i == 172 || i == 174 || i == 176 || i == 183) {
                this.mHandler.sendEmptyMessageDelayed(1, (long) this.mRecordProgressDelay);
            } else {
                this.mHandler.sendEmptyMessageDelayed(1, (long) this.mCaptureProgressDelay);
            }
        }
    }

    @Override // com.android.camera.protocol.ModeProtocol.ActionProcessing
    public void updatePauseAndCaptureView(boolean z) {
        if (z) {
            if (this.mVideoPauseSupported) {
                Completable.create(new AlphaInOnSubscribe(this.mRecordingPause)).subscribe();
            }
            if (this.mVideoCaptureEnable) {
                Completable.create(new AlphaInOnSubscribe(this.mRecordingSnap)).subscribe();
                return;
            }
            return;
        }
        if (this.mVideoPauseSupported) {
            Completable.create(new AlphaOutOnSubscribe(this.mRecordingPause).targetGone()).subscribe();
        }
        if (this.mVideoCaptureEnable) {
            Completable.create(new AlphaOutOnSubscribe(this.mRecordingSnap).targetGone()).subscribe();
        }
    }

    @Override // com.android.camera.protocol.ModeProtocol.ActionProcessing
    public void updateRecordingTime(String str) {
        this.mBottomRecordingTime.setText(str);
    }

    @Override // com.android.camera.protocol.ModeProtocol.ActionProcessing
    public void updateThumbnail(Thumbnail thumbnail, boolean z, int i) {
        if (isAdded()) {
            ActivityBase activityBase = (ActivityBase) getContext();
            if (!DataRepository.dataItemGlobal().getStartFromKeyguard() || i == activityBase.hashCode()) {
                ThumbnailUpdater thumbnailUpdater = activityBase.getThumbnailUpdater();
                if (!(thumbnailUpdater == null || thumbnailUpdater.getThumbnail() == thumbnail)) {
                    thumbnailUpdater.setThumbnail(thumbnail, false, false);
                    Log.d(TAG, "inconsistent thumbnail");
                }
                this.mHandler.removeCallbacksAndMessages(null);
                this.mInLoading = false;
                if (this.mThumbnailProgress.getVisibility() != 8) {
                    this.mThumbnailProgress.setVisibility(8);
                }
                if (!this.mIsIntentAction) {
                    if (thumbnail == null) {
                        this.mThumbnailImage.setImageDrawable(null);
                        return;
                    }
                    RoundedBitmapDrawable create = RoundedBitmapDrawableFactory.create(getResources(), thumbnail.getBitmap());
                    create.getPaint().setAntiAlias(true);
                    create.setCircular(true);
                    this.mThumbnailImage.setImageDrawable(create);
                    if (z && !this.mVideoRecordingStarted) {
                        ViewCompat.setAlpha(this.mThumbnailImageLayout, 0.3f);
                        ViewCompat.setScaleX(this.mThumbnailImageLayout, 1.3f);
                        ViewCompat.setScaleY(this.mThumbnailImageLayout, 1.3f);
                        ViewCompat.animate(this.mThumbnailImageLayout).alpha(1.0f).scaleX(1.0f).scaleY(1.0f).setDuration(80).start();
                    }
                }
            }
        }
    }
}
