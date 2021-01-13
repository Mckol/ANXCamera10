package com.miui.internal.analytics;

import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import com.miui.internal.net.KeyValuePair;
import com.miui.internal.util.DeviceHelper;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import miui.util.SoftReferenceSingleton;
import org.json.JSONObject;

public class XiaomiServer {
    private static final SoftReferenceSingleton<XiaomiServer> INSTANCE = new SoftReferenceSingleton<XiaomiServer>() {
        /* class com.miui.internal.analytics.XiaomiServer.AnonymousClass1 */

        /* access modifiers changed from: protected */
        @Override // miui.util.SoftReferenceSingleton
        public XiaomiServer createInstance() {
            return new XiaomiServer();
        }
    };
    private static final String SERVER_URL = "http://tracking.miui.com/tracks";
    private static final String SERVER_URL_GLOBAL = "https://tracking.miui.com/tracks";
    private static final String SIGNITURE = "s";
    private static final String SUBJECT = "miui_apps";
    private static final String TAG = "XIAOMI_SERVER";
    private static final String TYPE = "t";
    private static final String VALUE = "value";

    private XiaomiServer() {
    }

    public static String fromParamListToString(List<KeyValuePair> list) {
        StringBuffer stringBuffer = new StringBuffer();
        if (list != null) {
            for (KeyValuePair keyValuePair : list) {
                try {
                    if (keyValuePair.getValue() != null) {
                        stringBuffer.append(URLEncoder.encode(keyValuePair.getKey(), "UTF-8"));
                        stringBuffer.append("=");
                        stringBuffer.append(URLEncoder.encode(keyValuePair.getValue(), "UTF-8"));
                        stringBuffer.append("&");
                    }
                } catch (Exception e2) {
                    Log.i(TAG, "Failed to convert from param list to string: " + e2.toString());
                    Log.i(TAG, "pair: " + keyValuePair.toString());
                    return null;
                }
            }
        }
        if (stringBuffer.length() > 0) {
            stringBuffer = stringBuffer.deleteCharAt(stringBuffer.length() - 1);
        }
        return stringBuffer.toString();
    }

    public static XiaomiServer getInstance() {
        return INSTANCE.get();
    }

    /* JADX WARNING: Can't wrap try/catch for region: R(5:14|(2:25|26)|27|28|29) */
    /* JADX WARNING: Can't wrap try/catch for region: R(7:15|16|17|(2:19|20)|21|22|32) */
    /* JADX WARNING: Can't wrap try/catch for region: R(9:0|1|2|(2:3|(1:5)(1:31))|6|(2:8|9)|10|11|12) */
    /* JADX WARNING: Code restructure failed: missing block: B:33:?, code lost:
        return null;
     */
    /* JADX WARNING: Failed to process nested try/catch */
    /* JADX WARNING: Missing exception handler attribute for start block: B:10:0x0024 */
    /* JADX WARNING: Missing exception handler attribute for start block: B:21:0x0033 */
    /* JADX WARNING: Missing exception handler attribute for start block: B:27:0x003d */
    public static String readResponseContent(InputStream inputStream) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        try {
            byte[] bArr = new byte[1024];
            while (true) {
                int read = inputStream.read(bArr, 0, 1024);
                if (read <= 0) {
                    break;
                }
                byteArrayOutputStream.write(bArr, 0, read);
            }
            String str = new String(byteArrayOutputStream.toByteArray(), "UTF-8");
            if (inputStream != null) {
                inputStream.close();
            }
            byteArrayOutputStream.close();
            return str;
        } catch (Exception e2) {
            e2.printStackTrace();
            if (inputStream != null) {
                inputStream.close();
            }
            byteArrayOutputStream.close();
            return null;
        } catch (Throwable th) {
            if (inputStream != null) {
                inputStream.close();
            }
            byteArrayOutputStream.close();
            throw th;
        }
    }

    public void close() {
    }

    public void init() {
    }

    /* JADX WARNING: Code restructure failed: missing block: B:42:0x00c3, code lost:
        if (r1 != null) goto L_0x00c5;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:43:0x00c5, code lost:
        r1.disconnect();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:46:0x00cf, code lost:
        if (r1 != null) goto L_0x00c5;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:49:0x00d8, code lost:
        if (r1 != null) goto L_0x00c5;
     */
    /* JADX WARNING: Removed duplicated region for block: B:53:0x00de A[SYNTHETIC, Splitter:B:53:0x00de] */
    public boolean send(Map<String, Object> map) {
        Throwable th;
        UnsupportedEncodingException e2;
        IOException e3;
        Exception e4;
        boolean z = false;
        if (map == null) {
            return false;
        }
        HttpURLConnection httpURLConnection = null;
        try {
            String encodeToString = Base64.encodeToString(new JSONObject(map).toString().getBytes(), 2);
            HttpURLConnection httpURLConnection2 = (HttpURLConnection) new URL(DeviceHelper.IS_INTERNATIONAL_BUILD ? SERVER_URL_GLOBAL : SERVER_URL).openConnection();
            try {
                LinkedList linkedList = new LinkedList();
                linkedList.add(new KeyValuePair(TYPE, SUBJECT));
                linkedList.add(new KeyValuePair("value", encodeToString));
                linkedList.add(new KeyValuePair(SIGNITURE, SaltGenerate.getKeyFromParams(linkedList)));
                httpURLConnection2.setRequestMethod("POST");
                String fromParamListToString = fromParamListToString(linkedList);
                if (!TextUtils.isEmpty(fromParamListToString)) {
                    httpURLConnection2.setDoOutput(true);
                    byte[] bytes = fromParamListToString.getBytes();
                    OutputStream outputStream = httpURLConnection2.getOutputStream();
                    try {
                        outputStream.write(bytes, 0, bytes.length);
                        outputStream.flush();
                    } finally {
                        outputStream.close();
                    }
                }
                if (httpURLConnection2.getResponseCode() == 200) {
                    String string = new JSONObject(readResponseContent(httpURLConnection2.getInputStream())).getString("status");
                    if (string.equals("ok")) {
                        z = true;
                    } else {
                        Log.i(TAG, string);
                    }
                }
                if (httpURLConnection2 != null) {
                    try {
                        httpURLConnection2.disconnect();
                    } catch (Exception unused) {
                    }
                }
            } catch (UnsupportedEncodingException e5) {
                e2 = e5;
                httpURLConnection = httpURLConnection2;
                Log.e(TAG, "UnsupportedEncodingException catched when sending data", e2);
            } catch (IOException e6) {
                e3 = e6;
                httpURLConnection = httpURLConnection2;
                Log.e(TAG, "IOException catched when sending data", e3);
            } catch (Exception e7) {
                e4 = e7;
                httpURLConnection = httpURLConnection2;
                try {
                    Log.e(TAG, "Exception catched when sending data", e4);
                } catch (Throwable th2) {
                    th = th2;
                    httpURLConnection2 = httpURLConnection;
                    if (httpURLConnection2 != null) {
                        try {
                            httpURLConnection2.disconnect();
                        } catch (Exception unused2) {
                        }
                    }
                    throw th;
                }
            } catch (Throwable th3) {
                th = th3;
                if (httpURLConnection2 != null) {
                }
                throw th;
            }
        } catch (UnsupportedEncodingException e8) {
            e2 = e8;
            Log.e(TAG, "UnsupportedEncodingException catched when sending data", e2);
        } catch (IOException e9) {
            e3 = e9;
            Log.e(TAG, "IOException catched when sending data", e3);
        } catch (Exception e10) {
            e4 = e10;
            Log.e(TAG, "Exception catched when sending data", e4);
        }
        return z;
    }
}
