package com.android.camera.fragment.mode;

import android.view.View;
import android.view.animation.Animation;
import android.widget.FrameLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.android.camera.R;
import com.android.camera.Util;
import com.android.camera.animation.FragmentAnimationFactory;
import com.android.camera.fragment.BaseFragmentDelegate;
import com.android.camera.log.Log;
import com.android.camera.protocol.ModeCoordinatorImpl;
import com.android.camera.protocol.ModeProtocol;
import java.util.List;

public class FragmentMoreModeNormal extends FragmentMoreModeBase {
    private static final String TAG = "MoreModeNormal";

    @Override // com.android.camera.fragment.BaseFragment
    public int getFragmentInto() {
        return BaseFragmentDelegate.FRAGMENT_MODES_MORE_NORMAL;
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
        return 0;
    }

    /* access modifiers changed from: protected */
    @Override // com.android.camera.fragment.mode.FragmentMoreModeBase
    public boolean hide() {
        ModeProtocol.ModeListManager modeListManager = (ModeProtocol.ModeListManager) ModeCoordinatorImpl.getInstance().getAttachProtocol(2560);
        if (modeListManager == null || !modeListManager.isMoreModeShowing(false)) {
            return false;
        }
        modeListManager.hideMoreMode(false);
        return true;
    }

    /* access modifiers changed from: protected */
    @Override // com.android.camera.fragment.BaseFragment, com.android.camera.fragment.mode.FragmentMoreModeBase
    public void initView(View view) {
        super.initView(view);
        Log.d(TAG, "initView");
        FrameLayout frameLayout = (FrameLayout) this.mRootView.findViewById(R.id.modes_contain);
        FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) frameLayout.getLayoutParams();
        layoutParams.width = -1;
        layoutParams.height = -1;
        layoutParams.bottomMargin = Util.getBottomHeight();
        layoutParams.topMargin = Util.getTopHeight();
        ((FrameLayout.LayoutParams) frameLayout.getChildAt(0).getLayoutParams()).gravity = 81;
    }

    @Override // com.android.camera.fragment.BaseFragment, com.android.camera.animation.AnimationDelegate.AnimationResource
    public void notifyAfterFrameAvailable(int i) {
        super.notifyAfterFrameAvailable(i);
        if (this.mCurrentMode != 254) {
            hide();
        }
    }

    @Override // androidx.fragment.app.Fragment
    public void onPause() {
        super.onPause();
        Log.d(TAG, "onPause");
    }

    @Override // com.android.camera.fragment.BaseFragment, androidx.fragment.app.Fragment
    public void onResume() {
        super.onResume();
        Log.d(TAG, "onResume");
        if (getModeAdapter() != null) {
            getModeAdapter().notifyDataSetChanged();
        }
    }

    /* access modifiers changed from: protected */
    @Override // com.android.camera.fragment.BaseFragment
    public Animation provideEnterAnimation(int i) {
        Log.d(TAG, "provideEnterAnimation");
        return FragmentAnimationFactory.wrapperAnimation(161);
    }

    /* access modifiers changed from: protected */
    @Override // com.android.camera.fragment.BaseFragment
    public Animation provideExitAnimation(int i) {
        Log.d(TAG, "provideExitAnimation");
        return FragmentAnimationFactory.wrapperAnimation(162);
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
}
