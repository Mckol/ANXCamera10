package com.xiaomi.camera.core;

import android.media.Image;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Message;
import android.util.Size;
import com.android.camera.LocalParallelService;
import com.android.camera.log.Log;
import com.xiaomi.camera.core.CaptureData;
import com.xiaomi.camera.imagecodec.ReprocessData;
import com.xiaomi.camera.processor.ClearShotProcessor;
import com.xiaomi.camera.processor.GroupShotProcessor;
import com.xiaomi.camera.processor.MockMultiProcessor;
import com.xiaomi.camera.processor.ProcessResultListener;
import com.xiaomi.camera.processor.SuperResolutionProcessor;
import com.xiaomi.engine.TaskSession;
import java.io.File;
import java.util.List;
import miui.os.Build;

public class MultiFrameProcessor {
    private static final int REPROCESS_TIMEOUT_MS = 8000;
    private static final String TAG = "MultiFrameProcessor";
    private final int MSG_PROCESS_DATA;
    private Handler mHandler;
    private final Object mObjLock;
    private ProcessResultListener mProcessResultListener;
    private long mReprocessStartTime;
    private boolean mReprocessing;
    private HandlerThread mWorkThread;

    /* access modifiers changed from: package-private */
    public static class MultiFrameProcessorHolder {
        static MultiFrameProcessor INSTANCE = new MultiFrameProcessor();

        MultiFrameProcessorHolder() {
        }
    }

    /* access modifiers changed from: private */
    public class ProcessDataAndTaskSession {
        CaptureData data;
        TaskSession taskSession;

        public ProcessDataAndTaskSession(CaptureData captureData, TaskSession taskSession2) {
            this.data = captureData;
            this.taskSession = taskSession2;
        }
    }

    private class WorkerHandler extends Handler {
        public WorkerHandler(Looper looper) {
            super(looper);
        }

        public void handleMessage(Message message) {
            if (message.what != 1) {
                String str = MultiFrameProcessor.TAG;
                Log.w(str, "unexpected message " + message.what);
                return;
            }
            ProcessDataAndTaskSession processDataAndTaskSession = (ProcessDataAndTaskSession) message.obj;
            MultiFrameProcessor.this.doProcess(processDataAndTaskSession.data, processDataAndTaskSession.taskSession);
        }
    }

    private MultiFrameProcessor() {
        this.mObjLock = new Object();
        this.mProcessResultListener = new ProcessResultListener() {
            /* class com.xiaomi.camera.core.MultiFrameProcessor.AnonymousClass1 */

            @Override // com.xiaomi.camera.processor.ProcessResultListener
            public void onProcessFinished(CaptureData captureData, boolean z) {
                String str = MultiFrameProcessor.TAG;
                Log.d(str, "onProcessFinished: doReprocess = " + z);
                CaptureDataListener captureDataListener = captureData.getCaptureDataListener();
                if (captureDataListener == null) {
                    Log.w(MultiFrameProcessor.TAG, "onProcessFinished: null CaptureDataListener!");
                    if (captureData.isHdrSR()) {
                        List<CaptureData.CaptureDataBean> hDRSRResult = captureData.getHDRSRResult();
                        if (hDRSRResult != null) {
                            for (CaptureData.CaptureDataBean captureDataBean : hDRSRResult) {
                                captureDataBean.close();
                            }
                            hDRSRResult.clear();
                        }
                    } else {
                        CaptureData.CaptureDataBean multiFrameProcessResult = captureData.getMultiFrameProcessResult();
                        if (multiFrameProcessResult != null) {
                            multiFrameProcessResult.close();
                        }
                    }
                    for (CaptureData.CaptureDataBean captureDataBean2 : captureData.getCaptureDataBeanList()) {
                        if (captureDataBean2 != null) {
                            captureDataBean2.close();
                        }
                    }
                    return;
                }
                if (z) {
                    CaptureData.CaptureDataBean multiFrameProcessResult2 = captureData.getMultiFrameProcessResult();
                    MultiFrameProcessor.this.reprocessImage(multiFrameProcessResult2, 0, captureData.isCapturedByFrontCamera());
                    if (multiFrameProcessResult2.isSatFusionShot()) {
                        MultiFrameProcessor.this.reprocessImage(multiFrameProcessResult2, 1, captureData.isCapturedByFrontCamera());
                    }
                }
                Log.d(MultiFrameProcessor.TAG, "onProcessFinished: dispatch image to algorithm engine");
                captureDataListener.onCaptureDataAvailable(captureData);
            }
        };
        this.MSG_PROCESS_DATA = 1;
        this.mWorkThread = new HandlerThread("MultiFrameProcessorThread");
        this.mWorkThread.start();
        this.mHandler = new WorkerHandler(this.mWorkThread.getLooper());
    }

    /* access modifiers changed from: private */
    /* access modifiers changed from: public */
    private void doProcess(CaptureData captureData, TaskSession taskSession) {
        String str = TAG;
        Log.d(str, "doProcess: start process task: " + captureData.getCaptureTimestamp());
        if (PostProcessor.SKIP_MULTI) {
            Log.d(TAG, "prop skip multi");
            new MockMultiProcessor().doProcess(captureData, this.mProcessResultListener, null);
            return;
        }
        int algoType = captureData.getAlgoType();
        if (2 == algoType) {
            new ClearShotProcessor().doProcess(captureData, this.mProcessResultListener, null);
        } else if (5 == algoType) {
            new GroupShotProcessor().doProcess(captureData, this.mProcessResultListener, null);
        } else if (3 == algoType) {
            SuperResolutionProcessor superResolutionProcessor = new SuperResolutionProcessor();
            Size algoSize = captureData.getAlgoSize();
            if (algoSize != null) {
                superResolutionProcessor.setOutputSize(algoSize.getWidth(), algoSize.getHeight());
            }
            superResolutionProcessor.doProcess(captureData, this.mProcessResultListener, taskSession);
        } else {
            throw new RuntimeException("unknown multi-frame process algorithm type: " + algoType);
        }
    }

    public static MultiFrameProcessor getInstance() {
        return MultiFrameProcessorHolder.INSTANCE;
    }

    /* access modifiers changed from: private */
    /* access modifiers changed from: public */
    private void reprocessImage(final CaptureData.CaptureDataBean captureDataBean, final int i, boolean z) {
        Image subImage;
        AnonymousClass2 r11 = new ReprocessData.OnDataAvailableListener() {
            /* class com.xiaomi.camera.core.MultiFrameProcessor.AnonymousClass2 */

            @Override // com.xiaomi.camera.imagecodec.ReprocessData.OnDataAvailableListener
            public void onError(String str, String str2) {
                synchronized (MultiFrameProcessor.this.mObjLock) {
                    String str3 = MultiFrameProcessor.TAG;
                    Log.v(str3, "onError>>tag=" + str2 + " reason=" + str);
                    if (!Build.IS_DEBUGGABLE) {
                        MultiFrameProcessor.this.mReprocessing = false;
                        MultiFrameProcessor.this.mObjLock.notify();
                        String str4 = MultiFrameProcessor.TAG;
                        Log.v(str4, "onError<<cost=" + (System.currentTimeMillis() - MultiFrameProcessor.this.mReprocessStartTime));
                    } else {
                        throw new RuntimeException("reprocessImage failed image = " + str2 + " reason = " + str);
                    }
                }
            }

            @Override // com.xiaomi.camera.imagecodec.ReprocessData.OnDataAvailableListener
            public void onJpegAvailable(byte[] bArr, String str) {
            }

            @Override // com.xiaomi.camera.imagecodec.ReprocessData.OnDataAvailableListener
            public void onJpegImageAvailable(Image image, String str) {
            }

            @Override // com.xiaomi.camera.imagecodec.ReprocessData.OnDataAvailableListener
            public void onYuvAvailable(Image image, String str) {
                synchronized (MultiFrameProcessor.this.mObjLock) {
                    String str2 = MultiFrameProcessor.TAG;
                    Log.v(str2, "onYuvAvailable>>tag=" + str);
                    MultiFrameProcessor.this.mReprocessing = false;
                    captureDataBean.setImage(image, i);
                    MultiFrameProcessor.this.mObjLock.notify();
                    String str3 = MultiFrameProcessor.TAG;
                    Log.v(str3, "onYuvAvailable<<cost=" + (System.currentTimeMillis() - MultiFrameProcessor.this.mReprocessStartTime));
                }
            }
        };
        if (i == 0) {
            subImage = captureDataBean.getMainImage();
        } else if (i == 1) {
            subImage = captureDataBean.getSubImage();
        } else {
            throw new UnsupportedOperationException("ImageType(" + i + ") not supported.");
        }
        String str = captureDataBean.getResult().getTimeStamp() + File.separator + i;
        int width = subImage.getWidth();
        int height = subImage.getHeight();
        long timestamp = subImage.getTimestamp();
        Log.d(TAG, "E: reprocessImage: timestamp = " + timestamp + ", imageType = " + i);
        synchronized (this.mObjLock) {
            this.mReprocessStartTime = System.currentTimeMillis();
            this.mReprocessing = true;
            ReprocessData reprocessData = new ReprocessData(subImage, str, captureDataBean.getResult(), z, width, height, 35, r11);
            reprocessData.setImageFromPool(true);
            try {
                LocalParallelService.getReprocessor().submit(reprocessData);
                while (this.mReprocessing) {
                    this.mObjLock.wait(8000);
                    this.mReprocessing = false;
                }
            } catch (IllegalArgumentException | IllegalStateException | InterruptedException e2) {
                this.mReprocessing = false;
                Log.e(TAG, e2.getMessage(), e2);
            }
        }
        Log.d(TAG, "X: reprocessImage: timestamp = " + timestamp + ", imageType = " + i);
    }

    public void processData(CaptureData captureData, TaskSession taskSession) {
        if (captureData.getBurstNum() != captureData.getCaptureDataBeanList().size()) {
            throw new RuntimeException("Loss some capture data, burstNum is: " + captureData.getBurstNum() + "; but data bean list size is: " + captureData.getCaptureDataBeanList().size());
        } else if (!this.mWorkThread.isAlive() || this.mHandler == null) {
            Log.w(TAG, "processData: sync mode");
            doProcess(captureData, taskSession);
        } else {
            String str = TAG;
            Log.v(str, "processData: queue task: " + captureData.getCaptureTimestamp());
            this.mHandler.obtainMessage(1, new ProcessDataAndTaskSession(captureData, taskSession)).sendToTarget();
        }
    }
}
