package com.android.camera.data.data.runing;

import com.android.camera.R;
import com.android.camera.data.data.ComponentData;
import com.android.camera.data.data.ComponentDataItem;
import java.util.ArrayList;
import java.util.List;

public class ComponentRunningTimer extends ComponentData {
    public static final String TIMER_0 = "0";
    public static final String TIMER_10 = "10";
    public static final String TIMER_3 = "3";
    public static final String TIMER_5 = "5";

    public ComponentRunningTimer(DataItemRunning dataItemRunning) {
        super(dataItemRunning);
    }

    private List<ComponentDataItem> initItems() {
        ArrayList arrayList = new ArrayList();
        arrayList.add(new ComponentDataItem((int) R.drawable.ic_config_timer, (int) R.drawable.ic_config_timer, (int) R.string.pref_camera_delay_capture_title, "0", false, 654311423));
        arrayList.add(new ComponentDataItem(-1, -1, (int) R.string.pref_camera_delay_capture_entry_3s, "3", true, 0));
        arrayList.add(new ComponentDataItem(-1, -1, (int) R.string.pref_camera_delay_capture_entry_5s, "5", true, 0));
        arrayList.add(new ComponentDataItem(-1, -1, (int) R.string.pref_camera_delay_capture_entry_10s, "10", true, 0));
        return arrayList;
    }

    @Override // com.android.camera.data.data.ComponentData
    public String getDefaultValue(int i) {
        return "0";
    }

    @Override // com.android.camera.data.data.ComponentData
    public int getDisplayTitleString() {
        return R.string.pref_camera_delay_capture_title;
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
        return "pref_delay_capture_mode";
    }

    public String getNextValue() {
        String componentValue = getComponentValue(160);
        if ("0".equals(componentValue)) {
            return "3";
        }
        if ("3".equals(componentValue)) {
            return "5";
        }
        if ("5".equals(componentValue)) {
        }
        return "0";
    }

    public int getTimer() {
        return Integer.valueOf(getComponentValue(160)).intValue();
    }

    public boolean isSwitchOn() {
        return !getComponentValue(160).equals("0");
    }

    public void switchOff() {
        setComponentValue(160, "0");
    }
}
