package com.android.camera.data.data.config;

import com.android.camera.R;
import com.android.camera.ambilight.AmbilightEngine;
import com.android.camera.data.DataRepository;
import com.android.camera.data.data.ComponentData;
import com.android.camera.data.data.ComponentDataItem;
import com.android.camera.data.data.runing.DataItemRunning;
import com.android.camera.fragment.ambilight.AmbilightInfo;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ComponentConfigAmbilight extends ComponentData {
    public ComponentConfigAmbilight(DataItemRunning dataItemRunning) {
        super(dataItemRunning);
    }

    public int getAmbilightModeIndex() {
        int ambilightMode = DataRepository.dataItemLive().getAmbilightMode();
        if (ambilightMode == -1) {
            return 0;
        }
        return ambilightMode;
    }

    @Override // com.android.camera.data.data.ComponentData
    public String getDefaultValue(int i) {
        return String.valueOf(0);
    }

    @Override // com.android.camera.data.data.ComponentData
    public int getDisplayTitleString() {
        return R.string.parameter_ambilight_title;
    }

    @Override // com.android.camera.data.data.ComponentData
    public List<ComponentDataItem> getItems() {
        return this.mItems;
    }

    @Override // com.android.camera.data.data.ComponentData
    public String getKey(int i) {
        return "pref_camera_shader_coloreffect_key";
    }

    @AmbilightEngine.AmbilightSceneMode
    public int getSceneModeByIndex(int i) {
        if (i == 1) {
            return 0;
        }
        if (i == 2) {
            return 1;
        }
        if (i == 3) {
            return 2;
        }
        if (i != 4) {
            return i != 5 ? 4 : 3;
        }
        return 5;
    }

    public void mapToItems(ArrayList<AmbilightInfo> arrayList, int i) {
        this.mItems = new ArrayList(arrayList.size());
        Iterator<AmbilightInfo> it = arrayList.iterator();
        while (it.hasNext()) {
            AmbilightInfo next = it.next();
            this.mItems.add(new ComponentDataItem(next.getIconResId(), next.getIconResId(), next.getNameResId(), String.valueOf(next.getSceneMode())));
        }
    }

    public void setAmbilightModeValue(int i) {
        DataRepository.dataItemLive().setAmbilightMode(i);
    }
}
