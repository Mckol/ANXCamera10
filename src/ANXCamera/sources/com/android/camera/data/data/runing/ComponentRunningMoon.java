package com.android.camera.data.data.runing;

import androidx.annotation.NonNull;
import com.android.camera.R;
import com.android.camera.data.data.ComponentData;
import com.android.camera.data.data.ComponentDataItem;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ComponentRunningMoon extends ComponentData {
    public static final String MOON = "moon";
    public static final String NIGHT = "night";

    public ComponentRunningMoon(DataItemRunning dataItemRunning) {
        super(dataItemRunning);
    }

    @Override // com.android.camera.data.data.ComponentData
    @NonNull
    public String getDefaultValue(int i) {
        return NIGHT;
    }

    @Override // com.android.camera.data.data.ComponentData
    public int getDisplayTitleString() {
        return 0;
    }

    @Override // com.android.camera.data.data.ComponentData
    public List<ComponentDataItem> getItems() {
        ArrayList arrayList = new ArrayList();
        arrayList.add(new ComponentDataItem(-1, -1, (int) R.string.ai_scene_top_moon_off, NIGHT));
        arrayList.add(new ComponentDataItem(-1, -1, (int) R.string.ai_scene_top_tip, MOON));
        return Collections.unmodifiableList(arrayList);
    }

    @Override // com.android.camera.data.data.ComponentData
    public String getKey(int i) {
        return "pref_moon_mode_value_key";
    }
}
