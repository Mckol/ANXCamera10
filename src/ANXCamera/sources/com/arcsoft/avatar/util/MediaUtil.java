package com.arcsoft.avatar.util;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Location;
import android.media.ExifInterface;
import android.media.MediaMetadataRetriever;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import com.android.camera.storage.Storage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MediaUtil {
    public static final int BUCKET_ID_COLUMN_INDEX = 5;
    public static final int DATE_COLUMN_INDEX = 3;
    public static final int DURATION_COLUMN_INDEX = 14;
    public static final String EXTERNAL_BUCKET_NAME = b(f131a);
    public static final Uri EXTERNAL_CONTENT_URI = MediaStore.Files.getContentUri("external");
    public static final int FULLPATH_COLUMN_INDEX = 1;
    public static final int HEIGHT_COLUMN_INDEX = 12;
    public static final int ID_COLUMN_INDEX = 0;
    public static final int MEDIA_TYPE_COLUMN_INDEX = 8;
    public static final int MIME_TYPE_COLUMN_INDEX = 6;
    public static final int MODIFIED_DATE_COLUMN_INDEX = 7;
    public static final int ORIENTATION_COLUMN_INDEX = 13;
    public static final int RESOLUTION_COLUMN_INDEX = 9;
    public static final int SIZE_COLUMN_INDEX = 2;
    public static final int TAGS_COLUMN_INDEX = 10;
    public static final int TYPE_IMAGE = 1;
    public static final int TYPE_VIDEO = 3;
    public static final int URI_COLUMN_INDEX = 4;
    public static final int WIDTH_COLUMN_INDEX = 11;

    /* renamed from: a  reason: collision with root package name */
    private static final String f131a = (Environment.getExternalStorageDirectory().toString() + "/DCIM/Avatar/Result");

    /* renamed from: b  reason: collision with root package name */
    private static MediaUtil f132b = null;
    private static final String[] k = {"max(_id) as newId", "_data", "_size", "datetaken", FileColumns.f140e, "bucket_id", "mime_type", "date_modified", "media_type", "resolution", "tags", "width", "height", "orientation", "duration"};

    /* renamed from: c  reason: collision with root package name */
    private Context f133c = null;

    /* renamed from: d  reason: collision with root package name */
    private ContentResolver f134d = null;

    /* renamed from: e  reason: collision with root package name */
    private final String f135e = Storage.MIME_JPEG;
    private final String f = Storage.MIME_GIF;
    private final String g = "video/3gpp";
    private final String h = "video/mp4";
    private final String[] i = {"_id", "bucket_id", "bucket_display_name", "_data", "_display_name", "width", "height", "_size", "mime_type", "datetaken", "date_modified", "date_added", "latitude", "longitude", "duration", "resolution"};
    private final String[] j = {"_id", "bucket_id", "bucket_display_name", "_data", "_display_name", "width", "height", "_size", "mime_type", "datetaken", "date_modified", "date_added", "latitude", "longitude", "orientation"};

    private static final class FileColumns {

        /* renamed from: a  reason: collision with root package name */
        public static final String f136a = "_id";

        /* renamed from: b  reason: collision with root package name */
        public static final String f137b = "_data";

        /* renamed from: c  reason: collision with root package name */
        public static final String f138c = "_size";

        /* renamed from: d  reason: collision with root package name */
        public static final String f139d = "datetaken";

        /* renamed from: e  reason: collision with root package name */
        public static final String f140e = ("case media_type when 1 then \"" + MediaStore.Images.Media.EXTERNAL_CONTENT_URI + "\" else \"" + MediaStore.Video.Media.EXTERNAL_CONTENT_URI + "\" end");
        public static final String f = "bucket_id";
        public static final String g = "mime_type";
        public static final String h = "date_modified";
        public static final String i = "latitude";
        public static final String j = "longitude";
        public static final String k = "orientation";
        public static final String l = "media_type";
        public static final String m = "duration";
        public static final String n = "resolution";
        public static final String o = "tags";
        public static final String p = "width";
        public static final String q = "height";
        public static final String r = "title";
        public static final String s = "_display_name";

        private FileColumns() {
        }
    }

    public static class MediaFileInfo {

        /* renamed from: a  reason: collision with root package name */
        private boolean f141a;

        /* renamed from: b  reason: collision with root package name */
        private Uri f142b;

        /* renamed from: c  reason: collision with root package name */
        private long f143c;

        /* renamed from: d  reason: collision with root package name */
        private long f144d;

        /* renamed from: e  reason: collision with root package name */
        private String f145e;
        private String f;
        private String g;
        private int h;
        private int i;
        private long j;
        private String k;
        private String l;
        private String m;
        private String n;
        private double o;
        private double p;
        private int q;
        private long r;
        private String s;
    }

    private MediaUtil(Context context) {
        this.f133c = context;
        this.f134d = this.f133c.getContentResolver();
    }

    private static Uri a(Cursor cursor) {
        return ContentUris.withAppendedId(Uri.parse(cursor.getString(4)), cursor.getLong(0));
    }

    private MediaFileInfo a(Cursor cursor, boolean z) {
        if (cursor == null || cursor.getCount() <= 0) {
            return null;
        }
        MediaFileInfo mediaFileInfo = new MediaFileInfo();
        mediaFileInfo.f141a = z;
        if (z) {
            mediaFileInfo.f143c = cursor.getLong(ArrayUtil.getIndex(this.i, "_id"));
            mediaFileInfo.f142b = ContentUris.withAppendedId(MediaStore.Video.Media.EXTERNAL_CONTENT_URI, mediaFileInfo.f143c);
            mediaFileInfo.f144d = (long) cursor.getInt(ArrayUtil.getIndex(this.i, "bucket_id"));
            mediaFileInfo.f145e = cursor.getString(ArrayUtil.getIndex(this.i, "bucket_display_name"));
            mediaFileInfo.f = cursor.getString(ArrayUtil.getIndex(this.i, "_data"));
            mediaFileInfo.g = cursor.getString(ArrayUtil.getIndex(this.i, "_display_name"));
            mediaFileInfo.h = cursor.getInt(ArrayUtil.getIndex(this.i, "width"));
            mediaFileInfo.i = cursor.getInt(ArrayUtil.getIndex(this.i, "height"));
            mediaFileInfo.j = cursor.getLong(ArrayUtil.getIndex(this.i, "_size"));
            mediaFileInfo.k = cursor.getString(ArrayUtil.getIndex(this.i, "mime_type"));
            mediaFileInfo.l = cursor.getString(ArrayUtil.getIndex(this.i, "datetaken"));
            mediaFileInfo.m = cursor.getString(ArrayUtil.getIndex(this.i, "date_modified"));
            mediaFileInfo.n = cursor.getString(ArrayUtil.getIndex(this.i, "date_added"));
            mediaFileInfo.o = cursor.getDouble(ArrayUtil.getIndex(this.i, "latitude"));
            mediaFileInfo.p = cursor.getDouble(ArrayUtil.getIndex(this.i, "longitude"));
            mediaFileInfo.r = cursor.getLong(ArrayUtil.getIndex(this.i, "duration"));
            mediaFileInfo.s = cursor.getString(ArrayUtil.getIndex(this.i, "resolution"));
        } else {
            mediaFileInfo.f143c = cursor.getLong(ArrayUtil.getIndex(this.j, "_id"));
            mediaFileInfo.f142b = ContentUris.withAppendedId(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, mediaFileInfo.f143c);
            mediaFileInfo.f144d = (long) cursor.getInt(ArrayUtil.getIndex(this.j, "bucket_id"));
            mediaFileInfo.f145e = cursor.getString(ArrayUtil.getIndex(this.j, "bucket_display_name"));
            mediaFileInfo.f = cursor.getString(ArrayUtil.getIndex(this.j, "_data"));
            mediaFileInfo.g = cursor.getString(ArrayUtil.getIndex(this.j, "_display_name"));
            mediaFileInfo.h = cursor.getInt(ArrayUtil.getIndex(this.j, "width"));
            mediaFileInfo.i = cursor.getInt(ArrayUtil.getIndex(this.j, "height"));
            mediaFileInfo.j = cursor.getLong(ArrayUtil.getIndex(this.j, "_size"));
            mediaFileInfo.k = cursor.getString(ArrayUtil.getIndex(this.j, "mime_type"));
            mediaFileInfo.l = cursor.getString(ArrayUtil.getIndex(this.j, "datetaken"));
            mediaFileInfo.m = cursor.getString(ArrayUtil.getIndex(this.j, "date_modified"));
            mediaFileInfo.n = cursor.getString(ArrayUtil.getIndex(this.j, "date_added"));
            mediaFileInfo.o = cursor.getDouble(ArrayUtil.getIndex(this.j, "latitude"));
            mediaFileInfo.p = cursor.getDouble(ArrayUtil.getIndex(this.j, "longitude"));
            mediaFileInfo.q = cursor.getInt(ArrayUtil.getIndex(this.j, "orientation"));
        }
        return mediaFileInfo;
    }

    private String a(String str) {
        String substring = str.substring(str.lastIndexOf(46));
        return (Storage.JPEG_SUFFIX.equalsIgnoreCase(substring) || ".jpeg".equalsIgnoreCase(substring)) ? Storage.MIME_JPEG : ".gif".equalsIgnoreCase(substring) ? Storage.MIME_GIF : (".3gp".equalsIgnoreCase(substring) || ".3gpp".equalsIgnoreCase(substring)) ? "video/3gpp" : ".mp4".equalsIgnoreCase(substring) ? "video/mp4" : "";
    }

    private static String b(String str) {
        return String.valueOf(str.substring(0, str.lastIndexOf("/")).toLowerCase().hashCode());
    }

    private String c(String str) {
        int lastIndexOf;
        if (str == null || str.isEmpty() || (lastIndexOf = str.lastIndexOf(46)) < 0 || lastIndexOf >= str.length() - 1) {
            return null;
        }
        return str.substring(lastIndexOf + 1);
    }

    private boolean d(String str) {
        if (str == null) {
            return false;
        }
        return new File(str).exists();
    }

    private long e(String str) {
        if (!d(str)) {
            return -1;
        }
        try {
            MediaMetadataRetriever mediaMetadataRetriever = new MediaMetadataRetriever();
            mediaMetadataRetriever.setDataSource(str);
            String extractMetadata = mediaMetadataRetriever.extractMetadata(9);
            mediaMetadataRetriever.release();
            return Long.parseLong(extractMetadata);
        } catch (Exception e2) {
            e2.printStackTrace();
            return 0;
        }
    }

    public static MediaUtil getInstance(Context context) {
        if (f132b == null) {
            synchronized (MediaUtil.class) {
                if (f132b == null) {
                    f132b = new MediaUtil(context);
                }
            }
        }
        return f132b;
    }

    public static Cursor getMedias(ContentResolver contentResolver) {
        return contentResolver.query(EXTERNAL_CONTENT_URI, k, "(media_type=? or media_type=?) and bucket_id=? ", new String[]{String.valueOf(1), String.valueOf(3), EXTERNAL_BUCKET_NAME}, "_id DESC");
    }

    public Uri addMediaFile(String str, int i2, int i3) {
        return addMediaFile(str, i2, i3, null, 0);
    }

    public Uri addMediaFile(String str, int i2, int i3, int i4) {
        return addMediaFile(str, i2, i3, null, i4);
    }

    public Uri addMediaFile(String str, int i2, int i3, Location location) {
        return addMediaFile(str, i2, i3, location, 0);
    }

    public Uri addMediaFile(String str, int i2, int i3, Location location, int i4) {
        String str2;
        Uri uri;
        MediaUtil mediaUtil;
        boolean isVideoFile = isVideoFile(str);
        String a2 = a(str);
        File file = new File(str);
        String name = file.getName();
        String substring = name.substring(0, name.lastIndexOf("."));
        ContentValues contentValues = new ContentValues();
        if (isVideoFile) {
            contentValues.put("_data", file.getPath());
            contentValues.put("_display_name", name);
            contentValues.put("title", substring);
            contentValues.put("width", Integer.valueOf(i2));
            contentValues.put("height", Integer.valueOf(i3));
            contentValues.put("_size", Long.valueOf(file.length()));
            contentValues.put("mime_type", a2);
            contentValues.put("datetaken", Long.valueOf(System.currentTimeMillis()));
            if (location != null) {
                contentValues.put("latitude", Double.valueOf(location.getLatitude()));
                contentValues.put("longitude", Double.valueOf(location.getLongitude()));
            }
            contentValues.put("duration", Long.valueOf(e(str)));
            contentValues.put("resolution", i2 + "x" + i3);
            mediaUtil = this;
            uri = mediaUtil.f134d.insert(MediaStore.Video.Media.EXTERNAL_CONTENT_URI, contentValues);
            str2 = "android.hardware.action.NEW_VIDEO";
        } else {
            contentValues.put("_data", file.getPath());
            contentValues.put("_display_name", name);
            contentValues.put("title", substring);
            contentValues.put("width", Integer.valueOf(i2));
            contentValues.put("height", Integer.valueOf(i3));
            contentValues.put("_size", Long.valueOf(file.length()));
            contentValues.put("mime_type", a2);
            contentValues.put("datetaken", Long.valueOf(System.currentTimeMillis()));
            if (location != null) {
                contentValues.put("latitude", Double.valueOf(location.getLatitude()));
                contentValues.put("longitude", Double.valueOf(location.getLongitude()));
            }
            contentValues.put("orientation", Integer.valueOf(i4));
            mediaUtil = this;
            uri = mediaUtil.f134d.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues);
            str2 = "android.hardware.action.NEW_PICTURE";
        }
        if (Build.VERSION.SDK_INT >= 14) {
            mediaUtil.f133c.sendBroadcast(new Intent(str2, uri));
        }
        return uri;
    }

    public boolean deleteMediaFile(String str) {
        MediaFileInfo mediaFileInfo = getMediaFileInfo(str);
        return mediaFileInfo != null && this.f134d.delete(mediaFileInfo.f142b, null, null) > 0;
    }

    public String getLastMediaFilePath() {
        String str;
        Cursor medias = getMedias(this.f134d);
        if (medias == null || medias.getCount() <= 0) {
            str = null;
        } else {
            medias.moveToFirst();
            str = medias.getString(1);
        }
        if (medias != null) {
            medias.close();
        }
        return str;
    }

    public Uri getLastestMediaUri() {
        Uri uri;
        Cursor medias = getMedias(this.f134d);
        if (medias == null || medias.getCount() <= 0) {
            uri = null;
        } else {
            medias.moveToFirst();
            uri = a(medias);
        }
        if (medias != null) {
            medias.close();
        }
        return uri;
    }

    public MediaFileInfo getMediaFileInfo(String str) {
        Cursor cursor;
        MediaFileInfo mediaFileInfo = null;
        if (str == null) {
            return null;
        }
        boolean isVideoFile = isVideoFile(str);
        if (isVideoFile) {
            cursor = this.f134d.query(MediaStore.Video.Media.EXTERNAL_CONTENT_URI, this.i, "_data=?", new String[]{str}, "_id DESC");
        } else {
            cursor = this.f134d.query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, this.j, "_data=?", new String[]{str}, "_id DESC");
        }
        if (cursor != null && cursor.getCount() > 0) {
            cursor.moveToFirst();
            mediaFileInfo = a(cursor, isVideoFile);
        }
        if (cursor != null) {
            cursor.close();
        }
        return mediaFileInfo;
    }

    public List<MediaFileInfo> getMediaFileInfo(String str, boolean z) {
        Cursor cursor;
        if (str == null) {
            return Collections.emptyList();
        }
        if (str.endsWith(File.separator)) {
            str = str.substring(0, str.length() - 1);
        }
        int lastIndexOf = str.lastIndexOf(File.separator);
        if (-1 != lastIndexOf) {
            str = str.substring(lastIndexOf + 1);
        }
        if (z) {
            cursor = this.f134d.query(MediaStore.Video.Media.EXTERNAL_CONTENT_URI, this.i, "bucket_display_name=?", new String[]{str}, "_id ASC");
        } else {
            cursor = this.f134d.query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, this.j, "bucket_display_name=?", new String[]{str}, "_id ASC");
        }
        ArrayList arrayList = null;
        if (cursor != null && cursor.getCount() > 0) {
            arrayList = new ArrayList();
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                arrayList.add(a(cursor, z));
                cursor.moveToNext();
            }
        }
        if (cursor != null) {
            cursor.close();
        }
        return arrayList;
    }

    public Bitmap getMediaThumbnail(String str, BitmapFactory.Options options) {
        MediaFileInfo mediaFileInfo = getMediaFileInfo(str);
        if (mediaFileInfo == null) {
            for (int i2 = 20; i2 > 0; i2--) {
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e2) {
                    e2.printStackTrace();
                }
                MediaFileInfo mediaFileInfo2 = getMediaFileInfo(str);
                if (mediaFileInfo2 != null) {
                    mediaFileInfo = mediaFileInfo2;
                }
            }
            return null;
        }
        return mediaFileInfo.f141a ? MediaStore.Video.Thumbnails.getThumbnail(this.f134d, mediaFileInfo.f143c, 3, options) : MediaStore.Images.Thumbnails.getThumbnail(this.f134d, mediaFileInfo.f143c, 3, options);
    }

    public Bitmap getMediaThumbnailFromFile(String str, float f2) {
        if (str == null) {
            str = getLastMediaFilePath();
        }
        if (str == null) {
            return null;
        }
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = false;
        int i2 = (int) (((float) options.outHeight) / f2);
        if (i2 <= 0) {
            i2 = 1;
        }
        options.inSampleSize = i2;
        return BitmapFactory.decodeFile(str, options);
    }

    public boolean isVideoFile(String str) {
        if (str == null) {
            return false;
        }
        String substring = str.substring(str.lastIndexOf(46));
        return ".3gp".equals(substring) || ".mp4".equals(substring);
    }

    public int readPictureDegree(String str) {
        try {
            int attributeInt = new ExifInterface(str).getAttributeInt(androidx.exifinterface.media.ExifInterface.TAG_ORIENTATION, 1);
            if (attributeInt == 3) {
                return 180;
            }
            if (attributeInt != 6) {
                return attributeInt != 8 ? 0 : 270;
            }
            return 90;
        } catch (IOException e2) {
            e2.printStackTrace();
            return 0;
        }
    }

    public boolean renameMediaFile(String str, String str2) {
        String str3;
        MediaFileInfo mediaFileInfo = getMediaFileInfo(str);
        if (mediaFileInfo == null) {
            return false;
        }
        int lastIndexOf = str2.lastIndexOf(File.separator);
        if (-1 != lastIndexOf) {
            str2 = str2.substring(lastIndexOf + 1);
        }
        int lastIndexOf2 = str2.lastIndexOf(".");
        if (-1 != lastIndexOf2) {
            str2 = str2.substring(0, lastIndexOf2);
            str3 = str2;
        } else {
            str3 = str2 + "." + c(str);
        }
        ContentValues contentValues = new ContentValues();
        if (mediaFileInfo.f141a) {
            contentValues.put("_display_name", str3);
            contentValues.put("title", str2);
        } else {
            contentValues.put("_display_name", str3);
            contentValues.put("title", str2);
        }
        return this.f134d.update(mediaFileInfo.f142b, contentValues, null, null) > 0;
    }
}
