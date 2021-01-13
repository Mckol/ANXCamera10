package com.android.camera;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteException;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PaintFlagsDrawFilter;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.os.Build;
import android.os.ParcelFileDescriptor;
import android.provider.MediaStore;
import android.text.TextUtils;
import com.android.camera.lib.compatibility.util.CompatibilityUtils;
import com.android.camera.log.Log;
import com.android.camera.storage.Storage;
import com.android.gallery3d.exif.ExifInterface;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileDescriptor;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

public class Thumbnail {
    private static final int BUFSIZE = 4096;
    private static final String LAST_THUMB_FILENAME = "last_thumb";
    private static final int RETRY_CREATE_THUMBNAIL_INTERVAL_TIME = 20;
    private static final long RETRY_CREATE_THUMBNAIL_TIME = 2000;
    private static final String TAG = "Thumbnail";
    public static final int THUMBNAIL_DELETED = 2;
    public static final int THUMBNAIL_FAIL_FROM_FILE = 3;
    public static final int THUMBNAIL_FOUND = 1;
    public static final int THUMBNAIL_NOT_FOUND = 0;
    public static final int THUMBNAIL_USE_FROM_FILE = -1;
    private static Object sLock = new Object();
    private Bitmap mBitmap;
    private boolean mFromFile = false;
    private boolean mIsGif = false;
    private Uri mUri;
    private boolean mWaitingForUri = false;

    /* access modifiers changed from: private */
    public static class Media {
        public final long dateTaken;
        public final int height;
        public final long id;
        public final int orientation;
        public final String path;
        public final Uri uri;
        public final int width;

        public Media(long j, int i, long j2, Uri uri2, String str, int i2, int i3) {
            this.id = j;
            this.orientation = i;
            this.dateTaken = j2;
            this.uri = uri2;
            this.path = str;
            this.width = i2;
            this.height = i3;
        }
    }

    private Thumbnail(Uri uri, Bitmap bitmap, int i, boolean z) {
        this.mUri = uri;
        this.mBitmap = adjustImage(bitmap, i, z);
    }

    private static Bitmap adjustImage(Bitmap bitmap, int i, boolean z) {
        int i2;
        int i3;
        if (i == 0 && !z && bitmap.getWidth() == bitmap.getHeight()) {
            return bitmap;
        }
        Matrix matrix = new Matrix();
        if (i % 180 != 0) {
            i3 = bitmap.getHeight();
            i2 = bitmap.getWidth();
        } else {
            i3 = bitmap.getWidth();
            i2 = bitmap.getHeight();
        }
        matrix.postTranslate(((float) (-bitmap.getWidth())) / 2.0f, ((float) (-bitmap.getHeight())) / 2.0f);
        matrix.postRotate((float) i);
        float f = ((float) i3) / 2.0f;
        float f2 = ((float) i2) / 2.0f;
        matrix.postTranslate(f, f2);
        matrix.postScale(z ? -1.0f : 1.0f, 1.0f, f, f2);
        int min = Math.min(i3, i2);
        matrix.postTranslate(((float) (min - i3)) / 2.0f, ((float) (min - i2)) / 2.0f);
        Bitmap bitmap2 = null;
        try {
            bitmap2 = Bitmap.createBitmap(min, min, Bitmap.Config.ARGB_8888);
            Canvas canvas = new Canvas(bitmap2);
            canvas.setDrawFilter(new PaintFlagsDrawFilter(0, 3));
            Paint paint = new Paint();
            paint.setAntiAlias(true);
            paint.setFilterBitmap(true);
            canvas.drawBitmap(bitmap, matrix, paint);
            bitmap.recycle();
            return bitmap2;
        } catch (Exception e2) {
            Log.w(TAG, "Failed to rotate thumbnail", e2);
            return bitmap2;
        } catch (OutOfMemoryError e3) {
            Log.w(TAG, "Failed to rotate thumbnail", e3);
            return bitmap2;
        }
    }

    public static Bitmap createBitmap(byte[] bArr, int i, boolean z, int i2) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inSampleSize = i2;
        options.inPurgeable = true;
        Bitmap decodeByteArray = BitmapFactory.decodeByteArray(bArr, 0, bArr.length, options);
        int i3 = i % 360;
        if (decodeByteArray != null && (i3 != 0 || z)) {
            Matrix matrix = new Matrix();
            Matrix matrix2 = new Matrix();
            if (i3 != 0) {
                matrix.setRotate((float) i3, ((float) decodeByteArray.getWidth()) * 0.5f, ((float) decodeByteArray.getHeight()) * 0.5f);
            }
            if (z) {
                matrix2.setScale(-1.0f, 1.0f, ((float) decodeByteArray.getWidth()) * 0.5f, ((float) decodeByteArray.getHeight()) * 0.5f);
                matrix.postConcat(matrix2);
            }
            try {
                Log.d(TAG, "createBitmap:createBitmap start ");
                Bitmap createBitmap = Bitmap.createBitmap(decodeByteArray, 0, 0, decodeByteArray.getWidth(), decodeByteArray.getHeight(), matrix, true);
                Log.d(TAG, "createBitmap: createBitmap end");
                if (createBitmap != decodeByteArray) {
                    decodeByteArray.recycle();
                }
                return createBitmap;
            } catch (Exception e2) {
                Log.w(TAG, "Failed to rotate thumbnail", e2);
            }
        }
        return decodeByteArray;
    }

    public static Thumbnail createThumbnail(Uri uri, Bitmap bitmap, int i, boolean z) {
        if (bitmap != null) {
            return new Thumbnail(uri, bitmap, i, z);
        }
        Log.e(TAG, "Failed to create thumbnail from null bitmap");
        return null;
    }

    /* JADX WARNING: Removed duplicated region for block: B:9:0x001c  */
    public static Thumbnail createThumbnail(byte[] bArr, int i, int i2, Uri uri, boolean z) {
        Bitmap bitmap;
        if (11 <= i2) {
            ExifInterface exifInterface = new ExifInterface();
            try {
                exifInterface.readExif(bArr);
                bitmap = exifInterface.getThumbnailBitmap();
            } catch (IOException e2) {
                Log.e(TAG, "failed to extract thumbnail from exif", e2);
            }
            if (bitmap == null) {
                BitmapFactory.Options options = new BitmapFactory.Options();
                options.inSampleSize = i2;
                options.inPurgeable = true;
                bitmap = BitmapFactory.decodeByteArray(bArr, 0, bArr.length, options);
            }
            return createThumbnail(uri, bitmap, i, z);
        }
        bitmap = null;
        if (bitmap == null) {
        }
        return createThumbnail(uri, bitmap, i, z);
    }

    /* JADX WARNING: Removed duplicated region for block: B:31:0x0085  */
    /* JADX WARNING: Removed duplicated region for block: B:33:0x008a  */
    /* JADX WARNING: Removed duplicated region for block: B:45:0x00c4  */
    public static Thumbnail createThumbnailFromUri(Context context, Uri uri, boolean z) {
        int i;
        int i2;
        long j;
        String str;
        int i3;
        boolean z2;
        Bitmap bitmap;
        boolean z3;
        int i4;
        if (!(uri == null || uri.getPath() == null)) {
            boolean contains = uri.getPath().contains("/images/media");
            Cursor query = context.getContentResolver().query(uri, contains ? new String[]{"_id", "_data", "orientation", "width", "height"} : new String[]{"_id", "_data", "width", "height"}, null, null, null);
            if (query != null) {
                try {
                    if (query.moveToFirst()) {
                        long j2 = query.getLong(0);
                        str = query.getString(1);
                        int i5 = contains ? query.getInt(2) : 0;
                        i = contains ? query.getInt(3) : query.getInt(2);
                        i2 = contains ? query.getInt(4) : query.getInt(3);
                        z2 = true;
                        i3 = i5;
                        j = j2;
                        if (query != null) {
                            query.close();
                        }
                        if (z2) {
                            if (contains) {
                                String fileTitleFromPath = Util.getFileTitleFromPath(str);
                                if (Build.VERSION.SDK_INT > 28 && !TextUtils.isEmpty(fileTitleFromPath)) {
                                    if (fileTitleFromPath.startsWith(context.getString(R.string.pano_file_name_prefix))) {
                                        i4 = i2;
                                        bitmap = getMiniKindThumbnailByP(context, j, i3, str, null);
                                        if (bitmap == null) {
                                            bitmap = ThumbnailUtils.createImageThumbnail(str, 1);
                                        }
                                        if (bitmap == null && str != null && str.endsWith(Storage.HEIC_SUFFIX)) {
                                            bitmap = getScaledBitmapFromFile(str, i, i4, i3);
                                        }
                                    }
                                }
                                i4 = i2;
                                bitmap = MediaStore.Images.Thumbnails.getThumbnail(context.getContentResolver(), j, 1, null);
                                if (bitmap == null) {
                                }
                                bitmap = getScaledBitmapFromFile(str, i, i4, i3);
                            } else {
                                bitmap = MediaStore.Video.Thumbnails.getThumbnail(context.getContentResolver(), j, 1, null);
                                if (bitmap == null) {
                                    bitmap = ThumbnailUtils.createVideoThumbnail(str, 1);
                                }
                            }
                            if (Build.VERSION.SDK_INT > 28) {
                                z3 = z;
                                i3 = 0;
                            } else {
                                z3 = z;
                            }
                            return createThumbnail(uri, bitmap, i3, z3);
                        }
                    }
                } catch (Throwable th) {
                    if (query != null) {
                        query.close();
                    }
                    throw th;
                }
            }
            j = -1;
            z2 = false;
            i3 = 0;
            i2 = 0;
            i = 0;
            str = null;
            if (query != null) {
            }
            if (z2) {
            }
        }
        return null;
    }

    public static Bitmap createVideoThumbnailBitmap(FileDescriptor fileDescriptor, int i, int i2) {
        return CompatibilityUtils.createVideoThumbnailBitmap(null, fileDescriptor, i, i2);
    }

    public static Bitmap createVideoThumbnailBitmap(String str, int i, int i2) {
        return CompatibilityUtils.createVideoThumbnailBitmap(str, null, i, i2);
    }

    private static String getImageBucketIds() {
        if (Storage.secondaryStorageMounted()) {
            return "bucket_id IN (" + Storage.PRIMARY_BUCKET_ID + "," + Storage.SECONDARY_BUCKET_ID + ")";
        }
        return "bucket_id=" + Storage.BUCKET_ID;
    }

    /* JADX WARNING: Removed duplicated region for block: B:23:0x0128  */
    /* JADX WARNING: Removed duplicated region for block: B:42:0x01a1  */
    /* JADX WARNING: Removed duplicated region for block: B:44:0x01a4  */
    /* JADX WARNING: Removed duplicated region for block: B:55:0x01bc  */
    /* JADX WARNING: Removed duplicated region for block: B:60:0x01c7  */
    /* JADX WARNING: Removed duplicated region for block: B:62:0x01cc  */
    /* JADX WARNING: Removed duplicated region for block: B:70:? A[RETURN, SYNTHETIC] */
    /* JADX WARNING: Removed duplicated region for block: B:71:? A[RETURN, SYNTHETIC] */
    private static Media getLastImageThumbnail(ContentResolver contentResolver) {
        Cursor cursor;
        Cursor cursor2;
        Throwable th;
        Cursor cursor3;
        Exception e2;
        boolean z;
        Uri uri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
        String[] strArr = {"_id", "orientation", "datetaken", "_data", "width", "height"};
        String str = ((((((("" + "(") + "mime_type='image/jpeg'") + " OR ") + "mime_type='image/heic'") + " OR ") + "mime_type='image/gif'") + ") AND ") + getImageBucketIds() + " AND " + "_size" + " > 0";
        try {
            cursor = contentResolver.query(uri.buildUpon().appendQueryParameter("limit", "1").build(), strArr, str, null, "datetaken DESC,_id DESC");
            if (cursor != null) {
                try {
                    if (cursor.moveToFirst()) {
                        String string = cursor.getString(3);
                        if (TextUtils.isEmpty(string) || !new File(string).exists()) {
                            Log.d(TAG, "getLastImageThumbnail first file is deleted");
                            z = true;
                            if (!z) {
                                cursor2 = contentResolver.query(uri, strArr, str, null, "datetaken DESC,_id DESC");
                                if (cursor2 != null) {
                                    try {
                                        Log.d(TAG, "getLastImageThumbnail count=" + cursor2.getCount());
                                        while (cursor2.moveToNext()) {
                                            String string2 = cursor2.getString(3);
                                            if (!TextUtils.isEmpty(string2) && new File(string2).exists()) {
                                                long j = cursor2.getLong(0);
                                                Media media = new Media(j, cursor2.getInt(1), cursor2.getLong(2), ContentUris.withAppendedId(uri, j), string2, cursor.getInt(4), cursor.getInt(5));
                                                if (cursor != null) {
                                                    cursor.close();
                                                }
                                                if (cursor2 != null) {
                                                    cursor2.close();
                                                }
                                                return media;
                                            }
                                        }
                                    } catch (Exception e3) {
                                        e2 = e3;
                                        cursor3 = cursor;
                                        try {
                                            Log.w(TAG, "getLastImageThumbnail error", e2);
                                            if (cursor3 != null) {
                                            }
                                            if (cursor2 == null) {
                                            }
                                            cursor2.close();
                                            return null;
                                        } catch (Throwable th2) {
                                            th = th2;
                                            cursor = cursor3;
                                            if (cursor != null) {
                                                cursor.close();
                                            }
                                            if (cursor2 != null) {
                                                cursor2.close();
                                            }
                                            throw th;
                                        }
                                    } catch (Throwable th3) {
                                        th = th3;
                                        if (cursor != null) {
                                        }
                                        if (cursor2 != null) {
                                        }
                                        throw th;
                                    }
                                }
                            } else {
                                cursor2 = null;
                            }
                            if (cursor != null) {
                                cursor.close();
                            }
                            if (cursor2 == null) {
                                return null;
                            }
                            cursor2.close();
                            return null;
                        }
                        long j2 = cursor.getLong(0);
                        Media media2 = new Media(j2, cursor.getInt(1), cursor.getLong(2), ContentUris.withAppendedId(uri, j2), string, cursor.getInt(4), cursor.getInt(5));
                        if (cursor != null) {
                            cursor.close();
                        }
                        return media2;
                    }
                } catch (Exception e4) {
                    e2 = e4;
                    cursor3 = cursor;
                    cursor2 = null;
                    Log.w(TAG, "getLastImageThumbnail error", e2);
                    if (cursor3 != null) {
                    }
                    if (cursor2 == null) {
                    }
                    cursor2.close();
                    return null;
                } catch (Throwable th4) {
                    th = th4;
                    cursor2 = null;
                    if (cursor != null) {
                    }
                    if (cursor2 != null) {
                    }
                    throw th;
                }
            }
            z = false;
            if (!z) {
            }
            if (cursor != null) {
            }
            if (cursor2 == null) {
            }
        } catch (Exception e5) {
            e2 = e5;
            cursor2 = null;
            cursor3 = null;
            Log.w(TAG, "getLastImageThumbnail error", e2);
            if (cursor3 != null) {
                cursor3.close();
            }
            if (cursor2 == null) {
                return null;
            }
            cursor2.close();
            return null;
        } catch (Throwable th5) {
            th = th5;
            cursor2 = null;
            cursor = null;
            if (cursor != null) {
            }
            if (cursor2 != null) {
            }
            throw th;
        }
        cursor2.close();
        return null;
    }

    public static int getLastThumbnailFromContentResolver(Context context, Thumbnail[] thumbnailArr, Uri uri) {
        Bitmap bitmap;
        ContentResolver contentResolver = context.getContentResolver();
        Media lastImageThumbnail = getLastImageThumbnail(contentResolver);
        Media lastVideoThumbnail = getLastVideoThumbnail(contentResolver);
        if (lastImageThumbnail == null && lastVideoThumbnail == null) {
            return 0;
        }
        if (lastImageThumbnail == null || (lastVideoThumbnail != null && lastImageThumbnail.dateTaken < lastVideoThumbnail.dateTaken)) {
            if (uri != null && uri.equals(lastVideoThumbnail.uri)) {
                return -1;
            }
            Bitmap thumbnail = MediaStore.Video.Thumbnails.getThumbnail(contentResolver, lastVideoThumbnail.id, 1, null);
            if (thumbnail == null) {
                Bitmap bitmap2 = thumbnail;
                int i = 0;
                while (true) {
                    if (((long) i) >= RETRY_CREATE_THUMBNAIL_TIME) {
                        break;
                    }
                    try {
                        bitmap2 = ThumbnailUtils.createVideoThumbnail(lastVideoThumbnail.path, 1);
                        if (bitmap2 != null) {
                            break;
                        }
                        Thread.sleep(20);
                        i += 20;
                    } catch (Exception e2) {
                        Log.e(TAG, "exception in createVideoThumbnail", e2);
                    }
                }
                bitmap = bitmap2;
            } else {
                bitmap = thumbnail;
            }
            lastImageThumbnail = lastVideoThumbnail;
        } else if (uri != null && uri.equals(lastImageThumbnail.uri)) {
            return -1;
        } else {
            String str = lastImageThumbnail.path;
            String fileTitleFromPath = Util.getFileTitleFromPath(str);
            bitmap = (Build.VERSION.SDK_INT <= 28 || TextUtils.isEmpty(fileTitleFromPath) || !fileTitleFromPath.startsWith(context.getString(R.string.pano_file_name_prefix))) ? MediaStore.Images.Thumbnails.getThumbnail(contentResolver, lastImageThumbnail.id, 1, null) : getMiniKindThumbnailByP(context, lastImageThumbnail.id, lastImageThumbnail.orientation, str, null);
            if (bitmap == null) {
                try {
                    bitmap = ThumbnailUtils.createImageThumbnail(str, 1);
                } catch (Exception e3) {
                    Log.e(TAG, "exception in createImageThumbnail", e3);
                }
            }
            if (bitmap == null && str != null && str.endsWith(Storage.HEIC_SUFFIX)) {
                bitmap = getScaledBitmapFromFile(str, lastImageThumbnail.width, lastImageThumbnail.height, lastImageThumbnail.orientation);
            }
        }
        if (!Util.isUriValid(lastImageThumbnail.uri, contentResolver)) {
            return 2;
        }
        if (bitmap == null) {
            return 3;
        }
        thumbnailArr[0] = createThumbnail(lastImageThumbnail.uri, bitmap, Build.VERSION.SDK_INT > 28 ? 0 : lastImageThumbnail.orientation, false);
        return 1;
    }

    /* JADX DEBUG: Multi-variable search result rejected for r4v7, resolved type: java.io.DataInputStream */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARNING: Code restructure failed: missing block: B:20:0x0049, code lost:
        r7 = createThumbnail(r5, r8, 0, false);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:21:0x004e, code lost:
        if (r7 == null) goto L_0x0063;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:23:0x005b, code lost:
        if (r0.getAbsolutePath().endsWith(".gif") == false) goto L_0x0060;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:24:0x005d, code lost:
        r7.setIsGif(true);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:25:0x0060, code lost:
        r7.setFromFile(true);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:26:0x0063, code lost:
        return r7;
     */
    public static Thumbnail getLastThumbnailFromFile(File file, ContentResolver contentResolver) {
        Throwable th;
        BufferedInputStream bufferedInputStream;
        BufferedInputStream bufferedInputStream2;
        FileInputStream fileInputStream;
        Object e2;
        BufferedInputStream bufferedInputStream3;
        File file2 = new File(file, LAST_THUMB_FILENAME);
        synchronized (sLock) {
            try {
                fileInputStream = new FileInputStream(file2);
                try {
                    bufferedInputStream2 = new BufferedInputStream(fileInputStream, 4096);
                    try {
                        DataInputStream dataInputStream = new DataInputStream(bufferedInputStream2);
                        Uri parse = Uri.parse(dataInputStream.readUTF());
                        if (!Util.isUriValid(parse, contentResolver)) {
                            dataInputStream.close();
                            Util.closeSilently(fileInputStream);
                            Util.closeSilently(bufferedInputStream2);
                            Util.closeSilently(dataInputStream);
                            return null;
                        }
                        try {
                            Bitmap decodeStream = BitmapFactory.decodeStream(dataInputStream);
                            dataInputStream.close();
                            Util.closeSilently(fileInputStream);
                            Util.closeSilently(bufferedInputStream2);
                            Util.closeSilently(dataInputStream);
                        } catch (SQLiteException | IOException e3) {
                            e2 = e3;
                            bufferedInputStream3 = dataInputStream;
                            try {
                                Log.i(TAG, "Fail to load bitmap. " + e2);
                                Util.closeSilently(fileInputStream);
                                Util.closeSilently(bufferedInputStream2);
                                Util.closeSilently(bufferedInputStream3);
                                return null;
                            } catch (Throwable th2) {
                                th = th2;
                                bufferedInputStream = bufferedInputStream3;
                                Util.closeSilently(fileInputStream);
                                Util.closeSilently(bufferedInputStream2);
                                Util.closeSilently(bufferedInputStream);
                                throw th;
                            }
                        }
                    } catch (SQLiteException | IOException e4) {
                        e2 = e4;
                        bufferedInputStream3 = null;
                        Log.i(TAG, "Fail to load bitmap. " + e2);
                        Util.closeSilently(fileInputStream);
                        Util.closeSilently(bufferedInputStream2);
                        Util.closeSilently(bufferedInputStream3);
                        return null;
                    } catch (Throwable th3) {
                        th = th3;
                        bufferedInputStream = null;
                        Util.closeSilently(fileInputStream);
                        Util.closeSilently(bufferedInputStream2);
                        Util.closeSilently(bufferedInputStream);
                        throw th;
                    }
                } catch (SQLiteException | IOException e5) {
                    e2 = e5;
                    bufferedInputStream2 = null;
                    bufferedInputStream3 = bufferedInputStream2;
                    Log.i(TAG, "Fail to load bitmap. " + e2);
                    Util.closeSilently(fileInputStream);
                    Util.closeSilently(bufferedInputStream2);
                    Util.closeSilently(bufferedInputStream3);
                    return null;
                } catch (Throwable th4) {
                    th = th4;
                    bufferedInputStream2 = null;
                    bufferedInputStream = bufferedInputStream2;
                    Util.closeSilently(fileInputStream);
                    Util.closeSilently(bufferedInputStream2);
                    Util.closeSilently(bufferedInputStream);
                    throw th;
                }
            } catch (SQLiteException | IOException e6) {
                e2 = e6;
                fileInputStream = null;
                bufferedInputStream2 = null;
                bufferedInputStream3 = bufferedInputStream2;
                Log.i(TAG, "Fail to load bitmap. " + e2);
                Util.closeSilently(fileInputStream);
                Util.closeSilently(bufferedInputStream2);
                Util.closeSilently(bufferedInputStream3);
                return null;
            } catch (Throwable th5) {
                th = th5;
                fileInputStream = null;
                bufferedInputStream2 = null;
                bufferedInputStream = bufferedInputStream2;
                Util.closeSilently(fileInputStream);
                Util.closeSilently(bufferedInputStream2);
                Util.closeSilently(bufferedInputStream);
                throw th;
            }
        }
    }

    public static int getLastThumbnailFromUriList(Context context, Thumbnail[] thumbnailArr, ArrayList<Uri> arrayList, Uri uri) {
        if (!(arrayList == null || arrayList.size() == 0)) {
            for (int size = arrayList.size() - 1; size >= 0; size--) {
                Uri uri2 = arrayList.get(size);
                if (Util.isUriValid(uri2, context.getContentResolver())) {
                    if (uri != null && uri.equals(uri2)) {
                        return -1;
                    }
                    thumbnailArr[0] = createThumbnailFromUri(context, uri2, false);
                    if (thumbnailArr[0] != null) {
                        return 1;
                    }
                }
            }
        }
        return 0;
    }

    public static Uri getLastThumbnailUri(ContentResolver contentResolver) {
        Media lastImageThumbnail = getLastImageThumbnail(contentResolver);
        Media lastVideoThumbnail = getLastVideoThumbnail(contentResolver);
        if (lastImageThumbnail != null && (lastVideoThumbnail == null || lastImageThumbnail.dateTaken >= lastVideoThumbnail.dateTaken)) {
            return lastImageThumbnail.uri;
        }
        if (lastVideoThumbnail == null) {
            return null;
        }
        if (lastImageThumbnail == null || lastVideoThumbnail.dateTaken >= lastImageThumbnail.dateTaken) {
            return lastVideoThumbnail.uri;
        }
        return null;
    }

    /* JADX WARNING: Code restructure failed: missing block: B:43:0x0126, code lost:
        if (r2 == null) goto L_0x0140;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:44:0x0128, code lost:
        r2.close();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:54:0x013d, code lost:
        if (r2 != null) goto L_0x0128;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:55:0x0140, code lost:
        return null;
     */
    /* JADX WARNING: Removed duplicated region for block: B:23:0x00ab  */
    /* JADX WARNING: Removed duplicated region for block: B:40:0x0120  */
    /* JADX WARNING: Removed duplicated region for block: B:42:0x0123  */
    /* JADX WARNING: Removed duplicated region for block: B:53:0x013a  */
    /* JADX WARNING: Removed duplicated region for block: B:58:0x0144  */
    /* JADX WARNING: Removed duplicated region for block: B:60:0x0149  */
    private static Media getLastVideoThumbnail(ContentResolver contentResolver) {
        Cursor cursor;
        Cursor cursor2;
        Throwable th;
        Exception e2;
        boolean z;
        Uri uri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
        String[] strArr = {"_id", "_data", "datetaken", "width", "height"};
        String str = getVideoBucketIds() + " AND " + "_size" + " > 0";
        try {
            cursor = contentResolver.query(uri.buildUpon().appendQueryParameter("limit", "1").build(), strArr, str, null, "datetaken DESC,_id DESC");
            if (cursor != null) {
                try {
                    if (cursor.moveToFirst()) {
                        long j = cursor.getLong(0);
                        if (cursor.getString(1) == null || !new File(cursor.getString(1)).exists()) {
                            Log.d(TAG, "getLastVideoThumbnail first file is deleted");
                            z = true;
                            if (!z) {
                                cursor2 = contentResolver.query(uri, strArr, str, null, "datetaken DESC,_id DESC");
                                try {
                                    Log.d(TAG, "getLastVideoThumbnail count=" + cursor2.getCount());
                                    if (cursor2 != null) {
                                        while (cursor2.moveToNext()) {
                                            if (cursor2.getString(1) != null && new File(cursor2.getString(1)).exists()) {
                                                long j2 = cursor2.getLong(0);
                                                Media media = new Media(j2, 0, cursor2.getLong(2), ContentUris.withAppendedId(uri, j2), cursor2.getString(1), cursor.getInt(3), cursor.getInt(4));
                                                if (cursor != null) {
                                                    cursor.close();
                                                }
                                                if (cursor2 != null) {
                                                    cursor2.close();
                                                }
                                                return media;
                                            }
                                        }
                                    }
                                } catch (Exception e3) {
                                    e2 = e3;
                                    try {
                                        Log.w(TAG, "getLastVideoThumbnail error", e2);
                                        if (cursor != null) {
                                            cursor.close();
                                        }
                                    } catch (Throwable th2) {
                                        th = th2;
                                        if (cursor != null) {
                                            cursor.close();
                                        }
                                        if (cursor2 != null) {
                                            cursor2.close();
                                        }
                                        throw th;
                                    }
                                }
                            } else {
                                cursor2 = null;
                            }
                            if (cursor != null) {
                                cursor.close();
                            }
                        } else {
                            Media media2 = new Media(j, 0, cursor.getLong(2), ContentUris.withAppendedId(uri, j), cursor.getString(1), cursor.getInt(3), cursor.getInt(4));
                            if (cursor != null) {
                                cursor.close();
                            }
                            return media2;
                        }
                    }
                } catch (Exception e4) {
                    e2 = e4;
                    cursor2 = null;
                    Log.w(TAG, "getLastVideoThumbnail error", e2);
                    if (cursor != null) {
                    }
                } catch (Throwable th3) {
                    th = th3;
                    cursor2 = null;
                    if (cursor != null) {
                    }
                    if (cursor2 != null) {
                    }
                    throw th;
                }
            }
            z = false;
            if (!z) {
            }
            if (cursor != null) {
            }
        } catch (Exception e5) {
            e2 = e5;
            cursor2 = null;
            cursor = null;
            Log.w(TAG, "getLastVideoThumbnail error", e2);
            if (cursor != null) {
            }
        } catch (Throwable th4) {
            th = th4;
            cursor2 = null;
            cursor = null;
            if (cursor != null) {
            }
            if (cursor2 != null) {
            }
            throw th;
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:24:0x00a0  */
    /* JADX WARNING: Removed duplicated region for block: B:36:0x00b5  */
    /* JADX WARNING: Removed duplicated region for block: B:39:0x00bb A[ADDED_TO_REGION] */
    /* JADX WARNING: Removed duplicated region for block: B:42:0x00de  */
    /* JADX WARNING: Removed duplicated region for block: B:45:? A[ADDED_TO_REGION, RETURN, SYNTHETIC] */
    private static Bitmap getMiniKindThumbnailByP(Context context, long j, int i, String str, BitmapFactory.Options options) {
        Throwable th;
        Bitmap bitmap;
        Bitmap bitmap2;
        SQLiteException e2;
        IOException e3;
        Uri uri;
        OutOfMemoryError e4;
        ContentResolver contentResolver = context.getContentResolver();
        Uri mediaUri = Storage.getMediaUri(context, false, str);
        Cursor cursor = null;
        r13 = null;
        r13 = null;
        r13 = null;
        r13 = null;
        Bitmap bitmap3 = null;
        try {
            Cursor cursor2 = contentResolver.query(mediaUri.buildUpon().appendQueryParameter("blocking", "1").appendQueryParameter("orig_id", String.valueOf(j)).appendQueryParameter("group_id", String.valueOf(0L)).build(), null, null, null, null);
            if (cursor2 == null) {
                if (cursor2 != null) {
                    cursor2.close();
                }
                return null;
            }
            try {
                if (cursor2.moveToFirst()) {
                    try {
                        uri = ContentUris.withAppendedId(mediaUri, j);
                        try {
                            ParcelFileDescriptor openFileDescriptor = contentResolver.openFileDescriptor(uri, "r");
                            bitmap3 = BitmapFactory.decodeFileDescriptor(openFileDescriptor.getFileDescriptor(), null, options);
                            openFileDescriptor.close();
                        } catch (IOException e5) {
                            e3 = e5;
                        } catch (OutOfMemoryError e6) {
                            e4 = e6;
                            Log.e(TAG, "failed to allocate memory for thumbnail " + uri + "; " + e4);
                            if (cursor2 != null) {
                            }
                            bitmap = bitmap3;
                            if (i == 0) {
                            }
                        }
                    } catch (IOException e7) {
                        e3 = e7;
                        uri = null;
                        Log.e(TAG, "couldn't open thumbnail " + uri + "; " + e3);
                        if (cursor2 != null) {
                        }
                        bitmap = bitmap3;
                        if (i == 0) {
                        }
                    } catch (OutOfMemoryError e8) {
                        e4 = e8;
                        uri = null;
                        Log.e(TAG, "failed to allocate memory for thumbnail " + uri + "; " + e4);
                        if (cursor2 != null) {
                        }
                        bitmap = bitmap3;
                        if (i == 0) {
                        }
                    }
                }
                if (cursor2 != null) {
                    cursor2.close();
                }
                bitmap = bitmap3;
            } catch (SQLiteException e9) {
                e2 = e9;
                bitmap2 = null;
                cursor = cursor2;
                try {
                    Log.w(TAG, e2);
                    if (cursor != null) {
                        cursor.close();
                    }
                    bitmap = bitmap2;
                    return i == 0 ? bitmap : bitmap;
                } catch (Throwable th2) {
                    th = th2;
                    cursor2 = cursor;
                    if (cursor2 != null) {
                    }
                    throw th;
                }
            } catch (Throwable th3) {
                th = th3;
                if (cursor2 != null) {
                    cursor2.close();
                }
                throw th;
            }
            if (i == 0 && bitmap != null) {
                int width = bitmap.getWidth();
                int height = bitmap.getHeight();
                Matrix matrix = new Matrix();
                matrix.setRotate((float) i, (float) (width / 2), (float) (height / 2));
                return Bitmap.createBitmap(bitmap, 0, 0, width, height, matrix, false);
            }
        } catch (SQLiteException e10) {
            e2 = e10;
            bitmap2 = null;
            Log.w(TAG, e2);
            if (cursor != null) {
            }
            bitmap = bitmap2;
            if (i == 0) {
            }
        }
    }

    private static Bitmap getScaledBitmapFromFile(String str, int i, int i2, int i3) {
        Exception e2;
        try {
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inSampleSize = Integer.highestOneBit((int) Math.ceil(((double) Math.max(i, i2)) / 512.0d));
            options.inPurgeable = true;
            Bitmap decodeFile = BitmapFactory.decodeFile(str);
            if (decodeFile == null || i3 == 0) {
                return decodeFile;
            }
            try {
                int width = decodeFile.getWidth();
                int height = decodeFile.getHeight();
                Matrix matrix = new Matrix();
                matrix.setRotate((float) i3, (float) (width / 2), (float) (height / 2));
                return Bitmap.createBitmap(decodeFile, 0, 0, width, height, matrix, false);
            } catch (Exception e3) {
                e2 = e3;
            }
        } catch (Exception e4) {
            e2 = e4;
            Log.e(TAG, "decode fail from " + str, e2);
            return null;
        }
    }

    private static String getVideoBucketIds() {
        if (Storage.secondaryStorageMounted()) {
            return "bucket_id IN (" + Storage.PRIMARY_BUCKET_ID + "," + Storage.SECONDARY_BUCKET_ID + ")";
        }
        return "bucket_id=" + Storage.BUCKET_ID;
    }

    public boolean fromFile() {
        return this.mFromFile;
    }

    public Bitmap getBitmap() {
        return this.mBitmap;
    }

    public Uri getUri() {
        return this.mUri;
    }

    public boolean isGif() {
        return this.mIsGif;
    }

    public boolean isWaitingForUri() {
        return this.mWaitingForUri;
    }

    public void saveLastThumbnailToFile(File file) {
        Throwable th;
        DataOutputStream dataOutputStream;
        BufferedOutputStream bufferedOutputStream;
        IOException e2;
        if (this.mUri == null) {
            Log.w(TAG, "Fail to store bitmap. uri is null");
            return;
        }
        File file2 = new File(file, LAST_THUMB_FILENAME);
        synchronized (sLock) {
            FileOutputStream fileOutputStream = null;
            try {
                FileOutputStream fileOutputStream2 = new FileOutputStream(file2);
                try {
                    bufferedOutputStream = new BufferedOutputStream(fileOutputStream2, 4096);
                } catch (IOException e3) {
                    e2 = e3;
                    bufferedOutputStream = null;
                    dataOutputStream = null;
                    fileOutputStream = fileOutputStream2;
                    try {
                        Log.e(TAG, "Fail to store bitmap. path=" + file2.getPath(), e2);
                        Util.closeSilently(fileOutputStream);
                        Util.closeSilently(bufferedOutputStream);
                        Util.closeSilently(dataOutputStream);
                    } catch (Throwable th2) {
                        th = th2;
                        Util.closeSilently(fileOutputStream);
                        Util.closeSilently(bufferedOutputStream);
                        Util.closeSilently(dataOutputStream);
                        throw th;
                    }
                } catch (Throwable th3) {
                    th = th3;
                    bufferedOutputStream = null;
                    dataOutputStream = null;
                    fileOutputStream = fileOutputStream2;
                    Util.closeSilently(fileOutputStream);
                    Util.closeSilently(bufferedOutputStream);
                    Util.closeSilently(dataOutputStream);
                    throw th;
                }
                try {
                    dataOutputStream = new DataOutputStream(bufferedOutputStream);
                } catch (IOException e4) {
                    e2 = e4;
                    dataOutputStream = null;
                    fileOutputStream = fileOutputStream2;
                    Log.e(TAG, "Fail to store bitmap. path=" + file2.getPath(), e2);
                    Util.closeSilently(fileOutputStream);
                    Util.closeSilently(bufferedOutputStream);
                    Util.closeSilently(dataOutputStream);
                } catch (Throwable th4) {
                    th = th4;
                    dataOutputStream = null;
                    fileOutputStream = fileOutputStream2;
                    Util.closeSilently(fileOutputStream);
                    Util.closeSilently(bufferedOutputStream);
                    Util.closeSilently(dataOutputStream);
                    throw th;
                }
                try {
                    dataOutputStream.writeUTF(this.mUri.toString());
                    this.mBitmap.compress(Bitmap.CompressFormat.JPEG, 90, dataOutputStream);
                    dataOutputStream.close();
                    Util.closeSilently(fileOutputStream2);
                    Util.closeSilently(bufferedOutputStream);
                } catch (IOException e5) {
                    e2 = e5;
                    fileOutputStream = fileOutputStream2;
                    Log.e(TAG, "Fail to store bitmap. path=" + file2.getPath(), e2);
                    Util.closeSilently(fileOutputStream);
                    Util.closeSilently(bufferedOutputStream);
                    Util.closeSilently(dataOutputStream);
                } catch (Throwable th5) {
                    th = th5;
                    fileOutputStream = fileOutputStream2;
                    Util.closeSilently(fileOutputStream);
                    Util.closeSilently(bufferedOutputStream);
                    Util.closeSilently(dataOutputStream);
                    throw th;
                }
            } catch (IOException e6) {
                e2 = e6;
                bufferedOutputStream = null;
                dataOutputStream = null;
                Log.e(TAG, "Fail to store bitmap. path=" + file2.getPath(), e2);
                Util.closeSilently(fileOutputStream);
                Util.closeSilently(bufferedOutputStream);
                Util.closeSilently(dataOutputStream);
            } catch (Throwable th6) {
                th = th6;
                bufferedOutputStream = null;
                dataOutputStream = null;
                Util.closeSilently(fileOutputStream);
                Util.closeSilently(bufferedOutputStream);
                Util.closeSilently(dataOutputStream);
                throw th;
            }
            Util.closeSilently(dataOutputStream);
        }
    }

    public void setFromFile(boolean z) {
        this.mFromFile = z;
    }

    public void setIsGif(boolean z) {
        this.mIsGif = z;
    }

    public void setUri(Uri uri) {
        if (this.mUri != null) {
            Log.d(TAG, "the uri for thumbnail is being updated unexpectedly..ignore.");
            return;
        }
        this.mUri = uri;
        this.mWaitingForUri = false;
    }

    public void startWaitingForUri() {
        this.mWaitingForUri = true;
    }
}
