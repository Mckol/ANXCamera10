package com.xiaomi.stat.d;

import android.text.TextUtils;
import com.ss.android.vesdk.runtime.cloudconfig.HttpRequest;
import com.xiaomi.stat.ak;
import com.xiaomi.stat.d;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Map;

public class i {

    /* renamed from: a  reason: collision with root package name */
    public static final int f608a = 10000;

    /* renamed from: b  reason: collision with root package name */
    public static final int f609b = 15000;

    /* renamed from: c  reason: collision with root package name */
    private static final String f610c = "GET";

    /* renamed from: d  reason: collision with root package name */
    private static final String f611d = "POST";

    /* renamed from: e  reason: collision with root package name */
    private static final String f612e = "&";
    private static final String f = "=";
    private static final String g = "UTF-8";

    private i() {
    }

    public static String a(String str) throws IOException {
        return a(str, null, false);
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r0v2, types: [java.io.OutputStream] */
    /* JADX WARN: Type inference failed for: r0v13 */
    private static String a(String str, String str2, Map<String, String> map, boolean z) {
        HttpURLConnection httpURLConnection;
        Throwable th;
        OutputStream outputStream;
        IOException e2;
        InputStream inputStream;
        OutputStream outputStream2;
        InputStream inputStream2;
        String str3;
        String str4;
        OutputStream outputStream3;
        InputStream inputStream3 = null;
        if (map == null) {
            str3 = null;
        } else {
            try {
                str3 = a(map, z);
            } catch (IOException e3) {
                e2 = e3;
                httpURLConnection = null;
                inputStream2 = null;
                inputStream = inputStream2;
                outputStream2 = inputStream2;
                try {
                    k.e(String.format("HttpUtil %s failed, url: %s, error: %s", str, str2, e2.getMessage()));
                    j.a(inputStream);
                    j.a((OutputStream) outputStream2);
                    j.a(httpURLConnection);
                    return null;
                } catch (Throwable th2) {
                    th = th2;
                    inputStream3 = inputStream;
                    outputStream = outputStream2;
                    j.a(inputStream3);
                    j.a(outputStream);
                    j.a(httpURLConnection);
                    throw th;
                }
            } catch (Throwable th3) {
                th = th3;
                httpURLConnection = null;
                outputStream = null;
                j.a(inputStream3);
                j.a(outputStream);
                j.a(httpURLConnection);
                throw th;
            }
        }
        if (!"GET".equals(str) || str3 == null) {
            str4 = str2;
        } else {
            str4 = str2 + "? " + str3;
        }
        httpURLConnection = (HttpURLConnection) new URL(str4).openConnection();
        try {
            httpURLConnection.setConnectTimeout(10000);
            httpURLConnection.setReadTimeout(15000);
            if ("GET".equals(str)) {
                httpURLConnection.setRequestMethod("GET");
            } else if ("POST".equals(str) && str3 != null) {
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setRequestProperty("Content-Type", HttpRequest.CONTENT_TYPE_FORM);
                httpURLConnection.setDoOutput(true);
                byte[] bytes = str3.getBytes("UTF-8");
                OutputStream outputStream4 = httpURLConnection.getOutputStream();
                try {
                    outputStream4.write(bytes, 0, bytes.length);
                    outputStream4.flush();
                    outputStream3 = outputStream4;
                    int responseCode = httpURLConnection.getResponseCode();
                    inputStream = httpURLConnection.getInputStream();
                    byte[] b2 = j.b(inputStream);
                    k.b(String.format("HttpUtil %s succeed url: %s, code: %s", str, str2, Integer.valueOf(responseCode)));
                    String str5 = new String(b2, "UTF-8");
                    j.a(inputStream);
                    j.a(outputStream3);
                    j.a(httpURLConnection);
                    return str5;
                } catch (IOException e4) {
                    e2 = e4;
                    inputStream = null;
                    outputStream2 = outputStream4;
                    k.e(String.format("HttpUtil %s failed, url: %s, error: %s", str, str2, e2.getMessage()));
                    j.a(inputStream);
                    j.a((OutputStream) outputStream2);
                    j.a(httpURLConnection);
                    return null;
                } catch (Throwable th4) {
                    th = th4;
                    outputStream = outputStream4;
                    j.a(inputStream3);
                    j.a(outputStream);
                    j.a(httpURLConnection);
                    throw th;
                }
            }
            outputStream3 = null;
            int responseCode2 = httpURLConnection.getResponseCode();
            inputStream = httpURLConnection.getInputStream();
            try {
                byte[] b22 = j.b(inputStream);
                k.b(String.format("HttpUtil %s succeed url: %s, code: %s", str, str2, Integer.valueOf(responseCode2)));
                String str52 = new String(b22, "UTF-8");
                j.a(inputStream);
                j.a(outputStream3);
                j.a(httpURLConnection);
                return str52;
            } catch (IOException e5) {
                e2 = e5;
                outputStream2 = outputStream3;
                k.e(String.format("HttpUtil %s failed, url: %s, error: %s", str, str2, e2.getMessage()));
                j.a(inputStream);
                j.a((OutputStream) outputStream2);
                j.a(httpURLConnection);
                return null;
            }
        } catch (IOException e6) {
            e2 = e6;
            inputStream2 = null;
            inputStream = inputStream2;
            outputStream2 = inputStream2;
            k.e(String.format("HttpUtil %s failed, url: %s, error: %s", str, str2, e2.getMessage()));
            j.a(inputStream);
            j.a((OutputStream) outputStream2);
            j.a(httpURLConnection);
            return null;
        } catch (Throwable th5) {
            th = th5;
            outputStream = null;
            j.a(inputStream3);
            j.a(outputStream);
            j.a(httpURLConnection);
            throw th;
        }
    }

    public static String a(String str, Map<String, String> map) throws IOException {
        return a(str, map, true);
    }

    public static String a(String str, Map<String, String> map, boolean z) throws IOException {
        return a("GET", str, map, z);
    }

    public static String a(Map<String, String> map) {
        StringBuilder sb = new StringBuilder();
        if (map != null) {
            ArrayList<String> arrayList = new ArrayList(map.keySet());
            Collections.sort(arrayList);
            for (String str : arrayList) {
                if (!TextUtils.isEmpty(str)) {
                    sb.append(str);
                    sb.append(map.get(str));
                }
            }
        }
        sb.append(ak.c());
        return g.c(sb.toString());
    }

    private static String a(Map<String, String> map, boolean z) {
        StringBuilder sb = new StringBuilder();
        for (Map.Entry<String, String> entry : map.entrySet()) {
            try {
                if (!TextUtils.isEmpty(entry.getKey())) {
                    if (sb.length() > 0) {
                        sb.append(f612e);
                    }
                    sb.append(URLEncoder.encode(entry.getKey(), "UTF-8"));
                    sb.append(f);
                    sb.append(URLEncoder.encode(entry.getValue() == null ? "null" : entry.getValue(), "UTF-8"));
                }
            } catch (UnsupportedEncodingException unused) {
                k.e("format params failed");
            }
        }
        if (z) {
            String a2 = a(map);
            if (sb.length() > 0) {
                sb.append(f612e);
            }
            sb.append(URLEncoder.encode(d.f, "UTF-8"));
            sb.append(f);
            sb.append(URLEncoder.encode(a2, "UTF-8"));
        }
        return sb.toString();
    }

    public static String b(String str, Map<String, String> map) throws IOException {
        return b(str, map, true);
    }

    public static String b(String str, Map<String, String> map, boolean z) throws IOException {
        return a("POST", str, map, z);
    }
}
