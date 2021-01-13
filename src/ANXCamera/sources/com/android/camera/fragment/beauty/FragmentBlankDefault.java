package com.android.camera.fragment.beauty;

import android.view.View;
import android.view.animation.Animation;
import com.android.camera.R;
import com.android.camera.fragment.BaseFragment;

public class FragmentBlankDefault extends BaseFragment {
    public static final int FRAGMENT_INFO = 14;

    @Override // com.android.camera.fragment.BaseFragment
    public int getFragmentInto() {
        return 14;
    }

    /* access modifiers changed from: protected */
    @Override // com.android.camera.fragment.BaseFragment
    public int getLayoutResourceId() {
        return R.layout.fragment_blank_default;
    }

    /* access modifiers changed from: protected */
    @Override // com.android.camera.fragment.BaseFragment
    public void initView(View view) {
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
}