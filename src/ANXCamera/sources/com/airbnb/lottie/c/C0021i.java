package com.airbnb.lottie.c;

import com.airbnb.lottie.parser.moshi.JsonReader;
import java.io.IOException;

/* renamed from: com.airbnb.lottie.c.i  reason: case insensitive filesystem */
/* compiled from: FloatParser */
public class C0021i implements L<Float> {
    public static final C0021i INSTANCE = new C0021i();

    private C0021i() {
    }

    @Override // com.airbnb.lottie.c.L
    public Float a(JsonReader jsonReader, float f) throws IOException {
        return Float.valueOf(q.d(jsonReader) * f);
    }
}
