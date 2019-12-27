package com.android.camera2;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.RectF;
import android.graphics.SurfaceTexture;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraCaptureSession;
import android.hardware.camera2.CameraConstrainedHighSpeedCaptureSession;
import android.hardware.camera2.CameraDevice;
import android.hardware.camera2.CaptureRequest;
import android.hardware.camera2.CaptureResult;
import android.hardware.camera2.TotalCaptureResult;
import android.hardware.camera2.impl.CameraMetadataNative;
import android.hardware.camera2.params.InputConfiguration;
import android.hardware.camera2.params.MeteringRectangle;
import android.hardware.camera2.params.OutputConfiguration;
import android.hardware.camera2.utils.SurfaceUtils;
import android.location.Location;
import android.media.Image;
import android.media.ImageReader;
import android.media.ImageWriter;
import android.os.Build;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Message;
import android.os.RemoteException;
import android.os.SystemProperties;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Range;
import android.util.Size;
import android.view.Surface;
import android.view.SurfaceHolder;
import com.android.camera.CameraSettings;
import com.android.camera.CameraSize;
import com.android.camera.HybridZoomingSystem;
import com.android.camera.LocalParallelService;
import com.android.camera.Thumbnail;
import com.android.camera.Util;
import com.android.camera.data.DataRepository;
import com.android.camera.fragment.beauty.BeautyValues;
import com.android.camera.lib.compatibility.util.CompatibilityUtils;
import com.android.camera.log.Log;
import com.android.camera.module.loader.FunctionParseBeautyBodySlimCount;
import com.android.camera.module.loader.camera2.Camera2DataContainer;
import com.android.camera.module.loader.camera2.FocusTask;
import com.android.camera.parallel.AlgoConnector;
import com.android.camera.statistic.CameraStat;
import com.android.camera2.Camera2Proxy;
import com.android.camera2.compat.MiCameraCompat;
import com.android.camera2.vendortag.CaptureRequestVendorTags;
import com.android.camera2.vendortag.CaptureResultVendorTags;
import com.android.camera2.vendortag.VendorTagHelper;
import com.android.camera2.vendortag.struct.AWBFrameControl;
import com.android.camera2.vendortag.struct.MarshalQueryableASDScene;
import com.mi.config.b;
import com.xiaomi.camera.base.Constants;
import com.xiaomi.camera.core.ParallelCallback;
import com.xiaomi.protocol.IImageReaderParameterSets;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.concurrent.ConcurrentLinkedDeque;

@TargetApi(21)
public class MiCamera2 extends Camera2Proxy {
    private static final int DEF_QUICK_SHOT_THRESHOLD_INTERVAL_TIME = 50;
    private static final int DEF_QUICK_SHOT_THRESHOLD_SHOT_CACHE_COUNT = 10;
    private static final int DEF_QUICK_SHOT_THRESHOLD_SHOT_CACHE_TIME_OUT = 10000;
    private static final int MAX_IMAGE_BUFFER_SIZE = ((b.hi() || "tucana".equals(b.km)) ? 12 : 10);
    private static final int MSG_CHECK_CAMERA_ALIVE = 3;
    private static final int MSG_WAITING_AF_LOCK_TIMEOUT = 1;
    private static final int MSG_WAITING_LOCAL_PARALLEL_SERVICE_READY = 2;
    private static final int PARALLEL_SURFACE_INDEX_UNSET = -1;
    /* access modifiers changed from: private */
    public static final String TAG = "MiCamera2";
    private static final long TIME_WAITING_LOCK_AF_FLASH = 4000;
    private static final long TIME_WAITING_LOCK_AF_TORCH = 3000;
    static final MeteringRectangle[] ZERO_WEIGHT_3A_REGION;
    private final int AE_STATE_NULL;
    private final int MAX_PARALLEL_REQUEST_NUMBER;
    /* access modifiers changed from: private */
    public final boolean WAITING_AE_STATE_STRICT;
    private CameraDevice mCameraDevice;
    private Handler mCameraHandler;
    private Handler mCameraMainThreadHandler;
    private Handler mCameraPreviewHandler;
    /* access modifiers changed from: private */
    public final CameraCapabilities mCapabilities;
    private Camera2Proxy.CaptureBusyCallback mCaptureBusyCallback;
    private PictureCaptureCallback mCaptureCallback;
    /* access modifiers changed from: private */
    public CameraCaptureSession mCaptureSession;
    private CaptureSessionStateCallback mCaptureSessionStateCallback;
    private long mCaptureTime;
    /* access modifiers changed from: private */
    public CameraConfigs mConfigs;
    private long mCurrentFrameNum;
    /* access modifiers changed from: private */
    public List<OutputConfiguration> mDeferOutputConfigurations;
    /* access modifiers changed from: private */
    public Surface mDeferPreviewSurface;
    private ImageReader mDepthReader;
    private int mDisplayOrientation;
    private boolean mEnableParallelSession;
    private SurfaceTexture mFakeOutputTexture;
    /* access modifiers changed from: private */
    public int mFocusLockRequestHashCode;
    private Camera2Proxy.CaptureCallback mFrontQuickCaptureCallback;
    /* access modifiers changed from: private */
    public Handler mHelperHandler;
    private Range<Integer> mHighSpeedFpsRange;
    private volatile boolean mIsCameraClosed;
    private boolean mIsCaptureCompleted;
    /* access modifiers changed from: private */
    public volatile boolean mIsCaptureSessionClosed;
    private boolean mIsConfigRawStream;
    private boolean mIsPreviewCallbackEnabled;
    private boolean mIsPreviewCallbackStarted;
    private long mLastFrameNum;
    /* access modifiers changed from: private */
    public MiCamera2Shot mMiCamera2Shot;
    /* access modifiers changed from: private */
    public ConcurrentLinkedDeque<MiCamera2Shot> mMiCamera2ShotQueue;
    private List<Surface> mParallelCaptureSurfaceList;
    /* access modifiers changed from: private */
    public volatile boolean mPendingNotifyVideoEnd;
    private ImageReader mPhotoImageReader;
    private ImageReader mPortraitRawImageReader;
    /* access modifiers changed from: private */
    public int mPreCaptureRequestHashCode;
    /* access modifiers changed from: private */
    public MiCamera2Preview mPreviewControl;
    private ImageReader mPreviewImageReader;
    private CaptureRequest mPreviewRequest;
    /* access modifiers changed from: private */
    public CaptureRequest.Builder mPreviewRequestBuilder;
    /* access modifiers changed from: private */
    public Surface mPreviewSurface;
    private int mQcfaParallelSurfaceIndex;
    private ImageReader mRawImageReader;
    private ImageWriter mRawImageWriter;
    private Surface mRecordSurface;
    private List<ImageReader> mRemoteImageReaderList;
    private int mScreenLightColorTemperature;
    private CaptureSessionConfigurations mSessionConfigs;
    /* access modifiers changed from: private */
    public int mSessionId;
    /* access modifiers changed from: private */
    public final Object mSessionLock;
    /* access modifiers changed from: private */
    public boolean mSetRepeatingEarly;
    /* access modifiers changed from: private */
    public final Object mShotQueueLock;
    private int mSubParallelSurfaceIndex;
    private SuperNightReprocessHandler mSuperNightReprocessHandler;
    private int mTeleParallelSurfaceIndex;
    /* access modifiers changed from: private */
    public boolean mToTele;
    private int mUltraTeleParallelSurfaceIndex;
    private int mUltraWideParallelSurfaceIndex;
    /* access modifiers changed from: private */
    public Camera2Proxy.VideoRecordStateCallback mVideoRecordStateCallback;
    /* access modifiers changed from: private */
    public final Object mVideoRecordStateLock;
    /* access modifiers changed from: private */
    public int mVideoSessionId;
    private ImageReader mVideoSnapshotImageReader;
    private int mWideParallelSurfaceIndex;

    private class CaptureSessionStateCallback extends CameraCaptureSession.StateCallback {
        private WeakReference<Camera2Proxy.CameraPreviewCallback> mClientCb;
        private int mId;

        public CaptureSessionStateCallback(int i, Camera2Proxy.CameraPreviewCallback cameraPreviewCallback) {
            this.mId = i;
            this.mClientCb = new WeakReference<>(cameraPreviewCallback);
        }

        public void onClosed(@NonNull CameraCaptureSession cameraCaptureSession) {
            String access$000 = MiCamera2.TAG;
            Log.e(access$000, "onClosed: id=" + this.mId + " sessionId=" + MiCamera2.this.mSessionId);
            if (this.mId == MiCamera2.this.mSessionId) {
                WeakReference<Camera2Proxy.CameraPreviewCallback> weakReference = this.mClientCb;
                if (weakReference != null) {
                    Camera2Proxy.CameraPreviewCallback cameraPreviewCallback = (Camera2Proxy.CameraPreviewCallback) weakReference.get();
                    if (cameraPreviewCallback != null) {
                        cameraPreviewCallback.onPreviewSessionClosed(cameraCaptureSession);
                    }
                }
            }
        }

        public void onConfigureFailed(@NonNull CameraCaptureSession cameraCaptureSession) {
            String access$000 = MiCamera2.TAG;
            Log.e(access$000, "onConfigureFailed: id=" + this.mId + " sessionId=" + MiCamera2.this.mSessionId);
            if (this.mId == MiCamera2.this.mSessionId) {
                WeakReference<Camera2Proxy.CameraPreviewCallback> weakReference = this.mClientCb;
                if (weakReference != null) {
                    Camera2Proxy.CameraPreviewCallback cameraPreviewCallback = (Camera2Proxy.CameraPreviewCallback) weakReference.get();
                    if (cameraPreviewCallback != null) {
                        cameraPreviewCallback.onPreviewSessionFailed(cameraCaptureSession);
                    }
                }
            }
        }

        public void onConfigured(@NonNull CameraCaptureSession cameraCaptureSession) {
            boolean isEmpty;
            if (this.mId == MiCamera2.this.mSessionId) {
                synchronized (MiCamera2.this.mSessionLock) {
                    String access$000 = MiCamera2.TAG;
                    Log.d(access$000, "onConfigured: id = " + this.mId + ", session = " + cameraCaptureSession + ", reprocessable = " + cameraCaptureSession.isReprocessable());
                    CameraCaptureSession unused = MiCamera2.this.mCaptureSession = cameraCaptureSession;
                    if (MiCamera2.this.mCaptureSession.isReprocessable()) {
                        MiCamera2.this.prepareRawImageWriter(MiCamera2.this.mConfigs.getSensorRawImageSize(), MiCamera2.this.mCaptureSession.getInputSurface());
                    }
                }
                boolean unused2 = MiCamera2.this.mIsCaptureSessionClosed = false;
                if (MiCamera2.this.mPendingNotifyVideoEnd && this.mId == MiCamera2.this.mVideoSessionId) {
                    MiCamera2.this.notifyVideoStreamEnd();
                    boolean unused3 = MiCamera2.this.mPendingNotifyVideoEnd = false;
                }
                synchronized (MiCamera2.this.mSessionLock) {
                    isEmpty = MiCamera2.this.mDeferOutputConfigurations.isEmpty();
                    String access$0002 = MiCamera2.TAG;
                    Log.d(access$0002, "onConfigured: is mDeferOutputConfigurations null: " + isEmpty);
                }
                if (isEmpty) {
                    onPreviewSessionSuccess();
                    return;
                }
                if (MiCamera2.this.mSetRepeatingEarly) {
                    MiCamera2.this.resumePreview();
                }
                MiCamera2 miCamera2 = MiCamera2.this;
                miCamera2.updateDeferPreviewSession(miCamera2.mDeferPreviewSurface);
            }
        }

        public void onPreviewSessionSuccess() {
            boolean isEmpty;
            synchronized (MiCamera2.this.mSessionLock) {
                isEmpty = MiCamera2.this.mDeferOutputConfigurations.isEmpty();
            }
            if (isEmpty && this.mId == MiCamera2.this.mSessionId) {
                WeakReference<Camera2Proxy.CameraPreviewCallback> weakReference = this.mClientCb;
                if (weakReference != null) {
                    Camera2Proxy.CameraPreviewCallback cameraPreviewCallback = (Camera2Proxy.CameraPreviewCallback) weakReference.get();
                    if (cameraPreviewCallback != null) {
                        cameraPreviewCallback.onPreviewSessionSuccess(MiCamera2.this.mCaptureSession);
                    }
                }
            }
        }

        public void setClientCb(Camera2Proxy.CameraPreviewCallback cameraPreviewCallback) {
            this.mClientCb = new WeakReference<>(cameraPreviewCallback);
        }
    }

    private class HighSpeedCaptureSessionStateCallback extends CameraCaptureSession.StateCallback {
        private final WeakReference<Camera2Proxy.CameraPreviewCallback> mClientCb;
        private final int mId;

        public HighSpeedCaptureSessionStateCallback(int i, Camera2Proxy.CameraPreviewCallback cameraPreviewCallback) {
            this.mId = i;
            this.mClientCb = new WeakReference<>(cameraPreviewCallback);
        }

        public void onClosed(@NonNull CameraCaptureSession cameraCaptureSession) {
            String access$000 = MiCamera2.TAG;
            Log.d(access$000, "onHighSpeedClosed: " + cameraCaptureSession);
            synchronized (MiCamera2.this.mSessionLock) {
                if (MiCamera2.this.mCaptureSession != null && MiCamera2.this.mCaptureSession.equals(cameraCaptureSession)) {
                    CameraCaptureSession unused = MiCamera2.this.mCaptureSession = null;
                }
            }
            if (this.mClientCb.get() != null) {
                ((Camera2Proxy.CameraPreviewCallback) this.mClientCb.get()).onPreviewSessionClosed(cameraCaptureSession);
            }
        }

        public void onConfigureFailed(@NonNull CameraCaptureSession cameraCaptureSession) {
            String access$000 = MiCamera2.TAG;
            Log.e(access$000, "onHighSpeedConfigureFailed: id=" + this.mId + " sessionId=" + MiCamera2.this.mSessionId);
            if (this.mClientCb.get() != null) {
                ((Camera2Proxy.CameraPreviewCallback) this.mClientCb.get()).onPreviewSessionFailed(cameraCaptureSession);
            }
        }

        public void onConfigured(@NonNull CameraCaptureSession cameraCaptureSession) {
            if (this.mId == MiCamera2.this.mSessionId) {
                synchronized (MiCamera2.this.mSessionLock) {
                    String access$000 = MiCamera2.TAG;
                    Log.d(access$000, "onHighSpeedConfigured: id=" + this.mId + " highSpeedSession=" + cameraCaptureSession);
                    CameraCaptureSession unused = MiCamera2.this.mCaptureSession = cameraCaptureSession;
                }
                boolean unused2 = MiCamera2.this.mIsCaptureSessionClosed = false;
                MiCamera2 miCamera2 = MiCamera2.this;
                miCamera2.applySettingsForVideo(miCamera2.mPreviewRequestBuilder);
                MiCameraCompat.applyIsHfrPreview(MiCamera2.this.mPreviewRequestBuilder, true);
                if (this.mClientCb.get() != null) {
                    ((Camera2Proxy.CameraPreviewCallback) this.mClientCb.get()).onPreviewSessionSuccess(cameraCaptureSession);
                }
            }
        }
    }

    private class PictureCaptureCallback extends CameraCaptureSession.CaptureCallback {
        private boolean mAELockOnlySupported;
        private FocusTask mAutoFocusTask;
        private boolean mFocusAreaSupported;
        private int mLastResultAEState = -1;
        private int mLastResultAFState = -1;
        private FocusTask mManuallyFocusTask;
        private boolean mPartialResultSupported;
        private CaptureResult mPreviewCaptureResult;
        private final Object mPreviewCaptureResultLock = new Object();
        private int mState = 0;
        private final Object mStateLock = new Object();
        private FocusTask mTorchFocusTask;

        PictureCaptureCallback() {
            this.mPartialResultSupported = MiCamera2.this.mCapabilities.isPartialMetadataSupported();
            onCapabilityChanged(MiCamera2.this.mCapabilities);
        }

        private boolean isAeLocked(Integer num) {
            int intValue = num.intValue();
            return intValue == 2 || intValue == 3 || intValue == 4;
        }

        private Boolean isAutoFocusing(Integer num) {
            int intValue = num.intValue();
            return (intValue == 1 || intValue == 3) ? Boolean.TRUE : Boolean.FALSE;
        }

        private Boolean isFocusLocked(Integer num) {
            int intValue = num.intValue();
            if (intValue == 2 || intValue == 4) {
                return Boolean.TRUE;
            }
            if (intValue == 5 || intValue == 6) {
                return Boolean.FALSE;
            }
            return null;
        }

        private void process(@NonNull CaptureResult captureResult) {
            synchronized (this.mPreviewCaptureResultLock) {
                this.mPreviewCaptureResult = captureResult;
            }
            processVideoRecordStatus(captureResult);
            if (HybridZoomingSystem.IS_2_SAT && MiCamera2.this.mToTele && CaptureResultParser.getFastZoomResult(captureResult)) {
                Log.d(MiCamera2.TAG, "process: CaptureResultParser fast zoom...");
                MiCamera2.this.setOpticalZoomToTele(false);
                MiCamera2.this.resumePreview();
            }
            Camera2Proxy.CameraMetaDataCallback metadataCallback = MiCamera2.this.getMetadataCallback();
            int state = getState();
            if (state != 1) {
                switch (state) {
                    case 9:
                        if (MiCamera2.this.mIsCaptureSessionClosed) {
                            Log.w(MiCamera2.TAG, "process: STATE_WAITING_FLASH_CLOSE but capture session is closed");
                            return;
                        }
                        Integer num = (Integer) captureResult.get(CaptureResult.FLASH_STATE);
                        if (num == null || num.intValue() == 2) {
                            setState(8);
                            MiCamera2.this.captureStillPicture();
                            return;
                        }
                        return;
                    case 10:
                        Integer num2 = (Integer) captureResult.get(CaptureResult.FLASH_STATE);
                        if (num2 != null && 3 == num2.intValue()) {
                            MiCamera2.this.triggerPrecapture();
                            return;
                        }
                        return;
                    case 11:
                        Integer num3 = (Integer) captureResult.get(CaptureResult.FLASH_STATE);
                        if (num3 == null || num3.intValue() == 2) {
                            setState(0);
                            MiCamera2.this.pausePreview();
                            return;
                        }
                        return;
                    default:
                        return;
                }
            } else if (metadataCallback != null) {
                metadataCallback.onPreviewMetaDataUpdate(captureResult);
            }
        }

        private void processAeResult(CaptureResult captureResult) {
            Integer num = (Integer) captureResult.get(CaptureResult.CONTROL_AE_STATE);
            if (num != null) {
                Camera2Proxy.FocusCallback focusCallback = MiCamera2.this.getFocusCallback();
                if (focusCallback != null && this.mManuallyFocusTask != null) {
                    Log.d(MiCamera2.TAG, String.format(Locale.ENGLISH, "aeState changed from %s to %s,", new Object[]{Util.controlAEStateToString(Integer.valueOf(this.mLastResultAEState)), Util.controlAEStateToString(num)}));
                    this.mLastResultAEState = num.intValue();
                    if (this.mAutoFocusTask != null) {
                        this.mAutoFocusTask = null;
                    } else if (!this.mManuallyFocusTask.isTaskProcessed()) {
                        String access$000 = MiCamera2.TAG;
                        Log.d(access$000, "the task's request is not process yet. task=" + this.mManuallyFocusTask.hashCode() + ", request=" + captureResult.getRequest().hashCode());
                    } else if (isAeLocked(num)) {
                        Log.d(MiCamera2.TAG, "AE has been already converged, lock AE");
                        this.mManuallyFocusTask.setResult(true);
                        focusCallback.onFocusStateChanged(this.mManuallyFocusTask);
                        this.mManuallyFocusTask = null;
                    }
                }
            }
        }

        private void processAfResult(CaptureResult captureResult) {
            Integer num = (Integer) captureResult.get(CaptureResult.CONTROL_AF_STATE);
            if (num != null) {
                Camera2Proxy.FocusCallback focusCallback = MiCamera2.this.getFocusCallback();
                if (focusCallback != null && num.intValue() != this.mLastResultAFState) {
                    Log.d(MiCamera2.TAG, String.format(Locale.ENGLISH, "afState changed from %d to %d", new Object[]{Integer.valueOf(this.mLastResultAFState), Integer.valueOf(num.intValue())}));
                    this.mLastResultAFState = num.intValue();
                    if (this.mManuallyFocusTask == null) {
                        if (isAutoFocusing(num).booleanValue()) {
                            this.mAutoFocusTask = FocusTask.create(2);
                            focusCallback.onFocusStateChanged(this.mAutoFocusTask);
                            return;
                        }
                        Boolean isFocusLocked = isFocusLocked(num);
                        if (isFocusLocked != null) {
                            FocusTask focusTask = this.mAutoFocusTask;
                            if (focusTask != null) {
                                focusTask.setResult(isFocusLocked.booleanValue());
                                focusCallback.onFocusStateChanged(this.mAutoFocusTask);
                                this.mAutoFocusTask = null;
                            }
                        }
                    } else if (this.mAutoFocusTask != null) {
                        this.mAutoFocusTask = null;
                    } else {
                        Boolean isFocusLocked2 = isFocusLocked(num);
                        if (isFocusLocked2 != null) {
                            this.mManuallyFocusTask.setResult(isFocusLocked2.booleanValue());
                            focusCallback.onFocusStateChanged(this.mManuallyFocusTask);
                            this.mManuallyFocusTask = null;
                        }
                    }
                }
            }
        }

        private void processPartial(@NonNull CaptureResult captureResult) {
            FocusTask focusTask = this.mManuallyFocusTask;
            if (focusTask != null) {
                focusTask.processResult(captureResult);
            }
            if (this.mFocusAreaSupported) {
                processAfResult(captureResult);
            } else if (this.mAELockOnlySupported) {
                processAeResult(captureResult);
            }
            int state = getState();
            if (state == 3) {
                Integer num = (Integer) captureResult.get(CaptureResult.CONTROL_AF_STATE);
                if (num != null) {
                    if (4 == num.intValue() || 5 == num.intValue() || 2 == num.intValue() || 6 == num.intValue() || (MiCamera2.this.mFocusLockRequestHashCode == captureResult.getRequest().hashCode() && num.intValue() == 0)) {
                        if (MiCamera2.this.mFocusLockRequestHashCode == captureResult.getRequest().hashCode() || MiCamera2.this.mFocusLockRequestHashCode == 0) {
                            if (MiCamera2.this.mHelperHandler != null) {
                                MiCamera2.this.mHelperHandler.removeMessages(1);
                            }
                            if (MiCamera2.this.needOptimizedFlash() || b.isMTKPlatform() || MiCamera2.this.mConfigs.isForceBackSoftLight()) {
                                MiCamera2.this.runCaptureSequence();
                            } else {
                                MiCamera2.this.runPreCaptureSequence();
                            }
                        }
                    } else if (MiCamera2.this.mFocusLockRequestHashCode == captureResult.getRequest().hashCode()) {
                        int unused = MiCamera2.this.mFocusLockRequestHashCode = 0;
                    }
                }
            } else if (state == 4) {
                Integer num2 = (Integer) captureResult.get(CaptureResult.CONTROL_AE_STATE);
                String access$000 = MiCamera2.TAG;
                Log.d(access$000, "STATE_WAITING_AE_LOCK:  AF = " + Util.controlAFStateToString((Integer) captureResult.get(CaptureResult.CONTROL_AF_STATE)));
                String access$0002 = MiCamera2.TAG;
                Log.d(access$0002, "STATE_WAITING_AE_LOCK:  AE = " + Util.controlAEStateToString(num2));
                String access$0003 = MiCamera2.TAG;
                Log.d(access$0003, "STATE_WAITING_AE_LOCK: AWB = " + Util.controlAWBStateToString((Integer) captureResult.get(CaptureResult.CONTROL_AWB_STATE)));
                if (num2 == null && MiCamera2.this.WAITING_AE_STATE_STRICT) {
                    num2 = -1;
                }
                if (num2 == null || num2.intValue() == 3) {
                    Log.d(MiCamera2.TAG, "STATE_WAITING_AE_LOCK: runCaptureSequence()");
                    MiCamera2.this.runCaptureSequence();
                    return;
                }
                Log.d(MiCamera2.TAG, "STATE_WAITING_AE_LOCK: keep stay in STATE_WAITING_AE_LOCK");
            } else if (state == 5) {
                Integer num3 = (Integer) captureResult.get(CaptureResult.CONTROL_AE_STATE);
                String access$0004 = MiCamera2.TAG;
                Log.d(access$0004, "STATE_WAITING_AE_CONVERGED:  AF = " + Util.controlAFStateToString((Integer) captureResult.get(CaptureResult.CONTROL_AF_STATE)));
                String access$0005 = MiCamera2.TAG;
                Log.d(access$0005, "STATE_WAITING_AE_CONVERGED:  AE = " + Util.controlAEStateToString(num3));
                String access$0006 = MiCamera2.TAG;
                Log.d(access$0006, "STATE_WAITING_AE_CONVERGED: AWB = " + Util.controlAWBStateToString((Integer) captureResult.get(CaptureResult.CONTROL_AWB_STATE)));
                if (num3 == null && MiCamera2.this.WAITING_AE_STATE_STRICT) {
                    num3 = -1;
                }
                if (num3 != null && num3.intValue() != 2 && num3.intValue() != 4) {
                    Log.d(MiCamera2.TAG, "STATE_WAITING_AE_CONVERGED: keep stay in STATE_WAITING_AE_CONVERGED");
                } else if (!MiCamera2.this.mCapabilities.isAutoFocusSupported() || MiCamera2.this.mConfigs.getFocusMode() == 0) {
                    Log.d(MiCamera2.TAG, "STATE_WAITING_AE_CONVERGED: runCaptureSequence()");
                    MiCamera2.this.runCaptureSequence();
                } else {
                    Log.d(MiCamera2.TAG, "STATE_WAITING_AE_CONVERGED: lockFocus()");
                    MiCamera2.this.lockFocus();
                }
            } else if (state == 6) {
                Integer num4 = (Integer) captureResult.get(CaptureResult.CONTROL_AE_STATE);
                String access$0007 = MiCamera2.TAG;
                Log.d(access$0007, "STATE_WAITING_PRECAPTURE:  AF = " + Util.controlAFStateToString((Integer) captureResult.get(CaptureResult.CONTROL_AF_STATE)));
                String access$0008 = MiCamera2.TAG;
                Log.d(access$0008, "STATE_WAITING_PRECAPTURE:  AE = " + Util.controlAEStateToString(num4));
                String access$0009 = MiCamera2.TAG;
                Log.d(access$0009, "STATE_WAITING_PRECAPTURE: AWB = " + Util.controlAWBStateToString((Integer) captureResult.get(CaptureResult.CONTROL_AWB_STATE)));
                if (num4 == null && MiCamera2.this.WAITING_AE_STATE_STRICT) {
                    num4 = -1;
                }
                if (num4 == null || num4.intValue() == 5 || (num4.intValue() == 4 && !MiCamera2.this.WAITING_AE_STATE_STRICT)) {
                    Log.d(MiCamera2.TAG, "STATE_WAITING_PRECAPTURE: switch to STATE_WAITING_NON_PRECAPTURE(1)");
                    setState(7);
                } else if (MiCamera2.this.mPreCaptureRequestHashCode == captureResult.getRequest().hashCode()) {
                    Log.d(MiCamera2.TAG, "STATE_WAITING_PRECAPTURE: switch to STATE_WAITING_NON_PRECAPTURE(2)");
                    setState(7);
                } else {
                    Log.d(MiCamera2.TAG, "STATE_WAITING_PRECAPTURE: keep stay in STATE_WAITING_PRECAPTURE");
                }
            } else if (state == 7) {
                Integer num5 = (Integer) captureResult.get(CaptureResult.CONTROL_AE_STATE);
                String access$00010 = MiCamera2.TAG;
                Log.d(access$00010, "STATE_WAITING_NON_PRECAPTURE:  AF = " + Util.controlAFStateToString((Integer) captureResult.get(CaptureResult.CONTROL_AF_STATE)));
                String access$00011 = MiCamera2.TAG;
                Log.d(access$00011, "STATE_WAITING_NON_PRECAPTURE:  AE = " + Util.controlAEStateToString(num5));
                String access$00012 = MiCamera2.TAG;
                Log.d(access$00012, "STATE_WAITING_NON_PRECAPTURE: AWB = " + Util.controlAWBStateToString((Integer) captureResult.get(CaptureResult.CONTROL_AWB_STATE)));
                if (num5 == null && MiCamera2.this.WAITING_AE_STATE_STRICT) {
                    num5 = 5;
                }
                if (num5 != null && num5.intValue() == 5) {
                    Log.d(MiCamera2.TAG, "STATE_WAITING_NON_PRECAPTURE: keep stay in STATE_WAITING_NON_PRECAPTURE");
                } else if (MiCamera2.this.needOptimizedFlash() || (b.isMTKPlatform() && !MiCamera2.this.needScreenLight())) {
                    setState(5);
                } else {
                    Log.d(MiCamera2.TAG, "STATE_WAITING_NON_PRECAPTURE: lockExposure()");
                    MiCamera2.this.lockExposure(false);
                }
            }
        }

        private void processVideoRecordStatus(CaptureResult captureResult) {
            synchronized (MiCamera2.this.mVideoRecordStateLock) {
                if (MiCamera2.this.mVideoRecordStateCallback != null) {
                    Integer num = (Integer) VendorTagHelper.getValue(captureResult, CaptureResultVendorTags.VIDEO_RECORD_STATE);
                    if (num != null && 2 == num.intValue()) {
                        MiCamera2.this.mVideoRecordStateCallback.onVideoRecordStopped();
                        Camera2Proxy.VideoRecordStateCallback unused = MiCamera2.this.mVideoRecordStateCallback = null;
                    }
                }
            }
        }

        /* access modifiers changed from: package-private */
        public Integer getCurrentAEState() {
            if (getPreviewCaptureResult() == null) {
                return null;
            }
            return (Integer) getPreviewCaptureResult().get(CaptureResult.CONTROL_AE_STATE);
        }

        /* access modifiers changed from: package-private */
        public int getCurrentColorTemperature() {
            CaptureResult previewCaptureResult = getPreviewCaptureResult();
            if (previewCaptureResult == null) {
                return 0;
            }
            AWBFrameControl aWBFrameControl = CaptureResultParser.getAWBFrameControl(previewCaptureResult);
            if (aWBFrameControl != null) {
                return aWBFrameControl.getColorTemperature();
            }
            return 0;
        }

        /* access modifiers changed from: package-private */
        public FocusTask getFocusTask() {
            return this.mManuallyFocusTask;
        }

        /* access modifiers changed from: package-private */
        public CaptureResult getPreviewCaptureResult() {
            CaptureResult captureResult;
            synchronized (this.mPreviewCaptureResultLock) {
                if (this.mPreviewCaptureResult == null) {
                    String access$000 = MiCamera2.TAG;
                    Log.w(access$000, "returned a null PreviewCaptureResult, mState is " + this.mState);
                }
                captureResult = this.mPreviewCaptureResult;
            }
            return captureResult;
        }

        public int getState() {
            int i;
            synchronized (this.mStateLock) {
                i = this.mState;
            }
            return i;
        }

        /* access modifiers changed from: package-private */
        public void onCapabilityChanged(CameraCapabilities cameraCapabilities) {
            this.mFocusAreaSupported = cameraCapabilities.isAFRegionSupported();
            this.mAELockOnlySupported = DataRepository.dataItemFeature()._a() && !this.mFocusAreaSupported && cameraCapabilities.isAERegionSupported() && cameraCapabilities.isAELockSupported();
        }

        public void onCaptureCompleted(@NonNull CameraCaptureSession cameraCaptureSession, @NonNull CaptureRequest captureRequest, @NonNull TotalCaptureResult totalCaptureResult) {
            if (totalCaptureResult.getFrameNumber() == 0) {
                String access$000 = MiCamera2.TAG;
                Log.d(access$000, "onCaptureCompleted Sequence: " + totalCaptureResult.getSequenceId() + " first frame received");
                MiCamera2.this.triggerDeviceChecking(true, false);
            }
            if (getState() == 0) {
                setState(1);
            }
            if (!this.mPartialResultSupported) {
                processPartial(totalCaptureResult);
            }
            process(totalCaptureResult);
            MiCamera2.this.updateFrameNumber(totalCaptureResult.getFrameNumber());
        }

        public void onCaptureProgressed(@NonNull CameraCaptureSession cameraCaptureSession, @NonNull CaptureRequest captureRequest, @NonNull CaptureResult captureResult) {
            processPartial(captureResult);
        }

        /* access modifiers changed from: package-private */
        public void setFocusTask(FocusTask focusTask) {
            this.mManuallyFocusTask = focusTask;
        }

        /* access modifiers changed from: package-private */
        public void setState(int i) {
            synchronized (this.mStateLock) {
                String access$000 = MiCamera2.TAG;
                Log.d(access$000, "setState: " + i);
                this.mState = i;
            }
        }

        /* access modifiers changed from: package-private */
        public void showAutoFocusFinish(boolean z) {
            if (this.mTorchFocusTask != null) {
                Camera2Proxy.FocusCallback focusCallback = MiCamera2.this.getFocusCallback();
                if (focusCallback != null) {
                    this.mTorchFocusTask.setResult(z);
                    focusCallback.onFocusStateChanged(this.mTorchFocusTask);
                    this.mTorchFocusTask = null;
                }
            }
        }

        /* access modifiers changed from: package-private */
        public void showAutoFocusStart() {
            Camera2Proxy.FocusCallback focusCallback = MiCamera2.this.getFocusCallback();
            if (focusCallback != null) {
                this.mTorchFocusTask = FocusTask.create(3);
                focusCallback.onFocusStateChanged(this.mTorchFocusTask);
            }
        }
    }

    static {
        MeteringRectangle meteringRectangle = new MeteringRectangle(0, 0, 0, 0, 0);
        ZERO_WEIGHT_3A_REGION = new MeteringRectangle[]{meteringRectangle};
    }

    public MiCamera2(CameraDevice cameraDevice, int i, CameraCapabilities cameraCapabilities, @NonNull Handler handler, @NonNull Handler handler2, @NonNull Handler handler3) {
        super(i);
        this.MAX_PARALLEL_REQUEST_NUMBER = b.isMTKPlatform() ? 5 : 10;
        this.AE_STATE_NULL = -1;
        this.WAITING_AE_STATE_STRICT = b.isMTKPlatform();
        this.mVideoRecordStateLock = new Object();
        this.mUltraWideParallelSurfaceIndex = -1;
        this.mWideParallelSurfaceIndex = -1;
        this.mTeleParallelSurfaceIndex = -1;
        this.mQcfaParallelSurfaceIndex = -1;
        this.mSubParallelSurfaceIndex = -1;
        this.mUltraTeleParallelSurfaceIndex = -1;
        this.mLastFrameNum = -1;
        this.mCurrentFrameNum = -1;
        this.mDeferOutputConfigurations = new ArrayList();
        this.mRemoteImageReaderList = new ArrayList();
        this.mSetRepeatingEarly = DataRepository.dataItemFeature().tb();
        this.mSessionLock = new Object();
        this.mMiCamera2ShotQueue = new ConcurrentLinkedDeque<>();
        this.mCaptureTime = 0;
        this.mShotQueueLock = new Object();
        this.mIsCaptureCompleted = true;
        this.mCaptureBusyCallback = null;
        this.mCameraDevice = cameraDevice;
        this.mCapabilities = cameraCapabilities;
        this.mIsCameraClosed = false;
        this.mConfigs = new CameraConfigs();
        this.mSessionConfigs = new CaptureSessionConfigurations(cameraCapabilities);
        this.mCameraHandler = handler;
        this.mCameraPreviewHandler = handler2;
        this.mCameraMainThreadHandler = handler3;
        this.mHelperHandler = initHelperHandler(this.mCameraHandler.getLooper());
        this.mCaptureCallback = new PictureCaptureCallback();
    }

    private void abortCaptures() {
        if (DataRepository.dataItemFeature().yb()) {
            synchronized (this.mSessionLock) {
                if (this.mCaptureSession != null) {
                    try {
                        Log.d(TAG, "abortCaptures E");
                        this.mCaptureSession.abortCaptures();
                        Log.d(TAG, "abortCaptures X");
                    } catch (CameraAccessException e2) {
                        Log.e(TAG, "abortCaptures(): failed", e2);
                    } catch (IllegalStateException e3) {
                        Log.e(TAG, "abortCaptures, IllegalState", e3);
                    }
                }
            }
        }
    }

    private void applyCommonSettings(CaptureRequest.Builder builder, int i) {
        builder.set(CaptureRequest.CONTROL_MODE, 1);
        CaptureRequestBuilder.applyFocusMode(builder, this.mConfigs);
        CaptureRequestBuilder.applyFaceDetection(builder, this.mConfigs);
        CaptureRequestBuilder.applyAntiBanding(builder, this.mConfigs);
        CaptureRequestBuilder.applyExposureCompensation(builder, i, this.mCapabilities, this.mConfigs);
        CaptureRequestBuilder.applyZoomRatio(builder, this.mCapabilities, this.mConfigs);
        CaptureRequestBuilder.applyBeautyValues(builder, this.mCapabilities, this.mConfigs);
        CaptureRequestBuilder.applyAELock(builder, this.mConfigs.isAELocked());
        CaptureRequestBuilder.applyAWBLock(builder, this.mConfigs.isAWBLocked());
        CaptureRequestBuilder.applyEyeLight(builder, this.mCapabilities, this.mConfigs);
        CaptureRequestBuilder.applyWaterMark(builder, i, this.mCapabilities, this.mConfigs);
        if (this.mPreviewControl.needForCapture()) {
            CaptureRequestBuilder.applyContrast(builder, this.mCapabilities, this.mConfigs);
            CaptureRequestBuilder.applySaturation(builder, this.mConfigs);
            CaptureRequestBuilder.applySharpness(builder, this.mConfigs);
            CaptureRequestBuilder.applyExposureMeteringMode(builder, this.mConfigs);
            CaptureRequestBuilder.applySceneMode(builder, this.mConfigs);
            CaptureRequestBuilder.applySuperNightScene(builder, i, this.mCapabilities, this.mConfigs);
            CaptureRequestBuilder.applyHHT(builder, i, this.mCapabilities, this.mConfigs);
            CaptureRequestBuilder.applyHDR(builder, i, this.mCapabilities, this.mConfigs);
            CaptureRequestBuilder.applySuperResolution(builder, i, this.mCapabilities, this.mConfigs, DataRepository.dataItemFeature().Ac());
            CaptureRequestBuilder.applyHwMfnr(builder, i, this.mCapabilities, this.mConfigs);
            CaptureRequestBuilder.applySwMfnr(builder, i, this.mCapabilities, this.mConfigs);
            CaptureRequestBuilder.applyMiBokeh(builder, this.mCapabilities, this.mConfigs);
            CaptureRequestBuilder.applyRearBokeh(builder, this.mCapabilities, this.mConfigs);
            CaptureRequestBuilder.applyFaceAgeAnalyze(builder, this.mCapabilities, this.mConfigs);
            CaptureRequestBuilder.applyFaceScore(builder, this.mCapabilities, this.mConfigs);
            CaptureRequestBuilder.applyFrontMirror(builder, i, this.mCapabilities, this.mConfigs);
            CaptureRequestBuilder.applyCameraAi30Enable(builder, this.mCapabilities, this.mConfigs);
            CaptureRequestBuilder.applyMacroMode(builder, i, this.mCapabilities, this.mConfigs);
            CaptureRequestBuilder.applyUltraPixelPortrait(builder, i, this.mCapabilities, this.mConfigs);
        }
        if (this.mPreviewControl.needForPortrait()) {
            CaptureRequestBuilder.applyContrast(builder, this.mCapabilities, this.mConfigs);
            CaptureRequestBuilder.applySaturation(builder, this.mConfigs);
            CaptureRequestBuilder.applySharpness(builder, this.mConfigs);
            if (this.mPreviewControl.needForFrontCamera()) {
                CaptureRequestBuilder.applyMiBokeh(builder, this.mCapabilities, this.mConfigs);
                CaptureRequestBuilder.applyFrontMirror(builder, i, this.mCapabilities, this.mConfigs);
                CaptureRequestBuilder.applyFaceAgeAnalyze(builder, this.mCapabilities, this.mConfigs);
                CaptureRequestBuilder.applyFaceScore(builder, this.mCapabilities, this.mConfigs);
                CaptureRequestBuilder.applyHwMfnr(builder, i, this.mCapabilities, this.mConfigs);
                CaptureRequestBuilder.applySwMfnr(builder, i, this.mCapabilities, this.mConfigs);
            } else {
                CaptureRequestBuilder.applyMiBokeh(builder, this.mCapabilities, this.mConfigs);
                CaptureRequestBuilder.applyRearBokeh(builder, this.mCapabilities, this.mConfigs);
            }
            if (this.mCapabilities.isMFNRBokehSupported()) {
                CaptureRequestBuilder.applyHwMfnr(builder, i, this.mCapabilities, this.mConfigs);
            }
            CaptureRequestBuilder.applyPortraitLighting(builder, i, this.mCapabilities, this.mConfigs);
            CaptureRequestBuilder.applyFNumber(builder, this.mCapabilities, this.mConfigs);
        }
        if (this.mPreviewControl.needForManually()) {
            CaptureRequestBuilder.applyAWBMode(builder, this.mConfigs.getAWBMode());
            CaptureRequestBuilder.applyCustomAWB(builder, this.mConfigs.getAwbCustomValue());
            CaptureRequestBuilder.applyIso(builder, i, this.mCapabilities, this.mConfigs);
            CaptureRequestBuilder.applyExposureTime(builder, i, this.mConfigs);
        }
        CaptureRequestBuilder.applyFocusDistance(builder, this.mConfigs);
        CaptureRequestBuilder.applyNormalWideLDC(builder, i, this.mCapabilities, this.mConfigs);
        CaptureRequestBuilder.applyUltraWideLDC(builder, this.mCapabilities, this.mConfigs);
    }

    /* JADX WARNING: Code restructure failed: missing block: B:11:0x0042, code lost:
        r0 = 2;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:18:0x005d, code lost:
        if (r13.mPreviewControl.needForCapture() != false) goto L_0x003f;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:21:0x0066, code lost:
        if (useLegacyFlashStrategy() == false) goto L_0x0054;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:9:0x003d, code lost:
        if (r13.mPreviewControl.needForCapture() != false) goto L_0x003f;
     */
    /* JADX WARNING: Removed duplicated region for block: B:25:0x0092  */
    /* JADX WARNING: Removed duplicated region for block: B:26:0x0094  */
    /* JADX WARNING: Removed duplicated region for block: B:32:0x00a3  */
    /* JADX WARNING: Removed duplicated region for block: B:33:0x00a5  */
    /* JADX WARNING: Removed duplicated region for block: B:36:0x00ab  */
    /* JADX WARNING: Removed duplicated region for block: B:77:0x01e5  */
    private void applyFlashMode(CaptureRequest.Builder builder, int i) {
        boolean z;
        Log.d(TAG, "applyFlashMode: request = " + builder + ", applyType = " + i);
        if (builder != null) {
            int flashMode = this.mConfigs.getFlashMode();
            if (i != 3) {
                if (i == 6 && needOptimizedFlash()) {
                }
                z = false;
                Camera2Proxy.ScreenLightCallback screenLightCallback = getScreenLightCallback();
                Log.d(TAG, "applyFlashMode: flashMode = " + flashMode + ", mScreenLightCallback = " + screenLightCallback);
                CaptureRequestBuilder.applyScreenLightHint(this.mCapabilities, builder, flashMode != 101);
                if (!(flashMode == 200 || flashMode == 0)) {
                    CaptureRequestBuilder.applyBackSoftLight(this.mCapabilities, builder, flashMode != 5);
                }
                if (flashMode != 0) {
                    builder.set(CaptureRequest.CONTROL_AE_MODE, 1);
                    builder.set(CaptureRequest.FLASH_MODE, 0);
                    return;
                } else if (flashMode == 1) {
                    builder.set(CaptureRequest.CONTROL_AE_MODE, 3);
                    builder.set(CaptureRequest.FLASH_MODE, 1);
                    return;
                } else if (flashMode == 2) {
                    if (this.mCapabilities.isSupportSnapShotTorch()) {
                        MiCameraCompat.applySnapshotTorch(builder, z);
                    }
                    builder.set(CaptureRequest.CONTROL_AE_MODE, 1);
                    builder.set(CaptureRequest.FLASH_MODE, 2);
                    return;
                } else if (flashMode == 3) {
                    builder.set(CaptureRequest.CONTROL_AE_MODE, 2);
                    if (b.isMTKPlatform()) {
                        builder.set(CaptureRequest.FLASH_MODE, 0);
                        return;
                    } else {
                        builder.set(CaptureRequest.FLASH_MODE, 1);
                        return;
                    }
                } else if (flashMode == 4) {
                    builder.set(CaptureRequest.CONTROL_AE_MODE, 4);
                    return;
                } else if (flashMode == 5) {
                    builder.set(CaptureRequest.CONTROL_AE_MODE, 1);
                    builder.set(CaptureRequest.FLASH_MODE, 2);
                    return;
                } else if (flashMode != 101) {
                    if (flashMode == 103) {
                        Log.d(TAG, "applyFlashMode: FLASH_MODE_SCREEN_LIGHT_AUTO applyType = " + i);
                        if (screenLightCallback != null) {
                            screenLightCallback.stopScreenLight();
                            return;
                        }
                        return;
                    } else if (flashMode == 200) {
                        if (b.isMTKPlatform()) {
                            builder.set(CaptureRequest.CONTROL_AE_MODE, 0);
                        } else {
                            builder.set(CaptureRequest.CONTROL_AE_MODE, 1);
                        }
                        builder.set(CaptureRequest.FLASH_MODE, 0);
                        return;
                    } else {
                        return;
                    }
                } else if (screenLightCallback != null) {
                    if (i == 6) {
                        this.mScreenLightColorTemperature = this.mCaptureCallback.getCurrentColorTemperature();
                    }
                    int screenLightColor = Util.getScreenLightColor(SystemProperties.getInt("camera_screen_light_wb", this.mScreenLightColorTemperature));
                    int screenLightBrightness = CameraSettings.getScreenLightBrightness();
                    int i2 = SystemProperties.getInt("camera_screen_light_delay", 0);
                    Log.d(TAG, "applyFlashMode: FLASH_MODE_SCREEN_LIGHT_ON color = " + screenLightColor + ", brightness = " + screenLightBrightness + ", delay = " + i2 + ", mCameraHandler = " + this.mCameraHandler);
                    if (i == 6 || i == 3) {
                        screenLightCallback.startScreenLight(screenLightColor, screenLightBrightness);
                        return;
                    } else if (i != 7) {
                        return;
                    } else {
                        if (i2 == 0) {
                            screenLightCallback.stopScreenLight();
                            return;
                        } else {
                            this.mCameraHandler.postDelayed(new c(screenLightCallback), (long) i2);
                            return;
                        }
                    }
                } else {
                    return;
                }
            } else {
                if (!needOptimizedFlash()) {
                    if (flashMode == 3) {
                    }
                    z = false;
                    Camera2Proxy.ScreenLightCallback screenLightCallback2 = getScreenLightCallback();
                    Log.d(TAG, "applyFlashMode: flashMode = " + flashMode + ", mScreenLightCallback = " + screenLightCallback2);
                    CaptureRequestBuilder.applyScreenLightHint(this.mCapabilities, builder, flashMode != 101);
                    CaptureRequestBuilder.applyBackSoftLight(this.mCapabilities, builder, flashMode != 5);
                    if (flashMode != 0) {
                    }
                } else if (getExposureTime() <= 0) {
                }
                flashMode = 0;
                z = false;
                Camera2Proxy.ScreenLightCallback screenLightCallback22 = getScreenLightCallback();
                Log.d(TAG, "applyFlashMode: flashMode = " + flashMode + ", mScreenLightCallback = " + screenLightCallback22);
                CaptureRequestBuilder.applyScreenLightHint(this.mCapabilities, builder, flashMode != 101);
                CaptureRequestBuilder.applyBackSoftLight(this.mCapabilities, builder, flashMode != 5);
                if (flashMode != 0) {
                }
            }
            flashMode = 2;
            z = true;
            Camera2Proxy.ScreenLightCallback screenLightCallback222 = getScreenLightCallback();
            Log.d(TAG, "applyFlashMode: flashMode = " + flashMode + ", mScreenLightCallback = " + screenLightCallback222);
            CaptureRequestBuilder.applyScreenLightHint(this.mCapabilities, builder, flashMode != 101);
            CaptureRequestBuilder.applyBackSoftLight(this.mCapabilities, builder, flashMode != 5);
            if (flashMode != 0) {
            }
        }
    }

    private void applySettingsForFocusCapture(CaptureRequest.Builder builder) {
        CaptureRequestBuilder.applyAFRegions(builder, this.mConfigs);
        CaptureRequestBuilder.applyAERegions(builder, this.mConfigs);
        CaptureRequestBuilder.applyZoomRatio(builder, this.mCapabilities, this.mConfigs);
        CaptureRequestBuilder.applyAWBMode(builder, this.mConfigs.getAWBMode());
        CaptureRequestBuilder.applyCustomAWB(builder, this.mConfigs.getAwbCustomValue());
        CaptureRequestBuilder.applyExposureCompensation(builder, 1, this.mCapabilities, this.mConfigs);
        CaptureRequestBuilder.applyAntiShake(builder, this.mCapabilities, this.mConfigs);
        if (this.mPreviewControl.needForCapture()) {
            CaptureRequestBuilder.applyContrast(builder, this.mCapabilities, this.mConfigs);
            CaptureRequestBuilder.applySaturation(builder, this.mConfigs);
            CaptureRequestBuilder.applySharpness(builder, this.mConfigs);
        }
        if (this.mPreviewControl.needForManually()) {
            CaptureRequestBuilder.applyIso(builder, 1, this.mCapabilities, this.mConfigs);
            CaptureRequestBuilder.applyExposureTime(builder, 1, this.mConfigs);
        }
        if (this.mPreviewControl.needForVideo()) {
            CaptureRequestBuilder.applyVideoFpsRange(builder, this.mConfigs);
        }
        builder.set(CaptureRequest.CONTROL_AF_MODE, 1);
        builder.set(CaptureRequest.CONTROL_AF_TRIGGER, 0);
        applyFlashMode(builder, 1);
        CaptureRequestBuilder.applyFpsRange(builder, this.mConfigs);
        CaptureRequestBuilder.applyUltraWideLDC(builder, this.mCapabilities, this.mConfigs);
    }

    private void applySettingsForLockFocus(CaptureRequest.Builder builder) {
        builder.set(CaptureRequest.CONTROL_AF_TRIGGER, 1);
        CaptureRequestBuilder.applyAFRegions(builder, this.mConfigs);
        CaptureRequestBuilder.applyAERegions(builder, this.mConfigs);
        CaptureRequestBuilder.applyFpsRange(builder, this.mConfigs);
        applyCommonSettings(builder, 1);
        if (!useLegacyFlashStrategy()) {
            builder.set(CaptureRequest.CONTROL_AF_MODE, 1);
        }
        if (needOptimizedFlash() || needScreenLight() || b.isMTKPlatform()) {
            applyFlashMode(builder, 6);
        }
        CaptureRequestBuilder.applySessionParameters(builder, this.mSessionConfigs);
    }

    private void applySettingsForPreCapture(CaptureRequest.Builder builder) {
        applyCommonSettings(builder, 1);
        applyFlashMode(builder, 6);
        builder.set(CaptureRequest.CONTROL_AE_PRECAPTURE_TRIGGER, 1);
        CaptureRequestBuilder.applySessionParameters(builder, this.mSessionConfigs);
    }

    private void applySettingsForPreview(CaptureRequest.Builder builder) {
        String str = TAG;
        Log.d(str, "applySettingsForPreview: " + builder);
        if (builder != null) {
            applyFlashMode(builder, 1);
            applyCommonSettings(builder, 1);
            CaptureRequestBuilder.applyLensDirtyDetect(builder, this.mCapabilities, this.mConfigs);
            CaptureRequestBuilder.applyAELock(builder, this.mConfigs.isAELocked());
            CaptureRequestBuilder.applyAWBLock(builder, this.mConfigs.isAWBLocked());
            CaptureRequestBuilder.applyAntiShake(builder, this.mCapabilities, this.mConfigs);
            builder.set(CaptureRequest.CONTROL_AF_TRIGGER, 0);
            CaptureRequestBuilder.applySessionParameters(builder, this.mSessionConfigs);
        }
    }

    /* access modifiers changed from: private */
    public void applySettingsForVideo(CaptureRequest.Builder builder) {
        if (builder != null) {
            builder.set(CaptureRequest.CONTROL_MODE, 1);
            applyFlashMode(builder, 1);
            CaptureRequestBuilder.applyLensDirtyDetect(builder, this.mCapabilities, this.mConfigs);
            CaptureRequestBuilder.applyFocusMode(builder, this.mConfigs);
            CaptureRequestBuilder.applyFaceDetection(builder, this.mConfigs);
            CaptureRequestBuilder.applyAntiBanding(builder, this.mConfigs);
            CaptureRequestBuilder.applyExposureMeteringMode(builder, this.mConfigs);
            CaptureRequestBuilder.applyExposureCompensation(builder, 1, this.mCapabilities, this.mConfigs);
            CaptureRequestBuilder.applyZoomRatio(builder, this.mCapabilities, this.mConfigs);
            CaptureRequestBuilder.applyAntiShake(builder, this.mCapabilities, this.mConfigs);
            CaptureRequestBuilder.applyAELock(builder, this.mConfigs.isAELocked());
            CaptureRequestBuilder.applyBeautyValues(builder, this.mCapabilities, this.mConfigs);
            CaptureRequestBuilder.applyVideoFpsRange(builder, this.mConfigs);
            CaptureRequestBuilder.applyFrontMirror(builder, 3, this.mCapabilities, this.mConfigs);
            CaptureRequestBuilder.applyDeviceOrientation(builder, this.mCapabilities, this.mConfigs);
            CaptureRequestBuilder.applyUltraWideLDC(builder, this.mCapabilities, this.mConfigs);
            CaptureRequestBuilder.applyMacroMode(builder, 1, this.mCapabilities, this.mConfigs);
            CaptureRequestBuilder.applyHFRDeflicker(builder, 1, this.mCapabilities, this.mConfigs);
            CaptureRequestBuilder.applySessionParameters(builder, this.mSessionConfigs);
        }
    }

    private void assertRemoteSurfaceIndexIsValid(int i) {
        if (i < 0 || i > this.mParallelCaptureSurfaceList.size() - 1) {
            throw new RuntimeException("invalid remote surface index " + i);
        }
    }

    private int capture(CaptureRequest captureRequest, CameraCaptureSession.CaptureCallback captureCallback, Handler handler) throws CameraAccessException {
        synchronized (this.mSessionLock) {
            if (this.mCaptureSession == null) {
                Log.w(TAG, "capture: null session");
                return 0;
            } else if (this.mCaptureSession instanceof CameraConstrainedHighSpeedCaptureSession) {
                int captureBurst = this.mCaptureSession.captureBurst(createHighSpeedRequestList(captureRequest), captureCallback, handler);
                return captureBurst;
            } else {
                int capture = this.mCaptureSession.capture(captureRequest, captureCallback, handler);
                return capture;
            }
        }
    }

    /* access modifiers changed from: private */
    public void captureStillPicture() {
        if (checkCaptureSession(CameraStat.CATEGORY_CAMERA)) {
            MiCamera2Shot miCamera2Shot = null;
            switch (this.mConfigs.getShotType()) {
                case Constants.ShotType.INTENT_PARALLEL_DUAL_SHOT /*-7*/:
                case Constants.ShotType.INTENT_PARALLEL_SINGLE_PORTRAIT /*-6*/:
                case Constants.ShotType.INTENT_PARALLEL_SINGLE_SHOT /*-5*/:
                case 5:
                case 6:
                case 7:
                    miCamera2Shot = new MiCamera2ShotParallelStill(this, this.mCaptureCallback.getPreviewCaptureResult());
                    miCamera2Shot.setQuickShotAnimation(this.mConfigs.isQuickShotAnimation());
                    break;
                case -3:
                case -2:
                case 0:
                case 1:
                case 2:
                    miCamera2Shot = new MiCamera2ShotStill(this);
                    miCamera2Shot.setQuickShotAnimation(this.mConfigs.isQuickShotAnimation());
                    break;
                case -1:
                    miCamera2Shot = new MiCamera2ShotPreview(this, this.mCaptureCallback.getPreviewCaptureResult());
                    break;
                case 8:
                    miCamera2Shot = new MiCamera2ShotParallelBurst(this, this.mCaptureCallback.getPreviewCaptureResult());
                    break;
                case 10:
                    if (this.mSuperNightReprocessHandler == null) {
                        HandlerThread handlerThread = new HandlerThread("SNReprocessThread");
                        handlerThread.start();
                        this.mSuperNightReprocessHandler = new SuperNightReprocessHandler(handlerThread.getLooper(), this);
                    }
                    String str = TAG;
                    Log.d(str, "SuperNightReprocessHandler@" + this.mSuperNightReprocessHandler.hashCode());
                    miCamera2Shot = new MiCamera2ShotRawBurst(this, this.mSuperNightReprocessHandler);
                    miCamera2Shot.setQuickShotAnimation(this.mConfigs.isQuickShotAnimation());
                    break;
            }
            if (this.mConfigs.isNeedSequence()) {
                if (this.mMiCamera2ShotQueue.offerLast(miCamera2Shot)) {
                    this.mCaptureTime = System.currentTimeMillis();
                    String str2 = TAG;
                    Log.d(str2, "capture: mMiCamera2ShotQueue.offer, size: " + this.mMiCamera2ShotQueue.size());
                } else {
                    String str3 = TAG;
                    Log.e(str3, "capture: mMiCamera2ShotQueue.offer failure, size: " + this.mMiCamera2ShotQueue.size());
                }
            }
            this.mMiCamera2Shot = miCamera2Shot;
            if (this.mMiCamera2Shot != null) {
                String str4 = TAG;
                Log.d(str4, "startShot holder: " + this.mMiCamera2Shot.hashCode());
                this.mMiCamera2Shot.setPictureCallback(getPictureCallback());
                this.mMiCamera2Shot.setParallelCallback(getParallelCallback());
                this.mMiCamera2Shot.startShot();
                triggerDeviceChecking(true, true);
            }
        }
    }

    private boolean checkCameraDevice(String str) {
        if (this.mCameraDevice != null) {
            return true;
        }
        String str2 = "camera " + getId() + " is closed when " + str;
        if (this.mIsCameraClosed) {
            Log.d(TAG, str2);
            return false;
        }
        RuntimeException runtimeException = new RuntimeException(str2);
        if (!Build.IS_DEBUGGABLE) {
            Log.w(TAG, str2, runtimeException);
            return false;
        }
        throw runtimeException;
    }

    private boolean checkCaptureSession(String str) {
        synchronized (this.mSessionLock) {
            if (this.mCaptureSession != null) {
                return true;
            }
            String str2 = "session for camera " + getId() + " is closed when " + str;
            if (this.mIsCaptureSessionClosed) {
                Log.d(TAG, str2);
                return false;
            }
            RuntimeException runtimeException = new RuntimeException(str2);
            if (!Build.IS_DEBUGGABLE) {
                Log.w(TAG, str2, runtimeException);
                return false;
            }
            throw runtimeException;
        }
    }

    private void closeDepthImageReader() {
        ImageReader imageReader = this.mDepthReader;
        if (imageReader != null) {
            imageReader.close();
            this.mDepthReader = null;
        }
    }

    private void closePhotoImageReader() {
        ImageReader imageReader = this.mPhotoImageReader;
        if (imageReader != null) {
            imageReader.close();
            this.mPhotoImageReader = null;
        }
    }

    private void closePortraitRawImageReader() {
        ImageReader imageReader = this.mPortraitRawImageReader;
        if (imageReader != null) {
            imageReader.close();
            this.mPortraitRawImageReader = null;
        }
    }

    private void closePreviewImageReader() {
        ImageReader imageReader = this.mPreviewImageReader;
        if (imageReader != null) {
            imageReader.close();
            this.mPreviewImageReader = null;
        }
    }

    private void closeRawImageReader() {
        ImageReader imageReader = this.mRawImageReader;
        if (imageReader != null) {
            imageReader.close();
            this.mRawImageReader = null;
        }
    }

    private void closeVideoSnapshotImageReader() {
        ImageReader imageReader = this.mVideoSnapshotImageReader;
        if (imageReader != null) {
            imageReader.close();
            this.mVideoSnapshotImageReader = null;
        }
    }

    private void configMaxParallelRequestNumberLock() {
        LocalParallelService.LocalBinder localBinder = AlgoConnector.getInstance().getLocalBinder(true);
        if (localBinder != null) {
            int Ia = DataRepository.dataItemFeature().Ia();
            if (Ia <= 0 || DataRepository.dataItemGlobal().getCurrentCameraId() != 1) {
                localBinder.configMaxParallelRequestNumber(this.MAX_PARALLEL_REQUEST_NUMBER);
            } else {
                localBinder.configMaxParallelRequestNumber(Ia);
            }
        }
    }

    private List<CaptureRequest> createHighSpeedRequestList(CaptureRequest captureRequest) {
        if (captureRequest != null) {
            Collection targets = captureRequest.getTargets();
            Range range = (Range) captureRequest.get(CaptureRequest.CONTROL_AE_TARGET_FPS_RANGE);
            String str = TAG;
            Log.d(str, "createHighSpeedRequestList() fpsRange = " + range);
            int intValue = ((Integer) range.getUpper()).intValue() / 30;
            ArrayList arrayList = new ArrayList();
            CaptureRequest.Builder constructCaptureRequestBuilder = CompatibilityUtils.constructCaptureRequestBuilder(new CameraMetadataNative(captureRequest.getNativeCopy()), false, -1, captureRequest);
            Iterator it = targets.iterator();
            Surface surface = (Surface) it.next();
            if (targets.size() != 1 || SurfaceUtils.isSurfaceForHwVideoEncoder(surface)) {
                constructCaptureRequestBuilder.set(CaptureRequest.CONTROL_CAPTURE_INTENT, 3);
            } else {
                constructCaptureRequestBuilder.set(CaptureRequest.CONTROL_CAPTURE_INTENT, 1);
            }
            constructCaptureRequestBuilder.setPartOfCHSRequestList(true);
            CaptureRequest.Builder builder = null;
            if (targets.size() == 2) {
                builder = CompatibilityUtils.constructCaptureRequestBuilder(new CameraMetadataNative(captureRequest.getNativeCopy()), false, -1, captureRequest);
                builder.set(CaptureRequest.CONTROL_CAPTURE_INTENT, 3);
                builder.addTarget(surface);
                Surface surface2 = (Surface) it.next();
                builder.addTarget(surface2);
                builder.setPartOfCHSRequestList(true);
                if (SurfaceUtils.isSurfaceForHwVideoEncoder(surface)) {
                    surface2 = surface;
                }
                constructCaptureRequestBuilder.addTarget(surface2);
            } else {
                constructCaptureRequestBuilder.addTarget(surface);
            }
            for (int i = 0; i < intValue; i++) {
                if (i != 0 || builder == null) {
                    arrayList.add(constructCaptureRequestBuilder.build());
                } else {
                    arrayList.add(builder.build());
                }
            }
            return Collections.unmodifiableList(arrayList);
        }
        throw new IllegalArgumentException("Input capture request must not be null");
    }

    private int genSessionId() {
        int i = this.mSessionId + 1;
        this.mSessionId = i;
        if (i == Integer.MAX_VALUE) {
            this.mSessionId = 0;
        }
        String str = TAG;
        Log.v(str, "generateSessionId: id=" + this.mSessionId);
        return this.mSessionId;
    }

    private long getCaptureInterval() {
        long Ma = DataRepository.dataItemFeature().Ma() - (System.currentTimeMillis() - this.mCaptureTime);
        if (this.mConfigs.isHDREnabled() || this.mConfigs.isHDRCheckerEnabled()) {
            Ma += 800;
        }
        if (CameraSettings.isUltraPixelFront32MPOn()) {
            Ma += 600;
        }
        if (this.mConfigs.isMiBokehEnabled()) {
            Ma += 800;
        }
        String str = TAG;
        Log.d(str, "getCaptureInterval: return " + Ma);
        if (Ma > 0) {
            return Ma;
        }
        return 0;
    }

    private long getExposureTime() {
        return this.mConfigs.getExposureTime();
    }

    private Surface getRemoteSurface(int i) {
        assertRemoteSurfaceIndexIsValid(i);
        return this.mParallelCaptureSurfaceList.get(i);
    }

    private CaptureRequest.Builder initFocusRequestBuilder(int i) throws CameraAccessException {
        if (i == 160) {
            throw new IllegalArgumentException("Module index is error!");
        } else if (i == 166) {
            String str = TAG;
            Log.e(str, "initFocusRequestBuilder: error caller for " + i);
            return null;
        } else {
            CameraDevice cameraDevice = this.mCameraDevice;
            if (cameraDevice == null) {
                return null;
            }
            CaptureRequest.Builder createCaptureRequest = (i == 162 || i == 169 || i == 172) ? this.mCameraDevice.createCaptureRequest(3) : cameraDevice.createCaptureRequest(1);
            createCaptureRequest.addTarget(this.mPreviewSurface);
            if (isHighSpeedRecording()) {
                createCaptureRequest.addTarget(this.mRecordSurface);
                createCaptureRequest.set(CaptureRequest.CONTROL_AE_TARGET_FPS_RANGE, this.mHighSpeedFpsRange);
            }
            CaptureRequestBuilder.applySessionParameters(createCaptureRequest, this.mSessionConfigs);
            return createCaptureRequest;
        }
    }

    private Handler initHelperHandler(Looper looper) {
        return new Handler(looper) {
            public void handleMessage(Message message) {
                int i = message.what;
                if (i == 1) {
                    Log.e(MiCamera2.TAG, "waiting af lock timeOut");
                    MiCamera2.this.runCaptureSequence();
                } else if (i == 2) {
                    MiCamera2 miCamera2 = MiCamera2.this;
                    boolean updateDeferPreviewSession = miCamera2.updateDeferPreviewSession(miCamera2.mPreviewSurface);
                    String access$000 = MiCamera2.TAG;
                    Log.d(access$000, "handleMessage: MSG_WAITING_LOCAL_PARALLEL_SERVICE_READY updateDeferPreviewSession result = " + updateDeferPreviewSession);
                } else if (i == 3) {
                    if (message.arg1 == 1 && MiCamera2.this.mPreviewControl.needForManually() && MiCamera2.this.mConfigs.getExposureTime() / 1000000 >= 5000) {
                        removeMessages(3);
                        sendEmptyMessageDelayed(3, (MiCamera2.this.mConfigs.getExposureTime() / 1000000) + 5000);
                    } else if (message.arg1 != 0) {
                    } else {
                        if (MiCamera2.this.isDeviceAlive()) {
                            sendEmptyMessageDelayed(3, 5000);
                        } else {
                            MiCamera2.this.notifyOnError(238);
                        }
                    }
                }
            }
        };
    }

    /* access modifiers changed from: private */
    public boolean isDeviceAlive() {
        long j = this.mCurrentFrameNum;
        if (0 > j || j != this.mLastFrameNum) {
            this.mLastFrameNum = this.mCurrentFrameNum;
            return true;
        }
        String str = TAG;
        Log.e(str, "camera device maybe dead, current framenum is " + this.mLastFrameNum);
        return false;
    }

    private boolean isHighSpeedRecording() {
        if (this.mCaptureSession instanceof CameraConstrainedHighSpeedCaptureSession) {
            return true;
        }
        CaptureSessionConfigurations captureSessionConfigurations = this.mSessionConfigs;
        return (captureSessionConfigurations == null || ((int[]) captureSessionConfigurations.get(CaptureRequestVendorTags.SMVR_MODE)) == null) ? false : true;
    }

    private boolean isLocalParallelServiceReady() {
        return !this.mEnableParallelSession || AlgoConnector.getInstance().getLocalBinder() != null;
    }

    /* access modifiers changed from: private */
    public void lockFocus() {
        if (checkCaptureSession("lockFocus")) {
            if (this.mCaptureCallback.getFocusTask() == null || !useLegacyFlashStrategy()) {
                Log.v(TAG, "lockFocus");
                try {
                    CaptureRequest.Builder createCaptureRequest = this.mCameraDevice.createCaptureRequest(1);
                    createCaptureRequest.addTarget(this.mPreviewSurface);
                    applySettingsForLockFocus(createCaptureRequest);
                    CaptureRequest build = createCaptureRequest.build();
                    this.mFocusLockRequestHashCode = build.hashCode();
                    this.mCaptureCallback.setState(3);
                    this.mCaptureCallback.showAutoFocusStart();
                    capture(build, this.mCaptureCallback, this.mCameraHandler);
                    if (!useLegacyFlashStrategy()) {
                        setAFModeToPreview(1);
                    }
                    if (this.mHelperHandler != null) {
                        this.mHelperHandler.removeMessages(1);
                        this.mHelperHandler.sendEmptyMessageDelayed(1, useLegacyFlashStrategy() ? 4000 : TIME_WAITING_LOCK_AF_TORCH);
                    }
                } catch (CameraAccessException | IllegalStateException e2) {
                    e2.printStackTrace();
                    notifyOnError(-1);
                }
            } else {
                this.mFocusLockRequestHashCode = 0;
                this.mCaptureCallback.setState(3);
            }
        }
    }

    /* access modifiers changed from: private */
    public boolean needOptimizedFlash() {
        return this.mConfigs.isNeedFlash() && (this.mConfigs.getFlashMode() == 1 || this.mConfigs.getFlashMode() == 3 || getExposureTime() > 0) && !useLegacyFlashStrategy();
    }

    /* access modifiers changed from: private */
    public boolean needScreenLight() {
        return this.mConfigs.isNeedFlash() && this.mConfigs.getFlashMode() == 101;
    }

    private boolean needUnlockFocusAfterCapture() {
        return useLegacyFlashStrategy() && 2 != this.mConfigs.getFlashMode();
    }

    /* access modifiers changed from: private */
    public void notifyCaptureBusyCallback(boolean z) {
        boolean z2;
        synchronized (this.mShotQueueLock) {
            if (this.mMiCamera2ShotQueue != null) {
                if (!this.mMiCamera2ShotQueue.isEmpty()) {
                    z2 = false;
                }
            }
            z2 = true;
        }
        Camera2Proxy.CaptureBusyCallback captureBusyCallback = this.mCaptureBusyCallback;
        if (captureBusyCallback != null && z2) {
            captureBusyCallback.onCaptureCompleted(z);
            this.mCaptureBusyCallback = null;
        }
    }

    private void prepareDepthImageReader(CameraSize cameraSize) {
        closeDepthImageReader();
        AnonymousClass6 r0 = new ImageReader.OnImageAvailableListener() {
            public void onImageAvailable(ImageReader imageReader) {
                Image acquireNextImage = imageReader.acquireNextImage();
                if (acquireNextImage != null) {
                    if (MiCamera2.this.mMiCamera2Shot == null) {
                        Log.w(MiCamera2.TAG, "onImageAvailable: NO depth image processor!");
                        acquireNextImage.close();
                        return;
                    }
                    MiCamera2.this.mMiCamera2Shot.onImageReceived(acquireNextImage, 2);
                }
            }
        };
        this.mDepthReader = ImageReader.newInstance(cameraSize.getWidth() / 2, cameraSize.getHeight() / 2, 540422489, 2);
        this.mDepthReader.setOnImageAvailableListener(r0, this.mCameraHandler);
    }

    private void preparePhotoImageReader() {
        preparePhotoImageReader(this.mConfigs.getPhotoSize(), this.mConfigs.getPhotoFormat(), this.mConfigs.getPhotoMaxImages());
    }

    private void preparePhotoImageReader(@NonNull CameraSize cameraSize, int i, int i2) {
        closePhotoImageReader();
        this.mPhotoImageReader = ImageReader.newInstance(cameraSize.getWidth(), cameraSize.getHeight(), i, i2);
        this.mPhotoImageReader.setOnImageAvailableListener(new ImageReader.OnImageAvailableListener() {
            public void onImageAvailable(ImageReader imageReader) {
                MiCamera2Shot miCamera2Shot;
                Log.d(MiCamera2.TAG, "onImageAvailable: main");
                Image acquireNextImage = imageReader.acquireNextImage();
                if (acquireNextImage == null) {
                    Log.w(MiCamera2.TAG, "onImageAvailable: null image");
                    return;
                }
                synchronized (MiCamera2.this.mShotQueueLock) {
                    if (!MiCamera2.this.mMiCamera2ShotQueue.isEmpty()) {
                        miCamera2Shot = (MiCamera2Shot) MiCamera2.this.mMiCamera2ShotQueue.peek();
                        if (!(miCamera2Shot instanceof MiCamera2ShotStill)) {
                            MiCamera2.this.mMiCamera2ShotQueue.removeFirst();
                        } else if (acquireNextImage.getTimestamp() != ((MiCamera2ShotStill) miCamera2Shot).getTimeStamp()) {
                            miCamera2Shot = MiCamera2.this.replaceCorrectShot(acquireNextImage);
                        } else {
                            MiCamera2.this.mMiCamera2ShotQueue.removeFirst();
                        }
                        String access$000 = MiCamera2.TAG;
                        Log.d(access$000, "onImageAvailable: mMiCamera2ShotQueue.poll, size:" + MiCamera2.this.mMiCamera2ShotQueue.size());
                        MiCamera2.this.notifyCaptureBusyCallback(true);
                    } else {
                        miCamera2Shot = MiCamera2.this.mMiCamera2Shot;
                    }
                }
                if (miCamera2Shot != null) {
                    miCamera2Shot.onImageReceived(acquireNextImage, 0);
                    return;
                }
                acquireNextImage.close();
                Log.w(MiCamera2.TAG, "onImageAvailable: NO main image processor!");
            }
        }, this.mCameraHandler);
    }

    private void preparePortraitRawImageReader(CameraSize cameraSize) {
        closePortraitRawImageReader();
        AnonymousClass7 r0 = new ImageReader.OnImageAvailableListener() {
            public void onImageAvailable(ImageReader imageReader) {
                Image acquireNextImage = imageReader.acquireNextImage();
                if (acquireNextImage != null) {
                    if (MiCamera2.this.mMiCamera2Shot == null) {
                        Log.w(MiCamera2.TAG, "onImageAvailable: NO portrait image processor!");
                        acquireNextImage.close();
                        return;
                    }
                    MiCamera2.this.mMiCamera2Shot.onImageReceived(acquireNextImage, 1);
                }
            }
        };
        this.mPortraitRawImageReader = ImageReader.newInstance(cameraSize.getWidth(), cameraSize.getHeight(), 256, 2);
        this.mPortraitRawImageReader.setOnImageAvailableListener(r0, this.mCameraHandler);
    }

    private void preparePreviewImageReader() {
        preparePreviewImageReader(this.mConfigs.getAlgorithmPreviewSize(), this.mConfigs.getAlgorithmPreviewFormat(), this.mConfigs.getPreviewMaxImages());
    }

    private void preparePreviewImageReader(@NonNull CameraSize cameraSize, int i, int i2) {
        closePreviewImageReader();
        this.mPreviewImageReader = ImageReader.newInstance(cameraSize.getWidth(), cameraSize.getHeight(), i, i2);
        this.mPreviewImageReader.setOnImageAvailableListener(new d(this), this.mCameraPreviewHandler);
    }

    private void prepareRawImageReader(@NonNull CameraSize cameraSize, boolean z) {
        closeRawImageReader();
        AnonymousClass3 r0 = new ImageReader.OnImageAvailableListener() {
            public void onImageAvailable(ImageReader imageReader) {
                Image acquireNextImage = imageReader.acquireNextImage();
                if (acquireNextImage != null) {
                    if (MiCamera2.this.mMiCamera2Shot == null) {
                        Log.w(MiCamera2.TAG, "onImageAvailable: NO raw image processor!");
                        acquireNextImage.close();
                        return;
                    }
                    MiCamera2.this.mMiCamera2Shot.onImageReceived(acquireNextImage, 3);
                }
            }
        };
        this.mRawImageReader = ImageReader.newInstance(cameraSize.getWidth(), cameraSize.getHeight(), 32, z ? 10 : 2);
        this.mRawImageReader.setOnImageAvailableListener(r0, this.mCameraHandler);
    }

    /* access modifiers changed from: private */
    public void prepareRawImageWriter(@NonNull CameraSize cameraSize, @NonNull Surface surface) {
        ImageWriter imageWriter = this.mRawImageWriter;
        if (imageWriter != null) {
            imageWriter.close();
        }
        AnonymousClass4 r2 = new ImageWriter.OnImageReleasedListener() {
            public void onImageReleased(ImageWriter imageWriter) {
                Log.d(MiCamera2.TAG, "The enqueued imaged has be consumed");
            }
        };
        this.mRawImageWriter = ImageWriter.newInstance(surface, 2);
        this.mRawImageWriter.setOnImageReleasedListener(r2, this.mCameraHandler);
    }

    private List<Surface> prepareRemoteImageReader(@Nullable List<IImageReaderParameterSets> list) {
        int i;
        if (list == null || list.size() == 0) {
            List<IImageReaderParameterSets> arrayList = list == null ? new ArrayList<>() : list;
            if (isIn3OrMoreSatMode()) {
                CameraSize ultraWidePhotoSize = this.mConfigs.getUltraWidePhotoSize();
                IImageReaderParameterSets iImageReaderParameterSets = new IImageReaderParameterSets(ultraWidePhotoSize.getWidth(), ultraWidePhotoSize.getHeight(), 35, MAX_IMAGE_BUFFER_SIZE, 0);
                arrayList.add(iImageReaderParameterSets);
                this.mUltraWideParallelSurfaceIndex = 0;
                CameraSize widePhotoSize = this.mConfigs.getWidePhotoSize();
                IImageReaderParameterSets iImageReaderParameterSets2 = new IImageReaderParameterSets(widePhotoSize.getWidth(), widePhotoSize.getHeight(), 35, MAX_IMAGE_BUFFER_SIZE, 0);
                arrayList.add(iImageReaderParameterSets2);
                this.mWideParallelSurfaceIndex = 1;
                CameraSize telePhotoSize = this.mConfigs.getTelePhotoSize();
                IImageReaderParameterSets iImageReaderParameterSets3 = new IImageReaderParameterSets(telePhotoSize.getWidth(), telePhotoSize.getHeight(), 35, MAX_IMAGE_BUFFER_SIZE, 0);
                arrayList.add(iImageReaderParameterSets3);
                this.mTeleParallelSurfaceIndex = 2;
                i = 4;
                if (DataRepository.dataItemFeature().ec()) {
                    CameraSize standalonePhotoSize = this.mConfigs.getStandalonePhotoSize();
                    IImageReaderParameterSets iImageReaderParameterSets4 = r11;
                    IImageReaderParameterSets iImageReaderParameterSets5 = new IImageReaderParameterSets(standalonePhotoSize.getWidth(), standalonePhotoSize.getHeight(), 35, MAX_IMAGE_BUFFER_SIZE, 0);
                    arrayList.add(iImageReaderParameterSets4);
                    this.mUltraTeleParallelSurfaceIndex = 3;
                    Log.d(TAG, String.format(Locale.ENGLISH, "[4SAT]prepareRemoteImageReader:uwSize = %s wideSize = %s teleSize = %s ultraTeleSize =%s", new Object[]{ultraWidePhotoSize, widePhotoSize, telePhotoSize, standalonePhotoSize}));
                } else {
                    Log.d(TAG, String.format(Locale.ENGLISH, "[3SAT]prepareRemoteImageReader:uwSize = %s wideSize = %s teleSize = %s", new Object[]{ultraWidePhotoSize, widePhotoSize, telePhotoSize}));
                    i = 3;
                }
            } else if (!"tucana".equals(b.km) || !this.mCapabilities.isQcfaMode()) {
                CameraSize photoSize = this.mConfigs.getPhotoSize();
                IImageReaderParameterSets iImageReaderParameterSets6 = new IImageReaderParameterSets(photoSize.getWidth(), photoSize.getHeight(), 35, MAX_IMAGE_BUFFER_SIZE, 0);
                arrayList.add(iImageReaderParameterSets6);
                this.mWideParallelSurfaceIndex = 0;
                Log.d(TAG, "prepareRemoteImageReader: mainSize = " + photoSize);
                i = 1;
            } else {
                i = 0;
            }
            if (this.mConfigs.isParallelDualShotType()) {
                CameraSize subPhotoSize = this.mConfigs.getSubPhotoSize();
                IImageReaderParameterSets iImageReaderParameterSets7 = new IImageReaderParameterSets(subPhotoSize.getWidth(), subPhotoSize.getHeight(), 35, MAX_IMAGE_BUFFER_SIZE, 1);
                arrayList.add(iImageReaderParameterSets7);
                this.mSubParallelSurfaceIndex = i;
                Log.d(TAG, "prepareRemoteImageReader: subSize = " + subPhotoSize);
            } else if (isQcfaEnable() && !alwaysUseRemosaicSize()) {
                int width = this.mConfigs.getPhotoSize().getWidth() / 2;
                int height = this.mConfigs.getPhotoSize().getHeight() / 2;
                Log.d(TAG, "prepareRemoteImageReader: qcfaSize = " + width + "x" + height);
                IImageReaderParameterSets iImageReaderParameterSets8 = new IImageReaderParameterSets(width, height, 35, MAX_IMAGE_BUFFER_SIZE, 0);
                iImageReaderParameterSets8.setShouldHoldImages(false);
                arrayList.add(iImageReaderParameterSets8);
                this.mQcfaParallelSurfaceIndex = i;
            }
            LocalParallelService.LocalBinder localBinder = AlgoConnector.getInstance().getLocalBinder(true);
            if (localBinder == null) {
                Log.d(TAG, "prepareRemoteImageReader: ParallelService is not ready");
                ArrayList arrayList2 = new ArrayList();
                for (IImageReaderParameterSets next : arrayList) {
                    ImageReader newInstance = ImageReader.newInstance(next.width, next.height, next.format, next.maxImages);
                    arrayList2.add(newInstance.getSurface());
                    this.mRemoteImageReaderList.add(newInstance);
                }
                return arrayList2;
            }
            try {
                Log.d(TAG, "prepareRemoteImageReader: configurations: " + arrayList);
                List<Surface> configCaptureOutputBuffer = localBinder.configCaptureOutputBuffer(arrayList);
                if (configCaptureOutputBuffer != null) {
                    return configCaptureOutputBuffer;
                }
                throw new RemoteException("Config capture output buffer failed!");
            } catch (RemoteException e2) {
                e2.printStackTrace();
                return null;
            }
        } else {
            throw new IllegalArgumentException("The given \"params\" should be null or an empty list");
        }
    }

    private void prepareVideoSnapshotImageReader(CameraSize cameraSize) {
        closeVideoSnapshotImageReader();
        AnonymousClass5 r0 = new ImageReader.OnImageAvailableListener() {
            public void onImageAvailable(ImageReader imageReader) {
                Image acquireNextImage = imageReader.acquireNextImage();
                if (acquireNextImage != null) {
                    if (MiCamera2.this.mMiCamera2Shot == null) {
                        Log.w(MiCamera2.TAG, "onImageAvailable: NO video image processor!");
                        acquireNextImage.close();
                        return;
                    }
                    MiCamera2.this.mMiCamera2Shot.onImageReceived(acquireNextImage, 0);
                }
            }
        };
        this.mVideoSnapshotImageReader = ImageReader.newInstance(cameraSize.getWidth(), cameraSize.getHeight(), 256, 2);
        this.mVideoSnapshotImageReader.setOnImageAvailableListener(r0, this.mCameraHandler);
    }

    /* access modifiers changed from: private */
    public MiCamera2Shot replaceCorrectShot(Image image) {
        Iterator<MiCamera2Shot> it = this.mMiCamera2ShotQueue.iterator();
        while (it.hasNext()) {
            MiCamera2Shot next = it.next();
            if ((next instanceof MiCamera2ShotStill) && ((MiCamera2ShotStill) next).getTimeStamp() == image.getTimestamp()) {
                it.remove();
                return next;
            }
        }
        return this.mMiCamera2ShotQueue.pollFirst();
    }

    private void reset() {
        Log.v(TAG, "E: reset");
        this.mIsCaptureSessionClosed = true;
        synchronized (this.mSessionLock) {
            this.mCaptureSession = null;
        }
        this.mCameraDevice = null;
        this.mPreviewSurface = null;
        this.mDeferPreviewSurface = null;
        this.mRecordSurface = null;
        this.mSessionId = 0;
        this.mPhotoImageReader = null;
        this.mRawImageReader = null;
        this.mPreviewImageReader = null;
        this.mVideoSnapshotImageReader = null;
        this.mDepthReader = null;
        this.mPortraitRawImageReader = null;
        releaseCameraPreviewCallback((Camera2Proxy.CameraPreviewCallback) null);
        resetShotQueue("reset");
        Log.v(TAG, "X: reset");
    }

    private void resetShotQueue(String str) {
        synchronized (this.mShotQueueLock) {
            if (!this.mMiCamera2ShotQueue.isEmpty()) {
                String str2 = TAG;
                Log.d(str2, "resetShotQueue !!! " + str + " size:" + this.mMiCamera2ShotQueue.size());
                Iterator<MiCamera2Shot> it = this.mMiCamera2ShotQueue.iterator();
                while (it.hasNext()) {
                    it.next().makeClobber();
                }
                this.mMiCamera2ShotQueue.clear();
                notifyCaptureBusyCallback(false);
            }
        }
    }

    /* access modifiers changed from: private */
    public void runCaptureSequence() {
        this.mCaptureCallback.showAutoFocusFinish(true);
        if (b.isMTKPlatform() || getExposureTime() <= 0) {
            this.mCaptureCallback.setState(8);
            captureStillPicture();
            return;
        }
        waitFlashClosed();
    }

    /* access modifiers changed from: private */
    public void runPreCaptureSequence() {
        Log.v(TAG, "runPreCaptureSequence");
        try {
            CaptureRequest.Builder createCaptureRequest = this.mCameraDevice.createCaptureRequest(1);
            createCaptureRequest.addTarget(this.mPreviewSurface);
            applySettingsForPreCapture(createCaptureRequest);
            CaptureRequest build = createCaptureRequest.build();
            this.mPreCaptureRequestHashCode = build.hashCode();
            this.mCaptureCallback.setState(6);
            capture(build, this.mCaptureCallback, this.mCameraHandler);
        } catch (CameraAccessException | IllegalStateException e2) {
            e2.printStackTrace();
            notifyOnError(-1);
        }
    }

    private void setAFModeToPreview(int i) {
        String str = TAG;
        Log.d(str, "setAFModeToPreview: focusMode=" + i);
        this.mPreviewRequestBuilder.set(CaptureRequest.CONTROL_AF_MODE, Integer.valueOf(i));
        this.mPreviewRequestBuilder.set(CaptureRequest.CONTROL_AF_TRIGGER, 0);
        CaptureRequestBuilder.applyAFRegions(this.mPreviewRequestBuilder, this.mConfigs);
        CaptureRequestBuilder.applyAERegions(this.mPreviewRequestBuilder, this.mConfigs);
        CaptureRequestBuilder.applySessionParameters(this.mPreviewRequestBuilder, this.mSessionConfigs);
        resumePreview();
    }

    private void setVideoRecordControl(int i) throws CameraAccessException {
        String str = TAG;
        Log.d(str, "setVideoRecordControl: " + i);
        CaptureRequest.Builder createCaptureRequest = this.mCameraDevice.createCaptureRequest(3);
        if (1 == i) {
            createCaptureRequest.addTarget(this.mPreviewSurface);
        } else {
            createCaptureRequest.addTarget(this.mRecordSurface);
        }
        applySettingsForVideo(createCaptureRequest);
        VendorTagHelper.setValue(createCaptureRequest, CaptureRequestVendorTags.VIDEO_RECORD_CONTROL, Integer.valueOf(i));
        capture(createCaptureRequest.build(), this.mCaptureCallback, this.mCameraHandler);
    }

    private void triggerCapture() {
        if (this.mConfigs.isForceBackSoftLight()) {
            setFlashMode(5);
            resumePreview();
            this.mCaptureCallback.setState(10);
        } else if (isNeedFlashOn()) {
            this.mConfigs.setNeedFlash(true);
            if (needOptimizedFlash()) {
                this.mPreviewRequestBuilder.set(CaptureRequest.CONTROL_AE_MODE, 1);
                this.mPreviewRequestBuilder.set(CaptureRequest.FLASH_MODE, 2);
                CaptureRequestBuilder.applyAELock(this.mPreviewRequestBuilder, false);
                if (this.mCapabilities.isSupportSnapShotTorch()) {
                    MiCameraCompat.applySnapshotTorch(this.mPreviewRequestBuilder, true);
                }
                resumePreview();
                this.mCaptureCallback.setState(10);
            } else if (needScreenLight()) {
                this.mPreviewRequestBuilder.set(CaptureRequest.CONTROL_AE_MODE, 1);
                resumePreview();
                triggerPrecapture();
            } else {
                triggerPrecapture();
            }
        } else {
            this.mConfigs.setNeedFlash(false);
            captureStillPicture();
        }
    }

    /* access modifiers changed from: private */
    public void triggerDeviceChecking(boolean z, boolean z2) {
        if (b.Pn && DataRepository.dataItemFeature().Gb() && this.mHelperHandler != null) {
            String str = TAG;
            Log.d(str, "triggerDeviceChecking " + z);
            if (z) {
                Handler handler = this.mHelperHandler;
                handler.sendMessage(handler.obtainMessage(3, z2 ? 1 : 0, 0));
                return;
            }
            this.mHelperHandler.removeMessages(3);
        }
    }

    /* access modifiers changed from: private */
    public void triggerPrecapture() {
        if (!this.mCapabilities.isAutoFocusSupported() || this.mConfigs.getFocusMode() == 0) {
            runPreCaptureSequence();
        } else if (needOptimizedFlash()) {
            boolean z = this.mConfigs.getISO() == 0 || this.mConfigs.getExposureTime() == 0;
            if (DataRepository.dataItemFeature().yc() && z) {
                runPreCaptureSequence();
            } else if (!DataRepository.dataItemFeature().zc() || !z) {
                lockFocus();
            } else {
                this.mCaptureCallback.setState(5);
            }
        } else {
            lockFocus();
        }
    }

    private void unlockFocusForCapture() {
        if (checkCaptureSession("unlockFocusForCapture")) {
            Log.d(TAG, "unlockFocusForCapture");
            try {
                CaptureRequest.Builder createCaptureRequest = this.mCameraDevice.createCaptureRequest(1);
                createCaptureRequest.addTarget(this.mPreviewSurface);
                createCaptureRequest.set(CaptureRequest.CONTROL_AF_TRIGGER, 2);
                applyCommonSettings(createCaptureRequest, 1);
                CaptureRequestBuilder.applySessionParameters(createCaptureRequest, this.mSessionConfigs);
                capture(createCaptureRequest.build(), this.mCaptureCallback, this.mCameraHandler);
                CaptureRequestBuilder.applyFocusMode(this.mPreviewRequestBuilder, this.mConfigs);
                applyFlashMode(this.mPreviewRequestBuilder, 1);
                CaptureRequestBuilder.applyAELock(this.mPreviewRequestBuilder, this.mConfigs.isAELocked());
                this.mPreviewRequestBuilder.set(CaptureRequest.CONTROL_AF_TRIGGER, 0);
                this.mPreviewRequestBuilder.set(CaptureRequest.CONTROL_AE_PRECAPTURE_TRIGGER, 0);
                this.mCaptureCallback.setState(1);
                setAFModeToPreview(this.mConfigs.getFocusMode());
            } catch (CameraAccessException e2) {
                e2.printStackTrace();
                String str = TAG;
                Log.e(str, "unlockFocusForCapture: " + e2.getMessage());
                notifyOnError(e2.getReason());
            } catch (IllegalStateException e3) {
                Log.e(TAG, "Failed to unlock focus, IllegalState", e3);
                notifyOnError(256);
            }
        }
    }

    private void waitFlashClosed() {
        this.mPreviewRequestBuilder.set(CaptureRequest.CONTROL_AE_MODE, 1);
        this.mPreviewRequestBuilder.set(CaptureRequest.FLASH_MODE, 0);
        resumePreview();
        this.mCaptureCallback.setState(9);
    }

    public /* synthetic */ void a(ImageReader imageReader) {
        Image acquireNextImage = imageReader.acquireNextImage();
        if (acquireNextImage != null) {
            boolean z = true;
            Camera2Proxy.PreviewCallback previewCallback = getPreviewCallback();
            if (previewCallback != null) {
                z = previewCallback.onPreviewFrame(acquireNextImage, this, this.mConfigs.getDeviceOrientation());
            }
            if (z) {
                acquireNextImage.close();
            }
        }
    }

    public boolean alwaysUseRemosaicSize() {
        return b.isMTKPlatform();
    }

    /* access modifiers changed from: package-private */
    public void applySettingsForCapture(CaptureRequest.Builder builder, int i) {
        if (builder != null) {
            builder.set(CaptureRequest.CONTROL_AF_TRIGGER, 0);
            applyFlashMode(builder, i);
            applyCommonSettings(builder, i);
            applySettingsForJpeg(builder);
            CaptureRequestBuilder.applyZsl(builder, this.mConfigs);
            CaptureRequestBuilder.applyAntiShake(builder, this.mCapabilities, this.mConfigs);
            CaptureRequestBuilder.applyFpsRange(builder, this.mConfigs);
            boolean z = true;
            boolean z2 = i != 4 && ((DataRepository.dataItemFeature().Fb() && getZoomRatio() >= 0.6f && getZoomRatio() < 2.0f && getId() == Camera2DataContainer.getInstance().getSATCameraId()) || !this.mConfigs.isHDREnabled());
            if (this.mMiCamera2ShotQueue.size() > 1) {
                z2 = false;
            } else if (b.isMTKPlatform()) {
                z2 &= Camera2DataContainer.getInstance().getUltraWideCameraId() == getId();
            }
            CaptureRequestBuilder.applyDepurpleEnable(builder, this.mCapabilities, z2);
            CameraCapabilities cameraCapabilities = this.mCapabilities;
            if (!needScreenLight() && !needOptimizedFlash()) {
                z = false;
            }
            CaptureRequestBuilder.applyBackwardCaptureHint(cameraCapabilities, builder, z);
            if (b.isMTKPlatform() && needScreenLight()) {
                MiCameraCompat.applyZsl(builder, false);
            }
            CaptureRequestBuilder.applySessionParameters(builder, this.mSessionConfigs);
        }
    }

    public void applySettingsForJpeg(CaptureRequest.Builder builder) {
        if (builder != null) {
            Location gpsLocation = this.mConfigs.getGpsLocation();
            if (gpsLocation != null) {
                builder.set(CaptureRequest.JPEG_GPS_LOCATION, new Location(gpsLocation));
            }
            String str = TAG;
            Log.d(str, "jpegRotation=" + this.mConfigs.getJpegRotation());
            builder.set(CaptureRequest.JPEG_ORIENTATION, Integer.valueOf(this.mConfigs.getJpegRotation()));
            CameraSize thumbnailSize = this.mConfigs.getThumbnailSize();
            if (thumbnailSize != null) {
                builder.set(CaptureRequest.JPEG_THUMBNAIL_SIZE, new Size(thumbnailSize.getWidth(), thumbnailSize.getHeight()));
            }
            byte jpegQuality = (byte) this.mConfigs.getJpegQuality();
            builder.set(CaptureRequest.JPEG_THUMBNAIL_QUALITY, Byte.valueOf(jpegQuality));
            builder.set(CaptureRequest.JPEG_QUALITY, Byte.valueOf(jpegQuality));
        }
    }

    /* access modifiers changed from: package-private */
    public void applySettingsForVideoShot(CaptureRequest.Builder builder, int i) {
        applySettingsForJpeg(builder);
        CaptureRequestBuilder.applyVideoFlash(builder, this.mConfigs);
        CaptureRequestBuilder.applyExposureCompensation(builder, i, this.mCapabilities, this.mConfigs);
        CaptureRequestBuilder.applyZoomRatio(builder, this.mCapabilities, this.mConfigs);
        CaptureRequestBuilder.applyAntiShake(builder, this.mCapabilities, this.mConfigs);
        CaptureRequestBuilder.applyBeautyValues(builder, this.mCapabilities, this.mConfigs);
        CaptureRequestBuilder.applyFrontMirror(builder, i, this.mCapabilities, this.mConfigs);
        CaptureRequestBuilder.applyAELock(builder, this.mConfigs.isAELocked());
        CaptureRequestBuilder.applyFpsRange(builder, this.mConfigs);
        CaptureRequestBuilder.applyUltraWideLDC(builder, this.mCapabilities, this.mConfigs);
        CaptureRequestBuilder.applyMacroMode(builder, i, this.mCapabilities, this.mConfigs);
        CaptureRequestBuilder.applySessionParameters(builder, this.mSessionConfigs);
    }

    public void cancelContinuousShot() {
    }

    public void cancelFocus(int i) {
        if (checkCaptureSession("cancelFocus")) {
            try {
                CaptureRequest.Builder initFocusRequestBuilder = initFocusRequestBuilder(i);
                initFocusRequestBuilder.set(CaptureRequest.CONTROL_AF_MODE, 1);
                initFocusRequestBuilder.set(CaptureRequest.CONTROL_AF_TRIGGER, 2);
                CaptureRequestBuilder.applyZoomRatio(initFocusRequestBuilder, this.mCapabilities, this.mConfigs);
                applyFlashMode(initFocusRequestBuilder, 1);
                CaptureRequestBuilder.applyAWBMode(initFocusRequestBuilder, this.mConfigs.getAWBMode());
                CaptureRequestBuilder.applyCustomAWB(initFocusRequestBuilder, this.mConfigs.getAwbCustomValue());
                CaptureRequestBuilder.applyExposureCompensation(initFocusRequestBuilder, 1, this.mCapabilities, this.mConfigs);
                CaptureRequestBuilder.applyAntiShake(initFocusRequestBuilder, this.mCapabilities, this.mConfigs);
                if (this.mPreviewControl.needForCapture()) {
                    CaptureRequestBuilder.applyContrast(initFocusRequestBuilder, this.mCapabilities, this.mConfigs);
                    CaptureRequestBuilder.applySaturation(initFocusRequestBuilder, this.mConfigs);
                    CaptureRequestBuilder.applySharpness(initFocusRequestBuilder, this.mConfigs);
                }
                if (this.mPreviewControl.needForManually()) {
                    CaptureRequestBuilder.applyIso(initFocusRequestBuilder, 1, this.mCapabilities, this.mConfigs);
                    CaptureRequestBuilder.applyExposureTime(initFocusRequestBuilder, 1, this.mConfigs);
                }
                CaptureRequestBuilder.applyFpsRange(initFocusRequestBuilder, this.mConfigs);
                capture(initFocusRequestBuilder.build(), this.mCaptureCallback, this.mCameraHandler);
                this.mConfigs.setAERegions((MeteringRectangle[]) null);
                this.mConfigs.setAFRegions((MeteringRectangle[]) null);
                setAFModeToPreview(this.mConfigs.getFocusMode());
            } catch (CameraAccessException e2) {
                e2.printStackTrace();
                String str = TAG;
                Log.e(str, "cancelFocus: " + e2.getMessage());
                notifyOnError(e2.getReason());
            } catch (IllegalStateException e3) {
                Log.e(TAG, "Failed to cancel focus, IllegalState", e3);
                notifyOnError(256);
            }
        }
    }

    public void cancelSession() {
        String str = TAG;
        Log.d(str, "cancelSession: id=" + getId());
        this.mIsCaptureSessionClosed = true;
        try {
            this.mSessionId = genSessionId();
            synchronized (this.mSessionLock) {
                if (this.mCaptureSession != null) {
                    this.mCaptureSession.stopRepeating();
                    abortCaptures();
                    if (this.mCaptureSession instanceof CameraConstrainedHighSpeedCaptureSession) {
                        this.mCaptureSession.replaceSessionClose();
                    } else {
                        this.mCaptureSession.replaceSessionClose();
                    }
                    String str2 = TAG;
                    Log.d(str2, "cancelSession: reset session " + this.mCaptureSession);
                    this.mCaptureSession = null;
                }
            }
            resetConfigs();
        } catch (CameraAccessException e2) {
            Log.e(TAG, "Failed to stop repeating session", e2);
        } catch (IllegalStateException e3) {
            Log.e(TAG, "Failed to stop repeating, IllegalState", e3);
        }
    }

    public void captureAbortBurst() {
        String str = TAG;
        Log.d(str, "captureAbortBurst: shot queue size: " + this.mMiCamera2ShotQueue.size());
        synchronized (this.mSessionLock) {
            if (this.mCaptureSession == null) {
                Log.w(TAG, "captureAbortBurst: null session");
                return;
            }
            try {
                this.mCaptureSession.stopRepeating();
            } catch (CameraAccessException e2) {
                e2.printStackTrace();
                notifyOnError(e2.getReason());
            } catch (IllegalStateException e3) {
                Log.e(TAG, "Failed to abort burst, IllegalState", e3);
                notifyOnError(256);
            }
        }
    }

    public void captureBurstPictures(int i, @NonNull Camera2Proxy.PictureCallback pictureCallback, @NonNull ParallelCallback parallelCallback) {
        if (this.mConfigs.getShotType() == 9) {
            this.mMiCamera2Shot = new MiCamera2ShotParallelRepeating(this, i);
            this.mMiCamera2Shot.setPictureCallback(pictureCallback);
            this.mMiCamera2Shot.setParallelCallback(parallelCallback);
            this.mMiCamera2ShotQueue.offerLast(this.mMiCamera2Shot);
            this.mCaptureTime = System.currentTimeMillis();
            this.mMiCamera2Shot.startShot();
            return;
        }
        this.mMiCamera2Shot = new MiCamera2ShotBurst(this, i);
        this.mMiCamera2Shot.setPictureCallback(pictureCallback);
        this.mMiCamera2Shot.setParallelCallback(parallelCallback);
        this.mMiCamera2Shot.startShot();
    }

    public void captureGroupShotPictures(@NonNull Camera2Proxy.PictureCallback pictureCallback, @NonNull ParallelCallback parallelCallback, int i, Context context) {
        this.mMiCamera2Shot = new MiCamera2ShotGroup(this, i, context, this.mCaptureCallback.getPreviewCaptureResult());
        this.mMiCamera2Shot.setPictureCallback(pictureCallback);
        this.mMiCamera2Shot.setParallelCallback(parallelCallback);
        this.mMiCamera2Shot.startShot();
    }

    public void captureVideoSnapshot(Camera2Proxy.PictureCallback pictureCallback) {
        this.mMiCamera2Shot = new MiCamera2ShotVideo(this);
        this.mMiCamera2Shot.setPictureCallback(pictureCallback);
        this.mMiCamera2Shot.startShot();
    }

    public void close() {
        String str = TAG;
        Log.d(str, "E: close: cameraId = " + getId());
        SuperNightReprocessHandler superNightReprocessHandler = this.mSuperNightReprocessHandler;
        if (superNightReprocessHandler != null) {
            superNightReprocessHandler.cancel();
            this.mSuperNightReprocessHandler.getLooper().quitSafely();
            this.mSuperNightReprocessHandler = null;
        }
        this.mIsCameraClosed = true;
        abortCaptures();
        if (this.mCameraDevice != null) {
            if (DataRepository.dataItemFeature().Kb() && !DataRepository.dataItemFeature().yb()) {
                try {
                    this.mCameraDevice.flush();
                } catch (CameraAccessException e2) {
                    e2.printStackTrace();
                }
            }
            this.mCameraDevice.close();
        }
        closePhotoImageReader();
        closePreviewImageReader();
        closeRawImageReader();
        closeVideoSnapshotImageReader();
        closeDepthImageReader();
        closePortraitRawImageReader();
        MiCamera2Shot miCamera2Shot = this.mMiCamera2Shot;
        if (miCamera2Shot != null) {
            miCamera2Shot.makeClobber();
            this.mMiCamera2Shot = null;
        }
        reset();
        String str2 = TAG;
        Log.d(str2, "X: close: cameraId = " + getId());
    }

    public void forceTurnFlashONAndPausePreview() {
        int flashMode = this.mConfigs.getFlashMode();
        this.mConfigs.setFlashMode(2);
        applyFlashMode(this.mPreviewRequestBuilder, 1);
        resumePreview();
        this.mCaptureCallback.setState(10);
        this.mConfigs.setFlashMode(flashMode);
    }

    public void forceTurnFlashOffAndPausePreview() {
        this.mConfigs.getFlashMode();
        this.mConfigs.setFlashMode(0);
        applyFlashMode(this.mPreviewRequestBuilder, 1);
        resumePreview();
        this.mCaptureCallback.setState(11);
    }

    public int getAlgorithmPreviewFormat() {
        return this.mConfigs.getAlgorithmPreviewFormat();
    }

    public CameraSize getAlgorithmPreviewSize() {
        return this.mConfigs.getAlgorithmPreviewSize();
    }

    /* access modifiers changed from: protected */
    public CameraConfigs getCameraConfigs() {
        return this.mConfigs;
    }

    /* access modifiers changed from: protected */
    public CameraDevice getCameraDevice() {
        return this.mCameraDevice;
    }

    public Handler getCameraHandler() {
        return this.mCameraHandler;
    }

    public CameraCapabilities getCapabilities() {
        return this.mCapabilities;
    }

    /* access modifiers changed from: protected */
    public CameraCaptureSession getCaptureSession() {
        return this.mCaptureSession;
    }

    /* access modifiers changed from: protected */
    public ImageReader getDepthImageReader() {
        return this.mDepthReader;
    }

    public int getExposureCompensation() {
        return this.mConfigs.getExposureCompensationIndex();
    }

    public int getFlashMode() {
        return this.mConfigs.getFlashMode();
    }

    public int getFocusMode() {
        return this.mConfigs.getFocusMode();
    }

    /* access modifiers changed from: protected */
    public Surface getMainCaptureSurface() {
        int satMasterCameraId = getSatMasterCameraId();
        if (satMasterCameraId == 1) {
            return getUltraWideRemoteSurface();
        }
        if (satMasterCameraId == 2) {
            return getWideRemoteSurface();
        }
        if (satMasterCameraId == 3) {
            return getTeleRemoteSurface();
        }
        if (satMasterCameraId == 4) {
            return getUltraTeleRemoteSurface();
        }
        String str = TAG;
        Log.e(str, "getMainCaptureSurface: invalid satMasterCameraId " + satMasterCameraId);
        return getWideRemoteSurface();
    }

    public String getParallelShotSavePath() {
        return this.mConfigs.getThumbnailShotPath();
    }

    /* access modifiers changed from: protected */
    public ImageReader getPhotoImageReader() {
        return this.mPhotoImageReader;
    }

    public int getPictureFormat() {
        return this.mConfigs.getPhotoFormat();
    }

    public int getPictureMaxImages() {
        return this.mConfigs.getPhotoMaxImages();
    }

    public CameraSize getPictureSize() {
        return this.mConfigs.getPhotoSize();
    }

    /* access modifiers changed from: protected */
    public ImageReader getPortraitRawImageReader() {
        return this.mPortraitRawImageReader;
    }

    public int getPreviewMaxImages() {
        return this.mConfigs.getPreviewMaxImages();
    }

    /* access modifiers changed from: protected */
    public CaptureRequest.Builder getPreviewRequestBuilder() {
        return this.mPreviewRequestBuilder;
    }

    public CameraSize getPreviewSize() {
        return this.mConfigs.getPreviewSize();
    }

    /* access modifiers changed from: protected */
    public Surface getPreviewSurface() {
        return this.mPreviewSurface;
    }

    /* access modifiers changed from: protected */
    public Surface getQcfaRemoteSurface() {
        return getRemoteSurface(this.mQcfaParallelSurfaceIndex);
    }

    /* access modifiers changed from: protected */
    public ImageReader getRawImageReader() {
        return this.mRawImageReader;
    }

    /* access modifiers changed from: protected */
    public ImageWriter getRawImageWriter() {
        return this.mRawImageWriter;
    }

    /* access modifiers changed from: protected */
    public Surface getRawSurface() {
        return this.mRawImageReader.getSurface();
    }

    /* access modifiers changed from: protected */
    public Surface getRecordSurface() {
        return this.mRecordSurface;
    }

    /* access modifiers changed from: protected */
    public List<Surface> getRemoteSurfaceList() {
        return this.mParallelCaptureSurfaceList;
    }

    public int getSatMasterCameraId() {
        return CaptureResultParser.getSatMasterCameraId(this.mCaptureCallback.getPreviewCaptureResult());
    }

    public int getSceneMode() {
        return this.mConfigs.getSceneMode();
    }

    public CameraSize getSensorRawImageSize() {
        return this.mConfigs.getSensorRawImageSize();
    }

    /* access modifiers changed from: protected */
    public Surface getSubRemoteSurface() {
        return getRemoteSurface(this.mSubParallelSurfaceIndex);
    }

    public boolean getSuperNight() {
        return this.mConfigs.isSuperNightEnabled();
    }

    /* access modifiers changed from: protected */
    public Surface getTeleRemoteSurface() {
        return getRemoteSurface(this.mTeleParallelSurfaceIndex);
    }

    /* access modifiers changed from: protected */
    public Surface getUltraTeleRemoteSurface() {
        return getRemoteSurface(this.mUltraTeleParallelSurfaceIndex);
    }

    /* access modifiers changed from: protected */
    public Surface getUltraWideRemoteSurface() {
        return getRemoteSurface(this.mUltraWideParallelSurfaceIndex);
    }

    /* access modifiers changed from: protected */
    public ImageReader getVideoSnapshotImageReader() {
        return this.mVideoSnapshotImageReader;
    }

    /* access modifiers changed from: protected */
    public Surface getWideRemoteSurface() {
        return getRemoteSurface(this.mWideParallelSurfaceIndex);
    }

    public float getZoomRatio() {
        return this.mConfigs.getZoomRatio();
    }

    /* access modifiers changed from: protected */
    public boolean isBeautyOn() {
        BeautyValues beautyValues = this.mConfigs.getBeautyValues();
        if (beautyValues != null) {
            return beautyValues.isFaceBeautyOn();
        }
        Log.d(TAG, "Assume front beauty is off in case beautyValues is unavailable.");
        return false;
    }

    public boolean isBokehEnabled() {
        return this.mConfigs.isMiBokehEnabled();
    }

    public boolean isCaptureBusy(boolean z) {
        if (!this.mIsCaptureCompleted) {
            Log.d(TAG, "isCaptureBusy: mIsCaptureComplete = false");
            return true;
        } else if (this.mMiCamera2ShotQueue.isEmpty()) {
            return false;
        } else {
            long currentTimeMillis = System.currentTimeMillis() - this.mCaptureTime;
            if (currentTimeMillis > FunctionParseBeautyBodySlimCount.TIP_INTERVAL_TIME) {
                resetShotQueue("isCaptureBusy: timeout:" + currentTimeMillis);
                return false;
            } else if (z) {
                Log.d(TAG, "isCaptureBusy: simple return true");
                return true;
            } else {
                PictureCaptureCallback pictureCaptureCallback = this.mCaptureCallback;
                if (!(pictureCaptureCallback == null || pictureCaptureCallback.getPreviewCaptureResult() == null)) {
                    Integer num = (Integer) this.mCaptureCallback.getPreviewCaptureResult().get(CaptureResult.SENSOR_SENSITIVITY);
                    if (num == null || num.intValue() >= 800) {
                        String str = TAG;
                        Log.d(str, "isCaptureBusy: iso:" + num);
                        return true;
                    } else if (this.mCapabilities.isSensorHdrSupported()) {
                        Byte b2 = (Byte) VendorTagHelper.getValue(this.mCaptureCallback.getPreviewCaptureResult(), CaptureResultVendorTags.SENSOR_HDR_ENABLE);
                        if (b2 != null && b2.byteValue() > 0) {
                            String str2 = TAG;
                            Log.d(str2, "isCaptureBusy: sensorHdr:" + b2);
                            return true;
                        }
                    }
                }
                if (currentTimeMillis < 50) {
                    String str3 = TAG;
                    Log.d(str3, "isCaptureBusy: time:" + currentTimeMillis);
                    return true;
                }
                int size = this.mMiCamera2ShotQueue.size();
                if (size <= 10) {
                    return false;
                }
                String str4 = TAG;
                Log.d(str4, "isCaptureBusy: size:" + size);
                return true;
            }
        }
    }

    public boolean isConfigRawStream() {
        return this.mIsConfigRawStream;
    }

    public boolean isFacingFront() {
        return this.mCapabilities.getFacing() == 0;
    }

    /* access modifiers changed from: protected */
    public boolean isIn3OrMoreSatMode() {
        return 36866 == this.mCapabilities.getOperatingMode() && HybridZoomingSystem.IS_3_OR_MORE_SAT;
    }

    public boolean isNeedFlashOn() {
        if (this.mConfigs.getFlashMode() == 1 || this.mConfigs.getFlashMode() == 101) {
            return true;
        }
        if (this.mConfigs.getFlashMode() != 3) {
            return false;
        }
        Integer currentAEState = this.mCaptureCallback.getCurrentAEState();
        return currentAEState != null && currentAEState.intValue() == 4;
    }

    public boolean isNeedPreviewThumbnail() {
        return !this.mConfigs.isHDREnabled() && (this.mConfigs.isMfnrEnabled() || this.mConfigs.isSwMfnrEnabled() || this.mConfigs.isSuperResolutionEnabled());
    }

    public boolean isPreviewReady() {
        return (this.mCaptureCallback.getPreviewCaptureResult() == null || this.mCaptureCallback.getState() == 0) ? false : true;
    }

    public boolean isQcfaEnable() {
        return this.mConfigs.isQcfaEnable();
    }

    public boolean isSessionReady() {
        boolean z;
        synchronized (this.mSessionLock) {
            z = this.mCaptureSession != null;
        }
        return z;
    }

    public void lockExposure(boolean z) {
        if (checkCaptureSession("lockExposure")) {
            if (z) {
                setAELock(true);
            } else {
                this.mCaptureCallback.setState(4);
            }
            CaptureRequestBuilder.applyAELock(this.mPreviewRequestBuilder, true);
            resumePreview();
        }
    }

    public void lockExposure(boolean z, boolean z2) {
        if (checkCaptureSession("lockExposure")) {
            if (!z2) {
                this.mCaptureCallback.setState(4);
            }
            if (z) {
                setAELock(true);
            }
            CaptureRequestBuilder.applyAELock(this.mPreviewRequestBuilder, true);
            resumePreview();
        }
    }

    public /* synthetic */ void n(boolean z) {
        boolean z2 = false;
        boolean z3 = this.mMiCamera2ShotQueue.size() < DataRepository.dataItemFeature().La();
        Camera2Proxy.CaptureCallback captureCallback = this.mFrontQuickCaptureCallback;
        if (z && z3) {
            z2 = true;
        }
        captureCallback.onCaptureCompleted(z2);
        this.mIsCaptureCompleted = true;
    }

    public void notifyVideoStreamEnd() {
        try {
            synchronized (this.mSessionLock) {
                if (this.mCaptureSession == null || this.mCameraDevice == null || this.mRecordSurface == null) {
                    Log.w(TAG, "notifyVideoStreamEnd: null session");
                    this.mPendingNotifyVideoEnd = true;
                    return;
                }
                this.mCaptureSession.stopRepeating();
                CaptureRequest.Builder createCaptureRequest = this.mCameraDevice.createCaptureRequest(3);
                createCaptureRequest.addTarget(this.mRecordSurface);
                applySettingsForVideo(createCaptureRequest);
                MiCameraCompat.applyVideoStreamState(createCaptureRequest, false);
                int capture = capture(createCaptureRequest.build(), this.mCaptureCallback, this.mCameraHandler);
                String str = TAG;
                Log.v(str, "notifyVideoStreamEnd: requestId=" + capture);
            }
        } catch (CameraAccessException e2) {
            Log.e(TAG, e2.getMessage(), e2);
            notifyOnError(e2.getReason());
        } catch (IllegalArgumentException | IllegalStateException e3) {
            Log.e(TAG, "notifyVideoStreamEnd: ", e3);
        }
    }

    public void onCapabilityChanged(CameraCapabilities cameraCapabilities) {
        PictureCaptureCallback pictureCaptureCallback = this.mCaptureCallback;
        if (pictureCaptureCallback != null) {
            pictureCaptureCallback.onCapabilityChanged(cameraCapabilities);
        }
    }

    /* access modifiers changed from: protected */
    public void onCapturePictureFinished(boolean z, MiCamera2Shot miCamera2Shot) {
        boolean needResumePreviewAfterCapture = this.mPreviewControl.needResumePreviewAfterCapture(this.mConfigs);
        this.mConfigs.setNeedFlash(false);
        if (this.mConfigs.isForceBackSoftLight()) {
            this.mConfigs.setForceBackSoftLight(false);
            this.mConfigs.setFlashMode(3);
        }
        if (needUnlockFocusAfterCapture()) {
            unlockFocusForCapture();
        }
        this.mCaptureCallback.setState(1);
        applyFlashMode(this.mPreviewRequestBuilder, 7);
        applySettingsForPreview(this.mPreviewRequestBuilder);
        if (needResumePreviewAfterCapture) {
            resumePreview();
        }
        Camera2Proxy.PictureCallback pictureCallback = miCamera2Shot.getPictureCallback();
        if (!z && pictureCallback != null) {
            pictureCallback.onPictureTakenFinished(false);
        }
        if (!z && !this.mMiCamera2ShotQueue.isEmpty()) {
            synchronized (this.mShotQueueLock) {
                boolean remove = this.mMiCamera2ShotQueue.remove(miCamera2Shot);
                String str = TAG;
                Log.d(str, "onCapturePictureFinished failure: mMiCamera2ShotQueue.poll, size: " + this.mMiCamera2ShotQueue.size() + " removeResult: " + remove);
                notifyCaptureBusyCallback(z);
            }
        }
    }

    public void onMultiSnapEnd(boolean z, MiCamera2Shot miCamera2Shot) {
        String str = TAG;
        Log.d(str, "onMultiSnapEnd: " + z + " | " + miCamera2Shot);
        if (this.mMiCamera2ShotQueue.remove(miCamera2Shot)) {
            notifyCaptureBusyCallback(z);
        }
    }

    public void onParallelImagePostProcStart() {
        synchronized (this.mShotQueueLock) {
            String str = TAG;
            Log.d(str, "onParallelImagePostProcStart: mMiCamera2ShotQueue.poll, size:" + this.mMiCamera2ShotQueue.size());
            if (!this.mMiCamera2ShotQueue.isEmpty()) {
                this.mMiCamera2ShotQueue.pollFirst();
                notifyCaptureBusyCallback(true);
            }
        }
    }

    public void onPreviewComing() {
        synchronized (this.mShotQueueLock) {
            if (!this.mMiCamera2ShotQueue.isEmpty()) {
                Iterator<MiCamera2Shot> it = this.mMiCamera2ShotQueue.iterator();
                while (it.hasNext()) {
                    it.next().onPreviewComing();
                }
            }
        }
    }

    public void onPreviewThumbnailReceived(Thumbnail thumbnail) {
        MiCamera2Shot miCamera2Shot = this.mMiCamera2Shot;
        if (miCamera2Shot != null) {
            miCamera2Shot.onPreviewThumbnailReceived(thumbnail);
        }
    }

    public void pausePreview() {
        triggerDeviceChecking(false, false);
        if (checkCaptureSession("pausePreview")) {
            String str = TAG;
            Log.v(str, "pausePreview: cameraId=" + getId());
            synchronized (this.mSessionLock) {
                if (this.mCaptureSession == null) {
                    Log.w(TAG, "pausePreview: null session");
                    return;
                }
                try {
                    this.mCaptureSession.stopRepeating();
                } catch (CameraAccessException e2) {
                    e2.printStackTrace();
                    Log.e(TAG, "Failed to pause preview");
                    notifyOnError(e2.getReason());
                } catch (IllegalStateException e3) {
                    Log.e(TAG, "Failed to pause preview, IllegalState", e3);
                    notifyOnError(256);
                }
            }
        }
    }

    public boolean registerCaptureCallback(Camera2Proxy.CaptureCallback captureCallback) {
        boolean z = false;
        if (this.mConfigs.getShotType() == 0) {
            this.mFrontQuickCaptureCallback = captureCallback;
            this.mIsCaptureCompleted = false;
            z = true;
        }
        String str = TAG;
        Log.d(str, "registerCaptureCallback: " + z);
        return z;
    }

    public void releaseCameraPreviewCallback(@Nullable Camera2Proxy.CameraPreviewCallback cameraPreviewCallback) {
        CaptureSessionStateCallback captureSessionStateCallback = this.mCaptureSessionStateCallback;
        if (captureSessionStateCallback != null) {
            captureSessionStateCallback.setClientCb(cameraPreviewCallback);
        }
    }

    public void releaseFakeSurfaceIfNeed() {
        if (this.mFakeOutputTexture != null) {
            this.mFakeOutputTexture = null;
        }
    }

    public void releasePreview(int i) {
        Handler handler = this.mHelperHandler;
        if (handler != null) {
            handler.removeCallbacksAndMessages((Object) null);
            this.mHelperHandler = null;
        }
        if (i == 0) {
            synchronized (this.mSessionLock) {
                if (this.mCaptureSession == null) {
                    Log.w(TAG, "releasePreview: null session");
                    return;
                }
                try {
                    Log.v(TAG, "E: releasePreview");
                    this.mCaptureSession.stopRepeating();
                    abortCaptures();
                    this.mCaptureSession.close();
                    Log.v(TAG, "X: releasePreview");
                } catch (CameraAccessException e2) {
                    e2.printStackTrace();
                    Log.e(TAG, "Failed to release preview");
                    notifyOnError(e2.getReason());
                } catch (IllegalStateException e3) {
                    try {
                        Log.e(TAG, "Failed to release preview, IllegalState", e3);
                        notifyOnError(256);
                    } catch (Throwable th) {
                        this.mCaptureSession = null;
                        throw th;
                    }
                }
                this.mCaptureSession = null;
            }
        } else {
            synchronized (this.mSessionLock) {
                this.mCaptureSession = null;
            }
        }
        this.mIsCaptureSessionClosed = true;
    }

    public void resetConfigs() {
        Log.v(TAG, "E: resetConfigs");
        if (this.mConfigs != null) {
            this.mConfigs = new CameraConfigs();
        }
        CaptureSessionConfigurations captureSessionConfigurations = this.mSessionConfigs;
        if (captureSessionConfigurations != null) {
            captureSessionConfigurations.reset();
        }
        this.mSessionId = 0;
        releaseCameraPreviewCallback((Camera2Proxy.CameraPreviewCallback) null);
        Log.v(TAG, "X: resetConfigs");
    }

    public void resumePreview() {
        if (checkCaptureSession("resumePreview")) {
            boolean z = this.mCaptureSession instanceof CameraConstrainedHighSpeedCaptureSession;
            String str = TAG;
            Log.v(str, "resumePreview: cameraId=" + getId() + " highSpeed=" + z);
            synchronized (this.mSessionLock) {
                if (this.mCaptureSession != null) {
                    try {
                        this.mPreviewRequest = this.mPreviewRequestBuilder.build();
                        if (z) {
                            int repeatingBurst = this.mCaptureSession.setRepeatingBurst(createHighSpeedRequestList(this.mPreviewRequest), this.mCaptureCallback, this.mCameraHandler);
                            String str2 = TAG;
                            Log.d(str2, "high speed repeating sequenceId: " + repeatingBurst);
                        } else {
                            int repeatingRequest = this.mCaptureSession.setRepeatingRequest(this.mPreviewRequest, this.mCaptureCallback, this.mCameraHandler);
                            String str3 = TAG;
                            Log.d(str3, "repeating sequenceId: " + repeatingRequest);
                        }
                    } catch (CameraAccessException e2) {
                        Log.e(TAG, "Failed to resume preview", e2);
                        notifyOnError(e2.getReason());
                    } catch (IllegalArgumentException | IllegalStateException e3) {
                        Log.e(TAG, "Failed to resume preview, IllegalState", e3);
                        notifyOnError(256);
                    }
                }
            }
        }
    }

    public void setAELock(boolean z) {
        String str = TAG;
        Log.v(str, "setAELock: " + z);
        if (this.mConfigs.setAELock(z)) {
            CaptureRequestBuilder.applyAELock(this.mPreviewRequestBuilder, z);
        }
    }

    public void setAERegions(MeteringRectangle[] meteringRectangleArr) {
        Log.v(TAG, "setAERegions");
        if (this.mConfigs.setAERegions(meteringRectangleArr)) {
            CaptureRequestBuilder.applyAERegions(this.mPreviewRequestBuilder, this.mConfigs);
        }
    }

    public void setAFRegions(MeteringRectangle[] meteringRectangleArr) {
        Log.v(TAG, "setAFRegions");
        if (this.mConfigs.setAFRegions(meteringRectangleArr)) {
            CaptureRequestBuilder.applyAFRegions(this.mPreviewRequestBuilder, this.mConfigs);
        }
    }

    public void setASD(boolean z) {
        if (this.mConfigs.setAiSceneDetectEnable(z)) {
            CaptureRequestBuilder.applyAiSceneDetectEnable(this.mPreviewRequestBuilder, this.mConfigs);
        }
    }

    public void setASDPeriod(int i) {
        if (this.mConfigs.setAiSceneDetectPeriod(i)) {
            CaptureRequestBuilder.applyAiSceneDetectPeriod(this.mPreviewRequestBuilder, this.mConfigs);
        }
    }

    public void setASDScene(int i) {
        if (this.mConfigs.setASDScene(i)) {
            CaptureRequestBuilder.applyASDScene(this.mCapabilities, this.mPreviewRequestBuilder, this.mConfigs);
        }
    }

    public void setAWBLock(boolean z) {
        String str = TAG;
        Log.v(str, "setAWBLock: " + z);
        if (this.mConfigs.setAWBLock(z)) {
            CaptureRequestBuilder.applyAWBLock(this.mPreviewRequestBuilder, z);
        }
    }

    public void setAWBMode(int i) {
        String str = TAG;
        Log.v(str, "setAWBMode: " + i);
        if (this.mConfigs.setAWBMode(i)) {
            CaptureRequestBuilder.applyAWBMode(this.mPreviewRequestBuilder, this.mConfigs.getAWBMode());
        }
    }

    public void setAlgorithmPreviewFormat(int i) {
        if (i != this.mConfigs.getAlgorithmPreviewFormat()) {
            this.mConfigs.setAlgorithmPreviewFormat(i);
            if (this.mIsPreviewCallbackEnabled) {
                preparePreviewImageReader();
            }
        }
    }

    public void setAlgorithmPreviewSize(CameraSize cameraSize) {
        if (!Objects.equals(this.mConfigs.getAlgorithmPreviewSize(), cameraSize)) {
            this.mConfigs.setAlgorithmPreviewSize(cameraSize);
            if (this.mIsPreviewCallbackEnabled) {
                preparePreviewImageReader();
            }
        }
    }

    public void setAntiBanding(int i) {
        String str = TAG;
        Log.v(str, "setAntiBanding: " + i);
        if (this.mConfigs.setAntiBanding(i)) {
            CaptureRequestBuilder.applyAntiBanding(this.mPreviewRequestBuilder, this.mConfigs);
        }
    }

    public void setAutoZoomMode(int i) {
        this.mConfigs.setAutoZoomMode(i);
        CaptureRequestBuilder.applyAutoZoomMode(this.mPreviewRequestBuilder, this.mCapabilities, this.mConfigs);
    }

    public void setAutoZoomScaleOffset(float f2) {
        this.mConfigs.setAutoZoomScaleOffset(f2);
        CaptureRequestBuilder.applyAutoZoomScaleOffset(this.mPreviewRequestBuilder, this.mCapabilities, this.mConfigs);
    }

    public void setAutoZoomStartCapture(float[] fArr) {
        if (checkCameraDevice("setAutoZoomStartCapture")) {
            try {
                CaptureRequest.Builder createCaptureRequest = this.mCameraDevice.createCaptureRequest(3);
                createCaptureRequest.addTarget(this.mPreviewSurface);
                createCaptureRequest.addTarget(this.mRecordSurface);
                applySettingsForVideo(createCaptureRequest);
                VendorTagHelper.setValue(createCaptureRequest, CaptureRequestVendorTags.AUTOZOOM_START, fArr);
                capture(createCaptureRequest.build(), this.mCaptureCallback, this.mCameraHandler);
            } catch (CameraAccessException e2) {
                Log.e(TAG, e2.getMessage());
            }
        }
    }

    public void setAutoZoomStopCapture(int i) {
        if (checkCameraDevice("setAutoZoomStopCapture")) {
            try {
                CaptureRequest.Builder createCaptureRequest = this.mCameraDevice.createCaptureRequest(3);
                createCaptureRequest.addTarget(this.mPreviewSurface);
                createCaptureRequest.addTarget(this.mRecordSurface);
                applySettingsForVideo(createCaptureRequest);
                VendorTagHelper.setValue(createCaptureRequest, CaptureRequestVendorTags.AUTOZOOM_STOP, Integer.valueOf(i));
                capture(createCaptureRequest.build(), this.mCaptureCallback, this.mCameraHandler);
            } catch (CameraAccessException e2) {
                Log.e(TAG, e2.getMessage());
            }
        }
    }

    public void setBeautyValues(BeautyValues beautyValues) {
        this.mConfigs.setBeautyValues(beautyValues);
        CaptureRequestBuilder.applyBeautyValues(this.mPreviewRequestBuilder, this.mCapabilities, this.mConfigs);
    }

    public void setBurstShotSpeed(int i) {
    }

    public void setCameraAI30(boolean z) {
        if (this.mConfigs.setCameraAi30Enable(z)) {
            CaptureRequestBuilder.applyCameraAi30Enable(this.mPreviewRequestBuilder, this.mCapabilities, this.mConfigs);
        }
    }

    public void setCaptureBusyCallback(Camera2Proxy.CaptureBusyCallback captureBusyCallback) {
        String str = TAG;
        Log.d(str, "setCaptureBusyCallback: " + captureBusyCallback);
        if (captureBusyCallback == null) {
            this.mCaptureBusyCallback = null;
            return;
        }
        synchronized (this.mShotQueueLock) {
            if (this.mMiCamera2ShotQueue.isEmpty()) {
                Log.d(TAG, "setCaptureBusyCallback: shot queue empty");
                captureBusyCallback.onCaptureCompleted(true);
            } else {
                this.mCaptureBusyCallback = captureBusyCallback;
            }
        }
    }

    public void setCaptureEnable(boolean z) {
        String str = TAG;
        Log.d(str, "setCaptureEnable: " + z + " | " + this.mFrontQuickCaptureCallback);
        if (this.mFrontQuickCaptureCallback != null) {
            this.mCameraHandler.postDelayed(new b(this, z), getCaptureInterval());
        }
    }

    public void setCaptureTriggerFlow(int[] iArr) {
        this.mConfigs.setCaptureTriggerFlow(iArr);
    }

    public void setColorEffect(int i) {
        String str = TAG;
        Log.v(str, "setColorEffect: " + i);
        if (this.mConfigs.setColorEffect(i)) {
            CaptureRequestBuilder.applyColorEffect(this.mPreviewRequestBuilder, this.mConfigs);
        }
    }

    public void setContrast(int i) {
        String str = TAG;
        Log.v(str, "setContrast: " + i);
        if (this.mConfigs.setContrastLevel(i)) {
            CaptureRequestBuilder.applyContrast(this.mPreviewRequestBuilder, this.mCapabilities, this.mConfigs);
        }
    }

    public void setCustomAWB(int i) {
        String str = TAG;
        Log.v(str, "setCustomAWB: " + i);
        if (this.mConfigs.setCustomAWB(i)) {
            CaptureRequestBuilder.applyCustomAWB(this.mPreviewRequestBuilder, this.mConfigs.getAwbCustomValue());
        }
    }

    public void setDeviceOrientation(int i) {
        String str = TAG;
        Log.v(str, "setDeviceOrientation: " + i);
        if (this.mConfigs.setDeviceOrientation(i)) {
            CaptureRequestBuilder.applyDeviceOrientation(this.mPreviewRequestBuilder, this.mCapabilities, this.mConfigs);
        }
    }

    public void setDisplayOrientation(int i) {
        String str = TAG;
        Log.v(str, "setDisplayOrientation: " + i);
        this.mDisplayOrientation = i;
    }

    public void setDualCamWaterMarkEnable(boolean z) {
        this.mConfigs.setDualCamWaterMarkEnable(z);
    }

    public void setEnableEIS(boolean z) {
        String str = TAG;
        Log.v(str, "setEnableEIS: " + z);
        if (this.mConfigs.setEnableEIS(z)) {
            CaptureRequestBuilder.applyAntiShake(this.mPreviewRequestBuilder, this.mCapabilities, this.mConfigs);
        }
    }

    public void setEnableOIS(boolean z) {
        if (this.mCapabilities.isSupportOIS()) {
            String str = TAG;
            Log.v(str, "setEnableOIS " + z);
            this.mConfigs.setEnableOIS(z);
            CaptureRequestBuilder.applyAntiShake(this.mPreviewRequestBuilder, this.mCapabilities, this.mConfigs);
        }
    }

    public void setEnableZsl(boolean z) {
        String str = TAG;
        Log.v(str, "setEnableZsl " + z);
        this.mConfigs.setEnableZsl(z);
        CaptureRequestBuilder.applyZsl(this.mPreviewRequestBuilder, this.mConfigs);
    }

    public void setExposureCompensation(int i) {
        String str = TAG;
        Log.v(str, "setExposureCompensation: " + i);
        if (this.mConfigs.setExposureCompensationIndex(i)) {
            CaptureRequestBuilder.applyExposureCompensation(this.mPreviewRequestBuilder, 1, this.mCapabilities, this.mConfigs);
        }
    }

    public void setExposureMeteringMode(int i) {
        String str = TAG;
        Log.v(str, "setExposureMeteringMode: " + i);
        if (this.mConfigs.setExposureMeteringMode(i)) {
            CaptureRequestBuilder.applyExposureMeteringMode(this.mPreviewRequestBuilder, this.mConfigs);
        }
    }

    public void setExposureTime(long j) {
        if (this.mConfigs.setExposureTime(j)) {
            applyFlashMode(this.mPreviewRequestBuilder, 1);
            CaptureRequestBuilder.applySceneMode(this.mPreviewRequestBuilder, this.mConfigs);
            CaptureRequestBuilder.applyExposureCompensation(this.mPreviewRequestBuilder, 1, this.mCapabilities, this.mConfigs);
            CaptureRequestBuilder.applyIso(this.mPreviewRequestBuilder, 1, this.mCapabilities, this.mConfigs);
            CaptureRequestBuilder.applyExposureTime(this.mPreviewRequestBuilder, 1, this.mConfigs);
        }
    }

    public void setEyeLight(int i) {
        if (this.mConfigs.setEyeLight(i)) {
            CaptureRequestBuilder.applyEyeLight(this.mPreviewRequestBuilder, this.mCapabilities, this.mConfigs);
        }
    }

    public void setFNumber(String str) {
        String str2 = TAG;
        Log.d(str2, "setFNumber " + str + " for " + this.mPreviewRequestBuilder);
        this.mConfigs.setFNumber(str);
        CaptureRequestBuilder.applyFNumber(this.mPreviewRequestBuilder, this.mCapabilities, this.mConfigs);
    }

    public void setFaceAgeAnalyze(boolean z) {
        if (this.mConfigs.setFaceAgeAnalyzeEnabled(z)) {
            CaptureRequestBuilder.applyFaceAgeAnalyze(this.mPreviewRequestBuilder, this.mCapabilities, this.mConfigs);
        }
    }

    public void setFaceScore(boolean z) {
        if (this.mConfigs.setFaceScoreEnabled(z)) {
            CaptureRequestBuilder.applyFaceScore(this.mPreviewRequestBuilder, this.mCapabilities, this.mConfigs);
        }
    }

    public void setFaceWaterMarkEnable(boolean z) {
        this.mConfigs.setFaceWaterMarkEnable(z);
    }

    public void setFaceWaterMarkFormat(String str) {
        this.mConfigs.setFaceWaterMarkFormat(str);
    }

    public void setFlashMode(int i) {
        String str = TAG;
        Log.v(str, "setFlashMode: " + i);
        if (this.mConfigs.setFlashMode(i)) {
            applyFlashMode(this.mPreviewRequestBuilder, 1);
        }
    }

    public void setFlawDetectEnable(boolean z) {
        this.mConfigs.setFlawDetectEnable(z);
    }

    public void setFocusDistance(float f2) {
        String str = TAG;
        Log.v(str, "setFocusDistance: " + f2);
        if (this.mConfigs.setFocusDistance(f2)) {
            CaptureRequestBuilder.applyFocusDistance(this.mPreviewRequestBuilder, this.mConfigs);
        }
    }

    public void setFocusMode(int i) {
        String str = TAG;
        Log.v(str, "setFocusMode: " + i);
        if (this.mConfigs.setFocusMode(i)) {
            CaptureRequestBuilder.applyFocusMode(this.mPreviewRequestBuilder, this.mConfigs);
        }
    }

    public void setForceBackSoftLight(boolean z) {
        this.mConfigs.setForceBackSoftLight(z);
    }

    public void setFpsRange(Range<Integer> range) {
        String str = TAG;
        Log.v(str, "setFpsRange: " + range);
        this.mConfigs.setPreviewFpsRange(range);
        CaptureRequestBuilder.applyFpsRange(this.mPreviewRequestBuilder, this.mConfigs);
    }

    public void setFrontMirror(boolean z) {
        String str = TAG;
        Log.d(str, "setFrontMirror: " + z);
        this.mConfigs.setFrontMirror(z);
    }

    public void setGpsLocation(Location location) {
        this.mConfigs.setGpsLocation(location);
    }

    public void setHDR(boolean z) {
        if (this.mConfigs.setHDREnabled(z)) {
            CaptureRequestBuilder.applyHDR(this.mPreviewRequestBuilder, 1, this.mCapabilities, this.mConfigs);
        }
    }

    public void setHDRCheckerEnable(boolean z) {
        if (this.mConfigs.setHDRCheckerEnabled(z)) {
            CaptureRequestBuilder.applyHDRCheckerEnable(this.mPreviewRequestBuilder, 1, this.mCapabilities, this.mConfigs);
        }
    }

    public void setHFRDeflickerEnable(boolean z) {
        if (this.mConfigs.setHFRDeflickerEnable(z)) {
            CaptureRequestBuilder.applyHFRDeflicker(this.mPreviewRequestBuilder, 1, this.mCapabilities, this.mConfigs);
        }
    }

    public void setHHT(boolean z) {
        if (this.mConfigs.setHHTEnabled(z)) {
            CaptureRequestBuilder.applyHHT(this.mPreviewRequestBuilder, 1, this.mCapabilities, this.mConfigs);
        }
    }

    public void setISO(int i) {
        String str = TAG;
        Log.v(str, "setISO: " + i);
        if (this.mConfigs.setISO(i)) {
            applyFlashMode(this.mPreviewRequestBuilder, 1);
            CaptureRequestBuilder.applyExposureCompensation(this.mPreviewRequestBuilder, 1, this.mCapabilities, this.mConfigs);
            CaptureRequestBuilder.applyIso(this.mPreviewRequestBuilder, 1, this.mCapabilities, this.mConfigs);
            CaptureRequestBuilder.applyExposureTime(this.mPreviewRequestBuilder, 1, this.mConfigs);
        }
    }

    public void setJpegQuality(int i) {
        this.mConfigs.setJpegQuality(i);
    }

    public void setJpegRotation(int i) {
        this.mConfigs.setJpegRotation(i);
    }

    public void setJpegThumbnailSize(CameraSize cameraSize) {
        this.mConfigs.setThumbnailSize(cameraSize);
    }

    public void setLensDirtyDetect(boolean z) {
        if (this.mConfigs.setLensDirtyDetectEnabled(z)) {
            CaptureRequestBuilder.applyLensDirtyDetect(this.mPreviewRequestBuilder, this.mCapabilities, this.mConfigs);
        }
    }

    public void setMacroMode(boolean z) {
        if (this.mConfigs.setMacroMode(z)) {
            CaptureRequestBuilder.applyMacroMode(this.mPreviewRequestBuilder, 1, this.mCapabilities, this.mConfigs);
        }
    }

    public void setMfnr(boolean z) {
        if (this.mConfigs.setMfnrEnabled(z)) {
            CaptureRequestBuilder.applyHwMfnr(this.mPreviewRequestBuilder, 1, this.mCapabilities, this.mConfigs);
        }
    }

    public void setMiBokeh(boolean z) {
        if (this.mConfigs.setMiBokehEnabled(z)) {
            CaptureRequestBuilder.applyMiBokeh(this.mPreviewRequestBuilder, this.mCapabilities, this.mConfigs);
        }
    }

    public void setModuleParameter(int i, int i2) {
        this.mPreviewControl = new MiCamera2PreviewNormal(i, i2);
    }

    public void setNeedPausePreview(boolean z) {
        this.mConfigs.setPausePreview(z);
    }

    public void setNeedSequence(boolean z) {
        this.mConfigs.setNeedSequence(z);
    }

    public void setNormalWideLDC(boolean z) {
        String str = TAG;
        Log.v(str, "setNormalWideLDC: " + z);
        if (this.mConfigs.setNormalWideLDCEnabled(z)) {
            CaptureRequestBuilder.applyNormalWideLDC(this.mPreviewRequestBuilder, 1, this.mCapabilities, this.mConfigs);
        }
    }

    public void setOnTripodModeStatus(MarshalQueryableASDScene.ASDScene[] aSDSceneArr) {
        this.mConfigs.setOnTripodScenes(aSDSceneArr);
        CaptureRequestBuilder.applyOnTripodModeStatus(this.mPreviewRequestBuilder, this.mConfigs);
    }

    public void setOpticalZoomToTele(boolean z) {
        if (DataRepository.dataItemFeature().qc() && this.mCapabilities.isSupportFastZoomIn()) {
            String str = TAG;
            Log.d(str, "setOpticalZoomToTele: toTele = " + z);
            this.mToTele = z;
        }
        MiCameraCompat.applyStFastZoomIn(this.mPreviewRequestBuilder, z);
    }

    public void setPictureFormat(int i) {
        if (this.mConfigs.getPhotoFormat() != i) {
            this.mConfigs.setPhotoFormat(i);
            preparePhotoImageReader();
        }
    }

    public void setPictureMaxImages(int i) {
        if (i > this.mConfigs.getPhotoMaxImages()) {
            this.mConfigs.setPhotoMaxImages(i);
            preparePhotoImageReader();
        }
    }

    public void setPictureSize(CameraSize cameraSize) {
        if (!Objects.equals(this.mConfigs.getPhotoSize(), cameraSize)) {
            this.mConfigs.setPhotoSize(cameraSize);
            preparePhotoImageReader();
        }
    }

    public void setPortraitLighting(int i) {
        if (this.mConfigs.setPortraitLightingPattern(i)) {
            CaptureRequestBuilder.applyPortraitLighting(this.mPreviewRequestBuilder, 1, this.mCapabilities, this.mConfigs);
        }
    }

    public void setPreviewMaxImages(int i) {
        if (i > this.mConfigs.getPreviewMaxImages()) {
            this.mConfigs.setPreviewMaxImages(i);
        }
    }

    public void setPreviewSize(CameraSize cameraSize) {
        if (!Objects.equals(this.mConfigs.getPreviewSize(), cameraSize)) {
            this.mConfigs.setPreviewSize(cameraSize);
        }
    }

    public void setQcfaEnable(boolean z) {
        this.mConfigs.setQcfaEnable(z);
    }

    public void setQuickShotAnimation(boolean z) {
        this.mConfigs.setQuickShotAnimation(z);
    }

    public void setRearBokehEnable(boolean z) {
        if (this.mConfigs.setRearBokehEnable(z)) {
            CaptureRequestBuilder.applyRearBokeh(this.mPreviewRequestBuilder, this.mCapabilities, this.mConfigs);
        }
    }

    public void setSatIsZooming(boolean z) {
        this.mConfigs.setSatIsZooming(z);
        CaptureRequestBuilder.applySatIsZooming(this.mPreviewRequestBuilder, this.mConfigs);
    }

    public void setSaturation(int i) {
        String str = TAG;
        Log.v(str, "setSaturation: " + i);
        if (this.mConfigs.setSaturationLevel(i)) {
            CaptureRequestBuilder.applySaturation(this.mPreviewRequestBuilder, this.mConfigs);
        }
    }

    public void setSceneMode(int i) {
        String str = TAG;
        Log.v(str, "setSceneMode: " + i);
        if (this.mConfigs.setSceneMode(i)) {
            CaptureRequestBuilder.applySceneMode(this.mPreviewRequestBuilder, this.mConfigs);
        }
    }

    public void setSensorRawImageSize(CameraSize cameraSize, boolean z) {
        if (!Objects.equals(this.mConfigs.getSensorRawImageSize(), cameraSize)) {
            this.mConfigs.setSensorRawImageSize(cameraSize);
            prepareRawImageReader(cameraSize, z);
        }
    }

    public void setSharpness(int i) {
        String str = TAG;
        Log.v(str, "setSharpness: " + i);
        if (this.mConfigs.setSharpnessLevel(i)) {
            CaptureRequestBuilder.applySharpness(this.mPreviewRequestBuilder, this.mConfigs);
        }
    }

    public void setShot2Gallery(boolean z) {
        String str = TAG;
        Log.d(str, "setShot2Gallery: isShot2Gallery=" + z);
        this.mConfigs.setShot2Gallery(z);
    }

    public void setShotSavePath(String str, boolean z) {
        String str2 = TAG;
        Log.d(str2, "setShotSavePath: " + str + ", isParallel:" + z);
        this.mConfigs.setShotPath(str, z);
    }

    public void setShotType(int i) {
        String str = TAG;
        Log.d(str, "setShotType: algo=" + i);
        this.mConfigs.setShotType(i);
    }

    public void setSubPictureSize(CameraSize cameraSize) {
        if (!Objects.equals(this.mConfigs.getSubPhotoSize(), cameraSize)) {
            this.mConfigs.setSubPhotoSize(cameraSize);
        }
    }

    public void setSuperNight(boolean z) {
        this.mConfigs.setSuperNightEnabled(z);
    }

    public void setSuperResolution(boolean z) {
        if (this.mConfigs.setSuperResolutionEnabled(z)) {
            CaptureRequestBuilder.applySuperResolution(this.mPreviewRequestBuilder, 1, this.mCapabilities, this.mConfigs, DataRepository.dataItemFeature().Ac());
        }
    }

    public void setSwMfnr(boolean z) {
        if (this.mConfigs.setSwMfnrEnabled(z)) {
            CaptureRequestBuilder.applySwMfnr(this.mPreviewRequestBuilder, 1, this.mCapabilities, this.mConfigs);
        }
    }

    public void setTelePictureSize(CameraSize cameraSize) {
        if (!Objects.equals(this.mConfigs.getTelePhotoSize(), cameraSize)) {
            this.mConfigs.setTelePhotoSize(cameraSize);
        }
    }

    public void setTimeWaterMarkEnable(boolean z) {
        this.mConfigs.setTimeWaterMarkEnable(z);
    }

    public void setTimeWatermarkValue(String str) {
        this.mConfigs.setTimeWaterMarkValue(str);
    }

    public void setUltraPixelPortrait(boolean z) {
        if (this.mConfigs.setUltraPixelPortraitEnabled(z)) {
            CaptureRequestBuilder.applyUltraPixelPortrait(this.mPreviewRequestBuilder, 1, this.mCapabilities, this.mConfigs);
        }
    }

    public void setUltraTelePictureSize(CameraSize cameraSize) {
        if (!Objects.equals(this.mConfigs.getStandalonePhotoSize(), cameraSize)) {
            this.mConfigs.setUltraTelePhotoSize(cameraSize);
        }
    }

    public void setUltraWideLDC(boolean z) {
        String str = TAG;
        Log.v(str, "setUltraWideLDC: " + z);
        if (this.mConfigs.setUltraWideLDCEnabled(z)) {
            CaptureRequestBuilder.applyUltraWideLDC(this.mPreviewRequestBuilder, this.mCapabilities, this.mConfigs);
        }
    }

    public void setUltraWidePictureSize(CameraSize cameraSize) {
        if (!Objects.equals(this.mConfigs.getUltraWidePhotoSize(), cameraSize)) {
            this.mConfigs.setUltraWidePhotoSize(cameraSize);
        }
    }

    public void setUseLegacyFlashMode(boolean z) {
        this.mConfigs.setUseLegacyFlashMode(z);
    }

    public <T> void setVendorSetting(CaptureRequest.Key<T> key, T t) {
        CaptureRequest.Builder builder = this.mPreviewRequestBuilder;
        if (builder != null) {
            builder.set(key, t);
        }
    }

    public void setVideoFpsRange(Range<Integer> range) {
        String str = TAG;
        Log.v(str, "setVideoFpsRange: " + range);
        if (this.mConfigs.setVideoFpsRange(range)) {
            CaptureRequestBuilder.applyVideoFpsRange(this.mPreviewRequestBuilder, this.mConfigs);
        }
    }

    public void setVideoSnapshotSize(CameraSize cameraSize) {
        this.mConfigs.setVideoSnapshotSize(cameraSize);
    }

    public void setWidePictureSize(CameraSize cameraSize) {
        if (!Objects.equals(this.mConfigs.getWidePhotoSize(), cameraSize)) {
            this.mConfigs.setWidePhotoSize(cameraSize);
        }
    }

    public void setZoomRatio(float f2) {
        String str = TAG;
        Log.v(str, "setZoomRatio(): " + f2);
        if (this.mConfigs.setZoomRatio(f2)) {
            CaptureRequestBuilder.applyZoomRatio(this.mPreviewRequestBuilder, this.mCapabilities, this.mConfigs);
        }
    }

    public void startFaceDetection() {
        Log.v(TAG, "startFaceDetection");
        this.mConfigs.setFaceDetectionEnabled(true);
        CaptureRequestBuilder.applyFaceDetection(this.mPreviewRequestBuilder, this.mConfigs);
    }

    public void startFocus(FocusTask focusTask, int i) {
        String str = TAG;
        Log.v(str, "startFocus: " + i);
        try {
            this.mCaptureCallback.setFocusTask(focusTask);
            CaptureRequest.Builder initFocusRequestBuilder = initFocusRequestBuilder(i);
            initFocusRequestBuilder.set(CaptureRequest.CONTROL_MODE, 1);
            applySettingsForFocusCapture(initFocusRequestBuilder);
            initFocusRequestBuilder.set(CaptureRequest.CONTROL_AF_TRIGGER, 1);
            CaptureRequest build = initFocusRequestBuilder.build();
            focusTask.setRequest(build);
            capture(build, this.mCaptureCallback, this.mCameraHandler);
            this.mConfigs.setFocusMode(1);
            this.mPreviewRequestBuilder.set(CaptureRequest.CONTROL_MODE, 1);
            if (this.mPreviewControl.needForVideo()) {
                applySettingsForVideo(this.mPreviewRequestBuilder);
            } else {
                applySettingsForPreview(this.mPreviewRequestBuilder);
            }
            resumePreview();
        } catch (CameraAccessException e2) {
            e2.printStackTrace();
            Log.e(TAG, "Failed to start focus");
            notifyOnError(e2.getReason());
        }
    }

    public void startHighSpeedRecordPreview() {
        if (checkCameraDevice("startHighSpeedRecordPreview")) {
            Log.d(TAG, "startHighSpeedRecordPreview");
            applySettingsForVideo(this.mPreviewRequestBuilder);
            MiCameraCompat.applyIsHfrPreview(this.mPreviewRequestBuilder, true);
            resumePreview();
        }
    }

    public void startHighSpeedRecordSession(@NonNull Surface surface, @NonNull Surface surface2, Range<Integer> range, Camera2Proxy.CameraPreviewCallback cameraPreviewCallback) {
        int[] iArr;
        if (checkCameraDevice("startHighSpeedRecordSession")) {
            String str = TAG;
            Log.d(str, "startHighSpeedRecordSession: previewSurface = " + surface + " recordSurface = " + surface2 + " fpsRange = " + range);
            this.mPreviewSurface = surface;
            this.mRecordSurface = surface2;
            this.mHighSpeedFpsRange = range;
            this.mSessionId = genSessionId();
            try {
                this.mPreviewRequestBuilder = this.mCameraDevice.createCaptureRequest(3);
                this.mPreviewRequestBuilder.addTarget(this.mPreviewSurface);
                this.mPreviewRequestBuilder.addTarget(this.mRecordSurface);
                this.mPreviewRequestBuilder.set(CaptureRequest.CONTROL_AE_TARGET_FPS_RANGE, this.mHighSpeedFpsRange);
                synchronized (this.mSessionLock) {
                    String str2 = TAG;
                    Log.d(str2, "startHighSpeedRecordSession: reset session " + this.mCaptureSession);
                    this.mCaptureSession = null;
                }
                List<Surface> asList = Arrays.asList(new Surface[]{this.mPreviewSurface, this.mRecordSurface});
                if (b.isMTKPlatform()) {
                    int intValue = this.mHighSpeedFpsRange.getUpper().intValue();
                    if (DataRepository.dataItemFeature().wc()) {
                        if (intValue == 120) {
                            iArr = CaptureRequestVendorTags.VALUE_SMVR_MODE_120FPS;
                        } else if (intValue == 240) {
                            iArr = CaptureRequestVendorTags.VALUE_SMVR_MODE_240FPS;
                        } else {
                            throw new UnsupportedOperationException("Unsupported Slow Motion Recording: " + this.mHighSpeedFpsRange);
                        }
                        this.mSessionConfigs.set(CaptureRequestVendorTags.SMVR_MODE, iArr);
                        MiCameraCompat.applySlowMotionVideoRecordingMode(this.mPreviewRequestBuilder, iArr);
                        String str3 = TAG;
                        Log.d(str3, "startHighSpeedRecordSession: turns smvrmode to " + intValue);
                        ArrayList arrayList = new ArrayList();
                        for (Surface outputConfiguration : asList) {
                            arrayList.add(new OutputConfiguration(outputConfiguration));
                        }
                        CompatibilityUtils.createCaptureSessionWithSessionConfiguration(this.mCameraDevice, 0, (InputConfiguration) null, arrayList, this.mPreviewRequestBuilder.build(), new HighSpeedCaptureSessionStateCallback(this.mSessionId, cameraPreviewCallback), this.mCameraHandler);
                    } else if (intValue == 120) {
                        this.mCameraDevice.createConstrainedHighSpeedCaptureSession(asList, new HighSpeedCaptureSessionStateCallback(this.mSessionId, cameraPreviewCallback), this.mCameraHandler);
                    } else if (intValue == 240) {
                        this.mSessionConfigs.set(CaptureRequestVendorTags.SMVR_MODE, CaptureRequestVendorTags.VALUE_SMVR_MODE_240FPS);
                        MiCameraCompat.applySlowMotionVideoRecordingMode(this.mPreviewRequestBuilder, CaptureRequestVendorTags.VALUE_SMVR_MODE_240FPS);
                        Log.d(TAG, "startHighSpeedRecordSession: turns smvrmode to 240");
                        ArrayList arrayList2 = new ArrayList();
                        for (Surface outputConfiguration2 : asList) {
                            arrayList2.add(new OutputConfiguration(outputConfiguration2));
                        }
                        CompatibilityUtils.createCaptureSessionWithSessionConfiguration(this.mCameraDevice, 0, (InputConfiguration) null, arrayList2, this.mPreviewRequestBuilder.build(), new HighSpeedCaptureSessionStateCallback(this.mSessionId, cameraPreviewCallback), this.mCameraHandler);
                    } else {
                        throw new UnsupportedOperationException("Unsupported Slow Motion Recording: " + this.mHighSpeedFpsRange);
                    }
                } else if (this.mHighSpeedFpsRange.getUpper().intValue() == 120 && !DataRepository.dataItemFeature().wc()) {
                    ArrayList arrayList3 = new ArrayList();
                    for (Surface outputConfiguration3 : asList) {
                        arrayList3.add(new OutputConfiguration(outputConfiguration3));
                    }
                    this.mCameraDevice.createCustomCaptureSession((InputConfiguration) null, arrayList3, 32888, new HighSpeedCaptureSessionStateCallback(this.mSessionId, cameraPreviewCallback), this.mCameraHandler);
                } else if (DataRepository.dataItemFeature().Xa()) {
                    ArrayList arrayList4 = new ArrayList();
                    for (Surface outputConfiguration4 : asList) {
                        arrayList4.add(new OutputConfiguration(outputConfiguration4));
                    }
                    CompatibilityUtils.createCaptureSessionWithSessionConfiguration(this.mCameraDevice, 1, (InputConfiguration) null, arrayList4, this.mPreviewRequestBuilder.build(), new HighSpeedCaptureSessionStateCallback(this.mSessionId, cameraPreviewCallback), this.mCameraHandler);
                } else {
                    this.mCameraDevice.createConstrainedHighSpeedCaptureSession(asList, new HighSpeedCaptureSessionStateCallback(this.mSessionId, cameraPreviewCallback), this.mCameraHandler);
                }
            } catch (Exception e2) {
                notifyOnError(-1);
                Log.e(TAG, "Failed to start high speed record session", e2);
            }
        }
    }

    public void startHighSpeedRecording() {
        if (checkCaptureSession("startHighSpeedRecording")) {
            Log.d(TAG, "startHighSpeedRecording");
            MiCameraCompat.applyIsHfrPreview(this.mPreviewRequestBuilder, false);
            if (DataRepository.dataItemFeature().ua()) {
                Log.d(TAG, "startHighSpeedRecording: CAF is disabled");
                this.mPreviewRequestBuilder.set(CaptureRequest.CONTROL_AF_MODE, 1);
            }
            CaptureRequestBuilder.applySessionParameters(this.mPreviewRequestBuilder, this.mSessionConfigs);
            resumePreview();
        }
    }

    public void startObjectTrack(RectF rectF) {
    }

    public void startPreviewCallback(@NonNull Camera2Proxy.PreviewCallback previewCallback) {
        if (checkCaptureSession("startPreviewCallback")) {
            Log.v(TAG, "startPreviewCallback");
            if (this.mIsPreviewCallbackEnabled) {
                setPreviewCallback(previewCallback);
                if (!this.mIsPreviewCallbackStarted) {
                    this.mIsPreviewCallbackStarted = true;
                    this.mPreviewRequestBuilder.addTarget(this.mPreviewImageReader.getSurface());
                }
            }
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:46:0x0248 A[Catch:{ CameraAccessException -> 0x035d, IllegalStateException -> 0x034e, IllegalArgumentException -> 0x033f, all -> 0x036f }] */
    /* JADX WARNING: Removed duplicated region for block: B:51:0x0291 A[Catch:{ CameraAccessException -> 0x035d, IllegalStateException -> 0x034e, IllegalArgumentException -> 0x033f, all -> 0x036f }] */
    /* JADX WARNING: Removed duplicated region for block: B:54:0x02cf A[ADDED_TO_REGION, Catch:{ CameraAccessException -> 0x035d, IllegalStateException -> 0x034e, IllegalArgumentException -> 0x033f, all -> 0x036f }] */
    /* JADX WARNING: Removed duplicated region for block: B:62:0x0325 A[Catch:{ CameraAccessException -> 0x035d, IllegalStateException -> 0x034e, IllegalArgumentException -> 0x033f, all -> 0x036f }] */
    public void startPreviewSession(Surface surface, boolean z, boolean z2, boolean z3, int i, boolean z4, Camera2Proxy.CameraPreviewCallback cameraPreviewCallback) {
        InputConfiguration inputConfiguration;
        boolean z5;
        OutputConfiguration outputConfiguration;
        boolean z6 = z;
        boolean z7 = z2;
        boolean z8 = z3;
        boolean z9 = z4;
        if (checkCameraDevice("startPreviewSession")) {
            int i2 = 1;
            int i3 = 2;
            Log.d(TAG, String.format(Locale.ENGLISH, "startPreviewSession: opMode=0x%x previewCallback=%b configRawStream=%b", new Object[]{Integer.valueOf(i), Boolean.valueOf(z), Boolean.valueOf(z2)}));
            synchronized (this.mSessionLock) {
                try {
                    this.mEnableParallelSession = z9;
                    this.mPreviewSurface = surface;
                    this.mIsPreviewCallbackEnabled = z6;
                    this.mIsConfigRawStream = z7;
                    this.mSessionId = genSessionId();
                    this.mDeferOutputConfigurations.clear();
                    ArrayList arrayList = new ArrayList();
                    if (!z9) {
                        if (this.mConfigs.getPhotoSize() != null) {
                            preparePhotoImageReader();
                            arrayList.add(new OutputConfiguration(this.mPhotoImageReader.getSurface()));
                        }
                        if (this.mConfigs.getShotType() == 2 || this.mConfigs.getShotType() == -3) {
                            prepareDepthImageReader(this.mConfigs.getPhotoSize());
                            arrayList.add(new OutputConfiguration(this.mDepthReader.getSurface()));
                            preparePortraitRawImageReader(this.mConfigs.getPhotoSize());
                            arrayList.add(new OutputConfiguration(this.mPortraitRawImageReader.getSurface()));
                        }
                    } else {
                        ArrayList arrayList2 = new ArrayList();
                        this.mParallelCaptureSurfaceList = prepareRemoteImageReader(arrayList2);
                        configMaxParallelRequestNumberLock();
                        boolean isLocalParallelServiceReady = isLocalParallelServiceReady();
                        int size = this.mParallelCaptureSurfaceList.size();
                        int i4 = 0;
                        while (i4 < size) {
                            OutputConfiguration outputConfiguration2 = new OutputConfiguration(this.mParallelCaptureSurfaceList.get(i4));
                            if (b.isMTKPlatform() && Build.VERSION.SDK_INT >= 28 && this.mConfigs.isParallelDualShotType() && this.mCapabilities.getFacing() == i2 && i4 < i3) {
                                int i5 = ((IImageReaderParameterSets) arrayList2.get(i4)).targetCamera;
                                if (i5 == 0) {
                                    int mainBackCameraId = Camera2DataContainer.getInstance().getMainBackCameraId();
                                    Log.d(TAG, "Binds main output stream to camera " + mainBackCameraId);
                                } else if (i5 == i2) {
                                    CompatibilityUtils.setPhysicalCameraId(outputConfiguration2, String.valueOf(Camera2DataContainer.getInstance().getAuxCameraId()));
                                    Log.d(TAG, "Binds sub output stream to camera " + r10);
                                }
                            }
                            if (!isLocalParallelServiceReady) {
                                outputConfiguration2.enableSurfaceSharing();
                                Log.d(TAG, "add surface to deferredOutputConfig: " + outputConfiguration2.getSurface());
                                this.mDeferOutputConfigurations.add(outputConfiguration2);
                            }
                            arrayList.add(outputConfiguration2);
                            i4++;
                            i2 = 1;
                            i3 = 2;
                        }
                    }
                    this.mPreviewRequestBuilder = this.mCameraDevice.createCaptureRequest(1);
                    applySettingsForPreview(this.mPreviewRequestBuilder);
                    this.mCaptureSession = null;
                    this.mIsCaptureSessionClosed = true;
                    if (z6) {
                        preparePreviewImageReader(this.mConfigs.getAlgorithmPreviewSize(), this.mConfigs.getAlgorithmPreviewFormat(), this.mConfigs.getPreviewMaxImages());
                        arrayList.add(new OutputConfiguration(this.mPreviewImageReader.getSurface()));
                    }
                    if (z7) {
                        prepareRawImageReader(this.mConfigs.getSensorRawImageSize(), z8);
                        arrayList.add(new OutputConfiguration(this.mRawImageReader.getSurface()));
                        Log.d(TAG, "startPreviewSession: needsRawStream = " + z7 + ", size = " + this.mRawImageReader.getWidth() + "x" + this.mRawImageReader.getHeight());
                        if (z8) {
                            inputConfiguration = new InputConfiguration(this.mConfigs.getSensorRawImageSize().width, this.mConfigs.getSensorRawImageSize().height, 32);
                            Log.d(TAG, "startPreviewSession: setup input configuration: w = " + r2 + ", h = " + r5 + ", fmt = " + 32);
                            this.mCaptureSessionStateCallback = new CaptureSessionStateCallback(this.mSessionId, cameraPreviewCallback);
                            if (this.mPreviewSurface != null) {
                                CameraSize previewSize = getPreviewSize();
                                this.mFakeOutputTexture = new SurfaceTexture(false);
                                if (this.mSetRepeatingEarly) {
                                    this.mFakeOutputTexture.setDefaultBufferSize(previewSize.width, previewSize.height);
                                    Surface surface2 = new Surface(this.mFakeOutputTexture);
                                    outputConfiguration = new OutputConfiguration(surface2);
                                    outputConfiguration.enableSurfaceSharing();
                                    this.mPreviewRequestBuilder.addTarget(surface2);
                                } else {
                                    outputConfiguration = new OutputConfiguration(new Size(previewSize.width, previewSize.height), SurfaceHolder.class);
                                }
                                this.mDeferOutputConfigurations.add(0, outputConfiguration);
                                arrayList.add(0, outputConfiguration);
                                z5 = false;
                            } else {
                                Log.d(TAG, "startPreviewSession: add preview surface to HAL: " + this.mPreviewSurface + "->" + SurfaceUtils.getSurfaceSize(this.mPreviewSurface));
                                z5 = false;
                                arrayList.add(0, new OutputConfiguration(this.mPreviewSurface));
                                this.mPreviewRequestBuilder.addTarget(this.mPreviewSurface);
                            }
                            if (!b.isMTKPlatform()) {
                                if (!z7 && !z8 && this.mConfigs.isZslEnabled()) {
                                    z5 = true;
                                }
                                if (z5) {
                                    Log.d(TAG, "turns capture.zsl.mode on");
                                    this.mSessionConfigs.set(CaptureRequestVendorTags.ZSL_CAPTURE_MODE, (byte) 1);
                                    MiCameraCompat.applyZsd(this.mPreviewRequestBuilder, true);
                                }
                                Log.d(TAG, "turns quick preview on");
                                this.mSessionConfigs.set(CaptureRequestVendorTags.CONTROL_QUICK_PREVIEW, CaptureRequestVendorTags.CONTROL_QUICK_PREVIEW_ON);
                                MiCameraCompat.applyQuickPreview(this.mPreviewRequestBuilder, true);
                                CompatibilityUtils.createCaptureSessionWithSessionConfiguration(this.mCameraDevice, i, inputConfiguration, arrayList, this.mPreviewRequestBuilder.build(), this.mCaptureSessionStateCallback, this.mCameraHandler);
                            } else {
                                CaptureRequestBuilder.applySmoothTransition(this.mCapabilities, this.mPreviewRequestBuilder, true);
                                this.mCameraDevice.createCustomCaptureSession(inputConfiguration, arrayList, i, this.mCaptureSessionStateCallback, this.mCameraHandler);
                            }
                        }
                    }
                    inputConfiguration = null;
                    this.mCaptureSessionStateCallback = new CaptureSessionStateCallback(this.mSessionId, cameraPreviewCallback);
                    if (this.mPreviewSurface != null) {
                    }
                    if (!b.isMTKPlatform()) {
                    }
                } catch (CameraAccessException e2) {
                    CameraAccessException cameraAccessException = e2;
                    Log.e(TAG, "Failed to start preview session", cameraAccessException);
                    notifyOnError(cameraAccessException.getReason());
                } catch (IllegalStateException e3) {
                    Log.e(TAG, "Failed to start preview session, IllegalState", e3);
                    notifyOnError(256);
                } catch (IllegalArgumentException e4) {
                    Log.e(TAG, "Failed to start preview session, IllegalArgument", e4);
                    notifyOnError(256);
                } catch (Throwable th) {
                    throw th;
                }
            }
        }
    }

    public void startRecordPreview() {
        if (checkCameraDevice("startRecordPreview")) {
            Log.d(TAG, "startRecordPreview");
            synchronized (this.mVideoRecordStateLock) {
                this.mVideoRecordStateCallback = null;
            }
            try {
                this.mPreviewRequestBuilder = this.mCameraDevice.createCaptureRequest(3);
                this.mPreviewRequestBuilder.addTarget(this.mPreviewSurface);
                if (this.mConfigs.isEISEnabled()) {
                    VendorTagHelper.setValue(this.mPreviewRequestBuilder, CaptureRequestVendorTags.VIDEO_RECORD_CONTROL, 0);
                }
                applySettingsForVideo(this.mPreviewRequestBuilder);
                resumePreview();
            } catch (CameraAccessException e2) {
                Log.e(TAG, "Failed to start record preview", e2);
                notifyOnError(e2.getReason());
            } catch (IllegalStateException e3) {
                Log.e(TAG, "Failed to start record preview, IllegalState", e3);
                notifyOnError(256);
            }
        }
    }

    public void startRecordSession(@NonNull Surface surface, @NonNull Surface surface2, boolean z, int i, Camera2Proxy.CameraPreviewCallback cameraPreviewCallback) {
        List<Surface> list;
        if (checkCameraDevice("startRecordSession")) {
            Log.d(TAG, "startRecordSession: previewSurface=" + surface + " recordSurface=" + surface2);
            this.mPreviewSurface = surface;
            this.mRecordSurface = surface2;
            this.mSessionId = genSessionId();
            this.mVideoSessionId = this.mSessionId;
            try {
                this.mPreviewRequestBuilder = this.mCameraDevice.createCaptureRequest(3);
                this.mPreviewRequestBuilder.addTarget(this.mPreviewSurface);
                boolean z2 = false;
                if (this.mConfigs.isEISEnabled()) {
                    VendorTagHelper.setValue(this.mPreviewRequestBuilder, CaptureRequestVendorTags.VIDEO_RECORD_CONTROL, 0);
                }
                synchronized (this.mSessionLock) {
                    Log.d(TAG, "startRecordSession: reset session " + this.mCaptureSession);
                    this.mCaptureSession = null;
                }
                if (z) {
                    prepareVideoSnapshotImageReader(this.mConfigs.getVideoSnapshotSize());
                    list = Arrays.asList(new Surface[]{this.mPreviewSurface, this.mRecordSurface, this.mVideoSnapshotImageReader.getSurface()});
                } else {
                    list = Arrays.asList(new Surface[]{this.mPreviewSurface, this.mRecordSurface});
                }
                ArrayList arrayList = new ArrayList(list.size());
                for (Surface outputConfiguration : list) {
                    arrayList.add(new OutputConfiguration(outputConfiguration));
                }
                Log.d(TAG, "startRecordSession: operatingMode is " + Integer.toHexString(i));
                if (b.isMTKPlatform()) {
                    if (i == 32828) {
                        z2 = true;
                    }
                    if (z2) {
                        this.mSessionConfigs.set(CaptureRequestVendorTags.HFPSVR_MODE, 1);
                        MiCameraCompat.applyHighFpsVideoRecordingMode(this.mPreviewRequestBuilder, true);
                        Log.d(TAG, "startRecordSession: turns hfpsmode on");
                    }
                    CompatibilityUtils.createCaptureSessionWithSessionConfiguration(this.mCameraDevice, i, (InputConfiguration) null, arrayList, this.mPreviewRequestBuilder.build(), new CaptureSessionStateCallback(this.mSessionId, cameraPreviewCallback), this.mCameraHandler);
                    return;
                }
                this.mCameraDevice.createCustomCaptureSession((InputConfiguration) null, arrayList, i, new CaptureSessionStateCallback(this.mSessionId, cameraPreviewCallback), this.mCameraHandler);
            } catch (CameraAccessException e2) {
                Log.e(TAG, "Failed to start recording session", e2);
                notifyOnError(e2.getReason());
            } catch (IllegalStateException e3) {
                Log.e(TAG, "Failed to start recording session, IllegalState", e3);
                notifyOnError(256);
            }
        }
    }

    public void startRecording() {
        if (checkCaptureSession("startRecording")) {
            try {
                Log.d(TAG, "E: startRecording");
                if (this.mConfigs.isEISEnabled()) {
                    setVideoRecordControl(1);
                }
                this.mPreviewRequestBuilder = this.mCameraDevice.createCaptureRequest(3);
                this.mPreviewRequestBuilder.addTarget(this.mPreviewSurface);
                this.mPreviewRequestBuilder.addTarget(this.mRecordSurface);
                applySettingsForVideo(this.mPreviewRequestBuilder);
                resumePreview();
                Log.d(TAG, "X: startRecording");
            } catch (CameraAccessException e2) {
                Log.e(TAG, "Failed to start recording", e2);
                notifyOnError(e2.getReason());
            } catch (IllegalStateException e3) {
                Log.e(TAG, "Failed to start recording, IllegalState", e3);
                notifyOnError(256);
            }
        }
    }

    public void stopFaceDetection() {
        Log.v(TAG, "stopFaceDetection");
        this.mConfigs.setFaceDetectionEnabled(false);
        CaptureRequestBuilder.applyFaceDetection(this.mPreviewRequestBuilder, this.mConfigs);
    }

    public void stopObjectTrack() {
    }

    public void stopPreviewCallback(boolean z) {
        String str = TAG;
        Log.v(str, "stopPreviewCallback(): isRelease = " + z);
        if (this.mIsPreviewCallbackEnabled && this.mIsPreviewCallbackStarted && this.mPreviewImageReader != null) {
            this.mIsPreviewCallbackStarted = false;
            setPreviewCallback((Camera2Proxy.PreviewCallback) null);
            Surface surface = this.mPreviewImageReader.getSurface();
            this.mPreviewRequestBuilder.removeTarget(surface);
            surface.release();
            if (!z && checkCaptureSession("stopPreviewCallback")) {
                resumePreview();
            }
        }
    }

    public void stopRecording(Camera2Proxy.VideoRecordStateCallback videoRecordStateCallback) {
        if (checkCaptureSession("stopRecording")) {
            Log.d(TAG, "stopRecording");
            if (this.mConfigs.isEISEnabled()) {
                synchronized (this.mVideoRecordStateLock) {
                    this.mVideoRecordStateCallback = videoRecordStateCallback;
                }
                try {
                    setVideoRecordControl(2);
                } catch (CameraAccessException e2) {
                    e2.printStackTrace();
                    Log.e(TAG, "Failed to stop recording");
                    notifyOnError(e2.getReason());
                } catch (IllegalStateException e3) {
                    Log.e(TAG, "Failed to stop recording, IllegalState", e3);
                    notifyOnError(256);
                }
            }
        }
    }

    public void takePicture(@NonNull Camera2Proxy.PictureCallback pictureCallback, @NonNull ParallelCallback parallelCallback) {
        Log.v(TAG, "takePicture");
        setPictureCallback(pictureCallback);
        setParallelCallback(parallelCallback);
        triggerCapture();
    }

    public void unRegisterCaptureCallback() {
        this.mFrontQuickCaptureCallback = null;
        Log.d(TAG, "unRegisterCaptureCallback");
    }

    public void unlockExposure() {
        if (checkCaptureSession("unlockExposure")) {
            this.mCaptureCallback.setState(1);
            setAELock(false);
            CaptureRequestBuilder.applyAELock(this.mPreviewRequestBuilder, false);
            resumePreview();
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:53:0x00f3, code lost:
        return true;
     */
    public boolean updateDeferPreviewSession(Surface surface) {
        synchronized (this.mSessionLock) {
            if (this.mPreviewSurface == null) {
                this.mPreviewSurface = surface;
                this.mDeferPreviewSurface = surface;
            }
            if (this.mDeferOutputConfigurations.isEmpty()) {
                Log.d(TAG, "updateDeferPreviewSession: it is no need to update:");
                return false;
            }
            if (this.mCaptureSession != null) {
                if (this.mPreviewSurface != null) {
                    if (!isLocalParallelServiceReady()) {
                        Log.d(TAG, "updateDeferPreviewSession: ParallelService is not ready");
                        this.mHelperHandler.removeMessages(2);
                        this.mHelperHandler.sendEmptyMessageDelayed(2, 10);
                        return false;
                    }
                    this.mPreviewRequestBuilder.addTarget(this.mPreviewSurface);
                    try {
                        ArrayList arrayList = new ArrayList();
                        if (this.mFakeOutputTexture != null) {
                            OutputConfiguration outputConfiguration = this.mDeferOutputConfigurations.get(0);
                            this.mDeferOutputConfigurations.remove(0);
                            if (this.mSetRepeatingEarly) {
                                this.mPreviewRequestBuilder.removeTarget(outputConfiguration.getSurface());
                            }
                            outputConfiguration.addSurface(this.mPreviewSurface);
                            arrayList.add(outputConfiguration);
                        }
                        if (this.mEnableParallelSession && !this.mRemoteImageReaderList.isEmpty()) {
                            this.mParallelCaptureSurfaceList = prepareRemoteImageReader((List<IImageReaderParameterSets>) null);
                            if (this.mParallelCaptureSurfaceList != null) {
                                for (int i = 0; i < this.mDeferOutputConfigurations.size(); i++) {
                                    OutputConfiguration outputConfiguration2 = this.mDeferOutputConfigurations.get(i);
                                    outputConfiguration2.addSurface(this.mParallelCaptureSurfaceList.get(i));
                                    arrayList.add(outputConfiguration2);
                                }
                            }
                        }
                        if (this.mEnableParallelSession) {
                            configMaxParallelRequestNumberLock();
                        }
                        this.mCaptureSession.finalizeOutputConfigurations(arrayList);
                        Log.d(TAG, "updateDeferPreviewSession: finalizeOutputConfigurations success");
                    } catch (Exception e2) {
                        Log.e(TAG, "updateDeferPreviewSession: finalizeOutputConfigurations failed", e2);
                    }
                    for (ImageReader close : this.mRemoteImageReaderList) {
                        close.close();
                    }
                    this.mRemoteImageReaderList.clear();
                    this.mDeferOutputConfigurations.clear();
                    if (this.mCaptureSessionStateCallback != null) {
                        this.mCaptureSessionStateCallback.onPreviewSessionSuccess();
                    }
                }
            }
            Log.d(TAG, "updateDeferPreviewSession: it is no ready to update:");
            return false;
        }
    }

    public void updateFrameNumber(long j) {
        this.mCurrentFrameNum = j;
    }

    public boolean useLegacyFlashStrategy() {
        return this.mConfigs.isUseLegacyFlashMode();
    }
}
