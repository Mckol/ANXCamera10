package com.android.camera.data.data.runing;

import android.annotation.TargetApi;
import android.text.TextUtils;
import com.android.camera.CameraSettings;
import com.android.camera.R;
import com.android.camera.constant.FastMotionConstant;
import com.android.camera.data.data.ComponentData;
import com.android.camera.data.data.ComponentDataItem;
import com.android.camera.log.Log;
import com.android.camera2.CameraCapabilities;
import java.util.ArrayList;
import java.util.List;

public class ComponentRunningFastMotionDuration extends ComponentData {
    private static final String TAG = "ComponentRunningFastMotionDuration";
    private boolean mDisabled;
    private ComponentDataItem[] mFullItems;

    public ComponentRunningFastMotionDuration(DataItemRunning dataItemRunning) {
        super(dataItemRunning);
    }

    private ComponentDataItem[] getFullItems() {
        ComponentDataItem[] componentDataItemArr = this.mFullItems;
        if (componentDataItemArr != null) {
            return componentDataItemArr;
        }
        this.mIsDisplayStringFromResourceId = true;
        this.mIsKeepValueWhenDisabled = true;
        ArrayList arrayList = new ArrayList();
        arrayList.add(new ComponentDataItem(-1, -1, "0", "0"));
        arrayList.add(new ComponentDataItem(-1, -1, "10", "10"));
        arrayList.add(new ComponentDataItem(-1, -1, "20", "20"));
        arrayList.add(new ComponentDataItem(-1, -1, "30", "30"));
        arrayList.add(new ComponentDataItem(-1, -1, "40", "40"));
        arrayList.add(new ComponentDataItem(-1, -1, FastMotionConstant.FAST_MOTION_DURATION_50, FastMotionConstant.FAST_MOTION_DURATION_50));
        arrayList.add(new ComponentDataItem(-1, -1, "60", "60"));
        arrayList.add(new ComponentDataItem(-1, -1, FastMotionConstant.FAST_MOTION_DURATION_80, FastMotionConstant.FAST_MOTION_DURATION_80));
        arrayList.add(new ComponentDataItem(-1, -1, FastMotionConstant.FAST_MOTION_DURATION_100, FastMotionConstant.FAST_MOTION_DURATION_100));
        arrayList.add(new ComponentDataItem(-1, -1, "120", "120"));
        arrayList.add(new ComponentDataItem(-1, -1, FastMotionConstant.FAST_MOTION_DURATION_160, FastMotionConstant.FAST_MOTION_DURATION_160));
        arrayList.add(new ComponentDataItem(-1, -1, "200", "200"));
        arrayList.add(new ComponentDataItem(-1, -1, "240", "240"));
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
    public String getDefaultValue(int i) {
        return "0";
    }

    @Override // com.android.camera.data.data.ComponentData
    public int getDisplayTitleString() {
        return R.string.pref_camera_fastmotion_duration;
    }

    @Override // com.android.camera.data.data.ComponentData
    public List<ComponentDataItem> getItems() {
        return this.mItems;
    }

    @Override // com.android.camera.data.data.ComponentData
    public String getKey(int i) {
        return CameraSettings.KEY_NEW_VIDEO_TIME_LAPSE_DURATION;
    }

    public boolean isModified() {
        return !getComponentValue(160).equals(getDefaultValue(160));
    }

    @TargetApi(21)
    public List<ComponentDataItem> reInit(int i, CameraCapabilities cameraCapabilities) {
        ComponentDataItem[] fullItems;
        if (this.mItems == null) {
            this.mItems = new ArrayList();
        } else {
            this.mItems.clear();
        }
        if (i == 169) {
            for (ComponentDataItem componentDataItem : getFullItems()) {
                this.mItems.add(componentDataItem);
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
