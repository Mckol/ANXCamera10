package com.xiaomi.camera.parallelservice.util;

import com.android.camera.log.Log;
import com.android.gallery3d.exif.ExifInterface;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class ParallelExifUtil {
    private static final String TAG = "ParallelExifUtil";

    /* JADX WARNING: Removed duplicated region for block: B:27:0x0099  */
    /* JADX WARNING: Removed duplicated region for block: B:30:0x00a1  */
    /* JADX WARNING: Removed duplicated region for block: B:34:0x00ae A[SYNTHETIC, Splitter:B:34:0x00ae] */
    /* JADX WARNING: Removed duplicated region for block: B:39:0x00b6 A[Catch:{ IOException -> 0x00b2 }] */
    /* JADX WARNING: Removed duplicated region for block: B:43:? A[RETURN, SYNTHETIC] */
    public static void updateExif(String str) {
        Throwable th;
        FileOutputStream fileOutputStream;
        FileInputStream fileInputStream;
        Exception e2;
        Exception e3;
        Throwable th2;
        Log.v(TAG, "updateExif path:" + str);
        File file = new File(str);
        if (!file.exists()) {
            Log.e(TAG, "updateExif path not exist. " + str);
            return;
        }
        File file2 = new File(str + ".tmp");
        try {
            file2.createNewFile();
            fileInputStream = new FileInputStream(file);
            try {
                fileOutputStream = new FileOutputStream(file2);
                try {
                    byte[] bArr = new byte[((int) file.length())];
                    fileInputStream.read(bArr);
                    fileOutputStream.write(ExifInterface.removeParallelProcessComment(bArr));
                    fileOutputStream.close();
                    file.delete();
                    file2.renameTo(file);
                    try {
                        fileInputStream.close();
                        fileOutputStream.close();
                    } catch (IOException e4) {
                        Log.e(TAG, "close image file failed!", e4);
                    }
                } catch (Exception e5) {
                    e2 = e5;
                    try {
                        Log.e(TAG, "process exif failed!", e2);
                        if (fileInputStream != null) {
                            fileInputStream.close();
                        }
                        if (fileOutputStream != null) {
                            fileOutputStream.close();
                        }
                    } catch (Throwable th3) {
                        th = th3;
                        if (fileInputStream != null) {
                        }
                        if (fileOutputStream != null) {
                        }
                        throw th;
                    }
                }
            } catch (Exception e6) {
                e3 = e6;
                fileOutputStream = null;
                e2 = e3;
                Log.e(TAG, "process exif failed!", e2);
                if (fileInputStream != null) {
                }
                if (fileOutputStream != null) {
                }
            } catch (Throwable th4) {
                th2 = th4;
                fileOutputStream = null;
                th = th2;
                if (fileInputStream != null) {
                }
                if (fileOutputStream != null) {
                }
                throw th;
            }
        } catch (Exception e7) {
            e3 = e7;
            fileInputStream = null;
            fileOutputStream = null;
            e2 = e3;
            Log.e(TAG, "process exif failed!", e2);
            if (fileInputStream != null) {
            }
            if (fileOutputStream != null) {
            }
        } catch (Throwable th5) {
            th2 = th5;
            fileInputStream = null;
            fileOutputStream = null;
            th = th2;
            if (fileInputStream != null) {
                try {
                    fileInputStream.close();
                } catch (IOException e8) {
                    Log.e(TAG, "close image file failed!", e8);
                    throw th;
                }
            }
            if (fileOutputStream != null) {
                fileOutputStream.close();
            }
            throw th;
        }
    }
}
