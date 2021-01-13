package com.android.camera.fragment.aiwatermark;

import android.text.TextUtils;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import androidx.fragment.app.Fragment;
import com.android.camera.CameraSettings;
import com.android.camera.R;
import com.android.camera.Util;
import com.android.camera.animation.FolmeUtils;
import com.android.camera.animation.type.AlphaOutOnSubscribe;
import com.android.camera.animation.type.SlideInOnSubscribe;
import com.android.camera.data.DataRepository;
import com.android.camera.data.data.ComponentDataItem;
import com.android.camera.data.data.runing.ComponentRunningAIWatermark;
import com.android.camera.fragment.BaseFragment;
import com.android.camera.fragment.BaseFragmentPagerAdapter;
import com.android.camera.fragment.beauty.BaseBeautyFragment;
import com.android.camera.protocol.ModeCoordinatorImpl;
import com.android.camera.protocol.ModeProtocol;
import com.android.camera.statistic.CameraStatUtils;
import com.android.camera.statistic.MistatsConstants;
import com.android.camera.ui.NoScrollViewPager;
import io.reactivex.Completable;
import java.util.ArrayList;
import java.util.List;
import miui.view.animation.QuinticEaseOutInterpolator;

public class FragmentWatermark extends BaseFragment implements ModeProtocol.WatermarkProtocol, ModeProtocol.HandleBackTrace {
    public static final int FRAGMENT_INFO;
    private static final String TAG;
    private ComponentRunningAIWatermark mComponentAIWatermark;
    private int mCurrentState = -1;
    private BaseFragmentPagerAdapter mPagerAdapter;
    private View mRootView = null;
    private NoScrollViewPager mViewPager;

    static /* synthetic */ boolean b(View view, MotionEvent motionEvent) {
        return true;
    }

    private void checkAIWatermark() {
        if (DataRepository.dataItemRunning().getComponentRunningAIWatermark().needActive()) {
            resetFragment();
        }
    }

    private void feedRotation(List<Fragment> list) {
        for (Fragment fragment : list) {
            if (fragment instanceof BaseBeautyFragment) {
                ((BaseBeautyFragment) fragment).setDegree(this.mDegree);
            }
        }
    }

    private void initViewPager() {
        ArrayList arrayList = new ArrayList();
        for (ComponentDataItem componentDataItem : this.mComponentAIWatermark.getItems()) {
            int intValue = Integer.valueOf(componentDataItem.mValue).intValue();
            if (intValue == 0) {
                arrayList.add(new FragmentGenWatermark());
            } else if (intValue == 1) {
                arrayList.add(new FragmentSpotsWatermark());
            } else if (intValue == 2) {
                arrayList.add(new FragmentFestivalWatermark());
            } else if (intValue == 3) {
                arrayList.add(new FragmentASDWatermark());
            } else if (intValue == 4) {
                arrayList.add(new FragmentCityWatermark());
            } else if (intValue == 11) {
                arrayList.add(new FragmentSuperMoonSilhouetteWatermark());
            } else if (intValue == 12) {
                arrayList.add(new FragmentSuperMoonTextWatermark());
            }
        }
        feedRotation(arrayList);
        this.mPagerAdapter = new BaseFragmentPagerAdapter(getChildFragmentManager(), arrayList);
        this.mViewPager.setAdapter(this.mPagerAdapter);
        this.mViewPager.setOffscreenPageLimit(2);
        this.mViewPager.setFocusable(false);
        this.mViewPager.setOnTouchListener(b.INSTANCE);
    }

    private void initWatermarkType() {
        this.mComponentAIWatermark = DataRepository.dataItemRunning().getComponentRunningAIWatermark();
        this.mCurrentState = 1;
        moveAIWatermark(1);
        initViewPager();
        String currentType = this.mComponentAIWatermark.getCurrentType();
        initWatermarkType(currentType, true);
        setViewPagerPageByType(currentType);
    }

    private void initWatermarkType(String str, boolean z) {
        if (!TextUtils.isEmpty(str)) {
            this.mComponentAIWatermark.setCurrentType(str);
        }
    }

    private void moveAIWatermark(int i) {
        ModeProtocol.MainContentProtocol mainContentProtocol;
        if (DataRepository.dataItemRunning().getComponentRunningAIWatermark().needMoveUp(this.mCurrentMode) && (mainContentProtocol = (ModeProtocol.MainContentProtocol) ModeCoordinatorImpl.getInstance().getAttachProtocol(166)) != null) {
            mainContentProtocol.moveWatermarkLayout(i, getDistanceForWM());
        }
    }

    /* access modifiers changed from: public */
    /* access modifiers changed from: private */
    /* renamed from: onDismissFinished */
    public void Ea() {
        resetFragment();
        ModeProtocol.CameraAction cameraAction = (ModeProtocol.CameraAction) ModeCoordinatorImpl.getInstance().getAttachProtocol(161);
        if (cameraAction != null && !cameraAction.isDoingAction() && !cameraAction.isRecording()) {
            resetTips();
        }
    }

    private void resetFragment() {
        this.mViewPager.setAdapter(null);
        BaseFragmentPagerAdapter baseFragmentPagerAdapter = this.mPagerAdapter;
        if (baseFragmentPagerAdapter != null) {
            baseFragmentPagerAdapter.recycleFragmentList(getChildFragmentManager());
            this.mPagerAdapter = null;
        }
        this.mCurrentState = -1;
    }

    private void resetTips() {
        if (this.mCurrentMode == 188) {
            ModeProtocol.DualController dualController = (ModeProtocol.DualController) ModeCoordinatorImpl.getInstance().getAttachProtocol(182);
            if (dualController != null && !CameraSettings.isFrontCamera()) {
                dualController.showZoomButton();
                ModeProtocol.TopAlert topAlert = (ModeProtocol.TopAlert) ModeCoordinatorImpl.getInstance().getAttachProtocol(172);
                if (topAlert != null) {
                    topAlert.clearAlertStatus();
                }
            }
            ModeProtocol.BottomPopupTips bottomPopupTips = (ModeProtocol.BottomPopupTips) ModeCoordinatorImpl.getInstance().getAttachProtocol(175);
            if (bottomPopupTips != null) {
                bottomPopupTips.directShowOrHideLeftTipImage(true);
            }
        }
    }

    private void setViewPagerPageByType(String str) {
        List<ComponentDataItem> items = this.mComponentAIWatermark.getItems();
        for (int i = 0; i < items.size(); i++) {
            if (items.get(i).mValue.equals(str)) {
                this.mViewPager.setCurrentItem(i, false);
                return;
            }
        }
    }

    private void setViewPagerSelect(int i) {
        this.mComponentAIWatermark.getItems();
        this.mViewPager.setCurrentItem(i, false);
    }

    @Override // com.android.camera.protocol.ModeProtocol.WatermarkProtocol
    public boolean dismiss(int i, int i2) {
        ModeProtocol.BottomPopupTips bottomPopupTips;
        View view = getView();
        if (this.mCurrentState == -1 || view == null) {
            return false;
        }
        if (i == 1) {
            resetFragment();
            view.setVisibility(4);
        } else if (i == 2) {
            FolmeUtils.animateDeparture(this.mRootView, new c(this));
            ((ModeProtocol.BottomMenuProtocol) ModeCoordinatorImpl.getInstance().getAttachProtocol(197)).onRestoreCameraActionMenu(i2);
            if (this.mCurrentMode != 188) {
                ((ModeProtocol.BottomPopupTips) ModeCoordinatorImpl.getInstance().getAttachProtocol(175)).directShowOrHideLeftTipImage(true);
                CameraStatUtils.trackAIWatermarkClick(MistatsConstants.AIWatermark.AI_WATERMARK_LIST_HIDE);
            }
        } else if (i == 3) {
            ((ModeProtocol.BottomMenuProtocol) ModeCoordinatorImpl.getInstance().getAttachProtocol(197)).onRestoreCameraActionMenu(i2);
            Completable.create(new AlphaOutOnSubscribe(view).setDurationTime(250).setInterpolator(new DecelerateInterpolator())).subscribe(new a(this));
        }
        if (CameraSettings.isFrontCamera() && (bottomPopupTips = (ModeProtocol.BottomPopupTips) ModeCoordinatorImpl.getInstance().getAttachProtocol(175)) != null) {
            bottomPopupTips.updateTipImage();
        }
        moveAIWatermark(2);
        return true;
    }

    @Override // com.android.camera.protocol.ModeProtocol.WatermarkProtocol
    public int getDistanceForWM() {
        return getContext().getResources().getDimensionPixelSize(R.dimen.wm_item_width) + getContext().getResources().getDimensionPixelSize(R.dimen.beautycamera_makeup_item_margin);
    }

    @Override // com.android.camera.fragment.BaseFragment
    public int getFragmentInto() {
        return 1048574;
    }

    @Override // com.android.camera.fragment.BaseFragment
    public int getLayoutResourceId() {
        return R.layout.fragment_aiwatermark;
    }

    @Override // com.android.camera.fragment.BaseFragment
    public void initView(View view) {
        this.mRootView = view;
        Util.alignPopupBottom(view);
        this.mViewPager = (NoScrollViewPager) view.findViewById(R.id.water_viewPager);
        initWatermarkType();
    }

    @Override // com.android.camera.protocol.ModeProtocol.WatermarkProtocol
    public boolean isWatermarkPanelShow() {
        return this.mCurrentState == 1;
    }

    @Override // com.android.camera.protocol.ModeProtocol.HandleBackTrace
    public boolean onBackEvent(int i) {
        if (i == 3) {
            return false;
        }
        return dismiss(i != 4 ? 2 : 1, i);
    }

    @Override // androidx.fragment.app.Fragment
    public void onPause() {
        super.onPause();
        if (this.mCurrentMode == 205) {
            onBackEvent(4);
        }
    }

    @Override // com.android.camera.fragment.BaseFragment, androidx.fragment.app.Fragment
    public void onResume() {
        ModeProtocol.BottomPopupTips bottomPopupTips;
        super.onResume();
        if (this.mCurrentMode == 205 && (bottomPopupTips = (ModeProtocol.BottomPopupTips) ModeCoordinatorImpl.getInstance().getAttachProtocol(175)) != null) {
            bottomPopupTips.directShowOrHideLeftTipImage(!isWatermarkPanelShow());
        }
    }

    @Override // com.android.camera.fragment.BaseFragment, com.android.camera.animation.AnimationDelegate.AnimationResource
    public void provideAnimateElement(int i, List<Completable> list, int i2) {
        super.provideAnimateElement(i, list, i2);
        if (this.mCurrentState != -1) {
            if (i2 == 5) {
                checkAIWatermark();
            }
            onBackEvent(4);
        }
    }

    @Override // com.android.camera.fragment.BaseFragment, com.android.camera.animation.AnimationDelegate.AnimationResource
    public void provideRotateItem(List<View> list, int i) {
        super.provideRotateItem(list, i);
        if (!(this.mPagerAdapter == null || this.mViewPager == null)) {
            for (int i2 = 0; i2 < this.mPagerAdapter.getCount(); i2++) {
                Fragment item = this.mPagerAdapter.getItem(i2);
                if (item != null && (item instanceof BaseBeautyFragment)) {
                    ((BaseBeautyFragment) item).provideRotateItem(list, i);
                }
            }
        }
    }

    @Override // com.android.camera.fragment.BaseFragment
    public void register(ModeProtocol.ModeCoordinator modeCoordinator) {
        super.register(modeCoordinator);
        registerBackStack(modeCoordinator, this);
        modeCoordinator.attachProtocol(253, this);
    }

    @Override // com.android.camera.protocol.ModeProtocol.WatermarkProtocol
    public void show() {
        if (this.mCurrentState != 1) {
            initWatermarkType();
            Completable.create(new SlideInOnSubscribe(getView(), 80).setDurationTime(280).setInterpolator(new QuinticEaseOutInterpolator())).subscribe();
        }
    }

    @Override // com.android.camera.protocol.ModeProtocol.WatermarkProtocol
    public void switchType(String str, boolean z) {
        initWatermarkType(str, z);
        setViewPagerPageByType(str);
    }

    @Override // com.android.camera.fragment.BaseFragment
    public void unRegister(ModeProtocol.ModeCoordinator modeCoordinator) {
        super.unRegister(modeCoordinator);
        unRegisterBackStack(modeCoordinator, this);
        modeCoordinator.detachProtocol(253, this);
    }
}
