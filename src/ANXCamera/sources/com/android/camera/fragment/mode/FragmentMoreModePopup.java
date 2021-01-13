package com.android.camera.fragment.mode;

import android.graphics.Rect;
import android.graphics.drawable.GradientDrawable;
import android.view.View;
import android.widget.FrameLayout;
import androidx.annotation.CallSuper;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.android.camera.R;
import com.android.camera.Util;
import com.android.camera.data.DataRepository;
import com.android.camera.data.data.ComponentDataItem;
import com.android.camera.fragment.BaseFragmentDelegate;
import com.android.camera.log.Log;
import com.android.camera.protocol.ModeCoordinatorImpl;
import com.android.camera.protocol.ModeProtocol;
import com.android.camera.ui.DragLayout;
import java.util.List;

public class FragmentMoreModePopup extends FragmentMoreModeBase implements ModeProtocol.HandleBackTrace, DragLayout.OnDragListener {
    private static final String TAG = "MoreModePopup";
    private GradientDrawable mBgDrawable;
    private FrameLayout mContainView;
    private Rect mListArea = new Rect();
    private boolean mNeedBgAlphaAnimation = false;
    private boolean mOnDragging;
    private float[] mRadiusArrays;
    private int mTargetAlpha = 255;

    private boolean isFullScreen() {
        return DataRepository.dataItemRunning().getUiStyle() == 3;
    }

    private boolean needBgAlphaAnimation() {
        return DataRepository.dataItemRunning().getUiStyle() == 3 || DataRepository.dataItemRunning().getUiStyle() == 1;
    }

    @Override // com.android.camera.ui.DragLayout.OnDragListener
    public boolean catchDrag(int i, int i2) {
        RecyclerView recyclerView = this.mModeList;
        if (recyclerView == null || recyclerView.getVisibility() != 0) {
            return false;
        }
        this.mModeList.getGlobalVisibleRect(this.mListArea);
        boolean contains = this.mListArea.contains(i, i2);
        Log.d(TAG, "catchDrag = " + contains);
        return contains;
    }

    @Override // com.android.camera.fragment.BaseFragment
    public int getFragmentInto() {
        return BaseFragmentDelegate.FRAGMENT_MODES_MORE_POPUP;
    }

    /* access modifiers changed from: protected */
    @Override // com.android.camera.fragment.mode.FragmentMoreModeBase
    public RecyclerView.LayoutManager getLayoutManager() {
        return new GridLayoutManager(getContext(), 3, 1, false);
    }

    /* access modifiers changed from: protected */
    @Override // com.android.camera.fragment.mode.FragmentMoreModeBase
    public ModeItemDecoration getModeItemDecoration() {
        return new ModeItemDecoration(getContext(), getModeList(), getType());
    }

    /* access modifiers changed from: protected */
    @Override // com.android.camera.fragment.mode.FragmentMoreModeBase
    public RecyclerView getModeList() {
        return (RecyclerView) this.mRootView.findViewById(R.id.modes_content);
    }

    /* access modifiers changed from: protected */
    @Override // com.android.camera.fragment.mode.FragmentMoreModeBase
    public int getType() {
        return 1;
    }

    /* access modifiers changed from: protected */
    @Override // com.android.camera.fragment.mode.FragmentMoreModeBase
    public boolean hide() {
        return onBackEvent(5);
    }

    /* access modifiers changed from: protected */
    @Override // com.android.camera.fragment.BaseFragment, com.android.camera.fragment.mode.FragmentMoreModeBase
    public void initView(View view) {
        super.initView(view);
        this.mContainView = (FrameLayout) this.mRootView.findViewById(R.id.modes_contain);
        int dimensionPixelOffset = getResources().getDimensionPixelOffset(R.dimen.mode_more_hor_margin);
        FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) this.mContainView.getChildAt(0).getLayoutParams();
        layoutParams.topMargin = dimensionPixelOffset;
        layoutParams.bottomMargin = Util.getNavigationBarHeight(getContext());
        layoutParams.gravity = 1;
        this.mModeList.setVisibility(8);
    }

    @Override // com.android.camera.protocol.ModeProtocol.HandleBackTrace
    public boolean onBackEvent(int i) {
        ModeProtocol.MoreModePopupController moreModePopupController = (ModeProtocol.MoreModePopupController) ModeCoordinatorImpl.getInstance().getAttachProtocol(2561);
        if (moreModePopupController == null || !moreModePopupController.isExpanded()) {
            return false;
        }
        return moreModePopupController.shrink(true);
    }

    @Override // com.android.camera.fragment.mode.FragmentMoreModeBase
    public void onClick(View view) {
        int id = view.getId();
        if (id != R.id.mode_item) {
            if (id == R.id.modes_root) {
                onBackEvent(5);
            }
        } else if (!this.mOnDragging && this.mModeList.getVisibility() == 0 && this.mModeList.getAlpha() == 1.0f) {
            int parseInt = Integer.parseInt(((ComponentDataItem) view.getTag()).mValue);
            if (parseInt == 255) {
                ModeProtocol.ConfigChanges configChanges = (ModeProtocol.ConfigChanges) ModeCoordinatorImpl.getInstance().getAttachProtocol(164);
                if (configChanges != null) {
                    configChanges.configModeEdit();
                    return;
                }
                return;
            }
            hide();
            ModeProtocol.ModeChangeController modeChangeController = (ModeProtocol.ModeChangeController) ModeCoordinatorImpl.getInstance().getAttachProtocol(179);
            if (modeChangeController != null) {
                modeChangeController.changeModeByNewMode(parseInt, 0);
            }
        }
    }

    @Override // com.android.camera.ui.DragLayout.OnDragListener
    public void onDragDone(boolean z) {
        if (z) {
            this.mModeList.setAlpha(1.0f);
        } else {
            this.mModeList.setAlpha(0.0f);
            this.mModeList.setVisibility(8);
            this.mContainView.setBackground(null);
        }
        if (!this.mOnDragging) {
            Log.w(TAG, "damn, check this flag.");
        } else {
            this.mOnDragging = false;
        }
    }

    @Override // com.android.camera.ui.DragLayout.OnDragListener
    public void onDragProgress(int i) {
        if (this.mBgDrawable != null) {
            if (this.mRadiusArrays != null) {
                int totalDragDistance = (int) (DragLayout.getAnimationConfig().getTotalDragDistance() / 2.0f);
                if (Math.abs(i) > totalDragDistance) {
                    int floatValue = (int) (DragLayout.getAnimationConfig().getCornerRadiusRange().getUpper().floatValue() * Math.min(1.0f, ((float) Math.abs(i + totalDragDistance)) / ((float) totalDragDistance)));
                    float[] fArr = this.mRadiusArrays;
                    float f = (float) floatValue;
                    fArr[3] = f;
                    fArr[2] = f;
                    fArr[1] = f;
                    fArr[0] = f;
                }
                this.mBgDrawable.setCornerRadii(this.mRadiusArrays);
            }
            if (this.mNeedBgAlphaAnimation) {
                this.mBgDrawable.setAlpha((int) (((float) this.mTargetAlpha) * Math.min(((float) Math.abs(i)) / ((float) DragLayout.getAnimationConfig().getDisplayRange().getUpper().intValue()), 1.0f)));
            }
        }
        this.mModeList.setAlpha(Math.min(1.0f, Math.min(1.0f, ((float) ((int) (((float) Math.abs(i)) - DragLayout.getAnimationConfig().getDisappearDistance()))) / DragLayout.getAnimationConfig().getDisplayDistance()) + DragLayout.getAnimationConfig().getDisplayAlphaRange().getLower().floatValue()));
    }

    @Override // com.android.camera.ui.DragLayout.OnDragListener
    public void onDragStart(boolean z) {
        if (this.mBgDrawable == null && getContext() != null) {
            this.mBgDrawable = (GradientDrawable) getContext().getDrawable(R.drawable.bg_more_modes);
            this.mRadiusArrays = this.mBgDrawable.getCornerRadii();
        }
        this.mContainView.setBackground(this.mBgDrawable);
        this.mNeedBgAlphaAnimation = needBgAlphaAnimation();
        if (isFullScreen()) {
            this.mTargetAlpha = 153;
            this.mBgDrawable.setAlpha(z ? 0 : this.mTargetAlpha);
        } else {
            this.mTargetAlpha = 255;
            if (!this.mNeedBgAlphaAnimation || !z) {
                this.mBgDrawable.setAlpha(this.mTargetAlpha);
            } else {
                this.mBgDrawable.setAlpha(0);
            }
        }
        ModeProtocol.BaseDelegate baseDelegate = (ModeProtocol.BaseDelegate) ModeCoordinatorImpl.getInstance().getAttachProtocol(160);
        if (!(baseDelegate == null || this.mDegree == baseDelegate.getAnimationComposite().getTargetDegree())) {
            this.mDegree = baseDelegate.getAnimationComposite().getTargetDegree();
            if (this.mModeList != null) {
                for (int i = 0; i < this.mModeList.getChildCount(); i++) {
                    this.mModeList.getChildAt(i).setRotation((float) this.mDegree);
                }
            }
        }
        if (z) {
            float[] fArr = this.mRadiusArrays;
            fArr[3] = 0.0f;
            fArr[2] = 0.0f;
            fArr[1] = 0.0f;
            fArr[0] = 0.0f;
            this.mModeList.setVisibility(0);
            this.mModeList.scrollToPosition(0);
            this.mModeList.setAlpha(0.0f);
        } else {
            this.mModeList.setAlpha(1.0f);
        }
        this.mOnDragging = true;
    }

    @Override // com.android.camera.ui.DragLayout.OnDragListener
    public boolean onInterceptDrag() {
        RecyclerView recyclerView;
        if (this.mOnDragging || (recyclerView = this.mModeList) == null || recyclerView.getVisibility() != 0) {
            return false;
        }
        boolean canScrollVertically = this.mModeList.canScrollVertically(-1);
        Log.d(TAG, "canScrollDown = " + canScrollVertically);
        return canScrollVertically;
    }

    @Override // androidx.fragment.app.Fragment
    public void onPause() {
        super.onPause();
        this.mOnDragging = false;
        ModeProtocol.MoreModePopupController moreModePopupController = (ModeProtocol.MoreModePopupController) ModeCoordinatorImpl.getInstance().getAttachProtocol(2561);
        if (moreModePopupController != null && moreModePopupController.isExpanded()) {
            moreModePopupController.shrink(false);
        }
    }

    @Override // com.android.camera.fragment.BaseFragment, androidx.fragment.app.Fragment
    public void onResume() {
        super.onResume();
    }

    @Override // com.android.camera.fragment.BaseFragment, com.android.camera.fragment.mode.FragmentMoreModeBase, com.android.camera.animation.AnimationDelegate.AnimationResource
    public void provideRotateItem(List<View> list, int i) {
        super.provideRotateItem(list, i);
        if (this.mModeList != null) {
            for (int i2 = 0; i2 < this.mModeList.getChildCount(); i2++) {
                list.add(this.mModeList.getChildAt(i2));
            }
        }
        if (getModeAdapter() != null) {
            getModeAdapter().setRotate(i);
        }
    }

    /* access modifiers changed from: protected */
    @Override // com.android.camera.fragment.BaseFragment
    @CallSuper
    public void register(ModeProtocol.ModeCoordinator modeCoordinator) {
        super.register(modeCoordinator);
        Log.d(TAG, "register");
        registerBackStack(modeCoordinator, this);
    }

    @Override // com.android.camera.ui.DragLayout.OnDragListener
    public boolean showDragAnimation(int i, int i2) {
        RecyclerView recyclerView = this.mModeList;
        return recyclerView != null && !Util.isInViewRegion(recyclerView, i, i2) && this.mModeList.getVisibility() == 0;
    }

    /* access modifiers changed from: protected */
    @Override // com.android.camera.fragment.BaseFragment
    @CallSuper
    public void unRegister(ModeProtocol.ModeCoordinator modeCoordinator) {
        super.unRegister(modeCoordinator);
        Log.d(TAG, "unRegister");
        unRegisterBackStack(modeCoordinator, this);
    }
}
