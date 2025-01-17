package com.xiaomi.camera.processor;

import android.media.Image;
import androidx.annotation.NonNull;
import com.android.camera.log.Log;
import com.xiaomi.camera.base.PerformanceTracker;
import com.xiaomi.camera.core.CaptureData;
import com.xiaomi.camera.core.CaptureDataListener;
import com.xiaomi.camera.imagecodec.ImagePool;
import com.xiaomi.engine.FrameData;
import com.xiaomi.engine.MiaNodeJNI;
import com.xiaomi.engine.TaskSession;
import com.xiaomi.protocol.ICustomCaptureResult;
import java.util.ArrayList;
import java.util.List;

public class ClearShotProcessor implements AlgoProcessor {
    private static final String TAG = "ClearShotProcessor";

    private void onImageAvailable(CaptureData captureData, CaptureData.CaptureDataBean captureDataBean, ProcessResultListener processResultListener) {
        captureData.setMultiFrameProcessResult(captureDataBean);
        processResultListener.onProcessFinished(captureData, true);
    }

    @Override // com.xiaomi.camera.processor.AlgoProcessor
    public void doProcess(@NonNull CaptureData captureData, ProcessResultListener processResultListener, TaskSession taskSession) {
        Log.d(TAG, "doProcess: E");
        List<CaptureData.CaptureDataBean> captureDataBeanList = captureData.getCaptureDataBeanList();
        if (captureDataBeanList == null || captureDataBeanList.isEmpty()) {
            throw new IllegalArgumentException("taskBeanList is not allow to be empty!");
        }
        CaptureData.CaptureDataBean captureDataBean = new CaptureData.CaptureDataBean(captureData.getStreamNum(), captureData.isRequireTuningData(), captureData.isSatFusionShot());
        String str = TAG;
        Log.d(str, "doProcess: dataNum = " + captureDataBeanList.size());
        PerformanceTracker.trackAlgorithmProcess("[CLEARSHOT]", 0);
        ArrayList arrayList = new ArrayList();
        for (CaptureData.CaptureDataBean captureDataBean2 : captureDataBeanList) {
            arrayList.add(new FrameData(0, captureDataBean2.getResult().getSequenceId(), captureDataBean2.getResult().getFrameNumber(), captureDataBean2.getResult().getResults(), captureDataBean2.getMainImage()));
        }
        Image mainImage = captureDataBeanList.get(0).getMainImage();
        Image anEmptyImage = ImagePool.getInstance().getAnEmptyImage(new ImagePool.ImageFormat(mainImage.getWidth(), mainImage.getHeight(), mainImage.getFormat()));
        int process = MiaNodeJNI.getInstance().process(arrayList, anEmptyImage, 2, captureData.getStreamNum() == 2);
        if (process > arrayList.size() || process < 0) {
            String str2 = TAG;
            Log.w(str2, "doProcess: returned a error baseIndex: " + process);
            process = 0;
        }
        PerformanceTracker.trackAlgorithmProcess("[CLEARSHOT]", 1);
        String str3 = TAG;
        Log.d(str3, "doProcess: clearShot done. baseIndex = " + process);
        CaptureData.CaptureDataBean captureDataBean3 = captureDataBeanList.get(process);
        ICustomCaptureResult result = captureDataBean3.getResult();
        long timeStamp = result.getTimeStamp();
        anEmptyImage.setTimestamp(timeStamp);
        ImagePool.getInstance().queueImage(anEmptyImage);
        Image image = ImagePool.getInstance().getImage(timeStamp);
        captureDataBean.setImage(image, 0);
        ImagePool.getInstance().holdImage(image);
        CaptureDataListener captureDataListener = captureData.getCaptureDataListener();
        for (CaptureData.CaptureDataBean captureDataBean4 : captureDataBeanList) {
            if (captureDataBean4 != captureDataBean3) {
                Image mainImage2 = captureDataBean4.getMainImage();
                mainImage2.close();
                captureDataListener.onOriginalImageClosed(mainImage2);
                Image subImage = captureDataBean4.getSubImage();
                if (subImage != null) {
                    subImage.close();
                    captureDataListener.onOriginalImageClosed(subImage);
                }
                Image tuningImage = captureDataBean4.getTuningImage();
                if (tuningImage != null) {
                    tuningImage.close();
                    captureDataListener.onOriginalImageClosed(tuningImage);
                }
            }
        }
        captureDataBeanList.clear();
        Image mainImage3 = captureDataBean3.getMainImage();
        mainImage3.close();
        captureDataListener.onOriginalImageClosed(mainImage3);
        captureDataBean.setCaptureResult(result, true);
        if (captureData.isRequireTuningData()) {
            captureDataBean.setImage(captureDataBean3.getTuningImage(), 2);
        }
        Image subImage2 = captureDataBean3.getSubImage();
        if (subImage2 != null) {
            long timestamp = subImage2.getTimestamp();
            ImagePool.getInstance().queueImage(subImage2);
            Image image2 = ImagePool.getInstance().getImage(timestamp);
            captureDataBean.setImage(image2, 1);
            captureDataListener.onOriginalImageClosed(subImage2);
            if (timestamp != timeStamp) {
                image2.setTimestamp(timeStamp);
            }
            ImagePool.getInstance().holdImage(image2);
        }
        if (captureDataBean.isDataReady()) {
            onImageAvailable(captureData, captureDataBean, processResultListener);
        }
        Log.d(TAG, "doProcess: X");
    }
}
