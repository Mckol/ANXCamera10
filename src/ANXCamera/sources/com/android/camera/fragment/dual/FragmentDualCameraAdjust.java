package com.android.camera.fragment.dual;

import android.animation.Animator;
import android.animation.AnimatorInflater;
import android.animation.AnimatorSet;
import android.animation.ValueAnimator;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.ViewCompat;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.LinearInterpolator;
import android.view.animation.OvershootInterpolator;
import com.android.camera.CameraSettings;
import com.android.camera.HybridZoomingSystem;
import com.android.camera.R;
import com.android.camera.Util;
import com.android.camera.animation.type.AlphaInOnSubscribe;
import com.android.camera.animation.type.AlphaOutOnSubscribe;
import com.android.camera.animation.type.SlideInOnSubscribe;
import com.android.camera.animation.type.SlideOutOnSubscribe;
import com.android.camera.animation.type.TranslateYAlphaOutOnSubscribe;
import com.android.camera.animation.type.TranslateYOnSubscribe;
import com.android.camera.data.DataRepository;
import com.android.camera.data.data.ComponentData;
import com.android.camera.data.data.config.ComponentManuallyDualLens;
import com.android.camera.data.data.config.ComponentManuallyDualZoom;
import com.android.camera.fragment.BaseFragment;
import com.android.camera.fragment.manually.ManuallyListener;
import com.android.camera.fragment.manually.adapter.AbstractZoomSliderAdapter;
import com.android.camera.fragment.manually.adapter.sat.DuoSatZoomSliderAdapter;
import com.android.camera.fragment.manually.adapter.sat.FourSatZoomSliderAdapter;
import com.android.camera.fragment.manually.adapter.sat.TriSatZoomSliderAdapter;
import com.android.camera.fragment.top.FragmentTopAlert;
import com.android.camera.lib.compatibility.related.vibrator.ViberatorContext;
import com.android.camera.log.Log;
import com.android.camera.protocol.ModeCoordinatorImpl;
import com.android.camera.protocol.ModeProtocol;
import com.android.camera.statistic.CameraStat;
import com.android.camera.statistic.CameraStatUtil;
import com.android.camera.ui.BaseHorizontalZoomView;
import com.android.camera.ui.HorizontalSlideView;
import com.android.camera.ui.HorizontalZoomView;
import com.android.camera.ui.zoom.ZoomRatioToggleView;
import com.android.camera.ui.zoom.ZoomRatioView;
import com.mi.config.b;
import io.reactivex.Completable;
import io.reactivex.functions.Action;
import java.util.List;
import miui.view.animation.BackEaseOutInterpolator;
import miui.view.animation.ElasticEaseOutInterpolator;
import miui.view.animation.QuadraticEaseInOutInterpolator;

public class FragmentDualCameraAdjust extends BaseFragment implements ManuallyListener, ModeProtocol.DualController, ModeProtocol.HandleBackTrace, ModeProtocol.SnapShotIndicator, ZoomRatioToggleView.ToggleStateListener {
    public static final int FRAGMENT_INFO = 4084;
    private static final int HIDE_POPUP = 1;
    private static final String TAG = "FragmentDualCameraAdjust";
    private int mCurrentState = -1;
    private ViewGroup mDualParentLayout;
    private Handler mHandler = new Handler() {
        public void handleMessage(Message message) {
            if (message.what == 1) {
                FragmentDualCameraAdjust.this.onBackEvent(5);
            }
        }
    };
    private View mHorizontalBottomLine;
    /* access modifiers changed from: private */
    public ViewGroup mHorizontalSlideLayout;
    private BaseHorizontalZoomView mHorizontalSlideView;
    private View mHorizontalTopLine;
    private BaseHorizontalZoomView mHorizontalZoomView;
    private BaseHorizontalZoomView mHorizontalZoomViewSlide;
    /* access modifiers changed from: private */
    public boolean mIsHiding;
    private boolean mIsRecordingOrPausing = false;
    private boolean mIsStandaloneMacroMode = false;
    private boolean mIsUseSlider = false;
    /* access modifiers changed from: private */
    public boolean mIsZoomTo2X;
    private boolean mPassTouchFromZoomButtonToSlide;
    private AbstractZoomSliderAdapter mSlidingAdapter;
    private float mTouchDownX = -1.0f;
    /* access modifiers changed from: private */
    public AnimatorSet mZoomInAnimator;
    /* access modifiers changed from: private */
    public AnimatorSet mZoomOutAnimator;
    private float mZoomRatio;
    private ValueAnimator mZoomRatioToggleAnimator;
    private ZoomRatioToggleView mZoomRatioToggleView;
    private int mZoomSliderLayoutHeight;
    private View.OnTouchListener mZoomSliderViewTouchListener = new View.OnTouchListener() {
        private boolean mAnimated = false;

        public boolean onTouch(View view, MotionEvent motionEvent) {
            if (motionEvent.getAction() == 2) {
                if (!this.mAnimated) {
                    FragmentDualCameraAdjust.this.mZoomInAnimator.start();
                    this.mAnimated = true;
                }
            } else if ((motionEvent.getAction() == 1 || motionEvent.getAction() == 3) && this.mAnimated) {
                FragmentDualCameraAdjust.this.mZoomOutAnimator.start();
                this.mAnimated = false;
            }
            FragmentDualCameraAdjust.this.sendHideMessage();
            return false;
        }
    };
    private float mZoomSliderViewX = -1.0f;
    private int mZoomSwitchLayoutHeight;

    private void adjustViewBackground(View view, int i) {
        if (!HybridZoomingSystem.IS_4_SAT) {
            view.setBackgroundResource(R.color.fullscreen_background);
            return;
        }
        view.setBackgroundDrawable((Drawable) null);
        this.mHorizontalTopLine.setVisibility(8);
        this.mHorizontalBottomLine.setVisibility(8);
    }

    private void alphaOutZoomButtonAndSlideView() {
        this.mHandler.removeMessages(1);
        if (this.mHorizontalSlideLayout.getVisibility() == 0) {
            this.mIsHiding = true;
            this.mSlidingAdapter.setEnable(false);
            Completable.create(new TranslateYAlphaOutOnSubscribe(this.mHorizontalSlideLayout, this.mZoomSliderLayoutHeight).setInterpolator(new OvershootInterpolator())).subscribe((Action) new Action() {
                public void run() throws Exception {
                    boolean unused = FragmentDualCameraAdjust.this.mIsHiding = false;
                    FragmentDualCameraAdjust.this.mHorizontalSlideLayout.setVisibility(4);
                }
            });
            if (this.mCurrentMode == 163) {
                ModeProtocol.CameraModuleSpecial cameraModuleSpecial = (ModeProtocol.CameraModuleSpecial) ModeCoordinatorImpl.getInstance().getAttachProtocol(195);
                if (cameraModuleSpecial != null) {
                    cameraModuleSpecial.showOrHideChip(false);
                }
            }
            this.mCurrentState = -1;
            ViewCompat.setTranslationY(this.mZoomRatioToggleView, 0.0f);
            Completable.create(new TranslateYAlphaOutOnSubscribe(this.mZoomRatioToggleView, this.mZoomSliderLayoutHeight).setInterpolator(new OvershootInterpolator())).subscribe();
        } else {
            hideZoomButton();
            ModeProtocol.TopAlert topAlert = (ModeProtocol.TopAlert) ModeCoordinatorImpl.getInstance().getAttachProtocol(172);
            if (topAlert != null) {
                topAlert.alertUpdateValue(2);
            }
        }
        notifyTipsMargin();
    }

    /* JADX WARNING: Removed duplicated region for block: B:119:0x019a  */
    /* JADX WARNING: Removed duplicated region for block: B:51:0x00b2  */
    /* JADX WARNING: Removed duplicated region for block: B:52:0x00b4  */
    /* JADX WARNING: Removed duplicated region for block: B:54:0x00b9  */
    private static ZoomRatioToggleView.ViewSpec getViewSpecForCapturingMode(int i) {
        boolean z;
        boolean z2;
        int i2;
        int i3;
        boolean z3 = false;
        int i4 = -1;
        boolean z4 = true;
        if (DataRepository.dataItemGlobal().getCurrentCameraId() != 1 && ((!CameraSettings.isMacroModeEnabled(i) || DataRepository.dataItemFeature().ii()) && !CameraSettings.isAutoZoomEnabled(i) && !CameraSettings.isSuperEISEnabled(i) && HybridZoomingSystem.IS_2_OR_MORE_SAT)) {
            if (i == 161) {
                i2 = (!HybridZoomingSystem.IS_2_SAT && !CameraSettings.isUltraWideConfigOpen(i)) ? 1 : -1;
                z2 = i2 == -1;
                if (i2 != -1) {
                    z4 = false;
                }
            } else if (i == 174 || i == 179) {
                i2 = (!HybridZoomingSystem.IS_2_SAT && !CameraSettings.isUltraWideConfigOpen(i)) ? 1 : -1;
                z2 = i2 == -1;
                if (i2 != -1) {
                    z4 = false;
                }
            } else if (i == 162) {
                if (!HybridZoomingSystem.IS_2_SAT) {
                    ModeProtocol.MiBeautyProtocol miBeautyProtocol = (ModeProtocol.MiBeautyProtocol) ModeCoordinatorImpl.getInstance().getAttachProtocol(194);
                    if ((miBeautyProtocol == null || !miBeautyProtocol.isBeautyPanelShow()) && (((!CameraSettings.isMacroModeEnabled(i) || DataRepository.dataItemFeature().ii()) && !CameraSettings.isUltraWideConfigOpen(i)) || DataRepository.dataItemFeature().hH())) {
                        i3 = 1;
                        z2 = i2 != -1;
                        if (i2 != -1) {
                            z4 = false;
                        }
                    }
                }
                i3 = -1;
                if (i2 != -1) {
                }
                if (i2 != -1) {
                }
            } else if (i == 169) {
                i2 = HybridZoomingSystem.IS_2_SAT ? -1 : 1;
                z2 = i2 == -1;
                if (i2 != -1) {
                    z4 = false;
                }
            } else {
                if (i == 163) {
                    if (((!CameraSettings.isMacroModeEnabled(i) || DataRepository.dataItemFeature().ii()) && !CameraSettings.isUltraWideConfigOpen(i) && !CameraSettings.isUltraPixelRearOn()) || DataRepository.dataItemFeature().hH()) {
                        i4 = 1;
                    }
                    z2 = HybridZoomingSystem.IS_2_SAT;
                    z = HybridZoomingSystem.IS_2_SAT;
                    if (CameraSettings.isMacroModeEnabled(i) && DataRepository.dataItemFeature().ii()) {
                        z4 = false;
                    }
                } else if (i == 165) {
                    if ((!CameraSettings.isMacroModeEnabled(i) || DataRepository.dataItemFeature().ii()) && !CameraSettings.isUltraWideConfigOpen(i)) {
                        i4 = 1;
                    }
                    z2 = HybridZoomingSystem.IS_2_SAT;
                    z = HybridZoomingSystem.IS_2_SAT;
                    if (CameraSettings.isMacroModeEnabled(i) && DataRepository.dataItemFeature().ii()) {
                        z4 = false;
                    }
                } else if (i == 173) {
                    z2 = true;
                    i4 = 1;
                    z = true;
                } else if (i == 166 && DataRepository.dataItemFeature().gN()) {
                    i4 = 1;
                }
                if (CameraSettings.isSupportedOpticalZoom()) {
                    z3 = z4;
                }
                return new ZoomRatioToggleView.ViewSpec(i4, z2, z, z3);
            }
            i4 = i2;
            z = z4;
            z4 = false;
            if (CameraSettings.isSupportedOpticalZoom()) {
            }
            return new ZoomRatioToggleView.ViewSpec(i4, z2, z, z3);
        }
        z2 = true;
        z = true;
        z4 = false;
        if (CameraSettings.isSupportedOpticalZoom()) {
        }
        return new ZoomRatioToggleView.ViewSpec(i4, z2, z, z3);
    }

    private void initSlideZoomView(ComponentData componentData) {
        if (HybridZoomingSystem.IS_4_SAT) {
            this.mSlidingAdapter = new FourSatZoomSliderAdapter(getContext(), componentData, this.mCurrentMode, this);
        } else if (HybridZoomingSystem.IS_2_SAT || this.mCurrentMode == 173) {
            this.mSlidingAdapter = new DuoSatZoomSliderAdapter(getContext(), componentData, this.mCurrentMode, this);
        } else if (HybridZoomingSystem.IS_3_SAT) {
            this.mSlidingAdapter = new TriSatZoomSliderAdapter(getContext(), componentData, this.mCurrentMode, this);
        }
        this.mHorizontalSlideView.setOnPositionSelectListener(this.mSlidingAdapter);
        this.mHorizontalSlideView.setJustifyEnabled(false);
        this.mHorizontalSlideView.setDrawAdapter(this.mSlidingAdapter);
    }

    private void initiateZoomRatio() {
        if (CameraSettings.isZoomByCameraSwitchingSupported()) {
            String cameraLensType = CameraSettings.getCameraLensType(this.mCurrentMode);
            if (ComponentManuallyDualLens.LENS_ULTRA.equals(cameraLensType)) {
                this.mZoomRatio = 0.6f;
            } else if (ComponentManuallyDualLens.LENS_WIDE.equals(cameraLensType)) {
                this.mZoomRatio = 1.0f;
            } else if (ComponentManuallyDualLens.LENS_TELE.equals(cameraLensType)) {
                this.mZoomRatio = 2.0f;
            } else if ("macro".equals(cameraLensType)) {
                this.mZoomRatio = HybridZoomingSystem.sDefaultStandaloneMacroOpticalZoomRatio;
            } else if (ComponentManuallyDualLens.LENS_STANDALONE.equals(cameraLensType)) {
                this.mZoomRatio = 5.0f;
            } else {
                throw new IllegalStateException("initiateZoomRatio(): Unknown camera lens type: " + cameraLensType);
            }
            Log.d(TAG, "initiateZoomRatio(): lens-switch-zoom: " + this.mZoomRatio);
        } else if (HybridZoomingSystem.IS_3_OR_MORE_SAT && this.mCurrentMode == 162) {
            this.mZoomRatio = Float.parseFloat(HybridZoomingSystem.getZoomRatioHistory(this.mCurrentMode, "1.0"));
            Log.d(TAG, "initiateZoomRatio(): fake-sat-zoom: " + this.mZoomRatio);
        } else if (HybridZoomingSystem.IS_3_OR_MORE_SAT && this.mCurrentMode == 174) {
            this.mZoomRatio = Float.parseFloat(HybridZoomingSystem.getZoomRatioHistory(this.mCurrentMode, "1.0"));
            Log.d(TAG, "initiateZoomRatio(): fake-sat-zoom: " + this.mZoomRatio);
        } else if (HybridZoomingSystem.IS_3_OR_MORE_SAT && this.mCurrentMode == 161) {
            this.mZoomRatio = Float.parseFloat(HybridZoomingSystem.getZoomRatioHistory(this.mCurrentMode, "1.0"));
            Log.d(TAG, "initiateZoomRatio(): fake-sat-zoom: " + this.mZoomRatio);
        } else if (HybridZoomingSystem.IS_3_OR_MORE_SAT && (this.mCurrentMode == 163 || this.mCurrentMode == 165 || this.mCurrentMode == 173 || this.mCurrentMode == 177 || this.mCurrentState == 167)) {
            this.mZoomRatio = Float.parseFloat(HybridZoomingSystem.getZoomRatioHistory(this.mCurrentMode, "1.0"));
            Log.d(TAG, "initiateZoomRatio(): fake-sat-zoom: " + this.mZoomRatio);
        } else if (!HybridZoomingSystem.IS_3_OR_MORE_SAT || this.mCurrentMode != 169) {
            this.mZoomRatio = 1.0f;
            Log.d(TAG, "initiateZoomRatio(): real-sat-zoom: " + this.mZoomRatio);
        } else {
            this.mZoomRatio = Float.parseFloat(HybridZoomingSystem.getZoomRatioHistory(this.mCurrentMode, "1.0"));
            Log.d(TAG, "initiateZoomRatio(): fake-sat-zoom: " + this.mZoomRatio);
        }
    }

    private void notifyTipsMargin() {
        ModeProtocol.BottomPopupTips bottomPopupTips = (ModeProtocol.BottomPopupTips) ModeCoordinatorImpl.getInstance().getAttachProtocol(175);
        if (bottomPopupTips != null) {
            bottomPopupTips.directHideTipImage();
            bottomPopupTips.directlyHideTips();
            bottomPopupTips.directShowOrHideLeftTipImage(false);
        }
    }

    /* access modifiers changed from: private */
    public void notifyZoom2X(boolean z) {
        ModeProtocol.ManuallyValueChanged manuallyValueChanged = (ModeProtocol.ManuallyValueChanged) ModeCoordinatorImpl.getInstance().getAttachProtocol(174);
        if (manuallyValueChanged == null) {
            return;
        }
        if (Util.isZoomAnimationEnabled() || z) {
            manuallyValueChanged.onDualZoomHappened(z);
        }
    }

    /* access modifiers changed from: private */
    public void notifyZooming(boolean z) {
        ModeProtocol.ManuallyValueChanged manuallyValueChanged = (ModeProtocol.ManuallyValueChanged) ModeCoordinatorImpl.getInstance().getAttachProtocol(174);
        if (manuallyValueChanged != null) {
            manuallyValueChanged.onDualLensZooming(z);
        }
    }

    /* access modifiers changed from: private */
    public void requestZoomRatio(float f, int i) {
        ModeProtocol.ManuallyValueChanged manuallyValueChanged = (ModeProtocol.ManuallyValueChanged) ModeCoordinatorImpl.getInstance().getAttachProtocol(174);
        if (manuallyValueChanged != null) {
            manuallyValueChanged.onDualZoomValueChanged(f, i);
        }
    }

    /* access modifiers changed from: private */
    public void sendHideMessage() {
        this.mHandler.removeMessages(1);
        this.mHandler.sendEmptyMessageDelayed(1, FragmentTopAlert.HINT_DELAY_TIME);
    }

    private boolean showSlideView() {
        if (isSlideVisible() || !this.mIsUseSlider) {
            return false;
        }
        initSlideZoomView(new ComponentManuallyDualZoom(DataRepository.dataItemRunning()));
        this.mIsHiding = false;
        this.mSlidingAdapter.setEnable(true);
        this.mHorizontalSlideLayout.setVisibility(0);
        ViewCompat.setTranslationY(this.mHorizontalSlideLayout, (float) this.mZoomSliderLayoutHeight);
        ViewCompat.setAlpha(this.mHorizontalSlideLayout, 1.0f);
        Completable.create(new TranslateYOnSubscribe(this.mHorizontalSlideLayout, 0).setInterpolator(new DecelerateInterpolator())).subscribe();
        setImmersiveModeEnabled(true);
        ViewCompat.setTranslationY(this.mZoomRatioToggleView, (float) this.mZoomSliderLayoutHeight);
        Completable.create(new TranslateYOnSubscribe(this.mZoomRatioToggleView, 0).setInterpolator(new BackEaseOutInterpolator())).subscribe();
        notifyTipsMargin();
        if (this.mCurrentMode == 163) {
            ModeProtocol.CameraModuleSpecial cameraModuleSpecial = (ModeProtocol.CameraModuleSpecial) ModeCoordinatorImpl.getInstance().getAttachProtocol(195);
            if (cameraModuleSpecial != null) {
                cameraModuleSpecial.showOrHideChip(false);
            }
        }
        ModeProtocol.BottomPopupTips bottomPopupTips = (ModeProtocol.BottomPopupTips) ModeCoordinatorImpl.getInstance().getAttachProtocol(175);
        if (bottomPopupTips != null) {
            bottomPopupTips.directHideLyingDirectHint();
        }
        this.mPassTouchFromZoomButtonToSlide = true;
        ModeProtocol.BottomPopupTips bottomPopupTips2 = (ModeProtocol.BottomPopupTips) ModeCoordinatorImpl.getInstance().getAttachProtocol(175);
        if (bottomPopupTips2 != null) {
            bottomPopupTips2.hideQrCodeTip();
        }
        ModeProtocol.ManuallyValueChanged manuallyValueChanged = (ModeProtocol.ManuallyValueChanged) ModeCoordinatorImpl.getInstance().getAttachProtocol(174);
        if (manuallyValueChanged != null) {
            manuallyValueChanged.updateSATIsZooming(true);
        }
        return true;
    }

    private void switchCameraLens() {
        String str;
        ComponentManuallyDualLens manuallyDualLens = DataRepository.dataItemConfig().getManuallyDualLens();
        ModeProtocol.ManuallyValueChanged manuallyValueChanged = (ModeProtocol.ManuallyValueChanged) ModeCoordinatorImpl.getInstance().getAttachProtocol(174);
        if (manuallyValueChanged != null) {
            manuallyValueChanged.onDualLensSwitch(manuallyDualLens, this.mCurrentMode);
            updateZoomRatio(0);
        }
        String componentValue = manuallyDualLens.getComponentValue(this.mCurrentMode);
        if (ComponentManuallyDualLens.LENS_ULTRA.equals(componentValue)) {
            str = HybridZoomingSystem.STRING_ZOOM_RATIO_ULTR;
        } else if (ComponentManuallyDualLens.LENS_WIDE.equals(componentValue)) {
            str = HybridZoomingSystem.STRING_ZOOM_RATIO_WIDE;
        } else if (ComponentManuallyDualLens.LENS_TELE.equals(componentValue)) {
            str = HybridZoomingSystem.STRING_ZOOM_RATIO_TELE;
        } else {
            throw new IllegalStateException("switchCameraLens(): Unknown camera lens type: " + componentValue);
        }
        CameraStatUtil.trackDualZoomChanged(this.mCurrentMode, str);
    }

    private void updateZoomSlider(boolean z) {
        if (HybridZoomingSystem.IS_4_SAT) {
            updateZoomSliderPosition(z);
        } else if (HybridZoomingSystem.IS_2_SAT || this.mCurrentMode == 173) {
            float decimal = HybridZoomingSystem.toDecimal(CameraSettings.readZoom());
            if (decimal < 1.0f || decimal > 2.0f) {
                updateZoomSliderPosition(z);
                return;
            }
            int i = ((int) (decimal * 10.0f)) - 10;
            if (z) {
                this.mHorizontalSlideView.setSelection(i);
            } else {
                this.mHorizontalSlideView.setSelectionUpdateUI(i);
            }
        } else if (HybridZoomingSystem.IS_3_SAT) {
            float decimal2 = HybridZoomingSystem.toDecimal(CameraSettings.readZoom());
            if (decimal2 < 0.6f || decimal2 > 2.0f) {
                updateZoomSliderPosition(z);
                return;
            }
            int i2 = ((int) (decimal2 * 10.0f)) - 6;
            if (z) {
                this.mHorizontalSlideView.setSelection(i2);
            } else {
                this.mHorizontalSlideView.setSelectionUpdateUI(i2);
            }
        }
    }

    private void updateZoomSliderPosition(boolean z) {
        if (this.mSlidingAdapter != null && this.mHorizontalSlideView != null) {
            this.mHorizontalSlideView.setSelection(this.mSlidingAdapter.mapZoomRatioToPosition(CameraSettings.readZoom()) / ((float) (this.mSlidingAdapter.getCount() - 1)), z);
        }
    }

    public int getFragmentInto() {
        return 4084;
    }

    /* access modifiers changed from: protected */
    public int getLayoutResourceId() {
        return R.layout.fragment_dual_camera_adjust;
    }

    public void hideSlideView() {
        this.mHandler.removeMessages(1);
        if (this.mHorizontalSlideLayout.getVisibility() == 0) {
            this.mIsHiding = true;
            this.mSlidingAdapter.setEnable(false);
            Completable.create(new TranslateYOnSubscribe(this.mHorizontalSlideLayout, this.mZoomSliderLayoutHeight).setInterpolator(new ElasticEaseOutInterpolator(1.1f, 2.0f))).subscribe((Action) new Action() {
                public void run() throws Exception {
                    boolean unused = FragmentDualCameraAdjust.this.mIsHiding = false;
                    FragmentDualCameraAdjust.this.mHorizontalSlideLayout.setVisibility(4);
                }
            });
            ModeProtocol.ManuallyValueChanged manuallyValueChanged = (ModeProtocol.ManuallyValueChanged) ModeCoordinatorImpl.getInstance().getAttachProtocol(174);
            if (manuallyValueChanged != null) {
                manuallyValueChanged.updateSATIsZooming(false);
            }
            ViewCompat.setTranslationY(this.mZoomRatioToggleView, 0.0f);
            setImmersiveModeEnabled(false);
            Completable.create(new TranslateYOnSubscribe(this.mZoomRatioToggleView, this.mZoomSliderLayoutHeight).setInterpolator(new OvershootInterpolator())).subscribe();
            ModeProtocol.BottomPopupTips bottomPopupTips = (ModeProtocol.BottomPopupTips) ModeCoordinatorImpl.getInstance().getAttachProtocol(175);
            if (bottomPopupTips != null) {
                bottomPopupTips.reInitTipImage();
            }
            if (bottomPopupTips != null) {
                bottomPopupTips.updateLyingDirectHint(false, true);
            }
            if (this.mCurrentMode == 163) {
                ModeProtocol.CameraModuleSpecial cameraModuleSpecial = (ModeProtocol.CameraModuleSpecial) ModeCoordinatorImpl.getInstance().getAttachProtocol(195);
                if (cameraModuleSpecial != null) {
                    cameraModuleSpecial.showOrHideChip(true);
                }
            }
        }
    }

    public void hideZoomButton() {
        if (this.mCurrentState != -1) {
            this.mCurrentState = -1;
            Completable.create(new AlphaOutOnSubscribe(this.mZoomRatioToggleView)).subscribe();
            if (this.mHorizontalSlideLayout != null && this.mHorizontalSlideLayout.getVisibility() == 0) {
                this.mHandler.removeMessages(1);
                this.mIsHiding = true;
                this.mSlidingAdapter.setEnable(false);
                this.mHorizontalSlideLayout.setVisibility(4);
                ViewCompat.setTranslationY(this.mHorizontalSlideLayout, (float) this.mZoomSliderLayoutHeight);
                ViewCompat.setTranslationY(this.mZoomRatioToggleView, (float) this.mZoomSliderLayoutHeight);
            }
        }
    }

    /* access modifiers changed from: protected */
    public void initView(View view) {
        ((ViewGroup.MarginLayoutParams) view.getLayoutParams()).bottomMargin = Util.getBottomHeight(getResources());
        this.mDualParentLayout = (ViewGroup) view.findViewById(R.id.dual_layout_parent);
        this.mHorizontalSlideLayout = (ViewGroup) view.findViewById(R.id.dual_camera_zoom_slider_container);
        this.mHorizontalTopLine = view.findViewById(R.id.dual_camera_zoom_slider_top_line);
        this.mHorizontalBottomLine = view.findViewById(R.id.dual_camera_zoom_slider_bottom_line);
        this.mZoomRatioToggleView = (ZoomRatioToggleView) view.findViewById(R.id.zoom_ratio_toggle_button);
        this.mZoomRatioToggleView.setActionListener(this);
        View findViewById = view.findViewById(R.id.sat_optical_zoom_switch_simulator);
        findViewById.setOnClickListener(this.mZoomRatioToggleView);
        findViewById.setOnLongClickListener(this.mZoomRatioToggleView);
        this.mHorizontalZoomView = (HorizontalZoomView) view.findViewById(R.id.dual_camera_zoom);
        this.mHorizontalZoomViewSlide = (HorizontalSlideView) view.findViewById(R.id.dual_camera_zoom_slider);
        if (HybridZoomingSystem.IS_4_OR_MORE_SAT) {
            this.mHorizontalSlideView = this.mHorizontalZoomView;
            this.mHorizontalZoomViewSlide.setVisibility(8);
            this.mZoomSliderViewX = 0.0f;
        } else {
            this.mHorizontalSlideView = this.mHorizontalZoomViewSlide;
            this.mHorizontalZoomView.setVisibility(8);
            this.mZoomSliderViewX = (float) (getResources().getDisplayMetrics().widthPixels / 2);
        }
        this.mZoomSwitchLayoutHeight = this.mZoomRatioToggleView.getLayoutParams().height;
        this.mHorizontalSlideView.setOnTouchListener(this.mZoomSliderViewTouchListener);
        this.mZoomRatioToggleAnimator = new ValueAnimator();
        this.mZoomRatioToggleAnimator.setInterpolator(new LinearInterpolator());
        if (!Util.isZoomAnimationEnabled() || HybridZoomingSystem.IS_3_OR_MORE_SAT) {
            this.mZoomRatioToggleAnimator.setDuration(0);
        } else {
            this.mZoomRatioToggleAnimator.setDuration(100);
        }
        this.mZoomRatioToggleAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                float floatValue = ((Float) valueAnimator.getAnimatedValue()).floatValue();
                Log.d(FragmentDualCameraAdjust.TAG, "onAnimationUpdate(): zoom ratio = " + floatValue);
                FragmentDualCameraAdjust.this.requestZoomRatio(floatValue, 0);
            }
        });
        this.mZoomRatioToggleAnimator.addListener(new Animator.AnimatorListener() {
            public void onAnimationCancel(Animator animator) {
                FragmentDualCameraAdjust.this.notifyZooming(false);
                boolean unused = FragmentDualCameraAdjust.this.mIsZoomTo2X = false;
                FragmentDualCameraAdjust.this.notifyZoom2X(false);
            }

            public void onAnimationEnd(Animator animator) {
                FragmentDualCameraAdjust.this.notifyZooming(false);
                boolean unused = FragmentDualCameraAdjust.this.mIsZoomTo2X = false;
                FragmentDualCameraAdjust.this.notifyZoom2X(false);
            }

            public void onAnimationRepeat(Animator animator) {
            }

            public void onAnimationStart(Animator animator) {
                FragmentDualCameraAdjust.this.notifyZooming(true);
                if (FragmentDualCameraAdjust.this.mIsZoomTo2X) {
                    FragmentDualCameraAdjust.this.notifyZoom2X(true);
                } else {
                    FragmentDualCameraAdjust.this.notifyZoom2X(false);
                }
            }
        });
        this.mZoomInAnimator = (AnimatorSet) AnimatorInflater.loadAnimator(getContext(), R.animator.zoom_button_zoom_in);
        this.mZoomInAnimator.setTarget(this.mZoomRatioToggleView);
        this.mZoomInAnimator.setInterpolator(new QuadraticEaseInOutInterpolator());
        this.mZoomOutAnimator = (AnimatorSet) AnimatorInflater.loadAnimator(getContext(), R.animator.zoom_button_zoom_out);
        this.mZoomOutAnimator.setTarget(this.mZoomRatioToggleView);
        this.mZoomOutAnimator.setInterpolator(new QuadraticEaseInOutInterpolator());
        provideAnimateElement(this.mCurrentMode, (List<Completable>) null, 2);
    }

    public boolean isInteractive() {
        if (!isEnableClick()) {
            return false;
        }
        ModeProtocol.CameraAction cameraAction = (ModeProtocol.CameraAction) ModeCoordinatorImpl.getInstance().getAttachProtocol(161);
        if (cameraAction != null) {
            return !cameraAction.isDoingAction() && !cameraAction.isRecording();
        }
        return true;
    }

    public boolean isSlideVisible() {
        if (this.mCurrentState == -1 || this.mSlidingAdapter == null) {
            return false;
        }
        return this.mSlidingAdapter.isEnable();
    }

    public boolean isZoomVisible() {
        return this.mCurrentState == 1 && this.mZoomRatioToggleView.getVisibility() == 0;
    }

    public void notifyAfterFrameAvailable(int i) {
        super.notifyAfterFrameAvailable(i);
        provideAnimateElement(this.mCurrentMode, (List<Completable>) null, 2);
    }

    public void notifyDataChanged(int i, int i2) {
        super.notifyDataChanged(i, i2);
        if (i == 3) {
            adjustViewBackground(this.mHorizontalSlideLayout, this.mCurrentMode);
        }
    }

    public boolean onBackEvent(int i) {
        if (this.mDualParentLayout.getVisibility() != 0 || this.mIsHiding) {
            return false;
        }
        boolean z = i == 3 && this.mCurrentMode == 173;
        if (!z && this.mHorizontalSlideLayout.getVisibility() != 0) {
            return false;
        }
        if (z) {
            alphaOutZoomButtonAndSlideView();
        } else if (i == 3 || i == 2) {
            return false;
        } else {
            hideSlideView();
        }
        return true;
    }

    public void onClick(ZoomRatioView zoomRatioView) {
        int zoomRatioIndex = zoomRatioView.getZoomRatioIndex();
        if (!isSlideVisible()) {
            Log.d(TAG, "onClick(): current zoom ratio index = " + zoomRatioIndex);
            Log.d(TAG, "onClick(): current zoom ratio value = " + this.mZoomRatio);
            if (this.mZoomRatioToggleView.isSuppressed()) {
                if (CameraSettings.isZoomByCameraSwitchingSupported()) {
                    switchCameraLens();
                } else if (this.mZoomRatio == 1.0f) {
                    CameraStatUtil.trackDualZoomChanged(this.mCurrentMode, HybridZoomingSystem.STRING_ZOOM_RATIO_TELE);
                    this.mIsZoomTo2X = true;
                    this.mZoomRatioToggleAnimator.setFloatValues(new float[]{this.mZoomRatio, 2.0f});
                    this.mZoomRatioToggleAnimator.start();
                } else if (this.mZoomRatio <= 2.0f) {
                    CameraStatUtil.trackDualZoomChanged(this.mCurrentMode, HybridZoomingSystem.STRING_ZOOM_RATIO_WIDE);
                    this.mIsZoomTo2X = false;
                    this.mZoomRatioToggleAnimator.setFloatValues(new float[]{this.mZoomRatio, 1.0f});
                    this.mZoomRatioToggleAnimator.start();
                } else {
                    CameraStatUtil.trackDualZoomChanged(this.mCurrentMode, HybridZoomingSystem.STRING_ZOOM_RATIO_WIDE);
                    requestZoomRatio(2.0f, 0);
                    requestZoomRatio(1.0f, 0);
                }
            } else if (HybridZoomingSystem.IS_3_OR_MORE_SAT) {
                ModeProtocol.ConfigChanges configChanges = (ModeProtocol.ConfigChanges) ModeCoordinatorImpl.getInstance().getAttachProtocol(164);
                if (configChanges != null) {
                    ModeProtocol.CameraClickObservable cameraClickObservable = (ModeProtocol.CameraClickObservable) ModeCoordinatorImpl.getInstance().getAttachProtocol(227);
                    if (cameraClickObservable != null) {
                        cameraClickObservable.subscribe(167);
                    }
                    float opticalZoomRatioAt = HybridZoomingSystem.getOpticalZoomRatioAt(this.mCurrentMode, zoomRatioIndex);
                    if (HybridZoomingSystem.getMacroZoomRatioIndex(this.mCurrentMode) != -1 && zoomRatioIndex == HybridZoomingSystem.getMacroZoomRatioIndex(this.mCurrentMode)) {
                        CameraStatUtil.trackDualZoomChanged(this.mCurrentMode, HybridZoomingSystem.toString(opticalZoomRatioAt));
                        configChanges.onConfigChanged(255);
                        return;
                    } else if (HybridZoomingSystem.getMacroZoomRatioIndex(this.mCurrentMode) == -1 || !CameraSettings.isMacroModeEnabled(this.mCurrentMode)) {
                        CameraStatUtil.trackDualZoomChanged(this.mCurrentMode, HybridZoomingSystem.toString(opticalZoomRatioAt));
                        this.mIsZoomTo2X = false;
                        this.mZoomRatioToggleAnimator.setFloatValues(new float[]{this.mZoomRatio, opticalZoomRatioAt});
                        this.mZoomRatioToggleAnimator.start();
                    } else {
                        CameraSettings.writeZoom(opticalZoomRatioAt);
                        HybridZoomingSystem.setZoomRatioHistory(this.mCurrentMode, String.valueOf(opticalZoomRatioAt));
                        CameraStatUtil.trackDualZoomChanged(this.mCurrentMode, HybridZoomingSystem.toString(opticalZoomRatioAt));
                        configChanges.onConfigChanged(255);
                        if (DataRepository.dataItemFeature().iB()) {
                            CameraSettings.setMacro2Sat(true);
                            CameraSettings.setLensIndex(zoomRatioIndex);
                            return;
                        }
                        return;
                    }
                } else {
                    return;
                }
            }
            ViberatorContext.getInstance(getContext().getApplicationContext()).performSelectZoomNormal();
        }
        onBackEvent(5);
        ModeProtocol.BottomPopupTips bottomPopupTips = (ModeProtocol.BottomPopupTips) ModeCoordinatorImpl.getInstance().getAttachProtocol(175);
        if (bottomPopupTips != null) {
            bottomPopupTips.reConfigQrCodeTip();
        }
    }

    public void onManuallyDataChanged(ComponentData componentData, String str, String str2, boolean z, int i) {
        if (!isInModeChanging()) {
            CameraStatUtil.trackZoomAdjusted(CameraStat.ZOOM_MODE_SLIDER, false);
            requestZoomRatio(Float.valueOf(str2).floatValue(), 3);
        }
    }

    public boolean onTouch(ZoomRatioToggleView zoomRatioToggleView, MotionEvent motionEvent) {
        if (!this.mPassTouchFromZoomButtonToSlide) {
            return false;
        }
        switch (motionEvent.getAction()) {
            case 1:
            case 3:
                if (this.mHorizontalSlideView instanceof HorizontalSlideView) {
                    motionEvent.setLocation(this.mZoomSliderViewX + (motionEvent.getX() - this.mTouchDownX), motionEvent.getY());
                }
                this.mHorizontalSlideView.setEvent(motionEvent);
                this.mZoomSliderViewTouchListener.onTouch((View) null, motionEvent);
                this.mPassTouchFromZoomButtonToSlide = false;
                this.mTouchDownX = -1.0f;
                return true;
            case 2:
                if (this.mTouchDownX == -1.0f) {
                    this.mTouchDownX = motionEvent.getX();
                    motionEvent.setAction(0);
                    if (this.mHorizontalSlideView instanceof HorizontalSlideView) {
                        motionEvent.setLocation(this.mZoomSliderViewX, motionEvent.getY());
                    }
                    this.mHorizontalSlideView.setEvent(motionEvent);
                    return true;
                }
                if (this.mHorizontalSlideView instanceof HorizontalSlideView) {
                    motionEvent.setLocation(this.mZoomSliderViewX + (motionEvent.getX() - this.mTouchDownX), motionEvent.getY());
                }
                this.mHorizontalSlideView.setEvent(motionEvent);
                this.mZoomSliderViewTouchListener.onTouch((View) null, motionEvent);
                return true;
            default:
                return true;
        }
    }

    public void provideAnimateElement(int i, List<Completable> list, int i2) {
        boolean z;
        int i3 = this.mCurrentMode;
        super.provideAnimateElement(i, list, i2);
        int i4 = getResources().getDisplayMetrics().widthPixels;
        int i5 = this.mZoomSliderLayoutHeight;
        ViewGroup.LayoutParams layoutParams = this.mHorizontalSlideLayout.getLayoutParams();
        if (!HybridZoomingSystem.IS_4_OR_MORE_SAT || this.mCurrentMode == 165) {
            float f = (float) i4;
            layoutParams.height = ((int) (((f / 0.75f) - f) / 2.0f)) + getResources().getDimensionPixelSize(R.dimen.square_mode_bottom_cover_extra_margin);
        } else {
            float f2 = (float) i4;
            layoutParams.height = (((int) (((f2 / 0.75f) - f2) / 2.0f)) * 2) / 3;
        }
        this.mZoomSliderLayoutHeight = layoutParams.height;
        this.mHorizontalSlideLayout.setLayoutParams(layoutParams);
        adjustViewBackground(this.mHorizontalSlideLayout, this.mCurrentMode);
        if (DataRepository.dataItemFeature().hH()) {
            boolean isMacroModeEnabled = CameraSettings.isMacroModeEnabled(i);
            if (isMacroModeEnabled != this.mIsStandaloneMacroMode) {
                this.mIsStandaloneMacroMode = isMacroModeEnabled;
            }
        }
        this.mIsRecordingOrPausing = false;
        initiateZoomRatio();
        ZoomRatioToggleView.ViewSpec viewSpecForCapturingMode = getViewSpecForCapturingMode(this.mCurrentMode);
        if (viewSpecForCapturingMode.visibility == 1) {
            onBackEvent(4);
            if (HybridZoomingSystem.getZoomingSourceIdentity() != 0) {
                Log.d(TAG, "provideAnimateElement(): getZoomingSourceIdentity: " + r3);
                z = true;
            } else {
                z = false;
            }
            Log.d(TAG, "provideAnimateElement(): initialized zoom ratio: " + this.mZoomRatio);
            this.mZoomRatioToggleView.onDatasetChange(this.mCurrentMode, this.mIsStandaloneMacroMode);
            this.mZoomRatioToggleView.setRotation((float) this.mDegree);
            this.mZoomRatioToggleView.setSuppressed(viewSpecForCapturingMode.suppress);
            this.mZoomRatioToggleView.setImmersive(z || viewSpecForCapturingMode.immersive);
            this.mZoomRatioToggleView.setZoomRatio(this.mZoomRatio, -1);
            this.mZoomRatioToggleView.setUseSliderAllowed(viewSpecForCapturingMode.useSlider);
            this.mIsUseSlider = viewSpecForCapturingMode.useSlider;
            this.mZoomRatioToggleView.startInactiveTimer();
        }
        if (this.mCurrentState != viewSpecForCapturingMode.visibility || i5 != this.mZoomSliderLayoutHeight) {
            this.mCurrentState = viewSpecForCapturingMode.visibility;
            int i6 = this.mCurrentState;
            if (i6 != -1) {
                if (i6 == 1) {
                    SlideInOnSubscribe.directSetResult(this.mDualParentLayout, 80);
                    SlideOutOnSubscribe.directSetResult(this.mHorizontalSlideLayout, 80);
                    ViewCompat.setTranslationY(this.mZoomRatioToggleView, (float) this.mZoomSliderLayoutHeight);
                    if (list == null || (i == 165 && i3 != 167)) {
                        AlphaInOnSubscribe.directSetResult(this.mZoomRatioToggleView);
                    } else if (i3 == 167) {
                        list.add(Completable.create(new AlphaInOnSubscribe(this.mZoomRatioToggleView).setStartDelayTime(150)));
                    } else {
                        list.add(Completable.create(new AlphaInOnSubscribe(this.mZoomRatioToggleView)));
                    }
                }
            } else if (this.mHorizontalSlideLayout.getVisibility() == 0) {
                if (this.mSlidingAdapter != null) {
                    this.mSlidingAdapter.setEnable(false);
                }
                if (list == null) {
                    this.mHorizontalSlideLayout.setVisibility(8);
                } else {
                    list.add(Completable.create(new SlideOutOnSubscribe(this.mDualParentLayout, 80)));
                }
            } else if (list == null || i3 == 165) {
                AlphaOutOnSubscribe.directSetResult(this.mZoomRatioToggleView);
            } else {
                list.add(Completable.create(new AlphaOutOnSubscribe(this.mZoomRatioToggleView)));
            }
        }
    }

    public void provideRotateItem(List<View> list, int i) {
        super.provideRotateItem(list, i);
        if (this.mZoomRatioToggleView.getVisibility() == 0) {
            list.add(this.mZoomRatioToggleView);
        }
    }

    /* access modifiers changed from: protected */
    public void register(ModeProtocol.ModeCoordinator modeCoordinator) {
        super.register(modeCoordinator);
        modeCoordinator.attachProtocol(182, this);
        registerBackStack(modeCoordinator, this);
        if (b.isSupportedOpticalZoom() || HybridZoomingSystem.IS_3_OR_MORE_SAT) {
            modeCoordinator.attachProtocol(184, this);
        }
    }

    public void setClickEnable(boolean z) {
        super.setClickEnable(z);
        if (this.mZoomRatioToggleView != null) {
            this.mZoomRatioToggleView.setEnabled(z);
        }
    }

    public void setImmersiveModeEnabled(boolean z) {
        this.mZoomRatioToggleView.setImmersive(z);
    }

    public void setRecordingOrPausing(boolean z) {
        this.mIsRecordingOrPausing = z;
    }

    public void setSnapNumValue(int i) {
        this.mZoomRatioToggleView.setCaptureCount(i);
    }

    public void setSnapNumVisible(boolean z, boolean z2) {
        if (this.mHorizontalSlideLayout != null) {
            if (z) {
                if (z2) {
                    if (this.mHorizontalSlideLayout.getVisibility() == 0) {
                        hideSlideView();
                    }
                    this.mZoomInAnimator.start();
                } else {
                    ViewCompat.setRotation(this.mZoomRatioToggleView, (float) this.mDegree);
                    this.mZoomRatioToggleView.setVisibility(0);
                }
                this.mZoomRatioToggleView.setImmersive(true);
                return;
            }
            updateZoomRatio(-1);
            this.mZoomRatioToggleView.setImmersive(false);
            if (z2) {
                this.mZoomOutAnimator.start();
            }
        }
    }

    public void showZoomButton() {
        if (this.mCurrentState != 1 && !this.mIsRecordingOrPausing) {
            this.mCurrentState = 1;
            updateZoomRatio(-1);
            ViewCompat.setRotation(this.mZoomRatioToggleView, (float) this.mDegree);
            Completable.create(new AlphaInOnSubscribe(this.mZoomRatioToggleView)).subscribe();
        }
    }

    public boolean toShowSlideView() {
        boolean showSlideView = showSlideView();
        CameraStatUtil.trackShowZoomBarByScroll(showSlideView);
        updateZoomSlider(true);
        sendHideMessage();
        return showSlideView;
    }

    /* access modifiers changed from: protected */
    public void unRegister(ModeProtocol.ModeCoordinator modeCoordinator) {
        super.unRegister(modeCoordinator);
        this.mHandler.removeCallbacksAndMessages((Object) null);
        modeCoordinator.detachProtocol(182, this);
        unRegisterBackStack(modeCoordinator, this);
        if (b.isSupportedOpticalZoom() || HybridZoomingSystem.IS_3_OR_MORE_SAT) {
            modeCoordinator.detachProtocol(184, this);
        }
    }

    public boolean updateSlideAndZoomRatio(int i) {
        boolean showSlideView = ((i == 2 || i == 1) && HybridZoomingSystem.IS_4_SAT) ? showSlideView() : false;
        updateZoomRatio(i);
        if (isSlideVisible()) {
            sendHideMessage();
        }
        return showSlideView;
    }

    public void updateZoomRatio(int i) {
        if (CameraSettings.isZoomByCameraSwitchingSupported()) {
            String cameraLensType = CameraSettings.getCameraLensType(this.mCurrentMode);
            if (ComponentManuallyDualLens.LENS_ULTRA.equals(cameraLensType)) {
                this.mZoomRatio = 0.6f;
            } else if (ComponentManuallyDualLens.LENS_WIDE.equals(cameraLensType)) {
                this.mZoomRatio = 1.0f;
            } else if (ComponentManuallyDualLens.LENS_TELE.equals(cameraLensType)) {
                this.mZoomRatio = 2.0f;
            } else {
                throw new IllegalStateException("updateZoomRatio(): Unknown camera lens type: " + cameraLensType);
            }
        } else {
            this.mZoomRatio = CameraSettings.readZoom();
        }
        if (!this.mZoomRatioToggleAnimator.isRunning() || HybridZoomingSystem.isOpticalZoomRatio(this.mZoomRatio)) {
            this.mZoomRatioToggleView.setZoomRatio(this.mZoomRatio, i);
            if (i != 3) {
                updateZoomSlider(true);
            }
        }
    }

    public int visibleHeight() {
        if (this.mCurrentState == -1) {
            return 0;
        }
        return isSlideVisible() ? this.mZoomSliderLayoutHeight + this.mZoomRatioToggleView.getHeight() : this.mZoomSwitchLayoutHeight;
    }
}
