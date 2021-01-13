package com.android.camera.data.data.config;

import androidx.annotation.NonNull;
import com.android.camera.R;
import com.android.camera.data.data.ComponentData;
import com.android.camera.data.data.ComponentDataItem;
import com.android.camera.data.data.runing.DataItemRunning;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ComponentRunningCinematicAspectRatio extends ComponentData {
    public ComponentRunningCinematicAspectRatio(DataItemRunning dataItemRunning) {
        super(dataItemRunning);
    }

    @Override // com.android.camera.data.data.ComponentData
    @NonNull
    public String getDefaultValue(int i) {
        return null;
    }

    @Override // com.android.camera.data.data.ComponentData
    public int getDisplayTitleString() {
        return R.string.moive_frame;
    }

    @Override // com.android.camera.data.data.ComponentData
    public List<ComponentDataItem> getItems() {
        return this.mItems;
    }

    @Override // com.android.camera.data.data.ComponentData
    public String getKey(int i) {
        if (i == 162) {
            return "is_cinematic_162";
        }
        if (i != 163) {
            if (i == 169) {
                return "is_cinematic_162";
            }
            if (!(i == 171 || i == 173 || i == 180)) {
                return i != 204 ? "is_cinematic_unsupported" : "is_cinematic_162";
            }
        }
        return "is_cinematic_" + i;
    }

    @Override // com.android.camera.data.data.ComponentData
    public boolean isSwitchOn(int i) {
        if (i == 171 || i == 163 || i == 173 || i == 180 || i == 162 || i == 169) {
            return this.mParentDataItem.getBoolean(getKey(i), false);
        }
        return false;
    }

    public void reInit(int i) {
        ArrayList arrayList = new ArrayList();
        if (i == 162 || i == 163 || i == 169 || i == 171 || i == 173 || i == 180) {
            arrayList.add(new ComponentDataItem((int) R.drawable.ic_cinematic_aspect_ratio_off, (int) R.drawable.ic_cinematic_aspect_ratio_on, (int) R.string.accessibility_mimovie_on, "on"));
        }
        this.mItems = Collections.unmodifiableList(arrayList);
    }

    public void setEnabled(int i, boolean z) {
        this.mParentDataItem.putBoolean(getKey(i), z);
    }
}
