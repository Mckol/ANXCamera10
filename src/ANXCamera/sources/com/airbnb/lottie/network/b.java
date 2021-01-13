package com.airbnb.lottie.network;

import android.content.Context;
import androidx.annotation.Nullable;
import androidx.annotation.WorkerThread;
import androidx.core.util.Pair;
import com.airbnb.lottie.C0035m;
import com.airbnb.lottie.C0047y;
import com.airbnb.lottie.X;
import com.airbnb.lottie.d.d;
import com.ss.android.vesdk.runtime.cloudconfig.HttpRequest;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.zip.ZipInputStream;

/* compiled from: NetworkFetcher */
public class b {
    private final Context sf;
    private final a uf;
    private final String url;

    private b(Context context, String str) {
        this.sf = context.getApplicationContext();
        this.url = str;
        this.uf = new a(this.sf, str);
    }

    @Nullable
    @WorkerThread
    private C0035m Zo() {
        Pair<FileExtension, InputStream> fetch = this.uf.fetch();
        if (fetch == null) {
            return null;
        }
        F f = fetch.first;
        S s = fetch.second;
        X<C0035m> b2 = f == FileExtension.ZIP ? C0047y.b(new ZipInputStream(s), this.url) : C0047y.c((InputStream) s, this.url);
        if (b2.getValue() != null) {
            return b2.getValue();
        }
        return null;
    }

    @WorkerThread
    private X<C0035m> _o() {
        try {
            return ap();
        } catch (IOException e2) {
            return new X<>(e2);
        }
    }

    @WorkerThread
    private X ap() throws IOException {
        d.debug("Fetching " + this.url);
        HttpURLConnection httpURLConnection = (HttpURLConnection) new URL(this.url).openConnection();
        httpURLConnection.setRequestMethod("GET");
        try {
            httpURLConnection.connect();
            if (httpURLConnection.getErrorStream() == null) {
                if (httpURLConnection.getResponseCode() == 200) {
                    X<C0035m> c2 = c(httpURLConnection);
                    StringBuilder sb = new StringBuilder();
                    sb.append("Completed fetch from network. Success: ");
                    sb.append(c2.getValue() != null);
                    d.debug(sb.toString());
                    httpURLConnection.disconnect();
                    return c2;
                }
            }
            String b2 = b(httpURLConnection);
            return new X((Throwable) new IllegalArgumentException("Unable to fetch " + this.url + ". Failed with " + httpURLConnection.getResponseCode() + "\n" + b2));
        } catch (Exception e2) {
            return new X((Throwable) e2);
        } finally {
            httpURLConnection.disconnect();
        }
    }

    private String b(HttpURLConnection httpURLConnection) throws IOException {
        httpURLConnection.getResponseCode();
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(httpURLConnection.getErrorStream()));
        StringBuilder sb = new StringBuilder();
        while (true) {
            try {
                String readLine = bufferedReader.readLine();
                if (readLine != null) {
                    sb.append(readLine);
                    sb.append('\n');
                } else {
                    try {
                        break;
                    } catch (Exception unused) {
                    }
                }
            } catch (Exception e2) {
                throw e2;
            } catch (Throwable th) {
                try {
                    bufferedReader.close();
                } catch (Exception unused2) {
                }
                throw th;
            }
        }
        bufferedReader.close();
        return sb.toString();
    }

    @Nullable
    private X<C0035m> c(HttpURLConnection httpURLConnection) throws IOException {
        X<C0035m> x;
        FileExtension fileExtension;
        String contentType = httpURLConnection.getContentType();
        if (contentType == null) {
            contentType = HttpRequest.CONTENT_TYPE_JSON;
        }
        if (contentType.contains("application/zip")) {
            d.debug("Handling zip response.");
            fileExtension = FileExtension.ZIP;
            x = C0047y.b(new ZipInputStream(new FileInputStream(this.uf.a(httpURLConnection.getInputStream(), fileExtension))), this.url);
        } else {
            d.debug("Received json response.");
            fileExtension = FileExtension.JSON;
            x = C0047y.c(new FileInputStream(new File(this.uf.a(httpURLConnection.getInputStream(), fileExtension).getAbsolutePath())), this.url);
        }
        if (x.getValue() != null) {
            this.uf.a(fileExtension);
        }
        return x;
    }

    public static X<C0035m> l(Context context, String str) {
        return new b(context, str).rc();
    }

    @WorkerThread
    public X<C0035m> rc() {
        C0035m Zo = Zo();
        if (Zo != null) {
            return new X<>(Zo);
        }
        d.debug("Animation for " + this.url + " not found in cache. Fetching from network.");
        return _o();
    }
}
