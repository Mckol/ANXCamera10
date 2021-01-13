package com.android.camera.fragment.manually;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.graphics.Rect;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import androidx.annotation.Nullable;
import androidx.core.view.ViewCompat;
import androidx.recyclerview.widget.RecyclerView;
import b.c.a.c;
import com.android.camera.R;
import com.android.camera.animation.FolmeUtils;
import com.android.camera.customization.TintColor;
import com.android.camera.data.DataRepository;
import com.android.camera.data.data.ComponentData;
import com.android.camera.data.data.config.ComponentManuallyET;
import com.android.camera.data.data.config.ComponentManuallyFocus;
import com.android.camera.data.data.config.ComponentManuallyISO;
import com.android.camera.data.data.config.ComponentManuallyWB;
import com.android.camera.fragment.BaseFragment;
import com.android.camera.fragment.FragmentUtils;
import com.android.camera.fragment.beauty.LinearLayoutManagerWrapper;
import com.android.camera.fragment.manually.adapter.ExtraCustomWBListAdapter;
import com.android.camera.fragment.manually.adapter.ExtraFocusAdapter;
import com.android.camera.fragment.manually.adapter.ExtraHorizontalListAdapter;
import com.android.camera.fragment.manually.adapter.ExtraRecyclerViewAdapter;
import com.android.camera.ui.ColorImageView;
import com.android.camera.ui.HorizontalZoomView;
import io.reactivex.Completable;
import java.util.ArrayList;
import java.util.List;
import miui.view.animation.CubicEaseOutInterpolator;

public class FragmentManuallyExtra extends BaseFragment implements View.OnClickListener {
    public static final int FRAGMENT_INFO = 254;
    private ColorImageView mAutoButton;
    private int mCurrentTitle = -1;
    private ComponentData mData;
    private RecyclerView mExtraList;
    private FragmentManually mFragmentManually;
    private HorizontalZoomView mHorizontalView;
    private RelativeLayout mHorizontalViewLayout;
    private boolean mIsAnimateIng;
    private boolean mIsRecording;
    private ManuallyListener mManuallyListener;
    private boolean mNeedAnimation;
    private int mTargetKey;
    private View mTargetView;

    public static class ItemPadding extends RecyclerView.ItemDecoration {
        protected int mPadding;

        public ItemPadding(int i) {
            this.mPadding = i;
        }

        @Override // androidx.recyclerview.widget.RecyclerView.ItemDecoration
        public void getItemOffsets(Rect rect, View view, RecyclerView recyclerView, RecyclerView.State state) {
            int i = this.mPadding;
            rect.set(i, 0, i, 0);
        }
    }

    private void animateParentInOrOut(View view, boolean z, Runnable runnable) {
        if (z) {
            FolmeUtils.animateEntrance(view);
        } else {
            FolmeUtils.animateDeparture(view, runnable);
        }
    }

    private void animateShowView(View view) {
        view.setVisibility(0);
        if (!this.mNeedAnimation) {
            FolmeUtils.animateShow(view);
        }
    }

    private void hideView() {
        View view = this.mTargetView;
        if (view != null) {
            view.clearAnimation();
            FolmeUtils.clean(this.mTargetView);
            this.mTargetView.setVisibility(8);
        }
    }

    private void initAdapter(ComponentData componentData) {
        switch (componentData.getDisplayTitleString()) {
            case R.string.pref_camera_iso_title_abbr:
            case R.string.pref_camera_manually_exposure_value_abbr:
            case R.string.pref_manual_exposure_title_abbr:
                initHorizontalListView(componentData, c.mo());
                return;
            case R.string.pref_camera_whitebalance_title_abbr:
                initWBRecyclerView(componentData);
                return;
            case R.string.pref_camera_zoom_mode_title_abbr:
                initLensRecyclerView(componentData);
                return;
            case R.string.pref_qc_focus_position_title_abbr:
                initSlideFocusView(componentData);
                return;
            default:
                return;
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:5:0x003a, code lost:
        if (r7 != com.android.camera.R.string.pref_manual_exposure_title_abbr) goto L_0x00ba;
     */
    private void initHorizontalListView(ComponentData componentData, boolean z) {
        hideView();
        this.mTargetView = this.mHorizontalViewLayout;
        this.mTargetKey = componentData.getDisplayTitleString();
        int displayTitleString = componentData.getDisplayTitleString();
        String componentValue = componentData.getComponentValue(this.mCurrentMode);
        ExtraHorizontalListAdapter extraHorizontalListAdapter = new ExtraHorizontalListAdapter(getContext(), componentData, this.mCurrentMode, this.mManuallyListener);
        this.mHorizontalView.setDrawAdapter(extraHorizontalListAdapter, this.mDegree, false);
        if (displayTitleString != R.string.pref_camera_iso_title_abbr) {
            if (displayTitleString == R.string.pref_camera_manually_exposure_value_abbr) {
                ((ViewGroup.MarginLayoutParams) this.mHorizontalView.getLayoutParams()).setMarginStart(0);
                this.mAutoButton.setVisibility(8);
                this.mHorizontalView.setSelection((int) extraHorizontalListAdapter.mapValueToPosition(componentValue), true);
            }
            this.mHorizontalView.setListener(extraHorizontalListAdapter, null);
            animateShowView(this.mHorizontalViewLayout);
        }
        ((ViewGroup.MarginLayoutParams) this.mHorizontalView.getLayoutParams()).setMarginStart(getResources().getDimensionPixelSize(R.dimen.manual_extra_horizontal_view_left));
        this.mAutoButton.setVisibility(0);
        this.mAutoButton.setRotation((float) this.mDegree);
        this.mAutoButton.setImageResource(R.drawable.ic_manu_auto_normal);
        this.mAutoButton.setBackgroundResource(R.drawable.ic_manu_auto_normal_shadow);
        if (componentValue.equals("0")) {
            this.mAutoButton.setColorAndRefresh(TintColor.tintColor());
            this.mHorizontalView.setSelection(-1, true);
        } else {
            this.mAutoButton.setColor(0);
            this.mAutoButton.setIsNeedTransparent(true, true);
            this.mHorizontalView.setSelection((int) extraHorizontalListAdapter.mapValueToPosition(componentValue), true);
        }
        this.mHorizontalView.setListener(extraHorizontalListAdapter, null);
        animateShowView(this.mHorizontalViewLayout);
    }

    private void initLensRecyclerView(ComponentData componentData) {
        hideView();
        RecyclerView recyclerView = this.mExtraList;
        this.mTargetView = recyclerView;
        ViewGroup.MarginLayoutParams marginLayoutParams = (ViewGroup.MarginLayoutParams) recyclerView.getLayoutParams();
        marginLayoutParams.bottomMargin = getResources().getDimensionPixelSize(R.dimen.manual_extra_recyclerview_lens_margin_bottom);
        marginLayoutParams.height = getResources().getDimensionPixelSize(R.dimen.manual_extra_recyclerview_lens_height);
        AnonymousClass2 r1 = new ExtraRecyclerViewAdapter(this.mFragmentManually, componentData, this.mCurrentMode, this.mManuallyListener, marginLayoutParams.height, this.mDegree) {
            /* class com.android.camera.fragment.manually.FragmentManuallyExtra.AnonymousClass2 */

            /* access modifiers changed from: protected */
            @Override // com.android.camera.fragment.manually.adapter.ExtraRecyclerViewAdapter
            public boolean couldNewValueTakeEffect(String str) {
                if (str == null || !str.equals("manual")) {
                    return super.couldNewValueTakeEffect(str);
                }
                return true;
            }
        };
        LinearLayoutManagerWrapper linearLayoutManagerWrapper = new LinearLayoutManagerWrapper(getContext(), "manually_extra_list");
        linearLayoutManagerWrapper.setOrientation(0);
        this.mExtraList.setLayoutManager(linearLayoutManagerWrapper);
        if (this.mExtraList.getItemDecorationCount() != 0) {
            this.mExtraList.removeItemDecorationAt(0);
        }
        this.mExtraList.addItemDecoration(new ItemPadding(getResources().getDimensionPixelSize(R.dimen.manual_extra_recyclerview_lens_item_padding)));
        this.mExtraList.setAdapter(r1);
        animateShowView(this.mExtraList);
    }

    private void initSlideFocusView(ComponentData componentData) {
        hideView();
        this.mTargetView = this.mHorizontalViewLayout;
        this.mTargetKey = componentData.getDisplayTitleString();
        ExtraFocusAdapter extraFocusAdapter = new ExtraFocusAdapter(getContext(), componentData, this.mCurrentMode, this.mManuallyListener);
        this.mHorizontalView.setDrawAdapter(extraFocusAdapter, this.mDegree, false);
        int intValue = Integer.valueOf(componentData.getComponentValue(this.mCurrentMode)).intValue();
        this.mAutoButton.setImageResource(R.drawable.ic_manu_auto_normal);
        this.mAutoButton.setBackgroundResource(R.drawable.ic_manu_auto_normal_shadow);
        this.mAutoButton.setVisibility(0);
        this.mAutoButton.setRotation((float) this.mDegree);
        if (intValue == 1000) {
            this.mAutoButton.setColorAndRefresh(TintColor.tintColor());
            this.mHorizontalView.setSelection(-1, true);
        } else {
            this.mAutoButton.setColor(0);
            this.mAutoButton.setIsNeedTransparent(true, true);
            this.mHorizontalView.setSelection((int) extraFocusAdapter.mapValueToPosition(Integer.valueOf(intValue)), true);
        }
        ((ViewGroup.MarginLayoutParams) this.mHorizontalView.getLayoutParams()).setMarginStart(getResources().getDimensionPixelSize(R.dimen.manual_extra_horizontal_view_left));
        this.mHorizontalView.setListener(extraFocusAdapter, null);
        animateShowView(this.mHorizontalViewLayout);
    }

    private void initWBRecyclerView(ComponentData componentData) {
        hideView();
        RecyclerView recyclerView = this.mExtraList;
        this.mTargetView = recyclerView;
        ViewGroup.MarginLayoutParams marginLayoutParams = (ViewGroup.MarginLayoutParams) recyclerView.getLayoutParams();
        marginLayoutParams.bottomMargin = getResources().getDimensionPixelSize(R.dimen.manual_extra_recyclerview_margin_bottom);
        marginLayoutParams.height = getResources().getDimensionPixelSize(R.dimen.manual_extra_recyclerview_height);
        AnonymousClass1 r1 = new ExtraRecyclerViewAdapter(componentData, this.mCurrentMode, this.mManuallyListener, marginLayoutParams.height, this.mDegree) {
            /* class com.android.camera.fragment.manually.FragmentManuallyExtra.AnonymousClass1 */

            /* access modifiers changed from: protected */
            @Override // com.android.camera.fragment.manually.adapter.ExtraRecyclerViewAdapter
            public boolean couldNewValueTakeEffect(String str) {
                if (str == null || !str.equals("manual")) {
                    return super.couldNewValueTakeEffect(str);
                }
                return true;
            }
        };
        LinearLayoutManagerWrapper linearLayoutManagerWrapper = new LinearLayoutManagerWrapper(getContext(), "manually_extra_list");
        linearLayoutManagerWrapper.setOrientation(0);
        this.mExtraList.setLayoutManager(linearLayoutManagerWrapper);
        if (this.mExtraList.getItemDecorationCount() != 0) {
            this.mExtraList.removeItemDecorationAt(0);
        }
        this.mExtraList.addItemDecoration(new ItemPadding(getResources().getDimensionPixelSize(R.dimen.manual_extra_recyclerview_wb_item_padding)));
        this.mExtraList.setAdapter(r1);
        animateShowView(this.mExtraList);
    }

    private void toShowOrHideView(final View view, final View view2, boolean z) {
        this.mTargetView = view2;
        ArrayList arrayList = new ArrayList();
        int dimensionPixelSize = getResources().getDimensionPixelSize(R.dimen.manually_recycler_view_height);
        arrayList.add(ObjectAnimator.ofFloat(view2, View.ALPHA, 0.0f, 1.0f));
        if (z) {
            arrayList.add(ObjectAnimator.ofFloat(view2, View.TRANSLATION_X, (float) dimensionPixelSize, 0.0f));
        } else {
            arrayList.add(ObjectAnimator.ofFloat(view2, View.TRANSLATION_X, (float) (-dimensionPixelSize), 0.0f));
        }
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playTogether(arrayList);
        animatorSet.setInterpolator(new CubicEaseOutInterpolator());
        animatorSet.setDuration(400L).addListener(new Animator.AnimatorListener() {
            /* class com.android.camera.fragment.manually.FragmentManuallyExtra.AnonymousClass3 */

            public void onAnimationCancel(Animator animator) {
                view.setVisibility(8);
                view2.setVisibility(0);
                ViewCompat.setAlpha(view2, 1.0f);
                ViewCompat.setTranslationX(view2, 0.0f);
            }

            public void onAnimationEnd(Animator animator) {
                view.setVisibility(8);
                view2.setVisibility(0);
            }

            public void onAnimationRepeat(Animator animator) {
            }

            public void onAnimationStart(Animator animator) {
                view.setVisibility(8);
                view2.setVisibility(0);
            }
        });
        animatorSet.start();
    }

    public /* synthetic */ void Ra() {
        FragmentUtils.removeFragmentByTag(getFragmentManager(), getFragmentTag());
        this.mCurrentTitle = -1;
    }

    public void animateOut() {
        View view = getView();
        if (view != null) {
            animateParentInOrOut(view, false, new g(this));
        }
    }

    public int getCurrentTitle() {
        return this.mCurrentTitle;
    }

    @Override // com.android.camera.fragment.BaseFragment
    public int getFragmentInto() {
        return 254;
    }

    /* access modifiers changed from: protected */
    @Override // com.android.camera.fragment.BaseFragment
    public int getLayoutResourceId() {
        return R.layout.fragment_manually_extra;
    }

    /* access modifiers changed from: protected */
    @Override // com.android.camera.fragment.BaseFragment
    public void initView(View view) {
        this.mExtraList = (RecyclerView) view.findViewById(R.id.manually_extra_list);
        this.mExtraList.setFocusable(false);
        this.mHorizontalView = (HorizontalZoomView) view.findViewById(R.id.manually_extra_horizontal_view);
        this.mHorizontalViewLayout = (RelativeLayout) view.findViewById(R.id.manually_extra_horizontal_layout);
        this.mAutoButton = (ColorImageView) view.findViewById(R.id.manually_extra_auto_button);
        this.mAutoButton.setOnClickListener(this);
        ComponentData componentData = this.mData;
        if (componentData != null) {
            initAdapter(componentData);
            this.mCurrentTitle = this.mData.getDisplayTitleString();
        }
    }

    @Override // com.android.camera.fragment.BaseFragment, com.android.camera.animation.AnimationDelegate.AnimationResource
    public void notifyDataChanged(int i, int i2) {
        super.notifyDataChanged(i, i2);
        if (this.mExtraList.getAdapter() != null) {
            this.mExtraList.getAdapter().notifyDataSetChanged();
        }
    }

    public void onClick(View view) {
        switch (this.mTargetKey) {
            case R.string.pref_camera_iso_title_abbr:
                ((ColorImageView) view).setColorAndRefresh(TintColor.tintColor());
                this.mHorizontalView.setSelection(-1, true);
                ComponentManuallyISO componentManuallyISO = DataRepository.dataItemConfig().getmComponentManuallyISO();
                String componentValue = componentManuallyISO.getComponentValue(this.mCurrentMode);
                componentManuallyISO.setComponentValue(this.mCurrentMode, "0");
                this.mHorizontalView.getDrawAdapter().setCurrentValue("0");
                ManuallyListener manuallyListener = this.mManuallyListener;
                if (manuallyListener != null) {
                    manuallyListener.onManuallyDataChanged(componentManuallyISO, componentValue, "0", false, this.mCurrentMode);
                    return;
                }
                return;
            case R.string.pref_camera_whitebalance_title_abbr:
                toShowOrHideView(this.mHorizontalViewLayout, this.mExtraList, false);
                return;
            case R.string.pref_manual_exposure_title_abbr:
                ((ColorImageView) view).setColorAndRefresh(TintColor.tintColor());
                this.mHorizontalView.setSelection(-1, true);
                ComponentManuallyET componentManuallyET = DataRepository.dataItemConfig().getmComponentManuallyET();
                String componentValue2 = componentManuallyET.getComponentValue(this.mCurrentMode);
                componentManuallyET.setComponentValue(this.mCurrentMode, "0");
                this.mHorizontalView.getDrawAdapter().setCurrentValue("0");
                ManuallyListener manuallyListener2 = this.mManuallyListener;
                if (manuallyListener2 != null) {
                    manuallyListener2.onManuallyDataChanged(componentManuallyET, componentValue2, "0", false, this.mCurrentMode);
                    return;
                }
                return;
            case R.string.pref_qc_focus_position_title_abbr:
                ((ColorImageView) view).setColorAndRefresh(TintColor.tintColor());
                this.mHorizontalView.setSelection(-1, true);
                ComponentManuallyFocus manuallyFocus = DataRepository.dataItemConfig().getManuallyFocus();
                String componentValue3 = manuallyFocus.getComponentValue(this.mCurrentMode);
                manuallyFocus.setComponentValue(this.mCurrentMode, String.valueOf(1000));
                this.mHorizontalView.getDrawAdapter().setCurrentValue(String.valueOf(1000));
                ManuallyListener manuallyListener3 = this.mManuallyListener;
                if (manuallyListener3 != null) {
                    manuallyListener3.onManuallyDataChanged(manuallyFocus, componentValue3, String.valueOf(1000), false, this.mCurrentMode);
                    return;
                }
                return;
            default:
                return;
        }
    }

    @Override // androidx.fragment.app.Fragment
    public void onPause() {
        super.onPause();
        ColorImageView colorImageView = this.mAutoButton;
        if (colorImageView != null) {
            FolmeUtils.clean(colorImageView);
        }
        View view = this.mTargetView;
        if (view != null) {
            FolmeUtils.clean(view);
        }
        if (getView() != null) {
            FolmeUtils.clean(getView());
        }
    }

    @Override // com.android.camera.fragment.BaseFragment, androidx.fragment.app.Fragment
    public void onResume() {
        super.onResume();
        ColorImageView colorImageView = this.mAutoButton;
        if (colorImageView != null) {
            FolmeUtils.touchTint(colorImageView);
        }
    }

    @Override // com.android.camera.fragment.BaseFragment, androidx.fragment.app.Fragment
    public void onViewCreated(View view, @Nullable Bundle bundle) {
        super.onViewCreated(view, bundle);
        if (this.mNeedAnimation) {
            this.mNeedAnimation = false;
            animateParentInOrOut(view, true, null);
        }
    }

    @Override // com.android.camera.fragment.BaseFragment, com.android.camera.animation.AnimationDelegate.AnimationResource
    public void provideAnimateElement(int i, List<Completable> list, int i2) {
        super.provideAnimateElement(i, list, i2);
    }

    @Override // com.android.camera.fragment.BaseFragment, com.android.camera.animation.AnimationDelegate.AnimationResource
    public void provideRotateItem(List<View> list, int i) {
        super.provideRotateItem(list, i);
        RecyclerView recyclerView = this.mExtraList;
        if (recyclerView != null) {
            RecyclerView.Adapter adapter = recyclerView.getAdapter();
            if (adapter != null && (adapter instanceof ExtraRecyclerViewAdapter)) {
                ((ExtraRecyclerViewAdapter) adapter).setDegree(i);
            }
            for (int i2 = 0; i2 < this.mExtraList.getChildCount(); i2++) {
                list.add(this.mExtraList.getChildAt(i2));
            }
        }
        HorizontalZoomView horizontalZoomView = this.mHorizontalView;
        if (horizontalZoomView != null) {
            horizontalZoomView.setRotate(i);
        }
        ColorImageView colorImageView = this.mAutoButton;
        if (colorImageView == null) {
            return;
        }
        if (this.mTargetKey == R.string.pref_camera_whitebalance_title_abbr) {
            int i3 = this.mDegree;
            if (i3 == 0 || i3 == 90) {
                this.mAutoButton.setRotation(0.0f);
            } else {
                colorImageView.setRotation(180.0f);
            }
        } else {
            list.add(colorImageView);
        }
    }

    public void resetData(ComponentData componentData) {
        this.mData = componentData;
        initAdapter(this.mData);
        this.mCurrentTitle = this.mData.getDisplayTitleString();
    }

    public void setComponentData(ComponentData componentData, int i, boolean z, ManuallyListener manuallyListener) {
        this.mData = componentData;
        this.mCurrentMode = i;
        this.mNeedAnimation = z;
        this.mManuallyListener = manuallyListener;
    }

    public void setmFragmentManually(FragmentManually fragmentManually) {
        this.mFragmentManually = fragmentManually;
    }

    public void showCustomWB(ComponentManuallyWB componentManuallyWB, boolean z) {
        this.mTargetView = this.mHorizontalViewLayout;
        this.mTargetKey = componentManuallyWB.getDisplayTitleString();
        ExtraCustomWBListAdapter extraCustomWBListAdapter = new ExtraCustomWBListAdapter(getContext(), componentManuallyWB, this.mCurrentMode, z, this.mManuallyListener);
        this.mHorizontalView.setDrawAdapter(extraCustomWBListAdapter, this.mDegree, false);
        this.mHorizontalView.setListener(extraCustomWBListAdapter, null);
        ((ViewGroup.MarginLayoutParams) this.mHorizontalView.getLayoutParams()).setMarginStart(getResources().getDimensionPixelSize(R.dimen.manual_extra_horizontal_view_left));
        this.mHorizontalView.setSelection((int) extraCustomWBListAdapter.mapValueToPosition(Integer.valueOf(componentManuallyWB.getCustomWB())), true);
        this.mAutoButton.setColor(0);
        this.mAutoButton.setIsNeedTransparent(true, true);
        this.mAutoButton.setImageResource(R.drawable.ic_manually_extra_back_button);
        this.mAutoButton.setBackgroundResource(R.drawable.ic_manually_extra_back_button_shadow);
        this.mAutoButton.setVisibility(0);
        int i = this.mDegree;
        if (i == 0 || i == 90) {
            this.mAutoButton.setRotation(0.0f);
        } else {
            this.mAutoButton.setRotation(180.0f);
        }
        toShowOrHideView(this.mExtraList, this.mHorizontalViewLayout, true);
    }

    public void toUpdateAutoButton() {
        ColorImageView colorImageView = this.mAutoButton;
        if (colorImageView != null) {
            colorImageView.setColor(0);
            this.mAutoButton.setIsNeedTransparent(true, true);
        }
    }

    public void updateData() {
        this.mCurrentMode = DataRepository.dataItemGlobal().getCurrentMode();
        initAdapter(this.mData);
    }

    public void updateRecordingState(boolean z) {
        this.mIsRecording = z;
        if (this.mExtraList == null) {
        }
    }
}
