package com.android.camera.module.loader.camera2;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import com.android.camera.CameraSettings;
import com.android.camera.data.DataRepository;
import com.android.camera.lib.compatibility.util.CompatibilityUtils;
import com.android.camera.log.Log;
import com.android.camera.module.ModuleManager;
import com.xiaomi.camera.device.CameraHandlerThread;
import com.xiaomi.camera.device.CameraService;
import com.xiaomi.camera.device.callable.OpenCameraListener;
import com.xiaomi.camera.rx.CameraOpenObservable;
import com.xiaomi.camera.rx.CameraSchedulers;
import com.xiaomi.camera.util.Singleton;
import com.xiaomi.camera.util.SystemProperties;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.Single;
import io.reactivex.SingleSource;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observables.ConnectableObservable;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import miui.text.ExtraTextUtils;

@SuppressLint({"MissingPermission"})
@TargetApi(21)
public class Camera2OpenManager implements ObservableOnSubscribe<Camera2Result>, OpenCameraListener {
    private static final long CAMERA_OPEN_OR_CLOSE_TIMEOUT = 5000;
    private static final long POP_CAMERA_DELAY_CREATE_SESSION = SystemProperties.getLong("delay_create_session", 450);
    private static final String TAG = "Camera2OpenManager";
    private static final Singleton<Camera2OpenManager> sInstance = new Singleton<Camera2OpenManager>() {
        /* class com.android.camera.module.loader.camera2.Camera2OpenManager.AnonymousClass1 */

        /* access modifiers changed from: protected */
        @Override // com.xiaomi.camera.util.Singleton
        public Camera2OpenManager create() {
            return new Camera2OpenManager();
        }
    };
    private ObservableEmitter<Camera2Result> mCameraResultEmitter;
    private ConnectableObservable<Camera2Result> mCameraResultObservable;
    private CompositeDisposable mCompositeDisposable = new CompositeDisposable();
    private AtomicInteger mCurrentModule = new AtomicInteger(160);
    private AtomicInteger mPendingCameraId = new AtomicInteger(-1);
    private AtomicLong mPopCameraTimestamp = new AtomicLong(-1);

    static /* synthetic */ SingleSource a(ConcurrentHashMap concurrentHashMap, Camera2Result camera2Result) throws Exception {
        return camera2Result.getResult() != 2 ? Single.just(camera2Result) : CameraOpenObservable.create(String.valueOf(concurrentHashMap.get(100)), new String[0]);
    }

    private void abandonCameraResultObservable() {
        Log.d(TAG, "abandonCameraResultObservable: E");
        ObservableEmitter<Camera2Result> observableEmitter = this.mCameraResultEmitter;
        if (observableEmitter != null && !observableEmitter.isDisposed()) {
            Log.d(TAG, "abandonCameraResultObservable: fire");
            this.mCameraResultEmitter.onNext(Camera2Result.create(3).setCameraError(225));
            this.mCameraResultEmitter.onComplete();
            this.mCameraResultEmitter = null;
        }
        Log.d(TAG, "abandonCameraResultObservable: X");
    }

    private void delay() {
        if (DataRepository.dataItemFeature().zi()) {
            long currentTimeMillis = System.currentTimeMillis() - this.mPopCameraTimestamp.get();
            long j = POP_CAMERA_DELAY_CREATE_SESSION - currentTimeMillis;
            String str = TAG;
            Log.d(str, "delay: elapsed = " + currentTimeMillis + ", remaining = " + j);
            if (j > 0) {
                Log.d(TAG, "delay: sleep: E");
                try {
                    TimeUnit.MILLISECONDS.sleep(j);
                } catch (InterruptedException unused) {
                }
                Log.d(TAG, "delay: sleep: X");
            }
            this.mPopCameraTimestamp.set(-1);
        }
    }

    /* access modifiers changed from: private */
    public synchronized void fire(Camera2Result camera2Result) {
        String str = TAG;
        Log.d(str, "fire: result = " + camera2Result + ", requested cid = " + this.mPendingCameraId.get());
        delay();
        if (this.mCameraResultEmitter == null || this.mCameraResultEmitter.isDisposed()) {
            String str2 = TAG;
            Log.d(str2, "fire: skipped: " + this.mCameraResultEmitter);
        } else {
            Log.d(TAG, "fire: emitted");
            this.mCameraResultEmitter.onNext(camera2Result);
            this.mCameraResultEmitter.onComplete();
        }
    }

    private static long getCameraOpTimeout() {
        return ModuleManager.isProModule() ? CAMERA_OPEN_OR_CLOSE_TIMEOUT + (CameraSettings.getExposureTime() / ExtraTextUtils.MB) : CAMERA_OPEN_OR_CLOSE_TIMEOUT;
    }

    public static Camera2OpenManager getInstance() {
        return sInstance.get();
    }

    static /* synthetic */ ObservableSource h(Throwable th) throws Exception {
        String str = TAG;
        Log.d(str, "Exception occurs in camera open or close: " + th);
        if (!CameraService.getCameraCallableHandler().getLooper().getQueue().isPolling()) {
            Log.d(TAG, "CameraHandlerThread is being stuck...");
        }
        for (CameraHandlerThread.Cookie cookie : CameraService.getCookieStore().getCookies()) {
            if (cookie != null) {
                Log.d(TAG, "open camera timeout cookie.mIsOpening false");
                cookie.mIsOpening = false;
            }
        }
        return Observable.just(Camera2Result.create(3).setCameraError(236));
    }

    private void subscribeCameraResultObservable(Observer<Camera2Result> observer) {
        Log.d(TAG, "subscribeCameraResultObservable: E");
        ObservableEmitter<Camera2Result> observableEmitter = this.mCameraResultEmitter;
        if (observableEmitter == null || observableEmitter.isDisposed()) {
            this.mCameraResultObservable = Observable.create(this).timeout(getCameraOpTimeout(), TimeUnit.MILLISECONDS).onErrorResumeNext(c.INSTANCE).observeOn(CameraSchedulers.sCameraSetupScheduler).publish();
            this.mCameraResultObservable.subscribe(observer);
            this.mCameraResultObservable.connect();
        } else {
            this.mCameraResultObservable.subscribe(observer);
        }
        Log.d(TAG, "subscribeCameraResultObservable: X");
    }

    public synchronized CameraHandlerThread.CookieStore getCookieStore() {
        return CameraService.getCookieStore();
    }

    @Override // com.xiaomi.camera.device.callable.OpenCameraListener
    public synchronized void onClosed(String str) {
        String str2 = TAG;
        Log.d(str2, "onClosed: cid = " + str);
        if (DataRepository.dataItemFeature().zi() && this.mPendingCameraId.get() == -1 && (Integer.parseInt(str) == 1 || this.mPopCameraTimestamp.get() > 0)) {
            boolean takebackMotor = CompatibilityUtils.takebackMotor();
            String str3 = TAG;
            Log.d(str3, "onClosed: takebackMotor = " + takebackMotor);
        }
    }

    @Override // com.xiaomi.camera.device.callable.OpenCameraListener
    public synchronized void onDisconnected(String str) {
        String str2 = TAG;
        Log.d(str2, "onDisconnected: cid = " + str);
    }

    @Override // com.xiaomi.camera.device.callable.OpenCameraListener
    public synchronized void onError(String str, int i) {
        String str2 = TAG;
        Log.d(str2, "onClosed: cid = " + str + ", errno = " + i);
    }

    @Override // com.xiaomi.camera.device.callable.OpenCameraListener
    public synchronized void onOpened(String str) {
        String str2 = TAG;
        Log.d(str2, "onOpened: cid = " + str);
    }

    @SuppressLint({"CheckResult"})
    public synchronized void openCamera(int i, int i2, Observer<Camera2Result> observer, boolean z) {
        int actualOpenCameraId = Camera2DataContainer.getInstance().getActualOpenCameraId(i, i2);
        if (DataRepository.dataItemFeature().zi()) {
            boolean z2 = actualOpenCameraId == 1;
            if (z2 && this.mPendingCameraId.get() != actualOpenCameraId) {
                boolean popupMotor = CompatibilityUtils.popupMotor();
                this.mPopCameraTimestamp.set(System.currentTimeMillis());
                String str = TAG;
                Log.d(str, "openCamera: popupMotor = " + popupMotor);
            } else if (!z2) {
                this.mPopCameraTimestamp.set(-1);
            }
        }
        String str2 = TAG;
        Log.d(str2, "cid: " + this.mPendingCameraId.get() + " -> " + actualOpenCameraId + ", mid: " + this.mCurrentModule.get() + " -> " + i2 + ", fcc: " + z);
        if (!(this.mPendingCameraId.get() == actualOpenCameraId && this.mCurrentModule.get() == i2)) {
            this.mPendingCameraId.set(actualOpenCameraId);
            this.mCurrentModule.set(i2);
            abandonCameraResultObservable();
        }
        this.mCompositeDisposable.clear();
        CameraService.removeCameraCallables();
        String[] strArr = z ? null : i2 == 204 ? new String[]{DataRepository.dataItemRunning().getComponentRunningDualVideo().getIds().get(100).toString(), DataRepository.dataItemRunning().getComponentRunningDualVideo().getIds().get(101).toString()} : new String[]{String.valueOf(actualOpenCameraId)};
        CameraService.closeCamera(null, null, null, strArr);
        subscribeCameraResultObservable(observer);
        if (i2 == 204) {
            ConcurrentHashMap<Integer, Integer> ids = DataRepository.dataItemRunning().getComponentRunningDualVideo().getIds();
            String str3 = TAG;
            Log.d(str3, "dual video openCamera: " + ids.toString());
            this.mCompositeDisposable.add(CameraOpenObservable.create(String.valueOf(ids.get(101)), new String[0]).flatMap(new b(ids)).subscribe(new a(this)));
        } else {
            this.mCompositeDisposable.add(CameraOpenObservable.create(String.valueOf(actualOpenCameraId), strArr).subscribe(new a(this)));
        }
        if (ParallelSnapshotManager.isParallelTagOpen) {
            ParallelSnapshotManager.getInstance().openCamera();
        }
    }

    public synchronized void release(boolean z) {
        this.mPendingCameraId.set(-1);
        this.mCompositeDisposable.clear();
        CameraService.removeCameraCallables();
        abandonCameraResultObservable();
        CameraService.release(null, z, null, null);
    }

    @Override // io.reactivex.ObservableOnSubscribe
    public synchronized void subscribe(ObservableEmitter<Camera2Result> observableEmitter) throws Exception {
        this.mCameraResultEmitter = observableEmitter;
    }
}
