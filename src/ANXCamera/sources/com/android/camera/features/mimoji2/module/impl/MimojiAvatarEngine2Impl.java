package com.android.camera.features.mimoji2.module.impl;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.Rect;
import android.location.Location;
import android.media.Image;
import android.opengl.GLES20;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Message;
import android.text.TextUtils;
import android.util.Size;
import android.widget.Toast;
import com.android.camera.ActivityBase;
import com.android.camera.CameraSettings;
import com.android.camera.LocationManager;
import com.android.camera.R;
import com.android.camera.Thumbnail;
import com.android.camera.ToastUtils;
import com.android.camera.Util;
import com.android.camera.data.DataRepository;
import com.android.camera.data.data.runing.ComponentRunningShine;
import com.android.camera.effect.FilterInfo;
import com.android.camera.effect.renders.DeviceWatermarkParam;
import com.android.camera.features.mimoji2.bean.MimojiBgInfo;
import com.android.camera.features.mimoji2.bean.MimojiInfo2;
import com.android.camera.features.mimoji2.bean.MimojiTimbreInfo;
import com.android.camera.features.mimoji2.module.MimojiModule;
import com.android.camera.features.mimoji2.module.protocol.MimojiModeProtocol;
import com.android.camera.features.mimoji2.utils.BitmapUtils2;
import com.android.camera.features.mimoji2.widget.helper.AvatarEngineManager2;
import com.android.camera.features.mimoji2.widget.helper.MimojiHelper2;
import com.android.camera.features.mimoji2.widget.helper.MimojiStatusManager2;
import com.android.camera.log.Log;
import com.android.camera.module.BaseModule;
import com.android.camera.module.Module;
import com.android.camera.module.impl.component.FileUtils;
import com.android.camera.protocol.ModeCoordinatorImpl;
import com.android.camera.protocol.ModeProtocol;
import com.android.camera.statistic.CameraStatUtils;
import com.android.camera.statistic.MistatsConstants;
import com.android.camera.ui.V6CameraGLSurfaceView;
import com.android.camera2.Camera2Proxy;
import com.android.camera2.CameraCapabilities;
import com.arcsoft.avatar2.AvatarConfig;
import com.arcsoft.avatar2.AvatarEngine;
import com.arcsoft.avatar2.BackgroundInfo;
import com.arcsoft.avatar2.RecordModule;
import com.arcsoft.avatar2.extrascene.ExtraSceneEngine;
import com.arcsoft.avatar2.recoder.RecordingListener;
import com.arcsoft.avatar2.util.ASVLOFFSCREEN;
import com.arcsoft.avatar2.util.AsvloffscreenUtil;
import com.arcsoft.avatar2.util.LOG;
import com.xiaomi.camera.core.ParallelTaskData;
import com.xiaomi.camera.core.ParallelTaskDataParameter;
import com.xiaomi.camera.core.PictureInfo;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CountDownLatch;

public class MimojiAvatarEngine2Impl implements MimojiModeProtocol.MimojiAvatarEngine2 {
    public static final int DELETE_GIF;
    public static final int DELETE_MIMOJI_ALL;
    public static final int DELETE_MIMOJI_EMOTICON;
    public static final int DELETE_MIMOJI_VIDEO;
    private static final int FLAG_HAS_FACE;
    private static final int FLAG_INIT_FACE;
    private static final int HANDLER_RECORDING_CURRENT_FILE_SIZE;
    private static final int HANDLER_RECORDING_CURRENT_TIME;
    private static final int HANDLER_RECORDING_MAX_DURATION_REACHED;
    private static final int HANDLER_RECORDING_MAX_FILE_SIZE_REACHED;
    private static final int HANDLER_RESOURCE_ERROR_BROKEN;
    private static final long START_OFFSET_MS;
    private static final String TAG;
    private ActivityBase mActivityBase;
    private AvatarEngine mAvatar;
    private String mAvatarConfigPath = "";
    private final Object mAvatarLock = new Object();
    private String mAvatarTemplatePath = "";
    private V6CameraGLSurfaceView mCameraView;
    private RecordModule.MediaResultCallback mCaptureCallback = new RecordModule.MediaResultCallback() {
        /* class com.android.camera.features.mimoji2.module.impl.MimojiAvatarEngine2Impl.AnonymousClass3 */

        public /* synthetic */ void Mc() {
            ToastUtils.showToast(MimojiAvatarEngine2Impl.this.mContext, (int) R.string.mimoji_gif_record_time_short, 80);
        }

        public /* synthetic */ void a(ByteBuffer byteBuffer) {
            MimojiAvatarEngine2Impl.this.CaptureCallback(byteBuffer);
        }

        @Override // com.arcsoft.avatar2.RecordModule.MediaResultCallback
        public void onCaptureResult(ByteBuffer byteBuffer) {
            Log.d(MimojiAvatarEngine2Impl.TAG, "onCapture Result");
            MimojiAvatarEngine2Impl.this.mIsRecording = false;
            MimojiAvatarEngine2Impl.this.mIsRecordStopping = false;
            MimojiAvatarEngine2Impl.this.mCaptureHandler.post(new b(this, byteBuffer));
        }

        @Override // com.arcsoft.avatar2.RecordModule.MediaResultCallback
        public void onVideoResult(boolean z) {
            Log.d(MimojiAvatarEngine2Impl.TAG, "stop video record callback");
            if (MimojiAvatarEngine2Impl.this.mGetThumCountDownLatch != null) {
                MimojiAvatarEngine2Impl.this.mGetThumCountDownLatch.countDown();
            }
            MimojiAvatarEngine2Impl.this.mIsRecording = false;
            MimojiAvatarEngine2Impl.this.mIsRecordStopping = false;
            if (!CameraSettings.isGifOn()) {
                MimojiModeProtocol.MimojiVideoEditor mimojiVideoEditor = (MimojiModeProtocol.MimojiVideoEditor) ModeCoordinatorImpl.getInstance().getAttachProtocol(252);
                if (mimojiVideoEditor != null) {
                    mimojiVideoEditor.combineVideoAudio(MimojiAvatarEngine2Impl.this.getVideoCache(), MimojiAvatarEngine2Impl.this.mStopRecordType);
                }
            } else if (MimojiAvatarEngine2Impl.this.mTotalRecordingTime < 1000) {
                ModeProtocol.MimojiGifEditor mimojiGifEditor = (ModeProtocol.MimojiGifEditor) ModeCoordinatorImpl.getInstance().getAttachProtocol(251);
                if (mimojiGifEditor != null) {
                    mimojiGifEditor.coverGifError();
                }
                MimojiAvatarEngine2Impl.this.mUiHandler.post(new a(this));
            } else {
                ModeProtocol.MimojiGifEditor mimojiGifEditor2 = (ModeProtocol.MimojiGifEditor) ModeCoordinatorImpl.getInstance().getAttachProtocol(251);
                if (mimojiGifEditor2 != null) {
                    mimojiGifEditor2.combineVideoAudio(MimojiAvatarEngine2Impl.this.getVideoCache(), MimojiAvatarEngine2Impl.this.mTotalRecordingTime);
                }
            }
        }
    };
    private Handler mCaptureHandler;
    private HandlerThread mCaptureThread = new HandlerThread("Capture");
    private Context mContext;
    private CountDownTimer mCountDownTimer;
    private int mCurrentScreenOrientation = 0;
    private MimojiBgInfo mCurrentTempMimojiBgInfo;
    private int mDeviceRotation = 90;
    private int mDisplayOrientation;
    private Size mDrawSize;
    private int mFaceDectectResult = 1;
    private CountDownLatch mGetThumCountDownLatch;
    private Bitmap mGifBitmap;
    private Handler mHandler = new Handler(Looper.getMainLooper()) {
        /* class com.android.camera.features.mimoji2.module.impl.MimojiAvatarEngine2Impl.AnonymousClass5 */

        public void handleMessage(Message message) {
            super.handleMessage(message);
            int i = message.what;
            if (i != 0 && i != 1 && i != 2) {
            }
        }
    };
    private volatile boolean mIsAvatarInited;
    private boolean mIsFaceDetectSuccess = false;
    private boolean mIsFrontCamera;
    private boolean mIsGifOn;
    private int mIsNoFaceResult = -1;
    private boolean mIsRecordStopping = false;
    private volatile boolean mIsRecording;
    private boolean mIsShutterButtonClick = false;
    private boolean mIsStopRender = true;
    private boolean mLastInitResult = false;
    private boolean mLastNeedBeauty = true;
    private Handler mLoadHandler;
    private HandlerThread mLoadThread = new HandlerThread("LoadConfig");
    private ModeProtocol.MainContentProtocol mMainProtocol;
    private int mMaxVideoDurationInMs;
    private MimojiModeProtocol.MimojiEditor2 mMimojiEditor2;
    private MimojiStatusManager2 mMimojiStatusManager2;
    private boolean mNeedCapture = false;
    private boolean mNeedThumbnail = false;
    private int mOrientation;
    private int mPreviewHeight;
    private int mPreviewWidth;
    private RecordModule mRecordModule;
    private RecordingListener mRecordingListener = new RecordingListener() {
        /* class com.android.camera.features.mimoji2.module.impl.MimojiAvatarEngine2Impl.AnonymousClass4 */

        @Override // com.arcsoft.avatar2.recoder.RecordingListener
        public void onRecordingListener(int i, Object obj) {
            Message obtainMessage = MimojiAvatarEngine2Impl.this.mHandler.obtainMessage();
            switch (i) {
                case 257:
                    obtainMessage.arg1 = (int) ((((Long) obj).longValue() / 1000) / 1000);
                    obtainMessage.what = 2;
                    break;
                case 258:
                    long longValue = (((Long) obj).longValue() / 1000) / 1000;
                    obtainMessage.arg1 = (int) longValue;
                    Log.d(MimojiAvatarEngine2Impl.TAG, "onRecordingListener_time = " + longValue);
                    obtainMessage.what = 1;
                    break;
                case 259:
                    obtainMessage.arg1 = (int) (((Long) obj).longValue() / 1024);
                    obtainMessage.what = 4;
                    break;
                case 260:
                    obtainMessage.arg1 = (int) (((Long) obj).longValue() / 1024);
                    obtainMessage.what = 3;
                    break;
            }
            obtainMessage.sendToTarget();
        }
    };
    private ExtraSceneEngine.AvatarExtraSceneTriggerCallback mSceneTriggerCallback;
    private int mStopRecordType;
    private int[] mTextureId = new int[1];
    private long mTotalRecordingTime;
    private Handler mUiHandler;
    private final int[] resourceTimbreAlert = {R.string.timbre_normal, R.string.timbre_gentlemen, R.string.timbre_lady, R.string.timbre_girl, R.string.timbre_baby, R.string.timbre_robot};
    private int uiStyle;

    private MimojiAvatarEngine2Impl(ActivityBase activityBase) {
        this.mActivityBase = activityBase;
        this.mCameraView = activityBase.getGLView();
        if (CameraSettings.isGifOn()) {
            this.mMaxVideoDurationInMs = 5000;
        } else {
            this.mMaxVideoDurationInMs = 15000;
        }
        this.mContext = activityBase.getCameraAppImpl().getApplicationContext();
        this.mMainProtocol = (ModeProtocol.MainContentProtocol) ModeCoordinatorImpl.getInstance().getAttachProtocol(166);
        this.mMimojiEditor2 = (MimojiModeProtocol.MimojiEditor2) ModeCoordinatorImpl.getInstance().getAttachProtocol(247);
        this.mLoadThread.start();
        this.mLoadHandler = new Handler(this.mLoadThread.getLooper());
        this.mCaptureThread.start();
        this.mCaptureHandler = new Handler(this.mCaptureThread.getLooper());
        this.mUiHandler = new Handler(activityBase.getMainLooper());
        this.mMimojiStatusManager2 = DataRepository.dataItemLive().getMimojiStatusManager2();
        this.mSceneTriggerCallback = new ExtraSceneEngine.AvatarExtraSceneTriggerCallback() {
            /* class com.android.camera.features.mimoji2.module.impl.MimojiAvatarEngine2Impl.AnonymousClass1 */

            @Override // com.arcsoft.avatar2.extrascene.ExtraSceneEngine.AvatarExtraSceneTriggerCallback
            public void onExtraSceneTrigger(String str, int i) {
                CameraStatUtils.trackMimojiTrigger(str + i);
            }
        };
        setIsAvatarInited(false);
        Log.w(TAG, "MimojiAvatarEngine2Impl:  constructor");
    }

    /* JADX WARNING: Removed duplicated region for block: B:101:0x01d7  */
    /* JADX WARNING: Removed duplicated region for block: B:105:? A[RETURN, SYNTHETIC] */
    /* JADX WARNING: Removed duplicated region for block: B:33:0x0089 A[Catch:{ Exception -> 0x0146, all -> 0x0143 }] */
    /* JADX WARNING: Removed duplicated region for block: B:34:0x008b A[Catch:{ Exception -> 0x0146, all -> 0x0143 }] */
    /* JADX WARNING: Removed duplicated region for block: B:37:0x00a9 A[Catch:{ Exception -> 0x0146, all -> 0x0143 }] */
    /* JADX WARNING: Removed duplicated region for block: B:38:0x00af A[Catch:{ Exception -> 0x0146, all -> 0x0143 }] */
    /* JADX WARNING: Removed duplicated region for block: B:41:0x00c3 A[Catch:{ Exception -> 0x0146, all -> 0x0143 }] */
    /* JADX WARNING: Removed duplicated region for block: B:42:0x00c4 A[Catch:{ Exception -> 0x0146, all -> 0x0143 }] */
    /* JADX WARNING: Removed duplicated region for block: B:45:0x0106 A[Catch:{ Exception -> 0x0146, all -> 0x0143 }] */
    /* JADX WARNING: Removed duplicated region for block: B:46:0x0107 A[Catch:{ Exception -> 0x0146, all -> 0x0143 }] */
    /* JADX WARNING: Removed duplicated region for block: B:49:0x0113 A[Catch:{ Exception -> 0x0146, all -> 0x0143 }] */
    /* JADX WARNING: Removed duplicated region for block: B:50:0x0118 A[Catch:{ Exception -> 0x0146, all -> 0x0143 }] */
    private void CaptureCallback(ByteBuffer byteBuffer) {
        Bitmap bitmap;
        Bitmap bitmap2;
        Throwable th;
        Module currentModule;
        Exception e2;
        Module currentModule2;
        Bitmap bitmap3 = null;
        try {
            if (this.mActivityBase == null || byteBuffer == null || !byteBuffer.hasArray()) {
                bitmap = null;
            } else {
                bitmap2 = Bitmap.createBitmap(this.mDrawSize.getWidth(), this.mDrawSize.getHeight(), Bitmap.Config.ARGB_8888);
                try {
                    bitmap2.copyPixelsFromBuffer(byteBuffer);
                    Matrix matrix = new Matrix();
                    if (this.mIsFrontCamera) {
                        if (this.mIsFrontCamera) {
                            if (this.mDeviceRotation != 90) {
                                if (this.mDeviceRotation == 270) {
                                }
                            }
                        }
                        if (this.mDeviceRotation % 180 == 0) {
                            matrix.postScale(-1.0f, 1.0f);
                        }
                        bitmap = Bitmap.createBitmap(bitmap2, 0, 0, this.mDrawSize.getWidth(), this.mDrawSize.getHeight(), matrix, false);
                        int i = 0;
                        byte[] bitmapData = Util.getBitmapData(bitmap, CameraSettings.getEncodingQuality(false).toInteger(false));
                        Thumbnail createThumbnail = Thumbnail.createThumbnail(null, !this.mIsFrontCamera ? bitmap2 : bitmap, (this.mIsFrontCamera || this.mDeviceRotation % 180 != 0) ? this.mDeviceRotation : (this.mDeviceRotation + 180) % 360, this.mIsFrontCamera);
                        createThumbnail.startWaitingForUri();
                        int i2 = 1;
                        this.mActivityBase.getThumbnailUpdater().setThumbnail(createThumbnail, true, true);
                        MimojiModule mimojiModule = (MimojiModule) this.mActivityBase.getCurrentModule();
                        ParallelTaskData parallelTaskData = new ParallelTaskData(mimojiModule == null ? mimojiModule.getActualCameraId() : 0, System.currentTimeMillis(), -4, null);
                        parallelTaskData.fillJpegData(bitmapData, 0);
                        if (this.mIsFrontCamera) {
                            i2 = 0;
                        }
                        ParallelTaskDataParameter.Builder builder = new ParallelTaskDataParameter.Builder(this.mDrawSize, this.mDrawSize, this.mDrawSize, 256);
                        Location currentLocation = LocationManager.instance().getCurrentLocation();
                        ParallelTaskDataParameter.Builder filterId = builder.setHasDualWaterMark(CameraSettings.isDualCameraWaterMarkOpen()).setJpegRotation((Util.getJpegRotation(i2, this.mDeviceRotation) + 270) % 360).setJpegQuality(CameraSettings.getEncodingQuality(false).toInteger(false)).setFilterId(FilterInfo.FILTER_ID_NONE);
                        if (-1 == this.mOrientation) {
                            i = this.mOrientation;
                        }
                        parallelTaskData.fillParameter(filterId.setOrientation(i).setTimeWaterMarkString(!CameraSettings.isTimeWaterMarkOpen() ? Util.getTimeWatermark() : null).setDeviceWatermarkParam(getDeviceWaterMarkParam()).setPictureInfo(getPictureInfo()).setLocation(currentLocation).build());
                        this.mActivityBase.getImageSaver().onParallelProcessFinish(parallelTaskData, null, null);
                        bitmap3 = bitmap2;
                    }
                    matrix.postScale(1.0f, -1.0f);
                    bitmap = Bitmap.createBitmap(bitmap2, 0, 0, this.mDrawSize.getWidth(), this.mDrawSize.getHeight(), matrix, false);
                    int i3 = 0;
                    try {
                        byte[] bitmapData2 = Util.getBitmapData(bitmap, CameraSettings.getEncodingQuality(false).toInteger(false));
                        Thumbnail createThumbnail2 = Thumbnail.createThumbnail(null, !this.mIsFrontCamera ? bitmap2 : bitmap, (this.mIsFrontCamera || this.mDeviceRotation % 180 != 0) ? this.mDeviceRotation : (this.mDeviceRotation + 180) % 360, this.mIsFrontCamera);
                        createThumbnail2.startWaitingForUri();
                        int i22 = 1;
                        this.mActivityBase.getThumbnailUpdater().setThumbnail(createThumbnail2, true, true);
                        MimojiModule mimojiModule2 = (MimojiModule) this.mActivityBase.getCurrentModule();
                        ParallelTaskData parallelTaskData2 = new ParallelTaskData(mimojiModule2 == null ? mimojiModule2.getActualCameraId() : 0, System.currentTimeMillis(), -4, null);
                        parallelTaskData2.fillJpegData(bitmapData2, 0);
                        if (this.mIsFrontCamera) {
                        }
                        ParallelTaskDataParameter.Builder builder2 = new ParallelTaskDataParameter.Builder(this.mDrawSize, this.mDrawSize, this.mDrawSize, 256);
                        Location currentLocation2 = LocationManager.instance().getCurrentLocation();
                        ParallelTaskDataParameter.Builder filterId2 = builder2.setHasDualWaterMark(CameraSettings.isDualCameraWaterMarkOpen()).setJpegRotation((Util.getJpegRotation(i22, this.mDeviceRotation) + 270) % 360).setJpegQuality(CameraSettings.getEncodingQuality(false).toInteger(false)).setFilterId(FilterInfo.FILTER_ID_NONE);
                        if (-1 == this.mOrientation) {
                        }
                        parallelTaskData2.fillParameter(filterId2.setOrientation(i3).setTimeWaterMarkString(!CameraSettings.isTimeWaterMarkOpen() ? Util.getTimeWatermark() : null).setDeviceWatermarkParam(getDeviceWaterMarkParam()).setPictureInfo(getPictureInfo()).setLocation(currentLocation2).build());
                        this.mActivityBase.getImageSaver().onParallelProcessFinish(parallelTaskData2, null, null);
                        bitmap3 = bitmap2;
                    } catch (Exception e3) {
                        e2 = e3;
                        bitmap3 = bitmap2;
                        try {
                            Log.e(TAG, "mimoji void CaptureCallback[byteBuffer] exception " + e2);
                            if (bitmap3 != null && !bitmap3.isRecycled()) {
                                bitmap3.recycle();
                            }
                            if (bitmap != null && !bitmap.isRecycled()) {
                                bitmap.recycle();
                            }
                            currentModule2 = this.mActivityBase.getCurrentModule();
                            if (!(currentModule2 instanceof MimojiModule)) {
                                return;
                            }
                            ((MimojiModule) currentModule2).onMimojiCaptureCallback();
                        } catch (Throwable th2) {
                            th = th2;
                            bitmap2 = bitmap3;
                            if (bitmap2 != null && !bitmap2.isRecycled()) {
                                bitmap2.recycle();
                            }
                            if (bitmap != null && !bitmap.isRecycled()) {
                                bitmap.recycle();
                            }
                            currentModule = this.mActivityBase.getCurrentModule();
                            if (currentModule instanceof MimojiModule) {
                                ((MimojiModule) currentModule).onMimojiCaptureCallback();
                            }
                            throw th;
                        }
                    } catch (Throwable th3) {
                        th = th3;
                        bitmap2.recycle();
                        bitmap.recycle();
                        currentModule = this.mActivityBase.getCurrentModule();
                        if (currentModule instanceof MimojiModule) {
                        }
                        throw th;
                    }
                } catch (Exception e4) {
                    e2 = e4;
                    bitmap = null;
                    bitmap3 = bitmap2;
                    Log.e(TAG, "mimoji void CaptureCallback[byteBuffer] exception " + e2);
                    bitmap3.recycle();
                    bitmap.recycle();
                    currentModule2 = this.mActivityBase.getCurrentModule();
                    if (!(currentModule2 instanceof MimojiModule)) {
                    }
                    ((MimojiModule) currentModule2).onMimojiCaptureCallback();
                } catch (Throwable th4) {
                    th = th4;
                    bitmap = null;
                    bitmap2.recycle();
                    bitmap.recycle();
                    currentModule = this.mActivityBase.getCurrentModule();
                    if (currentModule instanceof MimojiModule) {
                    }
                    throw th;
                }
            }
            if (bitmap3 != null && !bitmap3.isRecycled()) {
                bitmap3.recycle();
            }
            if (bitmap != null && !bitmap.isRecycled()) {
                bitmap.recycle();
            }
            currentModule2 = this.mActivityBase.getCurrentModule();
            if (!(currentModule2 instanceof MimojiModule)) {
                return;
            }
        } catch (Exception e5) {
            e2 = e5;
            bitmap = null;
            Log.e(TAG, "mimoji void CaptureCallback[byteBuffer] exception " + e2);
            bitmap3.recycle();
            bitmap.recycle();
            currentModule2 = this.mActivityBase.getCurrentModule();
            if (!(currentModule2 instanceof MimojiModule)) {
            }
            ((MimojiModule) currentModule2).onMimojiCaptureCallback();
        } catch (Throwable th5) {
            th = th5;
            bitmap2 = null;
            bitmap = null;
            bitmap2.recycle();
            bitmap.recycle();
            currentModule = this.mActivityBase.getCurrentModule();
            if (currentModule instanceof MimojiModule) {
            }
            throw th;
        }
        ((MimojiModule) currentModule2).onMimojiCaptureCallback();
    }

    static /* synthetic */ void a(MimojiModeProtocol.MimojiBottomList mimojiBottomList, boolean z) {
        mimojiBottomList.firstProgressShow(z);
        mimojiBottomList.refreshMimojiList();
    }

    private void animateCapture() {
        if (CameraSettings.isCameraSoundOpen()) {
            this.mActivityBase.playCameraSound(0);
        }
    }

    public static MimojiAvatarEngine2Impl create(ActivityBase activityBase) {
        return new MimojiAvatarEngine2Impl(activityBase);
    }

    private void createAvatar(byte[] bArr, int i, int i2) {
        int avatarProfile;
        String str = this.mAvatarTemplatePath;
        String str2 = AvatarEngineManager2.TEMPLATE_PATH_HUMAN;
        if (str != str2) {
            this.mAvatarTemplatePath = str2;
            this.mAvatarConfigPath = "";
            this.mAvatar.setTemplatePath(str2);
        }
        AvatarConfig.ASAvatarProfileResult aSAvatarProfileResult = new AvatarConfig.ASAvatarProfileResult();
        synchronized (this.mAvatarLock) {
            avatarProfile = this.mAvatar.avatarProfile(AvatarEngineManager2.TEMPLATE_PATH_HUMAN, i, i2, i * 4, bArr, 0, false, aSAvatarProfileResult, null, p.INSTANCE);
        }
        LOG.d(TAG, "avatarProfile res: " + avatarProfile + ", status:" + aSAvatarProfileResult.status + ", gender: " + aSAvatarProfileResult.gender);
        int i3 = aSAvatarProfileResult.status;
        if (i3 == 254 || i3 == 246) {
            Log.d(TAG, "result = " + avatarProfile);
            this.mUiHandler.post(new m(this));
            return;
        }
        if (i3 == 1) {
            Toast.makeText(this.mContext, (int) R.string.mimoji_detect_no_face_failed, 0).show();
        } else if ((i3 & 2) == 0) {
            Toast.makeText(this.mContext, (int) R.string.mimoji_detect_facial_failed, 0).show();
        } else if ((i3 & 4) == 0) {
            Toast.makeText(this.mContext, (int) R.string.mimoji_detect_hairstyle_failed, 0).show();
        } else if ((i3 & 8) == 0) {
            Toast.makeText(this.mContext, (int) R.string.mimoji_detect_haircolor_failed, 0).show();
        } else if ((i3 & 16) == 0) {
            Toast.makeText(this.mContext, (int) R.string.mimoji_detect_gender_failed, 0).show();
        } else if ((i3 & 32) == 0) {
            Toast.makeText(this.mContext, (int) R.string.mimoji_detect_skincolor_failed, 0).show();
        } else if ((i3 & 64) == 0) {
            Toast.makeText(this.mContext, (int) R.string.mimoji_detect_glass_failed, 0).show();
        } else if ((i3 & 128) == 0) {
            Toast.makeText(this.mContext, (int) R.string.mimoji_detect_faceshape_failed, 0).show();
        } else {
            Toast.makeText(this.mContext, (int) R.string.mimoji_detect_unknow_failed, 0).show();
        }
        ActivityBase activityBase = this.mActivityBase;
        if (activityBase != null) {
            activityBase.runOnUiThread(new n(this));
        }
    }

    private void destoryAll() {
        this.mCaptureHandler.post(new d(this, hashCode()));
    }

    private DeviceWatermarkParam getDeviceWaterMarkParam() {
        float f;
        float f2;
        float f3;
        float resourceFloat;
        float resourceFloat2;
        float resourceFloat3;
        boolean isDualCameraWaterMarkOpen = CameraSettings.isDualCameraWaterMarkOpen();
        boolean isFrontCameraWaterMarkOpen = CameraSettings.isFrontCameraWaterMarkOpen();
        if (isDualCameraWaterMarkOpen || isFrontCameraWaterMarkOpen) {
            isDualCameraWaterMarkOpen = false;
            isFrontCameraWaterMarkOpen = true;
        }
        if (isDualCameraWaterMarkOpen) {
            resourceFloat = CameraSettings.getResourceFloat(R.dimen.dualcamera_watermark_size_ratio, 0.0f);
            resourceFloat2 = CameraSettings.getResourceFloat(R.dimen.dualcamera_watermark_padding_x_ratio, 0.0f);
            resourceFloat3 = CameraSettings.getResourceFloat(R.dimen.dualcamera_watermark_padding_y_ratio, 0.0f);
        } else if (isFrontCameraWaterMarkOpen) {
            resourceFloat = CameraSettings.getResourceFloat(R.dimen.frontcamera_watermark_size_ratio, 0.0f);
            resourceFloat2 = CameraSettings.getResourceFloat(R.dimen.frontcamera_watermark_padding_x_ratio, 0.0f);
            resourceFloat3 = CameraSettings.getResourceFloat(R.dimen.frontcamera_watermark_padding_y_ratio, 0.0f);
        } else {
            f3 = 0.0f;
            f2 = 0.0f;
            f = 0.0f;
            return new DeviceWatermarkParam(isDualCameraWaterMarkOpen, isFrontCameraWaterMarkOpen, CameraSettings.isUltraPixelRearOn(), CameraSettings.getDualCameraWaterMarkFilePathVendor(), f3, f2, f);
        }
        f = resourceFloat3;
        f3 = resourceFloat;
        f2 = resourceFloat2;
        return new DeviceWatermarkParam(isDualCameraWaterMarkOpen, isFrontCameraWaterMarkOpen, CameraSettings.isUltraPixelRearOn(), CameraSettings.getDualCameraWaterMarkFilePathVendor(), f3, f2, f);
    }

    private Map<String, String> getExtraMimojiPara() {
        String str;
        MimojiInfo2 currentMimojiInfo = this.mMimojiStatusManager2.getCurrentMimojiInfo();
        HashMap hashMap = new HashMap();
        if (!TextUtils.isEmpty(this.mAvatarTemplatePath)) {
            String str2 = this.mAvatarTemplatePath.equals(AvatarEngineManager2.TEMPLATE_PATH_HUMAN) ? "person" : this.mAvatarTemplatePath.equals(AvatarEngineManager2.TEMPLATE_PATH_BEAR) ? "bear" : this.mAvatarTemplatePath.equals(AvatarEngineManager2.TEMPLATE_PATH_ROYAN) ? "royan" : this.mAvatarTemplatePath.equals(AvatarEngineManager2.TEMPLATE_PATH_RABBIT) ? "rabbit" : this.mAvatarTemplatePath.equals(AvatarEngineManager2.TEMPLATE_PATH_RABBIT2) ? AvatarEngineManager2.CONFIG_PATH_FAKE_RABBIT2 : this.mAvatarTemplatePath.equals(AvatarEngineManager2.TEMPLATE_PATH_FROG) ? AvatarEngineManager2.CONFIG_PATH_FAKE_FROG : this.mAvatarTemplatePath.equals(AvatarEngineManager2.TEMPLATE_PATH_CAT) ? "cat" : "";
            if (currentMimojiInfo != null && currentMimojiInfo.getFrame() > 0) {
                str2 = str2 + "_hat";
            }
            hashMap.put(MistatsConstants.Mimoji.MIMOJI_SAVE_CARTOON, str2);
        }
        AvatarEngine avatarEngine = this.mAvatar;
        String extraSceneName = avatarEngine == null ? null : avatarEngine.getExtraSceneName();
        if (this.mAvatar != null) {
            AvatarConfig.ASAvatarConfigValue aSAvatarConfigValue = new AvatarConfig.ASAvatarConfigValue();
            this.mAvatar.getConfigValue(aSAvatarConfigValue);
            if (AvatarEngineManager2.isExtraResource(aSAvatarConfigValue.configHeadwearStyleID)) {
                hashMap.put(MistatsConstants.Mimoji.MIMOJI_SAVE_EXTRA_SCENE, extraSceneName + " " + aSAvatarConfigValue.configHeadwearStyleID);
            }
            if (currentMimojiInfo != null && currentMimojiInfo.isIsPreHuman()) {
                hashMap.put(MistatsConstants.Mimoji.MIMOJI_SAVE_EXTRA_SCENE, extraSceneName + " " + currentMimojiInfo.mConfigPath);
            }
        }
        MimojiTimbreInfo currentMimojiTimbreInfo = this.mMimojiStatusManager2.getCurrentMimojiTimbreInfo();
        String str3 = "null";
        if (currentMimojiTimbreInfo == null) {
            str = str3;
        } else {
            str = currentMimojiTimbreInfo.getTimbreId() + "";
        }
        hashMap.put(MistatsConstants.Mimoji.MIMOJI_SAVE_CHANGE_TIMBRE, str);
        MimojiBgInfo currentMimojiBgInfo = this.mMimojiStatusManager2.getCurrentMimojiBgInfo();
        if (currentMimojiBgInfo != null) {
            str3 = currentMimojiBgInfo.getBgId() + "";
        }
        hashMap.put(MistatsConstants.Mimoji.MIMOJI_SAVE_CHANGE_BG, str3);
        return hashMap;
    }

    private Map<String, String> getMimojiPara() {
        String str;
        String str2;
        Map<String, String> hashMap = new HashMap<>();
        String str3 = "null";
        if (isNeedShowAvatar()) {
            AvatarConfig.ASAvatarConfigValue aSAvatarConfigValue = new AvatarConfig.ASAvatarConfigValue();
            this.mAvatar.getConfigValue(aSAvatarConfigValue);
            hashMap = AvatarEngineManager2.getMimojiConfigValue(aSAvatarConfigValue);
            hashMap.put(MistatsConstants.Mimoji.MIMOJI_CATEGORY, this.mAvatarTemplatePath.equals(AvatarEngineManager2.TEMPLATE_PATH_HUMAN) ? "custom" : this.mAvatarTemplatePath.equals(AvatarEngineManager2.TEMPLATE_PATH_BEAR) ? "bear" : this.mAvatarTemplatePath.equals(AvatarEngineManager2.TEMPLATE_PATH_ROYAN) ? "royan" : this.mAvatarTemplatePath.equals(AvatarEngineManager2.TEMPLATE_PATH_RABBIT) ? "rabbit" : "");
        } else {
            hashMap.put(MistatsConstants.Mimoji.MIMOJI_CATEGORY, str3);
        }
        MimojiInfo2 currentMimojiInfo = this.mMimojiStatusManager2.getCurrentMimojiInfo();
        AvatarEngine avatarEngine = this.mAvatar;
        String extraSceneName = avatarEngine == null ? null : avatarEngine.getExtraSceneName();
        if (!TextUtils.isEmpty(extraSceneName)) {
            hashMap.put(MistatsConstants.Mimoji.MIMOJI_SAVE_EXTRA_SCENE, extraSceneName);
        }
        if (!(currentMimojiInfo == null || (str2 = currentMimojiInfo.mAvatarTemplatePath) == null || !AvatarEngineManager2.isPreCartoonModel(str2))) {
            hashMap.put(MistatsConstants.Mimoji.MIMOJI_SAVE_CARTOON, currentMimojiInfo.mAvatarTemplatePath);
        }
        MimojiTimbreInfo currentMimojiTimbreInfo = this.mMimojiStatusManager2.getCurrentMimojiTimbreInfo();
        if (currentMimojiTimbreInfo == null) {
            str = str3;
        } else {
            str = currentMimojiTimbreInfo.getTimbreId() + "";
        }
        hashMap.put(MistatsConstants.Mimoji.MIMOJI_SAVE_CHANGE_TIMBRE, str);
        MimojiBgInfo currentMimojiBgInfo = this.mMimojiStatusManager2.getCurrentMimojiBgInfo();
        if (currentMimojiBgInfo != null) {
            str3 = currentMimojiBgInfo.getBackgroundInfo().getName() + "";
        }
        hashMap.put(MistatsConstants.Mimoji.MIMOJI_SAVE_CHANGE_BG, str3);
        return hashMap;
    }

    private PictureInfo getPictureInfo() {
        PictureInfo opMode = new PictureInfo().setFrontMirror(isFrontMirror()).setSensorType(true).setBokehFrontCamera(false).setHdrType("off").setOpMode(getOperatingMode());
        opMode.end();
        return opMode;
    }

    private void initMimojiDataResource() {
        if (!FileUtils.checkFileDirectoryConsist(MimojiHelper2.DATA_DIR)) {
            Log.w(TAG, "MimojiAvatarEngine2Impl: initMimojiResource unzip...");
            long currentTimeMillis = System.currentTimeMillis();
            try {
                FileUtils.delDir(MimojiHelper2.DATA_DIR);
                FileUtils.makeNoMediaDir(MimojiHelper2.DATA_DIR);
                Util.verifyFileZip(this.mContext, "vendor/camera/mimoji/data.zip", MimojiHelper2.MIMOJI_DIR, 32768);
                Log.d(TAG, "init model spend time = " + (System.currentTimeMillis() - currentTimeMillis));
            } catch (Exception e2) {
                Log.e(TAG, "verify asset model zip failed...", e2);
                CameraSettings.setMimojiModleVersion(null);
                FileUtils.delDir(MimojiHelper2.DATA_DIR);
            }
        }
    }

    private boolean isFrontMirror() {
        if (!this.mIsFrontCamera) {
            return false;
        }
        if (CameraSettings.isLiveShotOn()) {
            return true;
        }
        return CameraSettings.isFrontMirror();
    }

    private void onProfileFinish() {
        Log.d(TAG, "onProfileFinish");
        ModeProtocol.RecordState recordState = (ModeProtocol.RecordState) ModeCoordinatorImpl.getInstance().getAttachProtocol(212);
        if (recordState != null) {
            recordState.onPostSavingFinish();
        }
        ModeProtocol.TopAlert topAlert = (ModeProtocol.TopAlert) ModeCoordinatorImpl.getInstance().getAttachProtocol(172);
        if (topAlert != null) {
            topAlert.alertMimojiFaceDetect(false, -1);
        }
        this.mMimojiEditor2 = (MimojiModeProtocol.MimojiEditor2) ModeCoordinatorImpl.getInstance().getAttachProtocol(247);
        releaseRender();
        this.mMainProtocol.mimojiEnd();
        this.mMimojiStatusManager2.setMode(6);
        MimojiModeProtocol.MimojiEditor2 mimojiEditor2 = this.mMimojiEditor2;
        if (mimojiEditor2 != null) {
            mimojiEditor2.startMimojiEdit(203);
        }
        ActivityBase activityBase = this.mActivityBase;
        if (activityBase != null) {
            ((MimojiModule) activityBase.getCurrentModule()).onMimojiCreateCompleted(true);
        }
        CameraStatUtils.trackMimojiClick(MistatsConstants.Mimoji.MIMOJI_CLICK_CREATE_CAPTURE, MistatsConstants.BaseEvent.CREATE);
    }

    static /* synthetic */ void s(int i) {
    }

    private void showLoadProgress(boolean z) {
        MimojiModeProtocol.MimojiBottomList mimojiBottomList = (MimojiModeProtocol.MimojiBottomList) ModeCoordinatorImpl.getInstance().getAttachProtocol(248);
        if (mimojiBottomList != null) {
            this.mUiHandler.post(new l(mimojiBottomList, z));
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:26:0x004d  */
    /* JADX WARNING: Removed duplicated region for block: B:28:? A[RETURN, SYNTHETIC] */
    private void updateBeauty(boolean z) {
        boolean z2;
        ActivityBase activityBase = this.mActivityBase;
        if (activityBase != null) {
            BaseModule baseModule = (BaseModule) activityBase.getCurrentModule();
            if (baseModule instanceof MimojiModule) {
                int i = z ? 3 : 0;
                int i2 = z ? 40 : 0;
                ComponentRunningShine componentRunningShine = DataRepository.dataItemRunning().getComponentRunningShine();
                if (componentRunningShine.supportBeautyLevel()) {
                    if (CameraSettings.getFaceBeautyRatio("pref_beautify_level_key_capture") != i) {
                        CameraSettings.setFaceBeautyLevel(i);
                    }
                    z2 = false;
                    if (z2) {
                        baseModule.updatePreferenceInWorkThread(13);
                        return;
                    }
                    return;
                }
                if (componentRunningShine.supportSmoothLevel() && CameraSettings.getFaceBeautyRatio("pref_beautify_skin_smooth_ratio_key") != i2) {
                    CameraSettings.setFaceBeautySmoothLevel(i2);
                }
                z2 = false;
                if (z2) {
                }
                z2 = true;
                if (z2) {
                }
            }
        }
    }

    private void updateVideoOrientation(int i) {
        if ((i > 315 && i <= 360) || (i >= 0 && i <= 45)) {
            this.mCurrentScreenOrientation = 0;
        } else if (i > 45 && i <= 135) {
            this.mCurrentScreenOrientation = 90;
        } else if (i > 135 && i <= 225) {
            this.mCurrentScreenOrientation = 180;
        } else if (i > 225 && i <= 315) {
            this.mCurrentScreenOrientation = 270;
        }
    }

    public /* synthetic */ void Nc() {
        setDisableSingleTapUp(true);
        onProfileFinish();
    }

    public /* synthetic */ void Oc() {
        ModeProtocol.RecordState recordState = (ModeProtocol.RecordState) ModeCoordinatorImpl.getInstance().getAttachProtocol(212);
        if (recordState != null) {
            recordState.onPostSavingFinish();
        }
        ModeProtocol.TopAlert topAlert = (ModeProtocol.TopAlert) ModeCoordinatorImpl.getInstance().getAttachProtocol(172);
        if (topAlert != null) {
            topAlert.alertMimojiFaceDetect(false, -1);
        }
        this.mMainProtocol.mimojiEnd();
        ((MimojiModule) this.mActivityBase.getCurrentModule()).onMimojiCreateCompleted(false);
        ((ModeProtocol.ActionProcessing) ModeCoordinatorImpl.getInstance().getAttachProtocol(162)).showOrHideMimojiProgress(false);
        backToPreview(false, false);
    }

    public /* synthetic */ void Pc() {
        if (!isAvatarInited()) {
            Log.d(TAG, "avatar need really init");
            int formatFileSize = (int) FileUtils.formatFileSize(FileUtils.getFileSize(MimojiHelper2.MODEL_PATH), 3);
            String Ah = DataRepository.dataItemFeature().Ah();
            if (Ah.equals(CameraSettings.getMimojiModleVersion()) && FileUtils.checkFileDirectoryConsist(MimojiHelper2.DATA_DIR) && this.mMimojiStatusManager2.IsLoading()) {
                showLoadProgress(true);
            } else if ((!Ah.equals(CameraSettings.getMimojiModleVersion()) || formatFileSize < 200) && this.mMimojiStatusManager2.isInMimojiPreview()) {
                initMimojiResource();
            } else {
                reloadConfig();
                onMimojiInitFinish();
            }
        }
    }

    public /* synthetic */ void Qc() {
        ModeProtocol.TopAlert topAlert = (ModeProtocol.TopAlert) ModeCoordinatorImpl.getInstance().getAttachProtocol(172);
        if (topAlert != null) {
            topAlert.alertMimojiFaceDetect(getIsNoFaceResult() && isNeedShowAvatar(), R.string.mimoji_check_no_face);
        }
    }

    public /* synthetic */ void Rc() {
        ModeProtocol.TopAlert topAlert = (ModeProtocol.TopAlert) ModeCoordinatorImpl.getInstance().getAttachProtocol(172);
        if (topAlert != null) {
            topAlert.alertMimojiFaceDetect(getIsNoFaceResult() && isNeedShowAvatar(), R.string.mimoji_check_no_face);
        }
    }

    public /* synthetic */ void Tc() {
        if (this.mRecordModule != null) {
            String str = CameraSettings.getVideoEncoder() == 5 ? "video/hevc" : "video/avc";
            if (!CameraSettings.isGifOn()) {
                deleteMimojiCache(1);
                FileUtils.makeNoMediaDir(MimojiHelper2.VIDEO_CACHE_DIR);
                this.mRecordModule.startRecording(getVideoCache(), this.mRecordingListener, this.mCurrentScreenOrientation, this.mPreviewWidth, this.mPreviewHeight, 10000000, str);
            } else {
                deleteMimojiCache(3);
                FileUtils.makeNoMediaDir(MimojiHelper2.GIF_CACHE_DIR);
                this.mRecordModule.startRecording(getVideoCache(), this.mRecordingListener, this.mCurrentScreenOrientation, 500, 500, 10000000, str);
            }
            this.mUiHandler.post(new c(this));
        }
    }

    public /* synthetic */ void Uc() {
        RecordModule recordModule = this.mRecordModule;
        if (recordModule != null) {
            recordModule.releaseRecordGLInfo();
        }
    }

    public /* synthetic */ void Vc() {
        this.mRecordModule.stopRecording();
        this.mCameraView.queueEvent(new o(this));
    }

    public /* synthetic */ void Wc() {
        if (this.mAvatar != null) {
            Log.d(TAG, "release releaseRender | ");
            this.mAvatar.releaseRender();
        }
        RecordModule recordModule = this.mRecordModule;
        if (recordModule != null) {
            recordModule.releaseRecordGLInfo();
        }
        destoryAll();
    }

    public /* synthetic */ void a(ByteBuffer byteBuffer, int i, int i2) {
        Bitmap bitmap;
        byte[] array = byteBuffer.array();
        Bitmap createBitmap = Bitmap.createBitmap(i, i2, Bitmap.Config.ARGB_8888);
        createBitmap.copyPixelsFromBuffer(ByteBuffer.wrap(array));
        if (this.mIsGifOn) {
            int i3 = this.mCurrentScreenOrientation;
            if (i3 == 0 || this.mDeviceRotation == 180) {
                bitmap = Util.rotateAndMirror(createBitmap, 180, true);
            } else {
                bitmap = Util.rotateAndMirror(createBitmap, i3 == 270 ? 90 : -90, true);
            }
            this.mGifBitmap = bitmap;
            return;
        }
        Bitmap rotateAndMirror = Util.rotateAndMirror(createBitmap, 180, true);
        MimojiModeProtocol.MimojiFullScreenProtocol mimojiFullScreenProtocol = (MimojiModeProtocol.MimojiFullScreenProtocol) ModeCoordinatorImpl.getInstance().getAttachProtocol(249);
        if (mimojiFullScreenProtocol != null) {
            mimojiFullScreenProtocol.setPreviewCover(rotateAndMirror);
        }
    }

    @Override // com.android.camera.features.mimoji2.module.protocol.MimojiModeProtocol.MimojiAvatarEngine2
    public void backToPreview(boolean z, boolean z2) {
        MimojiInfo2 currentMimojiInfo = this.mMimojiStatusManager2.getCurrentMimojiInfo();
        this.mMimojiStatusManager2.setMode(2);
        this.mIsStopRender = false;
        onMimojiSelect(currentMimojiInfo, true);
        ModeProtocol.TopAlert topAlert = (ModeProtocol.TopAlert) ModeCoordinatorImpl.getInstance().getAttachProtocol(172);
        ModeProtocol.BottomPopupTips bottomPopupTips = (ModeProtocol.BottomPopupTips) ModeCoordinatorImpl.getInstance().getAttachProtocol(175);
        bottomPopupTips.reInitTipImage();
        topAlert.alertAiDetectTipHint(8, R.string.mimoji_check_normal, -1);
        topAlert.alertMimojiFaceDetect(false, -1);
        if (this.mMimojiStatusManager2.getMimojiRecordState() != 1 || !CameraSettings.isFrontCamera()) {
            topAlert.enableMenuItem(true, 197, 193, 162);
        } else {
            topAlert.enableMenuItem(true, 197, 162);
        }
        topAlert.reInitAlert(true);
        topAlert.updateConfigItem(193);
        if (!z2 || this.mMimojiStatusManager2.getMimojiPanelState() == 1) {
            bottomPopupTips.updateMimojiBottomTipImage();
        } else {
            bottomPopupTips.showMimojiPanel(1);
        }
        AvatarEngine avatarEngine = this.mAvatar;
        if (avatarEngine != null) {
            avatarEngine.setRenderScene(true, 1.0f);
        }
        setDisableSingleTapUp(false);
    }

    public boolean changeIsNoFaceResult(boolean z) {
        if (!z) {
            int i = this.mIsNoFaceResult;
            if (i > 10) {
                return false;
            }
            this.mIsNoFaceResult = i + 1;
            if (this.mIsNoFaceResult == 10) {
                return true;
            }
        } else if (this.mIsNoFaceResult != 0) {
            this.mIsNoFaceResult = 0;
            return true;
        }
        return false;
    }

    @Override // com.android.camera.features.mimoji2.module.protocol.MimojiModeProtocol.MimojiAvatarEngine2
    public void changeToGif(boolean z) {
        setIsAvatarInited(false);
        if (z) {
            CameraStatUtils.trackMimoji2Click(null, MistatsConstants.Mimoji.GIF);
            MimojiStatusManager2 mimojiStatusManager2 = this.mMimojiStatusManager2;
            mimojiStatusManager2.setMimojiRecordStateFromGif(mimojiStatusManager2.getMimojiRecordState());
            return;
        }
        MimojiStatusManager2 mimojiStatusManager22 = this.mMimojiStatusManager2;
        mimojiStatusManager22.setMimojiRecordState(mimojiStatusManager22.getMimojiRecordStateFromGif());
    }

    public void checkIsNeedChangBg() {
        if (!this.mMimojiStatusManager2.IsLoading()) {
            MimojiBgInfo currentMimojiBgInfo = this.mMimojiStatusManager2.getCurrentMimojiBgInfo();
            if (currentMimojiBgInfo != null && !this.mMimojiStatusManager2.isInMimojiCreate() && CameraSettings.isFrontCamera() && !CameraSettings.isGifOn()) {
                MimojiBgInfo mimojiBgInfo = this.mCurrentTempMimojiBgInfo;
                if (mimojiBgInfo == null || !mimojiBgInfo.getBackgroundInfo().getName().equals(currentMimojiBgInfo.getBackgroundInfo().getName())) {
                    this.mCurrentTempMimojiBgInfo = currentMimojiBgInfo.clone();
                    this.mAvatar.setRenderScene(false, 1.0f);
                }
                if (this.mCurrentTempMimojiBgInfo.getIsNeedRefresh()) {
                    this.mRecordModule.setBackgroundToSquare(false);
                    int i = this.uiStyle;
                    if (i == 0) {
                        this.mCurrentTempMimojiBgInfo.getBackgroundInfo().setResolutionMode(1);
                    } else if (i != 1) {
                        this.mCurrentTempMimojiBgInfo.getBackgroundInfo().setResolutionMode(3);
                    } else {
                        this.mCurrentTempMimojiBgInfo.getBackgroundInfo().setResolutionMode(2);
                    }
                    try {
                        Bitmap decodeStream = BitmapFactory.decodeStream(new FileInputStream(this.mCurrentTempMimojiBgInfo.getBackgroundInfo().getBackGroundPath(this.mCurrentTempMimojiBgInfo.nextFrame())));
                        this.mRecordModule.setBackground(decodeStream, this.mCurrentTempMimojiBgInfo.getBackgroundInfo());
                        decodeStream.recycle();
                    } catch (FileNotFoundException e2) {
                        this.mCurrentTempMimojiBgInfo = null;
                        Log.e(TAG, "checkIsNeedChangBg  : " + e2.getMessage());
                    }
                }
            } else if (this.mCurrentTempMimojiBgInfo != null) {
                this.mCurrentTempMimojiBgInfo = null;
                this.mRecordModule.setBackground(null, null);
                this.mAvatar.setRenderScene(true, 1.0f);
            }
        }
    }

    @Override // com.android.camera.features.mimoji2.module.protocol.MimojiModeProtocol.MimojiAvatarEngine2
    public boolean deleteMimojiCache(int i) {
        Log.d(TAG, "mimoji boolean deleteMimojiCache[type] : " + i);
        if (i == 0) {
            FileUtils.deleteFile(MimojiHelper2.VIDEO_CACHE_DIR);
            FileUtils.deleteFile(MimojiHelper2.GIF_CACHE_DIR);
            FileUtils.deleteFile(MimojiHelper2.EMOTICON_CACHE_DIR);
        } else if (i == 1) {
            FileUtils.deleteFile(MimojiHelper2.VIDEO_CACHE_DIR);
        } else if (i == 2) {
            FileUtils.deleteFile(MimojiHelper2.EMOTICON_CACHE_DIR);
        } else if (i == 3) {
            try {
                FileUtils.deleteFile(MimojiHelper2.GIF_CACHE_DIR);
            } catch (Exception e2) {
                Log.e(TAG, "mimoji void deleteMimojiCache[] " + e2.getMessage());
                return false;
            }
        }
        return true;
    }

    @Override // com.android.camera.features.mimoji2.module.protocol.MimojiModeProtocol.MimojiAvatarEngine2
    public Bitmap getGifBitmap() {
        Bitmap bitmap = this.mGifBitmap;
        if (bitmap == null || bitmap.isRecycled()) {
            return null;
        }
        return this.mGifBitmap;
    }

    public boolean getIsNoFaceResult() {
        return this.mIsNoFaceResult < 10;
    }

    public int getOperatingMode() {
        return CameraCapabilities.SESSION_OPERATION_MODE_MIMOJI;
    }

    @Override // com.android.camera.features.mimoji2.module.protocol.MimojiModeProtocol.MimojiAvatarEngine2
    public String getTimeValue() {
        return Util.millisecondToTimeString(Util.clamp(this.mTotalRecordingTime, 1000, 15000), false, true);
    }

    @Override // com.android.camera.features.mimoji2.module.protocol.MimojiModeProtocol.MimojiAvatarEngine2
    public String getVideoCache() {
        return CameraSettings.isGifOn() ? MimojiHelper2.GIF_NORMAL_CACHE_FILE : MimojiHelper2.VIDEO_NORMAL_CACHE_FILE;
    }

    @Override // com.android.camera.features.mimoji2.module.protocol.MimojiModeProtocol.MimojiAvatarEngine2
    public void initAvatarEngine(int i, int i2, int i3, int i4, boolean z) {
        Log.d(TAG, "initAvatarEngine with parameters : displayOrientation = " + i + ", width = " + i3 + ", height = " + i4 + ", isFrontCamera = " + z);
        this.mDisplayOrientation = i;
        this.mPreviewWidth = i3;
        this.mPreviewHeight = i4;
        this.mIsFrontCamera = z;
        this.mOrientation = i2;
        this.mIsNoFaceResult = -1;
        this.mNeedThumbnail = false;
        this.mNeedCapture = false;
        setIsAvatarInited(false);
        this.uiStyle = DataRepository.dataItemRunning().getUiStyle();
        int hashCode = hashCode();
        initMimojiDataResource();
        Log.d(TAG, "avatar start init | " + hashCode);
        this.mAvatar = AvatarEngineManager2.getInstance().queryAvatar();
        this.mAvatar.setRenderScene(true, 1.0f);
        this.mIsGifOn = CameraSettings.isGifOn();
        Rect previewRect = Util.getPreviewRect(this.mContext);
        int i5 = previewRect.bottom - previewRect.top;
        int i6 = previewRect.right;
        if (this.mIsGifOn) {
            this.mDrawSize = new Size(i6, i6);
        } else {
            this.mDrawSize = new Size(i6, i5);
        }
        RecordModule recordModule = this.mRecordModule;
        if (recordModule == null) {
            this.mRecordModule = new RecordModule(this.mContext, this.mCaptureCallback);
            this.mRecordModule.init(i, this.mDrawSize.getWidth(), this.mDrawSize.getHeight(), this.mAvatar, z);
            this.mRecordModule.setExtraSceneTemplatePath(AvatarEngineManager2.TEMPLATE_PATH_HUMAN, this.mSceneTriggerCallback);
        } else {
            recordModule.setmImageOrientation(i);
            this.mRecordModule.setMirror(z);
            this.mRecordModule.setPreviewSize(this.mDrawSize.getWidth(), this.mDrawSize.getHeight());
        }
        this.mRecordModule.setBackgroundToSquare(this.mIsGifOn);
        releaseRender();
        updateBeauty(!isNeedShowAvatar() && this.mLastNeedBeauty);
        this.mIsStopRender = false;
        showLoadProgress(true);
        this.mLoadHandler.post(new e(this));
    }

    @Override // com.android.camera.features.mimoji2.module.protocol.MimojiModeProtocol.MimojiAvatarEngine2
    public void initMimojiResource() {
        String Ah = DataRepository.dataItemFeature().Ah();
        if (this.mMimojiStatusManager2.IsLoading()) {
            showLoadProgress(true);
            return;
        }
        long currentTimeMillis = System.currentTimeMillis();
        try {
            this.mMimojiStatusManager2.setIsLoading(true);
            this.mMimojiStatusManager2.setCurrentMimojiInfo(null);
            FileUtils.delDir(MimojiHelper2.MODEL_PATH);
            FileUtils.delDir(MimojiHelper2.CUSTOM_DIR);
            FileUtils.makeNoMediaDir(MimojiHelper2.MODEL_PATH);
            FileUtils.makeNoMediaDir(MimojiHelper2.CUSTOM_DIR);
            Util.verifyFileZip(this.mContext, "vendor/camera/mimoji/model2.zip", MimojiHelper2.MIMOJI_DIR, 32768);
            FileUtils.delDir(MimojiHelper2.DATA_DIR);
            FileUtils.makeNoMediaDir(MimojiHelper2.DATA_DIR);
            Util.verifyFileZip(this.mContext, "vendor/camera/mimoji/data.zip", MimojiHelper2.MIMOJI_DIR, 32768);
            Log.d(TAG, "init model spend time = " + (System.currentTimeMillis() - currentTimeMillis));
            this.mMimojiStatusManager2.setIsLoading(false);
            CameraSettings.setMimojiModleVersion(Ah);
            Log.i(TAG, "mAvatarTemplatePath = " + this.mAvatarTemplatePath);
            reloadConfig();
            onMimojiInitFinish();
        } catch (Exception e2) {
            Log.e(TAG, "verify asset model zip failed...", e2);
            this.mMimojiStatusManager2.setIsLoading(false);
            CameraSettings.setMimojiModleVersion(null);
            FileUtils.delDir(MimojiHelper2.MIMOJI_DIR);
        }
    }

    @Override // com.android.camera.features.mimoji2.module.protocol.MimojiModeProtocol.MimojiAvatarEngine2
    public boolean isAvatarInited() {
        return this.mIsAvatarInited;
    }

    public boolean isNeedShowAvatar() {
        MimojiInfo2 currentMimojiInfo = this.mMimojiStatusManager2.getCurrentMimojiInfo();
        return currentMimojiInfo != null && this.mAvatar != null && !TextUtils.isEmpty(currentMimojiInfo.mConfigPath) && !currentMimojiInfo.mConfigPath.equals("add_state") && !currentMimojiInfo.mConfigPath.equals("close_state") && !this.mMimojiStatusManager2.isInMimojiCreate();
    }

    @Override // com.android.camera.features.mimoji2.module.protocol.MimojiModeProtocol.MimojiAvatarEngine2
    public boolean isOnCreateMimoji() {
        return this.mMimojiStatusManager2.isInMimojiCreate();
    }

    @Override // com.android.camera.SurfaceTextureScreenNail.ExternalFrameProcessor
    public boolean isProcessorReady() {
        return this.mRecordModule != null && !this.mIsStopRender;
    }

    @Override // com.android.camera.features.mimoji2.module.protocol.MimojiModeProtocol.MimojiAvatarEngine2
    public boolean isRecordStopping() {
        return this.mIsRecordStopping;
    }

    @Override // com.android.camera.features.mimoji2.module.protocol.MimojiModeProtocol.MimojiAvatarEngine2
    public boolean isRecording() {
        Log.d(TAG, "Recording = " + this.mIsRecording);
        return this.mIsRecording;
    }

    @Override // com.android.camera.features.mimoji2.module.protocol.MimojiModeProtocol.MimojiAvatarEngine2
    public void onCaptureImage() {
        ActivityBase activityBase;
        if (this.mRecordModule != null && (activityBase = this.mActivityBase) != null) {
            this.mNeedCapture = true;
            CameraStatUtils.trackMimoji2CaptureOrRecord(getMimojiPara(), CameraSettings.getFlashMode(activityBase.getCurrentModuleIndex()), true, this.mIsFrontCamera);
            CameraStatUtils.trackMimojiSavePara(MistatsConstants.Mimoji.MIMOJI_CLICK_EDIT_SAVE, getExtraMimojiPara());
        }
    }

    @Override // com.android.camera.features.mimoji2.module.protocol.MimojiModeProtocol.MimojiAvatarEngine2
    public boolean onCreateCapture() {
        ActivityBase activityBase;
        Log.d(TAG, "onCreateCapture");
        if (this.mFaceDectectResult != 0 || !this.mIsFaceDetectSuccess || (activityBase = this.mActivityBase) == null) {
            return false;
        }
        Module currentModule = activityBase.getCurrentModule();
        if (currentModule instanceof MimojiModule) {
            CameraSettings.setFaceBeautyLevel(0);
            ((MimojiModule) currentModule).updatePreferenceInWorkThread(13);
        }
        ((ModeProtocol.ActionProcessing) ModeCoordinatorImpl.getInstance().getAttachProtocol(162)).showOrHideMimojiProgress(true);
        this.mIsShutterButtonClick = true;
        animateCapture();
        return true;
    }

    @Override // com.android.camera.features.mimoji2.module.protocol.MimojiModeProtocol.MimojiAvatarEngine2
    public void onDeviceRotationChange(int i) {
        this.mDeviceRotation = i;
        updateVideoOrientation(i);
        MimojiModeProtocol.MimojiEditor2 mimojiEditor2 = this.mMimojiEditor2;
        if (mimojiEditor2 != null) {
            mimojiEditor2.onDeviceRotationChange(i);
        }
    }

    @Override // com.android.camera.SurfaceTextureScreenNail.ExternalFrameProcessor, com.android.camera.features.mimoji2.module.protocol.MimojiModeProtocol.MimojiAvatarEngine2
    public void onDrawFrame(Rect rect, int i, int i2, boolean z) {
        if (this.mRecordModule != null && rect != null && !this.mIsStopRender) {
            int i3 = Util.sWindowHeight;
            int i4 = rect.bottom;
            int i5 = i3 - i4;
            int i6 = i4 - rect.top;
            int i7 = rect.right;
            if (z) {
                GLES20.glViewport(0, 0, i, i2);
            } else {
                GLES20.glViewport(0, i5, i7, i6);
                this.mRecordModule.setDrawScope(0, i5, i7, i6);
                if (this.mNeedCapture) {
                    Log.d(TAG, "onCapture start");
                    this.mRecordModule.capture();
                    ActivityBase activityBase = this.mActivityBase;
                    if (activityBase != null) {
                        ((MimojiModule) activityBase.getCurrentModule()).setCameraStatePublic(3);
                    }
                    this.mNeedCapture = false;
                }
            }
            GLES20.glClearColor(0.0f, 0.0f, 0.0f, 1.0f);
            GLES20.glClear(16384);
            this.mRecordModule.startRender(90, this.mIsFrontCamera, this.mDeviceRotation, 0, false, this.mTextureId, null, isNeedShowAvatar() && !getIsNoFaceResult());
            if (this.mNeedThumbnail) {
                this.mNeedThumbnail = false;
                ByteBuffer allocate = ByteBuffer.allocate(i7 * i6 * 4);
                GLES20.glReadPixels(0, i5, i7, i6, 6408, 5121, allocate);
                this.mLoadHandler.post(new g(this, allocate, i7, i6));
            }
        }
    }

    @Override // com.android.camera.features.mimoji2.module.protocol.MimojiModeProtocol.MimojiAvatarEngine2
    public void onMimojiChangeBg(MimojiBgInfo mimojiBgInfo) {
        if (mimojiBgInfo == null || mimojiBgInfo.getBackgroundInfo() == null) {
            this.mMimojiStatusManager2.setCurrentMimojiBgInfo(null);
        } else {
            this.mMimojiStatusManager2.setCurrentMimojiBgInfo(mimojiBgInfo);
        }
        CameraStatUtils.trackMimoji2Click(null, MistatsConstants.Mimoji.MIMOJI_CHANGE_BG);
    }

    @Override // com.android.camera.features.mimoji2.module.protocol.MimojiModeProtocol.MimojiAvatarEngine2
    public void onMimojiChangeTimbre(MimojiTimbreInfo mimojiTimbreInfo, int i) {
        if (i == 0 || mimojiTimbreInfo == null || mimojiTimbreInfo.getTimbreId() <= 0) {
            this.mMimojiStatusManager2.setCurrentMimojiTimbreInfo(null);
        } else {
            this.mMimojiStatusManager2.setCurrentMimojiTimbreInfo(mimojiTimbreInfo);
        }
        if (this.mMimojiStatusManager2.isInMimojiPreview()) {
            ModeProtocol.TopAlert topAlert = (ModeProtocol.TopAlert) ModeCoordinatorImpl.getInstance().getAttachProtocol(172);
            if (topAlert != null) {
                topAlert.alertTopHint(0, this.resourceTimbreAlert[i], 1000);
            }
        } else {
            MimojiModeProtocol.MimojiFullScreenProtocol mimojiFullScreenProtocol = (MimojiModeProtocol.MimojiFullScreenProtocol) ModeCoordinatorImpl.getInstance().getAttachProtocol(249);
            if (mimojiFullScreenProtocol != null) {
                mimojiFullScreenProtocol.alertTop(0, this.resourceTimbreAlert[i], 1000);
            }
            MimojiModeProtocol.MimojiVideoEditor mimojiVideoEditor = (MimojiModeProtocol.MimojiVideoEditor) ModeCoordinatorImpl.getInstance().getAttachProtocol(252);
            if (mimojiVideoEditor != null) {
                mimojiVideoEditor.changeTimbre();
            }
        }
        CameraStatUtils.trackMimoji2Click(null, MistatsConstants.Mimoji.MIMOJI_CHANGE_TIMBRE);
        Log.d(TAG, "mimoji void onMimojiChangeTimbre[mimojiTimbreInfo]" + mimojiTimbreInfo);
    }

    @Override // com.android.camera.features.mimoji2.module.protocol.MimojiModeProtocol.MimojiAvatarEngine2
    public void onMimojiCreate() {
        Log.d(TAG, "start create mimoji");
        this.mMimojiStatusManager2.setMode(4);
        ModeProtocol.TopAlert topAlert = (ModeProtocol.TopAlert) ModeCoordinatorImpl.getInstance().getAttachProtocol(172);
        if (topAlert != null) {
            topAlert.alertAiDetectTipHint(8, R.string.mimoji_create_tips, -1);
        }
        this.mMainProtocol.mimojiStart();
        ((ModeProtocol.RecordState) ModeCoordinatorImpl.getInstance().getAttachProtocol(212)).prepareCreateMimoji();
    }

    @Override // com.android.camera.features.mimoji2.module.protocol.MimojiModeProtocol.MimojiAvatarEngine2
    public void onMimojiDeleted() {
        this.mMimojiStatusManager2.setCurrentMimojiInfo(null);
        this.mMimojiStatusManager2.setCurrentMimojiBgInfo(null);
        this.mRecordModule.resetExtraScene();
    }

    @Override // com.android.camera.features.mimoji2.module.protocol.MimojiModeProtocol.MimojiAvatarEngine2
    public void onMimojiInitFinish() {
        Log.d(TAG, "onMimojiInitFinish");
        setIsAvatarInited(true);
        showLoadProgress(false);
    }

    @Override // com.android.camera.features.mimoji2.module.protocol.MimojiModeProtocol.MimojiAvatarEngine2
    public void onMimojiSelect(MimojiInfo2 mimojiInfo2, boolean z) {
        Log.d(TAG, "mimoji void onMimojiSelect[mimojiInfo2]");
        this.mUiHandler.post(new j(this));
        if (mimojiInfo2 == null || TextUtils.isEmpty(mimojiInfo2.mConfigPath) || mimojiInfo2.mConfigPath.equals("add_state") || mimojiInfo2.mConfigPath.equals("close_state") || this.mAvatar == null) {
            this.mMimojiStatusManager2.setCurrentMimojiInfo(null);
            MimojiModeProtocol.MimojiBottomList mimojiBottomList = (MimojiModeProtocol.MimojiBottomList) ModeCoordinatorImpl.getInstance().getAttachProtocol(248);
            if (mimojiBottomList != null && !z) {
                mimojiBottomList.refreshMimojiList();
                return;
            }
            return;
        }
        if (this.mMimojiStatusManager2.getCurrentMimojiInfo() == null) {
            this.mIsNoFaceResult = 2;
        }
        this.mMimojiStatusManager2.setCurrentMimojiInfo(mimojiInfo2);
        String str = mimojiInfo2.mAvatarTemplatePath;
        String str2 = mimojiInfo2.mConfigPath;
        Log.d(TAG, "change mimoji with path = " + str + ", and config = " + str2);
        synchronized (this.mAvatarLock) {
            this.mRecordModule.setExtraSceneTemplatePath(AvatarEngineManager2.TEMPLATE_PATH_HUMAN, this.mSceneTriggerCallback);
            this.mRecordModule.resetExtraScene();
            boolean equals = this.mAvatarTemplatePath.equals(str);
            this.mAvatarTemplatePath = str;
            this.mAvatarConfigPath = str2 == null ? "" : str2;
            if (str2.isEmpty() || AvatarEngineManager2.isPreCartoonModel(str2)) {
                if (!equals) {
                    this.mAvatar.setTemplatePath(str);
                }
                if (mimojiInfo2.getAvatarConfigInfoArrayList() == null) {
                    mimojiInfo2.setAvatarConfigInfoArrayList(this.mAvatar.getConfig(12, 1));
                }
                CameraStatUtils.trackMimoji2Click(MistatsConstants.Mimoji.CARTOON, null);
                if (mimojiInfo2.getFrame() >= 0 && mimojiInfo2.getDefaultFrame() > 0) {
                    this.mAvatar.setConfig(mimojiInfo2.getAvatarConfigInfoArrayList().get(mimojiInfo2.getFrame()));
                }
            } else {
                if (!equals) {
                    this.mRecordModule.changeHumanTemplate(str, str2);
                } else {
                    this.mAvatar.loadConfig(str2);
                }
                CameraStatUtils.trackMimoji2Click(MistatsConstants.Mimoji.HUMAN, null);
            }
            this.mRecordModule.updateAvatarConfigInfo(this.mAvatar);
            boolean z2 = false;
            this.mAvatar.setRenderScene(this.mCurrentTempMimojiBgInfo == null, 1.0f);
            if (!isNeedShowAvatar() && this.mLastNeedBeauty) {
                z2 = true;
            }
            updateBeauty(z2);
        }
    }

    @Override // com.android.camera2.Camera2Proxy.PreviewCallback
    public boolean onPreviewFrame(Image image, Camera2Proxy camera2Proxy, int i) {
        boolean startProcess;
        boolean z = false;
        if (this.mRecordModule == null) {
            Log.d(TAG, "MimojiAvatarEngine2Impl onPreviewFrame mRecordModule null");
            this.mLastInitResult = false;
            this.mCameraView.requestRender();
            return true;
        }
        ASVLOFFSCREEN buildNV21SingleBuffer = AsvloffscreenUtil.buildNV21SingleBuffer(image);
        if (this.mIsShutterButtonClick) {
            this.mIsShutterButtonClick = false;
            setIsAvatarInited(false);
            Bitmap rotateBitmap = BitmapUtils2.rotateBitmap(BitmapUtils2.rawByteArray2RGBABitmap(buildNV21SingleBuffer.getYData(), image.getWidth(), image.getHeight(), image.getPlanes()[0].getRowStride()), this.mIsFrontCamera ? -90 : 90);
            int width = rotateBitmap.getWidth();
            int height = rotateBitmap.getHeight();
            ByteBuffer order = ByteBuffer.allocate(rotateBitmap.getRowBytes() * rotateBitmap.getHeight()).order(ByteOrder.nativeOrder());
            rotateBitmap.copyPixelsToBuffer(order);
            createAvatar(order.array(), width, height);
        }
        this.mMimojiEditor2 = (MimojiModeProtocol.MimojiEditor2) ModeCoordinatorImpl.getInstance().getAttachProtocol(247);
        if (!this.mMimojiStatusManager2.isInMimojiEdit()) {
            if (this.mMimojiEditor2 != null && !this.mMimojiStatusManager2.isInMimojiEmoticon()) {
                this.mMimojiEditor2.requestRender(true);
                this.mMimojiEditor2.resetClickEnable(false);
            }
            synchronized (this.mAvatarLock) {
                checkIsNeedChangBg();
                startProcess = this.mRecordModule != null ? this.mRecordModule.startProcess(buildNV21SingleBuffer, MimojiHelper2.getOutlineOrientation(this.mOrientation, this.mDeviceRotation, this.mIsFrontCamera), isNeedShowAvatar()) : true;
            }
            if (changeIsNoFaceResult(startProcess) && !this.mMimojiStatusManager2.isInMimojiCreate()) {
                this.mUiHandler.post(new f(this));
            }
            boolean isNoFaceResult = getIsNoFaceResult();
            if (this.mLastNeedBeauty != isNoFaceResult) {
                this.mLastNeedBeauty = isNoFaceResult;
                if (!isNeedShowAvatar() && isNoFaceResult) {
                    z = true;
                }
                updateBeauty(z);
            }
            this.mCameraView.requestRender();
        } else if (!isAvatarInited() || this.mAvatar == null) {
            MimojiModeProtocol.MimojiEditor2 mimojiEditor2 = this.mMimojiEditor2;
            if (mimojiEditor2 != null) {
                mimojiEditor2.resetClickEnable(false);
                this.mMimojiEditor2.requestRender(true);
            }
            Log.d(TAG, "MimojiAvatarEngine2Impl onPreviewFrame need init, waiting......");
            return true;
        } else if (this.mMimojiEditor2 != null && isAvatarInited()) {
            AvatarConfig.ASAvatarProcessInfo aSAvatarProcessInfo = new AvatarConfig.ASAvatarProcessInfo();
            synchronized (this.mAvatarLock) {
                if (this.mAvatar != null) {
                    this.mAvatar.avatarProcessWithInfoEx(buildNV21SingleBuffer, 90, this.mIsFrontCamera, this.mOrientation, aSAvatarProcessInfo, true);
                }
            }
            this.mMimojiEditor2.requestRender(false);
            this.mMimojiEditor2.resetClickEnable(true);
            if (!this.mLastInitResult) {
                this.mMimojiEditor2.resetConfig();
            }
        }
        if (this.mMimojiStatusManager2.isInMimojiCreate() && isAvatarInited()) {
            synchronized (this.mAvatarLock) {
                this.mFaceDectectResult = this.mAvatar.outlineProcessEx(buildNV21SingleBuffer, MimojiHelper2.getOutlineOrientation(this.mOrientation, this.mDeviceRotation, this.mIsFrontCamera));
            }
            ModeProtocol.MainContentProtocol mainContentProtocol = this.mMainProtocol;
            if (mainContentProtocol != null) {
                mainContentProtocol.mimojiFaceDetect(this.mFaceDectectResult);
            }
        }
        this.mLastInitResult = true;
        return true;
    }

    @Override // com.android.camera.features.mimoji2.module.protocol.MimojiModeProtocol.MimojiAvatarEngine2
    public void onRecordStart() {
        ActivityBase activityBase;
        Log.d(TAG, "start record...");
        if (!this.mIsRecording && (activityBase = this.mActivityBase) != null) {
            CameraStatUtils.trackMimoji2CaptureOrRecord(getMimojiPara(), CameraSettings.getFlashMode(activityBase.getCurrentModuleIndex()), false, this.mIsFrontCamera);
            CameraStatUtils.trackMimojiSavePara(MistatsConstants.Mimoji.MIMOJI_CLICK_EDIT_SAVE, getExtraMimojiPara());
            this.mIsRecording = true;
            this.mNeedThumbnail = true;
            this.mCameraView.queueEvent(new h(this));
        }
    }

    @Override // com.android.camera.features.mimoji2.module.protocol.MimojiModeProtocol.MimojiAvatarEngine2
    public void onRecordStop(int i) {
        Log.d(TAG, "stop record...");
        this.mIsRecordStopping = true;
        this.mStopRecordType = i;
        if (i != 0) {
            this.mGetThumCountDownLatch = new CountDownLatch(1);
        }
        CountDownTimer countDownTimer = this.mCountDownTimer;
        if (countDownTimer != null) {
            countDownTimer.cancel();
        }
        new Thread(new k(this)).start();
    }

    @Override // com.android.camera.protocol.ModeProtocol.BaseProtocol
    public void registerProtocol() {
        ModeCoordinatorImpl.getInstance().attachProtocol(246, this);
    }

    @Override // com.android.camera.features.mimoji2.module.protocol.MimojiModeProtocol.MimojiAvatarEngine2
    public void release() {
        Log.d(TAG, "avatar release");
        this.mMimojiStatusManager2.setCurrentMimojiBgInfo(null);
        this.mIsRecordStopping = false;
        CountDownLatch countDownLatch = this.mGetThumCountDownLatch;
        if (countDownLatch != null) {
            try {
                countDownLatch.await();
            } catch (InterruptedException e2) {
                e2.printStackTrace();
            }
        }
        this.mDrawSize = null;
        if (this.mMimojiStatusManager2.isInPreviewSurface()) {
            this.mCameraView.queueEvent(new q(this));
        } else {
            destoryAll();
        }
    }

    @Override // com.android.camera.SurfaceTextureScreenNail.ExternalFrameProcessor
    public void releaseRender() {
        int hashCode = hashCode();
        this.mIsStopRender = true;
        if (this.mRecordModule != null) {
            Log.d(TAG, "mimoji void reloadConfig[]　extrascene init");
            this.mRecordModule.resetExtraScene();
        }
        if (this.mMimojiStatusManager2.isInPreviewSurface()) {
            this.mCameraView.queueEvent(new i(this, hashCode));
            return;
        }
        MimojiModeProtocol.MimojiEditor2 mimojiEditor2 = this.mMimojiEditor2;
        if (mimojiEditor2 != null) {
            mimojiEditor2.releaseRender();
        }
    }

    @Override // com.android.camera.features.mimoji2.module.protocol.MimojiModeProtocol.MimojiAvatarEngine2
    public void reloadConfig() {
        Log.d(TAG, "MimojiAvatarEngine2Impl reloadConfig");
        if (this.mRecordModule != null) {
            int mode = this.mMimojiStatusManager2.getMode();
            this.mAvatar = AvatarEngineManager2.getInstance().queryAvatar();
            if (mode == 2 || mode == 0) {
                this.mMimojiStatusManager2.setMode(2);
                MimojiInfo2 currentMimojiInfo = this.mMimojiStatusManager2.getCurrentMimojiInfo();
                ArrayList<BackgroundInfo> backgroundBmpInfo = this.mRecordModule.getBackgroundBmpInfo(AvatarEngineManager2.TEMPLATE_PATH_BG);
                if (backgroundBmpInfo != null) {
                    AvatarEngineManager2.getInstance().setBackgroundInfos(backgroundBmpInfo);
                }
                if (this.mRecordModule != null) {
                    Log.d(TAG, "mimoji void reloadConfig[]　extrascene init");
                    this.mRecordModule.resetExtraScene();
                }
                if (isNeedShowAvatar()) {
                    if (!this.mAvatarTemplatePath.equals(currentMimojiInfo.mAvatarTemplatePath)) {
                        this.mAvatar.setTemplatePath(currentMimojiInfo.mAvatarTemplatePath);
                        this.mAvatarTemplatePath = currentMimojiInfo.mAvatarTemplatePath;
                        this.mAvatarConfigPath = "";
                    }
                    String str = currentMimojiInfo.mConfigPath;
                    if (!AvatarEngineManager2.isPreCartoonModel(str)) {
                        if (!this.mAvatarConfigPath.equals(str)) {
                            this.mAvatar.loadConfig(str);
                            this.mAvatarConfigPath = str;
                        }
                    } else if (currentMimojiInfo.getFrame() > 0 && currentMimojiInfo.getAvatarConfigInfoArrayList() != null) {
                        this.mAvatar.setConfig(currentMimojiInfo.getAvatarConfigInfoArrayList().get(currentMimojiInfo.getFrame()));
                    }
                    this.mRecordModule.updateAvatarConfigInfo(this.mAvatar);
                }
            } else if (mode == 6) {
                String str2 = AvatarEngineManager2.TEMPLATE_PATH_HUMAN;
                this.mAvatarTemplatePath = str2;
                this.mAvatar.setTemplatePath(str2);
                this.mMimojiEditor2 = (MimojiModeProtocol.MimojiEditor2) ModeCoordinatorImpl.getInstance().getAttachProtocol(247);
                MimojiModeProtocol.MimojiEditor2 mimojiEditor2 = this.mMimojiEditor2;
                if (mimojiEditor2 != null) {
                    mimojiEditor2.resetClickEnable(false);
                    this.mMimojiEditor2.resetConfig();
                    return;
                }
                Log.e(TAG, "MimojiAvatarEngine2Impl reloadConfig: error mimojiEditor is null");
            }
        }
    }

    @Override // com.android.camera.features.mimoji2.module.protocol.MimojiModeProtocol.MimojiAvatarEngine2
    public void setDetectSuccess(boolean z) {
        this.mIsFaceDetectSuccess = z;
    }

    @Override // com.android.camera.features.mimoji2.module.protocol.MimojiModeProtocol.MimojiAvatarEngine2
    public void setDisableSingleTapUp(boolean z) {
        ActivityBase activityBase = this.mActivityBase;
        if (activityBase != null) {
            ((MimojiModule) activityBase.getCurrentModule()).setDisableSingleTapUp(z);
        }
    }

    public void setIsAvatarInited(boolean z) {
        this.mIsAvatarInited = z;
    }

    public /* synthetic */ void t(int i) {
        Log.d(TAG, "avatar destroy | " + i);
        synchronized (this.mAvatarLock) {
            if (isAvatarInited() && !this.mMimojiStatusManager2.isInMimojiEdit()) {
                this.mAvatar.saveConfig(AvatarEngineManager2.TempEditConfigPath);
            }
            if (this.mRecordModule != null) {
                this.mRecordModule.resetExtraScene();
                this.mRecordModule.unInit();
            }
            AvatarEngineManager2.getInstance().release();
            this.mActivityBase = null;
        }
    }

    public /* synthetic */ void u(int i) {
        synchronized (this.mAvatarLock) {
            if (this.mAvatar != null) {
                Log.d(TAG, "releaseRender | " + i);
                this.mAvatar.releaseRender();
            }
        }
    }

    @Override // com.android.camera.protocol.ModeProtocol.BaseProtocol
    public void unRegisterProtocol() {
        ModeCoordinatorImpl.getInstance().detachProtocol(246, this);
        release();
    }

    /* renamed from: updateRecordingTime */
    public void Sc() {
        CountDownTimer countDownTimer = this.mCountDownTimer;
        if (countDownTimer != null) {
            this.mTotalRecordingTime = 0;
            countDownTimer.cancel();
        }
        if (CameraSettings.isGifOn()) {
            this.mMaxVideoDurationInMs = 5000;
        } else {
            this.mMaxVideoDurationInMs = 15000;
        }
        this.mCountDownTimer = new CountDownTimer(450 + ((long) this.mMaxVideoDurationInMs), 1000) {
            /* class com.android.camera.features.mimoji2.module.impl.MimojiAvatarEngine2Impl.AnonymousClass2 */

            public void onFinish() {
                if (MimojiAvatarEngine2Impl.this.mActivityBase != null || MimojiAvatarEngine2Impl.this.mStopRecordType == 0) {
                    ((MimojiModule) MimojiAvatarEngine2Impl.this.mActivityBase.getCurrentModule()).stopVideoRecording(0);
                }
            }

            public void onTick(long j) {
                String millisecondToTimeString = Util.millisecondToTimeString((950 + j) - 450, false);
                MimojiAvatarEngine2Impl mimojiAvatarEngine2Impl = MimojiAvatarEngine2Impl.this;
                mimojiAvatarEngine2Impl.mTotalRecordingTime = (((long) mimojiAvatarEngine2Impl.mMaxVideoDurationInMs) - j) + 450;
                ModeProtocol.TopAlert topAlert = (ModeProtocol.TopAlert) ModeCoordinatorImpl.getInstance().getAttachProtocol(172);
                if (topAlert != null) {
                    topAlert.updateRecordingTime(millisecondToTimeString);
                }
            }
        };
        this.mCountDownTimer.start();
    }
}
