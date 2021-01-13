package miui.util.async.tasks;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import java.util.Map;
import miui.net.http.HttpResponse;
import miui.net.http.HttpSession;
import miui.util.async.Cacheable;
import miui.util.async.tasks.HttpTask;

public class HttpBitmapTask extends HttpTask<Bitmap> implements Cacheable {
    private BitmapFactory.Options mDecodeOptions;

    public HttpBitmapTask(String str) {
        this(null, HttpTask.Method.Get, str, null, null);
    }

    public HttpBitmapTask(HttpSession httpSession, String str) {
        this(httpSession, HttpTask.Method.Get, str, null, null);
    }

    public HttpBitmapTask(HttpSession httpSession, HttpTask.Method method, String str, Map<String, String> map) {
        this(httpSession, method, str, map, null);
    }

    public HttpBitmapTask(HttpSession httpSession, HttpTask.Method method, String str, Map<String, String> map, BitmapFactory.Options options) {
        super(httpSession, method, str, map);
        this.mDecodeOptions = options;
    }

    @Override // miui.util.async.Task
    public Bitmap doLoad() throws Exception {
        HttpResponse request = request();
        FileBitmapTask.DECODE_LIMITATION.acquireUninterruptibly();
        try {
            return BitmapFactory.decodeStream(request.getContent(), null, this.mDecodeOptions);
        } finally {
            request.release();
            FileBitmapTask.DECODE_LIMITATION.release();
        }
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
