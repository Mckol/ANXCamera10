package com.xiaomi.camera.rx;

import com.android.camera.constant.ExceptionConstant;
import com.android.camera.log.Log;
import com.android.camera.module.loader.camera2.Camera2OpenManager;
import com.android.camera.module.loader.camera2.Camera2Result;
import com.xiaomi.camera.device.CameraService;
import com.xiaomi.camera.device.callable.CameraListener;
import com.xiaomi.camera.device.callable.OpenCameraListener;
import io.reactivex.Single;
import io.reactivex.SingleObserver;

public class CameraOpenObservable extends Single<Camera2Result> {
    private static final String TAG = "CameraOpenObservable";
    private final String mCameraId;
    private final String[] mExclusions;

    private static final class Listener extends SimpleDisposable implements CameraListener, OpenCameraListener {
        private final String mCameraId;
        private final SingleObserver<? super Camera2Result> mObserver;

        private Listener(String str, SingleObserver<? super Camera2Result> singleObserver) {
            super(CameraService.getCameraCallableHandler());
            this.mCameraId = str;
            this.mObserver = singleObserver;
        }

        @Override // com.xiaomi.camera.device.callable.OpenCameraListener
        public void onClosed(String str) {
            Log.d(CameraOpenObservable.TAG, "onClosed: cid = " + this.mCameraId + ", listener = " + hashCode());
            Camera2OpenManager.getInstance().onClosed(str);
        }

        @Override // com.xiaomi.camera.device.callable.OpenCameraListener
        public void onDisconnected(String str) {
            Log.d(CameraOpenObservable.TAG, "onDisconnected: cid = " + this.mCameraId + ", listener = " + hashCode());
            Camera2OpenManager.getInstance().onDisconnected(str);
        }

        /* access modifiers changed from: protected */
        @Override // com.xiaomi.camera.rx.SimpleDisposable
        public void onDispose() {
            Log.d(CameraOpenObservable.TAG, "onDispose: listener: " + hashCode());
        }

        @Override // com.xiaomi.camera.device.callable.OpenCameraListener
        public void onError(String str, int i) {
            Log.d(CameraOpenObservable.TAG, "onError: cid = " + this.mCameraId + ", listener = " + hashCode());
            Camera2OpenManager.getInstance().onError(str, i);
            if (!isDisposed()) {
                this.mObserver.onSuccess(Camera2Result.create(3).setCameraError(ExceptionConstant.transFromCamera2Error(i)));
            }
        }

        @Override // com.xiaomi.camera.device.callable.CallableListener
        public void onFailure(Exception exc) {
            this.mObserver.onSuccess(Camera2Result.create(3).setCameraError(231));
        }

        @Override // com.xiaomi.camera.device.callable.OpenCameraListener
        public void onOpened(String str) {
            Log.d(CameraOpenObservable.TAG, "onOpened: cid = " + this.mCameraId + ", listener = " + hashCode());
            Camera2OpenManager.getInstance().onOpened(str);
            if (!isDisposed()) {
                this.mObserver.onSuccess(Camera2Result.create(2));
            }
        }

        public void onSuccess(Void r1) {
        }
    }

    private CameraOpenObservable(String str, String... strArr) {
        this.mCameraId = str;
        this.mExclusions = strArr;
    }

    public static CameraOpenObservable create(String str, String... strArr) {
        return new CameraOpenObservable(str, strArr);
    }

    /* access modifiers changed from: protected */
    @Override // io.reactivex.Single
    public void subscribeActual(SingleObserver<? super Camera2Result> singleObserver) {
        Listener listener = new Listener(this.mCameraId, singleObserver);
        singleObserver.onSubscribe(listener);
        Log.d(TAG, "subscribeActual: openCamera: cid = " + this.mCameraId + ", listener = " + listener.hashCode());
        CameraService.openCamera(this.mCameraId, listener, listener, CameraService.getCameraCallableHandler(), this.mExclusions);
    }
}
