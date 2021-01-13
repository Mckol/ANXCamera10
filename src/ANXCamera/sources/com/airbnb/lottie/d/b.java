package com.airbnb.lottie.d;

/* compiled from: GammaEvaluator */
public class b {
    public static int a(float f, int i, int i2) {
        if (i == i2) {
            return i;
        }
        float f2 = ((float) ((i >> 24) & 255)) / 255.0f;
        float n = n(((float) ((i >> 16) & 255)) / 255.0f);
        float n2 = n(((float) ((i >> 8) & 255)) / 255.0f);
        float n3 = n(((float) (i & 255)) / 255.0f);
        float n4 = n(((float) ((i2 >> 16) & 255)) / 255.0f);
        float n5 = n(((float) ((i2 >> 8) & 255)) / 255.0f);
        float n6 = n3 + (f * (n(((float) (i2 & 255)) / 255.0f) - n3));
        return (Math.round(o(n + ((n4 - n) * f)) * 255.0f) << 16) | (Math.round((f2 + (((((float) ((i2 >> 24) & 255)) / 255.0f) - f2) * f)) * 255.0f) << 24) | (Math.round(o(n2 + ((n5 - n2) * f)) * 255.0f) << 8) | Math.round(o(n6) * 255.0f);
    }

    private static float n(float f) {
        return f <= 0.04045f ? f / 12.92f : (float) Math.pow((double) ((f + 0.055f) / 1.055f), 2.4000000953674316d);
    }

    private static float o(float f) {
        return f <= 0.0031308f ? f * 12.92f : (float) ((Math.pow((double) f, 0.4166666567325592d) * 1.0549999475479126d) - 0.054999999701976776d);
    }
}
