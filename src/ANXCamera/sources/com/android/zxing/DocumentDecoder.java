package com.android.zxing;

import android.util.Pair;
import com.android.camera.CameraSettings;
import com.android.camera.Util;
import com.android.camera.log.Log;
import com.android.camera.module.loader.camera2.Camera2DataContainer;
import com.android.camera.protocol.ModeCoordinatorImpl;
import com.android.camera.protocol.ModeProtocol;
import com.android.camera2.CameraCapabilities;
import com.xiaomi.ocr.sdk.imgprocess.DocumentProcess;
import com.xiaomi.stat.d;
import io.reactivex.BackpressureStrategy;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subjects.PublishSubject;
import java.util.Arrays;

public class DocumentDecoder extends Decoder {
    private static final String TAG = "DocumentDecoder";
    private Pair<PreviewImage, float[]> cachePreview;
    private ModeProtocol.MainContentProtocol mMainProtocol;
    private int mSensorOrientation = 90;

    public DocumentDecoder() {
        this.mDecodeMaxCount = -1;
        this.mDecodeAutoInterval = Util.getSampleTime();
        this.mEnable = true;
        this.mMainProtocol = (ModeProtocol.MainContentProtocol) ModeCoordinatorImpl.getInstance().getAttachProtocol(166);
        long currentTimeMillis = System.currentTimeMillis();
        Log.d(TAG, "DocumentDecoder: init cost = " + (System.currentTimeMillis() - currentTimeMillis) + d.H);
        this.mSubjects = PublishSubject.create();
        this.mDecodeDisposable = this.mSubjects.toFlowable(BackpressureStrategy.LATEST).observeOn(Schedulers.computation()).map(new b(this)).observeOn(AndroidSchedulers.mainThread()).subscribe(new a(this));
    }

    private Pair<DocumentProcess.QuadStatus, float[]> decode(PreviewImage previewImage) {
        DocumentProcess.QuadStatus quadStatus = DocumentProcess.QuadStatus.QUAD_NOSHOW;
        float[] fArr = new float[8];
        if (previewImage == null || previewImage.getData() == null || previewImage.getData().length == 0 || previewImage.getWidth() == 0 || previewImage.getHeight() == 0) {
            return Pair.create(quadStatus, fArr);
        }
        DocumentProcess.RotateFlags rotateFlag = getRotateFlag(this.mSensorOrientation);
        int doScanDocument = DocumentProcess.getInstance().doScanDocument(previewImage.getData(), fArr, previewImage.getWidth(), previewImage.getHeight(), 0, 0, DocumentProcess.DocumentType.PPT, rotateFlag);
        this.cachePreview = Pair.create(previewImage, fArr);
        float[] rotatePoints = DocumentProcess.getInstance().rotatePoints(fArr, previewImage.getWidth(), previewImage.getHeight(), rotateFlag);
        Log.d(TAG, "decode: version = " + DocumentProcess.getVersion() + ", size = " + previewImage.getWidth() + "x" + previewImage.getHeight() + ", status = " + doScanDocument + ", points = " + Arrays.toString(fArr) + ", rotatePoints = " + Arrays.toString(rotatePoints));
        return Pair.create(DocumentProcess.QuadStatus.values()[doScanDocument], rotatePoints);
    }

    public static DocumentProcess.RotateFlags getRotateFlag(int i) {
        return i != 0 ? i != 180 ? i != 270 ? DocumentProcess.RotateFlags.ROTATE_90 : DocumentProcess.RotateFlags.ROTATE_270 : DocumentProcess.RotateFlags.ROTATE_180 : DocumentProcess.RotateFlags.ROTATE_0;
    }

    public /* synthetic */ Pair a(PreviewImage previewImage) throws Exception {
        StringBuilder sb = new StringBuilder();
        sb.append("decode E: previewImage is null? ");
        sb.append(previewImage == null);
        Log.d(TAG, sb.toString());
        return decode(previewImage);
    }

    public /* synthetic */ void a(Pair pair) throws Exception {
        this.mMainProtocol.updateDocument(pair);
    }

    public Pair<PreviewImage, float[]> getCachePreview() {
        return this.cachePreview;
    }

    @Override // com.android.zxing.Decoder
    public void init(int i) {
        CameraCapabilities capabilities = Camera2DataContainer.getInstance().getCapabilities(i);
        if (capabilities != null) {
            this.mSensorOrientation = capabilities.getSensorOrientation();
        } else {
            this.mSensorOrientation = 90;
        }
    }

    @Override // com.android.zxing.Decoder
    public boolean needPreviewFrame() {
        return CameraSettings.isDocumentMode2On(186) && super.needPreviewFrame();
    }

    @Override // com.android.zxing.Decoder
    public void onPreviewFrame(PreviewImage previewImage) {
        PublishSubject<PreviewImage> publishSubject = this.mSubjects;
        if (publishSubject != null) {
            publishSubject.onNext(previewImage);
        }
    }

    @Override // com.android.zxing.Decoder
    public void quit() {
        super.quit();
        Disposable disposable = this.mDecodeDisposable;
        if (disposable != null && !disposable.isDisposed()) {
            this.mDecodeDisposable.dispose();
        }
    }

    @Override // com.android.zxing.Decoder
    public void reset() {
    }

    @Override // com.android.zxing.Decoder
    public void startDecode() {
        this.mDecoding = true;
    }

    @Override // com.android.zxing.Decoder
    public void stopDecode() {
        this.mDecoding = false;
    }
}
