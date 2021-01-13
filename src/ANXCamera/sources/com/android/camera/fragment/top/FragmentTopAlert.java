package com.android.camera.fragment.top;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.LayoutTransition;
import android.animation.ObjectAnimator;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.animation.AlphaAnimation;
import android.view.animation.DecelerateInterpolator;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.core.graphics.drawable.DrawableCompat;
import androidx.core.view.GravityCompat;
import androidx.core.view.ViewCompat;
import androidx.fragment.app.DialogFragment;
import b.c.a.c;
import com.airbnb.lottie.LottieAnimationView;
import com.android.camera.ActivityBase;
import com.android.camera.CameraSettings;
import com.android.camera.HybridZoomingSystem;
import com.android.camera.R;
import com.android.camera.RotateDialogController;
import com.android.camera.ToastUtils;
import com.android.camera.Util;
import com.android.camera.animation.FolmeUtils;
import com.android.camera.animation.type.AlphaInOnSubscribe;
import com.android.camera.animation.type.AlphaOutOnSubscribe;
import com.android.camera.customization.BGTintTextView;
import com.android.camera.customization.TintColor;
import com.android.camera.data.DataRepository;
import com.android.camera.data.data.ComponentData;
import com.android.camera.data.data.config.ComponentConfigRatio;
import com.android.camera.data.data.runing.ComponentRunningDocument;
import com.android.camera.data.data.runing.DataItemRunning;
import com.android.camera.effect.FilterInfo;
import com.android.camera.fragment.BaseFragment;
import com.android.camera.fragment.FragmentUtils;
import com.android.camera.fragment.fastmotion.FragmentFastMotionDescription;
import com.android.camera.fragment.manually.FragmentAmbilightDescription;
import com.android.camera.fragment.manually.FragmentParameterDescription;
import com.android.camera.log.Log;
import com.android.camera.module.BaseModule;
import com.android.camera.module.ModuleManager;
import com.android.camera.module.VideoModule;
import com.android.camera.module.loader.camera2.Camera2DataContainer;
import com.android.camera.protocol.ModeCoordinatorImpl;
import com.android.camera.protocol.ModeProtocol;
import com.android.camera.statistic.CameraStatUtils;
import com.android.camera.statistic.MistatsConstants;
import com.android.camera.statistic.MistatsWrapper;
import com.android.camera.timerburst.TimerBurstController;
import com.android.camera.ui.AudioZoomIndicator;
import com.android.camera.ui.FastmotionIndicatorView;
import com.android.camera.ui.HistogramView;
import com.android.camera.ui.MutiStateButton;
import com.android.camera.ui.ToggleSwitch;
import com.android.camera.ui.TopAlertSlideSwitchButton;
import com.android.camera.ui.VideoTagView;
import io.reactivex.Completable;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import miui.view.animation.SineEaseOutInterpolator;

public class FragmentTopAlert extends BaseFragment implements View.OnClickListener {
    public static final int FRAGMENT_INFO;
    public static final long HINT_DELAY_TIME_3S;
    private static final String TAG;
    public static final String TIP_8K_DESC;
    public static final String TIP_AI;
    public static final String TIP_AI_AUDIO;
    public static final String TIP_AI_WATERMARK;
    public static final String TIP_AUTO_ZOOM;
    public static final String TIP_COLOR_ENHANCE;
    public static final String TIP_COLOR_MODE;
    public static final String TIP_FLASH;
    public static final String TIP_HAND_GESTURE;
    public static final String TIP_HAND_GESTURE_DESC;
    public static final String TIP_HDR;
    public static final String TIP_LIVE_SHOT;
    public static final String TIP_MACRO;
    public static final String TIP_METER;
    public static final String TIP_RECOMMEND_ULTRA_WIDE_DESC;
    public static final String TIP_SPEECH_SHUTTER_DESC;
    public static final String TIP_SUBTITLE;
    public static final String TIP_SUPER_EIS;
    public static final String TIP_SUPER_EIS_PRO;
    public static final String TIP_TIMER_BURST;
    public static final String TIP_ULTRA_PIXEL;
    public static final String TIP_ULTRA_PIXEL_PORTRAIT;
    public static final String TIP_ULTRA_WIDE_BOKEH;
    public static final String TIP_UNKNOW;
    public static final String TIP_VIDEO_BEAUTIFY;
    public static final String TIP_VIDEO_BOKEH;
    private static int sPendingRecordingTimeState;
    private BGTintTextView mAiAudioBGTip;
    public final Runnable mAiAudioBGTipHideRunnable = new TopAlertRunnable() {
        /* class com.android.camera.fragment.top.FragmentTopAlert.AnonymousClass9 */

        @Override // com.android.camera.fragment.top.FragmentTopAlert.TopAlertRunnable
        public void runToSafe() {
            FragmentTopAlert fragmentTopAlert = FragmentTopAlert.this;
            fragmentTopAlert.removeViewToToastLayout(fragmentTopAlert.mAiAudioBGTip);
        }
    };
    private TextView mAiAudioTip;
    private ToggleSwitch mAiSceneSelectView;
    private Runnable mAlertAiDetectTipHitRunable = new TopAlertRunnable() {
        /* class com.android.camera.fragment.top.FragmentTopAlert.AnonymousClass11 */

        @Override // com.android.camera.fragment.top.FragmentTopAlert.TopAlertRunnable
        public void runToSafe() {
            FragmentTopAlert fragmentTopAlert = FragmentTopAlert.this;
            fragmentTopAlert.removeViewToTipLayout(fragmentTopAlert.mRecommendTip);
        }
    };
    private long mAlertAiSceneSwitchHintTime;
    private AlertDialog mAlertDialog;
    private int mAlertImageType = -1;
    private Runnable mAlertRecommendDescRunable = new AlertRecommendDescRunable();
    private TextView mAlertRecordingText;
    private TextView mAlertRecordingTextDenominator;
    private TextView mAlertRecordingTextNumerator;
    private Runnable mAlertTopHintHideRunnable = new Runnable() {
        /* class com.android.camera.fragment.top.FragmentTopAlert.AnonymousClass10 */

        public void run() {
            FragmentTopAlert fragmentTopAlert = FragmentTopAlert.this;
            fragmentTopAlert.removeViewToToastLayout(fragmentTopAlert.mPermanentTip);
        }
    };
    private AudioZoomIndicator mAudioZoomIndicator = null;
    private AlphaAnimation mBlingAnimation;
    private LayoutTransition mCustomStubTransition;
    private LayoutTransition mCustomToastTransition;
    private MutiStateButton mDocumentStateButton;
    private LinkedHashMap<String, Integer> mDocumentStateMaps;
    private LinearLayout mEndGravityTipLayout;
    private FastmotionIndicatorView mFastmotionIndicatorView;
    private LinearLayout mFastmotionTip;
    private TextView mFastmotionTipDesc;
    private TextView mFastmotionTipTitle;
    public final Runnable mFastmotionTipToResetRunnable = new TopAlertRunnable() {
        /* class com.android.camera.fragment.top.FragmentTopAlert.AnonymousClass6 */

        @Override // com.android.camera.fragment.top.FragmentTopAlert.TopAlertRunnable
        public void runToSafe() {
            FragmentTopAlert fragmentTopAlert = FragmentTopAlert.this;
            fragmentTopAlert.removeViewToTipLayout(fragmentTopAlert.mFastmotionTip);
        }
    };
    private BGTintTextView mHandGestureTip;
    private Handler mHandler;
    private HistogramView mHistogramView;
    private boolean mIsAlertAnim = true;
    private boolean mIsParameterDescriptionVisibleBeforeRecording;
    private boolean mIsParameterResetVisibleBeforeRecording;
    private boolean mIsVideoRecording;
    private boolean mIsVideoUltraClearTipVisibleBeforeProVideoSimple;
    private TextView mLiveMusiHintText;
    private ImageView mLiveMusicClose;
    private LinearLayout mLiveMusicHintLayout;
    private LinearLayout mLlAlertRecordingTimeView;
    private TextView mLyingDirectHintText;
    private BGTintTextView mMacroModeTip;
    public final Runnable mMacroModeTipHideRunnable = new TopAlertRunnable() {
        /* class com.android.camera.fragment.top.FragmentTopAlert.AnonymousClass7 */

        @Override // com.android.camera.fragment.top.FragmentTopAlert.TopAlertRunnable
        public void runToSafe() {
            FragmentTopAlert fragmentTopAlert = FragmentTopAlert.this;
            fragmentTopAlert.removeViewToToastLayout(fragmentTopAlert.mMacroModeTip);
        }
    };
    private ImageView mManualParameterDescriptionTip;
    private ImageView mManualParameterResetTip;
    private View.OnClickListener mOnClickListener = new e(this);
    private BGTintTextView mPermanentTip;
    private BGTintTextView mProColourTip;
    private LottieAnimationView mProVideoRecordingSimpleView;
    private TextView mRecommendTip;
    private String mShortDurationDescriptionTip = TIP_UNKNOW;
    private String mShortDurationToastTip = TIP_UNKNOW;
    private boolean mShow;
    private Runnable mShowAction = new TopAlertRunnable() {
        /* class com.android.camera.fragment.top.FragmentTopAlert.AnonymousClass3 */

        @Override // com.android.camera.fragment.top.FragmentTopAlert.TopAlertRunnable
        public void runToSafe() {
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(-2, FragmentTopAlert.this.getResources().getDimensionPixelOffset(R.dimen.ai_scene_selector_layout_height));
            FragmentTopAlert fragmentTopAlert = FragmentTopAlert.this;
            fragmentTopAlert.addViewToTipLayout(fragmentTopAlert.mAiSceneSelectView, true, layoutParams, -1);
        }
    };
    private Runnable mShowSlideSwitchLayout = new TopAlertRunnable() {
        /* class com.android.camera.fragment.top.FragmentTopAlert.AnonymousClass16 */

        @Override // com.android.camera.fragment.top.FragmentTopAlert.TopAlertRunnable
        public void runToSafe() {
            if (FragmentTopAlert.this.mSlideSwitchButton != null) {
                FragmentTopAlert.this.mSlideSwitchButton.setBackgroundColor(((BaseFragment) FragmentTopAlert.this).mCurrentMode == 165 ? 872415231 : 855638016);
            }
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(-2, FragmentTopAlert.this.getResources().getDimensionPixelOffset(R.dimen.tilt_selector_layout_height));
            FragmentTopAlert fragmentTopAlert = FragmentTopAlert.this;
            fragmentTopAlert.addViewToTipLayout(fragmentTopAlert.mSlideSwitchButton, true, layoutParams, -1);
        }
    };
    private TopAlertSlideSwitchButton mSlideSwitchButton;
    private LinearLayout mStartGravityTipLayout;
    private BGTintTextView mSubtitleTip;
    private BGTintTextView mSuperNightSeTip;
    public final Runnable mTimerBurstRunnable = new TopAlertRunnable() {
        /* class com.android.camera.fragment.top.FragmentTopAlert.AnonymousClass8 */

        @Override // com.android.camera.fragment.top.FragmentTopAlert.TopAlertRunnable
        public void runToSafe() {
            FragmentTopAlert fragmentTopAlert = FragmentTopAlert.this;
            fragmentTopAlert.removeViewToToastLayout(fragmentTopAlert.mTimerBurstTip);
        }
    };
    private BGTintTextView mTimerBurstTip;
    private BGTintTextView mToastSwitchTip;
    private ImageView mToastTipFlash;
    private LinearLayout mToastTopTipLayout;
    private int mTopHintTextResource = 0;
    private LinearLayout mTopTipLayout;
    private VideoTagView mVideoTagView;
    private TextView mVideoUltraClearTip;
    public final Runnable mViewHideRunnable = new TopAlertRunnable() {
        /* class com.android.camera.fragment.top.FragmentTopAlert.AnonymousClass4 */

        @Override // com.android.camera.fragment.top.FragmentTopAlert.TopAlertRunnable
        public void runToSafe() {
            FragmentTopAlert.this.mShortDurationToastTip = FragmentTopAlert.TIP_UNKNOW;
            FragmentTopAlert fragmentTopAlert = FragmentTopAlert.this;
            fragmentTopAlert.removeViewToToastLayout(fragmentTopAlert.mToastSwitchTip);
        }
    };
    private TextView mZoomTip;
    public final Runnable mZoomTipToResetRunnable = new TopAlertRunnable() {
        /* class com.android.camera.fragment.top.FragmentTopAlert.AnonymousClass5 */

        @Override // com.android.camera.fragment.top.FragmentTopAlert.TopAlertRunnable
        public void runToSafe() {
            FragmentTopAlert.this.alertZoom(true);
            FragmentTopAlert.this.alertAudioZoomIndicator(true);
        }
    };

    public class AlertRecommendDescRunable extends TopAlertRunnable {
        String tipType = null;

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        public AlertRecommendDescRunable() {
            super();
            FragmentTopAlert.this = r2;
        }

        @Override // com.android.camera.fragment.top.FragmentTopAlert.TopAlertRunnable
        public /* bridge */ /* synthetic */ void run() {
            super.run();
        }

        @Override // com.android.camera.fragment.top.FragmentTopAlert.TopAlertRunnable
        public void runToSafe() {
            String str;
            FragmentTopAlert.this.mShortDurationDescriptionTip = FragmentTopAlert.TIP_UNKNOW;
            FragmentTopAlert fragmentTopAlert = FragmentTopAlert.this;
            fragmentTopAlert.removeViewToTipLayout(fragmentTopAlert.mRecommendTip);
            ModeProtocol.TopAlert topAlert = (ModeProtocol.TopAlert) ModeCoordinatorImpl.getInstance().getAttachProtocol(172);
            if (!(topAlert == null || (str = this.tipType) == null)) {
                topAlert.setTipsState(str, false);
            }
            ModeProtocol.TopAlertEvent topAlertEvent = (ModeProtocol.TopAlertEvent) ModeCoordinatorImpl.getInstance().getAttachProtocol(673);
            if (topAlertEvent != null) {
                topAlertEvent.onRecommendDescDismiss();
            }
        }

        public void setTipType(String str) {
            this.tipType = str;
        }
    }

    public @interface PermanentToastTip {
    }

    public @interface ShortDurationDescriptionTip {
    }

    public @interface ShortDurationToastTip {
    }

    /* access modifiers changed from: private */
    public abstract class TopAlertRunnable implements Runnable {
        private TopAlertRunnable() {
            FragmentTopAlert.this = r1;
        }

        public void run() {
            if (FragmentTopAlert.this.isAdded()) {
                runToSafe();
            }
        }

        /* access modifiers changed from: package-private */
        public abstract void runToSafe();
    }

    static /* synthetic */ void a(ToggleSwitch toggleSwitch, boolean z) {
        ModeProtocol.ConfigChanges configChanges = (ModeProtocol.ConfigChanges) ModeCoordinatorImpl.getInstance().getAttachProtocol(164);
        if (z) {
            if (configChanges != null) {
                configChanges.onConfigChanged(248);
            }
        } else if (configChanges != null) {
            configChanges.onConfigChanged(249);
        }
    }

    private void addTextViewShadowStyle(View view) {
        if (!(view instanceof BGTintTextView) && (view instanceof TextView)) {
            ((TextView) view).setTextAppearance(R.style.ShadowStyle);
        }
    }

    private void addViewToTipLayout(View view) {
        addViewToTipLayout(view, true, null, -1);
    }

    private void addViewToTipLayout(View view, Runnable runnable, Runnable runnable2) {
        addViewToTipLayout(view, true, null, runnable, runnable2);
    }

    private void addViewToTipLayout(View view, boolean z, LinearLayout.LayoutParams layoutParams, int i) {
        LinearLayout linearLayout;
        if (view != null && (linearLayout = this.mTopTipLayout) != null && linearLayout.indexOfChild(view) == -1) {
            if (!z || !this.mIsAlertAnim) {
                this.mTopTipLayout.setLayoutTransition(null);
            } else if (this.mTopTipLayout.getLayoutTransition() != customStubViewTransition()) {
                this.mTopTipLayout.setLayoutTransition(customStubViewTransition());
            }
            addTextViewShadowStyle(view);
            if (i < 0) {
                try {
                    this.mTopTipLayout.addView(view);
                } catch (Exception unused) {
                    Log.w(TAG, "The specified child already has a parent. You must call removeView() on the child's parent first");
                }
            } else {
                this.mTopTipLayout.addView(view, i);
            }
            if (layoutParams == null) {
                layoutParams = new LinearLayout.LayoutParams(-2, -2);
            }
            view.setLayoutParams(layoutParams);
            checkChildMargin();
        }
    }

    private void addViewToTipLayout(View view, boolean z, LinearLayout.LayoutParams layoutParams, Runnable runnable, Runnable runnable2) {
        LinearLayout linearLayout;
        if (view != null && (linearLayout = this.mTopTipLayout) != null && linearLayout.indexOfChild(view) == -1) {
            if (!z || !this.mIsAlertAnim) {
                this.mTopTipLayout.setLayoutTransition(null);
            } else if (this.mTopTipLayout.getLayoutTransition() != customStubViewTransition()) {
                this.mTopTipLayout.setLayoutTransition(customStubViewTransition());
            }
            if (runnable != null) {
                runnable.run();
            }
            addTextViewShadowStyle(view);
            try {
                this.mTopTipLayout.addView(view);
            } catch (IllegalStateException unused) {
                Log.w(TAG, "The specified child already has a parent. You must call removeView() on the child's parent first");
            }
            if (layoutParams == null) {
                layoutParams = new LinearLayout.LayoutParams(-2, -2);
            }
            view.setLayoutParams(layoutParams);
            if (runnable2 != null) {
                runnable2.run();
            }
            checkChildMargin();
        }
    }

    private void addViewToToastLayout(View view) {
        addViewToToastLayout(view, true, -1);
    }

    private void addViewToToastLayout(View view, boolean z) {
        addViewToToastLayout(view, z, -1);
    }

    private void addViewToToastLayout(View view, boolean z, int i) {
        LinearLayout linearLayout;
        if (view != null && (linearLayout = this.mToastTopTipLayout) != null && linearLayout.indexOfChild(view) == -1) {
            if (!z || !this.mIsAlertAnim) {
                setToastTipLayoutParams(false);
                this.mTopTipLayout.setLayoutTransition(null);
            } else {
                if (this.mToastTopTipLayout.getLayoutTransition() == null || this.mToastTopTipLayout.getLayoutTransition() != customToastLayoutTransition()) {
                    setToastTipLayoutParams(true);
                }
                if (this.mTopTipLayout.getLayoutTransition() != customStubViewTransition()) {
                    this.mTopTipLayout.setLayoutTransition(customStubViewTransition());
                }
            }
            addTextViewShadowStyle(view);
            if (i < 0) {
                this.mToastTopTipLayout.addView(view);
            } else {
                this.mToastTopTipLayout.addView(view, i);
            }
            LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) view.getLayoutParams();
            layoutParams.width = -2;
            layoutParams.height = -2;
            view.setLayoutParams(layoutParams);
            if (this.mToastTopTipLayout.getChildCount() > 0) {
                this.mToastTopTipLayout.setVisibility(0);
            }
            checkChildMargin();
        }
    }

    private void alertAiSceneSelector(String str, String str2, int i, ToggleSwitch.OnCheckedChangeListener onCheckedChangeListener, boolean z) {
        if (!TextUtils.isEmpty(str) && !TextUtils.isEmpty(str2)) {
            this.mAiSceneSelectView.setTextOnAndOff(str, str2);
        }
        if (i == 0) {
            long currentTimeMillis = 3000 - (System.currentTimeMillis() - this.mAlertAiSceneSwitchHintTime);
            if (CameraSettings.getShaderEffect() == FilterInfo.FILTER_ID_NONE) {
                Handler handler = this.mHandler;
                Runnable runnable = this.mShowAction;
                if (currentTimeMillis < 0) {
                    currentTimeMillis = 0;
                }
                handler.postDelayed(runnable, currentTimeMillis);
            }
        } else {
            this.mTopTipLayout.removeCallbacks(this.mShowAction);
            removeViewToTipLayout(this.mAiSceneSelectView);
        }
        this.mAiSceneSelectView.setOnCheckedChangeListener(onCheckedChangeListener);
        this.mAiSceneSelectView.setChecked(z);
    }

    private void alertAudioZoomIndicator(boolean z) {
        BaseModule baseModule;
        if (DataRepository.dataItemFeature().cj()) {
            if (z) {
                removeViewToTipLayout(this.mAudioZoomIndicator);
            } else if (!Util.isWiredHeadsetOn() && (baseModule = (BaseModule) ((ActivityBase) getActivity()).getCurrentModule()) != null && baseModule.isAlive()) {
                VideoModule videoModule = null;
                if (baseModule instanceof VideoModule) {
                    videoModule = (VideoModule) baseModule;
                }
                if (videoModule != null && videoModule.isNeedAlertAudioZoomIndicator()) {
                    if (this.mAudioZoomIndicator == null) {
                        this.mAudioZoomIndicator = new AudioZoomIndicator(getContext());
                    }
                    float minZoomRatio = videoModule.getMinZoomRatio();
                    float maxZoomRatio = videoModule.getMaxZoomRatio();
                    float zoomRatio = videoModule.getZoomRatio();
                    if (this.mTopTipLayout.indexOfChild(this.mAudioZoomIndicator) != -1 && this.mAudioZoomIndicator.getVisibility() == 0) {
                        this.mAudioZoomIndicator.update(minZoomRatio, maxZoomRatio, zoomRatio);
                        return;
                    }
                    LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(-2, -2);
                    layoutParams.gravity = 1;
                    addViewToTipLayout(this.mAudioZoomIndicator, true, layoutParams, -1);
                }
            }
        }
    }

    private int alertTintColor() {
        return TintColor.isYellowTintColor() ? TintColor.tintColor() : getResources().getColor(R.color.white);
    }

    private void alertZoom(boolean z) {
        boolean isMacroModeEnabled = CameraSettings.isMacroModeEnabled(this.mCurrentMode);
        ModeProtocol.MainContentProtocol mainContentProtocol = (ModeProtocol.MainContentProtocol) ModeCoordinatorImpl.getInstance().getAttachProtocol(166);
        ModeProtocol.DualController dualController = (ModeProtocol.DualController) ModeCoordinatorImpl.getInstance().getAttachProtocol(182);
        if (dualController == null || ((dualController != null && dualController.isButtonVisible()) || (mainContentProtocol != null && mainContentProtocol.isZoomAdjustVisible()))) {
            removeViewToTipLayout(this.mZoomTip);
            return;
        }
        ModeProtocol.CameraAction cameraAction = (ModeProtocol.CameraAction) ModeCoordinatorImpl.getInstance().getAttachProtocol(161);
        if (169 == DataRepository.dataItemGlobal().getCurrentMode() && cameraAction != null && !cameraAction.isRecording() && DataRepository.dataItemFeature().uj()) {
            removeViewToTipLayout(this.mZoomTip);
        } else if (204 == this.mCurrentMode) {
            removeViewToTipLayout(this.mZoomTip);
        } else {
            String zoomRatioTipText = getZoomRatioTipText(isMacroModeEnabled);
            if (zoomRatioTipText == null || TextUtils.isEmpty(zoomRatioTipText)) {
                removeViewToTipLayout(this.mZoomTip);
                return;
            }
            this.mZoomTip.setText(zoomRatioTipText);
            if (z) {
                ObjectAnimator.ofFloat(this.mZoomTip, "textSize", Util.pixelTodp(70.0f), Util.pixelTodp(43.0f)).setDuration(300L).start();
            } else {
                this.mZoomTip.setTextSize(Util.pixelTodp(43.0f));
            }
            if (this.mTopTipLayout.indexOfChild(this.mZoomTip) == -1 || this.mZoomTip.getVisibility() != 0) {
                addViewToTipLayout(this.mZoomTip);
            }
        }
    }

    static /* synthetic */ void b(ToggleSwitch toggleSwitch, boolean z) {
        ModeProtocol.ConfigChanges configChanges = (ModeProtocol.ConfigChanges) ModeCoordinatorImpl.getInstance().getAttachProtocol(164);
        if (z) {
            if (configChanges != null) {
                configChanges.onConfigChanged(246);
            }
        } else if (configChanges != null) {
            configChanges.onConfigChanged(247);
        }
    }

    private void checkChildMargin() {
        if ((DataRepository.dataItemFeature().qj() || DataRepository.dataItemFeature().rj()) && this.mTopTipLayout != null && this.mDocumentStateButton != null && this.mToastTopTipLayout != null) {
            int dimensionPixelSize = getResources().getDimensionPixelSize(R.dimen.top_tip_vertical_divider_support_document);
            int dimensionPixelSize2 = getResources().getDimensionPixelSize(R.dimen.top_tip_margin_top_support_document);
            boolean z = true;
            boolean z2 = this.mToastTopTipLayout.getChildCount() > 0;
            if (this.mTopTipLayout.indexOfChild(this.mDocumentStateButton) == -1) {
                z = false;
            }
            ViewGroup.MarginLayoutParams marginLayoutParams = (ViewGroup.MarginLayoutParams) this.mTopTipLayout.getLayoutParams();
            ViewGroup.MarginLayoutParams marginLayoutParams2 = (ViewGroup.MarginLayoutParams) this.mDocumentStateButton.getLayoutParams();
            if (!z) {
                marginLayoutParams.topMargin = getAlertTopMargin();
            } else if (z2) {
                marginLayoutParams.topMargin = getAlertTopMargin();
                marginLayoutParams2.topMargin = dimensionPixelSize2 - dimensionPixelSize;
            } else {
                marginLayoutParams.topMargin = getAlertTopMarginSupportDocument();
                marginLayoutParams2.topMargin = 0;
            }
        }
    }

    private void clearHandlerCallbacks() {
        this.mHandler.removeCallbacks(this.mAlertRecommendDescRunable);
    }

    private LayoutTransition customStubViewTransition() {
        if (this.mCustomStubTransition == null) {
            this.mCustomStubTransition = new LayoutTransition();
            ObjectAnimator ofFloat = ObjectAnimator.ofFloat((Object) null, "alpha", 0.0f, 1.0f);
            ofFloat.setInterpolator(new SineEaseOutInterpolator());
            ObjectAnimator ofFloat2 = ObjectAnimator.ofFloat((Object) null, "alpha", 1.0f, 0.0f);
            ofFloat2.setInterpolator(new SineEaseOutInterpolator());
            this.mCustomStubTransition.setStartDelay(2, 0);
            this.mCustomStubTransition.setDuration(2, 300);
            this.mCustomStubTransition.setAnimator(2, ofFloat);
            this.mCustomStubTransition.setStartDelay(3, 0);
            this.mCustomStubTransition.setDuration(3, 200);
            this.mCustomStubTransition.setAnimator(3, ofFloat2);
        }
        return this.mCustomStubTransition;
    }

    private LayoutTransition customToastLayoutTransition() {
        if (this.mCustomToastTransition == null) {
            ObjectAnimator ofFloat = ObjectAnimator.ofFloat((Object) null, "alpha", 0.0f, 1.0f);
            ofFloat.setInterpolator(new SineEaseOutInterpolator());
            ObjectAnimator ofFloat2 = ObjectAnimator.ofFloat((Object) null, "alpha", 1.0f, 0.0f);
            ofFloat2.setInterpolator(new SineEaseOutInterpolator());
            ofFloat2.addListener(new AnimatorListenerAdapter() {
                /* class com.android.camera.fragment.top.FragmentTopAlert.AnonymousClass1 */

                public void onAnimationCancel(Animator animator) {
                    if (FragmentTopAlert.this.mToastTopTipLayout.getChildCount() <= 0) {
                        FragmentTopAlert.this.mToastTopTipLayout.setVisibility(8);
                    }
                }

                public void onAnimationEnd(Animator animator) {
                    if (FragmentTopAlert.this.mToastTopTipLayout.getChildCount() <= 0) {
                        FragmentTopAlert.this.mToastTopTipLayout.setVisibility(8);
                    }
                }
            });
            this.mCustomToastTransition = new LayoutTransition();
            this.mCustomToastTransition.setStartDelay(2, 0);
            this.mCustomToastTransition.setDuration(2, 300);
            this.mCustomToastTransition.setAnimator(2, ofFloat);
            this.mCustomToastTransition.setStartDelay(3, 0);
            this.mCustomToastTransition.setDuration(3, 200);
            this.mCustomToastTransition.setAnimator(3, ofFloat2);
        }
        return this.mCustomToastTransition;
    }

    private int getAlertStartGravityTipLayoutTopMargin() {
        return Util.getDisplayRect(DataRepository.dataItemGlobal().getDisplayMode() == 2 ? 1 : 0).top + getResources().getDimensionPixelSize(R.dimen.manual_description_tip_top);
    }

    private int getAlertTopMargin() {
        return Util.getDisplayRect(DataRepository.dataItemGlobal().getDisplayMode() == 2 ? 1 : 0).top + getResources().getDimensionPixelSize(R.dimen.video_ultra_tip_margin_top);
    }

    private int getAlertTopMarginSupportDocument() {
        return Util.getDisplayRect(DataRepository.dataItemGlobal().getDisplayMode() == 2 ? 1 : 0).top + getResources().getDimensionPixelSize(R.dimen.top_tip_vertical_divider_support_document);
    }

    private Drawable getDividerDrawable() {
        return (DataRepository.dataItemFeature().qj() || DataRepository.dataItemFeature().rj()) ? getResources().getDrawable(R.drawable.top_tip_vertical_divider_support_document) : getResources().getDrawable(R.drawable.top_tip_vertical_divider);
    }

    private Drawable getEndGravityTipDividerDrawable() {
        return getResources().getDrawable(R.drawable.top_tip_end_vertical_divider);
    }

    /* JADX WARNING: Code restructure failed: missing block: B:66:0x00ee, code lost:
        if (com.android.camera.HybridZoomingSystem.sDefaultMacroOpticalZoomRatio == 1.0f) goto L_0x00f0;
     */
    private String getZoomRatioTipText(boolean z) {
        int i;
        float decimal = HybridZoomingSystem.toDecimal(CameraSettings.readZoom());
        int actualOpenCameraId = Camera2DataContainer.getInstance().getActualOpenCameraId(DataRepository.dataItemGlobal().getCurrentCameraId(), this.mCurrentMode);
        if (DataRepository.dataItemFeature().jj() && CameraSettings.isFrontCamera() && ModuleManager.isSupportCropFrontMode()) {
            return null;
        }
        if (decimal == 1.0f) {
            if (actualOpenCameraId == Camera2DataContainer.getInstance().getMainBackCameraId() || actualOpenCameraId == Camera2DataContainer.getInstance().getSATCameraId() || actualOpenCameraId == Camera2DataContainer.getInstance().getBokehCameraId() || actualOpenCameraId == Camera2DataContainer.getInstance().getUltraWideBokehCameraId() || actualOpenCameraId == Camera2DataContainer.getInstance().getFrontCameraId() || actualOpenCameraId == Camera2DataContainer.getInstance().getBokehFrontCameraId() || actualOpenCameraId == Camera2DataContainer.getInstance().getStandaloneMacroCameraId()) {
                return null;
            }
            if (DataRepository.dataItemFeature().qm() && actualOpenCameraId == Camera2DataContainer.getInstance().getVideoSATCameraId()) {
                return null;
            }
            if (((HybridZoomingSystem.IS_2_SAT || (!HybridZoomingSystem.IS_3_OR_MORE_SAT && !CameraSettings.isSupportedOpticalZoom())) && actualOpenCameraId == Camera2DataContainer.getInstance().getUltraWideCameraId()) || (i = this.mCurrentMode) == 167 || i == 180 || i == 166 || i == 175 || i == 179) {
                return null;
            }
            if (DataRepository.dataItemFeature().gk() && this.mCurrentMode == 172) {
                return null;
            }
            if (actualOpenCameraId == Camera2DataContainer.getInstance().getUltraWideCameraId()) {
                if (z) {
                }
            }
        }
        if (decimal == HybridZoomingSystem.FLOAT_ZOOM_RATIO_ULTR && ((HybridZoomingSystem.IS_3_OR_MORE_SAT && actualOpenCameraId == Camera2DataContainer.getInstance().getUltraWideCameraId()) || actualOpenCameraId == Camera2DataContainer.getInstance().getMainBackCameraId())) {
            return null;
        }
        if (actualOpenCameraId == Camera2DataContainer.getInstance().getAuxCameraId() && decimal <= HybridZoomingSystem.getTeleMinZoomRatio()) {
            return null;
        }
        if ((actualOpenCameraId == Camera2DataContainer.getInstance().getUltraTeleCameraId() && decimal <= HybridZoomingSystem.getUltraTeleMinZoomRatio()) || DataRepository.dataItemRunning().getComponentRunningAIWatermark().getAIWatermarkEnable(this.mCurrentMode)) {
            return null;
        }
        return decimal + "X";
    }

    /* access modifiers changed from: public */
    /* access modifiers changed from: private */
    /* renamed from: handleProVideoRecordingSimple */
    public void g(View view) {
        this.mProVideoRecordingSimpleView.cancelAnimation();
        this.mProVideoRecordingSimpleView.setScale(0.37f);
        this.mProVideoRecordingSimpleView.k(R.raw.pro_video_recording_simple_anim);
        this.mProVideoRecordingSimpleView.setProgress(1.0f);
        this.mProVideoRecordingSimpleView.T();
        this.mProVideoRecordingSimpleView.a(new Animator.AnimatorListener() {
            /* class com.android.camera.fragment.top.FragmentTopAlert.AnonymousClass14 */

            public void onAnimationCancel(Animator animator) {
                FragmentTopAlert.this.mProVideoRecordingSimpleView.setBackgroundResource(R.drawable.ic_vector_pro_video_recording_simple);
                FragmentTopAlert.this.mProVideoRecordingSimpleView.setImageDrawable(null);
            }

            public void onAnimationEnd(Animator animator) {
                FragmentTopAlert.this.mProVideoRecordingSimpleView.setBackgroundResource(R.drawable.ic_vector_pro_video_recording_simple);
                FragmentTopAlert.this.mProVideoRecordingSimpleView.setImageDrawable(null);
            }

            public void onAnimationRepeat(Animator animator) {
            }

            public void onAnimationStart(Animator animator) {
                FragmentTopAlert.this.mProVideoRecordingSimpleView.setBackground(null);
            }
        });
        boolean proVideoRecordingSimpleRunning = DataRepository.dataItemRunning().getProVideoRecordingSimpleRunning();
        DataRepository.dataItemRunning().setProVideoRecordingSimpleRunning(!proVideoRecordingSimpleRunning);
        ModeProtocol.ManuallyAdjust manuallyAdjust = (ModeProtocol.ManuallyAdjust) ModeCoordinatorImpl.getInstance().getAttachProtocol(181);
        ModeProtocol.MainContentProtocol mainContentProtocol = (ModeProtocol.MainContentProtocol) ModeCoordinatorImpl.getInstance().getAttachProtocol(166);
        ModeProtocol.ActionProcessing actionProcessing = (ModeProtocol.ActionProcessing) ModeCoordinatorImpl.getInstance().getAttachProtocol(162);
        if (!proVideoRecordingSimpleRunning) {
            if (manuallyAdjust != null) {
                manuallyAdjust.forceUpdateManualView(false);
            }
            if (mainContentProtocol != null) {
                mainContentProtocol.setZoomViewVisible(false);
            }
            if (actionProcessing != null) {
                actionProcessing.updatePauseAndCaptureView(false);
            }
            removeViewToTipLayout(this.mZoomTip);
            if (CameraSettings.isProVideoHistogramOpen(this.mCurrentMode)) {
                Completable.create(new AlphaOutOnSubscribe(this.mHistogramView)).subscribe();
            }
            if (this.mVideoUltraClearTip.getVisibility() == 0) {
                this.mIsVideoUltraClearTipVisibleBeforeProVideoSimple = true;
                Completable.create(new AlphaOutOnSubscribe(this.mVideoUltraClearTip).targetGone()).subscribe();
                return;
            }
            return;
        }
        if (manuallyAdjust != null) {
            manuallyAdjust.forceUpdateManualView(true);
        }
        if (mainContentProtocol != null) {
            mainContentProtocol.setZoomViewVisible(true);
        }
        if (actionProcessing != null) {
            actionProcessing.updatePauseAndCaptureView(true);
        }
        if (CameraSettings.isProVideoHistogramOpen(this.mCurrentMode)) {
            Completable.create(new AlphaInOnSubscribe(this.mHistogramView)).subscribe();
        }
        if (this.mIsVideoUltraClearTipVisibleBeforeProVideoSimple) {
            this.mIsVideoUltraClearTipVisibleBeforeProVideoSimple = false;
            Completable.create(new AlphaInOnSubscribe(this.mVideoUltraClearTip)).subscribe();
        }
    }

    private BGTintTextView initAiAudioBGTip() {
        return (BGTintTextView) LayoutInflater.from(getContext()).inflate(R.layout.ai_audio_top_tip_layout, (ViewGroup) null);
    }

    private void initDocumentStateButton() {
        this.mDocumentStateButton = (MutiStateButton) LayoutInflater.from(getContext()).inflate(R.layout.document_state_button, (ViewGroup) null);
        this.mDocumentStateMaps = new LinkedHashMap<>();
        this.mDocumentStateMaps.put("raw", Integer.valueOf((int) R.string.document_origin));
        this.mDocumentStateMaps.put(ComponentRunningDocument.DOCUMENT_BLACK_WHITE, Integer.valueOf((int) R.string.document_blackwhite));
        this.mDocumentStateMaps.put(ComponentRunningDocument.DOCUMENT_STRENGTHEN, Integer.valueOf((int) R.string.document_strengthen));
        this.mDocumentStateButton.initItems(this.mDocumentStateMaps, this.mOnClickListener);
    }

    private void initEndGravityTipLayout(int i) {
        this.mEndGravityTipLayout.setRotation((float) i);
        Completable.create(new AlphaInOnSubscribe(this.mEndGravityTipLayout).setDurationTime(300)).subscribe();
        int width = (this.mEndGravityTipLayout.getWidth() - this.mEndGravityTipLayout.getHeight()) / 2;
        if (isLandScape()) {
            if (isLeftLandScape()) {
                this.mEndGravityTipLayout.setGravity(GravityCompat.START);
            } else {
                this.mEndGravityTipLayout.setGravity(GravityCompat.END);
            }
            setViewEndMargin(this.mEndGravityTipLayout, getResources().getDimensionPixelOffset(R.dimen.video_ultra_tip_margin_end) - width);
            setViewTopMargin(this.mEndGravityTipLayout, getAlertTopMargin() + width);
            return;
        }
        this.mEndGravityTipLayout.setGravity(GravityCompat.END);
        setViewEndMargin(this.mEndGravityTipLayout, getResources().getDimensionPixelSize(R.dimen.video_ultra_tip_margin_end));
        setViewTopMargin(this.mEndGravityTipLayout, getAlertTopMargin());
    }

    private LinearLayout initFastmotionTip() {
        return (LinearLayout) LayoutInflater.from(getContext()).inflate(R.layout.fastmotion_top_tip_layout, (ViewGroup) null);
    }

    private BGTintTextView initHandGestureTip() {
        return (BGTintTextView) LayoutInflater.from(getContext()).inflate(R.layout.hand_gesture_top_tip_layout, (ViewGroup) null);
    }

    private void initHandler() {
        this.mHandler = new Handler();
    }

    private TextView initHorizonDirectTipText() {
        return (TextView) LayoutInflater.from(getContext()).inflate(R.layout.top_tip_lying_direct_hint_layout, (ViewGroup) null);
    }

    private void initLandscapeTopTipLayout() {
        int i;
        LinearLayout linearLayout = this.mTopTipLayout;
        if (linearLayout != null) {
            FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) linearLayout.getLayoutParams();
            String componentValue = DataRepository.dataItemConfig().getComponentConfigRatio().getComponentValue(this.mCurrentMode);
            boolean isSubtitleEnabled = CameraSettings.isSubtitleEnabled(this.mCurrentMode);
            ModeProtocol.CameraAction cameraAction = (ModeProtocol.CameraAction) ModeCoordinatorImpl.getInstance().getAttachProtocol(161);
            int i2 = ((cameraAction != null && isSubtitleEnabled && cameraAction.isRecording()) || (i = this.mCurrentMode) == 179 || i == 204) ? 1 : 0;
            int i3 = Util.getDisplayRect(i2).top;
            int height = Util.getDisplayRect(i2).height();
            if (componentValue == ComponentConfigRatio.RATIO_1X1) {
                i3 = Util.getTopCoverHeight();
                height = (height - Util.getSquareBottomCoverHeight()) - (Util.getTopCoverHeight() - Util.getTopHeight());
            }
            layoutParams.topMargin = ((height - Util.sWindowWidth) / 2) + i3;
            int width = CameraSettings.isCinematicAspectRatioEnabled(this.mCurrentMode) ? (int) ((((double) Util.getDisplayRect(1).width()) - (((double) Util.getDisplayRect(1).height()) / Util.getCinematicAspectRatio())) / 2.0d) : 0;
            if (isLeftLandScape()) {
                layoutParams.leftMargin = 0;
                layoutParams.rightMargin = getResources().getDimensionPixelSize(R.dimen.video_ultra_tip_margin_top) + width;
            } else if (isRightLandScape()) {
                layoutParams.leftMargin = getResources().getDimensionPixelSize(R.dimen.video_ultra_tip_margin_top) + width;
                layoutParams.rightMargin = 0;
            }
            layoutParams.height = Util.sWindowWidth;
            this.mTopTipLayout.setLayoutParams(layoutParams);
            this.mTopTipLayout.setRotation((float) this.mDegree);
        }
    }

    private BGTintTextView initMacroModeTip() {
        return (BGTintTextView) LayoutInflater.from(getContext()).inflate(R.layout.macro_mode_top_tip_layout, (ViewGroup) null);
    }

    private LinearLayout initMusicTipText() {
        return (LinearLayout) LayoutInflater.from(getContext()).inflate(R.layout.top_tip_music_layout, (ViewGroup) null);
    }

    private BGTintTextView initPermanentTip() {
        return (BGTintTextView) LayoutInflater.from(getContext()).inflate(R.layout.permanent_top_tip_layout, (ViewGroup) null);
    }

    private void initPortraitTopTipLayout() {
        LinearLayout linearLayout = this.mTopTipLayout;
        if (linearLayout != null) {
            ViewGroup.MarginLayoutParams marginLayoutParams = (ViewGroup.MarginLayoutParams) linearLayout.getLayoutParams();
            marginLayoutParams.topMargin = getAlertTopMargin();
            marginLayoutParams.leftMargin = 0;
            marginLayoutParams.rightMargin = 0;
            marginLayoutParams.height = (int) (((float) (Util.sWindowWidth * 4)) / 3.0f);
            this.mTopTipLayout.setLayoutParams(marginLayoutParams);
            this.mTopTipLayout.setDividerDrawable(getDividerDrawable());
            this.mTopTipLayout.setShowDividers(2);
            this.mTopTipLayout.setRotation(0.0f);
        }
    }

    private BGTintTextView initProColourTip() {
        return (BGTintTextView) LayoutInflater.from(getContext()).inflate(R.layout.procolour_top_tip_layout, (ViewGroup) null);
    }

    private TextView initRecommendTipText() {
        return (TextView) LayoutInflater.from(getContext()).inflate(R.layout.recommend_top_tip_layout, (ViewGroup) null);
    }

    private BGTintTextView initSubtitleTip() {
        return (BGTintTextView) LayoutInflater.from(getContext()).inflate(R.layout.subtitle_top_tip_layout, (ViewGroup) null);
    }

    private BGTintTextView initTimerBurstTip() {
        return (BGTintTextView) LayoutInflater.from(getContext()).inflate(R.layout.timer_burst_top_tip_layout, (ViewGroup) null);
    }

    private void initToastTipLayout() {
        setToastTipLayoutParams(true);
        this.mToastTopTipLayout.setVisibility(8);
        this.mToastTipFlash = initToastTopTipImage();
        this.mToastSwitchTip = initToastTopTipText();
        this.mSuperNightSeTip = initToastTopTipText();
    }

    private ImageView initToastTopTipImage() {
        return (ImageView) LayoutInflater.from(getContext()).inflate(R.layout.toast_top_tip_image_layout, (ViewGroup) null);
    }

    private BGTintTextView initToastTopTipText() {
        return (BGTintTextView) LayoutInflater.from(getContext()).inflate(R.layout.toast_top_tip_text_layout, (ViewGroup) null);
    }

    private TopAlertSlideSwitchButton initTopSlideSwitchButton() {
        return (TopAlertSlideSwitchButton) LayoutInflater.from(getContext()).inflate(R.layout.top_tip_slideswitch_layout, (ViewGroup) null);
    }

    private ToggleSwitch initTopTipToggleSwitch() {
        return (ToggleSwitch) LayoutInflater.from(getContext()).inflate(R.layout.top_tip_toggleswitch_layout, (ViewGroup) null);
    }

    private TextView initZoomTipText() {
        return (TextView) LayoutInflater.from(getContext()).inflate(R.layout.zoom_top_tip_layout, (ViewGroup) null);
    }

    private void removeViewToTipLayout(View view) {
        removeViewToTipLayout(view, true);
    }

    private void removeViewToTipLayout(View view, boolean z) {
        LinearLayout linearLayout;
        if (view != null && (linearLayout = this.mTopTipLayout) != null && linearLayout.indexOfChild(view) != -1) {
            if (!z || !this.mIsAlertAnim) {
                this.mTopTipLayout.setLayoutTransition(null);
            } else if (this.mTopTipLayout.getLayoutTransition() != customStubViewTransition()) {
                this.mTopTipLayout.setLayoutTransition(customStubViewTransition());
            }
            this.mTopTipLayout.removeView(view);
            if (this.mTopTipLayout.getChildCount() <= 0) {
                this.mTopTipLayout.removeAllViews();
            }
            checkChildMargin();
        }
    }

    private void removeViewToToastLayout(View view) {
        removeViewToToastLayout(view, true);
    }

    private void removeViewToToastLayout(View view, boolean z) {
        LinearLayout linearLayout;
        if (view != null && (linearLayout = this.mToastTopTipLayout) != null && linearLayout.indexOfChild(view) != -1) {
            if (!z || !this.mIsAlertAnim) {
                setToastTipLayoutParams(false);
                this.mTopTipLayout.setLayoutTransition(null);
            } else {
                if (this.mToastTopTipLayout.getLayoutTransition() == null || this.mToastTopTipLayout.getLayoutTransition() != customToastLayoutTransition()) {
                    setToastTipLayoutParams(true);
                }
                if (this.mTopTipLayout.getLayoutTransition() != customStubViewTransition()) {
                    this.mTopTipLayout.setLayoutTransition(customStubViewTransition());
                }
            }
            this.mToastTopTipLayout.removeView(view);
            if (this.mToastTopTipLayout.getChildCount() <= 0) {
                this.mToastTopTipLayout.removeAllViews();
                if (!z || !this.mIsAlertAnim) {
                    this.mToastTopTipLayout.setVisibility(8);
                }
            }
            checkChildMargin();
        }
    }

    public static void setPendingRecordingState(int i) {
        sPendingRecordingTimeState = i;
    }

    private void setToastTipLayoutParams(boolean z) {
        LinearLayout linearLayout = this.mToastTopTipLayout;
        if (linearLayout != null) {
            linearLayout.setDividerDrawable(getResources().getDrawable(R.drawable.top_tip_horizontal_divider));
            this.mToastTopTipLayout.setShowDividers(2);
            if (z) {
                this.mToastTopTipLayout.setLayoutTransition(customToastLayoutTransition());
            } else {
                this.mToastTopTipLayout.setLayoutTransition(null);
            }
            this.mToastTopTipLayout.setGravity(17);
        }
    }

    private void setViewEndMargin(View view, int i) {
        ViewGroup.MarginLayoutParams marginLayoutParams = (ViewGroup.MarginLayoutParams) view.getLayoutParams();
        marginLayoutParams.setMarginEnd(i);
        view.setLayoutParams(marginLayoutParams);
    }

    private void setViewTopMargin(View view, int i) {
        ViewGroup.MarginLayoutParams marginLayoutParams = (ViewGroup.MarginLayoutParams) view.getLayoutParams();
        marginLayoutParams.topMargin = i;
        view.setLayoutParams(marginLayoutParams);
        ViewCompat.setTranslationY(view, 0.0f);
    }

    private void showCloseConfirm() {
        if (this.mAlertDialog == null) {
            this.mAlertDialog = RotateDialogController.showSystemAlertDialog(getContext(), null, getString(R.string.live_music_close_message), getString(R.string.live_music_close_sure_message), new Runnable() {
                /* class com.android.camera.fragment.top.FragmentTopAlert.AnonymousClass12 */

                public void run() {
                    FragmentTopAlert fragmentTopAlert = FragmentTopAlert.this;
                    fragmentTopAlert.removeViewToTipLayout(fragmentTopAlert.mLiveMusicHintLayout);
                    ModeProtocol.LiveAudioChanges liveAudioChanges = (ModeProtocol.LiveAudioChanges) ModeCoordinatorImpl.getInstance().getAttachProtocol(211);
                    ModeProtocol.LiveRecordState liveRecordState = (ModeProtocol.LiveRecordState) ModeCoordinatorImpl.getInstance().getAttachProtocol(245);
                    if (liveAudioChanges != null && liveRecordState != null && !liveRecordState.isRecording() && !liveRecordState.isRecordingPaused()) {
                        liveAudioChanges.clearAudio();
                        ((ModeProtocol.TopAlert) ModeCoordinatorImpl.getInstance().getAttachProtocol(172)).updateConfigItem(245);
                    }
                }
            }, null, null, getString(R.string.live_music_close_cancel_message), null);
            this.mAlertDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                /* class com.android.camera.fragment.top.FragmentTopAlert.AnonymousClass13 */

                public void onDismiss(DialogInterface dialogInterface) {
                    FragmentTopAlert.this.mAlertDialog = null;
                }
            });
        }
    }

    private void showManualParameterResetDialog() {
        if (this.mAlertDialog == null) {
            this.mAlertDialog = RotateDialogController.showSystemAlertDialog(getContext(), null, getString(R.string.confirm_reset_manually_parameter_message), getString(R.string.reset_manually_parameter_confirmed), new f(this), null, null, getString(17039360), i.INSTANCE);
            this.mAlertDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                /* class com.android.camera.fragment.top.FragmentTopAlert.AnonymousClass2 */

                public void onDismiss(DialogInterface dialogInterface) {
                    FragmentTopAlert.this.mAlertDialog = null;
                }
            });
        }
    }

    private void updateDocumentState(boolean z) {
        ComponentRunningDocument componentRunningDocument = DataRepository.dataItemRunning().getComponentRunningDocument();
        if (componentRunningDocument != null && this.mDocumentStateButton != null) {
            this.mDocumentStateButton.updateCurrentIndex(componentRunningDocument.getComponentValue(this.mCurrentMode), z);
        }
    }

    private void updateFlashForPhotoRecording(boolean z) {
        int i;
        if (this.mCurrentMode == 163 && (i = this.mAlertImageType) != 2 && i != 5) {
            this.mTopTipLayout.setVisibility(z ? 4 : 0);
        }
    }

    private void updateTopHint(int i, String str, long j) {
        this.mHandler.removeCallbacks(this.mAlertTopHintHideRunnable);
        if (i == 0) {
            this.mPermanentTip.setText(str);
            this.mPermanentTip.setContentDescription(str);
            this.mPermanentTip.setBGColor(alertTintColor());
            addViewToToastLayout(this.mPermanentTip);
            if (j > 0) {
                this.mHandler.postDelayed(this.mAlertTopHintHideRunnable, j);
                return;
            }
            return;
        }
        removeViewToToastLayout(this.mPermanentTip);
    }

    public /* synthetic */ void Va() {
        if (isAdded()) {
            this.mHandGestureTip.sendAccessibilityEvent(32768);
        }
    }

    public /* synthetic */ void Wa() {
        if (isAdded()) {
            this.mToastSwitchTip.sendAccessibilityEvent(32768);
        }
    }

    public /* synthetic */ void Xa() {
        ModeProtocol.TopAlert topAlert;
        ModeProtocol.ManuallyAdjust manuallyAdjust = (ModeProtocol.ManuallyAdjust) ModeCoordinatorImpl.getInstance().getAttachProtocol(181);
        if (manuallyAdjust != null) {
            manuallyAdjust.resetManually();
        }
        ModeProtocol.ConfigChanges configChanges = (ModeProtocol.ConfigChanges) ModeCoordinatorImpl.getInstance().getAttachProtocol(164);
        if (configChanges != null) {
            configChanges.resetMeter(this.mCurrentMode);
        }
        if (CameraSettings.isFlashSupportedInManualMode() && (topAlert = (ModeProtocol.TopAlert) ModeCoordinatorImpl.getInstance().getAttachProtocol(172)) != null) {
            topAlert.updateConfigItem(193);
        }
        alertParameterResetTip(false, 8, 0, 0, false);
        CameraStatUtils.trackManuallyResetDialogOk();
    }

    public /* synthetic */ void Za() {
        this.mTopTipLayout.setLayoutTransition(null);
    }

    public void alertAiAudio(int i, int i2, boolean z) {
        if (this.mAiAudioTip.getVisibility() != 8 || i != 8) {
            String string = getString(i2);
            if (i != 0 || !z) {
                this.mAiAudioTip.setVisibility(i);
            } else {
                this.mAiAudioTip.setVisibility(i);
                ViewCompat.setAlpha(this.mAiAudioTip, 0.0f);
                ViewCompat.animate(this.mAiAudioTip).alpha(1.0f).setDuration(320).start();
            }
            if (i == 0) {
                this.mAiAudioTip.setTextSize(0, (float) getResources().getDimensionPixelSize(R.dimen.top_left_english_tip_size));
                this.mAiAudioTip.setText(string);
                this.mAiAudioTip.setContentDescription(string);
            }
            updateEndGravityTip();
        }
    }

    public void alertAiAudioBGHint(int i, int i2) {
        if (i == 0) {
            this.mAiAudioBGTip.setBGColor(alertTintColor());
            this.mAiAudioBGTip.setText(getString(i2));
            this.mAiAudioBGTip.setContentDescription(getString(i2));
            addViewToToastLayout(this.mAiAudioBGTip);
            this.mHandler.removeCallbacks(this.mAiAudioBGTipHideRunnable);
            this.mHandler.postDelayed(this.mAiAudioBGTipHideRunnable, 3000);
            alertAiAudioMutexToastIfNeed(getContext(), i2);
            return;
        }
        removeViewToToastLayout(this.mAiAudioBGTip);
    }

    public void alertAiAudioMutexToastIfNeed(Context context, int i) {
        int i2;
        if (Util.isWiredHeadsetOn() && i != -1) {
            switch (i) {
                case R.string.pref_camera_rec_type_3d_record:
                    i2 = R.string.unsupported_microphones_3d_record_tips;
                    break;
                case R.string.pref_camera_rec_type_audio_zoom:
                    i2 = R.string.unsupported_microphones_audio_zoom_tips;
                    break;
                default:
                    i2 = -1;
                    break;
            }
            if (i2 != -1) {
                ToastUtils.showToast(context, i2, true);
            }
        }
    }

    public void alertAiSceneSelector(int i) {
        alertAiSceneSelector(getResources().getString(R.string.text_ai_scene_selector_text_on), getResources().getString(R.string.text_ai_scene_selector_text_off), i, i == 0 ? g.INSTANCE : null, false);
    }

    public void alertFastmotionIndicator(int i, String str, String str2, String str3, boolean z) {
        if (this.mFastmotionIndicatorView.getVisibility() != 8 || i != 8) {
            if (i != 0 || !z) {
                this.mFastmotionIndicatorView.setVisibility(i);
            } else {
                this.mFastmotionIndicatorView.setVisibility(i);
                ViewCompat.setAlpha(this.mFastmotionIndicatorView, 0.0f);
                ViewCompat.animate(this.mFastmotionIndicatorView).alpha(1.0f).setDuration(320).start();
            }
            if (i == 0) {
                this.mFastmotionIndicatorView.showFastmotion(str, str2, str3);
            }
        }
    }

    public void alertFastmotionTip(String str, String str2) {
        removeViewToTipLayout(this.mZoomTip);
        removeFastmotionTipResetRunnable();
        this.mFastmotionTipTitle.setText(str);
        this.mFastmotionTipDesc.setText(str2);
        this.mHandler.postDelayed(this.mFastmotionTipToResetRunnable, 1000);
        if (this.mTopTipLayout.indexOfChild(this.mFastmotionTip) == -1 || this.mFastmotionTip.getVisibility() != 0) {
            addViewToTipLayout(this.mFastmotionTip);
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:19:0x0039  */
    /* JADX WARNING: Removed duplicated region for block: B:23:0x0042  */
    /* JADX WARNING: Removed duplicated region for block: B:26:0x0047 A[RETURN] */
    /* JADX WARNING: Removed duplicated region for block: B:27:0x0048  */
    public void alertFlash(int i, String str) {
        boolean z;
        int i2 = -1;
        if (i == 0) {
            int hashCode = str.hashCode();
            if (hashCode != 49) {
                if (hashCode != 50) {
                    if (hashCode == 53 && str.equals("5")) {
                        z = true;
                        if (z) {
                            i2 = 1;
                        } else if (z) {
                            i2 = 2;
                        } else if (z) {
                            i2 = 5;
                        }
                        if (this.mAlertImageType == i2) {
                            this.mAlertImageType = i2;
                            if (CameraSettings.isFrontCamera() && c.yn()) {
                                i2 = 1;
                            }
                            Drawable drawable = getResources().getDrawable(R.drawable.toast_top_tip_bg);
                            DrawableCompat.setTint(drawable, alertTintColor());
                            this.mToastTipFlash.setBackground(drawable);
                            if (i2 == 1) {
                                this.mToastTipFlash.setImageResource(R.drawable.ic_alert_flash);
                                addViewToToastLayout(this.mToastTipFlash, true, 0);
                                return;
                            } else if (i2 == 2) {
                                this.mToastTipFlash.setImageResource(R.drawable.ic_alert_flash_torch);
                                addViewToToastLayout(this.mToastTipFlash, true, 0);
                                return;
                            } else if (i2 != 5) {
                                removeViewToToastLayout(this.mToastTipFlash);
                                return;
                            } else {
                                this.mToastTipFlash.setImageResource(R.drawable.ic_alert_flash_back_soft_light);
                                addViewToToastLayout(this.mToastTipFlash, true, 0);
                                return;
                            }
                        } else {
                            return;
                        }
                    }
                } else if (str.equals("2")) {
                    z = true;
                    if (z) {
                    }
                    if (this.mAlertImageType == i2) {
                    }
                }
            } else if (str.equals("1")) {
                z = false;
                if (z) {
                }
                if (this.mAlertImageType == i2) {
                }
            }
            z = true;
            if (z) {
            }
            if (this.mAlertImageType == i2) {
            }
        } else {
            int i3 = this.mAlertImageType;
            if (i3 == 2 || i3 == 1 || i3 == 5) {
                this.mAlertImageType = -1;
                removeViewToToastLayout(this.mToastTipFlash);
            }
        }
    }

    public void alertHandGestureHint(int i) {
        String string = getString(i);
        this.mHandGestureTip.setText(string);
        this.mHandGestureTip.setContentDescription(string);
        this.mHandGestureTip.setBGColor(alertTintColor());
        addViewToToastLayout(this.mHandGestureTip);
        this.mHandler.postDelayed(new a(this), 300);
    }

    public void alertHistogram(int i) {
        HistogramView histogramView = this.mHistogramView;
        if (histogramView != null) {
            histogramView.setVisibility(i);
        }
        updateEndGravityTip();
    }

    public void alertLightingTip(int i) {
        int parseLightingRes = parseLightingRes(i);
        if (parseLightingRes == -1) {
            alertRecommendTipHint(8, parseLightingRes, -1);
        } else {
            alertRecommendTipHint(0, parseLightingRes, -1);
        }
    }

    public void alertMacroModeHint(int i, int i2, boolean z) {
        if (i == 0) {
            this.mMacroModeTip.setBGColor(alertTintColor());
            this.mMacroModeTip.setText(getString(i2));
            this.mMacroModeTip.setContentDescription(getString(i2));
            addViewToToastLayout(this.mMacroModeTip);
            this.mHandler.removeCallbacks(this.mMacroModeTipHideRunnable);
            if (z) {
                this.mHandler.postDelayed(this.mMacroModeTipHideRunnable, 3000);
                return;
            }
            return;
        }
        removeViewToToastLayout(this.mMacroModeTip);
    }

    public void alertMimojiFaceDetect(boolean z, int i) {
        if (z) {
            this.mToastSwitchTip.setText(i);
            this.mToastSwitchTip.setBGColor(alertTintColor());
            this.mToastSwitchTip.setVisibility(0);
            addViewToToastLayout(this.mToastSwitchTip);
        } else if (this.mToastSwitchTip.getVisibility() != 8) {
            this.mTopTipLayout.removeCallbacks(this.mViewHideRunnable);
            removeViewToToastLayout(this.mToastSwitchTip);
        }
    }

    public void alertMoonSelector(String str, String str2, int i, boolean z) {
        alertAiSceneSelector(str, str2, i, i == 0 ? d.INSTANCE : null, z);
    }

    public void alertMusicClose(boolean z) {
        ImageView imageView = this.mLiveMusicClose;
        if (imageView == null) {
            return;
        }
        if (z) {
            imageView.setAlpha(1.0f);
            this.mLiveMusicHintLayout.setClickable(true);
            FolmeUtils.touchTint(this.mLiveMusicHintLayout);
            return;
        }
        imageView.setAlpha(0.4f);
        this.mLiveMusicHintLayout.setClickable(false);
        FolmeUtils.clean(this.mLiveMusicHintLayout);
    }

    public void alertMusicTip(int i, String str) {
        if (i != 0 || TextUtils.isEmpty(str)) {
            removeViewToTipLayout(this.mLiveMusicHintLayout);
            return;
        }
        this.mLiveMusiHintText.setText(str);
        addViewToTipLayout(this.mLiveMusicHintLayout);
    }

    public void alertParameterDescriptionTip(int i, boolean z) {
        if (!this.mIsVideoRecording) {
            this.mManualParameterDescriptionTip.setVisibility(i);
        }
        if (!(i == 0 && this.mManualParameterDescriptionTip.getVisibility() == 0 && this.mManualParameterDescriptionTip.getAlpha() > 0.0f) && i == 0 && z) {
            ViewCompat.setAlpha(this.mManualParameterDescriptionTip, 0.0f);
            ViewCompat.animate(this.mManualParameterDescriptionTip).alpha(1.0f).setDuration(320).start();
        }
    }

    public void alertParameterResetTip(boolean z, int i, int i2, int i3, boolean z2) {
        if (this.mManualParameterResetTip.getVisibility() != i && !this.mIsVideoRecording) {
            if (i != 0 || !z2) {
                this.mManualParameterResetTip.setVisibility(i);
            } else {
                this.mManualParameterResetTip.setVisibility(i);
                ViewCompat.setAlpha(this.mManualParameterResetTip, 0.0f);
                ViewCompat.animate(this.mManualParameterResetTip).alpha(1.0f).setDuration(320).start();
            }
            if (i == 0) {
                this.mManualParameterResetTip.setContentDescription(getString(i2));
                if (!z) {
                    CameraStatUtils.trackManuallyResetShow();
                }
            }
        }
    }

    public void alertProColourHint(int i, int i2) {
        if (i == 0) {
            this.mProColourTip.setText(getString(i2));
            this.mProColourTip.setContentDescription(getString(i2));
            this.mProColourTip.setBGColor(alertTintColor());
            addViewToToastLayout(this.mProColourTip);
            return;
        }
        removeViewToToastLayout(this.mProColourTip);
    }

    public void alertRecommendDescTip(String str, int i, int i2, long j) {
        if (i == 0 || str.equals(this.mShortDurationDescriptionTip)) {
            if (i == 0 && !this.mShortDurationDescriptionTip.equals(TIP_UNKNOW)) {
                this.mShortDurationDescriptionTip = TIP_UNKNOW;
                removeViewToTipLayout(this.mRecommendTip);
            }
            this.mHandler.removeCallbacks(this.mAlertRecommendDescRunable);
            if (i == 0) {
                this.mShortDurationDescriptionTip = str;
                this.mRecommendTip.setText(i2);
                this.mRecommendTip.setContentDescription(getString(i2));
                addViewToTipLayout(this.mRecommendTip);
                if (j >= 0) {
                    ((AlertRecommendDescRunable) this.mAlertRecommendDescRunable).setTipType(str);
                    this.mHandler.postDelayed(this.mAlertRecommendDescRunable, j);
                    return;
                }
                return;
            }
            this.mShortDurationDescriptionTip = TIP_UNKNOW;
            removeViewToTipLayout(this.mRecommendTip);
        }
    }

    public void alertRecommendTipHint(int i, int i2, long j) {
        this.mHandler.removeCallbacks(this.mAlertAiDetectTipHitRunable);
        if (i == 0) {
            this.mRecommendTip.setText(i2);
            this.mRecommendTip.setContentDescription(getString(i2));
            addViewToTipLayout(this.mRecommendTip);
            if (j >= 0) {
                this.mHandler.postDelayed(this.mAlertAiDetectTipHitRunable, j);
                return;
            }
            return;
        }
        removeViewToTipLayout(this.mRecommendTip);
    }

    public void alertSlideSwitchLayout(boolean z, int i) {
        DataItemRunning dataItemRunning = DataRepository.dataItemRunning();
        AnonymousClass17 r1 = null;
        ComponentData componentData = null;
        if (z) {
            boolean z2 = true;
            if (i == 221) {
                componentData = dataItemRunning.getComponentRunningDocument();
            } else if (i == 228) {
                z2 = false;
                componentData = dataItemRunning.getComponentRunningTiltValue();
            } else if (i == 246) {
                componentData = dataItemRunning.getComponentRunningMoon();
            }
            this.mSlideSwitchButton.setType(i);
            this.mSlideSwitchButton.setIndicatorColor(getResources().getColor(R.color.white));
            this.mSlideSwitchButton.setComponentData(componentData, i, z2);
            r1 = new TopAlertSlideSwitchButton.SlideSwitchListener() {
                /* class com.android.camera.fragment.top.FragmentTopAlert.AnonymousClass17 */

                @Override // com.android.camera.ui.TopAlertSlideSwitchButton.SlideSwitchListener
                public boolean enableSwitch() {
                    ModeProtocol.CameraAction cameraAction = (ModeProtocol.CameraAction) ModeCoordinatorImpl.getInstance().getAttachProtocol(161);
                    return cameraAction == null || !cameraAction.isDoingAction();
                }

                @Override // com.android.camera.ui.TopAlertSlideSwitchButton.SlideSwitchListener
                public void toSlideSwitch(int i, String str) {
                    String str2 = FragmentTopAlert.TAG;
                    Log.d(str2, "onSlideSwitch: " + i + " | " + str);
                    ModeProtocol.ConfigChanges configChanges = (ModeProtocol.ConfigChanges) ModeCoordinatorImpl.getInstance().getAttachProtocol(164);
                    if (configChanges != null) {
                        configChanges.onConfigValueChanged(i, str);
                    }
                }
            };
            this.mHandler.post(this.mShowSlideSwitchLayout);
        } else {
            this.mTopTipLayout.removeCallbacks(this.mShowSlideSwitchLayout);
            removeViewToTipLayout(this.mSlideSwitchButton);
        }
        this.mSlideSwitchButton.setSlideSwitchListener(r1);
    }

    public void alertSubtitleHint(int i, int i2) {
        if (i == 0) {
            this.mSubtitleTip.setText(getString(i2));
            this.mSubtitleTip.setContentDescription(getString(i2));
            this.mSubtitleTip.setBGColor(alertTintColor());
            addViewToToastLayout(this.mSubtitleTip);
            return;
        }
        removeViewToToastLayout(this.mSubtitleTip);
    }

    public void alertSuperNightSeTip(int i) {
        if (i == 0) {
            this.mSuperNightSeTip.setBGColor(alertTintColor());
            this.mSuperNightSeTip.setText(R.string.ai_scene_top_moon_off);
            addViewToToastLayout(this.mSuperNightSeTip);
            return;
        }
        removeViewToToastLayout(this.mSuperNightSeTip);
    }

    public void alertSwitchTip(String str, int i, int i2, String str2, long j) {
        if (i == 0 || str.equals(this.mShortDurationToastTip)) {
            if (i == 0 && !this.mShortDurationToastTip.equals(TIP_UNKNOW)) {
                this.mShortDurationToastTip = TIP_UNKNOW;
                removeViewToToastLayout(this.mToastSwitchTip);
            }
            this.mToastSwitchTip.setTag(Integer.valueOf(i2));
            this.mToastSwitchTip.setContentDescription(str2);
            this.mToastSwitchTip.setBGColor(alertTintColor());
            this.mAlertAiSceneSwitchHintTime = System.currentTimeMillis();
            this.mHandler.postDelayed(new h(this), 300);
            this.mHandler.removeCallbacks(this.mViewHideRunnable);
            if (i != 0) {
                this.mShortDurationToastTip = TIP_UNKNOW;
                removeViewToToastLayout(this.mToastSwitchTip);
                return;
            }
            this.mShortDurationToastTip = str;
            this.mToastSwitchTip.setText(str2);
            addViewToToastLayout(this.mToastSwitchTip);
            if (j > 0) {
                this.mHandler.postDelayed(this.mViewHideRunnable, j);
            }
        }
    }

    public void alertTimerBurstHint(int i, int i2, boolean z) {
        if (i == 0) {
            this.mTimerBurstTip.setBGColor(alertTintColor());
            this.mTimerBurstTip.setText(getString(i2));
            this.mTimerBurstTip.setContentDescription(getString(i2));
            addViewToToastLayout(this.mTimerBurstTip);
            this.mHandler.removeCallbacks(this.mTimerBurstRunnable);
            if (z) {
                this.mHandler.postDelayed(this.mTimerBurstRunnable, 3000);
                return;
            }
            return;
        }
        removeViewToToastLayout(this.mTimerBurstTip);
    }

    public void alertTopHint(int i, int i2) {
        alertTopHint(i, i2, 0);
    }

    public void alertTopHint(int i, int i2, long j) {
        if (i2 > 0 && i == 0) {
            this.mTopHintTextResource = i2;
        } else if (i == 8) {
            this.mTopHintTextResource = 0;
        }
        String str = null;
        if (this.mTopHintTextResource == 0) {
            i = 8;
        } else {
            str = getString(i2);
        }
        updateTopHint(i, str, j);
    }

    public void alertTopHint(int i, String str) {
        if (TextUtils.isEmpty(str) && i == 0) {
            i = 8;
        }
        updateTopHint(i, str, 0);
    }

    public void alertTopHint(int i, String str, long j) {
        if (TextUtils.isEmpty(str) && i == 0) {
            i = 8;
        }
        updateTopHint(i, str, j);
    }

    public void alertUpdateValue(int i, String str) {
        removeZoomTipRestRunnable();
        if (i == 0) {
            alertZoom(false);
        } else if (i == 1) {
            if (str == null || TextUtils.isEmpty(str)) {
                removeViewToToastLayout(this.mToastSwitchTip);
            }
            removeViewToTipLayout(this.mFastmotionTip);
            this.mZoomTip.setTextSize(Util.pixelTodp(70.0f));
            this.mZoomTip.setText(str);
            this.mHandler.postDelayed(this.mZoomTipToResetRunnable, 1000);
            if (this.mTopTipLayout.indexOfChild(this.mZoomTip) == -1 || this.mZoomTip.getVisibility() != 0) {
                if (this.mCurrentMode != 204) {
                    addViewToTipLayout(this.mZoomTip);
                }
                alertAudioZoomIndicator(false);
                return;
            }
            alertAudioZoomIndicator(false);
        }
    }

    public void alertVideoUltraClear(int i, int i2, boolean z) {
        if (this.mVideoUltraClearTip.getVisibility() != 8 || i != 8) {
            String string = getString(i2);
            if (i != 0 || !z) {
                this.mVideoUltraClearTip.setVisibility(i);
            } else {
                this.mVideoUltraClearTip.setVisibility(i);
                ViewCompat.setAlpha(this.mVideoUltraClearTip, 0.0f);
                ViewCompat.animate(this.mVideoUltraClearTip).alpha(1.0f).setDuration(320).start();
            }
            if (i == 0) {
                this.mVideoUltraClearTip.setTextSize(0, (float) getResources().getDimensionPixelSize(R.dimen.top_left_english_tip_size));
                this.mVideoUltraClearTip.setText(string);
                this.mVideoUltraClearTip.setContentDescription(string);
            }
            updateEndGravityTip();
        }
    }

    public void clear(boolean z) {
        removeZoomTipRestRunnable();
        clearAlertStatus();
        if (z) {
            ModeProtocol.TopAlert topAlert = (ModeProtocol.TopAlert) ModeCoordinatorImpl.getInstance().getAttachProtocol(172);
            if (topAlert != null) {
                topAlert.clearAllTipsState();
            }
            clearHandlerCallbacks();
        }
        this.mAlertImageType = -1;
        int childCount = this.mToastTopTipLayout.getChildCount();
        ArrayList<View> arrayList = new ArrayList();
        for (int i = 0; i < childCount; i++) {
            View childAt = this.mToastTopTipLayout.getChildAt(i);
            Object tag = childAt.getTag();
            if (tag == null || !(tag == null || !(tag instanceof Integer) || ((Integer) tag).intValue() == 2)) {
                arrayList.add(childAt);
            }
            if (z) {
                arrayList.add(childAt);
            }
        }
        for (View view : arrayList) {
            this.mToastTopTipLayout.removeView(view);
        }
        if (this.mToastTopTipLayout.getChildCount() <= 0) {
            this.mToastTopTipLayout.removeAllViews();
            setToastTipLayoutParams(true);
            this.mToastTopTipLayout.setVisibility(8);
        }
        arrayList.clear();
        int childCount2 = this.mTopTipLayout.getChildCount();
        for (int i2 = 0; i2 < childCount2; i2++) {
            View childAt2 = this.mTopTipLayout.getChildAt(i2);
            if (i2 != 0) {
                arrayList.add(childAt2);
            } else {
                setToastTipLayoutParams(true);
            }
        }
        for (View view2 : arrayList) {
            this.mTopTipLayout.removeView(view2);
        }
        clearVideoUltraClear();
        ImageView imageView = this.mManualParameterResetTip;
        if (!(imageView == null || imageView.getVisibility() == 8)) {
            this.mManualParameterResetTip.setVisibility(8);
        }
        ImageView imageView2 = this.mManualParameterDescriptionTip;
        if (!(imageView2 == null || imageView2.getVisibility() == 8)) {
            this.mManualParameterDescriptionTip.setVisibility(8);
        }
        HistogramView histogramView = this.mHistogramView;
        if (!(histogramView == null || histogramView.getVisibility() == 8)) {
            this.mHistogramView.setVisibility(8);
        }
        this.mShortDurationToastTip = TIP_UNKNOW;
    }

    public void clearAlertStatus() {
        removeViewToTipLayout(this.mZoomTip);
    }

    public void clearFastmotionTip() {
        removeViewToTipLayout(this.mFastmotionTip, false);
    }

    public void clearVideoUltraClear() {
        TextView textView = this.mVideoUltraClearTip;
        if (!(textView == null || textView.getVisibility() == 8)) {
            this.mVideoUltraClearTip.setText("");
            this.mVideoUltraClearTip.setVisibility(8);
        }
        FastmotionIndicatorView fastmotionIndicatorView = this.mFastmotionIndicatorView;
        if (!(fastmotionIndicatorView == null || fastmotionIndicatorView.getVisibility() == 8)) {
            this.mFastmotionIndicatorView.setVisibility(8);
        }
        updateEndGravityTip();
    }

    public boolean containShortDurationDescriptionTips(String str) {
        return this.mShortDurationDescriptionTip.equals(str);
    }

    public /* synthetic */ void f(View view) {
        showCloseConfirm();
    }

    @Override // com.android.camera.fragment.BaseFragment
    public int getFragmentInto() {
        return 253;
    }

    @Override // com.android.camera.fragment.BaseFragment
    public int getLayoutResourceId() {
        return R.layout.fragment_top_alert;
    }

    public VideoTagView getVideoTag() {
        return this.mVideoTagView;
    }

    public /* synthetic */ void h(View view) {
        LinkedHashMap<String, Integer> linkedHashMap;
        if (isEnableClick()) {
            ModeProtocol.CameraAction cameraAction = (ModeProtocol.CameraAction) ModeCoordinatorImpl.getInstance().getAttachProtocol(161);
            if (cameraAction == null || !cameraAction.isDoingAction()) {
                String str = (String) view.getTag();
                if (str != null && (linkedHashMap = this.mDocumentStateMaps) != null && linkedHashMap.containsKey(str)) {
                    DataRepository.dataItemRunning().getComponentRunningDocument().setComponentValue(this.mCurrentMode, str);
                    CameraStatUtils.trackDocumentModeChanged(str);
                    updateDocumentState(true);
                    return;
                }
                return;
            }
            Log.d(TAG, "cameraAction.isDoingAction return");
        }
    }

    public void hideRecommendDescTip(String str) {
        if (containShortDurationDescriptionTips(str)) {
            removeViewToTipLayout(this.mRecommendTip);
        }
    }

    public void hideRecordingTime() {
        if (this.mAlertRecordingText != null && this.mIsVideoRecording) {
            AlphaAnimation alphaAnimation = this.mBlingAnimation;
            if (alphaAnimation != null) {
                alphaAnimation.cancel();
            }
            this.mAlertRecordingText.setVisibility(4);
        }
    }

    public void hideSwitchTip() {
        removeViewToToastLayout(this.mToastSwitchTip);
        this.mShortDurationToastTip = TIP_UNKNOW;
        if (this.mTopTipLayout.getVisibility() == 0) {
            this.mTopTipLayout.removeCallbacks(this.mViewHideRunnable);
        }
    }

    @Override // com.android.camera.fragment.BaseFragment
    public void initView(View view) {
        initHandler();
        this.mLlAlertRecordingTimeView = (LinearLayout) view.findViewById(R.id.ll_alert_recording_time_view);
        this.mAlertRecordingText = (TextView) view.findViewById(R.id.alert_recording_time_view);
        this.mAlertRecordingTextNumerator = (TextView) view.findViewById(R.id.alert_recording_numerator_view);
        this.mAlertRecordingTextDenominator = (TextView) view.findViewById(R.id.alert_recording_time_denominator_view);
        ViewGroup.MarginLayoutParams marginLayoutParams = (ViewGroup.MarginLayoutParams) this.mLlAlertRecordingTimeView.getLayoutParams();
        marginLayoutParams.height = Util.sTopBarHeight;
        int i = Util.sTopMargin;
        marginLayoutParams.topMargin = i;
        setViewTopMargin(this.mLlAlertRecordingTimeView, i);
        this.mAlertRecordingText.setVisibility(8);
        this.mAlertRecordingTextNumerator.setVisibility(8);
        this.mAlertRecordingTextDenominator.setVisibility(8);
        this.mVideoTagView = new VideoTagView();
        this.mVideoTagView.init(view, getContext());
        this.mVideoUltraClearTip = (TextView) view.findViewById(R.id.video_ultra_clear_tip);
        this.mFastmotionIndicatorView = (FastmotionIndicatorView) view.findViewById(R.id.fastmotion_indicator);
        this.mAiAudioTip = (TextView) view.findViewById(R.id.ai_audio_tip);
        this.mEndGravityTipLayout = (LinearLayout) view.findViewById(R.id.end_gravity_tip_layout);
        this.mEndGravityTipLayout.setDividerDrawable(getEndGravityTipDividerDrawable());
        this.mEndGravityTipLayout.setShowDividers(2);
        setViewTopMargin(this.mEndGravityTipLayout, getAlertTopMargin());
        this.mStartGravityTipLayout = (LinearLayout) view.findViewById(R.id.start_gravity_layout);
        setViewTopMargin(this.mStartGravityTipLayout, getAlertStartGravityTipLayoutTopMargin());
        this.mManualParameterResetTip = (ImageView) view.findViewById(R.id.reset_manually_parameter_tip);
        this.mManualParameterResetTip.setOnClickListener(this);
        this.mManualParameterDescriptionTip = (ImageView) view.findViewById(R.id.manually_parameter_description_tip);
        this.mManualParameterDescriptionTip.setOnClickListener(this);
        FolmeUtils.touchTint(this.mManualParameterResetTip, this.mManualParameterDescriptionTip);
        int i2 = sPendingRecordingTimeState;
        if (i2 != 0) {
            setRecordingTimeState(i2, false);
            setPendingRecordingState(0);
        }
        this.mTopTipLayout = (LinearLayout) view.findViewById(R.id.top_tip_layout);
        initPortraitTopTipLayout();
        this.mToastTopTipLayout = (LinearLayout) this.mTopTipLayout.findViewById(R.id.top_toast_layout);
        initToastTipLayout();
        this.mAiSceneSelectView = initTopTipToggleSwitch();
        this.mSlideSwitchButton = initTopSlideSwitchButton();
        this.mRecommendTip = initRecommendTipText();
        this.mZoomTip = initZoomTipText();
        this.mLiveMusicHintLayout = initMusicTipText();
        this.mLiveMusiHintText = (TextView) this.mLiveMusicHintLayout.findViewById(R.id.live_music_title_hint);
        this.mLiveMusicClose = (ImageView) this.mLiveMusicHintLayout.findViewById(R.id.live_music_close);
        FolmeUtils.touchTint(this.mLiveMusicHintLayout);
        this.mLiveMusicHintLayout.setOnClickListener(new k(this));
        this.mPermanentTip = initPermanentTip();
        this.mSubtitleTip = initSubtitleTip();
        this.mProColourTip = initProColourTip();
        this.mLyingDirectHintText = initHorizonDirectTipText();
        this.mMacroModeTip = initMacroModeTip();
        this.mTimerBurstTip = initTimerBurstTip();
        this.mAiAudioBGTip = initAiAudioBGTip();
        this.mHandGestureTip = initHandGestureTip();
        this.mHistogramView = (HistogramView) view.findViewById(R.id.rgb_histogram);
        this.mFastmotionTip = initFastmotionTip();
        this.mFastmotionTipTitle = (TextView) this.mFastmotionTip.findViewById(R.id.fastmotion_top_tip_layout_title);
        this.mFastmotionTipDesc = (TextView) this.mFastmotionTip.findViewById(R.id.fastmotion_top_tip_layout_desc);
        this.mFastmotionTipTitle.setTypeface(Typeface.create("mipro-regular", 0));
        this.mFastmotionTipDesc.setTypeface(Typeface.create("mipro-medium", 0));
        this.mProVideoRecordingSimpleView = (LottieAnimationView) view.findViewById(R.id.pro_video_recording_simple_view);
        FolmeUtils.touchTint(this.mProVideoRecordingSimpleView);
        ViewGroup.MarginLayoutParams marginLayoutParams2 = (ViewGroup.MarginLayoutParams) this.mProVideoRecordingSimpleView.getLayoutParams();
        marginLayoutParams2.height = getResources().getDimensionPixelSize(R.dimen.pro_video_recording_simple_width);
        marginLayoutParams2.topMargin = Util.sTopMargin + ((Util.sTopBarHeight - getResources().getDimensionPixelSize(R.dimen.pro_video_recording_simple_width)) / 2);
        this.mProVideoRecordingSimpleView.setOnClickListener(new j(this));
    }

    public boolean isContainAlertRecommendTip(int... iArr) {
        TextView textView;
        LinearLayout linearLayout = this.mTopTipLayout;
        if (!(linearLayout == null || (textView = this.mRecommendTip) == null || linearLayout.indexOfChild(textView) == -1 || iArr == null || iArr.length <= 0)) {
            for (int i : iArr) {
                if (i > 0 && getResources().getString(i).equals(this.mRecommendTip.getText())) {
                    return true;
                }
            }
        }
        return false;
    }

    public boolean isCurrentRecommendTipText(int i) {
        if (i <= 0) {
            return false;
        }
        String string = getResources().getString(i);
        return !TextUtils.isEmpty(string) && isShowTopLayoutSpecifyTip(this.mRecommendTip) && string.equals(this.mRecommendTip.getText());
    }

    public boolean isFlashShowing() {
        return this.mToastTopTipLayout != null && this.mToastTipFlash != null && TIP_FLASH.equals(this.mShortDurationToastTip) && this.mToastTipFlash.getVisibility() == 0;
    }

    public boolean isHDRShowing() {
        return this.mToastTopTipLayout != null && this.mToastTipFlash != null && this.mShortDurationToastTip == TIP_HDR && this.mToastSwitchTip.getVisibility() == 0;
    }

    public boolean isShow() {
        return this.mShow;
    }

    public boolean isShowBacklightSelector() {
        LinearLayout linearLayout = this.mTopTipLayout;
        return (linearLayout == null || linearLayout.indexOfChild(this.mAiSceneSelectView) == -1 || !getResources().getString(R.string.text_ai_scene_selector_text_on).equals(this.mAiSceneSelectView.getTextOn())) ? false : true;
    }

    public boolean isShowMoonSelector() {
        LinearLayout linearLayout = this.mTopTipLayout;
        return (linearLayout == null || linearLayout.indexOfChild(this.mAiSceneSelectView) == -1 || !getResources().getString(R.string.ai_scene_top_tip).equals(this.mAiSceneSelectView.getTextOn())) ? false : true;
    }

    public boolean isShowTopLayoutSpecifyTip(View view) {
        LinearLayout linearLayout;
        return (view == null || (linearLayout = this.mTopTipLayout) == null || linearLayout.indexOfChild(view) == -1) ? false : true;
    }

    @Override // com.android.camera.fragment.BaseFragment, com.android.camera.animation.AnimationDelegate.AnimationResource
    public void notifyAfterFrameAvailable(int i) {
        super.notifyAfterFrameAvailable(i);
        if (CameraSettings.isProVideoHistogramOpen(this.mCurrentMode)) {
            Completable.create(new AlphaInOnSubscribe(this.mHistogramView)).subscribe();
        }
    }

    @Override // androidx.fragment.app.Fragment
    public void onActivityCreated(Bundle bundle) {
        super.onActivityCreated(bundle);
    }

    public void onClick(View view) {
        DialogFragment dialogFragment;
        if (isEnableClick()) {
            ModeProtocol.CameraAction cameraAction = (ModeProtocol.CameraAction) ModeCoordinatorImpl.getInstance().getAttachProtocol(161);
            if (cameraAction == null || !cameraAction.isDoingAction()) {
                int id = view.getId();
                if (id == R.id.manually_parameter_description_tip) {
                    int i = this.mCurrentMode;
                    MistatsWrapper.moduleUIClickEvent(i == 180 ? "M_proVideo_" : i == 169 ? "M_fastMotion_" : "M_manual_", MistatsConstants.Manual.PARAMETER_DESCRIPTION, "on");
                    int i2 = this.mCurrentMode;
                    String str = i2 == 187 ? FragmentAmbilightDescription.TAG : i2 == 169 ? FragmentFastMotionDescription.TAG : FragmentParameterDescription.TAG;
                    if (FragmentUtils.getFragmentByTag(getFragmentManager(), str) == null) {
                        int i3 = this.mCurrentMode;
                        if (i3 == 187) {
                            dialogFragment = new FragmentAmbilightDescription();
                            CameraStatUtils.trackAmbilightClick(MistatsConstants.Ambilight.PARAM_AMBILIGHT_ABOUT_BUTTON_CLICK);
                        } else {
                            dialogFragment = i3 == 169 ? new FragmentFastMotionDescription() : new FragmentParameterDescription();
                        }
                        dialogFragment.setStyle(2, R.style.ManuallyDescriptionFragment);
                        getFragmentManager().beginTransaction().add(dialogFragment, str).commitAllowingStateLoss();
                    }
                } else if (id == R.id.reset_manually_parameter_tip) {
                    CameraStatUtils.trackManuallyResetClick();
                    showManualParameterResetDialog();
                }
            } else {
                Log.d(TAG, "cameraAction.isDoingAction return");
            }
        }
    }

    @Override // com.android.camera.fragment.BaseFragment, androidx.fragment.app.Fragment
    public void onStart() {
        super.onStart();
        Log.d(TAG, "onStart");
        showRecordingTime();
    }

    @Override // com.android.camera.fragment.BaseFragment, androidx.fragment.app.Fragment
    public void onStop() {
        super.onStop();
        clear(true);
        AlertDialog alertDialog = this.mAlertDialog;
        if (alertDialog != null) {
            alertDialog.dismiss();
            this.mAlertDialog = null;
        }
        Handler handler = this.mHandler;
        if (handler != null) {
            handler.removeCallbacksAndMessages(null);
        }
        AlphaAnimation alphaAnimation = this.mBlingAnimation;
        if (alphaAnimation != null) {
            alphaAnimation.cancel();
            this.mBlingAnimation = null;
        }
        TextView textView = this.mAlertRecordingText;
        if (textView != null) {
            textView.setVisibility(8);
        }
        this.mIsAlertAnim = true;
        TextView textView2 = this.mAlertRecordingTextNumerator;
        if (textView2 != null) {
            textView2.setVisibility(8);
        }
        TextView textView3 = this.mAlertRecordingTextDenominator;
        if (textView3 != null) {
            textView3.setVisibility(8);
        }
    }

    public int parseLightingRes(int i) {
        if (i == -1) {
            return -1;
        }
        if (i == 3) {
            return R.string.lighting_hint_too_close;
        }
        if (i == 4) {
            return R.string.lighting_hint_too_far;
        }
        if (i != 5) {
            return -1;
        }
        return R.string.lighting_hint_needed;
    }

    @Override // com.android.camera.fragment.BaseFragment, com.android.camera.animation.AnimationDelegate.AnimationResource
    public void provideAnimateElement(int i, List<Completable> list, int i2) {
        int i3 = this.mCurrentMode;
        this.mIsAlertAnim = true;
        Log.d(TAG, "provideAnimateElement " + i2);
        boolean z = false;
        if (i2 == 3) {
            this.mIsVideoRecording = false;
            this.mAlertRecordingText.setAnimation(null);
            this.mAlertRecordingText.setVisibility(8);
            this.mAlertRecordingTextDenominator.setVisibility(8);
            this.mAlertRecordingTextNumerator.setVisibility(8);
        } else if (this.mIsVideoRecording) {
            setRecordingTimeState(3, false);
        }
        super.provideAnimateElement(i, list, i2);
        if (!(i3 == i || ((i3 == 163 && i == 165) || (i3 == 165 && i == 163)))) {
            z = true;
        }
        clear(z);
        setShow(true);
        updateEndGravityTip();
        updateTopAlertLayout();
        if (this.mCurrentMode != 180 && this.mHistogramView.getVisibility() == 0) {
            if (list != null) {
                list.add(Completable.create(new AlphaOutOnSubscribe(this.mHistogramView)));
            } else {
                this.mHistogramView.setVisibility(8);
            }
        }
    }

    @Override // com.android.camera.fragment.BaseFragment, com.android.camera.animation.AnimationDelegate.AnimationResource
    public void provideRotateItem(List<View> list, int i) {
        super.provideRotateItem(list, i);
        initEndGravityTipLayout(i);
        list.add(this.mManualParameterDescriptionTip);
        list.add(this.mManualParameterResetTip);
        updateTopAlertLayout();
    }

    public void refreshHistogramStatsView() {
        HistogramView histogramView = this.mHistogramView;
        if (histogramView != null && histogramView.getVisibility() == 0) {
            this.mHistogramView.refresh();
        }
    }

    public void removeFastmotionTipResetRunnable() {
        this.mHandler.removeCallbacks(this.mFastmotionTipToResetRunnable);
    }

    public void removeZoomTipRestRunnable() {
        this.mHandler.removeCallbacks(this.mZoomTipToResetRunnable);
    }

    public void setAlertAnim(boolean z) {
        this.mIsAlertAnim = z;
    }

    public void setRecordingTimeState(int i, boolean z) {
        AlphaAnimation alphaAnimation;
        String str = TAG;
        Log.d(str, " setRecordingTimeState " + i + "   mCurrentMode: " + this.mCurrentMode);
        if (i == 1) {
            this.mIsVideoRecording = true;
            TimerBurstController timerBurstController = DataRepository.dataItemLive().getTimerBurstController();
            int i2 = this.mCurrentMode;
            if (i2 != 165) {
                if (i2 != 167) {
                    if (!(i2 == 169 || i2 == 172)) {
                        if (!(i2 == 174 || i2 == 177)) {
                            if (!(i2 == 180 || i2 == 187)) {
                                if (i2 != 183) {
                                    if (i2 != 184) {
                                        switch (i2) {
                                            case 163:
                                                if (!timerBurstController.isInTimerBurstShotting()) {
                                                    this.mAlertRecordingText.setText("00:15");
                                                    updateFlashForPhotoRecording(true);
                                                    break;
                                                } else {
                                                    this.mAlertRecordingText.setVisibility(8);
                                                    this.mAlertRecordingTextNumerator.setVisibility(0);
                                                    int timerBurstTotalCount = CameraSettings.getTimerBurstTotalCount();
                                                    this.mAlertRecordingTextNumerator.setText(String.valueOf(timerBurstController.getCaptureIndex()));
                                                    this.mAlertRecordingTextDenominator.setVisibility(0);
                                                    this.mAlertRecordingTextDenominator.setTextColor(getContext().getResources().getColor(R.color.vv_progress_bg_color));
                                                    TextView textView = this.mAlertRecordingTextDenominator;
                                                    textView.setText("/" + timerBurstTotalCount);
                                                    break;
                                                }
                                        }
                                    } else if (CameraSettings.isGifOn()) {
                                        this.mAlertRecordingText.setText("00:05");
                                    } else {
                                        this.mAlertRecordingText.setText("00:15");
                                    }
                                }
                            }
                        }
                        this.mAlertRecordingText.setText("00:15");
                    }
                    int i3 = this.mCurrentMode;
                    if (i3 == 180) {
                        if (this.mManualParameterResetTip.getVisibility() == 0) {
                            this.mManualParameterResetTip.setVisibility(4);
                            this.mIsParameterResetVisibleBeforeRecording = true;
                        }
                        if (this.mManualParameterDescriptionTip.getVisibility() == 0) {
                            this.mManualParameterDescriptionTip.setVisibility(4);
                            this.mIsParameterDescriptionVisibleBeforeRecording = true;
                        }
                    } else if (i3 == 187) {
                        this.mAlertRecordingText.setTypeface(Typeface.create("monospace", 1));
                        if (this.mManualParameterDescriptionTip.getVisibility() == 0) {
                            this.mManualParameterDescriptionTip.setVisibility(4);
                            this.mIsParameterDescriptionVisibleBeforeRecording = true;
                        }
                    } else if (i3 == 169 && DataRepository.dataItemFeature().uj() && this.mManualParameterDescriptionTip.getVisibility() == 0) {
                        this.mManualParameterDescriptionTip.setVisibility(4);
                        this.mIsParameterDescriptionVisibleBeforeRecording = true;
                    }
                    if (z) {
                        this.mAlertRecordingText.setVisibility(8);
                    } else {
                        this.mAlertRecordingText.setText("00:00");
                    }
                } else if (timerBurstController.isInTimerBurstShotting()) {
                    this.mAlertRecordingText.setVisibility(8);
                    this.mAlertRecordingTextNumerator.setVisibility(0);
                    int timerBurstTotalCount2 = CameraSettings.getTimerBurstTotalCount();
                    this.mAlertRecordingTextNumerator.setText(String.valueOf(timerBurstController.getCaptureIndex()));
                    this.mAlertRecordingTextDenominator.setVisibility(0);
                    this.mAlertRecordingTextDenominator.setTextColor(getContext().getResources().getColor(R.color.vv_progress_bg_color));
                    TextView textView2 = this.mAlertRecordingTextDenominator;
                    textView2.setText("/" + timerBurstTotalCount2);
                }
            } else if (timerBurstController.isInTimerBurstShotting()) {
                this.mAlertRecordingText.setVisibility(8);
                this.mAlertRecordingTextNumerator.setVisibility(0);
                int timerBurstTotalCount3 = CameraSettings.getTimerBurstTotalCount();
                this.mAlertRecordingTextNumerator.setText(String.valueOf(timerBurstController.getCaptureIndex()));
                this.mAlertRecordingTextDenominator.setVisibility(0);
                this.mAlertRecordingTextDenominator.setTextColor(getContext().getResources().getColor(R.color.vv_progress_bg_color));
                TextView textView3 = this.mAlertRecordingTextDenominator;
                textView3.setText("/" + timerBurstTotalCount3);
            }
            if (!z && !timerBurstController.isInTimerBurstShotting()) {
                Completable.create(new AlphaInOnSubscribe(this.mAlertRecordingText)).subscribe();
            }
        } else if (i == 2) {
            this.mIsVideoRecording = false;
            this.mAlertRecordingTextNumerator.setVisibility(8);
            this.mAlertRecordingTextDenominator.setVisibility(8);
            int i4 = this.mCurrentMode;
            if (i4 == 180) {
                if (this.mIsParameterResetVisibleBeforeRecording) {
                    this.mIsParameterResetVisibleBeforeRecording = false;
                    this.mManualParameterResetTip.setVisibility(0);
                }
                if (this.mIsParameterDescriptionVisibleBeforeRecording) {
                    this.mIsParameterDescriptionVisibleBeforeRecording = false;
                    this.mManualParameterDescriptionTip.setVisibility(0);
                }
            } else if ((i4 == 187 || (i4 == 169 && DataRepository.dataItemFeature().uj())) && this.mIsParameterDescriptionVisibleBeforeRecording) {
                this.mIsParameterDescriptionVisibleBeforeRecording = false;
                this.mManualParameterDescriptionTip.setVisibility(0);
            }
            AlphaAnimation alphaAnimation2 = this.mBlingAnimation;
            if (alphaAnimation2 != null) {
                alphaAnimation2.cancel();
            }
            if (this.mAlertRecordingText.getVisibility() != 0 || this.mAlertRecordingText.getAlpha() == 0.0f) {
                AlphaOutOnSubscribe.directSetResult(this.mAlertRecordingText);
            } else {
                Completable.create(new AlphaOutOnSubscribe(this.mAlertRecordingText)).subscribe();
            }
            updateFlashForPhotoRecording(false);
        } else if (i == 3) {
            if (this.mBlingAnimation == null) {
                this.mBlingAnimation = new AlphaAnimation(1.0f, 0.0f);
                this.mBlingAnimation.setDuration(400);
                this.mBlingAnimation.setStartOffset(100);
                this.mBlingAnimation.setInterpolator(new DecelerateInterpolator());
                this.mBlingAnimation.setRepeatMode(2);
                this.mBlingAnimation.setRepeatCount(-1);
            }
            this.mAlertRecordingText.startAnimation(this.mBlingAnimation);
        } else if (i == 4 && (alphaAnimation = this.mBlingAnimation) != null) {
            alphaAnimation.cancel();
        }
    }

    public void setShow(boolean z) {
        this.mShow = z;
    }

    public void showDocumentStateButton(int i) {
        if (this.mDocumentStateButton == null) {
            initDocumentStateButton();
        }
        if (i == 0) {
            addViewToTipLayout(this.mDocumentStateButton, true, null, this.mTopTipLayout.getChildCount() > 0 ? 1 : -1);
            updateDocumentState(false);
            return;
        }
        removeViewToTipLayout(this.mDocumentStateButton);
    }

    public void showRecordingTime() {
        TextView textView = this.mAlertRecordingText;
        if (textView != null && this.mIsVideoRecording) {
            AlphaAnimation alphaAnimation = this.mBlingAnimation;
            if (alphaAnimation != null) {
                textView.startAnimation(alphaAnimation);
            }
            this.mAlertRecordingText.setVisibility(0);
        }
    }

    public void updateEndGravityTip() {
        LinearLayout linearLayout = this.mEndGravityTipLayout;
        if (linearLayout != null) {
            linearLayout.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                /* class com.android.camera.fragment.top.FragmentTopAlert.AnonymousClass15 */

                public void onGlobalLayout() {
                    FragmentTopAlert.this.mEndGravityTipLayout.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                    if (FragmentTopAlert.this.isAdded()) {
                        FragmentTopAlert fragmentTopAlert = FragmentTopAlert.this;
                        fragmentTopAlert.initEndGravityTipLayout(((BaseFragment) fragmentTopAlert).mDegree);
                    }
                }
            });
        }
    }

    public void updateHistogramStatsData(int[] iArr) {
        HistogramView histogramView = this.mHistogramView;
        if (histogramView != null) {
            histogramView.updateStats(iArr);
        }
    }

    public void updateHistogramStatsData(int[] iArr, int[] iArr2, int[] iArr3) {
        this.mHistogramView.updateStats(iArr, iArr2, iArr3);
    }

    public void updateLyingDirectHint(boolean z) {
        if (z && this.mTopTipLayout.indexOfChild(this.mLyingDirectHintText) == -1) {
            addViewToTipLayout(this.mLyingDirectHintText, new c(this), b.INSTANCE);
        } else if (!z && this.mTopTipLayout.indexOfChild(this.mLyingDirectHintText) != -1) {
            removeViewToTipLayout(this.mLyingDirectHintText);
        }
    }

    public void updateProVideoRecordingSimpleView(boolean z) {
        LottieAnimationView lottieAnimationView = this.mProVideoRecordingSimpleView;
        if (lottieAnimationView == null) {
            return;
        }
        if (z) {
            lottieAnimationView.setVisibility(0);
            return;
        }
        lottieAnimationView.setVisibility(8);
        if (DataRepository.dataItemRunning().getProVideoRecordingSimpleRunning()) {
            DataRepository.dataItemRunning().setProVideoRecordingSimpleRunning(false);
            ModeProtocol.ManuallyAdjust manuallyAdjust = (ModeProtocol.ManuallyAdjust) ModeCoordinatorImpl.getInstance().getAttachProtocol(181);
            if (manuallyAdjust != null) {
                manuallyAdjust.forceUpdateManualView(true);
            }
            if (CameraSettings.isProVideoHistogramOpen(this.mCurrentMode)) {
                Completable.create(new AlphaInOnSubscribe(this.mHistogramView)).subscribe();
            }
            if (this.mIsVideoUltraClearTipVisibleBeforeProVideoSimple) {
                this.mIsVideoUltraClearTipVisibleBeforeProVideoSimple = false;
                Completable.create(new AlphaInOnSubscribe(this.mVideoUltraClearTip)).subscribe();
            }
        }
    }

    public void updateRGBHistogramSwitched(boolean z) {
        if (z) {
            AlphaInOnSubscribe.directSetResult(this.mHistogramView);
        } else {
            AlphaOutOnSubscribe.directSetResult(this.mHistogramView);
        }
    }

    public void updateRecordingTime(String str) {
        this.mAlertRecordingText.setText(str);
    }

    public void updateRecordingTimeStyle(boolean z) {
        if (z) {
            this.mAlertRecordingText.setCompoundDrawablesWithIntrinsicBounds(R.drawable.v6_ic_video_recordtime_indicator, 0, 0, 0);
        } else {
            this.mAlertRecordingText.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
        }
    }

    public void updateTopAlertLayout() {
        if (isLandScape()) {
            initLandscapeTopTipLayout();
        } else {
            initPortraitTopTipLayout();
        }
    }
}
