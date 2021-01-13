package com.airbnb.lottie.c;

import com.airbnb.lottie.parser.moshi.JsonReader;
import java.io.IOException;

/* compiled from: IntegerParser */
public class o implements L<Integer> {
    public static final o INSTANCE = new o();

    private o() {
    }

    @Override // com.airbnb.lottie.c.L
    public Integer a(JsonReader jsonReader, float f) throws IOException {
        return Integer.valueOf(Math.round(q.d(jsonReader) * f));
    }
}
