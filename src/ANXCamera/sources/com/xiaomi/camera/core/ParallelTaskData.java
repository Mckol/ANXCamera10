package com.xiaomi.camera.core;

import android.media.Image;
import com.android.camera.LocalParallelService;
import com.android.camera.log.Log;
import com.xiaomi.camera.base.Constants;
import com.xiaomi.camera.core.ParallelTaskDataParameter;
import com.xiaomi.camera.liveshot.CircularMediaRecorder;
import com.xiaomi.protocol.ICustomCaptureResult;

public class ParallelTaskData {
    private static final String GROUPSHOT_ORIGINAL_SUFFIX = "_ORG";
    private static final String TAG = "ParallelTaskData";
    private int currentModuleIndex;
    private boolean isAbandoned;
    private boolean isAdaptiveSnapshotSize;
    private boolean isHWMFNRProcessing;
    private boolean isLiveShotTask;
    private boolean isNeedThumbnail;
    private boolean isPictureFilled;
    private int mAlgoType;
    private int mBurstNum;
    private int mCameraId;
    private ICustomCaptureResult mCaptureResult;
    private long mCaptureTime;
    private int[] mCoordinatesOfTheRegionUnderWatermarks;
    private long mCoverFrameTimestamp;
    private byte[] mDataOfTheRegionUnderWatermarks;
    private ParallelTaskDataParameter mDataParameter;
    private long mDateTakenTime;
    private boolean mInTimerBurstShotting;
    public boolean mIsFrontProcessing;
    private boolean mIsHdrSR;
    private boolean mIsParallelVTCameraSnapshot;
    private boolean mIsSatFusionShot;
    private boolean mIsSaveToHiddenFolder;
    private boolean mIsShot2Gallery;
    private byte[] mJpegImageData;
    private boolean mMemDebug;
    private int mParallelType;
    private byte[] mPortraitDepthData;
    private byte[] mPortraitRawData;
    private byte[] mRawImageData;
    private boolean mRequireTuningData;
    private String mSavePath;
    private LocalParallelService.ServiceStatusListener mServiceStatusListener;
    private long mTimestamp;
    private Image mTuningImage;
    private String mVideoPath;
    private boolean noGaussian;
    private int previewThumbnailHash;

    public ParallelTaskData(int i, long j, int i2, String str) {
        this(i, j, i2, str, 0);
    }

    public ParallelTaskData(int i, long j, int i2, String str, long j2) {
        this.currentModuleIndex = -1;
        this.isHWMFNRProcessing = false;
        this.isAbandoned = false;
        this.mCameraId = i;
        this.mTimestamp = j;
        this.mParallelType = i2;
        this.mSavePath = str;
        this.mDateTakenTime = System.currentTimeMillis();
        this.mCaptureTime = j2;
    }

    public ParallelTaskData(ParallelTaskData parallelTaskData) {
        this.currentModuleIndex = -1;
        this.isHWMFNRProcessing = false;
        this.isAbandoned = false;
        this.mParallelType = parallelTaskData.mParallelType;
        this.mIsShot2Gallery = parallelTaskData.mIsShot2Gallery;
        this.mIsSaveToHiddenFolder = parallelTaskData.mIsSaveToHiddenFolder;
        this.mTimestamp = parallelTaskData.mTimestamp;
        this.mCaptureResult = parallelTaskData.mCaptureResult;
        this.mJpegImageData = parallelTaskData.mJpegImageData;
        this.mRawImageData = parallelTaskData.mRawImageData;
        this.mPortraitRawData = parallelTaskData.mPortraitRawData;
        this.mPortraitDepthData = parallelTaskData.mPortraitDepthData;
        this.mSavePath = parallelTaskData.mSavePath;
        this.mDataParameter = parallelTaskData.mDataParameter;
        this.isNeedThumbnail = parallelTaskData.isNeedThumbnail;
        this.mVideoPath = parallelTaskData.mVideoPath;
        this.mCoverFrameTimestamp = parallelTaskData.mCoverFrameTimestamp;
        this.isLiveShotTask = parallelTaskData.isLiveShotTask;
        this.isPictureFilled = parallelTaskData.isPictureFilled;
        this.mDataOfTheRegionUnderWatermarks = parallelTaskData.mDataOfTheRegionUnderWatermarks;
        this.mCoordinatesOfTheRegionUnderWatermarks = parallelTaskData.mCoordinatesOfTheRegionUnderWatermarks;
        this.mCameraId = parallelTaskData.mCameraId;
        this.mDateTakenTime = parallelTaskData.mDateTakenTime;
        this.mCaptureTime = parallelTaskData.mCaptureTime;
        this.mRequireTuningData = parallelTaskData.mRequireTuningData;
        this.mTuningImage = parallelTaskData.mTuningImage;
        this.noGaussian = parallelTaskData.noGaussian;
        this.mMemDebug = parallelTaskData.mMemDebug;
        this.mIsHdrSR = parallelTaskData.mIsHdrSR;
    }

    public void checkThread() {
    }

    public ParallelTaskData cloneTaskData(int i) {
        String str;
        ParallelTaskData parallelTaskData = new ParallelTaskData(this);
        String savePath = getSavePath();
        String str2 = GROUPSHOT_ORIGINAL_SUFFIX;
        if (i > 0) {
            str2 = str2 + "_" + i;
        }
        int lastIndexOf = savePath.lastIndexOf(".");
        if (lastIndexOf > 0) {
            str = savePath.substring(0, lastIndexOf) + str2 + savePath.substring(lastIndexOf);
        } else {
            str = savePath + str2;
        }
        Log.d(TAG, "[1] cloneTaskData: path=" + str);
        parallelTaskData.setSavePath(str);
        parallelTaskData.setNeedThumbnail(false);
        ParallelTaskDataParameter.Builder builder = new ParallelTaskDataParameter.Builder(getDataParameter());
        builder.setHasDualWaterMark(false);
        builder.setTimeWaterMarkString(null);
        builder.setSaveGroupshotPrimitive(false);
        parallelTaskData.fillParameter(builder.build());
        return parallelTaskData;
    }

    public synchronized void fillJpegData(byte[] bArr, int i) {
        checkThread();
        if (i != 0) {
            if (i != 1) {
                if (i != 2) {
                    if (i == 3) {
                        if (this.mRawImageData == null) {
                            this.mRawImageData = bArr;
                        } else {
                            throw new RuntimeException("algo fillJpegData: raw already set");
                        }
                    }
                } else if (this.mPortraitDepthData == null) {
                    this.mPortraitDepthData = new byte[bArr.length];
                    System.arraycopy(bArr, 0, this.mPortraitDepthData, 0, bArr.length);
                } else {
                    throw new RuntimeException("algo fillJpegData: depth already set");
                }
            } else if (this.mPortraitRawData == null) {
                this.mPortraitRawData = bArr;
            } else {
                throw new RuntimeException("algo fillJpegData: portrait raw already set");
            }
        } else if (this.mJpegImageData == null) {
            this.isPictureFilled = true;
            this.mJpegImageData = bArr;
        } else {
            throw new RuntimeException("algo fillJpegData: jpeg already set");
        }
        String str = TAG;
        Log.d(str, "fillJpegData: jpegData=" + bArr + "; imageType=" + i);
    }

    public void fillParameter(ParallelTaskDataParameter parallelTaskDataParameter) {
        this.mDataParameter = parallelTaskDataParameter;
    }

    public synchronized void fillVideoPath(String str, long j) {
        boolean z;
        checkThread();
        if (this.mVideoPath == null) {
            this.mVideoPath = str;
            this.mCoverFrameTimestamp = j;
            String str2 = TAG;
            StringBuilder sb = new StringBuilder();
            sb.append("fillVideoPath: isVideoEmpty = ");
            if (str != null) {
                if (!CircularMediaRecorder.VideoClipSavingCallback.EMPTY_VIDEO_PATH.equals(str)) {
                    z = false;
                    sb.append(z);
                    sb.append(", timestamp = ");
                    sb.append(j);
                    Log.d(str2, sb.toString());
                }
            }
            z = true;
            sb.append(z);
            sb.append(", timestamp = ");
            sb.append(j);
            Log.d(str2, sb.toString());
        } else {
            throw new IllegalStateException("algo fillVideoPath: microvideo already set");
        }
    }

    public int getAlgoType() {
        return this.mAlgoType;
    }

    public int getBurstNum() {
        return this.mBurstNum;
    }

    public int getCameraId() {
        return this.mCameraId;
    }

    public ICustomCaptureResult getCaptureResult() {
        return this.mCaptureResult;
    }

    public long getCaptureTime() {
        return this.mCaptureTime;
    }

    public int[] getCoordinatesOfTheRegionUnderWatermarks() {
        return this.mCoordinatesOfTheRegionUnderWatermarks;
    }

    public synchronized long getCoverFrameTimestamp() {
        return this.mCoverFrameTimestamp;
    }

    public int getCurrentModuleIndex() {
        return this.currentModuleIndex;
    }

    public byte[] getDataOfTheRegionUnderWatermarks() {
        return this.mDataOfTheRegionUnderWatermarks;
    }

    public ParallelTaskDataParameter getDataParameter() {
        return this.mDataParameter;
    }

    public long getDateTakenTime() {
        return this.mDateTakenTime;
    }

    public byte[] getJpegImageData() {
        return this.mJpegImageData;
    }

    public synchronized String getMicroVideoPath() {
        return this.mVideoPath;
    }

    public int getParallelType() {
        return this.mParallelType;
    }

    public byte[] getPortraitDepthData() {
        return this.mPortraitDepthData;
    }

    public byte[] getPortraitRawData() {
        return this.mPortraitRawData;
    }

    public int getPreviewThumbnailHash() {
        return this.previewThumbnailHash;
    }

    public byte[] getRawImageData() {
        return this.mRawImageData;
    }

    public String getSavePath() {
        return this.mSavePath;
    }

    public LocalParallelService.ServiceStatusListener getServiceStatusListener() {
        return this.mServiceStatusListener;
    }

    public long getTimestamp() {
        return this.mTimestamp;
    }

    public Image getTuningImage() {
        return this.mTuningImage;
    }

    public boolean isAbandoned() {
        return this.isAbandoned;
    }

    public boolean isAdaptiveSnapshotSize() {
        return this.isAdaptiveSnapshotSize;
    }

    public boolean isDataFilled(int i) {
        return i != 0 ? i != 1 ? i != 2 ? i == 3 && this.mRawImageData != null : this.mPortraitDepthData != null : this.mPortraitRawData != null : this.mJpegImageData != null;
    }

    public boolean isHWMFNRProcessing() {
        return this.isHWMFNRProcessing;
    }

    public boolean isHdrSR() {
        return this.mIsHdrSR;
    }

    public boolean isInTimerBurstShotting() {
        return this.mInTimerBurstShotting;
    }

    /* JADX INFO: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARNING: Code restructure failed: missing block: B:8:0x0010, code lost:
        if (r7.mRawImageData != null) goto L_0x0012;
     */
    public synchronized boolean isJpegDataReady() {
        boolean z;
        boolean z2 = true;
        switch (this.mParallelType) {
            case Constants.ShotType.INTENT_PARALLEL_DUAL_SHOT:
            case -3:
            case 2:
            case 6:
            case 7:
            case 11:
                if (!(this.mJpegImageData == null || this.mPortraitRawData == null || this.mPortraitDepthData == null)) {
                    z = true;
                    break;
                }
                z = false;
                break;
            case -6:
            case -5:
            case -2:
            case -1:
            case 0:
            case 5:
            case 8:
            case 9:
            case 10:
                if (this.mJpegImageData != null) {
                    z = true;
                    break;
                }
                z = false;
                break;
            case -4:
            case 3:
            case 4:
            default:
                z = false;
                break;
            case 1:
                if (this.mJpegImageData != null) {
                    break;
                }
                z = false;
                break;
        }
        String str = TAG;
        StringBuilder sb = new StringBuilder();
        sb.append("isJpegDataReady: object = ");
        sb.append(this);
        sb.append("; mParallelType = ");
        sb.append(this.mParallelType);
        sb.append("; mJpegImageData = ");
        sb.append(this.mJpegImageData);
        sb.append("; mRawImageData = ");
        sb.append(this.mRawImageData);
        sb.append("; mPortraitRawData = ");
        sb.append(this.mPortraitRawData);
        sb.append("; mPortraitDepthData = ");
        sb.append(this.mPortraitDepthData);
        sb.append("; isVideoEmpty = ");
        if (this.mVideoPath != null) {
            if (!CircularMediaRecorder.VideoClipSavingCallback.EMPTY_VIDEO_PATH.equals(this.mVideoPath)) {
                z2 = false;
            }
        }
        sb.append(z2);
        sb.append("; result = ");
        sb.append(z);
        Log.d(str, sb.toString());
        return z;
    }

    public synchronized boolean isLiveShotTask() {
        return this.isLiveShotTask;
    }

    public boolean isMemDebug() {
        return this.mMemDebug;
    }

    public boolean isNeedThumbnail() {
        return this.isNeedThumbnail;
    }

    public boolean isParallelVTCameraSnapshot() {
        return this.mIsParallelVTCameraSnapshot;
    }

    public synchronized boolean isPictureFilled() {
        return this.isPictureFilled;
    }

    public boolean isRequireTuningData() {
        return this.mRequireTuningData;
    }

    public boolean isSatFusionShot() {
        return this.mIsSatFusionShot;
    }

    public boolean isSaveToHiddenFolder() {
        return this.mIsSaveToHiddenFolder;
    }

    public boolean isShot2Gallery() {
        return this.mIsShot2Gallery;
    }

    public boolean noGaussian() {
        return this.noGaussian;
    }

    public void refillJpegData(byte[] bArr) {
        this.mJpegImageData = bArr;
        this.isPictureFilled = true;
    }

    public void releaseImageData() {
        this.mVideoPath = null;
        this.mJpegImageData = null;
        this.mRawImageData = null;
        this.mPortraitRawData = null;
        this.mPortraitDepthData = null;
        this.isPictureFilled = false;
        this.mDataOfTheRegionUnderWatermarks = null;
        this.mCoordinatesOfTheRegionUnderWatermarks = null;
        this.mRequireTuningData = false;
        this.mTuningImage = null;
        this.mIsSatFusionShot = false;
    }

    public void setAbandoned(boolean z) {
        this.isAbandoned = z;
    }

    public void setAdaptiveSnapshotSize(boolean z) {
        this.isAdaptiveSnapshotSize = z;
    }

    public void setAlgoType(int i) {
        this.mAlgoType = i;
    }

    public void setBurstNum(int i) {
        this.mBurstNum = i;
    }

    public void setCaptureResult(ICustomCaptureResult iCustomCaptureResult) {
        this.mCaptureResult = iCustomCaptureResult;
    }

    public void setCoordinatesOfTheRegionUnderWatermarks(int[] iArr) {
        this.mCoordinatesOfTheRegionUnderWatermarks = iArr;
    }

    public void setCurrentModuleIndex(int i) {
        this.currentModuleIndex = i;
    }

    public void setDataOfTheRegionUnderWatermarks(byte[] bArr) {
        this.mDataOfTheRegionUnderWatermarks = bArr;
    }

    public void setHWMFNRProcessing(boolean z) {
        this.isHWMFNRProcessing = z;
    }

    public void setHdrSR(boolean z) {
        this.mIsHdrSR = z;
    }

    public boolean setInTimerBurstShotting(boolean z) {
        if (this.mInTimerBurstShotting == z) {
            return false;
        }
        this.mInTimerBurstShotting = z;
        return true;
    }

    public void setIsSatFusionShot(boolean z) {
        this.mIsSatFusionShot = z;
    }

    public synchronized void setLiveShotTask(boolean z) {
        this.isLiveShotTask = z;
    }

    public void setMemDebug(boolean z) {
        this.mMemDebug = z;
    }

    public void setNeedThumbnail(boolean z) {
        this.isNeedThumbnail = z;
    }

    public void setNoGaussian(boolean z) {
        this.noGaussian = z;
    }

    public void setParallelVTCameraSnapshot(boolean z) {
        this.mIsParallelVTCameraSnapshot = z;
    }

    public synchronized void setPictureFilled(boolean z) {
        this.isPictureFilled = z;
    }

    public void setPreviewThumbnailHash(int i) {
        this.previewThumbnailHash = i;
    }

    public void setRequireTuningData(boolean z) {
        this.mRequireTuningData = z;
    }

    public void setSavePath(String str) {
        this.mSavePath = str;
    }

    public void setSaveToHiddenFolder(boolean z) {
        this.mIsSaveToHiddenFolder = z;
    }

    public void setServiceStatusListener(LocalParallelService.ServiceStatusListener serviceStatusListener) {
        this.mServiceStatusListener = serviceStatusListener;
    }

    public boolean setShot2Gallery(boolean z) {
        if (this.mIsShot2Gallery == z) {
            return false;
        }
        this.mIsShot2Gallery = z;
        return true;
    }

    public void setTimestamp(long j) {
        this.mTimestamp = j;
    }

    public void setTuningImage(Image image) {
        this.mTuningImage = image;
    }
}
