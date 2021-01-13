package com.android.camera.data.data.runing;

import com.android.camera.R;
import com.android.camera.data.data.ComponentData;
import com.android.camera.data.data.ComponentDataItem;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ComponentRunningTiltValue extends ComponentData {
    public static final String TILT_CIRCLE = "circle";
    public static final String TILT_PARALLEL = "parallel";

    public ComponentRunningTiltValue(DataItemRunning dataItemRunning) {
        super(dataItemRunning);
    }

    private List<ComponentDataItem> initItems() {
        ArrayList arrayList = new ArrayList();
        arrayList.add(new ComponentDataItem((int) R.drawable.ic_config_tilt_circle, (int) R.drawable.ic_config_tilt_circle, (int) R.string.pref_camera_tilt_shift_entry_circle, TILT_CIRCLE));
        arrayList.add(new ComponentDataItem((int) R.drawable.ic_config_tilt_parallel, (int) R.drawable.ic_config_tilt_parallel, (int) R.string.pref_camera_tilt_shift_entry_parallel, TILT_PARALLEL));
        return Collections.unmodifiableList(arrayList);
    }

    @Override // com.android.camera.data.data.ComponentData
    public String getDefaultValue(int i) {
        return TILT_CIRCLE;
    }

    @Override // com.android.camera.data.data.ComponentData
    public int getDisplayTitleString() {
        return R.string.pref_camera_tilt_shift_title;
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
        return "pref_camera_tilt_shift_key";
    }

    @Override // com.android.camera.data.data.ComponentData
    public boolean isSwitchOn(int i) {
        return this.mParentDataItem.getBoolean("pref_camera_tilt_shift_mode", false);
    }

    @Override // com.android.camera.data.data.ComponentData
    public void toSwitch(int i, boolean z) {
        this.mParentDataItem.putBoolean("pref_camera_tilt_shift_mode", z);
    }
}
