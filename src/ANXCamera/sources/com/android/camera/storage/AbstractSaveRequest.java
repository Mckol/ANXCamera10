package com.android.camera.storage;

import android.location.Location;
import android.net.Uri;
import b.c.a.c;
import com.android.camera.CameraSettings;
import com.android.camera.Util;
import com.android.camera.effect.EffectController;
import com.android.camera.effect.FilterInfo;
import com.android.camera.effect.draw_mode.DrawJPEGAttribute;
import com.android.camera.lib.compatibility.util.CompatibilityUtils;
import com.android.camera.log.Log;
import com.android.camera.watermark.WaterMarkData;
import com.android.camera2.PortraitDepthMap;
import com.android.gallery3d.exif.ExifHelper;
import com.android.gallery3d.exif.ExifInterface;
import com.xiaomi.camera.base.Constants;
import com.xiaomi.camera.base.PerformanceTracker;
import com.xiaomi.camera.core.ParallelTaskData;
import com.xiaomi.camera.core.ParallelTaskDataParameter;
import com.xiaomi.camera.core.PictureInfo;
import com.xiaomi.camera.liveshot.CircularMediaRecorder;
import java.lang.ref.WeakReference;
import java.util.List;
import java.util.Locale;

public abstract class AbstractSaveRequest implements SaveRequest {
    private static final String TAG = "AbstractSaveRequest";
    protected long date;
    public int height;
    protected ParallelTaskData mParallelTaskData;
    private WeakReference<SaverCallback> mSaverCallbackRef;
    public int orientation;
    public int width;

    private DrawJPEGAttribute getDrawJPEGAttribute(byte[] bArr, int i, int i2, int i3, boolean z, int i4, int i5, Location location, String str, int i6, int i7, float f, String str2, boolean z2, boolean z3, String str3, List<WaterMarkData> list, boolean z4, PictureInfo pictureInfo, int i8, int i9, boolean z5) {
        return new DrawJPEGAttribute(bArr, z, i4 > i5 ? Math.max(i, i2) : Math.min(i, i2), i5 > i4 ? Math.max(i, i2) : Math.min(i, i2), i4, i5, i3, EffectController.getInstance().copyEffectRectAttribute(), location == null ? null : new Location(location), str, System.currentTimeMillis(), i6, i7, f, pictureInfo.isFrontMirror(), str2, z2, pictureInfo, list, CameraSettings.isDualCameraWaterMarkOpen() || CameraSettings.isFrontCameraWaterMarkOpen(), z3, CameraSettings.isTimeWaterMarkOpen() ? str3 : null, z4, i8, i9, z5);
    }

    private SaverCallback getSaverCallback() {
        WeakReference<SaverCallback> weakReference = this.mSaverCallbackRef;
        if (weakReference != null) {
            return weakReference.get();
        }
        return null;
    }

    private void parserMimojiCaptureTask(ParallelTaskData parallelTaskData) {
        int i;
        int i2;
        ParallelTaskDataParameter parallelTaskDataParameter;
        ParallelTaskDataParameter dataParameter = parallelTaskData.getDataParameter();
        byte[] jpegImageData = parallelTaskData.getJpegImageData();
        int width2 = dataParameter.getOutputSize().getWidth();
        int height2 = dataParameter.getOutputSize().getHeight();
        int jpegRotation = dataParameter.getJpegRotation();
        int filterId = dataParameter.getFilterId();
        ExifInterface exif = ExifInterface.getExif(jpegImageData);
        int orientation2 = ExifInterface.getOrientation(exif);
        boolean z = EffectController.getInstance().hasEffect(false) || filterId != FilterInfo.FILTER_ID_NONE;
        String createJpegName = Util.createJpegName(System.currentTimeMillis());
        if (parallelTaskData.isAdaptiveSnapshotSize()) {
            int imageWidth = ExifInterface.getImageWidth(exif);
            i = ExifInterface.getImageHeight(exif);
            i2 = imageWidth;
        } else if ((jpegRotation + orientation2) % 180 == 0) {
            i2 = width2;
            i = height2;
        } else {
            i = width2;
            i2 = height2;
        }
        if (z || dataParameter.isHasWaterMark()) {
            SaverCallback saverCallback = getSaverCallback();
            if (saverCallback != null) {
                parallelTaskDataParameter = dataParameter;
                DrawJPEGAttribute drawJPEGAttribute = getDrawJPEGAttribute(jpegImageData, dataParameter.getPreviewSize().getWidth(), dataParameter.getPreviewSize().getHeight(), filterId, parallelTaskData.isNeedThumbnail(), i2, i, dataParameter.getLocation(), createJpegName, dataParameter.getShootOrientation(), jpegRotation, dataParameter.getShootRotation(), dataParameter.getAlgorithmName(), dataParameter.isHasWaterMark(), dataParameter.isUltraPixelWaterMark(), dataParameter.getTimeWaterMarkString(), dataParameter.getFaceWaterMarkList(), false, dataParameter.getPictureInfo(), parallelTaskData.getCurrentModuleIndex(), parallelTaskData.getPreviewThumbnailHash(), dataParameter.isMiMovieOpen());
                saverCallback.processorJpegSync(false, drawJPEGAttribute);
                jpegImageData = drawJPEGAttribute.mData;
            } else {
                parallelTaskDataParameter = dataParameter;
                Log.d(TAG, "parserMimojiCaptureTask(): saverCallback is null");
            }
        } else {
            parallelTaskDataParameter = dataParameter;
        }
        reFillSaveRequest(jpegImageData, parallelTaskData.isNeedThumbnail(), createJpegName, null, parallelTaskData.getDateTakenTime(), null, parallelTaskDataParameter.getLocation(), width2, height2, null, jpegRotation, false, false, true, false, false, Util.ALGORITHM_NAME_MIMOJI_CAPTURE, parallelTaskDataParameter.getPictureInfo(), parallelTaskData.getPreviewThumbnailHash());
    }

    /* JADX WARNING: Removed duplicated region for block: B:21:0x00a7  */
    /* JADX WARNING: Removed duplicated region for block: B:37:0x0190  */
    /* JADX WARNING: Removed duplicated region for block: B:38:0x01e6  */
    /* JADX WARNING: Removed duplicated region for block: B:41:0x0235  */
    /* JADX WARNING: Removed duplicated region for block: B:42:0x023e  */
    private void parserNormalDualTask(ParallelTaskData parallelTaskData) {
        int i;
        int i2;
        String str;
        byte[] bArr;
        int[] iArr;
        byte[] bArr2;
        String str2;
        boolean isDepthMapData = PortraitDepthMap.isDepthMapData(parallelTaskData.getPortraitDepthData());
        byte[] jpegImageData = parallelTaskData.getJpegImageData();
        byte[] portraitRawData = parallelTaskData.getPortraitRawData();
        byte[] portraitDepthData = parallelTaskData.getPortraitDepthData();
        ParallelTaskDataParameter dataParameter = parallelTaskData.getDataParameter();
        int filterId = dataParameter.getFilterId();
        boolean z = EffectController.getInstance().hasEffect(false) || filterId != FilterInfo.FILTER_ID_NONE;
        int width2 = dataParameter.getOutputSize().getWidth();
        int height2 = dataParameter.getOutputSize().getHeight();
        ExifInterface exif = ExifInterface.getExif(jpegImageData);
        int orientation2 = ExifInterface.getOrientation(exif);
        int jpegRotation = dataParameter.getJpegRotation();
        if (parallelTaskData.isAdaptiveSnapshotSize()) {
            width2 = ExifInterface.getImageWidth(exif);
            height2 = ExifInterface.getImageHeight(exif);
        } else if ((jpegRotation + orientation2) % 180 != 0) {
            i = width2;
            i2 = height2;
            if (!parallelTaskData.isShot2Gallery() || parallelTaskData.isInTimerBurstShotting()) {
                str = Util.getFileTitleFromPath(parallelTaskData.getSavePath());
            } else {
                str = Util.createJpegName(System.currentTimeMillis()) + dataParameter.getSuffix();
            }
            String str3 = TAG;
            byte[] bArr3 = null;
            if (z) {
                SaverCallback saverCallback = getSaverCallback();
                if (saverCallback != null) {
                    DrawJPEGAttribute drawJPEGAttribute = getDrawJPEGAttribute(jpegImageData, dataParameter.getPreviewSize().getWidth(), dataParameter.getPreviewSize().getHeight(), filterId, parallelTaskData.isNeedThumbnail(), i2, i, dataParameter.getLocation(), str, dataParameter.getShootOrientation(), orientation2, dataParameter.getShootRotation(), dataParameter.getAlgorithmName(), dataParameter.isHasWaterMark(), false, dataParameter.getTimeWaterMarkString(), dataParameter.getFaceWaterMarkList(), false, dataParameter.getPictureInfo(), parallelTaskData.getCurrentModuleIndex(), -1, dataParameter.isMiMovieOpen());
                    DrawJPEGAttribute drawJPEGAttribute2 = isDepthMapData ? getDrawJPEGAttribute(portraitRawData, dataParameter.getPreviewSize().getWidth(), dataParameter.getPreviewSize().getHeight(), filterId, parallelTaskData.isNeedThumbnail(), i2, i, dataParameter.getLocation(), str, dataParameter.getShootOrientation(), orientation2, dataParameter.getShootRotation(), dataParameter.getAlgorithmName(), false, false, dataParameter.getTimeWaterMarkString(), dataParameter.getFaceWaterMarkList(), true, dataParameter.getPictureInfo(), parallelTaskData.getCurrentModuleIndex(), -1, dataParameter.isMiMovieOpen()) : null;
                    saverCallback.processorJpegSync(false, drawJPEGAttribute, drawJPEGAttribute2);
                    byte[] bArr4 = drawJPEGAttribute.mData;
                    byte[] bArr5 = drawJPEGAttribute.mDataOfTheRegionUnderWatermarks;
                    iArr = drawJPEGAttribute.mCoordinatesOfTheRegionUnderWatermarks;
                    if (c.Zg) {
                        iArr = null;
                    } else {
                        bArr3 = bArr5;
                    }
                    if (isDepthMapData) {
                        portraitRawData = drawJPEGAttribute2.mData;
                    }
                    jpegImageData = bArr4;
                    bArr = portraitRawData;
                    str3 = str3;
                    if (isDepthMapData) {
                        str2 = str3;
                        bArr2 = Util.composeDepthMapPicture(jpegImageData, portraitDepthData, bArr, bArr3, iArr, -1, dataParameter.getSupportZeroDegreeOrientationImage(), dataParameter.isHasDualWaterMark(), dataParameter.isHasFrontWaterMark(), dataParameter.getLightingPattern(), dataParameter.getTimeWaterMarkString(), dataParameter.getOutputSize().getWidth(), dataParameter.getOutputSize().getHeight(), dataParameter.isMirror(), dataParameter.isBokehFrontCamera(), dataParameter.getJpegRotation(), dataParameter.getDeviceWatermarkParam(), dataParameter.getPictureInfo(), dataParameter.isSupportMiDualBokeh(), dataParameter.getPortraitLightingVersion(), parallelTaskData.getTimestamp(), dataParameter.getCameraPreferredMode());
                    } else {
                        str2 = str3;
                        bArr2 = Util.composeMainSubPicture(jpegImageData, bArr3, iArr, dataParameter.getOutputSize().getWidth(), dataParameter.getOutputSize().getHeight(), dataParameter.isHasDualWaterMark(), dataParameter.isHasFrontWaterMark(), dataParameter.getTimeWaterMarkString(), dataParameter.getJpegRotation(), dataParameter.getDeviceWatermarkParam(), parallelTaskData, -1);
                    }
                    Log.d(str2, "insertNormalDualTask: isShot2Gallery = " + parallelTaskData.isShot2Gallery());
                    if (parallelTaskData.isShot2Gallery()) {
                        parallelTaskData.refillJpegData(bArr2);
                        parserParallelDualTask(parallelTaskData);
                        return;
                    }
                    reFillSaveRequest(bArr2, parallelTaskData.isNeedThumbnail(), str, null, parallelTaskData.getDateTakenTime(), null, dataParameter.getLocation(), i2, i, null, orientation2, false, false, true, false, false, dataParameter.getAlgorithmName(), dataParameter.getPictureInfo(), -1);
                    return;
                }
                Log.d(str3, "parserNormalDualTask(): saverCallback is null");
            }
            bArr = portraitRawData;
            iArr = null;
            if (isDepthMapData) {
            }
            Log.d(str2, "insertNormalDualTask: isShot2Gallery = " + parallelTaskData.isShot2Gallery());
            if (parallelTaskData.isShot2Gallery()) {
            }
        }
        i2 = width2;
        i = height2;
        if (!parallelTaskData.isShot2Gallery()) {
        }
        str = Util.getFileTitleFromPath(parallelTaskData.getSavePath());
        String str32 = TAG;
        byte[] bArr32 = null;
        if (z) {
        }
        bArr = portraitRawData;
        iArr = null;
        if (isDepthMapData) {
        }
        Log.d(str2, "insertNormalDualTask: isShot2Gallery = " + parallelTaskData.isShot2Gallery());
        if (parallelTaskData.isShot2Gallery()) {
        }
    }

    private void parserParallelBurstTask(ParallelTaskData parallelTaskData) {
        int i;
        ParallelTaskDataParameter dataParameter = parallelTaskData.getDataParameter();
        Log.d(TAG, "insertParallelBurstTask: path=" + parallelTaskData.getSavePath());
        byte[] populateExif = populateExif(parallelTaskData.getJpegImageData(), parallelTaskData);
        byte[] dataOfTheRegionUnderWatermarks = parallelTaskData.getDataOfTheRegionUnderWatermarks();
        int[] coordinatesOfTheRegionUnderWatermarks = parallelTaskData.getCoordinatesOfTheRegionUnderWatermarks();
        int width2 = dataParameter.getPictureSize().getWidth();
        int height2 = dataParameter.getPictureSize().getHeight();
        int orientation2 = ExifHelper.getOrientation(populateExif);
        int jpegRotation = dataParameter.getJpegRotation();
        if (orientation2 == jpegRotation) {
            jpegRotation = -1;
        }
        int jpegRotation2 = dataParameter.getJpegRotation();
        Log.d(TAG, String.format(Locale.ENGLISH, "insertParallelBurstTask: %d x %d, %d : %d", Integer.valueOf(width2), Integer.valueOf(height2), Integer.valueOf(jpegRotation2), Integer.valueOf(orientation2)));
        if ((jpegRotation2 + orientation2) % 180 == 0) {
            i = height2;
        } else {
            i = width2;
            width2 = height2;
        }
        Log.d(TAG, "insertParallelBurstTask: result = " + width2 + "x" + i);
        String fileTitleFromPath = Util.getFileTitleFromPath(parallelTaskData.getSavePath());
        StringBuilder sb = new StringBuilder();
        sb.append("insertParallelBurstTask: ");
        sb.append(fileTitleFromPath);
        Log.d(TAG, sb.toString());
        reFillSaveRequest(Util.composeMainSubPicture(populateExif, dataOfTheRegionUnderWatermarks, coordinatesOfTheRegionUnderWatermarks, dataParameter.getOutputSize().getWidth(), dataParameter.getOutputSize().getHeight(), dataParameter.isHasDualWaterMark(), dataParameter.isHasFrontWaterMark(), dataParameter.getTimeWaterMarkString(), dataParameter.getJpegRotation(), dataParameter.getDeviceWatermarkParam(), parallelTaskData, jpegRotation), parallelTaskData.isNeedThumbnail(), fileTitleFromPath, null, parallelTaskData.getDateTakenTime(), null, dataParameter.getLocation(), width2, i, null, orientation2, false, false, parallelTaskData.isNeedThumbnail(), false, true, dataParameter.getAlgorithmName(), dataParameter.getPictureInfo(), -1);
    }

    private void parserParallelDualTask(ParallelTaskData parallelTaskData) {
        int i;
        byte[] bArr;
        ParallelTaskDataParameter dataParameter = parallelTaskData.getDataParameter();
        Log.d(TAG, "addParallel: path=" + parallelTaskData.getSavePath());
        boolean isMemDebug = parallelTaskData.isMemDebug();
        byte[] populateExif = populateExif(parallelTaskData.getJpegImageData(), parallelTaskData);
        byte[] dataOfTheRegionUnderWatermarks = parallelTaskData.getDataOfTheRegionUnderWatermarks();
        int[] coordinatesOfTheRegionUnderWatermarks = parallelTaskData.getCoordinatesOfTheRegionUnderWatermarks();
        if (isMemDebug) {
            reFillSaveRequest(populateExif, parallelTaskData.getTimestamp(), parallelTaskData.getDateTakenTime(), dataParameter.getLocation(), dataParameter.getJpegRotation(), parallelTaskData.getSavePath(), dataParameter.getOutputSize().getWidth(), dataParameter.getOutputSize().getHeight(), parallelTaskData.isNeedThumbnail(), dataParameter.getAlgorithmName(), dataParameter.getPictureInfo());
            return;
        }
        int orientation2 = ExifHelper.getOrientation(populateExif);
        int jpegRotation = dataParameter.getJpegRotation();
        int i2 = orientation2 == jpegRotation ? -1 : jpegRotation;
        if ((6 == parallelTaskData.getParallelType() || 11 == parallelTaskData.getParallelType() || 8 == parallelTaskData.getParallelType() || 7 == parallelTaskData.getParallelType() || -6 == parallelTaskData.getParallelType() || -7 == parallelTaskData.getParallelType()) && PortraitDepthMap.isDepthMapData(parallelTaskData.getPortraitDepthData())) {
            bArr = Util.composeDepthMapPicture(populateExif, parallelTaskData.getPortraitDepthData(), parallelTaskData.getPortraitRawData(), dataOfTheRegionUnderWatermarks, coordinatesOfTheRegionUnderWatermarks, i2, dataParameter.getSupportZeroDegreeOrientationImage(), dataParameter.isHasDualWaterMark(), dataParameter.isHasFrontWaterMark(), dataParameter.getLightingPattern(), dataParameter.getTimeWaterMarkString(), dataParameter.getOutputSize().getWidth(), dataParameter.getOutputSize().getHeight(), dataParameter.isMirror(), dataParameter.isBokehFrontCamera(), dataParameter.getJpegRotation(), dataParameter.getDeviceWatermarkParam(), dataParameter.getPictureInfo(), dataParameter.isSupportMiDualBokeh(), dataParameter.getPortraitLightingVersion(), parallelTaskData.getTimestamp(), dataParameter.getCameraPreferredMode());
        } else if (parallelTaskData.isLiveShotTask()) {
            String microVideoPath = parallelTaskData.getMicroVideoPath();
            byte[] composeLiveShotPicture = Util.composeLiveShotPicture(populateExif, dataParameter.getOutputSize().getWidth(), dataParameter.getOutputSize().getHeight(), microVideoPath, parallelTaskData.getCoverFrameTimestamp(), dataParameter.isHasDualWaterMark(), dataParameter.isHasFrontWaterMark(), dataParameter.getTimeWaterMarkString(), dataParameter.getJpegRotation(), dataParameter.getDeviceWatermarkParam(), dataOfTheRegionUnderWatermarks, coordinatesOfTheRegionUnderWatermarks, i2);
            if (microVideoPath != null && !CircularMediaRecorder.VideoClipSavingCallback.EMPTY_VIDEO_PATH.equals(microVideoPath) && !Util.keepLiveShotMicroVideoInCache()) {
                Util.deleteFile(microVideoPath);
            }
            bArr = composeLiveShotPicture;
        } else {
            i = -7;
            bArr = Util.composeMainSubPicture(populateExif, dataOfTheRegionUnderWatermarks, coordinatesOfTheRegionUnderWatermarks, dataParameter.getOutputSize().getWidth(), dataParameter.getOutputSize().getHeight(), dataParameter.isHasDualWaterMark(), dataParameter.isHasFrontWaterMark(), dataParameter.getTimeWaterMarkString(), dataParameter.getJpegRotation(), dataParameter.getDeviceWatermarkParam(), parallelTaskData, i2);
            if (parallelTaskData.getParallelType() != i || parallelTaskData.getParallelType() == -6 || parallelTaskData.getParallelType() == -5) {
                ExifInterface exif = ExifInterface.getExif(bArr);
                this.orientation = ExifInterface.getOrientation(exif);
                this.width = ExifInterface.getImageWidth(exif);
                parallelTaskData.refillJpegData(bArr);
            }
            reFillSaveRequest(bArr, parallelTaskData.getTimestamp(), parallelTaskData.getDateTakenTime(), dataParameter.getLocation(), dataParameter.getJpegRotation(), parallelTaskData.getSavePath(), dataParameter.getOutputSize().getWidth(), dataParameter.getOutputSize().getHeight(), parallelTaskData.isNeedThumbnail(), dataParameter.getAlgorithmName(), dataParameter.getPictureInfo());
            return;
        }
        i = -7;
        if (parallelTaskData.getParallelType() != i) {
        }
        ExifInterface exif2 = ExifInterface.getExif(bArr);
        this.orientation = ExifInterface.getOrientation(exif2);
        this.width = ExifInterface.getImageWidth(exif2);
        parallelTaskData.refillJpegData(bArr);
    }

    /* JADX WARNING: Removed duplicated region for block: B:19:0x0135  */
    private void parserPreviewShotTask(ParallelTaskData parallelTaskData) {
        PictureInfo pictureInfo;
        String str;
        Location location;
        int i;
        int i2;
        int i3;
        int i4;
        int i5;
        boolean z;
        byte[] bArr;
        String str2;
        byte[] jpegImageData = parallelTaskData.getJpegImageData();
        ParallelTaskDataParameter dataParameter = parallelTaskData.getDataParameter();
        int filterId = dataParameter.getFilterId();
        boolean isAnchorPreview = dataParameter.isAnchorPreview();
        int jpegRotation = dataParameter.getJpegRotation();
        boolean z2 = filterId != FilterInfo.FILTER_ID_NONE && isAnchorPreview;
        if (dataParameter != null) {
            int width2 = dataParameter.getOutputSize().getWidth();
            int height2 = dataParameter.getOutputSize().getHeight();
            i2 = width2;
            i = height2;
            location = dataParameter.getLocation();
            str = dataParameter.getAlgorithmName();
            pictureInfo = dataParameter.getPictureInfo();
            i3 = dataParameter.getOrientation();
        } else {
            location = null;
            str = null;
            pictureInfo = null;
            i3 = 0;
            i2 = 0;
            i = 0;
        }
        Log.d(TAG, "preview orientation is " + i3 + " jpegorientation " + jpegRotation + " anchorPreview " + isAnchorPreview);
        PerformanceTracker.trackImageSaver(jpegImageData, 0);
        String fileTitleFromPath = Util.getFileTitleFromPath(parallelTaskData.getSavePath());
        if (z2) {
            SaverCallback saverCallback = getSaverCallback();
            if (saverCallback != null) {
                i5 = i3;
                i4 = jpegRotation;
                z = isAnchorPreview;
                DrawJPEGAttribute drawJPEGAttribute = getDrawJPEGAttribute(jpegImageData, dataParameter.getPreviewSize().getWidth(), dataParameter.getPreviewSize().getHeight(), filterId, parallelTaskData.isNeedThumbnail(), i2, i, dataParameter.getLocation(), fileTitleFromPath, dataParameter.getShootOrientation(), i5, dataParameter.getShootRotation(), dataParameter.getAlgorithmName(), dataParameter.isHasWaterMark(), dataParameter.isUltraPixelWaterMark(), dataParameter.getTimeWaterMarkString(), dataParameter.getFaceWaterMarkList(), false, dataParameter.getPictureInfo(), parallelTaskData.getCurrentModuleIndex(), parallelTaskData.getPreviewThumbnailHash(), dataParameter.isMiMovieOpen());
                saverCallback.processorJpegSync(false, drawJPEGAttribute);
                bArr = drawJPEGAttribute.mData;
                str2 = TAG;
                Log.d(str2, "reFill preview image");
                boolean isNeedThumbnail = parallelTaskData.isNeedThumbnail();
                String savePath = parallelTaskData.getSavePath();
                long dateTakenTime = parallelTaskData.getDateTakenTime();
                if (!z) {
                    i4 = i5;
                }
                reFillSaveRequest(bArr, isNeedThumbnail, savePath, dateTakenTime, location, i2, i, i4, true, true, str, pictureInfo);
            }
            i4 = jpegRotation;
            z = isAnchorPreview;
            i5 = i3;
            str2 = TAG;
            Log.d(str2, "parserSingleTask(): saverCallback is null");
        } else {
            i4 = jpegRotation;
            z = isAnchorPreview;
            str2 = TAG;
            i5 = i3;
        }
        bArr = jpegImageData;
        Log.d(str2, "reFill preview image");
        boolean isNeedThumbnail2 = parallelTaskData.isNeedThumbnail();
        String savePath2 = parallelTaskData.getSavePath();
        long dateTakenTime2 = parallelTaskData.getDateTakenTime();
        if (!z) {
        }
        reFillSaveRequest(bArr, isNeedThumbnail2, savePath2, dateTakenTime2, location, i2, i, i4, true, true, str, pictureInfo);
    }

    /* JADX WARNING: Removed duplicated region for block: B:34:0x0151  */
    /* JADX WARNING: Removed duplicated region for block: B:44:0x01b6  */
    /* JADX WARNING: Removed duplicated region for block: B:57:0x022e  */
    /* JADX WARNING: Removed duplicated region for block: B:64:0x029e  */
    private void parserSingleTask(ParallelTaskData parallelTaskData) {
        int i;
        int i2;
        String str;
        int i3;
        String str2;
        int[] iArr;
        byte[] bArr;
        String str3;
        byte[] bArr2;
        int i4;
        int i5;
        int i6;
        int i7;
        byte[] bArr3;
        String str4;
        int i8;
        int i9;
        AbstractSaveRequest abstractSaveRequest;
        ParallelTaskData parallelTaskData2;
        int[] iArr2;
        byte[] bArr4;
        ParallelTaskDataParameter dataParameter = parallelTaskData.getDataParameter();
        int filterId = dataParameter.getFilterId();
        boolean z = EffectController.getInstance().hasEffect(false) || filterId != FilterInfo.FILTER_ID_NONE;
        byte[] jpegImageData = parallelTaskData.getJpegImageData();
        int width2 = dataParameter.getOutputSize().getWidth();
        int height2 = dataParameter.getOutputSize().getHeight();
        ExifInterface exif = ExifInterface.getExif(jpegImageData);
        int orientation2 = ExifInterface.getOrientation(exif);
        int jpegRotation = dataParameter.getJpegRotation();
        if (parallelTaskData.isAdaptiveSnapshotSize()) {
            int imageWidth = ExifInterface.getImageWidth(exif);
            i = ExifInterface.getImageHeight(exif);
            i2 = imageWidth;
        } else if ((jpegRotation + orientation2) % 180 == 0) {
            i2 = width2;
            i = height2;
        } else {
            i = width2;
            i2 = height2;
        }
        if (parallelTaskData.isShot2Gallery() || parallelTaskData.isSaveToHiddenFolder() || parallelTaskData.isInTimerBurstShotting()) {
            str = Util.getFileTitleFromPath(parallelTaskData.getSavePath());
        } else {
            str = Util.createJpegName(System.currentTimeMillis()) + dataParameter.getSuffix();
        }
        if (z) {
            SaverCallback saverCallback = getSaverCallback();
            if (saverCallback != null) {
                str2 = str;
                i3 = orientation2;
                DrawJPEGAttribute drawJPEGAttribute = getDrawJPEGAttribute(jpegImageData, dataParameter.getPreviewSize().getWidth(), dataParameter.getPreviewSize().getHeight(), filterId, parallelTaskData.isNeedThumbnail(), i2, i, dataParameter.getLocation(), str2, dataParameter.getShootOrientation(), i3, dataParameter.getShootRotation(), dataParameter.getAlgorithmName(), dataParameter.isHasWaterMark(), dataParameter.isUltraPixelWaterMark(), dataParameter.getTimeWaterMarkString(), dataParameter.getFaceWaterMarkList(), false, dataParameter.getPictureInfo(), parallelTaskData.getCurrentModuleIndex(), parallelTaskData.getPreviewThumbnailHash(), dataParameter.isMiMovieOpen());
                saverCallback.processorJpegSync(false, drawJPEGAttribute);
                byte[] bArr5 = drawJPEGAttribute.mData;
                int i10 = drawJPEGAttribute.mWidth;
                int i11 = drawJPEGAttribute.mHeight;
                byte[] bArr6 = drawJPEGAttribute.mDataOfTheRegionUnderWatermarks;
                int[] iArr3 = drawJPEGAttribute.mCoordinatesOfTheRegionUnderWatermarks;
                if (c.Zg) {
                    iArr3 = null;
                    bArr6 = null;
                }
                iArr = iArr3;
                bArr2 = bArr5;
                i5 = i10;
                i4 = i11;
                bArr = bArr6;
                str3 = TAG;
                if (parallelTaskData.isLiveShotTask()) {
                    if (bArr == null) {
                        byte[] dataOfTheRegionUnderWatermarks = parallelTaskData.getDataOfTheRegionUnderWatermarks();
                        iArr2 = parallelTaskData.getCoordinatesOfTheRegionUnderWatermarks();
                        bArr4 = dataOfTheRegionUnderWatermarks;
                    } else {
                        bArr4 = bArr;
                        iArr2 = iArr;
                    }
                    byte[] composeMainSubPicture = Util.composeMainSubPicture(bArr2, bArr4, iArr2, dataParameter.getOutputSize().getWidth(), dataParameter.getOutputSize().getHeight(), dataParameter.isHasDualWaterMark(), dataParameter.isHasFrontWaterMark(), dataParameter.getTimeWaterMarkString(), dataParameter.getJpegRotation(), dataParameter.getDeviceWatermarkParam(), parallelTaskData, -1);
                    if (composeMainSubPicture == null || composeMainSubPicture.length < bArr2.length) {
                        Log.e(str3, "Failed to compose main sub photos: " + str2);
                        i6 = i5;
                        i7 = i4;
                        bArr3 = bArr2;
                    } else {
                        bArr3 = composeMainSubPicture;
                        i6 = i5;
                        i7 = i4;
                    }
                    str4 = str3;
                } else {
                    String str5 = str2;
                    String microVideoPath = parallelTaskData.getMicroVideoPath();
                    i6 = i5;
                    i7 = i4;
                    str4 = str3;
                    byte[] composeLiveShotPicture = Util.composeLiveShotPicture(bArr2, width2, height2, microVideoPath, parallelTaskData.getCoverFrameTimestamp(), dataParameter.isHasDualWaterMark(), dataParameter.isHasFrontWaterMark(), dataParameter.getTimeWaterMarkString(), dataParameter.getJpegRotation(), dataParameter.getDeviceWatermarkParam(), bArr, iArr, -1);
                    if (composeLiveShotPicture == null || composeLiveShotPicture.length < bArr2.length) {
                        Log.e(str4, "Failed to compose LiveShot photo: " + str5);
                        composeLiveShotPicture = bArr2;
                    } else {
                        str5 = dataParameter.getPrefix() + str5;
                    }
                    if (microVideoPath != null && !CircularMediaRecorder.VideoClipSavingCallback.EMPTY_VIDEO_PATH.equals(microVideoPath)) {
                        Util.deleteFile(microVideoPath);
                    }
                    str2 = str5;
                    bArr3 = composeLiveShotPicture;
                }
                if (parallelTaskData.getParallelType() != -2) {
                    parallelTaskData2 = parallelTaskData;
                    i8 = i7;
                    i9 = i6;
                    abstractSaveRequest = this;
                } else if (parallelTaskData.getParallelType() == -3) {
                    abstractSaveRequest = this;
                    parallelTaskData2 = parallelTaskData;
                    i8 = i7;
                    i9 = i6;
                } else {
                    Log.d(str4, "insertSingleTask: isShot2Gallery = " + parallelTaskData.isShot2Gallery());
                    if (parallelTaskData.isShot2Gallery()) {
                        parallelTaskData.refillJpegData(bArr3);
                        parallelTaskData.getDataParameter().updateOutputSize(i6, i7);
                        parserParallelDualTask(parallelTaskData);
                        return;
                    }
                    reFillSaveRequest(bArr3, parallelTaskData.isNeedThumbnail(), str2, null, parallelTaskData.getDateTakenTime(), null, dataParameter.getLocation(), i6, i7, null, i3, false, false, true, false, false, dataParameter.getAlgorithmName(), dataParameter.getPictureInfo(), parallelTaskData.getPreviewThumbnailHash());
                    return;
                }
                abstractSaveRequest.width = i9;
                abstractSaveRequest.height = i8;
                abstractSaveRequest.orientation = i3;
                parallelTaskData2.refillJpegData(bArr3);
            }
            str2 = str;
            i3 = orientation2;
            str3 = TAG;
            Log.d(str3, "parserSingleTask(): saverCallback is null");
        } else {
            str2 = str;
            i3 = orientation2;
            str3 = TAG;
        }
        bArr2 = jpegImageData;
        i5 = i2;
        i4 = i;
        bArr = null;
        iArr = null;
        if (parallelTaskData.isLiveShotTask()) {
        }
        if (parallelTaskData.getParallelType() != -2) {
        }
        abstractSaveRequest.width = i9;
        abstractSaveRequest.height = i8;
        abstractSaveRequest.orientation = i3;
        parallelTaskData2.refillJpegData(bArr3);
    }

    private static byte[] populateExif(byte[] bArr, ParallelTaskData parallelTaskData) {
        return (parallelTaskData == null || parallelTaskData.getCaptureResult() == null) ? bArr : Util.appendCaptureResultToExif(bArr, parallelTaskData.getDataParameter().getOutputSize().getWidth(), parallelTaskData.getDataParameter().getOutputSize().getHeight(), parallelTaskData.getDataParameter().getJpegRotation(), parallelTaskData.getDateTakenTime(), parallelTaskData.getDataParameter().getLocation(), parallelTaskData.getCaptureResult().getResults());
    }

    /* access modifiers changed from: protected */
    public int calculateMemoryUsed() {
        ParallelTaskData parallelTaskData = this.mParallelTaskData;
        int i = 0;
        if (parallelTaskData == null) {
            return 0;
        }
        byte[] jpegImageData = parallelTaskData.getJpegImageData();
        int length = jpegImageData == null ? 0 : jpegImageData.length;
        byte[] portraitRawData = this.mParallelTaskData.getPortraitRawData();
        int length2 = length + (portraitRawData == null ? 0 : portraitRawData.length);
        byte[] portraitDepthData = this.mParallelTaskData.getPortraitDepthData();
        if (portraitDepthData != null) {
            i = portraitDepthData.length;
        }
        return i + length2;
    }

    /* access modifiers changed from: protected */
    public boolean isHeicSavingRequest() {
        ParallelTaskData parallelTaskData = this.mParallelTaskData;
        return (parallelTaskData == null || parallelTaskData.getDataParameter() == null || !CompatibilityUtils.isHeicImageFormat(this.mParallelTaskData.getDataParameter().getOutputFormat())) ? false : true;
    }

    /* access modifiers changed from: protected */
    public void parserParallelTaskData() {
        ParallelTaskData parallelTaskData = this.mParallelTaskData;
        if (parallelTaskData == null) {
            Log.v(TAG, "mParallelTaskData is null, ignore");
            return;
        }
        switch (parallelTaskData.getParallelType()) {
            case Constants.ShotType.INTENT_PARALLEL_DUAL_SHOT /*{ENCODED_INT: -7}*/:
            case -6:
            case -5:
            case 5:
            case 6:
            case 7:
            case 8:
            case 11:
                parserParallelDualTask(this.mParallelTaskData);
                return;
            case -4:
                parserMimojiCaptureTask(this.mParallelTaskData);
                return;
            case -3:
            case -2:
            case 0:
            case 1:
            case 10:
                parserSingleTask(this.mParallelTaskData);
                return;
            case -1:
                parserPreviewShotTask(this.mParallelTaskData);
                return;
            case 2:
                parserNormalDualTask(this.mParallelTaskData);
                return;
            case 3:
            case 4:
            default:
                throw new RuntimeException("Unknown shot type: " + this.mParallelTaskData.getParallelType());
            case 9:
                parserParallelBurstTask(this.mParallelTaskData);
                return;
        }
    }

    /* access modifiers changed from: protected */
    public void reFillSaveRequest(byte[] bArr, long j, long j2, Location location, int i, String str, int i2, int i3, boolean z, String str2, PictureInfo pictureInfo) {
    }

    /* access modifiers changed from: protected */
    public void reFillSaveRequest(byte[] bArr, boolean z, String str, long j, Location location, int i, int i2, int i3, boolean z2, boolean z3, String str2, PictureInfo pictureInfo) {
    }

    /* access modifiers changed from: protected */
    public void reFillSaveRequest(byte[] bArr, boolean z, String str, String str2, long j, Uri uri, Location location, int i, int i2, ExifInterface exifInterface, int i3, boolean z2, boolean z3, boolean z4, boolean z5, boolean z6, String str3, PictureInfo pictureInfo, int i4) {
    }

    public void setParallelTaskData(ParallelTaskData parallelTaskData) {
        this.mParallelTaskData = parallelTaskData;
    }

    public void setSaverCallback(SaverCallback saverCallback) {
        this.mSaverCallbackRef = new WeakReference<>(saverCallback);
    }
}
