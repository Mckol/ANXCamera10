package miui.net.http;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

/* access modifiers changed from: package-private */
public class DefaultHttpResponse implements HttpResponse {
    private InputStream mContent;
    private String mContentEncoding;
    private long mContentLength;
    private String mContentType;
    private Map<String, String> mHeaders;
    private int mStatusCode;

    public DefaultHttpResponse(int i, Map<String, String> map, InputStream inputStream, long j, String str, String str2) {
        this.mStatusCode = i;
        this.mContent = inputStream;
        this.mContentLength = j;
        this.mContentType = str;
        this.mContentEncoding = str2;
        this.mHeaders = map;
    }

    @Override // miui.net.http.HttpResponse
    public InputStream getContent() {
        return this.mContent;
    }

    @Override // miui.net.http.HttpResponse
    public String getContentEncoding() {
        return this.mContentEncoding;
    }

    @Override // miui.net.http.HttpResponse
    public long getContentLength() {
        return this.mContentLength;
    }

    @Override // miui.net.http.HttpResponse
    public String getContentType() {
        return this.mContentType;
    }

    @Override // miui.net.http.HttpResponse
    public Map<String, String> getHeaders() {
        return this.mHeaders;
    }

    @Override // miui.net.http.HttpResponse
    public int getStatusCode() {
        return this.mStatusCode;
    }

    @Override // miui.net.http.HttpResponse
    public void release() {
        try {
            if (this.mContent != null) {
                this.mContent.close();
            }
        } catch (IOException unused) {
        }
        this.mContent = null;
    }

    public void setContent(InputStream inputStream, long j) {
        this.mContent = inputStream;
        this.mContentLength = j;
    }
}
