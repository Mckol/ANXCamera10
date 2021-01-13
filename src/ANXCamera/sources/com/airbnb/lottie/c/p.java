package com.airbnb.lottie.c;

import com.airbnb.lottie.parser.moshi.JsonReader;

/* access modifiers changed from: package-private */
/* compiled from: JsonUtils */
public /* synthetic */ class p {
    static final /* synthetic */ int[] Cf = new int[JsonReader.Token.values().length];

    /* JADX WARNING: Can't wrap try/catch for region: R(8:0|1|2|3|4|5|6|8) */
    /* JADX WARNING: Failed to process nested try/catch */
    /* JADX WARNING: Missing exception handler attribute for start block: B:3:0x0014 */
    /* JADX WARNING: Missing exception handler attribute for start block: B:5:0x001f */
    static {
        Cf[JsonReader.Token.NUMBER.ordinal()] = 1;
        Cf[JsonReader.Token.BEGIN_ARRAY.ordinal()] = 2;
        try {
            Cf[JsonReader.Token.BEGIN_OBJECT.ordinal()] = 3;
        } catch (NoSuchFieldError unused) {
        }
    }
}
