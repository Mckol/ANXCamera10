package com.android.camera.storage;

import android.content.ContentUris;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Location;
import android.net.Uri;
import com.android.camera.Thumbnail;
import com.android.camera.Util;
import com.android.camera.data.DataRepository;
import com.android.camera.db.DbRepository;
import com.android.camera.db.element.SaveTask;
import com.android.camera.log.Log;
import com.android.camera.statistic.ScenarioTrackUtil;
import com.android.gallery3d.exif.ExifHelper;
import com.android.gallery3d.exif.ExifInterface;
import com.android.zxing.DocumentDecoder;
import com.xiaomi.camera.base.PerformanceTracker;
import com.xiaomi.camera.core.ParallelTaskData;
import com.xiaomi.camera.core.PictureInfo;
import com.xiaomi.camera.parallelservice.util.ParallelUtil;
import com.xiaomi.ocr.sdk.imgprocess.DocumentProcess;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Locale;

public final class ParallelSaveRequest extends AbstractSaveRequest {
    private static final String TAG = "ParallelSaveRequest";
    private boolean isHeic = isHeicSavingRequest();
    private String mAlgorithmName;
    private Context mContext;
    private byte[] mData;
    private PictureInfo mInfo;
    private int mJpegRotation;
    private Location mLocation;
    private boolean mNeedThumbnail;
    private String mSavePath;
    private SaverCallback mSaverCallback;
    private int mSize = calculateMemoryUsed();
    private long mTimestamp;

    public ParallelSaveRequest(ParallelTaskData parallelTaskData, SaverCallback saverCallback) {
        this.mParallelTaskData = parallelTaskData;
        setSaverCallback(saverCallback);
    }

    private void parseDocAndSave(int i, int i2, String str, Uri uri) {
        String str2 = TAG;
        Log.d(str2, "parse document E: sensorOrientation = " + i2);
        byte[] bArr = this.mData;
        Bitmap decodeByteArray = BitmapFactory.decodeByteArray(bArr, 0, bArr.length);
        float[] doScanDocument = DocumentProcess.getInstance().doScanDocument(decodeByteArray, DocumentProcess.DocumentType.PPT, DocumentDecoder.getRotateFlag(i2));
        String componentValue = DataRepository.dataItemRunning().getComponentRunningDocument().getComponentValue(163);
        Bitmap doCropAndEnhance = DocumentProcess.getInstance().doCropAndEnhance(decodeByteArray, doScanDocument, DocumentProcess.EnhanceType.valueOf(componentValue.toUpperCase(Locale.ENGLISH)), DocumentProcess.DocumentType.PPT, false);
        ExifInterface exifInterface = new ExifInterface();
        try {
            exifInterface.readExif(this.mData);
            exifInterface.removeCompressedThumbnail();
        } catch (IOException e2) {
            String str3 = TAG;
            Log.w(str3, "readExif: error " + e2.getMessage(), e2);
        }
        exifInterface.addXiaomiDocPhotoVersion((byte) 1);
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        doCropAndEnhance.compress(Bitmap.CompressFormat.JPEG, 100, exifInterface.getExifWriterStream(byteArrayOutputStream));
        byte[] addXmpData = Storage.addXmpData(byteArrayOutputStream.toByteArray(), (long) this.mData.length, doScanDocument, componentValue);
        byte[] bArr2 = new byte[(addXmpData.length + this.mData.length)];
        System.arraycopy(addXmpData, 0, bArr2, 0, addXmpData.length);
        byte[] bArr3 = this.mData;
        System.arraycopy(bArr3, 0, bArr2, addXmpData.length, bArr3.length);
        Log.d(TAG, "parse document X: ");
        Storage.updateImageWithExtraExif(this.mContext, bArr2, false, null, uri, str, this.mLocation, i, doCropAndEnhance.getWidth(), doCropAndEnhance.getHeight(), null, false, false, this.mAlgorithmName, this.mInfo);
    }

    @Override // com.android.camera.storage.SaveRequest
    public int getSize() {
        return this.mSize;
    }

    @Override // com.android.camera.storage.SaveRequest
    public boolean isFinal() {
        return true;
    }

    @Override // com.android.camera.storage.SaveRequest
    public void onFinish() {
        ParallelTaskData parallelTaskData = this.mParallelTaskData;
        if (!(parallelTaskData == null || parallelTaskData.getCaptureTime() == 0)) {
            ScenarioTrackUtil.trackShotToViewEnd(true, this.mParallelTaskData.getCaptureTime());
        }
        PerformanceTracker.trackImageSaver(this.mData, 1);
        this.mData = null;
        ParallelTaskData parallelTaskData2 = this.mParallelTaskData;
        if (parallelTaskData2 != null) {
            parallelTaskData2.releaseImageData();
            this.mParallelTaskData = null;
        }
        this.mSaverCallback.onSaveFinish(this.mSize);
    }

    /* access modifiers changed from: protected */
    @Override // com.android.camera.storage.AbstractSaveRequest
    public void reFillSaveRequest(byte[] bArr, long j, long j2, Location location, int i, String str, int i2, int i3, boolean z, String str2, PictureInfo pictureInfo) {
        this.mData = bArr;
        this.mTimestamp = j;
        this.date = j2;
        this.mLocation = location == null ? null : new Location(location);
        this.mJpegRotation = i;
        this.mSavePath = str;
        this.width = i2;
        this.height = i3;
        this.mNeedThumbnail = z;
        this.mAlgorithmName = str2;
        this.mInfo = pictureInfo;
    }

    public void run() {
        save();
        onFinish();
    }

    /* JADX WARNING: Removed duplicated region for block: B:46:0x01f0  */
    /* JADX WARNING: Removed duplicated region for block: B:47:0x021d A[ADDED_TO_REGION] */
    @Override // com.android.camera.storage.SaveRequest
    public void save() {
        boolean z;
        int i;
        boolean z2;
        Thumbnail createThumbnail;
        boolean z3;
        parserParallelTaskData();
        Log.d(TAG, "save: " + this.mSavePath + " | " + this.mTimestamp);
        synchronized (this.mSavePath.intern()) {
            SaveTask itemByPath = DbRepository.dbItemSaveTask().getItemByPath(this.mSavePath);
            if (itemByPath == null && !Storage.isSaveToHidenFolder(Util.getFileTitleFromPath(this.mSavePath))) {
                SaveTask saveTask = (SaveTask) DbRepository.dbItemSaveTask().generateItem(System.currentTimeMillis());
                saveTask.setPath(this.mSavePath);
                saveTask.setStartTime(-1L);
                DbRepository.dbItemSaveTask().endItemAndInsert(saveTask, 0);
                Log.w(TAG, "insert full size picture:" + this.mSavePath);
            }
            int i2 = this.width;
            int i3 = this.height;
            int orientation = ExifHelper.getOrientation(this.mData);
            if ((this.mJpegRotation + orientation) % 180 != 0) {
                i3 = i2;
                i2 = i3;
            }
            if (itemByPath != null) {
                if (itemByPath.isValid()) {
                    String fileTitleFromPath = Util.getFileTitleFromPath(this.mSavePath);
                    Uri resultUri = ParallelUtil.getResultUri(itemByPath.getMediaStoreId().longValue());
                    Log.d(TAG, "algo mark: uri: " + resultUri.toString() + " | " + itemByPath.getPath());
                    Log.d(TAG, "algo mark: " + this.mJpegRotation + " | " + i2 + " | " + i3 + " | " + orientation);
                    Uri withAppendedId = ContentUris.withAppendedId(Storage.getMediaUri(this.mContext, false, this.mSavePath), itemByPath.getMediaStoreId().longValue());
                    if (this.mParallelTaskData == null || this.mParallelTaskData.getDataParameter() == null || !this.mParallelTaskData.getDataParameter().isDocumentShot()) {
                        z3 = false;
                        Storage.updateImageWithExtraExif(this.mContext, this.mData, this.isHeic, null, withAppendedId, fileTitleFromPath, this.mLocation, orientation, i2, i3, null, false, false, this.mAlgorithmName, this.mInfo);
                    } else {
                        parseDocAndSave(orientation, this.mParallelTaskData.getDataParameter().getSensorOrientation(), fileTitleFromPath, withAppendedId);
                        z3 = false;
                    }
                    ParallelUtil.markTaskFinish(this.mContext, itemByPath, z3);
                }
            }
            String fileTitleFromPath2 = this.mSavePath != null ? Util.getFileTitleFromPath(this.mSavePath) : Util.createJpegName(this.date);
            Uri addImage = Storage.addImage(this.mContext, fileTitleFromPath2, this.date, this.mLocation, orientation, this.mData, this.isHeic, i2, i3, false, false, false, false, itemByPath != null, this.mAlgorithmName, this.mInfo);
            if (addImage != null) {
                if (this.mNeedThumbnail) {
                    i = orientation;
                    Thumbnail createThumbnail2 = Thumbnail.createThumbnail(this.mData, i, Integer.highestOneBit((int) Math.ceil(Math.max((double) i2, (double) i3) / 512.0d)), addImage, false);
                    if (createThumbnail2 != null) {
                        z = true;
                        this.mSaverCallback.postUpdateThumbnail(createThumbnail2, true);
                        z2 = true;
                        this.mSaverCallback.notifyNewMediaData(addImage, fileTitleFromPath2, 2);
                        if (itemByPath == null) {
                            Log.d(TAG, "algo mark: " + addImage.toString());
                            itemByPath.setMediaStoreId(Long.valueOf(ContentUris.parseId(addImage)));
                            ParallelUtil.markTaskFinish(this.mContext, itemByPath, false);
                        } else {
                            if (!z2 && (createThumbnail = Thumbnail.createThumbnail(this.mData, i, Integer.highestOneBit((int) Math.ceil(Math.max((double) i2, (double) i3) / 512.0d)), addImage, false)) != null) {
                                this.mSaverCallback.postUpdateThumbnail(createThumbnail, z);
                            }
                            ParallelUtil.insertImageToParallelService(this.mContext, ContentUris.parseId(addImage), this.mSavePath);
                        }
                    } else {
                        z = true;
                        this.mSaverCallback.postHideThumbnailProgressing();
                    }
                } else {
                    i = orientation;
                    z = true;
                }
                z2 = false;
                this.mSaverCallback.notifyNewMediaData(addImage, fileTitleFromPath2, 2);
                if (itemByPath == null) {
                }
            } else if (Storage.isSaveToHidenFolder(fileTitleFromPath2)) {
                this.mSaverCallback.notifyNewMediaData(null, fileTitleFromPath2, 3);
            }
        }
    }

    @Override // com.android.camera.storage.SaveRequest
    public void setContextAndCallback(Context context, SaverCallback saverCallback) {
        this.mContext = context;
        this.mSaverCallback = saverCallback;
    }
}
