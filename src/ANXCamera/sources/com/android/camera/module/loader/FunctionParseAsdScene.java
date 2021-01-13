package com.android.camera.module.loader;

import android.hardware.camera2.CaptureResult;
import android.util.Log;
import com.android.camera.constant.AsdSceneConstant;
import com.android.camera.data.DataRepository;
import com.android.camera.module.BaseModule;
import com.android.camera.module.ModuleManager;
import com.android.camera2.Camera2Proxy;
import com.android.camera2.CaptureResultParser;
import io.reactivex.functions.Function;
import java.lang.ref.WeakReference;

public class FunctionParseAsdScene implements Function<CaptureResult, Integer>, AsdSceneConstant {
    private static final float AEC_LUX_HEIGHT_LIGHT = ((float) DataRepository.dataItemFeature().oh());
    private static final float AEC_LUX_LAST_LIGHT = ((float) DataRepository.dataItemFeature().ph());
    private static final float AEC_LUX_LOW_LIGHT = 450.0f;
    private static final boolean DEBUG = Log.isLoggable(TAG, 3);
    private static final int FRAME_BYPASS_NUMBER = 2;
    private static final float LENS_FOCUS_DISTANCE_TOO_CLOSE = 2.5f;
    private static final float LENS_FOCUS_DISTANCE_TOO_FAR = 0.5f;
    private static final String TAG = "FunctionParseAsdScene";
    private static int mFrameNumber;
    private static boolean mIsFlashRetain;
    private WeakReference<BaseModule> mModuleWeakReference;

    public FunctionParseAsdScene(BaseModule baseModule) {
        this.mModuleWeakReference = new WeakReference<>(baseModule);
    }

    private static float getLowLightValue() {
        return ModuleManager.isFunARModule() ? (float) DataRepository.dataItemFeature().getConfig().J() : AEC_LUX_LOW_LIGHT;
    }

    public Integer apply(CaptureResult captureResult) {
        BaseModule baseModule = this.mModuleWeakReference.get();
        if (baseModule != null) {
            Camera2Proxy cameraDevice = baseModule.getCameraDevice();
            boolean isNeedFlashForAuto = cameraDevice != null ? cameraDevice.isNeedFlashForAuto((Integer) captureResult.get(CaptureResult.CONTROL_AE_STATE), cameraDevice.getFlashMode()) : false;
            boolean isFrontCamera = baseModule.isFrontCamera();
            boolean isScreenSlideOff = baseModule.getActivity() != null ? baseModule.getActivity().isScreenSlideOff() : false;
            if (baseModule.isPortraitMode() || isFrontCamera || baseModule.isMimojiMode()) {
                return Integer.valueOf(parseRtbSceneResult(captureResult, isFrontCamera, isScreenSlideOff));
            }
            if (isNeedFlashForAuto) {
                return (!(baseModule instanceof Camera2Proxy.ASDSceneCallback) || !((Camera2Proxy.ASDSceneCallback) baseModule).isAutoFlashOff()) ? 0 : -1;
            }
        }
        return -1;
    }

    public int parseRtbSceneResult(CaptureResult captureResult, boolean z, boolean z2) {
        int i;
        float aecLux = CaptureResultParser.getAecLux(captureResult);
        if (!z) {
            mIsFlashRetain = false;
            float lowLightValue = getLowLightValue();
            if (DEBUG) {
                com.android.camera.log.Log.d(TAG, "<back facing>aecLux:" + aecLux + ",low_light_value:" + lowLightValue);
            }
            if (aecLux > lowLightValue) {
                return 6;
            }
            if (captureResult.get(CaptureResult.LENS_FOCUS_DISTANCE) == null) {
                return -1;
            }
            float floatValue = ((Float) captureResult.get(CaptureResult.LENS_FOCUS_DISTANCE)).floatValue();
            if (floatValue >= LENS_FOCUS_DISTANCE_TOO_CLOSE) {
                return 4;
            }
            return floatValue <= 0.5f ? 5 : 7;
        } else if (z2) {
            mFrameNumber = 0;
            return -1;
        } else if (!DataRepository.dataItemFeature().Ri() || (i = mFrameNumber) >= 2) {
            float f = ModuleManager.isFunARModule() ? AEC_LUX_HEIGHT_LIGHT * 0.87f : AEC_LUX_HEIGHT_LIGHT;
            if (DEBUG) {
                com.android.camera.log.Log.d(TAG, "<front facing>aecLux:" + aecLux + ",AEC_LUX_LAST_LIGHT:" + AEC_LUX_LAST_LIGHT + ",,low_light_value:" + f + ",mIsFlashRetain:" + mIsFlashRetain);
            }
            if (mIsFlashRetain && aecLux > f) {
                return 9;
            }
            if (aecLux > AEC_LUX_LAST_LIGHT) {
                mIsFlashRetain = true;
                return 9;
            }
            mIsFlashRetain = false;
            return -1;
        } else {
            mFrameNumber = i + 1;
            return -1;
        }
    }
}
