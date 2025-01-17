package com.android.camera.aiwatermark.handler;

import com.android.camera.aiwatermark.data.AbstractWatermarkData;
import com.android.camera.aiwatermark.data.Region;
import com.android.camera.aiwatermark.data.ScenicSpotsWatermark;
import com.android.camera.aiwatermark.data.WatermarkItem;
import java.util.ArrayList;
import java.util.HashMap;

public class IndiaScenicSpotsHandler extends ScenicSpotsHandler {
    public IndiaScenicSpotsHandler(boolean z) {
        super(z);
    }

    /* access modifiers changed from: protected */
    @Override // com.android.camera.aiwatermark.handler.ScenicSpotsHandler
    public HashMap<String, Region> getRegionMap() {
        AbstractWatermarkData abstractWatermarkData = this.mData;
        if (abstractWatermarkData instanceof ScenicSpotsWatermark) {
            return ((ScenicSpotsWatermark) abstractWatermarkData).getRegionMap(2);
        }
        return null;
    }

    /* access modifiers changed from: protected */
    @Override // com.android.camera.aiwatermark.handler.ScenicSpotsHandler
    public ArrayList<WatermarkItem> getWatermarkList() {
        return this.mData.getForAI();
    }
}
