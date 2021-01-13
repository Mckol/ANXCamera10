package com.miui.internal.analytics;

import android.util.Base64;
import com.miui.internal.net.KeyValuePair;
import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import miui.security.DigestUtils;

public class SaltGenerate {
    private static final String SALT_P1 = "8007236f-";
    private static final String SALT_P2 = "a2d6-4847-ac83-";
    private static final String SALT_P3 = "c49395ad6d65";

    private SaltGenerate() {
    }

    private static byte[] getBytes(String str) {
        try {
            return str.getBytes("UTF-8");
        } catch (UnsupportedEncodingException unused) {
            return str.getBytes();
        }
    }

    public static String getKeyFromParams(List<KeyValuePair> list) {
        Collections.sort(list, new Comparator<KeyValuePair>() {
            /* class com.miui.internal.analytics.SaltGenerate.AnonymousClass1 */

            public int compare(KeyValuePair keyValuePair, KeyValuePair keyValuePair2) {
                return keyValuePair.getKey().compareTo(keyValuePair2.getKey());
            }
        });
        StringBuilder sb = new StringBuilder();
        boolean z = true;
        for (KeyValuePair keyValuePair : list) {
            if (!z) {
                sb.append("&");
            }
            sb.append(keyValuePair.getKey());
            sb.append("=");
            sb.append(keyValuePair.getValue());
            z = false;
        }
        sb.append("&");
        sb.append(SALT_P1);
        sb.append(SALT_P2);
        sb.append(SALT_P3);
        return String.format("%1$032X", new BigInteger(1, DigestUtils.get(Base64.encodeToString(getBytes(sb.toString()), 2), DigestUtils.ALGORITHM_MD5)));
    }
}
