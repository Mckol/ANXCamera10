package com.xiaomi.camera.device.callable;

import android.os.Handler;
import android.util.Log;
import androidx.annotation.NonNull;
import com.android.camera2.Camera2Proxy;
import com.xiaomi.camera.device.CameraHandlerThread;
import com.xiaomi.camera.device.CameraService;

public class ReleaseCameraCallable extends CameraCallable<Void> implements Camera2Proxy.CaptureBusyCallback {
    private static final String TAG = "ReleaseCameraCallable";
    private final boolean mImmediately;

    public ReleaseCameraCallable(String str, boolean z, CameraListener cameraListener, Handler handler) {
        super(str, cameraListener, handler);
        this.mImmediately = z;
    }

    @Override // com.xiaomi.camera.device.callable.CameraCallable
    @NonNull
    public CallableReturn<Void> call() {
        for (CameraHandlerThread.Cookie cookie : getCookieStore().getCookies()) {
            Camera2Proxy camera2Proxy = cookie.mCamera2Device;
            if (camera2Proxy != null) {
                String valueOf = String.valueOf(camera2Proxy.getId());
                String str = this.mCameraId;
                if (str == null || str.equals(valueOf)) {
                    String str2 = TAG;
                    Log.d(str2, "E: releaseCamera: cid = " + valueOf);
                    cookie.mCamera2Device.setCaptureBusyCallback(this);
                    String str3 = TAG;
                    Log.d(str3, "X: releaseCamera: cid = " + valueOf);
                }
            }
        }
        return new CallableReturn<>((Exception) null);
    }

    @Override // com.xiaomi.camera.device.callable.CameraCallable
    public String getTag() {
        return TAG;
    }

    @Override // com.android.camera2.Camera2Proxy.CaptureBusyCallback
    public void onCaptureCompleted(boolean z) {
        if (this.mImmediately) {
            CameraService.addCameraCallable(new CloseCameraCallable(null, null, null, new String[0]));
        }
    }
}
