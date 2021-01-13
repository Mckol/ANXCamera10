package com.miui.internal.net;

import android.text.TextUtils;
import android.util.Log;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.URLEncoder;
import java.util.List;

public class URLConnectionPostBuilder extends URLConnectionBuilder {
    public URLConnectionPostBuilder(String str) throws IOException {
        super(str);
    }

    /* JADX WARNING: Removed duplicated region for block: B:25:0x0075 A[SYNTHETIC, Splitter:B:25:0x0075] */
    /* JADX WARNING: Removed duplicated region for block: B:30:0x0083 A[SYNTHETIC, Splitter:B:30:0x0083] */
    public void post(OutputStream outputStream, List<KeyValuePair> list, String str) throws IOException {
        Throwable th;
        StringBuilder sb = new StringBuilder();
        for (KeyValuePair keyValuePair : list) {
            if (!TextUtils.isEmpty(sb)) {
                sb.append("&");
            }
            sb.append(URLEncoder.encode(keyValuePair.getKey(), str));
            sb.append("=");
            sb.append(URLEncoder.encode(keyValuePair.getValue(), str));
        }
        BufferedWriter bufferedWriter = null;
        try {
            BufferedWriter bufferedWriter2 = new BufferedWriter(new OutputStreamWriter(outputStream, str));
            try {
                bufferedWriter2.write(sb.toString());
                bufferedWriter2.flush();
                try {
                    bufferedWriter2.close();
                } catch (IOException e2) {
                    Log.e("URLConnectionBuilder", e2.getMessage());
                }
                if (outputStream != null) {
                    try {
                        outputStream.close();
                    } catch (IOException e3) {
                        Log.e("URLConnectionBuilder", e3.getMessage());
                    }
                }
            } catch (Throwable th2) {
                th = th2;
                bufferedWriter = bufferedWriter2;
                if (bufferedWriter != null) {
                }
                if (outputStream != null) {
                }
                throw th;
            }
        } catch (Throwable th3) {
            th = th3;
            if (bufferedWriter != null) {
                try {
                    bufferedWriter.close();
                } catch (IOException e4) {
                    Log.e("URLConnectionBuilder", e4.getMessage());
                }
            }
            if (outputStream != null) {
                try {
                    outputStream.close();
                } catch (IOException e5) {
                    Log.e("URLConnectionBuilder", e5.getMessage());
                }
            }
            throw th;
        }
    }

    public void post(List<KeyValuePair> list) throws IOException {
        post(this.urlConnection.getOutputStream(), list, "UTF-8");
    }
}
