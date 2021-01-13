package com.android.camera.module.loader;

import com.android.camera.Camera;
import com.android.camera.data.DataRepository;
import com.android.camera.data.data.global.DataItemGlobal;
import com.android.camera.data.data.runing.DataItemRunning;
import com.android.camera.effect.EffectController;
import com.android.camera.log.Log;
import com.android.camera.module.BaseModule;
import com.android.camera.module.loader.camera2.Camera2DataContainer;
import com.xiaomi.camera.device.exception.CameraNotOpenException;
import io.reactivex.annotations.NonNull;

public class FunctionModuleSetup extends Func1Base<BaseModule, BaseModule> {
    private static final String TAG = "FunctionModuleSetup";

    public FunctionModuleSetup(int i) {
        super(i);
    }

    public NullHolder<BaseModule> apply(@NonNull NullHolder<BaseModule> nullHolder) throws Exception {
        String str = TAG;
        Log.d(str, "apply: module isPresent = " + nullHolder.isPresent());
        if (!nullHolder.isPresent()) {
            return nullHolder;
        }
        BaseModule baseModule = nullHolder.get();
        if (baseModule.isDeparted()) {
            return NullHolder.ofNullable(baseModule, 225);
        }
        EffectController.getInstance().reset();
        Camera activity = baseModule.getActivity();
        DataItemGlobal dataItemGlobal = DataRepository.dataItemGlobal();
        DataItemRunning dataItemRunning = DataRepository.dataItemRunning();
        int i = this.mTargetMode;
        if (i == 162) {
            dataItemRunning.switchOff("pref_video_speed_fast_key");
        } else if (i == 163) {
            dataItemRunning.switchOn("pref_camera_square_mode_key");
        } else if (i == 165) {
            dataItemRunning.switchOn("pref_camera_square_mode_key");
        } else if (i == 167) {
            dataItemRunning.switchOn("pref_camera_manual_mode_key");
        } else if (i == 169) {
            dataItemRunning.switchOn("pref_video_speed_fast_key");
        } else if (i != 171) {
            if (i == 186) {
                dataItemRunning.getComponentRunningDocument().setEnabled(true);
            } else if (i == 205) {
                dataItemRunning.getComponentRunningAIWatermark().setAIWatermarkEnable(true);
            } else if (i != 174) {
                if (i == 175) {
                    int currentCameraId = dataItemGlobal.getCurrentCameraId();
                    dataItemRunning.getComponentUltraPixel().switchOnCurrentSupported(175, currentCameraId, Camera2DataContainer.getInstance().getCapabilitiesByBogusCameraId(currentCameraId, 175));
                }
            } else if (activity.startFromKeyguard()) {
                DataRepository.dataItemLive().setRecordSegmentTimeInfo(null);
            }
        } else if (dataItemGlobal.getCurrentCameraId() == 0) {
            dataItemRunning.switchOn("pref_camera_portrait_mode_key");
        } else {
            dataItemRunning.switchOff("pref_camera_portrait_mode_key");
        }
        if (baseModule.isDeparted()) {
            return NullHolder.ofNullable(baseModule, 225);
        }
        try {
            baseModule.onCreate(this.mTargetMode, dataItemGlobal.getCurrentCameraId());
            if (baseModule.isCreated()) {
                baseModule.registerProtocol();
                baseModule.onResume();
            }
            return nullHolder;
        } catch (CameraNotOpenException | IllegalArgumentException e2) {
            Log.e(TAG, "Module init error: ", e2);
            baseModule.setDeparted();
            return NullHolder.ofNullable(null, 237);
        }
    }
}
