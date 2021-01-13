package com.xiaomi.fenshen;

import android.app.Activity;
import android.media.Image;
import android.text.TextUtils;
import android.util.Log;
import com.android.camera.data.DataRepository;
import com.xiaomi.camera.util.SystemProperties;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;

public class FenShenCam {
    public static boolean DEBUG = Log.isLoggable(TAG, 3);
    private static boolean IS_MTK_PLATFORM = true;
    private static final String TAG = "CloneSDK";
    private static Listener listener;
    private static volatile boolean sReleased;

    public interface Listener {
        void onMessage(Message message);

        void onPhotoResult(byte[] bArr);

        void onStartPreview();

        void onStopPreview();

        void onStopRecord();

        void onSubjectCount(int i);

        void onVideoSaved(int i);

        void requestRender();
    }

    public enum Message {
        START,
        PREVIEW_NO_PERSON,
        ALIGN_OK,
        ALIGN_WARNING,
        ALIGN_TOO_LARGE_OR_FAILED,
        NO_PERSON,
        MOVE_OUTSIDE,
        DYNAMIC_SCENE,
        ERROR_INIT,
        ERROR_RUNTIME
    }

    public enum Mode {
        PHOTO,
        VIDEO
    }

    private static /* synthetic */ void $closeResource(Throwable th, AutoCloseable autoCloseable) {
        if (th != null) {
            try {
                autoCloseable.close();
            } catch (Throwable th2) {
                th.addSuppressed(th2);
            }
        } else {
            autoCloseable.close();
        }
    }

    static {
        try {
            String str = SystemProperties.get("ro.board.platform");
            Log.i(TAG, "platform is " + str);
            if (!TextUtils.isEmpty(str) && str.startsWith("mt")) {
            }
            Runtime.getRuntime().loadLibrary("clone_c++_shared");
            Runtime.getRuntime().loadLibrary("fenshen");
        } catch (Throwable th) {
            Log.e(TAG, "Load native library failed: " + th.toString());
        }
    }

    public static void addPhoto(Image image) {
        Log.d(TAG, "addPhoto " + image + ", " + image.getWidth() + "x" + image.getHeight());
        long timestamp = image.getTimestamp();
        if (image.getFormat() == 35) {
            nativeAddPhoto(timestamp, image.getPlanes()[0].getBuffer(), image.getPlanes()[0].getRowStride(), image.getPlanes()[1].getBuffer(), image.getPlanes()[1].getRowStride(), image.getPlanes()[2].getBuffer(), image.getPlanes()[2].getRowStride(), image.getWidth(), image.getHeight());
            Listener listener2 = listener;
            if (listener2 != null) {
                listener2.requestRender();
                return;
            }
            return;
        }
        throw new RuntimeException("illegal image format, expect YUV image");
    }

    public static synchronized void addPreviewFrame(Image image) {
        synchronized (FenShenCam.class) {
            if (sReleased) {
                Log.i(TAG, "ignore render, released");
                return;
            }
            if (DEBUG) {
                Log.d(TAG, "addPreviewFrame " + image + ", " + image.getWidth() + "x" + image.getHeight());
            }
            long timestamp = image.getTimestamp();
            if (image.getFormat() == 35) {
                nativeAddPreviewFrame(timestamp, image.getPlanes()[0].getBuffer(), image.getPlanes()[0].getRowStride(), image.getPlanes()[1].getBuffer(), image.getPlanes()[1].getRowStride(), image.getPlanes()[2].getBuffer(), image.getPlanes()[2].getRowStride(), image.getWidth(), image.getHeight());
                if (listener != null) {
                    listener.requestRender();
                } else {
                    Log.w(TAG, "addPreviewFrame, can't requestRender since listener is null");
                }
                return;
            }
            throw new RuntimeException("illegal image format, expect YUV image");
        }
    }

    public static void cancelPhoto() {
        Log.d(TAG, "cancelPhoto");
        nativeCancelPhoto();
        Listener listener2 = listener;
        if (listener2 != null) {
            listener2.requestRender();
        }
    }

    public static void cancelVideo() {
        Log.d(TAG, "cancelVideo");
        nativeCancelVideo();
        Listener listener2 = listener;
        if (listener2 != null) {
            listener2.requestRender();
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:24:0x0069, code lost:
        r0 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:25:0x006a, code lost:
        $closeResource(r6, r5);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:26:0x006d, code lost:
        throw r0;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:30:0x0070, code lost:
        r6 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:31:0x0071, code lost:
        if (r4 != null) goto L_0x0073;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:32:0x0073, code lost:
        $closeResource(r5, r4);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:33:0x0076, code lost:
        throw r6;
     */
    private static void extractRawTo(Activity activity, String str, File file) {
        String cloneModelVersion = CloneUtil.getCloneModelVersion(IS_MTK_PLATFORM);
        if (!file.exists() || !DataRepository.dataItemGlobal().matchCloneModelVersion(cloneModelVersion)) {
            Log.d(TAG, "extractRawTo " + file.getAbsolutePath());
            try {
                InputStream open = activity.getResources().getAssets().open(str);
                FileOutputStream fileOutputStream = new FileOutputStream(file);
                byte[] bArr = new byte[10240];
                while (true) {
                    int read = open.read(bArr);
                    if (read <= 0) {
                        break;
                    }
                    fileOutputStream.write(bArr, 0, read);
                }
                DataRepository.dataItemGlobal().updateCloneModelVersion(cloneModelVersion);
                $closeResource(null, fileOutputStream);
                if (open != null) {
                    $closeResource(null, open);
                }
            } catch (IOException e2) {
                throw new RuntimeException("extract raw file failed", e2);
            }
        } else {
            Log.d(TAG, "ignore extractRawTo, file exists and matched");
        }
    }

    public static void finishPhoto() {
        nativeFinishPhoto();
    }

    public static int getCurrentSubjectCount() {
        return nativeGetCurrentSubjectCount();
    }

    public static void init(int i, int i2, String str, String str2) {
        sReleased = false;
        Log.d(TAG, "init previewWidth " + i + ", previewHeight = " + i2);
        nativeInit(i, i2, str, str2);
    }

    public static void initResources(Activity activity) {
        Log.d(TAG, "initResources E");
        File filesDir = activity.getFilesDir();
        filesDir.mkdirs();
        if (IS_MTK_PLATFORM) {
            extractRawTo(activity, "photo_b384_384_version1_apu_20200508.bin", new File(filesDir, "photo_b384_384_version1_apu_20200508.bin"));
        } else {
            extractRawTo(activity, "xseg2_dlc", new File(filesDir, "xseg2.dlc"));
        }
        Log.d(TAG, "initResources X");
    }

    private static native void nativeAddPhoto(long j, ByteBuffer byteBuffer, int i, ByteBuffer byteBuffer2, int i2, ByteBuffer byteBuffer3, int i3, int i4, int i5);

    private static native void nativeAddPreviewFrame(long j, ByteBuffer byteBuffer, int i, ByteBuffer byteBuffer2, int i2, ByteBuffer byteBuffer3, int i3, int i4, int i5);

    private static native void nativeCancelPhoto();

    private static native void nativeCancelVideo();

    private static native void nativeFinishPhoto();

    private static native int nativeGetCurrentSubjectCount();

    private static native byte[] nativeGetResultJpeg();

    private static native int nativeInit(int i, int i2, String str, String str2);

    private static native String nativePullCmd();

    private static native void nativeRelease();

    private static native int nativeRender();

    private static native int nativeRenderInit(int i, int i2, int i3, int i4, int i5, int i6);

    private static native void nativeSaveVideo(String str);

    private static native void nativeSetMode(int i);

    private static native void nativeStart();

    private static native void nativeStartRecordVideo();

    private static native void nativeStopRecordVideo();

    public static synchronized void release() {
        synchronized (FenShenCam.class) {
            if (sReleased) {
                Log.w(TAG, "ignore release twice");
                return;
            }
            Log.d(TAG, "release");
            sReleased = true;
            nativeRelease();
        }
    }

    public static synchronized void render() {
        synchronized (FenShenCam.class) {
            if (sReleased) {
                Log.i(TAG, "ignore render, released");
                return;
            }
            nativeRender();
            if (DEBUG) {
                Log.d(TAG, "nativeRender ");
            }
            if (listener != null) {
                listener.onSubjectCount(nativeGetCurrentSubjectCount());
            }
            while (true) {
                String nativePullCmd = nativePullCmd();
                if (nativePullCmd != null) {
                    if (DEBUG) {
                        Log.d(TAG, "render cmd " + nativePullCmd);
                    }
                    if (listener != null) {
                        if (nativePullCmd.equals("request_render")) {
                            listener.requestRender();
                        } else if (nativePullCmd.equals("start_preview")) {
                            listener.onStartPreview();
                        } else if (nativePullCmd.equals("stop_preview")) {
                            listener.onStopPreview();
                        } else if (nativePullCmd.equals("stop_record")) {
                            listener.onStopRecord();
                        } else if (nativePullCmd.equals("jpg_available")) {
                            listener.onPhotoResult(nativeGetResultJpeg());
                        } else if (nativePullCmd.startsWith("video_saved ")) {
                            listener.onVideoSaved(Integer.parseInt(nativePullCmd.substring(12)));
                        } else if (nativePullCmd.equals("msg_start")) {
                            listener.onMessage(Message.START);
                        } else if (nativePullCmd.equals("align_ok")) {
                            listener.onMessage(Message.ALIGN_OK);
                        } else if (nativePullCmd.equals("align_warning")) {
                            listener.onMessage(Message.ALIGN_WARNING);
                        } else if (nativePullCmd.equals("align_fail")) {
                            listener.onMessage(Message.ALIGN_TOO_LARGE_OR_FAILED);
                        } else if (nativePullCmd.equals("preview_no_person")) {
                            listener.onMessage(Message.PREVIEW_NO_PERSON);
                        } else if (nativePullCmd.equals("no_person")) {
                            listener.onMessage(Message.NO_PERSON);
                        } else if (nativePullCmd.equals("move_outside")) {
                            listener.onMessage(Message.MOVE_OUTSIDE);
                        } else if (nativePullCmd.equals("dynamic_scene")) {
                            listener.onMessage(Message.DYNAMIC_SCENE);
                        } else if (nativePullCmd.equals("init_error")) {
                            listener.onMessage(Message.ERROR_INIT);
                        } else if (nativePullCmd.equals("runtime_error")) {
                            listener.onMessage(Message.ERROR_RUNTIME);
                        }
                    }
                } else {
                    return;
                }
            }
        }
    }

    public static void renderInit(int i, int i2, int i3, int i4, int i5, int i6) {
        Log.d(TAG, "renderInit screenW " + i + ", screenH " + i2 + ", drawX = " + i3 + ", drawY = " + i4 + ", drawW = " + i5 + ", drawH = " + i6);
        nativeRenderInit(i, i2, i3, i4, i5, i6);
    }

    public static void saveVideo(String str) {
        nativeSaveVideo(str);
        Listener listener2 = listener;
        if (listener2 != null) {
            listener2.requestRender();
        }
    }

    public static void setListener(Listener listener2) {
        Log.d(TAG, "setListener " + listener2);
        listener = listener2;
    }

    public static void setMode(Mode mode) {
        Log.d(TAG, "setMode " + mode.name());
        nativeSetMode(mode.ordinal());
    }

    public static void start() {
        Log.d(TAG, "start");
        nativeStart();
    }

    public static void startRecordVideo() {
        Log.d(TAG, "startRecordVideo");
        nativeStartRecordVideo();
    }

    public static void stopRecordVideo() {
        Log.d(TAG, "stopRecordVideo");
        nativeStopRecordVideo();
    }
}
