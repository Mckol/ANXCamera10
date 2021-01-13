package miui.util.async.tasks;

import java.util.Map;
import miui.net.http.HttpRequestParams;
import miui.net.http.HttpResponse;
import miui.net.http.HttpSession;
import miui.util.async.Task;

public abstract class HttpTask<T> extends Task<T> {
    private final Method mMethod;
    private final HttpRequestParams mParams;
    private HttpSession.ProgressListener mProgressListener;
    private final HttpSession mSession;
    private final String mUrl;

    /* renamed from: miui.util.async.tasks.HttpTask$2  reason: invalid class name */
    static /* synthetic */ class AnonymousClass2 {
        static final /* synthetic */ int[] $SwitchMap$miui$util$async$tasks$HttpTask$Method = new int[Method.values().length];

        /* JADX WARNING: Can't wrap try/catch for region: R(6:0|1|2|3|4|6) */
        /* JADX WARNING: Code restructure failed: missing block: B:7:?, code lost:
            return;
         */
        /* JADX WARNING: Failed to process nested try/catch */
        /* JADX WARNING: Missing exception handler attribute for start block: B:3:0x0014 */
        static {
            $SwitchMap$miui$util$async$tasks$HttpTask$Method[Method.Get.ordinal()] = 1;
            $SwitchMap$miui$util$async$tasks$HttpTask$Method[Method.Post.ordinal()] = 2;
        }
    }

    public enum Method {
        Get,
        Post
    }

    public HttpTask(String str) {
        this(null, Method.Get, str, null);
    }

    public HttpTask(HttpSession httpSession, String str) {
        this(httpSession, Method.Get, str, null);
    }

    public HttpTask(HttpSession httpSession, Method method, String str, Map<String, String> map) {
        String str2;
        this.mSession = httpSession == null ? HttpSession.getDefault() : httpSession;
        this.mMethod = method;
        if (method == Method.Get) {
            if (map == null || map.size() <= 0) {
                this.mUrl = str;
            } else {
                HttpRequestParams httpRequestParams = new HttpRequestParams(map);
                if (str.indexOf(63) >= 0) {
                    str2 = str + "&" + httpRequestParams.getParamString();
                } else {
                    str2 = str + "?" + httpRequestParams.getParamString();
                }
                this.mUrl = str2;
            }
            this.mParams = null;
            return;
        }
        this.mUrl = str;
        this.mParams = new HttpRequestParams();
        this.mParams.add(map);
    }

    @Override // miui.util.async.Task
    public String getDescription() {
        return this.mUrl;
    }

    /* access modifiers changed from: protected */
    public final HttpResponse request() throws Exception {
        if (this.mProgressListener == null) {
            this.mProgressListener = new HttpSession.ProgressListener() {
                /* class miui.util.async.tasks.HttpTask.AnonymousClass1 */

                @Override // miui.net.http.HttpSession.ProgressListener
                public void onProgress(long j, long j2) {
                    if (j > 2147483647L) {
                        long j3 = (long) ((int) (((float) (j * 2147483647L)) / 9.223372E18f));
                        j = j3;
                        j2 = (long) ((int) (((float) (2147483647L * j3)) / 9.223372E18f));
                    }
                    HttpTask.this.publishProgress((int) j, (int) j2);
                }
            };
        }
        int i = AnonymousClass2.$SwitchMap$miui$util$async$tasks$HttpTask$Method[this.mMethod.ordinal()];
        if (i == 1) {
            return this.mSession.get(this.mUrl, null, this.mParams, this.mProgressListener);
        }
        if (i != 2) {
            return null;
        }
        return this.mSession.post(this.mUrl, null, this.mParams, this.mProgressListener);
    }
}
