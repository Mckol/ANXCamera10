package com.airbnb.lottie.c;

import com.airbnb.lottie.model.b;
import com.airbnb.lottie.parser.moshi.JsonReader;
import java.io.IOException;

/* renamed from: com.airbnb.lottie.c.k  reason: case insensitive filesystem */
/* compiled from: FontParser */
class C0023k {
    private static final JsonReader.a NAMES = JsonReader.a.of("fFamily", "fName", "fStyle", "ascent");

    private C0023k() {
    }

    static b b(JsonReader jsonReader) throws IOException {
        jsonReader.beginObject();
        String str = null;
        String str2 = null;
        float f = 0.0f;
        String str3 = null;
        while (jsonReader.hasNext()) {
            int a2 = jsonReader.a(NAMES);
            if (a2 == 0) {
                str = jsonReader.nextString();
            } else if (a2 == 1) {
                str3 = jsonReader.nextString();
            } else if (a2 == 2) {
                str2 = jsonReader.nextString();
            } else if (a2 != 3) {
                jsonReader.sc();
                jsonReader.skipValue();
            } else {
                f = (float) jsonReader.nextDouble();
            }
        }
        jsonReader.endObject();
        return new b(str, str3, str2, f);
    }
}
