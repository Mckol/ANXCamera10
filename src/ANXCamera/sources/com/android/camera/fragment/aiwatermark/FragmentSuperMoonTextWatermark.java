package com.android.camera.fragment.aiwatermark;

import com.android.camera.aiwatermark.data.SuperMoonTextWatermark;
import com.android.camera.aiwatermark.data.WatermarkItem;
import java.util.List;

public class FragmentSuperMoonTextWatermark extends FragmentBaseWatermark {
    public static final int FRAGMENT_INFO = 1048570;
    private static final String TAG = "FragmentSuperMoonTextWatermark";

    public FragmentSuperMoonTextWatermark() {
        this.mForceUpdateSelected = true;
    }

    @Override // com.android.camera.fragment.aiwatermark.FragmentBaseWatermark
    public List<WatermarkItem> getWatermarkList() {
        if (this.mData == null) {
            this.mData = new SuperMoonTextWatermark();
        }
        return this.mData.getForManual();
    }
}
