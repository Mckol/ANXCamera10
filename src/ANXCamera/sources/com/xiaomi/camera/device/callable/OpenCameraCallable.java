package com.xiaomi.camera.device.callable;

import android.app.admin.DevicePolicyManager;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraDevice;
import android.os.Handler;
import android.util.Log;
import androidx.annotation.NonNull;
import com.android.camera.CameraAppImpl;
import com.android.camera.module.loader.camera2.Camera2DataContainer;
import com.android.camera2.Camera2Proxy;
import com.android.camera2.MiCamera2;
import com.xiaomi.camera.device.CameraHandlerThread;
import com.xiaomi.camera.device.CameraService;
import com.xiaomi.camera.device.exception.CameraDisabledException;
import java.util.concurrent.atomic.AtomicInteger;

public class OpenCameraCallable extends CameraCallable<Void> {
    private static final int OPEN_RETRY_COUNT = 10;
    private static final int OPEN_RETRY_SLEEP = 200;
    private static final String TAG = "OpenCameraCallable";
    private final String[] mExclusions;
    private final OpenCameraListener mOpenCameraListener;
    private AtomicInteger mRetryCount = new AtomicInteger(0);
    private final Handler mServiceHandler;
    private final CameraDevice.StateCallback mStateListener;

    public OpenCameraCallable(String str, CameraListener cameraListener, OpenCameraListener openCameraListener, Handler handler, Handler handler2, String... strArr) {
        super(str, cameraListener, handler);
        this.mExclusions = strArr;
        this.mOpenCameraListener = openCameraListener;
        this.mServiceHandler = handler2;
        this.mStateListener = new CameraDevice.StateCallback() {
            /* class com.xiaomi.camera.device.callable.OpenCameraCallable.AnonymousClass1 */

            public /* synthetic */ void c(String str, int i) {
                OpenCameraCallable.this.mOpenCameraListener.onError(str, i);
            }

            public void onClosed(@NonNull CameraDevice cameraDevice) {
                String id = cameraDevice.getId();
                String tag = OpenCameraCallable.this.getTag();
                Log.d(tag, "onClosed: cid = " + id);
                CameraHandlerThread.Cookie cookie = OpenCameraCallable.this.getCookieStore().getCookie(id);
                Camera2Proxy camera2Proxy = cookie.mCamera2Device;
                if (camera2Proxy != null && camera2Proxy.getCameraDevice() == cameraDevice) {
                    cookie.mCamera2Device = null;
                    String tag2 = OpenCameraCallable.this.getTag();
                    Log.d(tag2, "onClosed: cache removed: cid = " + id);
                }
                OpenCameraCallable.this.postCallback(new c(this, id));
            }

            public void onDisconnected(@NonNull CameraDevice cameraDevice) {
                String id = cameraDevice.getId();
                String tag = OpenCameraCallable.this.getTag();
                Log.d(tag, "onDisconnected: cid = " + id);
                CameraHandlerThread.Cookie cookie = OpenCameraCallable.this.getCookieStore().getCookie(id);
                Camera2Proxy camera2Proxy = cookie.mCamera2Device;
                if (camera2Proxy != null && camera2Proxy.getCameraDevice() == cameraDevice) {
                    cookie.mCamera2Device.onCameraDisconnected();
                    cookie.mCamera2Device.releasePreview(1);
                    cookie.mCamera2Device.resetConfigs();
                    cookie.mIsClosing = cookie.mCamera2Device.close();
                    cookie.mIsOpening = false;
                    cookie.mCamera2Device = null;
                    String tag2 = OpenCameraCallable.this.getTag();
                    Log.d(tag2, "onDisconnected: cache removed: cid = " + id);
                }
                OpenCameraCallable.this.postCallback(new f(this, id));
            }

            public void onError(@NonNull CameraDevice cameraDevice, int i) {
                String id = cameraDevice.getId();
                String tag = OpenCameraCallable.this.getTag();
                Log.d(tag, "onError: cid = " + id + ", error = " + i);
                CameraHandlerThread.Cookie cookie = OpenCameraCallable.this.getCookieStore().getCookie(id);
                Camera2Proxy camera2Proxy = cookie.mCamera2Device;
                if (camera2Proxy != null && camera2Proxy.getCameraDevice() == cameraDevice) {
                    cookie.mCamera2Device = null;
                    String tag2 = OpenCameraCallable.this.getTag();
                    Log.d(tag2, "onError: cache removed: cid = " + id);
                }
                if (!cookie.mIsOpening || OpenCameraCallable.this.mRetryCount.get() <= 10) {
                    Log.d(OpenCameraCallable.TAG, "onError: openCamera error, but we are retrying");
                    return;
                }
                Log.d(OpenCameraCallable.TAG, "onError: post onError to listener");
                OpenCameraCallable.this.postCallback(new d(this, id, i));
                cookie.mIsOpening = false;
            }

            public void onOpened(@NonNull CameraDevice cameraDevice) {
                String id = cameraDevice.getId();
                String tag = OpenCameraCallable.this.getTag();
                Log.d(tag, "onOpened: cid = " + id);
                CameraHandlerThread.Cookie cookie = OpenCameraCallable.this.getCookieStore().getCookie(id);
                cookie.mIsOpening = false;
                OpenCameraCallable.this.mRetryCount.getAndSet(0);
                if (cookie.mCameraCapabilities == null) {
                    cookie.mCameraCapabilities = Camera2DataContainer.getInstance().getCapabilities(Integer.parseInt(id));
                }
                if (cookie.mCameraCapabilities == null) {
                    String tag2 = OpenCameraCallable.this.getTag();
                    Log.d(tag2, "onOpened: cid = " + id + ", but camera capabilities is null");
                    OpenCameraCallable.this.postCallback(new e(this, id));
                    return;
                }
                Camera2Proxy camera2Proxy = cookie.mCamera2Device;
                if (!(camera2Proxy == null || camera2Proxy.getCameraDevice() == null || cookie.mCamera2Device.getCameraDevice() == cameraDevice)) {
                    String tag3 = OpenCameraCallable.this.getTag();
                    Log.d(tag3, "onOpened: already cached: cid = " + id);
                }
                Camera2DataContainer.getInstance().setCurrentOpenedCameraId(Integer.parseInt(id));
                cookie.mCamera2Device = new MiCamera2(Integer.parseInt(id), cameraDevice, cookie.mCameraCapabilities, OpenCameraCallable.this.mServiceHandler, cookie.mStreamingHandler);
                OpenCameraCallable.this.postCallback(new b(this, id));
            }

            public /* synthetic */ void q(String str) {
                OpenCameraCallable.this.mOpenCameraListener.onClosed(str);
            }

            public /* synthetic */ void r(String str) {
                OpenCameraCallable.this.mOpenCameraListener.onDisconnected(str);
            }

            public /* synthetic */ void s(String str) {
                OpenCameraCallable.this.mOpenCameraListener.onError(str, 231);
            }

            public /* synthetic */ void t(String str) {
                OpenCameraCallable.this.mOpenCameraListener.onOpened(str);
            }
        };
    }

    @Override // com.xiaomi.camera.device.callable.CameraCallable
    @NonNull
    public CallableReturn<Void> call() {
        CameraHandlerThread.Cookie cookie = getCookieStore().getCookie(this.mCameraId);
        if (cookie.mCamera2Device != null) {
            String str = TAG;
            Log.d(str, "Camera is already opened: cid = " + cookie.mCamera2Device.getId());
            if (cookie.mCamera2Device.getCapabilities() == null) {
                return new CallableReturn<>(new IllegalStateException("Camera capabilities are null"));
            }
            cookie.mCamera2Device.setCaptureBusyCallback(null);
            cookie.mCamera2Device.releasePreview(0);
            cookie.mCamera2Device.resetConfigs();
            postCallback(new g(this));
            return new CallableReturn<>((Exception) null);
        }
        DevicePolicyManager devicePolicyManager = (DevicePolicyManager) CameraAppImpl.getAndroidContext().getSystemService("device_policy");
        if (devicePolicyManager != null && devicePolicyManager.getCameraDisabled(null)) {
            return new CallableReturn<>(new CameraDisabledException());
        }
        for (CameraHandlerThread.Cookie cookie2 : getCookieStore().getCookies()) {
            if (cookie2.mIsOpening) {
                Log.d(TAG, String.format("CameraService is busy (cid = %s), postpone the open request (cid = %s)", cookie2.mCameraId, this.mCameraId));
                CameraService.closeCamera(null, null, null, this.mExclusions);
                CameraService.addCameraCallable(this);
                return new CallableReturn<>((Exception) null);
            }
        }
        this.mRetryCount.getAndSet(0);
        while (true) {
            try {
                String str2 = TAG;
                Log.d(str2, "openCamera: E: cid = " + this.mCameraId);
                String str3 = TAG;
                Log.d(str3, "openCamera: retries = " + this.mRetryCount.get());
                getCookieStore().mCameraManager.openCamera(this.mCameraId, this.mStateListener, this.mServiceHandler);
                getCookieStore().getCookie(this.mCameraId).mIsOpening = true;
                String str4 = TAG;
                Log.d(str4, "openCamera: X: cid = " + this.mCameraId);
                return new CallableReturn<>((Exception) null);
            } catch (CameraAccessException e2) {
                String str5 = TAG;
                Log.d(str5, "CameraAccessException: Can't open camera " + this.mCameraId);
                return new CallableReturn<>(e2);
            } catch (IllegalArgumentException | SecurityException e3) {
                String str6 = TAG;
                Log.d(str6, "Can't open camera " + this.mCameraId + ", " + e3);
                if (this.mRetryCount.incrementAndGet() > 10) {
                    Log.d(TAG, "Retry exceed max limit, return exception");
                    return new CallableReturn<>(e3);
                }
                try {
                    Thread.sleep(200);
                } catch (InterruptedException e4) {
                    String str7 = TAG;
                    Log.e(str7, "InterruptedException: while opening camera " + this.mCameraId);
                    return new CallableReturn<>(e4);
                }
            }
        }
    }

    @Override // com.xiaomi.camera.device.callable.CameraCallable
    public String getTag() {
        return TAG;
    }

    public /* synthetic */ void yo() {
        this.mOpenCameraListener.onOpened(this.mCameraId);
    }
}
