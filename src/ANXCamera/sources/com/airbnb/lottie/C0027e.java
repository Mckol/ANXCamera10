package com.airbnb.lottie;

import androidx.annotation.RestrictTo;
import androidx.core.os.TraceCompat;

@RestrictTo({RestrictTo.Scope.LIBRARY})
/* renamed from: com.airbnb.lottie.e  reason: case insensitive filesystem */
/* compiled from: L */
public class C0027e {
    public static boolean DBG = false;
    private static final int MAX_DEPTH = 20;
    public static final String TAG = "LOTTIE";
    private static boolean Wa;
    private static String[] Xa;
    private static long[] Ya;
    private static int Za;
    private static int _a;

    public static float D(String str) {
        int i = _a;
        if (i > 0) {
            _a = i - 1;
            return 0.0f;
        } else if (!Wa) {
            return 0.0f;
        } else {
            Za--;
            int i2 = Za;
            if (i2 == -1) {
                throw new IllegalStateException("Can't end trace section. There are none.");
            } else if (str.equals(Xa[i2])) {
                TraceCompat.endSection();
                return ((float) (System.nanoTime() - Ya[Za])) / 1000000.0f;
            } else {
                throw new IllegalStateException("Unbalanced trace call " + str + ". Expected " + Xa[Za] + ".");
            }
        }
    }

    public static void beginSection(String str) {
        if (Wa) {
            int i = Za;
            if (i == 20) {
                _a++;
                return;
            }
            Xa[i] = str;
            Ya[i] = System.nanoTime();
            TraceCompat.beginSection(str);
            Za++;
        }
    }

    public static void y(boolean z) {
        if (Wa != z) {
            Wa = z;
            if (Wa) {
                Xa = new String[20];
                Ya = new long[20];
            }
        }
    }
}
