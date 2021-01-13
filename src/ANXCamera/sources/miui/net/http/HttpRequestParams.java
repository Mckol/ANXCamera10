package miui.net.http;

import android.text.TextUtils;
import android.util.Pair;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class HttpRequestParams {
    public static String DEFAULT_ENCODING = "UTF-8";
    private String mEncoding;
    private boolean mHasStream;
    private ConcurrentHashMap<String, Object> mUrlParams;

    /* access modifiers changed from: package-private */
    public static class FileWrapper {
        public long contentLength;
        public String contentType;
        public String fileName;
        public InputStream inputStream;

        public FileWrapper(InputStream inputStream2, long j, String str, String str2) {
            this.inputStream = inputStream2;
            this.contentLength = j;
            this.fileName = str == null ? "nofilename" : str;
            this.contentType = str2;
        }
    }

    public HttpRequestParams() {
        this.mEncoding = DEFAULT_ENCODING;
        this.mUrlParams = new ConcurrentHashMap<>();
        this.mHasStream = false;
    }

    public HttpRequestParams(Object... objArr) {
        this();
        int length = objArr.length;
        if (length % 2 == 0) {
            for (int i = 0; i < length; i += 2) {
                if (objArr[i] instanceof String) {
                    String str = (String) objArr[i];
                    Object obj = objArr[i + 1];
                    if (obj instanceof String) {
                        add(str, (String) obj);
                    } else if (obj instanceof File) {
                        add(str, (File) obj);
                    } else if (obj instanceof List) {
                        add(str, (List) obj);
                    } else {
                        throw new IllegalArgumentException("Unknown argument type : " + obj);
                    }
                } else {
                    throw new IllegalArgumentException("Unknown argument name : " + objArr[i]);
                }
            }
            return;
        }
        throw new IllegalArgumentException("Supplied argument must be even");
    }

    private List<Pair<String, String>> getParamsList() {
        LinkedList linkedList = new LinkedList();
        for (Map.Entry<String, Object> entry : this.mUrlParams.entrySet()) {
            Object value = entry.getValue();
            if (value instanceof String) {
                linkedList.add(new Pair(entry.getKey(), (String) value));
            } else if (value instanceof List) {
                for (String str : (List) value) {
                    linkedList.add(new Pair(entry.getKey(), str));
                }
            }
        }
        return linkedList;
    }

    public HttpRequestParams add(String str, File file) {
        if (!(str == null || file == null)) {
            try {
                this.mUrlParams.put(str, new FileWrapper(new FileInputStream(file), file.length(), file.getName(), null));
                this.mHasStream = true;
            } catch (FileNotFoundException e2) {
                throw new IllegalArgumentException(e2.getMessage(), e2);
            }
        }
        return this;
    }

    public HttpRequestParams add(String str, File file, String str2) {
        if (!(str == null || file == null)) {
            try {
                this.mUrlParams.put(str, new FileWrapper(new FileInputStream(file), file.length(), file.getName(), str2));
                this.mHasStream = true;
            } catch (FileNotFoundException e2) {
                throw new IllegalArgumentException(e2.getMessage(), e2);
            }
        }
        return this;
    }

    public HttpRequestParams add(String str, InputStream inputStream, long j, String str2) {
        if (!(str == null || inputStream == null || j < 0)) {
            this.mUrlParams.put(str, new FileWrapper(inputStream, j, str2, null));
            this.mHasStream = true;
        }
        return this;
    }

    public HttpRequestParams add(String str, InputStream inputStream, long j, String str2, String str3) {
        if (!(str == null || inputStream == null || j < 0)) {
            this.mUrlParams.put(str, new FileWrapper(inputStream, j, str2, str3));
            this.mHasStream = true;
        }
        return this;
    }

    public HttpRequestParams add(String str, String str2) {
        if (!(str == null || str2 == null)) {
            this.mUrlParams.put(str, str2);
        }
        return this;
    }

    public HttpRequestParams add(String str, List<String> list) {
        if (!(str == null || list == null || list.size() <= 0)) {
            this.mUrlParams.put(str, list);
        }
        return this;
    }

    public HttpRequestParams add(Map<String, String> map) {
        for (Map.Entry<String, String> entry : map.entrySet()) {
            add(entry.getKey(), entry.getValue());
        }
        return this;
    }

    public String getParamString() {
        StringBuilder sb = new StringBuilder();
        for (Pair<String, String> pair : getParamsList()) {
            if (!TextUtils.isEmpty(sb)) {
                sb.append("&");
            }
            try {
                sb.append(URLEncoder.encode((String) pair.first, this.mEncoding));
                sb.append("=");
                sb.append(URLEncoder.encode((String) pair.second, this.mEncoding));
            } catch (UnsupportedEncodingException e2) {
                e2.printStackTrace();
            }
        }
        return sb.toString();
    }

    public HttpRequestParams remove(String str) {
        this.mUrlParams.remove(str);
        return this;
    }

    public HttpRequestParams setEncoding(String str) {
        if (str == null) {
            str = DEFAULT_ENCODING;
        }
        this.mEncoding = str;
        return this;
    }
}
