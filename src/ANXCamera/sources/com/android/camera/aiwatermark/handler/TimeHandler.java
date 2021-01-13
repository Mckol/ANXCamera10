package com.android.camera.aiwatermark.handler;

import com.android.camera.aiwatermark.data.TimeWatermark;
import com.android.camera.aiwatermark.data.WatermarkItem;
import java.util.ArrayList;
import java.util.Calendar;

public class TimeHandler extends AbstractHandler {
    protected ArrayList<WatermarkItem> mWatermarkItems = new ArrayList<>();

    public TimeHandler(boolean z) {
        super(z);
        this.mData = new TimeWatermark();
    }

    /* access modifiers changed from: protected */
    @Override // com.android.camera.aiwatermark.handler.AbstractHandler
    public WatermarkItem findWatermark() {
        if (this.mWatermarkItems.isEmpty()) {
            this.mWatermarkItems = this.mData.getForAI();
        }
        return this.mWatermarkItems.get(0);
    }

    public int getDayOfWeek() {
        return Calendar.getInstance().get(7) - 1;
    }
}
