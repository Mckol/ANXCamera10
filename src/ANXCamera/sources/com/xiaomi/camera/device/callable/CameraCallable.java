package com.xiaomi.camera.device.callable;

import android.os.Handler;
import android.os.SystemClock;
import android.util.Log;
import androidx.annotation.NonNull;
import com.xiaomi.camera.device.CameraHandlerThread;
import com.xiaomi.camera.rx.CameraSchedulers;
import java.lang.ref.WeakReference;

public abstract class CameraCallable<T> {
    private long mBeginning;
    protected final WeakReference<Handler> mCallbackHandler;
    protected final String mCameraId;
    protected final WeakReference<CallableListener<T>> mCameraListener;

    public CameraCallable(String str, CallableListener<T> callableListener) {
        this(str, callableListener, null);
    }

    public CameraCallable(String str, CallableListener<T> callableListener, Handler handler) {
        this.mCameraId = str;
        this.mCameraListener = new WeakReference<>(callableListener);
        this.mCallbackHandler = new WeakReference<>(handler);
    }

    protected static void runOnUiThread(Runnable runnable) {
        CameraSchedulers.sMainThreadScheduler.scheduleDirect(runnable);
    }

    @NonNull
    public abstract CallableReturn<T> call();

    /* renamed from: callback */
    public void a(CallableReturn<T> callableReturn) {
        long elapsedRealtime = SystemClock.elapsedRealtime() - this.mBeginning;
        CallableListener<T> callableListener = this.mCameraListener.get();
        if (callableReturn.exception != null) {
            String tag = getTag();
            Log.w(tag, "Failure: cid: " + this.mCameraId + " (dur: " + elapsedRealtime + ")", callableReturn.exception);
            if (callableListener != null) {
                callableListener.onFailure(callableReturn.exception);
                return;
            }
            return;
        }
        String tag2 = getTag();
        Log.d(tag2, "Success: cid: " + this.mCameraId + " (dur: " + elapsedRealtime + ")");
        if (callableListener != null) {
            callableListener.onSuccess(callableReturn.value);
        }
    }

    @NonNull
    public CameraHandlerThread.CookieStore getCookieStore() {
        return ((CameraHandlerThread) Thread.currentThread()).getCookieStore();
    }

    /* access modifiers changed from: protected */
    public abstract String getTag();

    /* access modifiers changed from: protected */
    public void postCallback(Runnable runnable) {
        Handler handler = this.mCallbackHandler.get();
        if (handler != null) {
            handler.post(runnable);
        } else {
            runOnUiThread(runnable);
        }
    }

    public void run() {
        String tag = getTag();
        Log.d(tag, "E: cid: " + this.mCameraId);
        this.mBeginning = SystemClock.elapsedRealtime();
        CallableReturn<T> call = call();
        String tag2 = getTag();
        Log.d(tag2, "X: cid: " + this.mCameraId + " (dur: " + (SystemClock.elapsedRealtime() - this.mBeginning) + ")");
        postCallback(new a(this, call));
    }
}
