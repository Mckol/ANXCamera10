package com.android.camera;

import android.app.AlertDialog;
import android.app.KeyguardManager;
import android.app.NotificationManager;
import android.content.ActivityNotFoundException;
import android.content.ContentUris;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.SurfaceTexture;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.view.Display;
import android.view.KeyEvent;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.UiThread;
import androidx.fragment.app.FragmentActivity;
import b.c.a.c;
import com.android.camera.CameraScreenNail;
import com.android.camera.aftersales.AftersalesManager;
import com.android.camera.data.DataRepository;
import com.android.camera.effect.draw_mode.DrawExtTexAttribute;
import com.android.camera.fragment.dialog.ThermalDialogFragment;
import com.android.camera.lib.compatibility.util.CompatibilityUtils;
import com.android.camera.log.Log;
import com.android.camera.module.Module;
import com.android.camera.module.ModuleManager;
import com.android.camera.module.loader.SurfaceStateListener;
import com.android.camera.protocol.ModeCoordinatorImpl;
import com.android.camera.protocol.ModeProtocol;
import com.android.camera.statistic.CameraStatUtils;
import com.android.camera.statistic.ScenarioTrackUtil;
import com.android.camera.storage.ImageSaver;
import com.android.camera.storage.Storage;
import com.android.camera.ui.CameraRootView;
import com.android.camera.ui.PopupManager;
import com.android.camera.ui.ScreenHint;
import com.android.camera.ui.V6CameraGLSurfaceView;
import com.android.camera.ui.V9EdgeShutterView;
import com.android.gallery3d.ui.GLCanvas;
import com.xiaomi.camera.device.CameraHandlerThread;
import com.xiaomi.camera.device.CameraService;
import com.xiaomi.stat.d;
import io.reactivex.Single;
import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import java.io.File;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Locale;
import java.util.Objects;

public abstract class ActivityBase extends FragmentActivity implements AppController, SurfaceStateListener, ImageSaver.ImageSaverCallback {
    public static final int MSG_CAMERA_OPEN_EXCEPTION = 10;
    protected static final int MSG_DEBUG_INFO = 0;
    protected static final int MSG_KEYGUARD_TWICE_RESUME = 1;
    protected static final int MSG_ON_THERMAL_CONTRAINT = 3;
    protected static final int MSG_TRACK_MODE_SWITCH = 2;
    private static final int START_GALLERY_TIMEOUT = 300;
    private static final String TAG = "ActivityBase";
    private static final int THERMAL_CONSTRAINED_EXIT_DELAY = 5000;
    private static final int TOUCH_EVENT_TRACK_TIME_OUT = 1000;
    protected volatile boolean mActivityPaused;
    protected volatile boolean mActivityStarted;
    protected volatile boolean mActivityStopped;
    private HashMap mAppLunchMap;
    protected long mAppStartTime;
    protected CameraAppImpl mApplication;
    private long mBlurStartTime = -1;
    protected CameraBrightness mCameraBrightness;
    private boolean mCameraErrorShown;
    public CameraIntentManager mCameraIntentManager;
    protected CameraRootView mCameraRootView;
    protected CameraScreenNail mCameraScreenNail;
    private MiuiCameraSound mCameraSound;
    private Thread mCloseActivityThread;
    protected Module mCurrentModule;
    private int mCurrentSurfaceState = 1;
    protected TextView mDebugInfoView;
    protected int mDisplayRotation;
    protected V9EdgeShutterView mEdgeShutterView;
    private AlertDialog mErrorDialog;
    private Disposable mGLCoverDisposable;
    protected ImageView mGLCoverView;
    protected V6CameraGLSurfaceView mGLView;
    private boolean mGalleryLocked = false;
    protected final Handler mHandler = new ActivityHandler(this);
    private boolean mIsFinishInKeyguard = false;
    private boolean mIsSwitchingModule;
    private int mJumpFlag = 0;
    protected boolean mJumpedToGallery;
    private KeyguardManager mKeyguardManager;
    private boolean mKeyguardSecureLocked = false;
    private int mLastJumpFlag = 0;
    private LocationManager mLocationManager;
    protected long mModeSelectGaussianTime = -1;
    protected long mModeSwitchTime;
    protected int mOrientation = -1;
    protected int mOrientationCompensation = 0;
    private boolean mPreviewThumbnail;
    protected boolean mReleaseByModule;
    protected ScreenHint mScreenHint;
    private ArrayList<Uri> mSecureUriList;
    protected boolean mStartFromKeyguard = false;
    private ThumbnailUpdater mThumbnailUpdater;
    private Disposable mTrackAppLunchDisposable;

    private static class ActivityHandler extends Handler {
        private final WeakReference<ActivityBase> mActivity;

        public ActivityHandler(ActivityBase activityBase) {
            this.mActivity = new WeakReference<>(activityBase);
        }

        /* JADX WARNING: Removed duplicated region for block: B:26:0x0061  */
        /* JADX WARNING: Removed duplicated region for block: B:27:0x0065  */
        public void handleMessage(Message message) {
            ActivityBase activityBase = this.mActivity.get();
            if (activityBase != null) {
                int i = message.what;
                if (i != 0) {
                    boolean z = true;
                    if (i == 1) {
                        Log.d(ActivityBase.TAG, "handleMessage:  set mIsFinishInKeyguard = true;");
                        activityBase.mIsFinishInKeyguard = true;
                    } else if (i == 2) {
                        CameraStatUtils.trackModeSwitch();
                    } else if (i == 3) {
                        ThermalDialogFragment thermalDialogFragment = new ThermalDialogFragment();
                        thermalDialogFragment.setStyle(2, R.style.DialogFragmentFullScreen);
                        activityBase.getSupportFragmentManager().beginTransaction().add(thermalDialogFragment, "ThermalDialog").commitAllowingStateLoss();
                        Objects.requireNonNull(activityBase);
                        postDelayed(new i(activityBase), 5000);
                        Log.w(ActivityBase.TAG, "onThermalNotification finish activity after 5 seconds");
                    } else if (i == 10) {
                        int i2 = message.arg1;
                        Log.d(ActivityBase.TAG, String.format(Locale.ENGLISH, "exception occurs, msg = %s , exception = 0x%x", message, Integer.valueOf(i2)));
                        if (!(i2 == 230 || i2 == 231)) {
                            if (i2 != 236) {
                                if (i2 != 237) {
                                    switch (i2) {
                                        case 226:
                                        case 228:
                                            break;
                                        case 227:
                                            Util.showErrorAndFinish(activityBase, R.string.camera_disabled, false);
                                            activityBase.showErrorDialog();
                                            return;
                                        default:
                                            return;
                                    }
                                }
                            }
                            Util.showErrorAndFinish(activityBase, CameraSettings.updateOpenCameraFailTimes() <= 1 ? R.string.cannot_connect_camera_twice : R.string.cannot_connect_camera_once, z);
                            activityBase.showErrorDialog();
                        }
                        z = false;
                        Util.showErrorAndFinish(activityBase, CameraSettings.updateOpenCameraFailTimes() <= 1 ? R.string.cannot_connect_camera_twice : R.string.cannot_connect_camera_once, z);
                        activityBase.showErrorDialog();
                    }
                } else if (!activityBase.isActivityPaused()) {
                    activityBase.showDebugInfo((String) message.obj);
                }
            }
        }
    }

    public static class SaveGaussian2File implements Runnable {
        Bitmap mBitmap;

        public SaveGaussian2File(Bitmap bitmap) {
            this.mBitmap = bitmap;
        }

        public void run() {
            Bitmap bitmap = this.mBitmap;
            if (bitmap != null && !bitmap.isRecycled()) {
                Util.saveLastFrameGaussian2File(this.mBitmap);
            }
        }
    }

    private void addSecureUriIfNecessary(Uri uri) {
        ArrayList<Uri> arrayList = this.mSecureUriList;
        if (arrayList != null) {
            if (arrayList.size() == 100) {
                this.mSecureUriList.remove(0);
            }
            this.mSecureUriList.add(uri);
        }
    }

    private void checkGalleryLock() {
        this.mGalleryLocked = Util.isAppLocked(this, Util.REVIEW_ACTIVITY_PACKAGE);
        Log.v(TAG, "checkGalleryLock: galleryLocked=" + this.mGalleryLocked);
    }

    private void checkKeyguardFlag() {
        String str;
        this.mStartFromKeyguard = getKeyguardFlag();
        this.mKeyguardSecureLocked = this.mStartFromKeyguard && this.mKeyguardManager.isKeyguardSecure() && this.mKeyguardManager.isKeyguardLocked();
        if (this.mStartFromKeyguard && !this.mIsFinishInKeyguard) {
            setShowWhenLocked(true);
            this.mIsFinishInKeyguard = false;
            this.mHandler.sendEmptyMessageDelayed(1, 100);
        }
        DataRepository.dataItemGlobal().setStartFromKeyguard(this.mKeyguardSecureLocked);
        if (!this.mKeyguardSecureLocked && !isGalleryLocked()) {
            this.mSecureUriList = null;
        } else if (this.mSecureUriList == null) {
            this.mSecureUriList = new ArrayList<>();
        }
        StringBuilder sb = new StringBuilder();
        sb.append("checkKeyguard: fromKeyguard=");
        sb.append(this.mStartFromKeyguard);
        sb.append(" keyguardSecureLocked=");
        sb.append(this.mKeyguardSecureLocked);
        sb.append(" secureUriList is ");
        if (this.mSecureUriList == null) {
            str = "null";
        } else {
            str = "not null (" + this.mSecureUriList.size() + ")";
        }
        sb.append(str);
        Log.v(TAG, sb.toString());
    }

    private void clearNotification() {
        NotificationManager notificationManager = (NotificationManager) getSystemService("notification");
        if (notificationManager != null) {
            notificationManager.cancelAll();
        }
    }

    private long[] getSecureStoreIds() {
        ArrayList<Uri> arrayList = this.mSecureUriList;
        int i = 0;
        if (arrayList == null || arrayList.isEmpty()) {
            return new long[0];
        }
        long[] jArr = new long[this.mSecureUriList.size()];
        Iterator<Uri> it = this.mSecureUriList.iterator();
        while (it.hasNext()) {
            jArr[i] = ContentUris.parseId(it.next());
            i++;
        }
        return jArr;
    }

    /* access modifiers changed from: private */
    /* access modifiers changed from: public */
    private void showBlurView(Bitmap bitmap) {
        Rect displayRect = Util.getDisplayRect();
        ((ViewGroup.MarginLayoutParams) this.mGLCoverView.getLayoutParams()).topMargin = displayRect.top;
        this.mGLCoverView.setMaxWidth(displayRect.right - displayRect.left);
        this.mGLCoverView.setMaxHeight(displayRect.bottom - displayRect.top);
        this.mGLCoverView.setImageBitmap(bitmap);
        this.mGLCoverView.setAlpha(1.0f);
        this.mGLCoverView.setVisibility(0);
    }

    public boolean couldShowErrorDialog() {
        return !this.mCameraErrorShown;
    }

    public void createCameraScreenNail(boolean z, boolean z2) {
        if (this.mCameraScreenNail == null) {
            this.mCameraScreenNail = new CameraScreenNail(new CameraScreenNail.NailListener() {
                /* class com.android.camera.ActivityBase.AnonymousClass7 */

                @Override // com.android.camera.CameraScreenNail.NailListener
                public int getOrientation() {
                    return ActivityBase.this.mOrientation;
                }

                @Override // com.android.camera.CameraScreenNail.NailListener
                public boolean isKeptBitmapTexture() {
                    return ActivityBase.this.mCurrentModule.isKeptBitmapTexture();
                }

                @Override // com.android.camera.CameraScreenNail.NailListener
                public void onFrameAvailable(int i) {
                    if (1 == i && ActivityBase.this.mAppStartTime != 0) {
                        try {
                            long currentTimeMillis = System.currentTimeMillis() - ActivityBase.this.mAppStartTime;
                            CameraStatUtils.trackStartAppCost(currentTimeMillis);
                            if (ActivityBase.this.mAppLunchMap != null) {
                                ScenarioTrackUtil.trackAppLunchTimeEnd(ActivityBase.this.mAppLunchMap, ActivityBase.this.getApplicationContext());
                            } else {
                                ScenarioTrackUtil.trackScenarioAbort(ScenarioTrackUtil.sLaunchTimeScenario);
                            }
                            Log.d(ActivityBase.TAG, "onFrameAvailable: trackStartAppCost: " + currentTimeMillis);
                        } catch (IllegalArgumentException e2) {
                            Log.w(ActivityBase.TAG, e2.getMessage() + ", start time: " + ActivityBase.this.mAppStartTime + ", now: " + System.currentTimeMillis());
                        }
                        ActivityBase.this.mAppStartTime = 0;
                    } else if (ActivityBase.this.mModeSwitchTime != 0) {
                        long currentTimeMillis2 = System.currentTimeMillis() - ActivityBase.this.mModeSwitchTime;
                        Log.d(ActivityBase.TAG, "onFrameAvailable: trackModeSwitchCost: " + currentTimeMillis2);
                        ActivityBase.this.mModeSwitchTime = 0;
                    }
                    ActivityBase.this.dismissBlurCover();
                    ActivityBase.this.notifyOnFirstFrameArrived(i);
                }

                @Override // com.android.camera.CameraScreenNail.NailListener
                public void onPreviewPixelsRead(byte[] bArr, int i, int i2) {
                    ActivityBase.this.mCurrentModule.onPreviewPixelsRead(bArr, i, i2);
                }

                @Override // com.android.camera.CameraScreenNail.NailListener
                public void onPreviewTextureCopied() {
                }

                @Override // com.android.camera.SurfaceTextureScreenNail.SurfaceTextureScreenNailCallback
                public void onSurfaceTextureCreated(SurfaceTexture surfaceTexture) {
                }

                @Override // com.android.camera.SurfaceTextureScreenNail.SurfaceTextureScreenNailCallback
                public boolean onSurfaceTexturePending(GLCanvas gLCanvas, DrawExtTexAttribute drawExtTexAttribute) {
                    Module module = ActivityBase.this.mCurrentModule;
                    return module != null && module.onSurfaceTexturePending(gLCanvas, drawExtTexAttribute);
                }

                @Override // com.android.camera.SurfaceTextureScreenNail.SurfaceTextureScreenNailCallback
                public void onSurfaceTextureReleased() {
                    Module module = ActivityBase.this.mCurrentModule;
                    if (module != null) {
                        module.onSurfaceTextureReleased();
                    }
                }

                @Override // com.android.camera.SurfaceTextureScreenNail.SurfaceTextureScreenNailCallback
                public void onSurfaceTextureUpdated(GLCanvas gLCanvas, DrawExtTexAttribute drawExtTexAttribute) {
                    Module module = ActivityBase.this.mCurrentModule;
                    if (module != null) {
                        module.onSurfaceTextureUpdated(gLCanvas, drawExtTexAttribute);
                    }
                }
            }, new CameraScreenNail.RequestRenderListener() {
                /* class com.android.camera.ActivityBase.AnonymousClass8 */

                @Override // com.android.camera.CameraScreenNail.RequestRenderListener
                public void requestRender() {
                    if (ActivityBase.this.mCameraScreenNail.isAnimationRunning() || ActivityBase.this.mCameraScreenNail.getExternalFrameProcessor() == null || !ActivityBase.this.mCameraScreenNail.getExternalFrameProcessor().isProcessorReady() || ActivityBase.this.mCameraScreenNail.getExternalFrameProcessor().getProcessorType() == 1) {
                        ActivityBase.this.mGLView.requestRender();
                    }
                    Module module = ActivityBase.this.mCurrentModule;
                    if (module != null) {
                        module.requestRender();
                    }
                }
            });
        }
        initCameraScreenNail();
    }

    public void dismissBlurCover() {
        ImageView imageView = this.mGLCoverView;
        if (imageView == null) {
            this.mBlurStartTime = -1;
        } else if (imageView.getVisibility() == 8) {
            this.mBlurStartTime = -1;
        } else {
            this.mGLCoverView.post(new Runnable() {
                /* class com.android.camera.ActivityBase.AnonymousClass4 */

                public void run() {
                    ActivityBase.this.mGLCoverView.animate().alpha(0.0f).setDuration(DataRepository.dataItemFeature().ik() ? 100 : 200).withEndAction(new Runnable() {
                        /* class com.android.camera.ActivityBase.AnonymousClass4.AnonymousClass1 */

                        public void run() {
                            ActivityBase.this.mGLCoverView.setVisibility(8);
                        }
                    }).start();
                }
            });
            if (this.mBlurStartTime > -1 && SystemClock.uptimeMillis() - this.mBlurStartTime > 3000) {
                AftersalesManager.getInstance().count(System.currentTimeMillis(), 3);
                this.mBlurStartTime = -1;
            }
        }
    }

    public void dismissKeyguard() {
        if (this.mStartFromKeyguard) {
            sendBroadcast(new Intent(Util.ACTION_DISMISS_KEY_GUARD));
        }
    }

    public CameraAppImpl getCameraAppImpl() {
        return this.mApplication;
    }

    public CameraIntentManager getCameraIntentManager() {
        this.mCameraIntentManager = CameraIntentManager.getInstance(getIntent());
        return this.mCameraIntentManager;
    }

    @Override // com.android.camera.storage.ImageSaver.ImageSaverCallback
    public CameraScreenNail getCameraScreenNail() {
        return this.mCameraScreenNail;
    }

    @Override // com.android.camera.AppController
    public Module getCurrentModule() {
        return this.mCurrentModule;
    }

    @Override // com.android.camera.storage.ImageSaver.ImageSaverCallback
    public int getDisplayRotation() {
        return Util.getDisplayRotation(this);
    }

    public V9EdgeShutterView getEdgeShutterView() {
        return this.mEdgeShutterView;
    }

    public V6CameraGLSurfaceView getGLView() {
        return this.mGLView;
    }

    /* access modifiers changed from: protected */
    public boolean getKeyguardFlag() {
        return getCameraIntentManager().isQuickLaunch();
    }

    public int getOrientation() {
        return this.mOrientation;
    }

    @Override // com.android.camera.storage.ImageSaver.ImageSaverCallback
    public ScreenHint getScreenHint() {
        return this.mScreenHint;
    }

    public ArrayList<Uri> getSecureUriList() {
        return this.mSecureUriList;
    }

    public long getSoundPlayTime() {
        MiuiCameraSound miuiCameraSound = this.mCameraSound;
        if (miuiCameraSound != null) {
            return miuiCameraSound.getLastSoundPlayTime();
        }
        return 0;
    }

    @Override // com.android.camera.storage.ImageSaver.ImageSaverCallback
    public ThumbnailUpdater getThumbnailUpdater() {
        return this.mThumbnailUpdater;
    }

    public void gotoGallery() {
        Thumbnail thumbnail;
        if (!isActivityPaused() && (thumbnail = this.mThumbnailUpdater.getThumbnail()) != null) {
            Uri uri = thumbnail.getUri();
            if (!Util.isUriValid(uri, getContentResolver())) {
                Log.e(TAG, "Uri invalid. uri=" + uri);
                if (!thumbnail.isWaitingForUri()) {
                    getThumbnailUpdater().getLastThumbnailUncached();
                    return;
                }
                return;
            }
            try {
                Intent intent = new Intent(Util.REVIEW_ACTION, uri);
                intent.setPackage(DataRepository.dataItemFeature().hi() ? Util.ANDROID_ONE_REVIEW_ACTIVITY_PACKAGE : Util.REVIEW_ACTIVITY_PACKAGE);
                intent.putExtra(Util.KEY_REVIEW_FROM_MIUICAMERA, true);
                if (c.xm()) {
                    if (this.mCameraBrightness.getCurrentBrightnessAuto() != 0.0f) {
                        intent.putExtra(Util.KEY_CAMERA_BRIGHTNESS_AUTO, this.mCameraBrightness.getCurrentBrightnessAuto());
                    } else {
                        intent.putExtra(Util.KEY_CAMERA_BRIGHTNESS_MANUAL, this.mCameraBrightness.getCurrentBrightnessManual());
                        intent.putExtra(Util.KEY_CAMERA_BRIGHTNESS, this.mCameraBrightness.getCurrentBrightness());
                    }
                }
                if (startFromKeyguard()) {
                    if (DataRepository.dataItemFeature().hi()) {
                        intent.putExtra(Util.ANDROID_ONE_EXTRA_IS_SECURE_MODE, true);
                    } else {
                        intent.putExtra("StartActivityWhenLocked", true);
                    }
                }
                if (Util.isAppLocked(this, Util.REVIEW_ACTIVITY_PACKAGE)) {
                    intent.putExtra(Util.EXTRAS_SKIP_LOCK, true);
                }
                if (thumbnail.isGif()) {
                    intent.putExtra(Util.EXTRA_WIDTH_FROM_CAMERA, 300);
                    intent.putExtra(Util.EXTRA_HEIGHT_FROM_CAMERA, 300);
                    intent.putExtra(Util.EXTRA_MIME_TYPE_FROM_CAMERA, Storage.MIME_GIF);
                }
                if (this.mSecureUriList != null) {
                    if (DataRepository.dataItemFeature().hi()) {
                        intent.putExtra(Util.ANDROID_ONE_EXTRA_SECURE_MODE_MEDIA_STORE_IDS, getSecureStoreIds());
                    } else {
                        intent.putParcelableArrayListExtra(Util.KEY_SECURE_ITEMS, this.mSecureUriList);
                    }
                }
                if (DataRepository.dataItemFeature().pj()) {
                    intent.putExtra("using_deputy_screen", DataRepository.dataItemGlobal().getDisplayMode() == 2);
                }
                intent.putExtra("device_orientation", this.mOrientation);
                startActivity(intent);
                this.mJumpFlag = 1;
                this.mJumpedToGallery = true;
                if (this.mCurrentModule != null) {
                    this.mCurrentModule.enableCameraControls(false);
                    CameraStatUtils.trackGotoGallery(this.mCurrentModule.getModuleIndex());
                }
            } catch (ActivityNotFoundException e2) {
                Log.e(TAG, "review activity not found!", e2);
                try {
                    startActivity(new Intent("android.intent.action.VIEW", uri));
                } catch (ActivityNotFoundException e3) {
                    Log.e(TAG, "review image fail. uri=" + uri, e3);
                }
            }
        }
    }

    @Override // com.android.camera.module.loader.SurfaceStateListener
    public synchronized boolean hasSurface() {
        int i = this.mCurrentSurfaceState;
        if (i != 2) {
            if (i == 4) {
                if (getCameraScreenNail().getSurfaceTexture() != null) {
                    return true;
                }
                this.mGLView.onResume();
                return false;
            }
        } else if (Util.sIsFullScreenNavBarHidden) {
            this.mHandler.post(new Runnable() {
                /* class com.android.camera.ActivityBase.AnonymousClass9 */

                public void run() {
                    ActivityBase.this.mGLView.setVisibility(4);
                    ActivityBase.this.mGLView.setVisibility(0);
                }
            });
        } else {
            this.mGLView.onResume();
        }
        return false;
    }

    public void initCameraScreenNail() {
        Log.d(TAG, "initCameraScreenNail");
        CameraScreenNail cameraScreenNail = this.mCameraScreenNail;
        if (cameraScreenNail != null && cameraScreenNail.getSurfaceTexture() == null) {
            Display defaultDisplay = getWindowManager().getDefaultDisplay();
            Point point = new Point();
            defaultDisplay.getSize(point);
            this.mCameraScreenNail.setPreviewSize(point.x, point.y);
        }
    }

    @Override // com.android.camera.storage.ImageSaver.ImageSaverCallback
    public boolean isActivityPaused() {
        return this.mActivityPaused;
    }

    public boolean isActivityStarted() {
        return this.mActivityStarted;
    }

    public boolean isActivityStopped() {
        return this.mActivityStopped;
    }

    public boolean isCameraAliveWhenResume() {
        boolean z;
        CameraHandlerThread.CookieStore cookieStore = CameraService.getCookieStore();
        if (cookieStore != null) {
            Iterator<CameraHandlerThread.Cookie> it = cookieStore.getCookies().iterator();
            while (true) {
                if (it.hasNext()) {
                    if (it.next().mCamera2Device != null) {
                        z = true;
                        break;
                    }
                } else {
                    break;
                }
            }
            Module module = this.mCurrentModule;
            boolean z2 = module == null && module.isCreated() && !this.mCurrentModule.isDeparted();
            Log.d(TAG, String.format("isCameraAliveWhenResume: mRealeaseByModule : %b , isModuleAlive: %b, isCameraDevicesAlive: %b", Boolean.valueOf(this.mReleaseByModule), Boolean.valueOf(z2), Boolean.valueOf(z)));
            return (!this.mReleaseByModule || z2) && z;
        }
        z = false;
        Module module2 = this.mCurrentModule;
        if (module2 == null) {
        }
        Log.d(TAG, String.format("isCameraAliveWhenResume: mRealeaseByModule : %b , isModuleAlive: %b, isCameraDevicesAlive: %b", Boolean.valueOf(this.mReleaseByModule), Boolean.valueOf(z2), Boolean.valueOf(z)));
        if (!this.mReleaseByModule) {
        }
    }

    public boolean isGalleryLocked() {
        return this.mGalleryLocked;
    }

    public boolean isGotoGallery() {
        return this.mJumpFlag == 1;
    }

    public boolean isGotoSettings() {
        return this.mJumpFlag == 2;
    }

    public boolean isJumpBack() {
        return this.mLastJumpFlag != 0;
    }

    public boolean isPostProcessing() {
        Module module = this.mCurrentModule;
        return module != null && module.isCreated() && this.mCurrentModule.isPostProcessing();
    }

    public boolean isPreviewThumbnail() {
        return this.mPreviewThumbnail;
    }

    /* access modifiers changed from: protected */
    public boolean isShowBottomIntentDone() {
        ModeProtocol.BaseDelegate baseDelegate;
        return (getCameraIntentManager().isImageCaptureIntent() || getCameraIntentManager().isVideoCaptureIntent()) && (baseDelegate = (ModeProtocol.BaseDelegate) ModeCoordinatorImpl.getInstance().getAttachProtocol(160)) != null && baseDelegate.getActiveFragment(R.id.bottom_action) == 4083;
    }

    public boolean isSwitchingModule() {
        return this.mIsSwitchingModule;
    }

    public void loadCameraSound(int i) {
        MiuiCameraSound miuiCameraSound = this.mCameraSound;
        if (miuiCameraSound != null) {
            miuiCameraSound.load(i);
        }
    }

    @UiThread
    public abstract void notifyOnFirstFrameArrived(int i);

    /* access modifiers changed from: protected */
    @Override // androidx.fragment.app.FragmentActivity
    public void onActivityResult(int i, int i2, Intent intent) {
        if (i != 35893) {
            this.mCurrentModule.onActivityResult(i, i2, intent);
        } else if (intent != null && intent.getData() != null) {
            addSecureUriIfNecessary(intent.getData());
        }
    }

    @Override // androidx.activity.ComponentActivity, androidx.fragment.app.FragmentActivity
    public void onCreate(Bundle bundle) {
        if (Util.isContentViewExtendToTopEdges()) {
            CompatibilityUtils.setCutoutModeShortEdges(getWindow());
        }
        super.onCreate(bundle);
        boolean z = true;
        setVolumeControlStream(1);
        this.mScreenHint = new ScreenHint(this);
        this.mThumbnailUpdater = new ThumbnailUpdater(this);
        this.mKeyguardManager = (KeyguardManager) getSystemService("keyguard");
        this.mStartFromKeyguard = getKeyguardFlag();
        if (this.mStartFromKeyguard) {
            this.mKeyguardSecureLocked = this.mKeyguardManager.isKeyguardSecure() && this.mKeyguardManager.isKeyguardLocked();
        }
        boolean z2 = getWindowManager().getDefaultDisplay().getState() == 2;
        if (!this.mStartFromKeyguard || (z2 && !getCameraIntentManager().isFromVolumeKey().booleanValue())) {
            z = false;
        }
        if (z) {
            Log.d(TAG, "onCreate: addFlag --> FLAG_TURN_SCREEN_ON");
            getWindow().addFlags(2097152);
        }
        this.mApplication.addActivity(this);
        this.mCameraBrightness = new CameraBrightness(this);
        this.mLocationManager = LocationManager.instance();
        this.mCloseActivityThread = new Thread(new Runnable() {
            /* class com.android.camera.ActivityBase.AnonymousClass1 */

            public void run() {
                ActivityBase activityBase = ActivityBase.this;
                activityBase.mApplication.closeAllActivitiesBut(activityBase);
            }
        });
        try {
            this.mCloseActivityThread.start();
        } catch (IllegalThreadStateException e2) {
            Log.e(TAG, e2.getMessage());
        }
    }

    /* access modifiers changed from: protected */
    @Override // androidx.fragment.app.FragmentActivity
    public void onDestroy() {
        CameraScreenNail cameraScreenNail;
        Bitmap lastFrameGaussianBitmap;
        if (!(DataRepository.dataItemFeature().ik() || (cameraScreenNail = this.mCameraScreenNail) == null || (lastFrameGaussianBitmap = cameraScreenNail.getLastFrameGaussianBitmap()) == null)) {
            Schedulers.io().scheduleDirect(new SaveGaussian2File(lastFrameGaussianBitmap));
        }
        PopupManager.removeInstance(this);
        this.mApplication.removeActivity(this);
        Disposable disposable = this.mTrackAppLunchDisposable;
        if (disposable != null) {
            disposable.dispose();
            this.mTrackAppLunchDisposable = null;
        }
        MiuiCameraSound miuiCameraSound = this.mCameraSound;
        if (miuiCameraSound != null) {
            miuiCameraSound.release();
            this.mCameraSound = null;
        }
        super.onDestroy();
    }

    public boolean onKeyDown(int i, KeyEvent keyEvent) {
        if (i != 84 || !keyEvent.isLongPress()) {
            return super.onKeyDown(i, keyEvent);
        }
        return true;
    }

    public void onLayoutChange(Rect rect) {
        this.mCameraScreenNail.setDisplayArea(rect);
        if (Util.getDisplayRotation(this) % 180 == 0) {
            this.mCameraScreenNail.setPreviewFrameLayoutSize(rect.width(), rect.height());
        } else {
            this.mCameraScreenNail.setPreviewFrameLayoutSize(rect.height(), rect.width());
        }
    }

    /* access modifiers changed from: protected */
    @Override // androidx.fragment.app.FragmentActivity
    public void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        checkGalleryLock();
        checkKeyguardFlag();
    }

    @Override // com.android.camera.storage.ImageSaver.ImageSaverCallback
    public void onNewUriArrived(Uri uri, String str) {
        Module module = this.mCurrentModule;
        if (module != null) {
            module.onNewUriArrived(uri, str);
        }
        if (uri != null) {
            addSecureUriIfNecessary(uri);
        }
    }

    /* access modifiers changed from: protected */
    @Override // androidx.fragment.app.FragmentActivity
    public void onPause() {
        super.onPause();
        getWindow().clearFlags(1024);
        if (this.mCameraScreenNail != null && !isShowBottomIntentDone()) {
            if ((DataRepository.dataItemFeature().cm() || CameraSettings.isCameraParallelProcessEnable()) && this.mJumpFlag == 1) {
                this.mCameraScreenNail.doPreviewGaussianForever();
            } else if (!DataRepository.dataItemFeature().ik() && ModuleManager.getActiveModuleIndex() != 254) {
                Log.d(TAG, "onPause: readLastFrameGaussian...");
                this.mCameraScreenNail.readLastFrameGaussian();
            }
        }
        Disposable disposable = this.mGLCoverDisposable;
        if (disposable != null) {
            disposable.dispose();
        }
        AlertDialog alertDialog = this.mErrorDialog;
        if (alertDialog != null) {
            alertDialog.dismiss();
        }
        pause();
        if (startFromKeyguard() && this.mIsFinishInKeyguard) {
            boolean isChangingConfigurations = isChangingConfigurations();
            Log.d(TAG, "onPause: clearFlag --> FLAG_TURN_SCREEN_ON and isChangingConfigurations is " + isChangingConfigurations);
            getWindow().clearFlags(2097152);
            if (this.mJumpFlag == 0 && !isChangingConfigurations) {
                finish();
            }
        }
        if (this.mJumpFlag == 0 && (startFromSecureKeyguard() || isGalleryLocked())) {
            this.mSecureUriList = null;
            this.mThumbnailUpdater.setThumbnail(null, true, false);
        } else if (this.mJumpFlag == 1) {
            clearNotification();
        }
        this.mHandler.removeMessages(1);
    }

    /* access modifiers changed from: protected */
    @Override // androidx.fragment.app.FragmentActivity
    public void onResume() {
        super.onResume();
        getWindow().addFlags(1024);
        this.mCameraErrorShown = false;
        this.mLastJumpFlag = this.mJumpFlag;
        this.mJumpFlag = 0;
        checkGalleryLock();
        checkKeyguardFlag();
        resume();
    }

    /* access modifiers changed from: protected */
    @Override // androidx.activity.ComponentActivity, androidx.fragment.app.FragmentActivity
    public void onSaveInstanceState(Bundle bundle) {
        super.onSaveInstanceState(bundle);
        Module module = this.mCurrentModule;
        if (module != null) {
            module.onSaveInstanceState(bundle);
        }
    }

    public boolean onSearchRequested() {
        return false;
    }

    /* access modifiers changed from: protected */
    @Override // androidx.fragment.app.FragmentActivity
    public void onStart() {
        super.onStart();
        this.mGLView.onResume();
    }

    /* access modifiers changed from: protected */
    @Override // androidx.fragment.app.FragmentActivity
    public void onStop() {
        super.onStop();
        V6CameraGLSurfaceView v6CameraGLSurfaceView = this.mGLView;
        if (v6CameraGLSurfaceView != null) {
            v6CameraGLSurfaceView.onPause();
        }
    }

    public void pause() {
        this.mHandler.removeCallbacksAndMessages(null);
        this.mCameraBrightness.onPause();
        Thread thread = this.mCloseActivityThread;
        if (thread != null) {
            try {
                thread.join();
            } catch (InterruptedException e2) {
                e2.printStackTrace();
            }
            this.mCloseActivityThread = null;
        }
        LocationManager locationManager = this.mLocationManager;
        if (locationManager != null) {
            locationManager.recordLocation(false);
        }
        if (this.mPreviewThumbnail) {
            this.mThumbnailUpdater.setThumbnail(null, false, false);
            this.mPreviewThumbnail = false;
            return;
        }
        ThumbnailUpdater thumbnailUpdater = this.mThumbnailUpdater;
        if (thumbnailUpdater != null) {
            thumbnailUpdater.saveThumbnailToFile();
            this.mThumbnailUpdater.cancelTask();
        }
    }

    public void playCameraSound(int i) {
        this.mCameraSound.playSound(i);
    }

    public void playCameraSound(int i, float f) {
        this.mCameraSound.playSound(i, f);
    }

    /* access modifiers changed from: protected */
    public void releaseCameraScreenNail() {
        Log.d(TAG, "releaseCameraScreenNail: ");
        CameraScreenNail cameraScreenNail = this.mCameraScreenNail;
        if (cameraScreenNail != null) {
            cameraScreenNail.releaseSurfaceTexture();
        }
        Module module = this.mCurrentModule;
        if (module != null) {
            module.setFrameAvailable(false);
        }
    }

    public void resetStartTime() {
        this.mAppStartTime = 0;
    }

    public void resume() {
        if (this.mCameraSound == null) {
            this.mCameraSound = new MiuiCameraSound(this);
        }
        this.mLocationManager.recordLocation(CameraSettings.isRecordLocation());
        this.mCameraBrightness.onResume();
    }

    public void setErrorDialog(AlertDialog alertDialog) {
        this.mErrorDialog = alertDialog;
    }

    public void setJumpFlag(int i) {
        this.mJumpFlag = i;
    }

    public void setPreviewThumbnail(boolean z) {
        this.mPreviewThumbnail = z;
    }

    public void setSwitchingModule(boolean z) {
        this.mIsSwitchingModule = z;
    }

    /* access modifiers changed from: protected */
    public void showBlurCover() {
        if (!isShowBottomIntentDone() && !isCameraAliveWhenResume() && !isPostProcessing() && !getCameraIntentManager().isFromScreenSlide().booleanValue() && !Util.isHasBackLightSensor()) {
            if (!DataRepository.dataItemFeature().ik()) {
                final long currentTimeMillis = System.currentTimeMillis();
                Bitmap bitmap = null;
                CameraScreenNail cameraScreenNail = this.mCameraScreenNail;
                if (cameraScreenNail != null) {
                    bitmap = cameraScreenNail.getLastFrameGaussianBitmap();
                }
                if (bitmap == null || bitmap.isRecycled()) {
                    this.mGLCoverDisposable = new Single<Bitmap>() {
                        /* class com.android.camera.ActivityBase.AnonymousClass2 */

                        /* access modifiers changed from: protected */
                        @Override // io.reactivex.Single
                        public void subscribeActual(SingleObserver<? super Bitmap> singleObserver) {
                            Bitmap decodeFile = BitmapFactory.decodeFile(new File(ActivityBase.this.getFilesDir(), Util.LAST_FRAME_GAUSSIAN_FILE_NAME).getAbsolutePath());
                            Log.d(ActivityBase.TAG, "showBlurCover: blur bitmap from user blur file!");
                            singleObserver.onSuccess(decodeFile);
                        }
                    }.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer<Bitmap>() {
                        /* class com.android.camera.ActivityBase.AnonymousClass3 */

                        public void accept(Bitmap bitmap) {
                            if (bitmap == null || bitmap.isRecycled()) {
                                ActivityBase.this.mGLCoverView.setVisibility(8);
                            } else {
                                ActivityBase.this.showBlurView(bitmap);
                            }
                            Log.d(ActivityBase.TAG, "showBlurCover: show... cost time = " + (System.currentTimeMillis() - currentTimeMillis) + d.H);
                        }
                    });
                } else {
                    Log.d(TAG, "showBlurCover: blur bitmap from memory!");
                    showBlurView(bitmap);
                }
            } else {
                Rect displayRect = Util.getDisplayRect();
                ViewGroup.MarginLayoutParams marginLayoutParams = (ViewGroup.MarginLayoutParams) this.mGLCoverView.getLayoutParams();
                int i = displayRect.top;
                marginLayoutParams.topMargin = i;
                marginLayoutParams.height = displayRect.bottom - i;
                this.mGLCoverView.setBackgroundColor(2130706432);
                this.mGLCoverView.setAlpha(1.0f);
                this.mGLCoverView.setVisibility(0);
            }
            this.mBlurStartTime = SystemClock.uptimeMillis();
        }
    }

    public void showDebugInfo(String str) {
        TextView textView = this.mDebugInfoView;
        if (textView != null) {
            textView.setText(str);
        }
    }

    public void showErrorDialog() {
        this.mCameraErrorShown = true;
    }

    public boolean startFromKeyguard() {
        return this.mStartFromKeyguard;
    }

    public boolean startFromSecureKeyguard() {
        return this.mKeyguardSecureLocked;
    }

    /* access modifiers changed from: protected */
    public void trackAppLunchTimeStart(boolean z) {
        ScenarioTrackUtil.trackAppLunchTimeStart(z);
        this.mTrackAppLunchDisposable = new Single<HashMap>() {
            /* class com.android.camera.ActivityBase.AnonymousClass5 */

            /* access modifiers changed from: protected */
            @Override // io.reactivex.Single
            public void subscribeActual(SingleObserver<? super HashMap> singleObserver) {
                HashMap hashMap = new HashMap();
                String execCommand = Util.execCommand("cat /dev/cpuset/camera-daemon/cpus", false);
                if (execCommand != null) {
                    hashMap.put("cpus", execCommand);
                    String execCommand2 = Util.execCommand("cat $(dirname $(grep -nir \"xo_therm\" /sys/class/thermal/thermal_zone*/type))/temp", false);
                    if (execCommand2 != null) {
                        hashMap.put("temperature", execCommand2);
                        String execCommand3 = Util.execCommand("cat /proc/meminfo|grep -E 'MemFree|MemAvailable'", true);
                        if (execCommand3 != null) {
                            String[] split = execCommand3.split("\r\n");
                            if (split.length == 2) {
                                for (String str : split) {
                                    String[] split2 = str.split(":");
                                    hashMap.put(split2[0], split2[1].replaceAll("\\D", ""));
                                }
                                singleObserver.onSuccess(hashMap);
                            }
                        }
                    }
                }
            }
        }.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer<HashMap>() {
            /* class com.android.camera.ActivityBase.AnonymousClass6 */

            public void accept(HashMap hashMap) {
                ActivityBase.this.mAppLunchMap = hashMap;
            }
        });
    }

    @Override // com.android.camera.module.loader.SurfaceStateListener
    public synchronized void updateSurfaceState(int i) {
        Log.d(TAG, "updateSurfaceState: " + i);
        this.mCurrentSurfaceState = i;
    }
}
