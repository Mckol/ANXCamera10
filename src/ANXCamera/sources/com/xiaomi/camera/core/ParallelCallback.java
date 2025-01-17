package com.xiaomi.camera.core;

import android.hardware.camera2.CameraCharacteristics;
import android.hardware.camera2.CaptureResult;
import androidx.annotation.Nullable;

public interface ParallelCallback {
    boolean onParallelProcessFinish(ParallelTaskData parallelTaskData, @Nullable CaptureResult captureResult, @Nullable CameraCharacteristics cameraCharacteristics);
}
