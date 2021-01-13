package miui.util.async.tasks;

import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import miui.util.async.Cacheable;

public class DatabaseBitmapTask extends ContentResolverQueryTask<Bitmap> implements Cacheable {
    private BitmapFactory.Options mDecodeOptions;

    public DatabaseBitmapTask(Uri uri, String str, String str2, String[] strArr) {
        this(uri, str, str2, strArr, null);
    }

    /* JADX WARNING: Illegal instructions before constructor call */
    public DatabaseBitmapTask(Uri uri, String str, String str2, String[] strArr, BitmapFactory.Options options) {
        super(uri, r4, str2, strArr, null);
        String[] strArr2;
        if (str == null) {
            strArr2 = null;
        } else {
            strArr2 = new String[]{str};
        }
        this.mDecodeOptions = options;
    }

    @Override // miui.util.async.Task
    public Bitmap doLoad() throws Exception {
        Cursor query = query();
        if (query != null) {
            try {
                if (query.moveToNext()) {
                    byte[] blob = query.getBlob(0);
                    if (blob == null) {
                        return null;
                    }
                    FileBitmapTask.DECODE_LIMITATION.acquireUninterruptibly();
                    try {
                        Bitmap decodeByteArray = BitmapFactory.decodeByteArray(blob, 0, blob.length, this.mDecodeOptions);
                        if (query != null) {
                            query.close();
                        }
                        return decodeByteArray;
                    } finally {
                        FileBitmapTask.DECODE_LIMITATION.release();
                    }
                }
            } finally {
                if (query != null) {
                    query.close();
                }
            }
        }
        if (query != null) {
            query.close();
        }
        return null;
    }

    @Override // miui.util.async.Cacheable
    public String getCacheKey() {
        return getDescription();
    }

    @Override // miui.util.async.Cacheable
    public int sizeOf(Object obj) {
        if (obj instanceof Bitmap) {
            return ((Bitmap) obj).getByteCount();
        }
        return 0;
    }
}
