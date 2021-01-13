package com.xiaomi.camera.core;

import android.hardware.camera2.params.OutputConfiguration;
import android.media.Image;
import android.media.ImageReader;
import android.os.Parcelable;
import b.c.a.c;
import com.android.camera.log.Log;
import com.xiaomi.camera.base.PerformanceTracker;
import com.xiaomi.camera.core.CaptureData;
import com.xiaomi.camera.core.ImageProcessor;
import com.xiaomi.camera.imagecodec.ImagePool;
import com.xiaomi.engine.BufferFormat;
import com.xiaomi.engine.FrameData;
import com.xiaomi.engine.TaskSession;
import com.xiaomi.protocol.ICustomCaptureResult;
import java.util.ArrayList;
import java.util.List;
import miui.reflect.Field;

public class DualCameraProcessor extends ImageProcessor {
    private static final String TAG = "DualCameraProcessor";
    private final boolean mIsFusionMode;

    DualCameraProcessor(ImageProcessor.ImageProcessorStatusCallback imageProcessorStatusCallback, BufferFormat bufferFormat) {
        super(imageProcessorStatusCallback, bufferFormat);
        this.mIsFusionMode = bufferFormat.getCameraCombinationMode() == 516;
    }

    private void processCaptureResult(ICustomCaptureResult iCustomCaptureResult, Image image, int i) {
        Parcelable results = iCustomCaptureResult.getResults();
        String str = TAG;
        Log.d(str, "processCaptureResult: cameraMetadataNative = " + results);
        String str2 = TAG;
        Log.d(str2, "processCaptureResult: image flag = " + i);
        String str3 = TAG;
        Log.d(str3, "processCaptureResult: image = " + image);
        String str4 = TAG;
        Log.d(str4, "processCaptureResult: image = " + image.getTimestamp());
        FrameData frameData = new FrameData(i, iCustomCaptureResult.getSequenceId(), iCustomCaptureResult.getFrameNumber(), results, iCustomCaptureResult.getParcelRequest(), image);
        frameData.setFrameCallback(new FrameData.FrameStatusCallback() {
            /* class com.xiaomi.camera.core.DualCameraProcessor.AnonymousClass4 */

            @Override // com.xiaomi.engine.FrameData.FrameStatusCallback
            public void onFrameImageClosed(Image image) {
                String str = DualCameraProcessor.TAG;
                Log.d(str, "onFrameImageClosed: " + image);
                ImageProcessor.ImageProcessorStatusCallback imageProcessorStatusCallback = DualCameraProcessor.this.mImageProcessorStatusCallback;
                if (imageProcessorStatusCallback != null) {
                    imageProcessorStatusCallback.onOriginalImageClosed(image);
                }
                ImagePool.getInstance().releaseImage(image);
            }
        });
        this.mTaskSession.processFrame(frameData, new TaskSession.FrameCallback() {
            /* class com.xiaomi.camera.core.DualCameraProcessor.AnonymousClass5 */

            @Override // com.xiaomi.engine.TaskSession.FrameCallback
            public void onFrameProcessed(int i, String str, Object obj) {
                String str2 = DualCameraProcessor.TAG;
                Log.d(str2, "onFrameProcessed: " + i);
            }
        });
    }

    @Override // com.xiaomi.camera.core.ImageProcessor
    public List<OutputConfiguration> configOutputConfigurations(BufferFormat bufferFormat) {
        ArrayList arrayList = new ArrayList();
        this.mEffectImageReaderHolder = ImageReader.newInstance(bufferFormat.getBufferWidth(), bufferFormat.getBufferHeight(), bufferFormat.getBufferFormat(), getImageBufferQueueSize());
        this.mEffectImageReaderHolder.setOnImageAvailableListener(new ImageReader.OnImageAvailableListener() {
            /* class com.xiaomi.camera.core.DualCameraProcessor.AnonymousClass1 */

            public void onImageAvailable(ImageReader imageReader) {
                Image acquireNextImage = imageReader.acquireNextImage();
                long timestamp = acquireNextImage.getTimestamp();
                PerformanceTracker.trackAlgorithmProcess("[  EFFECT]", 1);
                String str = DualCameraProcessor.TAG;
                Log.d(str, "onImageAvailable: effectImage received: " + timestamp);
                Image queueImageToPool = DualCameraProcessor.this.queueImageToPool(ImagePool.getInstance(), acquireNextImage);
                acquireNextImage.close();
                DualCameraProcessor.this.dispatchFilterTask(new ImageProcessor.FilterTaskData(queueImageToPool, 0, true));
                DualCameraProcessor.this.onProcessImageDone();
            }
        }, getImageReaderHandler());
        arrayList.add(new OutputConfiguration(0, this.mEffectImageReaderHolder.getSurface()));
        if (!this.mIsFusionMode) {
            this.mRawImageReaderHolder = ImageReader.newInstance(bufferFormat.getBufferWidth(), bufferFormat.getBufferHeight(), bufferFormat.getBufferFormat(), getImageBufferQueueSize());
            this.mRawImageReaderHolder.setOnImageAvailableListener(new ImageReader.OnImageAvailableListener() {
                /* class com.xiaomi.camera.core.DualCameraProcessor.AnonymousClass2 */

                public void onImageAvailable(ImageReader imageReader) {
                    Image acquireNextImage = imageReader.acquireNextImage();
                    long timestamp = acquireNextImage.getTimestamp();
                    PerformanceTracker.trackAlgorithmProcess("[     RAW]", 1);
                    String str = DualCameraProcessor.TAG;
                    Log.d(str, "onImageAvailable: rawImage received: " + timestamp);
                    Image queueImageToPool = DualCameraProcessor.this.queueImageToPool(ImagePool.getInstance(), acquireNextImage);
                    acquireNextImage.close();
                    DualCameraProcessor.this.dispatchFilterTask(new ImageProcessor.FilterTaskData(queueImageToPool, 1, true));
                }
            }, getImageReaderHandler());
            arrayList.add(new OutputConfiguration(1, this.mRawImageReaderHolder.getSurface()));
            int bufferWidth = bufferFormat.getBufferWidth();
            int bufferHeight = bufferFormat.getBufferHeight();
            if (!c.dh) {
                bufferWidth /= 2;
                bufferHeight /= 2;
            }
            this.mDepthImageReaderHolder = ImageReader.newInstance(bufferWidth, bufferHeight, 540422489, getImageBufferQueueSize());
            this.mDepthImageReaderHolder.setOnImageAvailableListener(new ImageReader.OnImageAvailableListener() {
                /* class com.xiaomi.camera.core.DualCameraProcessor.AnonymousClass3 */

                public void onImageAvailable(ImageReader imageReader) {
                    Image acquireNextImage = imageReader.acquireNextImage();
                    PerformanceTracker.trackAlgorithmProcess("[   DEPTH]", 1);
                    String str = DualCameraProcessor.TAG;
                    Log.d(str, "onImageAvailable: depthImage received: " + acquireNextImage.getTimestamp());
                    ImageProcessor.ImageProcessorStatusCallback imageProcessorStatusCallback = DualCameraProcessor.this.mImageProcessorStatusCallback;
                    if (imageProcessorStatusCallback != null) {
                        imageProcessorStatusCallback.onImageProcessed(acquireNextImage, 2, false);
                    }
                    acquireNextImage.close();
                    DualCameraProcessor.this.mNeedProcessDepthImageSize.getAndDecrement();
                    DualCameraProcessor.this.tryToStopWork();
                }
            }, getImageReaderHandler());
            arrayList.add(new OutputConfiguration(2, this.mDepthImageReaderHolder.getSurface()));
        }
        return arrayList;
    }

    @Override // com.xiaomi.camera.core.ImageProcessor
    public String getProcessorName() {
        return Field.DOUBLE_SIGNATURE_PRIMITIVE;
    }

    /* access modifiers changed from: package-private */
    @Override // com.xiaomi.camera.core.ImageProcessor
    public boolean isIdle() {
        String str = TAG;
        Log.d(str, "isIdle: " + this.mNeedProcessNormalImageSize.get() + " processing: " + this.mImageProcessorStatusCallback.isAnyFrontProcessing(this));
        return !this.mIsFusionMode ? this.mNeedProcessNormalImageSize.get() == 0 && this.mNeedProcessRawImageSize.get() == 0 && this.mNeedProcessDepthImageSize.get() == 0 : this.mNeedProcessNormalImageSize.get() == 0 && !this.mImageProcessorStatusCallback.isAnyFrontProcessing(this);
    }

    /* access modifiers changed from: package-private */
    @Override // com.xiaomi.camera.core.ImageProcessor
    public void processImage(List<CaptureData.CaptureDataBean> list) {
        if (list == null || list.size() == 0) {
            Log.w(TAG, "processImage: dataBeans is empty!");
            return;
        }
        onProcessImageStart();
        for (CaptureData.CaptureDataBean captureDataBean : list) {
            Image mainImage = captureDataBean.getMainImage();
            if (isImageValid(mainImage)) {
                Image subImage = captureDataBean.getSubImage();
                if (isImageValid(subImage)) {
                    PerformanceTracker.trackAlgorithmProcess("[ORIGINAL]", 0);
                    ICustomCaptureResult result = captureDataBean.getResult();
                    processCaptureResult(result, mainImage, 0);
                    processCaptureResult(result, subImage, 1);
                }
            }
        }
    }
}
