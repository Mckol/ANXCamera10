package com.xiaomi.camera.core;

import android.hardware.camera2.CaptureResult;
import android.hardware.camera2.TotalCaptureResult;
import android.hardware.camera2.params.OutputConfiguration;
import android.media.Image;
import android.media.ImageReader;
import android.os.ConditionVariable;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Message;
import android.support.annotation.NonNull;
import android.util.Size;
import com.android.camera.log.Log;
import com.mi.config.b;
import com.xiaomi.camera.core.CaptureData.CaptureDataBean;
import com.xiaomi.camera.imagecodec.ImagePool;
import com.xiaomi.camera.imagecodec.ImagePool.ImageFormat;
import com.xiaomi.engine.BufferFormat;
import com.xiaomi.engine.TaskSession;
import com.xiaomi.protocol.ICustomCaptureResult;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.atomic.AtomicInteger;
import miui.reflect.Field;

public abstract class ImageProcessor {
    private static final int DEFAULT_IMAGE_BUFFER_QUEUE_SIZE = 4;
    private static final int MSG_IMAGE_DOFILTER = 2;
    private static final int MSG_IMAGE_RECEIVED = 1;
    /* access modifiers changed from: private */
    public static final String TAG = "ImageProcessor";
    protected ConditionVariable mBlockVariable = new ConditionVariable();
    ImageReader mDepthImageReaderHolder;
    ImageReader mEffectImageReaderHolder;
    protected FilterProcessor mFilterProcessor;
    private int mImageBufferQueueSize = 4;
    ImageProcessorStatusCallback mImageProcessorStatusCallback;
    private Handler mImageReaderHandler;
    private HandlerThread mImageReaderThread;
    boolean mIsBokehMode;
    private boolean mIsNeedStopWork;
    AtomicInteger mNeedProcessDepthImageSize = new AtomicInteger(0);
    AtomicInteger mNeedProcessNormalImageSize = new AtomicInteger(0);
    AtomicInteger mNeedProcessRawImageSize = new AtomicInteger(0);
    ImageReader mRawImageReaderHolder;
    TaskSession mTaskSession;
    private Handler mWorkHandler;
    private HandlerThread mWorkThread;

    public class FilterTaskData {
        public Image image;
        public boolean isPoolImage;
        public int target;

        public FilterTaskData(Image image2, int i, boolean z) {
            this.image = image2;
            this.target = i;
            this.isPoolImage = z;
        }
    }

    public interface ImageProcessorStatusCallback {
        ParallelTaskData getParallelTaskData(long j);

        void onImageProcessFailed(Image image, String str);

        void onImageProcessStart(long j);

        void onImageProcessed(Image image, int i, boolean z);

        void onOriginalImageClosed(Image image);
    }

    public ImageProcessor(ImageProcessorStatusCallback imageProcessorStatusCallback, boolean z, BufferFormat bufferFormat) {
        this.mImageProcessorStatusCallback = imageProcessorStatusCallback;
        this.mIsBokehMode = z;
        Locale locale = Locale.ENGLISH;
        Object[] objArr = new Object[4];
        objArr[0] = this.mIsBokehMode ? Field.DOUBLE_SIGNATURE_PRIMITIVE : "S";
        objArr[1] = Integer.valueOf(bufferFormat.getBufferWidth());
        objArr[2] = Integer.valueOf(bufferFormat.getBufferHeight());
        objArr[3] = Integer.valueOf(hashCode());
        String format = String.format(locale, "_%s_%dx%d_%d", objArr);
        StringBuilder sb = new StringBuilder();
        sb.append("WorkerThread");
        sb.append(format);
        this.mWorkThread = new HandlerThread(sb.toString());
        StringBuilder sb2 = new StringBuilder();
        sb2.append("ReaderThread");
        sb2.append(format);
        this.mImageReaderThread = new HandlerThread(sb2.toString());
        this.mFilterProcessor = new FilterProcessor(this.mBlockVariable);
        this.mFilterProcessor.init(new Size(bufferFormat.getBufferWidth(), bufferFormat.getBufferHeight()));
    }

    private boolean isAlive() {
        HandlerThread handlerThread = this.mWorkThread;
        return handlerThread != null && handlerThread.isAlive();
    }

    public abstract List<OutputConfiguration> configOutputConfigurations(BufferFormat bufferFormat);

    /* access modifiers changed from: protected */
    public void dispatchFilterTask(@NonNull FilterTaskData filterTaskData) {
        if (isAlive()) {
            Message obtainMessage = this.mWorkHandler.obtainMessage();
            obtainMessage.what = 2;
            obtainMessage.obj = filterTaskData;
            this.mWorkHandler.sendMessage(obtainMessage);
            return;
        }
        throw new RuntimeException("Thread already die!");
    }

    public void dispatchTask(@NonNull List<CaptureDataBean> list) {
        if (isAlive()) {
            Message obtainMessage = this.mWorkHandler.obtainMessage();
            obtainMessage.what = 1;
            obtainMessage.obj = list;
            this.mWorkHandler.sendMessage(obtainMessage);
            return;
        }
        throw new RuntimeException("Thread already die!");
    }

    /* access modifiers changed from: protected */
    public void doFilter(@NonNull FilterTaskData filterTaskData) {
        Image image = filterTaskData.image;
        long timestamp = image.getTimestamp();
        int i = filterTaskData.target;
        ImageProcessorStatusCallback imageProcessorStatusCallback = this.mImageProcessorStatusCallback;
        if (imageProcessorStatusCallback != null) {
            ParallelTaskData parallelTaskData = imageProcessorStatusCallback.getParallelTaskData(timestamp);
            if (parallelTaskData != null && (i == 0 || 1 == i)) {
                if (b.isMTKPlatform() && (1 == parallelTaskData.getAlgoType() || 3 == parallelTaskData.getAlgoType())) {
                    TotalCaptureResult totalCaptureResult = ICustomCaptureResult.toTotalCaptureResult(parallelTaskData.getCaptureResult(), 0);
                    parallelTaskData.getCameraId();
                    Integer num = (Integer) totalCaptureResult.get(CaptureResult.SENSOR_SENSITIVITY);
                    if (num != null) {
                        num.intValue();
                    }
                }
                String str = TAG;
                StringBuilder sb = new StringBuilder();
                sb.append("doFilter: ");
                sb.append(timestamp);
                sb.append("/");
                sb.append(i);
                Log.d(str, sb.toString());
                this.mFilterProcessor.doFilterSync(parallelTaskData, image, i);
            } else if (parallelTaskData == null) {
                String str2 = TAG;
                StringBuilder sb2 = new StringBuilder();
                sb2.append("doFilter: no task data found for image ");
                sb2.append(timestamp);
                Log.w(str2, sb2.toString());
            }
            this.mImageProcessorStatusCallback.onImageProcessed(image, i, filterTaskData.isPoolImage);
        } else if (filterTaskData.isPoolImage) {
            String str3 = TAG;
            StringBuilder sb3 = new StringBuilder();
            sb3.append("doFilter: release pool image ");
            sb3.append(timestamp);
            Log.w(str3, sb3.toString());
            image.close();
            ImagePool.getInstance().releaseImage(image);
        }
        if (i == 0) {
            this.mNeedProcessNormalImageSize.getAndDecrement();
        } else if (i != 1) {
            if (i == 2) {
                this.mNeedProcessDepthImageSize.getAndDecrement();
            }
            String str4 = TAG;
            StringBuilder sb4 = new StringBuilder();
            sb4.append("invalid target: ");
            sb4.append(i);
            Log.e(str4, sb4.toString());
        } else {
            this.mNeedProcessRawImageSize.getAndDecrement();
        }
        tryToStopWork();
    }

    /* access modifiers changed from: protected */
    public int getImageBufferQueueSize() {
        return this.mImageBufferQueueSize;
    }

    /* access modifiers changed from: protected */
    public Handler getImageReaderHandler() {
        return this.mImageReaderHandler;
    }

    public int getProcessingRequestNumber() {
        return this.mNeedProcessNormalImageSize.get();
    }

    /* access modifiers changed from: 0000 */
    public abstract boolean isIdle();

    /* access modifiers changed from: 0000 */
    public abstract void processImage(List<CaptureDataBean> list);

    /* access modifiers changed from: protected */
    public Image queueImageToPool(ImagePool imagePool, Image image) {
        ImageFormat imageQueueKey = imagePool.toImageQueueKey(image);
        if (imagePool.isImageQueueFull(imageQueueKey, 2)) {
            Log.w(TAG, "queueImageToPool: wait E");
            imagePool.waitIfImageQueueFull(imageQueueKey, 2, 0);
            Log.w(TAG, "queueImageToPool: wait X");
        }
        long timestamp = image.getTimestamp();
        imagePool.queueImage(image);
        Image image2 = imagePool.getImage(timestamp);
        imagePool.holdImage(image2);
        return image2;
    }

    public void releaseResource() {
        this.mImageProcessorStatusCallback = null;
        ImageReader imageReader = this.mEffectImageReaderHolder;
        if (imageReader != null) {
            imageReader.close();
            this.mEffectImageReaderHolder = null;
        }
        ImageReader imageReader2 = this.mRawImageReaderHolder;
        if (imageReader2 != null) {
            imageReader2.close();
            this.mRawImageReaderHolder = null;
        }
        ImageReader imageReader3 = this.mDepthImageReaderHolder;
        if (imageReader3 != null) {
            imageReader3.close();
            this.mDepthImageReaderHolder = null;
        }
    }

    public void setImageBufferQueueSize(int i) {
        this.mImageBufferQueueSize = i;
    }

    public void setTaskSession(@NonNull TaskSession taskSession) {
        this.mTaskSession = taskSession;
    }

    public void startWork() {
        this.mWorkThread.start();
        this.mWorkHandler = new Handler(this.mWorkThread.getLooper()) {
            public void handleMessage(Message message) {
                int i = message.what;
                if (i == 1) {
                    ImageProcessor.this.processImage((List) message.obj);
                } else if (i != 2) {
                    String access$000 = ImageProcessor.TAG;
                    StringBuilder sb = new StringBuilder();
                    sb.append("handleMessage: unknown message: ");
                    sb.append(message.what);
                    Log.d(access$000, sb.toString());
                } else {
                    ImageProcessor.this.doFilter((FilterTaskData) message.obj);
                }
            }
        };
        this.mImageReaderThread.start();
        this.mImageReaderHandler = new Handler(this.mImageReaderThread.getLooper());
        Log.d(TAG, String.format(Locale.ENGLISH, "startWork: %s started", new Object[]{this.mWorkThread.getName()}));
    }

    public synchronized void stopWork() {
        this.mWorkThread.quitSafely();
        Log.d(TAG, String.format(Locale.ENGLISH, "stopWork: %s stopped", new Object[]{this.mWorkThread.getName()}));
        if (this.mWorkHandler != null) {
            this.mWorkHandler.removeCallbacksAndMessages(null);
            this.mWorkHandler = null;
        }
        this.mImageReaderThread.quitSafely();
        if (this.mImageReaderThread != null) {
            this.mImageReaderHandler.removeCallbacksAndMessages(null);
            this.mImageReaderHandler = null;
        }
        if (this.mFilterProcessor != null) {
            this.mFilterProcessor = null;
        }
        if (this.mTaskSession != null) {
            this.mTaskSession.close();
        }
        releaseResource();
    }

    public synchronized void stopWorkWhenIdle() {
        Log.d(TAG, "stopWorkWhenIdle");
        this.mIsNeedStopWork = true;
        tryToStopWork();
    }

    public synchronized void tryToStopWork() {
        if (this.mIsNeedStopWork && isIdle()) {
            stopWork();
        }
    }
}
