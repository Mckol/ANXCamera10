package com.android.camera.fragment.fastmotion;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.graphics.Rect;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import androidx.annotation.Nullable;
import androidx.core.view.ViewCompat;
import androidx.recyclerview.widget.RecyclerView;
import b.c.a.c;
import com.android.camera.R;
import com.android.camera.Util;
import com.android.camera.animation.FolmeUtils;
import com.android.camera.data.DataRepository;
import com.android.camera.data.data.ComponentData;
import com.android.camera.fragment.BaseFragment;
import com.android.camera.fragment.FragmentUtils;
import com.android.camera.fragment.manually.ManuallyListener;
import com.android.camera.fragment.manually.adapter.ExtraHorizontalListAdapter;
import com.android.camera.ui.HorizontalZoomView;
import io.reactivex.Completable;
import java.util.ArrayList;
import java.util.List;
import miui.view.animation.CubicEaseOutInterpolator;

public class FragmentFastMotionExtra extends BaseFragment {
    public static final int FRAGMENT_INFO = 16777202;
    private int mCurrentTitle = -1;
    private ComponentData mData;
    private HorizontalZoomView mHorizontalView;
    private RelativeLayout mHorizontalViewLayout;
    private ManuallyListener mManuallyListener;
    private boolean mNeedAnimation;
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
        initHorizontalListView(componentData, c.mo());
    }

    private void initHorizontalListView(ComponentData componentData, boolean z) {
        hideView();
        this.mTargetView = this.mHorizontalViewLayout;
        String componentValue = componentData.getComponentValue(this.mCurrentMode);
        ExtraHorizontalListAdapter extraHorizontalListAdapter = new ExtraHorizontalListAdapter(getContext(), componentData, this.mCurrentMode, this.mManuallyListener);
        this.mHorizontalView.setDrawAdapter(extraHorizontalListAdapter, this.mDegree, false);
        this.mHorizontalView.getLayoutParams().width = Util.sWindowWidth;
        this.mHorizontalView.setSelection((int) extraHorizontalListAdapter.mapValueToPosition(componentValue), true);
        this.mHorizontalView.setListener(extraHorizontalListAdapter, null);
        animateShowView(this.mHorizontalViewLayout);
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
            /* class com.android.camera.fragment.fastmotion.FragmentFastMotionExtra.AnonymousClass1 */

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

    public /* synthetic */ void Ha() {
        FragmentUtils.removeFragmentByTag(getFragmentManager(), getFragmentTag());
        this.mCurrentTitle = -1;
    }

    public void animateOut() {
        View view = getView();
        if (view != null) {
            animateParentInOrOut(view, false, new d(this));
        }
    }

    public int getCurrentTitle() {
        return this.mCurrentTitle;
    }

    @Override // com.android.camera.fragment.BaseFragment
    public int getFragmentInto() {
        return 16777202;
    }

    /* access modifiers changed from: protected */
    @Override // com.android.camera.fragment.BaseFragment
    public int getLayoutResourceId() {
        return R.layout.fragment_fastmotion_extra;
    }

    /* access modifiers changed from: protected */
    @Override // com.android.camera.fragment.BaseFragment
    public void initView(View view) {
        this.mHorizontalView = (HorizontalZoomView) view.findViewById(R.id.manually_extra_horizontal_view);
        this.mHorizontalViewLayout = (RelativeLayout) view.findViewById(R.id.manually_extra_horizontal_layout);
        ComponentData componentData = this.mData;
        if (componentData != null) {
            initAdapter(componentData);
            this.mCurrentTitle = this.mData.getDisplayTitleString();
        }
    }

    @Override // com.android.camera.fragment.BaseFragment, com.android.camera.animation.AnimationDelegate.AnimationResource
    public void notifyDataChanged(int i, int i2) {
        super.notifyDataChanged(i, i2);
    }

    @Override // androidx.fragment.app.Fragment
    public void onPause() {
        super.onPause();
        View view = this.mTargetView;
        if (view != null) {
            FolmeUtils.clean(view);
        }
    }

    @Override // com.android.camera.fragment.BaseFragment, androidx.fragment.app.Fragment
    public void onResume() {
        super.onResume();
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
        HorizontalZoomView horizontalZoomView = this.mHorizontalView;
        if (horizontalZoomView != null) {
            horizontalZoomView.setRotate(i);
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

    public void updateData() {
        this.mCurrentMode = DataRepository.dataItemGlobal().getCurrentMode();
        initAdapter(this.mData);
    }
}
