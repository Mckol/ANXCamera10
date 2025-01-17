package com.android.camera.fragment.lifeCircle;

import android.annotation.SuppressLint;
import androidx.fragment.app.Fragment;

public class BaseLifeCircleBindFragment extends Fragment {
    public static final String FRAGMENT_TAG = "com.android.camera.bind";
    private final BaseFragmentLifecycle mLifecycle;

    public BaseLifeCircleBindFragment() {
        this(new BaseFragmentLifecycle());
    }

    @SuppressLint({"ValidFragment"})
    public BaseLifeCircleBindFragment(BaseFragmentLifecycle baseFragmentLifecycle) {
        this.mLifecycle = baseFragmentLifecycle;
    }

    public BaseFragmentLifecycle getFragmentLifecycle() {
        return this.mLifecycle;
    }

    @Override // androidx.fragment.app.Fragment
    public void onDestroy() {
        super.onDestroy();
        this.mLifecycle.onDestroy(FRAGMENT_TAG);
    }

    @Override // androidx.fragment.app.Fragment
    public void onStart() {
        super.onStart();
        this.mLifecycle.onStart(FRAGMENT_TAG);
    }

    @Override // androidx.fragment.app.Fragment
    public void onStop() {
        super.onStop();
        this.mLifecycle.onStop(FRAGMENT_TAG);
    }
}
