package miui.autoinstall.config.utils;

import android.text.TextUtils;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import miui.autoinstall.config.entity.RequestEntity;
import miui.autoinstall.config.entity.ResponseAppInfo;
import org.json.JSONObject;

public class AutoInstallRequestUtil {
    private static final int DEFAULT_CONNECT_TIME_OUT = 2000;
    private static final int DEFAULT_READ_TIME_OUT = 2000;
    private static final String KEY_RESPONSE_CODE = "code";
    private static final String KEY_RESPONSE_MESSAGE = "message";
    private static final String KEY_RESPONSE_SUCCESS = "Success";
    private static final int RESPONSE_CODE_SUCCESS = 0;
    private static final String SERVER_ADDRESS = "https://control.preload.xiaomi.com/rom_reset/apks?";

    private AutoInstallRequestUtil() {
    }

    /* JADX WARNING: Removed duplicated region for block: B:19:0x0039  */
    /* JADX WARNING: Removed duplicated region for block: B:23:0x0040  */
    public static long calculateFileSize(String str) {
        HttpURLConnection httpURLConnection;
        Throwable th;
        IOException e2;
        try {
            httpURLConnection = (HttpURLConnection) new URL(str).openConnection();
            try {
                httpURLConnection.setConnectTimeout(2000);
                httpURLConnection.setReadTimeout(2000);
                httpURLConnection.setRequestMethod("GET");
                httpURLConnection.connect();
                long contentLength = (long) httpURLConnection.getContentLength();
                if (httpURLConnection != null) {
                    httpURLConnection.disconnect();
                }
                return contentLength;
            } catch (IOException e3) {
                e2 = e3;
                try {
                    e2.printStackTrace();
                    if (httpURLConnection != null) {
                        httpURLConnection.disconnect();
                    }
                    return 0;
                } catch (Throwable th2) {
                    th = th2;
                    if (httpURLConnection != null) {
                    }
                    throw th;
                }
            }
        } catch (IOException e4) {
            e2 = e4;
            httpURLConnection = null;
            e2.printStackTrace();
            if (httpURLConnection != null) {
            }
            return 0;
        } catch (Throwable th3) {
            th = th3;
            httpURLConnection = null;
            if (httpURLConnection != null) {
                httpURLConnection.disconnect();
            }
            throw th;
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:43:0x00b0  */
    /* JADX WARNING: Removed duplicated region for block: B:45:0x00b5  */
    /* JADX WARNING: Removed duplicated region for block: B:51:0x00c1  */
    /* JADX WARNING: Removed duplicated region for block: B:53:0x00c6 A[SYNTHETIC, Splitter:B:53:0x00c6] */
    public static ResponseAppInfo requestAppInfo(RequestEntity requestEntity) {
        HttpURLConnection httpURLConnection;
        BufferedReader bufferedReader;
        Throwable th;
        Exception e2;
        try {
            httpURLConnection = (HttpURLConnection) new URL(SERVER_ADDRESS + requestEntity.toString()).openConnection();
            try {
                httpURLConnection.setConnectTimeout(2000);
                httpURLConnection.setReadTimeout(2000);
                httpURLConnection.setRequestMethod("GET");
                httpURLConnection.connect();
                if (httpURLConnection.getResponseCode() == 200) {
                    bufferedReader = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream()), 1024);
                    try {
                        StringBuilder sb = new StringBuilder();
                        while (true) {
                            String readLine = bufferedReader.readLine();
                            if (readLine == null) {
                                break;
                            }
                            sb.append(readLine);
                        }
                        JSONObject jSONObject = new JSONObject(sb.toString());
                        int i = jSONObject.getInt(KEY_RESPONSE_CODE);
                        String string = jSONObject.getString(KEY_RESPONSE_MESSAGE);
                        if (i == 0 && TextUtils.equals(string, KEY_RESPONSE_SUCCESS)) {
                            ResponseAppInfo json2Entity = ResponseAppInfo.json2Entity(sb.toString());
                            if (httpURLConnection != null) {
                                httpURLConnection.disconnect();
                            }
                            try {
                                bufferedReader.close();
                            } catch (IOException e3) {
                                e3.printStackTrace();
                            }
                            return json2Entity;
                        }
                    } catch (Exception e4) {
                        e2 = e4;
                        try {
                            e2.printStackTrace();
                            if (httpURLConnection != null) {
                                httpURLConnection.disconnect();
                            }
                            if (bufferedReader != null) {
                                bufferedReader.close();
                            }
                            return null;
                        } catch (Throwable th2) {
                            th = th2;
                            if (httpURLConnection != null) {
                            }
                            if (bufferedReader != null) {
                            }
                            throw th;
                        }
                    }
                } else {
                    bufferedReader = null;
                }
                if (httpURLConnection != null) {
                    httpURLConnection.disconnect();
                }
                if (bufferedReader != null) {
                    try {
                        bufferedReader.close();
                    } catch (IOException e5) {
                        e5.printStackTrace();
                    }
                }
            } catch (Exception e6) {
                e2 = e6;
                bufferedReader = null;
                e2.printStackTrace();
                if (httpURLConnection != null) {
                }
                if (bufferedReader != null) {
                }
                return null;
            } catch (Throwable th3) {
                bufferedReader = null;
                th = th3;
                if (httpURLConnection != null) {
                }
                if (bufferedReader != null) {
                }
                throw th;
            }
        } catch (Exception e7) {
            e2 = e7;
            httpURLConnection = null;
            bufferedReader = null;
            e2.printStackTrace();
            if (httpURLConnection != null) {
            }
            if (bufferedReader != null) {
            }
            return null;
        } catch (Throwable th4) {
            bufferedReader = null;
            th = th4;
            httpURLConnection = null;
            if (httpURLConnection != null) {
                httpURLConnection.disconnect();
            }
            if (bufferedReader != null) {
                try {
                    bufferedReader.close();
                } catch (IOException e8) {
                    e8.printStackTrace();
                }
            }
            throw th;
        }
        return null;
    }
}
