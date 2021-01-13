package com.android.camera.module.loader.camera2;

import android.util.SparseArray;
import androidx.annotation.Nullable;
import com.android.camera.log.Log;
import com.android.camera2.CameraCapabilities;

public abstract class Camera2CompatAdapter implements Camera2Compat {
    protected static final int INVALID_CAMERA_ID = -1;
    private static final String TAG = "Camera2CompatAdapter";
    public static final int TELE_CAMERA_ID = 20;
    protected volatile SparseArray<CameraCapabilities> mCapabilities = null;
    protected volatile int mCurrentOpenedCameraId = -1;

    @Override // com.android.camera.module.loader.camera2.Camera2Compat
    public SparseArray<CameraCapabilities> getCapabilities() {
        return this.mCapabilities;
    }

    @Override // com.android.camera.module.loader.camera2.Camera2Compat
    @Nullable
    public synchronized CameraCapabilities getCapabilities(int i) {
        if (!isInitialized()) {
            Log.d(TAG, "Warning: getCapabilities(): #init() failed.");
            return null;
        }
        CameraCapabilities cameraCapabilities = this.mCapabilities.get(i);
        if (cameraCapabilities == null) {
            String str = TAG;
            Log.d(str, "Warning: getCapabilities(): return null for camera: " + i);
        }
        return cameraCapabilities;
    }

    @Override // com.android.camera.module.loader.camera2.Camera2Compat
    public synchronized CameraCapabilities getCurrentCameraCapabilities() {
        if (this.mCurrentOpenedCameraId == -1) {
            Log.d(TAG, "Warning: getCurrentCameraCapabilities(): mCurrentOpenedCameraId is invalid.");
        }
        return getCapabilities(this.mCurrentOpenedCameraId);
    }

    @Override // com.android.camera.module.loader.camera2.Camera2Compat
    public synchronized void setCurrentOpenedCameraId(int i) {
        this.mCurrentOpenedCameraId = i;
    }
}
