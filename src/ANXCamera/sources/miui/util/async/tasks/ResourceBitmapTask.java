package miui.util.async.tasks;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import miui.util.AppConstants;
import miui.util.async.Cacheable;
import miui.util.async.Task;

public class ResourceBitmapTask extends Task<Bitmap> implements Cacheable {
    private BitmapFactory.Options mDecodeOptions;
    private String mDescription;
    private int mResId;

    public ResourceBitmapTask(int i) {
        this(i, null);
    }

    public ResourceBitmapTask(int i, BitmapFactory.Options options) {
        this.mResId = i;
        this.mDecodeOptions = options;
    }

    @Override // miui.util.async.Task
    public Bitmap doLoad() throws Exception {
        FileBitmapTask.DECODE_LIMITATION.acquireUninterruptibly();
        try {
            return BitmapFactory.decodeResource(AppConstants.getCurrentApplication().getResources(), this.mResId, this.mDecodeOptions);
        } finally {
            FileBitmapTask.DECODE_LIMITATION.release();
        }
    }

    @Override // miui.util.async.Cacheable
    public String getCacheKey() {
        return getDescription();
    }

    @Override // miui.util.async.Task
    public String getDescription() {
        if (this.mDescription == null) {
            this.mDescription = "resource_" + this.mResId;
        }
        return this.mDescription;
    }

    @Override // miui.util.async.Cacheable
    public int sizeOf(Object obj) {
        if (obj instanceof Bitmap) {
            return ((Bitmap) obj).getByteCount();
        }
        return 0;
    }
}
