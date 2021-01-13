package com.android.camera.data.data.config;

import android.annotation.TargetApi;
import com.android.camera.CameraSettings;
import com.android.camera.R;
import com.android.camera.data.data.ComponentData;
import com.android.camera.data.data.ComponentDataItem;
import com.android.camera.statistic.MistatsConstants;
import com.android.camera2.CameraCapabilities;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@TargetApi(21)
public class ComponentConfigMeter extends ComponentData {
    public static final String METERING_MODE_CENTER_WEIGHTED = "1";
    public static final String METERING_MODE_FRAME_AVERAGE = "0";
    public static final String METERING_MODE_SPOT_METERING = "2";

    public ComponentConfigMeter(DataItemConfig dataItemConfig) {
        super(dataItemConfig);
        this.mItems = new ArrayList();
        this.mItems.add(new ComponentDataItem(getCenterWeighted()[0], getCenterWeighted()[1], (int) R.string.pref_camera_autoexposure_entry_centerweighted, "1"));
    }

    private int[] getCenterWeighted() {
        return new int[]{R.drawable.ic_new_config_meter_center_weighted, R.drawable.ic_new_config_meter_center_weighted};
    }

    private int[] getFrameAverage() {
        return new int[]{R.drawable.ic_new_config_meter_frame_average, R.drawable.ic_new_config_meter_frame_average};
    }

    private int[] getSpotMetering() {
        return new int[]{R.drawable.ic_new_config_meter_spot_metering, R.drawable.ic_new_config_meter_spot_metering};
    }

    @Override // com.android.camera.data.data.ComponentData
    public String getComponentValue(int i) {
        return isEmpty() ? "1" : super.getComponentValue(i);
    }

    @Override // com.android.camera.data.data.ComponentData
    public String getDefaultValue(int i) {
        return "1";
    }

    @Override // com.android.camera.data.data.ComponentData
    public int getDisplayTitleString() {
        return R.string.pref_camera_autoexposure_title;
    }

    @Override // com.android.camera.data.data.ComponentData
    public List<ComponentDataItem> getItems() {
        return this.mItems;
    }

    @Override // com.android.camera.data.data.ComponentData
    public String getKey(int i) {
        return i != 180 ? CameraSettings.KEY_AUTOEXPOSURE : CameraSettings.KEY_PRO_VIDEO_AUTOEXPOSURE;
    }

    public String getTrackValue(int i) {
        String componentValue = getComponentValue(i);
        return "0".equals(componentValue) ? MistatsConstants.Manual.AVERAGE_PHOTOMETRY : "1".equals(componentValue) ? MistatsConstants.Manual.CENTER_WEIGHT : "2".equals(componentValue) ? MistatsConstants.Manual.CENTER_PHOTOMETRY : MistatsConstants.BaseEvent.UNSPECIFIED;
    }

    public int getValueSelectedDrawableIgnoreClose(int i) {
        String componentValue = getComponentValue(i);
        return "0".equals(componentValue) ? getFrameAverage()[0] : "1".equals(componentValue) ? getCenterWeighted()[0] : "2".equals(componentValue) ? getSpotMetering()[0] : getCenterWeighted()[0];
    }

    public int getValueSelectedShadowDrawableId(int i) {
        String componentValue = getComponentValue(i);
        if ("0".equals(componentValue)) {
            return R.drawable.ic_new_config_meter_frame_average_shadow;
        }
        if ("1".equals(componentValue)) {
            return R.drawable.ic_new_config_meter_center_weighted_shadow;
        }
        if ("2".equals(componentValue)) {
            return R.drawable.ic_new_config_meter_spot_metering_shadow;
        }
        return -1;
    }

    public int getValueSelectedStringIdIgnoreClose(int i) {
        String componentValue = getComponentValue(i);
        if ("0".equals(componentValue)) {
            return R.string.pref_camera_autoexposure_entry_frameaverage;
        }
        if ("1".equals(componentValue)) {
            return R.string.pref_camera_autoexposure_entry_centerweighted;
        }
        if ("2".equals(componentValue)) {
            return R.string.pref_camera_autoexposure_entry_spotmetering;
        }
        return -1;
    }

    public void reInit(int i, int i2, CameraCapabilities cameraCapabilities) {
        ArrayList arrayList = new ArrayList();
        arrayList.add(new ComponentDataItem(getCenterWeighted()[0], (int) R.drawable.ic_new_config_meter_center_weighted_shadow, getCenterWeighted()[1], (int) R.string.pref_camera_autoexposure_entry_centerweighted, "1"));
        arrayList.add(new ComponentDataItem(getFrameAverage()[0], (int) R.drawable.ic_new_config_meter_frame_average_shadow, getFrameAverage()[1], (int) R.string.pref_camera_autoexposure_entry_frameaverage, "0"));
        arrayList.add(new ComponentDataItem(getSpotMetering()[0], (int) R.drawable.ic_new_config_meter_spot_metering_shadow, getSpotMetering()[1], (int) R.string.pref_camera_autoexposure_entry_spotmetering, "2"));
        this.mItems = Collections.unmodifiableList(arrayList);
    }

    @Override // com.android.camera.data.data.ComponentData
    public void setComponentValue(int i, String str) {
        super.setComponentValue(i, str);
    }
}
