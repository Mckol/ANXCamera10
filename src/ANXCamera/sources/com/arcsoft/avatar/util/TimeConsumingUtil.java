package com.arcsoft.avatar.util;

import java.util.HashMap;

public class TimeConsumingUtil {
    public static boolean DEBUG = false;

    /* renamed from: a  reason: collision with root package name */
    private static HashMap<String, Long> f151a = new HashMap<>();

    /* renamed from: b  reason: collision with root package name */
    private static final String f152b = "PERFORMANCE";

    public static void startTheTimer(String str) {
        HashMap<String, Long> hashMap;
        if (DEBUG && (hashMap = f151a) != null) {
            hashMap.put(str, Long.valueOf(System.currentTimeMillis()));
        }
    }

    public static void stopTiming(String str) {
        HashMap<String, Long> hashMap;
        if (DEBUG && (hashMap = f151a) != null && hashMap.containsKey(str)) {
            long longValue = f151a.get(str).longValue();
            LOG.d(f152b, "" + str + " : " + (System.currentTimeMillis() - longValue));
        }
    }

    public static void stopTiming(String str, String str2) {
        HashMap<String, Long> hashMap;
        if (DEBUG && (hashMap = f151a) != null && hashMap.containsKey(str2)) {
            long longValue = f151a.get(str2).longValue();
            LOG.d(str, "" + str2 + " : " + (System.currentTimeMillis() - longValue));
        }
    }
}
