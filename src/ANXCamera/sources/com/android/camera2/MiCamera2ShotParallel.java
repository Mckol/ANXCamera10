package com.android.camera2;

import android.graphics.Rect;
import android.hardware.camera2.CaptureResult;
import android.media.Image;
import android.util.Size;
import android.view.Surface;
import com.android.camera.CameraSettings;
import com.android.camera.CameraSize;
import com.android.camera.log.Log;
import com.android.camera.module.ModuleManager;
import com.android.camera.module.loader.camera2.Camera2DataContainer;
import com.android.camera.parallel.AlgoConnector;
import com.android.camera2.vendortag.CaptureResultVendorTags;
import com.android.camera2.vendortag.VendorTagHelper;
import com.xiaomi.camera.base.CameraDeviceUtil;
import com.xiaomi.engine.BufferFormat;
import com.xiaomi.engine.GraphDescriptorBean;

public abstract class MiCamera2ShotParallel<T> extends MiCamera2Shot<T> {
    private static final String TAG = "ShotParallelBase";
    protected final Rect mActiveArraySize;
    protected CameraSize mAlgoSize;
    protected Size mLockedAlgoSize;
    protected CaptureResult mPreviewCaptureResult;

    MiCamera2ShotParallel(MiCamera2 miCamera2) {
        super(miCamera2);
        this.mActiveArraySize = miCamera2.getCapabilities().getActiveArraySize();
        CameraSize lockedAlgoSize = miCamera2.getCameraConfigs().getLockedAlgoSize();
        if (lockedAlgoSize != null) {
            this.mLockedAlgoSize = new Size(lockedAlgoSize.getWidth(), lockedAlgoSize.getHeight());
            Log.d(TAG, "lockedAlgoSize = " + this.mLockedAlgoSize);
        }
    }

    private boolean hasDualCamera() {
        return this.mMiCamera.getId() == Camera2DataContainer.getInstance().getSATFrontCameraId() || this.mMiCamera.getId() == Camera2DataContainer.getInstance().getBokehFrontCameraId() || this.mMiCamera.getId() == Camera2DataContainer.getInstance().getSATCameraId() || this.mMiCamera.getId() == Camera2DataContainer.getInstance().getBokehCameraId() || this.mMiCamera.getId() == Camera2DataContainer.getInstance().getUltraWideBokehCameraId();
    }

    /* access modifiers changed from: package-private */
    public void configParallelSession(Size size) {
        GraphDescriptorBean graphDescriptorBean;
        int cameraCombinationMode = CameraDeviceUtil.getCameraCombinationMode(Camera2DataContainer.getInstance().getRoleIdByActualId(this.mMiCamera.getId()));
        if (ModuleManager.isPortraitModule()) {
            graphDescriptorBean = new GraphDescriptorBean(32770, hasDualCamera() ? 2 : 1, true, cameraCombinationMode);
        } else {
            graphDescriptorBean = ModuleManager.isProPhotoModule() ? new GraphDescriptorBean(32771, 1, true, cameraCombinationMode) : ModuleManager.isUltraPixel() ? new GraphDescriptorBean(33011, 1, true, cameraCombinationMode) : new GraphDescriptorBean(0, 1, true, cameraCombinationMode);
        }
        int width = size.getWidth();
        int height = size.getHeight();
        Log.d(TAG, "configParallelSession:  streamNbr = " + graphDescriptorBean.getStreamNumber());
        Log.d(TAG, "configParallelSession: " + width + "x" + height);
        AlgoConnector.getInstance().getLocalBinder().configCaptureSession(new BufferFormat(width, height, 35, graphDescriptorBean));
        this.mAlgoSize = new CameraSize(width, height);
    }

    /* access modifiers changed from: package-private */
    public void configParallelSession(Size size, int i) {
        GraphDescriptorBean graphDescriptorBean;
        int i2 = 2;
        if (ModuleManager.isPortraitModule()) {
            if (!hasDualCamera()) {
                i2 = 1;
            }
            graphDescriptorBean = new GraphDescriptorBean(32770, i2, true, i);
        } else {
            graphDescriptorBean = i == 516 ? new GraphDescriptorBean(0, 2, true, i) : new GraphDescriptorBean(0, 1, true, i);
        }
        if (this.mMiCamera.isFakeSatEnable() && this.mMiCamera.getFakeSatOutputSize() != null) {
            size = this.mMiCamera.getFakeSatOutputSize().toSizeObject();
        }
        int width = size.getWidth();
        int height = size.getHeight();
        Log.d(TAG, "configParallelSession:  streamNbr = " + graphDescriptorBean.getStreamNumber());
        Log.d(TAG, "configParallelSession: streamSize = " + width + "x" + height);
        AlgoConnector.getInstance().getLocalBinder().configCaptureSession(new BufferFormat(width, height, 35, graphDescriptorBean));
        this.mAlgoSize = new CameraSize(width, height);
    }

    /* access modifiers changed from: protected */
    public Surface getMainCaptureSurface() {
        return this.mMiCamera.getMainCaptureSurface();
    }

    /* access modifiers changed from: protected */
    public boolean isIn3OrMoreSatMode() {
        return this.mMiCamera.isIn3OrMoreSatMode();
    }

    /* access modifiers changed from: protected */
    public boolean isInMultiSurfaceSatMode() {
        return this.mMiCamera.isInMultiSurfaceSatMode();
    }

    /* access modifiers changed from: protected */
    public boolean isSatFusionShotEnabled() {
        boolean isSatFusionShotSupported = this.mMiCamera.getCapabilities().isSatFusionShotSupported();
        Log.d(TAG, "SAT_FUSION_SHOT_SUPPORTED: " + isSatFusionShotSupported);
        boolean z = false;
        if (!isSatFusionShotSupported) {
            return false;
        }
        CaptureResult captureResult = this.mPreviewCaptureResult;
        Byte b2 = captureResult == null ? null : (Byte) VendorTagHelper.getValueSafely(captureResult, CaptureResultVendorTags.SAT_FUSION_SHOT_PIPELINE_READY);
        Log.d(TAG, "SAT_FUSION_SHOT_PIPELINE_READY: " + b2);
        if (!(b2 == null || b2.byteValue() == 0)) {
            boolean isSuperNightOn = CameraSettings.isSuperNightOn();
            Log.d(TAG, "SAT_FUSION_SUPER_NIGHT_SE_ENABLED: " + isSuperNightOn);
            if (isSuperNightOn) {
                return false;
            }
            boolean isNeedFlash = this.mMiCamera.getCameraConfigs().isNeedFlash();
            Log.d(TAG, "SAT_FUSION_FLASH_NEEDED: " + isNeedFlash);
            if (isNeedFlash) {
                return false;
            }
            boolean isHDREnabled = this.mMiCamera.getCameraConfigs().isHDREnabled();
            Log.d(TAG, "SAT_FUSION_HDR_NEEDED: " + isHDREnabled);
            if (isHDREnabled) {
                return false;
            }
            float zoomRatio = this.mMiCamera.getCameraConfigs().getZoomRatio();
            Log.d(TAG, "SAT_FUSION_ZOOM_RATIO: " + zoomRatio);
            if (zoomRatio < 3.0f) {
                return false;
            }
            int id = this.mMiCamera.getId();
            Log.d(TAG, "SAT_FUSION_ACTUAL_CAMERA_ID: " + id);
            if (id != Camera2DataContainer.getInstance().getSATCameraId()) {
                return false;
            }
            if (!(this.mMiCamera.getSATSubCameraIds() == null || this.mMiCamera.getMainCaptureSurface() == null || this.mMiCamera.getMainCaptureSurface() != this.mMiCamera.getTeleRemoteSurface() || this.mMiCamera.getUltraTeleRemoteSurface() == null)) {
                z = true;
            }
            Log.d(TAG, "SAT_FUSION_T_UT_COMBINATION: " + z);
        }
        return z;
    }

    /* access modifiers changed from: protected */
    @Override // com.android.camera2.MiCamera2Shot
    public void notifyResultData(T t) {
    }

    /* access modifiers changed from: protected */
    @Override // com.android.camera2.MiCamera2Shot
    public void onImageReceived(Image image, int i) {
    }
}
