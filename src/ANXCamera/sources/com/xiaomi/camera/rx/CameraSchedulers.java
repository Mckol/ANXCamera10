package com.xiaomi.camera.rx;

import android.os.Looper;
import com.xiaomi.camera.device.CameraService;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class CameraSchedulers {
    public static final Scheduler sCameraCallableScheduler = AndroidSchedulers.from(CameraService.getCameraCallableHandler().getLooper());
    public static final Scheduler sCameraSetupScheduler;
    private static final ThreadLocal<Boolean> sIsCameraSetupThread = new ThreadLocal<Boolean>() {
        /* class com.xiaomi.camera.rx.CameraSchedulers.AnonymousClass1 */

        /* access modifiers changed from: protected */
        @Override // java.lang.ThreadLocal
        public Boolean initialValue() {
            return Boolean.FALSE;
        }
    };
    public static final Scheduler sMainThreadScheduler = AndroidSchedulers.mainThread();

    static {
        Scheduler single = Schedulers.single();
        single.scheduleDirect(a.INSTANCE);
        sCameraSetupScheduler = single;
    }

    public static boolean isOnCameraHandlerThread() {
        return Looper.myLooper() == CameraService.getCameraCallableHandler().getLooper();
    }

    public static boolean isOnCameraSetupThread() {
        return sIsCameraSetupThread.get().booleanValue();
    }

    public static boolean isOnMainThread() {
        return Looper.myLooper() == Looper.getMainLooper();
    }
}
