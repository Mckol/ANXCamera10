package com.android.camera.data.data.runing;

import android.text.TextUtils;
import androidx.collection.SimpleArrayMap;
import com.android.camera.CameraSettings;
import com.android.camera.R;
import com.android.camera.data.DataRepository;
import com.android.camera.data.data.ComponentData;
import com.android.camera.data.data.ComponentDataItem;
import com.android.camera.module.loader.camera2.Camera2DataContainer;
import com.android.camera2.CameraCapabilities;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ComponentRunningLighting extends ComponentData {
    private CameraCapabilities mCapabilities;
    private SimpleArrayMap<String, ComponentDataItem> mTotalDataItems = new SimpleArrayMap<>();

    public ComponentRunningLighting(DataItemRunning dataItemRunning) {
        super(dataItemRunning);
        this.mTotalDataItems.put("1", new ComponentDataItem((int) R.drawable.ic_lighting_nature, (int) R.drawable.ic_lighting_nature, (int) R.string.lighting_pattern_nature, "1"));
        this.mTotalDataItems.put("2", new ComponentDataItem((int) R.drawable.ic_lighting_stage, (int) R.drawable.ic_lighting_stage, (int) R.string.lighting_pattern_stage, "2"));
        this.mTotalDataItems.put("3", new ComponentDataItem((int) R.drawable.ic_lighting_movie, (int) R.drawable.ic_lighting_movie, (int) R.string.lighting_pattern_movie, "3"));
        this.mTotalDataItems.put("4", new ComponentDataItem((int) R.drawable.ic_lighting_rainbow, (int) R.drawable.ic_lighting_rainbow, (int) R.string.lighting_pattern_rainbow, "4"));
        this.mTotalDataItems.put("5", new ComponentDataItem((int) R.drawable.ic_lighting_shutter, (int) R.drawable.ic_lighting_shutter, (int) R.string.lighting_pattern_shutter, "5"));
        this.mTotalDataItems.put("6", new ComponentDataItem((int) R.drawable.ic_lighting_dot, (int) R.drawable.ic_lighting_dot, (int) R.string.lighting_pattern_dot, "6"));
        this.mTotalDataItems.put("7", new ComponentDataItem((int) R.drawable.ic_lighting_leaf, (int) R.drawable.ic_lighting_leaf, (int) R.string.lighting_pattern_leaf, "7"));
        this.mTotalDataItems.put("8", new ComponentDataItem((int) R.drawable.ic_lighting_holi, (int) R.drawable.ic_lighting_holi, (int) R.string.lighting_pattern_holi, "8"));
        this.mTotalDataItems.put("9", new ComponentDataItem((int) R.drawable.ic_2_lighting_neon, (int) R.drawable.ic_2_lighting_neon, (int) R.string.lighting_neon, "9"));
        this.mTotalDataItems.put("10", new ComponentDataItem((int) R.drawable.ic_2_lighting_phantom, (int) R.drawable.ic_2_lighting_phantom, (int) R.string.lighting_phantom, "10"));
        this.mTotalDataItems.put("11", new ComponentDataItem((int) R.drawable.ic_2_lighting_nostalgia, (int) R.drawable.ic_2_lighting_nostalgia, (int) R.string.lighting_nostalgia, "11"));
        this.mTotalDataItems.put("12", new ComponentDataItem((int) R.drawable.ic_2_lighting_rainbow, (int) R.drawable.ic_2_lighting_rainbow, (int) R.string.lighting_rainbow, "12"));
        this.mTotalDataItems.put("13", new ComponentDataItem((int) R.drawable.ic_2_lighting_lanshan, (int) R.drawable.ic_2_lighting_lanshan, (int) R.string.lighting_lanshan, "13"));
        this.mTotalDataItems.put("14", new ComponentDataItem((int) R.drawable.ic_2_lighting_dazzling, (int) R.drawable.ic_2_lighting_dazzling, (int) R.string.lighting_dazzling, "14"));
        this.mTotalDataItems.put("15", new ComponentDataItem((int) R.drawable.ic_2_lighting_gorgeous, (int) R.drawable.ic_2_lighting_gorgeous, (int) R.string.lighting_gorgeous, "15"));
        this.mTotalDataItems.put("16", new ComponentDataItem((int) R.drawable.ic_2_lighting_bright_red, (int) R.drawable.ic_2_lighting_bright_red, (int) R.string.lighting_bright_red, "16"));
        this.mTotalDataItems.put("17", new ComponentDataItem((int) R.drawable.ic_2_lighting_dreamland, (int) R.drawable.ic_2_lighting_dreamland, (int) R.string.lighting_dreamland, "17"));
    }

    @Override // com.android.camera.data.data.ComponentData
    public boolean checkValueValid(int i, String str) {
        List<ComponentDataItem> items = getItems();
        if (items == null) {
            return false;
        }
        for (ComponentDataItem componentDataItem : items) {
            if (TextUtils.equals(str, componentDataItem.mValue)) {
                return true;
            }
        }
        return false;
    }

    @Override // com.android.camera.data.data.ComponentData
    public String getDefaultValue(int i) {
        return "0";
    }

    @Override // com.android.camera.data.data.ComponentData
    public int getDisplayTitleString() {
        return 0;
    }

    @Override // com.android.camera.data.data.ComponentData
    public List<ComponentDataItem> getItems() {
        if (this.mItems == null) {
            this.mItems = initItems();
        }
        return this.mItems;
    }

    @Override // com.android.camera.data.data.ComponentData
    public String getKey(int i) {
        return "pref_portrait_lighting";
    }

    public int getPortraitLightVersion() {
        CameraCapabilities cameraCapabilities = this.mCapabilities;
        if (cameraCapabilities != null) {
            return cameraCapabilities.getPortraitLightingVersion();
        }
        return 1;
    }

    public List<ComponentDataItem> initItems() {
        if (this.mCapabilities == null || this.mTotalDataItems == null) {
            return null;
        }
        ArrayList arrayList = new ArrayList();
        if (this.mCapabilities.getPortraitLightingVersion() < 2) {
            arrayList.add(new ComponentDataItem((int) R.drawable.ic_lighting_none, (int) R.drawable.ic_lighting_none, (int) R.string.lighting_pattern_null, "0"));
        } else {
            arrayList.add(new ComponentDataItem((int) R.drawable.ic_2_lighting_none, (int) R.drawable.ic_2_lighting_none, -1, "0"));
        }
        int[] portraitLightingArray = this.mCapabilities.getPortraitLightingArray();
        if (portraitLightingArray != null) {
            for (int i : portraitLightingArray) {
                Integer valueOf = Integer.valueOf(i);
                if (valueOf != null) {
                    int intValue = valueOf.intValue();
                    if ((!"1".equals(String.valueOf(intValue)) || CameraSettings.isBackCamera() || Camera2DataContainer.getInstance().getBokehFrontCameraId() != -1) && (!"8".equals(String.valueOf(intValue)) || DataRepository.dataItemFeature().Ul())) {
                        arrayList.add(this.mTotalDataItems.get(String.valueOf(intValue)));
                    }
                }
            }
        }
        this.mItems = Collections.unmodifiableList(arrayList);
        return this.mItems;
    }

    @Override // com.android.camera.data.data.ComponentData
    public boolean isSwitchOn(int i) {
        return !getComponentValue(i).equals("0");
    }

    public void reInit(CameraCapabilities cameraCapabilities) {
        this.mCapabilities = cameraCapabilities;
        this.mItems = initItems();
    }
}
