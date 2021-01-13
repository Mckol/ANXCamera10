package com.android.zxing;

import android.media.Image;
import com.android.camera2.Camera2Proxy;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class PreviewDecodeManager {
    public static final int DECODE_TYPE_DOCUMENT = 3;
    public static final int DECODE_TYPE_GOOGLE_LENS = 2;
    public static final int DECODE_TYPE_HAND_GESTURE = 1;
    public static final int DECODE_TYPE_QR = 0;
    private ConcurrentHashMap<Integer, Decoder> mDecoders;
    private Camera2Proxy.PreviewCallback mPreviewCallback;

    /* access modifiers changed from: private */
    public static class Singleton {
        public static final PreviewDecodeManager INSTANCE = new PreviewDecodeManager();

        private Singleton() {
        }
    }

    private PreviewDecodeManager() {
        this.mPreviewCallback = new Camera2Proxy.PreviewCallback() {
            /* class com.android.zxing.PreviewDecodeManager.AnonymousClass1 */

            @Override // com.android.camera2.Camera2Proxy.PreviewCallback
            public boolean onPreviewFrame(Image image, Camera2Proxy camera2Proxy, int i) {
                for (Map.Entry entry : PreviewDecodeManager.this.mDecoders.entrySet()) {
                    Decoder decoder = (Decoder) entry.getValue();
                    if (decoder.needPreviewFrame()) {
                        if (decoder.isNeedImage()) {
                            decoder.onPreviewFrame(image);
                        } else {
                            decoder.onPreviewFrame(new PreviewImage(image, i));
                        }
                    }
                }
                return true;
            }
        };
        this.mDecoders = new ConcurrentHashMap<>();
    }

    public static PreviewDecodeManager getInstance() {
        return Singleton.INSTANCE;
    }

    public Decoder getDecoder(int i) {
        return this.mDecoders.get(Integer.valueOf(i));
    }

    public Camera2Proxy.PreviewCallback getPreviewCallback() {
        return this.mPreviewCallback;
    }

    public String getScanResult() {
        return ((QrDecoder) this.mDecoders.get(0)).getScanResult();
    }

    public void init(int i, int i2) {
        if (i2 != 0) {
            if (i2 != 1) {
                if (i2 != 2) {
                    if (i2 == 3 && this.mDecoders.get(3) == null) {
                        DocumentDecoder documentDecoder = new DocumentDecoder();
                        documentDecoder.init(i);
                        this.mDecoders.put(3, documentDecoder);
                    }
                } else if (this.mDecoders.get(2) == null) {
                    GoogleLensDecoder googleLensDecoder = new GoogleLensDecoder();
                    googleLensDecoder.init(i);
                    this.mDecoders.put(2, googleLensDecoder);
                }
            } else if (this.mDecoders.get(1) == null) {
                HandGestureDecoder handGestureDecoder = new HandGestureDecoder();
                handGestureDecoder.init(i);
                this.mDecoders.put(1, handGestureDecoder);
            }
        } else if (this.mDecoders.get(0) == null) {
            QrDecoder qrDecoder = new QrDecoder();
            qrDecoder.init(i);
            this.mDecoders.put(0, qrDecoder);
        }
    }

    public void quit() {
        for (Decoder decoder : this.mDecoders.values()) {
            decoder.stopDecode();
            decoder.quit();
        }
        this.mDecoders.clear();
    }

    public void reset() {
        for (Decoder decoder : this.mDecoders.values()) {
            decoder.reset();
        }
    }

    public void resetScanResult() {
        Decoder decoder = this.mDecoders.get(0);
        if (decoder != null) {
            ((QrDecoder) decoder).resetScanResult();
        }
    }

    public void startDecode() {
        for (Decoder decoder : this.mDecoders.values()) {
            decoder.startDecode();
        }
    }

    public void stopDecode(int i) {
        Decoder decoder = this.mDecoders.get(Integer.valueOf(i));
        if (decoder != null) {
            decoder.stopDecode();
        }
    }
}
