package com.android.camera.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.ViewPropertyAnimatorListener;
import android.text.TextUtils;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.MarginLayoutParams;
import android.view.animation.Animation;
import android.view.animation.DecelerateInterpolator;
import android.widget.SeekBar;
import com.android.camera.CameraSettings;
import com.android.camera.R;
import com.android.camera.Util;
import com.android.camera.animation.FragmentAnimationFactory;
import com.android.camera.animation.type.AlphaInOnSubscribe;
import com.android.camera.animation.type.AlphaOutOnSubscribe;
import com.android.camera.animation.type.SlideInOnSubscribe;
import com.android.camera.animation.type.SlideOutOnSubscribe;
import com.android.camera.constant.BeautyConstant;
import com.android.camera.constant.GlobalConstant;
import com.android.camera.data.DataRepository;
import com.android.camera.data.data.ComponentDataItem;
import com.android.camera.data.data.TypeItem;
import com.android.camera.data.data.runing.ComponentRunningShine;
import com.android.camera.data.data.runing.ComponentRunningShine.ShineType;
import com.android.camera.fragment.beauty.BeautyBodyFragment;
import com.android.camera.fragment.beauty.BeautyEyeLightFragment;
import com.android.camera.fragment.beauty.BeautyLevelFragment;
import com.android.camera.fragment.beauty.BeautySettingManager;
import com.android.camera.fragment.beauty.BeautySmoothLevelFragment;
import com.android.camera.fragment.beauty.IBeautySettingBusiness;
import com.android.camera.fragment.beauty.LiveBeautyFilterFragment;
import com.android.camera.fragment.beauty.LiveBeautyModeFragment;
import com.android.camera.fragment.beauty.MakeupBeautyFragment;
import com.android.camera.fragment.beauty.MakeupParamsFragment;
import com.android.camera.fragment.beauty.RemodelingParamsFragment;
import com.android.camera.fragment.live.FragmentLiveSpeed;
import com.android.camera.fragment.live.FragmentLiveSticker;
import com.android.camera.log.Log;
import com.android.camera.protocol.ModeCoordinatorImpl;
import com.android.camera.protocol.ModeProtocol.BokehFNumberController;
import com.android.camera.protocol.ModeProtocol.BottomMenuProtocol;
import com.android.camera.protocol.ModeProtocol.BottomPopupTips;
import com.android.camera.protocol.ModeProtocol.CameraAction;
import com.android.camera.protocol.ModeProtocol.CameraModuleSpecial;
import com.android.camera.protocol.ModeProtocol.ConfigChanges;
import com.android.camera.protocol.ModeProtocol.DualController;
import com.android.camera.protocol.ModeProtocol.HandleBackTrace;
import com.android.camera.protocol.ModeProtocol.MakeupProtocol;
import com.android.camera.protocol.ModeProtocol.ManuallyAdjust;
import com.android.camera.protocol.ModeProtocol.MiBeautyProtocol;
import com.android.camera.protocol.ModeProtocol.ModeCoordinator;
import com.android.camera.protocol.ModeProtocol.TopAlert;
import com.android.camera.ui.NoScrollViewPager;
import com.android.camera.ui.SeekBarCompat;
import com.android.camera.ui.SeekBarCompat.OnSeekBarCompatChangeListener;
import com.android.camera.ui.SeekBarCompat.OnSeekBarCompatTouchListener;
import io.reactivex.BackpressureStrategy;
import io.reactivex.Completable;
import io.reactivex.Flowable;
import io.reactivex.FlowableEmitter;
import io.reactivex.FlowableOnSubscribe;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import miui.view.animation.QuinticEaseInInterpolator;
import miui.view.animation.QuinticEaseOutInterpolator;

public class FragmentBeauty extends BaseFragment implements HandleBackTrace, MiBeautyProtocol, MakeupProtocol, Consumer<Integer>, OnClickListener {
    public static final int FRAGMENT_INFO = 251;
    private static final int INTERVAL = 5;
    private static final int SEEKBAR_PROGRESS_RATIO = 1;
    /* access modifiers changed from: private */
    public int mActiveProgress;
    /* access modifiers changed from: private */
    public SeekBarCompat mAdjustSeekBar;
    private View mBeautyContent;
    private View mBeautyExtraView;
    private BaseFragmentPagerAdapter mBeautyPagerAdapter;
    private BeautySettingManager mBeautySettingManager;
    private ComponentRunningShine mComponentRunningShine;
    private IBeautySettingBusiness mCurrentSettingBusiness;
    private int mCurrentState = -1;
    /* access modifiers changed from: private */
    public BeautyEyeLightFragment mEyeLightFragment;
    /* access modifiers changed from: private */
    public FlowableEmitter<Integer> mFlowableEmitter;
    private boolean mIsEyeLightShow;
    /* access modifiers changed from: private */
    public boolean mIsRTL;
    private Disposable mSeekBarDisposable;
    /* access modifiers changed from: private */
    public int mSeekBarMaxProgress = 100;
    private NoScrollViewPager mViewPager;

    static /* synthetic */ boolean b(View view, MotionEvent motionEvent) {
        return true;
    }

    private void extraEnterAnim() {
        this.mViewPager.setTranslationX(0.0f);
        this.mViewPager.setAlpha(1.0f);
        ViewCompat.animate(this.mViewPager).translationX(-100.0f).alpha(0.0f).setDuration(120).setStartDelay(0).setInterpolator(new QuinticEaseInInterpolator()).start();
    }

    private void extraExitAnim() {
        this.mViewPager.setTranslationX(-100.0f);
        this.mViewPager.setAlpha(0.0f);
        ViewCompat.animate(this.mViewPager).translationX(0.0f).alpha(1.0f).setDuration(280).setInterpolator(new QuinticEaseOutInterpolator()).setStartDelay(120).start();
    }

    private boolean hideBeautyLayout(int i, int i2) {
        if (this.mCurrentState == -1) {
            return false;
        }
        if (3 == i && !isHiddenBeautyPanelOnShutter()) {
            return false;
        }
        this.mCurrentState = -1;
        View view = getView();
        if (view == null) {
            return false;
        }
        if (i2 == 1) {
            resetFragment();
            view.setVisibility(4);
        } else if (i2 == 2) {
            ((BottomMenuProtocol) ModeCoordinatorImpl.getInstance().getAttachProtocol(197)).onSwitchCameraActionMenu(0);
            Completable.create(new SlideOutOnSubscribe(view, 80).setDurationTime(140).setInterpolator(new QuinticEaseInInterpolator())).subscribe((Action) new c(this));
        } else if (i2 == 3) {
            ((BottomMenuProtocol) ModeCoordinatorImpl.getInstance().getAttachProtocol(197)).onSwitchCameraActionMenu(0);
            Completable.create(new AlphaOutOnSubscribe(view).setDurationTime(250).setInterpolator(new DecelerateInterpolator())).subscribe((Action) new a(this));
        }
        return true;
    }

    private void initAdjustSeekBar() {
        if (this.mSeekBarDisposable == null) {
            this.mSeekBarDisposable = Flowable.create(new FlowableOnSubscribe<Integer>() {
                public void subscribe(FlowableEmitter<Integer> flowableEmitter) throws Exception {
                    FragmentBeauty.this.mFlowableEmitter = flowableEmitter;
                }
            }, BackpressureStrategy.DROP).observeOn(GlobalConstant.sCameraSetupScheduler).onBackpressureDrop(new Consumer<Integer>() {
                public void accept(@NonNull Integer num) throws Exception {
                    StringBuilder sb = new StringBuilder();
                    sb.append("seekbar change too fast :");
                    sb.append(num.toString());
                    Log.e(Log.VIEW_TAG, sb.toString());
                }
            }).subscribe((Consumer<? super T>) this);
            this.mAdjustSeekBar.setProgressDrawable(getResources().getDrawable(R.drawable.seekbar_style));
            this.mAdjustSeekBar.setOnSeekBarChangeListener(new OnSeekBarCompatChangeListener() {
                public void onProgressChanged(SeekBar seekBar, int i, boolean z) {
                    if (i == 0 || i == FragmentBeauty.this.mSeekBarMaxProgress || Math.abs(i - FragmentBeauty.this.mActiveProgress) > 5) {
                        FragmentBeauty.this.mActiveProgress = i;
                        FragmentBeauty.this.mFlowableEmitter.onNext(Integer.valueOf(i / 1));
                    }
                }

                public void onStartTrackingTouch(SeekBar seekBar) {
                }

                public void onStopTrackingTouch(SeekBar seekBar) {
                }
            });
            this.mAdjustSeekBar.setOnSeekBarCompatTouchListener(new OnSeekBarCompatTouchListener() {
                private int getNextProgress(MotionEvent motionEvent) {
                    int width = FragmentBeauty.this.mAdjustSeekBar.getWidth();
                    int x = (int) (((FragmentBeauty.this.mIsRTL ? ((float) width) - motionEvent.getX() : motionEvent.getX()) / ((float) width)) * ((float) FragmentBeauty.this.mSeekBarMaxProgress));
                    int pinProgress = FragmentBeauty.this.mAdjustSeekBar.getPinProgress();
                    if (pinProgress > 0 && (motionEvent.getAction() == 2 || motionEvent.getAction() == 1)) {
                        if (Math.abs(x + 0) <= 5) {
                            x = 0;
                        } else if (Math.abs(x - pinProgress) <= 5) {
                            x = pinProgress;
                        } else if (Math.abs(x - FragmentBeauty.this.mSeekBarMaxProgress) <= 5) {
                            x = FragmentBeauty.this.mSeekBarMaxProgress;
                        }
                    }
                    return FragmentBeauty.this.mAdjustSeekBar.isCenterTwoWayMode() ? Util.clamp(x - pinProgress, 0 - pinProgress, FragmentBeauty.this.mSeekBarMaxProgress - pinProgress) : Util.clamp(x, 0, FragmentBeauty.this.mSeekBarMaxProgress);
                }

                public boolean onTouch(View view, MotionEvent motionEvent) {
                    int action = motionEvent.getAction();
                    if (action == 0) {
                        if (!FragmentBeauty.this.mAdjustSeekBar.getThumb().getBounds().contains((int) motionEvent.getX(), (int) motionEvent.getY())) {
                            return true;
                        }
                    } else if (!(action == 1 || action == 2)) {
                        return false;
                    }
                    FragmentBeauty.this.mAdjustSeekBar.setProgress(getNextProgress(motionEvent));
                    return true;
                }
            });
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:11:0x0030  */
    /* JADX WARNING: Removed duplicated region for block: B:12:0x0047  */
    /* JADX WARNING: Removed duplicated region for block: B:15:0x006a  */
    private void initShineType() {
        int i;
        int uiStyle = DataRepository.dataItemRunning().getUiStyle();
        if (!(uiStyle == 3 || uiStyle == 1)) {
            int i2 = this.mCurrentMode;
            if (!(i2 == 171 || i2 == 176)) {
                this.mBeautyContent.setBackgroundResource(R.color.beauty_panel_bg);
                if (this.mCurrentMode != 165) {
                    int i3 = Util.sWindowWidth;
                    i = ((((int) (((float) i3) / 0.75f)) - i3) / 2) + getResources().getDimensionPixelSize(R.dimen.square_mode_bottom_cover_extra_margin);
                } else {
                    i = getResources().getDimensionPixelSize(R.dimen.beauty_fragment_height);
                }
                MarginLayoutParams marginLayoutParams = (MarginLayoutParams) this.mBeautyExtraView.getLayoutParams();
                ((MarginLayoutParams) this.mViewPager.getLayoutParams()).height = i;
                marginLayoutParams.height = i;
                if (this.mBeautySettingManager == null) {
                    this.mBeautySettingManager = new BeautySettingManager();
                }
                this.mCurrentState = 1;
                this.mComponentRunningShine = DataRepository.dataItemRunning().getComponentRunningShine();
                initAdjustSeekBar();
                String currentType = this.mComponentRunningShine.getCurrentType();
                initShineType(currentType, false);
                initViewPager();
                setViewPagerPageByType(currentType);
            }
        }
        this.mBeautyContent.setBackgroundResource(R.color.fullscreen_background);
        if (this.mCurrentMode != 165) {
        }
        MarginLayoutParams marginLayoutParams2 = (MarginLayoutParams) this.mBeautyExtraView.getLayoutParams();
        ((MarginLayoutParams) this.mViewPager.getLayoutParams()).height = i;
        marginLayoutParams2.height = i;
        if (this.mBeautySettingManager == null) {
        }
        this.mCurrentState = 1;
        this.mComponentRunningShine = DataRepository.dataItemRunning().getComponentRunningShine();
        initAdjustSeekBar();
        String currentType2 = this.mComponentRunningShine.getCurrentType();
        initShineType(currentType2, false);
        initViewPager();
        setViewPagerPageByType(currentType2);
    }

    private void initShineType(String str, boolean z) {
        if (!TextUtils.isEmpty(str)) {
            this.mComponentRunningShine.setCurrentType(str);
            if (isEyeLightShow()) {
                showHideEyeLighting(false);
            }
            char c2 = 65535;
            int hashCode = str.hashCode();
            if (hashCode != 57) {
                if (hashCode != 1568) {
                    switch (hashCode) {
                        case 49:
                            if (str.equals("1")) {
                                c2 = 1;
                                break;
                            }
                            break;
                        case 50:
                            if (str.equals("2")) {
                                c2 = 2;
                                break;
                            }
                            break;
                        case 51:
                            if (str.equals("3")) {
                                c2 = 3;
                                break;
                            }
                            break;
                        case 52:
                            if (str.equals("4")) {
                                c2 = 4;
                                break;
                            }
                            break;
                        case 53:
                            if (str.equals("5")) {
                                c2 = 5;
                                break;
                            }
                            break;
                        case 54:
                            if (str.equals("6")) {
                                c2 = 6;
                                break;
                            }
                            break;
                    }
                } else if (str.equals(ComponentRunningShine.SHINE_LIVE_BEAUTY)) {
                    c2 = 7;
                }
            } else if (str.equals("9")) {
                c2 = 0;
            }
            switch (c2) {
                case 0:
                    throw new RuntimeException("not allowed, see onMakeupItemSelected");
                case 1:
                case 2:
                    this.mCurrentSettingBusiness = null;
                    setAdjustSeekBarVisible(false, true);
                    break;
                case 3:
                case 4:
                case 5:
                case 6:
                case 7:
                    this.mCurrentSettingBusiness = this.mBeautySettingManager.constructAndGetSetting(str, this.mComponentRunningShine.getTypeElementsBeauty());
                    break;
                default:
                    this.mCurrentSettingBusiness = null;
                    setAdjustSeekBarVisible(false, true);
                    break;
            }
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:37:0x009a, code lost:
        if (r2.equals("1") != false) goto L_0x009e;
     */
    private void initViewPager() {
        ArrayList arrayList = new ArrayList();
        Iterator it = this.mComponentRunningShine.getItems().iterator();
        while (true) {
            char c2 = 0;
            if (it.hasNext()) {
                String str = ((ComponentDataItem) it.next()).mValue;
                int hashCode = str.hashCode();
                switch (hashCode) {
                    case 49:
                        break;
                    case 50:
                        if (str.equals("2")) {
                            c2 = 1;
                            break;
                        }
                    case 51:
                        if (str.equals("3")) {
                            c2 = 2;
                            break;
                        }
                    case 52:
                        if (str.equals("4")) {
                            c2 = 3;
                            break;
                        }
                    case 53:
                        if (str.equals("5")) {
                            c2 = 4;
                            break;
                        }
                    case 54:
                        if (str.equals("6")) {
                            c2 = 5;
                            break;
                        }
                    case 55:
                        if (str.equals("7")) {
                            c2 = 6;
                            break;
                        }
                    default:
                        switch (hashCode) {
                            case 1567:
                                if (str.equals("10")) {
                                    c2 = 7;
                                    break;
                                }
                            case 1568:
                                if (str.equals(ComponentRunningShine.SHINE_LIVE_BEAUTY)) {
                                    c2 = 8;
                                    break;
                                }
                            case 1569:
                                if (str.equals("12")) {
                                    c2 = 9;
                                    break;
                                }
                            case 1570:
                                if (str.equals("13")) {
                                    c2 = 10;
                                    break;
                                }
                        }
                        c2 = 65535;
                        break;
                }
                switch (c2) {
                    case 0:
                        arrayList.add(new BeautyLevelFragment());
                        break;
                    case 1:
                        arrayList.add(new BeautySmoothLevelFragment());
                        break;
                    case 2:
                        arrayList.add(new MakeupParamsFragment());
                        break;
                    case 3:
                        arrayList.add(new RemodelingParamsFragment());
                        break;
                    case 4:
                        arrayList.add(new MakeupBeautyFragment());
                        break;
                    case 5:
                        arrayList.add(new BeautyBodyFragment());
                        break;
                    case 6:
                        arrayList.add(new FragmentFilter());
                        break;
                    case 7:
                        arrayList.add(new LiveBeautyFilterFragment());
                        break;
                    case 8:
                        arrayList.add(new LiveBeautyModeFragment());
                        break;
                    case 9:
                        arrayList.add(new FragmentLiveSticker());
                        break;
                    case 10:
                        arrayList.add(new FragmentLiveSpeed());
                        break;
                    default:
                        throw new RuntimeException("unknown beauty type");
                }
            } else {
                this.mBeautyPagerAdapter = new BaseFragmentPagerAdapter(getChildFragmentManager(), arrayList);
                this.mViewPager.setAdapter(this.mBeautyPagerAdapter);
                this.mViewPager.setOffscreenPageLimit(2);
                this.mViewPager.setFocusable(false);
                this.mViewPager.setOnTouchListener(b.INSTANCE);
                return;
            }
        }
    }

    private boolean isHiddenBeautyPanelOnShutter() {
        int i = this.mCurrentMode;
        return i == 162 || i == 161 || i == 174 || i == 176;
    }

    /* access modifiers changed from: private */
    /* renamed from: onDismissFinished */
    public void ea() {
        resetFragment();
        CameraAction cameraAction = (CameraAction) ModeCoordinatorImpl.getInstance().getAttachProtocol(161);
        if (!isHiddenBeautyPanelOnShutter() || (cameraAction != null && !cameraAction.isDoingAction() && !cameraAction.isRecording())) {
            resetTips();
        }
    }

    private void resetFragment() {
        this.mAdjustSeekBar.setVisibility(4);
        this.mViewPager.setAdapter(null);
        BaseFragmentPagerAdapter baseFragmentPagerAdapter = this.mBeautyPagerAdapter;
        if (baseFragmentPagerAdapter != null) {
            baseFragmentPagerAdapter.recycleFragmentList(getChildFragmentManager());
            this.mBeautyPagerAdapter = null;
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:31:0x0088  */
    /* JADX WARNING: Removed duplicated region for block: B:33:? A[RETURN, SYNTHETIC] */
    private void resetTips() {
        ConfigChanges configChanges;
        BottomPopupTips bottomPopupTips = (BottomPopupTips) ModeCoordinatorImpl.getInstance().getAttachProtocol(175);
        if (bottomPopupTips != null) {
            bottomPopupTips.reInitTipImage();
        }
        if (CameraSettings.shouldShowUltraWideStickyTip(this.mCurrentMode) && bottomPopupTips != null) {
            bottomPopupTips.directlyShowTips(13, R.string.ultra_wide_open_tip);
        }
        if (bottomPopupTips != null) {
            bottomPopupTips.updateTipBottomMargin(0, true);
        }
        showZoomTipImage();
        int i = this.mCurrentMode;
        if (i == 163) {
            CameraModuleSpecial cameraModuleSpecial = (CameraModuleSpecial) ModeCoordinatorImpl.getInstance().getAttachProtocol(195);
            if (cameraModuleSpecial != null) {
                cameraModuleSpecial.showOrHideChip(true);
            }
        } else if (i != 165) {
            if (i == 167) {
                ManuallyAdjust manuallyAdjust = (ManuallyAdjust) ModeCoordinatorImpl.getInstance().getAttachProtocol(181);
                if (manuallyAdjust != null) {
                    manuallyAdjust.setManuallyLayoutVisible(true);
                }
            } else if (i == 171) {
                BokehFNumberController bokehFNumberController = (BokehFNumberController) ModeCoordinatorImpl.getInstance().getAttachProtocol(210);
                if (bokehFNumberController != null) {
                    bokehFNumberController.showFNumberPanel(true);
                }
            }
            configChanges = (ConfigChanges) ModeCoordinatorImpl.getInstance().getAttachProtocol(164);
            if (configChanges == null) {
                configChanges.reCheckEyeLight();
                return;
            }
            return;
        }
        if (bottomPopupTips != null) {
            bottomPopupTips.updateLyingDirectHint(false, true);
        }
        configChanges = (ConfigChanges) ModeCoordinatorImpl.getInstance().getAttachProtocol(164);
        if (configChanges == null) {
        }
    }

    private void setAdjustSeekBarVisible(boolean z, boolean z2) {
        if (z) {
            if (this.mAdjustSeekBar.getVisibility() != 0) {
                if (z2) {
                    Completable.create(new AlphaInOnSubscribe(this.mAdjustSeekBar)).subscribe();
                } else {
                    AlphaInOnSubscribe.directSetResult(this.mAdjustSeekBar);
                }
            }
        } else if (this.mAdjustSeekBar.getVisibility() == 0) {
            if (z2) {
                Completable.create(new AlphaOutOnSubscribe(this.mAdjustSeekBar)).subscribe();
            } else {
                AlphaOutOnSubscribe.directSetResult(this.mAdjustSeekBar);
            }
        }
    }

    private void setSeekBarMode(int i, int i2) {
        if (this.mCurrentSettingBusiness != null) {
            boolean z = false;
            if (i == 1) {
                this.mSeekBarMaxProgress = 100;
                this.mAdjustSeekBar.setProgressDrawable(getResources().getDrawable(R.drawable.seekbar_style));
            } else if (i == 2) {
                this.mSeekBarMaxProgress = 200;
                this.mAdjustSeekBar.setProgressDrawable(getResources().getDrawable(R.drawable.center_seekbar_style));
                i2 = 100;
                z = true;
            }
            this.mAdjustSeekBar.setMax(this.mSeekBarMaxProgress);
            this.mAdjustSeekBar.setSeekBarPinProgress(i2);
            this.mAdjustSeekBar.setCenterTwoWayMode(z);
            this.mAdjustSeekBar.setProgress(this.mCurrentSettingBusiness.getProgressByCurrentItem() * 1);
        }
    }

    private void setViewPagerPageByType(String str) {
        List items = this.mComponentRunningShine.getItems();
        for (int i = 0; i < items.size(); i++) {
            if (((ComponentDataItem) items.get(i)).mValue.equals(str)) {
                this.mViewPager.setCurrentItem(i, false);
                return;
            }
        }
    }

    /* access modifiers changed from: private */
    public void showHideExtraLayout(boolean z, Fragment fragment, String str) {
        if (z) {
            FragmentUtils.addFragmentWithTag(getFragmentManager(), (int) R.id.beauty_extra, fragment, str);
        } else {
            FragmentUtils.removeFragmentByTag(getFragmentManager(), str);
        }
    }

    private void showHideEyeLighting(boolean z) {
        setAdjustSeekBarVisible(false, false);
        if (DataRepository.dataItemFeature().Kc() && CameraSettings.isSupportBeautyMakeup()) {
            if (this.mEyeLightFragment == null) {
                this.mEyeLightFragment = new BeautyEyeLightFragment();
            }
            TopAlert topAlert = (TopAlert) ModeCoordinatorImpl.getInstance().getAttachProtocol(172);
            if (z) {
                topAlert.alertTopHint(0, (int) R.string.eye_light);
                BeautyEyeLightFragment beautyEyeLightFragment = this.mEyeLightFragment;
                showHideExtraLayout(true, beautyEyeLightFragment, beautyEyeLightFragment.getFragmentTag());
                extraEnterAnim();
                this.mEyeLightFragment.enterAnim(this.mBeautyExtraView, new ViewPropertyAnimatorListener() {
                    public void onAnimationCancel(View view) {
                    }

                    public void onAnimationEnd(View view) {
                        if (FragmentBeauty.this.mEyeLightFragment != null && FragmentBeauty.this.mEyeLightFragment.isAdded()) {
                            FragmentBeauty.this.mEyeLightFragment.userVisibleHint();
                        }
                    }

                    public void onAnimationStart(View view) {
                    }
                });
            } else {
                topAlert.alertTopHint(8, 0);
                this.mEyeLightFragment.exitAnim(this.mBeautyExtraView, new ViewPropertyAnimatorListener() {
                    public void onAnimationCancel(View view) {
                    }

                    public void onAnimationEnd(View view) {
                        FragmentBeauty fragmentBeauty = FragmentBeauty.this;
                        fragmentBeauty.showHideExtraLayout(false, fragmentBeauty.mEyeLightFragment, FragmentBeauty.this.mEyeLightFragment.getFragmentTag());
                        FragmentBeauty.this.mEyeLightFragment.userInvisibleHit();
                        BottomPopupTips bottomPopupTips = (BottomPopupTips) ModeCoordinatorImpl.getInstance().getAttachProtocol(175);
                        if (bottomPopupTips != null) {
                            bottomPopupTips.directlyHideTips();
                        }
                    }

                    public void onAnimationStart(View view) {
                    }
                });
                extraExitAnim();
            }
            this.mIsEyeLightShow = z;
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:13:0x0023, code lost:
        if (com.android.camera.HybridZoomingSystem.IS_3_OR_MORE_SAT == false) goto L_0x006e;
     */
    private void showZoomTipImage() {
        int i = this.mCurrentMode;
        if (i != 165) {
            if (i != 166) {
                if (i != 173) {
                    if (i != 174) {
                        switch (i) {
                            case 161:
                            case 162:
                                break;
                            case 163:
                                break;
                        }
                    }
                }
            } else if (!DataRepository.dataItemFeature().Yb()) {
                return;
            }
        }
        DualController dualController = (DualController) ModeCoordinatorImpl.getInstance().getAttachProtocol(182);
        if (dualController != null && !CameraSettings.isFrontCamera() && !CameraSettings.isUltraWideConfigOpen(this.mCurrentMode) && !CameraSettings.isUltraPixelOn() && (!CameraSettings.isMacroModeEnabled(this.mCurrentMode) || DataRepository.dataItemFeature().mb())) {
            dualController.showZoomButton();
            TopAlert topAlert = (TopAlert) ModeCoordinatorImpl.getInstance().getAttachProtocol(172);
            if (topAlert != null) {
                topAlert.clearAlertStatus();
            }
        }
    }

    public void accept(@NonNull Integer num) throws Exception {
        IBeautySettingBusiness iBeautySettingBusiness = this.mCurrentSettingBusiness;
        if (iBeautySettingBusiness != null) {
            iBeautySettingBusiness.setProgressForCurrentItem(num.intValue());
        }
    }

    public void clearBeauty() {
        IBeautySettingBusiness iBeautySettingBusiness = this.mCurrentSettingBusiness;
        if (iBeautySettingBusiness != null) {
            iBeautySettingBusiness.clearBeauty();
        }
    }

    public void dismiss(int i) {
        hideBeautyLayout(1, i);
    }

    public int getFragmentInto() {
        return 251;
    }

    /* access modifiers changed from: protected */
    public int getLayoutResourceId() {
        return R.layout.fragment_beauty;
    }

    public List<TypeItem> getSupportedBeautyItems(@ShineType String str) {
        return this.mBeautySettingManager.constructAndGetSetting(str, this.mComponentRunningShine.getTypeElementsBeauty()).getSupportedTypeArray(str);
    }

    /* access modifiers changed from: protected */
    public void initView(View view) {
        this.mIsRTL = Util.isLayoutRTL(getContext());
        this.mBeautyContent = view.findViewById(R.id.beauty_content);
        this.mBeautyExtraView = view.findViewById(R.id.beauty_extra);
        this.mViewPager = (NoScrollViewPager) view.findViewById(R.id.beauty_viewPager);
        this.mAdjustSeekBar = (SeekBarCompat) view.findViewById(R.id.beauty_adjust_seekbar);
        initShineType();
    }

    public boolean isBeautyPanelShow() {
        return this.mCurrentState == 1;
    }

    public boolean isEyeLightShow() {
        return this.mIsEyeLightShow;
    }

    public boolean isSeekBarVisible() {
        SeekBarCompat seekBarCompat = this.mAdjustSeekBar;
        return seekBarCompat != null && seekBarCompat.getVisibility() == 0;
    }

    public boolean needViewClear() {
        if (CameraSettings.isUltraPixelRearOn()) {
            return true;
        }
        return super.needViewClear();
    }

    public boolean onBackEvent(int i) {
        int i2 = 3;
        if (i != 3) {
            i2 = i != 4 ? 2 : 1;
        }
        return hideBeautyLayout(i, i2);
    }

    public void onClick(View view) {
    }

    public void onMakeupItemSelected(String str, boolean z) {
        if (str == "pref_eye_light_type_key") {
            if (z) {
                showHideEyeLighting(!isEyeLightShow());
            } else {
                setAdjustSeekBarVisible(false, false);
            }
            return;
        }
        IBeautySettingBusiness iBeautySettingBusiness = this.mCurrentSettingBusiness;
        if (iBeautySettingBusiness != null) {
            iBeautySettingBusiness.setCurrentType(str);
            this.mActiveProgress = this.mCurrentSettingBusiness.getProgressByCurrentItem() * 1;
            int defaultProgressByCurrentItem = this.mCurrentSettingBusiness.getDefaultProgressByCurrentItem() * 1;
            if (BeautyConstant.isSupportTwoWayAdjustable(str)) {
                setSeekBarMode(2, defaultProgressByCurrentItem);
            } else {
                setSeekBarMode(1, defaultProgressByCurrentItem);
            }
            setAdjustSeekBarVisible(true, true);
        }
    }

    public void onPause() {
        super.onPause();
    }

    public void onViewCreated(View view, @Nullable Bundle bundle) {
        super.onViewCreated(view, bundle);
        if (this.mCurrentMode == 163) {
            CameraModuleSpecial cameraModuleSpecial = (CameraModuleSpecial) ModeCoordinatorImpl.getInstance().getAttachProtocol(195);
            if (cameraModuleSpecial != null) {
                cameraModuleSpecial.showOrHideChip(false);
            }
        }
    }

    public void provideAnimateElement(int i, List<Completable> list, int i2) {
        super.provideAnimateElement(i, list, i2);
        if (this.mCurrentState != -1) {
            onBackEvent(4);
        }
    }

    /* access modifiers changed from: protected */
    public Animation provideEnterAnimation(int i) {
        Animation wrapperAnimation = FragmentAnimationFactory.wrapperAnimation(167, 161);
        wrapperAnimation.setDuration(280);
        wrapperAnimation.setInterpolator(new QuinticEaseOutInterpolator());
        return wrapperAnimation;
    }

    /* access modifiers changed from: protected */
    public Animation provideExitAnimation(int i) {
        return null;
    }

    /* access modifiers changed from: protected */
    public void register(ModeCoordinator modeCoordinator) {
        super.register(modeCoordinator);
        registerBackStack(modeCoordinator, this);
        modeCoordinator.attachProtocol(194, this);
        modeCoordinator.attachProtocol(180, this);
        this.mIsEyeLightShow = false;
    }

    public void resetBeauty() {
        IBeautySettingBusiness iBeautySettingBusiness = this.mCurrentSettingBusiness;
        if (iBeautySettingBusiness != null) {
            iBeautySettingBusiness.resetBeauty();
        }
    }

    public void setClickEnable(boolean z) {
        super.setClickEnable(z);
        BaseFragmentPagerAdapter baseFragmentPagerAdapter = this.mBeautyPagerAdapter;
        if (baseFragmentPagerAdapter != null) {
            List<Fragment> fragmentList = baseFragmentPagerAdapter.getFragmentList();
            if (fragmentList != null) {
                for (Fragment fragment : fragmentList) {
                    if (fragment instanceof BeautyLevelFragment) {
                        ((BeautyLevelFragment) fragment).setEnableClick(z);
                        return;
                    }
                }
            }
        }
    }

    public void show() {
        if (this.mCurrentState != 1) {
            if (this.mCurrentMode == 163) {
                CameraModuleSpecial cameraModuleSpecial = (CameraModuleSpecial) ModeCoordinatorImpl.getInstance().getAttachProtocol(195);
                if (cameraModuleSpecial != null) {
                    cameraModuleSpecial.showOrHideChip(false);
                }
            }
            initShineType();
            Completable.create(new SlideInOnSubscribe(getView(), 80).setDurationTime(280).setInterpolator(new QuinticEaseOutInterpolator())).subscribe();
        }
    }

    public void switchShineType(String str, boolean z) {
        initShineType(str, z);
        setViewPagerPageByType(str);
    }

    /* access modifiers changed from: protected */
    public void unRegister(ModeCoordinator modeCoordinator) {
        super.unRegister(modeCoordinator);
        unRegisterBackStack(modeCoordinator, this);
        modeCoordinator.detachProtocol(194, this);
        modeCoordinator.detachProtocol(180, this);
        Disposable disposable = this.mSeekBarDisposable;
        if (disposable != null && !disposable.isDisposed()) {
            this.mSeekBarDisposable.dispose();
        }
        this.mIsEyeLightShow = false;
    }
}
