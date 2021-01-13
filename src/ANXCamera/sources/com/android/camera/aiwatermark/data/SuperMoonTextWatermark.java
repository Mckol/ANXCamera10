package com.android.camera.aiwatermark.data;

import java.util.ArrayList;

public class SuperMoonTextWatermark extends AbstractWatermarkData {
    @Override // com.android.camera.aiwatermark.data.AbstractWatermarkData
    public ArrayList<WatermarkItem> getForAI() {
        return getWatermarkByType(12);
    }

    @Override // com.android.camera.aiwatermark.data.AbstractWatermarkData
    public ArrayList<WatermarkItem> getForManual() {
        return getWatermarkByType(12);
    }
}
