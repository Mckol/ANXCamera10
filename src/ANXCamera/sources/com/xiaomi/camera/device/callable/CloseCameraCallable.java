package com.xiaomi.camera.device.callable;

import android.os.Handler;
import android.util.Log;
import androidx.annotation.NonNull;
import com.android.camera2.Camera2Proxy;
import com.xiaomi.camera.device.CameraHandlerThread;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class CloseCameraCallable extends CameraCallable<Void> {
    private static final String TAG = "CloseCameraCallable";
    private final Set<String> mExclusions = new HashSet();

    public CloseCameraCallable(String str, CameraListener cameraListener, Handler handler, String... strArr) {
        super(str, cameraListener, handler);
        if (strArr != null && strArr.length > 0) {
            this.mExclusions.addAll(Arrays.asList(strArr));
            if (str != null && this.mExclusions.contains(str)) {
                throw new IllegalArgumentException("ambiguous camera id: " + str + "");
            }
        }
    }

    @Override // com.xiaomi.camera.device.callable.CameraCallable
    @NonNull
    public CallableReturn<Void> call() {
        CameraHandlerThread.CookieStore cookieStore = getCookieStore();
        String str = TAG;
        Log.d(str, "E: closeCamera: cid = " + this.mCameraId + ", excludes = " + this.mExclusions);
        for (CameraHandlerThread.Cookie cookie : cookieStore.getCookies()) {
            Camera2Proxy camera2Proxy = cookie.mCamera2Device;
            if (camera2Proxy != null) {
                String valueOf = String.valueOf(camera2Proxy.getId());
                String str2 = this.mCameraId;
                if (str2 == null || str2.equals(valueOf)) {
                    if (this.mExclusions.contains(valueOf)) {
                        String str3 = TAG;
                        Log.d(str3, "closeCamera: camera '" + valueOf + "' is excluded");
                    } else {
                        String str4 = TAG;
                        Log.d(str4, "closeCamera: cid = " + valueOf);
                        cookie.mCamera2Device.releasePreview(0);
                        cookie.mCamera2Device.resetConfigs();
                        cookie.mIsOpening = false;
                        cookie.mIsClosing = cookie.mCamera2Device.close();
                        cookie.mCamera2Device = null;
                    }
                }
            }
        }
        String str5 = TAG;
        Log.d(str5, "X: closeCamera: cid = " + this.mCameraId + ", excludes = " + this.mExclusions);
        return new CallableReturn<>((Exception) null);
    }

    @Override // com.xiaomi.camera.device.callable.CameraCallable
    public String getTag() {
        return TAG;
    }
}
