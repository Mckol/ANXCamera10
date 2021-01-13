package com.android.camera.fragment.fastmotion;

import android.text.TextUtils;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import androidx.fragment.app.Fragment;
import com.android.camera.ActivityBase;
import com.android.camera.CameraSettings;
import com.android.camera.HybridZoomingSystem;
import com.android.camera.R;
import com.android.camera.Util;
import com.android.camera.animation.FolmeUtils;
import com.android.camera.constant.FastMotionConstant;
import com.android.camera.data.DataRepository;
import com.android.camera.data.data.ComponentData;
import com.android.camera.data.data.ComponentDataItem;
import com.android.camera.data.data.runing.ComponentRunningFastMotion;
import com.android.camera.fragment.BaseFragment;
import com.android.camera.fragment.BaseFragmentPagerAdapter;
import com.android.camera.fragment.manually.ManuallyListener;
import com.android.camera.lib.compatibility.related.vibrator.ViberatorContext;
import com.android.camera.protocol.ModeCoordinatorImpl;
import com.android.camera.protocol.ModeProtocol;
import com.android.camera.ui.NoScrollViewPager;
import io.reactivex.Completable;
import java.util.ArrayList;
import java.util.List;

public class FragmentFastMotion extends BaseFragment implements ModeProtocol.FastMotionProtocol, ModeProtocol.HandleBackTrace, ManuallyListener {
    public static final int FRAGMENT_INFO = 16777201;
    private static final String TAG = "FragmentFastMotion";
    private ComponentRunningFastMotion mComponentFastMotion;
    private int mCurrentState = -1;
    private BaseFragmentPagerAdapter mPagerAdapter;
    private View mRootView;
    private NoScrollViewPager mViewPager;

    static /* synthetic */ boolean b(View view, MotionEvent motionEvent) {
        return true;
    }

    /* JADX INFO: Can't fix incorrect switch cases order, some code will duplicate */
    private String getExtraSpeedStr(String str) {
        char c2;
        switch (str.hashCode()) {
            case 48687:
                if (str.equals("120")) {
                    c2 = 0;
                    break;
                }
                c2 = 65535;
                break;
            case 50547:
                if (str.equals(FastMotionConstant.FAST_MOTION_SPEED_10X)) {
                    c2 = 1;
                    break;
                }
                c2 = 65535;
                break;
            case 52469:
                if (str.equals("500")) {
                    c2 = 2;
                    break;
                }
                c2 = 65535;
                break;
            case 1507423:
                if (str.equals(FastMotionConstant.FAST_MOTION_SPEED_30X)) {
                    c2 = 3;
                    break;
                }
                c2 = 65535;
                break;
            case 1537214:
                if (str.equals(FastMotionConstant.FAST_MOTION_SPEED_60X)) {
                    c2 = 4;
                    break;
                }
                c2 = 65535;
                break;
            case 1567005:
                if (str.equals(FastMotionConstant.FAST_MOTION_SPEED_90X)) {
                    c2 = 5;
                    break;
                }
                c2 = 65535;
                break;
            case 1596796:
                if (str.equals(FastMotionConstant.FAST_MOTION_SPEED_120X)) {
                    c2 = 6;
                    break;
                }
                c2 = 65535;
                break;
            case 1626587:
                if (str.equals(FastMotionConstant.FAST_MOTION_SPEED_150X)) {
                    c2 = 7;
                    break;
                }
                c2 = 65535;
                break;
            case 46730161:
                if (str.equals(FastMotionConstant.FAST_MOTION_SPEED_300X)) {
                    c2 = '\b';
                    break;
                }
                c2 = 65535;
                break;
            case 46879116:
                if (str.equals(FastMotionConstant.FAST_MOTION_SPEED_450X)) {
                    c2 = '\t';
                    break;
                }
                c2 = 65535;
                break;
            case 47653682:
                if (str.equals(FastMotionConstant.FAST_MOTION_SPEED_600X)) {
                    c2 = '\n';
                    break;
                }
                c2 = 65535;
                break;
            case 48577203:
                if (str.equals(FastMotionConstant.FAST_MOTION_SPEED_900X)) {
                    c2 = 11;
                    break;
                }
                c2 = 65535;
                break;
            case 51347766:
                if (str.equals(FastMotionConstant.FAST_MOTION_SPEED_1800X)) {
                    c2 = '\f';
                    break;
                }
                c2 = 65535;
                break;
            default:
                c2 = 65535;
                break;
        }
        switch (c2) {
            case 0:
            case 1:
            case 2:
            case 3:
                return getResources().getString(R.string.pref_camera_fastmotion_speed_30x);
            case 4:
            case 5:
                return getResources().getString(R.string.pref_camera_fastmotion_speed_90x);
            case 6:
            case 7:
                return getResources().getString(R.string.pref_camera_fastmotion_speed_150x);
            case '\b':
            case '\t':
            case '\n':
                return getResources().getString(R.string.pref_camera_fastmotion_speed_750x);
            case 11:
            case '\f':
                return getResources().getString(R.string.pref_camera_fastmotion_speed_1800x);
            default:
                return "";
        }
    }

    private void initFastMotionType() {
        this.mComponentFastMotion = DataRepository.dataItemRunning().getComponentRunningFastMotion();
        this.mCurrentState = 1;
        initViewPager();
        String currentType = this.mComponentFastMotion.getCurrentType();
        initFastMotionType(currentType, true);
        setViewPagerPageByType(currentType);
        ModeProtocol.ConfigChanges configChanges = (ModeProtocol.ConfigChanges) ModeCoordinatorImpl.getInstance().getAttachProtocol(164);
        if (configChanges != null) {
            configChanges.reCheckFastMotion(true);
        }
    }

    private void initFastMotionType(String str, boolean z) {
        if (!TextUtils.isEmpty(str)) {
            this.mComponentFastMotion.setCurrentType(str);
        }
    }

    private void initViewPager() {
        ArrayList arrayList = new ArrayList();
        for (ComponentDataItem componentDataItem : this.mComponentFastMotion.getItems()) {
            int intValue = Integer.valueOf(componentDataItem.mValue).intValue();
            if (intValue == 1) {
                FragmentFastMotionExtra fragmentFastMotionExtra = new FragmentFastMotionExtra();
                fragmentFastMotionExtra.setDegree(this.mDegree);
                fragmentFastMotionExtra.setComponentData(DataRepository.dataItemRunning().getComponentRunningFastMotionSpeed(), this.mCurrentMode, false, this);
                arrayList.add(fragmentFastMotionExtra);
            } else if (intValue == 2) {
                FragmentFastMotionExtra fragmentFastMotionExtra2 = new FragmentFastMotionExtra();
                fragmentFastMotionExtra2.setDegree(this.mDegree);
                fragmentFastMotionExtra2.setComponentData(DataRepository.dataItemRunning().getComponentRunningFastMotionDuration(), this.mCurrentMode, false, this);
                arrayList.add(fragmentFastMotionExtra2);
            }
        }
        this.mPagerAdapter = new BaseFragmentPagerAdapter(getChildFragmentManager(), arrayList);
        this.mViewPager.setAdapter(this.mPagerAdapter);
        this.mViewPager.setFocusable(false);
        this.mViewPager.setOnTouchListener(b.INSTANCE);
    }

    /* access modifiers changed from: private */
    /* renamed from: onDismissFinished */
    public void o(int i) {
        resetFragment();
        ModeProtocol.CameraAction cameraAction = (ModeProtocol.CameraAction) ModeCoordinatorImpl.getInstance().getAttachProtocol(161);
        if (cameraAction != null && !cameraAction.isDoingAction() && !cameraAction.isRecording()) {
            resetTips();
        }
        ModeProtocol.ConfigChanges configChanges = (ModeProtocol.ConfigChanges) ModeCoordinatorImpl.getInstance().getAttachProtocol(164);
        if (configChanges == null) {
            return;
        }
        if (i == 2) {
            configChanges.reCheckFastMotion(false);
            ((ModeProtocol.TopAlert) ModeCoordinatorImpl.getInstance().getAttachProtocol(172)).clearFastmotionValue();
            return;
        }
        configChanges.reCheckFastMotion(true);
    }

    private void resetFragment() {
        this.mViewPager.setAdapter(null);
        BaseFragmentPagerAdapter baseFragmentPagerAdapter = this.mPagerAdapter;
        if (baseFragmentPagerAdapter != null) {
            baseFragmentPagerAdapter.recycleFragmentList(getChildFragmentManager());
            this.mPagerAdapter = null;
        }
    }

    private void resetTips() {
        ModeProtocol.DualController dualController = (ModeProtocol.DualController) ModeCoordinatorImpl.getInstance().getAttachProtocol(182);
        if (dualController != null && HybridZoomingSystem.IS_3_OR_MORE_SAT && !CameraSettings.isFrontCamera()) {
            dualController.showZoomButton();
        }
        ModeProtocol.BottomPopupTips bottomPopupTips = (ModeProtocol.BottomPopupTips) ModeCoordinatorImpl.getInstance().getAttachProtocol(175);
        if (bottomPopupTips != null) {
            bottomPopupTips.reInitTipImage();
        }
    }

    private void setViewPagerPageByType(String str) {
        List<ComponentDataItem> items = this.mComponentFastMotion.getItems();
        for (int i = 0; i < items.size(); i++) {
            if (items.get(i).mValue.equals(str)) {
                this.mViewPager.setCurrentItem(i, false);
                return;
            }
        }
    }

    @Override // com.android.camera.protocol.ModeProtocol.FastMotionProtocol
    public boolean dismiss(int i, int i2) {
        View view = getView();
        if (this.mCurrentState == -1 || view == null) {
            return false;
        }
        this.mCurrentState = -1;
        if (i == 2) {
            ((ModeProtocol.BottomMenuProtocol) ModeCoordinatorImpl.getInstance().getAttachProtocol(197)).onRestoreCameraActionMenu(i2);
        } else if (i == 3) {
            ((ModeProtocol.BottomMenuProtocol) ModeCoordinatorImpl.getInstance().getAttachProtocol(197)).onRestoreCameraActionMenu(i2);
        }
        FolmeUtils.animateDeparture(this.mRootView, new a(this, i2));
        return true;
    }

    @Override // com.android.camera.fragment.BaseFragment
    public int getFragmentInto() {
        return 16777201;
    }

    /* access modifiers changed from: protected */
    @Override // com.android.camera.fragment.BaseFragment
    public int getLayoutResourceId() {
        return R.layout.fragment_fastmotion;
    }

    /* access modifiers changed from: protected */
    @Override // com.android.camera.fragment.BaseFragment
    public void initView(View view) {
        this.mRootView = view;
        ((ViewGroup.MarginLayoutParams) view.getLayoutParams()).bottomMargin = Util.getBottomHeight() - getResources().getDimensionPixelSize(R.dimen.fastmotion_fragment_layout_margin_compensation);
        this.mViewPager = (NoScrollViewPager) view.findViewById(R.id.fast_motion_viewPager);
        initFastMotionType();
    }

    @Override // com.android.camera.protocol.ModeProtocol.FastMotionProtocol
    public boolean isShowing() {
        return this.mCurrentState == 1;
    }

    @Override // com.android.camera.protocol.ModeProtocol.HandleBackTrace
    public boolean onBackEvent(int i) {
        int i2 = 3;
        if (i != 3) {
            i2 = i != 4 ? 2 : 1;
        }
        this.mComponentFastMotion.setClosed(true);
        return dismiss(i2, i);
    }

    @Override // com.android.camera.fragment.BaseFragment, androidx.fragment.app.Fragment
    public void onDestroyView() {
        super.onDestroyView();
        ComponentRunningFastMotion componentRunningFastMotion = this.mComponentFastMotion;
        if (componentRunningFastMotion != null) {
            componentRunningFastMotion.setClosed(true);
        }
    }

    @Override // com.android.camera.fragment.manually.ManuallyListener
    public void onManuallyDataChanged(ComponentData componentData, String str, String str2, boolean z, int i) {
        ModeProtocol.TopAlert topAlert;
        if (isEnableClick() && i == this.mCurrentMode && (topAlert = (ModeProtocol.TopAlert) ModeCoordinatorImpl.getInstance().getAttachProtocol(172)) != null) {
            int displayTitleString = componentData.getDisplayTitleString();
            if (displayTitleString != R.string.pref_camera_fastmotion_duration) {
                if (displayTitleString == R.string.pref_camera_fastmotion_speed) {
                    topAlert.alertFastmotionValue(componentData.getValueDisplayStringNotFromResource(this.mCurrentMode), getExtraSpeedStr(str2));
                }
            } else if (Util.isLocaleChinese() || "0".equals(str2)) {
                topAlert.alertFastmotionValue("0".equals(str2) ? getString(R.string.pref_camera_fastmotion_duration_infinity) : getResources().getQuantityString(R.plurals.pref_camera_fastmotion_duration_unit, Integer.parseInt(str2), str2), "");
            } else {
                topAlert.alertFastmotionValue(str2, getResources().getQuantityString(R.plurals.pref_camera_fastmotion_duration_unit, 10, ""));
            }
            ((ActivityBase) getContext()).playCameraSound(6);
            ViberatorContext.getInstance(getContext().getApplicationContext()).performSlideScaleNormal();
        }
    }

    @Override // com.android.camera.fragment.BaseFragment, com.android.camera.animation.AnimationDelegate.AnimationResource
    public void provideAnimateElement(int i, List<Completable> list, int i2) {
        super.provideAnimateElement(i, list, i2);
        if (this.mCurrentState != -1) {
            onBackEvent(4);
        }
    }

    @Override // com.android.camera.fragment.BaseFragment, com.android.camera.animation.AnimationDelegate.AnimationResource
    public void provideRotateItem(List<View> list, int i) {
        super.provideRotateItem(list, i);
        BaseFragmentPagerAdapter baseFragmentPagerAdapter = this.mPagerAdapter;
        if (!(baseFragmentPagerAdapter == null || baseFragmentPagerAdapter.getFragmentList() == null)) {
            List<Fragment> fragmentList = this.mPagerAdapter.getFragmentList();
            for (int i2 = 0; i2 < fragmentList.size(); i2++) {
                ((BaseFragment) fragmentList.get(i2)).provideRotateItem(null, i);
            }
        }
    }

    /* access modifiers changed from: protected */
    @Override // com.android.camera.fragment.BaseFragment
    public void register(ModeProtocol.ModeCoordinator modeCoordinator) {
        super.register(modeCoordinator);
        registerBackStack(modeCoordinator, this);
        modeCoordinator.attachProtocol(674, this);
    }

    @Override // com.android.camera.protocol.ModeProtocol.FastMotionProtocol
    public void show() {
        if (this.mCurrentState != 1) {
            initFastMotionType();
            FolmeUtils.animateEntrance(this.mRootView);
        }
    }

    @Override // com.android.camera.protocol.ModeProtocol.FastMotionProtocol
    public void switchType(String str, boolean z) {
        initFastMotionType(str, z);
        setViewPagerPageByType(str);
    }

    /* access modifiers changed from: protected */
    @Override // com.android.camera.fragment.BaseFragment
    public void unRegister(ModeProtocol.ModeCoordinator modeCoordinator) {
        super.unRegister(modeCoordinator);
        unRegisterBackStack(modeCoordinator, this);
        modeCoordinator.detachProtocol(674, this);
    }
}
