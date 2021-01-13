package com.android.camera.data.data.config;

import androidx.annotation.NonNull;
import com.android.camera.R;
import com.android.camera.data.DataRepository;
import com.android.camera.data.data.ComponentData;
import com.android.camera.data.data.ComponentDataItem;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ComponentConfigSlowMotion extends ComponentData {
    public static final String DATA_CONFIG_NEW_SLOW_MOTION_120 = "slow_motion_120";
    public static final String DATA_CONFIG_NEW_SLOW_MOTION_240 = "slow_motion_240";
    public static final String DATA_CONFIG_NEW_SLOW_MOTION_960 = "slow_motion_960";

    public ComponentConfigSlowMotion(DataItemConfig dataItemConfig, int i) {
        super(dataItemConfig);
        reInit(i);
    }

    private boolean isDefaultValue(int i) {
        return getComponentValue(i).equals(getDefaultValue(i));
    }

    @Override // com.android.camera.data.data.ComponentData
    @NonNull
    public String getDefaultValue(int i) {
        return this.mItems.size() == 0 ? "" : DATA_CONFIG_NEW_SLOW_MOTION_120;
    }

    @Override // com.android.camera.data.data.ComponentData
    public int getDisplayTitleString() {
        return 0;
    }

    @Override // com.android.camera.data.data.ComponentData
    public List<ComponentDataItem> getItems() {
        return this.mItems;
    }

    @Override // com.android.camera.data.data.ComponentData
    public String getKey(int i) {
        return DataItemConfig.DATA_CONFIG_NEW_SLOW_MOTION_KEY;
    }

    public int getValueSelectedStringIdIgnoreClose(int i) {
        if (isDefaultValue(i)) {
            return -1;
        }
        String componentValue = getComponentValue(i);
        if (DATA_CONFIG_NEW_SLOW_MOTION_120.equals(componentValue)) {
            return R.string.accessibility_camera_video_960fps_120;
        }
        if (DATA_CONFIG_NEW_SLOW_MOTION_240.equals(componentValue)) {
            return R.string.accessibility_camera_video_960fps_240;
        }
        if (DATA_CONFIG_NEW_SLOW_MOTION_960.equals(componentValue)) {
            return R.string.accessibility_camera_video_960fps_960;
        }
        return -1;
    }

    public boolean isSlowMotionFps120() {
        return DATA_CONFIG_NEW_SLOW_MOTION_120.equals(getComponentValue(172));
    }

    public boolean isSlowMotionFps960() {
        return DATA_CONFIG_NEW_SLOW_MOTION_960.equals(getComponentValue(172));
    }

    public void reInit(int i) {
        ArrayList arrayList = new ArrayList();
        if (i == 0) {
            if (DataRepository.dataItemFeature().yk()) {
                arrayList.add(new ComponentDataItem((int) R.drawable.ic_new_video_960fps_960, (int) R.drawable.ic_new_video_960fps_960, (int) R.string.accessibility_camera_video_960fps_960, DATA_CONFIG_NEW_SLOW_MOTION_960));
                arrayList.add(new ComponentDataItem((int) R.drawable.ic_new_video_960fps_240, (int) R.drawable.ic_new_video_960fps_240, (int) R.string.accessibility_camera_video_960fps_240, DATA_CONFIG_NEW_SLOW_MOTION_240));
                arrayList.add(new ComponentDataItem((int) R.drawable.ic_new_video_960fps_120, (int) R.drawable.ic_new_video_960fps_120, (int) R.string.accessibility_camera_video_960fps_120, DATA_CONFIG_NEW_SLOW_MOTION_120));
            } else if (DataRepository.dataItemFeature().xk()) {
                arrayList.add(new ComponentDataItem((int) R.drawable.ic_new_video_960fps_120, (int) R.drawable.ic_new_video_960fps_120, (int) R.string.accessibility_camera_video_960fps_120, DATA_CONFIG_NEW_SLOW_MOTION_120));
                arrayList.add(new ComponentDataItem((int) R.drawable.ic_new_video_960fps_240, (int) R.drawable.ic_new_video_960fps_240, (int) R.string.accessibility_camera_video_960fps_240, DATA_CONFIG_NEW_SLOW_MOTION_240));
            } else if (DataRepository.dataItemFeature().Ck()) {
                arrayList.add(new ComponentDataItem((int) R.drawable.ic_new_video_960fps_120, (int) R.drawable.ic_new_video_960fps_120, (int) R.string.accessibility_camera_video_960fps_120, DATA_CONFIG_NEW_SLOW_MOTION_120));
            }
        } else if (DataRepository.dataItemFeature().Ak()) {
            arrayList.add(new ComponentDataItem((int) R.drawable.ic_new_video_960fps_120, (int) R.drawable.ic_new_video_960fps_120, (int) R.string.accessibility_camera_video_960fps_120, DATA_CONFIG_NEW_SLOW_MOTION_120));
        }
        this.mItems = Collections.unmodifiableList(arrayList);
    }

    public void setVideoNewSlowMotionFPS(String str) {
        setComponentValue(172, str);
    }
}
