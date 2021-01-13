package com.android.camera;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.os.RemoteException;
import android.view.Surface;
import androidx.annotation.NonNull;
import b.c.a.c;
import com.android.camera.data.DataRepository;
import com.android.camera.log.Log;
import com.android.camera.module.loader.camera2.Camera2DataContainer;
import com.android.camera.storage.ImageSaver;
import com.xiaomi.camera.core.ParallelTaskData;
import com.xiaomi.camera.core.PostProcessor;
import com.xiaomi.camera.imagecodec.Reprocessor;
import com.xiaomi.camera.imagecodec.ReprocessorFactory;
import com.xiaomi.engine.BufferFormat;
import com.xiaomi.engine.MiCameraAlgo;
import com.xiaomi.protocol.ICustomCaptureResult;
import com.xiaomi.protocol.IImageReaderParameterSets;
import com.xiaomi.protocol.ISessionStatusCallBackListener;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class LocalParallelService extends Service {
    private static final String TAG = "LocalParallelService";
    private static int sReprocessorTypeIndex = -1;
    private LocalBinder mLocalBinder;
    private int mMaxParallelRequestNumber;
    private PostProcessor.PostProcessStatusCallback mPostProcessStatusCallback = new PostProcessor.PostProcessStatusCallback() {
        /* class com.android.camera.LocalParallelService.AnonymousClass1 */

        @Override // com.xiaomi.camera.core.PostProcessor.PostProcessStatusCallback
        public void onImagePostProcessEnd(ParallelTaskData parallelTaskData) {
            if (parallelTaskData.getServiceStatusListener() != null) {
                parallelTaskData.getServiceStatusListener().onImagePostProcessEnd(parallelTaskData);
            } else if (LocalParallelService.this.mServiceStatusListenerRef != null && LocalParallelService.this.mServiceStatusListenerRef.get() != null) {
                ((ServiceStatusListener) LocalParallelService.this.mServiceStatusListenerRef.get()).onImagePostProcessEnd(parallelTaskData);
            }
        }

        @Override // com.xiaomi.camera.core.PostProcessor.PostProcessStatusCallback
        public void onImagePostProcessStart(ParallelTaskData parallelTaskData) {
            if (parallelTaskData.getServiceStatusListener() != null) {
                parallelTaskData.getServiceStatusListener().onImagePostProcessStart(parallelTaskData);
            } else if (LocalParallelService.this.mServiceStatusListenerRef != null && LocalParallelService.this.mServiceStatusListenerRef.get() != null) {
                ((ServiceStatusListener) LocalParallelService.this.mServiceStatusListenerRef.get()).onImagePostProcessStart(parallelTaskData);
            }
        }

        @Override // com.xiaomi.camera.core.PostProcessor.PostProcessStatusCallback
        public void onPostProcessorClosed(PostProcessor postProcessor) {
            if (LocalParallelService.this.mLocalBinder != null) {
                LocalParallelService.this.mLocalBinder.onPostProcessorClosed(postProcessor);
            }
        }
    };
    private final Object mPostProcessorLock = new Object();
    private boolean mSRRequireReprocess;
    private WeakReference<ServiceStatusListener> mServiceStatusListenerRef;

    public class LocalBinder extends Binder {
        private List<PostProcessor> mAlivePostProcessor = new CopyOnWriteArrayList();
        private BufferFormat mCurrentBufferFormat;
        private List<IImageReaderParameterSets> mCurrentParams;
        private PostProcessor mCurrentPostProcessor;

        LocalBinder() {
            ReprocessorFactory.init(LocalParallelService.this);
            updateVirtualCameraIds();
            LocalParallelService.getReprocessor().init(LocalParallelService.this);
            MiCameraAlgo.init(LocalParallelService.this);
        }

        private void initCurrentPostProcessor(@NonNull List<IImageReaderParameterSets> list) {
            this.mCurrentParams = list;
            LocalParallelService localParallelService = LocalParallelService.this;
            this.mCurrentPostProcessor = new PostProcessor(localParallelService, localParallelService.mPostProcessStatusCallback);
            this.mCurrentPostProcessor.setMaxParallelRequestNumber(LocalParallelService.this.mMaxParallelRequestNumber);
            this.mCurrentPostProcessor.setSRRequireReprocess(LocalParallelService.this.mSRRequireReprocess);
            String str = LocalParallelService.TAG;
            Log.d(str, "initCurrentPostProcessor: create a new PostProcessor: " + this.mCurrentPostProcessor + " | maxParallelRequestNumber: " + LocalParallelService.this.mMaxParallelRequestNumber);
            this.mCurrentBufferFormat = null;
            this.mCurrentPostProcessor.setParams(list);
            synchronized (LocalParallelService.this.mPostProcessorLock) {
                this.mAlivePostProcessor.add(this.mCurrentPostProcessor);
            }
        }

        private boolean isSetsEquals(List<IImageReaderParameterSets> list, List<IImageReaderParameterSets> list2) {
            if (list == null || list2 == null || list.size() != list2.size()) {
                return false;
            }
            int i = 0;
            for (IImageReaderParameterSets iImageReaderParameterSets : list) {
                Iterator<IImageReaderParameterSets> it = list2.iterator();
                while (true) {
                    if (it.hasNext()) {
                        if (iImageReaderParameterSets.equals(it.next())) {
                            i++;
                            break;
                        }
                    } else {
                        break;
                    }
                }
            }
            return list.size() == i;
        }

        /* access modifiers changed from: private */
        /* access modifiers changed from: public */
        private void onPostProcessorClosed(PostProcessor postProcessor) {
            synchronized (LocalParallelService.this.mPostProcessorLock) {
                String str = LocalParallelService.TAG;
                Log.d(str, "onPostProcessorClosed: processor:" + postProcessor);
                this.mAlivePostProcessor.remove(postProcessor);
            }
        }

        public List<Surface> configCaptureOutputBuffer(@NonNull List<IImageReaderParameterSets> list, int i) throws RemoteException {
            PostProcessor postProcessor;
            if (list.isEmpty()) {
                throw new RemoteException("List is empty");
            } else if (!isSetsEquals(list, this.mCurrentParams) || (postProcessor = this.mCurrentPostProcessor) == null || postProcessor.isStopping() || this.mCurrentPostProcessor.getSurfaceList().isEmpty()) {
                String str = LocalParallelService.TAG;
                Log.d(str, "configCaptureOutputBuffer: sets is changed, mAlivePostProcessor.size is " + this.mAlivePostProcessor.size());
                PostProcessor postProcessor2 = null;
                synchronized (LocalParallelService.this.mPostProcessorLock) {
                    for (int i2 = 0; i2 < this.mAlivePostProcessor.size(); i2++) {
                        PostProcessor postProcessor3 = this.mAlivePostProcessor.get(i2);
                        if (isSetsEquals(list, postProcessor3.getParams()) && postProcessor3 != null && !postProcessor3.getSurfaceList().isEmpty()) {
                            String str2 = LocalParallelService.TAG;
                            Log.d(str2, "configCaptureOutputBuffer: ready to reuse activeProcessor:" + postProcessor3);
                            postProcessor2 = postProcessor3;
                        }
                    }
                }
                if (postProcessor2 != null) {
                    this.mCurrentParams = list;
                    this.mCurrentPostProcessor = postProcessor2;
                    if (this.mCurrentPostProcessor.tryToReuse()) {
                        String str3 = LocalParallelService.TAG;
                        Log.d(str3, "configCaptureOutputBuffer: sucess to reuse activeProcessor:" + this.mCurrentPostProcessor);
                        return new ArrayList(this.mCurrentPostProcessor.getSurfaceList());
                    }
                    Log.w(LocalParallelService.TAG, "configCaptureOutputBuffer: failed to reuse activeProcessor, need to create new one");
                }
                PostProcessor postProcessor4 = this.mCurrentPostProcessor;
                if (postProcessor4 != null && !postProcessor4.isStopping()) {
                    this.mCurrentPostProcessor.destroyWhenTasksFinished(i);
                }
                initCurrentPostProcessor(list);
                this.mCurrentPostProcessor.setToken(i);
                return this.mCurrentPostProcessor.configHALOutputSurface(list);
            } else {
                Log.d(LocalParallelService.TAG, "configCaptureOutputBuffer: sets is not changed, return the old.");
                this.mCurrentPostProcessor.setToken(i);
                return new ArrayList(this.mCurrentPostProcessor.getSurfaceList());
            }
        }

        public void configCaptureSession(@NonNull BufferFormat bufferFormat) {
            if (this.mCurrentPostProcessor == null || bufferFormat.equals(this.mCurrentBufferFormat)) {
                Log.d(LocalParallelService.TAG, "configCaptureSession: bufferFormat keeps unchanged");
                return;
            }
            this.mCurrentBufferFormat = bufferFormat;
            long currentTimeMillis = System.currentTimeMillis();
            this.mCurrentPostProcessor.configCaptureSession(this.mCurrentBufferFormat);
            String str = LocalParallelService.TAG;
            Log.d(str, "configCaptureSession: cost: " + (System.currentTimeMillis() - currentTimeMillis));
        }

        public void configMaxParallelRequestNumber(int i) {
            LocalParallelService.this.mMaxParallelRequestNumber = i;
            PostProcessor postProcessor = this.mCurrentPostProcessor;
            if (postProcessor != null) {
                postProcessor.setMaxParallelRequestNumber(LocalParallelService.this.mMaxParallelRequestNumber);
            }
        }

        public int getFrontProcessingCount() {
            PostProcessor postProcessor = this.mCurrentPostProcessor;
            if (postProcessor != null) {
                return postProcessor.getFrontProcessingCount();
            }
            return 0;
        }

        public boolean isAnyRequestBlocked() {
            PostProcessor postProcessor = this.mCurrentPostProcessor;
            if (postProcessor != null) {
                return postProcessor.isAnyRequestBlocked();
            }
            return false;
        }

        public boolean isAnyRequestIsHWMFNRProcessing() {
            PostProcessor postProcessor = this.mCurrentPostProcessor;
            if (postProcessor != null) {
                return postProcessor.isAnyRequestIsHWMFNRProcessing();
            }
            return false;
        }

        public boolean isIdle() {
            synchronized (LocalParallelService.this.mPostProcessorLock) {
                for (PostProcessor postProcessor : this.mAlivePostProcessor) {
                    if (!postProcessor.isIdle()) {
                        return false;
                    }
                }
                return true;
            }
        }

        public boolean needWaitProcess() {
            PostProcessor postProcessor = this.mCurrentPostProcessor;
            if (postProcessor != null) {
                return postProcessor.needWaitImageClose() || this.mCurrentPostProcessor.needWaitAlgorithmEngine();
            }
            return false;
        }

        public void onCameraClosed() {
            PostProcessor postProcessor = this.mCurrentPostProcessor;
            if (postProcessor != null) {
                postProcessor.clearFrontProcessingTask();
            }
        }

        public void onCaptureCompleted(ICustomCaptureResult iCustomCaptureResult, boolean z) {
            this.mCurrentPostProcessor.getCaptureStatusListener().onCaptureCompleted(iCustomCaptureResult, z);
        }

        public void onCaptureFailed(long j, int i) {
            this.mCurrentPostProcessor.getCaptureStatusListener().onCaptureFailed(j, i);
        }

        public void onCaptureStarted(ParallelTaskData parallelTaskData) {
            this.mCurrentPostProcessor.getCaptureStatusListener().onCaptureStarted(parallelTaskData);
        }

        public void onServiceDestroy() {
            LocalParallelService.getReprocessor().deInit();
            MiCameraAlgo.deInit();
        }

        public void prepareParallelCapture() {
            synchronized (this) {
                if (this.mCurrentPostProcessor != null) {
                    this.mCurrentPostProcessor.configParallelCaptureSession();
                }
            }
        }

        public void setImageSaver(ImageSaver imageSaver) {
            this.mCurrentPostProcessor.setImageSaver(imageSaver);
        }

        public void setOnPictureTakenListener(ServiceStatusListener serviceStatusListener) {
            LocalParallelService.this.mServiceStatusListenerRef = new WeakReference(serviceStatusListener);
        }

        public void setOnSessionStatusCallBackListener(ISessionStatusCallBackListener iSessionStatusCallBackListener) {
            PostProcessor postProcessor = this.mCurrentPostProcessor;
            if (postProcessor != null) {
                postProcessor.setOnSessionStatusCallBackListener(iSessionStatusCallBackListener);
            }
        }

        public void setOutputPictureSpec(int i, int i2, int i3) {
            LocalParallelService.getReprocessor().setOutputPictureSpec(i, i2, i3);
        }

        public void setSRRequireReprocess(boolean z) {
            LocalParallelService.this.mSRRequireReprocess = z;
            PostProcessor postProcessor = this.mCurrentPostProcessor;
            if (postProcessor != null) {
                postProcessor.setSRRequireReprocess(z);
            }
        }

        public void stopPostProcessor(int i) {
            synchronized (this) {
                if (this.mCurrentPostProcessor != null && !this.mCurrentPostProcessor.isStopping()) {
                    String str = LocalParallelService.TAG;
                    Log.d(str, "stopPostProcessor: " + this.mCurrentPostProcessor);
                    this.mCurrentPostProcessor.destroyWhenTasksFinished(i);
                }
            }
        }

        public void updateVirtualCameraIds() {
            if (c.Lg) {
                LocalParallelService.getReprocessor().setVirtualCameraIds("5", "6");
            } else if (c.vn()) {
                LocalParallelService.getReprocessor().setVirtualCameraIds(String.valueOf(Camera2DataContainer.getInstance().getVirtualBackCameraId()), String.valueOf(Camera2DataContainer.getInstance().getVirtualFrontCameraId()));
            }
        }
    }

    public interface ServiceStatusListener {
        void onImagePostProcessEnd(ParallelTaskData parallelTaskData);

        void onImagePostProcessStart(ParallelTaskData parallelTaskData);
    }

    public static Reprocessor getReprocessor() {
        if (sReprocessorTypeIndex == -1) {
            sReprocessorTypeIndex = DataRepository.dataItemFeature().Zk();
        }
        return ReprocessorFactory.getReprocessor(ReprocessorFactory.ReprocessorType.values()[sReprocessorTypeIndex]);
    }

    public IBinder onBind(Intent intent) {
        Log.d(TAG, "onBind: start");
        return this.mLocalBinder;
    }

    public void onCreate() {
        Log.d(TAG, "onCreate: start");
        if (!DataRepository.dataItemFeature().nl()) {
            Log.d(TAG, "This device does not support Algo up, do nothing.");
            stopSelf();
            return;
        }
        this.mLocalBinder = new LocalBinder();
        super.onCreate();
    }

    public void onDestroy() {
        Log.d(TAG, "onDestroy: start");
        LocalBinder localBinder = this.mLocalBinder;
        if (localBinder != null) {
            localBinder.onServiceDestroy();
            this.mLocalBinder = null;
        }
        super.onDestroy();
    }

    public int onStartCommand(Intent intent, int i, int i2) {
        Log.d(TAG, "onStartCommand: start");
        return super.onStartCommand(intent, i, i2);
    }

    public boolean onUnbind(Intent intent) {
        Log.d(TAG, "onUnbind: start");
        return super.onUnbind(intent);
    }
}
