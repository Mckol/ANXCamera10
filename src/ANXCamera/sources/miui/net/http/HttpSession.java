package miui.net.http;

import com.ss.android.vesdk.runtime.cloudconfig.HttpRequest;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FilterInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.RandomAccessFile;
import java.net.HttpURLConnection;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.zip.GZIPInputStream;
import miui.net.http.Cache;
import miui.util.IOUtils;
import miui.util.Log;

public class HttpSession {
    private static final int DEFAULT_SOCKET_BUFFER_SIZE = 8192;
    private static final int DEFAULT_SOCKET_TIMEOUT = 10000;
    private static final String ENCODING_GZIP = "gzip";
    private static final String HEADER_ACCEPT_ENCODING = "Accept-Encoding";
    private static final String TAG = "HttpSession";
    private Cache mCache = DiskBasedCache.getDefault();
    private final Map<String, String> mClientHeaders = new HashMap();
    private String mClientParams;
    private RetryStrategy mRetryStrategy = new BaseRetryStrategy();
    private int mTimeout;

    /* access modifiers changed from: private */
    public static class CountingInputStream extends FilterInputStream {
        private long mContentLength;
        private long mPercentage = 0;
        private ProgressListener mProgressListener;
        private long mRead = 0;

        public CountingInputStream(InputStream inputStream, long j, String str, ProgressListener progressListener) throws IOException {
            super(inputStream);
            this.mContentLength = j;
            this.mProgressListener = progressListener;
            if (str != null && str.length() > 0) {
                Matcher matcher = Pattern.compile("bytes\\s+(\\d+)-(\\d+)/(\\d+)").matcher(str);
                if (matcher.matches() && matcher.groupCount() == 3) {
                    this.mRead = Long.parseLong(matcher.group(1));
                    this.mContentLength = Long.parseLong(matcher.group(3));
                }
            }
        }

        private void reportProgress(int i) {
            long j = this.mContentLength;
            if (j > 0 && this.mProgressListener != null) {
                long j2 = (this.mRead * 10) / j;
                if (this.mPercentage != j2 || i > 1024) {
                    this.mPercentage = j2;
                    this.mProgressListener.onProgress(this.mContentLength, this.mRead);
                }
            }
        }

        @Override // java.io.FilterInputStream, java.io.InputStream
        public int read() throws IOException {
            int read = super.read();
            if (read > 0) {
                this.mRead++;
                reportProgress(1);
            }
            return read;
        }

        @Override // java.io.FilterInputStream, java.io.InputStream
        public int read(byte[] bArr) throws IOException {
            return read(bArr, 0, bArr.length);
        }

        @Override // java.io.FilterInputStream, java.io.InputStream
        public int read(byte[] bArr, int i, int i2) throws IOException {
            int read = super.read(bArr, i, i2);
            if (read > 0) {
                this.mRead += (long) read;
                reportProgress(read);
            }
            return read;
        }
    }

    public interface ProgressListener {
        void onProgress(long j, long j2);
    }

    private void addCacheHeaders(Cache.Entry entry) {
        String str = entry.etag;
        if (str != null) {
            addHeader(HttpRequest.HEADER_IF_NONE_MATCH, str);
        }
        long j = entry.serverDate;
        if (j > 0) {
            addHeader("If-Modified-Since", DateUtils.formatDate(new Date(j)));
        }
    }

    private void addRequestHeaders(Map<String, String> map) {
        if (map != null && map.size() > 0) {
            for (Map.Entry<String, String> entry : map.entrySet()) {
                this.mClientHeaders.put(entry.getKey(), entry.getValue());
            }
        }
    }

    private static Map<String, String> convertHeaders(Map<String, List<String>> map) {
        HashMap hashMap = new HashMap();
        if (map != null) {
            for (String str : map.keySet()) {
                String str2 = map.get(str).get(0);
                if (!(str == null || str2 == null)) {
                    hashMap.put(str.toLowerCase(), str2.toLowerCase());
                }
            }
        }
        return hashMap;
    }

    private DefaultHttpResponse executeGet(String str, Cache.Entry entry, ProgressListener progressListener) throws IOException {
        String str2;
        FilterInputStream filterInputStream;
        HttpURLConnection httpURLConnection = (HttpURLConnection) new URL(str).openConnection();
        httpURLConnection.setDoInput(true);
        httpURLConnection.setReadTimeout(this.mTimeout);
        httpURLConnection.setConnectTimeout(this.mTimeout);
        Map<String, String> map = this.mClientHeaders;
        if (map != null && map.keySet().size() > 0) {
            for (String str3 : this.mClientHeaders.keySet()) {
                httpURLConnection.setRequestProperty(str3, this.mClientHeaders.get(str3));
            }
        }
        httpURLConnection.connect();
        int responseCode = httpURLConnection.getResponseCode();
        if (responseCode == 304) {
            return new DefaultHttpResponse(200, entry.responseHeaders, entry.data, entry.length, entry.contentType, entry.contentEncoding);
        }
        if (responseCode < 200 || responseCode > 299) {
            throw new IOException(httpURLConnection.getResponseMessage());
        }
        long contentLength = (long) httpURLConnection.getContentLength();
        String contentType = httpURLConnection.getContentType();
        String contentEncoding = httpURLConnection.getContentEncoding();
        InputStream inputStream = httpURLConnection.getInputStream();
        Map<String, String> convertHeaders = convertHeaders(httpURLConnection.getHeaderFields());
        FilterInputStream countingInputStream = new CountingInputStream(inputStream, contentLength, httpURLConnection.getHeaderField("content-range"), progressListener);
        if (contentType != null) {
            contentType = contentType.toLowerCase();
        }
        if (contentEncoding != null) {
            String lowerCase = contentEncoding.toLowerCase();
            if (lowerCase.equalsIgnoreCase("gzip")) {
                filterInputStream = new GZIPInputStream(countingInputStream);
                str2 = "";
                DefaultHttpResponse defaultHttpResponse = new DefaultHttpResponse(responseCode, convertHeaders, filterInputStream, contentLength, contentType, str2);
                putCacheEntry(httpURLConnection.getURL().toURI().toString(), defaultHttpResponse);
                return defaultHttpResponse;
            }
            str2 = lowerCase;
        } else {
            str2 = contentEncoding;
        }
        filterInputStream = countingInputStream;
        DefaultHttpResponse defaultHttpResponse2 = new DefaultHttpResponse(responseCode, convertHeaders, filterInputStream, contentLength, contentType, str2);
        try {
            putCacheEntry(httpURLConnection.getURL().toURI().toString(), defaultHttpResponse2);
        } catch (URISyntaxException e2) {
            Log.d(TAG, e2.getMessage());
        }
        return defaultHttpResponse2;
    }

    /* JADX WARNING: Code restructure failed: missing block: B:20:0x005a, code lost:
        return executeGet(r10, r11, r12);
     */
    private DefaultHttpResponse executeGet(String str, HttpRequestParams httpRequestParams, ProgressListener progressListener) throws IOException {
        Cache.Entry cacheEntry = getCacheEntry(getUrlWithQueryString(str, httpRequestParams));
        if (cacheEntry == null || cacheEntry.softTtl <= System.currentTimeMillis()) {
            if (progressListener != null) {
                progressListener.onProgress(-1, -1);
            }
            if (cacheEntry != null) {
                addCacheHeaders(cacheEntry);
            }
            if (!this.mClientHeaders.containsKey("Accept-Encoding")) {
                this.mClientHeaders.put("Accept-Encoding", "gzip");
            }
            RetryStrategy retryStrategy = this.mRetryStrategy;
            while (true) {
                if (retryStrategy == null) {
                    break;
                }
                try {
                    setTimeout(retryStrategy.getCurrentTimeout());
                    break;
                } catch (IOException e2) {
                    if (retryStrategy == null || !retryStrategy.retry(e2)) {
                        throw e2;
                    }
                } catch (NullPointerException e3) {
                    if (retryStrategy == null || !retryStrategy.retry(e3)) {
                        throw e3;
                    }
                }
            }
            throw e3;
        }
        DefaultHttpResponse defaultHttpResponse = new DefaultHttpResponse(200, cacheEntry.responseHeaders, cacheEntry.data, cacheEntry.length, cacheEntry.contentType, cacheEntry.contentEncoding);
        if (progressListener != null) {
            long j = cacheEntry.length;
            progressListener.onProgress(j, j);
        }
        return defaultHttpResponse;
    }

    private Cache.Entry getCacheEntry(String str) {
        Cache cache = this.mCache;
        if (cache == null) {
            return null;
        }
        return cache.get(str.toString());
    }

    public static HttpSession getDefault() {
        return new HttpSession();
    }

    private static String getUrlWithQueryString(String str, HttpRequestParams httpRequestParams) {
        String paramString;
        if (httpRequestParams == null || (paramString = httpRequestParams.getParamString()) == null || paramString.length() <= 0) {
            return str;
        }
        if (str.indexOf(63) >= 0) {
            return str + "?" + paramString;
        }
        return str + "&" + paramString;
    }

    private void putCacheEntry(String str, DefaultHttpResponse defaultHttpResponse) {
        Cache.Entry parseCacheHeaders;
        Cache cache = this.mCache;
        if (cache != null && (parseCacheHeaders = HttpHeaderParser.parseCacheHeaders(defaultHttpResponse)) != null && cache.put(str, parseCacheHeaders)) {
            defaultHttpResponse.setContent(parseCacheHeaders.data, parseCacheHeaders.length);
        }
    }

    public void addHeader(String str, String str2) {
        this.mClientHeaders.put(str, str2);
    }

    public void clearCacheContent() {
        Cache cache = this.mCache;
        if (cache != null) {
            cache.clear();
        }
    }

    public HttpResponse delete(String str, Map<String, String> map, HttpRequestParams httpRequestParams, ProgressListener progressListener) throws IOException {
        throw new UnsupportedOperationException();
    }

    public void download(File file, String str, Map<String, String> map, HttpRequestParams httpRequestParams, ProgressListener progressListener) throws IOException {
        FileOutputStream fileOutputStream;
        Throwable th;
        addRequestHeaders(map);
        if (httpRequestParams != null) {
            this.mClientParams = httpRequestParams.getParamString();
        }
        long j = 0;
        if (file.exists()) {
            j = file.length();
        }
        addHeader("RANGE", "bytes=" + j + "-");
        DefaultHttpResponse executeGet = executeGet(str, httpRequestParams, progressListener);
        RandomAccessFile randomAccessFile = null;
        try {
            String str2 = executeGet.getHeaders().get("content-range");
            if (str2 != null) {
                if (str2.startsWith("bytes " + j)) {
                    RandomAccessFile randomAccessFile2 = new RandomAccessFile(file, "rw");
                    try {
                        randomAccessFile2.seek(j);
                        byte[] bArr = new byte[4096];
                        while (true) {
                            int read = executeGet.getContent().read(bArr);
                            if (read == -1) {
                                break;
                            }
                            randomAccessFile2.write(bArr, 0, read);
                        }
                        randomAccessFile2.close();
                        fileOutputStream = null;
                        randomAccessFile = randomAccessFile2;
                        IOUtils.closeQuietly(randomAccessFile);
                        IOUtils.closeQuietly((OutputStream) fileOutputStream);
                        executeGet.release();
                    } catch (Throwable th2) {
                        th = th2;
                        fileOutputStream = null;
                        randomAccessFile = randomAccessFile2;
                        IOUtils.closeQuietly(randomAccessFile);
                        IOUtils.closeQuietly((OutputStream) fileOutputStream);
                        executeGet.release();
                        throw th;
                    }
                }
            }
            fileOutputStream = new FileOutputStream(file);
            try {
                IOUtils.copy(executeGet.getContent(), fileOutputStream);
                fileOutputStream.close();
                IOUtils.closeQuietly(randomAccessFile);
                IOUtils.closeQuietly((OutputStream) fileOutputStream);
                executeGet.release();
            } catch (Throwable th3) {
                th = th3;
                IOUtils.closeQuietly(randomAccessFile);
                IOUtils.closeQuietly((OutputStream) fileOutputStream);
                executeGet.release();
                throw th;
            }
        } catch (Throwable th4) {
            th = th4;
            fileOutputStream = null;
            IOUtils.closeQuietly(randomAccessFile);
            IOUtils.closeQuietly((OutputStream) fileOutputStream);
            executeGet.release();
            throw th;
        }
    }

    public HttpResponse get(String str, Map<String, String> map, HttpRequestParams httpRequestParams, ProgressListener progressListener) throws IOException {
        addRequestHeaders(map);
        if (httpRequestParams != null) {
            this.mClientParams = httpRequestParams.getParamString();
        }
        return executeGet(str, httpRequestParams, progressListener);
    }

    public HttpResponse post(String str, Map<String, String> map, HttpRequestParams httpRequestParams, ProgressListener progressListener) throws IOException {
        throw new UnsupportedOperationException();
    }

    public HttpResponse put(String str, Map<String, String> map, HttpRequestParams httpRequestParams, ProgressListener progressListener) throws IOException {
        throw new UnsupportedOperationException();
    }

    public void removeCacheContent(String str) {
        Cache cache = this.mCache;
        if (cache != null) {
            cache.remove(str);
        }
    }

    public void setBasicAuth(String str, String str2) {
        throw new UnsupportedOperationException();
    }

    public void setCache(Cache cache) {
        if (this.mCache != cache) {
            this.mCache = cache;
        }
    }

    public void setRetryStrategy(RetryStrategy retryStrategy) {
        this.mRetryStrategy = retryStrategy;
    }

    public void setTimeout(int i) {
        this.mTimeout = i;
    }

    public void setUserAgent(String str) {
        throw new UnsupportedOperationException();
    }
}
