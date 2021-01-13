package com.android.camera;

import android.media.Image;
import android.util.Log;
import java.nio.ByteBuffer;

public final class JpegUtil {
    private static final String TAG = "JpegUtil";
    private static boolean sInitialized;

    static {
        try {
            System.loadLibrary("jni_jpegutil_xiaomi");
            nativeClassInit();
            sInitialized = true;
        } catch (UnsatisfiedLinkError unused) {
            Log.e(TAG, "couldn't find libjni_jpegutil_xiaomi.so");
            sInitialized = false;
        }
    }

    private JpegUtil() {
    }

    public static byte[] getJpegData(Image.Plane[] planeArr, int i) {
        if (planeArr == null || planeArr.length <= i || planeArr[i] == null) {
            return null;
        }
        ByteBuffer buffer = planeArr[i].getBuffer();
        byte[] bArr = new byte[buffer.remaining()];
        buffer.get(bArr);
        return bArr;
    }

    public static Image.Plane[] getPlanesExtra(Image image) {
        String str = TAG;
        Log.d(str, "getPlanesExtra: inited = " + sInitialized);
        if (!sInitialized) {
            return null;
        }
        if (image == null) {
            throw new IllegalArgumentException("The input image must not be null");
        } else if (image.getClass().getName().equals("android.media.ImageReader$SurfaceImage")) {
            Image.Plane[] nativeGetPlanesExtra = nativeGetPlanesExtra(image);
            String str2 = TAG;
            Log.d(str2, "getPlanesExtra planes:" + nativeGetPlanesExtra);
            return nativeGetPlanesExtra;
        } else {
            throw new IllegalArgumentException("Only images from ImageReader can be fed to JpegUtil, other image source is not supported yet!");
        }
    }

    private static native void nativeClassInit();

    private static native Image.Plane[] nativeGetPlanesExtra(Image image);
}
