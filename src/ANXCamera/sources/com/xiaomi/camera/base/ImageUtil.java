package com.xiaomi.camera.base;

import android.graphics.ImageFormat;
import android.graphics.Rect;
import android.media.Image;
import android.util.Size;
import com.android.camera.log.Log;
import com.xiaomi.camera.imagecodec.ImagePool;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.Locale;
import libcore.io.Memory;

public final class ImageUtil {
    private static final String TAG = "ImageUtil";

    private ImageUtil() {
    }

    private static void directByteBufferCopy(ByteBuffer byteBuffer, int i, ByteBuffer byteBuffer2, int i2, int i3) {
        Memory.memmove(byteBuffer2, i2, byteBuffer, i, (long) i3);
    }

    /* JADX WARNING: Code restructure failed: missing block: B:67:0x0319, code lost:
        r0 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:68:0x031a, code lost:
        r1 = r0;
        r17 = r4;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:69:0x031e, code lost:
        r0 = e;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:70:0x031f, code lost:
        r8 = false;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:86:?, code lost:
        r17.flush();
        r17.close();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:87:0x0340, code lost:
        r0 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:88:0x0341, code lost:
        com.android.camera.log.Log.e(com.xiaomi.camera.base.ImageUtil.TAG, "Failed to flush/close stream", r0);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:95:?, code lost:
        r17.flush();
        r17.close();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:96:0x035a, code lost:
        r0 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:97:0x035b, code lost:
        com.android.camera.log.Log.e(com.xiaomi.camera.base.ImageUtil.TAG, "Failed to flush/close stream", r0);
     */
    /* JADX WARNING: Failed to process nested try/catch */
    /* JADX WARNING: Removed duplicated region for block: B:29:0x01e7 A[SYNTHETIC, Splitter:B:29:0x01e7] */
    /* JADX WARNING: Removed duplicated region for block: B:35:0x01fa A[SYNTHETIC, Splitter:B:35:0x01fa] */
    /* JADX WARNING: Removed duplicated region for block: B:67:0x0319 A[ExcHandler: all (r0v15 'th' java.lang.Throwable A[CUSTOM_DECLARE]), Splitter:B:56:0x029f] */
    /* JADX WARNING: Removed duplicated region for block: B:85:0x0339 A[SYNTHETIC, Splitter:B:85:0x0339] */
    /* JADX WARNING: Removed duplicated region for block: B:94:0x0353 A[SYNTHETIC, Splitter:B:94:0x0353] */
    public static boolean dumpYuvImage(Image image, String str) {
        boolean z;
        boolean z2;
        boolean z3;
        IOException e2;
        ByteBuffer buffer;
        ByteBuffer buffer2;
        int width;
        int height;
        int rowStride;
        Locale locale;
        Object[] objArr;
        IOException e3;
        int height2;
        int rowStride2;
        int rowStride3;
        int rowStride4;
        FileOutputStream fileOutputStream;
        Log.d(TAG, "dumpYuvImage: E");
        int format = image.getFormat();
        String str2 = "";
        FileOutputStream fileOutputStream2 = null;
        if (format == 17 || format == 35) {
            try {
                try {
                    buffer = image.getPlanes()[0].getBuffer();
                    buffer2 = image.getPlanes()[2].getBuffer();
                    width = image.getWidth();
                    height = image.getHeight();
                } catch (IOException e4) {
                    e2 = e4;
                    z3 = false;
                    try {
                        Log.e(TAG, "Failed to write image", e2);
                        if (fileOutputStream2 != null) {
                        }
                        z2 = z3;
                        z = z2;
                        Log.d(TAG, "dumpYuvImage: X");
                        return z;
                    } catch (Throwable th) {
                        Throwable th2 = th;
                        if (fileOutputStream2 != null) {
                        }
                        throw th2;
                    }
                }
                try {
                    rowStride = image.getPlanes()[0].getRowStride();
                    locale = Locale.ENGLISH;
                    objArr = new Object[6];
                } catch (IOException e5) {
                    e2 = e5;
                    z3 = false;
                    Log.e(TAG, "Failed to write image", e2);
                    if (fileOutputStream2 != null) {
                    }
                    z2 = z3;
                    z = z2;
                    Log.d(TAG, "dumpYuvImage: X");
                    return z;
                }
                try {
                    objArr[0] = Long.valueOf(image.getTimestamp());
                    objArr[1] = Integer.valueOf(rowStride);
                    objArr[2] = Integer.valueOf(width);
                    objArr[3] = Integer.valueOf(height);
                    objArr[4] = Integer.valueOf(image.getFormat());
                    if (str != null) {
                        str2 = "_" + str;
                    }
                    objArr[5] = str2;
                    String format2 = String.format(locale, "sdcard/DCIM/Camera/%d_stride%d_width%d_height%d_f%x%s.yuv", objArr);
                    Log.v(TAG, "dumpYuvImage: fileName=" + format2);
                    FileOutputStream fileOutputStream3 = new FileOutputStream(format2, true);
                    int i = height * rowStride;
                    int i2 = i >> 1;
                    try {
                        int limit = buffer.limit();
                        int limit2 = buffer2.limit();
                        buffer.rewind();
                        buffer2.rewind();
                        Log.d(TAG, "dumpYuvImage: size=" + image.getWidth() + "x" + image.getHeight() + " stride=" + image.getPlanes()[2].getRowStride());
                        byte[] bArr = new byte[(i2 + i)];
                        buffer.rewind();
                        buffer2.rewind();
                        z3 = false;
                        buffer.get(bArr, 0, limit);
                        buffer2.get(bArr, i, limit2);
                        int i3 = rowStride - width;
                        bArr[(bArr.length - 1) - i3] = bArr[(bArr.length - 3) - i3];
                        fileOutputStream3.write(bArr);
                        buffer.rewind();
                        buffer2.rewind();
                        try {
                            fileOutputStream3.flush();
                            fileOutputStream3.close();
                        } catch (IOException e6) {
                            Log.e(TAG, "Failed to flush/close stream", e6);
                        }
                        z2 = true;
                    } catch (IOException e7) {
                        e2 = e7;
                        fileOutputStream2 = fileOutputStream3;
                        Log.e(TAG, "Failed to write image", e2);
                        if (fileOutputStream2 != null) {
                        }
                        z2 = z3;
                        z = z2;
                        Log.d(TAG, "dumpYuvImage: X");
                        return z;
                    } catch (Throwable th3) {
                    }
                } catch (IOException e8) {
                    e2 = e8;
                    z3 = false;
                    Log.e(TAG, "Failed to write image", e2);
                    if (fileOutputStream2 != null) {
                    }
                    z2 = z3;
                    z = z2;
                    Log.d(TAG, "dumpYuvImage: X");
                    return z;
                }
            } catch (IOException e9) {
                e2 = e9;
                z3 = false;
                Log.e(TAG, "Failed to write image", e2);
                if (fileOutputStream2 != null) {
                }
                z2 = z3;
                z = z2;
                Log.d(TAG, "dumpYuvImage: X");
                return z;
            }
            z = z2;
        } else {
            if (format == 842094169) {
                try {
                    int width2 = image.getWidth();
                    height2 = image.getHeight();
                    rowStride2 = image.getPlanes()[0].getRowStride();
                    rowStride3 = image.getPlanes()[1].getRowStride();
                    rowStride4 = image.getPlanes()[2].getRowStride();
                    Log.d(TAG, "dumpYuvImage: size=" + width2 + "x" + height2 + " rowStride=[" + rowStride2 + " " + rowStride3 + " " + rowStride4 + "] pixelStride=" + image.getPlanes()[0].getPixelStride() + " " + image.getPlanes()[1].getPixelStride() + " " + image.getPlanes()[2].getPixelStride());
                    Locale locale2 = Locale.ENGLISH;
                    Object[] objArr2 = new Object[6];
                    objArr2[0] = Long.valueOf(image.getTimestamp());
                    objArr2[1] = Integer.valueOf(rowStride2);
                    objArr2[2] = Integer.valueOf(width2);
                    objArr2[3] = Integer.valueOf(height2);
                    objArr2[4] = Integer.valueOf(image.getFormat());
                    if (str != null) {
                        str2 = "_" + str;
                    }
                    objArr2[5] = str2;
                    String format3 = String.format(locale2, "sdcard/DCIM/Camera/%d_stride%d_width%d_height%d_f%x%s.yuv", objArr2);
                    Log.v(TAG, "dumpYuvImage: fileName=" + format3);
                    fileOutputStream = new FileOutputStream(format3, true);
                } catch (IOException e10) {
                    e3 = e10;
                    try {
                        Log.e(TAG, "Failed to write image", e3);
                        if (fileOutputStream2 != null) {
                        }
                        z = false;
                        Log.d(TAG, "dumpYuvImage: X");
                        return z;
                    } catch (Throwable th4) {
                        th = th4;
                        if (fileOutputStream2 != null) {
                        }
                        throw th;
                    }
                }
                try {
                    ByteBuffer buffer3 = image.getPlanes()[0].getBuffer();
                    ByteBuffer buffer4 = image.getPlanes()[1].getBuffer();
                    ByteBuffer buffer5 = image.getPlanes()[2].getBuffer();
                    int i4 = rowStride2 * height2;
                    int i5 = (rowStride3 * height2) >> 1;
                    int i6 = (rowStride4 * height2) >> 1;
                    int limit3 = buffer3.limit();
                    int limit4 = buffer4.limit();
                    int limit5 = buffer5.limit();
                    Log.d(TAG, "dumpYuvImage: ysize=" + i4 + " usize=" + i5 + " vsize=" + i6);
                    Log.d(TAG, "dumpYuvImage: yBufferSize=" + limit3 + " uBufferSize=" + limit4 + " vBufferSize=" + limit5);
                    int i7 = i5 + i4;
                    byte[] bArr2 = new byte[(i6 + i7)];
                    buffer3.rewind();
                    buffer4.rewind();
                    buffer5.rewind();
                    buffer3.get(bArr2, 0, limit3);
                    buffer4.get(bArr2, i4, limit4);
                    buffer5.get(bArr2, i7, limit5);
                    fileOutputStream.write(bArr2);
                    buffer3.rewind();
                    buffer4.rewind();
                    buffer5.rewind();
                    try {
                        fileOutputStream.flush();
                        fileOutputStream.close();
                    } catch (IOException e11) {
                        Log.e(TAG, "Failed to flush/close stream", e11);
                    }
                    z = true;
                } catch (IOException e12) {
                    e3 = e12;
                    fileOutputStream2 = fileOutputStream;
                    Log.e(TAG, "Failed to write image", e3);
                    if (fileOutputStream2 != null) {
                        try {
                            fileOutputStream2.flush();
                            fileOutputStream2.close();
                        } catch (IOException e13) {
                            Log.e(TAG, "Failed to flush/close stream", e13);
                        }
                    }
                    z = false;
                    Log.d(TAG, "dumpYuvImage: X");
                    return z;
                } catch (Throwable th5) {
                    th = th5;
                    fileOutputStream2 = fileOutputStream;
                    if (fileOutputStream2 != null) {
                        try {
                            fileOutputStream2.flush();
                            fileOutputStream2.close();
                        } catch (IOException e14) {
                            Log.e(TAG, "Failed to flush/close stream", e14);
                        }
                    }
                    throw th;
                }
            }
            z = false;
        }
        Log.d(TAG, "dumpYuvImage: X");
        return z;
    }

    /* JADX WARNING: Removed duplicated region for block: B:24:0x0118 A[SYNTHETIC, Splitter:B:24:0x0118] */
    /* JADX WARNING: Removed duplicated region for block: B:29:0x0128 A[SYNTHETIC, Splitter:B:29:0x0128] */
    /* JADX WARNING: Removed duplicated region for block: B:36:? A[RETURN, SYNTHETIC] */
    public static boolean dumpYuvImageAppendWH(Image image, String str) {
        Exception e2;
        Log.d(TAG, "dumpYuvImageAppendWH start :" + str);
        String str2 = "sdcard/DCIM/Camera/" + str;
        int format = image.getFormat();
        if (format != 17 && format != 35) {
            return false;
        }
        FileOutputStream fileOutputStream = null;
        try {
            FileOutputStream fileOutputStream2 = new FileOutputStream(str2 + ".yuv", true);
            try {
                ByteBuffer buffer = image.getPlanes()[0].getBuffer();
                ByteBuffer buffer2 = image.getPlanes()[2].getBuffer();
                int width = image.getWidth();
                int height = image.getHeight();
                int rowStride = image.getPlanes()[0].getRowStride();
                int i = rowStride * height;
                int limit = buffer.limit();
                int limit2 = buffer2.limit();
                buffer.rewind();
                buffer2.rewind();
                Log.d(TAG, "dumpYuvImageAppendWH: size=" + image.getWidth() + "x" + image.getHeight() + " stride=" + image.getPlanes()[2].getRowStride());
                byte[] bArr = new byte[((i >> 1) + i)];
                buffer.rewind();
                buffer2.rewind();
                buffer.get(bArr, 0, limit);
                buffer2.get(bArr, i, limit2);
                int i2 = rowStride - width;
                bArr[(bArr.length - 1) - i2] = bArr[(bArr.length + -3) - i2];
                fileOutputStream2.write(toBytes(width));
                fileOutputStream2.write(toBytes(height));
                fileOutputStream2.write(bArr);
                buffer.rewind();
                buffer2.rewind();
                try {
                    fileOutputStream2.flush();
                    fileOutputStream2.close();
                } catch (Exception e3) {
                    Log.e(TAG, "Failed to flush/close stream", e3);
                }
                return true;
            } catch (Exception e4) {
                e2 = e4;
                fileOutputStream = fileOutputStream2;
                try {
                    Log.e(TAG, "Failed to write image", e2);
                    if (fileOutputStream != null) {
                    }
                } catch (Throwable th) {
                    th = th;
                    fileOutputStream2 = fileOutputStream;
                    if (fileOutputStream2 != null) {
                        try {
                            fileOutputStream2.flush();
                            fileOutputStream2.close();
                        } catch (Exception e5) {
                            Log.e(TAG, "Failed to flush/close stream", e5);
                        }
                    }
                    throw th;
                }
            } catch (Throwable th2) {
                th = th2;
                if (fileOutputStream2 != null) {
                }
                throw th;
            }
        } catch (Exception e6) {
            e2 = e6;
            Log.e(TAG, "Failed to write image", e2);
            if (fileOutputStream != null) {
                return false;
            }
            try {
                fileOutputStream.flush();
                fileOutputStream.close();
                return false;
            } catch (Exception e7) {
                Log.e(TAG, "Failed to flush/close stream", e7);
                return false;
            }
        }
    }

    private static Size getEffectivePlaneSizeForImage(Image image, int i) {
        int format = image.getFormat();
        if (!(format == 1 || format == 2 || format == 3 || format == 4)) {
            if (format == 16) {
                return i == 0 ? new Size(image.getWidth(), image.getHeight()) : new Size(image.getWidth(), image.getHeight() / 2);
            }
            if (format != 17) {
                if (!(format == 20 || format == 32 || format == 256)) {
                    if (format != 842094169) {
                        if (format == 34) {
                            return new Size(0, 0);
                        }
                        if (format != 35) {
                            if (!(format == 37 || format == 38)) {
                                throw new UnsupportedOperationException(String.format("Invalid image format %d", Integer.valueOf(image.getFormat())));
                            }
                        }
                    }
                }
            }
            return i == 0 ? new Size(image.getWidth(), image.getHeight()) : new Size(image.getWidth() / 2, image.getHeight() / 2);
        }
        return new Size(image.getWidth(), image.getHeight());
    }

    public static byte[] getFirstPlane(Image image) {
        Image.Plane[] planes = image.getPlanes();
        if (planes.length <= 0) {
            return null;
        }
        ByteBuffer buffer = planes[0].getBuffer();
        byte[] bArr = new byte[buffer.remaining()];
        buffer.get(bArr);
        return bArr;
    }

    public static byte[] getYUVFromPreviewImage(Image image) {
        int i;
        if (isPreviewFormatSupported(image)) {
            Rect cropRect = image.getCropRect();
            int format = image.getFormat();
            int width = cropRect.width();
            int height = cropRect.height();
            Image.Plane[] planes = image.getPlanes();
            int i2 = width * height;
            byte[] bArr = new byte[((ImageFormat.getBitsPerPixel(format) * i2) / 8)];
            int i3 = 0;
            byte[] bArr2 = new byte[planes[0].getRowStride()];
            int i4 = 1;
            int i5 = 0;
            int i6 = 0;
            int i7 = 1;
            while (i5 < planes.length) {
                if (i5 != 0) {
                    if (i5 == i4) {
                        i6 = i2 + 1;
                    } else if (i5 == 2) {
                        i6 = i2;
                    }
                    i7 = 2;
                } else {
                    i6 = i3;
                    i7 = i4;
                }
                ByteBuffer buffer = planes[i5].getBuffer();
                int rowStride = planes[i5].getRowStride();
                int pixelStride = planes[i5].getPixelStride();
                int i8 = i5 == 0 ? i3 : i4;
                int i9 = width >> i8;
                int i10 = height >> i8;
                buffer.position(((cropRect.top >> i8) * rowStride) + ((cropRect.left >> i8) * pixelStride));
                for (int i11 = 0; i11 < i10; i11++) {
                    if (pixelStride == 1 && i7 == 1) {
                        buffer.get(bArr, i6, i9);
                        i6 += i9;
                        i = i9;
                    } else {
                        i = ((i9 - 1) * pixelStride) + 1;
                        buffer.get(bArr2, 0, i);
                        int i12 = i6;
                        for (int i13 = 0; i13 < i9; i13++) {
                            bArr[i12] = bArr2[i13 * pixelStride];
                            i12 += i7;
                        }
                        i6 = i12;
                    }
                    if (i11 < i10 - 1) {
                        buffer.position((buffer.position() + rowStride) - i);
                    }
                }
                i5++;
                width = width;
                height = height;
                i3 = 0;
                i4 = 1;
            }
            return bArr;
        }
        throw new IllegalStateException("can't convert Image to byte array, format " + image.getFormat());
    }

    public static byte[] getYuvData(Image image) {
        if (image == null || 35 != image.getFormat()) {
            String str = TAG;
            Log.e(str, "getYuvData: " + image);
            return null;
        }
        ByteBuffer buffer = image.getPlanes()[0].getBuffer();
        ByteBuffer buffer2 = image.getPlanes()[2].getBuffer();
        int limit = buffer.limit();
        int limit2 = buffer2.limit();
        byte[] bArr = new byte[(limit + limit2 + 1)];
        buffer.get(bArr, 0, limit);
        buffer2.get(bArr, limit, limit2);
        bArr[bArr.length - 1] = bArr[bArr.length - 3];
        return bArr;
    }

    public static void imageCopy(Image image, Image image2) {
        int remaining;
        if (image == null || image2 == null) {
            throw new IllegalArgumentException("Images should be non-null");
        } else if (image.getFormat() == 34 || image2.getFormat() == 34) {
            throw new IllegalArgumentException("PRIVATE format images are not copyable");
        } else if (image.getFormat() != 36) {
            Size size = new Size(image.getWidth(), image.getHeight());
            Size size2 = new Size(image2.getWidth(), image2.getHeight());
            if (size.equals(size2)) {
                Image.Plane[] planes = image.getPlanes();
                Image.Plane[] planes2 = image2.getPlanes();
                for (int i = 0; i < planes.length; i++) {
                    int rowStride = planes[i].getRowStride();
                    int rowStride2 = planes2[i].getRowStride();
                    ByteBuffer buffer = planes[i].getBuffer();
                    ByteBuffer buffer2 = planes2[i].getBuffer();
                    if (!buffer.isDirect() || !buffer2.isDirect()) {
                        throw new IllegalArgumentException("Source and destination ByteBuffers must be direct byteBuffer!");
                    } else if (planes[i].getPixelStride() == planes2[i].getPixelStride()) {
                        int position = buffer.position();
                        buffer.rewind();
                        buffer2.rewind();
                        if (rowStride == rowStride2) {
                            buffer2.put(buffer);
                        } else {
                            int position2 = buffer.position();
                            int position3 = buffer2.position();
                            Size effectivePlaneSizeForImage = getEffectivePlaneSizeForImage(image, i);
                            int width = effectivePlaneSizeForImage.getWidth() * planes[i].getPixelStride();
                            int i2 = position3;
                            int i3 = position2;
                            for (int i4 = 0; i4 < effectivePlaneSizeForImage.getHeight(); i4++) {
                                if (i4 == effectivePlaneSizeForImage.getHeight() - 1 && width > (remaining = buffer.remaining() - i3)) {
                                    width = remaining;
                                }
                                directByteBufferCopy(buffer, i3, buffer2, i2, width);
                                i3 += rowStride;
                                i2 += rowStride2;
                            }
                        }
                        buffer.position(position);
                        buffer2.rewind();
                    } else {
                        throw new IllegalArgumentException("Source plane image pixel stride " + planes[i].getPixelStride() + " must be same as destination image pixel stride " + planes2[i].getPixelStride());
                    }
                }
                return;
            }
            throw new IllegalArgumentException("source image size " + size + " is different with destination image size " + size2);
        } else {
            throw new IllegalArgumentException("Copy of RAW_OPAQUE format has not been implemented");
        }
    }

    private static boolean isPreviewFormatSupported(Image image) {
        int format = image.getFormat();
        return format == 17 || format == 35 || format == 842094169;
    }

    public static Image queueImageToPool(ImagePool imagePool, Image image, int i) {
        if (i < 0) {
            i = 0;
        }
        ImagePool.ImageFormat imageQueueKey = imagePool.toImageQueueKey(image);
        if (imagePool.isImageQueueFull(imageQueueKey, i)) {
            Log.w(TAG, "queueImageToPool: wait E");
            imagePool.waitIfImageQueueFull(imageQueueKey, i, 0);
            Log.w(TAG, "queueImageToPool: wait X");
        }
        long timestamp = image.getTimestamp();
        imagePool.queueImage(image);
        Image image2 = imagePool.getImage(timestamp);
        imagePool.holdImage(image2);
        return image2;
    }

    public static ByteBuffer removePadding(Image.Plane plane, int i, int i2) {
        int remaining;
        long currentTimeMillis = System.currentTimeMillis();
        ByteBuffer buffer = plane.getBuffer();
        int rowStride = plane.getRowStride();
        int pixelStride = plane.getPixelStride();
        Log.d(TAG, String.format(Locale.ENGLISH, "removePadding: rowStride=%d pixelStride=%d size=%dx%d", Integer.valueOf(rowStride), Integer.valueOf(pixelStride), Integer.valueOf(i), Integer.valueOf(i2)));
        int i3 = i * pixelStride;
        if (rowStride == i3) {
            return buffer;
        }
        int i4 = i3 * i2;
        ByteBuffer allocateDirect = ByteBuffer.allocateDirect(i4);
        int position = buffer.position();
        int position2 = allocateDirect.position();
        for (int i5 = 0; i5 < i2; i5++) {
            if (i5 == i2 - 1 && i3 > (remaining = buffer.remaining() - position)) {
                String str = TAG;
                Log.d(str, "removePadding: " + remaining + "/" + i3);
                i3 = remaining;
            }
            directByteBufferCopy(buffer, position, allocateDirect, position2, i3);
            position += rowStride;
            position2 += i3;
        }
        if (position2 < i4) {
            String str2 = TAG;
            Log.d(str2, "removePadding: add data: " + position2 + "|" + i4);
            while (position2 < i4) {
                allocateDirect.put(position2, allocateDirect.get(position2 - 2));
                position2++;
            }
        }
        String str3 = TAG;
        Log.v(str3, "removePadding: cost=" + (System.currentTimeMillis() - currentTimeMillis));
        return allocateDirect;
    }

    private static byte[] toBytes(int i) {
        return new byte[]{(byte) (i >> 24), (byte) (i >> 16), (byte) (i >> 8), (byte) i};
    }

    private static void updateImagePlane(Image.Plane plane, int i, int i2, byte[] bArr, boolean z, int i3) {
        int i4 = i3;
        ByteBuffer buffer = plane.getBuffer();
        buffer.rewind();
        int rowStride = plane.getRowStride();
        int pixelStride = plane.getPixelStride();
        int i5 = i * pixelStride;
        Log.d(TAG, String.format(Locale.ENGLISH, "updateImagePlane: size=%dx%d offset=%d length=%d rowStride=%d pixelStride=%d", Integer.valueOf(i), Integer.valueOf(i2), Integer.valueOf(i3), Integer.valueOf(bArr.length), Integer.valueOf(rowStride), Integer.valueOf(pixelStride)));
        int i6 = i5 * i2;
        int length = bArr.length - i4;
        if (length >= i6) {
            if (rowStride == i5) {
                int min = Math.min(buffer.remaining(), i6);
                String str = TAG;
                Log.d(str, "updateImagePlane: " + min);
                buffer.put(bArr, i4, min);
            } else if (z) {
                buffer.put(bArr, i4, Math.min(buffer.remaining(), (rowStride * (i2 - 1)) + i));
            } else {
                int position = buffer.position();
                int i7 = i5;
                for (int i8 = 0; i8 < i2; i8++) {
                    buffer.position(position);
                    if (i8 == i2 - 1 && (i7 = Math.min(buffer.remaining(), i5)) < i5) {
                        String str2 = TAG;
                        Log.d(str2, "updateImagePlane: " + i7 + "/" + i5);
                        i7 = buffer.remaining();
                    }
                    buffer.put(bArr, i4, i7);
                    i4 += i5;
                    position += rowStride;
                }
            }
            buffer.rewind();
            return;
        }
        throw new RuntimeException(String.format(Locale.ENGLISH, "buffer size should be at least %d but actual size is %d", Integer.valueOf(i6), Integer.valueOf(length)));
    }

    public static void updateYuvImage(Image image, byte[] bArr, boolean z) {
        if (image == null || 35 != image.getFormat()) {
            String str = TAG;
            Log.e(str, "updateYuvImage: " + image);
            return;
        }
        Image.Plane[] planes = image.getPlanes();
        updateImagePlane(planes[0], image.getWidth(), image.getHeight(), bArr, z, 0);
        int width = image.getWidth() * image.getHeight();
        if (z) {
            width = (planes[2].getRowStride() * (image.getHeight() - 1)) + image.getWidth();
        }
        updateImagePlane(planes[2], image.getWidth() / 2, image.getHeight() / 2, bArr, z, width);
    }
}
