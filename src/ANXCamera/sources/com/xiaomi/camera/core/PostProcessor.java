package com.xiaomi.camera.core;

import android.content.Context;
import android.hardware.camera2.CaptureRequest;
import android.hardware.camera2.TotalCaptureResult;
import android.media.Image;
import android.media.ImageReader;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.RemoteException;
import android.os.SystemProperties;
import android.view.Surface;
import androidx.annotation.NonNull;
import b.c.a.c;
import com.android.camera.CameraSettings;
import com.android.camera.JpegUtil;
import com.android.camera.LocalParallelService;
import com.android.camera.Util;
import com.android.camera.data.DataRepository;
import com.android.camera.log.Log;
import com.android.camera.module.loader.camera2.ParallelSnapshotManager;
import com.android.camera.storage.ImageSaver;
import com.xiaomi.camera.base.CommonUtil;
import com.xiaomi.camera.base.ImageUtil;
import com.xiaomi.camera.base.PerformanceTracker;
import com.xiaomi.camera.core.CaptureData;
import com.xiaomi.camera.core.ImageProcessor;
import com.xiaomi.camera.core.ParallelDataZipper;
import com.xiaomi.camera.imagecodec.ImagePool;
import com.xiaomi.camera.imagecodec.ImageReaderHelper;
import com.xiaomi.camera.imagecodec.ReprocessData;
import com.xiaomi.engine.BufferFormat;
import com.xiaomi.engine.MiCameraAlgo;
import com.xiaomi.engine.ResultData;
import com.xiaomi.engine.TaskSession;
import com.xiaomi.protocol.ICustomCaptureResult;
import com.xiaomi.protocol.IImageReaderParameterSets;
import com.xiaomi.protocol.ISessionStatusCallBackListener;
import java.io.File;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import miui.os.Build;

public class PostProcessor {
    private static final int MSG_RESULT_FLAW = 0;
    public static final boolean SKIP_IMAGEPROCESSOR = SystemProperties.getBoolean(SKIP_IMAGEPROCESS_PROP, false);
    public static final String SKIP_IMAGEPROCESS_PROP = "camera.skip.imageprocessor";
    public static final boolean SKIP_MULTI = SystemProperties.getBoolean(SKIP_MULTI_PROP, false);
    public static final String SKIP_MULTI_PROP = "camera.skip.multi";
    public static final boolean SKIP_RENDER = SystemProperties.getBoolean(SKIP_RENDER_PROP, false);
    public static final String SKIP_RENDER_PROP = "camera.skip.render";
    private static final String TAG = "PostProcessor";
    private BoostFrameworkImpl mBoostFramework;
    private CaptureDataListener mCaptureDataListener = new CaptureDataListener() {
        /* class com.xiaomi.camera.core.PostProcessor.AnonymousClass3 */

        @Override // com.xiaomi.camera.core.CaptureDataListener
        public void onCaptureDataAvailable(@NonNull CaptureData captureData) {
            long captureTimestamp = captureData.getCaptureTimestamp();
            int algoType = captureData.getAlgoType();
            boolean z = false;
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
                    if (3 != algoType || !PostProcessor.this.isSRRequireReprocess()) {
                        captureData.getCaptureDataBeanList().add(multiFrameProcessResult);
                    } else if (!captureData.isHdrSR()) {
                        captureData.getCaptureDataBeanList().add(multiFrameProcessResult);
                    } else if (captureData.getHDRSRResult() != null) {
                        captureData.getCaptureDataBeanList().addAll(captureData.getHDRSRResult());
                        captureData.getHDRSRResult().clear();
                    } else {
                        captureData.getCaptureDataBeanList().add(multiFrameProcessResult);
                    }
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
                    if (parallelTaskData2.getDataParameter().shouldReprocessBurstShotPicture()) {
                        captureData.getImageProcessor().dispatchTask(captureData.getCaptureDataBeanList());
                        return;
                    }
                    PostProcessor.this.mImageProcessorStatusCb.onImageProcessStart(mainImage.getTimestamp());
                    PostProcessor.this.mImageProcessorStatusCb.onImageProcessed(mainImage, 0, false);
                    mainImage.close();
                    onOriginalImageClosed(mainImage);
                    return;
                }
                Log.e(PostProcessor.TAG, "[1] onCaptureDataAvailable: no captureResult " + captureTimestamp);
                mainImage.close();
                onOriginalImageClosed(mainImage);
                return;
            }
            List<CaptureData.CaptureDataBean> captureDataBeanList = captureData.getCaptureDataBeanList();
            if (captureDataBeanList != null && !captureDataBeanList.isEmpty()) {
                if (CameraSettings.isHighQualityPreferred() && captureData.isSatFusionShot() && captureData.getAlgoType() == 3) {
                    PostProcessor.dumpFusionInputs(captureData);
                }
                Iterator<CaptureData.CaptureDataBean> it = captureDataBeanList.iterator();
                while (true) {
                    if (!it.hasNext()) {
                        break;
                    }
                    CaptureData.CaptureDataBean next = it.next();
                    if (next.isFirstResult()) {
                        long timeStamp3 = next.getResult().getTimeStamp();
                        ParallelTaskData parallelTaskData3 = (ParallelTaskData) PostProcessor.this.mParallelTaskHashMap.get(Long.valueOf(timeStamp3));
                        if (parallelTaskData3 != null) {
                            parallelTaskData3.setCaptureResult(PostProcessor.this.chooseCaptureResult(captureData, algoType));
                        } else {
                            Log.e(PostProcessor.TAG, "[1] onCaptureDataAvailable: no task data with timestamp " + timeStamp3, new RuntimeException());
                            z = true;
                        }
                    }
                }
                if (z) {
                    for (CaptureData.CaptureDataBean captureDataBean2 : captureDataBeanList) {
                        Image mainImage2 = captureDataBean2.getMainImage();
                        mainImage2.close();
                        onOriginalImageClosed(mainImage2);
                        ImagePool.getInstance().releaseImage(mainImage2);
                        Image subImage = captureDataBean2.getSubImage();
                        if (subImage != null) {
                            subImage.close();
                            onOriginalImageClosed(subImage);
                            ImagePool.getInstance().releaseImage(subImage);
                        }
                        Image tuningImage = captureDataBean2.getTuningImage();
                        if (tuningImage != null) {
                            tuningImage.close();
                            onOriginalImageClosed(tuningImage);
                            ImagePool.getInstance().releaseImage(tuningImage);
                        }
                    }
                    return;
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

        @Override // com.xiaomi.camera.core.CaptureDataListener
        public void onOriginalImageClosed(Image image) {
            if (PostProcessor.this.mImageMemoryManager != null && image != null) {
                String str = PostProcessor.TAG;
                Log.d(str, "onOriginalImageClosed: " + image);
                PostProcessor.this.mImageMemoryManager.releaseAnImage(image);
            }
        }
    };
    private CaptureStatusListener mCaptureStatusListener = new CaptureStatusListener();
    private ImageMemoryManager mImageMemoryManager;
    private ImageProcessor mImageProcessor;
    private List<ImageProcessor> mImageProcessorList = new ArrayList();
    private ImageProcessor.ImageProcessorStatusCallback mImageProcessorStatusCb = new ImageProcessor.ImageProcessorStatusCallback() {
        /* class com.xiaomi.camera.core.PostProcessor.AnonymousClass4 */

        @Override // com.xiaomi.camera.core.ImageProcessor.ImageProcessorStatusCallback
        public ParallelTaskData getParallelTaskData(long j) {
            return (ParallelTaskData) PostProcessor.this.mParallelTaskHashMap.get(Long.valueOf(j));
        }

        @Override // com.xiaomi.camera.core.ImageProcessor.ImageProcessorStatusCallback
        public boolean isAnyFrontProcessing(ImageProcessor imageProcessor) {
            return ParallelDataZipper.getInstance().isAnyFrontProcessingByProcessor(imageProcessor);
        }

        @Override // com.xiaomi.camera.core.ImageProcessor.ImageProcessorStatusCallback
        public void onImageProcessFailed(Image image, String str) {
            String str2 = PostProcessor.TAG;
            StringBuilder sb = new StringBuilder();
            sb.append("onImageProcessFailed: image=");
            Object obj = image;
            if (image == null) {
                obj = "null";
            }
            sb.append(obj);
            sb.append(" reason=");
            sb.append(str);
            Log.d(str2, sb.toString());
        }

        @Override // com.xiaomi.camera.core.ImageProcessor.ImageProcessorStatusCallback
        public void onImageProcessStart(long j) {
            if (PostProcessor.this.mPostProcessStatusCallback != null) {
                String str = PostProcessor.TAG;
                Log.d(str, "onImageProcessStart: get parallelTaskData: " + j);
                PostProcessor.this.mPostProcessStatusCallback.onImagePostProcessStart((ParallelTaskData) PostProcessor.this.mParallelTaskHashMap.get(Long.valueOf(j)));
            }
        }

        @Override // com.xiaomi.camera.core.ImageProcessor.ImageProcessorStatusCallback
        public void onImageProcessed(Image image, int i, boolean z) {
            long timestamp = image.getTimestamp();
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
                ReprocessData reprocessData = new ReprocessData(image, str, captureResult, dataParameter.isFrontCamera(), dataParameter.getOutputSize().getWidth(), dataParameter.getOutputSize().getHeight(), dataParameter.getOutputFormat(), PostProcessor.this.mJpegEncoderListener);
                reprocessData.setFrontMirror(dataParameter.isMirror());
                reprocessData.setJpegQuality(dataParameter.getJpegQuality());
                reprocessData.setImageFromPool(z);
                if (parallelTaskData.isRequireTuningData()) {
                    reprocessData.setTuningImage(parallelTaskData.getTuningImage());
                }
                if (1212500294 == reprocessData.getOutputFormat() && dataParameter.getSupportZeroDegreeOrientationImage()) {
                    reprocessData.setRotateOrientationToZero(true);
                }
                PerformanceTracker.trackJpegReprocess(i, 0);
                try {
                    LocalParallelService.getReprocessor().submit(reprocessData);
                } catch (Exception unused) {
                    PostProcessor.this.mJpegEncoderListener.onError("nativeDetachImage failed for image!!!", str);
                }
            } else {
                throw new RuntimeException("no parallelTaskData with timestamp " + timestamp);
            }
        }

        @Override // com.xiaomi.camera.core.ImageProcessor.ImageProcessorStatusCallback
        public void onOriginalImageClosed(Image image) {
            PostProcessor.this.mCaptureDataListener.onOriginalImageClosed(image);
            String str = PostProcessor.TAG;
            Log.d(str, "onOriginalImageClosed: " + image);
        }
    };
    private List<ImageReader> mImageReaderList = new ArrayList();
    private ImageSaver mImageSaver;
    private ReprocessData.OnDataAvailableListener mJpegEncoderListener = new ReprocessData.OnDataAvailableListener() {
        /* class com.xiaomi.camera.core.PostProcessor.AnonymousClass5 */

        @Override // com.xiaomi.camera.imagecodec.ReprocessData.OnDataAvailableListener
        public void onError(String str, String str2) {
            String[] split = str2.split(File.separator);
            long parseLong = Long.parseLong(split[0]);
            int parseInt = Integer.parseInt(split[1]);
            String str3 = PostProcessor.TAG;
            Log.e(str3, "[3] onError: " + parseLong + " | " + parseInt + " | " + str);
            PerformanceTracker.trackJpegReprocess(parseInt, 1);
            ParallelTaskData parallelTaskData = (ParallelTaskData) PostProcessor.this.mParallelTaskHashMap.get(Long.valueOf(parseLong));
            if (parallelTaskData != null) {
                PostProcessor.this.closePoolImage(parallelTaskData.getTuningImage());
                parallelTaskData.releaseImageData();
                PostProcessor.this.mParallelTaskHashMap.remove(Long.valueOf(parseLong));
                String str4 = PostProcessor.TAG;
                Log.e(str4, "[3] onError: remove task " + parseLong + " | " + parseInt);
            }
            PostProcessor.this.tryToCloseSession();
        }

        @Override // com.xiaomi.camera.imagecodec.ReprocessData.OnDataAvailableListener
        public void onJpegAvailable(byte[] bArr, String str) {
            String[] split = str.split(File.separator);
            long parseLong = Long.parseLong(split[0]);
            int parseInt = Integer.parseInt(split[1]);
            String str2 = PostProcessor.TAG;
            Log.d(str2, "[3] onJpegAvailable: " + parseLong + " | " + parseInt);
            PerformanceTracker.trackJpegReprocess(parseInt, 1);
            ParallelTaskData parallelTaskData = (ParallelTaskData) PostProcessor.this.mParallelTaskHashMap.get(Long.valueOf(parseLong));
            if (parallelTaskData != null) {
                parallelTaskData.fillJpegData(bArr, parseInt);
                if (parallelTaskData.isJpegDataReady() || PostProcessor.SKIP_IMAGEPROCESSOR) {
                    parallelTaskData.setMemDebug(PostProcessor.SKIP_IMAGEPROCESSOR);
                    TotalCaptureResult totalCaptureResult = CommonUtil.toTotalCaptureResult(parallelTaskData.getCaptureResult(), -1);
                    String str3 = PostProcessor.TAG;
                    Log.d(str3, "[3] onJpegAvailable: save image start. dataLen=" + bArr.length);
                    boolean onParallelProcessFinish = PostProcessor.this.mImageSaver.onParallelProcessFinish(parallelTaskData, totalCaptureResult, null);
                    PostProcessor.this.closePoolImage(parallelTaskData.getTuningImage());
                    if (onParallelProcessFinish) {
                        parallelTaskData.releaseImageData();
                    }
                    if (PostProcessor.this.isNeedCallBackFinished(parallelTaskData) && PostProcessor.this.mPostProcessStatusCallback != null) {
                        PostProcessor.this.mPostProcessStatusCallback.onImagePostProcessEnd(parallelTaskData);
                    }
                    PostProcessor.this.mParallelTaskHashMap.remove(Long.valueOf(parseLong));
                    String str4 = PostProcessor.TAG;
                    Log.d(str4, "[3] onJpegAvailable: parallelTaskHashMap remove " + parseLong);
                } else {
                    Log.d(PostProcessor.TAG, "[3] onJpegAvailable: jpeg data isn't ready, save action has been ignored.");
                }
            } else {
                String str5 = PostProcessor.TAG;
                Log.w(str5, "[3] onJpegAvailable: null task data. timestamp=" + parseLong);
            }
            PostProcessor.this.tryToStopBoost();
            PostProcessor.this.tryToCloseSession();
        }

        @Override // com.xiaomi.camera.imagecodec.ReprocessData.OnDataAvailableListener
        public void onJpegImageAvailable(Image image, String str) {
            String[] split = str.split(File.separator);
            long parseLong = Long.parseLong(split[0]);
            int parseInt = Integer.parseInt(split[1]);
            String str2 = PostProcessor.TAG;
            Log.d(str2, "[3] onJpegImageAvailable: " + parseLong + " | " + parseInt);
            PerformanceTracker.trackJpegReprocess(parseInt, 1);
            ParallelTaskData parallelTaskData = (ParallelTaskData) PostProcessor.this.mParallelTaskHashMap.get(Long.valueOf(parseLong));
            if (parallelTaskData != null) {
                Image.Plane[] planesExtra = JpegUtil.getPlanesExtra(image);
                byte[] jpegData = JpegUtil.getJpegData(planesExtra, 0);
                if (jpegData == null) {
                    jpegData = Util.getFirstPlane(image);
                }
                parallelTaskData.fillJpegData(jpegData, parseInt);
                byte[] jpegData2 = JpegUtil.getJpegData(planesExtra, 1);
                if (jpegData2 != null) {
                    parallelTaskData.setDataOfTheRegionUnderWatermarks(jpegData2);
                    ParallelTaskDataParameter dataParameter = parallelTaskData.getDataParameter();
                    int[] watermarkRange = Util.getWatermarkRange(dataParameter.getPictureSize().getWidth(), dataParameter.getPictureSize().getHeight(), (dataParameter.getJpegRotation() + 270) % 360, true, true, false);
                    String str3 = PostProcessor.TAG;
                    Log.d(str3, "watermarkRange:" + watermarkRange[0] + "_" + watermarkRange[1] + "_" + watermarkRange[2] + "_" + watermarkRange[3]);
                    parallelTaskData.setCoordinatesOfTheRegionUnderWatermarks(watermarkRange);
                }
                if (parallelTaskData.isJpegDataReady() || PostProcessor.SKIP_IMAGEPROCESSOR) {
                    parallelTaskData.setMemDebug(PostProcessor.SKIP_IMAGEPROCESSOR);
                    TotalCaptureResult totalCaptureResult = CommonUtil.toTotalCaptureResult(parallelTaskData.getCaptureResult(), -1);
                    String str4 = PostProcessor.TAG;
                    Log.d(str4, "[3] onJpegImageAvailable: save image start. dataLen=" + jpegData.length);
                    boolean onParallelProcessFinish = PostProcessor.this.mImageSaver.onParallelProcessFinish(parallelTaskData, totalCaptureResult, null);
                    PostProcessor.this.closePoolImage(parallelTaskData.getTuningImage());
                    if (onParallelProcessFinish) {
                        parallelTaskData.releaseImageData();
                    }
                    if (PostProcessor.this.isNeedCallBackFinished(parallelTaskData) && PostProcessor.this.mPostProcessStatusCallback != null) {
                        PostProcessor.this.mPostProcessStatusCallback.onImagePostProcessEnd(parallelTaskData);
                    }
                    PostProcessor.this.mParallelTaskHashMap.remove(Long.valueOf(parseLong));
                    String str5 = PostProcessor.TAG;
                    Log.d(str5, "[3] onJpegImageAvailable: parallelTaskHashMap remove " + parseLong);
                } else {
                    Log.d(PostProcessor.TAG, "[3] onJpegImageAvailable: jpeg data isn't ready, save action has been ignored.");
                }
            } else {
                String str6 = PostProcessor.TAG;
                Log.w(str6, "[3] onJpegImageAvailable: null task data. timestamp=" + parseLong);
            }
            PostProcessor.this.tryToStopBoost();
            PostProcessor.this.tryToCloseSession();
        }

        @Override // com.xiaomi.camera.imagecodec.ReprocessData.OnDataAvailableListener
        public void onYuvAvailable(Image image, String str) {
        }
    };
    private int mMaxParallelRequestNumber = 10;
    private List<ImageReader> mObsoleteImageReaderList = new ArrayList();
    private ConcurrentHashMap<Long, ParallelTaskData> mParallelTaskHashMap = new ConcurrentHashMap<>();
    private List<IImageReaderParameterSets> mParams;
    private PostProcessStatusCallback mPostProcessStatusCallback;
    private boolean mSRRequireReprocess;
    private TaskSession.SessionStatusCallback mSessionStatusCb = new TaskSession.SessionStatusCallback() {
        /* class com.xiaomi.camera.core.PostProcessor.AnonymousClass1 */

        @Override // com.xiaomi.engine.TaskSession.SessionStatusCallback
        public void onSessionCallback(int i, String str, Object obj) {
            ISessionStatusCallBackListener iSessionStatusCallBackListener;
            if (i != 0) {
                String str2 = PostProcessor.TAG;
                Log.e(str2, "Unknown result type " + i);
                return;
            }
            ResultData resultData = (ResultData) obj;
            if (!(PostProcessor.this.mSessionStatusCbListener == null || resultData == null || (iSessionStatusCallBackListener = (ISessionStatusCallBackListener) PostProcessor.this.mSessionStatusCbListener.get()) == null)) {
                try {
                    iSessionStatusCallBackListener.onSessionStatusFlawResultData(resultData.getResultId(), resultData.getFlawResult());
                } catch (RemoteException e2) {
                    e2.printStackTrace();
                }
            }
            String str3 = PostProcessor.TAG;
            Log.d(str3, "onSessionCallback FLAW getResultId: " + resultData.getResultId() + ", getFlawResult: " + resultData.getFlawResult());
        }
    };
    private WeakReference<ISessionStatusCallBackListener> mSessionStatusCbListener;
    private boolean mShouldDestroyWhenTasksFinished = false;
    private List<Surface> mSurfaceList = new ArrayList();
    private int mToken = -1;
    private Handler mWorkerHandler;
    private HandlerThread mWorkerThread = new HandlerThread("CallbackHandleThread");
    private ParallelDataZipper.DataListener mZipperResultListener = new ParallelDataZipper.DataListener() {
        /* class com.xiaomi.camera.core.PostProcessor.AnonymousClass2 */

        @Override // com.xiaomi.camera.core.ParallelDataZipper.DataListener
        public void onParallelDataAbandoned(CaptureData captureData) {
            if (captureData != null) {
                String str = PostProcessor.TAG;
                Log.d(str, "onParallelDataAbandoned: " + captureData);
                PostProcessor.this.mParallelTaskHashMap.remove(Long.valueOf(captureData.getCaptureTimestamp()));
                for (CaptureData.CaptureDataBean captureDataBean : captureData.getCaptureDataBeanList()) {
                    if (captureDataBean != null) {
                        Image mainImage = captureDataBean.getMainImage();
                        String str2 = PostProcessor.TAG;
                        Log.d(str2, "onParallelDataAbandoned: mainImage = " + mainImage);
                        if (mainImage != null) {
                            mainImage.close();
                            PostProcessor.this.mCaptureDataListener.onOriginalImageClosed(mainImage);
                        }
                        Image subImage = captureDataBean.getSubImage();
                        String str3 = PostProcessor.TAG;
                        Log.d(str3, "onParallelDataAbandoned: subImage = " + subImage);
                        if (subImage != null) {
                            subImage.close();
                            PostProcessor.this.mCaptureDataListener.onOriginalImageClosed(subImage);
                        }
                        Image tuningImage = captureDataBean.getTuningImage();
                        if (tuningImage != null) {
                            tuningImage.close();
                            PostProcessor.this.mCaptureDataListener.onOriginalImageClosed(tuningImage);
                        }
                    }
                }
            }
        }

        @Override // com.xiaomi.camera.core.ParallelDataZipper.DataListener
        public void onParallelDataAvailable(@NonNull CaptureData captureData) {
            ImageProcessor imageProcessor;
            Log.d(PostProcessor.TAG, "[z] onParallelDataAvailable: " + captureData.getCaptureTimestamp());
            if (Build.IS_DEBUGGABLE) {
                Iterator<CaptureData.CaptureDataBean> it = captureData.getCaptureDataBeanList().iterator();
                while (it.hasNext()) {
                    Log.d(PostProcessor.TAG, "[z] onParallelDataAvailable: ------------------------");
                    Log.d(PostProcessor.TAG, "[z] Result timestamp: " + it.next().getResult().getTimeStamp());
                }
            }
            if (CameraSettings.isHighQualityPreferred() && 2 == captureData.getStreamNum() && !captureData.isSatFusionShot()) {
                if (captureData.getCaptureDataBeanList().size() > 1) {
                    CaptureData.CaptureDataBean captureDataBean = captureData.getCaptureDataBeanList().get(0);
                    if (((Integer) ICustomCaptureResult.toTotalCaptureResult(captureDataBean.getResult(), captureDataBean.getResult().getSessionId()).get(TotalCaptureResult.CONTROL_AE_EXPOSURE_COMPENSATION)).intValue() < 0) {
                        ImageUtil.dumpYuvImageAppendWH(captureData.getCaptureDataBeanList().get(0).getMainImage(), "evMinusMainImage" + captureData.getCaptureTimestamp());
                        ImageUtil.dumpYuvImageAppendWH(captureData.getCaptureDataBeanList().get(1).getMainImage(), "evZeroMainImage" + captureData.getCaptureTimestamp());
                        ImageUtil.dumpYuvImageAppendWH(captureData.getCaptureDataBeanList().get(1).getSubImage(), "evZeroSubImage" + captureData.getCaptureTimestamp());
                    } else {
                        ImageUtil.dumpYuvImageAppendWH(captureData.getCaptureDataBeanList().get(0).getMainImage(), "evZeroMainImage" + captureData.getCaptureTimestamp());
                        ImageUtil.dumpYuvImageAppendWH(captureData.getCaptureDataBeanList().get(0).getSubImage(), "evZeroSubImage" + captureData.getCaptureTimestamp());
                        ImageUtil.dumpYuvImageAppendWH(captureData.getCaptureDataBeanList().get(1).getMainImage(), "evMinusMainImage" + captureData.getCaptureTimestamp());
                    }
                } else if (captureData.getCaptureDataBeanList().size() == 1) {
                    ImageUtil.dumpYuvImageAppendWH(captureData.getCaptureDataBeanList().get(0).getMainImage(), "evZeroMainImage" + captureData.getCaptureTimestamp());
                    ImageUtil.dumpYuvImageAppendWH(captureData.getCaptureDataBeanList().get(0).getSubImage(), "evZeroSubImage" + captureData.getCaptureTimestamp());
                }
            }
            int algoType = captureData.getAlgoType();
            Log.d(PostProcessor.TAG, "[z] onParallelDataAvailable: algoType = " + algoType);
            if (algoType != 4) {
                ImagePool.ImageFormat imageQueueKey = ImagePool.getInstance().toImageQueueKey(captureData.getCaptureDataBeanList().get(0).getMainImage());
                if (ImagePool.getInstance().isImageQueueFull(imageQueueKey, 4)) {
                    Log.w(PostProcessor.TAG, "[z] wait image pool>>");
                    ImagePool.getInstance().waitIfImageQueueFull(imageQueueKey, 4, 0);
                    Log.w(PostProcessor.TAG, "[z] wait image pool<<");
                }
                if (!PostProcessor.SKIP_IMAGEPROCESSOR && (imageProcessor = captureData.getImageProcessor()) != null) {
                    imageProcessor.mNeedProcessNormalImageSize.getAndIncrement();
                    if (imageProcessor.isBokekMode()) {
                        imageProcessor.mNeedProcessRawImageSize.getAndIncrement();
                        imageProcessor.mNeedProcessDepthImageSize.getAndIncrement();
                    }
                }
                PostProcessor.this.mImageProcessorStatusCb.onImageProcessStart(captureData.getCaptureTimestamp());
            }
            ParallelTaskData parallelTaskData = (ParallelTaskData) PostProcessor.this.mParallelTaskHashMap.get(Long.valueOf(captureData.getCaptureTimestamp()));
            if (parallelTaskData != null && parallelTaskData.isRequireTuningData()) {
                List<CaptureData.CaptureDataBean> captureDataBeanList = captureData.getCaptureDataBeanList();
                for (int i = 0; i < captureDataBeanList.size(); i++) {
                    Image tuningImage = captureDataBeanList.get(i).getTuningImage();
                    if (i == 0) {
                        parallelTaskData.setTuningImage(ImageUtil.queueImageToPool(ImagePool.getInstance(), tuningImage, 1));
                    }
                    tuningImage.close();
                    PostProcessor.this.mCaptureDataListener.onOriginalImageClosed(tuningImage);
                }
            }
            if (2 == algoType || (3 == algoType && PostProcessor.this.isSRRequireReprocess())) {
                captureData.setMultiFrameProcessListener(PostProcessor.this.mCaptureDataListener);
                if (parallelTaskData != null) {
                    captureData.setMoonMode(parallelTaskData.getDataParameter().isMoonMode());
                    captureData.setHdrSR(parallelTaskData.isHdrSR());
                    captureData.setCapturedByFrontCamera(parallelTaskData.getDataParameter().isFrontCamera());
                    captureData.setAlgoSize(parallelTaskData.getDataParameter().getPictureSize());
                }
                MultiFrameProcessor.getInstance().processData(captureData, PostProcessor.this.mImageProcessor.getTaskSession());
            } else if (5 == algoType) {
                captureData.setMultiFrameProcessListener(PostProcessor.this.mCaptureDataListener);
                ParallelTaskData parallelTaskData2 = (ParallelTaskData) PostProcessor.this.mParallelTaskHashMap.get(Long.valueOf(captureData.getCaptureTimestamp()));
                if (parallelTaskData2 != null && parallelTaskData2.getDataParameter().isSaveGroupshotPrimitive()) {
                    captureData.setSaveInputImage(true);
                }
                MultiFrameProcessor.getInstance().processData(captureData, null);
            } else {
                PostProcessor.this.mCaptureDataListener.onCaptureDataAvailable(captureData);
            }
            if (parallelTaskData != null) {
                parallelTaskData.mIsFrontProcessing = false;
            }
            PostProcessor.this.tryToCloseSession();
        }
    };

    public class CaptureStatusListener {
        public CaptureStatusListener() {
        }

        public void onCaptureCompleted(ICustomCaptureResult iCustomCaptureResult, boolean z) {
            String str = PostProcessor.TAG;
            Log.d(str, "[0] onCaptureCompleted: timestamp = " + iCustomCaptureResult.getTimeStamp() + " frameNo = " + iCustomCaptureResult.getFrameNumber());
            ParallelDataZipper.getInstance().join(iCustomCaptureResult, z);
        }

        public void onCaptureFailed(long j, int i) {
            String str = PostProcessor.TAG;
            Log.w(str, "[0] onCaptureFailed: reason = " + i + " timestamp = " + j);
            PostProcessor.this.mParallelTaskHashMap.remove(Long.valueOf(j));
        }

        public void onCaptureStarted(@NonNull ParallelTaskData parallelTaskData) {
            PostProcessor.this.startBoost();
            long timestamp = parallelTaskData.getTimestamp();
            boolean isSatFusionShot = parallelTaskData.isSatFusionShot();
            String str = PostProcessor.TAG;
            Log.d(str, "[0] onCaptureStarted: timestamp = " + timestamp + ", fusion = " + isSatFusionShot);
            if (!parallelTaskData.isAbandoned()) {
                PostProcessor.this.mParallelTaskHashMap.put(Long.valueOf(timestamp), parallelTaskData);
                parallelTaskData.mIsFrontProcessing = true;
            }
            CaptureData captureData = new CaptureData(parallelTaskData.getAlgoType(), PostProcessor.this.mImageProcessor instanceof DualCameraProcessor ? 2 : 1, parallelTaskData.getBurstNum(), timestamp, parallelTaskData.isAbandoned(), PostProcessor.this.mImageProcessor);
            captureData.setDataListener(PostProcessor.this.mZipperResultListener);
            captureData.setRequireTuningData(parallelTaskData.isRequireTuningData());
            captureData.setIsSatFusionShot(parallelTaskData.isSatFusionShot());
            ParallelDataZipper.getInstance().startTask(captureData);
        }
    }

    /* access modifiers changed from: private */
    public static class ImageAvailableListener implements ImageReader.OnImageAvailableListener {
        private int mCameraType;
        private int mImageFlag;
        private ImageMemoryManager mMemoryManager;

        ImageAvailableListener(int i, int i2, ImageMemoryManager imageMemoryManager) {
            this.mImageFlag = i;
            this.mCameraType = i2;
            this.mMemoryManager = imageMemoryManager;
        }

        public void onImageAvailable(ImageReader imageReader) {
            if (imageReader == null) {
                Log.e(PostProcessor.TAG, "[0] onImageAvailable: null imageReader!");
                return;
            }
            this.mMemoryManager.waitImageCloseIfNeeded();
            Image acquireNextImage = imageReader.acquireNextImage();
            String str = PostProcessor.TAG;
            Log.d(str, "[0] onImageAvailable: timestamp = " + acquireNextImage.getTimestamp() + ", target = " + this.mImageFlag + ", type = " + this.mCameraType);
            this.mMemoryManager.holdAnImage(imageReader.hashCode(), acquireNextImage);
            if (Util.isDumpImageEnabled()) {
                ImageUtil.dumpYuvImage(acquireNextImage, "hal");
            }
            ParallelDataZipper.getInstance().join(acquireNextImage, this.mImageFlag, this.mCameraType);
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

    /* access modifiers changed from: private */
    /* access modifiers changed from: public */
    private ICustomCaptureResult chooseCaptureResult(CaptureData captureData, int i) {
        if (DataRepository.dataItemFeature().ll()) {
            int burstNum = captureData.getBurstNum() - 1;
            Log.i(TAG, "[ALGOUP|MMCAMERA]: Add last metadata, index = " + burstNum);
            return captureData.getCaptureDataBeanList().get(burstNum).getResult();
        }
        if (i == 1) {
            for (int i2 = 0; i2 < captureData.getCaptureDataBeanList().size(); i2++) {
                ICustomCaptureResult result = captureData.getCaptureDataBeanList().get(i2).getResult();
                if (((Integer) result.getRequest().get(CaptureRequest.CONTROL_AE_EXPOSURE_COMPENSATION)).intValue() == 0) {
                    Log.d(TAG, String.format("chooseCaptureResult: get the %d frame metadata", Integer.valueOf(i2)));
                    return result;
                }
            }
        }
        return captureData.getCaptureDataBeanList().get(0).getResult();
    }

    /* access modifiers changed from: private */
    /* access modifiers changed from: public */
    private void closePoolImage(Image image) {
        if (image != null) {
            image.close();
            ImagePool.getInstance().releaseImage(image);
        }
    }

    /* access modifiers changed from: private */
    public static void dumpFusionInputs(CaptureData captureData) {
        if (captureData.getCaptureDataBeanList().size() != 1) {
            Log.e(TAG, "dumpFusionInputs: illegal fusion input frame count!");
        } else if (captureData.getMultiFrameProcessResult() != captureData.getCaptureDataBeanList().get(0)) {
            Log.e(TAG, "dumpFusionInputs: illegal fusion input frame data!");
        } else {
            CaptureData.CaptureDataBean multiFrameProcessResult = captureData.getMultiFrameProcessResult();
            if (multiFrameProcessResult.getMainImage() == null) {
                Log.e(TAG, "dumpFusionInputs: fusion input frame main is null!");
            } else if (multiFrameProcessResult.getSubImage() == null) {
                Log.e(TAG, "dumpFusionInputs: fusion input frame sub is null!");
            } else if (multiFrameProcessResult.getResult() == null) {
                Log.e(TAG, "dumpFusionInputs: fusion input frame meta is null!");
            } else {
                Image mainImage = multiFrameProcessResult.getMainImage();
                ImageUtil.dumpYuvImageAppendWH(mainImage, "fusionMainImage" + multiFrameProcessResult.getResult().getTimeStamp());
                Image subImage = multiFrameProcessResult.getSubImage();
                ImageUtil.dumpYuvImageAppendWH(subImage, "fusionSubImage" + multiFrameProcessResult.getResult().getTimeStamp());
            }
        }
    }

    private int getMinHoldImageNum(List<IImageReaderParameterSets> list) {
        int i = 0;
        if (list == null || list.isEmpty()) {
            Log.e(TAG, "getMinHoldImageNum: empty param");
            return 0;
        }
        for (IImageReaderParameterSets iImageReaderParameterSets : list) {
            if (i == 0 || iImageReaderParameterSets.maxImages < i) {
                i = iImageReaderParameterSets.maxImages;
            }
        }
        return i;
    }

    private boolean isAnyFrontProcessing() {
        for (ParallelTaskData parallelTaskData : this.mParallelTaskHashMap.values()) {
            if (parallelTaskData.mIsFrontProcessing) {
                return true;
            }
        }
        return false;
    }

    /* access modifiers changed from: private */
    /* access modifiers changed from: public */
    private boolean isNeedCallBackFinished(ParallelTaskData parallelTaskData) {
        if (parallelTaskData == null) {
            return false;
        }
        return parallelTaskData.getParallelType() == -7 || parallelTaskData.getParallelType() == -5 || parallelTaskData.getParallelType() == -6 || parallelTaskData.getAlgoType() == 10;
    }

    private boolean isParallelSupportedAlgo(int i) {
        return i == 0 || i == 1 || i == 8 || i == 10;
    }

    /* access modifiers changed from: private */
    /* access modifiers changed from: public */
    private void startBoost() {
        if (c.isMTKPlatform()) {
            if (this.mBoostFramework == null) {
                this.mBoostFramework = new BoostFrameworkImpl();
            }
            this.mBoostFramework.startBoost();
        }
    }

    private void stopBoost() {
        BoostFrameworkImpl boostFrameworkImpl;
        if (c.isMTKPlatform() && (boostFrameworkImpl = this.mBoostFramework) != null) {
            boostFrameworkImpl.stopBoost();
        }
    }

    /* access modifiers changed from: private */
    /* access modifiers changed from: public */
    private synchronized void tryToCloseSession() {
        if (this.mShouldDestroyWhenTasksFinished && !isAnyFrontProcessing()) {
            Iterator<IImageReaderParameterSets> it = this.mParams.iterator();
            while (true) {
                if (it.hasNext()) {
                    if (it.next().isParallel) {
                        ParallelSnapshotManager.getInstance().closeCaptureSession(this.mSurfaceList);
                        break;
                    }
                } else {
                    break;
                }
            }
        }
        if (!this.mParallelTaskHashMap.isEmpty() || !this.mShouldDestroyWhenTasksFinished) {
            String str = TAG;
            Log.d(str, "tryToCloseSession: ignore  this:" + this);
        } else {
            String str2 = TAG;
            Log.d(str2, "tryToCloseSession: E  this:" + this);
            finish();
            deInit();
            if (this.mPostProcessStatusCallback != null) {
                this.mPostProcessStatusCallback.onPostProcessorClosed(this);
            }
            stopBoost();
            String str3 = TAG;
            Log.d(str3, "tryToCloseSession: X  this:" + this);
        }
    }

    /* access modifiers changed from: private */
    /* access modifiers changed from: public */
    private void tryToStopBoost() {
        Log.d(TAG, "tryToStopBoost");
        if (isIdle()) {
            Log.d(TAG, "stopBoost");
            stopBoost();
        }
    }

    public void clearFrontProcessingTask() {
        for (ParallelTaskData parallelTaskData : this.mParallelTaskHashMap.values()) {
            if (parallelTaskData.mIsFrontProcessing && !parallelTaskData.isParallelVTCameraSnapshot()) {
                String str = TAG;
                Log.i(str, "clearFrontProcessingTask: timestamp > " + parallelTaskData.getTimestamp());
                ParallelDataZipper.getInstance().releaseData(parallelTaskData.getTimestamp());
            }
        }
    }

    public synchronized void configCaptureSession(BufferFormat bufferFormat) {
        String str = TAG;
        Log.d(str, "configCaptureSession: " + bufferFormat);
        if (this.mImageProcessor != null) {
            this.mImageProcessor.stopWorkWhenIdle();
        }
        if (bufferFormat.getGraphDescriptor().getStreamNumber() == 2) {
            this.mImageProcessor = new DualCameraProcessor(this.mImageProcessorStatusCb, bufferFormat);
        } else {
            this.mImageProcessor = new SingleCameraProcessor(this.mImageProcessorStatusCb, bufferFormat);
        }
        this.mImageProcessor.setMaxParallelRequestNumber(this.mMaxParallelRequestNumber);
        this.mImageProcessor.setImageBufferQueueSize(this.mMaxParallelRequestNumber);
        this.mImageProcessorList.add(this.mImageProcessor);
        this.mImageProcessor.startWork();
        this.mImageProcessor.setTaskSession(MiCameraAlgo.createSessionByOutputConfigurations(bufferFormat, this.mImageProcessor.configOutputConfigurations(bufferFormat), this.mSessionStatusCb));
    }

    public List<Surface> configHALOutputSurface(@NonNull List<IImageReaderParameterSets> list) {
        String str = TAG;
        Log.d(str, "configHALOutputSurface: paramsNum=" + list.size());
        if (!this.mImageReaderList.isEmpty()) {
            Log.d(TAG, "save obsolete image readers");
            this.mObsoleteImageReaderList.addAll(this.mImageReaderList);
            this.mImageReaderList.clear();
        }
        if (!this.mSurfaceList.isEmpty()) {
            this.mSurfaceList.clear();
        }
        int minHoldImageNum = getMinHoldImageNum(list);
        this.mImageMemoryManager = new ImageMemoryManager(minHoldImageNum);
        String str2 = TAG;
        Log.d(str2, "configHALOutputSurface: holdNum=" + minHoldImageNum);
        for (IImageReaderParameterSets iImageReaderParameterSets : list) {
            ImageReader newInstance = ImageReader.newInstance(iImageReaderParameterSets.width, iImageReaderParameterSets.height, iImageReaderParameterSets.format, iImageReaderParameterSets.maxImages);
            ImageReaderHelper.setImageReaderNameDepends(newInstance, ImageReaderHelper.ImageReaderType.ORIGINAL, false);
            newInstance.setOnImageAvailableListener(new ImageAvailableListener(iImageReaderParameterSets.imageType, iImageReaderParameterSets.cameraType, this.mImageMemoryManager), this.mWorkerHandler);
            this.mSurfaceList.add(newInstance.getSurface());
            this.mImageReaderList.add(newInstance);
        }
        if (c.uh && (c.bh || c._g || c.ln())) {
            configParallelCaptureSession();
        }
        return this.mSurfaceList;
    }

    public boolean configParallelCaptureSession() {
        Log.d(TAG, "configParallelCaptureSession: mSurfaceList = " + this.mSurfaceList);
        List<Surface> list = this.mSurfaceList;
        if (list != null && list.size() > 0 && this.mSurfaceList.size() == this.mParams.size()) {
            ArrayList arrayList = new ArrayList();
            for (int i = 0; i < this.mParams.size(); i++) {
                if (this.mParams.get(i).isParallel) {
                    arrayList.add(this.mSurfaceList.get(i));
                }
            }
            if (arrayList.size() > 0) {
                Log.d(TAG, "configParallelCaptureSession: surfaceList.size = " + arrayList.size());
                ParallelSnapshotManager.getInstance().createCaptureSession(arrayList);
                return true;
            }
        }
        return false;
    }

    public void deInit() {
        Log.d(TAG, "deInit");
        if (!this.mImageReaderList.isEmpty()) {
            for (ImageReader imageReader : this.mImageReaderList) {
                imageReader.close();
            }
            this.mImageReaderList.clear();
        }
        if (!this.mObsoleteImageReaderList.isEmpty()) {
            for (ImageReader imageReader2 : this.mObsoleteImageReaderList) {
                imageReader2.close();
            }
            this.mObsoleteImageReaderList.clear();
        }
        if (!this.mSurfaceList.isEmpty()) {
            this.mSurfaceList.clear();
        }
    }

    public synchronized void destroyWhenTasksFinished(int i) {
        int token = getToken();
        if (token == -1 || i == token) {
            this.mShouldDestroyWhenTasksFinished = true;
            tryToCloseSession();
        }
    }

    public void finish() {
        ImageProcessor imageProcessor = this.mImageProcessor;
        if (imageProcessor != null) {
            imageProcessor.stopWork();
            this.mImageProcessor = null;
        }
        if (!this.mImageProcessorList.isEmpty() && isIdle()) {
            for (ImageProcessor imageProcessor2 : this.mImageProcessorList) {
                imageProcessor2.stopWork();
            }
            this.mImageProcessorList.clear();
        }
        HandlerThread handlerThread = this.mWorkerThread;
        if (handlerThread != null) {
            handlerThread.quitSafely();
            try {
                this.mWorkerThread.join();
                this.mWorkerThread = null;
                this.mWorkerHandler = null;
            } catch (InterruptedException e2) {
                Log.w(TAG, "finish: failed!", e2);
            }
        }
    }

    public CaptureStatusListener getCaptureStatusListener() {
        return this.mCaptureStatusListener;
    }

    public int getFrontProcessingCount() {
        int i = 0;
        for (ParallelTaskData parallelTaskData : this.mParallelTaskHashMap.values()) {
            if (parallelTaskData.mIsFrontProcessing) {
                i++;
            }
        }
        return i;
    }

    public List<IImageReaderParameterSets> getParams() {
        return this.mParams;
    }

    public List<Surface> getSurfaceList() {
        return this.mSurfaceList;
    }

    public synchronized int getToken() {
        return this.mToken;
    }

    public void init() {
    }

    public boolean isAnyRequestBlocked() {
        for (ParallelTaskData parallelTaskData : this.mParallelTaskHashMap.values()) {
            if (!isParallelSupportedAlgo(parallelTaskData.getAlgoType()) && parallelTaskData.mIsFrontProcessing) {
                String str = TAG;
                Log.d(str, "isAnyRequestBlocked: taskData AlgoType:" + parallelTaskData.getAlgoType());
                return true;
            }
        }
        return false;
    }

    public boolean isAnyRequestIsHWMFNRProcessing() {
        for (ParallelTaskData parallelTaskData : this.mParallelTaskHashMap.values()) {
            if (parallelTaskData.isHWMFNRProcessing()) {
                return true;
            }
        }
        return false;
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
        ImageProcessor imageProcessor = this.mImageProcessor;
        boolean z = imageProcessor != null && imageProcessor.isAlgorithmEngineBusy();
        if (z) {
            Log.d(TAG, "needWaitAlgorithmEngine: return true");
        } else {
            Log.c(TAG, "needWaitAlgorithmEngine: return false");
        }
        return z;
    }

    public boolean needWaitImageClose() {
        ImageMemoryManager imageMemoryManager = this.mImageMemoryManager;
        boolean z = imageMemoryManager != null && imageMemoryManager.needWaitImageClose();
        if (z) {
            Log.d(TAG, "needWaitImageClose: return true");
        } else {
            Log.c(TAG, "needWaitImageClose: return false");
        }
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
        this.mSessionStatusCbListener = new WeakReference<>(iSessionStatusCallBackListener);
    }

    public void setParams(List<IImageReaderParameterSets> list) {
        this.mParams = list;
    }

    public void setSRRequireReprocess(boolean z) {
        this.mSRRequireReprocess = z;
    }

    public synchronized void setToken(int i) {
        this.mToken = i;
    }

    public synchronized boolean tryToReuse() {
        String str = TAG;
        Log.d(str, "tryToReuse: mShouldDestroyWhenTasksFinished = " + this.mShouldDestroyWhenTasksFinished);
        if (!this.mShouldDestroyWhenTasksFinished || isAnyFrontProcessing() || configParallelCaptureSession()) {
            this.mShouldDestroyWhenTasksFinished = false;
            return true;
        }
        Log.w(TAG, "tryToReuse: fail to create configParallelCaptureSession");
        return false;
    }
}
