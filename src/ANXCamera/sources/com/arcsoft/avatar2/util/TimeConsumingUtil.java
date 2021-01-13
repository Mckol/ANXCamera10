package com.arcsoft.avatar2.util;

import java.util.HashMap;

public class TimeConsumingUtil {
    public static boolean DEBUG = false;

    /* renamed from: a  reason: collision with root package name */
    private static HashMap<String, Long> f309a = new HashMap<>();

    /* renamed from: b  reason: collision with root package name */
    private static final String f310b = "PERFORMANCE";

    public static void startTheTimer(String str) {
        HashMap<String, Long> hashMap;
        if (DEBUG && (hashMap = f309a) != null) {
            hashMap.put(str, Long.valueOf(System.currentTimeMillis()));
        }
    }

    public static void stopTiming(String str) {
        HashMap<String, Long> hashMap;
        if (DEBUG && (hashMap = f309a) != null && hashMap.containsKey(str)) {
            long longValue = f309a.get(str).longValue();
            LOG.d(f310b, "" + str + " : " + (System.currentTimeMillis() - longValue));
        }
    }

    public static void stopTiming(String str, String str2) {
        HashMap<String, Long> hashMap;
        if (DEBUG && (hashMap = f309a) != null && hashMap.containsKey(str2)) {
            long longValue = f309a.get(str2).longValue();
            LOG.d(str, "" + str2 + " : " + (System.currentTimeMillis() - longValue));
        }
    }
}
