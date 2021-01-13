package com.xiaomi.camera.processor;

import android.hardware.camera2.CaptureRequest;
import android.hardware.camera2.impl.CameraMetadataNative;
import android.media.Image;
import androidx.annotation.NonNull;
import com.android.camera.log.Log;
import com.android.camera2.vendortag.CaptureRequestVendorTags;
import com.xiaomi.camera.base.PerformanceTracker;
import com.xiaomi.camera.core.CaptureData;
import com.xiaomi.camera.core.CaptureDataListener;
import com.xiaomi.camera.imagecodec.ImagePool;
import com.xiaomi.engine.FrameData;
import com.xiaomi.engine.TaskSession;
import com.xiaomi.protocol.ICustomCaptureResult;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class SuperResolutionProcessor implements AlgoProcessor {
    private static final String TAG = "SRProcessor";
    private int mOutputHeight;
    private int mOutputWidth;

    private void onImageAvailable(CaptureData captureData, CaptureData.CaptureDataBean captureDataBean, ProcessResultListener processResultListener) {
        captureData.setMultiFrameProcessResult(captureDataBean);
        processResultListener.onProcessFinished(captureData, !captureData.isMoonMode());
    }

    private void onImageAvailable(CaptureData captureData, List<CaptureData.CaptureDataBean> list, ProcessResultListener processResultListener) {
        captureData.setMultiFrameProcessResult(list.get(0));
        captureData.setHDRSRResult(list);
        processResultListener.onProcessFinished(captureData, !captureData.isMoonMode());
    }

    @Override // com.xiaomi.camera.processor.AlgoProcessor
    public void doProcess(@NonNull CaptureData captureData, ProcessResultListener processResultListener, TaskSession taskSession) {
        boolean z;
        ArrayList arrayList;
        boolean z2;
        int i;
        boolean z3;
        Iterator<CaptureData.CaptureDataBean> it;
        Log.d(TAG, "doProcess: E");
        List<CaptureData.CaptureDataBean> captureDataBeanList = captureData.getCaptureDataBeanList();
        if (captureDataBeanList == null || captureDataBeanList.isEmpty()) {
            throw new IllegalArgumentException("taskBeanList is not allow to be empty!");
        }
        CaptureData.CaptureDataBean captureDataBean = new CaptureData.CaptureDataBean(captureData.getStreamNum(), captureData.isRequireTuningData(), captureData.isSatFusionShot());
        Log.d(TAG, "doProcess: dataNum = " + captureDataBeanList.size());
        PerformanceTracker.trackAlgorithmProcess("[SR]", 0);
        ArrayList arrayList2 = new ArrayList();
        boolean isHdrSR = captureData.isHdrSR();
        ArrayList arrayList3 = null;
        if (isHdrSR) {
            arrayList3 = new ArrayList();
        }
        Iterator<CaptureData.CaptureDataBean> it2 = captureDataBeanList.iterator();
        while (it2.hasNext()) {
            CaptureData.CaptureDataBean next = it2.next();
            CameraMetadataNative results = next.getResult().getResults();
            try {
                it = it2;
                try {
                    results.set(CaptureRequestVendorTags.CONTROL_SAT_FUSION_IMAGE_TYPE, (byte) 0);
                    Log.d(TAG, "update metadata with image flag: 0");
                } catch (Exception unused) {
                }
            } catch (Exception unused2) {
                it = it2;
            }
            if (!isHdrSR) {
                arrayList2.add(new FrameData(0, next.getResult().getSequenceId(), next.getResult().getFrameNumber(), results, next.getMainImage()));
            } else if (((Integer) next.getResult().getRequest().get(CaptureRequest.CONTROL_AE_EXPOSURE_COMPENSATION)).intValue() == 0) {
                arrayList2.add(new FrameData(0, next.getResult().getSequenceId(), next.getResult().getFrameNumber(), results, next.getMainImage()));
            } else {
                arrayList3.add(next);
            }
            it2 = it;
        }
        Image mainImage = captureDataBeanList.get(0).getMainImage();
        int i2 = this.mOutputWidth;
        if (i2 <= 0) {
            i2 = mainImage.getWidth();
        }
        int i3 = this.mOutputHeight;
        if (i3 <= 0) {
            i3 = mainImage.getHeight();
        }
        Image anEmptyImage = ImagePool.getInstance().getAnEmptyImage(new ImagePool.ImageFormat(i2, i3, mainImage.getFormat()));
        int processFrameWithSync = taskSession.processFrameWithSync(arrayList2, anEmptyImage, 0);
        if (processFrameWithSync > arrayList2.size() || processFrameWithSync < 0) {
            Log.w(TAG, "doProcess: returned a error baseIndex: " + processFrameWithSync);
            processFrameWithSync = 0;
        }
        PerformanceTracker.trackAlgorithmProcess("[SR]", 1);
        Log.d(TAG, "doProcess: SR done. baseIndex = " + processFrameWithSync);
        CaptureData.CaptureDataBean captureDataBean2 = captureDataBeanList.get(processFrameWithSync);
        ICustomCaptureResult result = captureDataBean2.getResult();
        long timeStamp = result.getTimeStamp();
        if (captureData.isSatFusionShot()) {
            Log.d(TAG, "doProcess: dataNum = " + captureDataBeanList.size());
            PerformanceTracker.trackAlgorithmProcess("[SR]", 0);
            ArrayList arrayList4 = new ArrayList();
            Iterator<CaptureData.CaptureDataBean> it3 = captureDataBeanList.iterator();
            while (it3.hasNext()) {
                CaptureData.CaptureDataBean next2 = it3.next();
                CameraMetadataNative results2 = next2.getResult().getResults();
                try {
                    z3 = isHdrSR;
                    try {
                        results2.set(CaptureRequestVendorTags.CONTROL_SAT_FUSION_IMAGE_TYPE, (byte) 1);
                        Log.d(TAG, "update metadata with image flag: 1");
                    } catch (Exception unused3) {
                    }
                } catch (Exception unused4) {
                    z3 = isHdrSR;
                }
                arrayList4.add(new FrameData(1, next2.getResult().getSequenceId(), next2.getResult().getFrameNumber(), results2, next2.getSubImage()));
                it3 = it3;
                isHdrSR = z3;
            }
            z = isHdrSR;
            Image subImage = captureDataBeanList.get(0).getSubImage();
            arrayList = arrayList3;
            Image anEmptyImage2 = ImagePool.getInstance().getAnEmptyImage(new ImagePool.ImageFormat(subImage.getWidth(), subImage.getHeight(), subImage.getFormat()));
            int processFrameWithSync2 = taskSession.processFrameWithSync(arrayList4, anEmptyImage2, 0);
            if (processFrameWithSync2 > arrayList4.size() || processFrameWithSync2 < 0) {
                Log.w(TAG, "doProcess: returned a error baseIndex: " + processFrameWithSync2);
                i = 1;
                processFrameWithSync2 = 0;
            } else {
                i = 1;
            }
            PerformanceTracker.trackAlgorithmProcess("[SR]", i);
            Log.d(TAG, "doProcess: SR done. baseIndex = " + processFrameWithSync2);
            anEmptyImage2.setTimestamp(timeStamp);
            ImagePool.getInstance().queueImage(anEmptyImage2);
            Image image = ImagePool.getInstance().getImage(timeStamp);
            z2 = true;
            captureDataBean.setImage(image, 1);
            ImagePool.getInstance().holdImage(image);
        } else {
            z = isHdrSR;
            arrayList = arrayList3;
            z2 = true;
        }
        captureDataBean.setCaptureResult(result, z2);
        if (captureData.isRequireTuningData()) {
            captureDataBean.setImage(captureDataBean2.getTuningImage(), 2);
        }
        anEmptyImage.setTimestamp(timeStamp);
        ImagePool.getInstance().queueImage(anEmptyImage);
        Image image2 = ImagePool.getInstance().getImage(timeStamp);
        captureDataBean.setImage(image2, 0);
        ImagePool.getInstance().holdImage(image2);
        CaptureDataListener captureDataListener = captureData.getCaptureDataListener();
        for (CaptureData.CaptureDataBean captureDataBean3 : captureDataBeanList) {
            if (captureDataBean3 != captureDataBean2 && (arrayList == null || !arrayList.contains(captureDataBean3))) {
                Image mainImage2 = captureDataBean3.getMainImage();
                mainImage2.close();
                captureDataListener.onOriginalImageClosed(mainImage2);
                Image subImage2 = captureDataBean3.getSubImage();
                if (subImage2 != null) {
                    subImage2.close();
                    captureDataListener.onOriginalImageClosed(subImage2);
                }
                Image tuningImage = captureDataBean3.getTuningImage();
                if (tuningImage != null) {
                    tuningImage.close();
                    captureDataListener.onOriginalImageClosed(tuningImage);
                }
            }
            arrayList = arrayList;
        }
        captureDataBeanList.clear();
        Image mainImage3 = captureDataBean2.getMainImage();
        mainImage3.close();
        captureDataListener.onOriginalImageClosed(mainImage3);
        Image subImage3 = captureDataBean2.getSubImage();
        if (subImage3 != null) {
            if (captureData.isSatFusionShot()) {
                subImage3.close();
                captureDataListener.onOriginalImageClosed(subImage3);
            } else {
                long timestamp = subImage3.getTimestamp();
                ImagePool.getInstance().queueImage(subImage3);
                Image image3 = ImagePool.getInstance().getImage(timestamp);
                captureDataBean.setImage(image3, 1);
                captureDataListener.onOriginalImageClosed(subImage3);
                if (timestamp != timeStamp) {
                    image3.setTimestamp(timeStamp);
                }
                ImagePool.getInstance().holdImage(image3);
            }
        }
        if (captureDataBean.isDataReady()) {
            if (z) {
                arrayList.add(0, captureDataBean);
                onImageAvailable(captureData, arrayList, processResultListener);
            } else {
                onImageAvailable(captureData, captureDataBean, processResultListener);
            }
        }
        Log.d(TAG, "doProcess: X");
    }

    public void setOutputSize(int i, int i2) {
        if (i > 0 && i2 > 0) {
            Log.d(TAG, "setOutputSize: " + i + "x" + i2);
            this.mOutputWidth = i;
            this.mOutputHeight = i2;
        }
    }
}
