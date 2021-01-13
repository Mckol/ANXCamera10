package miuix.animation.utils;

import android.util.Log;
import java.io.BufferedReader;
import java.io.Closeable;
import java.io.IOException;
import java.io.InputStreamReader;
import miui.reflect.Field;

public class LogUtils {
    private static final String COMMA = ", ";
    private static final boolean IS_LOG_ENABLED;

    static {
        String str = "";
        try {
            String readProp = readProp("log.tag.folme.level");
            if (readProp != null) {
                str = readProp;
            }
        } catch (Exception e2) {
            Log.i(CommonUtils.TAG, "can not access property log.tag.folme.level, no log", e2);
        }
        Log.d(CommonUtils.TAG, "logLevel = " + str);
        IS_LOG_ENABLED = str.equals(Field.DOUBLE_SIGNATURE_PRIMITIVE);
    }

    private LogUtils() {
    }

    private static void closeQuietly(Closeable closeable) {
        if (closeable != null) {
            try {
                closeable.close();
            } catch (Exception e2) {
                Log.w(CommonUtils.TAG, "close " + closeable + " failed", e2);
            }
        }
    }

    public static void debug(String str, Object... objArr) {
        if (IS_LOG_ENABLED) {
            if (objArr.length > 0) {
                StringBuilder sb = new StringBuilder(COMMA);
                int length = sb.length();
                for (Object obj : objArr) {
                    if (sb.length() > length) {
                        sb.append(COMMA);
                    }
                    sb.append(obj);
                }
                Log.i(CommonUtils.TAG, str + sb.toString());
                return;
            }
            Log.i(CommonUtils.TAG, str);
        }
    }

    public static boolean isLogEnabled() {
        return IS_LOG_ENABLED;
    }

    private static String readProp(String str) {
        Throwable th;
        InputStreamReader inputStreamReader;
        IOException e2;
        BufferedReader bufferedReader;
        BufferedReader bufferedReader2 = null;
        try {
            inputStreamReader = new InputStreamReader(Runtime.getRuntime().exec("getprop " + str).getInputStream());
            try {
                bufferedReader = new BufferedReader(inputStreamReader);
            } catch (IOException e3) {
                e2 = e3;
                try {
                    Log.i(CommonUtils.TAG, "readProp failed", e2);
                    closeQuietly(bufferedReader2);
                    closeQuietly(inputStreamReader);
                    return "";
                } catch (Throwable th2) {
                    th = th2;
                    closeQuietly(bufferedReader2);
                    closeQuietly(inputStreamReader);
                    throw th;
                }
            }
            try {
                String readLine = bufferedReader.readLine();
                closeQuietly(bufferedReader);
                closeQuietly(inputStreamReader);
                return readLine;
            } catch (IOException e4) {
                bufferedReader2 = bufferedReader;
                e2 = e4;
                Log.i(CommonUtils.TAG, "readProp failed", e2);
                closeQuietly(bufferedReader2);
                closeQuietly(inputStreamReader);
                return "";
            } catch (Throwable th3) {
                bufferedReader2 = bufferedReader;
                th = th3;
                closeQuietly(bufferedReader2);
                closeQuietly(inputStreamReader);
                throw th;
            }
        } catch (IOException e5) {
            e2 = e5;
            inputStreamReader = null;
            Log.i(CommonUtils.TAG, "readProp failed", e2);
            closeQuietly(bufferedReader2);
            closeQuietly(inputStreamReader);
            return "";
        } catch (Throwable th4) {
            th = th4;
            inputStreamReader = null;
            closeQuietly(bufferedReader2);
            closeQuietly(inputStreamReader);
            throw th;
        }
    }
}
