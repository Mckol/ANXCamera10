package com.android.camera.fragment.aiwatermark;

import com.android.camera.aiwatermark.data.GeneralWatermark;
import com.android.camera.aiwatermark.data.WatermarkItem;
import java.util.List;

public class FragmentGenWatermark extends FragmentBaseWatermark {
    public static final int FRAGMENT_INFO = 1048567;
    private static final String TAG = "FragmentGenWatermark";

    @Override // com.android.camera.fragment.aiwatermark.FragmentBaseWatermark
    public List<WatermarkItem> getWatermarkList() {
        if (this.mData == null) {
            this.mData = new GeneralWatermark();
        }
        return this.mData.getForManual();
    }
}
