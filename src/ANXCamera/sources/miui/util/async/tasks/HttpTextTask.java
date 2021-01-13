package miui.util.async.tasks;

import java.nio.charset.Charset;
import java.util.Map;
import miui.net.http.HttpResponse;
import miui.net.http.HttpSession;
import miui.util.IOUtils;
import miui.util.async.tasks.HttpTask;

public abstract class HttpTextTask<T> extends HttpTask<T> {
    public HttpTextTask(String str) {
        this(null, HttpTask.Method.Get, str, null);
    }

    public HttpTextTask(HttpSession httpSession, String str) {
        this(httpSession, HttpTask.Method.Get, str, null);
    }

    public HttpTextTask(HttpSession httpSession, HttpTask.Method method, String str, Map<String, String> map) {
        super(httpSession, method, str, map);
    }

    static String guessEncoding(byte[] bArr) {
        String str;
        String upperCase;
        int indexOf;
        int i = 500;
        if (bArr.length <= 500) {
            i = bArr.length;
        }
        String upperCase2 = new String(bArr, 0, i, Charset.forName("UTF-8")).toUpperCase();
        if (upperCase2.indexOf("CONTENT-TYPE") >= 0) {
            int lastIndexOf = upperCase2.lastIndexOf(10);
            if (lastIndexOf < 0) {
                lastIndexOf = 0;
            }
            int indexOf2 = upperCase2.indexOf(10);
            if (indexOf2 < 0) {
                indexOf2 = upperCase2.length();
            }
            str = upperCase2.substring(lastIndexOf, indexOf2);
        } else {
            str = null;
        }
        if (str == null || (indexOf = (upperCase = str.toUpperCase()).indexOf("CHARSET=")) < 0) {
            return "UTF-8";
        }
        StringBuilder sb = new StringBuilder();
        for (int i2 = indexOf + 8; i2 < upperCase.length(); i2++) {
            char charAt = upperCase.charAt(i2);
            if (charAt == '\'' || charAt == '\"' || charAt == ' ') {
                sb.append(charAt);
            }
        }
        return sb.toString();
    }

    /* access modifiers changed from: protected */
    public final String requestText() throws Exception {
        String str;
        HttpResponse request = request();
        String contentEncoding = request.getContentEncoding();
        if (contentEncoding == null || contentEncoding.length() <= 0) {
            byte[] byteArray = IOUtils.toByteArray(request.getContent());
            str = new String(byteArray, Charset.forName(guessEncoding(byteArray)));
        } else {
            str = IOUtils.toString(request.getContent(), contentEncoding.toUpperCase());
        }
        request.release();
        return str;
    }
}
