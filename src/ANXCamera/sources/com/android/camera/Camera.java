package com.android.camera;

import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.database.ContentObserver;
import android.hardware.camera2.CameraManager;
import android.os.Bundle;
import android.os.IBinder;
import android.os.LocaleList;
import android.os.Message;
import android.os.SystemClock;
import android.os.SystemProperties;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.OrientationEventListener;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.MainThread;
import androidx.core.app.ActivityCompat;
import androidx.documentfile.provider.DocumentFile;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import b.c.a.c;
import com.android.camera.CameraIntentManager;
import com.android.camera.LocalParallelService;
import com.android.camera.Manifest;
import com.android.camera.ThermalDetector;
import com.android.camera.aftersales.AftersalesManager;
import com.android.camera.data.DataRepository;
import com.android.camera.data.data.config.DataItemConfig;
import com.android.camera.data.data.global.DataItemGlobal;
import com.android.camera.data.observeable.VMProcessing;
import com.android.camera.effect.EffectController;
import com.android.camera.external.NotifyExternalManager;
import com.android.camera.fragment.BaseFragmentDelegate;
import com.android.camera.fragment.dialog.AiSceneNewbieDialogFragment;
import com.android.camera.fragment.dialog.BaseDialogFragment;
import com.android.camera.fragment.dialog.FrontRotateNewbieDialogFragment;
import com.android.camera.fragment.dialog.HibernationFragment;
import com.android.camera.fragment.dialog.IDCardModeNewbieDialogFragment;
import com.android.camera.fragment.dialog.LensDirtyDetectDialogFragment;
import com.android.camera.fragment.dialog.LongPressLiveFragment;
import com.android.camera.fragment.dialog.MacroModeNewbieDialogFragment;
import com.android.camera.fragment.dialog.PortraitNewbieDialogFragment;
import com.android.camera.fragment.dialog.UltraTeleNewbieDialogFragment;
import com.android.camera.fragment.dialog.UltraWideNewbieDialogFragment;
import com.android.camera.fragment.dialog.VVNewbieDialogFragment;
import com.android.camera.fragment.lifeCircle.BaseLifecycleListener;
import com.android.camera.fragment.top.FragmentTopConfig;
import com.android.camera.lib.compatibility.related.vibrator.ViberatorContext;
import com.android.camera.lib.compatibility.util.CompatibilityUtils;
import com.android.camera.log.Log;
import com.android.camera.module.BaseModule;
import com.android.camera.module.Camera2Module;
import com.android.camera.module.Module;
import com.android.camera.module.ModuleManager;
import com.android.camera.module.impl.ImplFactory;
import com.android.camera.module.loader.FunctionCameraPrepare;
import com.android.camera.module.loader.FunctionDataSetup;
import com.android.camera.module.loader.FunctionModuleSetup;
import com.android.camera.module.loader.FunctionResumeModule;
import com.android.camera.module.loader.FunctionUISetup;
import com.android.camera.module.loader.NullHolder;
import com.android.camera.module.loader.StartControl;
import com.android.camera.module.loader.camera2.Camera2DataContainer;
import com.android.camera.module.loader.camera2.Camera2OpenManager;
import com.android.camera.module.loader.camera2.Camera2OpenOnSubscribe;
import com.android.camera.module.loader.camera2.Camera2Result;
import com.android.camera.module.loader.camera2.CompletablePreFixCamera2Setup;
import com.android.camera.module.loader.camera2.ParallelSnapshotManager;
import com.android.camera.parallel.AlgoConnector;
import com.android.camera.permission.PermissionManager;
import com.android.camera.protocol.ModeCoordinatorImpl;
import com.android.camera.protocol.ModeProtocol;
import com.android.camera.scene.MiAlgoAsdSceneProfile;
import com.android.camera.statistic.CameraStatUtils;
import com.android.camera.statistic.MistatsConstants;
import com.android.camera.statistic.MistatsWrapper;
import com.android.camera.statistic.ScenarioTrackUtil;
import com.android.camera.storage.ImageSaver;
import com.android.camera.storage.PriorityStorageBroadcastReceiver;
import com.android.camera.storage.Storage;
import com.android.camera.ui.CameraRootView;
import com.android.camera.ui.V6CameraGLSurfaceView;
import com.android.camera.ui.V6GestureRecognizer;
import com.android.camera.ui.V9EdgeShutterView;
import com.android.camera2.Camera2Proxy;
import com.android.lens.LensAgent;
import com.google.lens.sdk.LensApi;
import com.ss.android.ugc.effectmanager.effect.model.ComposerHelper;
import com.xiaomi.camera.device.CameraHandlerThread;
import com.xiaomi.camera.device.CameraService;
import com.xiaomi.camera.device.callable.CallableReturn;
import com.xiaomi.camera.device.callable.CameraCallable;
import com.xiaomi.camera.imagecodec.ImagePool;
import com.xiaomi.camera.rx.CameraSchedulers;
import com.xiaomi.camera.util.ThreadUtils;
import io.reactivex.Completable;
import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.BiFunction;
import io.reactivex.functions.Consumer;
import java.lang.ref.WeakReference;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import miui.hardware.display.DisplayFeatureManager;
import miui.os.Build;

public class Camera extends ActivityBase implements ActivityCompat.OnRequestPermissionsResultCallback, BaseLifecycleListener, ModeProtocol.BaseProtocol {
    private static final int PERMISSION_REQUEST_CODE = 1;
    private static final int PERMISSION_RESULT_CODE = 2308;
    private static final int REPEAT_KEY_EVENT_GAP = 250;
    private final String TAG = (Camera.class.getSimpleName() + "@" + hashCode());
    private BaseFragmentDelegate mBaseFragmentDelegate;
    private Camera2OpenOnSubscribe mCamera2OpenOnSubscribe;
    private BiFunction<NullHolder<BaseModule>, Camera2Result, NullHolder<BaseModule>> mCameraOpenResult = new g(this);
    private Disposable mCameraPendingSetupDisposable;
    private Consumer<NullHolder<BaseModule>> mCameraSetupConsumer = new Consumer<NullHolder<BaseModule>>() {
        /* class com.android.camera.Camera.AnonymousClass2 */

        public void accept(@NonNull NullHolder<BaseModule> nullHolder) throws Exception {
            if (!nullHolder.isPresent()) {
                Camera.this.showCameraError(nullHolder.getException());
                Camera.this.mCurrentModule = null;
            } else {
                V6GestureRecognizer.getInstance(Camera.this).setCurrentModule(nullHolder.get());
            }
            Camera.this.setSwitchingModule(false);
            Camera.this.getCameraScreenNail().resetFrameAvailableFlag();
            ThreadUtils.assertMainThread();
            Camera.this.mCameraSetupDisposable = null;
            DataRepository.dataCloudMgr().fillCloudValues();
            AutoLockManager.getInstance(Camera.this).hibernateDelayed();
            if (CameraSettings.isCameraParallelProcessEnable()) {
                ImagePool.getInstance().trimPoolBuffer();
            }
            Log.d(Camera.this.TAG, "CameraSetupConsumer#accept: switch module done");
        }
    };
    private Disposable mCameraSetupDisposable;
    private int mCurrentDisplayMode;
    private LogThread mDebugThread;
    private boolean mDidRegister;
    private DisplayFeatureManager mDisplayFeatureManager;
    private boolean mFirstOrientationArrived;
    private boolean mHasBeenSetupOnFocusChanged = false;
    private boolean mHasFocus;
    private ImageSaver mImageSaver;
    private ImplFactory mImplFactory;
    private volatile boolean mIntentPhotoDone;
    private boolean mIsGalleryServiceBound = false;
    private boolean mIsInRequestRuntimePermission = false;
    private boolean mIsLunchFromAutoTest = false;
    private boolean mIsModeSwitched;
    private boolean mIsScreenSlideOff;
    private int mLastIgnoreKey = -1;
    private long mLastKeyDownEventTime = 0;
    private long mLastKeyUpEventTime = 0;
    private LensApi mLensApi;
    private ModeProtocol.MainContentProtocol mMainContentProtocolForDispatchTouch;
    private ModeProtocol.ModeChangeController mMoreChangeCtrl;
    private ModeProtocol.MoreModePopupController mMoreModePopupController;
    private MyOrientationEventListener mOrientationListener;
    private ProximitySensorLock mProximitySensorLock;
    private final BroadcastReceiver mReceiver = new BroadcastReceiver() {
        /* class com.android.camera.Camera.AnonymousClass6 */

        public void onReceive(Context context, Intent intent) {
            if (CameraIntentManager.ACTION_VOICE_CONTROL.equals(intent.getAction()) && CameraIntentManager.getInstance(intent).getVoiceControlAction().equals(CameraIntentManager.ControlActions.CONTROL_ACTION_QUERY_CAMERA_STATUS)) {
                int currentCameraId = DataRepository.dataItemGlobal().getCurrentCameraId();
                NotifyExternalManager.getInstance(Camera.this.getApplicationContext()).notifyModeAndFacing(DataRepository.dataItemGlobal().getCurrentMode(), currentCameraId);
            }
            Module module = Camera.this.mCurrentModule;
            if (module != null && !module.isDeparted()) {
                Camera.this.mCurrentModule.onBroadcastReceived(context, intent);
            }
        }
    };
    private BroadcastReceiver mSDReceiver = new BroadcastReceiver() {
        /* class com.android.camera.Camera.AnonymousClass7 */

        public void onReceive(Context context, Intent intent) {
            if (!Camera.this.mCurrentModule.isDeparted()) {
                Camera.this.mCurrentModule.onBroadcastReceived(context, intent);
            }
        }
    };
    private ContentObserver mScreenSlideStatusObserver = new ContentObserver(this.mHandler) {
        /* class com.android.camera.Camera.AnonymousClass3 */

        public boolean deliverSelfNotifications() {
            return true;
        }

        public void onChange(boolean z) {
            super.onChange(z);
            if (!Camera.this.mHasFocus && !Camera.this.mActivityPaused) {
                int i = Util.isScreenSlideOff(Camera.this) ? Util.KEYCODE_SLIDE_OFF : Util.KEYCODE_SLIDE_ON;
                String str = Camera.this.TAG;
                Log.d(str, "focus lost, try key code: " + i);
                Camera.this.onKeyDown(i, new KeyEvent(0, i));
            }
        }
    };
    private SensorStateManager mSensorStateManager;
    private ServiceConnection mServiceConnection = new ServiceConnection() {
        /* class com.android.camera.Camera.AnonymousClass4 */

        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
        }

        public void onServiceDisconnected(ComponentName componentName) {
        }
    };
    private StartControl mStartControl;
    private ThermalDetector.OnThermalNotificationListener mThermalNotificationListener = new a(this);
    private volatile int mTick;
    private ModeProtocol.TopAlert mTopAlert;
    private Thread mWatchDog;
    private String newbieDialogFragmentTag = null;
    private final Runnable tickerRunnable = new Runnable() {
        /* class com.android.camera.Camera.AnonymousClass1 */

        public void run() {
            Camera camera = Camera.this;
            camera.mTick = (camera.mTick + 1) % 10;
        }
    };

    /* access modifiers changed from: private */
    public static class AdjBoostCallable extends CameraCallable<Void> {
        private static final String TAG = "AdjBoostCallable";

        private AdjBoostCallable() {
            super(null, null);
        }

        @Override // com.xiaomi.camera.device.callable.CameraCallable
        @androidx.annotation.NonNull
        public CallableReturn<Void> call() {
            Iterator<CameraHandlerThread.Cookie> it = getCookieStore().getCookies().iterator();
            while (true) {
                if (it.hasNext()) {
                    Camera2Proxy camera2Proxy = it.next().mCamera2Device;
                    if (camera2Proxy != null && camera2Proxy.isCaptureBusy(true)) {
                        Util.adjBoost();
                        break;
                    }
                } else {
                    break;
                }
            }
            return new CallableReturn<>((Exception) null);
        }

        /* access modifiers changed from: protected */
        @Override // com.xiaomi.camera.device.callable.CameraCallable
        public String getTag() {
            return TAG;
        }
    }

    /* access modifiers changed from: private */
    public static class CameraRunnable implements Runnable {
        private static final String TAG = "CameraRunnable";
        private WeakReference<Camera> mCamera;
        private boolean mReleaseDevice;
        private boolean mReleaseImmediate;

        CameraRunnable(WeakReference<Camera> weakReference, boolean z, boolean z2) {
            this.mCamera = weakReference;
            this.mReleaseDevice = z;
            this.mReleaseImmediate = z2;
        }

        public void run() {
            Camera camera = this.mCamera.get();
            if (camera != null) {
                if (camera.isCurrentModuleAlive()) {
                    Module module = camera.mCurrentModule;
                    if (ModeCoordinatorImpl.isAlive(camera.hashCode())) {
                        module.unRegisterProtocol();
                        module.unRegisterModulePersistProtocol();
                    }
                    module.onPause();
                    module.onStop();
                    module.onDestroy();
                }
                DataRepository.dataItemGlobal().resetTimeOut();
                if (this.mReleaseDevice) {
                    boolean containsResumedCameraInStack = camera.mApplication.containsResumedCameraInStack();
                    StringBuilder sb = new StringBuilder();
                    sb.append("start releaseCameraDevice: ");
                    sb.append(!containsResumedCameraInStack);
                    Log.d(TAG, sb.toString());
                    if (!containsResumedCameraInStack) {
                        Camera2OpenManager.getInstance().release(this.mReleaseImmediate);
                        if (!(DataRepository.dataItemFeature().zi() && camera.mApplication.containsResumedCameraInStack())) {
                            camera.releaseCameraScreenNail();
                            return;
                        }
                        return;
                    }
                    Log.d(TAG, "Camera2OpenManager release ignored.");
                }
            }
        }
    }

    public static class HibernateRunnable implements Runnable {
        WeakReference<Module> mModule;

        public HibernateRunnable(Module module) {
            this.mModule = new WeakReference<>(module);
        }

        public void run() {
            WeakReference<Module> weakReference = this.mModule;
            Module module = weakReference != null ? weakReference.get() : null;
            if (module != null && module.isCreated()) {
                module.setDeparted();
                module.canIgnoreFocusChanged();
            }
            Camera2OpenManager.getInstance().release(true);
        }
    }

    /* access modifiers changed from: package-private */
    public class LogThread extends Thread {
        private volatile boolean mRunFlag = true;

        LogThread() {
        }

        public void run() {
            while (this.mRunFlag) {
                try {
                    Thread.sleep(10);
                    if (!Camera.this.isActivityPaused()) {
                        Camera.this.mHandler.obtainMessage(0, Util.getDebugInfo()).sendToTarget();
                    }
                } catch (Exception e2) {
                    e2.printStackTrace();
                    return;
                }
            }
        }

        public void setRunFlag(boolean z) {
            this.mRunFlag = z;
        }
    }

    private class MyOrientationEventListener extends OrientationEventListener {
        public MyOrientationEventListener(Context context) {
            super(context);
        }

        public void onOrientationChanged(int i) {
            if (c.isMTKPlatform()) {
                android.util.Log.d("MTKCAMERAXM", "onOrientationChanged: " + i);
            }
            if (i != -1) {
                Camera camera = Camera.this;
                camera.mOrientation = Util.roundOrientation(i, camera.mOrientation);
                if (Camera.this.mCurrentDisplayMode == 2) {
                    Camera camera2 = Camera.this;
                    camera2.mOrientation = 360 - camera2.mOrientation;
                    i = 360 - i;
                }
                if (!Camera.this.mFirstOrientationArrived) {
                    Camera.this.mFirstOrientationArrived = true;
                    String str = Camera.this.TAG;
                    Log.d(str, "onOrientationChanged: first orientation is arrived... , orientation = " + i + ", mOrientation = " + Camera.this.mOrientation);
                }
                int displayRotation = Util.getDisplayRotation(Camera.this);
                Camera camera3 = Camera.this;
                if (displayRotation != camera3.mDisplayRotation) {
                    camera3.mDisplayRotation = displayRotation;
                    camera3.onDisplayRotationChanged();
                }
                Camera camera4 = Camera.this;
                int i2 = camera4.mOrientation;
                camera4.mOrientationCompensation = (camera4.mDisplayRotation + i2) % 360;
                Module module = camera4.mCurrentModule;
                if (module != null) {
                    module.onOrientationChanged(i2, camera4.mOrientationCompensation, i);
                }
                if (Camera.this.mBaseFragmentDelegate != null) {
                    Camera.this.mBaseFragmentDelegate.getAnimationComposite().disposeRotation(Camera.this.mOrientationCompensation);
                }
            }
        }
    }

    /* access modifiers changed from: private */
    public class WatchDogThread extends Thread {
        private static final String TAG = "WatchDogThread";
        private static final int TIMEOUT_INTERVAL = 5000;

        private WatchDogThread() {
        }

        public void run() {
            setName("ANR-WatchDog");
            while (!isInterrupted()) {
                Log.v(TAG, "watch dog run " + Thread.currentThread().getId());
                int i = Camera.this.mTick;
                Camera camera = Camera.this;
                camera.mHandler.post(camera.tickerRunnable);
                try {
                    Thread.sleep(5000);
                    if (Camera.this.mTick == i) {
                        if (c.Jn()) {
                            CameraSettings.setEdgeMode(Camera.this, false);
                        }
                        Camera.this.setBrightnessRampRate(-1);
                        Camera.this.setScreenEffect(false);
                        AftersalesManager.getInstance().count(System.currentTimeMillis(), 2);
                        if (Util.sIsKillCameraService && DataRepository.dataItemFeature().oj() && SystemClock.elapsedRealtime() - CameraSettings.getBroadcastKillServiceTime() > 60000) {
                            Log.d(TAG, "ANR: broadcastKillService");
                            Util.broadcastKillService(Camera.this, false);
                            return;
                        }
                        return;
                    }
                } catch (InterruptedException unused) {
                    Log.e(TAG, "watch dog InterruptedException " + Thread.currentThread().getId());
                    return;
                }
            }
        }
    }

    private void bindServices() {
        try {
            Intent intent = new Intent(Util.ACTION_BIND_GALLERY_SERVICE);
            intent.setPackage(Util.REVIEW_ACTIVITY_PACKAGE);
            bindService(intent, this.mServiceConnection, 5);
            this.mIsGalleryServiceBound = true;
        } catch (Exception e2) {
            Log.w(this.TAG, "bindServices error.", e2.getCause());
        }
    }

    private void boostParallelServiceAdj() {
        if (!this.mCameraIntentManager.isImageCaptureIntent()) {
            LocalParallelService.LocalBinder localBinder = AlgoConnector.getInstance().getLocalBinder();
            boolean z = true;
            if (CameraSettings.isCameraParallelProcessEnable() && localBinder != null && !localBinder.isIdle()) {
                Util.adjBoost();
                return;
            }
            ImageSaver imageSaver = this.mImageSaver;
            if (imageSaver == null || !imageSaver.isPendingSave()) {
                z = false;
            }
            if (z) {
                Util.adjBoost();
            } else {
                CameraService.addStickyCameraCallable(new AdjBoostCallable());
            }
        }
    }

    private void closeCameraSetup() {
        ThreadUtils.assertMainThread();
        Log.d(this.TAG, "closeCameraSetup: CameraPendingSetupDisposable: X");
        Disposable disposable = this.mCameraPendingSetupDisposable;
        if (disposable != null && !disposable.isDisposed()) {
            this.mCameraPendingSetupDisposable.dispose();
            this.mCameraPendingSetupDisposable = null;
        }
        Log.d(this.TAG, "closeCameraSetup: CameraSetupDisposable: X");
        Disposable disposable2 = this.mCameraSetupDisposable;
        if (disposable2 != null && !disposable2.isDisposed()) {
            this.mCameraSetupDisposable.dispose();
            this.mCameraSetupDisposable = null;
        }
    }

    private Module createNewModule(StartControl startControl) {
        Module moduleByIndex = ModuleManager.getModuleByIndex(startControl.mTargetMode);
        if (moduleByIndex != null) {
            moduleByIndex.setActivity(this);
            moduleByIndex.fillFeatureControl(startControl);
            moduleByIndex.preTransferOrientation(this.mOrientation, this.mOrientationCompensation);
            return moduleByIndex;
        }
        throw new RuntimeException("invalid module index " + startControl.mTargetMode);
    }

    private void hideHibernationFragment() {
        Fragment findFragmentByTag = getSupportFragmentManager().findFragmentByTag(HibernationFragment.TAG);
        if (findFragmentByTag != null && (findFragmentByTag instanceof DialogFragment)) {
            ((DialogFragment) findFragmentByTag).dismissAllowingStateLoss();
        }
    }

    private boolean interceptTouchEvent(MotionEvent motionEvent) {
        if (this.mMoreChangeCtrl == null) {
            this.mMoreChangeCtrl = (ModeProtocol.ModeChangeController) ModeCoordinatorImpl.getInstance().getAttachProtocol(179);
        }
        ModeProtocol.ModeChangeController modeChangeController = this.mMoreChangeCtrl;
        if (modeChangeController == null || !modeChangeController.modeChanging()) {
            if (this.mMoreModePopupController == null) {
                this.mMoreModePopupController = (ModeProtocol.MoreModePopupController) ModeCoordinatorImpl.getInstance().getAttachProtocol(2561);
            }
            ModeProtocol.MoreModePopupController moreModePopupController = this.mMoreModePopupController;
            if (moreModePopupController != null && moreModePopupController.isExpanded()) {
                return false;
            }
            if (this.mTopAlert == null) {
                this.mTopAlert = (ModeProtocol.TopAlert) ModeCoordinatorImpl.getInstance().getAttachProtocol(172);
            }
            ModeProtocol.TopAlert topAlert = this.mTopAlert;
            if (topAlert != null && topAlert.isExtraMenuShowing()) {
                return false;
            }
            if (this.mMainContentProtocolForDispatchTouch == null) {
                this.mMainContentProtocolForDispatchTouch = (ModeProtocol.MainContentProtocol) ModeCoordinatorImpl.getInstance().getAttachProtocol(166);
            }
            ModeProtocol.MainContentProtocol mainContentProtocol = this.mMainContentProtocolForDispatchTouch;
            if (mainContentProtocol != null) {
                mainContentProtocol.checkTouchRegionContainSplitFocusExposure(motionEvent);
                if (this.mMainContentProtocolForDispatchTouch.isSplitFocusExposureDown()) {
                    V6GestureRecognizer.getInstance(this).onTouchEvent(motionEvent);
                    return true;
                }
            }
            Module module = this.mCurrentModule;
            if (module != null && (module instanceof Camera2Module)) {
                Camera2Module camera2Module = (Camera2Module) module;
                if (camera2Module.isLongPressedRecording() && motionEvent.getAction() == 262) {
                    camera2Module.onSingleTapUp((int) motionEvent.getX(1), (int) motionEvent.getY(1), false);
                }
            }
            return false;
        } else if (motionEvent.getActionMasked() != 5) {
            return false;
        } else {
            Log.d(this.TAG, "Touch event intercept caz mode change.");
            return true;
        }
    }

    private boolean isFromKeyguard() {
        Intent intent = getIntent();
        if (intent == null) {
            return false;
        }
        String action = intent.getAction();
        return (TextUtils.equals(action, "android.media.action.STILL_IMAGE_CAMERA") || TextUtils.equals(action, "android.media.action.STILL_IMAGE_CAMERA_SECURE")) && getKeyguardFlag();
    }

    private boolean isFromOneShotKeyPressed(KeyEvent keyEvent) {
        boolean isTimeout = Util.isTimeout(keyEvent.getEventTime(), this.mLastKeyUpEventTime, 250);
        boolean z = this.mLastKeyDownEventTime > this.mLastKeyUpEventTime;
        if (isTimeout && !z) {
            return false;
        }
        String str = this.TAG;
        Log.i(str, "isFromOneShotKeyPressed: lastUpTIme " + this.mLastKeyUpEventTime + " | eventTime " + keyEvent.getEventTime() + " isKeyEventOrderWrong: " + z);
        return true;
    }

    /* access modifiers changed from: private */
    /* access modifiers changed from: public */
    private void onDisplayRotationChanged() {
        FrontRotateNewbieDialogFragment frontRotateNewbieDialogFragment;
        if (c.Rm() && (frontRotateNewbieDialogFragment = (FrontRotateNewbieDialogFragment) getSupportFragmentManager().findFragmentByTag(FrontRotateNewbieDialogFragment.TAG)) != null) {
            frontRotateNewbieDialogFragment.animateOut(0);
        }
    }

    private void parseLocationPermission(String[] strArr, int[] iArr) {
        if (PermissionManager.isContainLocationPermissions(strArr)) {
            boolean isCameraLocationPermissionsResultReady = PermissionManager.isCameraLocationPermissionsResultReady(strArr, iArr);
            String str = this.TAG;
            Log.d(str, "onRequestPermissionsResult: is location granted = " + isCameraLocationPermissionsResultReady);
            CameraSettings.updateRecordLocationPreference(isCameraLocationPermissionsResultReady);
            if (!isActivityPaused()) {
                LocationManager.instance().recordLocation(CameraSettings.isRecordLocation());
            }
        }
    }

    private void prefixCamera2Setup() {
        Completable.create(new CompletablePreFixCamera2Setup(null, null, null, getIntent(), hashCode(), startFromSecureKeyguard(), this.mCameraIntentManager.checkCallerLegality())).subscribeOn(CameraSchedulers.sCameraSetupScheduler).subscribe();
    }

    private final void registerReceiver() {
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("android.intent.action.REBOOT");
        intentFilter.addAction("android.intent.action.ACTION_SHUTDOWN");
        intentFilter.addAction(CameraIntentManager.ACTION_SPEECH_SHUTTER);
        registerReceiver(this.mReceiver, intentFilter);
        IntentFilter intentFilter2 = new IntentFilter();
        intentFilter2.addAction(CameraIntentManager.ACTION_VOICE_CONTROL);
        registerReceiver(this.mReceiver, intentFilter2, Manifest.permission.AUX_CONTROL, null);
        this.mDidRegister = true;
    }

    private void registerSDReceiver() {
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("android.intent.action.MEDIA_EJECT");
        intentFilter.addAction("android.intent.action.MEDIA_MOUNTED");
        intentFilter.addAction("android.intent.action.MEDIA_UNMOUNTED");
        intentFilter.addAction("android.intent.action.MEDIA_SCANNER_STARTED");
        intentFilter.addAction("android.intent.action.MEDIA_SCANNER_FINISHED");
        intentFilter.addDataScheme(ComposerHelper.COMPOSER_PATH);
        registerReceiver(this.mSDReceiver, intentFilter);
    }

    private void releaseAll(boolean z, boolean z2, boolean z3) {
        boolean isFinishing = isFinishing();
        String str = this.TAG;
        Log.d(str, "releaseAll: releaseDevice = " + z2 + ", isCurrentModuleAlive = " + isCurrentModuleAlive() + ", releaseImmediate = " + z3 + ", isFinishing = " + isFinishing);
        this.mReleaseByModule = false;
        if (ParallelSnapshotManager.isParallelTagOpen) {
            ParallelSnapshotManager.getInstance().setCameraCloseState(true, hashCode());
        }
        Module module = this.mCurrentModule;
        if (module != null) {
            module.setDeparted();
        }
        CameraSchedulers.sCameraSetupScheduler.scheduleDirect(new CameraRunnable(new WeakReference(this), z2, z3));
    }

    private void resumeCamera() {
        boolean z;
        int i;
        Module module;
        Log.d(this.TAG, "resumeCamera: E");
        if (!isSwitchingModule()) {
            if (!ModeCoordinatorImpl.isAlive(hashCode())) {
                Log.d(this.TAG, "resumeCamera: module is obsolete");
                HybridZoomingSystem.clearZoomRatioHistory();
                unRegisterProtocol();
                registerProtocol();
            } else {
                DataItemGlobal dataItemGlobal = DataRepository.dataItemGlobal();
                boolean checkCallerLegality = this.mCameraIntentManager.checkCallerLegality();
                int i2 = 1;
                boolean z2 = false;
                if (isJumpBack()) {
                    Log.d(this.TAG, "resumeCamera: from gallery, mReleaseByModule = " + this.mReleaseByModule);
                    this.mJumpedToGallery = false;
                    if (!this.mReleaseByModule || (module = this.mCurrentModule) == null || !module.isShot2GalleryOrEnableParallel()) {
                        z = false;
                    } else {
                        this.mCurrentModule.enableCameraControls(true);
                        this.mReleaseByModule = false;
                        return;
                    }
                } else {
                    int currentCameraId = dataItemGlobal.getCurrentCameraId();
                    int intentType = dataItemGlobal.getIntentType();
                    int currentMode = dataItemGlobal.getCurrentMode();
                    boolean isCameraAliveWhenResume = isCameraAliveWhenResume();
                    dataItemGlobal.parseIntent(getIntent(), Boolean.valueOf(checkCallerLegality), startFromSecureKeyguard(), false, !isCameraAliveWhenResume);
                    int intentType2 = dataItemGlobal.getIntentType();
                    int currentMode2 = dataItemGlobal.getCurrentMode();
                    int currentCameraId2 = dataItemGlobal.getCurrentCameraId();
                    z = currentMode != currentMode2;
                    if (intentType != 0) {
                        Module module2 = this.mCurrentModule;
                        if (module2 != null && module2.isSelectingCapturedResult()) {
                            z2 = true;
                        }
                        Log.d(this.TAG, "resumeCamera: lastType=" + intentType + " curType=" + intentType2 + " captureFinish=" + z2);
                        if (intentType == intentType2 && z2) {
                            resumeCurrentMode(currentMode2);
                            return;
                        } else if (z2) {
                            this.mBaseFragmentDelegate.delegateEvent(6);
                        }
                    } else {
                        Log.d(this.TAG, "resumeCamera: lastType=" + intentType + " | mReleaseByModule " + this.mReleaseByModule);
                        if (isCameraAliveWhenResume && currentCameraId == currentCameraId2 && !z) {
                            this.mBaseFragmentDelegate.getAnimationComposite().notifyAfterFirstFrameArrived(4);
                            this.mReleaseByModule = false;
                            return;
                        }
                    }
                }
                if (dataItemGlobal.isTimeOut() || z) {
                    BaseFragmentDelegate baseFragmentDelegate = this.mBaseFragmentDelegate;
                    if (baseFragmentDelegate != null) {
                        if (baseFragmentDelegate.getActiveFragment(R.id.full_screen_feature) == 1048561) {
                            this.mBaseFragmentDelegate.delegateEvent(11);
                        }
                        this.mBaseFragmentDelegate.delegateEvent(7);
                    }
                    i = 3;
                } else {
                    i = 2;
                }
                if (i != 3 && checkCallerLegality) {
                    i2 = 2;
                } else if (i != 3 && dataItemGlobal.getCurrentMode() == 179) {
                    if (((VMProcessing) DataRepository.dataItemObservable().get(VMProcessing.class)).getCurrentState() == 7) {
                        Log.d(this.TAG, "resumeCamera: vv combine, return");
                        return;
                    }
                    i2 = -1;
                }
                HybridZoomingSystem.clearZoomRatioHistory();
                onModeSelected(StartControl.create(dataItemGlobal.getCurrentMode()).setResetType(i).setViewConfigType(i2));
            }
            Log.d(this.TAG, "resumeCamera: X");
        }
    }

    /* access modifiers changed from: private */
    /* access modifiers changed from: public */
    private void setBrightnessRampRate(int i) {
    }

    /* access modifiers changed from: private */
    /* access modifiers changed from: public */
    private void setScreenEffect(boolean z) {
        DisplayFeatureManager displayFeatureManager = this.mDisplayFeatureManager;
        if (displayFeatureManager != null) {
            displayFeatureManager.setScreenEffect(14, z ? 1 : 0);
        }
    }

    private void setTranslucentNavigation(boolean z) {
        if (Util.checkDeviceHasNavigationBar(this)) {
            Window window = getWindow();
            window.getDecorView().setSystemUiVisibility(768);
            window.addFlags(Integer.MIN_VALUE);
        }
    }

    private void setupCamera(StartControl startControl) {
        ThreadUtils.assertMainThread();
        if (this.mIsInRequestRuntimePermission) {
            Log.d(this.TAG, "setupCamera: skipped since we're requesting runtime permission.");
        } else if (isActivityPaused()) {
            Log.w(this.TAG, "setupCamera: skipped since activity is paused!");
            setSwitchingModule(false);
        } else if (!PermissionManager.checkCameraLaunchPermissions()) {
            Log.w(this.TAG, "setupCamera: skipped since some permissions need to be granted");
            setSwitchingModule(false);
        } else if (startControl.mResetType == 4 && startControl.mTargetMode == getCurrentModuleIndex() && this.mCurrentModule.isCreated()) {
            Log.w(this.TAG, "setupCamera: skipped since module has been created");
        } else {
            Log.d(this.TAG, "setupCamera: E");
            closeCameraSetup();
            FunctionCameraPrepare functionCameraPrepare = new FunctionCameraPrepare(startControl.mTargetMode, startControl.mLastMode, startControl.mResetType, startControl.mNeedReConfigureData, (BaseModule) this.mCurrentModule);
            FunctionModuleSetup functionModuleSetup = new FunctionModuleSetup(startControl.mTargetMode);
            FunctionDataSetup functionDataSetup = new FunctionDataSetup(startControl.mTargetMode);
            FunctionUISetup functionUISetup = new FunctionUISetup(startControl.mTargetMode, startControl.needNotifyUI());
            Single map = Single.just(NullHolder.ofNullable(this)).observeOn(CameraSchedulers.sCameraSetupScheduler).map(functionCameraPrepare);
            Single observeOn = Single.create(this.mCamera2OpenOnSubscribe).subscribeOn(CameraSchedulers.sCameraSetupScheduler).observeOn(CameraSchedulers.sCameraSetupScheduler);
            Log.d(this.TAG, "setupCamera: CameraSetupDisposable: E");
            this.mCameraSetupDisposable = map.zipWith(observeOn, this.mCameraOpenResult).map(functionModuleSetup).map(functionDataSetup).observeOn(AndroidSchedulers.mainThread()).map(functionUISetup).subscribe(this.mCameraSetupConsumer);
            Log.d(this.TAG, "setupCamera: X");
        }
    }

    private boolean shouldReleaseLater() {
        return isCurrentModuleAlive() && this.mCurrentModule.shouldReleaseLater();
    }

    /* access modifiers changed from: private */
    /* access modifiers changed from: public */
    private void showCameraError(int i) {
        CameraStatUtils.trackCameraError("" + i);
        AftersalesManager.getInstance().count(System.currentTimeMillis(), 4, CameraSettings.getCameraId());
        Message obtain = Message.obtain();
        obtain.what = 10;
        obtain.arg1 = i;
        this.mHandler.sendMessage(obtain);
        if (DataRepository.dataItemFeature().zi()) {
            CompatibilityUtils.takebackMotor();
        }
    }

    private void showDebug() {
        if (Util.isShowDebugInfo()) {
            TextView textView = this.mDebugInfoView;
            if (textView != null) {
                textView.setVisibility(0);
            }
            this.mDebugThread = new LogThread();
            this.mDebugThread.start();
        }
        if (this.mDebugInfoView != null && Util.isShowPreviewDebugInfo()) {
            this.mDebugInfoView.setVisibility(0);
        }
    }

    private void showFirstUseHintIfNeeded() {
        if (!startFromSecureKeyguard()) {
            CameraRootView cameraRootView = this.mCameraRootView;
            if (cameraRootView != null) {
                cameraRootView.disableTouchEvent();
            }
            this.mHandler.postDelayed(new Runnable() {
                /* class com.android.camera.Camera.AnonymousClass5 */

                public /* synthetic */ void Dc() {
                    if (DataRepository.dataItemGlobal().getBoolean(DataItemGlobal.DATA_COMMON_LPL_SELECTOR_USE_HINT_SHOWN, true)) {
                        Camera.this.showLplSelectDialog();
                    }
                }

                public void run() {
                    if (!Camera.this.isActivityPaused()) {
                        Camera.this.getScreenHint().showFirstUseHint(new b(this));
                        CameraRootView cameraRootView = Camera.this.mCameraRootView;
                        if (cameraRootView != null) {
                            cameraRootView.enableTouchEvent();
                        }
                    }
                }
            }, 1000);
        }
    }

    private void showFirstUsePermissionActivity() {
        if (DataRepository.dataItemGlobal().getBoolean(CameraSettings.KEY_CAMERA_FIRST_USE_PERMISSION_SHOWN, true)) {
            boolean z = Build.IS_INTERNATIONAL_BUILD;
            String str = Util.sRegion;
            if (z && "KR".equals(str)) {
                Intent intent = new Intent("miui.intent.action.APP_PERMISSION_USE");
                ArrayList<String> arrayList = new ArrayList<>(6);
                arrayList.add(getResources().getString(R.string.permission_contacts));
                arrayList.add(getResources().getString(R.string.permission_location));
                arrayList.add(getResources().getString(R.string.permission_camera));
                arrayList.add(getResources().getString(R.string.permission_phone_state));
                arrayList.add(getResources().getString(R.string.permission_storage));
                arrayList.add(getResources().getString(R.string.permission_microphone));
                intent.putStringArrayListExtra("extra_main_permission_groups", arrayList);
                intent.putExtra("extra_pkgname", "com.android.camera");
                try {
                    startActivityForResult(intent, 1);
                } catch (Exception e2) {
                    String str2 = this.TAG;
                    Log.i(str2, "KR Exception:" + e2);
                }
            }
        }
    }

    private void showHibernationFragment() {
        HibernationFragment hibernationFragment = new HibernationFragment();
        hibernationFragment.setStyle(2, R.style.DialogFragmentFullScreen);
        getSupportFragmentManager().beginTransaction().add(hibernationFragment, HibernationFragment.TAG).commitAllowingStateLoss();
    }

    private void switchEdgeFingerMode(boolean z) {
        if (c.Jn()) {
            CameraSettings.setEdgeMode(this, z);
        }
    }

    private void triggerWatchDog(boolean z) {
        String str = this.TAG;
        Log.d(str, "triggerWatchDog: " + z);
        if (c.uh && DataRepository.dataItemFeature().oj()) {
            if (z) {
                this.mWatchDog = new WatchDogThread();
                this.mWatchDog.start();
                return;
            }
            Thread thread = this.mWatchDog;
            if (thread != null) {
                thread.interrupt();
                this.mWatchDog = null;
            }
        }
    }

    private void unbindServices() {
        if (this.mIsGalleryServiceBound) {
            unbindService(this.mServiceConnection);
            this.mIsGalleryServiceBound = false;
        }
    }

    private void unregisterSDReceiver() {
        try {
            unregisterReceiver(this.mSDReceiver);
        } catch (Exception e2) {
            Log.e(this.TAG, e2.getMessage());
        }
    }

    public /* synthetic */ void X() {
        Fragment findFragmentByTag;
        if (!isFinishing() && (findFragmentByTag = getSupportFragmentManager().findFragmentByTag(LensDirtyDetectDialogFragment.TAG)) != null && (findFragmentByTag instanceof DialogFragment)) {
            ((DialogFragment) findFragmentByTag).dismissAllowingStateLoss();
        }
    }

    public /* synthetic */ NullHolder a(NullHolder nullHolder) throws Exception {
        if (!nullHolder.isPresent()) {
            return nullHolder;
        }
        BaseModule baseModule = (BaseModule) nullHolder.get();
        if (!baseModule.isDeparted()) {
            return nullHolder;
        }
        Log.d(this.TAG, "cameraSingle: EXCEPTION_CAMERA_OPEN_CANCEL");
        return NullHolder.ofNullable(baseModule, 225);
    }

    public /* synthetic */ NullHolder a(NullHolder nullHolder, Camera2Result camera2Result) throws Exception {
        int result = camera2Result.getResult();
        String str = this.TAG;
        Log.d(str, "mCameraOpenResult apply: result = " + result);
        if (result != 2 && result == 3) {
            if (nullHolder.isPresent()) {
                ((BaseModule) nullHolder.get()).setDeparted();
            }
            showCameraError(camera2Result.getCameraError());
        }
        return nullHolder;
    }

    public /* synthetic */ void a(DataItemGlobal dataItemGlobal) {
        if (!CameraSettings.isShowFirstUseHint() && dataItemGlobal.getBoolean(DataItemGlobal.DATA_COMMON_LPL_SELECTOR_USE_HINT_SHOWN, true)) {
            showLplSelectDialog();
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:6:0x0046, code lost:
        r0 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:7:0x0047, code lost:
        com.android.camera.log.Log.e(r8.TAG, r0.getMessage(), r0);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:8:?, code lost:
        return;
     */
    /* JADX WARNING: Removed duplicated region for block: B:6:0x0046 A[ExcHandler: ClassNotFoundException | IllegalAccessException | NoSuchMethodException | InvocationTargetException (r0v2 'e' java.lang.ReflectiveOperationException A[CUSTOM_DECLARE]), Splitter:B:4:0x0027] */
    public void boostCameraByThreshold() {
        long uh = DataRepository.dataItemFeature().uh();
        if (uh > 0) {
            String str = this.TAG;
            Log.d(str, "boostCameraByThreshold " + uh);
            try {
                Class.forName("miui.process.ProcessManager").getDeclaredMethod("boostCameraByThreshold", Long.TYPE).invoke(null, Long.valueOf(uh));
            } catch (ClassNotFoundException | IllegalAccessException | NoSuchMethodException | InvocationTargetException e2) {
            }
        }
    }

    public void changeRequestOrientation() {
        if (c.Rm()) {
            if (CameraSettings.isFrontCamera()) {
                setRequestedOrientation(7);
            } else {
                setRequestedOrientation(1);
            }
        }
    }

    @Override // androidx.core.app.ComponentActivity
    public boolean dispatchKeyEvent(KeyEvent keyEvent) {
        ProximitySensorLock proximitySensorLock = this.mProximitySensorLock;
        if (proximitySensorLock != null && proximitySensorLock.intercept(keyEvent)) {
            return true;
        }
        if (this.mMoreChangeCtrl == null) {
            this.mMoreChangeCtrl = (ModeProtocol.ModeChangeController) ModeCoordinatorImpl.getInstance().getAttachProtocol(179);
        }
        ModeProtocol.ModeChangeController modeChangeController = this.mMoreChangeCtrl;
        if (modeChangeController == null || !modeChangeController.modeChanging()) {
            return super.dispatchKeyEvent(keyEvent);
        }
        Log.d(this.TAG, "Key event intercept caz mode change.");
        return true;
    }

    public boolean dispatchTouchEvent(MotionEvent motionEvent) {
        if (this.mActivityPaused) {
            return true;
        }
        ProximitySensorLock proximitySensorLock = this.mProximitySensorLock;
        if (proximitySensorLock != null && proximitySensorLock.active()) {
            return true;
        }
        Module module = this.mCurrentModule;
        return (module == null || module.isIgnoreTouchEvent()) ? super.dispatchTouchEvent(motionEvent) : interceptTouchEvent(motionEvent) || super.dispatchTouchEvent(motionEvent) || V6GestureRecognizer.getInstance(this).onTouchEvent(motionEvent);
    }

    public int getCapturePosture() {
        return this.mSensorStateManager.getCapturePosture();
    }

    public int getCurrentBrightness() {
        return this.mCameraBrightness.getCurrentBrightness();
    }

    @Override // com.android.camera.AppController
    public int getCurrentModuleIndex() {
        if (isCurrentModuleAlive()) {
            return this.mCurrentModule.getModuleIndex();
        }
        return 160;
    }

    @Override // com.android.camera.AppController
    public ImageSaver getImageSaver() {
        return this.mImageSaver;
    }

    public ImplFactory getImplFactory() {
        return this.mImplFactory;
    }

    public SensorStateManager getSensorStateManager() {
        return this.mSensorStateManager;
    }

    public /* synthetic */ void h(int i) {
        if (!this.mIsLunchFromAutoTest) {
            if (i == 1) {
                if (!isActivityPaused()) {
                    this.mHandler.sendEmptyMessage(3);
                } else {
                    return;
                }
            }
            ModeProtocol.ConfigChanges configChanges = (ModeProtocol.ConfigChanges) ModeCoordinatorImpl.getInstance().getAttachProtocol(164);
            if (configChanges == null) {
                Log.w(this.TAG, "onThermalNotification config is null");
                return;
            }
            try {
                configChanges.onThermalNotification(i);
            } catch (Exception e2) {
                Log.w(this.TAG, "onThermalNotification error", e2);
            }
        }
    }

    public boolean handleScreenSlideKeyEvent(int i, KeyEvent keyEvent) {
        Log.d(this.TAG, "handleScreenSlideKeyEvent " + i);
        if (DataRepository.dataItemFeature().pj()) {
            return true;
        }
        if (i == 701 && getCameraIntentManager().isFromScreenSlide().booleanValue() && !isModeSwitched()) {
            finish();
            overridePendingTransition(R.anim.anim_screen_slide_fade_in, R.anim.anim_screen_slide_fade_out);
            return true;
        } else if (isPostProcessing()) {
            return true;
        } else {
            DataItemGlobal dataItemGlobal = DataRepository.dataItemGlobal();
            int currentCameraId = dataItemGlobal.getCurrentCameraId();
            boolean z = false;
            int i2 = i == 700 ? 1 : 0;
            int currentMode = dataItemGlobal.getCurrentMode();
            if ((currentMode == 171 && !DataRepository.dataItemFeature().Bk()) || currentMode == 166 || currentMode == 167 || currentMode == 173 || currentMode == 175) {
                currentMode = 163;
            }
            if (currentMode == 163 || currentMode == 165) {
                currentMode = ((DataItemConfig) DataRepository.provider().dataConfig(i2)).getComponentConfigRatio().getMappingModeByRatio(163);
            }
            if (currentCameraId != i2) {
                ModeProtocol.TopAlert topAlert = (ModeProtocol.TopAlert) ModeCoordinatorImpl.getInstance().getAttachProtocol(172);
                ModeProtocol.ActionProcessing actionProcessing = (ModeProtocol.ActionProcessing) ModeCoordinatorImpl.getInstance().getAttachProtocol(162);
                if (topAlert != null) {
                    topAlert.removeExtraMenu(4);
                }
                if (actionProcessing != null) {
                    actionProcessing.hideExtra();
                }
                dataItemGlobal.setCurrentMode(currentMode);
                dataItemGlobal.setCameraId(i2);
                boolean z2 = currentCameraId == 1;
                if (i2 == 1) {
                    z = true;
                }
                ScenarioTrackUtil.trackSwitchCameraStart(z2, z, dataItemGlobal.getCurrentMode());
                onModeSelected(StartControl.create(currentMode).setFromScreenSlide(true).setNeedBlurAnimation(true).setViewConfigType(2));
            } else if (i == 700 && isCurrentModuleAlive()) {
                ((BaseModule) this.mCurrentModule).updateScreenSlide(true);
                ModeProtocol.MainContentProtocol mainContentProtocol = (ModeProtocol.MainContentProtocol) ModeCoordinatorImpl.getInstance().getAttachProtocol(166);
                if (Util.isAccessible() && mainContentProtocol != null) {
                    mainContentProtocol.updateContentDescription();
                }
            }
            return true;
        }
    }

    public void hideLensDirtyDetectedHint() {
        if (!DataRepository.dataItemFeature().Jj()) {
            this.mHandler.post(new d(this));
        }
    }

    public /* synthetic */ void i(int i) {
        String str = this.TAG;
        Log.d(str, "checkLensAvailability callback: status = " + i);
        CameraSettings.setGoogleLensAvailability(i == 0);
    }

    public boolean isCurrentModuleAlive() {
        Module module = this.mCurrentModule;
        return module != null && module.isCreated();
    }

    public boolean isIntentPhotoDone() {
        return this.mIntentPhotoDone;
    }

    public boolean isModeSwitched() {
        return this.mIsModeSwitched;
    }

    public boolean isNewBieAlive(int i) {
        String str;
        switch (i) {
            case 1:
                str = PortraitNewbieDialogFragment.TAG;
                break;
            case 2:
                str = FrontRotateNewbieDialogFragment.TAG;
                break;
            case 3:
                str = AiSceneNewbieDialogFragment.TAG;
                break;
            case 4:
                str = UltraWideNewbieDialogFragment.TAG;
                break;
            case 5:
                str = MacroModeNewbieDialogFragment.TAG;
                break;
            case 6:
                str = VVNewbieDialogFragment.TAG;
                break;
            case 7:
                str = UltraTeleNewbieDialogFragment.TAG;
                break;
            default:
                str = null;
                break;
        }
        return (str == null || getSupportFragmentManager().findFragmentByTag(str) == null) ? false : true;
    }

    public boolean isRecording() {
        return isCurrentModuleAlive() && this.mCurrentModule.isRecording();
    }

    public boolean isScreenSlideOff() {
        return this.mIsScreenSlideOff;
    }

    public boolean isSelectingCapturedResult() {
        return isCurrentModuleAlive() && this.mCurrentModule.isSelectingCapturedResult();
    }

    public boolean isStable() {
        return this.mSensorStateManager.isStable();
    }

    @Override // com.android.camera.ActivityBase
    public void notifyOnFirstFrameArrived(int i) {
        Module module = this.mCurrentModule;
        if (module != null && !module.isDeparted() && !isSwitchingModule()) {
            if (this.mCurrentModule.getModuleIndex() != 254) {
                this.mHandler.sendEmptyMessageDelayed(2, 2000);
            }
            getCameraScreenNail().clearAnimation();
            this.mBaseFragmentDelegate.getAnimationComposite().notifyAfterFirstFrameArrived(i);
            this.mCurrentModule.enableCameraControls(true);
            this.mCurrentModule.setFrameAvailable(true);
            if (this.mModeSelectGaussianTime > 0 && SystemClock.uptimeMillis() - this.mModeSelectGaussianTime > 3000) {
                AftersalesManager.getInstance().count(SystemClock.uptimeMillis(), 3);
                this.mModeSelectGaussianTime = -1;
            }
            if (getCurrentModuleIndex() == 254) {
                getCameraScreenNail().startRealtimeBlur();
            } else if ((getCurrentModuleIndex() == 165 || getCurrentModuleIndex() == 163) && c.Rm() && CameraSettings.isFrontCamera() && this.mDisplayRotation == 0 && DataRepository.dataItemGlobal().getBoolean("pref_front_camera_first_use_hint_shown_key", true)) {
                DataRepository.dataItemGlobal().editor().putBoolean("pref_front_camera_first_use_hint_shown_key", false).apply();
                showNewBie(2);
            }
        }
    }

    /* access modifiers changed from: protected */
    @Override // androidx.fragment.app.FragmentActivity, com.android.camera.ActivityBase
    public void onActivityResult(int i, int i2, Intent intent) {
        super.onActivityResult(i, i2, intent);
        if (i != 1) {
            if (i == 161) {
                if (FileCompat.handleActivityResult(this, i, i2, intent)) {
                    DocumentFile fromTreeUri = DocumentFile.fromTreeUri(this, intent.getData());
                    if (fromTreeUri.findFile("Camera") == null) {
                        fromTreeUri.createDirectory("Camera");
                        return;
                    }
                    return;
                }
                Log.w(this.TAG, "onActivityResult documents permission not granted");
                PriorityStorageBroadcastReceiver.setPriorityStorage(false);
            }
        } else if (i2 == PERMISSION_RESULT_CODE) {
            DataRepository.dataItemGlobal().putBoolean(CameraSettings.KEY_CAMERA_FIRST_USE_PERMISSION_SHOWN, false);
        }
    }

    public void onAwaken() {
        Log.d(this.TAG, "onAwaken");
        getCameraScreenNail().requestAwaken();
        onModeSelected(this.mStartControl);
    }

    @Override // androidx.activity.ComponentActivity
    public void onBackPressed() {
        Log.d(this.TAG, "onBackPressed");
        Module module = this.mCurrentModule;
        if (module == null || !module.onBackPressed()) {
            super.onBackPressed();
        }
    }

    @Override // androidx.activity.ComponentActivity, androidx.fragment.app.FragmentActivity, com.android.camera.ActivityBase
    public void onCreate(Bundle bundle) {
        Log.d(this.TAG, "onCreate start");
        this.mApplication = (CameraAppImpl) getApplication();
        this.mIsLunchFromAutoTest = SystemProperties.getBoolean("camera.test.auto", false);
        MistatsWrapper.initialize(CameraApplicationDelegate.getAndroidContext());
        trackAppLunchTimeStart(this.mApplication.isApplicationFirstLaunched());
        this.mAppStartTime = System.currentTimeMillis();
        this.mIntentPhotoDone = false;
        this.mCameraIntentManager = CameraIntentManager.getInstance(getIntent());
        this.mCameraIntentManager.setReferer(this);
        if (CompatibilityUtils.isInMultiWindowMode(this)) {
            super.onCreate(null);
            if (DataRepository.dataItemFeature().Xj()) {
                ToastUtils.showToast(this, (int) R.string.only_mimoji_multi_window_mode_not_supported);
            } else {
                ToastUtils.showToast(this, (int) R.string.multi_window_mode_not_supported);
            }
            Log.d(this.TAG, "isInMultiWindowMode call finish");
            finish();
        } else if (!CameraIntentManager.ACTION_VOICE_CONTROL.equals(getIntent().getAction()) || this.mCameraIntentManager.checkCallerLegality()) {
            String caller = this.mCameraIntentManager.getCaller();
            if (this.mCameraIntentManager.checkCallerLegality()) {
                CameraStatUtils.trackCallerControl(getIntent(), caller);
            }
            if (DataRepository.dataItemFeature().pj()) {
                Util.initialize(getApplicationContext());
            }
            Util.initStatusBarHeight(getApplicationContext());
            super.onCreate(bundle);
            Util.updateDeviceConfig(this);
            showFirstUsePermissionActivity();
            if (getKeyguardFlag()) {
                HybridZoomingSystem.clearZoomRatioHistory();
            } else if (CameraSettings.isShowFirstUseHint()) {
                showFirstUseHintIfNeeded();
            } else if (PermissionManager.checkCameraLaunchPermissions()) {
                Log.d(this.TAG, "onCreate(): prefixCamera2Setup");
                prefixCamera2Setup();
            } else {
                this.mIsInRequestRuntimePermission = !PermissionManager.requestCameraRuntimePermissions(this);
            }
            if (ProximitySensorLock.enabled() && isFromKeyguard()) {
                if (!Util.isNonUIEnabled() || !this.mCameraIntentManager.isFromVolumeKey().booleanValue()) {
                    if (ProximitySensorLock.supported()) {
                        this.mProximitySensorLock = new ProximitySensorLock(this);
                        this.mProximitySensorLock.startWatching();
                    }
                } else if (Util.isNonUI()) {
                    CameraStatUtils.trackPocketModeEnter(MistatsConstants.NonUI.POCKET_MODE_NONUI_ENTER_VOLUME);
                    Log.d(this.TAG, "Finish from NonUI mode.");
                    finish();
                    return;
                }
            }
            EffectController.releaseInstance();
            setContentView(R.layout.v9_main);
            getWindow().setBackgroundDrawable(null);
            this.mGLView = (V6CameraGLSurfaceView) findViewById(R.id.v6_gl_surface_view);
            this.mGLCoverView = (ImageView) findViewById(R.id.gl_root_cover);
            this.mDebugInfoView = (TextView) findViewById(R.id.camera_debug_content);
            FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) this.mDebugInfoView.getLayoutParams();
            layoutParams.topMargin = Util.getDisplayRect().top;
            this.mDebugInfoView.setLayoutParams(layoutParams);
            this.mEdgeShutterView = (V9EdgeShutterView) findViewById(R.id.v9_edge_shutter_view);
            this.mCameraRootView = (CameraRootView) findViewById(R.id.camera_app_root);
            this.mSensorStateManager = new SensorStateManager(this, getMainLooper());
            this.mOrientationListener = new MyOrientationEventListener(this);
            createCameraScreenNail(false, false);
            this.mCamera2OpenOnSubscribe = new Camera2OpenOnSubscribe(this);
            registerProtocol();
            if (c.En()) {
                try {
                    this.mDisplayFeatureManager = DisplayFeatureManager.getInstance();
                } catch (Exception e2) {
                    Log.w(this.TAG, "DisplayFeatureManager init failed", e2);
                }
            }
            setTranslucentNavigation(true);
            EffectChangedListenerController.setHoldKey(hashCode());
            if (c.Pl()) {
                FrameLayout frameLayout = (FrameLayout) getLayoutInflater().inflate(R.layout.layout_google_lens, (ViewGroup) null);
                this.mCameraRootView.addView(frameLayout);
                LensAgent.getInstance().init(this, this.mGLView, frameLayout);
            } else if (CameraSettings.supportGoogleLens()) {
                this.mLensApi = new LensApi(this);
                this.mLensApi.checkLensAvailability(new e(this));
                Log.d(this.TAG, "Bind Lens service: E");
                this.mLensApi.onResume();
                Log.d(this.TAG, "Bind Lens service: X");
            }
            showDebug();
            this.mCurrentDisplayMode = DataRepository.dataItemGlobal().getDisplayMode();
            ThermalDetector.getInstance().onCreate(CameraAppImpl.getAndroidContext());
            ViberatorContext.getInstance(getBaseContext()).setSnapClickVibratorEnable(DataRepository.dataItemFeature().dm());
            Log.d(this.TAG, "onCreate end");
        } else {
            String str = this.TAG;
            Log.e(str, "An illegal caller:" + this.mCameraIntentManager.getCaller() + " use VOICE_CONTROL_INTENT!");
            super.onCreate(null);
            finish();
        }
    }

    @Override // androidx.fragment.app.FragmentActivity, com.android.camera.ActivityBase
    public void onDestroy() {
        Log.d(this.TAG, "onDestroy start");
        super.onDestroy();
        int currentMode = DataRepository.dataItemGlobal().getCurrentMode();
        int i = 163;
        if (currentMode == 185) {
            DataItemGlobal dataItemGlobal = DataRepository.dataItemGlobal();
            if (DataRepository.dataItemFeature().hj()) {
                i = 210;
            }
            dataItemGlobal.setCurrentMode(i);
        } else if (currentMode == 179) {
            DataItemGlobal dataItemGlobal2 = DataRepository.dataItemGlobal();
            if (DataRepository.dataItemFeature().H()) {
                i = 209;
            }
            dataItemGlobal2.setCurrentMode(i);
        }
        AutoLockManager.removeInstance(this);
        unRegisterProtocol();
        ThermalDetector.getInstance().onDestroy();
        ImageSaver imageSaver = this.mImageSaver;
        if (imageSaver != null) {
            imageSaver.onHostDestroy();
        }
        SensorStateManager sensorStateManager = this.mSensorStateManager;
        if (sensorStateManager != null) {
            sensorStateManager.onDestroy();
        }
        this.mDisplayFeatureManager = null;
        V6GestureRecognizer.onDestroy(this);
        EffectChangedListenerController.removeEffectChangedListenerMap(hashCode());
        LogThread logThread = this.mDebugThread;
        if (logThread != null) {
            logThread.setRunFlag(false);
        }
        ProximitySensorLock proximitySensorLock = this.mProximitySensorLock;
        if (proximitySensorLock != null) {
            proximitySensorLock.destroy();
        }
        CameraIntentManager cameraIntentManager = this.mCameraIntentManager;
        if (cameraIntentManager != null) {
            cameraIntentManager.destroy();
            this.mCameraIntentManager = null;
        }
        if (getScreenHint() != null) {
            getScreenHint().dismissSystemChoiceDialog();
        }
        CameraIntentManager.removeAllInstance();
        if (c.Pl()) {
            LensAgent.getInstance().release();
        } else if (CameraSettings.supportGoogleLens()) {
            if (this.mLensApi == null) {
                Log.w(this.TAG, "onDestroy: mLensApi is null!");
            } else {
                Log.d(this.TAG, "Unbind Lens service: E");
                this.mLensApi.onPause();
                Log.d(this.TAG, "Unbind Lens service: X");
            }
        }
        Log.d(this.TAG, "onDestroy end");
    }

    public void onHibernate() {
        Log.d(this.TAG, "onHibernate");
        if (isDestroyed()) {
            AutoLockManager.getInstance(this).removeMessage();
            return;
        }
        showHibernationFragment();
        getCameraScreenNail().requestHibernate();
        CameraSchedulers.sCameraSetupScheduler.scheduleDirect(new HibernateRunnable(this.mCurrentModule));
    }

    @Override // com.android.camera.ActivityBase
    public boolean onKeyDown(int i, KeyEvent keyEvent) {
        if (this.mActivityPaused) {
            return super.onKeyDown(i, keyEvent);
        }
        String str = this.TAG;
        Log.d(str, "onKeyDown: keycode " + i);
        if (keyEvent.getRepeatCount() == 0 && (i == 66 || i == 27 || i == 88 || i == 87 || i == 24 || i == 25)) {
            if (this.mLastKeyDownEventTime == 0 || keyEvent.getEventTime() >= this.mLastKeyDownEventTime) {
                String volumeCameraFunction = CameraSettings.getVolumeCameraFunction("");
                if (this.mLastKeyDownEventTime == 0 || !isFromOneShotKeyPressed(keyEvent) || volumeCameraFunction.equals(getString(R.string.pref_camera_volumekey_function_entryvalue_volume))) {
                    this.mLastIgnoreKey = -1;
                    this.mLastKeyDownEventTime = keyEvent.getEventTime();
                } else {
                    String str2 = this.TAG;
                    Log.d(str2, "onKeyDown: isFromOneShotKeyPressed and return! keyCode is " + i);
                    this.mLastIgnoreKey = i;
                    this.mLastKeyDownEventTime = 0;
                    return true;
                }
            } else {
                this.mLastIgnoreKey = i;
                this.mLastKeyDownEventTime = 0;
                return true;
            }
        } else if (keyEvent.getRepeatCount() > 0 && i == this.mLastIgnoreKey) {
            this.mLastIgnoreKey = -1;
        }
        if (i == 700) {
            this.mIsScreenSlideOff = false;
        } else if (i == 701) {
            this.mIsScreenSlideOff = true;
        }
        if (isCurrentModuleAlive() && this.mCurrentModule.isFrameAvailable()) {
            return (!c.jh || !(i == 24 || i == 25 || i == 87 || i == 88)) ? this.mCurrentModule.onKeyDown(i, keyEvent) || super.onKeyDown(i, keyEvent) : super.onKeyDown(i, keyEvent);
        }
        if (i == 24 || i == 25 || i == 27 || i == 66 || i == 80 || i == 87 || i == 88) {
            return true;
        }
        return (i == 700 || i == 701) ? handleScreenSlideKeyEvent(i, keyEvent) : super.onKeyDown(i, keyEvent);
    }

    public boolean onKeyUp(int i, KeyEvent keyEvent) {
        if (this.mActivityPaused) {
            return super.onKeyUp(i, keyEvent);
        }
        if (i == 4 && (!keyEvent.isTracking() || keyEvent.isCanceled())) {
            Log.d(this.TAG, "onKeyUp: keyCode KeyEvent.KEYCODE_BACK is not isTracking or isCanceled");
            return false;
        } else if (i == this.mLastIgnoreKey) {
            this.mLastKeyUpEventTime = 0;
            this.mLastIgnoreKey = -1;
            String str = this.TAG;
            Log.d(str, "onKeyUp: key is lastIgnore key   keyCode : " + i);
            return true;
        } else {
            this.mLastKeyUpEventTime = keyEvent.getEventTime();
            String str2 = this.TAG;
            Log.d(str2, "onKeyUp: mLastKeyUpEventTime " + this.mLastKeyUpEventTime + " keyCode : " + i);
            return !isCurrentModuleAlive() ? super.onKeyUp(i, keyEvent) : (!c.jh || !(i == 24 || i == 25 || i == 87 || i == 88)) ? this.mCurrentModule.onKeyUp(i, keyEvent) || super.onKeyUp(i, keyEvent) : super.onKeyUp(i, keyEvent);
        }
    }

    @Override // com.android.camera.fragment.lifeCircle.BaseLifecycleListener
    public void onLifeAlive() {
        ThreadUtils.assertMainThread();
        Log.d(this.TAG, String.format(Locale.ENGLISH, "onLifeAlive module 0x%x, need anim %d, need blur %b need reconfig %b reset type %d", Integer.valueOf(this.mStartControl.mTargetMode), Integer.valueOf(this.mStartControl.mViewConfigType), Boolean.valueOf(this.mStartControl.mNeedBlurAnimation), Boolean.valueOf(this.mStartControl.mNeedReConfigureCamera), Integer.valueOf(this.mStartControl.mResetType)));
        String str = this.TAG;
        Log.d(str, "onLifeAlive: isFromKeyguard: " + isFromKeyguard() + ", mHasBeenSetupOnFocusChanged: " + this.mHasBeenSetupOnFocusChanged + ", mHasFocus: " + this.mHasFocus);
        if (!isFromKeyguard()) {
            Log.d(this.TAG, "onLifeAlive: setupCamera: !keyguard");
            setupCamera(this.mStartControl);
        } else if ((this.mHasBeenSetupOnFocusChanged || this.mHasFocus) && this.mCameraSetupDisposable == null) {
            Log.d(this.TAG, "onLifeAlive: setupCamera: focused");
            setupCamera(this.mStartControl);
        }
    }

    @Override // com.android.camera.fragment.lifeCircle.BaseLifecycleListener
    public void onLifeDestroy(String str) {
        String str2 = this.TAG;
        Log.d(str2, "onLifeDestroy " + str);
    }

    @Override // com.android.camera.fragment.lifeCircle.BaseLifecycleListener
    public void onLifeStart(String str) {
        String str2 = this.TAG;
        Log.d(str2, "onLifeStart " + str);
    }

    @Override // com.android.camera.fragment.lifeCircle.BaseLifecycleListener
    public void onLifeStop(String str) {
        String str2 = this.TAG;
        Log.d(str2, "onLifeStop " + str);
    }

    @Override // com.android.camera.AppController
    @MainThread
    public void onModeSelected(StartControl startControl) {
        ThreadUtils.assertMainThread();
        int currentModuleIndex = getCurrentModuleIndex();
        this.mIsModeSwitched = this.mStartControl != null;
        this.mIsScreenSlideOff = Util.isScreenSlideOff(this);
        Log.d(this.TAG, String.format(Locale.ENGLISH, "onModeSelected from 0x%x to 0x%x, ScreenSlideOff = %b", Integer.valueOf(currentModuleIndex), Integer.valueOf(startControl.mTargetMode), Boolean.valueOf(this.mIsScreenSlideOff)));
        if (currentModuleIndex != 160 && !CameraStatUtils.modeIdToName(currentModuleIndex).equals(CameraStatUtils.modeIdToName(startControl.mTargetMode))) {
            this.mHandler.removeMessages(2);
            ScenarioTrackUtil.trackSwitchModeStart(currentModuleIndex, startControl.mTargetMode, CameraSettings.isFrontCamera());
        }
        closeCameraSetup();
        this.mStartControl = startControl;
        ModuleManager.setActiveModuleIndex(startControl.mTargetMode);
        Completable completable = null;
        if (!startControl.mNeedReConfigureCamera) {
            this.mBaseFragmentDelegate.delegateMode(null, startControl, null);
            return;
        }
        BaseFragmentDelegate baseFragmentDelegate = this.mBaseFragmentDelegate;
        if (baseFragmentDelegate != null) {
            baseFragmentDelegate.getAnimationComposite().setClickEnable(false);
        }
        this.mModeSelectGaussianTime = -1;
        if (startControl.mNeedBlurAnimation) {
            this.mModeSelectGaussianTime = SystemClock.uptimeMillis();
        }
        setSwitchingModule(true);
        if (!isCurrentModuleAlive()) {
            startControl.mNeedBlurAnimation = false;
            getWindow().clearFlags(128);
        }
        V6GestureRecognizer.getInstance(this).setCurrentModule(null);
        BaseModule baseModule = (BaseModule) this.mCurrentModule;
        if (baseModule != null) {
            baseModule.setDeparted();
            startControl.setLastMode(baseModule.getModuleIndex());
        }
        this.mCurrentModule = createNewModule(startControl);
        String str = this.TAG;
        Log.d(str, "onModeSelected: module instantiated: " + this.mCurrentModule);
        if (this.mBaseFragmentDelegate == null) {
            Log.d(this.TAG, "onModeSelected: without prefix");
            this.mBaseFragmentDelegate = new BaseFragmentDelegate(this);
            this.mBaseFragmentDelegate.init(getSupportFragmentManager(), startControl.mTargetMode, this);
        } else {
            Log.d(this.TAG, "onModeSelected: with prefix");
            boolean checkCallerLegality = this.mCameraIntentManager.checkCallerLegality();
            if (PermissionManager.checkCameraLaunchPermissions()) {
                completable = Completable.create(new CompletablePreFixCamera2Setup(baseModule, startControl, getCameraScreenNail(), null, hashCode(), startFromSecureKeyguard(), checkCallerLegality)).subscribeOn(CameraSchedulers.sCameraSetupScheduler).observeOn(AndroidSchedulers.mainThread());
            }
            Log.d(this.TAG, "CameraPendingSetupDisposable: E");
            this.mCameraPendingSetupDisposable = this.mBaseFragmentDelegate.delegateMode(completable, startControl, this);
        }
        this.mBaseFragmentDelegate.lazyLoadFragment(R.id.full_screen, 4086);
        this.mBaseFragmentDelegate.lazyLoadFragment(R.id.mimoji_full_screen, 65523);
        this.mBaseFragmentDelegate.batchFragmentRequest(startControl.getFeatureFragmentAlias());
    }

    /* access modifiers changed from: protected */
    @Override // androidx.fragment.app.FragmentActivity, com.android.camera.ActivityBase
    public void onNewIntent(Intent intent) {
        Log.d(this.TAG, "onNewIntent start");
        setIntent(intent);
        super.onNewIntent(intent);
        this.mCameraIntentManager.destroy();
        this.mIntentPhotoDone = false;
        this.mCameraIntentManager = CameraIntentManager.getInstance(intent);
        this.mCameraIntentManager.setReferer(this);
        this.mJumpedToGallery = false;
        Log.d(this.TAG, "onNewIntent end");
    }

    @Override // androidx.fragment.app.FragmentActivity, com.android.camera.ActivityBase
    public void onPause() {
        Log.d(this.TAG, "onPause start");
        CameraSettings.setLensIndex(0);
        CameraSettings.setMacro2Sat(false);
        this.mAppStartTime = 0;
        this.mActivityPaused = true;
        this.mActivityStarted = false;
        this.mIsInRequestRuntimePermission = false;
        getContentResolver().unregisterContentObserver(this.mScreenSlideStatusObserver);
        switchEdgeFingerMode(false);
        this.mOrientationListener.disable();
        AutoLockManager.getInstance(this).onPause();
        hideHibernationFragment();
        setBrightnessRampRate(-1);
        setScreenEffect(false);
        getWindow().clearFlags(128);
        if (this.mDidRegister) {
            unregisterReceiver(this.mReceiver);
            this.mDidRegister = false;
        }
        unregisterSDReceiver();
        super.onPause();
        if (c.Pl()) {
            LensAgent.getInstance().onPause();
        }
        if (getScreenHint() != null) {
            getScreenHint().cancelHint();
        }
        CameraRootView cameraRootView = this.mCameraRootView;
        if (cameraRootView != null) {
            cameraRootView.enableTouchEvent();
        }
        ImageSaver imageSaver = this.mImageSaver;
        if (imageSaver != null) {
            imageSaver.onHostPause();
        }
        MistatsWrapper.recordPageEnd("CameraActivity");
        this.mReleaseByModule = false;
        if (shouldReleaseLater()) {
            Log.d(this.TAG, "release by module");
            this.mReleaseByModule = true;
            DataRepository.dataItemGlobal().resetTimeOut();
            this.mCurrentModule.onHostStopAndNotifyActionStop();
        }
        if (ThermalDetector.getInstance().thermalConstrained()) {
            Log.w(this.TAG, "onThermalNotification finish activity now");
            finish();
        }
        ThermalDetector.getInstance().unregisterReceiver();
        triggerWatchDog(false);
        boostParallelServiceAdj();
        Log.d(this.TAG, "onPause end");
    }

    @Override // androidx.core.app.ActivityCompat.OnRequestPermissionsResultCallback, androidx.fragment.app.FragmentActivity
    public void onRequestPermissionsResult(int i, String[] strArr, int[] iArr) {
        if (i == PermissionManager.getCameraRuntimePermissionRequestCode()) {
            int i2 = 0;
            this.mIsInRequestRuntimePermission = false;
            if (!PermissionManager.isCameraLaunchPermissionsResultReady(strArr, iArr)) {
                String str = this.TAG;
                Log.w(str, "onRequestPermissionsResult: no permission finish, " + Arrays.toString(strArr) + Arrays.toString(iArr));
                finish();
            } else if (strArr.length == 0 && iArr.length == 0) {
                Log.w(this.TAG, "ignore this onRequestPermissionsResult callback");
            } else {
                while (true) {
                    if (i2 >= strArr.length) {
                        break;
                    } else if ("android.permission.CAMERA".equals(strArr[i2])) {
                        Camera2DataContainer.getInstance().init((CameraManager) CameraAppImpl.getAndroidContext().getSystemService("camera"));
                        LocalParallelService.LocalBinder localBinder = AlgoConnector.getInstance().getLocalBinder();
                        if (localBinder != null) {
                            localBinder.updateVirtualCameraIds();
                        }
                    } else {
                        i2++;
                    }
                }
                Log.d(this.TAG, "onRequestPermissionsResult: prefixCamera2Setup");
                prefixCamera2Setup();
                setupCamera(this.mStartControl);
                parseLocationPermission(strArr, iArr);
            }
        } else if (i != PermissionManager.getCameraLocationPermissionRequestCode()) {
        } else {
            if (strArr.length == 0 && iArr.length == 0) {
                Log.w(this.TAG, "ignore this onRequestPermissionsResult callback");
            } else {
                parseLocationPermission(strArr, iArr);
            }
        }
    }

    /* access modifiers changed from: protected */
    public void onRestart() {
        super.onRestart();
        Log.d(this.TAG, "onRestart start");
        trackAppLunchTimeStart(false);
        this.mAppStartTime = System.currentTimeMillis();
        if (!getKeyguardFlag()) {
            if (CameraSettings.isShowFirstUseHint()) {
                showFirstUseHintIfNeeded();
            } else if (DataRepository.dataItemGlobal().getBoolean(DataItemGlobal.DATA_COMMON_LPL_SELECTOR_USE_HINT_SHOWN, true)) {
                showLplSelectDialog();
            }
        }
        Log.d(this.TAG, "onRestart end");
    }

    @Override // androidx.fragment.app.FragmentActivity, com.android.camera.ActivityBase
    public void onResume() {
        Log.d(this.TAG, "onResume start");
        if (CompatibilityUtils.isInMultiWindowMode(this)) {
            if (DataRepository.dataItemFeature().Xj()) {
                ToastUtils.showToast(this, (int) R.string.only_mimoji_multi_window_mode_not_supported);
            } else {
                ToastUtils.showToast(this, (int) R.string.multi_window_mode_not_supported);
            }
            Log.d(this.TAG, "isInMultiWindowMode call finish");
            finish();
        }
        if (getKeyguardFlag() && !PermissionManager.checkCameraLaunchPermissions()) {
            Log.w(this.TAG, "start from keyguard, not grant lunch permission, finish activity");
            finish();
        }
        showBlurCover();
        if (!(Util.sIsnotchScreenHidden == Util.isNotchScreenHidden(this) && Util.sIsFullScreenNavBarHidden == Util.isFullScreenNavBarHidden(this))) {
            Util.initialize(this);
            Util.initStatusBarHeight(getApplicationContext());
            if (Util.isContentViewExtendToTopEdges()) {
                CompatibilityUtils.setCutoutModeShortEdges(getWindow());
            }
        }
        AutoLockManager.getInstance(this).onResume();
        ProximitySensorLock proximitySensorLock = this.mProximitySensorLock;
        if (proximitySensorLock != null) {
            proximitySensorLock.onResume();
        }
        boolean z = false;
        getContentResolver().registerContentObserver(Util.SCREEN_SLIDE_STATUS_SETTING_URI, false, this.mScreenSlideStatusObserver);
        MistatsWrapper.recordPageStart("CameraActivity");
        Util.checkLockedOrientation(this);
        this.mActivityPaused = false;
        this.mActivityStopped = false;
        switchEdgeFingerMode(true);
        this.mFirstOrientationArrived = false;
        this.mOrientationListener.enable();
        if (ParallelSnapshotManager.isParallelTagOpen) {
            ParallelSnapshotManager.getInstance().setCameraCloseState(false, hashCode());
        }
        super.onResume();
        this.mIsScreenSlideOff = Util.isScreenSlideOff(this);
        if (c.Pl()) {
            LensAgent.getInstance().onResume();
        }
        Storage.initStorage(this);
        if (Storage.isUseDocumentMode() && !FileCompat.hasStoragePermission(Storage.DIRECTORY)) {
            if (!getKeyguardFlag()) {
                Log.w(this.TAG, "start request documents permission");
                FileCompat.getStorageAccessForLOLLIPOP(this, Storage.DIRECTORY);
                return;
            }
            Log.w(this.TAG, "documents permission not granted, getKeyguardFlag = " + getKeyguardFlag());
            PriorityStorageBroadcastReceiver.setPriorityStorage(false);
        }
        if (getScreenHint() != null) {
            getScreenHint().updateHint();
        }
        registerReceiver();
        registerSDReceiver();
        resumeCamera();
        this.mIsModeSwitched = false;
        ThermalDetector.getInstance().registerReceiver(this.mThermalNotificationListener);
        if (this.mCameraIntentManager.isImageCaptureIntent() || this.mCameraIntentManager.isVideoCaptureIntent()) {
            z = true;
        }
        if (this.mImageSaver == null) {
            this.mImageSaver = new ImageSaver(this, this.mHandler, z);
        }
        this.mImageSaver.onHostResume(z);
        bindServices();
        triggerWatchDog(true);
        Util.updateAccessibility(this);
        if (this.mIsInRequestRuntimePermission) {
            this.mCameraRootView.post(new j(this));
        } else if (!CameraSettings.isShowFirstUseHint() && DataRepository.dataItemGlobal().getBoolean(DataItemGlobal.DATA_COMMON_LPL_SELECTOR_USE_HINT_SHOWN, true)) {
            showLplSelectDialog();
        }
        Log.d(this.TAG, "onResume end");
    }

    /* access modifiers changed from: protected */
    @Override // androidx.activity.ComponentActivity, androidx.fragment.app.FragmentActivity, com.android.camera.ActivityBase
    public void onSaveInstanceState(Bundle bundle) {
        Log.d(this.TAG, "onSaveInstanceState");
    }

    /* access modifiers changed from: protected */
    @Override // androidx.fragment.app.FragmentActivity, com.android.camera.ActivityBase
    public void onStart() {
        Log.d(this.TAG, "onStart start");
        this.mActivityStarted = true;
        super.onStart();
        BaseFragmentDelegate baseFragmentDelegate = this.mBaseFragmentDelegate;
        if (baseFragmentDelegate != null) {
            baseFragmentDelegate.getAnimationComposite().onStart();
        }
        Log.d(this.TAG, "onStart end");
    }

    @Override // androidx.fragment.app.FragmentActivity, com.android.camera.ActivityBase
    public void onStop() {
        Log.d(this.TAG, "onStop start");
        super.onStop();
        removeNewBie();
        this.mActivityStopped = true;
        closeCameraSetup();
        setSwitchingModule(false);
        this.mHasBeenSetupOnFocusChanged = false;
        BaseFragmentDelegate baseFragmentDelegate = this.mBaseFragmentDelegate;
        if (baseFragmentDelegate != null) {
            baseFragmentDelegate.getAnimationComposite().onStop();
        }
        if (!this.mReleaseByModule) {
            releaseAll(true, true);
        }
        unbindServices();
        if (!isGotoGallery() && !isGotoSettings()) {
            MiAlgoAsdSceneProfile.clearInitASDScenes();
        }
        if (getCurrentModule() != null && (getCurrentModule() instanceof Camera2Module)) {
            ((Camera2Module) getCurrentModule()).resetMagneticInfo();
        }
        Log.d(this.TAG, "onStop end");
    }

    public void onTrimMemory(int i) {
        super.onTrimMemory(i);
        String str = this.TAG;
        Log.v(str, "onTrimMemory: level=" + i);
        MemoryHelper.setTrimLevel(i);
    }

    public void onUserInteraction() {
        super.onUserInteraction();
        Log.d(this.TAG, "onUserInteraction");
        AutoLockManager.getInstance(this).onUserInteraction();
        if (isCurrentModuleAlive()) {
            this.mCurrentModule.onUserInteraction();
        }
    }

    public void onWindowFocusChanged(boolean z) {
        boolean isFromKeyguard = isFromKeyguard();
        String str = this.TAG;
        Log.d(str, "onWindowFocusChanged: hasFocus: " + z + ", isFromKeyguard: " + isFromKeyguard + ", mHasBeenSetupOnFocusChanged " + this.mHasBeenSetupOnFocusChanged);
        this.mHasFocus = z;
        super.onWindowFocusChanged(z);
        if (isFromKeyguard && this.mHasFocus && this.mCameraSetupDisposable == null && !this.mHasBeenSetupOnFocusChanged && !isCurrentModuleAlive()) {
            Log.d(this.TAG, "onWindowFocusChanged: setupCamera: keyguard");
            this.mHasBeenSetupOnFocusChanged = true;
            setupCamera(this.mStartControl);
        }
        Module module = this.mCurrentModule;
        if (module != null) {
            module.onWindowFocusChanged(z);
            CameraBrightness cameraBrightness = this.mCameraBrightness;
            if (cameraBrightness != null) {
                cameraBrightness.onWindowFocusChanged(z);
            }
            if (z) {
                Util.checkLockedOrientation(this);
                this.mCurrentModule.checkActivityOrientation();
                SensorStateManager sensorStateManager = this.mSensorStateManager;
                if (sensorStateManager != null) {
                    sensorStateManager.register();
                    return;
                }
                return;
            }
            SensorStateManager sensorStateManager2 = this.mSensorStateManager;
            if (sensorStateManager2 != null) {
                sensorStateManager2.unregister(SensorStateManager.SENSOR_ALL);
            }
        }
    }

    @Override // com.android.camera.ActivityBase
    public void pause() {
        if (!isRecording()) {
            super.pause();
        }
    }

    @Override // com.android.camera.protocol.ModeProtocol.BaseProtocol
    public void registerProtocol() {
        ModeCoordinatorImpl.forceDestroy();
        ModeCoordinatorImpl.create(hashCode());
        EffectChangedListenerController.setHoldKey(hashCode());
        this.mImplFactory = new ImplFactory();
        this.mImplFactory.initBase(this, 171);
        if (DataRepository.dataItemFeature().em()) {
            this.mImplFactory.initBase(this, 239);
        }
        if (c.hk()) {
            this.mImplFactory.initBase(this, 255);
        }
        DataItemGlobal dataItemGlobal = DataRepository.dataItemGlobal();
        getCameraIntentManager();
        dataItemGlobal.parseIntent(getIntent(), Boolean.valueOf(this.mCameraIntentManager.checkCallerLegality()), startFromSecureKeyguard(), false, true);
        int i = 2;
        if (dataItemGlobal.isTimeOut()) {
            i = 3;
        }
        onModeSelected(StartControl.create(dataItemGlobal.getCurrentMode()).setResetType(i));
    }

    public void releaseAll(boolean z, boolean z2) {
        if (isActivityStopped() || !z) {
            releaseAll(z, z2, true);
        } else {
            this.mReleaseByModule = false;
        }
    }

    public void removeNewBie() {
        Fragment findFragmentByTag;
        String str = this.TAG;
        Log.d(str, "removeNewBie = " + this.newbieDialogFragmentTag);
        getCameraScreenNail().drawBlackFrame(false);
        FragmentTransaction beginTransaction = getSupportFragmentManager().beginTransaction();
        if (!(this.newbieDialogFragmentTag == null || (findFragmentByTag = getSupportFragmentManager().findFragmentByTag(this.newbieDialogFragmentTag)) == null)) {
            beginTransaction.remove(findFragmentByTag);
        }
        beginTransaction.commitAllowingStateLoss();
    }

    public void restoreWindowBrightness() {
        int currentBrightness;
        CameraBrightness cameraBrightness = this.mCameraBrightness;
        float f = (cameraBrightness == null || (currentBrightness = cameraBrightness.getCurrentBrightness()) <= 0) ? -1.0f : ((float) currentBrightness) / 255.0f;
        WindowManager.LayoutParams attributes = getWindow().getAttributes();
        attributes.screenBrightness = f;
        getWindow().setAttributes(attributes);
        setBrightnessRampRate(-1);
        setScreenEffect(false);
    }

    @Override // com.android.camera.ActivityBase
    public void resume() {
        if (!isRecording()) {
            super.resume();
        }
    }

    public void resumeCurrentMode(int i) {
        ThreadUtils.assertMainThread();
        closeCameraSetup();
        setSwitchingModule(true);
        FunctionResumeModule functionResumeModule = new FunctionResumeModule(i);
        Single map = Single.just(NullHolder.ofNullable((BaseModule) this.mCurrentModule)).observeOn(CameraSchedulers.sCameraSetupScheduler).map(new f(this));
        Single observeOn = Single.create(this.mCamera2OpenOnSubscribe).subscribeOn(CameraSchedulers.sCameraSetupScheduler).observeOn(CameraSchedulers.sCameraSetupScheduler);
        Log.d(this.TAG, "resumeCurrentMode: CameraSetupDisposable: E");
        this.mCameraSetupDisposable = map.zipWith(observeOn, this.mCameraOpenResult).map(functionResumeModule).observeOn(AndroidSchedulers.mainThread()).subscribe(this.mCameraSetupConsumer);
    }

    @Override // com.android.camera.fragment.lifeCircle.BaseLifecycleListener
    @Deprecated
    public void setBlockingLifeCycles(List<String> list) {
    }

    public void setIntnetPhotoDone(boolean z) {
        this.mIntentPhotoDone = z;
    }

    public void setWindowBrightness(int i) {
        setBrightnessRampRate(0);
        setScreenEffect(true);
        WindowManager.LayoutParams attributes = getWindow().getAttributes();
        attributes.screenBrightness = ((float) i) / 255.0f;
        getWindow().setAttributes(attributes);
    }

    public void showGuide() {
        showGuide(DataRepository.dataItemGlobal().getCurrentMode());
    }

    public void showGuide(int i) {
        boolean isCtsCall = getCameraIntentManager().isCtsCall();
        String str = this.TAG;
        Log.d(str, "showGuide = " + i + "  isCtsCall = " + isCtsCall);
        if (!isCtsCall && !ThermalDetector.getInstance().thermalConstrained()) {
            DataItemGlobal dataItemGlobal = DataRepository.dataItemGlobal();
            c cVar = new c(this, dataItemGlobal);
            BaseDialogFragment baseDialogFragment = null;
            if (i != 162 && i != 163 && i == 182 && DataRepository.dataItemFeature().Gj() && !isNewBieAlive(8) && dataItemGlobal.getBoolean("pref_camera_first_id_card_mode_use_hint_shown_key", true)) {
                baseDialogFragment = showNewBie(8);
            }
            if (baseDialogFragment != null) {
                baseDialogFragment.setDismissCallback(cVar);
            } else {
                cVar.onDismiss();
            }
        }
    }

    public void showLensDirtyDetectedHint() {
        if (DataRepository.dataItemFeature().Jj()) {
            this.mHandler.post(new Runnable() {
                /* class com.android.camera.Camera.AnonymousClass8 */

                public void run() {
                    FragmentTopConfig fragmentTopConfig = (FragmentTopConfig) ModeCoordinatorImpl.getInstance().getAttachProtocol(172);
                    if (fragmentTopConfig != null) {
                        fragmentTopConfig.alertAiDetectTipHint(0, R.string.dirty_tip_toast, 3000);
                    }
                }
            });
        } else if (getSupportFragmentManager().findFragmentByTag(LensDirtyDetectDialogFragment.TAG) == null) {
            LensDirtyDetectDialogFragment lensDirtyDetectDialogFragment = new LensDirtyDetectDialogFragment();
            lensDirtyDetectDialogFragment.setStyle(2, R.style.LensDirtyDetectDialogFragment);
            getSupportFragmentManager().beginTransaction().add(lensDirtyDetectDialogFragment, LensDirtyDetectDialogFragment.TAG).commitAllowingStateLoss();
        }
    }

    public void showLplSelectDialog() {
        if (DataRepository.dataItemFeature().Oj() && c.Ln()) {
            LongPressLiveFragment longPressLiveFragment = new LongPressLiveFragment();
            longPressLiveFragment.setStyle(2, R.style.DialogFragmentFullScreen);
            getSupportFragmentManager().beginTransaction().add(longPressLiveFragment, "ThermalDialog").commitAllowingStateLoss();
            this.newbieDialogFragmentTag = "ThermalDialog";
            DataRepository.dataItemGlobal().editor().putBoolean(DataItemGlobal.DATA_COMMON_LPL_SELECTOR_USE_HINT_SHOWN, false).apply();
        }
    }

    public BaseDialogFragment showNewBie(int i) {
        if (!(i == 2 || getCameraScreenNail() == null)) {
            getCameraScreenNail().drawBlackFrame(true);
        }
        if (i == 2) {
            FrontRotateNewbieDialogFragment frontRotateNewbieDialogFragment = new FrontRotateNewbieDialogFragment();
            frontRotateNewbieDialogFragment.setStyle(2, R.style.DialogFragmentFullScreen);
            getSupportFragmentManager().beginTransaction().add(frontRotateNewbieDialogFragment, FrontRotateNewbieDialogFragment.TAG).commitAllowingStateLoss();
            this.newbieDialogFragmentTag = FrontRotateNewbieDialogFragment.TAG;
            return frontRotateNewbieDialogFragment;
        } else if (i != 8) {
            return null;
        } else {
            IDCardModeNewbieDialogFragment iDCardModeNewbieDialogFragment = new IDCardModeNewbieDialogFragment();
            iDCardModeNewbieDialogFragment.setStyle(2, R.style.DialogFragmentFullScreen);
            getSupportFragmentManager().beginTransaction().add(iDCardModeNewbieDialogFragment, IDCardModeNewbieDialogFragment.TAG).commitAllowingStateLoss();
            this.newbieDialogFragmentTag = IDCardModeNewbieDialogFragment.TAG;
            DataRepository.dataItemGlobal().editor().putBoolean("pref_camera_first_id_card_mode_use_hint_shown_key", false).apply();
            return iDCardModeNewbieDialogFragment;
        }
    }

    public void showNewNotification() {
        if (startFromSecureKeyguard() || Util.isGlobalVersion() || this.mIsLunchFromAutoTest || TextUtils.isEmpty(DataRepository.dataItemFeature().Jh()) || DataRepository.dataItemGlobal().getBoolean(CameraSettings.KEY_CAMERA_FIRST_NOTIFICATION_SHOWN, false)) {
            Log.w(this.TAG, "showNewNotification: return...");
            return;
        }
        LocaleList locales = getResources().getConfiguration().getLocales();
        if (locales != null && locales.size() >= 1) {
            Locale locale = locales.get(0);
            if (locale.getDisplayName().equals(Locale.CHINA.getDisplayName()) || locale.getDisplayName().equals(Locale.US.getDisplayName())) {
                DataRepository.dataItemGlobal().editor().putBoolean(CameraSettings.KEY_CAMERA_FIRST_NOTIFICATION_SHOWN, true).apply();
                Intent intent = new Intent("com.miui.miservice.MISERVICE_NOTIFICATION");
                intent.putExtra("intent_extra_key_flag", "2");
                intent.putExtra("intent_extra_key_label", DataRepository.dataItemFeature().Jh());
                intent.putExtra("intent_extra_key_title", getString(R.string.miservice_notification_title));
                intent.putExtra("intent_extra_key_content", getString(R.string.miservice_notification_content));
                intent.putExtra("intent_extra_key_is_to_main", true);
                intent.addFlags(32);
                intent.addFlags(16777216);
                sendBroadcast(intent);
                return;
            }
            Log.w(this.TAG, "showNewNotification: locale does not match, return...");
        }
    }

    public void startLensActivity() {
        if (this.mLensApi != null) {
            boolean checkLensAvailability = CameraSettings.checkLensAvailability(getApplicationContext());
            String str = this.TAG;
            Log.d(str, "startLensActivity: isAvailable = " + checkLensAvailability);
            if (checkLensAvailability) {
                this.mLensApi.launchLensActivity(this, 0);
            }
        }
    }

    @Override // com.android.camera.protocol.ModeProtocol.BaseProtocol
    public void unRegisterProtocol() {
        ImplFactory implFactory = this.mImplFactory;
        if (implFactory != null) {
            implFactory.detachBase();
        }
        BaseFragmentDelegate baseFragmentDelegate = this.mBaseFragmentDelegate;
        if (baseFragmentDelegate != null) {
            baseFragmentDelegate.unRegisterProtocol();
            this.mBaseFragmentDelegate = null;
        }
    }

    @Override // com.android.camera.module.loader.SurfaceStateListener, com.android.camera.ActivityBase
    public void updateSurfaceState(int i) {
        super.updateSurfaceState(i);
        if (i == 4) {
            this.mCamera2OpenOnSubscribe.onGlSurfaceCreated();
            if (ModuleManager.isCapture()) {
                Module module = this.mCurrentModule;
                if (module != null) {
                    ((BaseModule) module).updatePreviewSurface();
                } else {
                    Log.w(this.TAG, "updateSurfaceState: module has not been initialized");
                }
            }
        }
    }
}
