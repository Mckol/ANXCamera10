package com.android.zxing;

import android.text.TextUtils;
import com.android.camera.log.Log;
import com.android.camera.protocol.ModeCoordinatorImpl;
import com.android.camera.protocol.ModeProtocol;
import com.google.zxing.BinaryBitmap;
import com.google.zxing.DecodeHintType;
import com.google.zxing.MultiFormatReader;
import com.google.zxing.Result;
import com.google.zxing.common.HybridBinarizer;
import io.reactivex.BackpressureStrategy;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subjects.PublishSubject;
import java.util.ArrayList;
import java.util.HashMap;

public class QrDecoder extends Decoder {
    public static final String TAG = "QrDecoder";
    private final MultiFormatReader mMultiFormatReader;
    private String mScanResult = "";

    QrDecoder() {
        this.mSubjects = PublishSubject.create();
        this.mMultiFormatReader = new MultiFormatReader();
        HashMap hashMap = new HashMap(1);
        ArrayList arrayList = new ArrayList();
        arrayList.addAll(DecodeFormats.QR_CODE_FORMATS);
        hashMap.put(DecodeHintType.POSSIBLE_FORMATS, arrayList);
        this.mMultiFormatReader.setHints(hashMap);
        this.mDecodeDisposable = this.mSubjects.toFlowable(BackpressureStrategy.LATEST).observeOn(Schedulers.computation()).map(new e(this)).observeOn(AndroidSchedulers.mainThread()).subscribe(new f(this));
        this.mEnable = true;
    }

    public /* synthetic */ void U(String str) throws Exception {
        boolean z;
        Log.d(TAG, "decode X: result = " + str);
        if (TextUtils.isEmpty(str)) {
            this.mDecoding = true;
            z = this.mScanResult.isEmpty();
        } else {
            this.mDecoding = false;
            z = this.mScanResult.equals(str);
        }
        if (!z) {
            this.mScanResult = str;
            ModeProtocol.BottomPopupTips bottomPopupTips = (ModeProtocol.BottomPopupTips) ModeCoordinatorImpl.getInstance().getAttachProtocol(175);
            ModeProtocol.TopAlert topAlert = (ModeProtocol.TopAlert) ModeCoordinatorImpl.getInstance().getAttachProtocol(172);
            if (!this.mDecoding) {
                if (topAlert != null) {
                    topAlert.alertAiDetectTipHint(8, 0, 0);
                }
                if (bottomPopupTips == null || !bottomPopupTips.isNearRangeTipShowing()) {
                    bottomPopupTips.showQrCodeTip();
                    return;
                }
                return;
            }
            bottomPopupTips.hideQrCodeTip();
        }
    }

    public /* synthetic */ String c(PreviewImage previewImage) throws Exception {
        StringBuilder sb = new StringBuilder();
        sb.append("decode E: previewImage is null? ");
        sb.append(previewImage == null);
        Log.d(TAG, sb.toString());
        return decode(previewImage);
    }

    /* access modifiers changed from: protected */
    public String decode(PreviewImage previewImage) {
        byte[] bArr;
        String str = "";
        if (previewImage == null || previewImage.getData() == null || previewImage.getData().length == 0 || previewImage.getWidth() == 0 || previewImage.getHeight() == 0) {
            return str;
        }
        byte[] data = previewImage.getData();
        int width = previewImage.getWidth();
        int height = previewImage.getHeight();
        int i = width * height;
        byte[] bArr2 = new byte[i];
        if (data.length > i) {
            System.arraycopy(data, 0, bArr2, 0, bArr2.length);
            bArr = bArr2;
        } else {
            bArr = data;
        }
        try {
            Result decodeWithState = this.mMultiFormatReader.decodeWithState(new BinaryBitmap(new HybridBinarizer(new YUVLuminanceSource(bArr, width, height, 0, 0, width, height))));
            if (decodeWithState != null) {
                str = decodeWithState.getText();
            }
        } catch (Exception unused) {
            Log.w(TAG, "decode: failed...  ");
        } catch (Throwable th) {
            this.mMultiFormatReader.reset();
            throw th;
        }
        this.mMultiFormatReader.reset();
        return str;
    }

    public String getScanResult() {
        return this.mScanResult;
    }

    @Override // com.android.zxing.Decoder
    public void init(int i) {
    }

    @Override // com.android.zxing.Decoder
    public void onPreviewFrame(PreviewImage previewImage) {
        PublishSubject<PreviewImage> publishSubject = this.mSubjects;
        if (publishSubject != null) {
            publishSubject.onNext(previewImage);
        }
    }

    @Override // com.android.zxing.Decoder
    public void reset() {
    }

    public void resetScanResult() {
        this.mDecoding = true;
        this.mScanResult = "";
    }

    @Override // com.android.zxing.Decoder
    public void startDecode() {
        this.mDecoding = true;
        this.mDecodingCount.set(0);
    }
}
