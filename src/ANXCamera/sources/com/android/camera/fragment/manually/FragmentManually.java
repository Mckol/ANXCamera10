package com.android.camera.fragment.manually;

import android.content.Context;
import android.graphics.Rect;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Animation;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;
import b.c.a.c;
import com.android.camera.ActivityBase;
import com.android.camera.CameraSettings;
import com.android.camera.R;
import com.android.camera.Util;
import com.android.camera.animation.FolmeUtils;
import com.android.camera.animation.type.AlphaInOnSubscribe;
import com.android.camera.animation.type.AlphaOutOnSubscribe;
import com.android.camera.animation.type.BaseOnSubScribe;
import com.android.camera.animation.type.SlideInOnSubscribe;
import com.android.camera.animation.type.SlideOutOnSubscribe;
import com.android.camera.data.DataRepository;
import com.android.camera.data.data.ComponentData;
import com.android.camera.data.data.config.ComponentManuallyDualLens;
import com.android.camera.data.data.config.ComponentManuallyET;
import com.android.camera.data.data.config.ComponentManuallyEV;
import com.android.camera.data.data.config.ComponentManuallyFocus;
import com.android.camera.data.data.config.ComponentManuallyISO;
import com.android.camera.data.data.config.ComponentManuallyWB;
import com.android.camera.data.data.config.DataItemConfig;
import com.android.camera.fragment.BaseFragment;
import com.android.camera.fragment.FragmentUtils;
import com.android.camera.fragment.beauty.LinearLayoutManagerWrapper;
import com.android.camera.fragment.manually.adapter.ExtraRecyclerViewAdapter;
import com.android.camera.fragment.manually.adapter.ManuallyAdapter;
import com.android.camera.fragment.manually.adapter.ManuallySingleAdapter;
import com.android.camera.lib.compatibility.related.vibrator.ViberatorContext;
import com.android.camera.module.loader.camera2.Camera2DataContainer;
import com.android.camera.protocol.ModeCoordinatorImpl;
import com.android.camera.protocol.ModeProtocol;
import com.android.camera.statistic.MistatsConstants;
import com.android.camera.statistic.MistatsWrapper;
import com.android.camera2.CameraCapabilities;
import io.reactivex.Completable;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class FragmentManually extends BaseFragment implements View.OnClickListener, ModeProtocol.HandleBackTrace, ModeProtocol.ManuallyAdjust, ManuallyListener {
    private RecyclerView.Adapter mAdapter;
    private int mCurrentAdjustType = -1;
    private FragmentManuallyExtra mFragmentManuallyExtra;
    private boolean mIsSuperEISEnabled;
    private boolean mIsVideoRecording;
    private List<ComponentData> mManuallyComponents;
    private ViewGroup mManuallyParent;
    private RecyclerView mRecyclerView;
    private float mRecyclerViewItemWidth;

    public static class ItemPadding extends RecyclerView.ItemDecoration {
        protected int padding;

        public ItemPadding(Context context) {
            this.padding = context.getResources().getDimensionPixelSize(R.dimen.manual_recyclerview_item_padding);
        }

        @Override // androidx.recyclerview.widget.RecyclerView.ItemDecoration
        public void getItemOffsets(Rect rect, View view, RecyclerView recyclerView, RecyclerView.State state) {
            int i = this.padding;
            rect.set(i, 0, i, 0);
        }
    }

    private FragmentManuallyExtra getExtraFragment() {
        FragmentManuallyExtra fragmentManuallyExtra = this.mFragmentManuallyExtra;
        if (fragmentManuallyExtra == null || !fragmentManuallyExtra.isAdded()) {
            return null;
        }
        return this.mFragmentManuallyExtra;
    }

    private void hideTips() {
        ModeProtocol.BottomPopupTips bottomPopupTips = (ModeProtocol.BottomPopupTips) ModeCoordinatorImpl.getInstance().getAttachProtocol(175);
        if (bottomPopupTips != null) {
            bottomPopupTips.directlyHideTips();
        }
    }

    private void initManually() {
        initManuallyDataList();
        ManuallyAdapter manuallyAdapter = new ManuallyAdapter(this.mCurrentMode, this, this.mManuallyComponents, Math.round(this.mRecyclerViewItemWidth));
        FragmentManuallyExtra extraFragment = getExtraFragment();
        if (extraFragment != null) {
            extraFragment.updateData();
            manuallyAdapter.setSelectedTitle(extraFragment.getCurrentTitle());
        }
        this.mAdapter = manuallyAdapter;
        ((ManuallyAdapter) this.mAdapter).setRotate(this.mDegree);
    }

    private List<ComponentData> initManuallyDataList() {
        List<ComponentData> list = this.mManuallyComponents;
        if (list == null) {
            this.mManuallyComponents = new ArrayList();
        } else {
            list.clear();
        }
        DataItemConfig dataItemConfig = DataRepository.dataItemConfig();
        this.mManuallyComponents.add(dataItemConfig.getmComponentManuallyWB());
        if (c.Mn()) {
            ComponentManuallyFocus manuallyFocus = dataItemConfig.getManuallyFocus();
            CameraCapabilities currentCameraCapabilities = Camera2DataContainer.getInstance().getCurrentCameraCapabilities();
            if (currentCameraCapabilities != null) {
                manuallyFocus.setFixedFocusLens(!currentCameraCapabilities.isAFRegionSupported());
            }
            this.mManuallyComponents.add(manuallyFocus);
            this.mManuallyComponents.add(dataItemConfig.getmComponentManuallyET());
        }
        this.mManuallyComponents.add(dataItemConfig.getmComponentManuallyISO());
        this.mManuallyComponents.add(dataItemConfig.getComponentManuallyEV());
        if ((CameraSettings.isSupportedOpticalZoom() || DataRepository.dataItemFeature().isSupportUltraWide()) && !this.mIsVideoRecording) {
            this.mManuallyComponents.add(dataItemConfig.getManuallyDualLens());
        }
        return this.mManuallyComponents;
    }

    private int initRecyclerView(int i, ManuallyListener manuallyListener) {
        this.mCurrentAdjustType = i;
        if (i != 0) {
            if (i == 1) {
                this.mCurrentAdjustType = 1;
                initManually();
            } else if (i == 2) {
                this.mCurrentAdjustType = 2;
                initScene(manuallyListener);
            } else if (i == 3) {
                this.mCurrentAdjustType = 3;
                initTilt(manuallyListener);
            }
            return this.mRecyclerView.getLayoutParams().height;
        }
        this.mCurrentAdjustType = 0;
        this.mManuallyParent.setVisibility(4);
        this.mAdapter = null;
        return 0;
    }

    private void initScene(ManuallyListener manuallyListener) {
        this.mAdapter = new ExtraRecyclerViewAdapter(DataRepository.dataItemRunning().getComponentRunningSceneValue(), this.mCurrentMode, manuallyListener, Math.round(this.mRecyclerViewItemWidth), this.mDegree);
    }

    private void initTilt(ManuallyListener manuallyListener) {
        this.mAdapter = new ManuallySingleAdapter(DataRepository.dataItemRunning().getComponentRunningTiltValue(), this.mCurrentMode, manuallyListener, Math.round(this.mRecyclerViewItemWidth));
    }

    private boolean isReinitNeeded(int i, int i2, int i3) {
        if (this.mCurrentAdjustType != i) {
            return true;
        }
        if (i3 != 167) {
            if (i3 == 180 && i2 == 167) {
                return true;
            }
        } else if (i2 == 180) {
            return true;
        }
        return this.mIsSuperEISEnabled != CameraSettings.isMovieSolidOn();
    }

    private void performFocusValueChangedViberator(String str, String str2) {
        if (!TextUtils.equals(str, str2)) {
            try {
                if (Integer.parseInt(str2) % 100 == 0) {
                    ViberatorContext.getInstance(getContext().getApplicationContext()).performFocusValueLargeChangedInManual();
                } else {
                    ViberatorContext.getInstance(getContext().getApplicationContext()).performFocusValueLightChangedInManual();
                }
            } catch (NumberFormatException e2) {
                e2.printStackTrace();
            }
        }
    }

    private void reInitManuallyLayout(int i, int i2, int i3, List<Completable> list) {
        if (isReinitNeeded(i, i2, i3)) {
            this.mIsSuperEISEnabled = CameraSettings.isMovieSolidOn();
            this.mCurrentAdjustType = i;
            if (i == 0) {
                initRecyclerView(0, this);
            } else if (i == 1) {
                initRecyclerView(1, this);
            }
            if (i == 0 && list != null) {
                if (i2 == 167 || i2 == 180 || this.mManuallyParent.getVisibility() == 0) {
                    FolmeUtils.clean(this.mManuallyParent);
                    FolmeUtils.animateHide(this.mManuallyParent);
                }
            }
        }
    }

    private void removeExtra() {
        if (this.mFragmentManuallyExtra != null) {
            FragmentTransaction beginTransaction = getChildFragmentManager().beginTransaction();
            beginTransaction.remove(this.mFragmentManuallyExtra);
            beginTransaction.commitAllowingStateLoss();
        }
    }

    private void setManuallyLayoutViewVisible(int i) {
        FragmentUtils.removeFragmentByTag(getChildFragmentManager(), String.valueOf(254));
        if (i == 1) {
            Completable.create(new SlideInOnSubscribe(this.mManuallyParent, 80).setDurationTime(280).setInterpolator(new AccelerateDecelerateInterpolator())).subscribe();
        } else if (i == 2) {
            this.mCurrentAdjustType = 0;
            Completable.create(new SlideOutOnSubscribe(this.mManuallyParent, 80).setDurationTime(280).setInterpolator(new AccelerateDecelerateInterpolator())).subscribe();
        } else if (i == 3) {
            this.mCurrentAdjustType = 0;
            SlideOutOnSubscribe.directSetResult(this.mManuallyParent, 80);
        } else if (i == 4) {
            this.mCurrentAdjustType = 0;
            BaseOnSubScribe.setAnimateViewVisible(this.mManuallyParent, 4);
        }
    }

    private void updateEVState(int i) {
        FragmentManuallyExtra fragmentManuallyExtra;
        DataItemConfig dataItemConfig = DataRepository.dataItemConfig();
        ComponentManuallyEV componentManuallyEV = dataItemConfig.getComponentManuallyEV();
        ComponentManuallyET componentManuallyET = dataItemConfig.getmComponentManuallyET();
        ComponentManuallyISO componentManuallyISO = dataItemConfig.getmComponentManuallyISO();
        String componentValue = componentManuallyISO.getComponentValue(i);
        String componentValue2 = componentManuallyET.getComponentValue(i);
        boolean z = true;
        if (Long.parseLong(componentValue2) <= 125000000 && (componentValue2.equals(componentManuallyET.getDefaultValue(i)) || componentValue.equals(componentManuallyISO.getDefaultValue(i)))) {
            z = false;
        }
        componentManuallyEV.setDisabled(z);
        if (z && (fragmentManuallyExtra = this.mFragmentManuallyExtra) != null && fragmentManuallyExtra.getCurrentTitle() == R.string.pref_camera_manually_exposure_value_abbr) {
            removeExtra();
        }
    }

    @Override // com.android.camera.protocol.ModeProtocol.ManuallyAdjust
    public void forceUpdateManualView(boolean z) {
        ViewGroup viewGroup = this.mManuallyParent;
        if (viewGroup == null) {
            return;
        }
        if (z) {
            Completable.create(new AlphaInOnSubscribe(viewGroup)).subscribe();
        } else {
            Completable.create(new AlphaOutOnSubscribe(viewGroup)).subscribe();
        }
    }

    @Override // com.android.camera.fragment.BaseFragment
    public int getFragmentInto() {
        return 247;
    }

    /* access modifiers changed from: protected */
    @Override // com.android.camera.fragment.BaseFragment
    public int getLayoutResourceId() {
        return R.layout.fragment_manually;
    }

    /* access modifiers changed from: protected */
    @Override // com.android.camera.fragment.BaseFragment
    public void initView(View view) {
        ((ViewGroup.MarginLayoutParams) view.getLayoutParams()).bottomMargin = Util.getBottomHeight();
        this.mManuallyParent = (ViewGroup) view.findViewById(R.id.manually_adjust_layout);
        this.mRecyclerView = (RecyclerView) view.findViewById(R.id.manually_recycler_view);
        this.mRecyclerView.setFocusable(false);
        LinearLayoutManagerWrapper linearLayoutManagerWrapper = new LinearLayoutManagerWrapper(getContext(), "manually_recycler_view");
        linearLayoutManagerWrapper.setOrientation(0);
        this.mRecyclerView.setLayoutManager(linearLayoutManagerWrapper);
        this.mRecyclerView.addItemDecoration(new ItemPadding(getContext()));
        this.mRecyclerViewItemWidth = (float) this.mRecyclerView.getLayoutParams().height;
        provideAnimateElement(this.mCurrentMode, null, 2);
    }

    @Override // com.android.camera.fragment.BaseFragment, com.android.camera.animation.AnimationDelegate.AnimationResource
    public void notifyAfterFrameAvailable(int i) {
        super.notifyAfterFrameAvailable(i);
        ModeProtocol.MiBeautyProtocol miBeautyProtocol = (ModeProtocol.MiBeautyProtocol) ModeCoordinatorImpl.getInstance().getAttachProtocol(194);
        boolean z = miBeautyProtocol != null && miBeautyProtocol.isBeautyPanelShow();
        int i2 = this.mCurrentMode;
        if ((i2 == 167 || i2 == 180) && this.mManuallyParent.getVisibility() != 0 && !z) {
            FolmeUtils.clean(this.mManuallyParent);
            FolmeUtils.animateShow(this.mManuallyParent);
        }
    }

    @Override // com.android.camera.fragment.BaseFragment, com.android.camera.animation.AnimationDelegate.AnimationResource
    public void notifyDataChanged(int i, int i2) {
        super.notifyDataChanged(i, i2);
        if (this.mCurrentAdjustType == 1 && this.mAdapter != null) {
            initManuallyDataList();
            this.mRecyclerView.setAdapter(this.mAdapter);
            this.mAdapter.notifyDataSetChanged();
        }
        FragmentManuallyExtra extraFragment = getExtraFragment();
        if (extraFragment != null) {
            extraFragment.notifyDataChanged(i, this.mCurrentMode);
        }
    }

    @Override // com.android.camera.protocol.ModeProtocol.HandleBackTrace
    public boolean onBackEvent(int i) {
        FragmentManuallyExtra fragmentManuallyExtra;
        ViewGroup viewGroup = this.mManuallyParent;
        if ((viewGroup == null || viewGroup.getVisibility() == 0) && i == 2 && (fragmentManuallyExtra = this.mFragmentManuallyExtra) != null && fragmentManuallyExtra.getCurrentTitle() != -1) {
            MistatsWrapper.moduleUIClickEvent("M_manual_", MistatsConstants.Manual.MANUAL_EDIT_TAB_HIDE, (Object) 1);
            this.mFragmentManuallyExtra.animateOut();
            ManuallyAdapter manuallyAdapter = (ManuallyAdapter) this.mRecyclerView.getAdapter();
            if (manuallyAdapter != null) {
                manuallyAdapter.setSelectedTitle(-1);
            }
        }
        return false;
    }

    public void onClick(View view) {
        if (isEnableClick()) {
            view.getId();
            ComponentData componentData = (ComponentData) view.getTag();
            int displayTitleString = componentData.getDisplayTitleString();
            this.mFragmentManuallyExtra = getExtraFragment();
            FragmentManuallyExtra fragmentManuallyExtra = this.mFragmentManuallyExtra;
            if (fragmentManuallyExtra == null) {
                hideTips();
                this.mFragmentManuallyExtra = new FragmentManuallyExtra();
                this.mFragmentManuallyExtra.setDegree(this.mDegree);
                this.mFragmentManuallyExtra.setmFragmentManually(this);
                this.mFragmentManuallyExtra.updateRecordingState(this.mIsVideoRecording);
                this.mFragmentManuallyExtra.setComponentData(componentData, this.mCurrentMode, true, this);
                FragmentManager childFragmentManager = getChildFragmentManager();
                FragmentManuallyExtra fragmentManuallyExtra2 = this.mFragmentManuallyExtra;
                FragmentUtils.addFragmentWithTag(childFragmentManager, (int) R.id.manually_popup, fragmentManuallyExtra2, fragmentManuallyExtra2.getFragmentTag());
                ((ManuallyAdapter) this.mRecyclerView.getAdapter()).setSelectedTitle(displayTitleString);
            } else if (fragmentManuallyExtra.getCurrentTitle() == displayTitleString) {
                this.mFragmentManuallyExtra.animateOut();
                ((ManuallyAdapter) this.mRecyclerView.getAdapter()).setSelectedTitle(-1);
            } else {
                hideTips();
                this.mFragmentManuallyExtra.updateRecordingState(this.mIsVideoRecording);
                this.mFragmentManuallyExtra.resetData(componentData);
                ((ManuallyAdapter) this.mRecyclerView.getAdapter()).setSelectedTitle(displayTitleString);
            }
        }
    }

    @Override // com.android.camera.fragment.BaseFragment, androidx.fragment.app.Fragment
    public Animation onCreateAnimation(int i, boolean z, int i2) {
        return super.onCreateAnimation(i, z, i2);
    }

    @Override // com.android.camera.fragment.manually.ManuallyListener
    public void onManuallyDataChanged(ComponentData componentData, String str, String str2, boolean z, int i) {
        ModeProtocol.ManuallyValueChanged manuallyValueChanged;
        ModeProtocol.TopAlert topAlert;
        if (isEnableClick() && i == this.mCurrentMode && (manuallyValueChanged = (ModeProtocol.ManuallyValueChanged) ModeCoordinatorImpl.getInstance().getAttachProtocol(174)) != null && (topAlert = (ModeProtocol.TopAlert) ModeCoordinatorImpl.getInstance().getAttachProtocol(172)) != null) {
            boolean z2 = true;
            switch (componentData.getDisplayTitleString()) {
                case R.string.pref_camera_iso_title_abbr:
                    if (!str2.equals("0")) {
                        Optional.ofNullable(getExtraFragment()).ifPresent(d.INSTANCE);
                        topAlert.alertUpdateValue(1, getResources().getString(componentData.getValueDisplayString(this.mCurrentMode)));
                    }
                    manuallyValueChanged.onISOValueChanged((ComponentManuallyISO) componentData, str, str2);
                    if (!this.mIsVideoRecording) {
                        ((ActivityBase) getContext()).playCameraSound(6);
                        ViberatorContext.getInstance(getContext().getApplicationContext()).performSlideScaleNormal();
                        break;
                    }
                    break;
                case R.string.pref_camera_manually_exposure_value_abbr:
                    if (!str2.equals(String.valueOf(1000))) {
                        Optional.ofNullable(getExtraFragment()).ifPresent(c.INSTANCE);
                        topAlert.alertUpdateValue(1, componentData.getValueDisplayStringNotFromResource(this.mCurrentMode));
                    }
                    manuallyValueChanged.onEVValueChanged((ComponentManuallyEV) componentData, str2);
                    if (!this.mIsVideoRecording) {
                        ((ActivityBase) getContext()).playCameraSound(6);
                        ViberatorContext.getInstance(getContext().getApplicationContext()).performSlideScaleNormal();
                        break;
                    }
                    break;
                case R.string.pref_camera_whitebalance_title_abbr:
                    if (str2.equals("manual")) {
                        Optional.ofNullable(getExtraFragment()).ifPresent(new e(componentData, c.mo()));
                    }
                    manuallyValueChanged.onWBValueChanged((ComponentManuallyWB) componentData, str2, z);
                    if (z && !this.mIsVideoRecording) {
                        ((ActivityBase) getContext()).playCameraSound(6);
                        ViberatorContext.getInstance(getContext().getApplicationContext()).performSlideScaleNormal();
                    }
                    if (z) {
                        topAlert.alertUpdateValue(1, String.valueOf(CameraSettings.getCustomWB()));
                        break;
                    }
                    break;
                case R.string.pref_camera_zoom_mode_title_abbr:
                    manuallyValueChanged.onDualLensSwitch((ComponentManuallyDualLens) componentData, this.mCurrentMode);
                    z2 = false;
                    break;
                case R.string.pref_manual_exposure_title_abbr:
                    if (!str2.equals("0")) {
                        Optional.ofNullable(getExtraFragment()).ifPresent(f.INSTANCE);
                        topAlert.alertUpdateValue(1, getResources().getString(componentData.getValueDisplayString(this.mCurrentMode)));
                    }
                    manuallyValueChanged.onETValueChanged((ComponentManuallyET) componentData, str, str2);
                    if (!this.mIsVideoRecording) {
                        ((ActivityBase) getContext()).playCameraSound(6);
                        ViberatorContext.getInstance(getContext().getApplicationContext()).performSlideScaleNormal();
                        break;
                    }
                    break;
                case R.string.pref_qc_focus_position_title_abbr:
                    if (!str2.equals(String.valueOf(1000))) {
                        Optional.ofNullable(getExtraFragment()).ifPresent(b.INSTANCE);
                    }
                    manuallyValueChanged.onFocusValueChanged((ComponentManuallyFocus) componentData, str, str2);
                    if (!this.mIsVideoRecording) {
                        performFocusValueChangedViberator(str, str2);
                        break;
                    }
                    break;
            }
            updateEVState(i);
            if (z2) {
                ((ModeProtocol.ConfigChanges) ModeCoordinatorImpl.getInstance().getAttachProtocol(164)).reCheckParameterResetTip(false);
            } else {
                topAlert.refreshExtraMenu();
            }
            if (this.mRecyclerView.getAdapter() != null) {
                this.mRecyclerView.post(new Runnable() {
                    /* class com.android.camera.fragment.manually.FragmentManually.AnonymousClass1 */

                    public void run() {
                        FragmentManually.this.mRecyclerView.getAdapter().notifyDataSetChanged();
                    }
                });
            }
        }
    }

    @Override // com.android.camera.protocol.ModeProtocol.ManuallyAdjust
    public void onRecordingPrepare() {
        this.mIsVideoRecording = true;
        FragmentManuallyExtra fragmentManuallyExtra = this.mFragmentManuallyExtra;
        if (fragmentManuallyExtra != null) {
            fragmentManuallyExtra.updateRecordingState(this.mIsVideoRecording);
            if (R.string.pref_camera_zoom_mode_title_abbr == this.mFragmentManuallyExtra.getCurrentTitle()) {
                this.mFragmentManuallyExtra.animateOut();
                ((ManuallyAdapter) this.mRecyclerView.getAdapter()).setSelectedTitle(-1);
            }
        }
        initRecyclerView(1, this);
        notifyDataChanged(1, 180);
    }

    @Override // com.android.camera.protocol.ModeProtocol.ManuallyAdjust
    public void onRecordingStop() {
        this.mIsVideoRecording = false;
        FragmentManuallyExtra fragmentManuallyExtra = this.mFragmentManuallyExtra;
        if (fragmentManuallyExtra != null) {
            fragmentManuallyExtra.updateRecordingState(this.mIsVideoRecording);
        }
        initRecyclerView(1, this);
        notifyDataChanged(1, 180);
    }

    @Override // com.android.camera.fragment.BaseFragment, com.android.camera.animation.AnimationDelegate.AnimationResource
    public void provideAnimateElement(int i, List<Completable> list, int i2) {
        int i3 = this.mCurrentMode;
        super.provideAnimateElement(i, list, i2);
        int i4 = (i == 167 || i == 180) ? 1 : 0;
        if (i4 != 0) {
            updateEVState(i);
        }
        reInitManuallyLayout(i4, i3, i, list);
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
        super.provideRotateItem(list, i);
        if (this.mRecyclerView != null) {
            for (int i2 = 0; i2 < this.mRecyclerView.getChildCount(); i2++) {
                list.add(this.mRecyclerView.getChildAt(i2));
            }
        }
        RecyclerView.Adapter adapter = this.mAdapter;
        if (adapter != null && (adapter instanceof ManuallyAdapter)) {
            ((ManuallyAdapter) adapter).setRotate(i);
        }
        if (getExtraFragment() != null) {
            getExtraFragment().provideRotateItem(list, i);
        }
    }

    /* access modifiers changed from: protected */
    @Override // com.android.camera.fragment.BaseFragment
    public void register(ModeProtocol.ModeCoordinator modeCoordinator) {
        super.register(modeCoordinator);
        modeCoordinator.attachProtocol(181, this);
        registerBackStack(modeCoordinator, this);
    }

    @Override // com.android.camera.protocol.ModeProtocol.ManuallyAdjust
    public void resetManually() {
        if (!(this.mManuallyComponents == null || this.mAdapter == null)) {
            FragmentManuallyExtra extraFragment = getExtraFragment();
            int currentTitle = extraFragment != null ? extraFragment.getCurrentTitle() : -1;
            ArrayList arrayList = new ArrayList();
            int i = -1;
            for (int i2 = 0; i2 < this.mManuallyComponents.size(); i2++) {
                ComponentData componentData = this.mManuallyComponents.get(i2);
                if (!(componentData instanceof ComponentManuallyDualLens)) {
                    if (componentData.isModified(this.mCurrentMode)) {
                        arrayList.add(componentData);
                    }
                    componentData.reset(this.mCurrentMode);
                    if (componentData.getDisplayTitleString() == currentTitle) {
                        i = i2;
                    }
                }
            }
            this.mAdapter.notifyDataSetChanged();
            if (!(currentTitle == -1 || i == -1)) {
                extraFragment.resetData(this.mManuallyComponents.get(i));
                ((ManuallyAdapter) this.mAdapter).setSelectedTitle(currentTitle);
            }
            ModeProtocol.ManuallyValueChanged manuallyValueChanged = (ModeProtocol.ManuallyValueChanged) ModeCoordinatorImpl.getInstance().getAttachProtocol(174);
            if (manuallyValueChanged != null) {
                manuallyValueChanged.resetManuallyParameters(arrayList);
                updateEVState(this.mCurrentMode);
            }
        }
    }

    @Override // com.android.camera.protocol.ModeProtocol.ManuallyAdjust
    public void setManuallyLayoutVisible(boolean z) {
        if (z) {
            int i = this.mCurrentMode;
            this.mCurrentAdjustType = (i == 167 || i == 180) ? 1 : 0;
            int i2 = this.mCurrentMode;
            if (i2 == 180 || i2 == 167) {
                FolmeUtils.clean(this.mManuallyParent);
                FolmeUtils.animateShow(this.mManuallyParent);
                return;
            }
            this.mManuallyParent.setVisibility(0);
            return;
        }
        this.mCurrentAdjustType = 0;
        int i3 = this.mCurrentMode;
        if (i3 == 180 || i3 == 167) {
            FolmeUtils.clean(this.mManuallyParent);
            this.mManuallyParent.setVisibility(4);
            return;
        }
        this.mManuallyParent.setVisibility(4);
    }

    @Override // com.android.camera.protocol.ModeProtocol.ManuallyAdjust
    public int setManuallyVisible(int i, int i2, ManuallyListener manuallyListener) {
        RecyclerView.Adapter adapter;
        int initRecyclerView = initRecyclerView(i, manuallyListener);
        if (!(i == 0 || (adapter = this.mAdapter) == null)) {
            this.mRecyclerView.setAdapter(adapter);
        }
        setManuallyLayoutViewVisible(i2);
        return initRecyclerView;
    }

    /* access modifiers changed from: protected */
    @Override // com.android.camera.fragment.BaseFragment
    public void unRegister(ModeProtocol.ModeCoordinator modeCoordinator) {
        super.unRegister(modeCoordinator);
        modeCoordinator.detachProtocol(181, this);
        unRegisterBackStack(modeCoordinator, this);
        removeExtra();
    }

    @Override // com.android.camera.protocol.ModeProtocol.ManuallyAdjust
    public int visibleHeight() {
        int i = this.mCurrentAdjustType;
        if (i == 0 || i == -1) {
            return 0;
        }
        return this.mManuallyParent.getHeight() + (getResources().getDimensionPixelSize(R.dimen.tips_margin_bottom_normal) / 2);
    }
}
