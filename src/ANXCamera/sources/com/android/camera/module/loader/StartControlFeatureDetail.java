package com.android.camera.module.loader;

import androidx.annotation.IdRes;
import com.android.camera.fragment.BaseFragmentOperation;
import com.android.camera.module.interceptor.BaseModuleInterceptor;
import java.util.ArrayList;
import java.util.List;

public class StartControlFeatureDetail {
    private List<BaseFragmentOperation> fragmentAlias;
    private List<BaseModuleInterceptor> interceptorList;
    private int[] topConfigItems;

    public void addFragmentInfo(@IdRes int i, int i2) {
        if (this.fragmentAlias == null) {
            this.fragmentAlias = new ArrayList();
        }
        this.fragmentAlias.add(new BaseFragmentOperation(i).replaceWith(i2));
    }

    public void addFragmentInfo(@IdRes int i, String str) {
        if (this.fragmentAlias == null) {
            this.fragmentAlias = new ArrayList();
        }
        this.fragmentAlias.add(new BaseFragmentOperation(i).featureWith(str));
    }

    public void addInterceptor(BaseModuleInterceptor baseModuleInterceptor) {
        if (this.interceptorList == null) {
            this.interceptorList = new ArrayList();
        }
        this.interceptorList.add(baseModuleInterceptor);
    }

    public List<BaseFragmentOperation> getFragmentAlias() {
        return this.fragmentAlias;
    }

    public List<BaseModuleInterceptor> getInterceptorList() {
        return this.interceptorList;
    }

    public int[] getTopConfigItems() {
        return this.topConfigItems;
    }

    public void hideFragment(@IdRes int i) {
        if (this.fragmentAlias == null) {
            this.fragmentAlias = new ArrayList();
        }
        this.fragmentAlias.add(new BaseFragmentOperation(i).hideCurrent());
    }

    public void removeCurrentFragment(@IdRes int i) {
        if (this.fragmentAlias == null) {
            this.fragmentAlias = new ArrayList();
        }
        this.fragmentAlias.add(new BaseFragmentOperation(i).removeCurrent());
    }

    public void setTopConfigItems(int... iArr) {
        this.topConfigItems = iArr;
    }
}
