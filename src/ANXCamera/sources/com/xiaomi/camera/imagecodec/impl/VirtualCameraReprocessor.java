package com.xiaomi.camera.imagecodec.impl;

import android.annotation.SuppressLint;
import android.content.Context;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraCaptureSession;
import android.hardware.camera2.CameraDevice;
import android.hardware.camera2.CameraManager;
import android.hardware.camera2.CaptureFailure;
import android.hardware.camera2.CaptureRequest;
import android.hardware.camera2.TotalCaptureResult;
import android.hardware.camera2.params.InputConfiguration;
import android.hardware.camera2.params.SessionConfiguration;
import android.media.Image;
import android.media.ImageReader;
import android.media.ImageWriter;
import android.os.Build;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Message;
import android.os.PowerManager;
import android.support.annotation.AnyThread;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.annotation.WorkerThread;
import android.util.Log;
import android.util.Range;
import android.view.Surface;
import com.xiaomi.camera.imagecodec.ImagePool;
import com.xiaomi.camera.imagecodec.ImageReaderHelper;
import com.xiaomi.camera.imagecodec.OutputConfiguration;
import com.xiaomi.camera.imagecodec.ReprocessData;
import com.xiaomi.camera.imagecodec.Reprocessor;
import com.xiaomi.protocol.ICustomCaptureResult;
import java.lang.reflect.Field;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.Executor;

public class VirtualCameraReprocessor implements Reprocessor {
    private static String BACK_VT_CAMERA_ID_DEFAULT = "100";
    private static String FRONT_VT_CAMERA_ID_DEFAULT = "101";
    private static final int MAX_IMAGE_BUFFER_SIZE = 2;
    private static final String TAG = "VirtualCameraReprocessor";
    public static final Reprocessor.Singleton<VirtualCameraReprocessor> sInstance = new Reprocessor.Singleton<VirtualCameraReprocessor>() {
        /* class com.xiaomi.camera.imagecodec.impl.VirtualCameraReprocessor.AnonymousClass1 */

        /* access modifiers changed from: protected */
        @Override // com.xiaomi.camera.imagecodec.Reprocessor.Singleton
        public VirtualCameraReprocessor create() {
            return new VirtualCameraReprocessor();
        }
    };
    private String mBackVtCameraId;
    private final Object mCameraLock;
    private CameraManager mCameraManager;
    private Handler mCameraOperationHandler;
    private HandlerThread mCameraOperationThread;
    private volatile boolean mCreatingReprocessSession;
    private ReprocessData mCurrentProcessingData;
    private int mCurrentSessionId;
    private final Object mDataLock;
    private String mFrontVtCameraId;
    private boolean mInitialized;
    private InputConfiguration mInputConfiguration;
    private boolean mNeedReopenCamera;
    private ImageReader mPicImageReader;
    private OutputConfiguration mPicOutputConfiguration;
    private Handler mReprocessHandler;
    private ImageWriter mReprocessImageWriter;
    private long mReprocessStartTime;
    private HandlerThread mReprocessThread;
    private Range<Integer> mSessionFpsRange;
    private LinkedList<ReprocessData> mTaskDataList;
    private CameraDevice mVTCameraDevice;
    private CameraCaptureSession mVTCaptureSession;
    private PowerManager.WakeLock mWakeLock;
    private ImageReader mYuvImageReader;
    private OutputConfiguration mYuvOutputConfiguration;

    private class ReprocessHandler extends Handler {
        private static final int MSG_CLOSE_VT_CAMERA = 2;
        private static final int MSG_REPROCESS_IMG = 1;

        ReprocessHandler(Looper looper) {
            super(looper);
        }

        public void handleMessage(Message message) {
            int i = message.what;
            if (i == 1) {
                Log.d(VirtualCameraReprocessor.TAG, "recv MSG_REPROCESS_IMG");
                if (VirtualCameraReprocessor.this.checkConditionIsReady()) {
                    VirtualCameraReprocessor.this.reprocessImage();
                }
            } else if (i != 2) {
                super.handleMessage(message);
            } else {
                Log.d(VirtualCameraReprocessor.TAG, "recv MSG_CLOSE_VT_CAMERA");
                synchronized (VirtualCameraReprocessor.this.mCameraLock) {
                    if (VirtualCameraReprocessor.this.mVTCameraDevice != null) {
                        String str = VirtualCameraReprocessor.TAG;
                        Log.d(str, "close current VtCamera: " + VirtualCameraReprocessor.this.mVTCameraDevice);
                        VirtualCameraReprocessor.this.mVTCameraDevice.close();
                        VirtualCameraReprocessor.this.mVTCameraDevice = null;
                    }
                }
                VirtualCameraReprocessor.this.releaseWakeLock();
            }
        }
    }

    private VirtualCameraReprocessor() {
        this.mBackVtCameraId = BACK_VT_CAMERA_ID_DEFAULT;
        this.mFrontVtCameraId = FRONT_VT_CAMERA_ID_DEFAULT;
        this.mCurrentSessionId = -1;
        this.mCameraLock = new Object();
        this.mTaskDataList = new LinkedList<>();
        this.mDataLock = new Object();
        this.mYuvOutputConfiguration = new OutputConfiguration(0, 0, 35);
    }

    static /* synthetic */ int access$808(VirtualCameraReprocessor virtualCameraReprocessor) {
        int i = virtualCameraReprocessor.mCurrentSessionId;
        virtualCameraReprocessor.mCurrentSessionId = i + 1;
        return i;
    }

    private void acquireWakeLock() {
        if (!this.mWakeLock.isHeld()) {
            Log.d(TAG, "acquireWakeLock");
            this.mWakeLock.acquire();
        }
    }

    /* access modifiers changed from: private */
    /* access modifiers changed from: public */
    /* JADX WARNING: Code restructure failed: missing block: B:10:0x001a, code lost:
        if (r1 != null) goto L_0x0024;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:11:0x001c, code lost:
        android.util.Log.w(com.xiaomi.camera.imagecodec.impl.VirtualCameraReprocessor.TAG, "checkConditionIsReady: ignore null request!");
     */
    /* JADX WARNING: Code restructure failed: missing block: B:12:0x0023, code lost:
        return false;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:14:0x002a, code lost:
        return !createCaptureSessionIfNeed(r1);
     */
    @WorkerThread
    private boolean checkConditionIsReady() {
        synchronized (this.mDataLock) {
            if (this.mCurrentProcessingData != null) {
                Log.d(TAG, "checkConditionIsReady: processor is busy!");
                return false;
            }
            ReprocessData peek = this.mTaskDataList.peek();
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:20:0x0059  */
    /* JADX WARNING: Removed duplicated region for block: B:28:0x009f  */
    /* JADX WARNING: Removed duplicated region for block: B:63:0x0153  */
    @WorkerThread
    private boolean createCaptureSessionIfNeed(@NonNull ReprocessData reprocessData) {
        Object[] objArr;
        synchronized (this.mCameraLock) {
            if (this.mCreatingReprocessSession) {
                Log.d(TAG, "creating reprocess session...");
                return true;
            }
            Image yuvImage = reprocessData.getYuvImage();
            InputConfiguration inputConfiguration = new InputConfiguration(yuvImage.getWidth(), yuvImage.getHeight(), yuvImage.getFormat());
            OutputConfiguration outputConfiguration = new OutputConfiguration(reprocessData.getOutputWidth(), reprocessData.getOutputHeight(), reprocessData.getOutputFormat());
            String str = reprocessData.isFrontCamera() ? this.mFrontVtCameraId : this.mBackVtCameraId;
            int i = 0;
            boolean z = false;
            if (this.mVTCameraDevice != null) {
                if (str.equals(this.mVTCameraDevice.getId())) {
                    objArr = null;
                    if (objArr == null) {
                        this.mCreatingReprocessSession = true;
                        if (this.mVTCameraDevice == null) {
                            openVTCamera(str);
                        } else if (!str.equals(this.mVTCameraDevice.getId())) {
                            Log.d(TAG, "createCaptureSessionIfNeed: expected device changed. oldId=" + this.mVTCameraDevice.getId() + " newId=" + str);
                            this.mVTCameraDevice.close();
                            this.mVTCameraDevice = null;
                            this.mNeedReopenCamera = true;
                        }
                        return true;
                    }
                    int format = outputConfiguration.getFormat();
                    OutputConfiguration outputConfiguration2 = format != 35 ? this.mPicOutputConfiguration : this.mYuvOutputConfiguration;
                    if (this.mVTCaptureSession != null) {
                        if (!inputConfiguration.equals(this.mInputConfiguration) || !outputConfiguration.equals(outputConfiguration2)) {
                            String str2 = TAG;
                            Object[] objArr2 = new Object[9];
                            objArr2[0] = Integer.valueOf(this.mInputConfiguration == null ? 0 : this.mInputConfiguration.getWidth());
                            objArr2[1] = Integer.valueOf(this.mInputConfiguration == null ? 0 : this.mInputConfiguration.getHeight());
                            objArr2[2] = Integer.valueOf(inputConfiguration.getWidth());
                            objArr2[3] = Integer.valueOf(inputConfiguration.getHeight());
                            objArr2[4] = format == 35 ? "yuv" : format == 256 ? "jpeg" : "heic";
                            objArr2[5] = Integer.valueOf(outputConfiguration2 == null ? 0 : outputConfiguration2.getWidth());
                            if (outputConfiguration2 != null) {
                                i = outputConfiguration2.getHeight();
                            }
                            objArr2[6] = Integer.valueOf(i);
                            objArr2[7] = Integer.valueOf(outputConfiguration.getWidth());
                            objArr2[8] = Integer.valueOf(outputConfiguration.getHeight());
                            Log.d(str2, String.format("recreate session. in: %dx%d->%dx%d %s out: %dx%d->%dx%d", objArr2));
                        }
                        if (z) {
                            this.mCreatingReprocessSession = true;
                            this.mInputConfiguration = inputConfiguration;
                            if (format != 35) {
                                this.mPicOutputConfiguration = outputConfiguration;
                                this.mYuvOutputConfiguration = new OutputConfiguration(outputConfiguration.getWidth(), outputConfiguration.getHeight(), 35);
                            } else {
                                this.mYuvOutputConfiguration = outputConfiguration;
                                this.mPicOutputConfiguration = new OutputConfiguration(outputConfiguration.getWidth(), outputConfiguration.getHeight(), 256);
                            }
                            createReprocessSession(this.mInputConfiguration, this.mYuvOutputConfiguration, this.mPicOutputConfiguration);
                        }
                        return z;
                    }
                    z = true;
                    if (z) {
                    }
                    return z;
                }
            }
            objArr = 1;
            if (objArr == null) {
            }
        }
    }

    @RequiresApi(api = 28)
    public static void createCaptureSessionWithSessionConfiguration(CameraDevice cameraDevice, InputConfiguration inputConfiguration, List<android.hardware.camera2.params.OutputConfiguration> list, CaptureRequest captureRequest, CameraCaptureSession.StateCallback stateCallback, final Handler handler) throws CameraAccessException {
        SessionConfiguration sessionConfiguration = new SessionConfiguration(0, list, handler == null ? null : new Executor() {
            /* class com.xiaomi.camera.imagecodec.impl.VirtualCameraReprocessor.AnonymousClass4 */

            public void execute(Runnable runnable) {
                handler.post(runnable);
            }
        }, stateCallback);
        if (inputConfiguration != null) {
            sessionConfiguration.setInputConfiguration(inputConfiguration);
        }
        sessionConfiguration.setSessionParameters(captureRequest);
        cameraDevice.createCaptureSession(sessionConfiguration);
    }

    @WorkerThread
    private void createReprocessSession(@NonNull InputConfiguration inputConfiguration, @NonNull OutputConfiguration outputConfiguration, @NonNull OutputConfiguration outputConfiguration2) {
        Log.d(TAG, String.format(Locale.ENGLISH, "createReprocessSession>>input[%dx%d] output[%dx%d@%d]", Integer.valueOf(inputConfiguration.getWidth()), Integer.valueOf(inputConfiguration.getHeight()), Integer.valueOf(outputConfiguration2.getWidth()), Integer.valueOf(outputConfiguration2.getHeight()), Integer.valueOf(outputConfiguration2.getFormat())));
        initYuvImageReader(outputConfiguration.getWidth(), outputConfiguration.getHeight());
        initPicImageReader(outputConfiguration2.getWidth(), outputConfiguration2.getHeight(), outputConfiguration2.getFormat());
        try {
            List<Surface> asList = (outputConfiguration.getWidth() <= 0 || this.mYuvImageReader == null) ? Arrays.asList(this.mPicImageReader.getSurface()) : Arrays.asList(this.mPicImageReader.getSurface(), this.mYuvImageReader.getSurface());
            InputConfiguration inputConfiguration2 = new InputConfiguration(inputConfiguration.getWidth(), inputConfiguration.getHeight(), inputConfiguration.getFormat());
            AnonymousClass5 r4 = new CameraCaptureSession.StateCallback() {
                /* class com.xiaomi.camera.imagecodec.impl.VirtualCameraReprocessor.AnonymousClass5 */

                public void onConfigureFailed(@NonNull CameraCaptureSession cameraCaptureSession) {
                    Log.d(VirtualCameraReprocessor.TAG, "onConfigureFailed");
                    synchronized (VirtualCameraReprocessor.this.mCameraLock) {
                        VirtualCameraReprocessor.this.mCreatingReprocessSession = false;
                        VirtualCameraReprocessor.this.mVTCaptureSession = null;
                        VirtualCameraReprocessor.this.mReprocessImageWriter = null;
                    }
                }

                public void onConfigured(@NonNull CameraCaptureSession cameraCaptureSession) {
                    Log.d(VirtualCameraReprocessor.TAG, "onConfigured>>");
                    synchronized (VirtualCameraReprocessor.this.mCameraLock) {
                        if (VirtualCameraReprocessor.this.mVTCameraDevice == null) {
                            Log.e(VirtualCameraReprocessor.TAG, "onConfigured: null camera device");
                            VirtualCameraReprocessor.this.mCreatingReprocessSession = false;
                            return;
                        }
                        VirtualCameraReprocessor.this.mVTCaptureSession = cameraCaptureSession;
                        VirtualCameraReprocessor.access$808(VirtualCameraReprocessor.this);
                        int sessionId = VirtualCameraReprocessor.getSessionId(cameraCaptureSession);
                        Log.d(VirtualCameraReprocessor.TAG, String.format("sessionId: %d %d", Integer.valueOf(VirtualCameraReprocessor.this.mCurrentSessionId), Integer.valueOf(sessionId)));
                        if (!(sessionId == -1 || VirtualCameraReprocessor.this.mCurrentSessionId == sessionId)) {
                            Log.w(VirtualCameraReprocessor.TAG, String.format("sessionId: %d->%d", Integer.valueOf(VirtualCameraReprocessor.this.mCurrentSessionId), Integer.valueOf(sessionId)));
                            VirtualCameraReprocessor.this.mCurrentSessionId = sessionId;
                        }
                        if (VirtualCameraReprocessor.this.mReprocessImageWriter != null) {
                            VirtualCameraReprocessor.this.mReprocessImageWriter.close();
                        }
                        Surface inputSurface = cameraCaptureSession.getInputSurface();
                        VirtualCameraReprocessor.this.mReprocessImageWriter = ImageWriter.newInstance(inputSurface, 2);
                        VirtualCameraReprocessor.this.mCreatingReprocessSession = false;
                        VirtualCameraReprocessor.this.sendReprocessRequest();
                        Log.d(VirtualCameraReprocessor.TAG, "onConfigured<<");
                    }
                }
            };
            if (Build.VERSION.SDK_INT >= 28) {
                ArrayList arrayList = new ArrayList(asList.size());
                for (Surface surface : asList) {
                    arrayList.add(new android.hardware.camera2.params.OutputConfiguration(surface));
                }
                CaptureRequest build = this.mVTCameraDevice.createCaptureRequest(2).build();
                Range range = (Range) build.get(CaptureRequest.CONTROL_AE_TARGET_FPS_RANGE);
                if (range != null) {
                    this.mSessionFpsRange = Range.create(range.getLower(), range.getUpper());
                }
                createCaptureSessionWithSessionConfiguration(this.mVTCameraDevice, inputConfiguration2, arrayList, build, r4, this.mCameraOperationHandler);
            } else {
                this.mVTCameraDevice.createReprocessableCaptureSession(inputConfiguration2, asList, r4, this.mCameraOperationHandler);
            }
        } catch (CameraAccessException e2) {
            e2.printStackTrace();
        }
        Log.d(TAG, "createReprocessSession<<");
    }

    private static byte[] getJpegData(Image image) {
        Image.Plane[] planes = image.getPlanes();
        if (planes.length <= 0) {
            return null;
        }
        ByteBuffer buffer = planes[0].getBuffer();
        byte[] bArr = new byte[buffer.remaining()];
        buffer.get(bArr);
        return bArr;
    }

    /* access modifiers changed from: private */
    public static int getSessionId(CameraCaptureSession cameraCaptureSession) {
        if (cameraCaptureSession == null) {
            return -1;
        }
        try {
            Field declaredField = Class.forName("android.hardware.camera2.impl.CameraCaptureSessionImpl").getDeclaredField("mId");
            declaredField.setAccessible(true);
            return declaredField.getInt(cameraCaptureSession);
        } catch (ClassNotFoundException | IllegalAccessException | NoSuchFieldException e2) {
            Log.w(TAG, "getSessionId: failed!", e2);
            return -1;
        }
    }

    @WorkerThread
    private void initPicImageReader(int i, int i2, int i3) {
        Log.d(TAG, "initPicImageReader>>");
        ImageReader imageReader = this.mPicImageReader;
        if (!(imageReader == null || (imageReader.getWidth() == i && this.mPicImageReader.getHeight() == i2 && this.mPicImageReader.getImageFormat() == i3))) {
            Log.d(TAG, "closing obsolete reprocess reader");
            this.mPicImageReader.close();
        }
        String str = TAG;
        Log.d(str, "initPicImageReader: " + i + "x" + i2 + "@" + i3);
        this.mPicImageReader = ImageReader.newInstance(i, i2, i3, 2);
        ImageReaderHelper.setImageReaderNameDepends(this.mPicImageReader, ImageReaderHelper.ImageReaderType.JPEG, false);
        this.mPicImageReader.setOnImageAvailableListener(new ImageReader.OnImageAvailableListener() {
            /* class com.xiaomi.camera.imagecodec.impl.VirtualCameraReprocessor.AnonymousClass6 */

            public void onImageAvailable(ImageReader imageReader) {
                Image acquireNextImage = imageReader.acquireNextImage();
                String str = VirtualCameraReprocessor.TAG;
                Log.d(str, "onImageAvailable: received reprocessed image " + acquireNextImage);
                synchronized (VirtualCameraReprocessor.this.mDataLock) {
                    if (VirtualCameraReprocessor.this.mCurrentProcessingData != null) {
                        VirtualCameraReprocessor.this.mCurrentProcessingData.getResultListener().onJpegImageAvailable(acquireNextImage, VirtualCameraReprocessor.this.mCurrentProcessingData.getImageTag());
                        String str2 = VirtualCameraReprocessor.TAG;
                        Object[] objArr = new Object[3];
                        objArr[0] = imageReader.getImageFormat() == 256 ? "jpeg" : "heic";
                        objArr[1] = VirtualCameraReprocessor.this.mCurrentProcessingData.getImageTag();
                        objArr[2] = Long.valueOf(System.currentTimeMillis() - VirtualCameraReprocessor.this.mReprocessStartTime);
                        Log.d(str2, String.format("%s return for %s. cost=%d", objArr));
                        VirtualCameraReprocessor.this.mCurrentProcessingData = null;
                    } else {
                        Log.w(VirtualCameraReprocessor.TAG, "onImageAvailable: null task!");
                    }
                }
                acquireNextImage.close();
                VirtualCameraReprocessor.this.sendReprocessRequest();
            }
        }, this.mCameraOperationHandler);
        Log.d(TAG, "initPicImageReader<<");
    }

    @WorkerThread
    private void initYuvImageReader(int i, int i2) {
        String str = TAG;
        Log.d(str, "initYuvImageReader>>" + i + "x" + i2);
        ImageReader imageReader = this.mYuvImageReader;
        if (!(imageReader == null || (imageReader.getWidth() == i && this.mYuvImageReader.getHeight() == i2))) {
            Log.d(TAG, "closing obsolete yuv reader");
            this.mYuvImageReader.close();
            this.mYuvImageReader = null;
        }
        if (i > 0 && i2 > 0) {
            this.mYuvImageReader = ImageReader.newInstance(i, i2, 35, 2);
            ImageReaderHelper.setImageReaderNameDepends(this.mYuvImageReader, ImageReaderHelper.ImageReaderType.YUV, false);
            this.mYuvImageReader.setOnImageAvailableListener(new ImageReader.OnImageAvailableListener() {
                /* class com.xiaomi.camera.imagecodec.impl.VirtualCameraReprocessor.AnonymousClass7 */

                public void onImageAvailable(ImageReader imageReader) {
                    Image acquireNextImage = imageReader.acquireNextImage();
                    long timeStamp = VirtualCameraReprocessor.this.mCurrentProcessingData.getTotalCaptureResult().getTimeStamp();
                    acquireNextImage.setTimestamp(timeStamp);
                    ImagePool.getInstance().queueImage(acquireNextImage);
                    Image image = ImagePool.getInstance().getImage(timeStamp);
                    String str = VirtualCameraReprocessor.TAG;
                    Log.d(str, "receive yuv image: " + image + " | " + timeStamp);
                    ImagePool.getInstance().holdImage(image);
                    synchronized (VirtualCameraReprocessor.this.mDataLock) {
                        VirtualCameraReprocessor.this.mCurrentProcessingData.getResultListener().onYuvAvailable(image, VirtualCameraReprocessor.this.mCurrentProcessingData.getImageTag());
                        Log.d(VirtualCameraReprocessor.TAG, String.format("yuv return for %s. cost=%d", VirtualCameraReprocessor.this.mCurrentProcessingData.getImageTag(), Long.valueOf(System.currentTimeMillis() - VirtualCameraReprocessor.this.mReprocessStartTime)));
                        VirtualCameraReprocessor.this.mCurrentProcessingData = null;
                    }
                    VirtualCameraReprocessor.this.sendReprocessRequest();
                }
            }, this.mCameraOperationHandler);
            Log.d(TAG, "initYuvImageReader<<");
        }
    }

    @WorkerThread
    @SuppressLint({"MissingPermission"})
    private void openVTCamera(String str) {
        String str2 = TAG;
        Log.d(str2, "openVTCamera: " + str);
        try {
            this.mCameraManager.openCamera(str, new CameraDevice.StateCallback() {
                /* class com.xiaomi.camera.imagecodec.impl.VirtualCameraReprocessor.AnonymousClass3 */

                public void onClosed(@NonNull CameraDevice cameraDevice) {
                    synchronized (VirtualCameraReprocessor.this.mCameraLock) {
                        String str = VirtualCameraReprocessor.TAG;
                        Log.d(str, "onClosed>>id=" + cameraDevice.getId());
                        VirtualCameraReprocessor.this.mCreatingReprocessSession = false;
                        if (cameraDevice == VirtualCameraReprocessor.this.mVTCameraDevice) {
                            String str2 = VirtualCameraReprocessor.TAG;
                            Log.d(str2, "onClosed: " + VirtualCameraReprocessor.this.mVTCameraDevice);
                            VirtualCameraReprocessor.this.mVTCameraDevice = null;
                        }
                        if (VirtualCameraReprocessor.this.mNeedReopenCamera) {
                            VirtualCameraReprocessor.this.sendReprocessRequest();
                            VirtualCameraReprocessor.this.mNeedReopenCamera = false;
                        }
                        Log.d(VirtualCameraReprocessor.TAG, "onClosed<<");
                    }
                }

                public void onDisconnected(@NonNull CameraDevice cameraDevice) {
                    boolean z;
                    String str = VirtualCameraReprocessor.TAG;
                    Log.d(str, "onDisconnected>>id=" + cameraDevice.getId());
                    synchronized (VirtualCameraReprocessor.this.mCameraLock) {
                        z = cameraDevice == VirtualCameraReprocessor.this.mVTCameraDevice;
                        cameraDevice.close();
                        VirtualCameraReprocessor.this.mCreatingReprocessSession = false;
                        if (z) {
                            String str2 = VirtualCameraReprocessor.TAG;
                            Log.d(str2, "onDisconnected: " + VirtualCameraReprocessor.this.mVTCameraDevice);
                            VirtualCameraReprocessor.this.mVTCameraDevice = null;
                        }
                    }
                    synchronized (VirtualCameraReprocessor.this.mDataLock) {
                        if (VirtualCameraReprocessor.this.mCurrentProcessingData != null && z) {
                            ICustomCaptureResult totalCaptureResult = VirtualCameraReprocessor.this.mCurrentProcessingData.getTotalCaptureResult();
                            String str3 = VirtualCameraReprocessor.TAG;
                            StringBuilder sb = new StringBuilder();
                            sb.append("onDisconnected: clear task with timestamp = ");
                            sb.append(totalCaptureResult != null ? totalCaptureResult.getTimeStamp() : 0);
                            Log.w(str3, sb.toString());
                            VirtualCameraReprocessor.this.mCurrentProcessingData = null;
                        }
                    }
                    Log.d(VirtualCameraReprocessor.TAG, "onDisconnected<<");
                }

                public void onError(@NonNull CameraDevice cameraDevice, int i) {
                    boolean z;
                    String str = VirtualCameraReprocessor.TAG;
                    Log.e(str, "onError>>id=" + cameraDevice.getId() + " error=" + i);
                    synchronized (VirtualCameraReprocessor.this.mCameraLock) {
                        z = cameraDevice == VirtualCameraReprocessor.this.mVTCameraDevice;
                        cameraDevice.close();
                        VirtualCameraReprocessor.this.mCreatingReprocessSession = false;
                        if (z) {
                            String str2 = VirtualCameraReprocessor.TAG;
                            Log.d(str2, "onError: " + VirtualCameraReprocessor.this.mVTCameraDevice);
                            VirtualCameraReprocessor.this.mVTCameraDevice = null;
                        }
                    }
                    synchronized (VirtualCameraReprocessor.this.mDataLock) {
                        if (VirtualCameraReprocessor.this.mCurrentProcessingData != null && z) {
                            ICustomCaptureResult totalCaptureResult = VirtualCameraReprocessor.this.mCurrentProcessingData.getTotalCaptureResult();
                            String str3 = VirtualCameraReprocessor.TAG;
                            StringBuilder sb = new StringBuilder();
                            sb.append("onError: clear task with timestamp = ");
                            sb.append(totalCaptureResult != null ? totalCaptureResult.getTimeStamp() : 0);
                            Log.w(str3, sb.toString());
                            VirtualCameraReprocessor.this.mCurrentProcessingData = null;
                            VirtualCameraReprocessor.this.sendReprocessRequest();
                        }
                    }
                    Log.e(VirtualCameraReprocessor.TAG, "onError<<");
                }

                public void onOpened(@NonNull CameraDevice cameraDevice) {
                    synchronized (VirtualCameraReprocessor.this.mCameraLock) {
                        String str = VirtualCameraReprocessor.TAG;
                        Log.d(str, "onOpened>>id=" + cameraDevice.getId());
                        VirtualCameraReprocessor.this.mVTCameraDevice = cameraDevice;
                        String str2 = VirtualCameraReprocessor.TAG;
                        Log.d(str2, "onOpened: " + VirtualCameraReprocessor.this.mVTCameraDevice);
                        VirtualCameraReprocessor.this.mCurrentSessionId = -1;
                        VirtualCameraReprocessor.this.mVTCaptureSession = null;
                        VirtualCameraReprocessor.this.mCreatingReprocessSession = false;
                        VirtualCameraReprocessor.this.sendReprocessRequest();
                        Log.d(VirtualCameraReprocessor.TAG, "onOpened<<");
                    }
                }
            }, this.mCameraOperationHandler);
        } catch (CameraAccessException e2) {
            Log.e(TAG, "openVTCamera: open camera failed! No permission Exception.", e2);
        }
    }

    /* access modifiers changed from: private */
    /* access modifiers changed from: public */
    private void releaseWakeLock() {
        if (this.mWakeLock.isHeld()) {
            Log.d(TAG, "releaseWakeLock");
            this.mWakeLock.release();
        }
    }

    /* access modifiers changed from: private */
    /* access modifiers changed from: public */
    /* JADX WARNING: Code restructure failed: missing block: B:10:0x0061, code lost:
        r9.mReprocessStartTime = java.lang.System.currentTimeMillis();
        r6 = r9.mCameraLock;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:11:0x0069, code lost:
        monitor-enter(r6);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:13:?, code lost:
        r0 = r9.mVTCameraDevice.createReprocessCaptureRequest(r1);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:14:0x0072, code lost:
        if (r9.mSessionFpsRange == null) goto L_0x007b;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:15:0x0074, code lost:
        r0.set(android.hardware.camera2.CaptureRequest.CONTROL_AE_TARGET_FPS_RANGE, r9.mSessionFpsRange);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:17:0x007d, code lost:
        if (35 == r3) goto L_0x00ef;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:18:0x007f, code lost:
        r0.addTarget(r9.mPicImageReader.getSurface());
        r0.set(android.hardware.camera2.CaptureRequest.JPEG_QUALITY, java.lang.Byte.valueOf(r2));
     */
    /* JADX WARNING: Code restructure failed: missing block: B:19:0x0091, code lost:
        if (r4 == false) goto L_0x00f8;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:20:0x0093, code lost:
        r1 = (java.lang.Integer) r1.get(android.hardware.camera2.CaptureResult.JPEG_ORIENTATION);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:21:0x009d, code lost:
        if (256 == r3) goto L_0x00f8;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:22:0x009f, code lost:
        if (r1 == null) goto L_0x00f8;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:24:0x00a5, code lost:
        if (r1.intValue() == 0) goto L_0x00f8;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:26:?, code lost:
        r0.set(com.xiaomi.camera.imagecodec.CaptureRequestVendorTags.XIAOMI_JPEG_ORIENTATION.getKey(), r1);
        r2 = com.xiaomi.camera.imagecodec.impl.VirtualCameraReprocessor.TAG;
        android.util.Log.d(r2, "reprocessImage: jpegOrientation=" + r1);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:27:0x00c9, code lost:
        r1 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:28:0x00ca, code lost:
        r2 = com.xiaomi.camera.imagecodec.impl.VirtualCameraReprocessor.TAG;
        android.util.Log.e(r2, "reprocessImage: failed to set XIAOMI_JPEG_ORIENTATION. " + r1.getMessage());
     */
    /* JADX WARNING: Code restructure failed: missing block: B:30:0x00ef, code lost:
        r0.addTarget(r9.mYuvImageReader.getSurface());
     */
    /* JADX WARNING: Code restructure failed: missing block: B:33:0x0139, code lost:
        r9 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:35:?, code lost:
        r9.printStackTrace();
     */
    @WorkerThread
    private void reprocessImage() {
        Image yuvImage;
        Object obj;
        CaptureRequest.Builder createReprocessCaptureRequest;
        Log.d(TAG, "reprocessImage>>");
        synchronized (this.mDataLock) {
            this.mCurrentProcessingData = this.mTaskDataList.poll();
            TotalCaptureResult totalCaptureResult = ICustomCaptureResult.toTotalCaptureResult(this.mCurrentProcessingData.getTotalCaptureResult(), this.mCurrentSessionId);
            if (totalCaptureResult == null) {
                Log.wtf(TAG, "reprocessImage<<null metadata!");
                return;
            }
            String str = TAG;
            Log.d(str, "reprocessImage: tag=" + this.mCurrentProcessingData.getImageTag());
            byte jpegQuality = (byte) this.mCurrentProcessingData.getJpegQuality();
            int outputFormat = this.mCurrentProcessingData.getOutputFormat();
            boolean isRotateOrientationToZero = this.mCurrentProcessingData.isRotateOrientationToZero();
            yuvImage = this.mCurrentProcessingData.getYuvImage();
        }
        createReprocessCaptureRequest.set(CaptureRequest.JPEG_ORIENTATION, 0);
        String str2 = TAG;
        Log.d(str2, "reprocessImage: " + yuvImage + " | " + yuvImage.getTimestamp());
        this.mReprocessImageWriter.queueInputImage(yuvImage);
        ImagePool.getInstance().releaseImage(yuvImage);
        this.mVTCaptureSession.capture(createReprocessCaptureRequest.build(), new CameraCaptureSession.CaptureCallback() {
            /* class com.xiaomi.camera.imagecodec.impl.VirtualCameraReprocessor.AnonymousClass2 */

            public void onCaptureBufferLost(CameraCaptureSession cameraCaptureSession, CaptureRequest captureRequest, Surface surface, long j) {
                String str = VirtualCameraReprocessor.TAG;
                Log.e(str, "onCaptureBufferLost: frameNo=" + j + " target=" + surface);
            }

            public void onCaptureCompleted(@NonNull CameraCaptureSession cameraCaptureSession, @NonNull CaptureRequest captureRequest, @NonNull TotalCaptureResult totalCaptureResult) {
                String str = VirtualCameraReprocessor.TAG;
                Log.d(str, "onCaptureCompleted: frameNo=" + totalCaptureResult.getFrameNumber() + " taskSize=" + VirtualCameraReprocessor.this.mTaskDataList.size());
            }

            public void onCaptureFailed(@NonNull CameraCaptureSession cameraCaptureSession, @NonNull CaptureRequest captureRequest, @NonNull CaptureFailure captureFailure) {
                String str = VirtualCameraReprocessor.TAG;
                Log.e(str, "onCaptureFailed: frameNo=" + captureFailure.getFrameNumber() + " reason=" + captureFailure.getReason() + " imageCaptured=" + captureFailure.wasImageCaptured());
                synchronized (VirtualCameraReprocessor.this.mDataLock) {
                    if (VirtualCameraReprocessor.this.mCurrentProcessingData != null) {
                        ReprocessData.OnDataAvailableListener resultListener = VirtualCameraReprocessor.this.mCurrentProcessingData.getResultListener();
                        resultListener.onError("" + captureFailure.getReason(), VirtualCameraReprocessor.this.mCurrentProcessingData.getImageTag());
                        VirtualCameraReprocessor.this.mCurrentProcessingData = null;
                    }
                }
                VirtualCameraReprocessor.this.sendReprocessRequest();
            }
        }, this.mCameraOperationHandler);
        Log.d(TAG, "reprocessImage<<");
        Log.d(TAG, "reprocessImage<<");
    }

    /* access modifiers changed from: private */
    /* access modifiers changed from: public */
    /* JADX WARNING: Code restructure failed: missing block: B:13:0x0024, code lost:
        r1 = r5.mDataLock;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:14:0x0026, code lost:
        monitor-enter(r1);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:17:0x002e, code lost:
        if (r5.mTaskDataList.isEmpty() == false) goto L_0x0040;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:18:0x0030, code lost:
        android.util.Log.d(com.xiaomi.camera.imagecodec.impl.VirtualCameraReprocessor.TAG, "sendReprocessRequest: idle. Try to close device 30s later.");
        r5.mReprocessHandler.sendEmptyMessageDelayed(2, 30000);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:19:0x003e, code lost:
        monitor-exit(r1);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:20:0x003f, code lost:
        return;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:22:0x0046, code lost:
        if (r5.mReprocessHandler.hasMessages(2) == false) goto L_0x004d;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:23:0x0048, code lost:
        r5.mReprocessHandler.removeMessages(2);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:24:0x004d, code lost:
        monitor-exit(r1);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:26:0x0055, code lost:
        if (r5.mReprocessHandler.hasMessages(1) == false) goto L_0x005f;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:27:0x0057, code lost:
        android.util.Log.d(com.xiaomi.camera.imagecodec.impl.VirtualCameraReprocessor.TAG, "sendReprocessRequest: busy");
     */
    /* JADX WARNING: Code restructure failed: missing block: B:28:0x005f, code lost:
        android.util.Log.d(com.xiaomi.camera.imagecodec.impl.VirtualCameraReprocessor.TAG, "sendReprocessRequest: send MSG_REPROCESS_IMG");
        r5.mReprocessHandler.sendEmptyMessageDelayed(1, 0);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:35:?, code lost:
        return;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:36:?, code lost:
        return;
     */
    @AnyThread
    private void sendReprocessRequest() {
        Log.i(TAG, "=============================================================");
        if (!this.mInitialized) {
            Log.w(TAG, "sendReprocessRequest: NOT initialized!");
            return;
        }
        synchronized (this.mCameraLock) {
            if (this.mCreatingReprocessSession) {
                Log.d(TAG, "sendReprocessRequest: creating session...");
            }
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:10:0x001b, code lost:
        monitor-enter(r2);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:13:0x001e, code lost:
        if (r3.mVTCaptureSession == null) goto L_0x002d;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:14:0x0020, code lost:
        r3.mVTCaptureSession.close();
        r3.mVTCaptureSession = null;
        r3.mPicImageReader = null;
        r3.mYuvImageReader = null;
        r3.mReprocessImageWriter = null;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:16:0x002f, code lost:
        if (r3.mVTCameraDevice == null) goto L_0x0038;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:17:0x0031, code lost:
        r3.mVTCameraDevice.close();
        r3.mVTCameraDevice = null;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:18:0x0038, code lost:
        monitor-exit(r2);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:19:0x0039, code lost:
        r0 = r3.mCameraOperationThread;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:20:0x003b, code lost:
        if (r0 == null) goto L_0x004e;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:21:0x003d, code lost:
        r0.quitSafely();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:23:?, code lost:
        r3.mCameraOperationThread.join();
        r3.mCameraOperationThread = null;
        r3.mCameraOperationHandler = null;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:24:0x004a, code lost:
        r0 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:25:0x004b, code lost:
        r0.printStackTrace();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:9:0x0019, code lost:
        r2 = r3.mCameraLock;
     */
    @Override // com.xiaomi.camera.imagecodec.Reprocessor
    @AnyThread
    public void deInit() {
        Log.d(TAG, "deInit>>");
        synchronized (this.mDataLock) {
            if (this.mInitialized) {
                this.mInitialized = false;
                this.mCameraManager = null;
                this.mCurrentProcessingData = null;
            } else {
                return;
            }
        }
        Log.d(TAG, "deInit<<");
        HandlerThread handlerThread = this.mReprocessThread;
        if (handlerThread != null) {
            handlerThread.quitSafely();
            try {
                this.mReprocessThread.join();
                this.mReprocessThread = null;
                this.mReprocessHandler = null;
            } catch (InterruptedException e2) {
                e2.printStackTrace();
            }
        }
        Log.d(TAG, "deInit<<");
    }

    @Override // com.xiaomi.camera.imagecodec.Reprocessor
    @AnyThread
    public void init(Context context) {
        Log.d(TAG, "init>>");
        synchronized (this.mDataLock) {
            if (!this.mInitialized) {
                this.mWakeLock = ((PowerManager) context.getSystemService("power")).newWakeLock(1, TAG);
                this.mWakeLock.setReferenceCounted(false);
                this.mCameraManager = (CameraManager) context.getSystemService("camera");
                this.mCameraOperationThread = new HandlerThread("CameraOperationThread");
                this.mCameraOperationThread.start();
                this.mCameraOperationHandler = new Handler(this.mCameraOperationThread.getLooper());
                this.mReprocessThread = new HandlerThread("JpegEncoderThread");
                this.mReprocessThread.start();
                this.mReprocessHandler = new ReprocessHandler(this.mReprocessThread.getLooper());
                this.mInitialized = true;
            }
        }
        Log.d(TAG, "init<<");
    }

    @Override // com.xiaomi.camera.imagecodec.Reprocessor
    public void setOutputPictureSpec(int i, int i2, int i3) {
        if (this.mPicOutputConfiguration == null) {
            String str = TAG;
            Log.d(str, "setOutputPictureSpec: " + i + "x" + i2 + "@" + Integer.toHexString(i3));
            this.mPicOutputConfiguration = new OutputConfiguration(i, i2, i3);
        }
    }

    @Override // com.xiaomi.camera.imagecodec.Reprocessor
    @AnyThread
    public void setVirtualCameraIds(@NonNull String str, @NonNull String str2) {
        Log.d(TAG, String.format("setVTCameraIds: backId=%s frontId=%s", str, str2));
        this.mBackVtCameraId = str;
        this.mFrontVtCameraId = str2;
    }

    @Override // com.xiaomi.camera.imagecodec.Reprocessor
    @AnyThread
    public void submit(ReprocessData reprocessData) {
        String str = TAG;
        Log.d(str, "submit: " + reprocessData.getImageTag() + ", yuv = " + reprocessData.getYuvImage() + ", fmt = " + reprocessData.getOutputFormat());
        if (reprocessData.getResultListener() == null) {
            Log.d(TAG, "submit: drop this request due to no callback was provided!");
        } else if (this.mInitialized) {
            acquireWakeLock();
            if (!reprocessData.isImageFromPool()) {
                Image yuvImage = reprocessData.getYuvImage();
                ImagePool.ImageFormat imageQueueKey = ImagePool.getInstance().toImageQueueKey(yuvImage);
                if (ImagePool.getInstance().isImageQueueFull(imageQueueKey, 2)) {
                    Log.w(TAG, "submit: wait image pool>>");
                    ImagePool.getInstance().waitIfImageQueueFull(imageQueueKey, 2, 0);
                    Log.w(TAG, "submit: wait image pool<<");
                }
                long timestamp = yuvImage.getTimestamp();
                ImagePool.getInstance().queueImage(yuvImage);
                Image image = ImagePool.getInstance().getImage(timestamp);
                String str2 = TAG;
                Log.d(str2, "submit: image: " + image + " | " + timestamp);
                reprocessData.setYuvImage(image);
                ImagePool.getInstance().holdImage(image);
            }
            synchronized (this.mDataLock) {
                this.mTaskDataList.add(reprocessData);
            }
            sendReprocessRequest();
        } else {
            throw new RuntimeException("NOT initialized. Call init() first!");
        }
    }
}
