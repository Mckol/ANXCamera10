package com.android.camera.fragment.dual;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.LinearInterpolator;
import android.view.animation.ScaleAnimation;
import android.widget.ImageView;
import androidx.core.view.ViewCompat;
import b.c.a.c;
import com.android.camera.ActivityBase;
import com.android.camera.CameraSettings;
import com.android.camera.HybridZoomingSystem;
import com.android.camera.R;
import com.android.camera.Util;
import com.android.camera.animation.FolmeUtils;
import com.android.camera.animation.type.AlphaInOnSubscribe;
import com.android.camera.data.DataRepository;
import com.android.camera.data.data.config.ComponentConfigVideoQuality;
import com.android.camera.data.data.config.ComponentManuallyDualLens;
import com.android.camera.data.data.config.SupportedConfigFactory;
import com.android.camera.fragment.BaseFragment;
import com.android.camera.fragment.manually.ZoomValueListener;
import com.android.camera.fragment.manually.adapter.AbstractZoomSliderAdapter;
import com.android.camera.fragment.manually.adapter.ExtraSlideFNumberAdapter;
import com.android.camera.fragment.manually.adapter.sat.StopsZoomSliderAdapter;
import com.android.camera.lib.compatibility.related.vibrator.ViberatorContext;
import com.android.camera.log.Log;
import com.android.camera.module.BaseModule;
import com.android.camera.module.ModuleManager;
import com.android.camera.module.loader.camera2.Camera2DataContainer;
import com.android.camera.protocol.ModeCoordinatorImpl;
import com.android.camera.protocol.ModeProtocol;
import com.android.camera.statistic.CameraStatUtils;
import com.android.camera.statistic.MistatsConstants;
import com.android.camera.statistic.MistatsWrapper;
import com.android.camera.ui.BaseHorizontalZoomView;
import com.android.camera.ui.HorizontalZoomView;
import com.android.camera.ui.zoom.ZoomIndexButtonsLayout;
import com.android.camera.ui.zoom.ZoomRatioToggleView;
import com.android.camera.ui.zoom.ZoomRatioView;
import com.android.camera2.CameraCapabilities;
import io.reactivex.Completable;
import java.util.ArrayList;
import java.util.List;
import miui.view.animation.CubicEaseOutInterpolator;

public class FragmentDualCameraAdjust extends BaseFragment implements ZoomRatioToggleView.ToggleStateListener, ZoomValueListener, ModeProtocol.HandleBackTrace, ModeProtocol.DualController, ModeProtocol.SnapShotIndicator, View.OnClickListener, ZoomIndexButtonsLayout.OnIndexButtonClickListener, BaseHorizontalZoomView.onTouchUpStateListener {
    public static final int FRAGMENT_INFO = 4084;
    private static final int HIDE_POPUP = 1;
    private static final int STATE_BOKEH_SHOW = -100;
    private static final int STATE_ZOOM_SHOW = -200;
    private static final String TAG = "FragmentDualCameraAdjust";
    private int mButtonLayoutHeight;
    private int mCurrentState = -1;
    private ViewGroup mDualParentLayout;
    private Handler mHandler = new Handler() {
        /* class com.android.camera.fragment.dual.FragmentDualCameraAdjust.AnonymousClass1 */

        public void handleMessage(Message message) {
            if (message.what == 1) {
                FragmentDualCameraAdjust.this.onBackEvent(5);
            }
        }
    };
    private ViewGroup mHorizontalSlideLayout;
    private HorizontalZoomView mHorizontalSlideView;
    private ImageView mImageBokehIndicator;
    private boolean mIsHiding;
    private boolean mIsRecordingOrPausing = false;
    private boolean mIsUseSlider = false;
    private boolean mIsZoomTo2X;
    private AbstractZoomSliderAdapter mSlidingAdapter;
    private float mTargetZoomRatio;
    private ZoomIndexButtonsLayout mZoomIndexButtons;
    private float mZoomRatio;
    private ValueAnimator mZoomRatioToggleAnimator;
    private ZoomRatioToggleView mZoomRatioToggleView;
    private int mZoomSliderLayoutHeight;

    private void adjustViewBackground(View view, int i) {
        ZoomRatioToggleView zoomRatioToggleView = this.mZoomRatioToggleView;
        if (zoomRatioToggleView != null) {
            zoomRatioToggleView.setBackgroundColor(getResources().getColor(getZoomBackgroundColor(i)));
        }
        ZoomIndexButtonsLayout zoomIndexButtonsLayout = this.mZoomIndexButtons;
        if (zoomIndexButtonsLayout != null) {
            zoomIndexButtonsLayout.setBackgroundColor(getResources().getColor(getZoomBackgroundColor(i)));
        }
        view.setBackgroundDrawable(null);
    }

    /* JADX WARNING: Code restructure failed: missing block: B:103:0x0143, code lost:
        if (com.android.camera.data.DataRepository.dataItemConfig().getComponentConfigVideoQuality().supportVideoSATForVideoQuality(r12) != false) goto L_0x0251;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:118:0x0173, code lost:
        if (r3 == -1) goto L_0x0085;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:149:0x01bf, code lost:
        if (r1.isSupportLightTripartite() != false) goto L_0x024f;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:151:0x01c5, code lost:
        if (com.android.camera.CameraSettings.isMacroModeEnabled(r12) != false) goto L_0x024f;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:193:0x0246, code lost:
        if (r1.isSupportLightTripartite() != false) goto L_0x024f;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:195:0x024c, code lost:
        if (com.android.camera.CameraSettings.isMacroModeEnabled(r12) != false) goto L_0x024f;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:35:0x0081, code lost:
        if (r3 == -1) goto L_0x0085;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:48:0x009d, code lost:
        if (r3 == -1) goto L_0x0085;
     */
    private static ZoomRatioToggleView.ViewSpec getViewSpecForCapturingMode(int i) {
        boolean z;
        boolean z2;
        boolean z3;
        int i2;
        boolean z4;
        boolean z5;
        boolean z6;
        boolean z7;
        boolean z8;
        ModeProtocol.MiBeautyProtocol miBeautyProtocol;
        ModeProtocol.MiBeautyProtocol miBeautyProtocol2;
        ModeProtocol.MiBeautyProtocol miBeautyProtocol3;
        boolean isNormalIntent = DataRepository.dataItemGlobal().isNormalIntent();
        CameraCapabilities currentCameraCapabilities = Camera2DataContainer.getInstance().getCurrentCameraCapabilities();
        int i3 = -200;
        boolean z9 = true;
        if (DataRepository.dataItemGlobal().getCurrentCameraId() != 1 && !CameraSettings.isMacroModeEnabled(i) && !CameraSettings.isAutoZoomEnabled(i) && !CameraSettings.isSuperEISEnabled(i) && HybridZoomingSystem.IS_2_OR_MORE_SAT) {
            if (i == 188) {
                i2 = -200;
                z4 = true;
                z3 = false;
            } else {
                z3 = true;
                z4 = false;
                i2 = -1;
            }
            if (i != 175 || ((!DataRepository.dataItemFeature().bk() && !DataRepository.dataItemFeature().ak()) || CameraSettings.isSRTo108mModeOn())) {
                if (i == 161) {
                    if (HybridZoomingSystem.IS_2_SAT || CameraSettings.isUltraWideConfigOpen(i)) {
                        i3 = -1;
                    }
                    z8 = i3 == -1;
                } else if (i == 174) {
                    if (HybridZoomingSystem.IS_2_SAT || CameraSettings.isUltraWideConfigOpen(i)) {
                        i3 = -1;
                    }
                    z8 = i3 == -1;
                } else {
                    if (i == 183) {
                        int i4 = (!HybridZoomingSystem.IS_2_SAT && ((miBeautyProtocol3 = (ModeProtocol.MiBeautyProtocol) ModeCoordinatorImpl.getInstance().getAttachProtocol(194)) == null || !miBeautyProtocol3.isBeautyPanelShow()) && !CameraSettings.isUltraWideConfigOpen(i)) ? -200 : -1;
                        boolean z10 = i4 == -1;
                        z5 = i4 == -1;
                        z6 = z10;
                        i2 = i4;
                        z4 = false;
                    } else {
                        z5 = z3;
                        z6 = true;
                    }
                    if (i == 162) {
                        if (HybridZoomingSystem.IS_2_SAT || (((miBeautyProtocol2 = (ModeProtocol.MiBeautyProtocol) ModeCoordinatorImpl.getInstance().getAttachProtocol(194)) != null && miBeautyProtocol2.isBeautyPanelShow()) || ((CameraSettings.isMacroModeEnabled(i) || CameraSettings.isUltraWideConfigOpen(i)) && !DataRepository.dataItemFeature().fm()))) {
                            i3 = -1;
                        }
                        z = i3 == -1 || (!isNormalIntent && currentCameraCapabilities != null && currentCameraCapabilities.isSupportLightTripartite()) || CameraSettings.isVideoQuality8KOpen(i);
                        z2 = i3 == -1 || (!isNormalIntent && currentCameraCapabilities != null && currentCameraCapabilities.isSupportLightTripartite()) || CameraSettings.isVideoQuality8KOpen(i);
                    } else if (i == 169) {
                        if (HybridZoomingSystem.IS_2_SAT || (DataRepository.dataItemFeature().uj() && (miBeautyProtocol = (ModeProtocol.MiBeautyProtocol) ModeCoordinatorImpl.getInstance().getAttachProtocol(194)) != null && miBeautyProtocol.isBeautyPanelShow())) {
                            i3 = -1;
                        }
                        z8 = i3 == -1;
                    } else {
                        if (i == 163 || i == 186) {
                            if ((CameraSettings.isMacroModeEnabled(i) || CameraSettings.isUltraWideConfigOpen(i) || CameraSettings.isUltraPixelRearOn()) && !DataRepository.dataItemFeature().fm()) {
                                i3 = -1;
                            }
                            z = HybridZoomingSystem.IS_2_SAT || (!isNormalIntent && currentCameraCapabilities != null && currentCameraCapabilities.isSupportLightTripartite());
                            z7 = HybridZoomingSystem.IS_2_SAT || (!isNormalIntent && currentCameraCapabilities != null && currentCameraCapabilities.isSupportLightTripartite());
                            if (!isNormalIntent) {
                                if (currentCameraCapabilities != null) {
                                }
                            }
                        } else if (i == 165) {
                            if (CameraSettings.isMacroModeEnabled(i) || CameraSettings.isUltraWideConfigOpen(i)) {
                                i3 = -1;
                            }
                            z = HybridZoomingSystem.IS_2_SAT || (!isNormalIntent && currentCameraCapabilities != null && currentCameraCapabilities.isSupportLightTripartite());
                            z7 = HybridZoomingSystem.IS_2_SAT || (!isNormalIntent && currentCameraCapabilities != null && currentCameraCapabilities.isSupportLightTripartite());
                            if (!isNormalIntent) {
                                if (currentCameraCapabilities != null) {
                                }
                            }
                        } else if (i == 173) {
                            if (DataRepository.dataItemFeature().Fk()) {
                                z2 = false;
                                z = false;
                                z9 = false;
                            }
                        } else if (i == 166) {
                            if (!DataRepository.dataItemFeature().Zj()) {
                                i3 = -1;
                            }
                        } else if (i != 205) {
                            z9 = z4;
                            i3 = i2;
                            z = z6;
                            z2 = z5;
                        }
                        z9 = false;
                        z2 = z7;
                    }
                    if (CameraSettings.isFakePartSAT() || !CameraSettings.isSupportedOpticalZoom()) {
                        z9 = false;
                    }
                    return new ZoomRatioToggleView.ViewSpec(i3, z, z2, z9);
                }
                z9 = false;
                z = z8;
                z2 = z9;
                z9 = false;
                z9 = false;
                return new ZoomRatioToggleView.ViewSpec(i3, z, z2, z9);
            }
            z2 = true;
            z = true;
            z9 = false;
            z9 = false;
            return new ZoomRatioToggleView.ViewSpec(i3, z, z2, z9);
        }
        z2 = true;
        z = true;
        z9 = false;
        i3 = -1;
        z9 = false;
        return new ZoomRatioToggleView.ViewSpec(i3, z, z2, z9);
    }

    private int getZoomBackgroundColor(int i) {
        return i != 165 ? i != 188 ? R.color.zoom_button_background_color : R.color.zoom_button_background_super_moon_color : R.color.zoom_button_background_1_1_color;
    }

    private void initSlideFNumberView() {
        String readFNumber = CameraSettings.readFNumber();
        this.mSlidingAdapter = new ExtraSlideFNumberAdapter(getContext(), readFNumber, this);
        this.mHorizontalSlideView.setListener(this.mSlidingAdapter, this);
        this.mHorizontalSlideView.setDrawAdapter(this.mSlidingAdapter, this.mDegree, false);
        this.mHorizontalSlideView.setSelection((int) this.mSlidingAdapter.mapValueToPosition(readFNumber), true);
    }

    private void initSlideZoomView() {
        this.mSlidingAdapter = new StopsZoomSliderAdapter(getContext(), this.mCurrentMode, this);
        this.mHorizontalSlideView.setListener(this.mSlidingAdapter, this);
        this.mHorizontalSlideView.setDrawAdapter(this.mSlidingAdapter, this.mDegree, c.dh);
        this.mZoomRatioToggleView.setVisibility(8);
        boolean z = this.mCurrentMode == 188;
        boolean isVideoCategory = ModuleManager.isVideoCategory(this.mCurrentMode);
        BaseModule baseModule = (BaseModule) ((ActivityBase) getActivity()).getCurrentModule();
        List<Float> a2 = DataRepository.dataItemFeature().a(z, isVideoCategory, HybridZoomingSystem.ZOOM_INDEXS_DEFAULT);
        ArrayList arrayList = new ArrayList();
        for (int i = 0; i < a2.size(); i++) {
            float floatValue = a2.get(i).floatValue();
            if (floatValue >= baseModule.getMinZoomRatio() && floatValue <= baseModule.getMaxZoomRatio()) {
                arrayList.add(Float.valueOf(floatValue));
            }
        }
        if (!arrayList.contains(Float.valueOf(baseModule.getMaxZoomRatio()))) {
            arrayList.add(Float.valueOf(baseModule.getMaxZoomRatio()));
        }
        this.mZoomIndexButtons.setZoomIndexButtons(arrayList, this);
        this.mZoomIndexButtons.setSelect(this.mZoomRatio, false);
    }

    private void initiateZoomRatio() {
        if (CameraSettings.isZoomByCameraSwitchingSupported()) {
            String cameraLensType = CameraSettings.getCameraLensType(this.mCurrentMode);
            if (ComponentManuallyDualLens.LENS_ULTRA.equals(cameraLensType)) {
                this.mZoomRatio = HybridZoomingSystem.FLOAT_ZOOM_RATIO_ULTR;
            } else if (ComponentManuallyDualLens.LENS_WIDE.equals(cameraLensType)) {
                this.mZoomRatio = 1.0f;
            } else if (ComponentManuallyDualLens.LENS_TELE.equals(cameraLensType)) {
                this.mZoomRatio = HybridZoomingSystem.getTeleMinZoomRatio();
            } else if ("macro".equals(cameraLensType)) {
                this.mZoomRatio = HybridZoomingSystem.sDefaultMacroOpticalZoomRatio;
            } else if (ComponentManuallyDualLens.LENS_ULTRA_TELE.equals(cameraLensType)) {
                this.mZoomRatio = HybridZoomingSystem.getUltraTeleMinZoomRatio();
            } else {
                throw new IllegalStateException("initiateZoomRatio(): Unknown camera lens type: " + cameraLensType);
            }
            Log.d(TAG, "initiateZoomRatio(): lens-switch-zoom: " + this.mZoomRatio);
            return;
        }
        this.mZoomRatio = Float.parseFloat(HybridZoomingSystem.getZoomRatioHistory(this.mCurrentMode, "1.0"));
        Log.d(TAG, "initiateZoomRatio(): zoom: " + this.mZoomRatio);
    }

    private boolean isVisible(View view) {
        return view.getVisibility() == 0 && view.getAlpha() != 0.0f;
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
    /* access modifiers changed from: public */
    private void notifyZoom2X(boolean z) {
        ModeProtocol.ManuallyValueChanged manuallyValueChanged = (ModeProtocol.ManuallyValueChanged) ModeCoordinatorImpl.getInstance().getAttachProtocol(174);
        if (manuallyValueChanged == null) {
            return;
        }
        if (Util.isZoomAnimationEnabled() || z) {
            manuallyValueChanged.onDualZoomHappened(z);
        }
    }

    /* access modifiers changed from: private */
    /* access modifiers changed from: public */
    private void notifyZooming(boolean z) {
        ModeProtocol.ManuallyValueChanged manuallyValueChanged = (ModeProtocol.ManuallyValueChanged) ModeCoordinatorImpl.getInstance().getAttachProtocol(174);
        if (manuallyValueChanged != null) {
            manuallyValueChanged.onDualLensZooming(z);
        }
    }

    /* access modifiers changed from: private */
    /* access modifiers changed from: public */
    private void requestZoomRatio(float f, int i) {
        ModeProtocol.ManuallyValueChanged manuallyValueChanged = (ModeProtocol.ManuallyValueChanged) ModeCoordinatorImpl.getInstance().getAttachProtocol(174);
        if (manuallyValueChanged != null) {
            manuallyValueChanged.onDualZoomValueChanged(f, i);
        }
    }

    private void sendHideMessage() {
        if (this.mCurrentMode != 188) {
            this.mHandler.removeMessages(1);
            this.mHandler.sendEmptyMessageDelayed(1, 3000);
        }
    }

    private void showBokehPanel() {
        ModeProtocol.MiBeautyProtocol miBeautyProtocol = (ModeProtocol.MiBeautyProtocol) ModeCoordinatorImpl.getInstance().getAttachProtocol(194);
        if (miBeautyProtocol == null || !miBeautyProtocol.isBeautyPanelShow()) {
            initSlideFNumberView();
            this.mSlidingAdapter.setEnable(true);
            AlphaInOnSubscribe.directSetResult(this.mDualParentLayout);
            this.mHorizontalSlideLayout.setVisibility(0);
            ((ViewGroup.MarginLayoutParams) this.mDualParentLayout.getLayoutParams()).bottomMargin = Util.getBottomHeight() - this.mZoomSliderLayoutHeight;
            ScaleAnimation scaleAnimation = new ScaleAnimation(0.9f, 1.0f, 1.0f, 1.0f, 1, 0.5f, 1, 0.5f);
            scaleAnimation.setDuration(300);
            scaleAnimation.setInterpolator(new CubicEaseOutInterpolator());
            this.mHorizontalSlideView.startAnimation(scaleAnimation);
            showOrHideBottomMenu(false, true);
            ModeProtocol.BottomPopupTips bottomPopupTips = (ModeProtocol.BottomPopupTips) ModeCoordinatorImpl.getInstance().getAttachProtocol(175);
            if (bottomPopupTips != null) {
                bottomPopupTips.directHideTipImage();
                bottomPopupTips.directShowOrHideLeftTipImage(false);
            }
            this.mImageBokehIndicator.setVisibility(8);
            return;
        }
        Log.v(TAG, "beauty panel shown. do not show the slide view.");
    }

    private void showOrHideBottomMenu(boolean z, boolean z2) {
        ModeProtocol.BottomMenuProtocol bottomMenuProtocol = (ModeProtocol.BottomMenuProtocol) ModeCoordinatorImpl.getInstance().getAttachProtocol(197);
        if (z) {
            bottomMenuProtocol.setModeLayoutVisibility(0, z2);
        } else {
            bottomMenuProtocol.setModeLayoutVisibility(8, false);
        }
    }

    private boolean showZoomPanel() {
        ModeProtocol.CameraModuleSpecial cameraModuleSpecial;
        if (isZoomPanelVisible() || !this.mIsUseSlider) {
            return false;
        }
        initSlideZoomView();
        this.mIsHiding = false;
        this.mSlidingAdapter.setEnable(true);
        ScaleAnimation scaleAnimation = new ScaleAnimation(0.9f, 1.0f, 1.0f, 1.0f, 1, 0.5f, 1, 0.5f);
        scaleAnimation.setDuration(300);
        scaleAnimation.setInterpolator(new CubicEaseOutInterpolator());
        this.mZoomIndexButtons.startAnimation(scaleAnimation);
        this.mHorizontalSlideView.startAnimation(scaleAnimation);
        this.mZoomIndexButtons.setVisibility(0);
        this.mZoomIndexButtons.setDegree(this.mDegree);
        this.mHorizontalSlideLayout.setVisibility(0);
        ((ViewGroup.MarginLayoutParams) this.mDualParentLayout.getLayoutParams()).bottomMargin = Util.getBottomHeight() - this.mZoomSliderLayoutHeight;
        showOrHideBottomMenu(false, true);
        notifyTipsMargin();
        if (this.mCurrentMode == 163 && (cameraModuleSpecial = (ModeProtocol.CameraModuleSpecial) ModeCoordinatorImpl.getInstance().getAttachProtocol(195)) != null) {
            cameraModuleSpecial.showOrHideChip(false);
        }
        ModeProtocol.BottomPopupTips bottomPopupTips = (ModeProtocol.BottomPopupTips) ModeCoordinatorImpl.getInstance().getAttachProtocol(175);
        if (bottomPopupTips != null) {
            bottomPopupTips.directHideLyingDirectHint();
        }
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
            str = HybridZoomingSystem.toString(HybridZoomingSystem.FLOAT_ZOOM_RATIO_ULTR);
        } else if (ComponentManuallyDualLens.LENS_WIDE.equals(componentValue)) {
            str = HybridZoomingSystem.toString(1.0f);
        } else if (ComponentManuallyDualLens.LENS_TELE.equals(componentValue)) {
            str = HybridZoomingSystem.toString(HybridZoomingSystem.getTeleMinZoomRatio());
        } else if (ComponentManuallyDualLens.LENS_ULTRA_TELE.equals(componentValue)) {
            str = HybridZoomingSystem.toString(HybridZoomingSystem.getUltraTeleMinZoomRatio());
        } else {
            throw new IllegalStateException("switchCameraLens(): Unknown camera lens type: " + componentValue);
        }
        CameraStatUtils.trackDualZoomChanged(this.mCurrentMode, str);
    }

    private void toHideBokehPanel(boolean z) {
        AbstractZoomSliderAdapter abstractZoomSliderAdapter = this.mSlidingAdapter;
        if (abstractZoomSliderAdapter != null) {
            abstractZoomSliderAdapter.setEnable(false);
        }
        this.mHorizontalSlideLayout.setVisibility(8);
        showOrHideBottomMenu(true, z);
        if (DataRepository.dataItemGlobal().isNormalIntent()) {
            this.mImageBokehIndicator.setVisibility(0);
        }
        ((ViewGroup.MarginLayoutParams) this.mDualParentLayout.getLayoutParams()).bottomMargin = Util.getBottomHeight();
    }

    private void toHideZoomPanel(boolean z) {
        toHideZoomPanel(z, true);
    }

    private void toHideZoomPanel(boolean z, boolean z2) {
        ModeProtocol.CameraModuleSpecial cameraModuleSpecial;
        this.mHandler.removeMessages(1);
        if (this.mHorizontalSlideLayout.getVisibility() == 0) {
            ((ViewGroup.MarginLayoutParams) this.mDualParentLayout.getLayoutParams()).bottomMargin = Util.getBottomHeight();
            this.mIsHiding = false;
            this.mHorizontalSlideLayout.setVisibility(8);
            this.mZoomIndexButtons.setVisibility(8);
            if (z2) {
                this.mZoomRatioToggleView.setVisibility(0);
            }
            this.mSlidingAdapter.setEnable(false);
            showOrHideBottomMenu(true, z);
            if (z) {
                this.mZoomRatioToggleView.startTranslationAnimationShow();
                ModeProtocol.ManuallyValueChanged manuallyValueChanged = (ModeProtocol.ManuallyValueChanged) ModeCoordinatorImpl.getInstance().getAttachProtocol(174);
                if (manuallyValueChanged != null) {
                    manuallyValueChanged.updateSATIsZooming(false);
                }
                ModeProtocol.BottomPopupTips bottomPopupTips = (ModeProtocol.BottomPopupTips) ModeCoordinatorImpl.getInstance().getAttachProtocol(175);
                if (bottomPopupTips != null) {
                    bottomPopupTips.reInitTipImage();
                }
                if (bottomPopupTips != null) {
                    bottomPopupTips.updateLyingDirectHint(false, true);
                }
                if (this.mCurrentMode == 163 && (cameraModuleSpecial = (ModeProtocol.CameraModuleSpecial) ModeCoordinatorImpl.getInstance().getAttachProtocol(195)) != null) {
                    cameraModuleSpecial.showOrHideChip(true);
                }
            }
        }
    }

    private void updateZoomSlider() {
        AbstractZoomSliderAdapter abstractZoomSliderAdapter = this.mSlidingAdapter;
        if (abstractZoomSliderAdapter != null && this.mHorizontalSlideView != null && abstractZoomSliderAdapter.isEnable()) {
            updateZoomSliderPosition();
        }
    }

    private void updateZoomSliderPosition() {
        this.mHorizontalSlideView.setSelection(this.mSlidingAdapter.mapValueToPosition(Float.valueOf(CameraSettings.readZoom())));
    }

    @Override // com.android.camera.fragment.BaseFragment
    public int getFragmentInto() {
        return 4084;
    }

    /* access modifiers changed from: protected */
    @Override // com.android.camera.fragment.BaseFragment
    public int getLayoutResourceId() {
        return R.layout.fragment_dual_camera_adjust;
    }

    @Override // com.android.camera.protocol.ModeProtocol.DualController
    public void hideAllPanel() {
        int i = this.mCurrentState;
        if (i != -1) {
            if (i == -100) {
                toHideBokehPanel(true);
            } else if (i == -200) {
                toHideZoomPanel(true);
            }
        }
    }

    @Override // com.android.camera.protocol.ModeProtocol.DualController
    public void hideBokehButton(boolean z) {
        if (this.mCurrentState != -1) {
            this.mCurrentState = -1;
            toHideBokehPanel(z);
            this.mDualParentLayout.setVisibility(8);
        }
    }

    @Override // com.android.camera.protocol.ModeProtocol.DualController
    public void hideZoomButton() {
        if (this.mCurrentState == -200) {
            this.mCurrentState = -1;
            this.mZoomRatioToggleView.setVisibility(8);
            ViewGroup viewGroup = this.mHorizontalSlideLayout;
            if (viewGroup != null && viewGroup.getVisibility() == 0) {
                this.mHandler.removeMessages(1);
                this.mIsHiding = true;
                this.mSlidingAdapter.setEnable(false);
                this.mZoomIndexButtons.setVisibility(8);
                this.mHorizontalSlideLayout.setVisibility(8);
                showOrHideBottomMenu(true, true);
                ((ViewGroup.MarginLayoutParams) this.mDualParentLayout.getLayoutParams()).bottomMargin = Util.getBottomHeight();
            }
            this.mDualParentLayout.setVisibility(8);
        }
    }

    /* access modifiers changed from: protected */
    @Override // com.android.camera.fragment.BaseFragment
    public void initView(View view) {
        ((ViewGroup.MarginLayoutParams) view.getLayoutParams()).bottomMargin = Util.getBottomHeight();
        this.mDualParentLayout = (ViewGroup) view.findViewById(R.id.dual_layout_parent);
        this.mHorizontalSlideLayout = (ViewGroup) view.findViewById(R.id.dual_camera_zoom_slider_container);
        this.mZoomIndexButtons = (ZoomIndexButtonsLayout) view.findViewById(R.id.zoom_index_buttons_layout);
        this.mZoomRatioToggleView = (ZoomRatioToggleView) view.findViewById(R.id.zoom_ratio_toggle_button);
        this.mZoomRatioToggleView.setActionListener(this);
        this.mImageBokehIndicator = (ImageView) view.findViewById(R.id.dual_camera_bokeh_indicator);
        this.mImageBokehIndicator.setOnClickListener(this);
        if (Util.isAccessible()) {
            this.mImageBokehIndicator.setContentDescription(getString(R.string.accessibility_bokeh_adjust));
            this.mImageBokehIndicator.postDelayed(new Runnable() {
                /* class com.android.camera.fragment.dual.FragmentDualCameraAdjust.AnonymousClass2 */

                public void run() {
                    if (FragmentDualCameraAdjust.this.isAdded()) {
                        FragmentDualCameraAdjust.this.mImageBokehIndicator.sendAccessibilityEvent(4);
                    }
                }
            }, 3000);
        }
        View findViewById = view.findViewById(R.id.sat_optical_zoom_switch_simulator);
        findViewById.setOnClickListener(this.mZoomRatioToggleView);
        findViewById.setOnLongClickListener(this.mZoomRatioToggleView);
        this.mHorizontalSlideView = (HorizontalZoomView) view.findViewById(R.id.dual_camera_zoom);
        this.mButtonLayoutHeight = this.mZoomRatioToggleView.getLayoutParams().height;
        this.mZoomRatioToggleAnimator = new ValueAnimator();
        this.mZoomRatioToggleAnimator.setInterpolator(new LinearInterpolator());
        if (!Util.isZoomAnimationEnabled() || HybridZoomingSystem.IS_3_OR_MORE_SAT) {
            this.mZoomRatioToggleAnimator.setDuration(0L);
        } else {
            this.mZoomRatioToggleAnimator.setDuration(100L);
        }
        this.mZoomRatioToggleAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            /* class com.android.camera.fragment.dual.FragmentDualCameraAdjust.AnonymousClass3 */

            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                float floatValue = ((Float) valueAnimator.getAnimatedValue()).floatValue();
                Log.d(FragmentDualCameraAdjust.TAG, "onAnimationUpdate(): zoom ratio = " + floatValue);
                if (FragmentDualCameraAdjust.this.mTargetZoomRatio <= 0.0f || FragmentDualCameraAdjust.this.mZoomRatioToggleAnimator.getDuration() != 0) {
                    FragmentDualCameraAdjust.this.requestZoomRatio(floatValue, 0);
                    return;
                }
                FragmentDualCameraAdjust fragmentDualCameraAdjust = FragmentDualCameraAdjust.this;
                fragmentDualCameraAdjust.requestZoomRatio(fragmentDualCameraAdjust.mTargetZoomRatio, 0);
            }
        });
        this.mZoomRatioToggleAnimator.addListener(new Animator.AnimatorListener() {
            /* class com.android.camera.fragment.dual.FragmentDualCameraAdjust.AnonymousClass4 */

            public void onAnimationCancel(Animator animator) {
                FragmentDualCameraAdjust.this.notifyZooming(false);
                FragmentDualCameraAdjust.this.mIsZoomTo2X = false;
                FragmentDualCameraAdjust.this.notifyZoom2X(false);
            }

            public void onAnimationEnd(Animator animator) {
                FragmentDualCameraAdjust.this.notifyZooming(false);
                FragmentDualCameraAdjust.this.mIsZoomTo2X = false;
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
        FolmeUtils.touchScaleTint(this.mImageBokehIndicator);
        provideAnimateElement(this.mCurrentMode, null, 2);
    }

    @Override // com.android.camera.protocol.ModeProtocol.DualController
    public boolean isButtonVisible() {
        int i = this.mCurrentState;
        if (i == -1) {
            return false;
        }
        if (i != -200 && i == -100) {
            return this.mImageBokehIndicator.getVisibility() == 0;
        }
        return true;
    }

    @Override // com.android.camera.ui.zoom.ZoomRatioToggleView.ToggleStateListener
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

    @Override // com.android.camera.protocol.ModeProtocol.DualController
    public boolean isZoomPanelVisible() {
        AbstractZoomSliderAdapter abstractZoomSliderAdapter;
        if (this.mCurrentState == -200 && (abstractZoomSliderAdapter = this.mSlidingAdapter) != null) {
            return abstractZoomSliderAdapter.isEnable();
        }
        return false;
    }

    @Override // com.android.camera.protocol.ModeProtocol.DualController
    public boolean isZoomSliderViewIdle() {
        HorizontalZoomView horizontalZoomView = this.mHorizontalSlideView;
        return horizontalZoomView == null || horizontalZoomView.isIdle();
    }

    @Override // com.android.camera.protocol.ModeProtocol.DualController
    public boolean isZoomVisible() {
        int i = this.mCurrentState;
        return i != -1 && i == -200 && this.mZoomRatioToggleView.getVisibility() == 0;
    }

    @Override // com.android.camera.fragment.BaseFragment, com.android.camera.animation.AnimationDelegate.AnimationResource
    public void notifyAfterFrameAvailable(int i) {
        ModeProtocol.MiBeautyProtocol miBeautyProtocol;
        super.notifyAfterFrameAvailable(i);
        if (this.mCurrentState != -100 || (miBeautyProtocol = (ModeProtocol.MiBeautyProtocol) ModeCoordinatorImpl.getInstance().getAttachProtocol(194)) == null || !miBeautyProtocol.isBeautyPanelShow()) {
            provideAnimateElement(this.mCurrentMode, null, 2);
        } else {
            this.mImageBokehIndicator.setVisibility(8);
        }
    }

    @Override // com.android.camera.fragment.BaseFragment, com.android.camera.animation.AnimationDelegate.AnimationResource
    public void notifyDataChanged(int i, int i2) {
        super.notifyDataChanged(i, i2);
        if (i == 3) {
            adjustViewBackground(this.mHorizontalSlideLayout, this.mCurrentMode);
        }
    }

    @Override // com.android.camera.protocol.ModeProtocol.HandleBackTrace
    public boolean onBackEvent(int i) {
        ModeProtocol.BottomPopupTips bottomPopupTips;
        if (!isVisible(this.mDualParentLayout)) {
            return false;
        }
        boolean z = i == 4;
        int i2 = this.mCurrentState;
        if (i2 == -1) {
            return false;
        }
        if (i2 == -200) {
            if (this.mIsHiding) {
                return false;
            }
            boolean z2 = i == 3 && this.mCurrentMode == 173 && !DataRepository.dataItemGlobal().isOnSuperNightAlgoUpAndQuickShot();
            if (!z2 && this.mHorizontalSlideLayout.getVisibility() != 0) {
                return false;
            }
            if (z2) {
                hideZoomButton();
            } else if (i == 3) {
                return false;
            } else {
                if (i == 8) {
                    toHideZoomPanel(true, false);
                    return false;
                } else if (i == 9) {
                    hideZoomButton();
                    return false;
                } else if (i == 2) {
                    toHideZoomPanel(!z);
                    return false;
                } else {
                    toHideZoomPanel(!z);
                }
            }
        } else if (i2 == -100) {
            if (this.mHorizontalSlideLayout.getVisibility() != 0 || i == 3) {
                return false;
            }
            toHideBokehPanel(!z);
            if (!z && (bottomPopupTips = (ModeProtocol.BottomPopupTips) ModeCoordinatorImpl.getInstance().getAttachProtocol(175)) != null) {
                bottomPopupTips.reInitTipImage();
            }
        }
        return true;
    }

    public void onClick(View view) {
        if (!isEnableClick()) {
            Log.d(TAG, "ignore click due to disabled");
            return;
        }
        ModeProtocol.CameraAction cameraAction = (ModeProtocol.CameraAction) ModeCoordinatorImpl.getInstance().getAttachProtocol(161);
        if (cameraAction != null && cameraAction.isDoingAction()) {
            Log.d(TAG, "ignore click due to doing action");
        } else if (view.getId() == R.id.dual_camera_bokeh_indicator) {
            MistatsWrapper.moduleUIClickEvent("M_portrait_", MistatsConstants.PortraitAttr.VALUE_BOKEH_ADJUST_ENTRY, (Object) 1);
            ModeProtocol.ActionProcessing actionProcessing = (ModeProtocol.ActionProcessing) ModeCoordinatorImpl.getInstance().getAttachProtocol(162);
            ModeProtocol.ConfigChanges configChanges = (ModeProtocol.ConfigChanges) ModeCoordinatorImpl.getInstance().getAttachProtocol(164);
            if (!(actionProcessing == null || !actionProcessing.isShowFilterView() || configChanges == null)) {
                configChanges.showOrHideFilter();
            }
            showBokehPanel();
        }
    }

    @Override // com.android.camera.ui.zoom.ZoomRatioToggleView.ToggleStateListener
    public void onClick(ZoomRatioView zoomRatioView) {
        Camera2DataContainer instance;
        if (!isInteractive()) {
            Log.w(TAG, "onClick(): ignored due to not interactive");
            return;
        }
        int zoomRatioIndex = zoomRatioView.getZoomRatioIndex();
        if (!isZoomPanelVisible()) {
            Log.d(TAG, "onClick(): current zoom ratio index = " + zoomRatioIndex);
            Log.d(TAG, "onClick(): current zoom ratio value = " + this.mZoomRatio);
            if (this.mZoomRatioToggleView.isSuppressed()) {
                if (CameraSettings.isZoomByCameraSwitchingSupported()) {
                    switchCameraLens();
                } else {
                    float f = 2.0f;
                    if (DataRepository.dataItemGlobal().isNormalIntent()) {
                        f = HybridZoomingSystem.getTeleZoomRatio(this.mCurrentMode);
                    }
                    if (DataRepository.dataItemFeature().pk() && (instance = Camera2DataContainer.getInstance()) != null && ComponentConfigVideoQuality.is8KCamcorderSupported(instance.getUltraTeleCameraId()) && CameraSettings.isVideoQuality8KOpen(this.mCurrentMode) && this.mCurrentMode == 162) {
                        f = HybridZoomingSystem.getUltraTeleMinZoomRatio();
                    }
                    float f2 = this.mZoomRatio;
                    if (f2 == 1.0f) {
                        CameraStatUtils.trackDualZoomChanged(this.mCurrentMode, HybridZoomingSystem.toString(f));
                        this.mIsZoomTo2X = true;
                        this.mTargetZoomRatio = f;
                        this.mZoomRatioToggleAnimator.setFloatValues(this.mZoomRatio, f);
                        this.mZoomRatioToggleAnimator.start();
                    } else if (f2 <= f) {
                        CameraStatUtils.trackDualZoomChanged(this.mCurrentMode, HybridZoomingSystem.toString(1.0f));
                        this.mIsZoomTo2X = false;
                        this.mTargetZoomRatio = 1.0f;
                        this.mZoomRatioToggleAnimator.setFloatValues(this.mZoomRatio, 1.0f);
                        this.mZoomRatioToggleAnimator.start();
                    } else {
                        CameraStatUtils.trackDualZoomChanged(this.mCurrentMode, HybridZoomingSystem.toString(1.0f));
                        requestZoomRatio(f, 0);
                        requestZoomRatio(1.0f, 0);
                    }
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
                        CameraStatUtils.trackDualZoomChanged(this.mCurrentMode, HybridZoomingSystem.toString(opticalZoomRatioAt));
                        configChanges.onConfigChanged(255);
                        return;
                    } else if (HybridZoomingSystem.getMacroZoomRatioIndex(this.mCurrentMode) == -1 || !CameraSettings.isMacroModeEnabled(this.mCurrentMode)) {
                        CameraStatUtils.trackDualZoomChanged(this.mCurrentMode, HybridZoomingSystem.toString(opticalZoomRatioAt));
                        this.mIsZoomTo2X = false;
                        this.mTargetZoomRatio = opticalZoomRatioAt;
                        this.mZoomRatioToggleAnimator.setFloatValues(this.mZoomRatio, opticalZoomRatioAt);
                        this.mZoomRatioToggleAnimator.start();
                    } else {
                        CameraSettings.writeZoom(opticalZoomRatioAt);
                        HybridZoomingSystem.setZoomRatioHistory(this.mCurrentMode, String.valueOf(opticalZoomRatioAt));
                        CameraStatUtils.trackDualZoomChanged(this.mCurrentMode, HybridZoomingSystem.toString(opticalZoomRatioAt));
                        configChanges.onConfigChanged(255);
                        if (DataRepository.dataItemFeature().Hi()) {
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

    @Override // androidx.fragment.app.Fragment
    public void onHiddenChanged(boolean z) {
        super.onHiddenChanged(z);
        if (this.mCurrentState == -1 && !z) {
            this.mDualParentLayout.setVisibility(8);
        }
    }

    @Override // com.android.camera.ui.zoom.ZoomIndexButtonsLayout.OnIndexButtonClickListener
    public void onIndexButtonClick(View view) {
        if (this.mHorizontalSlideView != null) {
            this.mHorizontalSlideView.setSelection(Math.round(this.mSlidingAdapter.mapValueToPosition(view.getTag())), false);
            CameraStatUtils.trackDualZoomChanged(this.mCurrentMode, view.getTag().toString());
        }
    }

    @Override // com.android.camera.fragment.manually.ZoomValueListener
    public void onManuallyDataChanged(String str) {
        ModeProtocol.ManuallyValueChanged manuallyValueChanged;
        if (!isInModeChanging() && (manuallyValueChanged = (ModeProtocol.ManuallyValueChanged) ModeCoordinatorImpl.getInstance().getAttachProtocol(174)) != null) {
            int i = this.mCurrentState;
            if (i == -200) {
                manuallyValueChanged.onDualZoomValueChanged(Float.valueOf(str).floatValue(), 3);
            } else if (i == -100) {
                manuallyValueChanged.onBokehFNumberValueChanged(str);
                ModeProtocol.TopAlert topAlert = (ModeProtocol.TopAlert) ModeCoordinatorImpl.getInstance().getAttachProtocol(172);
                if (topAlert != null) {
                    topAlert.alertUpdateValue(1, SupportedConfigFactory.CLOSE_BY_BOKEH + str);
                }
            }
        }
    }

    @Override // androidx.fragment.app.Fragment
    public void onPause() {
        super.onPause();
        this.mHandler.removeMessages(1);
    }

    @Override // com.android.camera.ui.BaseHorizontalZoomView.onTouchUpStateListener
    public void onScrollEnd() {
        CameraSettings.writeTargetZoom(0.0f);
        ((BaseModule) ((ActivityBase) getActivity()).getCurrentModule()).updatePreferenceTrampoline(79);
    }

    @Override // com.android.camera.ui.BaseHorizontalZoomView.onTouchUpStateListener
    public void onScrollStart(float f) {
        CameraSettings.writeTargetZoom(f);
        ((BaseModule) ((ActivityBase) getActivity()).getCurrentModule()).updatePreferenceTrampoline(79);
    }

    @Override // com.android.camera.ui.BaseHorizontalZoomView.onTouchUpStateListener
    public void onTouchUpState() {
        if (this.mCurrentState == -200) {
            CameraStatUtils.trackZoomAdjusted(MistatsConstants.BaseEvent.SLIDER, false);
            ((BaseModule) ((ActivityBase) getActivity()).getCurrentModule()).onZoomingActionEnd(3);
        }
    }

    @Override // com.android.camera.fragment.manually.ZoomValueListener
    public void onZoomItemSlideOn(int i, boolean z) {
        int i2 = this.mCurrentState;
        if (i2 == -100) {
            if (canProvide()) {
                ((ActivityBase) getContext()).playCameraSound(6, 0.5f);
            }
            ViberatorContext.getInstance(getContext().getApplicationContext()).performBokehAdjust();
        } else if (i2 != -200) {
        } else {
            if (z) {
                ViberatorContext.getInstance(getContext()).performSelectZoomNormal();
            } else {
                ViberatorContext.getInstance(getContext()).performSelectZoomLight();
            }
        }
    }

    @Override // com.android.camera.fragment.BaseFragment, com.android.camera.animation.AnimationDelegate.AnimationResource
    public void provideAnimateElement(int i, List<Completable> list, int i2) {
        int i3 = this.mCurrentMode;
        super.provideAnimateElement(i, list, i2);
        ViewGroup.LayoutParams layoutParams = this.mHorizontalSlideLayout.getLayoutParams();
        layoutParams.height = getResources().getDimensionPixelSize(R.dimen.manual_popup_layout_height);
        this.mZoomSliderLayoutHeight = layoutParams.height;
        this.mHorizontalSlideLayout.setLayoutParams(layoutParams);
        this.mIsRecordingOrPausing = false;
        initiateZoomRatio();
        onBackEvent(i2 == 2 ? 5 : 4);
        ZoomRatioToggleView.ViewSpec viewSpecForCapturingMode = getViewSpecForCapturingMode(this.mCurrentMode);
        if (viewSpecForCapturingMode.visibility == -200) {
            Log.d(TAG, "provideAnimateElement(): initialized zoom ratio: " + this.mZoomRatio);
            this.mZoomRatioToggleView.setCapturingMode(this.mCurrentMode, viewSpecForCapturingMode.suppress);
            this.mZoomRatioToggleView.setRotation((float) this.mDegree);
            this.mZoomRatioToggleView.setZoomRatio(this.mZoomRatio, -1);
            this.mZoomRatioToggleView.setUseSliderAllowed(viewSpecForCapturingMode.useSlider);
            this.mIsUseSlider = viewSpecForCapturingMode.useSlider;
        }
        adjustViewBackground(this.mHorizontalSlideLayout, this.mCurrentMode);
        int i4 = viewSpecForCapturingMode.visibility;
        if (i == 171) {
            if (!DataRepository.dataItemGlobal().isNormalIntent() || !DataRepository.dataItemFeature().isSupportBokehAdjust()) {
                i4 = -1;
            } else {
                this.mImageBokehIndicator.setRotation((float) this.mDegree);
                i4 = -100;
            }
        }
        if (i4 != this.mCurrentState) {
            this.mCurrentState = i4;
            int i5 = this.mCurrentState;
            if (i5 == -200) {
                AlphaInOnSubscribe.directSetResult(this.mDualParentLayout);
                this.mImageBokehIndicator.setVisibility(8);
                this.mZoomRatioToggleView.setVisibility(0);
                if (list == null || (i == 165 && i3 != 167)) {
                    AlphaInOnSubscribe.directSetResult(this.mZoomRatioToggleView);
                } else if (i3 == 167) {
                    list.add(Completable.create(new AlphaInOnSubscribe(this.mZoomRatioToggleView).setStartDelayTime(150)));
                } else {
                    list.add(Completable.create(new AlphaInOnSubscribe(this.mZoomRatioToggleView)));
                }
            } else if (i5 == -100) {
                AlphaInOnSubscribe.directSetResult(this.mDualParentLayout);
                this.mZoomRatioToggleView.setVisibility(8);
                this.mImageBokehIndicator.setVisibility(0);
                this.mHorizontalSlideView.setVisibility(0);
                if (isVisible(this.mImageBokehIndicator)) {
                    return;
                }
                if (list == null) {
                    AlphaInOnSubscribe.directSetResult(this.mImageBokehIndicator);
                } else {
                    list.add(Completable.create(new AlphaInOnSubscribe(this.mImageBokehIndicator)));
                }
            } else if (i5 == -1 && isVisible(this.mDualParentLayout)) {
                this.mDualParentLayout.setVisibility(8);
            }
        }
    }

    @Override // com.android.camera.fragment.BaseFragment, com.android.camera.animation.AnimationDelegate.AnimationResource
    public void provideRotateItem(List<View> list, int i) {
        ZoomIndexButtonsLayout zoomIndexButtonsLayout;
        ZoomRatioToggleView zoomRatioToggleView;
        super.provideRotateItem(list, i);
        if (this.mCurrentState == -200 && (zoomRatioToggleView = this.mZoomRatioToggleView) != null) {
            list.add(zoomRatioToggleView);
        }
        if (this.mCurrentState == -200 && (zoomIndexButtonsLayout = this.mZoomIndexButtons) != null) {
            zoomIndexButtonsLayout.provideRotateItem(list, i);
        }
        if (this.mImageBokehIndicator.getVisibility() == 0) {
            list.add(this.mImageBokehIndicator);
        }
    }

    /* access modifiers changed from: protected */
    @Override // com.android.camera.fragment.BaseFragment
    public void register(ModeProtocol.ModeCoordinator modeCoordinator) {
        super.register(modeCoordinator);
        modeCoordinator.attachProtocol(182, this);
        registerBackStack(modeCoordinator, this);
        if (c.isSupportedOpticalZoom() || HybridZoomingSystem.IS_3_OR_MORE_SAT) {
            modeCoordinator.attachProtocol(184, this);
        }
    }

    @Override // com.android.camera.fragment.BaseFragment, com.android.camera.animation.AnimationDelegate.AnimationResource
    public void setClickEnable(boolean z) {
        super.setClickEnable(z);
        ZoomRatioToggleView zoomRatioToggleView = this.mZoomRatioToggleView;
        if (zoomRatioToggleView != null) {
            zoomRatioToggleView.setEnabled(z);
        }
        ImageView imageView = this.mImageBokehIndicator;
        if (imageView != null) {
            imageView.setEnabled(z);
        }
    }

    @Override // com.android.camera.protocol.ModeProtocol.DualController
    public void setRecordingOrPausing(boolean z) {
        this.mIsRecordingOrPausing = z;
    }

    @Override // com.android.camera.protocol.ModeProtocol.SnapShotIndicator
    public void setSnapNumValue(int i) {
        this.mZoomRatioToggleView.setCaptureCount(i);
    }

    @Override // com.android.camera.protocol.ModeProtocol.SnapShotIndicator
    public void setSnapNumVisible(boolean z, boolean z2) {
        ViewGroup viewGroup = this.mHorizontalSlideLayout;
        if (viewGroup != null) {
            if (z) {
                if (!z2) {
                    ViewCompat.setRotation(this.mZoomRatioToggleView, (float) this.mDegree);
                    this.mZoomRatioToggleView.setVisibility(0);
                } else if (viewGroup.getVisibility() == 0) {
                    toHideZoomPanel(true);
                }
                this.mZoomRatioToggleView.setImmersive(true, false, z2);
                return;
            }
            updateZoomRatio(-1);
            this.mZoomRatioToggleView.setImmersive(false, false);
        }
    }

    @Override // com.android.camera.protocol.ModeProtocol.DualController
    public void showBokehButton() {
        if (this.mCurrentState != -100 && DataRepository.dataItemGlobal().isNormalIntent() && DataRepository.dataItemFeature().isSupportBokehAdjust()) {
            this.mCurrentState = -100;
            this.mDualParentLayout.setVisibility(0);
            Log.d(TAG, "showBokehButton");
        }
    }

    @Override // com.android.camera.protocol.ModeProtocol.DualController
    public void showZoomButton() {
        if (this.mCurrentState != -200 && !this.mIsRecordingOrPausing && getViewSpecForCapturingMode(this.mCurrentMode).visibility != -1) {
            this.mDualParentLayout.setVisibility(0);
            this.mCurrentState = -200;
            updateZoomRatio(-1);
            ViewCompat.setRotation(this.mZoomRatioToggleView, (float) this.mDegree);
            if (this.mCurrentMode == 188) {
                this.mZoomRatioToggleView.setVisibility(0);
            } else {
                Completable.create(new AlphaInOnSubscribe(this.mZoomRatioToggleView)).subscribe();
            }
        }
    }

    @Override // com.android.camera.ui.zoom.ZoomRatioToggleView.ToggleStateListener
    public boolean toShowSlideView(ZoomRatioView zoomRatioView) {
        boolean showZoomPanel = showZoomPanel();
        CameraStatUtils.trackShowZoomBarByScroll(showZoomPanel);
        updateZoomSlider();
        sendHideMessage();
        return showZoomPanel;
    }

    /* access modifiers changed from: protected */
    @Override // com.android.camera.fragment.BaseFragment
    public void unRegister(ModeProtocol.ModeCoordinator modeCoordinator) {
        super.unRegister(modeCoordinator);
        this.mHandler.removeCallbacksAndMessages(null);
        modeCoordinator.detachProtocol(182, this);
        unRegisterBackStack(modeCoordinator, this);
        if (c.isSupportedOpticalZoom() || HybridZoomingSystem.IS_3_OR_MORE_SAT) {
            modeCoordinator.detachProtocol(184, this);
        }
    }

    @Override // com.android.camera.protocol.ModeProtocol.DualController
    public boolean updateSlideAndZoomRatio(int i) {
        boolean showZoomPanel = (!this.mIsUseSlider || !(i == 2 || i == 1) || HybridZoomingSystem.toDecimal(CameraSettings.readZoom()) < 2.0f) ? false : showZoomPanel();
        updateZoomRatio(i);
        if (isZoomPanelVisible()) {
            sendHideMessage();
        }
        return showZoomPanel;
    }

    @Override // com.android.camera.protocol.ModeProtocol.DualController
    public void updateZoomIndexsButton() {
        ZoomIndexButtonsLayout zoomIndexButtonsLayout = this.mZoomIndexButtons;
        if (zoomIndexButtonsLayout != null) {
            zoomIndexButtonsLayout.setSelect(this.mZoomRatio, true);
        }
    }

    @Override // com.android.camera.protocol.ModeProtocol.DualController
    public void updateZoomRatio(int i) {
        if (CameraSettings.isZoomByCameraSwitchingSupported()) {
            String cameraLensType = CameraSettings.getCameraLensType(this.mCurrentMode);
            if (ComponentManuallyDualLens.LENS_ULTRA.equals(cameraLensType)) {
                this.mZoomRatio = HybridZoomingSystem.FLOAT_ZOOM_RATIO_ULTR;
            } else if (ComponentManuallyDualLens.LENS_WIDE.equals(cameraLensType)) {
                this.mZoomRatio = 1.0f;
            } else if (ComponentManuallyDualLens.LENS_TELE.equals(cameraLensType)) {
                this.mZoomRatio = HybridZoomingSystem.getTeleMinZoomRatio();
            } else if (ComponentManuallyDualLens.LENS_ULTRA_TELE.equals(cameraLensType)) {
                this.mZoomRatio = HybridZoomingSystem.getUltraTeleMinZoomRatio();
            } else {
                throw new IllegalStateException("updateZoomRatio(): Unknown camera lens type: " + cameraLensType);
            }
        } else {
            this.mZoomRatio = CameraSettings.readZoom();
        }
        if (!this.mZoomRatioToggleAnimator.isRunning() || HybridZoomingSystem.isOpticalZoomRatio(this.mCurrentMode, this.mZoomRatio) || (!DataRepository.dataItemGlobal().isNormalIntent() && this.mZoomRatio == 2.0f)) {
            this.mZoomRatioToggleView.setZoomRatio(this.mZoomRatio, i);
            if (i != 3) {
                updateZoomSlider();
            }
        }
    }

    @Override // com.android.camera.protocol.ModeProtocol.DualController
    public int visibleHeight() {
        int i = this.mCurrentState;
        if (i == -1) {
            return 0;
        }
        if (i == -100 && this.mImageBokehIndicator.getVisibility() == 8) {
            return 0;
        }
        return this.mButtonLayoutHeight;
    }
}
