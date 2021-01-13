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
import android.util.Size;
import androidx.annotation.CallSuper;
import androidx.annotation.NonNull;
import b.c.a.c;
import com.android.camera.log.Log;
import com.xiaomi.camera.base.ImageUtil;
import com.xiaomi.camera.core.CaptureData;
import com.xiaomi.camera.imagecodec.ImagePool;
import com.xiaomi.engine.BufferFormat;
import com.xiaomi.engine.TaskSession;
import com.xiaomi.protocol.ICustomCaptureResult;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.atomic.AtomicInteger;

public abstract class ImageProcessor {
    private static final int DEFAULT_IMAGE_BUFFER_QUEUE_SIZE = 4;
    private static final int MSG_IMAGE_DOFILTER = 2;
    private static final int MSG_IMAGE_RECEIVED = 1;
    private static final String TAG = "ImageProcessor";
    protected ConditionVariable mBlockVariable;
    ImageReader mDepthImageReaderHolder;
    ImageReader mEffectImageReaderHolder;
    AtomicInteger mEngineProcessingImageSize;
    protected FilterProcessor mFilterProcessor;
    private int mImageBufferQueueSize;
    ImageProcessorStatusCallback mImageProcessorStatusCallback;
    private Handler mImageReaderHandler;
    private HandlerThread mImageReaderThread;
    private final boolean mIsBokenMode;
    private boolean mIsNeedStopWork;
    private int mMaxParallelRequestNumber = 10;
    AtomicInteger mNeedProcessDepthImageSize;
    AtomicInteger mNeedProcessNormalImageSize;
    AtomicInteger mNeedProcessRawImageSize;
    ImageReader mRawImageReaderHolder;
    private LinkedList<List<CaptureData.CaptureDataBean>> mTaskDataList;
    private Object mTaskDataLock;
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

        boolean isAnyFrontProcessing(ImageProcessor imageProcessor);

        void onImageProcessFailed(Image image, String str);

        void onImageProcessStart(long j);

        void onImageProcessed(Image image, int i, boolean z);

        void onOriginalImageClosed(Image image);
    }

    public ImageProcessor(ImageProcessorStatusCallback imageProcessorStatusCallback, BufferFormat bufferFormat) {
        boolean z = false;
        this.mEngineProcessingImageSize = new AtomicInteger(0);
        this.mNeedProcessNormalImageSize = new AtomicInteger(0);
        this.mNeedProcessRawImageSize = new AtomicInteger(0);
        this.mNeedProcessDepthImageSize = new AtomicInteger(0);
        this.mImageBufferQueueSize = 4;
        this.mBlockVariable = new ConditionVariable();
        this.mTaskDataLock = new Object();
        this.mTaskDataList = new LinkedList<>();
        Log.d(TAG, "create ImageProcessor");
        this.mImageProcessorStatusCallback = imageProcessorStatusCallback;
        String format = String.format(Locale.ENGLISH, "_%s_%dx%d_%d", getProcessorName(), Integer.valueOf(bufferFormat.getBufferWidth()), Integer.valueOf(bufferFormat.getBufferHeight()), Integer.valueOf(hashCode()));
        this.mWorkThread = new HandlerThread("WorkerThread" + format);
        this.mImageReaderThread = new HandlerThread("ReaderThread" + format);
        this.mFilterProcessor = new FilterProcessor(this.mBlockVariable);
        this.mFilterProcessor.init(new Size(bufferFormat.getBufferWidth(), bufferFormat.getBufferHeight()));
        this.mIsBokenMode = bufferFormat.getGraphDescriptor().getOperationModeID() == 32770 ? true : z;
    }

    private boolean isAlive() {
        HandlerThread handlerThread = this.mWorkThread;
        return handlerThread != null && handlerThread.isAlive();
    }

    private void mockProcessImage(List<CaptureData.CaptureDataBean> list) {
        Log.d(TAG, "prop skip imageprocessor");
        if (list == null || list.size() == 0) {
            Log.w(TAG, "processImage: dataBeans is empty!");
            return;
        }
        onProcessImageStart();
        CaptureData.CaptureDataBean captureDataBean = list.get(0);
        for (int i = 1; i < list.size(); i++) {
            Image mainImage = list.get(i).getMainImage();
            if (mainImage != null) {
                mainImage.close();
                ImageProcessorStatusCallback imageProcessorStatusCallback = this.mImageProcessorStatusCallback;
                if (imageProcessorStatusCallback != null) {
                    imageProcessorStatusCallback.onOriginalImageClosed(mainImage);
                }
                ImagePool.getInstance().releaseImage(mainImage);
            }
            Image subImage = list.get(i).getSubImage();
            if (subImage != null) {
                subImage.close();
                ImageProcessorStatusCallback imageProcessorStatusCallback2 = this.mImageProcessorStatusCallback;
                if (imageProcessorStatusCallback2 != null) {
                    imageProcessorStatusCallback2.onOriginalImageClosed(subImage);
                }
                ImagePool.getInstance().releaseImage(subImage);
            }
            Image tuningImage = list.get(i).getTuningImage();
            if (tuningImage != null) {
                tuningImage.close();
                ImageProcessorStatusCallback imageProcessorStatusCallback3 = this.mImageProcessorStatusCallback;
                if (imageProcessorStatusCallback3 != null) {
                    imageProcessorStatusCallback3.onOriginalImageClosed(tuningImage);
                }
            }
        }
        Image subImage2 = captureDataBean.getSubImage();
        if (subImage2 != null) {
            subImage2.close();
            ImageProcessorStatusCallback imageProcessorStatusCallback4 = this.mImageProcessorStatusCallback;
            if (imageProcessorStatusCallback4 != null) {
                imageProcessorStatusCallback4.onOriginalImageClosed(subImage2);
            }
            ImagePool.getInstance().releaseImage(subImage2);
        }
        Image tuningImage2 = captureDataBean.getTuningImage();
        if (tuningImage2 != null) {
            tuningImage2.close();
            ImageProcessorStatusCallback imageProcessorStatusCallback5 = this.mImageProcessorStatusCallback;
            if (imageProcessorStatusCallback5 != null) {
                imageProcessorStatusCallback5.onOriginalImageClosed(tuningImage2);
            }
        }
        Image mainImage2 = captureDataBean.getMainImage();
        Image queueImageToPool = queueImageToPool(ImagePool.getInstance(), mainImage2);
        mainImage2.close();
        ImageProcessorStatusCallback imageProcessorStatusCallback6 = this.mImageProcessorStatusCallback;
        if (imageProcessorStatusCallback6 != null) {
            imageProcessorStatusCallback6.onOriginalImageClosed(mainImage2);
        }
        ImagePool.getInstance().releaseImage(mainImage2);
        dispatchFilterTask(new FilterTaskData(queueImageToPool, 0, true));
        onProcessImageDone();
    }

    /* access modifiers changed from: private */
    /* access modifiers changed from: public */
    private void processImageInner(List<CaptureData.CaptureDataBean> list) {
        if (PostProcessor.SKIP_IMAGEPROCESSOR) {
            mockProcessImage(list);
        } else {
            processImage(list);
        }
    }

    private void sendProcessImageMessage() {
        if (isAlive()) {
            Message obtainMessage = this.mWorkHandler.obtainMessage();
            obtainMessage.what = 1;
            this.mWorkHandler.sendMessage(obtainMessage);
            Log.c(TAG, "sendProcessImageMessage");
            return;
        }
        Object[] objArr = new Object[1];
        HandlerThread handlerThread = this.mWorkThread;
        objArr[0] = handlerThread == null ? "Thread" : handlerThread.getName();
        throw new RuntimeException(String.format("%s already die!", objArr));
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

    public void dispatchTask(@NonNull List<CaptureData.CaptureDataBean> list) {
        synchronized (this.mTaskDataLock) {
            this.mTaskDataList.add(list);
            if (!isAlgorithmEngineBusy()) {
                sendProcessImageMessage();
            } else {
                String str = TAG;
                Log.v(str, "dispatchTask: taskSize = " + this.mTaskDataList.size());
            }
        }
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
                if (c.Um() && (1 == parallelTaskData.getAlgoType() || 3 == parallelTaskData.getAlgoType())) {
                    TotalCaptureResult totalCaptureResult = ICustomCaptureResult.toTotalCaptureResult(parallelTaskData.getCaptureResult(), 0);
                    parallelTaskData.getCameraId();
                    Integer num = (Integer) totalCaptureResult.get(CaptureResult.SENSOR_SENSITIVITY);
                    if (num != null) {
                        num.intValue();
                    }
                }
                if (!PostProcessor.SKIP_RENDER) {
                    String str = TAG;
                    Log.d(str, "doFilter: " + timestamp + "/" + i);
                    this.mFilterProcessor.doFilterSync(parallelTaskData, image, i);
                } else {
                    Log.d(TAG, "prop skip render");
                }
            } else if (parallelTaskData == null) {
                String str2 = TAG;
                Log.w(str2, "doFilter: no task data found for image " + timestamp);
            }
            this.mImageProcessorStatusCallback.onImageProcessed(image, i, filterTaskData.isPoolImage);
        } else if (filterTaskData.isPoolImage) {
            String str3 = TAG;
            Log.w(str3, "doFilter: release pool image " + timestamp);
            image.close();
            ImagePool.getInstance().releaseImage(image);
        }
        if (!PostProcessor.SKIP_IMAGEPROCESSOR) {
            if (i == 0) {
                this.mNeedProcessNormalImageSize.getAndDecrement();
            } else if (i == 1) {
                this.mNeedProcessRawImageSize.getAndDecrement();
            } else if (i != 2) {
                String str4 = TAG;
                Log.e(str4, "invalid target: " + i);
            } else {
                this.mNeedProcessDepthImageSize.getAndDecrement();
            }
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

    public String getProcessorName() {
        return TAG;
    }

    public TaskSession getTaskSession() {
        return this.mTaskSession;
    }

    public boolean isAlgorithmEngineBusy() {
        return this.mEngineProcessingImageSize.get() >= this.mMaxParallelRequestNumber;
    }

    public boolean isBokekMode() {
        return this.mIsBokenMode;
    }

    /* access modifiers changed from: package-private */
    public abstract boolean isIdle();

    /* access modifiers changed from: protected */
    public boolean isImageValid(Image image) {
        if (image == null) {
            return false;
        }
        try {
            image.getHardwareBuffer();
            return true;
        } catch (IllegalArgumentException | IllegalStateException e2) {
            Log.e(TAG, e2.getMessage(), e2);
            return false;
        }
    }

    /* access modifiers changed from: protected */
    public void onProcessImageDone() {
        this.mEngineProcessingImageSize.getAndDecrement();
        if (!isAlgorithmEngineBusy()) {
            synchronized (this.mTaskDataLock) {
                int size = this.mTaskDataList.size();
                if (size > 0) {
                    String str = TAG;
                    Log.v(str, "onProcessImageDone: taskSize = " + size);
                    sendProcessImageMessage();
                }
            }
        }
    }

    /* access modifiers changed from: protected */
    public void onProcessImageStart() {
        this.mEngineProcessingImageSize.getAndIncrement();
    }

    /* access modifiers changed from: package-private */
    public abstract void processImage(List<CaptureData.CaptureDataBean> list);

    /* access modifiers changed from: protected */
    public Image queueImageToPool(ImagePool imagePool, Image image) {
        return ImageUtil.queueImageToPool(imagePool, image, 2);
    }

    @CallSuper
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

    public void setMaxParallelRequestNumber(int i) {
        if (i > 0) {
            this.mMaxParallelRequestNumber = i;
        }
    }

    public void setTaskSession(@NonNull TaskSession taskSession) {
        this.mTaskSession = taskSession;
    }

    public void startWork() {
        this.mWorkThread.start();
        this.mWorkHandler = new Handler(this.mWorkThread.getLooper()) {
            /* class com.xiaomi.camera.core.ImageProcessor.AnonymousClass1 */

            public void handleMessage(Message message) {
                List list;
                int i = message.what;
                if (i == 1) {
                    synchronized (ImageProcessor.this.mTaskDataLock) {
                        list = (List) ImageProcessor.this.mTaskDataList.poll();
                    }
                    if (list != null) {
                        String str = ImageProcessor.TAG;
                        Log.c(str, "processImage: ts = " + ((CaptureData.CaptureDataBean) list.get(0)).getResult().getTimeStamp());
                        ImageProcessor.this.processImageInner(list);
                    }
                } else if (i != 2) {
                    String str2 = ImageProcessor.TAG;
                    Log.d(str2, "handleMessage: unknown message: " + message.what);
                } else {
                    ImageProcessor.this.doFilter((FilterTaskData) message.obj);
                }
            }
        };
        this.mImageReaderThread.start();
        this.mImageReaderHandler = new Handler(this.mImageReaderThread.getLooper());
        Log.d(TAG, String.format(Locale.ENGLISH, "startWork: %s started", this.mWorkThread.getName()));
    }

    public synchronized void stopWork() {
        this.mWorkThread.quitSafely();
        Log.d(TAG, String.format(Locale.ENGLISH, "destroy ImageProcessor: %s stopped", this.mWorkThread.getName()));
        if (this.mWorkHandler != null) {
            this.mWorkHandler.removeCallbacksAndMessages(null);
            this.mWorkHandler = null;
        }
        this.mImageReaderThread.quitSafely();
        if (this.mImageReaderHandler != null) {
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
