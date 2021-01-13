package com.android.camera.module;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.SurfaceTexture;
import android.hardware.camera2.CameraCaptureSession;
import android.hardware.camera2.CaptureResult;
import android.location.Location;
import android.media.AudioDeviceCallback;
import android.media.AudioDeviceInfo;
import android.media.AudioManager;
import android.media.AudioSystem;
import android.media.CamcorderProfile;
import android.media.CameraProfile;
import android.media.MediaCodec;
import android.media.MediaRecorder;
import android.net.Uri;
import android.os.Build;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.ParcelFileDescriptor;
import android.os.SystemClock;
import android.os.SystemProperties;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.Range;
import android.util.Size;
import android.util.TypedValue;
import android.view.Surface;
import android.widget.Toast;
import androidx.annotation.MainThread;
import androidx.core.app.NotificationManagerCompat;
import b.c.a.c;
import com.android.camera.AutoLockManager;
import com.android.camera.Camera;
import com.android.camera.CameraIntentManager;
import com.android.camera.CameraSettings;
import com.android.camera.CameraSize;
import com.android.camera.FileCompat;
import com.android.camera.HybridZoomingSystem;
import com.android.camera.LocationManager;
import com.android.camera.R;
import com.android.camera.SettingUiState;
import com.android.camera.ThermalHelper;
import com.android.camera.ToastUtils;
import com.android.camera.Util;
import com.android.camera.constant.AutoFocus;
import com.android.camera.constant.UpdateConstant;
import com.android.camera.data.DataRepository;
import com.android.camera.data.data.config.ComponentConfigBokeh;
import com.android.camera.data.data.config.ComponentConfigHdr;
import com.android.camera.data.data.config.ComponentConfigSlowMotion;
import com.android.camera.data.data.config.ComponentRunning8KVideoQuality;
import com.android.camera.data.data.config.ComponentRunningMacroMode;
import com.android.camera.data.data.runing.ComponentRunningAiAudio;
import com.android.camera.data.data.runing.ComponentRunningEisPro;
import com.android.camera.data.provider.DataProvider;
import com.android.camera.effect.EffectController;
import com.android.camera.effect.FilterInfo;
import com.android.camera.fragment.top.FragmentTopAlert;
import com.android.camera.lib.compatibility.util.CompatibilityUtils;
import com.android.camera.log.Log;
import com.android.camera.module.loader.FunctionParseHistogramStats;
import com.android.camera.module.loader.camera2.Camera2DataContainer;
import com.android.camera.module.loader.camera2.FocusManager2;
import com.android.camera.preferences.CameraSettingPreferences;
import com.android.camera.protocol.ModeCoordinatorImpl;
import com.android.camera.protocol.ModeProtocol;
import com.android.camera.statistic.CameraStatUtils;
import com.android.camera.statistic.MistatsConstants;
import com.android.camera.statistic.MistatsWrapper;
import com.android.camera.statistic.ScenarioTrackUtil;
import com.android.camera.storage.ImageSaver;
import com.android.camera.storage.Storage;
import com.android.camera.ui.ObjectView;
import com.android.camera.ui.PopupManager;
import com.android.camera.ui.RotateTextToast;
import com.android.camera.ui.VideoTagView;
import com.android.camera.ui.zoom.ZoomingAction;
import com.android.camera2.Camera2Proxy;
import com.android.camera2.CameraCapabilities;
import com.android.camera2.CameraConfigs;
import com.android.camera2.autozoom.AutoZoomCaptureResult;
import com.android.gallery3d.exif.ExifHelper;
import com.miui.extravideo.interpolation.VideoInterpolator;
import com.ss.android.vesdk.VEEditor;
import com.xiaomi.camera.rx.CameraSchedulers;
import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import io.reactivex.FlowableEmitter;
import io.reactivex.FlowableOnSubscribe;
import io.reactivex.Observable;
import io.reactivex.Single;
import io.reactivex.SingleEmitter;
import io.reactivex.SingleOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import miui.os.Build;
import miui.reflect.Method;

public class VideoModule extends VideoBase implements ModeProtocol.AutoZoomModuleProtocol, ModeProtocol.TopConfigProtocol, MediaRecorder.OnErrorListener, MediaRecorder.OnInfoListener, ObjectView.ObjectViewListener {
    private static final HashMap<String, Integer> HEVC_VIDEO_ENCODER_BITRATE = new HashMap<>();
    private static final int MAX_DURATION_4K = 480000;
    private static final int MAX_DURATION_8K = 360000;
    private static final int RESET_VIDEO_AUTO_FOCUS_TIME = 3000;
    public static final Size SIZE_1080 = new Size(1920, 1080);
    public static final Size SIZE_720 = new Size(1280, Util.LIMIT_SURFACE_WIDTH);
    private static final long START_OFFSET_MS = 450;
    private static final int VIDEO_HFR_FRAME_RATE_120 = 120;
    private static final int VIDEO_HFR_FRAME_RATE_240 = 240;
    public static final long VIDEO_MAX_SINGLE_FILE_SIZE = 3670016000L;
    public static final long VIDEO_MIN_SINGLE_FILE_SIZE = Math.min(8388608L, (long) Storage.LOW_STORAGE_THRESHOLD);
    private static final int VIDEO_NORMAL_FRAME_RATE = 30;
    private AtomicBoolean isAutoZoomTracking = new AtomicBoolean(false);
    private AtomicBoolean isShowOrHideUltraWideHint = new AtomicBoolean(false);
    private AudioManager mAudioManager;
    private AudioManagerAudioDeviceCallback mAudioManagerAudioDeviceCallback;
    private Disposable mAutoZoomDataDisposable;
    private FlowableEmitter<CaptureResult> mAutoZoomEmitter;
    private Disposable mAutoZoomUiDisposable;
    private ModeProtocol.AutoZoomViewProtocol mAutoZoomViewProtocol;
    private boolean mCaptureTimeLapse;
    private CountDownTimer mCountDownTimer;
    private volatile int mCurrentFileNumber;
    private Boolean mDumpOrig960 = null;
    private boolean mFovcEnabled;
    private int mFrameRate;
    private int mFrameRateTrack;
    private int mHfrFPSLower;
    private int mHfrFPSUpper;
    private Disposable mHistogramDisposable;
    private FlowableEmitter<CaptureResult> mHistogramEmitter;
    private boolean mIsSubtitleSupported;
    private boolean mIsVideoTagSupported;
    private final Object mLock = new Object();
    private MediaRecorder mMediaRecorder;
    private boolean mMediaRecorderPostProcessing;
    private boolean mMediaRecorderWorking;
    private String mNextVideoFileName;
    private ContentValues mNextVideoValues;
    private long mPauseClickTime = 0;
    private volatile boolean mPendingStopRecorder;
    private CamcorderProfile mProfile;
    protected Optional<ModeProtocol.DualVideoRenderProtocol> mProtolRender = Optional.empty();
    private int mQuality = 5;
    private boolean mQuickCapture;
    protected Surface mRecorderSurface;
    private String mRecordingTime;
    private boolean mRecordingTimeCountsDown;
    private String mSlowModeFps;
    private boolean mSnapshotInProgress;
    private String mSpeed = "normal";
    private boolean mSplitWhenReachMaxSize;
    private CountDownLatch mStopRecorderDone;
    private ModeProtocol.SubtitleRecording mSubtitleRecording;
    private String mTemporaryVideoPath;
    private int mTimeBetweenTimeLapseFrameCaptureMs = 0;
    private long mTimeLapseDuration = 0;
    private int mTrackLostCount;
    protected long mVideoRecordTime = 0;
    private long mVideoRecordedDuration;
    private String mVideoTagFileName;

    private class AudioManagerAudioDeviceCallback extends AudioDeviceCallback {
        private AudioManagerAudioDeviceCallback() {
        }

        public void onAudioDevicesAdded(AudioDeviceInfo[] audioDeviceInfoArr) {
            VideoModule.this.onAudioDevicesAddedOrRemoved(audioDeviceInfoArr, true);
        }

        public void onAudioDevicesRemoved(AudioDeviceInfo[] audioDeviceInfoArr) {
            VideoModule.this.onAudioDevicesAddedOrRemoved(audioDeviceInfoArr, false);
        }
    }

    public final class JpegPictureCallback extends Camera2Proxy.PictureCallbackWrapper {
        Location mLocation;

        public JpegPictureCallback(Location location) {
            this.mLocation = location;
        }

        private void storeImage(byte[] bArr, Location location) {
            long currentTimeMillis = System.currentTimeMillis();
            int orientation = ExifHelper.getOrientation(bArr);
            ImageSaver imageSaver = VideoModule.this.mActivity.getImageSaver();
            boolean needImageThumbnail = VideoModule.this.needImageThumbnail(12);
            String createJpegName = Util.createJpegName(currentTimeMillis);
            long currentTimeMillis2 = System.currentTimeMillis();
            CameraSize cameraSize = VideoModule.this.mPictureSize;
            imageSaver.addImage(bArr, needImageThumbnail, createJpegName, null, currentTimeMillis2, null, location, cameraSize.width, cameraSize.height, null, orientation, false, false, true, false, false, null, null, -1, null);
        }

        @Override // com.android.camera2.Camera2Proxy.PictureCallbackWrapper, com.android.camera2.Camera2Proxy.PictureCallback
        public void onPictureTaken(byte[] bArr, CaptureResult captureResult) {
            Log.v(VideoBase.TAG, "onPictureTaken");
            VideoModule.this.mSnapshotInProgress = false;
            if (!VideoModule.this.mPaused && bArr != null) {
                Location location = null;
                if (VideoModule.this.mActivity.getCameraIntentManager().checkIntentLocationPermission(VideoModule.this.mActivity)) {
                    location = this.mLocation;
                }
                storeImage(bArr, location);
            }
        }
    }

    static {
        HEVC_VIDEO_ENCODER_BITRATE.put("3840x2160:30", 38500000);
        HEVC_VIDEO_ENCODER_BITRATE.put("1920x1080:30", 15400000);
        HEVC_VIDEO_ENCODER_BITRATE.put("1280x720:30", 10780000);
        HEVC_VIDEO_ENCODER_BITRATE.put("720x480:30", 1379840);
    }

    public VideoModule() {
        super(VideoModule.class.getSimpleName());
    }

    static /* synthetic */ Boolean a(ModeProtocol.DualVideoRenderProtocol dualVideoRenderProtocol) {
        return Boolean.valueOf(dualVideoRenderProtocol.getControler() != null);
    }

    static /* synthetic */ Boolean b(ModeProtocol.DualVideoRenderProtocol dualVideoRenderProtocol) {
        return Boolean.valueOf(dualVideoRenderProtocol.getControler() == null);
    }

    static /* synthetic */ Boolean c(ModeProtocol.DualVideoRenderProtocol dualVideoRenderProtocol) {
        return Boolean.valueOf(dualVideoRenderProtocol.getControler() != null);
    }

    /* access modifiers changed from: private */
    /* access modifiers changed from: public */
    private void consumeAutoZoomData(AutoZoomCaptureResult autoZoomCaptureResult) {
        if (isAlive() && !this.mActivity.isActivityPaused() && this.isAutoZoomTracking.get()) {
            this.mAutoZoomViewProtocol.feedData(autoZoomCaptureResult);
        }
    }

    private void countDownForVideoBokeh() {
        if (this.mMediaRecorderRecording) {
            CountDownTimer countDownTimer = this.mCountDownTimer;
            if (countDownTimer != null) {
                countDownTimer.cancel();
            }
            this.mCountDownTimer = new CountDownTimer(30450, 1000) {
                /* class com.android.camera.module.VideoModule.AnonymousClass7 */

                public void onFinish() {
                    VideoModule.this.stopVideoRecording(false);
                }

                public void onTick(long j) {
                    String millisecondToTimeString = Util.millisecondToTimeString((j + 950) - 450, false);
                    ModeProtocol.TopAlert topAlert = VideoModule.this.mTopAlert;
                    if (topAlert != null) {
                        topAlert.updateRecordingTime(millisecondToTimeString);
                    }
                }
            };
            this.mCountDownTimer.start();
        }
    }

    private void forceToNormalMode() {
        DataProvider.ProviderEditor editor = DataRepository.dataItemConfig().editor();
        editor.putString(CameraSettings.KEY_VIDEO_SPEED, "normal");
        editor.apply();
        this.mSpeed = "normal";
    }

    private int getHSRValue() {
        String hSRValue = CameraSettings.getHSRValue(isUltraWideBackCamera());
        if (hSRValue == null || hSRValue.isEmpty() || hSRValue.equals("off")) {
            return 0;
        }
        return Integer.parseInt(hSRValue);
    }

    private int getHevcVideoEncoderBitRate(CamcorderProfile camcorderProfile) {
        String str = camcorderProfile.videoFrameWidth + "x" + camcorderProfile.videoFrameHeight + ":" + camcorderProfile.videoFrameRate;
        if (HEVC_VIDEO_ENCODER_BITRATE.containsKey(str)) {
            return HEVC_VIDEO_ENCODER_BITRATE.get(str).intValue();
        }
        Log.d(VideoBase.TAG, "no pre-defined bitrate for " + str);
        return camcorderProfile.videoBitRate;
    }

    private String getManualValue(String str, String str2) {
        return ModuleManager.isProVideoModule() ? CameraSettingPreferences.instance().getString(str, str2) : str2;
    }

    private int getOperatingModeForPreview() {
        if (CameraSettings.isAutoZoomEnabled(this.mModuleIndex)) {
            return CameraCapabilities.SESSION_OPERATION_MODE_AUTO_ZOOM;
        }
        return 0;
    }

    private long getRecorderMaxFileSize(int i) {
        long leftSpace = Storage.getLeftSpace() - Storage.LOW_STORAGE_THRESHOLD;
        if (i > 0) {
            long j = (long) i;
            if (j < leftSpace) {
                leftSpace = j;
            }
        }
        long j2 = VIDEO_MAX_SINGLE_FILE_SIZE;
        if (leftSpace <= VIDEO_MAX_SINGLE_FILE_SIZE || !DataRepository.dataItemFeature().Ti()) {
            j2 = VIDEO_MIN_SINGLE_FILE_SIZE;
            if (leftSpace >= j2) {
                j2 = leftSpace;
            }
        }
        long j3 = this.mIntentRequestSize;
        return (j3 <= 0 || j3 >= j2) ? j2 : j3;
    }

    private int getRecorderOrientationHint() {
        int sensorOrientation = this.mCameraCapabilities.getSensorOrientation();
        return this.mOrientation != -1 ? isFrontCamera() ? ((sensorOrientation - this.mOrientation) + 360) % 360 : (sensorOrientation + this.mOrientation) % 360 : sensorOrientation;
    }

    private float getResourceFloat(int i, float f) {
        TypedValue typedValue = new TypedValue();
        try {
            this.mActivity.getResources().getValue(i, typedValue, true);
            return typedValue.getFloat();
        } catch (Exception unused) {
            String str = VideoBase.TAG;
            Log.e(str, "Missing resource " + Integer.toHexString(i));
            return f;
        }
    }

    private long getSpeedRecordVideoLength(long j, double d2) {
        if (d2 == 0.0d) {
            return 0;
        }
        return (long) (((((double) j) / d2) / ((double) getNormalVideoFrameRate())) * 1000.0d);
    }

    private void handleAiAudioTipsState(boolean z) {
        if (DataRepository.dataItemFeature().cj()) {
            int i = this.mModuleIndex;
            if ((i == 162 || i == 180) && !isFrontCamera()) {
                ModeProtocol.TopAlert topAlert = (ModeProtocol.TopAlert) ModeCoordinatorImpl.getInstance().getAttachProtocol(172);
                int currentStringRes = DataRepository.dataItemRunning().getComponentRunningAiAudio().getCurrentStringRes(this.mModuleIndex);
                if (topAlert != null && currentStringRes != -1) {
                    if (!Util.isWiredHeadsetOn()) {
                        topAlert.alertAiAudio(z ? 0 : 8, currentStringRes);
                    } else if (z) {
                        topAlert.alertAiAudioMutexToastIfNeed(getActivity(), currentStringRes);
                    }
                }
            }
        }
    }

    private void hideHint() {
        ModeProtocol.TopAlert topAlert = (ModeProtocol.TopAlert) ModeCoordinatorImpl.getInstance().getAttachProtocol(172);
        if (topAlert != null) {
            topAlert.alertMacroModeHint(8, DataRepository.dataItemRunning().getComponentRunningMacroMode().getResText());
            topAlert.alertAiAudioBGHint(8, R.string.pref_camera_rec_type_audio_zoom);
            topAlert.alertSwitchTip(FragmentTopAlert.TIP_SUPER_EIS, 8, R.string.super_eis);
            topAlert.alertSwitchTip(FragmentTopAlert.TIP_VIDEO_BEAUTIFY, 8, R.string.video_beauty_tip_beautification);
            topAlert.alertSwitchTip(FragmentTopAlert.TIP_VIDEO_BEAUTIFY, 8, R.string.video_beauty_tip);
            topAlert.hideSwitchTip();
        }
        ModeProtocol.BottomPopupTips bottomPopupTips = (ModeProtocol.BottomPopupTips) ModeCoordinatorImpl.getInstance().getAttachProtocol(175);
        if (bottomPopupTips != null) {
            bottomPopupTips.directlyHideTipsForce();
        }
    }

    private void initAutoZoom() {
        if (DataRepository.dataItemFeature().fj()) {
            if (CameraSettings.isAutoZoomEnabled(this.mModuleIndex)) {
                startAutoZoom();
            } else {
                stopAutoZoom();
            }
            this.mAutoZoomDataDisposable = Flowable.create(new FlowableOnSubscribe<CaptureResult>() {
                /* class com.android.camera.module.VideoModule.AnonymousClass3 */

                @Override // io.reactivex.FlowableOnSubscribe
                public void subscribe(FlowableEmitter<CaptureResult> flowableEmitter) throws Exception {
                    VideoModule.this.mAutoZoomEmitter = flowableEmitter;
                }
            }, BackpressureStrategy.DROP).observeOn(CameraSchedulers.sCameraSetupScheduler).map(new Function<CaptureResult, AutoZoomCaptureResult>() {
                /* class com.android.camera.module.VideoModule.AnonymousClass2 */

                public AutoZoomCaptureResult apply(CaptureResult captureResult) throws Exception {
                    return new AutoZoomCaptureResult(captureResult);
                }
            }).observeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer<AutoZoomCaptureResult>() {
                /* class com.android.camera.module.VideoModule.AnonymousClass1 */

                public void accept(AutoZoomCaptureResult autoZoomCaptureResult) throws Exception {
                    VideoModule.this.consumeAutoZoomData(autoZoomCaptureResult);
                }
            });
        }
    }

    private void initHistogramEmitter() {
        if (this.mModuleIndex == 180 && this.mCameraCapabilities.isSupportHistogram()) {
            this.mHistogramDisposable = Flowable.create(new FlowableOnSubscribe<CaptureResult>() {
                /* class com.android.camera.module.VideoModule.AnonymousClass5 */

                @Override // io.reactivex.FlowableOnSubscribe
                public void subscribe(FlowableEmitter<CaptureResult> flowableEmitter) throws Exception {
                    VideoModule.this.mHistogramEmitter = flowableEmitter;
                }
            }, BackpressureStrategy.DROP).observeOn(CameraSchedulers.sCameraSetupScheduler).map(new FunctionParseHistogramStats(this, this.mTopAlert)).observeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer<int[]>() {
                /* class com.android.camera.module.VideoModule.AnonymousClass4 */

                public void accept(int[] iArr) throws Exception {
                    VideoModule videoModule = VideoModule.this;
                    if (videoModule.mMainProtocol != null) {
                        videoModule.mTopAlert.refreshHistogramStatsView();
                    }
                }
            });
        }
    }

    private void initRecorderSurface() {
        this.mRecorderSurface = MediaCodec.createPersistentInputSurface();
    }

    private boolean initializeObjectTrack(RectF rectF, boolean z) {
        mapTapCoordinate(rectF);
        stopObjectTracking(false);
        return this.mMainProtocol.initializeObjectTrack(rectF, z);
    }

    private boolean initializeRecorder(boolean z) {
        Log.d(VideoBase.TAG, "initializeRecorder>>");
        long currentTimeMillis = System.currentTimeMillis();
        boolean z2 = false;
        if (getActivity() == null) {
            Log.w(VideoBase.TAG, "initializeRecorder: null host");
            return false;
        }
        closeVideoFileDescriptor();
        cleanupEmptyFile();
        if (isCaptureIntent()) {
            handleTempVideoFile();
            parseIntent(this.mActivity.getIntent());
        }
        if (this.mVideoFileDescriptor == null) {
            this.mCurrentVideoValues = genContentValues(this.mOutputFormat, this.mCurrentFileNumber, this.mSlowModeFps, is8KCamcorder(), z);
            this.mCurrentVideoFilename = this.mCurrentVideoValues.getAsString("_data");
        }
        if (this.mStopRecorderDone != null) {
            long currentTimeMillis2 = System.currentTimeMillis();
            try {
                this.mStopRecorderDone.await(1000, TimeUnit.MILLISECONDS);
            } catch (InterruptedException e2) {
                e2.printStackTrace();
            }
            Log.d(VideoBase.TAG, "initializeRecorder: waitTime=" + (System.currentTimeMillis() - currentTimeMillis2));
        }
        long currentTimeMillis3 = System.currentTimeMillis();
        synchronized (this.mLock) {
            if (this.mMediaRecorder == null) {
                this.mMediaRecorder = new MediaRecorder();
            } else {
                this.mMediaRecorder.reset();
                if (BaseModule.DEBUG) {
                    Log.v(VideoBase.TAG, "initializeRecorder: t1=" + (System.currentTimeMillis() - currentTimeMillis3));
                }
            }
        }
        ParcelFileDescriptor parcelFileDescriptor = null;
        try {
            setupRecorder(this.mMediaRecorder);
            if (z) {
                setCurrentAiAudioParameters(true);
            }
            if (this.mVideoFileDescriptor == null) {
                if (Storage.isUseDocumentMode()) {
                    if (z) {
                        parcelFileDescriptor = FileCompat.getParcelFileDescriptor(this.mCurrentVideoFilename, true);
                        this.mMediaRecorder.setOutputFile(parcelFileDescriptor.getFileDescriptor());
                    }
                }
                this.mMediaRecorder.setOutputFile(this.mCurrentVideoFilename);
            } else if (z) {
                this.mMediaRecorder.setOutputFile(this.mVideoFileDescriptor.getFileDescriptor());
            } else {
                this.mMediaRecorder.setOutputFile(this.mTemporaryVideoPath);
            }
            this.mMediaRecorder.setInputSurface(this.mRecorderSurface);
            long currentTimeMillis4 = System.currentTimeMillis();
            this.mMediaRecorder.prepare();
            this.mMediaRecorder.setOnErrorListener(this);
            this.mMediaRecorder.setOnInfoListener(this);
            if (BaseModule.DEBUG) {
                Log.v(VideoBase.TAG, "initializeRecorder: t2=" + (System.currentTimeMillis() - currentTimeMillis4));
            }
            Util.closeSilently(parcelFileDescriptor);
            z2 = true;
        } catch (Exception e3) {
            Log.e(VideoBase.TAG, "prepare failed for " + this.mCurrentVideoFilename, e3);
            releaseMediaRecorder();
            Util.closeSilently(null);
        } catch (Throwable th) {
            Util.closeSilently(null);
            throw th;
        }
        if (z2 && BaseModule.DEBUG) {
            Util.showSurfaceInfo(this.mRecorderSurface);
        }
        Log.d(VideoBase.TAG, "initializeRecorder<<time=" + (System.currentTimeMillis() - currentTimeMillis));
        return z2;
    }

    private static boolean is4K30FpsEISSupported() {
        return DataRepository.dataItemFeature().is4K30FpsEISSupported();
    }

    private boolean is4K60FpsEISSupported() {
        return this.mCameraCapabilities.is4K60FpsEISSupported();
    }

    private boolean is4KCamcorder() {
        return this.mQuality == CameraSettings.get4kProfile() || new CameraSize(3840, 2160).equals(this.mVideoSize);
    }

    private boolean is8KCamcorder() {
        return this.mQuality == CameraSettings.get8kProfile() || new CameraSize(7680, 4320).equals(this.mVideoSize);
    }

    /* access modifiers changed from: private */
    /* access modifiers changed from: public */
    private boolean isActivityResumed() {
        Camera activity = getActivity();
        return activity != null && !activity.isActivityPaused();
    }

    private boolean isDump960Orig() {
        if (this.mDumpOrig960 == null) {
            this.mDumpOrig960 = SystemProperties.getBoolean("camera.record.960origdump", false) ? Boolean.TRUE : Boolean.FALSE;
        }
        return this.mDumpOrig960.booleanValue();
    }

    private boolean isEisOn() {
        if (!c.Nn()) {
            return false;
        }
        if (CameraSettings.isMacroModeEnabled(this.mModuleIndex) && isUltraWideBackCamera()) {
            return true;
        }
        if (this.mActualCameraId == Camera2DataContainer.getInstance().getStandaloneMacroCameraId()) {
            return false;
        }
        if (CameraSettings.isAutoZoomEnabled(this.mModuleIndex)) {
            Log.d(VideoBase.TAG, "videoStabilization: disabled EIS and OIS when AutoZoom is opened");
            return true;
        } else if (CameraSettings.isSuperEISEnabled(this.mModuleIndex) || CameraSettings.isVideoEisBeautyMeanwhileEnable()) {
            return true;
        } else {
            if (CameraSettings.isVhdrOn(this.mCameraCapabilities, this.mModuleIndex) || !CameraSettings.isMovieSolidOn()) {
                return false;
            }
            if ((!isNormalMode() && !isFastMode()) || needChooseVideoBeauty(this.mBeautyValues) || this.mQuality == 0) {
                return false;
            }
            int i = 60;
            boolean z = getHSRValue() == 60;
            CameraCapabilities cameraCapabilities = this.mCameraCapabilities;
            int i2 = this.mQuality;
            if (!z) {
                i = 30;
            }
            if (cameraCapabilities.isCurrentQualitySupportEis(i2, i)) {
                Log.d(VideoBase.TAG, "isEisOn, current quality support eis");
                return true;
            } else if (is8KCamcorder()) {
                return DataRepository.dataItemRunning().getComponentRunning8KVideoQuality().get8KEisIsSupported(this.mCameraCapabilities) && CameraSettings.isMovieSolidOn();
            } else {
                if (z) {
                    if (!is4K60FpsEISSupported()) {
                        return false;
                    }
                } else if (CameraSettings.is4KHigherVideoQuality(this.mQuality) && !is4K30FpsEISSupported()) {
                    return false;
                }
                return c.Xm() || !isFrontCamera();
            }
        }
    }

    /* access modifiers changed from: private */
    /* access modifiers changed from: public */
    private boolean isFPS120() {
        return ComponentConfigSlowMotion.DATA_CONFIG_NEW_SLOW_MOTION_120.equals(this.mSlowModeFps);
    }

    /* access modifiers changed from: private */
    /* access modifiers changed from: public */
    private boolean isFPS240() {
        return ComponentConfigSlowMotion.DATA_CONFIG_NEW_SLOW_MOTION_240.equals(this.mSlowModeFps);
    }

    /* access modifiers changed from: private */
    /* access modifiers changed from: public */
    private boolean isFPS960() {
        return ComponentConfigSlowMotion.DATA_CONFIG_NEW_SLOW_MOTION_960.equals(this.mSlowModeFps);
    }

    private boolean isFastMode() {
        return CameraSettings.VIDEO_SPEED_FAST.equals(this.mSpeed);
    }

    private boolean isNormalMode() {
        return "normal".equals(this.mSpeed);
    }

    private boolean isSplitWhenReachMaxSize() {
        return this.mSplitWhenReachMaxSize;
    }

    private boolean needDisableEISAndOIS() {
        if (!CameraSettings.isVideoBokehOn() || !isFrontCamera()) {
            return false;
        }
        Log.d(VideoBase.TAG, "videoStabilization: disabled EIS and OIS when VIDEO_BOKEH is opened");
        return true;
    }

    /* access modifiers changed from: private */
    /* access modifiers changed from: public */
    private boolean needImageThumbnail(int i) {
        return i != 12;
    }

    private void notifyAutoZoomStartUiHint() {
        notifyAutoZoomStopUiHint();
        ModeProtocol.TopAlert topAlert = this.mTopAlert;
        if (topAlert == null || !topAlert.isExtraMenuShowing()) {
            this.mAutoZoomUiDisposable = Observable.timer(800, TimeUnit.MILLISECONDS).observeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer<Long>() {
                /* class com.android.camera.module.VideoModule.AnonymousClass12 */

                public void accept(Long l) throws Exception {
                    ModeProtocol.TopAlert topAlert = VideoModule.this.mTopAlert;
                    if (topAlert != null) {
                        topAlert.alertAiDetectTipHint(0, R.string.autozoom_click_hint, -1);
                    }
                }
            });
        }
    }

    private void notifyAutoZoomStopUiHint() {
        Disposable disposable = this.mAutoZoomUiDisposable;
        if (disposable != null && !disposable.isDisposed()) {
            this.mAutoZoomUiDisposable.dispose();
            this.mAutoZoomUiDisposable = null;
        }
    }

    /* access modifiers changed from: private */
    /* access modifiers changed from: public */
    private void onAudioDevicesAddedOrRemoved(AudioDeviceInfo[] audioDeviceInfoArr, boolean z) {
        boolean z2;
        int i = this.mModuleIndex;
        if (i == 162 || i == 180) {
            boolean z3 = false;
            boolean z4 = z || !Util.isWiredHeadsetOn();
            int length = audioDeviceInfoArr.length;
            int i2 = 0;
            while (true) {
                if (i2 >= length) {
                    z2 = false;
                    break;
                }
                AudioDeviceInfo audioDeviceInfo = audioDeviceInfoArr[i2];
                Log.d(VideoBase.TAG, "Audio device added or removed: " + ((Object) audioDeviceInfo.getProductName()) + " type: " + audioDeviceInfo.getType() + " isAdd: " + z + " canBreakFlag: " + z4);
                if (Util.isWiredAudioHeadset(audioDeviceInfo) && z4) {
                    z2 = true;
                    break;
                }
                i2++;
            }
            Log.d(VideoBase.TAG, "onAudioDevicesAddedOrRemoved: " + z2);
            if (z2) {
                ModeProtocol.ConfigChanges configChanges = (ModeProtocol.ConfigChanges) ModeCoordinatorImpl.getInstance().getAttachProtocol(164);
                if (this.mMediaRecorderRecording || this.mMediaRecorderRecordingPaused) {
                    z3 = true;
                }
                if (configChanges != null) {
                    configChanges.reConfigAiAudio(this.mActivity, this.mModuleIndex, z3);
                }
                if (z3) {
                    setCurrentAiAudioParameters(true);
                    setCurrentAiAudioZoomLv();
                }
            }
        }
    }

    private void onMaxFileSizeReached() {
        String str = this.mCurrentVideoFilename;
        if (str != null) {
            saveVideo(str, this.mCurrentVideoValues, false, false);
            this.mCurrentVideoValues = null;
            this.mCurrentVideoFilename = null;
        }
    }

    private void onMediaRecorderReleased() {
        ModeProtocol.RecordState recordState;
        String str;
        Log.d(VideoBase.TAG, "onMediaRecorderReleased>>");
        long currentTimeMillis = System.currentTimeMillis();
        setCurrentAiAudioParameters(false);
        restoreOuterAudio();
        if (isCaptureIntent() && !this.mPaused) {
            if (this.mCurrentVideoUri == null && (str = this.mCurrentVideoFilename) != null) {
                this.mCurrentVideoUri = saveVideo(str, this.mCurrentVideoValues, true, true);
                String str2 = VideoBase.TAG;
                Log.d(str2, "onMediaRecorderReleased: outputUri=" + this.mCurrentVideoUri);
            }
            boolean z = this.mCurrentVideoUri != null;
            if (this.mQuickCapture) {
                doReturnToCaller(z);
            } else if (z && !getActivity().isActivityPaused()) {
                showAlert();
            }
        }
        if (this.mCurrentVideoFilename != null) {
            if (!isCaptureIntent()) {
                saveVideo(this.mCurrentVideoFilename, this.mCurrentVideoValues, true, false);
            }
            this.mCurrentVideoFilename = null;
            this.mCurrentVideoValues = null;
        } else if (!this.mPaused && !this.mActivity.isActivityPaused()) {
            this.mActivity.getThumbnailUpdater().getLastThumbnail();
        }
        if (this.mMediaRecorderPostProcessing && (recordState = (ModeProtocol.RecordState) ModeCoordinatorImpl.getInstance().getAttachProtocol(212)) != null) {
            recordState.onPostSavingFinish();
        }
        this.mActivity.sendBroadcast(new Intent(BaseModule.STOP_VIDEO_RECORDING_ACTION));
        this.mTelephonyManager.listen(this.mPhoneStateListener, 0);
        Log.v(VideoBase.TAG, "listen none");
        enableCameraControls(true);
        if (!AutoFocus.LEGACY_CONTINUOUS_VIDEO.equals(this.mVideoFocusMode)) {
            this.mFocusManager.resetFocusStateIfNeeded();
            if (!this.mPaused && !this.mActivity.isActivityPaused()) {
                this.mMainProtocol.clearFocusView(2);
                setVideoFocusMode(AutoFocus.LEGACY_CONTINUOUS_VIDEO, false);
                updatePreferenceInWorkThread(14);
            }
        }
        keepScreenOnAwhile();
        String str3 = VideoBase.TAG;
        Log.d(str3, "onMediaRecorderReleased<<time=" + (System.currentTimeMillis() - currentTimeMillis));
        ScenarioTrackUtil.trackStopVideoRecordEnd();
        doLaterReleaseIfNeed();
        if (this.mMediaRecorderPostProcessing) {
            this.mMediaRecorderPostProcessing = false;
        }
        this.mMediaRecorderWorking = false;
        this.mHandler.post(new Runnable() {
            /* class com.android.camera.module.VideoModule.AnonymousClass9 */

            public void run() {
                VideoModule.this.handlePendingScreenSlide();
            }
        });
    }

    private void onStartRecorderFail() {
        enableCameraControls(true);
        releaseMediaRecorder();
        restoreOuterAudio();
        ((ModeProtocol.RecordState) ModeCoordinatorImpl.getInstance().getAttachProtocol(212)).onFailed();
        updateVideoTagState(4);
    }

    private void onStartRecorderSucceed() {
        ModeProtocol.TopAlert topAlert;
        ModeProtocol.ConfigChanges configChanges;
        int i;
        ModeProtocol.SubtitleRecording subtitleRecording;
        if (!isFPS960()) {
            enableCameraControls(true);
        }
        this.mActivity.sendBroadcast(new Intent(BaseModule.START_VIDEO_RECORDING_ACTION));
        this.mMediaRecorderWorking = true;
        this.mMediaRecorderRecording = true;
        setCurrentAiAudioZoomLv();
        hideHint();
        if (this.mIsSubtitleSupported && (subtitleRecording = this.mSubtitleRecording) != null) {
            subtitleRecording.handleSubtitleRecordingStart();
        }
        ModeProtocol.MainContentProtocol mainContentProtocol = this.mMainProtocol;
        if (mainContentProtocol != null) {
            this.mVideoTagFileName = this.mCurrentVideoFilename;
            mainContentProtocol.processingStart(this.mIsVideoTagSupported ? this.mVideoTagFileName : null);
        }
        handleAiAudioTipsState(true);
        if (HybridZoomingSystem.IS_3_OR_MORE_SAT && (i = this.mModuleIndex) != 172 && i != 180 && !CameraSettings.isMacroModeEnabled(i) && !CameraSettings.isAutoZoomEnabled(this.mModuleIndex) && !CameraSettings.isSuperEISEnabled(this.mModuleIndex) && isBackCamera()) {
            updateZoomRatioToggleButtonState(true);
            if (isStandaloneMacroCamera()) {
                setMinZoomRatio(HybridZoomingSystem.getMinimumMacroOpticalZoomRatio());
                setMaxZoomRatio(HybridZoomingSystem.getMaximumMacroOpticalZoomRatio());
            } else if (isUltraWideBackCamera()) {
                if (CameraSettings.isMacroModeEnabled(this.mModuleIndex)) {
                    setMinZoomRatio(HybridZoomingSystem.getMinimumMacroOpticalZoomRatio());
                    setMaxZoomRatio(HybridZoomingSystem.getMaximumMacroOpticalZoomRatio());
                } else {
                    setMinZoomRatio(HybridZoomingSystem.FLOAT_ZOOM_RATIO_ULTR);
                    setMaxZoomRatio(2.0f);
                }
            } else if (isAuxCamera()) {
                setMinZoomRatio(HybridZoomingSystem.getTeleMinZoomRatio());
                setVideoMaxZoomRatioByTele();
            } else if (isUltraTeleCamera()) {
                setMinZoomRatio(HybridZoomingSystem.getUltraTeleMinZoomRatio());
                setVideoMaxZoomRatioByTele();
            } else if (!DataRepository.dataItemFeature().qm()) {
                setMinZoomRatio(1.0f);
                setMaxZoomRatio(Math.min(6.0f, this.mCameraCapabilities.getMaxZoomRatio()));
            } else if (isInVideoSAT()) {
                setMinZoomRatio(HybridZoomingSystem.FLOAT_ZOOM_RATIO_ULTR);
                setMaxZoomRatio(DataRepository.dataItemFeature().Gh());
            } else if (isStandaloneMacroCamera()) {
                setMinZoomRatio(1.0f);
                setMaxZoomRatio(Math.min(2.0f, this.mCameraCapabilities.getMaxZoomRatio()));
            } else {
                setMinZoomRatio(1.0f);
                setMaxZoomRatio(Math.min(6.0f, this.mCameraCapabilities.getMaxZoomRatio()));
            }
        }
        this.mMainProtocol.updateZoomRatio(getMinZoomRatio(), getMaxZoomRatio());
        this.mMediaRecorderRecordingPaused = false;
        this.mRecordingStartTime = SystemClock.uptimeMillis();
        this.mPauseClickTime = SystemClock.uptimeMillis();
        this.mRecordingTime = "";
        this.mTelephonyManager.listen(this.mPhoneStateListener, 32);
        Log.v(VideoBase.TAG, "listen call state");
        if (CameraSettings.isVideoBokehOn()) {
            countDownForVideoBokeh();
        } else {
            updateRecordingTime();
        }
        keepScreenOn();
        AutoLockManager.getInstance(this.mActivity).removeMessage();
        HashMap hashMap = new HashMap();
        hashMap.put(MistatsConstants.Manual.PARAM_3A_LOCKED, Boolean.valueOf(this.m3ALocked));
        trackGeneralInfo(hashMap, 1, false, this.mBeautyValues, false, 0);
        if (this.mCaptureTimeLapse && DataRepository.dataItemFeature().uj() && (configChanges = (ModeProtocol.ConfigChanges) ModeCoordinatorImpl.getInstance().getAttachProtocol(164)) != null) {
            configChanges.reCheckFastMotion(true);
        }
        if (this.mModuleIndex == 180 && (topAlert = this.mTopAlert) != null) {
            topAlert.updateProVideoRecordingSimpleView(true);
        }
        keepPowerSave();
    }

    /* access modifiers changed from: private */
    /* access modifiers changed from: public */
    /* JADX WARNING: Code restructure failed: missing block: B:33:0x00f6, code lost:
        if (r5 == false) goto L_0x00f8;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:34:0x00f8, code lost:
        com.android.camera.log.Log.e(com.android.camera.module.VideoBase.TAG, "960fps processing failed. delete the files.");
        r2.delete();
        r3.delete();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:38:0x010c, code lost:
        if (0 != 0) goto L_0x010f;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:39:0x010f, code lost:
        if (r5 == false) goto L_?;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:45:?, code lost:
        return r4.getAbsolutePath();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:46:?, code lost:
        return null;
     */
    private String postProcessVideo(String str) {
        boolean z;
        if (str == null) {
            return null;
        }
        File file = new File(str);
        File file2 = new File(str + ".bak");
        File file3 = Storage.isUseDocumentMode() ? new File(Storage.generatePrimaryFilepath(file.getName())) : new File(Storage.generateFilepath(file.getName()));
        boolean z2 = false;
        try {
            boolean z3 = !Build.IS_INTERNATIONAL_BUILD && CameraSettings.isSupport960VideoEditor();
            if (CameraSettings.is960WatermarkOn(getModuleIndex())) {
                Bitmap load960fpsCameraWatermark = Util.load960fpsCameraWatermark(this.mActivity);
                float resourceFloat = getResourceFloat(R.dimen.global_fps960_watermark_size_ratio, 0.0f);
                if (!Util.isGlobalVersion() || resourceFloat == 0.0f) {
                    resourceFloat = getResourceFloat(R.dimen.fps960_watermark_size_ratio, 0.0f);
                }
                z = VideoInterpolator.doDecodeAndEncodeSyncWithWatermark(file.getAbsolutePath(), file2.getAbsolutePath(), DataRepository.dataItemFeature().mj(), load960fpsCameraWatermark, new float[]{resourceFloat, getResourceFloat(R.dimen.fps960_watermark_padding_x_ratio, 0.0f), getResourceFloat(R.dimen.fps960_watermark_padding_y_ratio, 0.0f)}, z3);
            } else {
                Log.d(VideoBase.TAG, "postProcessVideo: start ");
                z = VideoInterpolator.doDecodeAndEncodeSync(file.getAbsolutePath(), file2.getAbsolutePath(), DataRepository.dataItemFeature().mj(), z3);
                Log.d(VideoBase.TAG, "postProcessVideo: end ");
            }
            if (z && file2.renameTo(file3)) {
                z2 = true;
            }
            if (isDump960Orig()) {
                file.renameTo(new File(str + ".orig.mp4"));
            } else {
                file.delete();
            }
        } catch (Throwable th) {
            if (0 == 0) {
                Log.e(VideoBase.TAG, "960fps processing failed. delete the files.");
                file.delete();
                file2.delete();
            }
            throw th;
        }
    }

    /* access modifiers changed from: private */
    /* access modifiers changed from: public */
    private void releaseMediaRecorder() {
        MediaRecorder mediaRecorder;
        Log.v(VideoBase.TAG, "releaseRecorder");
        synchronized (this.mLock) {
            mediaRecorder = this.mMediaRecorder;
            this.mMediaRecorder = null;
        }
        if (mediaRecorder != null) {
            cleanupEmptyFile();
            long currentTimeMillis = System.currentTimeMillis();
            mediaRecorder.reset();
            String str = VideoBase.TAG;
            Log.v(str, "releaseRecorder: t1=" + (System.currentTimeMillis() - currentTimeMillis));
            long currentTimeMillis2 = System.currentTimeMillis();
            mediaRecorder.release();
            String str2 = VideoBase.TAG;
            Log.v(str2, "releaseRecorder: t2=" + (System.currentTimeMillis() - currentTimeMillis2));
        }
    }

    private void releaseRecorderSurface() {
        Surface surface = this.mRecorderSurface;
        if (surface != null) {
            surface.release();
        }
    }

    private void releaseResources() {
        FlowableEmitter<CaptureResult> flowableEmitter = this.mAutoZoomEmitter;
        if (flowableEmitter != null) {
            flowableEmitter.onComplete();
        }
        Disposable disposable = this.mAutoZoomUiDisposable;
        if (disposable != null && !disposable.isDisposed()) {
            this.mAutoZoomUiDisposable.dispose();
            this.mAutoZoomUiDisposable = null;
        }
        Disposable disposable2 = this.mAutoZoomDataDisposable;
        if (disposable2 != null && !disposable2.isDisposed()) {
            this.mAutoZoomDataDisposable.dispose();
            this.mAutoZoomDataDisposable = null;
        }
        FlowableEmitter<CaptureResult> flowableEmitter2 = this.mHistogramEmitter;
        if (flowableEmitter2 != null) {
            flowableEmitter2.onComplete();
        }
        Disposable disposable3 = this.mHistogramDisposable;
        if (disposable3 != null && !disposable3.isDisposed()) {
            this.mHistogramDisposable.dispose();
            this.mHistogramDisposable = null;
        }
        stopTracking(0);
        stopAutoZoom();
        closeCamera();
        releaseMediaRecorder();
        handleTempVideoFile();
    }

    private Uri saveVideo(String str, ContentValues contentValues, boolean z, boolean z2) {
        if (this.mActivity != null) {
            String str2 = VideoBase.TAG;
            Log.w(str2, "saveVideo: path=" + str + " isFinal=" + z);
            contentValues.put("datetaken", Long.valueOf(System.currentTimeMillis()));
            if (z2) {
                return this.mActivity.getImageSaver().addVideoSync(str, contentValues, false);
            }
            this.mActivity.getImageSaver().addVideo(str, contentValues, z);
        } else {
            String str3 = VideoBase.TAG;
            Log.w(str3, "saveVideo: failed to save " + str);
        }
        return null;
    }

    private void setCurrentAiAudioParameters(boolean z) {
        ComponentRunningAiAudio componentRunningAiAudio;
        if (DataRepository.dataItemFeature().cj() && (componentRunningAiAudio = DataRepository.dataItemRunning().getComponentRunningAiAudio()) != null) {
            String currentParameters = componentRunningAiAudio.getCurrentParameters(this.mModuleIndex, z, this.mOrientation);
            String str = VideoBase.TAG;
            Log.d(str, "setCurrentAiAudioParameters.parameters = " + currentParameters);
            AudioSystem.setParameters(currentParameters);
        }
    }

    private void setCurrentAiAudioZoomLv() {
        if (DataRepository.dataItemFeature().cj() && !Util.isWiredHeadsetOn()) {
            int i = this.mModuleIndex;
            if ((i != 162 && i != 180) || isFrontCamera() || CameraSettings.isMacroLensOn(this.mModuleIndex) || DataRepository.dataItemRunning().getComponentRunningAiAudio().getCurrentRecType(this.mModuleIndex) == 1) {
                return;
            }
            if (this.mMediaRecorderRecording || this.mMediaRecorderRecordingPaused) {
                double deviceBasedZoomRatio = (double) getDeviceBasedZoomRatio();
                String str = "AURISYS_SET_PARAM,DSP,RECORD,FV_SPH,KEY_VALUE,audioZoom,ZoomLv@" + Util.getEnglishDecimalFormat().format(deviceBasedZoomRatio) + "/=SET";
                Log.d(VideoBase.TAG, "setCurrentAiAudioZoomLv.parameters = " + str);
                AudioSystem.setParameters(str);
            }
        }
    }

    private void setJpegQuality() {
        if (isDeviceAlive()) {
            int jpegEncodingQualityParameter = CameraProfile.getJpegEncodingQualityParameter(this.mBogusCameraId, 2);
            String str = VideoBase.TAG;
            Log.d(str, "jpegQuality=" + jpegEncodingQualityParameter);
            this.mCamera2Device.setJpegQuality(jpegEncodingQualityParameter);
        }
    }

    private boolean setNextOutputFile(String str) {
        if (TextUtils.isEmpty(str)) {
            Log.w(VideoBase.TAG, "setNextOutputFile, filePath is empty");
            return false;
        } else if (!Storage.isUseDocumentMode()) {
            return CompatibilityUtils.setNextOutputFile(this.mMediaRecorder, str);
        } else {
            ParcelFileDescriptor parcelFileDescriptor = null;
            try {
                parcelFileDescriptor = FileCompat.getParcelFileDescriptor(str, true);
                return CompatibilityUtils.setNextOutputFile(this.mMediaRecorder, parcelFileDescriptor.getFileDescriptor());
            } catch (Exception e2) {
                String str2 = VideoBase.TAG;
                Log.d(str2, "open file failed, filePath " + str, e2);
                return false;
            } finally {
                Util.closeSafely(parcelFileDescriptor);
            }
        }
    }

    private void setParameterExtra(MediaRecorder mediaRecorder, String str) {
        Class<?>[] clsArr = {MediaRecorder.class};
        Method method = Util.getMethod(clsArr, "setParameter", "(Ljava/lang/String;)V");
        if (method != null) {
            method.invoke(clsArr[0], mediaRecorder, str);
        }
    }

    private void setSplitWhenReachMaxSize(boolean z) {
        this.mSplitWhenReachMaxSize = z;
    }

    private void setupRecorder(MediaRecorder mediaRecorder) throws FileNotFoundException {
        int i;
        boolean isNormalMode = isNormalMode();
        boolean z = isNormalMode || ((isFPS120() || isFPS240()) && !DataRepository.dataItemFeature().hi());
        mediaRecorder.setVideoSource(2);
        if (z) {
            mediaRecorder.setAudioSource(5);
        }
        mediaRecorder.setOutputFormat(this.mProfile.fileFormat);
        mediaRecorder.setVideoEncoder(this.mProfile.videoCodec);
        if (getModuleIndex() == 204) {
            CamcorderProfile camcorderProfile = this.mProfile;
            mediaRecorder.setVideoSize(camcorderProfile.videoFrameHeight, camcorderProfile.videoFrameWidth);
        } else {
            CamcorderProfile camcorderProfile2 = this.mProfile;
            mediaRecorder.setVideoSize(camcorderProfile2.videoFrameWidth, camcorderProfile2.videoFrameHeight);
        }
        int hSRValue = getHSRValue();
        if (hSRValue > 0) {
            mediaRecorder.setVideoFrameRate(hSRValue);
            this.mFrameRateTrack = hSRValue;
            String str = VideoBase.TAG;
            Log.d(str, "setVideoFrameRate: " + hSRValue);
        } else {
            mediaRecorder.setVideoFrameRate(this.mProfile.videoFrameRate);
            this.mFrameRateTrack = this.mProfile.videoFrameRate;
            String str2 = VideoBase.TAG;
            Log.d(str2, "setVideoFrameRate: " + this.mProfile.videoFrameRate);
        }
        CamcorderProfile camcorderProfile3 = this.mProfile;
        if (5 == camcorderProfile3.videoCodec) {
            i = getHevcVideoEncoderBitRate(camcorderProfile3);
            String str3 = VideoBase.TAG;
            Log.d(str3, "H265 bitrate: " + i);
            if (CameraSettings.isHdr10Alive(this.mModuleIndex)) {
                int i2 = !is8KCamcorder() ? 262144 : 1048576;
                this.mMediaRecorder.setVideoEncodingProfileLevel(4096, i2);
                String str4 = VideoBase.TAG;
                Log.d(str4, "setupRecorder: HEVCProfileMain10HDR10 & " + i2);
            }
        } else {
            i = camcorderProfile3.videoBitRate;
            String str5 = VideoBase.TAG;
            Log.d(str5, "H264 bitrate: " + i);
        }
        mediaRecorder.setVideoEncodingBitRate(i);
        if (z) {
            mediaRecorder.setAudioEncodingBitRate(this.mProfile.audioBitRate);
            mediaRecorder.setAudioChannels(this.mProfile.audioChannels);
            mediaRecorder.setAudioSamplingRate(this.mProfile.audioSampleRate);
            mediaRecorder.setAudioEncoder(this.mProfile.audioCodec);
        }
        if (this.mCaptureTimeLapse) {
            if (DataRepository.dataItemFeature().uj()) {
                this.mTimeBetweenTimeLapseFrameCaptureMs = Integer.parseInt(DataRepository.dataItemRunning().getString(CameraSettings.KEY_NEW_VIDEO_TIME_LAPSE_FRAME_INTERVAL, DataRepository.dataItemRunning().getComponentRunningFastMotionSpeed().getDefaultValue(160)));
                this.mTimeLapseDuration = (long) (Integer.parseInt(DataRepository.dataItemRunning().getString(CameraSettings.KEY_NEW_VIDEO_TIME_LAPSE_DURATION, DataRepository.dataItemRunning().getComponentRunningFastMotionDuration().getDefaultValue(160))) * 60 * 1000);
                String str6 = VideoBase.TAG;
                Log.i(str6, "setupRecorder " + this.mTimeBetweenTimeLapseFrameCaptureMs + " mTimeLapseDuration " + this.mTimeLapseDuration);
            }
            mediaRecorder.setCaptureRate(1000.0d / ((double) this.mTimeBetweenTimeLapseFrameCaptureMs));
        } else if (!isNormalMode) {
            if (ModuleManager.isVideoNewSlowMotion() && !DataRepository.dataItemFeature().hi()) {
                mediaRecorder.setVideoFrameRate(this.mFrameRate);
                mediaRecorder.setVideoEncodingBitRate(Build.VERSION.SDK_INT < 28 ? (int) ((((long) i) * ((long) this.mFrameRate)) / ((long) getNormalVideoFrameRate())) : ((this.mFrameRate / getNormalVideoFrameRate()) / 2) * i);
            }
            mediaRecorder.setCaptureRate((double) this.mFrameRate);
        } else if (hSRValue > 0) {
            mediaRecorder.setVideoFrameRate(hSRValue);
            mediaRecorder.setCaptureRate((double) hSRValue);
        }
        mediaRecorder.setMaxDuration(this.mMaxVideoDurationInMs);
        Location location = null;
        if (this.mActivity.getCameraIntentManager().checkIntentLocationPermission(this.mActivity)) {
            location = LocationManager.instance().getCurrentLocation();
        }
        if (location != null) {
            mediaRecorder.setLocation((float) location.getLatitude(), (float) location.getLongitude());
        }
        int i3 = SystemProperties.getInt("camera.debug.video_max_size", 0);
        long recorderMaxFileSize = getRecorderMaxFileSize(i3);
        if (recorderMaxFileSize > 0) {
            String str7 = VideoBase.TAG;
            Log.v(str7, "maxFileSize=" + recorderMaxFileSize);
            mediaRecorder.setMaxFileSize(recorderMaxFileSize);
            if (recorderMaxFileSize > VIDEO_MAX_SINGLE_FILE_SIZE) {
                setParameterExtra(mediaRecorder, "param-use-64bit-offset=1");
            }
        }
        if (!DataRepository.dataItemFeature().Ti() || (i3 <= 0 && recorderMaxFileSize != VIDEO_MAX_SINGLE_FILE_SIZE)) {
            setSplitWhenReachMaxSize(false);
        } else {
            setSplitWhenReachMaxSize(true);
        }
        try {
            if ((isFPS240() || isFPS960()) && !DataRepository.dataItemFeature().hi()) {
                setParameterExtra(mediaRecorder, "video-param-i-frames-interval=0.033");
            }
        } catch (Exception e2) {
            Log.e(VideoBase.TAG, e2.getMessage(), e2);
        }
        int recorderOrientationHint = getRecorderOrientationHint();
        if (getModuleIndex() == 204) {
            mediaRecorder.setOrientationHint((recorderOrientationHint + 270) % 360);
        } else {
            mediaRecorder.setOrientationHint(recorderOrientationHint);
        }
        AudioSystem.setParameters("video_rotation=" + this.mOrientation);
        this.mOrientationCompensationAtRecordStart = this.mOrientationCompensation;
    }

    private boolean shouldApplyUltraWideLDC() {
        if (CameraSettings.shouldUltraWideVideoLDCBeVisibleInMode(this.mModuleIndex) && this.mActualCameraId == Camera2DataContainer.getInstance().getUltraWideCameraId()) {
            return CameraSettings.isUltraWideVideoLDCEnabled();
        }
        return false;
    }

    private void startHighSpeedRecordSession() {
        Log.d(VideoBase.TAG, "startHighSpeedRecordSession");
        if (isDeviceAlive()) {
            checkDisplayOrientation();
            this.mCamera2Device.setErrorCallback(this.mErrorCallback);
            this.mCamera2Device.setPictureSize(this.mPreviewSize);
            if (this.mAELockOnlySupported) {
                this.mCamera2Device.setFocusCallback(this);
            }
            this.mSurfaceCreatedTimestamp = this.mActivity.getCameraScreenNail().getSurfaceCreatedTimestamp();
            this.mCamera2Device.startHighSpeedRecordSession(new Surface(this.mActivity.getCameraScreenNail().getSurfaceTexture()), this.mRecorderSurface, new Range<>(Integer.valueOf(this.mHfrFPSLower), Integer.valueOf(this.mHfrFPSUpper)), this);
            this.mFocusManager.resetFocused();
            Util.showSurfaceInfo(this.mRecorderSurface);
        }
    }

    private void startPreviewAfterRecord() {
        if (isDeviceAlive() && !this.mActivity.isActivityPaused()) {
            unlockAEAF();
            if (isCaptureIntent()) {
                return;
            }
            if (ModuleManager.isVideoNewSlowMotion()) {
                this.mCamera2Device.startHighSpeedRecordPreview();
            } else if (this.mProtolRender.isPresent()) {
                this.mCamera2Device.startRecordPreview();
            } else {
                this.mCamera2Device.startRecordPreview();
            }
        }
    }

    private void startRecordSession() {
        String str = VideoBase.TAG;
        Log.d(str, "startRecordSession: mode=" + this.mSpeed);
        if (isDeviceAlive()) {
            checkDisplayOrientation();
            this.mCamera2Device.setErrorCallback(this.mErrorCallback);
            this.mCamera2Device.setPictureSize(this.mPreviewSize);
            this.mCamera2Device.setVideoSnapshotSize(this.mPictureSize);
            if (this.mAELockOnlySupported) {
                this.mCamera2Device.setFocusCallback(this);
            }
            int operatingMode = getOperatingMode();
            Log.d(VideoBase.TAG, String.format("startRecordSession: operatingMode = 0x%x", Integer.valueOf(operatingMode)));
            this.mSurfaceCreatedTimestamp = this.mActivity.getCameraScreenNail().getSurfaceCreatedTimestamp();
            updateFpsRange();
            this.mCamera2Device.startRecordSession(new Surface(this.mActivity.getCameraScreenNail().getSurfaceTexture()), this.mRecorderSurface, true, operatingMode, this);
            this.mFocusManager.resetFocused();
            this.mPreviewing = true;
        }
    }

    private boolean startRecorder() {
        if (!initializeRecorder(true)) {
            return false;
        }
        if (DataRepository.dataItemFeature().Wj() && CameraSettings.is4KHigherVideoQuality(this.mQuality)) {
            int hSRValue = getHSRValue();
            if (hSRValue <= 0) {
                hSRValue = this.mProfile.videoFrameRate;
            }
            ThermalHelper.notifyThermalRecordStart(this.mQuality, hSRValue);
        }
        try {
            this.mMediaRecorder.start();
            this.mMediaRecorderWorking = true;
            return true;
        } catch (IllegalStateException e2) {
            Log.e(VideoBase.TAG, "could not start recorder", e2);
            showConfirmMessage(R.string.confirm_recording_fail_title, R.string.confirm_recording_fail_recorder_busy_alert);
            return false;
        }
    }

    private void startVideoRecordingIfNeeded() {
        if (!this.mActivity.getCameraIntentManager().checkCallerLegality() || this.mActivity.isActivityPaused()) {
            return;
        }
        if (!this.mActivity.getCameraIntentManager().isOpenOnly(this.mActivity)) {
            this.mActivity.getIntent().removeExtra(CameraIntentManager.CameraExtras.CAMERA_OPEN_ONLY);
            if (this.mActivity.getCameraIntentManager().getTimerDurationSeconds() > 0) {
                Log.e(VideoBase.TAG, "Video mode doesn't support Time duration!");
            } else if (!this.mActivity.isIntentPhotoDone()) {
                this.mHandler.postDelayed(new Runnable() {
                    /* class com.android.camera.module.VideoModule.AnonymousClass6 */

                    public void run() {
                        VideoModule videoModule = VideoModule.this;
                        videoModule.onShutterButtonClick(videoModule.getTriggerMode());
                    }
                }, 1500);
                this.mActivity.setIntnetPhotoDone(true);
            }
        } else {
            this.mActivity.getIntent().removeExtra(CameraIntentManager.CameraExtras.TIMER_DURATION_SECONDS);
        }
    }

    private void trackProVideoInfo() {
        CameraStatUtils.trackRecordVideoInProMode(getManualValue(CameraSettings.KEY_QC_PRO_VIDEO_WHITEBALANCE_VALUE, getString(R.string.pref_camera_whitebalance_default)), getManualValue(CameraSettings.KEY_QC_PRO_VIDEO_EXPOSURETIME, getString(R.string.pref_camera_exposuretime_default)), getManualValue(CameraSettings.KEY_QC_PRO_VIDEO_ISO, getString(R.string.pref_camera_iso_default)), getManualValue(CameraSettings.KEY_QC_PRO_VIDEO_EXPOSURE_VALUE, getString(R.string.pref_camera_iso_default)), this.mModuleIndex, getActualCameraId());
    }

    private void updateAutoZoomMode() {
        boolean isAutoZoomEnabled = CameraSettings.isAutoZoomEnabled(this.mModuleIndex);
        if (this.mCamera2Device != null && isAlive()) {
            this.mCamera2Device.setAutoZoomMode(isAutoZoomEnabled ? 1 : 0);
            if (isAutoZoomEnabled) {
                this.mCamera2Device.setAutoZoomScaleOffset(0.0f);
            }
        }
    }

    private void updateBokeh() {
        ComponentConfigBokeh componentBokeh = DataRepository.dataItemConfig().getComponentBokeh();
        if (!ModuleManager.isPortraitModule() && !"on".equals(componentBokeh.getComponentValue(this.mModuleIndex))) {
            this.mCamera2Device.setSingleBokeh(false);
            this.mCamera2Device.setDualBokehEnable(false);
        } else if (isSingleCamera()) {
            this.mCamera2Device.setSingleBokeh(true);
            this.mCamera2Device.setDualBokehEnable(false);
        } else {
            this.mCamera2Device.setSingleBokeh(false);
            this.mCamera2Device.setDualBokehEnable(true);
        }
    }

    private void updateCinematicVideo() {
        this.mCamera2Device.setCinematicVideoEnabled(CameraSettings.isCinematicAspectRatioEnabled(this.mModuleIndex));
    }

    private void updateEvValue() {
        String manualValue = getManualValue(CameraSettings.KEY_QC_PRO_VIDEO_EXPOSURE_VALUE, "0");
        this.mEvValue = (int) (Float.parseFloat(manualValue) / this.mCameraCapabilities.getExposureCompensationStep());
        this.mEvState = 3;
        setEvValue();
    }

    private void updateExposureTime() {
        this.mCamera2Device.setExposureTime(Long.parseLong(getManualValue(CameraSettings.KEY_QC_PRO_VIDEO_EXPOSURETIME, getString(R.string.pref_camera_exposuretime_default))));
    }

    private void updateFilter() {
        EffectController.getInstance().setEffect(CameraSettings.getShaderEffect());
    }

    private void updateFrontMirror() {
        this.mCamera2Device.setFrontMirror(isFrontCamera() && !c.Xm() && DataRepository.dataItemFeature().Gk() && CameraSettings.isFrontMirror());
    }

    private void updateHFRDeflicker() {
        if (DataRepository.dataItemFeature().mj() && isFPS960()) {
            this.mCamera2Device.setHFRDeflickerEnable(true);
        }
    }

    private void updateHdr10VideoMode() {
        if (this.mCameraCapabilities.isVideoHDR10Supported()) {
            this.mCamera2Device.setHdr10VideoMode(CameraSettings.isHdr10Alive(this.mModuleIndex));
        }
    }

    private void updateHfrFPSRange(Size size, int i) {
        Range<Integer>[] supportedHighSpeedVideoFPSRange = this.mCameraCapabilities.getSupportedHighSpeedVideoFPSRange(size);
        Range<Integer> range = null;
        for (Range<Integer> range2 : supportedHighSpeedVideoFPSRange) {
            if (range2.getUpper().intValue() == i && (range == null || range.getLower().intValue() < range2.getLower().intValue())) {
                range = range2;
            }
        }
        this.mHfrFPSLower = range.getLower().intValue();
        this.mHfrFPSUpper = range.getUpper().intValue();
    }

    private void updateHistogramStats() {
        this.mCamera2Device.setHistogramStatsEnabled(CameraSettings.isProVideoHistogramOpen(this.mModuleIndex));
    }

    private void updateISO() {
        String string = getString(R.string.pref_camera_iso_default);
        String manualValue = getManualValue(CameraSettings.KEY_QC_PRO_VIDEO_ISO, string);
        if (manualValue == null || manualValue.equals(string)) {
            this.mCamera2Device.setISO(0);
        } else {
            this.mCamera2Device.setISO(Math.min(Util.parseInt(manualValue, 0), this.mCameraCapabilities.getMaxIso()));
        }
    }

    private void updateMacroMode() {
        this.mCamera2Device.setMacroMode(CameraSettings.isMacroModeEnabled(this.mModuleIndex));
    }

    private void updateMutexModePreference() {
        if ("on".equals(DataRepository.dataItemConfig().getComponentHdr().getComponentValue(this.mModuleIndex))) {
            this.mMutexModePicker.setMutexMode(2);
        }
    }

    private void updateTargetZoom() {
        float readTargetZoom = CameraSettings.readTargetZoom();
        Camera2Proxy camera2Proxy = this.mCamera2Device;
        if (camera2Proxy != null) {
            camera2Proxy.setTargetZoom(readTargetZoom);
        }
    }

    private void updateUltraWideLDC() {
        this.mCamera2Device.setUltraWideLDC(shouldApplyUltraWideLDC());
    }

    private void updateVideoBokeh(boolean z) {
        float videoBokehRatio = CameraSettings.getVideoBokehRatio();
        if (isFrontCamera()) {
            String str = VideoBase.TAG;
            Log.i(str, "frontVideoBokeh: " + videoBokehRatio);
            this.mCamera2Device.setVideoBokehLevelFront(videoBokehRatio);
        } else {
            int i = (int) videoBokehRatio;
            String str2 = VideoBase.TAG;
            Log.i(str2, "backVideoBokeh: " + i);
            this.mCamera2Device.setVideoBokehLevelBack(i);
        }
        if (z) {
            this.mCamera2Device.setVideoFilterColorRetentionMode(CameraSettings.getVideoBokehColorRetentionMode(), isFrontCamera());
        }
    }

    private void updateVideoColorRetention() {
        int shaderEffect = CameraSettings.getShaderEffect();
        boolean isFrontCamera = isFrontCamera();
        if (shaderEffect == 200) {
            if (isFrontCamera) {
                this.mCamera2Device.setVideoFilterColorRetentionFront(true);
            } else {
                this.mCamera2Device.setVideoFilterColorRetentionBack(true);
            }
        } else if (isFrontCamera) {
            this.mCamera2Device.setVideoFilterColorRetentionFront(false);
        } else {
            this.mCamera2Device.setVideoFilterColorRetentionBack(false);
        }
    }

    private void updateVideoFilter() {
        int shaderEffect = CameraSettings.getShaderEffect();
        if (shaderEffect == 200) {
            shaderEffect = FilterInfo.FILTER_ID_NONE;
        }
        if (shaderEffect == FilterInfo.FILTER_ID_NONE) {
            shaderEffect = 0;
        }
        this.mCamera2Device.setVideoFilterId(shaderEffect);
    }

    private void updateVideoLog() {
        this.mCamera2Device.setVideoLogEnabled(CameraSettings.isProVideoLogOpen(this.mModuleIndex));
    }

    private void updateVideoSubtitle() {
        this.mIsSubtitleSupported = DataRepository.dataItemRunning().getComponentRunningSubtitle().isSwitchOn(this.mModuleIndex);
    }

    private void updateVideoTag() {
        SettingUiState videoTagSettingNeedRemove = CameraSettings.getVideoTagSettingNeedRemove(this.mModuleIndex, isFrontCamera());
        this.mIsVideoTagSupported = !videoTagSettingNeedRemove.isRomove && !videoTagSettingNeedRemove.isMutexEnable && CameraSettings.isVideoTagOn();
    }

    private void updateVideoTagState(int i) {
        if (this.mIsVideoTagSupported) {
            VideoTagView videoTagView = null;
            ModeProtocol.TopAlert topAlert = this.mTopAlert;
            if (topAlert != null) {
                videoTagView = topAlert.getVideoTag();
            }
            if (videoTagView != null) {
                if (i == 0) {
                    videoTagView.prepare();
                } else if (i == 1) {
                    videoTagView.start();
                } else if (i == 2) {
                    videoTagView.pause();
                } else if (i == 3) {
                    videoTagView.resume();
                } else if (i == 4) {
                    videoTagView.stop();
                }
            }
        }
    }

    private void updateWhiteBalance() {
        setAWBMode(getManualValue(CameraSettings.KEY_PRO_VIDEO_WHITE_BALANCE, "1"));
    }

    public /* synthetic */ void I(boolean z) {
        this.mMainProtocol.setEvAdjustable(z);
    }

    public /* synthetic */ void Id() {
        RotateTextToast.getInstance(this.mActivity).show(R.string.time_lapse_error, this.mOrientation);
    }

    public /* synthetic */ void Jd() {
        this.mFocusManager.cancelFocus();
    }

    /* access modifiers changed from: protected */
    @Override // com.android.camera.module.BaseModule
    public void applyZoomRatio() {
        super.applyZoomRatio();
        setCurrentAiAudioZoomLv();
    }

    public /* synthetic */ void c(Boolean bool) throws Exception {
        onMediaRecorderReleased();
    }

    @Override // com.android.camera.module.BaseModule
    public void consumePreference(@UpdateConstant.UpdateType int... iArr) {
        for (int i : iArr) {
            switch (i) {
                case 1:
                    updatePictureAndPreviewSize();
                    break;
                case 2:
                    updateFilter();
                    break;
                case 3:
                    updateFocusArea();
                    break;
                case 4:
                case 7:
                case 8:
                case 17:
                case 18:
                case 21:
                case 22:
                case 23:
                case 26:
                case 27:
                case 28:
                case 32:
                case 36:
                case 38:
                case 39:
                case 41:
                case 44:
                case 49:
                case 54:
                case 56:
                case 57:
                case 59:
                case 61:
                case 62:
                case 64:
                case 70:
                case 71:
                case 72:
                case 73:
                case 74:
                case 76:
                case 77:
                case 78:
                default:
                    if (!BaseModule.DEBUG) {
                        Log.w(VideoBase.TAG, "no consumer for this updateType: " + i);
                        break;
                    } else {
                        throw new RuntimeException("no consumer for this updateType: " + i);
                    }
                case 5:
                    updateFace();
                    break;
                case 6:
                    updateWhiteBalance();
                    break;
                case 9:
                    updateAntiBanding(DataRepository.dataItemFeature().hi() ? "0" : CameraSettings.getAntiBanding());
                    break;
                case 10:
                    updateFlashPreference();
                    break;
                case 11:
                    updateHDRPreference();
                    break;
                case 12:
                    setEvValue();
                    break;
                case 13:
                    updateBeauty();
                    break;
                case 14:
                    updateVideoFocusMode();
                    break;
                case 15:
                    updateISO();
                    break;
                case 16:
                    updateExposureTime();
                    break;
                case 19:
                    updateFpsRange();
                    break;
                case 20:
                case 30:
                case 34:
                case 42:
                case 43:
                case 45:
                case 46:
                case 48:
                case 50:
                    break;
                case 24:
                    applyZoomRatio();
                    break;
                case 25:
                    focusCenter();
                    break;
                case 29:
                    updateExposureMeteringMode();
                    break;
                case 31:
                    updateVideoStabilization();
                    break;
                case 33:
                    Camera2Proxy camera2Proxy = this.mCamera2Device;
                    if (camera2Proxy != null) {
                        camera2Proxy.setVideoSnapshotSize(this.mPictureSize);
                        break;
                    } else {
                        break;
                    }
                case 35:
                    updateDeviceOrientation();
                    break;
                case 37:
                    updateBokeh();
                    break;
                case 40:
                    updateFrontMirror();
                    break;
                case 47:
                    updateUltraWideLDC();
                    break;
                case 51:
                    updateAutoZoomMode();
                    break;
                case 52:
                    updateMacroMode();
                    break;
                case 53:
                    updateHFRDeflicker();
                    break;
                case 55:
                    updateModuleRelated();
                    break;
                case 58:
                    updateBackSoftLightPreference();
                    break;
                case 60:
                    updateCinematicVideo();
                    break;
                case 63:
                    updateEvValue();
                    break;
                case 65:
                    updateVideoLog();
                    break;
                case 66:
                    updateThermalLevel();
                    break;
                case 67:
                    updateVideoBokeh(this.mCameraCapabilities.isSupportVideoBokehColorRetentionTag(isFrontCamera()));
                    break;
                case 68:
                    updateVideoFilter();
                    break;
                case 69:
                    updateVideoColorRetention();
                    break;
                case 75:
                    updateHistogramStats();
                    break;
                case 79:
                    updateTargetZoom();
                    break;
                case 80:
                    updateHdr10VideoMode();
                    break;
                case 81:
                    updateVideoBokeh(true);
                    break;
            }
        }
    }

    /* access modifiers changed from: protected */
    @Override // com.android.camera.module.VideoBase
    public void doLaterReleaseIfNeed() {
        super.doLaterReleaseIfNeed();
        if (isFPS960() && !this.mActivity.isActivityPaused()) {
            if (isTextureExpired()) {
                Log.d(VideoBase.TAG, "doLaterReleaseIfNeed: restartModule...");
                restartModule();
                return;
            }
            Log.d(VideoBase.TAG, "doLaterReleaseIfNeed: dismissBlurCover...");
            this.mActivity.dismissBlurCover();
        }
    }

    /* access modifiers changed from: protected */
    @Override // com.android.camera.module.VideoBase
    public boolean enableFaceDetection() {
        if (!DataRepository.dataItemFeature().Jk() || !isBackCamera()) {
            return (!ModuleManager.isVideoNewSlowMotion() || !isBackCamera()) && DataRepository.dataItemGlobal().getBoolean(CameraSettings.KEY_FACE_DETECTION, getResources().getBoolean(R.bool.pref_camera_facedetection_default));
        }
        return false;
    }

    /* access modifiers changed from: protected */
    public CamcorderProfile fetchProfile(int i, int i2) {
        return CamcorderProfile.get(i, i2);
    }

    /* access modifiers changed from: protected */
    public int getNormalVideoFrameRate() {
        CamcorderProfile camcorderProfile;
        if (DataRepository.dataItemFeature().hi() || (camcorderProfile = this.mProfile) == null) {
            return 30;
        }
        return camcorderProfile.videoFrameRate;
    }

    /* access modifiers changed from: protected */
    @Override // com.android.camera.module.BaseModule
    public int getOperatingMode() {
        boolean isFrontCamera = isFrontCamera();
        int i = CameraCapabilities.SESSION_OPERATION_MODE_VIDEO_BEAUTY_WITH_PREVIEW_EIS;
        int i2 = 32772;
        if (!isFrontCamera) {
            boolean isEisOn = isEisOn();
            if (!needChooseVideoBeauty(this.mBeautyValues)) {
                i = this.mQuality == 0 ? 0 : (CameraSettings.isSuperEISEnabled(this.mModuleIndex) || !CameraSettings.getSuperEISProValue(this.mModuleIndex).equals("off")) ? CameraSettings.getSuperEISProValue(this.mModuleIndex).equals(ComponentRunningEisPro.EIS_VALUE_PRO) ? CameraCapabilities.SESSION_OPERATION_MODE_VIDEO_SUPEREISPRO : CameraCapabilities.SESSION_OPERATION_MODE_VIDEO_SUPEREIS : isEisOn ? 32772 : 61456;
            } else if (!isEisOn) {
                i = 32777;
            }
            boolean z = true;
            if (getHSRValue() == 60) {
                CameraCapabilities cameraCapabilities = this.mCameraCapabilities;
                boolean z2 = cameraCapabilities != null && cameraCapabilities.isCurrentQualitySupportEis(this.mQuality, 60);
                if ((!is4K60FpsEISSupported() && !z2) || !isEisOn) {
                    i2 = CameraCapabilities.SESSION_OPERATION_MODE_HSR_60;
                }
            } else {
                i2 = i;
            }
            if (CameraSettings.isAutoZoomEnabled(this.mModuleIndex)) {
                i2 = CameraCapabilities.SESSION_OPERATION_MODE_AUTO_ZOOM;
            }
            if (CameraSettings.isHdr10Alive(this.mModuleIndex) && !isEisOn) {
                i2 = CameraCapabilities.SESSION_OPERATION_MODE_HDR10;
            }
            i = (!is8KCamcorder() || !isEisOn) ? i2 : 32797;
            if (this.mCameraCapabilities.isFovcSupported()) {
                if (i == 0) {
                    z = false;
                }
                this.mFovcEnabled = z;
            }
        } else if (CameraSettings.isVideoBokehOn()) {
            i = 32770;
        } else if (!isEisOn()) {
            i = this.mCameraCapabilities.isSupportVideoBeauty() ? 32777 : 0;
        } else if (!needChooseVideoBeauty(this.mBeautyValues)) {
            i = 32772;
        }
        Log.d(VideoBase.TAG, "getOperatingMode: " + Integer.toHexString(i));
        return i;
    }

    @Override // com.android.camera.module.BaseModule, com.android.camera.module.Module
    public List<String> getSupportedSettingKeys() {
        ArrayList arrayList = new ArrayList();
        if (isBackCamera()) {
            arrayList.add("pref_video_speed_fast_key");
        }
        return arrayList;
    }

    /* access modifiers changed from: protected */
    public void handleTempVideoFile() {
        if (isCaptureIntent()) {
            String str = this.mTemporaryVideoPath;
            if (str == null) {
                this.mTemporaryVideoPath = getActivity().getCacheDir().getPath() + "/temp_video.mp4";
                String str2 = VideoBase.TAG;
                android.util.Log.d(str2, "VideoModule: wq " + this.mTemporaryVideoPath);
                return;
            }
            File file = new File(str);
            if (file.exists()) {
                file.delete();
            }
        }
    }

    /* access modifiers changed from: protected */
    @Override // com.android.camera.module.VideoBase
    public boolean isAEAFLockSupported() {
        return !this.mMediaRecorderRecording || !isFPS960();
    }

    /* access modifiers changed from: protected */
    @Override // com.android.camera.module.BaseModule
    public boolean isCameraSwitchingDuringZoomingAllowed() {
        int i;
        return HybridZoomingSystem.IS_3_OR_MORE_SAT && ((i = this.mModuleIndex) == 162 || i == 169) && !CameraSettings.isMacroModeEnabled(this.mModuleIndex) && !CameraSettings.isSuperEISEnabled(this.mModuleIndex) && isBackCamera() && !this.mMediaRecorderRecording && !this.mMediaRecorderRecordingPaused && (DataRepository.dataItemGlobal().isNormalIntent() || !this.mCameraCapabilities.isSupportLightTripartite());
    }

    public boolean isNeedAlertAudioZoomIndicator() {
        int i = this.mModuleIndex;
        if ((i == 162 || i == 180) && !isFrontCamera()) {
            return (this.mMediaRecorderRecording || this.mMediaRecorderRecordingPaused) && DataRepository.dataItemRunning().getComponentRunningAiAudio().getCurrentRecType(this.mModuleIndex) == 2;
        }
        return false;
    }

    @Override // com.android.camera.module.BaseModule, com.android.camera.ui.FocusView.ExposureViewListener
    public boolean isNeedHapticFeedback() {
        return !this.mMediaRecorderRecording || this.mMediaRecorderRecordingPaused;
    }

    @Override // com.android.camera.module.BaseModule
    public boolean isNeedMute() {
        return this.mObjectTrackingStarted || (this.mMediaRecorderRecording && !this.mMediaRecorderRecordingPaused);
    }

    @Override // com.android.camera.module.BaseModule, com.android.camera.module.Module
    public boolean isPostProcessing() {
        return this.mMediaRecorderPostProcessing;
    }

    /* access modifiers changed from: protected */
    public boolean isShowHFRDuration() {
        return true;
    }

    @Override // com.android.camera.module.Module, com.android.camera.module.VideoBase
    public boolean isUnInterruptable() {
        if (!super.isUnInterruptable() && !isNormalMode() && this.mMediaRecorder != null && this.mMediaRecorderWorking) {
            this.mUnInterruptableReason = "recorder release";
        }
        return this.mUnInterruptableReason != null;
    }

    @Override // com.android.camera.module.BaseModule, com.android.camera.module.VideoBase
    public boolean isZoomEnabled() {
        if ((!isFPS960() || !this.mMediaRecorderRecording) && !CameraSettings.isAutoZoomEnabled(this.mModuleIndex) && !CameraSettings.isSuperEISEnabled(this.mModuleIndex)) {
            return super.isZoomEnabled();
        }
        return false;
    }

    @Override // com.android.camera.module.BaseModule, com.android.camera.module.Module, com.android.camera.module.VideoBase
    public boolean onBackPressed() {
        if (!isFrameAvailable()) {
            return false;
        }
        if (this.mPaused || this.mActivity.isActivityPaused()) {
            return true;
        }
        if (this.mStereoSwitchThread != null) {
            return false;
        }
        if (isFPS960() && this.mMediaRecorderPostProcessing) {
            return true;
        }
        if (this.mMediaRecorderRecording) {
            long currentTimeMillis = System.currentTimeMillis();
            if (currentTimeMillis - this.mLastBackPressedTime > 3000) {
                this.mLastBackPressedTime = currentTimeMillis;
                ToastUtils.showToast((Context) this.mActivity, (int) R.string.record_back_pressed_hint, true);
            } else {
                stopVideoRecording(false);
            }
            return true;
        } else if (!this.isAutoZoomTracking.get()) {
            return super.onBackPressed();
        } else {
            stopTracking(0);
            return true;
        }
    }

    /* access modifiers changed from: protected */
    @Override // com.android.camera.module.BaseModule, com.android.camera.module.VideoBase
    public void onCameraOpened() {
        super.onCameraOpened();
        updateBeauty();
        updateVideoSubtitle();
        updateVideoTag();
        readVideoPreferences();
        initializeFocusManager();
        updatePreferenceTrampoline(UpdateConstant.VIDEO_TYPES_INIT);
        if (!initializeRecorder(false)) {
            startVideoPreviewSession();
        } else if (getModuleIndex() == 204) {
            startVideoPreviewSession();
        } else if (ModuleManager.isVideoNewSlowMotion()) {
            startHighSpeedRecordSession();
        } else {
            startRecordSession();
        }
        if (CameraSettings.is8KHigherVideoQuality(this.mQuality)) {
            this.mActivity.boostCameraByThreshold();
        }
        initAutoZoom();
        initHistogramEmitter();
    }

    /* access modifiers changed from: protected */
    @Override // com.android.camera.module.BaseModule
    public void onCapabilityChanged(CameraCapabilities cameraCapabilities) {
        super.onCapabilityChanged(cameraCapabilities);
        this.mUltraWideAELocked = false;
        FocusManager2 focusManager2 = this.mFocusManager;
        if (focusManager2 != null) {
            focusManager2.setCharacteristics(cameraCapabilities);
        }
        Camera2Proxy camera2Proxy = this.mCamera2Device;
        if (camera2Proxy != null) {
            camera2Proxy.onCapabilityChanged(cameraCapabilities);
        }
    }

    @Override // com.android.camera.module.BaseModule, com.android.camera.module.Module
    public void onCreate(int i, int i2) {
        super.onCreate(i, i2);
        if (DataRepository.dataItemFeature().cj()) {
            this.mAudioManager = (AudioManager) this.mActivity.getSystemService(VEEditor.MVConsts.TYPE_AUDIO);
            this.mAudioManagerAudioDeviceCallback = new AudioManagerAudioDeviceCallback();
            this.mAudioManager.registerAudioDeviceCallback(this.mAudioManagerAudioDeviceCallback, this.mHandler);
        }
        setCaptureIntent(this.mActivity.getCameraIntentManager().isVideoCaptureIntent());
        EffectController.getInstance().setEffect(FilterInfo.FILTER_ID_NONE);
        this.mActivity.getSensorStateManager().setSensorStateListener(this.mSensorStateListener);
        this.mQuickCapture = this.mActivity.getCameraIntentManager().isQuickCapture().booleanValue();
        enableCameraControls(false);
        this.mTelephonyManager = (TelephonyManager) this.mActivity.getSystemService("phone");
        this.mVideoFocusMode = AutoFocus.LEGACY_CONTINUOUS_VIDEO;
        this.mAutoZoomViewProtocol = (ModeProtocol.AutoZoomViewProtocol) ModeCoordinatorImpl.getInstance().getAttachProtocol(214);
        this.mSubtitleRecording = (ModeProtocol.SubtitleRecording) ModeCoordinatorImpl.getInstance().getAttachProtocol(231);
        initRecorderSurface();
        onCameraOpened();
    }

    @Override // com.android.camera.module.BaseModule, com.android.camera.module.Module
    public void onDestroy() {
        super.onDestroy();
        releaseRecorderSurface();
        this.mHandler.sendEmptyMessage(45);
        if (DataRepository.dataItemFeature().cj()) {
            this.mAudioManager.unregisterAudioDeviceCallback(this.mAudioManagerAudioDeviceCallback);
        }
    }

    public void onError(MediaRecorder mediaRecorder, int i, int i2) {
        String str = VideoBase.TAG;
        Log.e(str, "MediaRecorder error. what=" + i + " extra=" + i2);
        if (i == 1 || i == 100) {
            if (this.mMediaRecorderRecording) {
                stopVideoRecording(false);
            }
            this.mActivity.getScreenHint().updateHint();
        }
    }

    @Override // com.android.camera.module.BaseModule, com.android.camera.module.Module
    public boolean onGestureTrack(RectF rectF, boolean z) {
        if (this.mInStartingFocusRecording || !isBackCamera() || !c.Qn() || CameraSettings.is4KHigherVideoQuality(this.mQuality) || isCaptureIntent()) {
            return false;
        }
        return initializeObjectTrack(rectF, z);
    }

    @Override // com.android.camera.module.BaseModule
    public void onGradienterSwitched(boolean z) {
        this.isGradienterOn = z;
        this.mActivity.getSensorStateManager().setGradienterEnabled(z);
        updatePreferenceTrampoline(2, 5);
    }

    public void onInfo(MediaRecorder mediaRecorder, int i, int i2) {
        if (!this.mMediaRecorderRecording) {
            Log.w(VideoBase.TAG, "onInfo: ignore event " + i);
            return;
        }
        switch (i) {
            case 800:
                stopVideoRecording(false);
                return;
            case 801:
                Log.w(VideoBase.TAG, "reached max size. fileNumber=" + this.mCurrentFileNumber);
                stopVideoRecording(false);
                if (!this.mActivity.getScreenHint().isScreenHintVisible()) {
                    Toast.makeText(this.mActivity, (int) R.string.video_reach_size_limit, 1).show();
                    return;
                }
                return;
            case 802:
                boolean isSplitWhenReachMaxSize = isSplitWhenReachMaxSize();
                Log.d(VideoBase.TAG, "max file size is approaching. split: " + isSplitWhenReachMaxSize);
                if (isSplitWhenReachMaxSize) {
                    this.mCurrentFileNumber++;
                    ContentValues genContentValues = genContentValues(this.mOutputFormat, this.mCurrentFileNumber, this.mSlowModeFps, is8KCamcorder(), true);
                    String asString = genContentValues.getAsString("_data");
                    Log.d(VideoBase.TAG, "nextVideoPath: " + asString);
                    if (setNextOutputFile(asString)) {
                        this.mNextVideoValues = genContentValues;
                        this.mNextVideoFileName = asString;
                        return;
                    }
                    return;
                }
                return;
            case 803:
                Log.d(VideoBase.TAG, "next output file started");
                onMaxFileSizeReached();
                this.mCurrentVideoValues = this.mNextVideoValues;
                this.mCurrentVideoFilename = this.mNextVideoFileName;
                this.mNextVideoValues = null;
                this.mNextVideoFileName = null;
                return;
            default:
                Log.w(VideoBase.TAG, "onInfo what : " + i);
                return;
        }
    }

    /* access modifiers changed from: protected */
    @Override // com.android.camera.module.BaseModule
    public boolean onInterceptZoomingEvent(float f, float f2, int i) {
        int i2;
        if (!DataRepository.dataItemFeature().qm() || !DataRepository.dataItemConfig().getComponentConfigVideoQuality().supportVideoSATForVideoQuality(getModuleIndex()) || CameraSettings.isVideoQuality8KOpen(getModuleIndex()) || !HybridZoomingSystem.IS_3_OR_MORE_SAT || (((i2 = this.mModuleIndex) != 162 && i2 != 169) || CameraSettings.isMacroModeEnabled(this.mModuleIndex) || CameraSettings.isSuperEISEnabled(this.mModuleIndex) || !isBackCamera())) {
            return super.onInterceptZoomingEvent(f, f2, i);
        }
        if (!DataRepository.dataItemFeature().nm()) {
            return false;
        }
        if (this.m3ALocked) {
            unlockAEAF();
            FocusManager2 focusManager2 = this.mFocusManager;
            if (focusManager2 != null) {
                focusManager2.cancelFocus();
            }
            ModeProtocol.BottomPopupTips bottomPopupTips = (ModeProtocol.BottomPopupTips) ModeCoordinatorImpl.getInstance().getAttachProtocol(175);
            if (bottomPopupTips == null) {
                return false;
            }
            bottomPopupTips.directlyHideTips();
            return false;
        }
        Camera2Proxy camera2Proxy = this.mCamera2Device;
        if (camera2Proxy == null || 3 == camera2Proxy.getFocusMode()) {
            return false;
        }
        Log.d(VideoBase.TAG, "onInterceptZoomingEvent: should cancel focus.");
        FocusManager2 focusManager22 = this.mFocusManager;
        if (focusManager22 == null) {
            return false;
        }
        focusManager22.cancelFocus();
        return false;
    }

    @Override // com.android.camera.module.BaseModule, com.android.camera.module.Module
    public void onNewUriArrived(Uri uri, String str) {
        String str2;
        ModeProtocol.SubtitleRecording subtitleRecording;
        super.onNewUriArrived(uri, str);
        if (str.contains("VID")) {
            ArrayList<String> arrayList = new ArrayList<>();
            if (!this.mIsSubtitleSupported || (subtitleRecording = this.mSubtitleRecording) == null) {
                str2 = "";
            } else {
                str2 = subtitleRecording.getSubtitleContent();
                if (!TextUtils.isEmpty(str2)) {
                    arrayList.add("com.xiaomi.support_subtitle");
                } else {
                    Log.e(VideoBase.TAG, "video subtitle is empty ");
                }
            }
            if (this.mIsVideoTagSupported) {
                ModeProtocol.TopAlert topAlert = this.mTopAlert;
                if (topAlert == null || topAlert.getVideoTag() == null) {
                    Log.e(VideoBase.TAG, "video tag is empty ");
                } else {
                    str2 = this.mTopAlert.getVideoTag().getVideoTagContent();
                    if (!TextUtils.isEmpty(str2)) {
                        arrayList.add("com.xiaomi.support_tags");
                    } else {
                        Log.e(VideoBase.TAG, "video tag is empty ");
                    }
                }
            }
            if (CameraSettings.isProVideoLogOpen(this.mModuleIndex)) {
                arrayList.add("com.xiaomi.record_log");
            }
            if (CameraSettings.isCinematicAspectRatioEnabled(this.mModuleIndex)) {
                arrayList.add("com.xiaomi.record_mimovie");
            }
            if (CameraSettings.isAiAudioOn(this.mModuleIndex)) {
                arrayList.add("com.xiaomi.ai_audio");
            }
            if (CameraSettings.isVideoQuality8KOpen(this.mModuleIndex) && CameraSettings.isReal8K()) {
                arrayList.add("com.xiaomi.real_8k");
            }
            if (arrayList.size() != 0) {
                this.mActivity.getImageSaver().addVideoTag(this.mVideoTagFileName, str2, arrayList, this.mIsSubtitleSupported);
            }
        }
    }

    @Override // com.android.camera.ui.ObjectView.ObjectViewListener
    public void onObjectStable() {
    }

    @Override // com.android.camera.module.BaseModule, com.android.camera.module.Module, com.android.camera.module.VideoBase
    public void onPause() {
        if (this.mCamera2Device != null && (this.mFovcEnabled || (this.mCameraCapabilities.isEISPreviewSupported() && isEisOn()))) {
            this.mCamera2Device.notifyVideoStreamEnd();
        }
        super.onPause();
        waitStereoSwitchThread();
        stopObjectTracking(false);
        releaseResources();
        closeVideoFileDescriptor();
        this.mActivity.getSensorStateManager().reset();
        stopFaceDetection(true);
        resetScreenOn();
        this.mHandler.removeCallbacksAndMessages(null);
        FocusManager2 focusManager2 = this.mFocusManager;
        if (focusManager2 != null) {
            focusManager2.removeMessages();
        }
        if (!this.mActivity.isActivityPaused()) {
            PopupManager.getInstance(this.mActivity).notifyShowPopup(null, 1);
        }
    }

    public void onPauseButtonClick() {
        ModeProtocol.SubtitleRecording subtitleRecording;
        String str = VideoBase.TAG;
        Log.d(str, "onPauseButtonClick: isRecordingPaused=" + this.mMediaRecorderRecordingPaused + " isRecording=" + this.mMediaRecorderRecording);
        long currentTimeMillis = System.currentTimeMillis();
        if (this.mMediaRecorderRecording && currentTimeMillis - this.mPauseClickTime >= 500) {
            this.mPauseClickTime = currentTimeMillis;
            ModeProtocol.RecordState recordState = (ModeProtocol.RecordState) ModeCoordinatorImpl.getInstance().getAttachProtocol(212);
            if (this.mMediaRecorderRecordingPaused) {
                try {
                    this.mMediaRecorder.resume();
                    this.mRecordingStartTime = SystemClock.uptimeMillis() - this.mVideoRecordedDuration;
                    this.mVideoRecordedDuration = 0;
                    this.mMediaRecorderRecordingPaused = false;
                    this.mHandler.removeMessages(42);
                    this.mRecordingTime = "";
                    updateRecordingTime();
                    if (this.mIsSubtitleSupported && this.mSubtitleRecording != null) {
                        this.mSubtitleRecording.handleSubtitleRecordingResume();
                    }
                    updateVideoTagState(3);
                    recordState.onResume();
                } catch (IllegalStateException e2) {
                    Log.e(VideoBase.TAG, "failed to resume media recorder", e2);
                    releaseMediaRecorder();
                    recordState.onFailed();
                    updateVideoTagState(4);
                }
            } else {
                pauseVideoRecording();
                updateVideoTagState(2);
                CameraStatUtils.trackPauseVideoRecording(isFrontCamera());
                if (this.mIsSubtitleSupported && (subtitleRecording = this.mSubtitleRecording) != null) {
                    subtitleRecording.handleSubtitleRecordingPause();
                }
                recordState.onPause();
            }
            Log.d(VideoBase.TAG, "onPauseButtonClick");
        }
    }

    @Override // com.android.camera.module.BaseModule
    public void onPreviewLayoutChanged(Rect rect) {
        String str = VideoBase.TAG;
        Log.v(str, "onPreviewLayoutChanged: " + rect);
        this.mActivity.onLayoutChange(rect);
        if (this.mFocusManager != null && this.mActivity.getCameraScreenNail() != null) {
            this.mActivity.getCameraScreenNail().setDisplayArea(rect);
            this.mFocusManager.setRenderSize(this.mActivity.getCameraScreenNail().getRenderWidth(), this.mActivity.getCameraScreenNail().getRenderHeight());
            this.mFocusManager.setPreviewSize(rect.width(), rect.height());
        }
    }

    @Override // com.android.camera.module.BaseModule, com.android.camera2.Camera2Proxy.CameraMetaDataCallback, com.android.camera.module.VideoBase
    public void onPreviewMetaDataUpdate(CaptureResult captureResult) {
        super.onPreviewMetaDataUpdate(captureResult);
        if (this.isAutoZoomTracking.get()) {
            this.mAutoZoomEmitter.onNext(captureResult);
        }
        FlowableEmitter<CaptureResult> flowableEmitter = this.mHistogramEmitter;
        if (flowableEmitter != null) {
            flowableEmitter.onNext(captureResult);
        }
    }

    @Override // com.android.camera.module.BaseModule, com.android.camera2.Camera2Proxy.CameraPreviewCallback
    public void onPreviewRelease() {
        if (this.mMediaRecorderRecording) {
            stopRecorder();
        }
        super.onPreviewRelease();
    }

    @Override // com.android.camera2.Camera2Proxy.CameraPreviewCallback, com.android.camera.module.VideoBase
    public void onPreviewSessionFailed(CameraCaptureSession cameraCaptureSession) {
        super.onPreviewSessionFailed(cameraCaptureSession);
        enableCameraControls(true);
    }

    @Override // com.android.camera2.Camera2Proxy.CameraPreviewCallback, com.android.camera.module.VideoBase
    public void onPreviewSessionSuccess(CameraCaptureSession cameraCaptureSession) {
        if (this.mCamera2Device != null) {
            super.onPreviewSessionSuccess(cameraCaptureSession);
            if (!isCreated()) {
                Log.w(VideoBase.TAG, "onPreviewSessionSuccess: module is not ready");
                enableCameraControls(true);
                return;
            }
            Log.d(VideoBase.TAG, "onPreviewSessionSuccess: session=" + cameraCaptureSession);
            boolean z = false;
            this.mFaceDetectionEnabled = false;
            synchronized (this.mLock) {
                if (this.mCamera2Device != null) {
                    CameraConfigs cameraConfigs = this.mCamera2Device.getCameraConfigs();
                    if (this.mCameraCapabilities.isSupportVideoBokehAdjust() && isVideoBokehEnabled()) {
                        z = true;
                    }
                    cameraConfigs.setVideoBokehEnabled(z);
                }
            }
            updatePreferenceInWorkThread(UpdateConstant.VIDEO_TYPES_ON_PREVIEW_SUCCESS);
            enableCameraControls(true);
            if (this.mModuleIndex == 180) {
                updatePreferenceInWorkThread(UpdateConstant.CAMERA_TYPES_MANUALLY);
            }
        }
    }

    /* access modifiers changed from: protected */
    @Override // com.android.camera.module.VideoBase
    @MainThread
    public void onPreviewStart() {
        if (this.mPreviewing) {
            this.mMainProtocol.initializeFocusView(this);
            updateMutexModePreference();
            onShutterButtonFocus(true, 3);
            startVideoRecordingIfNeeded();
        }
    }

    @Override // com.android.camera.module.BaseModule
    public void onSharedPreferenceChanged() {
        if (!this.mPaused && !this.mActivity.isActivityPaused() && this.mCamera2Device != null) {
            CamcorderProfile camcorderProfile = this.mProfile;
            int i = camcorderProfile.videoFrameWidth;
            int i2 = camcorderProfile.videoFrameHeight;
            readVideoPreferences();
            CamcorderProfile camcorderProfile2 = this.mProfile;
            if (camcorderProfile2.videoFrameWidth != i || camcorderProfile2.videoFrameHeight != i2) {
                Log.d(VideoBase.TAG, String.format(Locale.ENGLISH, "profile size changed [%d %d]->[%d %d]", Integer.valueOf(i), Integer.valueOf(i2), Integer.valueOf(this.mProfile.videoFrameWidth), Integer.valueOf(this.mProfile.videoFrameHeight)));
                updatePreferenceTrampoline(1);
            }
        }
    }

    @Override // com.android.camera.protocol.ModeProtocol.CameraAction, com.android.camera.module.VideoBase
    public void onShutterButtonClick(int i) {
        String str = VideoBase.TAG;
        Log.v(str, "onShutterButtonClick isRecording=" + this.mMediaRecorderRecording + " inStartingFocusRecording=" + this.mInStartingFocusRecording);
        this.mInStartingFocusRecording = false;
        this.mLastBackPressedTime = 0;
        if (isIgnoreTouchEvent()) {
            Log.w(VideoBase.TAG, "onShutterButtonClick: ignore touch event");
        } else if (isFrontCamera() && this.mActivity.isScreenSlideOff()) {
        } else {
            if (this.mMediaRecorderRecording) {
                stopVideoRecording(false);
                return;
            }
            ModeProtocol.RecordState recordState = (ModeProtocol.RecordState) ModeCoordinatorImpl.getInstance().getAttachProtocol(212);
            if (this.mIsSatFallback == 0 || !shouldCheckSatFallbackState()) {
                recordState.onPrepare();
                updateVideoTagState(0);
                if (!checkCallingState()) {
                    recordState.onFailed();
                    updateVideoTagState(4);
                    return;
                }
                this.mActivity.getScreenHint().updateHint();
                if (Storage.isLowStorageAtLastPoint()) {
                    recordState.onFailed();
                    updateVideoTagState(4);
                    return;
                }
                setTriggerMode(i);
                enableCameraControls(false);
                playCameraSound(2);
                if (this.mFocusManager.canRecord()) {
                    startVideoRecording();
                    return;
                }
                Log.v(VideoBase.TAG, "wait for autoFocus");
                this.mInStartingFocusRecording = true;
                this.mHandler.sendEmptyMessageDelayed(55, 3000);
                return;
            }
            Log.w(VideoBase.TAG, "video record check: sat fallback");
        }
    }

    @Override // com.android.camera.protocol.ModeProtocol.CameraAction, com.android.camera.module.VideoBase
    public void onShutterButtonFocus(boolean z, int i) {
    }

    @Override // com.android.camera.module.BaseModule, com.android.camera.module.Module
    public void onSingleTapUp(int i, int i2, boolean z) {
        ModeProtocol.BackStack backStack;
        if (!this.mPaused && this.mCamera2Device != null && !hasCameraException() && this.mCamera2Device.isSessionReady() && !this.mSnapshotInProgress && isInTapableRect(i, i2)) {
            if (!isFrameAvailable()) {
                Log.w(VideoBase.TAG, "onSingleTapUp: frame not available");
            } else if ((!isFrontCamera() || !this.mActivity.isScreenSlideOff()) && (backStack = (ModeProtocol.BackStack) ModeCoordinatorImpl.getInstance().getAttachProtocol(171)) != null && !backStack.handleBackStackFromTapDown(i, i2) && !this.isAutoZoomTracking.get()) {
                if (this.mObjectTrackingStarted) {
                    stopObjectTracking(false);
                }
                unlockAEAF();
                this.mMainProtocol.setFocusViewType(true);
                this.mTouchFocusStartingTime = System.currentTimeMillis();
                Point point = new Point(i, i2);
                mapTapCoordinate(point);
                this.mFocusManager.onSingleTapUp(point.x, point.y, z);
            }
        }
    }

    @Override // com.android.camera.module.BaseModule, com.android.camera.module.Module
    public void onStop() {
        super.onStop();
        this.mHandler.removeCallbacksAndMessages(null);
        exitSavePowerMode();
    }

    @Override // com.android.camera.protocol.ModeProtocol.AutoZoomModuleProtocol
    public void onTrackLost() {
        notifyAutoZoomStartUiHint();
    }

    @Override // com.android.camera.protocol.ModeProtocol.AutoZoomModuleProtocol
    public void onTrackLosting() {
        this.mTrackLostCount++;
    }

    @Override // com.android.camera.module.BaseModule, com.android.camera.module.Module, com.android.camera.module.VideoBase
    public void onUserInteraction() {
        super.onUserInteraction();
        if (this.mMediaRecorderRecording) {
            keepPowerSave();
        }
    }

    /* access modifiers changed from: protected */
    @Override // com.android.camera.module.VideoBase
    public void onWaitStopCallbackTimeout() {
        stopRecorder();
        startPreviewAfterRecord();
    }

    @Override // com.android.camera.module.BaseModule
    public void onZoomingActionEnd(int i) {
        String str = VideoBase.TAG;
        Log.d(str, "onZoomingActionEnd(): " + ZoomingAction.toString(i) + " @hash: " + hashCode());
        ModeProtocol.DualController dualController = (ModeProtocol.DualController) ModeCoordinatorImpl.getInstance().getAttachProtocol(182);
        if (dualController != null && dualController.isZoomPanelVisible()) {
            dualController.updateZoomIndexsButton();
        }
    }

    @Override // com.android.camera.module.BaseModule
    public void onZoomingActionStart(int i) {
        String str = VideoBase.TAG;
        Log.d(str, "onZoomingActionStart(): " + ZoomingAction.toString(i) + " @hash: " + hashCode());
        ModeProtocol.TopAlert topAlert = (ModeProtocol.TopAlert) ModeCoordinatorImpl.getInstance().getAttachProtocol(172);
        if (topAlert != null && topAlert.isExtraMenuShowing()) {
            topAlert.hideExtraMenu();
        }
    }

    /* access modifiers changed from: protected */
    public void pauseMediaRecorder(MediaRecorder mediaRecorder) {
        try {
            mediaRecorder.pause();
        } catch (IllegalArgumentException e2) {
            Log.e(VideoBase.TAG, e2.getMessage());
        }
    }

    @Override // com.android.camera.module.Module
    public void pausePreview() {
        Log.v(VideoBase.TAG, "pausePreview");
        this.mPreviewing = false;
        if (CameraSchedulers.isOnMainThread()) {
            stopObjectTracking(false);
        }
        Camera2Proxy camera2Proxy = this.mCamera2Device;
        if (camera2Proxy != null) {
            camera2Proxy.pausePreview();
        }
        FocusManager2 focusManager2 = this.mFocusManager;
        if (focusManager2 != null) {
            focusManager2.resetFocused();
        }
    }

    /* access modifiers changed from: protected */
    public void pauseVideoRecording() {
        Log.d(VideoBase.TAG, "pauseVideoRecording");
        if (this.mMediaRecorderRecording && !this.mMediaRecorderRecordingPaused) {
            try {
                pauseMediaRecorder(this.mMediaRecorder);
            } catch (IllegalStateException unused) {
                Log.e(VideoBase.TAG, "failed to pause media recorder");
            }
            this.mVideoRecordedDuration = SystemClock.uptimeMillis() - this.mRecordingStartTime;
            this.mMediaRecorderRecordingPaused = true;
            this.mHandler.removeMessages(42);
            updateRecordingTime();
        }
    }

    @Override // com.android.camera.protocol.ModeProtocol.TopConfigProtocol
    public void reShowMoon() {
        if (CameraSettings.isAutoZoomEnabled(this.mModuleIndex)) {
            notifyAutoZoomStartUiHint();
        }
    }

    /* access modifiers changed from: protected */
    @Override // com.android.camera.module.VideoBase
    public void readVideoPreferences() {
        int preferVideoQuality = !ModuleManager.isVideoNewSlowMotion() ? CameraSettings.getPreferVideoQuality(this.mActualCameraId, this.mModuleIndex) : 6;
        int videoQuality = getActivity().getCameraIntentManager().getVideoQuality();
        if (videoQuality > -1) {
            preferVideoQuality = videoQuality == 1 ? CameraSettings.getPreferVideoQuality(this.mActualCameraId, this.mModuleIndex) : videoQuality == 0 ? videoQuality : CameraSettings.getPreferVideoQuality(String.valueOf(videoQuality), this.mActualCameraId, this.mModuleIndex);
        }
        this.mSpeed = DataRepository.dataItemRunning().getVideoSpeed();
        if (this.mModuleIndex == 172) {
            this.mSpeed = CameraSettings.VIDEO_MODE_960;
        } else {
            this.mSlowModeFps = null;
        }
        this.mTimeBetweenTimeLapseFrameCaptureMs = 0;
        this.mCaptureTimeLapse = false;
        int i = 5;
        if (CameraSettings.VIDEO_SPEED_FAST.equals(this.mSpeed)) {
            if (DataRepository.dataItemFeature().uj()) {
                this.mTimeBetweenTimeLapseFrameCaptureMs = Integer.parseInt(DataRepository.dataItemRunning().getString(CameraSettings.KEY_NEW_VIDEO_TIME_LAPSE_FRAME_INTERVAL, DataRepository.dataItemRunning().getComponentRunningFastMotionSpeed().getDefaultValue(160)));
            } else {
                this.mTimeBetweenTimeLapseFrameCaptureMs = Integer.parseInt(DataRepository.dataItemGlobal().getString(CameraSettings.KEY_VIDEO_TIME_LAPSE_FRAME_INTERVAL, getString(R.string.pref_video_time_lapse_frame_interval_default)));
            }
            this.mCaptureTimeLapse = this.mTimeBetweenTimeLapseFrameCaptureMs != 0;
            if (this.mCaptureTimeLapse && ((preferVideoQuality = preferVideoQuality + 1000) < 1000 || preferVideoQuality > 1008)) {
                preferVideoQuality += NotificationManagerCompat.IMPORTANCE_UNSPECIFIED;
                this.mCaptureTimeLapse = false;
                forceToNormalMode();
                this.mActivity.runOnUiThread(new pa(this));
            }
            this.mQuality = preferVideoQuality % 1000;
        } else if (this.mModuleIndex == 172) {
            this.mQuality = 6;
            Size size = SIZE_1080;
            int parseInt = Integer.parseInt(DataRepository.dataItemConfig().getComponentConfigSlowMotionQuality().getComponentValue(172));
            if (parseInt == 5) {
                size = SIZE_720;
                this.mQuality = parseInt;
            }
            this.mSlowModeFps = DataRepository.dataItemConfig().getComponentConfigSlowMotion().getComponentValue(172);
            if (isFPS120()) {
                updateHfrFPSRange(size, 120);
            } else {
                updateHfrFPSRange(size, 240);
            }
            preferVideoQuality = parseInt;
        } else {
            this.mQuality = preferVideoQuality;
        }
        CamcorderProfile camcorderProfile = this.mProfile;
        if (!(camcorderProfile == null || camcorderProfile.quality % 1000 == this.mQuality)) {
            stopObjectTracking(false);
        }
        this.mProfile = fetchProfile(this.mBogusCameraId, preferVideoQuality);
        CamcorderProfile camcorderProfile2 = this.mProfile;
        if (!CameraSettings.isHdr10Alive(this.mModuleIndex)) {
            i = CameraSettings.getVideoEncoder();
        }
        camcorderProfile2.videoCodec = i;
        this.mOutputFormat = this.mProfile.fileFormat;
        String str = VideoBase.TAG;
        Locale locale = Locale.ENGLISH;
        Object[] objArr = new Object[4];
        objArr[0] = Integer.valueOf(getHSRValue() > 0 ? getHSRValue() : this.mProfile.videoFrameRate);
        objArr[1] = Integer.valueOf(this.mProfile.videoFrameWidth);
        objArr[2] = Integer.valueOf(this.mProfile.videoFrameHeight);
        objArr[3] = Integer.valueOf(this.mProfile.videoCodec);
        Log.d(str, String.format(locale, "frameRate=%d profileSize=%dx%d codec=%d", objArr));
        if (ModuleManager.isVideoNewSlowMotion()) {
            this.mFrameRate = this.mHfrFPSUpper;
        } else {
            this.mFrameRate = this.mProfile.videoFrameRate;
        }
        this.mFrameRateTrack = this.mFrameRate;
        if (isFPS960()) {
            this.mMaxVideoDurationInMs = 2000;
            return;
        }
        try {
            this.mMaxVideoDurationInMs = this.mActivity.getCameraIntentManager().getVideoDurationTime() * 1000;
        } catch (RuntimeException unused) {
            if (!CameraSettings.is4KHigherVideoQuality(this.mQuality) || this.mCaptureTimeLapse) {
                this.mMaxVideoDurationInMs = 0;
            } else {
                boolean Yi = DataRepository.dataItemFeature().Yi();
                if (DataRepository.dataItemFeature().Zi() && is8KCamcorder()) {
                    this.mMaxVideoDurationInMs = MAX_DURATION_8K;
                } else if (Yi && is4KCamcorder()) {
                    this.mMaxVideoDurationInMs = MAX_DURATION_4K;
                }
            }
        }
        int i2 = this.mMaxVideoDurationInMs;
        if (i2 != 0 && i2 < 1000) {
            this.mMaxVideoDurationInMs = 1000;
        }
    }

    @Override // com.android.camera.protocol.ModeProtocol.BaseProtocol, com.android.camera.module.BaseModule, com.android.camera.module.Module
    public void registerProtocol() {
        super.registerProtocol();
        ModeCoordinatorImpl.getInstance().attachProtocol(161, this);
        ModeCoordinatorImpl.getInstance().attachProtocol(167, this);
        ModeCoordinatorImpl.getInstance().attachProtocol(169, this);
        ModeCoordinatorImpl.getInstance().attachProtocol(215, this);
        ModeCoordinatorImpl.getInstance().attachProtocol(193, this);
        getActivity().getImplFactory().initAdditional(getActivity(), 164, 174, 234, 212);
    }

    /* access modifiers changed from: protected */
    @Override // com.android.camera.module.VideoBase
    public void resizeForPreviewAspectRatio() {
        if (((this.mCameraCapabilities.getSensorOrientation() - Util.getDisplayRotation(this.mActivity)) + 360) % 180 == 0) {
            ModeProtocol.MainContentProtocol mainContentProtocol = this.mMainProtocol;
            CameraSize cameraSize = this.mVideoSize;
            mainContentProtocol.setPreviewAspectRatio(((float) cameraSize.height) / ((float) cameraSize.width));
            return;
        }
        ModeProtocol.MainContentProtocol mainContentProtocol2 = this.mMainProtocol;
        CameraSize cameraSize2 = this.mVideoSize;
        mainContentProtocol2.setPreviewAspectRatio(((float) cameraSize2.width) / ((float) cameraSize2.height));
    }

    @Override // com.android.camera.module.Module
    public void resumePreview() {
        Log.v(VideoBase.TAG, "resumePreview");
        this.mPreviewing = true;
        Camera2Proxy camera2Proxy = this.mCamera2Device;
        if (camera2Proxy != null) {
            camera2Proxy.resumePreview();
        }
    }

    @Override // com.android.camera.module.VideoBase
    public void set3DAudioParameter() {
        super.set3DAudioParameter();
        if (DataRepository.dataItemFeature().cj()) {
            int i = this.mModuleIndex;
            if (i != 162 && i != 180) {
                return;
            }
            if ((this.mMediaRecorderRecording || this.mMediaRecorderRecordingPaused) && DataRepository.dataItemRunning().getComponentRunningAiAudio().getCurrentRecType(this.mModuleIndex) == 3) {
                setCurrentAiAudioParameters(true);
            }
        }
    }

    @Override // com.android.camera.protocol.ModeProtocol.AutoZoomModuleProtocol
    public void setAutoZoomMode(int i) {
        updatePreferenceInWorkThread(51);
    }

    @Override // com.android.camera.protocol.ModeProtocol.AutoZoomModuleProtocol
    public void setAutoZoomStartCapture(RectF rectF) {
        if (this.mCamera2Device != null && isAlive()) {
            this.mCamera2Device.setAutoZoomStartCapture(new float[]{rectF.left, rectF.top, rectF.width(), rectF.height()}, this.mMediaRecorderRecording);
        }
    }

    @Override // com.android.camera.protocol.ModeProtocol.AutoZoomModuleProtocol
    public void setAutoZoomStopCapture(int i) {
        if (this.mCamera2Device != null && isAlive()) {
            this.mCamera2Device.setAutoZoomStopCapture(i, this.mMediaRecorderRecording);
        }
    }

    /* access modifiers changed from: protected */
    @Override // com.android.camera.module.BaseModule
    public boolean shouldCheckSatFallbackState() {
        return HybridZoomingSystem.IS_3_OR_MORE_SAT && isInVideoSAT() && !this.mInStartingFocusRecording;
    }

    @Override // com.android.camera.protocol.ModeProtocol.TopConfigProtocol
    public void startAiLens() {
    }

    @Override // com.android.camera.protocol.ModeProtocol.AutoZoomModuleProtocol
    public void startAutoZoom() {
        this.isShowOrHideUltraWideHint.getAndSet(true);
        this.isAutoZoomTracking.getAndSet(false);
        this.mHandler.post(new Runnable() {
            /* class com.android.camera.module.VideoModule.AnonymousClass10 */

            public void run() {
                if (VideoModule.this.mAutoZoomViewProtocol != null) {
                    VideoModule.this.mAutoZoomViewProtocol.onAutoZoomStarted();
                }
            }
        });
        notifyAutoZoomStartUiHint();
    }

    @Override // com.android.camera.ui.ObjectView.ObjectViewListener
    public void startObjectTracking() {
        String str = VideoBase.TAG;
        Log.d(str, "startObjectTracking: started=" + this.mObjectTrackingStarted);
    }

    @Override // com.android.camera.module.Module
    public void startPreview() {
        String str = VideoBase.TAG;
        Log.v(str, "startPreview: previewing=" + this.mPreviewing);
        checkDisplayOrientation();
        this.mPreviewing = true;
    }

    @Override // com.android.camera.protocol.ModeProtocol.AutoZoomModuleProtocol
    public void startTracking(RectF rectF) {
        if (this.mCamera2Device != null && isAlive()) {
            ModeProtocol.TopAlert topAlert = this.mTopAlert;
            if (topAlert != null) {
                topAlert.alertAiDetectTipHint(4, 0, 0);
            }
            notifyAutoZoomStopUiHint();
            this.mCamera2Device.setAutoZoomStopCapture(-1, this.mMediaRecorderRecording);
            this.mCamera2Device.setAutoZoomStartCapture(new float[]{rectF.left, rectF.top, rectF.width(), rectF.height()}, this.mMediaRecorderRecording);
            this.mCamera2Device.setAutoZoomStartCapture(new float[]{0.0f, 0.0f, 0.0f, 0.0f}, this.mMediaRecorderRecording);
            this.isAutoZoomTracking.getAndSet(true);
            CameraStatUtils.trackSelectObject(this.mMediaRecorderRecording);
        }
    }

    /* access modifiers changed from: protected */
    public void startVideoPreviewSession() {
        Log.d(VideoBase.TAG, "startPreviewSession");
        if (isDeviceAlive()) {
            checkDisplayOrientation();
            this.mCamera2Device.setFocusCallback(this);
            this.mCamera2Device.setErrorCallback(this.mErrorCallback);
            this.mCamera2Device.setPictureSize(this.mPreviewSize);
            Surface surface = new Surface(this.mActivity.getCameraScreenNail().getSurfaceTexture());
            this.mSurfaceCreatedTimestamp = this.mActivity.getCameraScreenNail().getSurfaceCreatedTimestamp();
            this.mCamera2Device.startVideoPreviewSession(surface, 0, false, false, null, getOperatingMode(), false, this);
            this.mFocusManager.resetFocused();
            this.mPreviewing = true;
        }
    }

    /* access modifiers changed from: protected */
    @Override // com.android.camera.module.VideoBase
    public void startVideoRecording() {
        String str = VideoBase.TAG;
        Log.v(str, "startVideoRecording: mode=" + this.mSpeed);
        if (isDeviceAlive()) {
            ScenarioTrackUtil.trackStartVideoRecordStart(this.mSpeed, isFrontCamera());
            this.mCurrentFileNumber = isCaptureIntent() ? -1 : 0;
            silenceOuterAudio();
            if (!startRecorder()) {
                onStartRecorderFail();
                if (DataRepository.dataItemFeature().Wj() && CameraSettings.is4KHigherVideoQuality(this.mQuality)) {
                    int hSRValue = getHSRValue();
                    if (hSRValue <= 0) {
                        hSRValue = this.mProfile.videoFrameRate;
                    }
                    ThermalHelper.notifyThermalRecordStop(this.mQuality, hSRValue);
                    return;
                }
                return;
            }
            ModeProtocol.RecordState recordState = (ModeProtocol.RecordState) ModeCoordinatorImpl.getInstance().getAttachProtocol(212);
            if (recordState != null) {
                recordState.onStart();
                updateVideoTagState(1);
            }
            updatePreferenceTrampoline(UpdateConstant.VIDEO_TYPES_RECORD);
            if (ModuleManager.isVideoNewSlowMotion()) {
                this.mCamera2Device.startHighSpeedRecording();
            } else if (!this.mProtolRender.map(ua.INSTANCE).orElse((U) false).booleanValue()) {
                this.mCamera2Device.startRecording(false);
            }
            Log.v(VideoBase.TAG, "startVideoRecording process done");
            this.mTrackLostCount = 0;
            ScenarioTrackUtil.trackStartVideoRecordEnd();
            onStartRecorderSucceed();
            this.mHandler.removeMessages(60);
            this.mHandler.sendEmptyMessage(60);
        }
    }

    @Override // com.android.camera.protocol.ModeProtocol.AutoZoomModuleProtocol
    public void stopAutoZoom() {
        this.isShowOrHideUltraWideHint.getAndSet(false);
        this.isAutoZoomTracking.getAndSet(false);
        this.mHandler.post(new Runnable() {
            /* class com.android.camera.module.VideoModule.AnonymousClass11 */

            public void run() {
                if (VideoModule.this.mAutoZoomViewProtocol != null) {
                    VideoModule.this.mAutoZoomViewProtocol.onAutoZoomStopped();
                }
            }
        });
        notifyAutoZoomStopUiHint();
    }

    @Override // com.android.camera.module.loader.camera2.FocusManager2.Listener, com.android.camera.ui.ObjectView.ObjectViewListener, com.android.camera.module.VideoBase
    public void stopObjectTracking(boolean z) {
        String str = VideoBase.TAG;
        Log.d(str, "stopObjectTracking: started=" + this.mObjectTrackingStarted);
    }

    /* access modifiers changed from: protected */
    @SuppressLint({"CheckResult"})
    public void stopRecorder() {
        this.mPendingStopRecorder = false;
        this.mHandler.removeMessages(46);
        if (DataRepository.dataItemFeature().Wj() && CameraSettings.is4KHigherVideoQuality(this.mQuality)) {
            int hSRValue = getHSRValue();
            if (hSRValue <= 0) {
                hSRValue = this.mProfile.videoFrameRate;
            }
            ThermalHelper.notifyThermalRecordStop(this.mQuality, hSRValue);
        }
        Single.create(new SingleOnSubscribe<Boolean>() {
            /* class com.android.camera.module.VideoModule.AnonymousClass8 */

            @Override // io.reactivex.SingleOnSubscribe
            public void subscribe(SingleEmitter<Boolean> singleEmitter) throws Exception {
                VideoModule.this.mStopRecorderDone = new CountDownLatch(1);
                long currentTimeMillis = System.currentTimeMillis();
                ScenarioTrackUtil.trackStopVideoRecordStart(VideoModule.this.mSpeed, VideoModule.this.isFrontCamera());
                try {
                    VideoModule.this.mMediaRecorder.setOnErrorListener(null);
                    VideoModule.this.mMediaRecorder.setOnInfoListener(null);
                    VideoModule.this.mMediaRecorder.stop();
                } catch (RuntimeException e2) {
                    Log.e(VideoBase.TAG, "failed to stop media recorder", e2);
                    VideoModule videoModule = VideoModule.this;
                    String str = videoModule.mCurrentVideoFilename;
                    if (str != null) {
                        videoModule.deleteVideoFile(str);
                        VideoModule.this.mCurrentVideoFilename = null;
                    }
                }
                if (!VideoModule.this.mPaused && !VideoModule.this.mActivity.isActivityPaused()) {
                    VideoModule.this.playCameraSound(3);
                }
                VideoModule.this.releaseMediaRecorder();
                VideoModule.this.mStopRecorderDone.countDown();
                String str2 = VideoBase.TAG;
                Log.d(str2, "releaseTime=" + (System.currentTimeMillis() - currentTimeMillis));
                long uptimeMillis = SystemClock.uptimeMillis();
                VideoModule videoModule2 = VideoModule.this;
                long j = uptimeMillis - videoModule2.mRecordingStartTime;
                if (videoModule2.mCamera2Device != null && ModuleManager.isVideoNewSlowMotion() && (VideoModule.this.isFPS120() || VideoModule.this.isFPS240())) {
                    CameraStatUtils.trackNewSlowMotionVideoRecorded(VideoModule.this.isFPS120() ? CameraSettings.VIDEO_MODE_120 : CameraSettings.VIDEO_MODE_240, VideoModule.this.mQuality, VideoModule.this.mCamera2Device.getFlashMode(), VideoModule.this.mFrameRate, j / 1000, false);
                }
                VideoModule videoModule3 = VideoModule.this;
                if (videoModule3.mCurrentVideoFilename != null && videoModule3.isFPS960()) {
                    if (!VideoModule.this.mMediaRecorderPostProcessing || !VideoModule.this.isActivityResumed()) {
                        String str3 = VideoBase.TAG;
                        Log.d(str3, "uncomplete video.=" + j);
                        VideoModule videoModule4 = VideoModule.this;
                        videoModule4.deleteVideoFile(videoModule4.mCurrentVideoFilename);
                        VideoModule.this.mCurrentVideoFilename = null;
                        MistatsWrapper.keyTriggerEvent(MistatsConstants.VideoAttr.KEY_VIDEO_960, MistatsConstants.BaseEvent.FEATURE_NAME, MistatsConstants.VideoAttr.VALUE_FPS960_TOO_SHORT);
                    } else {
                        long currentTimeMillis2 = System.currentTimeMillis();
                        VideoModule videoModule5 = VideoModule.this;
                        String postProcessVideo = videoModule5.postProcessVideo(videoModule5.mCurrentVideoFilename);
                        String str4 = VideoBase.TAG;
                        Log.d(str4, "processTime=" + (System.currentTimeMillis() - currentTimeMillis2));
                        if (postProcessVideo == null) {
                            VideoModule videoModule6 = VideoModule.this;
                            videoModule6.mCurrentVideoFilename = null;
                            videoModule6.mCurrentVideoValues = null;
                            MistatsWrapper.keyTriggerEvent(MistatsConstants.VideoAttr.KEY_VIDEO_960, MistatsConstants.BaseEvent.FEATURE_NAME, MistatsConstants.VideoAttr.VALUE_FPS960_PROCESS_FAILED);
                        } else {
                            VideoModule videoModule7 = VideoModule.this;
                            videoModule7.mCurrentVideoFilename = postProcessVideo;
                            videoModule7.mCurrentVideoValues.put("_data", postProcessVideo);
                            VideoModule videoModule8 = VideoModule.this;
                            if (videoModule8.mCamera2Device != null) {
                                CameraStatUtils.trackNewSlowMotionVideoRecorded(CameraSettings.VIDEO_MODE_960, videoModule8.mQuality, VideoModule.this.mCamera2Device.getFlashMode(), 960, 10, CameraSettings.is960WatermarkOn(VideoModule.this.getModuleIndex()));
                            }
                        }
                    }
                }
                singleEmitter.onSuccess(Boolean.TRUE);
            }
        }).subscribeOn(CameraSchedulers.sCameraSetupScheduler).observeOn(AndroidSchedulers.mainThread()).subscribe(new qa(this));
    }

    @Override // com.android.camera.protocol.ModeProtocol.AutoZoomModuleProtocol
    public void stopTracking(int i) {
        if (this.isAutoZoomTracking.get()) {
            this.isAutoZoomTracking.getAndSet(false);
            if (this.mCamera2Device != null && isAlive()) {
                this.mCamera2Device.setAutoZoomStopCapture(0, this.mMediaRecorderRecording);
                this.mCamera2Device.setAutoZoomStopCapture(-1, this.mMediaRecorderRecording);
            }
            this.mAutoZoomViewProtocol.onTrackingStopped(i);
        }
        notifyAutoZoomStartUiHint();
    }

    @Override // com.android.camera.module.VideoBase
    public void stopVideoRecording(boolean z) {
        long j;
        ModeProtocol.ConfigChanges configChanges;
        int i;
        ModeProtocol.TopAlert topAlert;
        ModeProtocol.SubtitleRecording subtitleRecording;
        String str = VideoBase.TAG;
        Log.v(str, "stopVideoRecording>>" + this.mMediaRecorderRecording + "|" + true);
        if (this.mMediaRecorderRecording) {
            if (this.isAutoZoomTracking.get()) {
                stopTracking(0);
            }
            this.mMediaRecorderRecording = false;
            this.mMediaRecorderRecordingPaused = false;
            long currentTimeMillis = System.currentTimeMillis();
            if (isFPS960()) {
                if (2000 - (SystemClock.uptimeMillis() - this.mRecordingStartTime) <= 100) {
                    this.mMediaRecorderPostProcessing = true;
                }
            }
            if (this.mIsSubtitleSupported && (subtitleRecording = this.mSubtitleRecording) != null) {
                subtitleRecording.handleSubtitleRecordingStop();
            }
            if (this.mModuleIndex == 180 && (topAlert = this.mTopAlert) != null) {
                topAlert.updateProVideoRecordingSimpleView(false);
            }
            ModeProtocol.MainContentProtocol mainContentProtocol = this.mMainProtocol;
            if (mainContentProtocol != null) {
                mainContentProtocol.processingFinish();
                updateVideoTagState(4);
            }
            handleAiAudioTipsState(false);
            if (HybridZoomingSystem.IS_3_OR_MORE_SAT && (i = this.mModuleIndex) != 172 && i != 204 && i != 180 && !CameraSettings.isMacroModeEnabled(i) && !CameraSettings.isAutoZoomEnabled(this.mModuleIndex) && !CameraSettings.isSuperEISEnabled(this.mModuleIndex) && isBackCamera()) {
                updateZoomRatioToggleButtonState(false);
                if (isUltraWideBackCamera()) {
                    if (CameraSettings.isMacroModeEnabled(this.mModuleIndex)) {
                        setMinZoomRatio(HybridZoomingSystem.getMinimumMacroOpticalZoomRatio());
                        setMaxZoomRatio(HybridZoomingSystem.getMaximumMacroOpticalZoomRatio());
                    } else {
                        setMinZoomRatio(HybridZoomingSystem.FLOAT_ZOOM_RATIO_ULTR);
                        setMaxZoomRatio(HybridZoomingSystem.FLOAT_ZOOM_RATIO_ULTR * this.mCameraCapabilities.getMaxZoomRatio());
                    }
                } else if (!DataRepository.dataItemGlobal().isNormalIntent() && this.mCameraCapabilities.isSupportLightTripartite()) {
                    setMinZoomRatio(1.0f);
                    setMaxZoomRatio(Math.min(6.0f, this.mCameraCapabilities.getMaxZoomRatio()));
                } else if (DataRepository.dataItemFeature().qm()) {
                    setMinZoomRatio(HybridZoomingSystem.FLOAT_ZOOM_RATIO_ULTR);
                    if (isInVideoSAT()) {
                        setMaxZoomRatio(DataRepository.dataItemFeature().Gh());
                    } else if (isStandaloneMacroCamera()) {
                        setMinZoomRatio(1.0f);
                        setMaxZoomRatio(Math.min(2.0f, this.mCameraCapabilities.getMaxZoomRatio()));
                    } else {
                        setMaxZoomRatio(Math.min(6.0f, this.mCameraCapabilities.getMaxZoomRatio()));
                    }
                } else {
                    setMinZoomRatio(HybridZoomingSystem.FLOAT_ZOOM_RATIO_ULTR);
                    if (isInVideoSAT()) {
                        setMaxZoomRatio(Math.min(6.0f, this.mCameraCapabilities.getMaxZoomRatio()));
                    } else {
                        setVideoMaxZoomRatioByTele();
                    }
                }
            }
            enableCameraControls(false);
            if (!(this.mCamera2Device == null || getModuleIndex() == 204)) {
                this.mCamera2Device.stopRecording();
            }
            if (this.mCountDownTimer != null && CameraSettings.isVideoBokehOn()) {
                this.mCountDownTimer.cancel();
            }
            ModeProtocol.RecordState recordState = (ModeProtocol.RecordState) ModeCoordinatorImpl.getInstance().getAttachProtocol(212);
            if (recordState != null) {
                if (this.mMediaRecorderPostProcessing) {
                    recordState.onPostSavingStart();
                } else {
                    recordState.onFinish();
                }
            }
            if (this.mCamera2Device == null || ModuleManager.isVideoNewSlowMotion()) {
                j = currentTimeMillis;
            } else {
                boolean isAutoZoomEnabled = CameraSettings.isAutoZoomEnabled(this.mModuleIndex);
                boolean isSuperEISEnabled = CameraSettings.isSuperEISEnabled(this.mModuleIndex);
                String str2 = this.mSpeed;
                if (isFPS120() || isFPS240() || isFPS960()) {
                    str2 = MistatsConstants.VideoAttr.VALUE_SPEED_SLOW;
                }
                ComponentRunningMacroMode componentRunningMacroMode = DataRepository.dataItemRunning().getComponentRunningMacroMode();
                if (componentRunningMacroMode != null && componentRunningMacroMode.isSwitchOn(getModuleIndex())) {
                    HashMap hashMap = new HashMap();
                    hashMap.put(MistatsConstants.MacroAttr.PARAM_SLOW_MOTION_MACRO, this.mSlowModeFps);
                    MistatsWrapper.mistatEvent(MistatsConstants.MacroAttr.FUCNAME_MACRO_MODE, hashMap);
                }
                if (this.mModuleIndex == 180) {
                    trackProVideoInfo();
                    j = currentTimeMillis;
                } else {
                    j = currentTimeMillis;
                    CameraStatUtils.trackVideoRecorded(isFrontCamera(), getActualCameraId(), getModuleIndex(), isAutoZoomEnabled, isSuperEISEnabled, CameraSettings.isUltraWideConfigOpen(getModuleIndex()), str2, this.mQuality, this.mCamera2Device.getFlashMode(), this.mFrameRateTrack, this.mTimeBetweenTimeLapseFrameCaptureMs, this.mBeautyValues, this.mVideoRecordTime, this.mIsSubtitleSupported);
                }
                if (isAutoZoomEnabled) {
                    String str3 = VideoBase.TAG;
                    Log.v(str3, "track count is " + this.mTrackLostCount);
                    CameraStatUtils.trackLostCount(this.mTrackLostCount);
                }
            }
            this.mVideoRecordTime = 0;
            stopRecorder();
            startPreviewAfterRecord();
            handleTempVideoFile();
            AutoLockManager.getInstance(this.mActivity).hibernateDelayed();
            if (this.mCaptureTimeLapse && DataRepository.dataItemFeature().uj() && (configChanges = (ModeProtocol.ConfigChanges) ModeCoordinatorImpl.getInstance().getAttachProtocol(164)) != null) {
                configChanges.reCheckFastMotion(true);
            }
            exitSavePowerMode();
            String str4 = VideoBase.TAG;
            Log.v(str4, "stopVideoRecording<<time=" + (System.currentTimeMillis() - j));
        }
    }

    public boolean takeVideoSnapShoot() {
        if (this.mPaused || this.mActivity.isActivityPaused() || this.mSnapshotInProgress || !this.mMediaRecorderRecording || !isDeviceAlive()) {
            return false;
        }
        if (Storage.isLowStorageAtLastPoint()) {
            Log.w(VideoBase.TAG, "capture: low storage");
            stopVideoRecording(false);
            return false;
        } else if (this.mActivity.getImageSaver().isBusy()) {
            Log.w(VideoBase.TAG, "capture: ImageSaver is full");
            RotateTextToast.getInstance(this.mActivity).show(R.string.toast_saving, 0);
            return false;
        } else {
            this.mCamera2Device.setJpegRotation(Util.getJpegRotation(this.mBogusCameraId, this.mOrientation));
            Location currentLocation = LocationManager.instance().getCurrentLocation();
            this.mCamera2Device.setGpsLocation(currentLocation);
            setJpegQuality();
            updateFrontMirror();
            if (this.mProtolRender.map(oa.INSTANCE).orElse((U) true).booleanValue()) {
                this.mActivity.getCameraScreenNail().animateCapture(getCameraRotation());
            }
            Log.v(VideoBase.TAG, "capture: start");
            if (this.mProtolRender.map(ra.INSTANCE).orElse((U) false).booleanValue()) {
                this.mProtolRender.get().getControler().captureVideoSnapshot(new JpegPictureCallback(currentLocation), this.mActivity.getOrientation(), this.mCamera2Device.getPreviewCaptureResult());
            } else {
                this.mCamera2Device.captureVideoSnapshot(new JpegPictureCallback(currentLocation));
            }
            this.mSnapshotInProgress = true;
            CameraStatUtils.trackVideoSnapshot(isFrontCamera());
            return true;
        }
    }

    /* access modifiers changed from: protected */
    public void trackDualVideo() {
    }

    @Override // com.android.camera.protocol.ModeProtocol.BaseProtocol, com.android.camera.module.BaseModule, com.android.camera.module.Module
    public void unRegisterProtocol() {
        super.unRegisterProtocol();
        ModeCoordinatorImpl.getInstance().detachProtocol(215, this);
        ModeCoordinatorImpl.getInstance().detachProtocol(161, this);
        ModeCoordinatorImpl.getInstance().detachProtocol(167, this);
        ModeCoordinatorImpl.getInstance().detachProtocol(169, this);
        ModeCoordinatorImpl.getInstance().detachProtocol(193, this);
        getActivity().getImplFactory().detachAdditional();
    }

    /* access modifiers changed from: protected */
    public void updateFpsRange() {
        Range<Integer> range;
        if (isDeviceAlive()) {
            if (ModuleManager.isVideoNewSlowMotion()) {
                String str = VideoBase.TAG;
                Log.d(str, "mHfrFPSLower = " + this.mHfrFPSLower + ", mHfrFPSUpper = " + this.mHfrFPSUpper);
                this.mCamera2Device.setVideoFpsRange(new Range<>(Integer.valueOf(this.mHfrFPSLower), Integer.valueOf(this.mHfrFPSUpper)));
                return;
            }
            Range<Integer>[] supportedFpsRange = this.mCameraCapabilities.getSupportedFpsRange();
            int i = 0;
            Range<Integer> range2 = supportedFpsRange[0];
            ComponentRunning8KVideoQuality componentRunning8KVideoQuality = DataRepository.dataItemRunning().getComponentRunning8KVideoQuality();
            if (!componentRunning8KVideoQuality.isEnabled(this.mModuleIndex) || componentRunning8KVideoQuality.getFps(this.mModuleIndex) != 24) {
                int length = supportedFpsRange.length;
                while (true) {
                    if (i >= length) {
                        range = range2;
                        break;
                    }
                    Range<Integer> range3 = supportedFpsRange[i];
                    int hSRValue = getHSRValue();
                    if (hSRValue == 60) {
                        range = CameraSettings.isVideoDynamic60fpsOn(this.mModuleIndex) ? new Range<>(30, Integer.valueOf(hSRValue)) : new Range<>(Integer.valueOf(hSRValue), Integer.valueOf(hSRValue));
                    } else {
                        if (range2.getUpper().intValue() < range3.getUpper().intValue() || (range2.getUpper() == range3.getUpper() && range2.getLower().intValue() < range3.getLower().intValue())) {
                            range2 = range3;
                        }
                        i++;
                    }
                }
            } else {
                range = Range.create(24, 24);
                this.mFrameRateTrack = 24;
            }
            String str2 = VideoBase.TAG;
            Log.d(str2, "bestRange = " + range);
            this.mCamera2Device.setFpsRange(range);
            this.mCamera2Device.setVideoFpsRange(range);
        }
    }

    /* access modifiers changed from: protected */
    @Override // com.android.camera.module.BaseModule
    public void updateHDRPreference() {
        ComponentConfigHdr componentHdr = DataRepository.dataItemConfig().getComponentHdr();
        if (!componentHdr.isEmpty() && !isBackCamera()) {
            boolean equals = "normal".equals(componentHdr.getComponentValue(this.mModuleIndex));
            Camera2Proxy camera2Proxy = this.mCamera2Device;
            if (camera2Proxy != null) {
                camera2Proxy.applyVideoHdrMode(equals);
            }
        }
    }

    public void updateManualEvAdjust() {
        if (this.mModuleIndex == 167) {
            String manualValue = getManualValue(CameraSettings.KEY_QC_EXPOSURETIME, getString(R.string.pref_camera_exposuretime_default));
            String manualValue2 = getManualValue(CameraSettings.KEY_QC_ISO, getString(R.string.pref_camera_iso_default));
            String str = VideoBase.TAG;
            Log.d(str, "MODE_MANUAL: exposureTime = " + manualValue + "iso = " + manualValue2);
            boolean equals = c.mo() ? getString(R.string.pref_camera_exposuretime_default).equals(manualValue) : getString(R.string.pref_camera_iso_default).equals(manualValue2) || getString(R.string.pref_camera_exposuretime_default).equals(manualValue);
            Handler handler = this.mHandler;
            if (handler != null) {
                handler.post(new sa(this, equals));
            }
            if (1 == this.mCamera2Device.getFocusMode()) {
                Camera camera = this.mActivity;
                if (camera != null) {
                    camera.runOnUiThread(new ta(this));
                }
                unlockAEAF();
            }
        }
    }

    /* access modifiers changed from: protected */
    public void updatePictureAndPreviewSize() {
        int i;
        int i2;
        CamcorderProfile camcorderProfile = this.mProfile;
        double d2 = ((double) camcorderProfile.videoFrameWidth) / ((double) camcorderProfile.videoFrameHeight);
        List<CameraSize> supportedOutputSizeWithAssignedMode = this.mCameraCapabilities.getSupportedOutputSizeWithAssignedMode(MediaRecorder.class);
        CamcorderProfile camcorderProfile2 = this.mProfile;
        CameraSize optimalVideoSnapshotPictureSize = Util.getOptimalVideoSnapshotPictureSize(supportedOutputSizeWithAssignedMode, d2, camcorderProfile2.videoFrameWidth, camcorderProfile2.videoFrameHeight);
        this.mVideoSize = optimalVideoSnapshotPictureSize;
        Log.d(VideoBase.TAG, "videoSize: " + optimalVideoSnapshotPictureSize.toString());
        int i3 = Integer.MAX_VALUE;
        if (c.m0do()) {
            i3 = optimalVideoSnapshotPictureSize.width;
            i = optimalVideoSnapshotPictureSize.height;
        } else {
            i = Integer.MAX_VALUE;
        }
        this.mPictureSize = Util.getOptimalVideoSnapshotPictureSize(this.mCameraCapabilities.getSupportedOutputSizeWithAssignedMode(256), d2, i3, i);
        Log.d(VideoBase.TAG, "pictureSize: " + this.mPictureSize);
        int i4 = optimalVideoSnapshotPictureSize.width;
        if (i4 > Util.sWindowHeight || i4 < 720) {
            i4 = Util.sWindowHeight;
            i2 = Util.sWindowWidth;
        } else {
            i2 = optimalVideoSnapshotPictureSize.height;
        }
        this.mPreviewSize = Util.getOptimalVideoSnapshotPictureSize(this.mCameraCapabilities.getSupportedOutputSizeWithAssignedMode(SurfaceTexture.class), d2, i4, i2);
        CameraSize cameraSize = this.mPreviewSize;
        updateCameraScreenNailSize(cameraSize.width, cameraSize.height);
    }

    /* access modifiers changed from: protected */
    /* JADX WARNING: Removed duplicated region for block: B:36:0x008f  */
    /* JADX WARNING: Removed duplicated region for block: B:39:0x0098  */
    /* JADX WARNING: Removed duplicated region for block: B:42:0x00a0  */
    @Override // com.android.camera.module.VideoBase
    public void updateRecordingTime() {
        String str;
        long j;
        ModeProtocol.TopAlert topAlert;
        double d2;
        super.updateRecordingTime();
        if (this.mMediaRecorderRecording && !isFPS960() && !CameraSettings.isVideoBokehOn()) {
            long uptimeMillis = SystemClock.uptimeMillis() - this.mRecordingStartTime;
            if (this.mMediaRecorderRecordingPaused) {
                uptimeMillis = this.mVideoRecordedDuration;
            }
            int i = this.mMaxVideoDurationInMs;
            boolean z = i != 0 && uptimeMillis >= ((long) (i - 60000));
            long max = z ? Math.max(0L, ((long) this.mMaxVideoDurationInMs) - uptimeMillis) + 999 : uptimeMillis;
            long j2 = 1000;
            if (isNormalMode()) {
                this.mVideoRecordTime = max / 1000;
                str = Util.millisecondToTimeString(max, false);
            } else {
                if (CameraSettings.VIDEO_SPEED_FAST.equals(this.mSpeed)) {
                    d2 = (double) this.mTimeBetweenTimeLapseFrameCaptureMs;
                    j = (long) d2;
                } else {
                    j = 1000;
                    d2 = 0.0d;
                }
                if (d2 != 0.0d) {
                    str = Util.millisecondToTimeString(getSpeedRecordVideoLength(uptimeMillis, d2), CameraSettings.VIDEO_SPEED_FAST.equals(this.mSpeed));
                    if (str.equals(this.mRecordingTime)) {
                        j2 = (long) d2;
                    }
                } else {
                    str = Util.millisecondToTimeString(max, false);
                }
                topAlert = this.mTopAlert;
                if (topAlert != null) {
                    topAlert.updateRecordingTime(str);
                }
                this.mRecordingTime = str;
                if (this.mRecordingTimeCountsDown != z) {
                    this.mRecordingTimeCountsDown = z;
                }
                long j3 = 500;
                if (!this.mMediaRecorderRecordingPaused) {
                    j3 = j - (uptimeMillis % j);
                }
                this.mHandler.sendEmptyMessageDelayed(42, j3);
                if (this.mCaptureTimeLapse && this.mTimeLapseDuration > 0 && DataRepository.dataItemFeature().uj() && uptimeMillis > this.mTimeLapseDuration) {
                    stopVideoRecording(false);
                    Log.d(VideoBase.TAG, "updateRecordingTime " + uptimeMillis + " mTimeLapseDuration " + this.mTimeLapseDuration);
                    return;
                }
                return;
            }
            j = j2;
            topAlert = this.mTopAlert;
            if (topAlert != null) {
            }
            this.mRecordingTime = str;
            if (this.mRecordingTimeCountsDown != z) {
            }
            long j32 = 500;
            if (!this.mMediaRecorderRecordingPaused) {
            }
            this.mHandler.sendEmptyMessageDelayed(42, j32);
            if (this.mCaptureTimeLapse) {
            }
        }
    }

    @Override // com.android.camera.module.BaseModule
    public void updateSATZooming(boolean z) {
        if (DataRepository.dataItemFeature().Xi() && HybridZoomingSystem.IS_3_OR_MORE_SAT && this.mCamera2Device != null && DataRepository.dataItemFeature().qm() && isInVideoSAT()) {
            this.mCamera2Device.setSatIsZooming(z);
            resumePreview();
        }
    }

    /* access modifiers changed from: protected */
    public void updateVideoStabilization() {
        if (isDeviceAlive()) {
            if (needDisableEISAndOIS()) {
                this.mCamera2Device.setEnableEIS(false);
                this.mCamera2Device.setEnableOIS(false);
                this.mActivity.getCameraScreenNail().setVideoStabilizationCropped(false);
            } else if (isEisOn()) {
                Log.d(VideoBase.TAG, "videoStabilization: EIS");
                this.mCamera2Device.setEnableOIS(false);
                this.mCamera2Device.setEnableEIS(true);
                if (!this.mCameraCapabilities.isEISPreviewSupported()) {
                    this.mActivity.getCameraScreenNail().setVideoStabilizationCropped(true);
                }
            } else {
                Log.d(VideoBase.TAG, "videoStabilization: OIS");
                this.mCamera2Device.setEnableEIS(false);
                this.mCamera2Device.setEnableOIS(true);
                this.mActivity.getCameraScreenNail().setVideoStabilizationCropped(false);
            }
        }
    }
}
