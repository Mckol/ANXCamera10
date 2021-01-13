package miui.util.async.tasks;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import java.util.concurrent.Semaphore;
import miui.util.async.Cacheable;
import miui.util.async.Task;

public class FileBitmapTask extends Task<Bitmap> implements Cacheable {
    static final Semaphore DECODE_LIMITATION = new Semaphore(2);
    private BitmapFactory.Options mDecodeOptions;
    private String mFilePath;

    public FileBitmapTask(String str) {
        this(str, null);
    }

    public FileBitmapTask(String str, BitmapFactory.Options options) {
        this.mFilePath = str;
        this.mDecodeOptions = options;
    }

    @Override // miui.util.async.Task
    public Bitmap doLoad() throws Exception {
        DECODE_LIMITATION.acquireUninterruptibly();
        try {
            return BitmapFactory.decodeFile(this.mFilePath, this.mDecodeOptions);
        } finally {
            DECODE_LIMITATION.release();
        }
    }

    @Override // miui.util.async.Cacheable
    public String getCacheKey() {
        return this.mFilePath;
    }

    @Override // miui.util.async.Task
    public String getDescription() {
        return this.mFilePath;
    }

    @Override // miui.util.async.Cacheable
    public int sizeOf(Object obj) {
        if (obj instanceof Bitmap) {
            return ((Bitmap) obj).getByteCount();
        }
        return 0;
    }
}
