package com.android.camera.data.data.runing;

import android.graphics.Rect;
import android.text.TextUtils;
import androidx.annotation.NonNull;
import com.android.camera.CameraSettings;
import com.android.camera.aiwatermark.data.WatermarkItem;
import com.android.camera.aiwatermark.util.WatermarkConstant;
import com.android.camera.data.DataRepository;
import com.android.camera.data.data.ComponentData;
import com.android.camera.data.data.ComponentDataItem;
import com.android.camera.data.data.config.ComponentConfigRatio;
import java.util.ArrayList;
import java.util.List;

public class ComponentRunningAIWatermark extends ComponentData {
    private boolean mBackEnable;
    int mCurrentModule;
    private String mCurrentType;
    private boolean mFrontEnable;
    private boolean mIWatermarkEnable;
    private ArrayList<ComponentDataItem> mList;
    private String mMajorCurrentKey;
    private WatermarkItem mMajorWatermarkItem;
    private String mMinorCurrentKey;
    private WatermarkItem mMinorWatermarkItem;

    public ComponentRunningAIWatermark(DataItemRunning dataItemRunning) {
        super(dataItemRunning);
        this.mCurrentType = String.valueOf(0);
        this.mMajorCurrentKey = WatermarkConstant.AI_TRIGGER;
        this.mMinorCurrentKey = WatermarkConstant.SUPER_MOON_RESET;
        this.mList = new ArrayList<>();
        this.mIWatermarkEnable = true;
        this.mMajorWatermarkItem = null;
        this.mMinorWatermarkItem = null;
        this.mFrontEnable = false;
        this.mBackEnable = false;
    }

    public ComponentRunningAIWatermark(DataItemRunning dataItemRunning, ArrayList<ComponentDataItem> arrayList, int i) {
        this(dataItemRunning);
        this.mList = arrayList;
        this.mCurrentModule = i;
        resetAIWatermark(false);
    }

    private void updateDualWatermarkItem(WatermarkItem watermarkItem) {
        if (this.mMajorWatermarkItem == null && this.mMinorWatermarkItem == null) {
            this.mMajorWatermarkItem = watermarkItem;
        } else {
            WatermarkItem watermarkItem2 = this.mMajorWatermarkItem;
            if (watermarkItem2 == null || watermarkItem2.getType() != watermarkItem.getType()) {
                WatermarkItem watermarkItem3 = this.mMinorWatermarkItem;
                if (watermarkItem3 == null || watermarkItem3.getType() != watermarkItem.getType()) {
                    this.mMinorWatermarkItem = watermarkItem;
                } else {
                    this.mMinorWatermarkItem = watermarkItem;
                }
            } else {
                this.mMajorWatermarkItem = watermarkItem;
            }
        }
        WatermarkItem watermarkItem4 = this.mMinorWatermarkItem;
        if (watermarkItem4 != null && this.mMajorWatermarkItem != null && watermarkItem4.getType() < this.mMajorWatermarkItem.getType()) {
            WatermarkItem watermarkItem5 = this.mMajorWatermarkItem;
            this.mMajorWatermarkItem = this.mMinorWatermarkItem;
            this.mMinorWatermarkItem = watermarkItem5;
        }
    }

    public boolean getAIWatermarkEnable() {
        if (needForceEnable()) {
            return true;
        }
        try {
            return Boolean.valueOf(getComponentValue(205)).booleanValue();
        } catch (Exception unused) {
            return false;
        }
    }

    public boolean getAIWatermarkEnable(int i) {
        if (needForceEnable()) {
            return true;
        }
        return getAIWatermarkEnable() && supportTopConfigEntry(i);
    }

    public String getCurrentKey() {
        return isDualWatermark() ? TextUtils.equals(this.mCurrentType, String.valueOf(11)) ? this.mMajorCurrentKey : this.mMinorCurrentKey : this.mMajorCurrentKey;
    }

    public String getCurrentType() {
        return this.mCurrentType;
    }

    @Override // com.android.camera.data.data.ComponentData
    @NonNull
    public String getDefaultValue(int i) {
        return "off";
    }

    @Override // com.android.camera.data.data.ComponentData
    public int getDisplayTitleString() {
        return 0;
    }

    public boolean getEnable(int i) {
        if (i == 0) {
            return this.mBackEnable;
        }
        if (i != 1) {
            return false;
        }
        return this.mFrontEnable;
    }

    public boolean getIWatermarkEnable() {
        return this.mIWatermarkEnable;
    }

    @Override // com.android.camera.data.data.ComponentData
    public List<ComponentDataItem> getItems() {
        return this.mList;
    }

    @Override // com.android.camera.data.data.ComponentData
    public String getKey(int i) {
        return "pref_watermark_key";
    }

    public WatermarkItem getMajorWatermarkItem() {
        if (getAIWatermarkEnable()) {
            return this.mMajorWatermarkItem;
        }
        return null;
    }

    public WatermarkItem getMinorWatermarkItem() {
        if (!getAIWatermarkEnable() || !isDualWatermark()) {
            return null;
        }
        return this.mMinorWatermarkItem;
    }

    public boolean getSuperMoonTextEnable() {
        if (this.mCurrentModule != 188) {
            return false;
        }
        WatermarkItem watermarkItem = this.mMajorWatermarkItem;
        if (watermarkItem != null && watermarkItem.getType() == 12 && !TextUtils.equals(this.mMajorWatermarkItem.getKey(), WatermarkConstant.SUPER_MOON_RESET)) {
            return true;
        }
        WatermarkItem watermarkItem2 = this.mMinorWatermarkItem;
        return watermarkItem2 != null && watermarkItem2.getType() == 12 && !TextUtils.equals(this.mMinorWatermarkItem.getKey(), WatermarkConstant.SUPER_MOON_RESET);
    }

    public boolean isDualWatermark() {
        return this.mCurrentModule == 188;
    }

    public boolean isFixedLocation() {
        return this.mCurrentModule == 188;
    }

    public boolean isFixedOrientation() {
        return this.mCurrentModule == 188;
    }

    public boolean isSwitchOn() {
        WatermarkItem watermarkItem;
        WatermarkItem watermarkItem2 = this.mMajorWatermarkItem;
        return (watermarkItem2 != null && !TextUtils.equals(WatermarkConstant.SUPER_MOON_RESET, watermarkItem2.getKey())) || ((watermarkItem = this.mMinorWatermarkItem) != null && !TextUtils.equals(WatermarkConstant.SUPER_MOON_RESET, watermarkItem.getKey()));
    }

    public boolean needActive() {
        return getAIWatermarkEnable() && getIWatermarkEnable();
    }

    public boolean needForceDisable(int i) {
        if (needForceEnable()) {
            return false;
        }
        DataRepository.dataItemRunning().getComponentRunningAIWatermark();
        return !TextUtils.equals(ComponentConfigRatio.RATIO_4X3, DataRepository.dataItemConfig().getComponentConfigRatio().getComponentValue(i)) || CameraSettings.isMacroModeEnabled(i) || i != 163;
    }

    public boolean needForceEnable() {
        return this.mCurrentModule == 188;
    }

    public boolean needMoveUp(int i) {
        return getAIWatermarkEnable() && i == 205;
    }

    public void resetAIWatermark(boolean z) {
        if (!z) {
            setAIWatermarkEnable(false);
            setIWatermarkEnable(true);
        }
        this.mMajorWatermarkItem = null;
        this.mMinorWatermarkItem = null;
        if (this.mCurrentModule == 188) {
            this.mMajorCurrentKey = WatermarkConstant.SUPER_MOON_RESET;
            this.mMinorCurrentKey = WatermarkConstant.SUPER_MOON_RESET;
            this.mCurrentType = String.valueOf(11);
            return;
        }
        this.mMajorCurrentKey = WatermarkConstant.AI_TRIGGER;
        this.mCurrentType = String.valueOf(0);
    }

    public void setAIWatermarkEnable(boolean z) {
        setComponentValue(205, String.valueOf(z));
    }

    public void setCurrentKey(String str) {
        if (!isDualWatermark()) {
            this.mMajorCurrentKey = str;
        } else if (TextUtils.equals(this.mCurrentType, String.valueOf(11))) {
            this.mMajorCurrentKey = str;
        } else {
            this.mMinorCurrentKey = str;
        }
    }

    public void setCurrentType(String str) {
        this.mCurrentType = str;
    }

    public void setEnable(int i, boolean z) {
        if (i == 0) {
            this.mBackEnable = z;
        } else if (i == 1) {
            this.mFrontEnable = z;
        }
    }

    public void setHasMove(boolean z) {
        WatermarkItem watermarkItem = this.mMajorWatermarkItem;
        if (watermarkItem != null) {
            watermarkItem.setHasMove(z);
        }
        WatermarkItem watermarkItem2 = this.mMinorWatermarkItem;
        if (watermarkItem2 != null) {
            watermarkItem2.setHasMove(z);
        }
    }

    public void setIWatermarkEnable(boolean z) {
        this.mIWatermarkEnable = z;
    }

    public boolean supportTopConfigEntry(int i) {
        return i == 163 || i == 188 || i == 205;
    }

    public void updateLocation(int[] iArr, Rect rect, int i) {
        WatermarkItem watermarkItem = this.mMajorWatermarkItem;
        if (watermarkItem == null || !(watermarkItem.getType() == i || i == -1)) {
            WatermarkItem watermarkItem2 = this.mMinorWatermarkItem;
            if (watermarkItem2 != null) {
                watermarkItem2.updateCoordinate(iArr);
                this.mMinorWatermarkItem.setLimitArea(rect);
                return;
            }
            return;
        }
        this.mMajorWatermarkItem.updateCoordinate(iArr);
        this.mMajorWatermarkItem.setLimitArea(rect);
    }

    public void updateWatermarkItem(WatermarkItem watermarkItem) {
        if (watermarkItem == null) {
            this.mMajorWatermarkItem = null;
            this.mMinorWatermarkItem = null;
        } else if (isDualWatermark()) {
            updateDualWatermarkItem(watermarkItem);
        } else {
            this.mMajorWatermarkItem = watermarkItem;
        }
    }
}
