package com.android.camera.fragment;

import android.view.View;
import android.view.ViewGroup;
import com.android.camera.R;
import com.android.camera.protocol.ModeCoordinatorImpl;
import com.android.camera.protocol.ModeProtocol;

public class FragmentModuleContent extends BaseFragment implements ModeProtocol.ModuleContent {
    private ViewGroup mParent;

    @Override // com.android.camera.fragment.BaseFragment
    public int getFragmentInto() {
        return BaseFragmentDelegate.MODULE_CONTENT;
    }

    /* access modifiers changed from: protected */
    @Override // com.android.camera.fragment.BaseFragment
    public int getLayoutResourceId() {
        return R.layout.fragment_module_content;
    }

    @Override // com.android.camera.protocol.ModeProtocol.ModuleContent
    public ViewGroup getParent() {
        return this.mParent;
    }

    /* access modifiers changed from: protected */
    @Override // com.android.camera.fragment.BaseFragment
    public void initView(View view) {
        this.mParent = (ViewGroup) view;
        ModeCoordinatorImpl.getInstance().attachProtocol(431, this);
    }

    @Override // com.android.camera.fragment.BaseFragment, androidx.fragment.app.Fragment
    public void onDestroyView() {
        ModeCoordinatorImpl.getInstance().detachProtocol(431, this);
        super.onDestroyView();
    }
}
