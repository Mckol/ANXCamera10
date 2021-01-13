package com.android.camera.fragment;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Animation;
import android.widget.SeekBar;
import androidx.annotation.Nullable;
import androidx.core.view.ViewCompat;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;
import com.android.camera.Camera;
import com.android.camera.CameraSettings;
import com.android.camera.HybridZoomingSystem;
import com.android.camera.NoClipChildrenLayout;
import com.android.camera.R;
import com.android.camera.Util;
import com.android.camera.animation.FolmeUtils;
import com.android.camera.constant.BeautyConstant;
import com.android.camera.data.DataRepository;
import com.android.camera.data.data.ComponentDataItem;
import com.android.camera.data.data.TypeItem;
import com.android.camera.data.data.runing.ComponentRunningShine;
import com.android.camera.fragment.beauty.BaseBeautyFragment;
import com.android.camera.fragment.beauty.BeautyBodyFragment;
import com.android.camera.fragment.beauty.BeautyLevelFragment;
import com.android.camera.fragment.beauty.BeautySettingManager;
import com.android.camera.fragment.beauty.BeautySmoothLevelFragment;
import com.android.camera.fragment.beauty.BokehSmoothLevelFragment;
import com.android.camera.fragment.beauty.IBeautySettingBusiness;
import com.android.camera.fragment.beauty.LiveBeautyFilterFragment;
import com.android.camera.fragment.beauty.LiveBeautyModeFragment;
import com.android.camera.fragment.beauty.MakeupBeautyFragment;
import com.android.camera.fragment.beauty.MakeupParamsFragment;
import com.android.camera.fragment.beauty.MiLiveParamsFragment;
import com.android.camera.fragment.beauty.MiNightParamsFragment;
import com.android.camera.fragment.beauty.RemodelingParamsFragment;
import com.android.camera.fragment.beauty.VideoBokehColorRetentionFragment;
import com.android.camera.fragment.live.FragmentKaleidoscope;
import com.android.camera.fragment.live.FragmentLiveSpeed;
import com.android.camera.fragment.live.FragmentLiveSticker;
import com.android.camera.log.Log;
import com.android.camera.protocol.ModeCoordinatorImpl;
import com.android.camera.protocol.ModeProtocol;
import com.android.camera.statistic.CameraStatUtils;
import com.android.camera.statistic.MistatsConstants;
import com.android.camera.statistic.MistatsWrapper;
import com.android.camera.ui.NoScrollViewPager;
import com.android.camera.ui.SeekBarCompat;
import com.xiaomi.camera.rx.CameraSchedulers;
import io.reactivex.BackpressureStrategy;
import io.reactivex.Completable;
import io.reactivex.Flowable;
import io.reactivex.FlowableEmitter;
import io.reactivex.FlowableOnSubscribe;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

public class FragmentBeauty extends BaseFragment implements ModeProtocol.HandleBackTrace, ModeProtocol.MiBeautyProtocol, ModeProtocol.MakeupProtocol, Consumer<Integer>, View.OnClickListener, ViewPager.OnPageChangeListener {
    public static final int FRAGMENT_INFO = 251;
    private static final String LOG_TAG = "FragmentBeauty";
    private static final int SEEKBAR_PROGRESS_RATIO = 1;
    private int mActiveProgress;
    private SeekBarCompat mAdjustSeekBar;
    private BaseFragmentPagerAdapter mBeautyPagerAdapter;
    private BeautySettingManager mBeautySettingManager;
    private ComponentRunningShine mComponentRunningShine;
    private IBeautySettingBusiness mCurrentSettingBusiness;
    private int mCurrentState = -1;
    private FlowableEmitter<Integer> mFlowableEmitter;
    private boolean mIsEyeLightShow;
    private boolean mIsRTL;
    private View mRootView;
    private Disposable mSeekBarDisposable;
    private int mSeekBarMaxProgress = 100;
    private NoScrollViewPager mViewPager;

    static /* synthetic */ boolean b(View view, MotionEvent motionEvent) {
        return true;
    }

    private void extraEnterAnim() {
        this.mViewPager.setTranslationX(0.0f);
        this.mViewPager.setAlpha(1.0f);
        ViewCompat.animate(this.mViewPager).translationX(-100.0f).alpha(0.0f).setDuration(120).setStartDelay(0).setInterpolator(new AccelerateDecelerateInterpolator()).start();
    }

    private void extraExitAnim() {
        this.mViewPager.setTranslationX(-100.0f);
        this.mViewPager.setAlpha(0.0f);
        ViewCompat.animate(this.mViewPager).translationX(0.0f).alpha(1.0f).setDuration(280).setInterpolator(new AccelerateDecelerateInterpolator()).setStartDelay(120).start();
    }

    private void feedRotation(List<Fragment> list) {
        for (Fragment fragment : list) {
            if (fragment instanceof BaseBeautyFragment) {
                ((BaseBeautyFragment) fragment).setDegree(this.mDegree);
            }
        }
    }

    private boolean hideBeautyLayout(int i, int i2) {
        if (this.mCurrentState == -1) {
            return false;
        }
        if (3 == i && !isHiddenBeautyPanelOnShutter()) {
            return false;
        }
        this.mCurrentState = -1;
        moveAIWatermark(2);
        ComponentRunningShine componentRunningShine = this.mComponentRunningShine;
        if (componentRunningShine != null) {
            componentRunningShine.setTargetShow(false);
        }
        if (this.mRootView == null) {
            return false;
        }
        if (i2 == 1) {
            resetFragment();
        } else if (i2 == 2) {
            ((ModeProtocol.BottomMenuProtocol) ModeCoordinatorImpl.getInstance().getAttachProtocol(197)).onRestoreCameraActionMenu(i);
        } else if (i2 == 3) {
            ((ModeProtocol.BottomMenuProtocol) ModeCoordinatorImpl.getInstance().getAttachProtocol(197)).onRestoreCameraActionMenu(i);
        }
        if (((Camera) getActivity()).getCameraIntentManager().isImageCaptureIntent()) {
            Aa();
        } else {
            FolmeUtils.animateDeparture(this.mRootView, new f(this));
        }
        ModeProtocol.ConfigChanges configChanges = (ModeProtocol.ConfigChanges) ModeCoordinatorImpl.getInstance().getAttachProtocol(164);
        if (configChanges != null) {
            configChanges.onShineDismiss(i);
        }
        return true;
    }

    private void initAdjustSeekBar() {
        if (this.mSeekBarDisposable == null) {
            this.mSeekBarDisposable = Flowable.create(new FlowableOnSubscribe<Integer>() {
                /* class com.android.camera.fragment.FragmentBeauty.AnonymousClass2 */

                @Override // io.reactivex.FlowableOnSubscribe
                public void subscribe(FlowableEmitter<Integer> flowableEmitter) throws Exception {
                    FragmentBeauty.this.mFlowableEmitter = flowableEmitter;
                }
            }, BackpressureStrategy.DROP).observeOn(CameraSchedulers.sCameraSetupScheduler).onBackpressureDrop(new Consumer<Integer>() {
                /* class com.android.camera.fragment.FragmentBeauty.AnonymousClass1 */

                public void accept(@NonNull Integer num) throws Exception {
                    Log.e(Log.VIEW_TAG, "seekbar change too fast :" + num.toString());
                }
            }).subscribe(this);
            this.mAdjustSeekBar.setProgressDrawable(getResources().getDrawable(R.drawable.seekbar_style));
            this.mAdjustSeekBar.setOnSeekBarChangeListener(new SeekBarCompat.OnSeekBarCompatChangeListener() {
                /* class com.android.camera.fragment.FragmentBeauty.AnonymousClass3 */

                @Override // com.android.camera.ui.SeekBarCompat.OnSeekBarCompatChangeListener
                public void onProgressChanged(SeekBar seekBar, int i, boolean z) {
                    ModeProtocol.TopAlert topAlert = (ModeProtocol.TopAlert) ModeCoordinatorImpl.getInstance().getAttachProtocol(172);
                    if (topAlert != null && z) {
                        topAlert.alertUpdateValue(1, String.valueOf((FragmentBeauty.this.mAdjustSeekBar.isCenterTwoWayMode() ? i / 2 : i) / 1));
                    }
                    int i2 = "14".equals(DataRepository.dataItemRunning().getComponentRunningShine().getCurrentType()) ? 0 : 5;
                    if (i == 0 || i == FragmentBeauty.this.mSeekBarMaxProgress || Math.abs(i - FragmentBeauty.this.mActiveProgress) > i2 * 1) {
                        FragmentBeauty.this.mActiveProgress = i;
                        FragmentBeauty.this.mFlowableEmitter.onNext(Integer.valueOf(i / 1));
                    }
                }

                @Override // com.android.camera.ui.SeekBarCompat.OnSeekBarCompatChangeListener
                public void onStartTrackingTouch(SeekBar seekBar) {
                }

                @Override // com.android.camera.ui.SeekBarCompat.OnSeekBarCompatChangeListener
                public void onStopTrackingTouch(SeekBar seekBar) {
                }
            });
            this.mAdjustSeekBar.setOnSeekBarCompatTouchListener(new SeekBarCompat.OnSeekBarCompatTouchListener() {
                /* class com.android.camera.fragment.FragmentBeauty.AnonymousClass4 */

                @Override // com.android.camera.ui.SeekBarCompat.OnSeekBarCompatTouchListener
                public boolean onTouch(View view, MotionEvent motionEvent) {
                    int action = motionEvent.getAction();
                    if (action == 0) {
                        if (!FragmentBeauty.this.mAdjustSeekBar.getTouchRect().contains((int) motionEvent.getX(), (int) motionEvent.getY())) {
                            return true;
                        }
                    } else if (!(action == 1 || action == 2)) {
                        return false;
                    }
                    FragmentBeauty.this.mAdjustSeekBar.setProgress(FragmentBeauty.this.mAdjustSeekBar.getNextProgress(motionEvent.getX()), true);
                    return true;
                }
            });
        }
    }

    private void initShineType() {
        ViewGroup.MarginLayoutParams marginLayoutParams = (ViewGroup.MarginLayoutParams) this.mRootView.getLayoutParams();
        ViewGroup.MarginLayoutParams marginLayoutParams2 = (ViewGroup.MarginLayoutParams) this.mViewPager.getLayoutParams();
        int dimensionPixelSize = getResources().getDimensionPixelSize(R.dimen.beauty_fragment_height);
        int dimensionPixelSize2 = getResources().getDimensionPixelSize(R.dimen.beautycamera_makeup_item_margin);
        if (this.mCurrentMode == 165) {
            int i = dimensionPixelSize2 / 2;
            marginLayoutParams.bottomMargin = Util.getBottomHeight() - i;
            marginLayoutParams2.topMargin = i;
            marginLayoutParams2.bottomMargin = 0;
        } else {
            marginLayoutParams.bottomMargin = Util.getBottomHeight();
            marginLayoutParams2.topMargin = 0;
            marginLayoutParams2.bottomMargin = dimensionPixelSize2;
        }
        marginLayoutParams2.height = dimensionPixelSize;
        if (this.mBeautySettingManager == null) {
            this.mBeautySettingManager = new BeautySettingManager();
        }
        this.mCurrentState = 1;
        moveAIWatermark(1);
        this.mComponentRunningShine = DataRepository.dataItemRunning().getComponentRunningShine();
        this.mComponentRunningShine.setTargetShow(false);
        initAdjustSeekBar();
        String currentType = this.mComponentRunningShine.getCurrentType();
        initShineType(currentType, false);
        initViewPager();
        setViewPagerPageByType(currentType);
    }

    private void initShineType(String str, boolean z) {
        if (!TextUtils.isEmpty(str)) {
            this.mComponentRunningShine.setCurrentType(str);
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
                                c2 = 4;
                                break;
                            }
                            break;
                        case 52:
                            if (str.equals("4")) {
                                c2 = 5;
                                break;
                            }
                            break;
                        case 53:
                            if (str.equals("5")) {
                                c2 = '\b';
                                break;
                            }
                            break;
                        case 54:
                            if (str.equals("6")) {
                                c2 = '\t';
                                break;
                            }
                            break;
                        default:
                            switch (hashCode) {
                                case 1571:
                                    if (str.equals("14")) {
                                        c2 = 11;
                                        break;
                                    }
                                    break;
                                case 1572:
                                    if (str.equals("15")) {
                                        c2 = 6;
                                        break;
                                    }
                                    break;
                                case 1573:
                                    if (str.equals("16")) {
                                        c2 = 3;
                                        break;
                                    }
                                    break;
                                case 1574:
                                    if (str.equals("17")) {
                                        c2 = 7;
                                        break;
                                    }
                                    break;
                            }
                    }
                } else if (str.equals("11")) {
                    c2 = '\n';
                }
            } else if (str.equals("9")) {
                c2 = 0;
            }
            switch (c2) {
                case 0:
                    throw new RuntimeException("not allowed, see onMakeupItemSelected");
                case 1:
                case 2:
                    HashMap hashMap = new HashMap();
                    hashMap.put(MistatsConstants.BaseEvent.OPERATE_STATE, MistatsConstants.BeautyAttr.VALUE_BEAUTY_V1_BOTTOM_TAB);
                    MistatsWrapper.mistatEvent(MistatsConstants.BeautyAttr.KEY_BEAUTY_CLICK, hashMap);
                    this.mCurrentSettingBusiness = null;
                    setAdjustSeekBarVisible(false, true);
                    return;
                case 3:
                    CameraStatUtils.trackKaleidoscopeClick(MistatsConstants.MiLive.VALUE_MI_LIVE_CLICK_KALEIDOSCOPE);
                    this.mCurrentSettingBusiness = null;
                    setAdjustSeekBarVisible(false, true);
                    return;
                case 4:
                case 5:
                case 6:
                case 7:
                    this.mCurrentSettingBusiness = this.mBeautySettingManager.constructAndGetSetting(str, this.mComponentRunningShine.getTypeElementsBeauty());
                    HashMap hashMap2 = new HashMap();
                    hashMap2.put(MistatsConstants.BaseEvent.OPERATE_STATE, MistatsConstants.BeautyAttr.VALUE_BEAUTY_BOTTOM_TAB);
                    MistatsWrapper.mistatEvent(MistatsConstants.BeautyAttr.KEY_BEAUTY_CLICK, hashMap2);
                    return;
                case '\b':
                    this.mCurrentSettingBusiness = this.mBeautySettingManager.constructAndGetSetting(str, this.mComponentRunningShine.getTypeElementsBeauty());
                    HashMap hashMap3 = new HashMap();
                    hashMap3.put(MistatsConstants.BaseEvent.OPERATE_STATE, MistatsConstants.BeautyAttr.VALUE_MAKEUP_BOTTOM_TAB);
                    MistatsWrapper.mistatEvent(MistatsConstants.BeautyAttr.KEY_BEAUTY_CLICK, hashMap3);
                    return;
                case '\t':
                case '\n':
                    this.mCurrentSettingBusiness = this.mBeautySettingManager.constructAndGetSetting(str, this.mComponentRunningShine.getTypeElementsBeauty());
                    return;
                case 11:
                    if (this.mComponentRunningShine.supportVideoBokehColorRetention()) {
                        this.mCurrentSettingBusiness = this.mBeautySettingManager.constructAndGetSetting(str, this.mComponentRunningShine.getTypeElementsBeauty());
                        return;
                    }
                    return;
                default:
                    this.mCurrentSettingBusiness = null;
                    setAdjustSeekBarVisible(false, true);
                    return;
            }
        }
    }

    /* JADX INFO: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARNING: Code restructure failed: missing block: B:49:0x00ca, code lost:
        if (r2.equals("1") != false) goto L_0x00ce;
     */
    private void initViewPager() {
        ArrayList arrayList = new ArrayList();
        Iterator<ComponentDataItem> it = this.mComponentRunningShine.getItems().iterator();
        while (true) {
            char c2 = 0;
            if (it.hasNext()) {
                String str = it.next().mValue;
                int hashCode = str.hashCode();
                switch (hashCode) {
                    case 49:
                        break;
                    case 50:
                        if (str.equals("2")) {
                            c2 = 1;
                            break;
                        }
                        c2 = 65535;
                        break;
                    case 51:
                        if (str.equals("3")) {
                            c2 = 2;
                            break;
                        }
                        c2 = 65535;
                        break;
                    case 52:
                        if (str.equals("4")) {
                            c2 = 3;
                            break;
                        }
                        c2 = 65535;
                        break;
                    case 53:
                        if (str.equals("5")) {
                            c2 = 5;
                            break;
                        }
                        c2 = 65535;
                        break;
                    case 54:
                        if (str.equals("6")) {
                            c2 = 6;
                            break;
                        }
                        c2 = 65535;
                        break;
                    case 55:
                        if (str.equals("7")) {
                            c2 = 7;
                            break;
                        }
                        c2 = 65535;
                        break;
                    default:
                        switch (hashCode) {
                            case 1567:
                                if (str.equals("10")) {
                                    c2 = '\b';
                                    break;
                                }
                                c2 = 65535;
                                break;
                            case 1568:
                                if (str.equals("11")) {
                                    c2 = '\t';
                                    break;
                                }
                                c2 = 65535;
                                break;
                            case 1569:
                                if (str.equals("12")) {
                                    c2 = '\n';
                                    break;
                                }
                                c2 = 65535;
                                break;
                            case 1570:
                                if (str.equals("13")) {
                                    c2 = 11;
                                    break;
                                }
                                c2 = 65535;
                                break;
                            case 1571:
                                if (str.equals("14")) {
                                    c2 = '\f';
                                    break;
                                }
                                c2 = 65535;
                                break;
                            case 1572:
                                if (str.equals("15")) {
                                    c2 = 4;
                                    break;
                                }
                                c2 = 65535;
                                break;
                            case 1573:
                                if (str.equals("16")) {
                                    c2 = '\r';
                                    break;
                                }
                                c2 = 65535;
                                break;
                            case 1574:
                                if (str.equals("17")) {
                                    c2 = 14;
                                    break;
                                }
                                c2 = 65535;
                                break;
                            default:
                                c2 = 65535;
                                break;
                        }
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
                        arrayList.add(new MiLiveParamsFragment());
                        break;
                    case 5:
                        arrayList.add(new MakeupBeautyFragment());
                        break;
                    case 6:
                        arrayList.add(new BeautyBodyFragment());
                        break;
                    case 7:
                        arrayList.add(new FragmentFilter());
                        break;
                    case '\b':
                        arrayList.add(new LiveBeautyFilterFragment());
                        break;
                    case '\t':
                        arrayList.add(new LiveBeautyModeFragment());
                        break;
                    case '\n':
                        arrayList.add(new FragmentLiveSticker());
                        break;
                    case 11:
                        arrayList.add(new FragmentLiveSpeed());
                        break;
                    case '\f':
                        if (!this.mComponentRunningShine.supportVideoBokehColorRetention()) {
                            arrayList.add(new BokehSmoothLevelFragment());
                            break;
                        } else {
                            arrayList.add(new VideoBokehColorRetentionFragment());
                            break;
                        }
                    case '\r':
                        arrayList.add(new FragmentKaleidoscope());
                        break;
                    case 14:
                        arrayList.add(new MiNightParamsFragment());
                        break;
                    default:
                        throw new RuntimeException("unknown beauty type");
                }
            } else {
                feedRotation(arrayList);
                this.mBeautyPagerAdapter = new BaseFragmentPagerAdapter(getChildFragmentManager(), arrayList);
                this.mViewPager.clearOnPageChangeListeners();
                this.mViewPager.addOnPageChangeListener(this);
                this.mViewPager.setAdapter(this.mBeautyPagerAdapter);
                this.mViewPager.setOffscreenPageLimit(2);
                this.mViewPager.setFocusable(false);
                this.mViewPager.setOnTouchListener(e.INSTANCE);
                return;
            }
        }
    }

    private boolean isHiddenBeautyPanelOnShutter() {
        int i = this.mCurrentMode;
        return i == 162 || i == 161 || i == 174 || i == 183 || i == 176 || i == 180 || i == 169;
    }

    private void moveAIWatermark(int i) {
        ModeProtocol.MainContentProtocol mainContentProtocol;
        if (DataRepository.dataItemRunning().getComponentRunningAIWatermark().needMoveUp(this.mCurrentMode) && (mainContentProtocol = (ModeProtocol.MainContentProtocol) ModeCoordinatorImpl.getInstance().getAttachProtocol(166)) != null) {
            mainContentProtocol.moveWatermarkLayout(i, getDistanceForWM());
        }
    }

    /* access modifiers changed from: private */
    /* renamed from: onDismissFinished */
    public void Aa() {
        resetFragment();
        ModeProtocol.CameraAction cameraAction = (ModeProtocol.CameraAction) ModeCoordinatorImpl.getInstance().getAttachProtocol(161);
        if (!isHiddenBeautyPanelOnShutter() || (cameraAction != null && !cameraAction.isDoingAction() && !cameraAction.isRecording())) {
            int i = this.mCurrentMode;
            if ((i != 163 && i != 165) || cameraAction == null || !cameraAction.isRecording()) {
                resetTips();
            } else {
                return;
            }
        }
        View view = this.mRootView;
        if (view != null) {
            FolmeUtils.clean(view);
        }
    }

    private void resetFragment() {
        setAdjustSeekBarVisible(false, false);
        this.mViewPager.setAdapter(null);
        BaseFragmentPagerAdapter baseFragmentPagerAdapter = this.mBeautyPagerAdapter;
        if (baseFragmentPagerAdapter != null) {
            baseFragmentPagerAdapter.recycleFragmentList(getChildFragmentManager());
            this.mBeautyPagerAdapter = null;
        }
    }

    private void resetTips() {
        ModeProtocol.DualController dualController;
        ModeProtocol.BottomPopupTips bottomPopupTips = (ModeProtocol.BottomPopupTips) ModeCoordinatorImpl.getInstance().getAttachProtocol(175);
        if (bottomPopupTips != null) {
            bottomPopupTips.reInitTipImage();
        }
        if (bottomPopupTips != null) {
            bottomPopupTips.updateTipBottomMargin(0, true);
        }
        showZoomTipImage();
        int i = this.mCurrentMode;
        if (i == 163) {
            ModeProtocol.CameraModuleSpecial cameraModuleSpecial = (ModeProtocol.CameraModuleSpecial) ModeCoordinatorImpl.getInstance().getAttachProtocol(195);
            if (cameraModuleSpecial != null) {
                cameraModuleSpecial.showOrHideChip(true);
            }
        } else if (i != 165) {
            if (i == 171 && (dualController = (ModeProtocol.DualController) ModeCoordinatorImpl.getInstance().getAttachProtocol(182)) != null) {
                dualController.showBokehButton();
                return;
            }
            return;
        }
        if (bottomPopupTips != null) {
            bottomPopupTips.updateLyingDirectHint(false, true);
        }
    }

    private void setAdjustSeekBarVisible(boolean z, boolean z2) {
        animateViews(z ? 1 : -1, z2, this.mAdjustSeekBar);
    }

    private void setSeekBarMode(int i, int i2) {
        boolean z;
        if (this.mCurrentSettingBusiness != null) {
            if (i == 1) {
                this.mSeekBarMaxProgress = 100;
                this.mAdjustSeekBar.setProgressDrawable(getResources().getDrawable(R.drawable.seekbar_style));
            } else if (i == 2) {
                this.mSeekBarMaxProgress = 200;
                this.mAdjustSeekBar.setProgressDrawable(getResources().getDrawable(R.drawable.center_seekbar_style));
                i2 = 100;
                z = true;
                this.mAdjustSeekBar.setCenterTwoWayMode(z);
                this.mAdjustSeekBar.setMax(this.mSeekBarMaxProgress);
                this.mAdjustSeekBar.setSeekBarPinProgress(i2);
                this.mAdjustSeekBar.setProgress(this.mCurrentSettingBusiness.getProgressByCurrentItem() * 1, false);
            }
            z = false;
            this.mAdjustSeekBar.setCenterTwoWayMode(z);
            this.mAdjustSeekBar.setMax(this.mSeekBarMaxProgress);
            this.mAdjustSeekBar.setSeekBarPinProgress(i2);
            this.mAdjustSeekBar.setProgress(this.mCurrentSettingBusiness.getProgressByCurrentItem() * 1, false);
        }
    }

    private void setViewPagerPageByType(String str) {
        List<ComponentDataItem> items = this.mComponentRunningShine.getItems();
        for (int i = 0; i < items.size(); i++) {
            if (items.get(i).mValue.equals(str)) {
                BaseFragmentPagerAdapter baseFragmentPagerAdapter = this.mBeautyPagerAdapter;
                if (baseFragmentPagerAdapter != null) {
                    Fragment item = baseFragmentPagerAdapter.getItem(i);
                    if (item instanceof BaseBeautyFragment) {
                        ((BaseBeautyFragment) item).setDegree(this.mDegree);
                    }
                }
                this.mViewPager.setCurrentItem(i, false);
                return;
            }
        }
    }

    private void showHideExtraLayout(boolean z, Fragment fragment, String str) {
        if (z) {
            FragmentUtils.addFragmentWithTag(getFragmentManager(), (int) R.id.beauty_extra, fragment, str);
        } else {
            FragmentUtils.removeFragmentByTag(getFragmentManager(), str);
        }
    }

    private void showZoomTipImage() {
        int i = this.mCurrentMode;
        if (i != 165) {
            if (i != 166) {
                if (!(i == 169 || i == 183)) {
                    switch (i) {
                        case 161:
                        case 162:
                            break;
                        case 163:
                            break;
                        default:
                            switch (i) {
                                case 173:
                                case 175:
                                    break;
                                case 174:
                                    break;
                                default:
                                    return;
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
        if (dualController != null && !CameraSettings.isFrontCamera() && !CameraSettings.isUltraWideConfigOpen(this.mCurrentMode)) {
            if ((!CameraSettings.isUltraPixelOn() || DataRepository.dataItemFeature().bk() || DataRepository.dataItemFeature().ak()) && !CameraSettings.isMacroModeEnabled(this.mCurrentMode) && !CameraSettings.isAIWatermarkOn(this.mCurrentMode)) {
                dualController.showZoomButton();
                ModeProtocol.TopAlert topAlert = (ModeProtocol.TopAlert) ModeCoordinatorImpl.getInstance().getAttachProtocol(172);
                if (topAlert != null) {
                    topAlert.clearAlertStatus();
                }
            }
        }
    }

    public void accept(@NonNull Integer num) throws Exception {
        IBeautySettingBusiness iBeautySettingBusiness = this.mCurrentSettingBusiness;
        if (iBeautySettingBusiness != null) {
            iBeautySettingBusiness.setProgressForCurrentItem(num.intValue());
        }
    }

    @Override // com.android.camera.protocol.ModeProtocol.MiBeautyProtocol
    public void clearBeauty() {
        IBeautySettingBusiness iBeautySettingBusiness = this.mCurrentSettingBusiness;
        if (iBeautySettingBusiness != null) {
            iBeautySettingBusiness.clearBeauty();
        }
    }

    @Override // com.android.camera.protocol.ModeProtocol.MiBeautyProtocol
    public void dismiss(int i) {
        hideBeautyLayout(6, i);
    }

    /* JADX WARNING: Removed duplicated region for block: B:23:0x0052 A[ADDED_TO_REGION] */
    @Override // com.android.camera.protocol.ModeProtocol.MiBeautyProtocol
    public int getDistanceForWM() {
        char c2;
        String currentType = DataRepository.dataItemRunning().getComponentRunningShine().getCurrentType();
        int hashCode = currentType.hashCode();
        if (hashCode != 49) {
            if (hashCode != 50) {
                if (hashCode != 55) {
                    if (hashCode == 1573 && currentType.equals("16")) {
                        c2 = 3;
                        return (c2 != 0 || c2 == 1 || c2 == 2 || c2 == 3) ? getContext().getResources().getDimensionPixelSize(R.dimen.beauty_fragment_height) : getContext().getResources().getDimensionPixelSize(R.dimen.beautycamera_popup_fragment_height) + getContext().getResources().getDimensionPixelSize(R.dimen.beauty_fragment_height);
                    }
                } else if (currentType.equals("7")) {
                    c2 = 0;
                    if (c2 != 0) {
                    }
                }
            } else if (currentType.equals("2")) {
                c2 = 2;
                if (c2 != 0) {
                }
            }
        } else if (currentType.equals("1")) {
            c2 = 1;
            if (c2 != 0) {
            }
        }
        c2 = 65535;
        if (c2 != 0) {
        }
    }

    @Override // com.android.camera.fragment.BaseFragment
    public int getFragmentInto() {
        return 251;
    }

    /* access modifiers changed from: protected */
    @Override // com.android.camera.fragment.BaseFragment
    public int getLayoutResourceId() {
        return R.layout.fragment_beauty;
    }

    @Override // com.android.camera.protocol.ModeProtocol.MiBeautyProtocol
    public List<TypeItem> getSupportedBeautyItems(@ComponentRunningShine.ShineType String str) {
        return this.mBeautySettingManager.constructAndGetSetting(str, this.mComponentRunningShine.getTypeElementsBeauty()).getSupportedTypeArray(str);
    }

    /* access modifiers changed from: protected */
    @Override // com.android.camera.fragment.BaseFragment
    public void initView(View view) {
        this.mRootView = view;
        this.mIsRTL = Util.isLayoutRTL(getContext());
        this.mViewPager = (NoScrollViewPager) view.findViewById(R.id.beauty_viewPager);
        this.mAdjustSeekBar = (SeekBarCompat) view.findViewById(R.id.beauty_adjust_seekbar);
        setAdjustSeekBarVisible(false, false);
        initShineType();
    }

    @Override // com.android.camera.protocol.ModeProtocol.MiBeautyProtocol
    public boolean isBeautyPanelShow() {
        return this.mCurrentState == 1;
    }

    @Override // com.android.camera.protocol.ModeProtocol.MakeupProtocol
    public boolean isSeekBarVisible() {
        SeekBarCompat seekBarCompat = this.mAdjustSeekBar;
        return seekBarCompat != null && seekBarCompat.getVisibility() == 0;
    }

    @Override // com.android.camera.fragment.BaseFragment, com.android.camera.animation.AnimationDelegate.AnimationResource
    public boolean needViewClear() {
        if (CameraSettings.isUltraPixelRearOn()) {
            return true;
        }
        return super.needViewClear();
    }

    @Override // com.android.camera.protocol.ModeProtocol.HandleBackTrace
    public boolean onBackEvent(int i) {
        if (isHidden()) {
            return false;
        }
        int i2 = 3;
        if (i != 3) {
            i2 = i != 4 ? 2 : 1;
        }
        return hideBeautyLayout(i, i2);
    }

    public void onClick(View view) {
    }

    @Override // com.android.camera.protocol.ModeProtocol.MakeupProtocol
    public void onMakeupItemSelected(String str, boolean z) {
        IBeautySettingBusiness iBeautySettingBusiness = this.mCurrentSettingBusiness;
        if (iBeautySettingBusiness != null) {
            iBeautySettingBusiness.setCurrentType(str);
            if (TextUtils.equals(str, "key_video_bokeh_blur_null")) {
                setAdjustSeekBarVisible(false, true);
                return;
            }
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

    @Override // androidx.viewpager.widget.ViewPager.OnPageChangeListener
    public void onPageScrollStateChanged(int i) {
    }

    @Override // androidx.viewpager.widget.ViewPager.OnPageChangeListener
    public void onPageScrolled(int i, float f, int i2) {
    }

    @Override // androidx.viewpager.widget.ViewPager.OnPageChangeListener
    public void onPageSelected(int i) {
        boolean z = false;
        int i2 = -1;
        for (int i3 = 0; i3 < this.mBeautyPagerAdapter.getCount(); i3++) {
            if (this.mBeautyPagerAdapter.getItem(i3) instanceof NoClipChildrenLayout) {
                i2 = i3;
            }
        }
        if (i2 >= 0) {
            NoClipChildrenLayout noClipChildrenLayout = (NoClipChildrenLayout) this.mBeautyPagerAdapter.getItem(i2);
            if (i2 == i) {
                z = true;
            }
            noClipChildrenLayout.setNoClip(z);
        }
    }

    @Override // androidx.fragment.app.Fragment
    public void onPause() {
        super.onPause();
        hideBeautyLayout(5, 2);
    }

    @Override // com.android.camera.protocol.ModeProtocol.MiBeautyProtocol
    public void onStateChanged() {
        IBeautySettingBusiness iBeautySettingBusiness = this.mCurrentSettingBusiness;
        if (iBeautySettingBusiness != null) {
            iBeautySettingBusiness.onStateChanged();
        }
    }

    @Override // com.android.camera.fragment.BaseFragment, androidx.fragment.app.Fragment
    public void onViewCreated(View view, @Nullable Bundle bundle) {
        ModeProtocol.CameraModuleSpecial cameraModuleSpecial;
        super.onViewCreated(view, bundle);
        if (this.mCurrentMode == 163 && (cameraModuleSpecial = (ModeProtocol.CameraModuleSpecial) ModeCoordinatorImpl.getInstance().getAttachProtocol(195)) != null) {
            cameraModuleSpecial.showOrHideChip(false);
        }
        FolmeUtils.animateEntrance(view);
    }

    @Override // com.android.camera.fragment.BaseFragment, com.android.camera.animation.AnimationDelegate.AnimationResource
    public void provideAnimateElement(int i, List<Completable> list, int i2) {
        super.provideAnimateElement(i, list, i2);
        if (this.mCurrentState != -1) {
            if (i2 != 7 || i != 180) {
                onBackEvent(4);
            }
        }
    }

    /* access modifiers changed from: protected */
    @Override // com.android.camera.fragment.BaseFragment
    public Animation provideEnterAnimation(int i) {
        return null;
    }

    /* access modifiers changed from: protected */
    @Override // com.android.camera.fragment.BaseFragment
    public Animation provideExitAnimation(int i) {
        return null;
    }

    @Override // com.android.camera.fragment.BaseFragment, com.android.camera.animation.AnimationDelegate.AnimationResource
    public void provideRotateItem(List<View> list, int i) {
        NoScrollViewPager noScrollViewPager;
        super.provideRotateItem(list, i);
        BaseFragmentPagerAdapter baseFragmentPagerAdapter = this.mBeautyPagerAdapter;
        Fragment item = (baseFragmentPagerAdapter == null || (noScrollViewPager = this.mViewPager) == null) ? null : baseFragmentPagerAdapter.getItem(noScrollViewPager.getCurrentItem());
        if (item != null && (item instanceof BaseBeautyFragment)) {
            ((BaseBeautyFragment) item).provideRotateItem(list, i);
        }
    }

    /* access modifiers changed from: protected */
    @Override // com.android.camera.fragment.BaseFragment
    public void register(ModeProtocol.ModeCoordinator modeCoordinator) {
        super.register(modeCoordinator);
        registerBackStack(modeCoordinator, this);
        modeCoordinator.attachProtocol(194, this);
        modeCoordinator.attachProtocol(180, this);
        this.mIsEyeLightShow = false;
    }

    @Override // com.android.camera.protocol.ModeProtocol.MiBeautyProtocol
    public void resetBeauty() {
        IBeautySettingBusiness iBeautySettingBusiness = this.mCurrentSettingBusiness;
        if (iBeautySettingBusiness != null) {
            iBeautySettingBusiness.resetBeauty();
        }
    }

    @Override // com.android.camera.fragment.BaseFragment, com.android.camera.animation.AnimationDelegate.AnimationResource
    public void setClickEnable(boolean z) {
        List<Fragment> fragmentList;
        super.setClickEnable(z);
        BaseFragmentPagerAdapter baseFragmentPagerAdapter = this.mBeautyPagerAdapter;
        if (!(baseFragmentPagerAdapter == null || (fragmentList = baseFragmentPagerAdapter.getFragmentList()) == null)) {
            for (Fragment fragment : fragmentList) {
                if (fragment instanceof BeautyLevelFragment) {
                    ((BeautyLevelFragment) fragment).setEnableClick(z);
                    return;
                }
            }
        }
    }

    @Override // com.android.camera.protocol.ModeProtocol.MiBeautyProtocol
    public void show() {
        ModeProtocol.CameraModuleSpecial cameraModuleSpecial;
        if (this.mCurrentState != 1) {
            FolmeUtils.clean(this.mRootView);
            if (this.mCurrentMode == 163 && (cameraModuleSpecial = (ModeProtocol.CameraModuleSpecial) ModeCoordinatorImpl.getInstance().getAttachProtocol(195)) != null) {
                cameraModuleSpecial.showOrHideChip(false);
            }
            initShineType();
            FolmeUtils.animateEntrance(this.mRootView);
        }
    }

    @Override // com.android.camera.protocol.ModeProtocol.MiBeautyProtocol
    public void switchShineType(String str, boolean z) {
        initShineType(str, z);
        setViewPagerPageByType(str);
        moveAIWatermark(1);
    }

    /* access modifiers changed from: protected */
    @Override // com.android.camera.fragment.BaseFragment
    public void unRegister(ModeProtocol.ModeCoordinator modeCoordinator) {
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
