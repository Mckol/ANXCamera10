package com.android.camera.data.data.config;

import android.annotation.TargetApi;
import android.icu.text.DecimalFormat;
import android.icu.text.DecimalFormatSymbols;
import android.text.TextUtils;
import android.util.Range;
import b.c.a.c;
import com.android.camera.CameraSettings;
import com.android.camera.R;
import com.android.camera.data.DataRepository;
import com.android.camera.data.data.ComponentData;
import com.android.camera.data.data.ComponentDataItem;
import com.android.camera.log.Log;
import com.android.camera.module.loader.camera2.Camera2DataContainer;
import com.android.camera2.CameraCapabilities;
import com.xiaomi.stat.b;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class ComponentManuallyEV extends ComponentData {
    private static final float FLOAT_ERROR = 0.001f;
    private static final String TAG = "ComponentManuallyEV";
    private boolean mDisabled;
    private ComponentDataItem[] mFullItems;

    public ComponentManuallyEV(DataItemConfig dataItemConfig) {
        super(dataItemConfig);
    }

    private ComponentDataItem[] getFullItems() {
        ComponentDataItem[] componentDataItemArr = this.mFullItems;
        if (componentDataItemArr != null) {
            return componentDataItemArr;
        }
        this.mIsDisplayStringFromResourceId = true;
        this.mIsKeepValueWhenDisabled = true;
        ArrayList arrayList = new ArrayList();
        CameraCapabilities capabilitiesByBogusCameraId = Camera2DataContainer.getInstance().getCapabilitiesByBogusCameraId(DataRepository.dataItemGlobal().getCurrentCameraId(), DataRepository.dataItemGlobal().getCurrentMode());
        Range<Integer> exposureCompensationRange = capabilitiesByBogusCameraId.getExposureCompensationRange();
        int intValue = exposureCompensationRange.getLower().intValue();
        int intValue2 = exposureCompensationRange.getUpper().intValue();
        if (c.Tg || c.Rg) {
            intValue = -12;
            intValue2 = 12;
        }
        float exposureCompensationStep = ((float) intValue2) * capabilitiesByBogusCameraId.getExposureCompensationStep();
        DecimalFormat decimalFormat = new DecimalFormat(b.m);
        DecimalFormatSymbols decimalFormatSymbols = new DecimalFormatSymbols(Locale.ENGLISH);
        decimalFormatSymbols.setDecimalSeparator('.');
        decimalFormat.setDecimalFormatSymbols(decimalFormatSymbols);
        float exposureCompensationStep2 = capabilitiesByBogusCameraId.getExposureCompensationStep() * 2.0f;
        for (float exposureCompensationStep3 = ((float) intValue) * capabilitiesByBogusCameraId.getExposureCompensationStep(); exposureCompensationStep3 < FLOAT_ERROR + exposureCompensationStep; exposureCompensationStep3 += exposureCompensationStep2) {
            String format = decimalFormat.format((double) exposureCompensationStep3);
            if (format.equals("-0.0") || format.equals(b.m)) {
                format = "0";
            }
            arrayList.add(new ComponentDataItem(-1, -1, exposureCompensationStep3 > 0.01f ? "+" + format : format, format));
        }
        this.mFullItems = (ComponentDataItem[]) arrayList.toArray(new ComponentDataItem[arrayList.size()]);
        return this.mFullItems;
    }

    @Override // com.android.camera.data.data.ComponentData
    public boolean checkValueValid(int i, String str) {
        if (!TextUtils.isEmpty(str)) {
            for (ComponentDataItem componentDataItem : getFullItems()) {
                if (componentDataItem.mValue.equals(str)) {
                    return true;
                }
            }
        }
        Log.d(TAG, "checkValueValid: invalid value!");
        return false;
    }

    @Override // com.android.camera.data.data.ComponentData
    public boolean disableUpdate() {
        return this.mDisabled;
    }

    @Override // com.android.camera.data.data.ComponentData
    public String getComponentValue(int i) {
        String componentValue = super.getComponentValue(i);
        List<ComponentDataItem> items = getItems();
        if (items.isEmpty()) {
            return componentValue;
        }
        String str = items.get(items.size() - 1).mValue;
        if (Float.parseFloat(componentValue) <= Float.parseFloat(str)) {
            return componentValue;
        }
        setComponentValue(i, str);
        return str;
    }

    @Override // com.android.camera.data.data.ComponentData
    public String getDefaultValue(int i) {
        return "0";
    }

    @Override // com.android.camera.data.data.ComponentData
    public int getDisplayTitleString() {
        return R.string.pref_camera_manually_exposure_value_abbr;
    }

    @Override // com.android.camera.data.data.ComponentData
    public List<ComponentDataItem> getItems() {
        return this.mItems;
    }

    @Override // com.android.camera.data.data.ComponentData
    public String getKey(int i) {
        return i != 167 ? CameraSettings.KEY_QC_PRO_VIDEO_EXPOSURE_VALUE : CameraSettings.KEY_QC_MANUAL_EXPOSURE_VALUE;
    }

    @Override // com.android.camera.data.data.ComponentData
    public int getValueDisplayString(int i) {
        String componentValue = getComponentValue(i);
        ComponentDataItem[] fullItems = getFullItems();
        for (ComponentDataItem componentDataItem : fullItems) {
            if (componentDataItem.mValue.equals(componentValue)) {
                return componentDataItem.mDisplayNameRes;
            }
        }
        throw new IllegalArgumentException("invalid value");
    }

    @TargetApi(21)
    public List<ComponentDataItem> reInit(int i, CameraCapabilities cameraCapabilities) {
        if (this.mItems == null) {
            this.mItems = new ArrayList();
        } else {
            this.mItems.clear();
        }
        if (cameraCapabilities == null) {
            return this.mItems;
        }
        if (i == 167 || i == 180) {
            ComponentDataItem[] fullItems = getFullItems();
            this.mItems.add(fullItems[0]);
            Range<Integer> exposureCompensationRange = cameraCapabilities.getExposureCompensationRange();
            if (exposureCompensationRange != null) {
                int intValue = exposureCompensationRange.getLower().intValue();
                int intValue2 = exposureCompensationRange.getUpper().intValue();
                if (c.Tg || c.Rg) {
                    intValue = -12;
                    intValue2 = 12;
                }
                float exposureCompensationStep = cameraCapabilities.getExposureCompensationStep() * 2.0f;
                float f = ((float) intValue) * exposureCompensationStep;
                float f2 = ((float) intValue2) * exposureCompensationStep;
                for (int i2 = 1; i2 < fullItems.length; i2++) {
                    ComponentDataItem componentDataItem = fullItems[i2];
                    float parseFloat = Float.parseFloat(componentDataItem.mValue);
                    if (f <= parseFloat && parseFloat <= f2) {
                        this.mItems.add(componentDataItem);
                    }
                }
            }
        }
        return this.mItems;
    }

    @Override // com.android.camera.data.data.ComponentData
    public void resetComponentValue(int i) {
        super.resetComponentValue(i);
        setComponentValue(i, getDefaultValue(i));
    }

    public void setDisabled(boolean z) {
        this.mDisabled = z;
    }
}
