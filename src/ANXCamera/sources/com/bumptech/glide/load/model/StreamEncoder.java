package com.bumptech.glide.load.model;

import android.util.Log;
import androidx.annotation.NonNull;
import com.bumptech.glide.load.Encoder;
import com.bumptech.glide.load.Options;
import com.bumptech.glide.load.engine.bitmap_recycle.ArrayPool;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class StreamEncoder implements Encoder<InputStream> {
    private static final String TAG = "StreamEncoder";
    private final ArrayPool byteArrayPool;

    public StreamEncoder(ArrayPool arrayPool) {
        this.byteArrayPool = arrayPool;
    }

    /* JADX WARNING: Removed duplicated region for block: B:21:0x0038 A[Catch:{ all -> 0x002e }] */
    /* JADX WARNING: Removed duplicated region for block: B:23:0x003f  */
    /* JADX WARNING: Removed duplicated region for block: B:28:0x004a A[SYNTHETIC, Splitter:B:28:0x004a] */
    public boolean encode(@NonNull InputStream inputStream, @NonNull File file, @NonNull Options options) {
        Throwable th;
        IOException e2;
        byte[] bArr = (byte[]) this.byteArrayPool.get(65536, byte[].class);
        boolean z = false;
        FileOutputStream fileOutputStream = null;
        try {
            FileOutputStream fileOutputStream2 = new FileOutputStream(file);
            while (true) {
                try {
                    int read = inputStream.read(bArr);
                    if (read == -1) {
                        break;
                    }
                    fileOutputStream2.write(bArr, 0, read);
                } catch (IOException e3) {
                    e2 = e3;
                    fileOutputStream = fileOutputStream2;
                    try {
                        if (Log.isLoggable(TAG, 3)) {
                        }
                        if (fileOutputStream != null) {
                        }
                        this.byteArrayPool.put(bArr);
                        return z;
                    } catch (Throwable th2) {
                        th = th2;
                        if (fileOutputStream != null) {
                            try {
                                fileOutputStream.close();
                            } catch (IOException unused) {
                            }
                        }
                        this.byteArrayPool.put(bArr);
                        throw th;
                    }
                } catch (Throwable th3) {
                    th = th3;
                    fileOutputStream = fileOutputStream2;
                    if (fileOutputStream != null) {
                    }
                    this.byteArrayPool.put(bArr);
                    throw th;
                }
            }
            fileOutputStream2.close();
            z = true;
            try {
                fileOutputStream2.close();
            } catch (IOException unused2) {
            }
        } catch (IOException e4) {
            e2 = e4;
            if (Log.isLoggable(TAG, 3)) {
                Log.d(TAG, "Failed to encode data onto the OutputStream", e2);
            }
            if (fileOutputStream != null) {
                fileOutputStream.close();
            }
            this.byteArrayPool.put(bArr);
            return z;
        }
        this.byteArrayPool.put(bArr);
        return z;
    }
}
