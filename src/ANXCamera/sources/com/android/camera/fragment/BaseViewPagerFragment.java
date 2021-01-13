package com.android.camera.fragment;

import android.os.Bundle;
import android.view.View;
import androidx.annotation.CallSuper;
import androidx.fragment.app.Fragment;

public abstract class BaseViewPagerFragment extends Fragment {
    private boolean isOnCreate;
    private boolean isViewCreated;
    private boolean isViewCreatedAndVisibleToUser;
    private boolean isVisibleToUser;

    private void beforeViewGoneToUser() {
        this.isViewCreatedAndVisibleToUser = false;
    }

    private void beforeViewVisibleToUser() {
        this.isViewCreatedAndVisibleToUser = true;
    }

    @Override // androidx.fragment.app.Fragment
    @CallSuper
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        this.isOnCreate = true;
    }

    @Override // androidx.fragment.app.Fragment
    @CallSuper
    public void onDestroyView() {
        super.onDestroyView();
        this.isViewCreated = false;
        if (this.isViewCreatedAndVisibleToUser) {
            beforeViewGoneToUser();
            onViewCreatedAndJumpOut();
        }
    }

    @Override // androidx.fragment.app.Fragment
    @CallSuper
    public void onViewCreated(View view, Bundle bundle) {
        super.onViewCreated(view, bundle);
        this.isViewCreated = true;
        if (!this.isVisibleToUser) {
            return;
        }
        if (this.isOnCreate) {
            this.isOnCreate = false;
            beforeViewVisibleToUser();
            onViewCreatedAndVisibleToUser(true);
            return;
        }
        beforeViewVisibleToUser();
        onViewCreatedAndVisibleToUser(false);
    }

    /* access modifiers changed from: protected */
    @CallSuper
    public void onViewCreatedAndJumpOut() {
    }

    /* access modifiers changed from: protected */
    @CallSuper
    public void onViewCreatedAndVisibleToUser(boolean z) {
    }

    @Override // androidx.fragment.app.Fragment
    @CallSuper
    public void setUserVisibleHint(boolean z) {
        this.isVisibleToUser = z;
        super.setUserVisibleHint(z);
        if (this.isViewCreated && z) {
            if (this.isOnCreate) {
                this.isOnCreate = false;
                beforeViewVisibleToUser();
                onViewCreatedAndVisibleToUser(true);
            } else {
                beforeViewVisibleToUser();
                onViewCreatedAndVisibleToUser(false);
            }
        }
        if (!z && this.isViewCreatedAndVisibleToUser) {
            beforeViewGoneToUser();
            onViewCreatedAndJumpOut();
        }
    }
}
