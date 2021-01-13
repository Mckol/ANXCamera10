package com.android.camera.module.loader.camera2;

import com.android.camera.data.DataRepository;
import com.android.camera.data.data.global.DataItemGlobal;
import com.android.camera.log.Log;
import com.android.camera.module.ModuleManager;
import com.android.camera.module.loader.SurfaceCreatedCallback;
import com.android.camera.module.loader.SurfaceStateListener;
import com.android.camera.snap.SnapTrigger;
import io.reactivex.Observer;
import io.reactivex.SingleEmitter;
import io.reactivex.SingleOnSubscribe;
import io.reactivex.disposables.Disposable;

public class Camera2OpenOnSubscribe implements SingleOnSubscribe<Camera2Result>, SurfaceCreatedCallback, Observer<Camera2Result> {
    private static final String TAG = "Camera2OpenOnSubScribe";
    private Camera2Result mCamera2Result;
    private SingleEmitter<Camera2Result> mSingleEmitter;
    private final SurfaceStateListener mSurfaceStateListener;

    public Camera2OpenOnSubscribe(SurfaceStateListener surfaceStateListener) {
        this.mSurfaceStateListener = surfaceStateListener;
    }

    private void submitResult(Camera2Result camera2Result) {
        SingleEmitter<Camera2Result> singleEmitter = this.mSingleEmitter;
        if (singleEmitter != null) {
            singleEmitter.onSuccess(camera2Result);
        }
    }

    @Override // io.reactivex.Observer
    public void onComplete() {
        Log.d(TAG, "onComplete");
    }

    @Override // io.reactivex.Observer
    public void onError(Throwable th) {
        Log.d(TAG, "onError: hasSurface = " + this.mSurfaceStateListener.hasSurface());
        this.mCamera2Result = Camera2Result.create(3);
        submitResult(this.mCamera2Result);
    }

    @Override // com.android.camera.module.loader.SurfaceCreatedCallback
    public void onGlSurfaceCreated() {
        Log.d(TAG, "onGlSurfaceCreated: mSingleEmitter = " + this.mSingleEmitter);
        SingleEmitter<Camera2Result> singleEmitter = this.mSingleEmitter;
        if (singleEmitter == null || singleEmitter.isDisposed()) {
            Log.d(TAG, "onGlSurfaceCreated: mSingleEmitter already disposed");
            return;
        }
        Log.d(TAG, "onGlSurfaceCreated: mCamera2Result = " + this.mCamera2Result);
        Camera2Result camera2Result = this.mCamera2Result;
        if (camera2Result != null) {
            submitResult(camera2Result);
        }
    }

    public void onNext(Camera2Result camera2Result) {
        boolean isCapture = ModuleManager.isCapture();
        boolean hasSurface = this.mSurfaceStateListener.hasSurface();
        Log.d(TAG, "onNext: hasSurface = " + hasSurface + ", isCapture = " + isCapture);
        this.mCamera2Result = camera2Result;
        if (isCapture || hasSurface) {
            submitResult(camera2Result);
        }
    }

    @Override // io.reactivex.Observer
    public void onSubscribe(Disposable disposable) {
        Log.d(TAG, "onSubscribe");
    }

    @Override // io.reactivex.SingleOnSubscribe
    public void subscribe(SingleEmitter<Camera2Result> singleEmitter) throws Exception {
        this.mCamera2Result = null;
        this.mSingleEmitter = singleEmitter;
        if (SnapTrigger.getInstance().isRunning()) {
            SnapTrigger.getInstance();
            SnapTrigger.destroy();
        }
        DataItemGlobal dataItemGlobal = DataRepository.dataItemGlobal();
        int currentCameraId = dataItemGlobal.getCurrentCameraId();
        int currentMode = dataItemGlobal.getCurrentMode();
        Log.d(TAG, "subscribe: request to open " + currentCameraId);
        Camera2OpenManager.getInstance().openCamera(currentCameraId, currentMode, this, false);
    }
}
