package com.xiaomi.ocr.sdk.imgprocess;

import android.graphics.Bitmap;

public class DocumentProcessJNI {
    static {
        System.loadLibrary("DocumentProcess");
        nativeGlobalInit();
    }

    public static native Bitmap nativeCropAndEnhanceBitmap(long j, Bitmap bitmap, float[] fArr, int i, int i2, boolean z);

    public static native Bitmap nativeCropAndEnhanceYUV(long j, byte[] bArr, int i, int i2, float[] fArr, int i3, int i4, boolean z, int i5);

    public static native int nativeCropImage(long j, String str, String str2, float[] fArr);

    public static native Bitmap nativeCropImageBitmap(long j, Bitmap bitmap, float[] fArr, int i);

    public static native int nativeEnhance(long j, String str, String str2, int i);

    public static native int nativeEnhanceBitmap(long j, Bitmap bitmap, int i, boolean z);

    public static native String nativeGetVersion();

    public static native void nativeGlobalInit();

    public static native long nativeInit(String str, String str2);

    public static native void nativeInitAndRun(String str, String str2);

    public static native int nativeNormalizationPoints(long j, float[] fArr);

    public static native void nativeRelease(long j);

    public static native void nativeRotateDocumentYUVScanPoints(float[] fArr, int i, int i2, int i3, float[] fArr2);

    public static native void nativeScanDocument(long j, String str, int i, float[] fArr);

    public static native int nativeScanDocumentBitmap(long j, Bitmap bitmap, int i, float[] fArr, int i2);

    public static native int nativeScanDocumentYUV(long j, byte[] bArr, int i, int i2, int i3, float[] fArr, int i4);
}
