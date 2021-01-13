package com.android.camera.module;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.SurfaceTexture;
import android.hardware.camera2.CameraCaptureSession;
import android.hardware.camera2.CaptureResult;
import android.hardware.camera2.TotalCaptureResult;
import android.location.Location;
import android.media.Image;
import android.net.Uri;
import android.opengl.Matrix;
import android.os.AsyncTask;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Message;
import android.os.SystemClock;
import android.text.TextUtils;
import android.text.format.DateFormat;
import android.util.Range;
import android.util.Rational;
import android.util.Size;
import android.view.KeyEvent;
import android.view.Surface;
import android.view.View;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.UiThread;
import b.c.a.c;
import com.android.camera.AutoLockManager;
import com.android.camera.Camera;
import com.android.camera.CameraAppImpl;
import com.android.camera.CameraIntentManager;
import com.android.camera.CameraSettings;
import com.android.camera.CameraSize;
import com.android.camera.HybridZoomingSystem;
import com.android.camera.ImageHelper;
import com.android.camera.LocationManager;
import com.android.camera.PictureSizeManager;
import com.android.camera.R;
import com.android.camera.SensorStateManager;
import com.android.camera.SurfaceTextureScreenNail;
import com.android.camera.Thumbnail;
import com.android.camera.Util;
import com.android.camera.ambilight.AmbilightEngine;
import com.android.camera.ambilight.AmbilightRoi;
import com.android.camera.constant.DurationConstant;
import com.android.camera.constant.UpdateConstant;
import com.android.camera.data.DataRepository;
import com.android.camera.data.data.config.ComponentConfigAmbilight;
import com.android.camera.effect.EffectController;
import com.android.camera.effect.FilterInfo;
import com.android.camera.effect.MiYuvImage;
import com.android.camera.effect.draw_mode.DrawYuvAttribute;
import com.android.camera.fragment.BaseFragmentDelegate;
import com.android.camera.fragment.top.FragmentTopAlert;
import com.android.camera.log.Log;
import com.android.camera.module.encoder.LiveMediaRecorder;
import com.android.camera.module.loader.StartControl;
import com.android.camera.module.loader.camera2.FocusManager2;
import com.android.camera.module.loader.camera2.FocusTask;
import com.android.camera.protocol.ModeCoordinatorImpl;
import com.android.camera.protocol.ModeProtocol;
import com.android.camera.scene.FunctionMiAlgoASDEngine;
import com.android.camera.scene.MiAlgoAsdSceneProfile;
import com.android.camera.statistic.CameraStatUtils;
import com.android.camera.sticker.FileUtils;
import com.android.camera.storage.MediaProviderUtil;
import com.android.camera.storage.Storage;
import com.android.camera2.Camera2Proxy;
import com.android.camera2.CameraCapabilities;
import com.android.camera2.vendortag.struct.MarshalQueryableASDScene;
import com.android.gallery3d.exif.ExifHelper;
import com.android.gallery3d.ui.GLCanvasImpl;
import com.arcsoft.camera.utils.d;
import com.xiaomi.camera.core.BoostFrameworkImpl;
import com.xiaomi.camera.core.ParallelTaskData;
import com.xiaomi.camera.util.SystemProperties;
import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import io.reactivex.FlowableEmitter;
import io.reactivex.FlowableOnSubscribe;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import java.io.File;
import java.lang.ref.WeakReference;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import miui.text.ExtraTextUtils;

public class AmbilightModule extends BaseModule implements FocusManager2.Listener, Camera2Proxy.FocusCallback, Camera2Proxy.CameraPreviewCallback, ModeProtocol.CameraAction, ModeProtocol.AmbilightProtocol, SurfaceTextureScreenNail.ExternalFrameProcessor {
    public static final String ALGO_XML_FILENAME = "ambilight_parameter.xml";
    public static final String ALGO_XML_FOLDER_NAME = "ambilight";
    private static final boolean DEBUG = true;
    private static final int MAX_IMAGES_COUNT = 6;
    private static final int MIN_RECORD_TIME = 1800000;
    private static final int MIN_SHOOTING_TIME = 600;
    public static final float STAR_TRACK_VIDEO_SPEED = 0.0027777778f;
    public static final String TAG = "AmbilightModule";
    public static boolean mSupportAutoAe;
    private boolean m3ALocked;
    private final AtomicInteger mAcquiredCount = new AtomicInteger(0);
    private AmbilightEngine mAmbilightEngine;
    private int mAmbilightHeight;
    private AmbilightRoi mAmbilightRoi = null;
    private int mAmbilightWidth;
    private MarshalQueryableASDScene.ASDScene[] mAsdScenes;
    private BackgroundHandler mBackgroundHandler;
    private HandlerThread mBackgroundThread;
    private TotalCaptureResult mCaptureResult;
    private long mCaptureStartTime;
    private long mCaptureStopTime;
    private ComponentConfigAmbilight mComponentConfigAmbilight;
    private CountDownTimer mCountDownTimer;
    private Disposable mCountdownDisposable;
    private BoostFrameworkImpl mCpuBoost;
    private Rect mCropRegion;
    private int mDropCount = 0;
    private Long mExposureTime = 0L;
    private String mFileNameTemplate;
    private float mFocusDistance;
    private FocusManager2 mFocusManager;
    private MiYuvImage mImage = null;
    private boolean mInDebugMode = false;
    private volatile boolean mIsPrepareSaveTask = false;
    private boolean mIsRegisterSensorListener = false;
    private volatile boolean mIsShooting = false;
    private int mIso = 0;
    private int mJpegRotation;
    private LiveMediaRecorder mLiveMediaRecorder;
    private final LiveMediaRecorder.EncoderListener mMediaEncoderListener = new LiveMediaRecorder.EncoderListener(this);
    private Disposable mMetaDataDisposable;
    private FlowableEmitter<CaptureResult> mMetaDataFlowableEmitter;
    private int[] mMtkPerfList = {4194304, 2000000, 4194560, 2000000, 16777216, 0, 12582912, 0};
    private ArrayList<SaveVideoTask> mPendingSaveTaskList = new ArrayList<>();
    private ByteBuffer mPhotoBuffer = null;
    private int mPreviewHeight;
    private int mPreviewWidth;
    private int[] mQComPerfList = {1082146816, 4095, 1082147072, 4095, 1082130432, 4095, 1082130688, 4095, 1082130944, 4095, 1082147328, 4095};
    private int mReceivedPicturesCount;
    private long mRecordingStartTime;
    private final AtomicInteger mReleaseedCount = new AtomicInteger(0);
    private final Object mRenderLock = new Object();
    private long mRequestStartTime;
    private SaveOutputImageTask mSaveOutputImageTask;
    @AmbilightEngine.AmbilightSceneMode
    private int mSceneMode = 4;
    private SensorStateManager.SensorStateListener mSensorStateListener = new SensorStateManager.SensorStateAdapter() {
        /* class com.android.camera.module.AmbilightModule.AnonymousClass6 */

        @Override // com.android.camera.SensorStateManager.SensorStateListener, com.android.camera.SensorStateManager.SensorStateAdapter
        public boolean isWorking() {
            return AmbilightModule.this.isAlive() && AmbilightModule.this.getCameraState() != 0;
        }

        @Override // com.android.camera.SensorStateManager.SensorStateListener, com.android.camera.SensorStateManager.SensorStateAdapter
        public void onDeviceOrientationChanged(float f, boolean z) {
            AmbilightModule ambilightModule = AmbilightModule.this;
            if (z) {
                f = (float) ambilightModule.mOrientation;
            }
            ambilightModule.mDeviceRotation = f;
            if (AmbilightModule.this.getCameraState() != 3 || CameraSettings.isGradienterOn()) {
                EffectController instance = EffectController.getInstance();
                AmbilightModule ambilightModule2 = AmbilightModule.this;
                instance.setDeviceRotation(z, Util.getShootRotation(ambilightModule2.mActivity, ambilightModule2.mDeviceRotation));
            }
        }
    };
    private long mShootingStartTime;
    private ModeProtocol.TopAlert mTopAlert;
    private float[] mTransform = new float[16];
    private int mTranslateY;
    private int mTripodMode = 0;
    private ByteBuffer mUBuffer;
    private ByteBuffer mVBuffer;
    private Size mVideoSize = new Size(1440, 1920);
    private ByteBuffer mYBuffer = null;
    private DrawYuvAttribute mYuvAttribute;
    private DrawYuvAttribute mYuvAttributeForVideo;
    private float mZoom;
    private long prevOutputPTSUs = 0;

    public static class BackgroundHandler extends Handler {
        private static final int MSG_NEW_IMAGE_ARRIVE = 0;
        private static final int MSG_START_CAPTURE = 2;
        private static final int MSG_STOP_CAPTURE = 1;
        private boolean mCaptureStarted = false;
        private WeakReference<AmbilightModule> mModule;

        BackgroundHandler(Looper looper, AmbilightModule ambilightModule) {
            super(looper);
            this.mModule = new WeakReference<>(ambilightModule);
        }

        private void processImage(AmbilightModule ambilightModule, ByteBuffer byteBuffer, ByteBuffer byteBuffer2, ByteBuffer byteBuffer3) {
            ambilightModule.mAmbilightEngine.prepareData(byteBuffer, byteBuffer2, byteBuffer3);
            if (ambilightModule.mAmbilightEngine.frameProc() == 0) {
                ambilightModule.mHandler.obtainMessage(100).sendToTarget();
            }
        }

        public void handleMessage(@NonNull Message message) {
            int i = message.what;
            if (i == 0) {
                AmbilightModule ambilightModule = this.mModule.get();
                Image image = (Image) message.obj;
                if (!this.mCaptureStarted || ambilightModule == null) {
                    image.close();
                    return;
                }
                ByteBuffer buffer = image.getPlanes()[0].getBuffer();
                ByteBuffer buffer2 = c.qn() ? image.getPlanes()[1].getBuffer() : null;
                if (image.getTimestamp() == 1) {
                    String str = AmbilightModule.TAG;
                    Log.d(str, "stride: " + image.getPlanes()[0].getRowStride());
                    ambilightModule.mAmbilightEngine.initData(image.getPlanes()[0].getRowStride());
                }
                processImage(ambilightModule, buffer, buffer2, null);
                image.close();
                ambilightModule.mReleaseedCount.incrementAndGet();
                int i2 = ambilightModule.mAcquiredCount.get() - ambilightModule.mReleaseedCount.get();
                String str2 = AmbilightModule.TAG;
                Log.d(str2, "Wait process count: << " + i2);
            } else if (i == 1) {
                this.mCaptureStarted = false;
                removeCallbacksAndMessages(0);
                if (message.obj != null) {
                    AmbilightModule ambilightModule2 = this.mModule.get();
                    if (ambilightModule2 != null) {
                        ambilightModule2.mHandler.obtainMessage(106, ambilightModule2.mPhotoBuffer.array()).sendToTarget();
                    }
                    AmbilightEngine ambilightEngine = (AmbilightEngine) message.obj;
                    int postProc = ambilightEngine.postProc();
                    Log.d(AmbilightModule.TAG, "on PostProc done.");
                    if (!(postProc != 0 || ambilightModule2 == null || ambilightModule2.mPhotoBuffer == null)) {
                        ambilightModule2.mHandler.obtainMessage(101, ambilightModule2.mPhotoBuffer.array()).sendToTarget();
                    }
                    ambilightEngine.destroy();
                }
            } else if (i == 2) {
                this.mCaptureStarted = true;
            }
        }
    }

    /* access modifiers changed from: private */
    public static class MainHandler extends Handler {
        public static final int MSG_FRAME_PROC_DONE = 100;
        public static final int MSG_MAGIC_STAR_DONE = 102;
        public static final int MSG_ON_PAUSE = 105;
        public static final int MSG_POST_PROC_DONE = 101;
        public static final int MSG_POST_PROC_START = 106;
        public static final int MSG_TAKE_SHOT = 104;
        public static final int MSG_UPDATE_DEBUG_INFO = 103;
        private WeakReference<AmbilightModule> mModule;
        private boolean showPreview = false;

        public MainHandler(Looper looper, AmbilightModule ambilightModule) {
            super(looper);
            this.mModule = new WeakReference<>(ambilightModule);
        }

        private void handleDoneMsg(Message message) {
            byte[] bArr = (byte[]) message.obj;
            AmbilightModule ambilightModule = this.mModule.get();
            if (ambilightModule == null) {
                Log.e(AmbilightModule.TAG, "handleDoneMsg module is null!");
                return;
            }
            if (bArr != null) {
                ambilightModule.startSaveTask(bArr, ambilightModule.mAmbilightWidth, ambilightModule.mAmbilightHeight, ambilightModule.mCaptureResult);
            } else {
                ambilightModule.onSaveFinish();
            }
            this.showPreview = false;
            ambilightModule.mActivity.getCameraScreenNail().setExternalFrameProcessor(null);
        }

        public void handleMessage(Message message) {
            AmbilightModule ambilightModule = this.mModule.get();
            int i = message.what;
            if (i != 9) {
                if (i != 42) {
                    switch (i) {
                        case 100:
                            if (ambilightModule != null && ambilightModule.mIsShooting) {
                                ambilightModule.handleFrameProcessDone();
                                if (!this.showPreview) {
                                    ambilightModule.mActivity.getCameraScreenNail().setExternalFrameProcessor(ambilightModule);
                                    ambilightModule.onAmbilightPreviewAvailable();
                                    this.showPreview = true;
                                    return;
                                }
                                return;
                            }
                            return;
                        case 101:
                            handleDoneMsg(message);
                            return;
                        case 102:
                            if (ambilightModule != null) {
                                ambilightModule.playCameraSound(0);
                                ambilightModule.stopAmbilightShooting();
                            }
                            handleDoneMsg(message);
                            return;
                        case 103:
                            ModeProtocol.AmbilightSelector ambilightSelector = (ModeProtocol.AmbilightSelector) ModeCoordinatorImpl.getInstance().getAttachProtocol(421);
                            if (ambilightSelector != null) {
                                ambilightSelector.updateDebugInfo((String) message.obj);
                                return;
                            }
                            return;
                        case 104:
                            if (ambilightModule != null && !ambilightModule.mIsShooting) {
                                ambilightModule.takeShot();
                                return;
                            }
                            return;
                        case 105:
                            if (ambilightModule != null) {
                                ambilightModule.closeCamera();
                                ambilightModule.clearBuffer();
                                return;
                            }
                            return;
                        case 106:
                            if (ambilightModule != null && isInRendering()) {
                                ambilightModule.playCameraSound(0);
                                return;
                            }
                            return;
                        default:
                            return;
                    }
                } else if (ambilightModule != null) {
                    ambilightModule.updateRecordingTime();
                }
            } else if (ambilightModule != null) {
                ambilightModule.mMainProtocol.initializeFocusView(ambilightModule);
            }
        }

        public boolean isInRendering() {
            return this.showPreview;
        }
    }

    public static class PictureCallback extends Camera2Proxy.PictureCallbackWrapper {
        private WeakReference<AmbilightModule> ambilightModule;

        PictureCallback(AmbilightModule ambilightModule2) {
            this.ambilightModule = new WeakReference<>(ambilightModule2);
        }

        private void handleImage(Image image, TotalCaptureResult totalCaptureResult) {
            String str = AmbilightModule.TAG;
            Log.v(str, "onPictureTaken>>image=" + image);
            AmbilightModule ambilightModule2 = this.ambilightModule.get();
            if (image != null) {
                if (ambilightModule2 == null) {
                    image.close();
                    return;
                }
                AmbilightModule.access$408(ambilightModule2);
                ambilightModule2.mCaptureResult = totalCaptureResult;
                if (!ambilightModule2.mIsShooting) {
                    image.close();
                    return;
                }
                int i = ambilightModule2.mAcquiredCount.get() - ambilightModule2.mReleaseedCount.get();
                if (i > 4) {
                    image.close();
                    AmbilightModule.access$808(ambilightModule2);
                    if (ambilightModule2.mInDebugMode) {
                        Handler handler = ambilightModule2.mHandler;
                        handler.obtainMessage(103, "L: " + ambilightModule2.mDropCount).sendToTarget();
                    }
                    String str2 = AmbilightModule.TAG;
                    Log.e(str2, "Proc is busy, drop this frame, All drop count:" + ambilightModule2.mDropCount);
                    return;
                }
                ambilightModule2.mAcquiredCount.incrementAndGet();
                String str3 = AmbilightModule.TAG;
                Log.d(str3, "Wait process count: >> " + i);
                if (ambilightModule2.mBackgroundHandler != null) {
                    image.setTimestamp((long) ambilightModule2.mReceivedPicturesCount);
                    if (!ambilightModule2.mBackgroundHandler.sendMessage(ambilightModule2.mBackgroundHandler.obtainMessage(0, image))) {
                        image.close();
                        return;
                    }
                    return;
                }
                image.close();
            }
        }

        @Override // com.android.camera2.Camera2Proxy.PictureCallbackWrapper, com.android.camera2.Camera2Proxy.PictureCallback
        public void onPictureTakenFinished(boolean z) {
            String str = AmbilightModule.TAG;
            Log.d(str, "onPictureBurstFinished success = " + z);
        }

        @Override // com.android.camera2.Camera2Proxy.PictureCallbackWrapper, com.android.camera2.Camera2Proxy.PictureCallback
        public boolean onPictureTakenImageConsumed(Image image, TotalCaptureResult totalCaptureResult) {
            handleImage(image, totalCaptureResult);
            return true;
        }
    }

    public static class SaveOutputImageTask extends AsyncTask<Void, Integer, Integer> {
        private WeakReference<Camera> mActivityRef;
        private SaveStateCallback mCallback;
        private TotalCaptureResult mCaptureResult;
        private final long mCaptureTime;
        private final String mFileName;
        private int mHeight;
        private byte[] mNv21Data;
        private int mOrientation;
        private int mWidth;

        public SaveOutputImageTask(@Nullable Camera camera, String str, byte[] bArr, int i, int i2, int i3, long j, TotalCaptureResult totalCaptureResult, SaveStateCallback saveStateCallback) {
            this.mFileName = str;
            this.mNv21Data = bArr;
            this.mWidth = i;
            this.mHeight = i2;
            this.mOrientation = i3;
            this.mCallback = saveStateCallback;
            this.mCaptureResult = totalCaptureResult;
            this.mActivityRef = new WeakReference<>(camera);
            this.mCaptureTime = j;
        }

        private void addImageAsApplication(String str, String str2, byte[] bArr, int i, int i2, int i3, long j) {
            Uri uri;
            Location location;
            long currentTimeMillis = System.currentTimeMillis();
            Location currentLocation = LocationManager.instance().getCurrentLocation();
            if (bArr != null) {
                uri = Storage.addImage(CameraAppImpl.getAndroidContext(), str2, currentTimeMillis, currentLocation, i3, bArr, false, i, i2, false, false, false, true, false, "", null);
            } else {
                TotalCaptureResult totalCaptureResult = this.mCaptureResult;
                if (totalCaptureResult != null) {
                    location = currentLocation;
                    Util.appendCaptureResultToExif(str, i, i2, i3, currentTimeMillis, currentLocation, j, totalCaptureResult);
                } else {
                    location = currentLocation;
                    ExifHelper.writeExifByFilePath(str, i3, location, currentTimeMillis);
                }
                uri = Storage.addImageForGroupOrPanorama(CameraAppImpl.getAndroidContext(), str, i3, currentTimeMillis, location, i, i2);
            }
            if (uri == null) {
                String str3 = AmbilightModule.TAG;
                Log.w(str3, "insert MediaProvider failed, attempt to find uri by path, " + str);
                uri = MediaProviderUtil.getContentUriFromPath(CameraAppImpl.getAndroidContext(), str);
            }
            String str4 = AmbilightModule.TAG;
            Log.d(str4, "addImageAsApplication uri = " + uri + ", path = " + str);
            Camera camera = this.mActivityRef.get();
            if (camera != null && uri != null) {
                camera.onNewUriArrived(uri, str2);
                Thumbnail createThumbnailFromUri = Thumbnail.createThumbnailFromUri(camera, uri, false);
                String str5 = AmbilightModule.TAG;
                Log.d(str5, "addImageAsApplication Thumbnail = " + createThumbnailFromUri);
                Util.broadcastNewPicture(camera, uri);
                camera.getThumbnailUpdater().setThumbnail(createThumbnailFromUri, true, false);
            }
        }

        /* access modifiers changed from: protected */
        public Integer doInBackground(Void... voidArr) {
            byte[] encodeNv21ToJpeg = ImageHelper.encodeNv21ToJpeg(this.mNv21Data, this.mWidth, this.mHeight, CameraSettings.getEncodingQuality(false).toInteger(false));
            this.mNv21Data = null;
            if (encodeNv21ToJpeg == null) {
                Log.w(AmbilightModule.TAG, "jpegData is null, can't save");
                return null;
            }
            String generateFilepath4Image = Storage.generateFilepath4Image(this.mFileName, false);
            if (Storage.isUseDocumentMode()) {
                addImageAsApplication(generateFilepath4Image, this.mFileName, encodeNv21ToJpeg, this.mWidth, this.mHeight, this.mOrientation, this.mCaptureTime);
            } else {
                d.b(generateFilepath4Image, encodeNv21ToJpeg);
                addImageAsApplication(generateFilepath4Image, this.mFileName, null, this.mWidth, this.mHeight, this.mOrientation, this.mCaptureTime);
            }
            return null;
        }

        /* access modifiers changed from: protected */
        public void onPostExecute(Integer num) {
            super.onPostExecute((Object) num);
            SaveStateCallback saveStateCallback = this.mCallback;
            if (saveStateCallback != null) {
                saveStateCallback.onSaveCompleted();
            }
        }

        /* access modifiers changed from: protected */
        public void onPreExecute() {
            super.onPreExecute();
            Log.w(AmbilightModule.TAG, "onPreExecute");
        }
    }

    public interface SaveStateCallback {
        void onSaveCompleted();
    }

    private static final class SaveVideoTask {
        public ContentValues contentValues;
        public String videoPath;

        public SaveVideoTask(String str, ContentValues contentValues2) {
            this.videoPath = str;
            this.contentValues = contentValues2;
        }
    }

    static /* synthetic */ int access$408(AmbilightModule ambilightModule) {
        int i = ambilightModule.mReceivedPicturesCount;
        ambilightModule.mReceivedPicturesCount = i + 1;
        return i;
    }

    static /* synthetic */ int access$808(AmbilightModule ambilightModule) {
        int i = ambilightModule.mDropCount;
        ambilightModule.mDropCount = i + 1;
        return i;
    }

    private void ambilightEngineInit() {
        startCpuBoost();
        this.mAmbilightEngine.init(this.mSceneMode, this.mAmbilightWidth, this.mAmbilightHeight, this.mTripodMode);
        if (this.mPhotoBuffer == null) {
            this.mPhotoBuffer = ByteBuffer.allocateDirect(((this.mAmbilightWidth * this.mAmbilightHeight) * 3) / 2);
        }
        this.mAmbilightEngine.prepare(this.mPhotoBuffer, this.mPhotoBuffer.arrayOffset());
        if (this.mZoom != 1.0f) {
            AmbilightEngine ambilightEngine = this.mAmbilightEngine;
            Rect rect = this.mCropRegion;
            ambilightEngine.setZoomRoi(rect.left, rect.top, rect.right, rect.bottom);
        }
        this.mPreviewWidth = this.mAmbilightEngine.getPreviewWidth();
        this.mPreviewHeight = this.mAmbilightEngine.getPreviewHeight();
        Log.d(TAG, "update preview size [%d, %d]", Integer.valueOf(this.mPreviewWidth), Integer.valueOf(this.mPreviewHeight));
        if (this.mSceneMode == 2) {
            int i = this.mJpegRotation;
            if (i == 0 || i == 180) {
                this.mAmbilightEngine.setFlipMode(1);
            } else {
                this.mAmbilightEngine.setFlipMode(2);
            }
        }
        updateBuffers(this.mPreviewWidth * this.mPreviewHeight);
        this.mAcquiredCount.set(0);
        this.mReleaseedCount.set(0);
        this.mReceivedPicturesCount = 0;
    }

    private boolean checkShutterCondition() {
        if (isBlockSnap() || isIgnoreTouchEvent()) {
            String str = TAG;
            Log.w(str, "checkShutterCondition: blockSnap=" + isBlockSnap() + " ignoreTouchEvent=" + isIgnoreTouchEvent());
            return false;
        } else if (Storage.isLowStorageAtLastPoint()) {
            Log.w(TAG, "checkShutterCondition: low storage");
            return false;
        } else if (!isFrontCamera() || !this.mActivity.isScreenSlideOff()) {
            ModeProtocol.BackStack backStack = (ModeProtocol.BackStack) ModeCoordinatorImpl.getInstance().getAttachProtocol(171);
            if (backStack == null) {
                return true;
            }
            backStack.handleBackStackFromShutter();
            return true;
        } else {
            Log.w(TAG, "checkShutterCondition: screen is slide off");
            return false;
        }
    }

    /* access modifiers changed from: private */
    /* access modifiers changed from: public */
    private void clearBuffer() {
        this.mYBuffer = null;
        this.mUBuffer = null;
        this.mVBuffer = null;
        this.mPhotoBuffer = null;
        MiYuvImage miYuvImage = this.mImage;
        if (miYuvImage != null) {
            miYuvImage.updateData(null, null, null);
            this.mImage = null;
        }
        LiveMediaRecorder liveMediaRecorder = this.mLiveMediaRecorder;
        if (liveMediaRecorder != null) {
            liveMediaRecorder.release();
        }
    }

    private void doLaterReleaseIfNeed() {
        Camera camera = this.mActivity;
        if (camera != null && camera.isActivityPaused()) {
            this.mActivity.pause();
            this.mActivity.releaseAll(true, true);
        }
    }

    private File getAlgoXmlFile() {
        String str;
        File filesDir = CameraAppImpl.getAndroidContext().getFilesDir();
        if (filesDir != null) {
            str = filesDir.getAbsolutePath() + File.separator + ALGO_XML_FOLDER_NAME;
        } else {
            str = null;
        }
        return new File(str, ALGO_XML_FILENAME);
    }

    private void handleFoucsStateChange(FocusTask focusTask) {
        String str = focusTask.isFocusing() ? "onAutoFocusMoving start" : null;
        if (Util.sIsDumpLog && str != null) {
            Log.v(TAG, str);
        }
        if (this.m3ALocked) {
            return;
        }
        if (getCameraState() != 3 || focusTask.getFocusTrigger() == 3) {
            this.mFocusManager.onFocusResult(focusTask);
        }
    }

    private void initMetaParser() {
        this.mMetaDataDisposable = Flowable.create(new FlowableOnSubscribe<CaptureResult>() {
            /* class com.android.camera.module.AmbilightModule.AnonymousClass1 */

            @Override // io.reactivex.FlowableOnSubscribe
            public void subscribe(FlowableEmitter<CaptureResult> flowableEmitter) throws Exception {
                AmbilightModule.this.mMetaDataFlowableEmitter = flowableEmitter;
            }
        }, BackpressureStrategy.DROP).map(new FunctionMiAlgoASDEngine(this)).subscribe();
    }

    private void initializeFocusManager() {
        this.mFocusManager = new FocusManager2(this.mCameraCapabilities, this, isFrontCamera(), this.mActivity.getMainLooper());
        Rect renderRect = this.mActivity.getCameraScreenNail() != null ? this.mActivity.getCameraScreenNail().getRenderRect() : null;
        if (renderRect == null || renderRect.width() <= 0) {
            this.mFocusManager.setRenderSize(Util.sWindowWidth, Util.sWindowHeight);
            this.mFocusManager.setPreviewSize(Util.sWindowWidth, Util.sWindowHeight);
            return;
        }
        this.mFocusManager.setRenderSize(this.mActivity.getCameraScreenNail().getRenderWidth(), this.mActivity.getCameraScreenNail().getRenderHeight());
        this.mFocusManager.setPreviewSize(renderRect.width(), renderRect.height());
    }

    private boolean isCannotGotoGallery() {
        return this.mPaused || this.isZooming || getCameraState() == 0 || isInCountDown();
    }

    private boolean isInCountDown() {
        Disposable disposable = this.mCountdownDisposable;
        return disposable != null && !disposable.isDisposed();
    }

    private boolean isProcessingSaveTask() {
        SaveOutputImageTask saveOutputImageTask = this.mSaveOutputImageTask;
        return (saveOutputImageTask == null || saveOutputImageTask.getStatus() == AsyncTask.Status.FINISHED) ? false : true;
    }

    private boolean isShootingTooShort() {
        return !((MainHandler) this.mHandler).isInRendering() || SystemClock.elapsedRealtime() - this.mShootingStartTime < 600;
    }

    /* access modifiers changed from: private */
    /* access modifiers changed from: public */
    private void onAmbilightPreviewAvailable() {
        ModeProtocol.RecordState recordState = (ModeProtocol.RecordState) ModeCoordinatorImpl.getInstance().getAttachProtocol(212);
        if (recordState != null) {
            recordState.onResume();
        }
    }

    /* access modifiers changed from: private */
    /* access modifiers changed from: public */
    private void onSaveFinish() {
        Log.d(TAG, "onSaveFinish E");
        stopCpuBoost();
        updateRecordingTimeStyle(true);
        String str = TAG;
        Log.d(str, "Photo generation cost = " + (System.currentTimeMillis() - this.mCaptureStopTime) + com.xiaomi.stat.d.H);
        if (isAlive() && this.mCamera2Device != null) {
            enableCameraControls(true);
            if (this.mAeLockSupported) {
                this.mCamera2Device.setAELock(false);
            }
            if (this.mAwbLockSupported) {
                this.mCamera2Device.setAWBLock(false);
            }
            this.mCamera2Device.setFocusMode(4);
            ModeProtocol.RecordState recordState = (ModeProtocol.RecordState) ModeCoordinatorImpl.getInstance().getAttachProtocol(212);
            if (recordState != null) {
                recordState.onPostSavingFinish();
            }
            this.mIsPrepareSaveTask = false;
            Log.d(TAG, "onSaveFinish X");
        }
    }

    private void registerSensorListener() {
        if (!this.mIsRegisterSensorListener) {
            this.mIsRegisterSensorListener = true;
            this.mActivity.getSensorStateManager().setSensorStateListener(this.mSensorStateListener);
        }
    }

    private void resetParameters() {
        this.mCamera2Device.setExposureTime(0);
        this.mCamera2Device.setExposureCompensation(0);
        this.mCamera2Device.setISO(0);
        int i = this.mSceneMode;
        if (i == 5 || i == 3) {
            this.mCamera2Device.setFpsRange(new Range<>(0, 30));
        } else {
            this.mCamera2Device.setFpsRange(new Range<>(5, 30));
        }
        updateAutoAeParameters(true);
        unlockAEAF();
        this.mCamera2Device.setFocusMode(4);
        this.mCamera2Device.cancelFocus(this.mModuleIndex);
    }

    private boolean shouldHideTimeLabel() {
        int i = this.mSceneMode;
        return i == 4 || (mSupportAutoAe && i == 5);
    }

    private void startAmbilightShooting() {
        ModeProtocol.RecordState recordState = (ModeProtocol.RecordState) ModeCoordinatorImpl.getInstance().getAttachProtocol(212);
        if (recordState == null) {
            Log.w(TAG, "startAmbilightShooting: recordState is null");
            return;
        }
        recordState.onPrepare();
        this.mRequestStartTime = System.currentTimeMillis();
        this.prevOutputPTSUs = 0;
        if (this.mSceneMode == 3) {
            startVideoRecording();
        }
        ModeProtocol.TopAlert topAlert = this.mTopAlert;
        boolean z = false;
        if (topAlert != null) {
            topAlert.setShow(true);
            this.mTopAlert.clearAllTipsState();
            this.mTopAlert.alertAiDetectTipHint(0, R.string.super_night_toast, -1);
        }
        ModeProtocol.TopAlert topAlert2 = this.mTopAlert;
        if (topAlert2 != null) {
            topAlert2.setRecordingTimeState(1, shouldHideTimeLabel());
        }
        updateSpeechShutter(false);
        if (this.mInDebugMode) {
            this.mHandler.obtainMessage(103, "").sendToTarget();
        }
        this.mJpegRotation = Util.getJpegRotation(this.mBogusCameraId, this.mOrientation);
        Log.v(TAG, "startAmbilightShooting mJpegRotation = " + this.mJpegRotation);
        if (this.mSceneMode != 5) {
            ambilightEngineInit();
        }
        this.mIsShooting = true;
        updateExposureParameters();
        updateAutoAeParameters(false);
        this.mCamera2Device.setFocusMode(0);
        Log.d(TAG, "focus distance:" + this.mFocusDistance);
        int i = this.mSceneMode;
        if (i == 5 || i == 3) {
            this.mCamera2Device.setFocusDistance(0.0f);
        } else {
            this.mCamera2Device.setFocusDistance(this.mFocusDistance);
        }
        this.mCamera2Device.setGpsLocation(LocationManager.instance().getCurrentLocation());
        this.mCamera2Device.setNeedPausePreview(true);
        if (this.mIso != 0 && this.mAwbLockSupported) {
            this.mCamera2Device.setAWBLock(true);
        }
        if (this.mSceneMode == 5) {
            this.mCamera2Device.takePicture(new Camera2Proxy.PictureCallbackWrapper() {
                /* class com.android.camera.module.AmbilightModule.AnonymousClass4 */

                @Override // com.android.camera2.Camera2Proxy.PictureCallbackWrapper, com.android.camera2.Camera2Proxy.PictureCallback
                public ParallelTaskData onCaptureStart(ParallelTaskData parallelTaskData, CameraSize cameraSize, boolean z, boolean z2, boolean z3, boolean z4) {
                    return parallelTaskData;
                }

                @Override // com.android.camera2.Camera2Proxy.PictureCallbackWrapper, com.android.camera2.Camera2Proxy.PictureCallback
                public void onPictureTaken(byte[] bArr, CaptureResult captureResult) {
                    super.onPictureTaken(bArr, captureResult);
                }

                @Override // com.android.camera2.Camera2Proxy.PictureCallbackWrapper, com.android.camera2.Camera2Proxy.PictureCallback
                public boolean onPictureTakenImageConsumed(Image image, TotalCaptureResult totalCaptureResult) {
                    if (image == null) {
                        return true;
                    }
                    byte[] dataFromImage = d.getDataFromImage(image, 2);
                    image.close();
                    AmbilightModule.this.mCaptureResult = totalCaptureResult;
                    AmbilightModule.this.mHandler.obtainMessage(102, dataFromImage).sendToTarget();
                    return true;
                }
            }, this.mActivity.getImageSaver());
        } else {
            this.mCamera2Device.setEnableZsl(isZslPreferred());
            this.mCamera2Device.setPictureMaxImages(6);
            this.mCamera2Device.setZoomRatioForCapture(1.0f);
            this.mCamera2Device.setShotType(12);
            if (this.mIso == 0 && (!mSupportAutoAe || this.mSceneMode != 3)) {
                z = true;
            }
            this.mCamera2Device.setMFLockAfAe(z);
            this.mCamera2Device.takePicture(new PictureCallback(this), null);
        }
        recordState.onStart();
        keepScreenOn();
        AutoLockManager.getInstance(this.mActivity).removeMessage();
        BackgroundHandler backgroundHandler = this.mBackgroundHandler;
        if (backgroundHandler != null) {
            backgroundHandler.obtainMessage(2, this.mAmbilightEngine).sendToTarget();
        }
    }

    private void startBackgroundThread() {
        this.mBackgroundThread = new HandlerThread("AmbilightBackground");
        this.mBackgroundThread.start();
        this.mBackgroundHandler = new BackgroundHandler(this.mBackgroundThread.getLooper(), this);
    }

    private void startCount(final int i, int i2) {
        if (checkShutterCondition()) {
            setTriggerMode(i2);
            tryRemoveCountDownMessage();
            String str = TAG;
            Log.d(str, "startCount: " + i);
            Observable.interval(1, TimeUnit.SECONDS).take((long) i).observeOn(AndroidSchedulers.mainThread()).subscribe(new Observer<Long>() {
                /* class com.android.camera.module.AmbilightModule.AnonymousClass2 */

                public /* synthetic */ void _c() {
                    AmbilightModule.this.mTopAlert.hideAlert();
                }

                @Override // io.reactivex.Observer
                public void onComplete() {
                    Camera camera;
                    if (AmbilightModule.this.isAlive() && (camera = AmbilightModule.this.mActivity) != null && !camera.isActivityPaused()) {
                        AmbilightModule.this.onShutterButtonFocus(true, 3);
                        Handler handler = AmbilightModule.this.mHandler;
                        if (handler != null) {
                            handler.obtainMessage(104).sendToTarget();
                        }
                        AmbilightModule.this.onShutterButtonFocus(false, 0);
                    }
                    AmbilightModule.this.tryRemoveCountDownMessage();
                }

                @Override // io.reactivex.Observer
                public void onError(Throwable th) {
                }

                public void onNext(Long l) {
                    int intValue = i - (l.intValue() + 1);
                    if (intValue > 0) {
                        AmbilightModule.this.playCameraSound(5);
                        AmbilightModule.this.mTopAlert.showDelayNumber(intValue);
                    }
                }

                @Override // io.reactivex.Observer
                public void onSubscribe(Disposable disposable) {
                    AmbilightModule.this.mCountdownDisposable = disposable;
                    AmbilightModule.this.mMainProtocol.clearFocusView(7);
                    AmbilightModule.this.playCameraSound(7);
                    if (AmbilightModule.this.mTopAlert != null) {
                        AndroidSchedulers.mainThread().scheduleDirect(new RunnableC0069a(this), 120, TimeUnit.MILLISECONDS);
                        AmbilightModule.this.mTopAlert.showDelayNumber(i);
                    }
                }
            });
        }
    }

    private void startCpuBoost() {
        if (this.mCpuBoost == null) {
            this.mCpuBoost = new BoostFrameworkImpl();
            this.mCpuBoost.setDefaultParams(c.isMTKPlatform() ? this.mMtkPerfList : this.mQComPerfList);
        }
        this.mCpuBoost.startBoost();
    }

    private void startPreviewSession() {
        Log.d(TAG, "startPreviewSession(): ");
        if (this.mCamera2Device == null) {
            Log.e(TAG, "startPreview: camera has been closed");
            return;
        }
        int i = this.mSceneMode;
        if (i == 5 || i == 3) {
            this.mCamera2Device.setFpsRange(new Range<>(0, 30));
        }
        this.mCamera2Device.setFocusCallback(this);
        this.mCamera2Device.setDualCamWaterMarkEnable(false);
        this.mCamera2Device.setErrorCallback(this.mErrorCallback);
        this.mCamera2Device.setPreviewSize(this.mPreviewSize);
        this.mCamera2Device.setAlgorithmPreviewSize(this.mPreviewSize);
        this.mCamera2Device.setPictureSize(this.mPictureSize);
        this.mCamera2Device.setPictureMaxImages(6);
        this.mCamera2Device.setPictureFormat(35);
        this.mSurfaceCreatedTimestamp = this.mActivity.getCameraScreenNail().getSurfaceCreatedTimestamp();
        this.mCamera2Device.startPreviewSession(new Surface(this.mActivity.getCameraScreenNail().getSurfaceTexture()), 0, false, false, null, getOperatingMode(), false, this);
    }

    /* access modifiers changed from: private */
    /* access modifiers changed from: public */
    @UiThread
    private void startSaveTask(byte[] bArr, int i, int i2, TotalCaptureResult totalCaptureResult) {
        Log.v(TAG, "startSaveTask stitchResult ");
        keepScreenOnAwhile();
        String charSequence = DateFormat.format(this.mFileNameTemplate, System.currentTimeMillis()).toString();
        long j = this.mCaptureStopTime - this.mCaptureStartTime;
        CameraStatUtils.trackAmbilightCapture(this.mSceneMode, j);
        if (this.mSceneMode == 5) {
            j = 0;
        }
        this.mSaveOutputImageTask = new SaveOutputImageTask(this.mActivity, charSequence, bArr, i, i2, this.mJpegRotation, j, totalCaptureResult, new C0071c(this));
        this.mSaveOutputImageTask.execute(new Void[0]);
    }

    /* access modifiers changed from: private */
    /* access modifiers changed from: public */
    private void stopAmbilightShooting() {
        LiveMediaRecorder liveMediaRecorder;
        if (this.mCamera2Device != null) {
            updateSpeechShutter(true);
            this.mCaptureStopTime = System.currentTimeMillis();
            if (!this.mIsShooting) {
                Log.w(TAG, "stopAmbilightShooting return, is not shooting");
                return;
            }
            ModeProtocol.TopAlert topAlert = this.mTopAlert;
            if (topAlert != null) {
                topAlert.setRecordingTimeState(2);
            }
            Log.d(TAG, "stopAmbilightShooting");
            CountDownTimer countDownTimer = this.mCountDownTimer;
            if (countDownTimer != null) {
                countDownTimer.cancel();
            }
            if (this.mMediaRecorderRecording && (liveMediaRecorder = this.mLiveMediaRecorder) != null) {
                liveMediaRecorder.stopRecorder(this.mRecordingStartTime, this.mCaptureStopTime - this.mCaptureStartTime < 1800000);
            }
            this.mMediaRecorderRecording = false;
            this.mIsPrepareSaveTask = true;
            this.mIsShooting = false;
            this.mCamera2Device.captureAbortBurst();
            resetParameters();
            if (this.mSceneMode != 5) {
                this.mBackgroundHandler.obtainMessage(1, this.mAmbilightEngine).sendToTarget();
            }
            ModeProtocol.RecordState recordState = (ModeProtocol.RecordState) ModeCoordinatorImpl.getInstance().getAttachProtocol(212);
            if (recordState == null) {
                Log.w(TAG, "onPreExecute recordState is null");
                return;
            }
            recordState.onFinish();
            recordState.onPostSavingStart();
            int i = this.mSceneMode;
            if (i == 2 || i == 3 || i == 0 || i == 5 || i == 4 || i == 1) {
                this.mActivity.onModeSelected(StartControl.create(187).setViewConfigType(2).setNeedBlurAnimation(true).setNeedReConfigureCamera(true));
            }
        }
    }

    private void stopBackgroundThread() {
        HandlerThread handlerThread = this.mBackgroundThread;
        if (handlerThread != null) {
            handlerThread.quitSafely();
            try {
                this.mBackgroundThread.join();
            } catch (InterruptedException e2) {
                Log.e(TAG, e2.getMessage());
            }
            this.mBackgroundThread = null;
            this.mBackgroundHandler = null;
        }
    }

    private void stopCpuBoost() {
        BoostFrameworkImpl boostFrameworkImpl = this.mCpuBoost;
        if (boostFrameworkImpl != null) {
            boostFrameworkImpl.stopBoost();
            this.mCpuBoost = null;
        }
    }

    /* access modifiers changed from: private */
    /* access modifiers changed from: public */
    private void takeShot() {
        if (this.mCamera2Device != null) {
            this.mActivity.getScreenHint().updateHint();
            if (Storage.isLowStorageAtLastPoint()) {
                ((ModeProtocol.RecordState) ModeCoordinatorImpl.getInstance().getAttachProtocol(212)).onFailed();
                Log.w(TAG, "onShutterButtonClick return, isLowStorageAtLastPoint");
                return;
            }
            startAmbilightShooting();
            this.mRecordingStartTime = SystemClock.uptimeMillis();
            updateRecordingTimeStyle(false);
            updateRecordingTime();
            this.mShootingStartTime = SystemClock.elapsedRealtime();
            this.mCaptureStartTime = System.currentTimeMillis();
        }
    }

    private void unregisterSensor() {
        if (CameraSettings.isGradienterOn()) {
            this.mActivity.getSensorStateManager().setGradienterEnabled(false);
        }
        if (this.mIsRegisterSensorListener) {
            this.mIsRegisterSensorListener = false;
            this.mActivity.getSensorStateManager().setSensorStateListener(null);
        }
    }

    private void updateASD() {
        Camera2Proxy camera2Proxy = this.mCamera2Device;
        if (camera2Proxy != null) {
            camera2Proxy.setASDEnable(true);
            Log.d(TAG, "updateASD call setASDEnable with true");
        }
    }

    private boolean updateAutoAeParameters(boolean z) {
        if (this.mCamera2Device == null) {
            return false;
        }
        int i = this.mInDebugMode ? SystemProperties.getInt("ambi_auto_ae_target", 40) : 40;
        int i2 = this.mSceneMode;
        if (i2 == 5) {
            this.mCamera2Device.setAmbilightMode(1);
            Camera2Proxy camera2Proxy = this.mCamera2Device;
            if (z) {
                i = 0;
            }
            camera2Proxy.setAmbilightAeTarget(i);
            return true;
        } else if (i2 == 3) {
            this.mCamera2Device.setAmbilightMode(2);
            Camera2Proxy camera2Proxy2 = this.mCamera2Device;
            if (z) {
                i = 0;
            }
            camera2Proxy2.setAmbilightAeTarget(i);
            return true;
        } else {
            this.mCamera2Device.setAmbilightMode(0);
            return false;
        }
    }

    private void updateExposureParameters() {
        int i;
        long j;
        int i2;
        float f;
        this.mIso = 0;
        int i3 = this.mSceneMode;
        if (i3 != 0) {
            if (i3 != 1) {
                if (i3 == 2) {
                    long j2 = c.isMTKPlatform() ? 500000000 : 333333333;
                    this.mIso = 50;
                    i2 = 4;
                    j = j2;
                } else if (i3 == 3) {
                    i2 = 6;
                    if (!mSupportAutoAe) {
                        j = ((long) getDuration()) * ExtraTextUtils.MB;
                        this.mIso = 800;
                    } else {
                        i = 0;
                    }
                } else if (i3 == 4) {
                    i = 0;
                    i2 = 1;
                } else if (i3 != 5) {
                    i2 = 0;
                    i = 0;
                } else {
                    if (!mSupportAutoAe) {
                        long duration = ((long) getDuration()) * ExtraTextUtils.MB;
                        this.mIso = 800;
                        i = 0;
                        j = duration;
                    } else {
                        i = 0;
                        j = 0;
                    }
                    f = 0.0f;
                    i2 = 5;
                }
                i = 0;
                f = 0.0f;
            } else {
                i = 15;
                i2 = 3;
            }
            j = 0;
            f = 0.0f;
        } else {
            j = 50000000;
            i = 0;
            f = -1.5f;
            i2 = 2;
        }
        if (this.mInDebugMode) {
            String str = SystemProperties.get("ambi_ev_" + i2);
            if (!TextUtils.isEmpty(str)) {
                try {
                    f = Float.parseFloat(str);
                    Log.e(TAG, "set ev:" + f);
                } catch (NumberFormatException unused) {
                    Log.e(TAG, "wrong ev:" + str);
                }
            }
            int i4 = SystemProperties.getInt("ambi_fps_" + i2, -1);
            if (i4 != -1) {
                i = i4;
            }
            int i5 = SystemProperties.getInt("ambi_ms_" + i2, -1);
            if (i5 != -1) {
                j = ((long) i5) * ExtraTextUtils.MB;
            }
            int i6 = SystemProperties.getInt("ambi_iso_" + i2, -1);
            if (i6 != -1) {
                this.mIso = i6;
            }
        }
        if (f != 0.0f) {
            Rational exposureCompensationRational = this.mCameraCapabilities.getExposureCompensationRational();
            this.mCamera2Device.setExposureCompensation((int) ((f * ((float) exposureCompensationRational.getDenominator())) / ((float) exposureCompensationRational.getNumerator())));
        }
        int i7 = this.mIso;
        if (i7 != 0) {
            this.mCamera2Device.setISO(i7);
        }
        if (j != 0) {
            this.mCamera2Device.setExposureTime(j);
        }
        if (i == 0) {
            return;
        }
        if (c.isMTKPlatform()) {
            this.mCamera2Device.setFpsRange(new Range<>(5, Integer.valueOf(i)));
        } else {
            this.mCamera2Device.setFpsRange(new Range<>(Integer.valueOf(i), Integer.valueOf(i)));
        }
    }

    private void updateFilter() {
        int shaderEffect = CameraSettings.getShaderEffect();
        String str = TAG;
        Log.v(str, "updateFilter: 0x" + Integer.toHexString(shaderEffect));
        EffectController.getInstance().setEffect(shaderEffect);
    }

    private void updateFocusMode() {
        setFocusMode(this.mFocusManager.setFocusMode(CameraSettings.getFocusMode()));
    }

    private void updatePictureAndPreviewSize() {
        List<CameraSize> supportedOutputSizeWithAssignedMode = this.mCameraCapabilities.getSupportedOutputSizeWithAssignedMode(35);
        Size sh = DataRepository.dataItemFeature().sh();
        if (sh == null) {
            sh = new Size(4184, 3138);
        }
        PictureSizeManager.initialize(supportedOutputSizeWithAssignedMode, sh.getHeight() * sh.getWidth(), 187, this.mBogusCameraId);
        CameraSize bestPictureSize = PictureSizeManager.getBestPictureSize(this.mModuleIndex);
        this.mPreviewSize = Util.getOptimalPreviewSize(false, this.mBogusCameraId, this.mCameraCapabilities.getSupportedOutputSizeWithAssignedMode(SurfaceTexture.class), (double) CameraSettings.getPreviewAspectRatio(bestPictureSize.width, bestPictureSize.height));
        this.mPictureSize = bestPictureSize;
        String str = TAG;
        Log.d(str, "pictureSize= " + bestPictureSize.width + "X" + bestPictureSize.height + " previewSize=" + this.mPreviewSize.width + "X" + this.mPreviewSize.height);
        if (this.mAmbilightEngine == null) {
            FileUtils.copyFileIfNeed(CameraAppImpl.getAndroidContext(), getAlgoXmlFile(), ALGO_XML_FOLDER_NAME + File.separator + ALGO_XML_FILENAME);
            this.mAmbilightEngine = new AmbilightEngine();
        }
        this.mAmbilightWidth = bestPictureSize.width;
        this.mAmbilightHeight = bestPictureSize.height;
        CameraSize cameraSize = this.mPreviewSize;
        updateCameraScreenNailSize(cameraSize.width, cameraSize.height);
    }

    private void updateRecordingTimeStyle(boolean z) {
        if (this.mTopAlert != null) {
            AndroidSchedulers.mainThread().scheduleDirect(new RunnableC0070b(this, z));
        }
    }

    private void updateSpeechShutter(boolean z) {
        if (CameraSettings.isSpeechShutterOpen()) {
            ModeProtocol.SpeechShutterDetect speechShutterDetect = (ModeProtocol.SpeechShutterDetect) ModeCoordinatorImpl.getInstance().getAttachProtocol(255);
            if (speechShutterDetect != null) {
                speechShutterDetect.processingSpeechShutter(z);
            }
            ModeProtocol.ActionProcessing actionProcessing = (ModeProtocol.ActionProcessing) ModeCoordinatorImpl.getInstance().getAttachProtocol(162);
            if (actionProcessing != null) {
                actionProcessing.processingSpeechShutter(z);
            }
        }
    }

    private void updateTransform(float f, float f2, float f3, float f4, float f5) {
        float[] texMatrix = getActivity().getGLView().getGLCanvas().getState().getTexMatrix();
        int length = texMatrix.length;
        int i = 0;
        int i2 = 0;
        while (i < length) {
            this.mTransform[i2] = texMatrix[i];
            i++;
            i2++;
        }
        Matrix.translateM(this.mTransform, 0, f, f2, 0.0f);
        Matrix.translateM(this.mTransform, 0, f4, f5, 0.0f);
        Matrix.scaleM(this.mTransform, 0, f3, f3, 1.0f);
        Matrix.translateM(this.mTransform, 0, -f, -f2, 0.0f);
        DrawYuvAttribute drawYuvAttribute = this.mYuvAttribute;
        if (drawYuvAttribute != null) {
            drawYuvAttribute.updateZoom(this.mTransform);
        }
        DrawYuvAttribute drawYuvAttribute2 = this.mYuvAttributeForVideo;
        if (drawYuvAttribute2 != null) {
            drawYuvAttribute2.updateZoom(this.mTransform);
            this.mYuvAttributeForVideo.updatePosition(0, 0);
        }
    }

    public /* synthetic */ void F(boolean z) {
        this.mTopAlert.updateRecordingTimeStyle(z);
    }

    @Override // com.android.camera.module.BaseModule
    public void addSaveTask(String str, ContentValues contentValues) {
        contentValues.put("datetaken", Long.valueOf(System.currentTimeMillis()));
        SaveVideoTask saveVideoTask = new SaveVideoTask(str, contentValues);
        synchronized (this) {
            this.mPendingSaveTaskList.add(saveVideoTask);
        }
    }

    /* access modifiers changed from: protected */
    @Override // com.android.camera.module.BaseModule
    public void applyZoomRatio() {
        if (this.mCamera2Device != null) {
            float deviceBasedZoomRatio = getDeviceBasedZoomRatio();
            String str = TAG;
            Log.d(str, "applyZoomRatio(): apply zoom ratio to device = " + deviceBasedZoomRatio);
            this.mCamera2Device.setZoomRatio(deviceBasedZoomRatio);
            this.mZoom = 1.0f / deviceBasedZoomRatio;
            float f = this.mZoom;
            if (f != 1.0f) {
                updateTransform(0.5f, 0.5f, f, 0.0f, 0.0f);
            }
            this.mCropRegion = HybridZoomingSystem.toCropRegion(deviceBasedZoomRatio, new Rect(0, 0, this.mAmbilightWidth, this.mAmbilightHeight));
        }
    }

    @Override // com.android.camera.module.loader.camera2.FocusManager2.Listener
    public void cancelFocus(boolean z) {
        if (isAlive() && isFrameAvailable() && getCameraState() != 0) {
            Camera2Proxy camera2Proxy = this.mCamera2Device;
            if (camera2Proxy != null) {
                if (z) {
                    camera2Proxy.setFocusMode(4);
                }
                this.mCamera2Device.cancelFocus(this.mModuleIndex);
            }
            if (getCameraState() != 3) {
                setCameraState(1);
            }
        }
    }

    @Override // com.android.camera.module.BaseModule
    public void checkDisplayOrientation() {
        if (isCreated()) {
            super.checkDisplayOrientation();
            ModeProtocol.MainContentProtocol mainContentProtocol = this.mMainProtocol;
            if (mainContentProtocol != null) {
                mainContentProtocol.setCameraDisplayOrientation(this.mCameraDisplayOrientation);
            }
            FocusManager2 focusManager2 = this.mFocusManager;
            if (focusManager2 != null) {
                focusManager2.setDisplayOrientation(this.mCameraDisplayOrientation);
            }
        }
    }

    @Override // com.android.camera.module.Module
    public void closeCamera() {
        Log.d(TAG, "closeCamera: start");
        FlowableEmitter<CaptureResult> flowableEmitter = this.mMetaDataFlowableEmitter;
        if (flowableEmitter != null) {
            flowableEmitter.onComplete();
        }
        Disposable disposable = this.mMetaDataDisposable;
        if (disposable != null) {
            disposable.dispose();
        }
        Camera camera = this.mActivity;
        if (camera != null) {
            camera.getCameraScreenNail().setExternalFrameProcessor(null);
        }
        Camera2Proxy camera2Proxy = this.mCamera2Device;
        if (camera2Proxy != null) {
            camera2Proxy.setASDEnable(false);
            this.mCamera2Device.releaseCameraPreviewCallback(null);
            this.mCamera2Device.setFocusCallback(null);
            this.mCamera2Device.setErrorCallback(null);
            this.mCamera2Device.setMetaDataCallback(null);
            this.mCamera2Device = null;
        }
        stopCpuBoost();
        Log.d(TAG, "closeCamera: end");
    }

    /* access modifiers changed from: protected */
    @Override // com.android.camera.module.BaseModule
    public void consumePreference(int... iArr) {
        for (int i : iArr) {
            if (i == 1) {
                updatePictureAndPreviewSize();
            } else if (i == 2) {
                updateFilter();
            } else if (i == 3) {
                updateFocusArea();
            } else if (i == 9) {
                updateAntiBanding(CameraSettings.getAntiBanding());
            } else if (i == 10) {
                updateFlashPreference();
            } else if (i == 12) {
                setEvValue();
            } else if (i == 14) {
                updateFocusMode();
            } else if (i == 19) {
                updateAutoAeParameters(true);
            } else if (i == 29) {
                updateExposureMeteringMode();
            } else if (i == 55) {
                updateModuleRelated();
            } else if (i == 59) {
                updateOnTripMode();
            } else if (i == 70) {
                updateASD();
            } else if (i == 24) {
                applyZoomRatio();
            } else if (i == 25) {
                focusCenter();
            }
        }
    }

    @Override // com.android.camera.module.BaseModule
    public void executeSaveTask(boolean z) {
        synchronized (this) {
            do {
                if (this.mPendingSaveTaskList.isEmpty()) {
                    break;
                }
                SaveVideoTask remove = this.mPendingSaveTaskList.remove(0);
                String str = TAG;
                Log.d(str, "executeSaveTask: " + remove.videoPath);
                this.mActivity.getImageSaver().addVideo(remove.videoPath, remove.contentValues, true);
                CameraStatUtils.trackAmbilightGenerateVideo();
            } while (!z);
            doLaterReleaseIfNeed();
        }
    }

    public /* synthetic */ void fd() {
        Log.d(TAG, "onSaveCompleted");
        onSaveFinish();
    }

    @Override // com.android.camera.module.Module, com.android.camera.module.BaseModule
    public void fillFeatureControl(StartControl startControl) {
        startControl.getFeatureDetail().addFragmentInfo(R.id.bottom_beauty, BaseFragmentDelegate.FRAGMENT_AMBILIGHT);
    }

    @Override // com.android.camera.protocol.ModeProtocol.AmbilightProtocol
    public boolean getAutoFinish() {
        int i = this.mSceneMode;
        return i == 4 || i == 5;
    }

    @Override // com.android.camera.protocol.ModeProtocol.AmbilightProtocol
    public int getDuration() {
        int i;
        int i2 = this.mSceneMode;
        if (i2 == 3) {
            return DurationConstant.DURATION_AMBILIGHT_STAR_TRACK_CAPTURE;
        }
        if (i2 != 4) {
            if (i2 != 5) {
                return 0;
            }
            if (mSupportAutoAe) {
                return 1000;
            }
            return DurationConstant.DURATION_AMBILIGHT_MAGIC_STAR_CAPTURE;
        } else if (!this.mInDebugMode || (i = SystemProperties.getInt("ambi_crowd_duration", -1)) == -1) {
            return 2000;
        } else {
            return i;
        }
    }

    /* access modifiers changed from: protected */
    @Override // com.android.camera.module.BaseModule
    public int getOperatingMode() {
        if (c.isMTKPlatform() || !mSupportAutoAe) {
            return 0;
        }
        return CameraCapabilities.SESSION_OPERATION_MODE_AMBILIGHT;
    }

    /* access modifiers changed from: protected */
    public long getPTSUs() {
        long nanoTime = System.nanoTime() / 1000;
        long j = this.prevOutputPTSUs;
        return nanoTime < j ? nanoTime + (j - nanoTime) : nanoTime;
    }

    @Override // com.android.camera.protocol.ModeProtocol.AmbilightProtocol
    public boolean getRepeat() {
        int i = this.mSceneMode;
        return i == 3 || (i == 5 && mSupportAutoAe);
    }

    public void handleFrameProcessDone() {
        synchronized (this.mRenderLock) {
            this.mAmbilightEngine.updatePreview(this.mYBuffer, this.mUBuffer, this.mVBuffer, this.mAmbilightRoi);
            if (this.mAmbilightRoi.zoomRatio < 1.0f) {
                Log.d(TAG, "update preview roi:[%f - %f, zoom:%f] ", Float.valueOf(this.mAmbilightRoi.xOffset), Float.valueOf(this.mAmbilightRoi.yOffset), Float.valueOf(this.mAmbilightRoi.zoomRatio));
                updateTransform(this.mAmbilightRoi.xOffset, this.mAmbilightRoi.yOffset, this.mAmbilightRoi.zoomRatio * this.mZoom, this.mAmbilightRoi.xTrans, this.mAmbilightRoi.yTrans);
            }
            this.mActivity.getGLView().requestRender();
        }
    }

    @Override // com.android.camera.module.Module, com.android.camera.protocol.ModeProtocol.CameraAction
    public boolean isDoingAction() {
        return this.mPaused || this.isZooming || getCameraState() == 0 || isInCountDown() || isProcessingSaveTask() || this.mIsPrepareSaveTask || this.mIsShooting;
    }

    @Override // com.android.camera.SurfaceTextureScreenNail.ExternalFrameProcessor
    public boolean isProcessorReady() {
        return ((MainHandler) this.mHandler).isInRendering();
    }

    @Override // com.android.camera.module.Module, com.android.camera.module.BaseModule, com.android.camera.protocol.ModeProtocol.CameraAction
    public boolean isRecording() {
        return this.mIsShooting || this.mIsPrepareSaveTask;
    }

    @Override // com.android.camera.module.Module
    public boolean isUnInterruptable() {
        return false;
    }

    @Override // com.android.camera.module.BaseModule
    public boolean isZoomEnabled() {
        if (this.mIsShooting) {
            return false;
        }
        return super.isZoomEnabled();
    }

    /* access modifiers changed from: protected */
    @Override // com.android.camera.module.BaseModule
    public boolean isZslPreferred() {
        return c.isMTKPlatform();
    }

    /* access modifiers changed from: protected */
    @Override // com.android.camera.module.BaseModule
    public void keepScreenOn() {
        this.mHandler.removeMessages(17);
        this.mHandler.removeMessages(2);
        getWindow().addFlags(128);
    }

    @Override // com.android.camera.module.loader.camera2.FocusManager2.Listener
    public boolean multiCapture() {
        return false;
    }

    @Override // com.android.camera.module.loader.camera2.FocusManager2.Listener
    public void notifyFocusAreaUpdate() {
        updatePreferenceTrampoline(3);
    }

    @Override // com.android.camera.module.Module, com.android.camera.module.BaseModule
    public boolean onBackPressed() {
        if (!this.mIsShooting) {
            return super.onBackPressed();
        }
        if (isProcessingSaveTask()) {
            return true;
        }
        stopAmbilightShooting();
        return true;
    }

    @Override // com.android.camera.module.Module, com.android.camera.module.BaseModule
    public void onBroadcastReceived(Context context, Intent intent) {
        if (intent != null && isAlive()) {
            if (CameraIntentManager.ACTION_SPEECH_SHUTTER.equals(intent.getAction())) {
                Log.d(TAG, "on Receive speech shutter broadcast action intent");
                if (!this.mIsShooting) {
                    onShutterButtonClick(110);
                }
            }
            super.onBroadcastReceived(context, intent);
        }
    }

    /* access modifiers changed from: protected */
    @Override // com.android.camera.module.BaseModule
    public void onCameraOpened() {
        super.onCameraOpened();
        initializeFocusManager();
        updatePreferenceTrampoline(UpdateConstant.FUN_TYPES_INIT);
        startPreviewSession();
        initMetaParser();
    }

    @Override // com.android.camera.module.Module, com.android.camera.module.BaseModule
    public void onCreate(int i, int i2) {
        super.onCreate(i, i2);
        mSupportAutoAe = this.mCameraCapabilities.isSupportAmbilightAutoAeTag();
        this.mHandler = new MainHandler(this.mActivity.getMainLooper(), this);
        this.mComponentConfigAmbilight = DataRepository.dataItemRunning().getComponentConfigAmbilight();
        this.mSceneMode = this.mComponentConfigAmbilight.getSceneModeByIndex(this.mComponentConfigAmbilight.getAmbilightModeIndex());
        onCameraOpened();
        EffectController.getInstance().setEffect(FilterInfo.FILTER_ID_NONE);
        this.mFileNameTemplate = this.mActivity.getString(R.string.ambi_file_name_format);
        this.mYuvAttribute = new DrawYuvAttribute();
        this.mYuvAttributeForVideo = new DrawYuvAttribute();
    }

    @Override // com.android.camera.SurfaceTextureScreenNail.ExternalFrameProcessor
    public void onDrawFrame(Rect rect, int i, int i2, boolean z) {
        GLCanvasImpl gLCanvas = getActivity().getGLView().getGLCanvas();
        if (gLCanvas != null) {
            gLCanvas.prepareYuvRenders();
            synchronized (this.mRenderLock) {
                this.mYuvAttribute.updatePosition(0, this.mTranslateY);
                gLCanvas.draw(this.mYuvAttribute.init(this.mImage, new Size(rect.width(), rect.height())));
                long pTSUs = getPTSUs();
                if (this.mMediaRecorderRecording && this.mLiveMediaRecorder != null && pTSUs - this.prevOutputPTSUs > 5900000) {
                    this.mLiveMediaRecorder.onSurfaceTextureUpdated(this.mYuvAttributeForVideo.init(this.mImage, this.mVideoSize), this.mMediaRecorderRecording);
                    this.prevOutputPTSUs = pTSUs;
                }
            }
        }
    }

    @Override // com.android.camera2.Camera2Proxy.FocusCallback
    public void onFocusStateChanged(FocusTask focusTask) {
        CameraCapabilities cameraCapabilities;
        if (isFrameAvailable() && !isDeparted()) {
            int focusTrigger = focusTask.getFocusTrigger();
            if (focusTrigger == 1) {
                Log.v(TAG, String.format(Locale.ENGLISH, "FocusTime=%1$dms focused=%2$b", Long.valueOf(focusTask.getElapsedTime()), Boolean.valueOf(focusTask.isSuccess())));
                if (!this.mFocusManager.isFocusingSnapOnFinish() && getCameraState() != 3) {
                    setCameraState(1);
                }
                this.mFocusManager.onFocusResult(focusTask);
                this.mActivity.getSensorStateManager().reset();
                if (focusTask.isSuccess() && this.m3ALocked) {
                    if (!DataRepository.dataItemFeature().nm() && isZoomRatioBetweenUltraAndWide() && (cameraCapabilities = this.mUltraCameraCapabilities) != null) {
                        boolean isAFRegionSupported = cameraCapabilities.isAFRegionSupported();
                        String str = TAG;
                        Log.d(str, "onFocusStateChanged: isUltraFocusAreaSupported = " + isAFRegionSupported);
                        if (!isAFRegionSupported) {
                            this.mCamera2Device.setFocusMode(0);
                            this.mCamera2Device.setFocusDistance(0.0f);
                        }
                    }
                    this.mCamera2Device.lockExposure(true);
                }
            } else if (focusTrigger != 2) {
                if (focusTrigger == 3) {
                    handleFoucsStateChange(focusTask);
                }
            } else if (!focusTask.isIsDepthFocus()) {
                handleFoucsStateChange(focusTask);
            }
        }
    }

    @Override // com.android.camera.module.Module, com.android.camera.module.BaseModule
    public void onHostStopAndNotifyActionStop() {
        if (this.mIsShooting) {
            ModeProtocol.RecordState recordState = (ModeProtocol.RecordState) ModeCoordinatorImpl.getInstance().getAttachProtocol(212);
            if (recordState != null) {
                recordState.onFinish();
            }
            stopAmbilightShooting();
            doLaterReleaseIfNeed();
        }
    }

    @Override // com.android.camera.module.Module, com.android.camera.module.BaseModule
    public boolean onKeyDown(int i, KeyEvent keyEvent) {
        if (this.mPaused) {
            return true;
        }
        boolean z = false;
        if (!isFrameAvailable()) {
            return false;
        }
        if (i == 27 || i == 66) {
            if (keyEvent.getRepeatCount() == 0) {
                if (!Util.isFingerPrintKeyEvent(keyEvent)) {
                    performKeyClicked(40, getString(R.string.pref_camera_volumekey_function_entryvalue_shutter), keyEvent.getRepeatCount(), true);
                } else if (CameraSettings.isFingerprintCaptureEnable()) {
                    performKeyClicked(30, getString(R.string.pref_camera_volumekey_function_entryvalue_shutter), keyEvent.getRepeatCount(), true);
                }
                return true;
            }
        } else if (i != 700) {
            if (!(i == 87 || i == 88)) {
                switch (i) {
                    case 23:
                        if (keyEvent.getRepeatCount() == 0) {
                            performKeyClicked(50, getString(R.string.pref_camera_volumekey_function_entryvalue_shutter), keyEvent.getRepeatCount(), true);
                            return true;
                        }
                        break;
                }
            }
            if (i == 24 || i == 88) {
                z = true;
            }
            if (handleVolumeKeyEvent(z, true, keyEvent.getRepeatCount(), keyEvent.getDevice().isExternal())) {
                return true;
            }
        } else if (this.mIsShooting) {
            stopAmbilightShooting();
        }
        return super.onKeyDown(i, keyEvent);
    }

    @Override // com.android.camera.module.Module, com.android.camera.module.BaseModule
    public boolean onKeyUp(int i, KeyEvent keyEvent) {
        if (i != 4) {
            if (i == 27 || i == 66) {
                return true;
            }
        } else if (((ModeProtocol.BackStack) ModeCoordinatorImpl.getInstance().getAttachProtocol(171)).handleBackStackFromKeyBack()) {
            return true;
        }
        return super.onKeyUp(i, keyEvent);
    }

    @Override // com.android.camera.module.Module, com.android.camera.module.BaseModule
    public void onPause() {
        stopBackgroundThread();
        super.onPause();
        unregisterSensor();
        tryRemoveCountDownMessage();
        this.mHandler.removeMessages(104);
        resetScreenOn();
        this.mHandler.sendMessageAtFrontOfQueue(this.mHandler.obtainMessage(105));
    }

    @Override // com.android.camera.module.BaseModule
    public void onPreviewLayoutChanged(Rect rect) {
        super.onPreviewLayoutChanged(rect);
        this.mActivity.onLayoutChange(rect);
        this.mTranslateY = getActivity().getCameraScreenNail().getTranslateY();
        this.mYuvAttribute.updatePosition(0, this.mTranslateY);
    }

    @Override // com.android.camera.module.BaseModule, com.android.camera2.Camera2Proxy.CameraMetaDataCallback
    public void onPreviewMetaDataUpdate(CaptureResult captureResult) {
        super.onPreviewMetaDataUpdate(captureResult);
        if (captureResult != null) {
            super.onPreviewMetaDataUpdate(captureResult);
            Float f = (Float) captureResult.get(CaptureResult.LENS_FOCUS_DISTANCE);
            if (f != null) {
                this.mFocusDistance = f.floatValue();
            }
            Integer num = (Integer) captureResult.get(CaptureResult.SENSOR_SENSITIVITY);
            if (this.mMetaDataFlowableEmitter != null && !this.mIsShooting) {
                this.mMetaDataFlowableEmitter.onNext(captureResult);
            }
        }
    }

    @Override // com.android.camera2.Camera2Proxy.CameraPreviewCallback
    public void onPreviewSessionClosed(CameraCaptureSession cameraCaptureSession) {
    }

    @Override // com.android.camera2.Camera2Proxy.CameraPreviewCallback
    public void onPreviewSessionFailed(CameraCaptureSession cameraCaptureSession) {
        if (!isTextureExpired() || !retryOnceIfCameraError(this.mHandler)) {
            this.mHandler.sendEmptyMessage(51);
        } else {
            Log.d(TAG, "sessionFailed due to surfaceTexture expired, retry");
        }
    }

    @Override // com.android.camera2.Camera2Proxy.CameraPreviewCallback
    public void onPreviewSessionSuccess(CameraCaptureSession cameraCaptureSession) {
        if (cameraCaptureSession != null && isAlive()) {
            this.mHandler.sendEmptyMessage(9);
            setCameraState(1);
            updatePreferenceInWorkThread(UpdateConstant.AMBILIGHT_TYPES_ON_PREVIEW_SUCCESS);
        }
    }

    @Override // com.android.camera.module.Module, com.android.camera.module.BaseModule
    public void onResume() {
        super.onResume();
        MiAlgoAsdSceneProfile.clearInitASDScenes();
        this.mTripodMode = 0;
        startBackgroundThread();
        registerSensorListener();
        this.mInDebugMode = SystemProperties.getBoolean("enable_ambi_debug", false);
    }

    @Override // com.android.camera.protocol.ModeProtocol.CameraAction
    public void onReviewCancelClicked() {
    }

    @Override // com.android.camera.protocol.ModeProtocol.CameraAction
    public void onReviewDoneClicked() {
    }

    @Override // com.android.camera.protocol.ModeProtocol.AmbilightProtocol
    public void onSceneModeSelect(int i) {
        String str = TAG;
        Log.d(str, "onSceneModeSelect:" + i);
        if (i != this.mSceneMode) {
            this.mSceneMode = i;
            this.mCamera2Device.pausePreview();
            if (updateAutoAeParameters(true) && c.qn()) {
                this.mCamera2Device.setFpsRange(new Range<>(0, 30));
            }
            resumePreview();
        }
    }

    @Override // com.android.camera.module.BaseModule
    public void onShineChanged(int i) {
        if (i == 196) {
            updatePreferenceTrampoline(2);
            this.mMainProtocol.updateEffectViewVisible();
        }
    }

    @Override // com.android.camera.protocol.ModeProtocol.AmbilightProtocol
    public void onShutterAnimationEnd() {
        if (this.mSceneMode == 4) {
            stopAmbilightShooting();
        }
    }

    @Override // com.android.camera.protocol.ModeProtocol.CameraAction
    public void onShutterButtonClick(int i) {
        if (!this.mPaused && getCameraState() != 0 && !isIgnoreTouchEvent() && !this.mActivity.isScreenSlideOff()) {
            if (this.mIsPrepareSaveTask || isProcessingSaveTask()) {
                String str = TAG;
                Log.w(str, "onShutterButtonClick return, mIsPrepareSaveTask " + this.mIsPrepareSaveTask);
                return;
            }
            if (i == 100) {
                this.mActivity.onUserInteraction();
            } else if (i == 110) {
                this.mActivity.onUserInteraction();
                ModeProtocol.TopAlert topAlert = (ModeProtocol.TopAlert) ModeCoordinatorImpl.getInstance().getAttachProtocol(172);
                if (topAlert != null) {
                    topAlert.hideRecommendDescTip(FragmentTopAlert.TIP_SPEECH_SHUTTER_DESC);
                }
            }
            setTriggerMode(i);
            if (!this.mIsShooting) {
                int countDownTimes = CameraSettings.getCountDownTimes();
                if (countDownTimes > 0) {
                    startCount(countDownTimes, i);
                } else {
                    takeShot();
                }
            } else if (isShootingTooShort()) {
                Log.w(TAG, "shooting is too short, ignore this click");
            } else {
                int i2 = this.mSceneMode;
                if (i2 != 5 && i2 != 4) {
                    stopAmbilightShooting();
                }
            }
        }
    }

    @Override // com.android.camera.protocol.ModeProtocol.CameraAction
    public void onShutterButtonFocus(boolean z, int i) {
    }

    @Override // com.android.camera.protocol.ModeProtocol.CameraAction
    public boolean onShutterButtonLongClick() {
        return false;
    }

    @Override // com.android.camera.protocol.ModeProtocol.CameraAction
    public void onShutterButtonLongClickCancel(boolean z) {
        onShutterButtonFocus(false, 2);
    }

    @Override // com.android.camera.module.Module, com.android.camera.module.BaseModule
    public void onSingleTapUp(int i, int i2, boolean z) {
        Camera2Proxy camera2Proxy;
        super.onSingleTapUp(i, i2, z);
        if (!this.mPaused && (camera2Proxy = this.mCamera2Device) != null && camera2Proxy.isSessionReady() && isInTapableRect(i, i2) && getCameraState() != 3 && getCameraState() != 0 && !this.mIsShooting && !isInCountDown()) {
            if (!isFrameAvailable()) {
                Log.w(TAG, "onSingleTapUp: frame not available");
            } else if ((!isFrontCamera() || !this.mActivity.isScreenSlideOff()) && !((ModeProtocol.BackStack) ModeCoordinatorImpl.getInstance().getAttachProtocol(171)).handleBackStackFromTapDown(i, i2)) {
                this.mMainProtocol.setFocusViewType(true);
                Point point = new Point(i, i2);
                mapTapCoordinate(point);
                unlockAEAF();
                this.mFocusManager.onSingleTapUp(point.x, point.y, z);
            }
        }
    }

    @Override // com.android.camera.protocol.ModeProtocol.CameraAction
    public void onThumbnailClicked(View view) {
        if (!this.mPaused && !isProcessingSaveTask() && this.mActivity.getThumbnailUpdater().getThumbnail() != null) {
            if (isCannotGotoGallery()) {
                Log.d(TAG, "onThumbnailClicked: CannotGotoGallery...");
            } else {
                this.mActivity.gotoGallery();
            }
        }
    }

    @Override // com.android.camera.module.Module, com.android.camera.module.BaseModule
    public void onUserInteraction() {
        super.onUserInteraction();
        if (!this.mPaused && !this.mIsShooting) {
            keepScreenOnAwhile();
        }
    }

    @Override // com.android.camera.module.loader.camera2.FocusManager2.Listener
    public boolean onWaitingFocusFinished() {
        return false;
    }

    @Override // com.android.camera.module.Module
    public void pausePreview() {
        Log.v(TAG, "pausePreview");
        this.mCamera2Device.pausePreview();
        setCameraState(0);
    }

    /* access modifiers changed from: protected */
    @Override // com.android.camera.module.BaseModule
    public void performKeyClicked(int i, String str, int i2, boolean z) {
        if (i2 == 0 && z) {
            ModeProtocol.TopAlert topAlert = this.mTopAlert;
            if (topAlert != null) {
                topAlert.hideAlert();
            }
            if (str.equals(getString(R.string.pref_camera_volumekey_function_entryvalue_timer))) {
                startCount(2, 20);
            } else {
                onShutterButtonClick(i);
            }
        }
    }

    @Override // com.android.camera.module.loader.camera2.FocusManager2.Listener
    public void playFocusSound(int i) {
        playCameraSound(i);
    }

    @Override // com.android.camera.protocol.ModeProtocol.BaseProtocol, com.android.camera.module.Module, com.android.camera.module.BaseModule
    public void registerProtocol() {
        super.registerProtocol();
        ModeCoordinatorImpl.getInstance().attachProtocol(161, this);
        ModeCoordinatorImpl.getInstance().attachProtocol(169, this);
        ModeCoordinatorImpl.getInstance().attachProtocol(234, this);
        ModeCoordinatorImpl.getInstance().attachProtocol(420, this);
        getActivity().getImplFactory().initAdditional(getActivity(), 164, 234, 212);
        this.mTopAlert = (ModeProtocol.TopAlert) ModeCoordinatorImpl.getInstance().getAttachProtocol(172);
    }

    @Override // com.android.camera.SurfaceTextureScreenNail.ExternalFrameProcessor
    public void releaseRender() {
    }

    @Override // com.android.camera.module.Module
    public void resumePreview() {
        Log.v(TAG, "resumePreview");
        this.mCamera2Device.resumePreview();
        setCameraState(1);
    }

    public void setAsdScenes(MarshalQueryableASDScene.ASDScene[] aSDSceneArr) {
        this.mAsdScenes = aSDSceneArr;
    }

    @Override // com.android.camera.module.loader.camera2.FocusManager2.Listener
    public boolean shouldCaptureDirectly() {
        return false;
    }

    @Override // com.android.camera.protocol.ModeProtocol.AmbilightProtocol
    public boolean shouldDisableStopButton() {
        return this.mSceneMode == 3;
    }

    @Override // com.android.camera.module.Module, com.android.camera.module.BaseModule
    public boolean shouldReleaseLater() {
        tryRemoveCountDownMessage();
        return isRecording();
    }

    @Override // com.android.camera.module.loader.camera2.FocusManager2.Listener
    public void startFocus() {
        if (isDeviceAlive() && isFrameAvailable()) {
            Log.v(TAG, "startFocus");
            if (this.mFocusOrAELockSupported) {
                this.mCamera2Device.startFocus(FocusTask.create(1), this.mModuleIndex);
            } else {
                this.mCamera2Device.resumePreview();
            }
        }
    }

    @Override // com.android.camera.module.Module
    public void startPreview() {
        if (!isDeviceAlive()) {
        }
    }

    /* access modifiers changed from: protected */
    public void startVideoRecording() {
        if (this.mCamera2Device == null) {
            Log.e(TAG, "initializeRecorder: null camera");
            return;
        }
        Log.v(TAG, "startVideoRecording");
        if (this.mLiveMediaRecorder == null) {
            this.mLiveMediaRecorder = new LiveMediaRecorder();
        }
        if (this.mLiveMediaRecorder.init(Util.genContentValues(2, Util.genVideoPath(2, getString(R.string.video_file_name_format)), this.mVideoSize.getWidth(), this.mVideoSize.getHeight()), this.mOrientationCompensation, getActivity().getGLView().getEGLContext14(), this.mMediaEncoderListener, false, 0.0027777778f) && this.mLiveMediaRecorder.startRecorder(this.mRequestStartTime)) {
            this.mMediaRecorderRecording = true;
            Log.v(TAG, "startVideoRecording process done");
        }
    }

    @Override // com.android.camera.module.BaseModule
    public void tryRemoveCountDownMessage() {
        if (isInCountDown()) {
            this.mCountdownDisposable.dispose();
            this.mCountdownDisposable = null;
            this.mHandler.post(new Runnable() {
                /* class com.android.camera.module.AmbilightModule.AnonymousClass3 */

                public void run() {
                    Log.d(AmbilightModule.TAG, "run: hide delay number in main thread");
                    AmbilightModule.this.mTopAlert.hideDelayNumber();
                }
            });
        }
    }

    @Override // com.android.camera.module.Module, com.android.camera.module.BaseModule
    public void unRegisterModulePersistProtocol() {
        super.unRegisterModulePersistProtocol();
        Log.d(TAG, "unRegisterModulePersistProtocol");
        getActivity().getImplFactory().detachModulePersistent();
    }

    @Override // com.android.camera.protocol.ModeProtocol.BaseProtocol, com.android.camera.module.Module, com.android.camera.module.BaseModule
    public void unRegisterProtocol() {
        super.unRegisterProtocol();
        ModeCoordinatorImpl.getInstance().detachProtocol(161, this);
        ModeCoordinatorImpl.getInstance().detachProtocol(169, this);
        ModeCoordinatorImpl.getInstance().detachProtocol(234, this);
        ModeCoordinatorImpl.getInstance().detachProtocol(420, this);
        Camera camera = this.mActivity;
        if (camera != null) {
            camera.getImplFactory().detachAdditional();
        }
    }

    /* access modifiers changed from: protected */
    public void unlockAEAF() {
        Log.d(TAG, "unlockAEAF");
        this.m3ALocked = false;
        if (this.mAeLockSupported) {
            this.mCamera2Device.setAELock(false);
        }
        this.mFocusManager.setAeAwbLock(false);
    }

    public void updateBuffers(int i) {
        if (this.mYBuffer == null) {
            this.mYBuffer = ByteBuffer.allocateDirect(i);
            this.mUBuffer = ByteBuffer.allocateDirect(i);
            this.mVBuffer = ByteBuffer.allocateDirect(i);
        }
        if (this.mAmbilightRoi == null) {
            this.mAmbilightRoi = new AmbilightRoi();
        }
        if (this.mImage == null) {
            this.mImage = new MiYuvImage(null, this.mPreviewWidth, this.mPreviewHeight, 35);
        }
        this.mImage.updateData(this.mYBuffer, this.mUBuffer, this.mVBuffer);
    }

    /* access modifiers changed from: protected */
    @Override // com.android.camera.module.BaseModule
    public void updateFlashPreference() {
        setFlashMode(DataRepository.dataItemConfig().getComponentFlash().getComponentValue(this.mModuleIndex));
    }

    /* access modifiers changed from: protected */
    public void updateFocusArea() {
        if (this.mCamera2Device == null) {
            Log.e(TAG, "updateFocusArea: null camera device");
            return;
        }
        Rect cropRegion = getCropRegion();
        Rect activeArraySize = getActiveArraySize();
        this.mActivity.getSensorStateManager().setFocusSensorEnabled(this.mFocusManager.getMeteringAreas(cropRegion, activeArraySize) != null);
        this.mCamera2Device.setAERegions(this.mFocusManager.getMeteringAreas(cropRegion, activeArraySize));
        if (this.mFocusAreaSupported) {
            this.mCamera2Device.setAFRegions(this.mFocusManager.getFocusAreas(cropRegion, activeArraySize));
        } else {
            this.mCamera2Device.resumePreview();
        }
    }

    public void updateOnTripMode() {
        MarshalQueryableASDScene.ASDScene[] aSDSceneArr;
        if (!(this.mCamera2Device == null || (aSDSceneArr = this.mAsdScenes) == null)) {
            for (MarshalQueryableASDScene.ASDScene aSDScene : aSDSceneArr) {
                if (aSDScene.type == 4) {
                    this.mTripodMode = aSDScene.value;
                }
            }
        }
    }

    /* access modifiers changed from: protected */
    public void updateRecordingTime() {
        if (this.mIsShooting && !shouldHideTimeLabel()) {
            if (this.mSceneMode == 5) {
                CountDownTimer countDownTimer = this.mCountDownTimer;
                if (countDownTimer != null) {
                    countDownTimer.cancel();
                }
                this.mCountDownTimer = new CountDownTimer((long) getDuration(), 1) {
                    /* class com.android.camera.module.AmbilightModule.AnonymousClass5 */

                    public void onFinish() {
                    }

                    public void onTick(long j) {
                        String millisecondToTimeString = Util.millisecondToTimeString(j, false);
                        if (AmbilightModule.this.mTopAlert != null) {
                            AmbilightModule.this.mTopAlert.updateRecordingTime(millisecondToTimeString);
                        }
                    }
                };
                this.mCountDownTimer.start();
                return;
            }
            String millisecondToTimeString = Util.millisecondToTimeString(SystemClock.uptimeMillis() - this.mRecordingStartTime, false);
            ModeProtocol.TopAlert topAlert = this.mTopAlert;
            if (topAlert != null) {
                topAlert.updateRecordingTime(millisecondToTimeString);
            }
            this.mHandler.sendEmptyMessageDelayed(42, 500);
        }
    }
}
