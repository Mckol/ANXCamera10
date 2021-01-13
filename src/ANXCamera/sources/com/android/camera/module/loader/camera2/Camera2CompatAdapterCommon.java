package com.android.camera.module.loader.camera2;

import android.hardware.camera2.CameraCharacteristics;
import android.hardware.camera2.CameraManager;
import android.util.SparseArray;
import b.c.a.c;
import com.android.camera.CameraSettings;
import com.android.camera.HybridZoomingSystem;
import com.android.camera.data.DataRepository;
import com.android.camera.log.Log;
import com.android.camera2.CameraCapabilities;
import java.util.Arrays;
import java.util.concurrent.ConcurrentHashMap;

public class Camera2CompatAdapterCommon extends Camera2CompatAdapter {
    private static final int INDEX_AUX = 1;
    private static final int INDEX_BOKEH = 3;
    private static final int INDEX_INFRARED = 5;
    private static final int INDEX_MAIN = 0;
    private static final int INDEX_SAT = 2;
    private static final int INDEX_VIRTUAL = 4;
    private static final int MAX_TYPES_OF_CAMERAS_OF_EACH_FACING_DIRECTION = 6;
    public static final int STANDALONE_MACRO_CAMERA_ID = 22;
    private static final int STANDALONE_SAT_CAMERA_ID = 180;
    private static final String TAG = "Camera2CompatAdapterCommon";
    public static final int TRIPLE_SAT_CAMERA_ID = 120;
    public static final int ULTRA_TELE_CAMERA_ID = 23;
    public static final int ULTRA_WIDE_BOKEH_CAMERA_ID = 63;
    public static final int ULTRA_WIDE_CAMERA_ID = 21;
    private volatile int[] mOrderedCameraIds = null;

    protected Camera2CompatAdapterCommon() {
    }

    private void dumpCameraIds() {
        int[] iArr = new int[6];
        int[] iArr2 = new int[6];
        for (int i = 0; i < 6; i++) {
            iArr[i] = this.mOrderedCameraIds[i];
            iArr2[i] = this.mOrderedCameraIds[(this.mOrderedCameraIds.length / 2) + i];
        }
        Log.d(TAG, "====================================================================");
        String str = TAG;
        Log.d(str, " BACK: [main, aux, sat, bokeh, virtual, infrared] = " + Arrays.toString(iArr));
        String str2 = TAG;
        Log.d(str2, "FRONT: [main, aux, sat, bokeh, virtual, infrared] = " + Arrays.toString(iArr2));
        Log.d(TAG, "====================================================================");
    }

    private synchronized boolean isValid(int i) {
        for (int i2 = 0; i2 < this.mOrderedCameraIds.length; i2++) {
            if (this.mOrderedCameraIds[i2] == i) {
                return true;
            }
        }
        return false;
    }

    @Override // com.android.camera.module.loader.camera2.Camera2Compat
    public synchronized int getAuxCameraId() {
        if (!isInitialized()) {
            Log.d(TAG, "Warning: getAuxCameraId(): #init() failed.");
            return -1;
        }
        return this.mOrderedCameraIds[1];
    }

    @Override // com.android.camera.module.loader.camera2.Camera2Compat
    public synchronized int getAuxFrontCameraId() {
        if (!isInitialized()) {
            Log.d(TAG, "Warning: getAuxFrontCameraId(): #init() failed.");
            return -1;
        }
        return this.mOrderedCameraIds[(this.mOrderedCameraIds.length / 2) + 1];
    }

    @Override // com.android.camera.module.loader.camera2.Camera2Compat
    public synchronized int getBokehCameraId() {
        if (!isInitialized()) {
            Log.d(TAG, "Warning: getBokehCameraId(): #init() failed.");
            return -1;
        } else if (this.mOrderedCameraIds[3] != -1) {
            return this.mOrderedCameraIds[3];
        } else {
            return this.mOrderedCameraIds[2];
        }
    }

    @Override // com.android.camera.module.loader.camera2.Camera2Compat
    public synchronized int getBokehFrontCameraId() {
        if (!isInitialized()) {
            Log.d(TAG, "Warning: getBokehFrontCameraId(): #init() failed.");
            return -1;
        }
        return this.mOrderedCameraIds[(this.mOrderedCameraIds.length / 2) + 3];
    }

    @Override // com.android.camera.module.loader.camera2.Camera2Compat
    public ConcurrentHashMap getDefaultDualVideoCameraIds() {
        ConcurrentHashMap concurrentHashMap = new ConcurrentHashMap();
        if (DataRepository.dataItemFeature().tj()) {
            concurrentHashMap.put(100, Integer.valueOf(getUltraWideCameraId()));
        } else {
            concurrentHashMap.put(100, Integer.valueOf(getMainBackCameraId()));
        }
        concurrentHashMap.put(101, Integer.valueOf(getFrontCameraId()));
        return concurrentHashMap;
    }

    @Override // com.android.camera.module.loader.camera2.Camera2Compat
    public synchronized int getFrontCameraId() {
        if (!isInitialized()) {
            Log.d(TAG, "Warning: getFrontCameraId(): #init() failed.");
            return -1;
        }
        return this.mOrderedCameraIds[(this.mOrderedCameraIds.length / 2) + 0];
    }

    @Override // com.android.camera.module.loader.camera2.Camera2Compat
    public synchronized int getMainBackCameraId() {
        if (!isInitialized()) {
            Log.d(TAG, "Warning: getMainBackCameraId(): #init() failed.");
            return -1;
        }
        return this.mOrderedCameraIds[0];
    }

    @Override // com.android.camera.module.loader.camera2.Camera2Compat
    public int getParallelVirtualCameraId() {
        return -1;
    }

    @Override // com.android.camera.module.loader.camera2.Camera2Compat
    public synchronized int getRoleIdByActualId(int i) {
        return i;
    }

    @Override // com.android.camera.module.loader.camera2.Camera2Compat
    public synchronized int getSATCameraId() {
        if (!isInitialized()) {
            Log.d(TAG, "Warning: getSATCameraId(): #init() failed.");
            return -1;
        } else if (HybridZoomingSystem.IS_4_SAT && CameraSettings.isSupportedOpticalZoom() && isValid(180)) {
            return 180;
        } else {
            if (HybridZoomingSystem.IS_3_SAT && CameraSettings.isSupportedOpticalZoom() && isValid(120)) {
                return 120;
            }
            return this.mOrderedCameraIds[2];
        }
    }

    @Override // com.android.camera.module.loader.camera2.Camera2Compat
    public synchronized int getSATFrontCameraId() {
        if (!isInitialized()) {
            Log.d(TAG, "Warning: getSATFrontCameraId(): #init() failed.");
            return -1;
        }
        return this.mOrderedCameraIds[(this.mOrderedCameraIds.length / 2) + 2];
    }

    @Override // com.android.camera.module.loader.camera2.Camera2Compat
    public synchronized int getStandaloneMacroCameraId() {
        if (isInitialized()) {
            return 22;
        }
        Log.d(TAG, "Warning: getStandaloneMacroCameraId(): #init() failed.");
        return -1;
    }

    @Override // com.android.camera.module.loader.camera2.Camera2Compat
    public synchronized int getUltraTeleCameraId() {
        if (isInitialized()) {
            return 23;
        }
        Log.d(TAG, "Warning: getUltraWideCameraId(): #init() failed.");
        return -1;
    }

    @Override // com.android.camera.module.loader.camera2.Camera2Compat
    public synchronized int getUltraWideBokehCameraId() {
        if (isInitialized()) {
            return 63;
        }
        Log.d(TAG, "Warning: getUltraWideBokehCameraId(): #init() failed.");
        return -1;
    }

    @Override // com.android.camera.module.loader.camera2.Camera2Compat
    public synchronized int getUltraWideCameraId() {
        if (isInitialized()) {
            return 21;
        }
        Log.d(TAG, "Warning: getUltraWideCameraId(): #init() failed.");
        return -1;
    }

    @Override // com.android.camera.module.loader.camera2.Camera2Compat
    public synchronized int getVideoSATCameraId() {
        return -1;
    }

    @Override // com.android.camera.module.loader.camera2.Camera2Compat
    public int getVirtualBackCameraId() {
        return -1;
    }

    @Override // com.android.camera.module.loader.camera2.Camera2Compat
    public int getVirtualFrontCameraId() {
        return -1;
    }

    @Override // com.android.camera.module.loader.camera2.Camera2Compat
    public synchronized boolean hasBokehCamera() {
        boolean z = false;
        if (!isInitialized()) {
            Log.d(TAG, "Warning: hasBokehCamera(): #init() failed.");
            return false;
        }
        if (this.mOrderedCameraIds[3] != -1) {
            z = true;
        }
        return z;
    }

    @Override // com.android.camera.module.loader.camera2.Camera2Compat
    public boolean hasPortraitCamera() {
        return hasSATCamera();
    }

    @Override // com.android.camera.module.loader.camera2.Camera2Compat
    public synchronized boolean hasSATCamera() {
        boolean z = false;
        if (!isInitialized()) {
            Log.d(TAG, "Warning: hasSATCamera(): #init() failed.");
            return false;
        }
        if (this.mOrderedCameraIds[2] != -1) {
            z = true;
        }
        return z;
    }

    @Override // com.android.camera.module.loader.camera2.Camera2Compat
    public boolean hasTeleCamera() {
        return (getAuxCameraId() == -1 && getUltraTeleCameraId() == -1) ? false : true;
    }

    @Override // com.android.camera.module.loader.camera2.Camera2Compat
    public void init(CameraManager cameraManager) {
        Log.d(TAG, "E: init()");
        try {
            reset();
            String[] cameraIdList = cameraManager.getCameraIdList();
            Log.d(TAG, "All available camera ids: " + Arrays.deepToString(cameraIdList));
            int max = Math.max(6, cameraIdList.length);
            this.mOrderedCameraIds = new int[(max * 2)];
            Arrays.fill(this.mOrderedCameraIds, -1);
            this.mCapabilities = new SparseArray<>(cameraIdList.length);
            int i = max;
            int i2 = 0;
            for (String str : cameraIdList) {
                try {
                    int parseInt = Integer.parseInt(str);
                    CameraCharacteristics cameraCharacteristics = cameraManager.getCameraCharacteristics(str);
                    this.mCapabilities.put(parseInt, new CameraCapabilities(cameraCharacteristics, parseInt));
                    if ((!DataRepository.dataItemFeature().isSupportUltraWide() || ((21 != parseInt || c.Ug) && 63 != parseInt)) && ((!DataRepository.dataItemFeature().isSupportMacroMode() || 22 != parseInt) && (!DataRepository.dataItemFeature().pk() || 23 != parseInt))) {
                        Integer num = (Integer) cameraCharacteristics.get(CameraCharacteristics.LENS_FACING);
                        if (num == null) {
                            Log.d(TAG, "Unknown facing direction of camera " + parseInt);
                        } else if (num.intValue() == 1) {
                            this.mOrderedCameraIds[i2] = parseInt;
                            i2++;
                        } else if (num.intValue() == 0) {
                            this.mOrderedCameraIds[i] = parseInt;
                            i++;
                        }
                    }
                } catch (NumberFormatException unused) {
                    Log.e(TAG, "non-integer camera id: " + str);
                }
            }
            dumpCameraIds();
        } catch (Exception e2) {
            Log.e(TAG, "Failed to init Camera2DataContainer: " + e2);
            reset();
        }
        Log.d(TAG, "X: init()");
    }

    @Override // com.android.camera.module.loader.camera2.Camera2Compat
    public boolean isInitialized() {
        return (this.mCapabilities == null || this.mCapabilities.size() <= 0 || this.mOrderedCameraIds == null) ? false : true;
    }

    @Override // com.android.camera.module.loader.camera2.Camera2Compat
    public synchronized boolean isPartSAT() {
        int sATCameraId = getSATCameraId();
        if (sATCameraId == -1) {
            return false;
        }
        if (sATCameraId != 120) {
            if (sATCameraId != 180) {
                if (HybridZoomingSystem.IS_2_SAT) {
                    return false;
                }
            } else if (HybridZoomingSystem.IS_4_SAT) {
                return false;
            }
        } else if (HybridZoomingSystem.IS_3_SAT) {
            return false;
        }
        return true;
    }

    @Override // com.android.camera.module.loader.camera2.Camera2Compat
    public synchronized void reset() {
        Log.d(TAG, "E: reset()");
        this.mCurrentOpenedCameraId = -1;
        this.mCapabilities = null;
        this.mOrderedCameraIds = null;
        Log.d(TAG, "X: reset()");
    }
}
