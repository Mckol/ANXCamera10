package com.android.camera.log;

import android.os.Looper;
import com.ss.android.ttve.common.TEDefine;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class LogUtil {
    private static final ThreadLocal<Boolean> sIsMainThread = new ThreadLocal<Boolean>() {
        /* access modifiers changed from: protected */
        public Boolean initialValue() {
            return Boolean.valueOf(Looper.getMainLooper().getThread() == Thread.currentThread());
        }
    };

    public static String addTags(Object obj, String str) {
        return hashCodeTag(obj) + mainThreadTag() + " " + str;
    }

    public static String addTags(Object obj, String str, String str2) {
        return hashCodeTag(obj) + mainThreadTag() + formatTags(str2) + " " + str;
    }

    private static String formatTags(String str) {
        List<String> asList = Arrays.asList(str.split("[\\x00-\\x1F\\x28-\\x29\\x2C\\x2F\\x3B-\\x3F\\x5B-\\x5D\\x7B-\\x7D]"));
        Collections.sort(asList);
        StringBuilder sb = new StringBuilder();
        for (String trim : asList) {
            String trim2 = trim.trim();
            if (trim2.length() > 0) {
                sb.append("[");
                sb.append(trim2);
                sb.append("]");
            }
        }
        return sb.toString();
    }

    private static String hashCodeTag(Object obj) {
        String hexString = obj == null ? TEDefine.FACE_BEAUTY_NULL : Integer.toHexString(Objects.hashCode(obj));
        return String.format("[%-9s]", new Object[]{"@" + hexString});
    }

    public static boolean isMainThread() {
        return sIsMainThread.get().booleanValue();
    }

    private static String mainThreadTag() {
        return isMainThread() ? "[ui]" : "";
    }
}
