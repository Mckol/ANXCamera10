package com.android.camera.fragment;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.OvershootInterpolator;
import android.view.animation.PathInterpolator;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.StringRes;
import androidx.core.view.ViewCompat;
import b.c.a.c;
import com.android.camera.ActivityBase;
import com.android.camera.Camera;
import com.android.camera.CameraSettings;
import com.android.camera.HybridZoomingSystem;
import com.android.camera.R;
import com.android.camera.Util;
import com.android.camera.animation.FolmeUtils;
import com.android.camera.animation.FragmentAnimationFactory;
import com.android.camera.animation.type.AlphaInOnSubscribe;
import com.android.camera.animation.type.AlphaOutOnSubscribe;
import com.android.camera.animation.type.TranslateYOnSubscribe;
import com.android.camera.constant.ColorConstant;
import com.android.camera.constant.DurationConstant;
import com.android.camera.customization.TintColor;
import com.android.camera.data.DataRepository;
import com.android.camera.data.data.runing.ComponentRunningFastMotion;
import com.android.camera.data.data.runing.ComponentRunningShine;
import com.android.camera.features.mimoji2.widget.helper.MimojiStatusManager2;
import com.android.camera.log.Log;
import com.android.camera.protocol.ModeCoordinatorImpl;
import com.android.camera.protocol.ModeProtocol;
import com.android.camera.statistic.CameraStatUtils;
import com.android.camera.statistic.MistatsConstants;
import com.android.camera.statistic.MistatsWrapper;
import com.android.camera.timerburst.TimerBurstController;
import com.android.camera.ui.ColorImageView;
import io.reactivex.Completable;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.HashMap;
import java.util.List;
import miui.view.animation.BackEaseOutInterpolator;

public class FragmentBottomPopupTips extends BaseFragment implements View.OnClickListener, ModeProtocol.BottomPopupTips, ModeProtocol.HandleBackTrace {
    private static final int ANIM_DELAY_SHOW = 3;
    private static final int ANIM_DIRECT_HIDE = 1;
    private static final int ANIM_DIRECT_SHOW = 0;
    private static final int ANIM_HIDE = 4;
    private static final int ANIM_SHOW = 2;
    private static final int CALL_TYPE_NOTIFY = 1;
    private static final int CALL_TYPE_PROVIDE = 0;
    private static final int CENTER_TIP_IMAGE_SPEED = 33;
    public static final int FRAGMENT_INFO = 65529;
    private static final int LEFT_TIP_IMAGE_AI_WATERMARK = 23;
    private static final int LEFT_TIP_IMAGE_CLOSE = 20;
    private static final int LEFT_TIP_IMAGE_FAST_MOTION = 25;
    private static final int LEFT_TIP_IMAGE_KALEIDOSCOPE = 22;
    private static final int LEFT_TIP_IMAGE_LIGHTING = 19;
    private static final int LEFT_TIP_IMAGE_STICKER = 18;
    private static final int LEFT_TIP_IMAGE_SUPER_MOON = 32;
    private static final int LEFT_TIP_IMAGE_ULTRA_WIDE = 21;
    private static final int LEFT_TIP_IMAGE_VIDEO_BEAUTY = 24;
    private static final long MAX_RED_DOT_TIME = 86400000;
    private static final int MSG_HIDE_TIP = 1;
    private static final String TAG = "FragmentBottomPopupTips";
    private static final int TIP_ID_CARD = 4;
    private static final int TIP_IMAGE_INVALID = -1;
    private static final int TIP_IMAGE_STICKER = 2;
    private static final int TIP_MIMOJI = 34;
    private static final int TIP_SHINE = 3;
    private static final int TIP_SWITCH_CAMERA = 36;
    private boolean INIT_TAG = false;
    private int mBottomTipHeight;
    private View mCenterRedDot;
    private ImageView mCenterTipImage;
    private int mCloseType = 0;
    private String mCurrentTipMessage;
    private int mCurrentTipType;
    private Handler mHandler = new Handler() {
        /* class com.android.camera.fragment.FragmentBottomPopupTips.AnonymousClass1 */

        public void handleMessage(Message message) {
            ModeProtocol.CameraModuleSpecial cameraModuleSpecial;
            if (message.what == 1) {
                FragmentBottomPopupTips.this.mTipMessage.setVisibility(8);
                if (FragmentBottomPopupTips.this.mCurrentMode == 163 && (cameraModuleSpecial = (ModeProtocol.CameraModuleSpecial) ModeCoordinatorImpl.getInstance().getAttachProtocol(195)) != null) {
                    cameraModuleSpecial.showOrHideChip(true);
                }
                if (FragmentBottomPopupTips.this.mLastTipType == 6 && FragmentBottomPopupTips.this.mCurrentTipType != 8 && !FragmentBottomPopupTips.this.isPortraitHintVisible()) {
                    FragmentBottomPopupTips fragmentBottomPopupTips = FragmentBottomPopupTips.this;
                    fragmentBottomPopupTips.showTips(fragmentBottomPopupTips.mLastTipType, FragmentBottomPopupTips.this.mLastTipMessage, 4);
                } else if (FragmentBottomPopupTips.this.mLastTipType == 10 && CameraSettings.isEyeLightOpen()) {
                    FragmentBottomPopupTips fragmentBottomPopupTips2 = FragmentBottomPopupTips.this;
                    fragmentBottomPopupTips2.showTips(fragmentBottomPopupTips2.mLastTipType, FragmentBottomPopupTips.this.mLastTipMessage, 4);
                } else if ((FragmentBottomPopupTips.this.mLastTipType != 18 || !CameraSettings.isMacroModeEnabled(FragmentBottomPopupTips.this.mCurrentMode)) && (FragmentBottomPopupTips.this.mLastTipType != 8 || !CameraSettings.isMacroModeEnabled(FragmentBottomPopupTips.this.mCurrentMode))) {
                    FragmentBottomPopupTips.this.updateLyingDirectHint(false, true);
                }
                FragmentBottomPopupTips.this.mLastTipType = 4;
            }
        }
    };
    private boolean mIsShowLeftImageIntro;
    private boolean mIsShowLyingDirectHint;
    private String mLastTipMessage;
    private int mLastTipType;
    private FrameLayout mLeftImageIntro;
    private AnimatorSet mLeftImageIntroAnimator;
    private TextView mLeftImageIntroContent;
    private int mLeftImageIntroRadius;
    private int mLeftImageIntroWidth;
    private ImageView mLeftTipExtraImage;
    private ImageView mLeftTipImage;
    private TextView mLyingDirectHint;
    private LinearLayout mNearRangeModeButton;
    private boolean mNeedShowIDCardTip;
    private View mPortraitSuccessHint;
    private ImageView mQrCodeButton;
    private ImageView mRightTipExtraImage;
    private ImageView mRightTipImage;
    private View mRootView;
    private ViewGroup mSpeedTipImage;
    private ImageView mTipImage;
    private TextView mTipMessage;

    @Retention(RetentionPolicy.SOURCE)
    @interface TipImageType {
    }

    private void adjustViewBackground(int i) {
        if (DataRepository.dataItemRunning().getUiStyle() == 1) {
        }
    }

    private void clickAIWatermarkListEnter() {
        ModeProtocol.WatermarkProtocol watermarkProtocol = (ModeProtocol.WatermarkProtocol) ModeCoordinatorImpl.getInstance().getAttachProtocol(253);
        if (watermarkProtocol == null || !watermarkProtocol.isWatermarkPanelShow()) {
            hideAllTipImage();
            showAIWatermark(this.mCurrentMode);
        }
    }

    private void closeFilter() {
        ModeProtocol.ConfigChanges configChanges = (ModeProtocol.ConfigChanges) ModeCoordinatorImpl.getInstance().getAttachProtocol(164);
        if (configChanges != null) {
            configChanges.showOrHideFilter();
        }
    }

    private void closeLight() {
        ModeProtocol.ConfigChanges configChanges = (ModeProtocol.ConfigChanges) ModeCoordinatorImpl.getInstance().getAttachProtocol(164);
        if (configChanges != null) {
            configChanges.showOrHideLighting(false);
        }
        updateLeftTipImage();
    }

    private boolean currentIsIDCardShow() {
        ImageView imageView = this.mTipImage;
        return (imageView == null || imageView.getTag() == null || ((Integer) this.mTipImage.getTag()).intValue() != 4) ? false : true;
    }

    private int getLeftImageIntroWide() {
        this.mLeftImageIntroContent.measure(0, 0);
        int measuredWidth = this.mLeftImageIntroContent.getMeasuredWidth();
        int dimensionPixelSize = getResources().getDimensionPixelSize(R.dimen.popup_tip_beauty_intro_left_padding);
        return measuredWidth + dimensionPixelSize + getResources().getDimensionPixelSize(R.dimen.popup_tip_beauty_intro_right_padding) + ((this.mLeftImageIntroRadius - dimensionPixelSize) * 2);
    }

    private int getMarginEnd() {
        return DataRepository.dataItemFeature().Gj() ? getResources().getDimensionPixelSize(R.dimen.popup_indicator_button_extra_margin_end_support_idcard) : getResources().getDimensionPixelSize(R.dimen.popup_indicator_button_extra_margin_end);
    }

    private int getTipBottomMargin(int i) {
        int dimensionPixelSize;
        int i2;
        int dimensionPixelSize2;
        ImageView imageView;
        ModeProtocol.DualController dualController = (ModeProtocol.DualController) ModeCoordinatorImpl.getInstance().getAttachProtocol(182);
        ModeProtocol.ManuallyAdjust manuallyAdjust = (ModeProtocol.ManuallyAdjust) ModeCoordinatorImpl.getInstance().getAttachProtocol(181);
        ModeProtocol.MiBeautyProtocol miBeautyProtocol = (ModeProtocol.MiBeautyProtocol) ModeCoordinatorImpl.getInstance().getAttachProtocol(194);
        int dimensionPixelSize3 = getResources().getDimensionPixelSize(R.dimen.beauty_extra_tip_bottom_margin);
        int i3 = this.mCurrentMode;
        if (i3 == 165) {
            if (!CameraSettings.isUltraWideConfigOpen(i3) || (((imageView = this.mCenterTipImage) == null || imageView.getVisibility() == 0) && !HybridZoomingSystem.IS_3_OR_MORE_SAT)) {
                return getResources().getDimensionPixelSize(R.dimen.tips_margin_bottom_normal) + Util.getSquareBottomCoverHeight();
            }
            dimensionPixelSize = getResources().getDimensionPixelSize(R.dimen.manually_indicator_layout_height) / 2;
            i2 = i / 2;
        } else if (manuallyAdjust != null && manuallyAdjust.visibleHeight() > 0) {
            return manuallyAdjust.visibleHeight();
        } else {
            if (this.mCenterTipImage.getVisibility() == 0) {
                if (miBeautyProtocol == null || !miBeautyProtocol.isBeautyPanelShow()) {
                    return this.mTipImage.getHeight();
                }
                dimensionPixelSize2 = getResources().getDimensionPixelSize(R.dimen.beauty_fragment_height);
            } else if (dualController != null && dualController.isButtonVisible()) {
                return dualController.visibleHeight();
            } else {
                ModeProtocol.MakeupProtocol makeupProtocol = (ModeProtocol.MakeupProtocol) ModeCoordinatorImpl.getInstance().getAttachProtocol(180);
                ModeProtocol.ActionProcessing actionProcessing = (ModeProtocol.ActionProcessing) ModeCoordinatorImpl.getInstance().getAttachProtocol(162);
                if (makeupProtocol != null && makeupProtocol.isSeekBarVisible()) {
                    return getResources().getDimensionPixelSize(R.dimen.beautycamera_popup_fragment_height) + getResources().getDimensionPixelSize(R.dimen.beauty_fragment_height);
                }
                if (miBeautyProtocol != null && miBeautyProtocol.isBeautyPanelShow()) {
                    dimensionPixelSize2 = getResources().getDimensionPixelSize(R.dimen.beauty_fragment_height);
                } else if (actionProcessing == null || !actionProcessing.isShowLightingView()) {
                    dimensionPixelSize = getResources().getDimensionPixelSize(R.dimen.manually_indicator_layout_height) / 2;
                    i2 = i / 2;
                } else {
                    dimensionPixelSize2 = getResources().getDimensionPixelSize(R.dimen.beauty_fragment_height);
                }
            }
            return dimensionPixelSize2 + dimensionPixelSize3;
        }
        return dimensionPixelSize - i2;
    }

    private void hideAllTipImage() {
        hideTipImage();
        hideLeftTipImage();
        hideRightTipImage();
        directHideLeftImageIntro();
        hideSpeedTipImage();
        hideTip(this.mTipMessage);
        hideZoomTipImage(this.mCurrentMode);
        hideCenterTipImage();
    }

    private boolean hideTip(View view) {
        if (view.getVisibility() == 8) {
            return false;
        }
        view.setVisibility(8);
        return true;
    }

    private void hideZoomTipImage(int i) {
        if (i != 165) {
            if (i != 166) {
                if (!(i == 169 || i == 183)) {
                    if (!(i == 188 || i == 173)) {
                        if (i != 174) {
                            switch (i) {
                                case 161:
                                case 162:
                                    break;
                                case 163:
                                    break;
                                default:
                                    return;
                            }
                        }
                    }
                }
            } else if (!DataRepository.dataItemFeature().Zj()) {
                return;
            }
            if (!HybridZoomingSystem.IS_3_OR_MORE_SAT) {
                return;
            }
        }
        ModeProtocol.DualController dualController = (ModeProtocol.DualController) ModeCoordinatorImpl.getInstance().getAttachProtocol(182);
        if (dualController != null) {
            dualController.hideZoomButton();
            ModeProtocol.TopAlert topAlert = (ModeProtocol.TopAlert) ModeCoordinatorImpl.getInstance().getAttachProtocol(172);
            if (topAlert != null) {
                topAlert.alertUpdateValue(0, null);
            }
        }
    }

    private boolean ignoreClick() {
        if (!isEnableClick()) {
            return true;
        }
        ModeProtocol.CameraAction cameraAction = (ModeProtocol.CameraAction) ModeCoordinatorImpl.getInstance().getAttachProtocol(161);
        if (cameraAction == null || !cameraAction.isDoingAction()) {
            return CameraSettings.isFrontCamera() && ((Camera) getContext()).isScreenSlideOff();
        }
        return true;
    }

    private boolean isPortraitSuccessHintVisible() {
        return this.mPortraitSuccessHint.getVisibility() == 0;
    }

    /* JADX INFO: Can't fix incorrect switch cases order, some code will duplicate */
    private void onLeftImageClick(View view) {
        int intValue = ((Integer) view.getTag()).intValue();
        ModeProtocol.ConfigChanges configChanges = (ModeProtocol.ConfigChanges) ModeCoordinatorImpl.getInstance().getAttachProtocol(164);
        switch (intValue) {
            case 19:
                if (configChanges != null) {
                    hideAllTipImage();
                    configChanges.onConfigChanged(203);
                    return;
                }
                return;
            case 20:
                int i = this.mCloseType;
                if (i == 1) {
                    closeFilter();
                    return;
                } else if (i == 2) {
                    closeLight();
                    return;
                } else {
                    return;
                }
            case 21:
                if (configChanges != null && !HybridZoomingSystem.IS_3_OR_MORE_SAT) {
                    configChanges.onConfigChanged(205);
                    return;
                }
                return;
            case 22:
                hideAllTipImage();
                showKaleidoscope();
                return;
            case 23:
                break;
            case 24:
                showOrHideVideoBeautyPanel();
                return;
            case 25:
                if (DataRepository.dataItemFeature().uj()) {
                    showFastMotionPanel();
                    return;
                }
                return;
            default:
                switch (intValue) {
                    case 32:
                        hideAllTipImage();
                        showSuperMoonEffect(this.mCurrentMode);
                        return;
                    case 33:
                        hideAllTipImage();
                        CameraStatUtils.trackMiLiveClick(MistatsConstants.MiLive.VALUE_MI_LIVE_CLICK_SPEED);
                        showLiveSpeed();
                        break;
                    case 34:
                        if (this.mCurrentMode != 184) {
                            return;
                        }
                        if (DataRepository.dataItemLive().getMimojiStatusManager2().getMimojiRecordState() == 1) {
                            showMimojiPanel(3);
                            return;
                        } else {
                            showMimojiPanel(2);
                            return;
                        }
                    default:
                        return;
                }
        }
        clickAIWatermarkListEnter();
    }

    private void reConfigBottomTipOfMimoji() {
        if (this.mCurrentMode == 184) {
            updateMimojiBottomTipImage();
        }
    }

    private void reIntTipViewMarginBottom(View view, int i) {
        ViewGroup.MarginLayoutParams marginLayoutParams = (ViewGroup.MarginLayoutParams) view.getLayoutParams();
        int tipBottomMargin = getTipBottomMargin(i);
        if (marginLayoutParams.bottomMargin != tipBottomMargin) {
            marginLayoutParams.bottomMargin = tipBottomMargin;
            view.setLayoutParams(marginLayoutParams);
        }
    }

    /* access modifiers changed from: private */
    /* access modifiers changed from: public */
    private void setBeautyIntroButtonWidth(View view, int i) {
        if (view != null) {
            ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
            layoutParams.width = i;
            view.setLayoutParams(layoutParams);
        }
    }

    private void showAIWatermark(int i) {
        ModeProtocol.ConfigChanges configChanges;
        if ((i == 163 || i == 205) && (configChanges = (ModeProtocol.ConfigChanges) ModeCoordinatorImpl.getInstance().getAttachProtocol(164)) != null) {
            configChanges.showOrHideAIWatermark();
        }
    }

    private void showBeauty(int i) {
        ModeProtocol.ConfigChanges configChanges = (ModeProtocol.ConfigChanges) ModeCoordinatorImpl.getInstance().getAttachProtocol(164);
        if (configChanges != null) {
            configChanges.showOrHideShine();
        }
    }

    private void showKaleidoscope() {
    }

    private void showLiveSpeed() {
        ModeProtocol.BottomMenuProtocol bottomMenuProtocol = (ModeProtocol.BottomMenuProtocol) ModeCoordinatorImpl.getInstance().getAttachProtocol(197);
        if (bottomMenuProtocol != null) {
            bottomMenuProtocol.onSwitchLiveActionMenu(165);
        }
        ModeProtocol.BaseDelegate baseDelegate = (ModeProtocol.BaseDelegate) ModeCoordinatorImpl.getInstance().getAttachProtocol(160);
        if (baseDelegate != null) {
            baseDelegate.delegateEvent(13);
        }
    }

    private void showLiveSticker() {
        CameraStatUtils.trackLiveClick(MistatsConstants.Live.VALUE_LIVE_STICKER);
        ModeProtocol.BottomMenuProtocol bottomMenuProtocol = (ModeProtocol.BottomMenuProtocol) ModeCoordinatorImpl.getInstance().getAttachProtocol(197);
        if (bottomMenuProtocol != null) {
            bottomMenuProtocol.onSwitchLiveActionMenu(164);
        }
        ModeProtocol.BaseDelegate baseDelegate = (ModeProtocol.BaseDelegate) ModeCoordinatorImpl.getInstance().getAttachProtocol(160);
        if (baseDelegate != null) {
            baseDelegate.delegateEvent(12);
        }
        ModeProtocol.BottomPopupTips bottomPopupTips = (ModeProtocol.BottomPopupTips) ModeCoordinatorImpl.getInstance().getAttachProtocol(175);
        if (bottomPopupTips != null) {
            bottomPopupTips.directlyHideTips();
        }
    }

    private void showSuperMoonEffect(int i) {
        Log.d(TAG, "showSuperMoonEffect E");
        if (i == 188) {
            ModeProtocol.ConfigChanges configChanges = (ModeProtocol.ConfigChanges) ModeCoordinatorImpl.getInstance().getAttachProtocol(164);
            if (configChanges != null) {
                Log.d(TAG, "prepare to call showOrHideAIWatermark");
                configChanges.showOrHideAIWatermark();
            }
            Log.d(TAG, "showSuperMoonEffect X");
        }
    }

    private void startLeftImageIntroAnim(int i) {
        if (i != 1) {
            directShowOrHideLeftTipImage(false);
            this.mLeftImageIntro.setVisibility(0);
            if (this.mLeftImageIntroAnimator == null) {
                ValueAnimator ofInt = ValueAnimator.ofInt(this.mLeftImageIntroWidth, this.mLeftImageIntroRadius * 2);
                ofInt.setDuration(300L);
                ObjectAnimator ofFloat = ObjectAnimator.ofFloat(this.mLeftImageIntroContent, "alpha", 1.0f, 0.0f);
                ofFloat.setDuration(250L);
                ofInt.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                    /* class com.android.camera.fragment.FragmentBottomPopupTips.AnonymousClass6 */

                    public void onAnimationUpdate(ValueAnimator valueAnimator) {
                        FragmentBottomPopupTips fragmentBottomPopupTips = FragmentBottomPopupTips.this;
                        fragmentBottomPopupTips.setBeautyIntroButtonWidth(fragmentBottomPopupTips.mLeftImageIntro, ((Integer) valueAnimator.getAnimatedValue()).intValue());
                    }
                });
                this.mLeftImageIntroAnimator = new AnimatorSet();
                this.mLeftImageIntroAnimator.playTogether(ofInt, ofFloat);
                this.mLeftImageIntroAnimator.setInterpolator(new PathInterpolator(0.25f, 0.1f, 0.25f, 0.1f));
                this.mLeftImageIntroAnimator.setStartDelay(2000);
                this.mLeftImageIntroAnimator.addListener(new AnimatorListenerAdapter() {
                    /* class com.android.camera.fragment.FragmentBottomPopupTips.AnonymousClass7 */
                    private boolean cancelled;

                    public void onAnimationCancel(Animator animator) {
                        this.cancelled = true;
                    }

                    public void onAnimationEnd(Animator animator) {
                        if (FragmentBottomPopupTips.this.canProvide() && !this.cancelled) {
                            FragmentBottomPopupTips.this.directHideLeftImageIntro();
                            FragmentBottomPopupTips.this.updateLeftTipImage();
                        }
                    }

                    public void onAnimationStart(Animator animator) {
                        this.cancelled = false;
                    }
                });
            } else {
                this.mLeftImageIntro.setAlpha(1.0f);
                this.mLeftImageIntroContent.clearAnimation();
                this.mLeftImageIntroAnimator.cancel();
            }
            this.mLeftImageIntroAnimator.start();
            CameraSettings.addPopupUltraWideIntroShowTimes();
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:12:0x0049, code lost:
        if (com.android.camera.data.DataRepository.dataItemLive().getMimojiStatusManager().getMimojiPannelState() == false) goto L_0x004b;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:63:0x0136, code lost:
        if (r6 != false) goto L_0x0154;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:8:0x002c, code lost:
        if (r4.getMimojiPanelState() == 0) goto L_0x004b;
     */
    /* JADX WARNING: Removed duplicated region for block: B:77:0x0156  */
    /* JADX WARNING: Removed duplicated region for block: B:85:0x019c  */
    private void updateCenterTipImage(int i, int i2, List<Completable> list) {
        int i3;
        boolean z;
        boolean z2;
        if (i != 174) {
            if (i != 177) {
                if (i == 184) {
                    MimojiStatusManager2 mimojiStatusManager2 = DataRepository.dataItemLive().getMimojiStatusManager2();
                    if (mimojiStatusManager2.isInMimojiPreview()) {
                    }
                }
                i3 = -1;
            } else {
                if (DataRepository.dataItemLive().getMimojiStatusManager().IsInMimojiPreview()) {
                }
                i3 = -1;
            }
            i3 = 34;
        } else {
            i3 = 18;
        }
        FrameLayout frameLayout = (FrameLayout) this.mRootView.findViewById(R.id.popup_center_tip_layout);
        FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) frameLayout.getLayoutParams();
        int i4 = R.drawable.ic_vector_live_sticker;
        boolean z3 = false;
        if (i3 != -1) {
            if (i3 == 18) {
                z2 = !"".equals(CameraSettings.getCurrentLiveSticker());
                if (this.mCenterRedDot != null) {
                    boolean tTLiveStickerNeedRedDot = CameraSettings.getTTLiveStickerNeedRedDot();
                    long liveStickerRedDotTime = CameraSettings.getLiveStickerRedDotTime();
                    long currentTimeMillis = System.currentTimeMillis();
                    if ((liveStickerRedDotTime <= 0 || currentTimeMillis - liveStickerRedDotTime < 86400000) && tTLiveStickerNeedRedDot) {
                        this.mCenterRedDot.setVisibility(0);
                    }
                }
                if (!HybridZoomingSystem.IS_3_OR_MORE_SAT) {
                    layoutParams.gravity = 81;
                } else {
                    layoutParams.gravity = 8388691;
                }
            } else if (i3 != 34) {
                z = true;
                z2 = false;
                i4 = 0;
            } else {
                String currentMimojiState = this.mCurrentMode == 184 ? DataRepository.dataItemLive().getMimojiStatusManager2().getCurrentMimojiState() : DataRepository.dataItemLive().getMimojiStatusManager().getCurrentMimojiState();
                z2 = !"add_state".equals(currentMimojiState) && !"close_state".equals(currentMimojiState);
                layoutParams.gravity = 81;
            }
            z = true;
        } else {
            View view = this.mCenterRedDot;
            if (view != null) {
                view.setVisibility(8);
            }
            z2 = false;
            z = false;
            i4 = 0;
        }
        if (i4 > 0) {
            frameLayout.requestLayout();
            ((ColorImageView) this.mCenterTipImage).setColor(z2 ? TintColor.tintColor() : ColorConstant.IMAGE_NORMAL_COLOR);
            this.mCenterTipImage.setImageResource(i4);
        }
        updateImageBgColor(i, this.mCenterTipImage);
        if (this.mCenterTipImage.getTag() == null || ((Integer) this.mCenterTipImage.getTag()).intValue() != i3) {
            this.mCenterTipImage.setTag(Integer.valueOf(i3));
            if (list != null) {
                if (z) {
                    if (i2 != 165) {
                        z3 = true;
                    } else if (!c.isSupportedOpticalZoom()) {
                        z3 = true;
                    }
                } else if (!(i2 == 165 || this.mCurrentMode == 165)) {
                    z3 = true;
                }
                if (z3) {
                    AlphaInOnSubscribe.directSetResult(this.mCenterTipImage);
                    return;
                } else if (z3) {
                    AlphaOutOnSubscribe.directSetResult(this.mCenterTipImage);
                    return;
                } else if (z3) {
                    list.add(Completable.create(new AlphaInOnSubscribe(this.mCenterTipImage)));
                    return;
                } else if (z3) {
                    list.add(Completable.create(new AlphaInOnSubscribe(this.mCenterTipImage).setStartDelayTime(150).setDurationTime(150)));
                    return;
                } else if (z3) {
                    list.add(Completable.create(new AlphaOutOnSubscribe(this.mCenterTipImage)));
                    return;
                } else {
                    return;
                }
            }
            z3 = true;
            if (z3) {
            }
        }
    }

    private void updateImageBgColor(int i, View view) {
        if (i != 165) {
            int i2 = R.drawable.bg_popup_indicator_no_stroke;
            if (i == 184) {
                if (CameraSettings.isGifOn()) {
                    i2 = R.drawable.bg_popup_indicator_gif;
                }
                view.setBackgroundResource(i2);
            } else if (i != 188) {
                view.setBackgroundResource(R.drawable.bg_popup_indicator_no_stroke);
            } else {
                view.setBackgroundResource(R.drawable.bg_popup_indicator_super_moon);
            }
        } else {
            view.setBackgroundResource(R.drawable.square_module_bg_popup_indicator_no_stroke);
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:20:0x0046  */
    /* JADX WARNING: Removed duplicated region for block: B:29:0x0079  */
    /* JADX WARNING: Removed duplicated region for block: B:38:0x0097  */
    /* JADX WARNING: Removed duplicated region for block: B:39:0x009c  */
    /* JADX WARNING: Removed duplicated region for block: B:42:0x00a4 A[RETURN] */
    /* JADX WARNING: Removed duplicated region for block: B:43:0x00a5  */
    private void updateLeftTipExtraImage(int i, int i2, List<Completable> list) {
        int i3;
        boolean z;
        boolean z2;
        boolean z3;
        boolean z4;
        ImageView imageView = this.mLeftTipExtraImage;
        if (i == 184) {
            MimojiStatusManager2 mimojiStatusManager2 = DataRepository.dataItemLive().getMimojiStatusManager2();
            if (mimojiStatusManager2.getMimojiPanelState() == 0 && !mimojiStatusManager2.isInMimojiCreate() && !mimojiStatusManager2.isInMimojiPreviewPlay() && mimojiStatusManager2.getCurrentMimojiInfo() != null && !CameraSettings.isGifOn() && CameraSettings.isFrontCamera() && mimojiStatusManager2.getMimojiRecordState() == 1) {
                i3 = 34;
                int i4 = R.drawable.ic_vector_mimoji_change_bg;
                boolean z5 = false;
                if (i3 != -1) {
                    z = true;
                    z3 = false;
                    z2 = false;
                    i4 = 0;
                } else if (i3 == 22) {
                    i4 = R.drawable.ic_vector_kaleidoscope;
                    z3 = DataRepository.dataItemRunning().getComponentRunningKaleidoscope().isSwitchOn();
                    z2 = true;
                    z = false;
                } else if (i3 != 34) {
                    z2 = true;
                    z = true;
                    z3 = false;
                    i4 = 0;
                } else if (DataRepository.dataItemLive().getMimojiStatusManager2().getCurrentMimojiBgInfo() != null) {
                    z3 = true;
                    z2 = true;
                    z = true;
                } else {
                    z2 = true;
                    z = true;
                    z3 = false;
                }
                z4 = (imageView.getTag() == null && ((Integer) imageView.getTag()).intValue() == i3) ? false : true;
                ((ColorImageView) imageView).setColorAndRefresh(!z3 ? TintColor.tintColor() : ColorConstant.IMAGE_NORMAL_COLOR);
                if (!z4) {
                    if (i4 > 0) {
                        imageView.setImageResource(i4);
                    }
                    if (!z2 || !z) {
                        ViewCompat.setRotation(imageView, 0.0f);
                    } else {
                        ViewCompat.setRotation(imageView, (float) this.mDegree);
                    }
                    imageView.setTag(Integer.valueOf(i3));
                    if (list == null) {
                        if (!z2) {
                            z5 = true;
                        }
                    } else if (!z2) {
                        z5 = true;
                    } else if (i2 != 163) {
                        z5 = true;
                    } else if (!c.isSupportedOpticalZoom()) {
                        z5 = true;
                    }
                    if (!z5) {
                        AlphaInOnSubscribe.directSetResult(imageView);
                        return;
                    } else if (z5) {
                        AlphaOutOnSubscribe.directSetResult(imageView);
                        return;
                    } else if (z5) {
                        list.add(Completable.create(new AlphaInOnSubscribe(imageView)));
                        return;
                    } else if (z5) {
                        list.add(Completable.create(new AlphaInOnSubscribe(imageView).setStartDelayTime(150).setDurationTime(150)));
                        return;
                    } else if (z5) {
                        list.add(Completable.create(new AlphaOutOnSubscribe(imageView)));
                        return;
                    } else {
                        return;
                    }
                } else {
                    return;
                }
            }
        }
        i3 = -1;
        int i42 = R.drawable.ic_vector_mimoji_change_bg;
        boolean z52 = false;
        if (i3 != -1) {
        }
        if (imageView.getTag() == null) {
        }
        ((ColorImageView) imageView).setColorAndRefresh(!z3 ? TintColor.tintColor() : ColorConstant.IMAGE_NORMAL_COLOR);
        if (!z4) {
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:112:0x0178, code lost:
        if (r4 != false) goto L_0x00ec;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:128:0x01b5, code lost:
        if (r6.getCurrentMimojiBgInfo() == null) goto L_0x0259;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:135:0x01d7, code lost:
        if (com.android.camera.CameraSettings.getCurrentLiveSpeed().equals(java.lang.String.valueOf(2)) != false) goto L_0x0259;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:137:0x01ea, code lost:
        if (com.android.camera.data.DataRepository.dataItemRunning().getComponentRunningAIWatermark().isSwitchOn() != false) goto L_0x0256;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:142:0x020c, code lost:
        if (com.android.camera.data.DataRepository.dataItemRunning().getComponentRunningKaleidoscope().isSwitchOn() != false) goto L_0x0256;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:156:0x0254, code lost:
        if ("".equals(r4) == false) goto L_0x0256;
     */
    /* JADX WARNING: Removed duplicated region for block: B:115:0x0185  */
    /* JADX WARNING: Removed duplicated region for block: B:162:0x0263  */
    /* JADX WARNING: Removed duplicated region for block: B:165:0x026e  */
    /* JADX WARNING: Removed duplicated region for block: B:166:0x0273  */
    /* JADX WARNING: Removed duplicated region for block: B:174:0x0297  */
    /* JADX WARNING: Removed duplicated region for block: B:178:0x02a8  */
    /* JADX WARNING: Removed duplicated region for block: B:183:0x02c6  */
    /* JADX WARNING: Removed duplicated region for block: B:186:0x02cc  */
    /* JADX WARNING: Removed duplicated region for block: B:199:0x02ee  */
    /* JADX WARNING: Removed duplicated region for block: B:207:0x0334  */
    private void updateLeftTipImage(int i, int i2, int i3, List<Completable> list) {
        int i4;
        boolean z;
        int i5;
        boolean z2;
        boolean z3;
        int i6;
        int i7;
        ModeProtocol.MiBeautyProtocol miBeautyProtocol;
        int currentCameraId = DataRepository.dataItemGlobal().getCurrentCameraId();
        boolean isNormalIntent = DataRepository.dataItemGlobal().isNormalIntent();
        boolean z4 = DataRepository.dataItemFeature().isSupportUltraWide() && !HybridZoomingSystem.IS_3_OR_MORE_SAT;
        int i8 = 21;
        if (i2 != 165) {
            if (i2 != 169) {
                if (i2 == 171) {
                    if (isNormalIntent) {
                        i7 = (currentCameraId == 0 ? !DataRepository.dataItemFeature().Vl() : currentCameraId != 1 || !DataRepository.dataItemFeature().Wl()) ? -1 : 19;
                    }
                    i8 = -1;
                    i4 = R.drawable.ic_vector_mimoji_change_bg;
                    if (i8 != -1) {
                    }
                    z2 = false;
                    i5 = 0;
                    i4 = 0;
                    z = false;
                    if (i4 > 0) {
                    }
                    ((ColorImageView) this.mLeftTipImage).setColor(z2 ? TintColor.tintColor() : ColorConstant.IMAGE_NORMAL_COLOR);
                    this.mLeftTipImage.setContentDescription(getString(i5));
                    updateImageBgColor(i2, this.mLeftTipImage);
                    if (this.mLeftTipImage.getTag() != null) {
                    }
                    if (z) {
                    }
                    this.mLeftTipImage.setTag(Integer.valueOf(i8));
                    if (list != null) {
                    }
                    z3 = true;
                    if (!z3) {
                    }
                } else if (i2 == 174) {
                    if (z4 && currentCameraId == 0) {
                        if (this.mIsShowLeftImageIntro) {
                            startLeftImageIntroAnim(i);
                            return;
                        }
                        i4 = R.drawable.ic_vector_mimoji_change_bg;
                        if (i8 != -1) {
                        }
                        z2 = false;
                        i5 = 0;
                        i4 = 0;
                        z = false;
                        if (i4 > 0) {
                        }
                        ((ColorImageView) this.mLeftTipImage).setColor(z2 ? TintColor.tintColor() : ColorConstant.IMAGE_NORMAL_COLOR);
                        this.mLeftTipImage.setContentDescription(getString(i5));
                        updateImageBgColor(i2, this.mLeftTipImage);
                        if (this.mLeftTipImage.getTag() != null) {
                        }
                        if (z) {
                        }
                        this.mLeftTipImage.setTag(Integer.valueOf(i8));
                        if (list != null) {
                        }
                        z3 = true;
                        if (!z3) {
                        }
                    }
                    i8 = -1;
                    i4 = R.drawable.ic_vector_mimoji_change_bg;
                    if (i8 != -1) {
                    }
                    z2 = false;
                    i5 = 0;
                    i4 = 0;
                    z = false;
                    if (i4 > 0) {
                    }
                    ((ColorImageView) this.mLeftTipImage).setColor(z2 ? TintColor.tintColor() : ColorConstant.IMAGE_NORMAL_COLOR);
                    this.mLeftTipImage.setContentDescription(getString(i5));
                    updateImageBgColor(i2, this.mLeftTipImage);
                    if (this.mLeftTipImage.getTag() != null) {
                    }
                    if (z) {
                    }
                    this.mLeftTipImage.setTag(Integer.valueOf(i8));
                    if (list != null) {
                    }
                    z3 = true;
                    if (!z3) {
                    }
                } else if (i2 != 188) {
                    if (i2 != 205) {
                        if (i2 != 183) {
                            if (i2 != 184) {
                                switch (i2) {
                                    case 161:
                                        if (z4 && currentCameraId == 0) {
                                            if (this.mIsShowLeftImageIntro) {
                                                startLeftImageIntroAnim(i);
                                                return;
                                            }
                                            i4 = R.drawable.ic_vector_mimoji_change_bg;
                                            if (i8 != -1) {
                                                if (i8 == 18) {
                                                    String currentLiveSticker = CameraSettings.getCurrentLiveSticker();
                                                    i4 = R.drawable.ic_vector_live_sticker;
                                                    break;
                                                } else {
                                                    if (i8 != 19) {
                                                        switch (i8) {
                                                            case 21:
                                                                boolean isUltraWideConfigOpen = CameraSettings.isUltraWideConfigOpen(this.mCurrentMode);
                                                                i4 = isUltraWideConfigOpen ? R.drawable.icon_config_ultra_wide_on : R.drawable.icon_config_ultra_wide_off;
                                                                if (!isUltraWideConfigOpen) {
                                                                    i6 = R.string.accessibility_ultra_wide_off;
                                                                    break;
                                                                } else {
                                                                    i6 = R.string.accessibility_ultra_wide_on;
                                                                    break;
                                                                }
                                                            case 22:
                                                                i4 = R.drawable.ic_vector_kaleidoscope;
                                                                break;
                                                            case 23:
                                                                i4 = R.drawable.ic_ai_watermark_enter;
                                                                z = true;
                                                                z2 = false;
                                                                i5 = 0;
                                                                break;
                                                            case 24:
                                                                i4 = R.drawable.ic_vector_manually_indicator;
                                                                z = true;
                                                                z2 = false;
                                                                i5 = 0;
                                                                break;
                                                            case 25:
                                                                i4 = R.drawable.ic_vector_fastmotion_indicator;
                                                                z = true;
                                                                z2 = false;
                                                                i5 = 0;
                                                                break;
                                                            default:
                                                                switch (i8) {
                                                                    case 32:
                                                                        i4 = R.drawable.ic_super_moon_effect_load_list;
                                                                        break;
                                                                    case 33:
                                                                        i4 = R.drawable.ic_vector_live_speed;
                                                                        break;
                                                                    case 34:
                                                                        MimojiStatusManager2 mimojiStatusManager2 = DataRepository.dataItemLive().getMimojiStatusManager2();
                                                                        if (mimojiStatusManager2.getMimojiRecordState() != 0) {
                                                                            if (mimojiStatusManager2.getCurrentMimojiTimbreInfo() != null) {
                                                                                i4 = R.drawable.ic_vector_mimoji_change_timbre;
                                                                                break;
                                                                            } else {
                                                                                i4 = R.drawable.ic_vector_mimoji_change_timbre;
                                                                                z = true;
                                                                                z2 = false;
                                                                                i5 = 0;
                                                                                break;
                                                                            }
                                                                        } else if (mimojiStatusManager2.getCurrentMimojiInfo() != null && CameraSettings.isFrontCamera()) {
                                                                            break;
                                                                        } else {
                                                                            i8 = -1;
                                                                            break;
                                                                        }
                                                                        break;
                                                                    default:
                                                                        z = true;
                                                                        z2 = false;
                                                                        i5 = 0;
                                                                        i4 = 0;
                                                                        break;
                                                                }
                                                        }
                                                        if (i4 > 0) {
                                                            this.mLeftTipImage.setImageResource(i4);
                                                        }
                                                        ((ColorImageView) this.mLeftTipImage).setColor(z2 ? TintColor.tintColor() : ColorConstant.IMAGE_NORMAL_COLOR);
                                                        if (i5 > 0 && Util.isAccessible()) {
                                                            this.mLeftTipImage.setContentDescription(getString(i5));
                                                        }
                                                        updateImageBgColor(i2, this.mLeftTipImage);
                                                        if (this.mLeftTipImage.getTag() != null || ((Integer) this.mLeftTipImage.getTag()).intValue() != i8) {
                                                            if (z) {
                                                                if (i8 == 24) {
                                                                    ViewCompat.setRotation(this.mLeftTipImage, 0.0f);
                                                                } else {
                                                                    ViewCompat.setRotation(this.mLeftTipImage, (float) this.mDegree);
                                                                }
                                                            }
                                                            this.mLeftTipImage.setTag(Integer.valueOf(i8));
                                                            if (list != null) {
                                                                if (z) {
                                                                    boolean z5 = i3 == 165 ? c.isSupportedOpticalZoom() ? false : true : true;
                                                                    directHideLeftImageIntro();
                                                                    z3 = z5;
                                                                } else if (!(i3 == 165 || this.mCurrentMode == 165)) {
                                                                    z3 = true;
                                                                }
                                                                if (!z3) {
                                                                }
                                                            } else if (z) {
                                                                z3 = false;
                                                                if (!z3) {
                                                                    AlphaInOnSubscribe.directSetResult(this.mLeftTipImage);
                                                                    return;
                                                                } else if (z3) {
                                                                    AlphaOutOnSubscribe.directSetResult(this.mLeftTipImage);
                                                                    return;
                                                                } else if (z3) {
                                                                    list.add(Completable.create(new AlphaInOnSubscribe(this.mLeftTipImage)));
                                                                    return;
                                                                } else if (z3) {
                                                                    list.add(Completable.create(new AlphaInOnSubscribe(this.mLeftTipImage).setStartDelayTime(150).setDurationTime(150)));
                                                                    return;
                                                                } else if (z3) {
                                                                    list.add(Completable.create(new AlphaOutOnSubscribe(this.mLeftTipImage)));
                                                                    return;
                                                                } else {
                                                                    return;
                                                                }
                                                            }
                                                            z3 = true;
                                                            if (!z3) {
                                                            }
                                                        } else {
                                                            return;
                                                        }
                                                    } else {
                                                        i4 = R.drawable.ic_vector_portrait_lighting;
                                                        i6 = R.string.accessibility_lighting_panel_on;
                                                        if (DataRepository.dataItemRunning().getComponentRunningLighting().getComponentValue(171) != "0") {
                                                            i5 = R.string.accessibility_lighting_panel_on;
                                                            z2 = true;
                                                            z = true;
                                                            if (i4 > 0) {
                                                            }
                                                            ((ColorImageView) this.mLeftTipImage).setColor(z2 ? TintColor.tintColor() : ColorConstant.IMAGE_NORMAL_COLOR);
                                                            this.mLeftTipImage.setContentDescription(getString(i5));
                                                            updateImageBgColor(i2, this.mLeftTipImage);
                                                            if (this.mLeftTipImage.getTag() != null) {
                                                            }
                                                            if (z) {
                                                            }
                                                            this.mLeftTipImage.setTag(Integer.valueOf(i8));
                                                            if (list != null) {
                                                            }
                                                            z3 = true;
                                                            if (!z3) {
                                                            }
                                                        }
                                                    }
                                                    i5 = i6;
                                                    z = true;
                                                    z2 = false;
                                                    if (i4 > 0) {
                                                    }
                                                    ((ColorImageView) this.mLeftTipImage).setColor(z2 ? TintColor.tintColor() : ColorConstant.IMAGE_NORMAL_COLOR);
                                                    this.mLeftTipImage.setContentDescription(getString(i5));
                                                    updateImageBgColor(i2, this.mLeftTipImage);
                                                    if (this.mLeftTipImage.getTag() != null) {
                                                    }
                                                    if (z) {
                                                    }
                                                    this.mLeftTipImage.setTag(Integer.valueOf(i8));
                                                    if (list != null) {
                                                    }
                                                    z3 = true;
                                                    if (!z3) {
                                                    }
                                                }
                                                z2 = true;
                                                z = true;
                                                i5 = 0;
                                                if (i4 > 0) {
                                                }
                                                ((ColorImageView) this.mLeftTipImage).setColor(z2 ? TintColor.tintColor() : ColorConstant.IMAGE_NORMAL_COLOR);
                                                this.mLeftTipImage.setContentDescription(getString(i5));
                                                updateImageBgColor(i2, this.mLeftTipImage);
                                                if (this.mLeftTipImage.getTag() != null) {
                                                }
                                                if (z) {
                                                }
                                                this.mLeftTipImage.setTag(Integer.valueOf(i8));
                                                if (list != null) {
                                                }
                                                z3 = true;
                                                if (!z3) {
                                                }
                                            }
                                            z2 = false;
                                            i5 = 0;
                                            i4 = 0;
                                            z = false;
                                            if (i4 > 0) {
                                            }
                                            ((ColorImageView) this.mLeftTipImage).setColor(z2 ? TintColor.tintColor() : ColorConstant.IMAGE_NORMAL_COLOR);
                                            this.mLeftTipImage.setContentDescription(getString(i5));
                                            updateImageBgColor(i2, this.mLeftTipImage);
                                            if (this.mLeftTipImage.getTag() != null) {
                                            }
                                            if (z) {
                                            }
                                            this.mLeftTipImage.setTag(Integer.valueOf(i8));
                                            if (list != null) {
                                            }
                                            z3 = true;
                                            if (!z3) {
                                            }
                                        }
                                        break;
                                    case 162:
                                        if (!CameraSettings.isMacroModeEnabled(i2) && ((miBeautyProtocol = (ModeProtocol.MiBeautyProtocol) ModeCoordinatorImpl.getInstance().getAttachProtocol(194)) == null || !miBeautyProtocol.isBeautyPanelShow())) {
                                            if (currentCameraId != 0 || !isNormalIntent || !z4) {
                                                i8 = -1;
                                            } else if (!CameraSettings.isAutoZoomEnabled(i2) && !CameraSettings.isSuperEISEnabled(i2)) {
                                                if (this.mIsShowLeftImageIntro) {
                                                    startLeftImageIntroAnim(i);
                                                    return;
                                                }
                                            }
                                            ComponentRunningShine componentRunningShine = DataRepository.dataItemRunning().getComponentRunningShine();
                                            if ((!componentRunningShine.isTopBeautyEntry() && CameraSettings.isFaceBeautyOn(i2, null)) && !componentRunningShine.isTargetShow() && (miBeautyProtocol == null || !miBeautyProtocol.isBeautyPanelShow())) {
                                                i8 = 24;
                                            }
                                            i4 = R.drawable.ic_vector_mimoji_change_bg;
                                            if (i8 != -1) {
                                            }
                                            z2 = false;
                                            i5 = 0;
                                            i4 = 0;
                                            z = false;
                                            if (i4 > 0) {
                                            }
                                            ((ColorImageView) this.mLeftTipImage).setColor(z2 ? TintColor.tintColor() : ColorConstant.IMAGE_NORMAL_COLOR);
                                            this.mLeftTipImage.setContentDescription(getString(i5));
                                            updateImageBgColor(i2, this.mLeftTipImage);
                                            if (this.mLeftTipImage.getTag() != null) {
                                            }
                                            if (z) {
                                            }
                                            this.mLeftTipImage.setTag(Integer.valueOf(i8));
                                            if (list != null) {
                                            }
                                            z3 = true;
                                            if (!z3) {
                                            }
                                            break;
                                        }
                                        break;
                                }
                            } else {
                                MimojiStatusManager2 mimojiStatusManager22 = DataRepository.dataItemLive().getMimojiStatusManager2();
                                if (mimojiStatusManager22.getMimojiPanelState() == 0 && !CameraSettings.isGifOn() && !mimojiStatusManager22.isInMimojiCreate()) {
                                    i7 = 34;
                                }
                            }
                            i8 = -1;
                            i4 = R.drawable.ic_vector_mimoji_change_bg;
                            if (i8 != -1) {
                            }
                            z2 = false;
                            i5 = 0;
                            i4 = 0;
                            z = false;
                            if (i4 > 0) {
                            }
                            ((ColorImageView) this.mLeftTipImage).setColor(z2 ? TintColor.tintColor() : ColorConstant.IMAGE_NORMAL_COLOR);
                            this.mLeftTipImage.setContentDescription(getString(i5));
                            updateImageBgColor(i2, this.mLeftTipImage);
                            if (this.mLeftTipImage.getTag() != null) {
                            }
                            if (z) {
                            }
                            this.mLeftTipImage.setTag(Integer.valueOf(i8));
                            if (list != null) {
                            }
                            z3 = true;
                            if (!z3) {
                            }
                        } else {
                            i7 = 33;
                        }
                    }
                    i8 = 23;
                    i4 = R.drawable.ic_vector_mimoji_change_bg;
                    if (i8 != -1) {
                    }
                    z2 = false;
                    i5 = 0;
                    i4 = 0;
                    z = false;
                    if (i4 > 0) {
                    }
                    ((ColorImageView) this.mLeftTipImage).setColor(z2 ? TintColor.tintColor() : ColorConstant.IMAGE_NORMAL_COLOR);
                    this.mLeftTipImage.setContentDescription(getString(i5));
                    updateImageBgColor(i2, this.mLeftTipImage);
                    if (this.mLeftTipImage.getTag() != null) {
                    }
                    if (z) {
                    }
                    this.mLeftTipImage.setTag(Integer.valueOf(i8));
                    if (list != null) {
                    }
                    z3 = true;
                    if (!z3) {
                    }
                } else {
                    i7 = 32;
                }
            } else if (currentCameraId != 0 || !isNormalIntent || !z4) {
                if (isNormalIntent && DataRepository.dataItemFeature().uj()) {
                    i7 = 25;
                }
                i8 = -1;
                i4 = R.drawable.ic_vector_mimoji_change_bg;
                if (i8 != -1) {
                }
                z2 = false;
                i5 = 0;
                i4 = 0;
                z = false;
                if (i4 > 0) {
                }
                ((ColorImageView) this.mLeftTipImage).setColor(z2 ? TintColor.tintColor() : ColorConstant.IMAGE_NORMAL_COLOR);
                this.mLeftTipImage.setContentDescription(getString(i5));
                updateImageBgColor(i2, this.mLeftTipImage);
                if (this.mLeftTipImage.getTag() != null) {
                }
                if (z) {
                }
                this.mLeftTipImage.setTag(Integer.valueOf(i8));
                if (list != null) {
                }
                z3 = true;
                if (!z3) {
                }
            } else {
                if (!CameraSettings.isAutoZoomEnabled(i2)) {
                    if (this.mIsShowLeftImageIntro) {
                        startLeftImageIntroAnim(i);
                        return;
                    }
                    i4 = R.drawable.ic_vector_mimoji_change_bg;
                    if (i8 != -1) {
                    }
                    z2 = false;
                    i5 = 0;
                    i4 = 0;
                    z = false;
                    if (i4 > 0) {
                    }
                    ((ColorImageView) this.mLeftTipImage).setColor(z2 ? TintColor.tintColor() : ColorConstant.IMAGE_NORMAL_COLOR);
                    this.mLeftTipImage.setContentDescription(getString(i5));
                    updateImageBgColor(i2, this.mLeftTipImage);
                    if (this.mLeftTipImage.getTag() != null) {
                    }
                    if (z) {
                    }
                    this.mLeftTipImage.setTag(Integer.valueOf(i8));
                    if (list != null) {
                    }
                    z3 = true;
                    if (!z3) {
                    }
                }
                i8 = -1;
                i4 = R.drawable.ic_vector_mimoji_change_bg;
                if (i8 != -1) {
                }
                z2 = false;
                i5 = 0;
                i4 = 0;
                z = false;
                if (i4 > 0) {
                }
                ((ColorImageView) this.mLeftTipImage).setColor(z2 ? TintColor.tintColor() : ColorConstant.IMAGE_NORMAL_COLOR);
                this.mLeftTipImage.setContentDescription(getString(i5));
                updateImageBgColor(i2, this.mLeftTipImage);
                if (this.mLeftTipImage.getTag() != null) {
                }
                if (z) {
                }
                this.mLeftTipImage.setTag(Integer.valueOf(i8));
                if (list != null) {
                }
                z3 = true;
                if (!z3) {
                }
            }
            i8 = i7;
            i4 = R.drawable.ic_vector_mimoji_change_bg;
            if (i8 != -1) {
            }
            z2 = false;
            i5 = 0;
            i4 = 0;
            z = false;
            if (i4 > 0) {
            }
            ((ColorImageView) this.mLeftTipImage).setColor(z2 ? TintColor.tintColor() : ColorConstant.IMAGE_NORMAL_COLOR);
            this.mLeftTipImage.setContentDescription(getString(i5));
            updateImageBgColor(i2, this.mLeftTipImage);
            if (this.mLeftTipImage.getTag() != null) {
            }
            if (z) {
            }
            this.mLeftTipImage.setTag(Integer.valueOf(i8));
            if (list != null) {
            }
            z3 = true;
            if (!z3) {
            }
        }
        if (!CameraSettings.isMacroModeEnabled(i2) && !CameraSettings.isUltraPixelRearOn()) {
            if (!z4 || currentCameraId != 0) {
                i8 = -1;
            } else if (this.mIsShowLeftImageIntro) {
                startLeftImageIntroAnim(i);
                return;
            }
            boolean aIWatermarkEnable = DataRepository.dataItemRunning().getComponentRunningAIWatermark().getAIWatermarkEnable();
            if (!DataRepository.dataItemRunning().getComponentRunningAIWatermark().needForceDisable(i2)) {
            }
            i4 = R.drawable.ic_vector_mimoji_change_bg;
            if (i8 != -1) {
            }
            z2 = false;
            i5 = 0;
            i4 = 0;
            z = false;
            if (i4 > 0) {
            }
            ((ColorImageView) this.mLeftTipImage).setColor(z2 ? TintColor.tintColor() : ColorConstant.IMAGE_NORMAL_COLOR);
            this.mLeftTipImage.setContentDescription(getString(i5));
            updateImageBgColor(i2, this.mLeftTipImage);
            if (this.mLeftTipImage.getTag() != null) {
            }
            if (z) {
            }
            this.mLeftTipImage.setTag(Integer.valueOf(i8));
            if (list != null) {
            }
            z3 = true;
            if (!z3) {
            }
        }
        i8 = -1;
        i4 = R.drawable.ic_vector_mimoji_change_bg;
        if (i8 != -1) {
        }
        z2 = false;
        i5 = 0;
        i4 = 0;
        z = false;
        if (i4 > 0) {
        }
        ((ColorImageView) this.mLeftTipImage).setColor(z2 ? TintColor.tintColor() : ColorConstant.IMAGE_NORMAL_COLOR);
        this.mLeftTipImage.setContentDescription(getString(i5));
        updateImageBgColor(i2, this.mLeftTipImage);
        if (this.mLeftTipImage.getTag() != null) {
        }
        if (z) {
        }
        this.mLeftTipImage.setTag(Integer.valueOf(i8));
        if (list != null) {
        }
        z3 = true;
        if (!z3) {
        }
    }

    private void updateRightExtraTipImage(int i, int i2, List<Completable> list) {
        boolean z;
        boolean z2;
        int i3;
        ImageView imageView = this.mRightTipExtraImage;
        int i4 = (i == 183 && DataRepository.dataItemRunning().supportPopShineEntry() && DataRepository.dataItemLive().getMiLiveSegmentData() != null) ? 3 : -1;
        boolean z3 = false;
        if (i4 == -1) {
            z = false;
            i3 = 0;
            z2 = false;
        } else if (i4 != 3) {
            z2 = true;
            z = false;
            i3 = 0;
        } else {
            i3 = DataRepository.dataItemRunning().getComponentRunningShine().getBottomEntryRes(i);
            z = DataRepository.dataItemRunning().getComponentRunningShine().determineStatus(i);
            z2 = true;
        }
        boolean z4 = imageView.getTag() == null || ((Integer) imageView.getTag()).intValue() != i4;
        if (i3 > 0) {
            ((ColorImageView) imageView).setColor(z ? TintColor.tintColor() : ColorConstant.IMAGE_NORMAL_COLOR);
            imageView.setImageResource(i3);
        }
        if (z2) {
            ViewCompat.setRotation(imageView, (float) this.mDegree);
        } else {
            ViewCompat.setRotation(imageView, 0.0f);
        }
        if (z4) {
            imageView.setTag(Integer.valueOf(i4));
            if (list == null) {
                if (!z2) {
                    z3 = true;
                }
            } else if (!z2) {
                z3 = true;
            } else if (i2 != 163) {
                z3 = true;
            } else if (!c.isSupportedOpticalZoom()) {
                z3 = true;
            }
            if (!z3) {
                AlphaInOnSubscribe.directSetResult(imageView);
            } else if (z3) {
                AlphaOutOnSubscribe.directSetResult(imageView);
            } else if (z3) {
                list.add(Completable.create(new AlphaInOnSubscribe(imageView)));
            } else if (z3) {
                list.add(Completable.create(new AlphaInOnSubscribe(imageView).setStartDelayTime(150).setDurationTime(150)));
            } else if (z3) {
                list.add(Completable.create(new AlphaOutOnSubscribe(imageView)));
            }
        }
    }

    private void updateRightTipImage(int i, int i2, List<Completable> list) {
        boolean z;
        int i3;
        ImageView imageView = this.mRightTipImage;
        int i4 = (i == 183 && DataRepository.dataItemRunning().supportPopShineEntry() && DataRepository.dataItemLive().getMiLiveSegmentData() != null) ? 36 : -1;
        boolean z2 = false;
        if (i4 == -1) {
            i3 = 0;
            z = false;
        } else if (i4 != 36) {
            z = true;
            i3 = 0;
        } else {
            i3 = R.drawable.ic_vector_live_camera_picker;
            z = true;
        }
        boolean z3 = imageView.getTag() == null || ((Integer) imageView.getTag()).intValue() != i4;
        if (i3 > 0) {
            imageView.setImageResource(i3);
        }
        if (z) {
            ViewCompat.setRotation(imageView, (float) this.mDegree);
        } else {
            ViewCompat.setRotation(imageView, 0.0f);
        }
        if (z3) {
            imageView.setTag(Integer.valueOf(i4));
            if (list == null) {
                if (!z) {
                    z2 = true;
                }
            } else if (!z) {
                z2 = true;
            } else if (i2 != 163) {
                z2 = true;
            } else if (!c.isSupportedOpticalZoom()) {
                z2 = true;
            }
            if (!z2) {
                AlphaInOnSubscribe.directSetResult(imageView);
            } else if (z2) {
                AlphaOutOnSubscribe.directSetResult(imageView);
            } else if (z2) {
                list.add(Completable.create(new AlphaInOnSubscribe(imageView)));
            } else if (z2) {
                list.add(Completable.create(new AlphaInOnSubscribe(imageView).setStartDelayTime(150).setDurationTime(150)));
            } else if (z2) {
                list.add(Completable.create(new AlphaOutOnSubscribe(imageView)));
            }
        }
    }

    private void updateSpeedTipImage(int i, int i2, List<Completable> list) {
        boolean z;
        boolean z2;
        int i3;
        int i4 = (i == 161 ? !DataRepository.dataItemFeature().Ai() : i != 174) ? -1 : 33;
        boolean z3 = false;
        if (i4 == -1) {
            z = true;
            i3 = 0;
            z2 = false;
        } else if (i4 != 33) {
            z2 = true;
            z = true;
            i3 = 0;
        } else {
            i3 = R.layout.bottom_popup_tips_center_live_speed;
            z2 = true;
            z = false;
        }
        boolean z4 = this.mSpeedTipImage.getTag() == null || ((Integer) this.mSpeedTipImage.getTag()).intValue() != i4;
        if (z4) {
            this.mSpeedTipImage.removeAllViews();
            if (i3 > 0) {
                this.mSpeedTipImage.addView(LayoutInflater.from(getContext()).inflate(i3, (ViewGroup) null));
            }
        }
        if (i4 == 33) {
            ((TextView) this.mSpeedTipImage.findViewById(R.id.live_speed_text)).setText(CameraSettings.getCurrentLiveSpeedText());
            View findViewById = this.mSpeedTipImage.findViewById(R.id.icon);
            if (CameraSettings.getCurrentLiveSpeed().equals(String.valueOf(2))) {
                ((ColorImageView) findViewById).setColor(ColorConstant.IMAGE_NORMAL_COLOR);
            } else {
                ((ColorImageView) findViewById).setColor(TintColor.tintColor());
            }
        }
        if (z4) {
            if (!z2 || !z) {
                ViewCompat.setRotation(this.mSpeedTipImage, 0.0f);
            } else {
                ViewCompat.setRotation(this.mSpeedTipImage, (float) this.mDegree);
            }
            this.mSpeedTipImage.setTag(Integer.valueOf(i4));
            if (list == null) {
                if (!z2) {
                    z3 = true;
                }
            } else if (!z2) {
                z3 = true;
            } else if (i2 != 163) {
                z3 = true;
            } else if (!c.isSupportedOpticalZoom()) {
                z3 = true;
            }
            if (!z3) {
                AlphaInOnSubscribe.directSetResult(this.mSpeedTipImage);
            } else if (z3) {
                AlphaOutOnSubscribe.directSetResult(this.mSpeedTipImage);
            } else if (z3) {
                list.add(Completable.create(new AlphaInOnSubscribe(this.mSpeedTipImage)));
            } else if (z3) {
                list.add(Completable.create(new AlphaInOnSubscribe(this.mSpeedTipImage).setStartDelayTime(150).setDurationTime(150)));
            } else if (z3) {
                list.add(Completable.create(new AlphaOutOnSubscribe(this.mSpeedTipImage)));
            }
        }
    }

    /* JADX INFO: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARNING: Code restructure failed: missing block: B:100:0x01c6, code lost:
        if (r10 != false) goto L_0x01e2;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:23:0x003e, code lost:
        if (com.android.camera.CameraSettings.isSuperEISEnabled(r13) == false) goto L_0x0097;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:34:0x0084, code lost:
        if (com.android.camera.data.DataRepository.dataItemLive().getMiLiveSegmentData() == null) goto L_0x00a1;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:37:0x0094, code lost:
        if (com.android.camera.data.DataRepository.dataItemRunning().supportPopShineEntry() != false) goto L_0x00a1;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:39:0x009f, code lost:
        if (com.android.camera.data.DataRepository.dataItemRunning().supportPopShineEntry() != false) goto L_0x00a1;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:45:0x00bf, code lost:
        if (r6.isBeautyPanelShow() == false) goto L_0x00a1;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:47:0x00ca, code lost:
        if (com.android.camera.data.DataRepository.dataItemRunning().supportPopShineEntry() != false) goto L_0x00a1;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:60:0x00fe, code lost:
        if (com.android.camera.data.DataRepository.dataItemRunning().supportPopShineEntry() != false) goto L_0x00a1;
     */
    /* JADX WARNING: Removed duplicated region for block: B:100:0x01c6  */
    /* JADX WARNING: Removed duplicated region for block: B:102:0x01cb  */
    /* JADX WARNING: Removed duplicated region for block: B:113:0x01e4  */
    /* JADX WARNING: Removed duplicated region for block: B:121:0x022a  */
    /* JADX WARNING: Removed duplicated region for block: B:64:0x0107  */
    /* JADX WARNING: Removed duplicated region for block: B:78:0x015f  */
    /* JADX WARNING: Removed duplicated region for block: B:80:0x0165  */
    /* JADX WARNING: Removed duplicated region for block: B:87:0x0181  */
    /* JADX WARNING: Removed duplicated region for block: B:88:0x0186  */
    /* JADX WARNING: Removed duplicated region for block: B:91:0x0199  */
    /* JADX WARNING: Removed duplicated region for block: B:95:0x01aa  */
    private void updateTipImage(int i, int i2, List<Completable> list) {
        int i3;
        boolean z;
        boolean z2;
        boolean z3;
        int i4;
        int i5;
        if (i != 165) {
            if (i != 171) {
                if (i != 205) {
                    if (i == 173) {
                        ModeProtocol.MiBeautyProtocol miBeautyProtocol = (ModeProtocol.MiBeautyProtocol) ModeCoordinatorImpl.getInstance().getAttachProtocol(194);
                        if (DataRepository.dataItemRunning().supportPopShineEntry()) {
                            if (miBeautyProtocol != null) {
                            }
                            i3 = 3;
                            z = false;
                            if (i3 != -1) {
                            }
                            if (i5 > 0) {
                            }
                            this.mTipImage.setContentDescription(getString(i4));
                            ((ColorImageView) this.mTipImage).setColor(z3 ? TintColor.tintColor() : ColorConstant.IMAGE_NORMAL_COLOR);
                            updateImageBgColor(i, this.mTipImage);
                            if (this.mTipImage.getTag() != null) {
                            }
                            if (z2) {
                            }
                            this.mTipImage.setTag(Integer.valueOf(i3));
                            if (list != null) {
                            }
                            z = true;
                            if (z) {
                            }
                        }
                        i3 = -1;
                        z = false;
                        if (i3 != -1) {
                        }
                        if (i5 > 0) {
                        }
                        this.mTipImage.setContentDescription(getString(i4));
                        ((ColorImageView) this.mTipImage).setColor(z3 ? TintColor.tintColor() : ColorConstant.IMAGE_NORMAL_COLOR);
                        updateImageBgColor(i, this.mTipImage);
                        if (this.mTipImage.getTag() != null) {
                        }
                        if (z2) {
                        }
                        this.mTipImage.setTag(Integer.valueOf(i3));
                        if (list != null) {
                        }
                        z = true;
                        if (z) {
                        }
                    } else if (i != 174) {
                        if (i != 176) {
                            if (i == 177) {
                                updateCenterTipImage(i, i2, list);
                            } else if (i != 183) {
                                if (i != 184) {
                                    switch (i) {
                                        case 162:
                                            if (!CameraSettings.isAutoZoomEnabled(i)) {
                                                if (!CameraSettings.isMacroModeEnabled(i)) {
                                                    break;
                                                }
                                            }
                                            break;
                                    }
                                    z = false;
                                    if (i3 != -1) {
                                        if (i3 != 34) {
                                            if (i3 == 2) {
                                                i5 = R.drawable.ic_beauty_sticker;
                                            } else if (i3 == 3) {
                                                i5 = DataRepository.dataItemRunning().getComponentRunningShine().getBottomEntryRes(i);
                                                i4 = R.string.accessibility_beauty_panel_open;
                                                z2 = true;
                                                z3 = DataRepository.dataItemRunning().getComponentRunningShine().determineStatus(i);
                                            } else if (i3 != 4) {
                                                z2 = true;
                                                i5 = 0;
                                                i4 = 0;
                                            } else {
                                                i5 = R.drawable.id_card_mode;
                                            }
                                            z2 = true;
                                            i4 = 0;
                                        } else if (this.mCurrentMode == 184) {
                                            i5 = R.drawable.ic_vector_mimoji_change_camera;
                                            z2 = true;
                                            i4 = 0;
                                        } else {
                                            i5 = DataRepository.dataItemRunning().getComponentRunningShine().getBottomEntryRes(i);
                                            z3 = DataRepository.dataItemRunning().getComponentRunningShine().determineStatus(i);
                                            z2 = true;
                                            i4 = 0;
                                        }
                                        z3 = i4;
                                    } else {
                                        i5 = 0;
                                        i4 = 0;
                                        z3 = false;
                                        z2 = false;
                                    }
                                    if (i5 > 0) {
                                        this.mTipImage.setImageResource(i5);
                                    }
                                    if (i4 > 0 && Util.isAccessible()) {
                                        this.mTipImage.setContentDescription(getString(i4));
                                    }
                                    ((ColorImageView) this.mTipImage).setColor(z3 ? TintColor.tintColor() : ColorConstant.IMAGE_NORMAL_COLOR);
                                    updateImageBgColor(i, this.mTipImage);
                                    if (this.mTipImage.getTag() != null || ((Integer) this.mTipImage.getTag()).intValue() != i3) {
                                        if (z2) {
                                            if (i3 == 4) {
                                                ViewCompat.setRotation(this.mTipImage, 0.0f);
                                            } else {
                                                ViewCompat.setRotation(this.mTipImage, (float) this.mDegree);
                                            }
                                        }
                                        this.mTipImage.setTag(Integer.valueOf(i3));
                                        if (list != null) {
                                            if (z2) {
                                                if (i2 != 165) {
                                                    z = true;
                                                } else if (!c.isSupportedOpticalZoom()) {
                                                    z = true;
                                                }
                                            } else if (!(i2 == 165 || this.mCurrentMode == 165)) {
                                                z = true;
                                            }
                                            if (z) {
                                                AlphaInOnSubscribe.directSetResult(this.mTipImage);
                                                return;
                                            } else if (z) {
                                                AlphaOutOnSubscribe.directSetResult(this.mTipImage);
                                                return;
                                            } else if (z) {
                                                list.add(Completable.create(new AlphaInOnSubscribe(this.mTipImage)));
                                                return;
                                            } else if (z) {
                                                list.add(Completable.create(new AlphaInOnSubscribe(this.mTipImage).setStartDelayTime(150).setDurationTime(150)));
                                                return;
                                            } else if (z) {
                                                list.add(Completable.create(new AlphaOutOnSubscribe(this.mTipImage)));
                                                return;
                                            } else {
                                                return;
                                            }
                                        }
                                        z = true;
                                        if (z) {
                                        }
                                    } else {
                                        return;
                                    }
                                } else {
                                    updateCenterTipImage(i, i2, list);
                                    if (DataRepository.dataItemLive().getMimojiStatusManager2().getMimojiPanelState() == 0 && !DataRepository.dataItemLive().getMimojiStatusManager2().isInMimojiPreviewPlay() && !DataRepository.dataItemLive().getMimojiStatusManager2().isInMimojiCreate()) {
                                        i3 = 34;
                                        z = false;
                                        if (i3 != -1) {
                                        }
                                        if (i5 > 0) {
                                        }
                                        this.mTipImage.setContentDescription(getString(i4));
                                        ((ColorImageView) this.mTipImage).setColor(z3 ? TintColor.tintColor() : ColorConstant.IMAGE_NORMAL_COLOR);
                                        updateImageBgColor(i, this.mTipImage);
                                        if (this.mTipImage.getTag() != null) {
                                        }
                                        if (z2) {
                                        }
                                        this.mTipImage.setTag(Integer.valueOf(i3));
                                        if (list != null) {
                                        }
                                        z = true;
                                        if (z) {
                                        }
                                    }
                                }
                            } else if (DataRepository.dataItemRunning().supportPopShineEntry()) {
                            }
                            i3 = -1;
                            z = false;
                            if (i3 != -1) {
                            }
                            if (i5 > 0) {
                            }
                            this.mTipImage.setContentDescription(getString(i4));
                            ((ColorImageView) this.mTipImage).setColor(z3 ? TintColor.tintColor() : ColorConstant.IMAGE_NORMAL_COLOR);
                            updateImageBgColor(i, this.mTipImage);
                            if (this.mTipImage.getTag() != null) {
                            }
                            if (z2) {
                            }
                            this.mTipImage.setTag(Integer.valueOf(i3));
                            if (list != null) {
                            }
                            z = true;
                            if (z) {
                            }
                        }
                    }
                }
            }
        }
        if (!CameraSettings.isMacroModeEnabled(i) && !CameraSettings.isUltraPixelPortraitFrontOn()) {
            TimerBurstController timerBurstController = DataRepository.dataItemLive().getTimerBurstController();
            if (!timerBurstController.isInTimerBurstShotting() || timerBurstController.isPendingStopTimerBurst()) {
                if (this.mNeedShowIDCardTip) {
                    i3 = 4;
                    z = false;
                    if (i3 != -1) {
                    }
                    if (i5 > 0) {
                    }
                    this.mTipImage.setContentDescription(getString(i4));
                    ((ColorImageView) this.mTipImage).setColor(z3 ? TintColor.tintColor() : ColorConstant.IMAGE_NORMAL_COLOR);
                    updateImageBgColor(i, this.mTipImage);
                    if (this.mTipImage.getTag() != null) {
                    }
                    if (z2) {
                    }
                    this.mTipImage.setTag(Integer.valueOf(i3));
                    if (list != null) {
                    }
                    z = true;
                    if (z) {
                    }
                }
            }
        }
        i3 = -1;
        z = false;
        if (i3 != -1) {
        }
        if (i5 > 0) {
        }
        this.mTipImage.setContentDescription(getString(i4));
        ((ColorImageView) this.mTipImage).setColor(z3 ? TintColor.tintColor() : ColorConstant.IMAGE_NORMAL_COLOR);
        updateImageBgColor(i, this.mTipImage);
        if (this.mTipImage.getTag() != null) {
        }
        if (z2) {
        }
        this.mTipImage.setTag(Integer.valueOf(i3));
        if (list != null) {
        }
        z = true;
        if (z) {
        }
    }

    @Override // com.android.camera.protocol.ModeProtocol.BottomPopupTips
    public boolean containTips(@StringRes int i) {
        TextView textView = this.mTipMessage;
        return textView != null && textView.getVisibility() == 0 && getString(i).equals(this.mTipMessage.getText().toString());
    }

    @Override // com.android.camera.protocol.ModeProtocol.BottomPopupTips
    public void directHideCenterTipImage() {
        ImageView imageView = this.mCenterTipImage;
        if (imageView != null) {
            imageView.setTag(-1);
            this.mCenterTipImage.setVisibility(8);
        }
    }

    @Override // com.android.camera.protocol.ModeProtocol.BottomPopupTips
    public void directHideLeftImageIntro() {
        this.mIsShowLeftImageIntro = false;
        AnimatorSet animatorSet = this.mLeftImageIntroAnimator;
        if (animatorSet != null) {
            animatorSet.cancel();
        }
        if (this.mLeftImageIntro.getVisibility() == 0) {
            AlphaOutOnSubscribe.directSetResult(this.mLeftImageIntro);
        }
    }

    @Override // com.android.camera.protocol.ModeProtocol.LyingDirectHint
    public void directHideLyingDirectHint() {
        this.mLyingDirectHint.setVisibility(8);
    }

    @Override // com.android.camera.protocol.ModeProtocol.BottomPopupTips
    public void directHideTipImage() {
        if (this.mTipImage.getVisibility() != 4) {
            this.mTipImage.setTag(-1);
            this.mTipImage.setVisibility(4);
        }
    }

    @Override // com.android.camera.protocol.ModeProtocol.BottomPopupTips
    public void directShowLeftImageIntro() {
        if (CameraSettings.isShowUltraWideIntro()) {
            this.mIsShowLeftImageIntro = true;
        }
        int i = this.mCurrentMode;
        updateLeftTipImage(0, i, i, null);
    }

    @Override // com.android.camera.protocol.ModeProtocol.BottomPopupTips
    public void directShowOrHideLeftTipImage(boolean z) {
        ImageView imageView = this.mLeftTipImage;
        if (imageView != null) {
            if (z) {
                updateLeftTipImage();
                this.mLeftTipImage.setVisibility(0);
                return;
            }
            imageView.setTag(-1);
            this.mLeftTipImage.setVisibility(4);
        }
    }

    @Override // com.android.camera.protocol.ModeProtocol.BottomPopupTips
    public void directlyHideTips() {
        ViewCompat.animate(this.mTipMessage).cancel();
        this.mHandler.removeCallbacksAndMessages(null);
        if (this.mTipMessage.getVisibility() == 0) {
            this.mTipMessage.setVisibility(8);
            ModeProtocol.CameraModuleSpecial cameraModuleSpecial = (ModeProtocol.CameraModuleSpecial) ModeCoordinatorImpl.getInstance().getAttachProtocol(195);
            if (cameraModuleSpecial != null) {
                cameraModuleSpecial.showOrHideChip(true);
            }
            if (this.mLastTipType == 6 && !isPortraitHintVisible()) {
                showTips(this.mLastTipType, this.mLastTipMessage, 4);
            }
            this.mLastTipType = 4;
        }
    }

    @Override // com.android.camera.protocol.ModeProtocol.BottomPopupTips
    public void directlyHideTips(@StringRes int i) {
        if (i <= 0 || containTips(i)) {
            directlyHideTips();
        }
    }

    @Override // com.android.camera.protocol.ModeProtocol.BottomPopupTips
    public void directlyHideTipsForce() {
        ViewCompat.animate(this.mTipMessage).cancel();
        this.mHandler.removeCallbacksAndMessages(null);
        this.mLastTipMessage = null;
        this.mTipMessage.setVisibility(8);
        this.mLastTipType = 4;
    }

    @Override // com.android.camera.protocol.ModeProtocol.BottomPopupTips
    public void directlyShowTips(int i, @StringRes int i2) {
        ViewCompat.animate(this.mTipMessage).cancel();
        this.mHandler.removeCallbacksAndMessages(null);
        if (this.mTipMessage.getVisibility() != 0) {
            this.mLastTipType = this.mCurrentTipType;
            this.mLastTipMessage = this.mCurrentTipMessage;
            this.mCurrentTipType = i;
            this.mCurrentTipMessage = getString(i2);
            AlphaInOnSubscribe.directSetResult(this.mTipMessage);
            this.mTipMessage.setText(i2);
        }
    }

    public int getBottomMargin() {
        return DataRepository.dataItemFeature().Gj() ? getResources().getDimensionPixelSize(R.dimen.popup_indicator_button_extra_margin_bottom_support_idcard) : getResources().getDimensionPixelSize(R.dimen.popup_indicator_button_extra_margin_bottom);
    }

    @Override // com.android.camera.protocol.ModeProtocol.BottomPopupTips
    public String getCurrentBottomTipMsg() {
        return this.mCurrentTipMessage;
    }

    @Override // com.android.camera.protocol.ModeProtocol.BottomPopupTips
    public int getCurrentBottomTipType() {
        return this.mCurrentTipType;
    }

    @Override // com.android.camera.fragment.BaseFragment
    public int getFragmentInto() {
        return 65529;
    }

    /* access modifiers changed from: protected */
    @Override // com.android.camera.fragment.BaseFragment
    public int getLayoutResourceId() {
        return R.layout.fragment_bottom_popup_tips;
    }

    @Override // com.android.camera.protocol.ModeProtocol.BottomPopupTips
    public void hideCenterTipImage() {
        ImageView imageView = this.mCenterTipImage;
        if (imageView != null) {
            imageView.setTag(-1);
            if (this.mCenterTipImage.getVisibility() != 4) {
                Completable.create(new AlphaOutOnSubscribe(this.mCenterTipImage)).subscribe();
                View view = this.mCenterRedDot;
                if (view != null) {
                    view.setVisibility(8);
                }
            }
        }
    }

    @Override // com.android.camera.protocol.ModeProtocol.BottomPopupTips
    public void hideLeftTipImage() {
        ImageView imageView = this.mLeftTipImage;
        if (imageView != null && imageView.getVisibility() == 0) {
            this.mLeftTipImage.setTag(-1);
            Completable.create(new AlphaOutOnSubscribe(this.mLeftTipImage)).subscribe();
        }
        ImageView imageView2 = this.mLeftTipExtraImage;
        if (imageView2 != null && imageView2.getVisibility() == 0) {
            this.mLeftTipExtraImage.setTag(-1);
            Completable.create(new AlphaOutOnSubscribe(this.mLeftTipExtraImage)).subscribe();
        }
    }

    @Override // com.android.camera.protocol.ModeProtocol.BottomPopupTips
    public void hideNearRangeTip() {
        if (this.mNearRangeModeButton.getVisibility() != 8) {
            this.mNearRangeModeButton.setVisibility(8);
        }
    }

    @Override // com.android.camera.protocol.ModeProtocol.BottomPopupTips
    public void hideQrCodeTip() {
        if (this.mQrCodeButton.getVisibility() != 8) {
            this.mQrCodeButton.setVisibility(8);
            String tag = getTag();
            Log.w(tag, "  hideQrCodeTip  time  : " + System.currentTimeMillis());
        }
    }

    @Override // com.android.camera.protocol.ModeProtocol.BottomPopupTips
    public void hideRightTipImage() {
        ImageView imageView = this.mRightTipImage;
        if (imageView != null && imageView.getVisibility() == 0) {
            this.mRightTipImage.setTag(-1);
            Completable.create(new AlphaOutOnSubscribe(this.mRightTipImage)).subscribe();
        }
        ImageView imageView2 = this.mRightTipExtraImage;
        if (imageView2 != null && imageView2.getVisibility() == 0) {
            this.mRightTipExtraImage.setTag(-1);
            Completable.create(new AlphaOutOnSubscribe(this.mRightTipExtraImage)).subscribe();
        }
    }

    @Override // com.android.camera.protocol.ModeProtocol.BottomPopupTips
    public void hideSpeedTipImage() {
        ViewGroup viewGroup = this.mSpeedTipImage;
        if (viewGroup != null && viewGroup.getVisibility() != 4) {
            this.mSpeedTipImage.setTag(-1);
            Completable.create(new AlphaOutOnSubscribe(this.mSpeedTipImage)).subscribe();
        }
    }

    @Override // com.android.camera.protocol.ModeProtocol.BottomPopupTips
    public void hideTipImage() {
        ImageView imageView = this.mTipImage;
        if (imageView != null && imageView.getVisibility() != 4) {
            this.mTipImage.setTag(-1);
            Completable.create(new AlphaOutOnSubscribe(this.mTipImage)).subscribe();
        }
    }

    /* access modifiers changed from: protected */
    @Override // com.android.camera.fragment.BaseFragment
    public void initView(View view) {
        this.mRootView = view;
        this.mTipImage = (ImageView) view.findViewById(R.id.popup_tip_image);
        FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) this.mTipImage.getLayoutParams();
        layoutParams.gravity = 8388693;
        layoutParams.setMarginEnd(getMarginEnd());
        layoutParams.bottomMargin = getBottomMargin();
        this.mLeftTipImage = (ImageView) view.findViewById(R.id.popup_left_tip_image);
        ((FrameLayout.LayoutParams) this.mLeftTipImage.getLayoutParams()).gravity = 8388691;
        this.mLeftTipImage.setImageResource(R.drawable.ic_new_effect_button_normal);
        this.mLeftTipImage.setOnClickListener(this);
        this.mLeftTipExtraImage = (ImageView) view.findViewById(R.id.popup_left_tip_extra_image);
        this.mLeftTipExtraImage.setImageResource(R.drawable.ic_vector_mimoji_change_bg);
        this.mLeftTipExtraImage.setOnClickListener(this);
        this.mRightTipImage = (ImageView) view.findViewById(R.id.popup_right_tip_image);
        this.mRightTipImage.setOnClickListener(this);
        this.mRightTipExtraImage = (ImageView) view.findViewById(R.id.popup_right_tip_extra_image);
        this.mRightTipExtraImage.setOnClickListener(this);
        this.mSpeedTipImage = (ViewGroup) view.findViewById(R.id.popup_speed_tip_image);
        this.mSpeedTipImage.setOnClickListener(this);
        ViewGroup viewGroup = (ViewGroup) view.findViewById(R.id.popup_center_tip_layout);
        FrameLayout.LayoutParams layoutParams2 = (FrameLayout.LayoutParams) viewGroup.getLayoutParams();
        if (!HybridZoomingSystem.IS_3_OR_MORE_SAT) {
            layoutParams2.gravity = 81;
        } else {
            layoutParams2.gravity = 8388691;
        }
        this.mCenterTipImage = (ImageView) viewGroup.findViewById(R.id.popup_center_tip_image);
        this.mCenterTipImage.setOnClickListener(this);
        this.mCenterRedDot = viewGroup.findViewById(R.id.popup_center_red_dot);
        this.mLeftImageIntro = (FrameLayout) view.findViewById(R.id.popup_left_tip_intro);
        this.mLeftImageIntro.setOnClickListener(this);
        this.mLeftImageIntroContent = (TextView) view.findViewById(R.id.popup_left_tip_intro_text);
        this.mLeftImageIntroRadius = getResources().getDimensionPixelSize(R.dimen.popup_tip_beauty_intro_radius);
        this.mLeftImageIntroWidth = getLeftImageIntroWide();
        this.mQrCodeButton = (ImageView) view.findViewById(R.id.popup_tips_qrcode);
        this.mNearRangeModeButton = (LinearLayout) view.findViewById(R.id.popup_tips_near_range_mode);
        this.mLyingDirectHint = (TextView) view.findViewById(R.id.bottom_lying_direct_hint_text);
        this.mTipMessage = (TextView) view.findViewById(R.id.popup_tips_message);
        this.mPortraitSuccessHint = view.findViewById(R.id.portrait_success_hint);
        ((ViewGroup.MarginLayoutParams) view.getLayoutParams()).bottomMargin = Util.getBottomHeight();
        this.mTipImage.setOnClickListener(this);
        this.mQrCodeButton.setOnClickListener(this);
        this.mNearRangeModeButton.setOnClickListener(this);
        adjustViewBackground(this.mCurrentMode);
        FolmeUtils.touchScaleTint(this.mLeftTipImage, this.mLeftTipExtraImage, this.mRightTipImage, this.mRightTipExtraImage, this.mSpeedTipImage, this.mCenterTipImage, this.mLeftImageIntro, this.mTipImage, this.mQrCodeButton);
        FolmeUtils.touchTint(this.mNearRangeModeButton);
        provideAnimateElement(this.mCurrentMode, null, 2);
        if (((ActivityBase) getContext()).getCameraIntentManager().isFromScreenSlide().booleanValue()) {
            Util.startScreenSlideAlphaInAnimation(this.mTipImage);
        }
        this.mBottomTipHeight = getResources().getDimensionPixelSize(R.dimen.portrait_hint_height);
    }

    @Override // com.android.camera.protocol.ModeProtocol.BottomPopupTips
    public boolean isNearRangeTipShowing() {
        return this.mNearRangeModeButton.getVisibility() == 0;
    }

    @Override // com.android.camera.protocol.ModeProtocol.BottomPopupTips
    public boolean isPortraitHintVisible() {
        return this.mPortraitSuccessHint.getVisibility() == 0;
    }

    @Override // com.android.camera.protocol.ModeProtocol.BottomPopupTips
    public boolean isQRTipVisible() {
        ImageView imageView = this.mQrCodeButton;
        return imageView != null && imageView.getVisibility() == 0;
    }

    @Override // com.android.camera.protocol.ModeProtocol.BottomPopupTips
    public boolean isTipShowing() {
        TextView textView = this.mTipMessage;
        return textView != null && textView.getVisibility() == 0;
    }

    @Override // com.android.camera.fragment.BaseFragment, com.android.camera.animation.AnimationDelegate.AnimationResource
    public boolean needViewClear() {
        if (DataRepository.dataItemGlobal().getCurrentMode() == 162) {
            return true;
        }
        return super.needViewClear();
    }

    @Override // com.android.camera.fragment.BaseFragment, com.android.camera.animation.AnimationDelegate.AnimationResource
    public void notifyAfterFrameAvailable(int i) {
        super.notifyAfterFrameAvailable(i);
        reConfigBottomTipOfMimoji();
        if (CameraSettings.isCinematicAspectRatioEnabled(this.mCurrentMode)) {
            updateLeftRightTipImageForCinematic(true);
        }
    }

    @Override // com.android.camera.fragment.BaseFragment, com.android.camera.animation.AnimationDelegate.AnimationResource
    public void notifyDataChanged(int i, int i2) {
        super.notifyDataChanged(i, i2);
        if (i == 2) {
            directlyHideTips();
        } else if (i == 3) {
            adjustViewBackground(this.mCurrentMode);
        }
        int i3 = this.mCurrentMode;
        updateTipImage(i3, i3, null);
        int i4 = this.mCurrentMode;
        updateLeftTipImage(1, i4, i4, null);
        int i5 = this.mCurrentMode;
        updateLeftTipExtraImage(i5, i5, null);
        int i6 = this.mCurrentMode;
        updateSpeedTipImage(i6, i6, null);
        int i7 = this.mCurrentMode;
        updateCenterTipImage(i7, i7, null);
    }

    @Override // com.android.camera.protocol.ModeProtocol.HandleBackTrace
    public boolean onBackEvent(int i) {
        if (i != 1) {
            if (i != 2) {
                if (i == 3) {
                    int i2 = this.mCurrentTipType;
                    if (i2 == 6 || i2 == 7 || i2 == 11 || i2 == 9 || i2 == 12 || i2 == 18 || i2 == 10) {
                        return false;
                    }
                }
                hideTip(this.mTipMessage);
                hideTip(this.mPortraitSuccessHint);
                hideTip(this.mQrCodeButton);
                hideTip(this.mNearRangeModeButton);
                this.mHandler.removeCallbacksAndMessages(null);
            }
            return false;
        }
        if (this.mCurrentTipType == 9) {
            return false;
        }
        hideTip(this.mTipMessage);
        hideTip(this.mPortraitSuccessHint);
        hideTip(this.mQrCodeButton);
        hideTip(this.mNearRangeModeButton);
        this.mHandler.removeCallbacksAndMessages(null);
        return false;
    }

    /* JADX WARNING: Removed duplicated region for block: B:101:? A[RETURN, SYNTHETIC] */
    /* JADX WARNING: Removed duplicated region for block: B:66:0x0197  */
    /* JADX WARNING: Removed duplicated region for block: B:69:0x01b0  */
    /* JADX WARNING: Removed duplicated region for block: B:74:0x01bf  */
    /* JADX WARNING: Removed duplicated region for block: B:79:0x01dd  */
    public void onClick(View view) {
        ModeProtocol.ConfigChanges configChanges;
        int intValue;
        if (isEnableClick()) {
            ModeProtocol.CameraAction cameraAction = (ModeProtocol.CameraAction) ModeCoordinatorImpl.getInstance().getAttachProtocol(161);
            if (cameraAction != null && cameraAction.isDoingAction()) {
                return;
            }
            if (!CameraSettings.isFrontCamera() || !((Camera) getContext()).isScreenSlideOff()) {
                switch (view.getId()) {
                    case R.id.popup_center_tip_image:
                        intValue = ((Integer) view.getTag()).intValue();
                        if (intValue == 18) {
                            hideAllTipImage();
                            showLiveSticker();
                            View view2 = this.mCenterRedDot;
                            if (view2 != null) {
                                view2.setVisibility(8);
                                CameraSettings.setTTLiveStickerNeedRedDot(false);
                            }
                        } else if (intValue == 34) {
                            if (this.mCurrentMode == 184) {
                                showMimojiPanel(1);
                            } else {
                                showOrHideMimojiPanel();
                            }
                        }
                        configChanges = (ModeProtocol.ConfigChanges) ModeCoordinatorImpl.getInstance().getAttachProtocol(164);
                        if (configChanges == null) {
                            Log.d(TAG, "NearRangeMode:Exit near range mode");
                            configChanges.onConfigChanged(167);
                            return;
                        }
                        return;
                    case R.id.popup_center_tip_layout:
                    case R.id.popup_cover:
                    case R.id.popup_left_tip_intro_text:
                    case R.id.popup_left_top:
                    case R.id.popup_menu:
                    case R.id.popup_more_mode_layout:
                    case R.id.popup_tips_message:
                    default:
                        return;
                    case R.id.popup_left_tip_extra_image:
                        int intValue2 = ((Integer) view.getTag()).intValue();
                        if (intValue2 == 22) {
                            hideAllTipImage();
                            showKaleidoscope();
                        } else if (intValue2 == 34) {
                            showMimojiPanel(2);
                        }
                        if (((Integer) view.getTag()).intValue() == 33) {
                            hideAllTipImage();
                            CameraStatUtils.trackLiveClick(MistatsConstants.Live.VALUE_LIVE_SPEED);
                            showLiveSpeed();
                        }
                        intValue = ((Integer) view.getTag()).intValue();
                        if (intValue == 18) {
                        }
                        configChanges = (ModeProtocol.ConfigChanges) ModeCoordinatorImpl.getInstance().getAttachProtocol(164);
                        if (configChanges == null) {
                        }
                        break;
                    case R.id.popup_left_tip_image:
                        onLeftImageClick(view);
                        return;
                    case R.id.popup_left_tip_intro:
                        view.setTag(21);
                        CameraSettings.setPopupUltraWideIntroClicked();
                        directHideLeftImageIntro();
                        onLeftImageClick(view);
                        return;
                    case R.id.popup_right_tip_extra_image:
                        if (((Integer) view.getTag()).intValue() == 3) {
                            MistatsWrapper.commonKeyTriggerEvent(MistatsConstants.BeautyAttr.VALUE_BEAUTY_SHOW_BOTTOM_BUTTON, null, null);
                            showBeauty(this.mCurrentMode);
                            return;
                        }
                        return;
                    case R.id.popup_right_tip_image:
                        int intValue3 = ((Integer) view.getTag()).intValue();
                        ModeProtocol.ModeChangeController modeChangeController = (ModeProtocol.ModeChangeController) ModeCoordinatorImpl.getInstance().getAttachProtocol(179);
                        if (intValue3 == 3) {
                            MistatsWrapper.commonKeyTriggerEvent(MistatsConstants.BeautyAttr.VALUE_BEAUTY_SHOW_BOTTOM_BUTTON, null, null);
                            showBeauty(this.mCurrentMode);
                            return;
                        } else if (intValue3 == 36 && this.mCurrentMode == 183 && cameraAction != null && !cameraAction.isDoingAction() && !cameraAction.isRecording()) {
                            CameraStatUtils.trackMiLiveClick(MistatsConstants.MiLive.VALUE_MI_LIVE_CLICK_SWITCH);
                            if (modeChangeController != null) {
                                modeChangeController.changeCamera(view);
                                return;
                            }
                            return;
                        } else {
                            return;
                        }
                    case R.id.popup_speed_tip_image:
                        if (((Integer) view.getTag()).intValue() == 33) {
                        }
                        intValue = ((Integer) view.getTag()).intValue();
                        if (intValue == 18) {
                        }
                        configChanges = (ModeProtocol.ConfigChanges) ModeCoordinatorImpl.getInstance().getAttachProtocol(164);
                        if (configChanges == null) {
                        }
                        break;
                    case R.id.popup_tip_image:
                        int intValue4 = ((Integer) view.getTag()).intValue();
                        CameraSettings.setPopupTipBeautyIntroClicked();
                        ModeProtocol.BaseDelegate baseDelegate = (ModeProtocol.BaseDelegate) ModeCoordinatorImpl.getInstance().getAttachProtocol(160);
                        ModeProtocol.ModeChangeController modeChangeController2 = (ModeProtocol.ModeChangeController) ModeCoordinatorImpl.getInstance().getAttachProtocol(179);
                        if (intValue4 == 2) {
                            hideAllTipImage();
                            return;
                        } else if (intValue4 == 3) {
                            hideAllTipImage();
                            MistatsWrapper.commonKeyTriggerEvent(MistatsConstants.BeautyAttr.VALUE_BEAUTY_SHOW_BOTTOM_BUTTON, null, null);
                            showBeauty(this.mCurrentMode);
                            return;
                        } else if (intValue4 == 4) {
                            hideAllTipImage();
                            ((ModeProtocol.ConfigChanges) ModeCoordinatorImpl.getInstance().getAttachProtocol(164)).onConfigChanged(166);
                            return;
                        } else if (intValue4 == 34 && this.mCurrentMode == 184 && cameraAction != null && !cameraAction.isDoingAction() && !cameraAction.isRecording()) {
                            if (DataRepository.dataItemLive().getMimojiStatusManager2().isInMimojiCreate()) {
                                CameraStatUtils.trackMimojiClick(MistatsConstants.Mimoji.MIMOJI_CLICK_CREATE_SWITCH, MistatsConstants.BaseEvent.CREATE);
                            }
                            if (modeChangeController2 != null) {
                                modeChangeController2.changeCamera(view);
                                return;
                            }
                            return;
                        } else {
                            return;
                        }
                    case R.id.popup_tips_near_range_mode:
                        configChanges = (ModeProtocol.ConfigChanges) ModeCoordinatorImpl.getInstance().getAttachProtocol(164);
                        if (configChanges == null) {
                        }
                        break;
                    case R.id.popup_tips_qrcode:
                        hideQrCodeTip();
                        HashMap hashMap = new HashMap();
                        hashMap.put(MistatsConstants.CaptureAttr.PARAM_ASD_DETECT_TIP, MistatsConstants.FeatureName.VALUE_QRCODE_DETECTED);
                        MistatsWrapper.mistatEvent(MistatsConstants.FeatureName.KEY_COMMON_TIPS, hashMap);
                        ModeProtocol.CameraModuleSpecial cameraModuleSpecial = (ModeProtocol.CameraModuleSpecial) ModeCoordinatorImpl.getInstance().getAttachProtocol(195);
                        if (cameraModuleSpecial != null) {
                            cameraModuleSpecial.showQRCodeResult();
                            return;
                        }
                        return;
                }
            }
        }
    }

    @Override // com.android.camera.fragment.BaseFragment, com.android.camera.animation.AnimationDelegate.AnimationResource
    public void provideAnimateElement(int i, List<Completable> list, int i2) {
        if (i2 == 3 || this.mCurrentMode != i) {
            this.mCloseType = 0;
        }
        int i3 = this.mCurrentMode;
        super.provideAnimateElement(i, list, i2);
        if (isInModeChanging() || i2 == 3) {
            this.mIsShowLyingDirectHint = false;
            directHideLyingDirectHint();
        }
        onBackEvent(4);
        updateTipBottomMargin(0, false);
        updateTipImage(i, i3, list);
        updateLeftTipImage(0, i, i3, list);
        updateLeftTipExtraImage(i, i3, list);
        updateRightTipImage(i, i3, list);
        updateRightExtraTipImage(i, i3, list);
        updateSpeedTipImage(i, i3, list);
        updateCenterTipImage(i, i3, list);
        if (!CameraSettings.isCinematicAspectRatioEnabled(this.mCurrentMode)) {
            updateLeftRightTipImageForCinematic(false);
        }
    }

    /* access modifiers changed from: protected */
    @Override // com.android.camera.fragment.BaseFragment
    public Animation provideEnterAnimation(int i) {
        if (i == 240 || i == getFragmentInto()) {
            return null;
        }
        return FragmentAnimationFactory.wrapperAnimation(161);
    }

    @Override // com.android.camera.fragment.BaseFragment, com.android.camera.animation.AnimationDelegate.AnimationResource
    public void provideRotateItem(List<View> list, int i) {
        super.provideRotateItem(list, i);
        if (this.mTipImage.getVisibility() == 0 && !currentIsIDCardShow()) {
            list.add(this.mTipImage);
        }
        ImageView imageView = this.mLeftTipImage;
        if (!(imageView == null || imageView.getVisibility() != 0 || ((Integer) this.mLeftTipImage.getTag()).intValue() == 24)) {
            list.add(this.mLeftTipImage);
        }
        ViewGroup viewGroup = this.mSpeedTipImage;
        if (!(viewGroup == null || viewGroup.getVisibility() != 0 || ((Integer) this.mSpeedTipImage.getTag()).intValue() == 33)) {
            list.add(this.mSpeedTipImage);
        }
        ImageView imageView2 = this.mCenterTipImage;
        if (imageView2 != null) {
            list.add(imageView2);
        }
        ImageView imageView3 = this.mLeftTipExtraImage;
        if (imageView3 != null) {
            list.add(imageView3);
        }
    }

    @Override // com.android.camera.protocol.ModeProtocol.BottomPopupTips
    public void reConfigBottomTipOfUltraWide() {
        if (HybridZoomingSystem.IS_3_OR_MORE_SAT) {
            int i = this.mCurrentMode;
            if (!(163 == i || 165 == i || 162 == i || 169 == i || 174 == i || 161 == i || 183 == i)) {
                return;
            }
        } else {
            int i2 = this.mCurrentMode;
            if (!(163 == i2 || 165 == i2 || 162 == i2)) {
                return;
            }
        }
        boolean isAutoZoomEnabled = CameraSettings.isAutoZoomEnabled(this.mCurrentMode);
        if ((162 != this.mCurrentMode || !isAutoZoomEnabled) && !CameraSettings.isSuperEISEnabled(this.mCurrentMode) && !CameraSettings.isMacroModeEnabled(this.mCurrentMode)) {
            if (HybridZoomingSystem.IS_3_OR_MORE_SAT) {
                if (HybridZoomingSystem.toFloat(HybridZoomingSystem.getZoomRatioHistory(this.mCurrentMode, "1.0"), 1.0f) >= 1.0f) {
                    return;
                }
            } else if (!CameraSettings.isUltraWideConfigOpen(this.mCurrentMode)) {
                return;
            }
            ModeProtocol.MiBeautyProtocol miBeautyProtocol = (ModeProtocol.MiBeautyProtocol) ModeCoordinatorImpl.getInstance().getAttachProtocol(194);
            if ((miBeautyProtocol == null || !miBeautyProtocol.isBeautyPanelShow()) && CameraSettings.shouldShowUltraWideStickyTip(this.mCurrentMode) && !isTipShowing()) {
                directlyShowTips(13, R.string.ultra_wide_open_tip);
            }
        }
    }

    @Override // com.android.camera.protocol.ModeProtocol.BottomPopupTips
    public boolean reConfigQrCodeTip() {
        if (this.mCurrentMode == 163) {
            ModeProtocol.BottomPopupTips bottomPopupTips = (ModeProtocol.BottomPopupTips) ModeCoordinatorImpl.getInstance().getAttachProtocol(175);
            boolean z = bottomPopupTips != null && bottomPopupTips.isTipShowing() && (TextUtils.equals(this.mCurrentTipMessage, getString(R.string.ultra_wide_recommend_tip_hint)) || TextUtils.equals(this.mCurrentTipMessage, getString(R.string.ultra_wide_recommend_tip_hint_sat)));
            boolean z2 = HybridZoomingSystem.toDecimal(CameraSettings.readZoom()) == HybridZoomingSystem.FLOAT_ZOOM_RATIO_ULTR || CameraSettings.isUltraWideConfigOpen(this.mCurrentMode);
            ModeProtocol.MakeupProtocol makeupProtocol = (ModeProtocol.MakeupProtocol) ModeCoordinatorImpl.getInstance().getAttachProtocol(180);
            boolean z3 = makeupProtocol != null && makeupProtocol.isSeekBarVisible();
            ModeProtocol.MiBeautyProtocol miBeautyProtocol = (ModeProtocol.MiBeautyProtocol) ModeCoordinatorImpl.getInstance().getAttachProtocol(194);
            boolean z4 = miBeautyProtocol != null && miBeautyProtocol.isBeautyPanelShow();
            ModeProtocol.TopAlert topAlert = (ModeProtocol.TopAlert) ModeCoordinatorImpl.getInstance().getAttachProtocol(172);
            int currentAiSceneLevel = topAlert.getCurrentAiSceneLevel();
            ModeProtocol.DualController dualController = (ModeProtocol.DualController) ModeCoordinatorImpl.getInstance().getAttachProtocol(182);
            boolean z5 = dualController != null && dualController.isZoomPanelVisible();
            boolean z6 = topAlert.getAlertIsShow() && (currentAiSceneLevel == -1 || currentAiSceneLevel == 23 || currentAiSceneLevel == 24 || currentAiSceneLevel == 35 || currentAiSceneLevel == -35);
            if (CameraSettings.isTiltShiftOn() || CameraSettings.isGroupShotOn() || CameraSettings.isGradienterOn() || z2 || z4 || z3 || z || z6 || z5) {
                hideQrCodeTip();
                return true;
            }
        }
        return false;
    }

    @Override // com.android.camera.protocol.ModeProtocol.BottomPopupTips
    public void reInitTipImage() {
        provideAnimateElement(this.mCurrentMode, null, 2);
    }

    /* access modifiers changed from: protected */
    @Override // com.android.camera.fragment.BaseFragment
    public void register(ModeProtocol.ModeCoordinator modeCoordinator) {
        super.register(modeCoordinator);
        modeCoordinator.attachProtocol(175, this);
        registerBackStack(modeCoordinator, this);
        boolean z = DataRepository.dataItemGlobal().getBoolean("pref_camera_first_ultra_wide_use_hint_shown_key", true);
        if (CameraSettings.isShowUltraWideIntro() && !z) {
            this.mIsShowLeftImageIntro = true;
        }
    }

    @Override // com.android.camera.protocol.ModeProtocol.BottomPopupTips
    public void selectBeautyTipImage(boolean z) {
        if (z) {
            this.mTipImage.setImageResource(R.drawable.ic_beauty_on);
        } else {
            this.mTipImage.setImageResource(R.drawable.ic_beauty_normal);
        }
    }

    @Override // com.android.camera.protocol.ModeProtocol.BottomPopupTips
    public void setPortraitHintVisible(int i) {
        if (i != 0 && this.mCurrentTipType != 21) {
            this.mLastTipType = i == 0 ? 7 : 4;
            directlyHideTips();
            if (i == 0) {
                reIntTipViewMarginBottom(this.mPortraitSuccessHint, this.mBottomTipHeight);
            }
            this.mPortraitSuccessHint.setVisibility(i);
        }
    }

    @Override // com.android.camera.protocol.ModeProtocol.BottomPopupTips
    public void showFastMotionPanel() {
        hideAllTipImage();
        ComponentRunningFastMotion componentRunningFastMotion = DataRepository.dataItemRunning().getComponentRunningFastMotion();
        ModeProtocol.FastMotionProtocol fastMotionProtocol = (ModeProtocol.FastMotionProtocol) ModeCoordinatorImpl.getInstance().getAttachProtocol(674);
        ModeProtocol.BottomMenuProtocol bottomMenuProtocol = (ModeProtocol.BottomMenuProtocol) ModeCoordinatorImpl.getInstance().getAttachProtocol(197);
        if (componentRunningFastMotion.isClosed()) {
            componentRunningFastMotion.setClosed(false);
            bottomMenuProtocol.onSwitchFastMotionAction(componentRunningFastMotion);
            if (fastMotionProtocol != null) {
                fastMotionProtocol.show();
                return;
            }
            ModeProtocol.BaseDelegate baseDelegate = (ModeProtocol.BaseDelegate) ModeCoordinatorImpl.getInstance().getAttachProtocol(160);
            if (baseDelegate != null) {
                baseDelegate.delegateEvent(34);
                return;
            }
            return;
        }
        componentRunningFastMotion.setClosed(true);
        if (fastMotionProtocol != null) {
            fastMotionProtocol.dismiss(2, 6);
        }
    }

    @Override // com.android.camera.protocol.ModeProtocol.BottomPopupTips
    public void showIDCardTip(boolean z) {
        this.mNeedShowIDCardTip = z;
        if (currentIsIDCardShow() || z) {
            int i = this.mCurrentMode;
            updateTipImage(i, i, null);
        }
    }

    @Override // com.android.camera.protocol.ModeProtocol.BottomPopupTips
    public boolean showMimojiPanel(int i) {
        MimojiStatusManager2 mimojiStatusManager2 = DataRepository.dataItemLive().getMimojiStatusManager2();
        if (mimojiStatusManager2.getMimojiPanelState() == i) {
            return false;
        }
        mimojiStatusManager2.setMimojiPanelState(i);
        hideAllTipImage();
        ModeProtocol.ConfigChanges configChanges = (ModeProtocol.ConfigChanges) ModeCoordinatorImpl.getInstance().getAttachProtocol(164);
        if (configChanges == null) {
            return true;
        }
        configChanges.showOrHideMimoji();
        return true;
    }

    @Override // com.android.camera.protocol.ModeProtocol.BottomPopupTips
    public void showNearRangeTip() {
        if (this.mNearRangeModeButton.getVisibility() != 0) {
            directHideLyingDirectHint();
            hideTip(this.mTipMessage);
            hideQrCodeTip();
            ViewGroup.MarginLayoutParams marginLayoutParams = (ViewGroup.MarginLayoutParams) this.mNearRangeModeButton.getLayoutParams();
            int tipBottomMargin = getTipBottomMargin(this.mNearRangeModeButton.getBackground().getIntrinsicHeight());
            if (this.mCurrentMode == 165) {
                marginLayoutParams.bottomMargin = tipBottomMargin;
            } else {
                marginLayoutParams.bottomMargin = tipBottomMargin + getResources().getDimensionPixelSize(R.dimen.btn_bottom_capsule_tip_bottom_margin);
            }
            this.mNearRangeModeButton.setLayoutParams(marginLayoutParams);
            AlphaInOnSubscribe.directSetResult(this.mNearRangeModeButton);
            if (Util.isAccessible()) {
                this.mNearRangeModeButton.postDelayed(new Runnable() {
                    /* class com.android.camera.fragment.FragmentBottomPopupTips.AnonymousClass5 */

                    public void run() {
                        FragmentBottomPopupTips.this.mNearRangeModeButton.sendAccessibilityEvent(128);
                    }
                }, 100);
            }
        }
    }

    @Override // com.android.camera.protocol.ModeProtocol.BottomPopupTips
    public void showOrHideCloseImage(boolean z) {
        ImageView imageView = this.mLeftTipImage;
        if (imageView != null) {
            imageView.setImageResource(R.drawable.ic_vector_manually_indicator);
            if (z) {
                if (Util.isAccessible() && this.mCloseType == 2) {
                    this.mLeftTipImage.setContentDescription(getString(R.string.accessibility_lighting_panel_off));
                }
                this.mLeftTipImage.setTag(20);
                Completable.create(new AlphaInOnSubscribe(this.mLeftTipImage)).subscribe();
                return;
            }
            this.mLeftTipImage.setTag(-1);
            Completable.create(new AlphaOutOnSubscribe(this.mLeftTipImage)).subscribe();
        }
    }

    @Override // com.android.camera.protocol.ModeProtocol.BottomPopupTips
    public void showOrHideMimojiPanel() {
        DataRepository.dataItemLive().getMimojiStatusManager().setMimojiPannelState(true);
        hideAllTipImage();
        ModeProtocol.ConfigChanges configChanges = (ModeProtocol.ConfigChanges) ModeCoordinatorImpl.getInstance().getAttachProtocol(164);
        if (configChanges != null) {
            configChanges.showOrHideMimoji();
        }
    }

    @Override // com.android.camera.protocol.ModeProtocol.BottomPopupTips
    public void showOrHideVideoBeautyPanel() {
        hideAllTipImage();
        ModeProtocol.MiBeautyProtocol miBeautyProtocol = (ModeProtocol.MiBeautyProtocol) ModeCoordinatorImpl.getInstance().getAttachProtocol(194);
        if (miBeautyProtocol != null) {
            miBeautyProtocol.show();
        } else {
            ModeProtocol.BaseDelegate baseDelegate = (ModeProtocol.BaseDelegate) ModeCoordinatorImpl.getInstance().getAttachProtocol(160);
            if (baseDelegate != null) {
                baseDelegate.delegateEvent(2);
            }
        }
        ((ModeProtocol.BottomMenuProtocol) ModeCoordinatorImpl.getInstance().getAttachProtocol(197)).expandShineBottomMenu(DataRepository.dataItemRunning().getComponentRunningShine());
    }

    @Override // com.android.camera.protocol.ModeProtocol.BottomPopupTips
    public void showQrCodeTip() {
        if (!reConfigQrCodeTip() && this.mQrCodeButton.getVisibility() != 0) {
            AlphaInOnSubscribe.directSetResult(this.mQrCodeButton);
            if (Util.isAccessible()) {
                this.mQrCodeButton.postDelayed(new Runnable() {
                    /* class com.android.camera.fragment.FragmentBottomPopupTips.AnonymousClass4 */

                    public void run() {
                        FragmentBottomPopupTips.this.mQrCodeButton.sendAccessibilityEvent(128);
                    }
                }, 100);
            }
        }
    }

    @Override // com.android.camera.protocol.ModeProtocol.BottomPopupTips
    public void showTips(int i, @StringRes int i2, int i3) {
        showTips(i, getString(i2), i3);
    }

    @Override // com.android.camera.protocol.ModeProtocol.BottomPopupTips
    public void showTips(final int i, final int i2, final int i3, int i4) {
        this.mHandler.postDelayed(new Runnable() {
            /* class com.android.camera.fragment.FragmentBottomPopupTips.AnonymousClass3 */

            public void run() {
                FragmentBottomPopupTips.this.showTips(i, i2, i3);
            }
        }, (long) i4);
    }

    @Override // com.android.camera.protocol.ModeProtocol.BottomPopupTips
    public void showTips(int i, String str, int i2) {
        int i3;
        ModeProtocol.CameraModuleSpecial cameraModuleSpecial;
        if (!isNearRangeTipShowing()) {
            if (!isResumed()) {
                Log.w(getTag(), "current fragment is not resumed");
            } else if (i != 6 || (this.mCurrentMode == 171 && CameraSettings.getCameraId() != 1)) {
                int i4 = this.mCurrentMode;
                if ((i4 != 171 && i4 != 169 && i4 != 162 && i4 != 180) || this.mTipMessage.getVisibility() == 8 || this.mCurrentTipType != 21) {
                    if (i != 10 || CameraSettings.getBogusCameraId() != 0) {
                        if (isPortraitSuccessHintVisible()) {
                            hideTip(this.mPortraitSuccessHint);
                        }
                        this.mLastTipType = this.mCurrentTipType;
                        this.mLastTipMessage = this.mCurrentTipMessage;
                        this.mCurrentTipType = i;
                        this.mCurrentTipMessage = str;
                        hideTip(this.mQrCodeButton);
                        directHideLyingDirectHint();
                        reIntTipViewMarginBottom(this.mTipMessage, this.mBottomTipHeight);
                        AlphaInOnSubscribe.directSetResult(this.mTipMessage);
                        this.mTipMessage.setText(str);
                        if (Util.isAccessible()) {
                            this.mTipMessage.setContentDescription(this.mCurrentTipMessage);
                            this.mTipMessage.postDelayed(new Runnable() {
                                /* class com.android.camera.fragment.FragmentBottomPopupTips.AnonymousClass2 */

                                public void run() {
                                    if (FragmentBottomPopupTips.this.isAdded()) {
                                        FragmentBottomPopupTips.this.mTipMessage.sendAccessibilityEvent(4);
                                    }
                                }
                            }, 3000);
                        }
                        switch (i2) {
                            case 1:
                                i3 = 1000;
                                break;
                            case 2:
                                i3 = 5000;
                                break;
                            case 3:
                                i3 = 15000;
                                break;
                            case 4:
                            default:
                                i3 = 0;
                                break;
                            case 5:
                                i3 = 2000;
                                break;
                            case 6:
                                i3 = 3000;
                                break;
                            case 7:
                                i3 = DurationConstant.DURATION_LANDSCAPE_HINT;
                                break;
                        }
                        this.mHandler.removeCallbacksAndMessages(null);
                        if (i3 > 0) {
                            this.mHandler.sendEmptyMessageDelayed(1, (long) i3);
                        }
                        if (this.mCurrentMode == 163 && (cameraModuleSpecial = (ModeProtocol.CameraModuleSpecial) ModeCoordinatorImpl.getInstance().getAttachProtocol(195)) != null) {
                            cameraModuleSpecial.showOrHideChip(false);
                        }
                    }
                }
            }
        }
    }

    @Override // com.android.camera.protocol.ModeProtocol.BottomPopupTips
    public void showTipsWithOrientation(int i, int i2, int i3, int i4, int i5) {
        if (i5 == 0) {
            showTips(i, i2, i3, i4);
        } else if (i5 != 1) {
            if (i5 == 2 && !isLandScape()) {
                showTips(i, i2, i3, i4);
            }
        } else if (isLandScape()) {
            showTips(i, i2, i3, i4);
        }
    }

    /* access modifiers changed from: protected */
    @Override // com.android.camera.fragment.BaseFragment
    public void unRegister(ModeProtocol.ModeCoordinator modeCoordinator) {
        super.unRegister(modeCoordinator);
        this.mHandler.removeCallbacksAndMessages(null);
        modeCoordinator.detachProtocol(175, this);
        unRegisterBackStack(modeCoordinator, this);
        this.mIsShowLeftImageIntro = false;
    }

    public void updateLeftRightTipImageForCinematic(boolean z) {
        FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) this.mTipImage.getLayoutParams();
        FrameLayout.LayoutParams layoutParams2 = (FrameLayout.LayoutParams) this.mLeftTipImage.getLayoutParams();
        if (z) {
            if (layoutParams.rightMargin != Util.getCinematicAspectRatioMargin()) {
                layoutParams.rightMargin = Util.getCinematicAspectRatioMargin();
                this.mTipImage.setLayoutParams(layoutParams);
            }
            if (layoutParams2.leftMargin != Util.getCinematicAspectRatioMargin()) {
                layoutParams2.leftMargin = Util.getCinematicAspectRatioMargin();
                this.mLeftTipImage.setLayoutParams(layoutParams2);
                return;
            }
            return;
        }
        if (layoutParams.rightMargin != 0) {
            layoutParams.rightMargin = (int) getResources().getDimension(R.dimen.popup_indicator_button_extra_margin_end);
            this.mTipImage.setLayoutParams(layoutParams);
        }
        if (layoutParams2.leftMargin != 0) {
            layoutParams2.leftMargin = 0;
            this.mLeftTipImage.setLayoutParams(layoutParams2);
        }
    }

    @Override // com.android.camera.protocol.ModeProtocol.BottomPopupTips
    public void updateLeftTipImage() {
        int i = this.mCurrentMode;
        updateLeftTipImage(1, i, i, null);
    }

    @Override // com.android.camera.protocol.ModeProtocol.LyingDirectHint
    public void updateLyingDirectHint(boolean z, boolean z2) {
        if (!z2) {
            this.mIsShowLyingDirectHint = z;
        }
        if (this.mIsShowLyingDirectHint) {
            ModeProtocol.DualController dualController = (ModeProtocol.DualController) ModeCoordinatorImpl.getInstance().getAttachProtocol(182);
            boolean z3 = true;
            boolean z4 = dualController != null && dualController.isZoomPanelVisible();
            ModeProtocol.MakeupProtocol makeupProtocol = (ModeProtocol.MakeupProtocol) ModeCoordinatorImpl.getInstance().getAttachProtocol(180);
            boolean z5 = makeupProtocol != null && makeupProtocol.isSeekBarVisible();
            ModeProtocol.MiBeautyProtocol miBeautyProtocol = (ModeProtocol.MiBeautyProtocol) ModeCoordinatorImpl.getInstance().getAttachProtocol(194);
            if (miBeautyProtocol == null || !miBeautyProtocol.isBeautyPanelShow()) {
                z3 = false;
            }
            if (isNearRangeTipShowing() || isTipShowing() || z4 || z5 || z3 || this.mLyingDirectHint.getVisibility() == 0) {
                this.mLyingDirectHint.setVisibility(8);
                return;
            }
            this.mLyingDirectHint.setRotation(180.0f);
            reIntTipViewMarginBottom(this.mLyingDirectHint, this.mBottomTipHeight);
            this.mLyingDirectHint.setVisibility(0);
            CameraStatUtils.trackLyingDirectShow(180);
        } else if (this.mLyingDirectHint.getVisibility() == 0) {
            this.mLyingDirectHint.setVisibility(8);
        }
    }

    @Override // com.android.camera.protocol.ModeProtocol.BottomPopupTips
    public void updateMimojiBottomTipImage() {
        int i = this.mCurrentMode;
        updateLeftTipImage(1, i, i, null);
        int i2 = this.mCurrentMode;
        updateLeftTipExtraImage(i2, i2, null);
        updateTipImage();
    }

    @Override // com.android.camera.protocol.ModeProtocol.BottomPopupTips
    public void updateTipBottomMargin(int i, boolean z) {
        if (this.mRootView.getPaddingTop() < i) {
            this.mRootView.setPadding(0, (int) (((float) i) * 1.2f), 0, 0);
        }
        if (!z) {
            TranslateYOnSubscribe.directSetResult(this.mRootView, -i);
        } else if (((float) i) < ViewCompat.getTranslationY(this.mRootView)) {
            Completable.create(new TranslateYOnSubscribe(this.mRootView, -i).setInterpolator(new OvershootInterpolator())).subscribe();
        } else {
            Completable.create(new TranslateYOnSubscribe(this.mRootView, -i).setInterpolator(new BackEaseOutInterpolator())).subscribe();
        }
    }

    @Override // com.android.camera.protocol.ModeProtocol.BottomPopupTips
    public void updateTipImage() {
        int i = this.mCurrentMode;
        updateTipImage(i, i, null);
    }
}
