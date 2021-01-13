package com.xiaomi.camera.core;

import android.media.Image;
import android.util.Size;
import androidx.annotation.NonNull;
import com.android.camera.log.Log;
import com.xiaomi.camera.core.ParallelDataZipper;
import com.xiaomi.protocol.ICustomCaptureResult;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class CaptureData {
    private static final String TAG = "CaptureData";
    private Size mAlgoSize;
    private int mAlgoType;
    private int mAlreadyDataNum = 0;
    private int mBurstNum;
    private List<CaptureDataBean> mCaptureDataBeanList = new ArrayList();
    private CaptureDataListener mCaptureDataListener;
    private long mCaptureTimestamp;
    private boolean mCapturedByFrontCamera;
    private ParallelDataZipper.DataListener mDataListener;
    private List<CaptureDataBean> mHdrSRBeans;
    private ImageProcessor mImageProcessor;
    private boolean mIsAbandoned;
    private boolean mIsHdrSR;
    private boolean mIsMoonMode;
    private boolean mIsSatFusionShot;
    private CaptureDataBean mMultiFrameProcessResult;
    private boolean mRequireTuningData;
    private boolean mSaveInputImage;
    private int mStreamNum;

    public static class CaptureDataBean {
        private boolean mIsFirstResult;
        private boolean mIsSatFusionShot;
        private Image mMainImage;
        private boolean mRequireTuningData;
        private ICustomCaptureResult mResult;
        private int mStreamNum;
        private Image mSubImage;
        private Image mTuningImage;

        public CaptureDataBean(int i, boolean z, boolean z2) {
            this.mStreamNum = i;
            this.mRequireTuningData = z;
            this.mIsSatFusionShot = z2;
        }

        public void close() {
            Image image = this.mMainImage;
            if (image != null) {
                image.close();
                this.mMainImage = null;
            }
            Image image2 = this.mSubImage;
            if (image2 != null) {
                image2.close();
                this.mSubImage = null;
            }
            Image image3 = this.mTuningImage;
            if (image3 != null) {
                image3.close();
                this.mTuningImage = null;
            }
        }

        public Image getMainImage() {
            return this.mMainImage;
        }

        public ICustomCaptureResult getResult() {
            return this.mResult;
        }

        public int getStreamNum() {
            return this.mStreamNum;
        }

        public Image getSubImage() {
            return this.mSubImage;
        }

        public long getTimestamp() {
            Image image = this.mMainImage;
            if (image != null) {
                return image.getTimestamp();
            }
            Image image2 = this.mSubImage;
            if (image2 != null) {
                return image2.getTimestamp();
            }
            return 0;
        }

        public Image getTuningImage() {
            return this.mTuningImage;
        }

        public boolean isDataReady() {
            boolean z = !this.mRequireTuningData || this.mTuningImage != null;
            int i = this.mStreamNum;
            return 2 == i ? (this.mResult == null || this.mMainImage == null || this.mSubImage == null || !z) ? false : true : 1 == i && this.mResult != null && this.mMainImage != null && z;
        }

        public boolean isFirstResult() {
            return this.mIsFirstResult;
        }

        public boolean isRequireTuningData() {
            return this.mRequireTuningData;
        }

        public boolean isSatFusionShot() {
            return this.mIsSatFusionShot;
        }

        public void setCaptureResult(ICustomCaptureResult iCustomCaptureResult, boolean z) {
            this.mResult = iCustomCaptureResult;
            this.mIsFirstResult = z;
        }

        public void setImage(Image image, int i) {
            if (i == 0) {
                this.mMainImage = image;
            } else if (i == 1) {
                this.mSubImage = image;
            } else if (i == 2) {
                this.mTuningImage = image;
            } else {
                String str = CaptureData.TAG;
                Log.e(str, "setImage: unknown imageFlag: " + i);
            }
        }

        public void setIsSatFusionShot(boolean z) {
            this.mIsSatFusionShot = z;
        }

        public void setRequireTuningData(boolean z) {
            this.mRequireTuningData = z;
        }

        public void setStreamNum(int i) {
            this.mStreamNum = i;
        }
    }

    public CaptureData(int i, int i2, int i3, long j, boolean z, @NonNull ImageProcessor imageProcessor) {
        this.mAlgoType = i;
        this.mStreamNum = i2;
        this.mBurstNum = i3;
        this.mCaptureTimestamp = j;
        this.mIsAbandoned = z;
        this.mImageProcessor = imageProcessor;
    }

    public Size getAlgoSize() {
        return this.mAlgoSize;
    }

    public int getAlgoType() {
        return this.mAlgoType;
    }

    public int getBurstNum() {
        return this.mBurstNum;
    }

    public List<CaptureDataBean> getCaptureDataBeanList() {
        return this.mCaptureDataBeanList;
    }

    public CaptureDataListener getCaptureDataListener() {
        return this.mCaptureDataListener;
    }

    public long getCaptureTimestamp() {
        return this.mCaptureTimestamp;
    }

    public ParallelDataZipper.DataListener getDataListener() {
        return this.mDataListener;
    }

    public List<CaptureDataBean> getHDRSRResult() {
        return this.mHdrSRBeans;
    }

    public ImageProcessor getImageProcessor() {
        return this.mImageProcessor;
    }

    public CaptureDataBean getMultiFrameProcessResult() {
        return this.mMultiFrameProcessResult;
    }

    public int getStreamNum() {
        return this.mStreamNum;
    }

    public boolean isAbandoned() {
        return this.mIsAbandoned;
    }

    public boolean isCapturedByFrontCamera() {
        return this.mCapturedByFrontCamera;
    }

    public boolean isDataReady() {
        return this.mAlreadyDataNum == this.mBurstNum;
    }

    public boolean isHdrSR() {
        return this.mIsHdrSR;
    }

    public boolean isMoonMode() {
        return this.mIsMoonMode;
    }

    public boolean isRequireTuningData() {
        return this.mRequireTuningData;
    }

    public boolean isSatFusionShot() {
        return this.mIsSatFusionShot;
    }

    public boolean isSaveInputImage() {
        return this.mSaveInputImage;
    }

    public void putCaptureDataBean(CaptureDataBean captureDataBean) {
        this.mCaptureDataBeanList.add(captureDataBean);
        this.mAlreadyDataNum++;
    }

    public void setAlgoSize(Size size) {
        if (size != null) {
            this.mAlgoSize = new Size(size.getWidth(), size.getHeight());
        }
    }

    public void setCapturedByFrontCamera(boolean z) {
        this.mCapturedByFrontCamera = z;
    }

    public void setDataListener(ParallelDataZipper.DataListener dataListener) {
        this.mDataListener = dataListener;
    }

    public void setHDRSRResult(List<CaptureDataBean> list) {
        this.mHdrSRBeans = list;
    }

    public void setHdrSR(boolean z) {
        this.mIsHdrSR = z;
    }

    public void setIsSatFusionShot(boolean z) {
        this.mIsSatFusionShot = z;
    }

    public void setMoonMode(boolean z) {
        this.mIsMoonMode = z;
    }

    public void setMultiFrameProcessListener(CaptureDataListener captureDataListener) {
        this.mCaptureDataListener = captureDataListener;
    }

    public void setMultiFrameProcessResult(CaptureDataBean captureDataBean) {
        this.mMultiFrameProcessResult = captureDataBean;
    }

    public void setRequireTuningData(boolean z) {
        this.mRequireTuningData = z;
    }

    public void setSaveInputImage(boolean z) {
        this.mSaveInputImage = z;
    }

    public String toString() {
        return String.format(Locale.ENGLISH, "CaptureData{algoType=%d, streamNum=%d, burstNum=%d, captureTimestamp=%d, isAbandoned=%b}", Integer.valueOf(this.mAlgoType), Integer.valueOf(this.mStreamNum), Integer.valueOf(this.mBurstNum), Long.valueOf(this.mCaptureTimestamp), Boolean.valueOf(this.mIsAbandoned));
    }
}
