package com.android.camera.storage;

import android.content.Context;
import android.graphics.Bitmap;
import android.location.Location;
import android.net.Uri;
import com.android.camera.EncodingQuality;
import com.android.camera.Thumbnail;
import com.android.camera.Util;
import com.android.camera.log.Log;
import com.android.camera.statistic.ScenarioTrackUtil;
import com.android.gallery3d.exif.ExifInterface;
import com.xiaomi.camera.core.ParallelTaskData;
import com.xiaomi.camera.core.PictureInfo;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;

public final class SimpleImageSaveRequest extends AbstractSaveRequest {
    private static final String TAG = "SimpleImageSaveRequest";
    private String algorithmName;
    private Context context;
    private byte[] data;
    private ExifInterface exif;
    private boolean finalImage;
    private PictureInfo info;
    private boolean isHeic;
    private boolean isHide;
    private boolean isMap;
    private boolean isParallelProcess;
    private Location loc;
    private boolean mirror;
    private boolean needThumbnail;
    public String oldTitle;
    private int previewThumbnailHash;
    private SaverCallback saverCallback;
    private int size;
    public String title;
    private Uri uri;

    SimpleImageSaveRequest() {
    }

    SimpleImageSaveRequest(ParallelTaskData parallelTaskData, SaverCallback saverCallback2) {
        this.mParallelTaskData = parallelTaskData;
        setSaverCallback(saverCallback2);
        this.size = calculateMemoryUsed();
        this.isHeic = isHeicSavingRequest();
    }

    SimpleImageSaveRequest(byte[] bArr, boolean z, String str, String str2, long j, Uri uri2, Location location, int i, int i2, ExifInterface exifInterface, int i3, boolean z2, boolean z3, boolean z4, boolean z5, boolean z6, String str3, PictureInfo pictureInfo, int i4) {
        reFillSaveRequest(bArr, z, str, str2, j, uri2, location, i, i2, exifInterface, i3, z2, z3, z4, z5, z6, str3, pictureInfo, i4);
    }

    private void parsePixelsToJpeg() {
        Bitmap createBitmap = Bitmap.createBitmap(this.width, this.height, Bitmap.Config.ARGB_8888);
        createBitmap.copyPixelsFromBuffer(ByteBuffer.wrap(this.data));
        byte[] bitmapData = Util.getBitmapData(createBitmap, EncodingQuality.NORMAL.toInteger(false));
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        ExifInterface exifInterface = new ExifInterface();
        try {
            exifInterface.readExif(bitmapData);
            exifInterface.addParallelProcessComment("None", this.orientation, this.width, this.height);
            exifInterface.removeParallelProcessComment();
            exifInterface.writeExif(bitmapData, byteArrayOutputStream);
            byte[] byteArray = byteArrayOutputStream.toByteArray();
            byteArrayOutputStream.close();
            bitmapData = byteArray;
        } catch (IOException e2) {
            Log.e(TAG, "updateExif error", e2);
        }
        this.data = bitmapData;
    }

    private void trackScenarioAbort() {
        ParallelTaskData parallelTaskData = this.mParallelTaskData;
        if (parallelTaskData != null) {
            ScenarioTrackUtil.trackScenarioAbort(ScenarioTrackUtil.sShotToGalleryTimeScenario, String.valueOf(parallelTaskData.getCaptureTime()));
            ScenarioTrackUtil.trackScenarioAbort(ScenarioTrackUtil.sShotToViewTimeScenario, String.valueOf(this.mParallelTaskData.getCaptureTime()));
        }
    }

    @Override // com.android.camera.storage.SaveRequest
    public int getSize() {
        return this.size;
    }

    @Override // com.android.camera.storage.SaveRequest
    public boolean isFinal() {
        return this.finalImage;
    }

    @Override // com.android.camera.storage.SaveRequest
    public void onFinish() {
        this.data = null;
        ParallelTaskData parallelTaskData = this.mParallelTaskData;
        if (parallelTaskData != null) {
            parallelTaskData.releaseImageData();
            this.mParallelTaskData = null;
        }
        this.saverCallback.onSaveFinish(getSize());
    }

    /* access modifiers changed from: protected */
    @Override // com.android.camera.storage.AbstractSaveRequest
    public void reFillSaveRequest(byte[] bArr, boolean z, String str, String str2, long j, Uri uri2, Location location, int i, int i2, ExifInterface exifInterface, int i3, boolean z2, boolean z3, boolean z4, boolean z5, boolean z6, String str3, PictureInfo pictureInfo, int i4) {
        this.data = bArr;
        this.needThumbnail = z;
        this.date = j;
        this.uri = uri2;
        this.title = str;
        this.oldTitle = str2;
        this.loc = location == null ? null : new Location(location);
        this.width = i;
        this.height = i2;
        this.exif = exifInterface;
        this.orientation = i3;
        this.isHide = z2;
        this.isMap = z3;
        this.finalImage = z4;
        this.mirror = z5;
        this.isParallelProcess = z6;
        this.algorithmName = str3;
        this.info = pictureInfo;
        this.previewThumbnailHash = i4;
    }

    public void run() {
        save();
        onFinish();
    }

    @Override // com.android.camera.storage.SaveRequest
    public void save() {
        parsePixelsToJpeg();
        Uri uri2 = this.uri;
        if (uri2 != null) {
            Storage.updateImageWithExtraExif(this.context, this.data, this.isHeic, this.exif, uri2, this.title, this.loc, this.orientation, this.width, this.height, this.oldTitle, this.mirror, this.isParallelProcess, this.algorithmName, this.info);
        } else if (this.data != null) {
            String str = this.algorithmName;
            this.uri = Storage.addImage(this.context, this.title, this.date, this.loc, this.orientation, this.data, this.isHeic, this.width, this.height, false, this.isHide, this.isMap, str != null && str.equals(Util.ALGORITHM_NAME_MIMOJI_CAPTURE), this.isParallelProcess, this.algorithmName, this.info);
        }
        Storage.getAvailableSpace();
        boolean z = this.needThumbnail && this.saverCallback.needThumbnail(isFinal());
        Uri uri3 = this.uri;
        if (uri3 != null) {
            if (z) {
                int highestOneBit = Integer.highestOneBit((int) Math.ceil(Math.max((double) this.width, (double) this.height) / 512.0d));
                Log.d(TAG, "image save try to create thumbnail");
                Thumbnail createThumbnailFromUri = this.isMap ? Thumbnail.createThumbnailFromUri(this.context, this.uri, this.mirror) : Thumbnail.createThumbnail(this.data, this.orientation, highestOneBit, this.uri, this.mirror);
                if (createThumbnailFromUri != null) {
                    this.saverCallback.postUpdateThumbnail(createThumbnailFromUri, true);
                } else {
                    this.saverCallback.postHideThumbnailProgressing();
                }
            } else {
                this.saverCallback.updatePreviewThumbnailUri(this.previewThumbnailHash, uri3);
            }
            this.saverCallback.notifyNewMediaData(this.uri, this.title, 2);
            ParallelTaskData parallelTaskData = this.mParallelTaskData;
            if (!(parallelTaskData == null || parallelTaskData.getCaptureTime() == 0)) {
                ScenarioTrackUtil.trackShotToGalleryEnd(false, this.mParallelTaskData.getCaptureTime());
                ScenarioTrackUtil.trackShotToViewEnd(false, this.mParallelTaskData.getCaptureTime());
            }
            Log.d(TAG, "image save finished");
        } else if (Storage.isSaveToHidenFolder(this.title)) {
            this.saverCallback.notifyNewMediaData(null, this.title, 3);
        } else {
            Log.e(TAG, "image save failed");
            if (z) {
                this.saverCallback.postHideThumbnailProgressing();
                return;
            }
            trackScenarioAbort();
            Log.e(TAG, "set mWaitingForUri is false");
            this.saverCallback.updatePreviewThumbnailUri(this.previewThumbnailHash, null);
        }
    }

    @Override // com.android.camera.storage.SaveRequest
    public void setContextAndCallback(Context context2, SaverCallback saverCallback2) {
        this.context = context2;
        this.saverCallback = saverCallback2;
    }
}
