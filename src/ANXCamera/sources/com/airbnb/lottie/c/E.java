package com.airbnb.lottie.c;

import com.airbnb.lottie.e.k;
import com.airbnb.lottie.parser.moshi.JsonReader;
import java.io.IOException;

/* compiled from: ScaleXYParser */
public class E implements L<k> {
    public static final E INSTANCE = new E();

    private E() {
    }

    @Override // com.airbnb.lottie.c.L
    public k a(JsonReader jsonReader, float f) throws IOException {
        boolean z = jsonReader.peek() == JsonReader.Token.BEGIN_ARRAY;
        if (z) {
            jsonReader.beginArray();
        }
        float nextDouble = (float) jsonReader.nextDouble();
        float nextDouble2 = (float) jsonReader.nextDouble();
        while (jsonReader.hasNext()) {
            jsonReader.skipValue();
        }
        if (z) {
            jsonReader.endArray();
        }
        return new k((nextDouble / 100.0f) * f, (nextDouble2 / 100.0f) * f);
    }
}
