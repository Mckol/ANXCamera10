package com.android.camera.fragment.aiwatermark;

import com.android.camera.aiwatermark.data.SuperMoonSilhouetteWatermark;
import com.android.camera.aiwatermark.data.WatermarkItem;
import java.util.List;

public class FragmentSuperMoonSilhouetteWatermark extends FragmentBaseWatermark {
    public static final int FRAGMENT_INFO = 1048569;
    private static final String TAG = "FragmentSuperMoonSilhouetteWatermark";

    public FragmentSuperMoonSilhouetteWatermark() {
        this.mForceUpdateSelected = true;
    }

    @Override // com.android.camera.fragment.aiwatermark.FragmentBaseWatermark
    public List<WatermarkItem> getWatermarkList() {
        if (this.mData == null) {
            this.mData = new SuperMoonSilhouetteWatermark();
        }
        return this.mData.getForManual();
    }
}
