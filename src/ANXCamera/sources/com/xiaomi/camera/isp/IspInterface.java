package com.xiaomi.camera.isp;

import android.os.Parcelable;
import android.util.Log;
import android.view.Surface;
import java.lang.ref.WeakReference;

public class IspInterface {
    private static final String TAG = "IspInterface";
    private final Object mContextLock = new Object();
    private IspStream mInputStream;
    private Surface mJpegOutputSurface;
    private long mNativeContext;
    private Parcelable mSettings;
    private Surface mYuvOutputSurface;

    static {
        try {
            System.loadLibrary("jni_ispinterface");
        } catch (UnsatisfiedLinkError e2) {
            Log.e(TAG, "load library jni_ispinterface.so failed", e2);
        }
        nativeClassInit();
    }

    private IspInterface(IspStream ispStream, Surface surface, Surface surface2, Parcelable parcelable) {
        String str = TAG;
        Log.d(str, "ctor: E " + hashCode());
        this.mInputStream = ispStream;
        this.mJpegOutputSurface = surface;
        this.mYuvOutputSurface = surface2;
        this.mSettings = parcelable;
        nativeCreate(new WeakReference(this), this.mInputStream, this.mJpegOutputSurface, this.mYuvOutputSurface, this.mSettings);
        String str2 = TAG;
        Log.d(str2, "ctor: X " + hashCode());
    }

    public static IspInterface create(IspStream ispStream, Surface surface, Surface surface2, Parcelable parcelable) {
        if (ispStream == null) {
            throw new IllegalArgumentException("The inputStream is empty");
        } else if (surface2 != null) {
            return new IspInterface(ispStream, surface2, surface, parcelable);
        } else {
            throw new IllegalArgumentException("The jpeg output surface is null");
        }
    }

    private static native void nativeClassInit();

    private native void nativeCreate(Object obj, IspStream ispStream, Surface surface, Surface surface2, Parcelable parcelable);

    private native void nativeDestroy();

    private native void nativeSetFlipFlag(int i);

    private native void nativeYuvToJpeg(IspRequest ispRequest);

    private native void nativeYuvToYuv(IspRequest ispRequest);

    private static void postEventFromNative(Object obj) {
        Log.d(TAG, "postEventFromNative: E");
        if (((IspInterface) ((WeakReference) obj).get()) != null) {
            Log.d(TAG, "postEventFromNative: X");
        }
    }

    public long getInputStreamId() {
        IspStream ispStream = this.mInputStream;
        if (ispStream != null) {
            return ispStream.streamId;
        }
        Log.e(TAG, "getInputStreamId: input stream is not initialized");
        return 1;
    }

    public void release() {
        String str = TAG;
        Log.d(str, "release: E " + hashCode());
        synchronized (this.mContextLock) {
            nativeDestroy();
        }
        String str2 = TAG;
        Log.d(str2, "release: X " + hashCode());
    }

    public void setFlip(boolean z) {
        synchronized (this.mContextLock) {
            nativeSetFlipFlag(z ? 1 : 0);
        }
    }

    public void yuvToJpeg(IspRequest ispRequest) {
        nativeYuvToJpeg(ispRequest);
    }

    public void yuvToYuv(IspRequest ispRequest) {
        nativeYuvToYuv(ispRequest);
    }
}
