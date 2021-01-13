package com.android.camera.aiwatermark.data;

import com.android.camera.aiwatermark.util.WatermarkConstant;
import java.util.ArrayList;

public class FestivalWatermark extends AbstractWatermarkData {
    private ArrayList<WatermarkItem> mChinaTraditionlist = new ArrayList<>();
    private ArrayList<WatermarkItem> mInternationallist = new ArrayList<>();

    private ArrayList<WatermarkItem> getChinaTraditionWM() {
        if (this.mChinaTraditionlist.isEmpty()) {
            this.mChinaTraditionlist.add(new WatermarkItem(WatermarkConstant.SPRING_FESTIVAL, 2, 1, 12));
            this.mChinaTraditionlist.add(new WatermarkItem(WatermarkConstant.DRAGON_BOAT_FESTIVAL, 2, 1, 12));
            this.mChinaTraditionlist.add(new WatermarkItem(WatermarkConstant.MID_AUTUMN_FESTIVAL, 2, 1, 12));
            this.mChinaTraditionlist.add(new WatermarkItem(WatermarkConstant.CHINESE_EVE, 2, 1, 12));
            this.mChinaTraditionlist.add(new WatermarkItem(WatermarkConstant.CHINESE_VALENTINE_DAY, 2, 1, 12));
            this.mChinaTraditionlist.add(new WatermarkItem(WatermarkConstant.LABA_FESTIVAL, 2, 1, 12));
        }
        return this.mChinaTraditionlist;
    }

    private ArrayList<WatermarkItem> getInternationalFestivalWM() {
        if (this.mInternationallist.isEmpty()) {
            this.mInternationallist.add(new WatermarkItem(WatermarkConstant.NEW_YEAR_DAY, 2, 1, 12));
            this.mInternationallist.add(new WatermarkItem(WatermarkConstant.VALENTINE_DAY, 2, 1, 12));
            this.mInternationallist.add(new WatermarkItem(WatermarkConstant.CHILDREN_DAY, 2, 1, 12));
            this.mInternationallist.add(new WatermarkItem(WatermarkConstant.CHRISTMAS_DAY, 2, 1, 12));
        }
        return this.mInternationallist;
    }

    public ArrayList<WatermarkItem> getFestivalWatermark(int i) {
        return i != 0 ? i != 1 ? new ArrayList<>() : getChinaTraditionWM() : getInternationalFestivalWM();
    }

    @Override // com.android.camera.aiwatermark.data.AbstractWatermarkData
    public ArrayList<WatermarkItem> getForAI() {
        return getWatermarkByType(6);
    }

    @Override // com.android.camera.aiwatermark.data.AbstractWatermarkData
    public ArrayList<WatermarkItem> getForManual() {
        return getWatermarkByType(2);
    }
}
