package com.xiaomi.camera.imagecodec.impl;

import android.annotation.TargetApi;
import android.content.Context;
import android.hardware.camera2.params.InputConfiguration;
import android.media.Image;
import android.media.ImageReader;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Message;
import android.os.Parcelable;
import android.os.PowerManager;
import android.support.annotation.NonNull;
import android.support.annotation.WorkerThread;
import android.util.Log;
import com.xiaomi.camera.imagecodec.ImagePool;
import com.xiaomi.camera.imagecodec.OutputConfiguration;
import com.xiaomi.camera.imagecodec.ReprocessData;
import com.xiaomi.camera.imagecodec.Reprocessor;
import com.xiaomi.camera.isp.IspBuffer;
import com.xiaomi.camera.isp.IspInterface;
import com.xiaomi.camera.isp.IspStream;
import com.xiaomi.protocol.ICustomCaptureResult;
import java.nio.ByteBuffer;
import java.util.LinkedList;
import java.util.Locale;

public class IspInterfaceReprocessor implements Reprocessor {
    private static final int MAX_IMAGE_BUFFER_SIZE = 2;
    private static final String TAG = "IspInterfaceReprocessor";
    public static final Reprocessor.Singleton<IspInterfaceReprocessor> sInstance = new Reprocessor.Singleton<IspInterfaceReprocessor>() {
        /* class com.xiaomi.camera.imagecodec.impl.IspInterfaceReprocessor.AnonymousClass1 */

        /* access modifiers changed from: protected */
        @Override // com.xiaomi.camera.imagecodec.Reprocessor.Singleton
        public IspInterfaceReprocessor create() {
            return new IspInterfaceReprocessor();
        }
    };
    private final Object mCodecLock;
    private Handler mCodecOperationHandler;
    private HandlerThread mCodecOperationThread;
    private ReprocessData mCurrentProcessingData;
    private final Object mDataLock;
    private boolean mInitialized;
    private InputConfiguration mInputConfiguration;
    private IspInterface mIspInterface;
    private OutputConfiguration mJpegOutputConfiguration;
    private ImageReader mPicImageReader;
    private long mReprocessStartTime;
    private Handler mRequestDispatchHandler;
    private HandlerThread mRequestDispatchThread;
    private LinkedList<ReprocessData> mTaskDataList;
    private PowerManager.WakeLock mWakeLock;
    private ImageReader mYuvImageReader;
    private OutputConfiguration mYuvOutputConfiguration;

    private class ReprocessHandler extends Handler {
        private static final int MSG_DESTROY_ENCODER = 2;
        private static final int MSG_REPROCESS_IMAGE = 1;

        ReprocessHandler(Looper looper) {
            super(looper);
        }

        public void handleMessage(Message message) {
            int i = message.what;
            if (i == 1) {
                Log.d(IspInterfaceReprocessor.TAG, "recv MSG_REPROCESS_IMAGE");
                if (IspInterfaceReprocessor.this.checkConditionIsReady()) {
                    IspInterfaceReprocessor.this.reprocessImage();
                }
            } else if (i != 2) {
                super.handleMessage(message);
            } else {
                Log.d(IspInterfaceReprocessor.TAG, "recv MSG_DESTROY_ENCODER");
                synchronized (IspInterfaceReprocessor.this.mCodecLock) {
                    if (IspInterfaceReprocessor.this.mIspInterface != null) {
                        String str = IspInterfaceReprocessor.TAG;
                        Log.d(str, "release current codec: " + IspInterfaceReprocessor.this.mIspInterface);
                        IspInterfaceReprocessor.this.mIspInterface.release();
                        IspInterfaceReprocessor.this.mIspInterface = null;
                    }
                }
                IspInterfaceReprocessor.this.releaseWakeLock();
            }
        }
    }

    private IspInterfaceReprocessor() {
        this.mCodecLock = new Object();
        this.mDataLock = new Object();
        this.mTaskDataList = new LinkedList<>();
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
        android.util.Log.w(com.xiaomi.camera.imagecodec.impl.IspInterfaceReprocessor.TAG, "checkConditionIsReady: ignore null request!");
     */
    /* JADX WARNING: Code restructure failed: missing block: B:12:0x0023, code lost:
        return false;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:13:0x0024, code lost:
        createIspInterfaceIfNeed(r1);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:14:0x0028, code lost:
        return true;
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

    @WorkerThread
    private void createIspInterface(@NonNull InputConfiguration inputConfiguration, int[] iArr, @NonNull OutputConfiguration outputConfiguration, @NonNull OutputConfiguration outputConfiguration2, @NonNull Parcelable parcelable) {
        Log.d(TAG, String.format(Locale.ENGLISH, "createIspInterface>>input[%dx%d] output[%dx%d@%d]", Integer.valueOf(inputConfiguration.getWidth()), Integer.valueOf(inputConfiguration.getHeight()), Integer.valueOf(outputConfiguration2.getWidth()), Integer.valueOf(outputConfiguration2.getHeight()), Integer.valueOf(outputConfiguration2.getFormat())));
        initYuvImageReader(outputConfiguration.getWidth(), outputConfiguration.getHeight());
        initPicImageReader(outputConfiguration2.getWidth(), outputConfiguration2.getHeight(), outputConfiguration2.getFormat());
        IspInterface ispInterface = this.mIspInterface;
        if (ispInterface != null) {
            ispInterface.release();
        }
        this.mIspInterface = IspInterface.create(new IspStream(1, inputConfiguration.getWidth(), inputConfiguration.getHeight(), iArr, inputConfiguration.getFormat()), this.mYuvImageReader.getSurface(), this.mPicImageReader.getSurface(), parcelable);
        Log.d(TAG, "createIspInterface<<");
    }

    @WorkerThread
    private void createIspInterfaceIfNeed(@NonNull ReprocessData reprocessData) {
        Image yuvImage = reprocessData.getYuvImage();
        InputConfiguration inputConfiguration = new InputConfiguration(yuvImage.getWidth(), yuvImage.getHeight(), yuvImage.getFormat());
        Image.Plane[] planes = yuvImage.getPlanes();
        boolean z = true;
        int i = 0;
        if (planes == null || planes.length <= 0) {
            Object[] objArr = new Object[1];
            objArr[0] = planes == null ? "null" : Integer.valueOf(planes.length);
            throw new IllegalArgumentException(String.format("invalid input image with %s planes", objArr));
        }
        int[] iArr = new int[planes.length];
        for (int i2 = 0; i2 < iArr.length; i2++) {
            iArr[i2] = planes[i2].getRowStride();
        }
        OutputConfiguration outputConfiguration = new OutputConfiguration(reprocessData.getOutputWidth(), reprocessData.getOutputHeight(), reprocessData.getOutputFormat());
        int format = outputConfiguration.getFormat();
        OutputConfiguration outputConfiguration2 = format != 35 ? this.mJpegOutputConfiguration : this.mYuvOutputConfiguration;
        if (this.mIspInterface != null) {
            if (!inputConfiguration.equals(this.mInputConfiguration) || !outputConfiguration.equals(outputConfiguration2)) {
                String str = TAG;
                Object[] objArr2 = new Object[9];
                InputConfiguration inputConfiguration2 = this.mInputConfiguration;
                objArr2[0] = Integer.valueOf(inputConfiguration2 == null ? 0 : inputConfiguration2.getWidth());
                InputConfiguration inputConfiguration3 = this.mInputConfiguration;
                objArr2[1] = Integer.valueOf(inputConfiguration3 == null ? 0 : inputConfiguration3.getHeight());
                objArr2[2] = Integer.valueOf(inputConfiguration.getWidth());
                objArr2[3] = Integer.valueOf(inputConfiguration.getHeight());
                objArr2[4] = format == 35 ? "yuv" : "jpeg";
                objArr2[5] = Integer.valueOf(outputConfiguration2 == null ? 0 : outputConfiguration2.getWidth());
                if (outputConfiguration2 != null) {
                    i = outputConfiguration2.getHeight();
                }
                objArr2[6] = Integer.valueOf(i);
                objArr2[7] = Integer.valueOf(outputConfiguration.getWidth());
                objArr2[8] = Integer.valueOf(outputConfiguration.getHeight());
                Log.d(str, String.format("recreate session. in: %dx%d->%dx%d %s out: %dx%d->%dx%d", objArr2));
            } else {
                z = false;
            }
        }
        if (z) {
            this.mInputConfiguration = inputConfiguration;
            if (format != 35) {
                this.mJpegOutputConfiguration = outputConfiguration;
                this.mYuvOutputConfiguration = new OutputConfiguration(outputConfiguration.getWidth(), outputConfiguration.getHeight(), 35);
            } else {
                this.mYuvOutputConfiguration = outputConfiguration;
                this.mJpegOutputConfiguration = new OutputConfiguration(outputConfiguration.getWidth(), outputConfiguration.getHeight(), 256);
            }
            ICustomCaptureResult totalCaptureResult = reprocessData.getTotalCaptureResult();
            createIspInterface(this.mInputConfiguration, iArr, this.mYuvOutputConfiguration, this.mJpegOutputConfiguration, totalCaptureResult != null ? totalCaptureResult.getResults() : null);
        }
    }

    /* access modifiers changed from: private */
    public static byte[] getJpegData(Image image) {
        Image.Plane[] planes = image.getPlanes();
        if (planes.length <= 0) {
            return null;
        }
        ByteBuffer buffer = planes[0].getBuffer();
        byte[] bArr = new byte[buffer.remaining()];
        buffer.get(bArr);
        return bArr;
    }

    @WorkerThread
    @TargetApi(19)
    private void initPicImageReader(int i, int i2, int i3) {
        String str = TAG;
        Log.d(str, "initPicImageReader>>" + i + "x" + i2 + "@" + i3);
        ImageReader imageReader = this.mPicImageReader;
        if (!(imageReader == null || (imageReader.getWidth() == i && this.mPicImageReader.getHeight() == i2 && this.mPicImageReader.getImageFormat() == i3))) {
            Log.d(TAG, "closing obsolete reprocess reader");
            this.mPicImageReader.close();
        }
        this.mPicImageReader = ImageReader.newInstance(i, i2, i3, 2);
        this.mPicImageReader.setOnImageAvailableListener(new ImageReader.OnImageAvailableListener() {
            /* class com.xiaomi.camera.imagecodec.impl.IspInterfaceReprocessor.AnonymousClass3 */

            public void onImageAvailable(ImageReader imageReader) {
                Image acquireNextImage = imageReader.acquireNextImage();
                String str = IspInterfaceReprocessor.TAG;
                Log.d(str, "onImageAvailable: received reprocessed image " + acquireNextImage);
                byte[] jpegData = IspInterfaceReprocessor.getJpegData(acquireNextImage);
                acquireNextImage.close();
                synchronized (IspInterfaceReprocessor.this.mDataLock) {
                    if (IspInterfaceReprocessor.this.mCurrentProcessingData != null) {
                        Image yuvImage = IspInterfaceReprocessor.this.mCurrentProcessingData.getYuvImage();
                        if (yuvImage != null) {
                            yuvImage.close();
                            ImagePool.getInstance().releaseImage(yuvImage);
                        }
                        IspInterfaceReprocessor.this.mCurrentProcessingData.getResultListener().onJpegAvailable(jpegData, IspInterfaceReprocessor.this.mCurrentProcessingData.getImageTag());
                        Log.d(IspInterfaceReprocessor.TAG, String.format("jpeg return for %s. cost=%d", IspInterfaceReprocessor.this.mCurrentProcessingData.getImageTag(), Long.valueOf(System.currentTimeMillis() - IspInterfaceReprocessor.this.mReprocessStartTime)));
                        IspInterfaceReprocessor.this.mCurrentProcessingData = null;
                    } else {
                        Log.w(IspInterfaceReprocessor.TAG, "onImageAvailable: null task!");
                    }
                }
                IspInterfaceReprocessor.this.sendReprocessRequest();
            }
        }, this.mCodecOperationHandler);
        Log.d(TAG, "initPicImageReader: X");
    }

    @WorkerThread
    @TargetApi(19)
    private void initYuvImageReader(int i, int i2) {
        String str = TAG;
        Log.d(str, "initYuvImageReader>>" + i + "x" + i2);
        ImageReader imageReader = this.mYuvImageReader;
        if (!(imageReader == null || (imageReader.getWidth() == i && this.mYuvImageReader.getHeight() == i2))) {
            Log.d(TAG, "closing obsolete yuv reader");
            this.mYuvImageReader.close();
            this.mYuvImageReader = null;
        }
        if (i <= 0 || i2 <= 0) {
            Log.w(TAG, "initYuvImageReader<<invalid size");
            return;
        }
        this.mYuvImageReader = ImageReader.newInstance(i, i2, 35, 2);
        this.mYuvImageReader.setOnImageAvailableListener(new ImageReader.OnImageAvailableListener() {
            /* class com.xiaomi.camera.imagecodec.impl.IspInterfaceReprocessor.AnonymousClass4 */

            public void onImageAvailable(ImageReader imageReader) {
                Image acquireNextImage = imageReader.acquireNextImage();
                long timeStamp = IspInterfaceReprocessor.this.mCurrentProcessingData.getTotalCaptureResult().getTimeStamp();
                acquireNextImage.setTimestamp(timeStamp);
                ImagePool.getInstance().queueImage(acquireNextImage);
                Image image = ImagePool.getInstance().getImage(timeStamp);
                String str = IspInterfaceReprocessor.TAG;
                Log.d(str, "receive yuv image: " + image + " | " + timeStamp);
                ImagePool.getInstance().holdImage(image);
                synchronized (IspInterfaceReprocessor.this.mDataLock) {
                    IspInterfaceReprocessor.this.mCurrentProcessingData.getResultListener().onYuvAvailable(image, IspInterfaceReprocessor.this.mCurrentProcessingData.getImageTag());
                    Log.d(IspInterfaceReprocessor.TAG, String.format("yuv return for %s. cost=%d", IspInterfaceReprocessor.this.mCurrentProcessingData.getImageTag(), Long.valueOf(System.currentTimeMillis() - IspInterfaceReprocessor.this.mReprocessStartTime)));
                    IspInterfaceReprocessor.this.mCurrentProcessingData = null;
                }
                IspInterfaceReprocessor.this.sendReprocessRequest();
            }
        }, this.mCodecOperationHandler);
        Log.d(TAG, "initYuvImageReader<<");
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
    /* JADX WARNING: Code restructure failed: missing block: B:10:0x005f, code lost:
        r0 = new java.util.ArrayList(1);
        r0.add(r4);
        r4 = new com.xiaomi.camera.isp.IspRequest((int) r1.getFrameNumber(), r1.getResults(), r0);
        r0 = com.xiaomi.camera.imagecodec.impl.IspInterfaceReprocessor.TAG;
        android.util.Log.d(r0, "reprocessImage: requestFrameNo=" + r1.getFrameNumber());
        r8.mReprocessStartTime = java.lang.System.currentTimeMillis();
        r0 = r8.mCodecOperationHandler;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:11:0x0098, code lost:
        if (r0 == null) goto L_0x00a2;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:12:0x009a, code lost:
        r0.post(new com.xiaomi.camera.imagecodec.impl.IspInterfaceReprocessor.AnonymousClass2(r8));
     */
    /* JADX WARNING: Code restructure failed: missing block: B:13:0x00a2, code lost:
        android.util.Log.d(com.xiaomi.camera.imagecodec.impl.IspInterfaceReprocessor.TAG, "reprocessImage<<");
     */
    /* JADX WARNING: Code restructure failed: missing block: B:14:0x00a9, code lost:
        return;
     */
    @WorkerThread
    private void reprocessImage() {
        Log.d(TAG, "reprocessImage>>");
        synchronized (this.mDataLock) {
            this.mCurrentProcessingData = this.mTaskDataList.poll();
            String str = TAG;
            Log.d(str, "reprocessImage: tag=" + this.mCurrentProcessingData.getImageTag());
            ICustomCaptureResult totalCaptureResult = this.mCurrentProcessingData.getTotalCaptureResult();
            if (totalCaptureResult == null) {
                Log.wtf(TAG, "reprocessImage<<null metadata!");
                return;
            }
            final int outputFormat = this.mCurrentProcessingData.getOutputFormat();
            final Image yuvImage = this.mCurrentProcessingData.getYuvImage();
            IspBuffer ispBuffer = new IspBuffer(this.mIspInterface.getInputStreamId(), yuvImage, this.mCurrentProcessingData.getTuningImage());
        }
    }

    /* access modifiers changed from: private */
    /* access modifiers changed from: public */
    /* JADX WARNING: Code restructure failed: missing block: B:17:0x0044, code lost:
        if (r5.mRequestDispatchHandler.hasMessages(1) == false) goto L_0x004e;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:18:0x0046, code lost:
        android.util.Log.d(com.xiaomi.camera.imagecodec.impl.IspInterfaceReprocessor.TAG, "sendReprocessRequest: BUSY");
     */
    /* JADX WARNING: Code restructure failed: missing block: B:19:0x004e, code lost:
        android.util.Log.d(com.xiaomi.camera.imagecodec.impl.IspInterfaceReprocessor.TAG, "sendReprocessRequest: send MSG_REPROCESS_IMAGE");
        r5.mRequestDispatchHandler.sendEmptyMessageDelayed(1, 0);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:23:?, code lost:
        return;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:24:?, code lost:
        return;
     */
    private void sendReprocessRequest() {
        Log.i(TAG, "=============================================================");
        if (!this.mInitialized) {
            Log.w(TAG, "sendReprocessRequest: NOT initialized!");
            return;
        }
        synchronized (this.mDataLock) {
            if (this.mTaskDataList.isEmpty()) {
                Log.d(TAG, "sendReprocessRequest: idle. Try to close device 30s later.");
                this.mRequestDispatchHandler.sendEmptyMessageDelayed(2, 30000);
            } else if (this.mRequestDispatchHandler.hasMessages(2)) {
                this.mRequestDispatchHandler.removeMessages(2);
            }
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:10:0x0019, code lost:
        monitor-enter(r2);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:12:?, code lost:
        r3.mPicImageReader = null;
        r3.mYuvImageReader = null;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:13:0x0020, code lost:
        if (r3.mIspInterface == null) goto L_0x0029;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:14:0x0022, code lost:
        r3.mIspInterface.release();
        r3.mIspInterface = null;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:15:0x0029, code lost:
        monitor-exit(r2);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:16:0x002a, code lost:
        r0 = r3.mCodecOperationThread;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:17:0x002c, code lost:
        if (r0 == null) goto L_0x003f;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:18:0x002e, code lost:
        r0.quitSafely();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:20:?, code lost:
        r3.mCodecOperationThread.join();
        r3.mCodecOperationThread = null;
        r3.mCodecOperationHandler = null;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:21:0x003b, code lost:
        r0 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:22:0x003c, code lost:
        r0.printStackTrace();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:9:0x0017, code lost:
        r2 = r3.mCodecLock;
     */
    @Override // com.xiaomi.camera.imagecodec.Reprocessor
    public void deInit() {
        Log.d(TAG, "deInit>>");
        synchronized (this.mDataLock) {
            if (this.mInitialized) {
                this.mInitialized = false;
                this.mCurrentProcessingData = null;
            } else {
                return;
            }
        }
        Log.d(TAG, "deInit<<");
        HandlerThread handlerThread = this.mRequestDispatchThread;
        if (handlerThread != null) {
            handlerThread.quitSafely();
            try {
                this.mRequestDispatchThread.join();
                this.mRequestDispatchThread = null;
                this.mRequestDispatchHandler = null;
            } catch (InterruptedException e2) {
                e2.printStackTrace();
            }
        }
        Log.d(TAG, "deInit<<");
    }

    @Override // com.xiaomi.camera.imagecodec.Reprocessor
    public void init(Context context) {
        Log.d(TAG, "init>>");
        synchronized (this.mDataLock) {
            if (!this.mInitialized) {
                this.mWakeLock = ((PowerManager) context.getSystemService("power")).newWakeLock(1, TAG);
                this.mWakeLock.setReferenceCounted(false);
                this.mCodecOperationThread = new HandlerThread("IspHandlerThread");
                this.mCodecOperationThread.start();
                this.mCodecOperationHandler = new Handler(this.mCodecOperationThread.getLooper());
                this.mRequestDispatchThread = new HandlerThread("RequestDispatcher");
                this.mRequestDispatchThread.start();
                this.mRequestDispatchHandler = new ReprocessHandler(this.mRequestDispatchThread.getLooper());
                this.mInitialized = true;
            }
        }
        Log.d(TAG, "init<<");
    }

    @Override // com.xiaomi.camera.imagecodec.Reprocessor
    public void setOutputPictureSpec(int i, int i2, int i3) {
        if (i3 != 256) {
            throw new IllegalArgumentException("Only supports JPEG encoding");
        } else if (this.mJpegOutputConfiguration == null) {
            String str = TAG;
            Log.d(str, "setOutputPictureSpec: " + i + "x" + i2);
            this.mJpegOutputConfiguration = new OutputConfiguration(i, i2, i3);
        }
    }

    @Override // com.xiaomi.camera.imagecodec.Reprocessor
    public void setVirtualCameraIds(String str, String str2) {
    }

    @Override // com.xiaomi.camera.imagecodec.Reprocessor
    public void submit(ReprocessData reprocessData) {
        String str = TAG;
        Log.d(str, "submit: " + reprocessData.getImageTag());
        if (!this.mInitialized) {
            throw new RuntimeException("NOT initialized. Call init() first!");
        } else if (reprocessData.getResultListener() == null) {
            Log.d(TAG, "submit: drop this request due to no callback was provided!");
        } else {
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
        }
    }
}
