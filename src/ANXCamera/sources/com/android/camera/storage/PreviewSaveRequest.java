package com.android.camera.storage;

import android.content.Context;
import android.location.Location;
import android.net.Uri;
import android.text.TextUtils;
import com.android.camera.Thumbnail;
import com.android.camera.Util;
import com.android.camera.db.DbRepository;
import com.android.camera.db.element.SaveTask;
import com.android.camera.log.Log;
import com.android.camera.statistic.ScenarioTrackUtil;
import com.xiaomi.camera.core.ParallelTaskData;
import com.xiaomi.camera.core.PictureInfo;
import com.xiaomi.camera.parallelservice.util.ParallelUtil;

public final class PreviewSaveRequest extends AbstractSaveRequest {
    private static final String TAG = "PreviewSaveRequest";
    private String algorithmName;
    private Context context;
    private byte[] data;
    private boolean finalImage;
    private int height;
    private PictureInfo info;
    private boolean isHeic;
    private boolean isParallelProcess;
    private Location loc;
    private String mSavePath;
    private boolean needThumbnail;
    private boolean noGaussian;
    private int orientation;
    private SaverCallback saverCallback;
    private int size;
    private int width;

    PreviewSaveRequest(ParallelTaskData parallelTaskData, SaverCallback saverCallback2) {
        this(parallelTaskData, saverCallback2, false);
    }

    PreviewSaveRequest(ParallelTaskData parallelTaskData, SaverCallback saverCallback2, boolean z) {
        this.mParallelTaskData = parallelTaskData;
        setSaverCallback(saverCallback2);
        this.size = calculateMemoryUsed();
        this.isHeic = isHeicSavingRequest();
        this.noGaussian = z;
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
    public void reFillSaveRequest(byte[] bArr, boolean z, String str, long j, Location location, int i, int i2, int i3, boolean z2, boolean z3, String str2, PictureInfo pictureInfo) {
        this.data = bArr;
        this.needThumbnail = z;
        this.date = j;
        this.mSavePath = str;
        this.loc = location == null ? null : new Location(location);
        this.width = i;
        this.height = i2;
        this.orientation = i3;
        this.finalImage = z2;
        this.isParallelProcess = z3;
        this.algorithmName = str2;
        this.info = pictureInfo;
    }

    public void run() {
        save();
        onFinish();
    }

    @Override // com.android.camera.storage.SaveRequest
    public void save() {
        parserParallelTaskData();
        if (this.data != null && !TextUtils.isEmpty(this.mSavePath)) {
            synchronized (this.mSavePath.intern()) {
                SaveTask itemByPath = DbRepository.dbItemSaveTask().getItemByPath(this.mSavePath);
                if (itemByPath != null) {
                    Log.w(TAG, "save preview: task is exist! isValid = " + itemByPath.isValid());
                    if (itemByPath.isValid()) {
                        ParallelUtil.ParallelProvider.deleteInProvider(this.context, itemByPath.getMediaStoreId().longValue());
                    }
                    return;
                }
                SaveTask saveTask = (SaveTask) DbRepository.dbItemSaveTask().generateItem(this.date);
                saveTask.setPath(this.mSavePath);
                saveTask.setNoGaussian(this.noGaussian ? 1 : 0);
                DbRepository.dbItemSaveTask().endItemAndInsert(saveTask, 0);
                Log.d(TAG, "insert preview picture:" + this.mSavePath);
                String fileTitleFromPath = Util.getFileTitleFromPath(this.mSavePath);
                Uri addImage = Storage.addImage(this.context, fileTitleFromPath, this.date, this.loc, this.orientation, this.data, this.isHeic, this.width, this.height, false, false, false, this.algorithmName != null && this.algorithmName.equals(Util.ALGORITHM_NAME_MIMOJI_CAPTURE), this.isParallelProcess, this.algorithmName, this.info, this.noGaussian);
                Storage.getAvailableSpace();
                boolean z = this.needThumbnail && this.saverCallback.needThumbnail(isFinal());
                if (addImage != null) {
                    if (z) {
                        int highestOneBit = Integer.highestOneBit((int) Math.ceil(Math.max((double) this.width, (double) this.height) / 512.0d));
                        Log.d(TAG, "image save try to create thumbnail");
                        Thumbnail createThumbnail = Thumbnail.createThumbnail(this.data, this.orientation, highestOneBit, addImage, false);
                        if (createThumbnail != null) {
                            this.saverCallback.postUpdateThumbnail(createThumbnail, true);
                        } else {
                            this.saverCallback.postHideThumbnailProgressing();
                        }
                    } else {
                        this.saverCallback.updatePreviewThumbnailUri(-1, addImage);
                    }
                    this.saverCallback.notifyNewMediaData(addImage, fileTitleFromPath, 2);
                    if (!(this.mParallelTaskData == null || this.mParallelTaskData.getCaptureTime() == 0)) {
                        ScenarioTrackUtil.trackShotToGalleryEnd(true, this.mParallelTaskData.getCaptureTime());
                    }
                    Log.d(TAG, "image save finished");
                } else {
                    ScenarioTrackUtil.trackScenarioAbort(ScenarioTrackUtil.sShotToGalleryTimeScenario, String.valueOf(this.mParallelTaskData.getCaptureTime()));
                    Log.e(TAG, "image save failed");
                    if (z) {
                        this.saverCallback.postHideThumbnailProgressing();
                    }
                }
            }
        }
    }

    @Override // com.android.camera.storage.SaveRequest
    public void setContextAndCallback(Context context2, SaverCallback saverCallback2) {
        this.context = context2;
        this.saverCallback = saverCallback2;
    }
}
