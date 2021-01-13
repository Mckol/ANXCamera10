package com.android.camera.data.data.runing;

import androidx.annotation.NonNull;
import com.android.camera.R;
import com.android.camera.data.data.ComponentData;
import com.android.camera.data.data.ComponentDataItem;
import com.android.camera.statistic.CameraStatUtils;
import java.util.ArrayList;
import java.util.List;

public class ComponentRunningEisPro extends ComponentData {
    public static final String EIS_VALUE_NORMAL = "normal";
    public static final String EIS_VALUE_OFF = "off";
    public static final String EIS_VALUE_PRO = "pro";
    public String mPreValue = "off";

    public ComponentRunningEisPro(DataItemRunning dataItemRunning) {
        super(dataItemRunning);
        this.mItems = new ArrayList();
        this.mItems.add(new ComponentDataItem(getConfigEisOffRes(), getConfigEisOffRes(), (int) R.string.pref_camera_eis_entry_off, "off"));
        this.mItems.add(new ComponentDataItem(getConfigEisNormalRes(), getConfigEisNormalRes(), (int) R.string.pref_camera_eis_entry_normal, "normal"));
        this.mItems.add(new ComponentDataItem(getConfigEisProRes(), getConfigEisProRes(), (int) R.string.pref_camera_eis_entry_pro, EIS_VALUE_PRO));
    }

    private int getConfigEisNormalRes() {
        return R.drawable.ic_config_super_eis_on;
    }

    private int getConfigEisOffRes() {
        return R.drawable.ic_config_super_eis_off;
    }

    private int getConfigEisProRes() {
        return R.drawable.ic_config_super_eis_pro_on;
    }

    public String getComponentPreValue() {
        return this.mPreValue;
    }

    @Override // com.android.camera.data.data.ComponentData
    public String getComponentValue(int i) {
        return super.getComponentValue(i);
    }

    @Override // com.android.camera.data.data.ComponentData
    @NonNull
    public String getDefaultValue(int i) {
        if (isEmpty()) {
        }
        return "off";
    }

    @Override // com.android.camera.data.data.ComponentData
    public int getDisplayTitleString() {
        return R.string.pref_camera_eis_title;
    }

    @Override // com.android.camera.data.data.ComponentData
    public List<ComponentDataItem> getItems() {
        return this.mItems;
    }

    @Override // com.android.camera.data.data.ComponentData
    public String getKey(int i) {
        if (i != 160) {
            return "pref_eis_pro" + i;
        }
        throw new RuntimeException("unspecified EIS");
    }

    public int getValueSelectedDrawableIgnoreClose(int i) {
        String componentValue = getComponentValue(i);
        if ("off".equals(componentValue)) {
            return getConfigEisOffRes();
        }
        if ("normal".equals(componentValue)) {
            return getConfigEisNormalRes();
        }
        if (EIS_VALUE_PRO.equals(componentValue)) {
            return getConfigEisProRes();
        }
        return -1;
    }

    public int getValueSelectedStringIdIgnoreClose(int i) {
        String componentValue = getComponentValue(i);
        if ("off".equals(componentValue)) {
            return R.string.accessibility_eis_off;
        }
        if ("normal".equals(componentValue)) {
            return R.string.super_eis;
        }
        if (EIS_VALUE_PRO.equals(componentValue)) {
            return R.string.accessibility_eis_pro;
        }
        return -1;
    }

    @Override // com.android.camera.data.data.ComponentData
    public boolean isEmpty() {
        return this.mItems == null || this.mItems.isEmpty();
    }

    public void setComponentPreValue(String str) {
        this.mPreValue = str;
    }

    @Override // com.android.camera.data.data.ComponentData
    public void setComponentValue(int i, String str) {
        super.setComponentValue(i, str);
        CameraStatUtils.trackSuperEisPro(i, str);
    }
}
