package com.android.camera.fragment.beauty;

import androidx.fragment.app.Fragment;
import b.c.a.c;
import com.android.camera.CameraSettings;
import com.android.camera.data.DataRepository;
import java.util.ArrayList;
import java.util.List;

@Deprecated
public class FrontFragmentBusiness extends AbBeautyFragmentBusiness {
    List<Fragment> mFragments;

    @Override // com.android.camera.fragment.beauty.IBeautyFragmentBusiness
    public List<Fragment> getCurrentShowFragmentList() {
        List<Fragment> list = this.mFragments;
        if (list == null) {
            this.mFragments = new ArrayList();
        } else {
            list.clear();
        }
        if (c.Bn()) {
            this.mFragments.add(new BeautyLevelFragment());
        } else {
            this.mFragments.add(new BeautyLevelFragment());
            this.mFragments.add(new MakeupParamsFragment());
        }
        if (DataRepository.dataItemFeature().rl() && CameraSettings.isSupportBeautyMakeup()) {
            this.mFragments.add(new MakeupBeautyFragment());
        }
        return this.mFragments;
    }
}
