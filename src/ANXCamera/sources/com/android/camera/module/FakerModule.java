package com.android.camera.module;

import android.graphics.Rect;
import android.graphics.SurfaceTexture;
import android.hardware.camera2.CameraCaptureSession;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.Surface;
import com.android.camera.CameraSettings;
import com.android.camera.CameraSize;
import com.android.camera.R;
import com.android.camera.Util;
import com.android.camera.constant.UpdateConstant;
import com.android.camera.data.DataRepository;
import com.android.camera.effect.EffectController;
import com.android.camera.fragment.BaseFragmentDelegate;
import com.android.camera.log.Log;
import com.android.camera.module.loader.StartControl;
import com.android.camera.module.loader.StartControlFeatureDetail;
import com.android.camera.protocol.ModeCoordinatorImpl;
import com.android.camera.protocol.ModeProtocol;
import com.android.camera2.Camera2Proxy;
import java.lang.ref.WeakReference;
import java.util.List;

public class FakerModule extends BaseModule implements Camera2Proxy.CameraPreviewCallback {
    private static final String TAG = "FakerModule";

    private class MainHandler extends Handler {
        private WeakReference<FakerModule> mModule;

        public MainHandler(FakerModule fakerModule, Looper looper) {
            super(looper);
            this.mModule = new WeakReference<>(fakerModule);
        }

        public void handleMessage(Message message) {
            FakerModule fakerModule = this.mModule.get();
            if (message.what == 45) {
                Log.d(FakerModule.TAG, "onMessage MSG_ABANDON_HANDLER setActivity null");
                FakerModule.this.setActivity(null);
            }
            if (!FakerModule.this.isCreated()) {
                removeCallbacksAndMessages(null);
            } else if (FakerModule.this.getActivity() != null) {
                if (message.what == 45) {
                    fakerModule.setActivity(null);
                    return;
                }
                throw new RuntimeException("no consumer for this message: " + message.what);
            }
        }
    }

    private void previewWhenSessionSuccess() {
        setCameraState(1);
        updatePreferenceInWorkThread(UpdateConstant.FAKER_TYPES_ON_PREVIEW_SUCCESS);
    }

    private void updateFilter() {
        int shaderEffect = CameraSettings.getShaderEffect();
        String str = TAG;
        Log.v(str, "updateFilter: 0x" + Integer.toHexString(shaderEffect));
        EffectController.getInstance().setEffect(shaderEffect);
        Camera2Proxy camera2Proxy = this.mCamera2Device;
        if (camera2Proxy != null) {
            camera2Proxy.resumePreview();
        }
    }

    private void updatePictureAndPreviewSize() {
        this.mCameraCapabilities.setOperatingMode(getOperatingMode());
        List<CameraSize> supportedOutputSizeWithAssignedMode = this.mCameraCapabilities.getSupportedOutputSizeWithAssignedMode(SurfaceTexture.class);
        int uiStyle = DataRepository.dataItemRunning().getUiStyle();
        String str = TAG;
        Log.d(str, "getLastUiStyle = " + uiStyle);
        this.mPreviewSize = Util.getOptimalPreviewSize(false, this.mBogusCameraId, supportedOutputSizeWithAssignedMode, (double) (uiStyle != 0 ? uiStyle != 1 ? uiStyle != 3 ? CameraSettings.getPreviewAspectRatio(4, 3) : CameraSettings.getPreviewAspectRatio(Util.getScreenHeight(this.mActivity), Util.getScreenWidth(this.mActivity)) : CameraSettings.getPreviewAspectRatio(16, 9) : CameraSettings.getPreviewAspectRatio(4, 3)), null);
        CameraSize cameraSize = this.mPreviewSize;
        if (cameraSize != null) {
            updateCameraScreenNailSize(cameraSize.width, cameraSize.height);
        }
        String str2 = TAG;
        Log.d(str2, "previewSize: " + this.mPreviewSize);
    }

    @Override // com.android.camera.module.Module
    public void closeCamera() {
    }

    /* access modifiers changed from: protected */
    @Override // com.android.camera.module.BaseModule
    public void consumePreference(int... iArr) {
        for (int i : iArr) {
            if (i == 1) {
                updatePictureAndPreviewSize();
            } else if (i == 2) {
                updateFilter();
            } else if (i == 24) {
                applyZoomRatio();
            } else if (i == 55) {
                updateModuleRelated();
            }
        }
    }

    @Override // com.android.camera.module.Module, com.android.camera.module.BaseModule
    public void fillFeatureControl(StartControl startControl) {
        StartControlFeatureDetail featureDetail = startControl.getFeatureDetail();
        if (startControl.mTargetMode == 254) {
            featureDetail.addFragmentInfo(R.id.full_screen_feature, BaseFragmentDelegate.FRAGMENT_MODES_MORE_NORMAL);
        }
    }

    /* access modifiers changed from: protected */
    @Override // com.android.camera.module.BaseModule
    public int getOperatingMode() {
        return 0;
    }

    @Override // com.android.camera.module.Module
    public boolean isDoingAction() {
        return false;
    }

    @Override // com.android.camera.module.Module
    public boolean isUnInterruptable() {
        return false;
    }

    /* access modifiers changed from: protected */
    @Override // com.android.camera.module.BaseModule
    public void onCameraOpened() {
        updatePreferenceTrampoline(UpdateConstant.FAKER_TYPES_INIT);
        startPreview();
    }

    @Override // com.android.camera.module.Module, com.android.camera.module.BaseModule
    public void onCreate(int i, int i2) {
        super.onCreate(i, i2);
        this.mHandler = new MainHandler(this, this.mActivity.getMainLooper());
        onCameraOpened();
    }

    @Override // com.android.camera.module.Module, com.android.camera.module.BaseModule
    public void onDestroy() {
        super.onDestroy();
        Handler handler = this.mHandler;
        if (handler != null) {
            handler.sendEmptyMessage(45);
        }
    }

    @Override // com.android.camera.module.BaseModule
    public void onPreviewLayoutChanged(Rect rect) {
        this.mActivity.onLayoutChange(rect);
    }

    @Override // com.android.camera2.Camera2Proxy.CameraPreviewCallback
    public void onPreviewSessionClosed(CameraCaptureSession cameraCaptureSession) {
    }

    @Override // com.android.camera2.Camera2Proxy.CameraPreviewCallback
    public void onPreviewSessionFailed(CameraCaptureSession cameraCaptureSession) {
    }

    @Override // com.android.camera2.Camera2Proxy.CameraPreviewCallback
    public void onPreviewSessionSuccess(CameraCaptureSession cameraCaptureSession) {
        if (cameraCaptureSession != null && isAlive()) {
            previewWhenSessionSuccess();
        }
    }

    /* access modifiers changed from: protected */
    @Override // com.android.camera.module.BaseModule
    public void openSettingActivity() {
    }

    @Override // com.android.camera.module.Module
    public void pausePreview() {
    }

    @Override // com.android.camera.protocol.ModeProtocol.BaseProtocol, com.android.camera.module.Module, com.android.camera.module.BaseModule
    public void registerProtocol() {
        super.registerProtocol();
        Log.d(TAG, "registerProtocol");
        ModeCoordinatorImpl.getInstance().attachProtocol(2560, this);
        getActivity().getImplFactory().initAdditional(getActivity(), 164);
    }

    @Override // com.android.camera.module.Module
    public void resumePreview() {
    }

    @Override // com.android.camera.module.Module, com.android.camera.module.BaseModule
    public void setFrameAvailable(boolean z) {
        super.setFrameAvailable(z);
        ModeProtocol.MainContentProtocol mainContentProtocol = this.mMainProtocol;
        if (mainContentProtocol != null) {
            mainContentProtocol.hideReferenceGradienter();
        }
    }

    @Override // com.android.camera.module.Module
    public void startPreview() {
        if (isDeviceAlive()) {
            this.mCamera2Device.setPreviewSize(this.mPreviewSize);
            this.mCamera2Device.startPreviewSession(new Surface(this.mActivity.getCameraScreenNail().getSurfaceTexture()), 0, false, false, null, getOperatingMode(), false, this);
        }
    }

    @Override // com.android.camera.protocol.ModeProtocol.BaseProtocol, com.android.camera.module.Module, com.android.camera.module.BaseModule
    public void unRegisterProtocol() {
        super.unRegisterProtocol();
        Log.d(TAG, "unRegisterProtocol");
        ModeCoordinatorImpl.getInstance().detachProtocol(2560, this);
        getActivity().getImplFactory().detachAdditional();
    }
}
