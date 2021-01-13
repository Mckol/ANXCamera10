package com.airbnb.lottie.c;

import com.airbnb.lottie.C0035m;
import com.airbnb.lottie.model.a.h;
import com.airbnb.lottie.model.content.k;
import com.airbnb.lottie.parser.moshi.JsonReader;
import java.io.IOException;

/* access modifiers changed from: package-private */
/* compiled from: ShapePathParser */
public class I {
    static JsonReader.a NAMES = JsonReader.a.of("nm", "ind", "ks", "hd");

    private I() {
    }

    static k a(JsonReader jsonReader, C0035m mVar) throws IOException {
        int i = 0;
        String str = null;
        boolean z = false;
        h hVar = null;
        while (jsonReader.hasNext()) {
            int a2 = jsonReader.a(NAMES);
            if (a2 == 0) {
                str = jsonReader.nextString();
            } else if (a2 == 1) {
                i = jsonReader.nextInt();
            } else if (a2 == 2) {
                hVar = C0016d.i(jsonReader, mVar);
            } else if (a2 != 3) {
                jsonReader.skipValue();
            } else {
                z = jsonReader.nextBoolean();
            }
        }
        return new k(str, i, hVar, z);
    }
}
