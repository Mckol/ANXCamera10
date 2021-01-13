package com.xiaomi.camera.core;

import android.annotation.SuppressLint;
import android.media.Image;
import android.os.Handler;
import android.os.HandlerThread;
import android.util.Log;
import android.util.LongSparseArray;
import androidx.annotation.NonNull;
import com.xiaomi.camera.core.CaptureData;
import com.xiaomi.protocol.ICustomCaptureResult;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import miui.os.Build;

public class ParallelDataZipper {
    private static final String TAG = "ParallelDataZipper";
    @SuppressLint({"UseSparseArrays"})
    private final Map<Long, CaptureData> mCaptureDataArray;
    private LongSparseArray<CaptureData.CaptureDataBean> mCaptureDataBeanArray;
    private Handler mHandler;
    private final LongSparseArray<List<Long>> mUnTrackCaptureDataBeanMap;
    private HandlerThread mWorkThread;

    public interface DataListener {
        void onParallelDataAbandoned(CaptureData captureData);

        void onParallelDataAvailable(CaptureData captureData);
    }

    /* access modifiers changed from: package-private */
    public static class InstanceHolder {
        static ParallelDataZipper INSTANCE = new ParallelDataZipper();

        InstanceHolder() {
        }
    }

    private ParallelDataZipper() {
        this.mCaptureDataBeanArray = new LongSparseArray<>(4);
        this.mCaptureDataArray = new HashMap(4);
        this.mUnTrackCaptureDataBeanMap = new LongSparseArray<>(4);
        this.mWorkThread = new HandlerThread("ParallelDataZipperThread");
        this.mWorkThread.start();
        this.mHandler = new Handler(this.mWorkThread.getLooper());
    }

    /* access modifiers changed from: private */
    /* access modifiers changed from: public */
    private long getFirstFrameTimestamp(long j) {
        if (this.mCaptureDataArray.containsKey(Long.valueOf(j))) {
            String str = TAG;
            Log.d(str, "getFirstFrameTimestamp: return current timestamp: " + j);
            return j;
        }
        Long[] lArr = (Long[]) this.mCaptureDataArray.keySet().toArray(new Long[0]);
        if (!(lArr == null || lArr.length == 0)) {
            Arrays.sort(lArr);
            if (lArr.length == 1) {
                return lArr[0].longValue();
            }
            for (int i = 0; i <= lArr.length - 2; i++) {
                if (j > lArr[i].longValue() && j < lArr[i + 1].longValue()) {
                    return lArr[i].longValue();
                }
            }
            if (j > lArr[lArr.length - 1].longValue()) {
                return lArr[lArr.length - 1].longValue();
            }
        }
        String str2 = TAG;
        Log.e(str2, "getFirstFrameTimestamp: return an error result with timestamp: " + j);
        return 0;
    }

    public static ParallelDataZipper getInstance() {
        return InstanceHolder.INSTANCE;
    }

    private int getStreamNumberByTimestamp(long j) {
        CaptureData captureData = this.mCaptureDataArray.get(Long.valueOf(getFirstFrameTimestamp(j)));
        if (captureData != null) {
            return captureData.getStreamNum();
        }
        String str = TAG;
        Log.e(str, "getStreamNumberByTimestamp: returned an error result with timestamp: " + j);
        return 0;
    }

    /* access modifiers changed from: private */
    /* access modifiers changed from: public */
    private void handleData(CaptureData.CaptureDataBean captureDataBean, long j, long j2) {
        if (captureDataBean.isDataReady()) {
            List<Long> list = this.mUnTrackCaptureDataBeanMap.get(j);
            if (list != null) {
                list.remove(Long.valueOf(j2));
                if (list.size() == 0) {
                    this.mUnTrackCaptureDataBeanMap.remove(j);
                    String str = TAG;
                    Log.d(str, "handleData: remove untrack size " + this.mUnTrackCaptureDataBeanMap.size());
                }
            }
            this.mCaptureDataBeanArray.remove(j2);
            tryToCallback(captureDataBean);
            return;
        }
        List<Long> list2 = this.mUnTrackCaptureDataBeanMap.get(j);
        if (list2 == null) {
            list2 = new ArrayList<>();
        }
        if (!list2.contains(Long.valueOf(j2))) {
            list2.add(Long.valueOf(j2));
            this.mUnTrackCaptureDataBeanMap.put(j, list2);
            String str2 = TAG;
            Log.d(str2, "handleData: added,  firstTimestamp " + j + " | timestamp " + j2 + "'s child size " + list2.size());
        }
    }

    private void tryToCallback(CaptureData.CaptureDataBean captureDataBean) {
        Integer valueOf = Integer.valueOf(captureDataBean.getResult().getSequenceId());
        long timeStamp = captureDataBean.getResult().getTimeStamp();
        long firstFrameTimestamp = getFirstFrameTimestamp(timeStamp);
        CaptureData captureData = this.mCaptureDataArray.get(Long.valueOf(firstFrameTimestamp));
        if (captureData != null) {
            captureData.putCaptureDataBean(captureDataBean);
            if (captureData.isDataReady()) {
                DataListener dataListener = captureData.getDataListener();
                if (dataListener != null) {
                    if (!captureData.isAbandoned()) {
                        dataListener.onParallelDataAvailable(captureData);
                    } else {
                        dataListener.onParallelDataAbandoned(captureData);
                    }
                }
                this.mCaptureDataArray.remove(Long.valueOf(firstFrameTimestamp));
                return;
            }
            return;
        }
        String str = "No task found with sequenceId: " + valueOf + " timestamp: " + timeStamp + "|" + firstFrameTimestamp;
        Log.e(TAG, str, new RuntimeException(str));
        captureDataBean.close();
        if (Build.IS_DEBUGGABLE) {
            throw new RuntimeException(str);
        }
    }

    public /* synthetic */ void f(long j) {
        long firstFrameTimestamp = getFirstFrameTimestamp(j);
        List<Long> list = this.mUnTrackCaptureDataBeanMap.get(firstFrameTimestamp);
        if (list != null) {
            String str = TAG;
            Log.d(str, "releaseData: unTrackDatas size " + list.size());
            for (Long l : list) {
                CaptureData.CaptureDataBean captureDataBean = this.mCaptureDataBeanArray.get(l.longValue());
                if (captureDataBean != null) {
                    String str2 = TAG;
                    Log.d(str2, "releaseData: close untrack image : " + l);
                    captureDataBean.close();
                    this.mCaptureDataBeanArray.remove(l.longValue());
                }
            }
            this.mUnTrackCaptureDataBeanMap.remove(firstFrameTimestamp);
        }
        CaptureData captureData = this.mCaptureDataArray.get(Long.valueOf(firstFrameTimestamp));
        if (captureData != null) {
            if (captureData.getCaptureDataBeanList() != null) {
                for (CaptureData.CaptureDataBean captureDataBean2 : captureData.getCaptureDataBeanList()) {
                    long timestamp = captureDataBean2.getTimestamp();
                    this.mCaptureDataBeanArray.remove(timestamp);
                    String str3 = TAG;
                    Log.d(str3, "releaseData: CaptureDataBean.timestamp > " + timestamp);
                }
            }
            DataListener dataListener = captureData.getDataListener();
            if (dataListener != null) {
                dataListener.onParallelDataAbandoned(captureData);
            }
            this.mCaptureDataArray.remove(Long.valueOf(firstFrameTimestamp));
        } else {
            Log.w(TAG, "releaseData: CaptureData is null");
            CaptureData.CaptureDataBean captureDataBean3 = this.mCaptureDataBeanArray.get(j);
            if (captureDataBean3 != null) {
                captureDataBean3.close();
                this.mCaptureDataBeanArray.remove(j);
            }
        }
        String str4 = TAG;
        Log.d(str4, "clearTimeStampData: X | mCaptureDataBeanArray.size > " + this.mCaptureDataBeanArray.size() + "|mCaptureDataArray.size > " + this.mCaptureDataArray.size());
    }

    public Handler getHandler() {
        return this.mHandler;
    }

    public boolean isAnyFrontProcessingByProcessor(ImageProcessor imageProcessor) {
        for (CaptureData captureData : this.mCaptureDataArray.values()) {
            ImageProcessor imageProcessor2 = captureData.getImageProcessor();
            if (imageProcessor2 == null) {
                String str = TAG;
                Log.w(str, "WARNING: isAnyFrontProcessingByProcessor: ImageProcessor is null! captureData = " + captureData);
            } else if (!captureData.isAbandoned() && Objects.equals(imageProcessor2, imageProcessor)) {
                return true;
            }
        }
        return false;
    }

    public synchronized void join(final Image image, final int i, final int i2) {
        if (this.mWorkThread.isAlive() && this.mHandler != null) {
            this.mHandler.post(new Runnable() {
                /* class com.xiaomi.camera.core.ParallelDataZipper.AnonymousClass3 */

                public void run() {
                    boolean z;
                    int i;
                    boolean z2;
                    long timestamp = image.getTimestamp();
                    CaptureData.CaptureDataBean captureDataBean = (CaptureData.CaptureDataBean) ParallelDataZipper.this.mCaptureDataBeanArray.get(timestamp);
                    long firstFrameTimestamp = ParallelDataZipper.this.getFirstFrameTimestamp(timestamp);
                    CaptureData captureData = (CaptureData) ParallelDataZipper.this.mCaptureDataArray.get(Long.valueOf(firstFrameTimestamp));
                    if (captureData == null) {
                        String str = ParallelDataZipper.TAG;
                        Log.e(str, "setImage: no capture data with timestamp: " + firstFrameTimestamp);
                        z2 = false;
                        i = 0;
                        z = false;
                    } else {
                        i = captureData.getStreamNum();
                        z = captureData.isRequireTuningData();
                        z2 = captureData.isSatFusionShot();
                    }
                    if (captureDataBean == null) {
                        captureDataBean = new CaptureData.CaptureDataBean(i, z, z2);
                        ParallelDataZipper.this.mCaptureDataBeanArray.append(timestamp, captureDataBean);
                    }
                    if (!(captureDataBean.getStreamNum() == i || i == 0)) {
                        String str2 = ParallelDataZipper.TAG;
                        Log.d(str2, "setImage: update stream number with: " + i);
                        captureDataBean.setStreamNum(i);
                    }
                    if (captureDataBean.isRequireTuningData() != z) {
                        captureDataBean.setRequireTuningData(z);
                    }
                    if (captureDataBean.isSatFusionShot() != z2) {
                        captureDataBean.setIsSatFusionShot(z2);
                    }
                    String str3 = ParallelDataZipper.TAG;
                    Log.d(str3, "setImage: timestamp=" + timestamp + ", streamNum=" + captureDataBean.getStreamNum() + ", fusion = " + z2);
                    if (!z2) {
                        captureDataBean.setImage(image, i);
                    } else if (i2 == 6) {
                        captureDataBean.setImage(image, 1);
                    } else {
                        captureDataBean.setImage(image, 0);
                    }
                    ParallelDataZipper.this.handleData(captureDataBean, firstFrameTimestamp, timestamp);
                }
            });
        }
    }

    public synchronized void join(@NonNull final ICustomCaptureResult iCustomCaptureResult, final boolean z) {
        if (!this.mWorkThread.isAlive() || this.mHandler == null) {
            throw new RuntimeException("Thread already die!");
        }
        this.mHandler.post(new Runnable() {
            /* class com.xiaomi.camera.core.ParallelDataZipper.AnonymousClass2 */

            public void run() {
                boolean z;
                boolean z2;
                long timeStamp = iCustomCaptureResult.getTimeStamp();
                int sequenceId = iCustomCaptureResult.getSequenceId();
                long firstFrameTimestamp = ParallelDataZipper.this.getFirstFrameTimestamp(timeStamp);
                CaptureData captureData = (CaptureData) ParallelDataZipper.this.mCaptureDataArray.get(Long.valueOf(firstFrameTimestamp));
                int i = 0;
                if (captureData == null) {
                    String str = ParallelDataZipper.TAG;
                    Log.e(str, "setResult: no capture data with timestamp: " + firstFrameTimestamp);
                    z2 = false;
                    z = false;
                } else {
                    i = captureData.getStreamNum();
                    z = captureData.isRequireTuningData();
                    z2 = captureData.isSatFusionShot();
                }
                CaptureData.CaptureDataBean captureDataBean = (CaptureData.CaptureDataBean) ParallelDataZipper.this.mCaptureDataBeanArray.get(timeStamp);
                if (captureDataBean == null) {
                    captureDataBean = new CaptureData.CaptureDataBean(i, z, z2);
                    ParallelDataZipper.this.mCaptureDataBeanArray.append(timeStamp, captureDataBean);
                }
                if (!(captureDataBean.getStreamNum() == i || i == 0)) {
                    String str2 = ParallelDataZipper.TAG;
                    Log.d(str2, "setResult: update stream number with: " + i);
                    captureDataBean.setStreamNum(i);
                }
                if (captureDataBean.isRequireTuningData() != z) {
                    captureDataBean.setRequireTuningData(z);
                }
                if (captureDataBean.isSatFusionShot() != z2) {
                    captureDataBean.setIsSatFusionShot(z2);
                }
                captureDataBean.setCaptureResult(iCustomCaptureResult, z);
                String str3 = ParallelDataZipper.TAG;
                Log.d(str3, "setResult: timestamp=" + timeStamp + " sequenceId=" + sequenceId + " streamNum=" + captureDataBean.getStreamNum() + " isFirst=" + z + " fusion = " + z2);
                ParallelDataZipper.this.handleData(captureDataBean, firstFrameTimestamp, timeStamp);
            }
        });
    }

    public void releaseData(long j) {
        Handler handler;
        String str = TAG;
        Log.d(str, "releaseData: E timestamp " + j);
        if (this.mWorkThread.isAlive() && (handler = this.mHandler) != null) {
            handler.post(new a(this, j));
        }
    }

    public synchronized void startTask(@NonNull final CaptureData captureData) {
        if (!this.mWorkThread.isAlive() || this.mHandler == null) {
            throw new RuntimeException("Thread already die!");
        }
        this.mHandler.post(new Runnable() {
            /* class com.xiaomi.camera.core.ParallelDataZipper.AnonymousClass1 */

            public void run() {
                String str = ParallelDataZipper.TAG;
                Log.d(str, "startTask: " + captureData);
                ParallelDataZipper.this.mCaptureDataArray.put(Long.valueOf(captureData.getCaptureTimestamp()), captureData);
            }
        });
    }
}
