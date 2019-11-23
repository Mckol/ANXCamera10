package com.xiaomi.camera.core;

import android.content.Context;
import android.hardware.camera2.CameraCharacteristics;
import android.hardware.camera2.TotalCaptureResult;
import android.media.Image;
import android.media.ImageReader;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.RemoteException;
import android.support.annotation.NonNull;
import android.view.Surface;
import com.android.camera.Util;
import com.android.camera.log.Log;
import com.android.camera.storage.ImageSaver;
import com.mi.config.b;
import com.ss.android.ttve.common.TEDefine;
import com.xiaomi.camera.base.CommonUtil;
import com.xiaomi.camera.base.ImageUtil;
import com.xiaomi.camera.base.PerformanceTracker;
import com.xiaomi.camera.core.CaptureData;
import com.xiaomi.camera.core.ImageProcessor;
import com.xiaomi.camera.core.ParallelDataZipper;
import com.xiaomi.camera.imagecodec.ImagePool;
import com.xiaomi.camera.imagecodec.ReprocessData;
import com.xiaomi.camera.imagecodec.ReprocessorFactory;
import com.xiaomi.engine.BufferFormat;
import com.xiaomi.engine.MiCameraAlgo;
import com.xiaomi.engine.ResultData;
import com.xiaomi.engine.TaskSession;
import com.xiaomi.protocol.ICustomCaptureResult;
import com.xiaomi.protocol.IImageReaderParameterSets;
import com.xiaomi.protocol.ISessionStatusCallBackListener;
import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import miui.os.Build;

public class PostProcessor {
    private static final int MSG_RESULT_FLAW = 0;
    /* access modifiers changed from: private */
    public static final String TAG = PostProcessor.class.getSimpleName();
    /* access modifiers changed from: private */
    public CaptureDataListener mCaptureDataListener = new CaptureDataListener() {
        public void onCaptureDataAvailable(@NonNull CaptureData captureData) {
            long captureTimestamp = captureData.getCaptureTimestamp();
            int algoType = captureData.getAlgoType();
            if (2 == algoType || ((3 == algoType && PostProcessor.this.isSRRequireReprocess()) || 5 == algoType)) {
                CaptureData.CaptureDataBean multiFrameProcessResult = captureData.getMultiFrameProcessResult();
                if (multiFrameProcessResult != null) {
                    ParallelTaskData parallelTaskData = (ParallelTaskData) PostProcessor.this.mParallelTaskHashMap.get(Long.valueOf(captureTimestamp));
                    long timeStamp = multiFrameProcessResult.getResult().getTimeStamp();
                    Log.d(PostProcessor.TAG, "[1] onCaptureDataAvailable: timestamp: " + captureTimestamp + " | " + timeStamp);
                    if (timeStamp != captureTimestamp) {
                        parallelTaskData.setTimestamp(timeStamp);
                        PostProcessor.this.mParallelTaskHashMap.remove(Long.valueOf(captureTimestamp));
                        PostProcessor.this.mParallelTaskHashMap.put(Long.valueOf(timeStamp), parallelTaskData);
                    }
                    if (5 == algoType && parallelTaskData.getDataParameter().isSaveGroupshotPrimitive()) {
                        for (int i = 0; i < captureData.getCaptureDataBeanList().size(); i++) {
                            ParallelTaskData cloneTaskData = parallelTaskData.cloneTaskData(i);
                            long timeStamp2 = captureData.getCaptureDataBeanList().get(i).getResult().getTimeStamp();
                            if (timeStamp2 == timeStamp) {
                                timeStamp2++;
                            }
                            cloneTaskData.setTimestamp(timeStamp2);
                            PostProcessor.this.mParallelTaskHashMap.put(Long.valueOf(timeStamp2), cloneTaskData);
                            Log.d(PostProcessor.TAG, "[1] onCaptureDataAvailable: add " + timeStamp2);
                        }
                    }
                    captureData.getCaptureDataBeanList().add(multiFrameProcessResult);
                } else {
                    throw new RuntimeException("No multi-frame process result!");
                }
            }
            if (4 == algoType) {
                if (Build.IS_DEBUGGABLE) {
                    Log.d(PostProcessor.TAG, "onCaptureDataAvailable: start process multi-shot image...");
                }
                CaptureData.CaptureDataBean captureDataBean = captureData.getCaptureDataBeanList().get(0);
                ICustomCaptureResult result = captureDataBean.getResult();
                Image mainImage = captureDataBean.getMainImage();
                ParallelTaskData parallelTaskData2 = (ParallelTaskData) PostProcessor.this.mParallelTaskHashMap.get(Long.valueOf(captureTimestamp));
                if (parallelTaskData2 != null) {
                    parallelTaskData2.setCaptureResult(result);
                    PostProcessor.this.mImageProcessorStatusCb.onImageProcessStart(mainImage.getTimestamp());
                    PostProcessor.this.mImageProcessorStatusCb.onImageProcessed(mainImage, 0, false);
                } else {
                    Log.e(PostProcessor.TAG, "[1] onCaptureDataAvailable: no captureResult " + captureTimestamp);
                }
                mainImage.close();
                onOriginalImageClosed(mainImage);
                return;
            }
            List<CaptureData.CaptureDataBean> captureDataBeanList = captureData.getCaptureDataBeanList();
            if (captureDataBeanList != null && !captureDataBeanList.isEmpty()) {
                for (CaptureData.CaptureDataBean next : captureDataBeanList) {
                    if (next.isFirstResult()) {
                        ParallelTaskData parallelTaskData3 = (ParallelTaskData) PostProcessor.this.mParallelTaskHashMap.get(Long.valueOf(next.getResult().getTimeStamp()));
                        if (parallelTaskData3 != null) {
                            parallelTaskData3.setCaptureResult(next.getResult());
                        } else {
                            Log.e(PostProcessor.TAG, "[1] onCaptureDataAvailable: no task data with timestamp " + r3, new RuntimeException());
                            Image mainImage2 = next.getMainImage();
                            mainImage2.close();
                            onOriginalImageClosed(mainImage2);
                            ImagePool.getInstance().releaseImage(mainImage2);
                            Image subImage = next.getSubImage();
                            if (subImage != null) {
                                subImage.close();
                                onOriginalImageClosed(subImage);
                                ImagePool.getInstance().releaseImage(subImage);
                            }
                        }
                    }
                }
                ImageProcessor imageProcessor = captureData.getImageProcessor();
                if (imageProcessor != PostProcessor.this.mImageProcessor) {
                    Log.w(PostProcessor.TAG, "[1] onCaptureDataAvailable: image processor switched");
                }
                imageProcessor.dispatchTask(captureDataBeanList);
            } else if (!Build.IS_DEBUGGABLE) {
                Log.e(PostProcessor.TAG, "[1] onCaptureDataAvailable: There are no result to process!");
            } else {
                throw new RuntimeException("There are no result to process!");
            }
        }

        public void onOriginalImageClosed(Image image) {
            if (PostProcessor.this.mImageMemoryManager != null && image != null) {
                String access$100 = PostProcessor.TAG;
                Log.d(access$100, "onOriginalImageClosed: " + image);
                PostProcessor.this.mImageMemoryManager.releaseAnImage(image);
            }
        }
    };
    private CaptureStatusListener mCaptureStatusListener = new CaptureStatusListener();
    /* access modifiers changed from: private */
    public ImageMemoryManager mImageMemoryManager;
    /* access modifiers changed from: private */
    public ImageProcessor mImageProcessor;
    private List<ImageProcessor> mImageProcessorList = new ArrayList();
    /* access modifiers changed from: private */
    public ImageProcessor.ImageProcessorStatusCallback mImageProcessorStatusCb = new ImageProcessor.ImageProcessorStatusCallback() {
        public ParallelTaskData getParallelTaskData(long j) {
            return (ParallelTaskData) PostProcessor.this.mParallelTaskHashMap.get(Long.valueOf(j));
        }

        public void onImageProcessFailed(Image image, String str) {
            String access$100 = PostProcessor.TAG;
            StringBuilder sb = new StringBuilder();
            sb.append("onImageProcessFailed: image=");
            Object obj = image;
            if (image == null) {
                obj = TEDefine.FACE_BEAUTY_NULL;
            }
            sb.append(obj);
            sb.append(" reason=");
            sb.append(str);
            Log.d(access$100, sb.toString());
        }

        public void onImageProcessStart(long j) {
            if (PostProcessor.this.mPostProcessStatusCallback != null) {
                String access$100 = PostProcessor.TAG;
                Log.d(access$100, "onImageProcessStart: get parallelTaskData: " + j);
                PostProcessor.this.mPostProcessStatusCallback.onImagePostProcessStart((ParallelTaskData) PostProcessor.this.mParallelTaskHashMap.get(Long.valueOf(j)));
            }
        }

        public void onImageProcessed(Image image, int i, boolean z) {
            long timestamp;
            String str = timestamp + File.separator + i;
            Log.d(PostProcessor.TAG, "[2] onImageProcessed: " + image + " | " + str);
            if (i == 2) {
                PerformanceTracker.trackJpegReprocess(i, 0);
                PostProcessor.this.mJpegEncoderListener.onJpegAvailable(ImageUtil.getFirstPlane(image), str);
                return;
            }
            ParallelTaskData parallelTaskData = (ParallelTaskData) PostProcessor.this.mParallelTaskHashMap.get(Long.valueOf(timestamp));
            if (parallelTaskData != null) {
                ICustomCaptureResult captureResult = parallelTaskData.getCaptureResult();
                Log.d(PostProcessor.TAG, "[2] onImageProcessed: captureResult = " + captureResult.getResults());
                ParallelTaskDataParameter dataParameter = parallelTaskData.getDataParameter();
                ReprocessData reprocessData = new ReprocessData(image, str, captureResult, dataParameter.isFrontCamera(), dataParameter.getOutputSize().getWidth(), dataParameter.getOutputSize().getHeight(), PostProcessor.this.mJpegEncoderListener);
                reprocessData.setFrontMirror(dataParameter.isMirror());
                reprocessData.setJpegQuality(dataParameter.getJpegQuality());
                reprocessData.setImageFromPool(z);
                PerformanceTracker.trackJpegReprocess(i, 0);
                try {
                    ReprocessorFactory.getReprocessor().submit(reprocessData);
                } catch (Exception e) {
                    PostProcessor.this.mJpegEncoderListener.onError("nativeDetachImage failed for image!!!", str);
                }
            } else {
                throw new RuntimeException("no parallelTaskData with timestamp " + timestamp);
            }
        }

        public void onOriginalImageClosed(Image image) {
            PostProcessor.this.mCaptureDataListener.onOriginalImageClosed(image);
            String access$100 = PostProcessor.TAG;
            Log.d(access$100, "onOriginalImageClosed: " + image);
        }
    };
    private List<ImageReader> mImageReaderList = new ArrayList();
    /* access modifiers changed from: private */
    public ImageSaver mImageSaver;
    /* access modifiers changed from: private */
    public ReprocessData.OnDataAvailableListener mJpegEncoderListener = new ReprocessData.OnDataAvailableListener() {
        public void onError(String str, String str2) {
            String[] split = str2.split(File.separator);
            long parseLong = Long.parseLong(split[0]);
            int parseInt = Integer.parseInt(split[1]);
            String access$100 = PostProcessor.TAG;
            Log.e(access$100, "[3] onError: " + parseLong + " | " + parseInt + " | " + str);
            PerformanceTracker.trackJpegReprocess(parseInt, 1);
            ParallelTaskData parallelTaskData = (ParallelTaskData) PostProcessor.this.mParallelTaskHashMap.get(Long.valueOf(parseLong));
            if (parallelTaskData != null) {
                parallelTaskData.releaseImageData();
                PostProcessor.this.mParallelTaskHashMap.remove(Long.valueOf(parseLong));
                String access$1002 = PostProcessor.TAG;
                Log.e(access$1002, "[3] onError: remove task " + parseLong + " | " + parseInt);
            }
            PostProcessor.this.tryToCloseSession();
        }

        public void onJpegAvailable(byte[] bArr, String str) {
            String[] split = str.split(File.separator);
            long parseLong = Long.parseLong(split[0]);
            int parseInt = Integer.parseInt(split[1]);
            String access$100 = PostProcessor.TAG;
            Log.d(access$100, "[3] onJpegAvailable: " + parseLong + " | " + parseInt);
            PerformanceTracker.trackJpegReprocess(parseInt, 1);
            ParallelTaskData parallelTaskData = (ParallelTaskData) PostProcessor.this.mParallelTaskHashMap.get(Long.valueOf(parseLong));
            if (parallelTaskData != null) {
                parallelTaskData.fillJpegData(bArr, parseInt);
                if (parallelTaskData.isJpegDataReady()) {
                    TotalCaptureResult totalCaptureResult = CommonUtil.toTotalCaptureResult(parallelTaskData.getCaptureResult(), -1);
                    String access$1002 = PostProcessor.TAG;
                    Log.d(access$1002, "[3] onJpegAvailable: save image start. dataLen=" + bArr.length);
                    if (PostProcessor.this.mImageSaver.onParallelProcessFinish(parallelTaskData, totalCaptureResult, (CameraCharacteristics) null)) {
                        parallelTaskData.releaseImageData();
                    }
                    if (PostProcessor.this.isNeedCallBackFinished(parallelTaskData) && PostProcessor.this.mPostProcessStatusCallback != null) {
                        PostProcessor.this.mPostProcessStatusCallback.onImagePostProcessEnd(parallelTaskData);
                    }
                    PostProcessor.this.mParallelTaskHashMap.remove(Long.valueOf(parseLong));
                    String access$1003 = PostProcessor.TAG;
                    Log.d(access$1003, "[3] onJpegAvailable: parallelTaskHashMap remove " + parseLong);
                } else {
                    Log.d(PostProcessor.TAG, "[3] onJpegAvailable: jpeg data isn't ready, save action has been ignored.");
                }
            } else {
                String access$1004 = PostProcessor.TAG;
                Log.w(access$1004, "[3] onJpegAvailable: null task data. timestamp=" + parseLong);
            }
            PostProcessor.this.tryToStopBoost();
            PostProcessor.this.tryToCloseSession();
        }

        public void onYuvAvailable(Image image, String str) {
        }
    };
    private int mMaxParallelRequestNumber = 10;
    private MtkBoost mMtkBoost;
    /* access modifiers changed from: private */
    public ConcurrentHashMap<Long, ParallelTaskData> mParallelTaskHashMap = new ConcurrentHashMap<>();
    /* access modifiers changed from: private */
    public PostProcessStatusCallback mPostProcessStatusCallback;
    private boolean mSRRequireReprocess;
    private TaskSession.SessionStatusCallback mSessionStatusCb = new TaskSession.SessionStatusCallback() {
        public void onSessionCallback(int i, String str, Object obj) {
            if (i != 0) {
                String access$100 = PostProcessor.TAG;
                Log.e(access$100, "Unknown result type " + i);
                return;
            }
            ResultData resultData = (ResultData) obj;
            if (!(PostProcessor.this.mSessionStatusCbListener == null || resultData == null)) {
                try {
                    PostProcessor.this.mSessionStatusCbListener.onSessionStatusFlawResultData(resultData.getResultId(), resultData.getFlawResult());
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
            String access$1002 = PostProcessor.TAG;
            Log.d(access$1002, "onSessionCallback FLAW getResultId: " + resultData.getResultId() + ", getFlawResult: " + resultData.getFlawResult());
        }
    };
    /* access modifiers changed from: private */
    public ISessionStatusCallBackListener mSessionStatusCbListener;
    private boolean mShouldDestroyWhenTasksFinished = false;
    private List<Surface> mSurfaceList = new ArrayList();
    private int mToken = -1;
    private Handler mWorkerHandler;
    private HandlerThread mWorkerThread = new HandlerThread("CallbackHandleThread");
    /* access modifiers changed from: private */
    public ParallelDataZipper.DataListener mZipperResultListener = new ParallelDataZipper.DataListener() {
        public void onParallelDataAbandoned(CaptureData captureData) {
            if (captureData != null) {
                String access$100 = PostProcessor.TAG;
                Log.d(access$100, "onParallelDataAbandoned: " + captureData);
                PostProcessor.this.mParallelTaskHashMap.remove(Long.valueOf(captureData.getCaptureTimestamp()));
                for (CaptureData.CaptureDataBean next : captureData.getCaptureDataBeanList()) {
                    if (next != null) {
                        Image mainImage = next.getMainImage();
                        String access$1002 = PostProcessor.TAG;
                        Log.d(access$1002, "onParallelDataAbandoned: mainImage = " + mainImage);
                        if (mainImage != null) {
                            mainImage.close();
                            PostProcessor.this.mCaptureDataListener.onOriginalImageClosed(mainImage);
                        }
                        Image subImage = next.getSubImage();
                        String access$1003 = PostProcessor.TAG;
                        Log.d(access$1003, "onParallelDataAbandoned: subImage = " + subImage);
                        if (subImage != null) {
                            subImage.close();
                            PostProcessor.this.mCaptureDataListener.onOriginalImageClosed(subImage);
                        }
                    }
                }
            }
        }

        public void onParallelDataAvailable(@NonNull CaptureData captureData) {
            String access$100 = PostProcessor.TAG;
            Log.d(access$100, "[z] onParallelDataAvailable: " + captureData.getCaptureTimestamp());
            if (Build.IS_DEBUGGABLE) {
                Iterator<CaptureData.CaptureDataBean> it = captureData.getCaptureDataBeanList().iterator();
                while (it.hasNext()) {
                    Log.d(PostProcessor.TAG, "[z] onParallelDataAvailable: ------------------------");
                    String access$1002 = PostProcessor.TAG;
                    Log.d(access$1002, "[z] Result timestamp: " + it.next().getResult().getTimeStamp());
                }
            }
            int algoType = captureData.getAlgoType();
            String access$1003 = PostProcessor.TAG;
            Log.d(access$1003, "[z] onParallelDataAvailable: algoType = " + algoType);
            if (algoType != 4) {
                ImagePool.ImageFormat imageQueueKey = ImagePool.getInstance().toImageQueueKey(captureData.getCaptureDataBeanList().get(0).getMainImage());
                if (ImagePool.getInstance().isImageQueueFull(imageQueueKey, 4)) {
                    Log.w(PostProcessor.TAG, "[z] wait image pool>>");
                    ImagePool.getInstance().waitIfImageQueueFull(imageQueueKey, 4, 0);
                    Log.w(PostProcessor.TAG, "[z] wait image pool<<");
                }
                ImageProcessor imageProcessor = captureData.getImageProcessor();
                if (imageProcessor != null) {
                    imageProcessor.mNeedProcessNormalImageSize.getAndIncrement();
                    if (imageProcessor.mIsBokehMode) {
                        imageProcessor.mNeedProcessRawImageSize.getAndIncrement();
                        imageProcessor.mNeedProcessDepthImageSize.getAndIncrement();
                    }
                }
                PostProcessor.this.mImageProcessorStatusCb.onImageProcessStart(captureData.getCaptureTimestamp());
            }
            if (2 == algoType || (3 == algoType && PostProcessor.this.isSRRequireReprocess())) {
                captureData.setMultiFrameProcessListener(PostProcessor.this.mCaptureDataListener);
                ParallelTaskData parallelTaskData = (ParallelTaskData) PostProcessor.this.mParallelTaskHashMap.get(Long.valueOf(captureData.getCaptureTimestamp()));
                if (parallelTaskData != null) {
                    captureData.setMoonMode(parallelTaskData.getDataParameter().isMoonMode());
                    captureData.setCapturedByFrontCamera(parallelTaskData.getDataParameter().isFrontCamera());
                }
                MultiFrameProcessor.getInstance().processData(captureData, PostProcessor.this.mImageProcessor.getTaskSession());
            } else if (5 == algoType) {
                captureData.setMultiFrameProcessListener(PostProcessor.this.mCaptureDataListener);
                ParallelTaskData parallelTaskData2 = (ParallelTaskData) PostProcessor.this.mParallelTaskHashMap.get(Long.valueOf(captureData.getCaptureTimestamp()));
                if (parallelTaskData2 != null && parallelTaskData2.getDataParameter().isSaveGroupshotPrimitive()) {
                    captureData.setSaveInputImage(true);
                }
                MultiFrameProcessor.getInstance().processData(captureData, (TaskSession) null);
            } else {
                PostProcessor.this.mCaptureDataListener.onCaptureDataAvailable(captureData);
            }
        }
    };

    public class CaptureStatusListener {
        public CaptureStatusListener() {
        }

        public void onCaptureCompleted(ICustomCaptureResult iCustomCaptureResult, boolean z) {
            String access$100 = PostProcessor.TAG;
            Log.d(access$100, "[0] onCaptureCompleted: timestamp = " + iCustomCaptureResult.getTimeStamp());
            ParallelDataZipper.getInstance().join(iCustomCaptureResult, z);
        }

        public void onCaptureFailed(long j, int i) {
            String access$100 = PostProcessor.TAG;
            Log.w(access$100, "[0] onCaptureFailed: reason = " + i + " timestamp = " + j);
            PostProcessor.this.mParallelTaskHashMap.remove(Long.valueOf(j));
        }

        public void onCaptureStarted(@NonNull ParallelTaskData parallelTaskData) {
            PostProcessor.this.startMtkBoost();
            long timestamp = parallelTaskData.getTimestamp();
            String access$100 = PostProcessor.TAG;
            Log.d(access$100, "[0] onCaptureStarted: timestamp = " + timestamp);
            if (!parallelTaskData.isAbandoned()) {
                PostProcessor.this.mParallelTaskHashMap.put(Long.valueOf(timestamp), parallelTaskData);
            }
            CaptureData captureData = new CaptureData(parallelTaskData.getAlgoType(), PostProcessor.this.mImageProcessor instanceof DualCameraProcessor ? 2 : 1, parallelTaskData.getBurstNum(), timestamp, parallelTaskData.isAbandoned(), PostProcessor.this.mImageProcessor);
            captureData.setDataListener(PostProcessor.this.mZipperResultListener);
            ParallelDataZipper.getInstance().startTask(captureData);
        }
    }

    private static class ImageAvailableListener implements ImageReader.OnImageAvailableListener {
        private int mImageFlag;
        private ImageMemoryManager mMemoryManager;

        ImageAvailableListener(int i, ImageMemoryManager imageMemoryManager) {
            this.mImageFlag = i;
            this.mMemoryManager = imageMemoryManager;
        }

        public void onImageAvailable(ImageReader imageReader) {
            if (imageReader == null) {
                Log.e(PostProcessor.TAG, "[0] onImageAvailable: null imageReader!");
                return;
            }
            this.mMemoryManager.waitImageCloseIfNeeded();
            Image acquireNextImage = imageReader.acquireNextImage();
            String access$100 = PostProcessor.TAG;
            Log.d(access$100, "[0] onImageAvailable: timestamp = " + acquireNextImage.getTimestamp());
            this.mMemoryManager.holdAnImage(imageReader.hashCode(), acquireNextImage);
            if (Util.isDumpImageEnabled()) {
                ImageUtil.dumpYuvImage(acquireNextImage, "dump_0_" + acquireNextImage.getTimestamp());
            }
            ParallelDataZipper.getInstance().join(acquireNextImage, this.mImageFlag);
        }
    }

    public interface PostProcessStatusCallback {
        void onImagePostProcessEnd(ParallelTaskData parallelTaskData);

        void onImagePostProcessStart(ParallelTaskData parallelTaskData);

        void onPostProcessorClosed(PostProcessor postProcessor);
    }

    public PostProcessor(Context context, PostProcessStatusCallback postProcessStatusCallback) {
        this.mWorkerThread.start();
        this.mWorkerHandler = new Handler(this.mWorkerThread.getLooper());
        this.mPostProcessStatusCallback = postProcessStatusCallback;
        init();
    }

    private int getMinHoldImageNum(List<IImageReaderParameterSets> list) {
        int i = 0;
        if (list == null || list.isEmpty()) {
            Log.e(TAG, "getMinHoldImageNum: empty param");
            return 0;
        }
        for (IImageReaderParameterSets next : list) {
            if (i == 0 || next.maxImages < i) {
                i = next.maxImages;
            }
        }
        return i;
    }

    /* access modifiers changed from: private */
    public boolean isNeedCallBackFinished(ParallelTaskData parallelTaskData) {
        if (parallelTaskData == null) {
            return false;
        }
        return parallelTaskData.getParallelType() == -7 || parallelTaskData.getParallelType() == -5 || parallelTaskData.getParallelType() == -6;
    }

    /* access modifiers changed from: private */
    public void startMtkBoost() {
        if (b.isMTKPlatform()) {
            if (this.mMtkBoost == null) {
                this.mMtkBoost = new MtkBoost();
            }
            this.mMtkBoost.startBoost();
        }
    }

    private void stopMtkBoost() {
        if (b.isMTKPlatform() && this.mMtkBoost != null) {
            this.mMtkBoost.stopBoost();
        }
    }

    /* access modifiers changed from: private */
    public synchronized void tryToCloseSession() {
        if (!this.mParallelTaskHashMap.isEmpty() || !this.mShouldDestroyWhenTasksFinished) {
            Log.d(TAG, "tryToCloseSession: ignore");
        } else {
            Log.d(TAG, "tryToCloseSession: E");
            finish();
            deInit();
            if (this.mPostProcessStatusCallback != null) {
                this.mPostProcessStatusCallback.onPostProcessorClosed(this);
            }
            stopMtkBoost();
            Log.d(TAG, "tryToCloseSession: X");
        }
    }

    /* access modifiers changed from: private */
    public void tryToStopBoost() {
        Log.d(TAG, "tryToStopBoost");
        if (isIdle()) {
            Log.d(TAG, "stopBoost");
            stopMtkBoost();
        }
    }

    public void configCaptureSession(BufferFormat bufferFormat) {
        String str = TAG;
        Log.d(str, "configCaptureSession: " + bufferFormat);
        boolean z = bufferFormat.getGraphDescriptor().getOperationModeID() == 32770;
        if (this.mImageProcessor != null) {
            this.mImageProcessor.stopWorkWhenIdle();
        }
        if (bufferFormat.getGraphDescriptor().getStreamNumber() == 2) {
            this.mImageProcessor = new DualCameraProcessor(this.mImageProcessorStatusCb, z, bufferFormat);
        } else {
            this.mImageProcessor = new SingleCameraProcessor(this.mImageProcessorStatusCb, z, bufferFormat);
        }
        this.mImageProcessorList.add(this.mImageProcessor);
        this.mImageProcessor.startWork();
        TaskSession createSessionByOutputConfigurations = MiCameraAlgo.createSessionByOutputConfigurations(bufferFormat, this.mImageProcessor.configOutputConfigurations(bufferFormat), this.mSessionStatusCb);
        if (this.mImageProcessor != null) {
            this.mImageProcessor.setTaskSession(createSessionByOutputConfigurations);
        }
    }

    public List<Surface> configHALOutputSurface(@NonNull List<IImageReaderParameterSets> list) {
        String str = TAG;
        Log.d(str, "configHALOutputSurface: paramsNum=" + list.size());
        if (!this.mImageReaderList.isEmpty()) {
            this.mImageReaderList.clear();
        }
        if (!this.mSurfaceList.isEmpty()) {
            this.mSurfaceList.clear();
        }
        int minHoldImageNum = getMinHoldImageNum(list);
        this.mImageMemoryManager = new ImageMemoryManager(minHoldImageNum);
        String str2 = TAG;
        Log.d(str2, "configHALOutputSurface: holdNum=" + minHoldImageNum);
        for (IImageReaderParameterSets next : list) {
            ImageReader newInstance = ImageReader.newInstance(next.width, next.height, next.format, next.maxImages);
            newInstance.setOnImageAvailableListener(new ImageAvailableListener(next.targetCamera, this.mImageMemoryManager), this.mWorkerHandler);
            this.mSurfaceList.add(newInstance.getSurface());
            this.mImageReaderList.add(newInstance);
        }
        return this.mSurfaceList;
    }

    public void deInit() {
        if (!this.mImageReaderList.isEmpty()) {
            for (ImageReader close : this.mImageReaderList) {
                close.close();
            }
            this.mImageReaderList.clear();
        }
        if (!this.mSurfaceList.isEmpty()) {
            this.mSurfaceList.clear();
        }
    }

    public synchronized void destroyWhenTasksFinished() {
        this.mShouldDestroyWhenTasksFinished = true;
        tryToCloseSession();
    }

    public void finish() {
        if (this.mImageProcessor != null) {
            this.mImageProcessor.stopWork();
            this.mImageProcessor = null;
        }
        if (!this.mImageProcessorList.isEmpty() && isIdle()) {
            for (ImageProcessor stopWork : this.mImageProcessorList) {
                stopWork.stopWork();
            }
            this.mImageProcessorList.clear();
        }
        if (this.mWorkerThread != null) {
            this.mWorkerThread.quitSafely();
            try {
                this.mWorkerThread.join();
                this.mWorkerThread = null;
                this.mWorkerHandler = null;
            } catch (InterruptedException e) {
                Log.w(TAG, "finish: failed!", e);
            }
        }
    }

    public CaptureStatusListener getCaptureStatusListener() {
        return this.mCaptureStatusListener;
    }

    public List<Surface> getSurfaceList() {
        return this.mSurfaceList;
    }

    public int getToken() {
        return this.mToken;
    }

    public void init() {
    }

    public boolean isIdle() {
        String str = TAG;
        Log.d(str, "isIdle " + this.mParallelTaskHashMap.size());
        return this.mParallelTaskHashMap.isEmpty();
    }

    public boolean isSRRequireReprocess() {
        return this.mSRRequireReprocess;
    }

    public synchronized boolean isStopping() {
        return this.mShouldDestroyWhenTasksFinished;
    }

    public boolean needWaitAlgorithmEngine() {
        boolean z = this.mImageProcessor != null && this.mImageProcessor.getProcessingRequestNumber() >= this.mMaxParallelRequestNumber;
        String str = TAG;
        Log.c(str, "needWaitAlgorithmEngine: return " + z);
        return z;
    }

    public boolean needWaitImageClose() {
        boolean z = this.mImageMemoryManager != null && this.mImageMemoryManager.needWaitImageClose();
        String str = TAG;
        Log.c(str, "needWaitImageClose: return " + z);
        return z;
    }

    public void setImageSaver(ImageSaver imageSaver) {
        this.mImageSaver = imageSaver;
    }

    public void setMaxParallelRequestNumber(int i) {
        if (i > 0) {
            this.mMaxParallelRequestNumber = i;
        }
    }

    public void setOnSessionStatusCallBackListener(ISessionStatusCallBackListener iSessionStatusCallBackListener) {
        this.mSessionStatusCbListener = iSessionStatusCallBackListener;
    }

    public void setSRRequireReprocess(boolean z) {
        this.mSRRequireReprocess = z;
    }

    public void setToken(int i) {
        this.mToken = i;
    }
}
