package com.airbnb.lottie.c;

import com.airbnb.lottie.C0035m;
import com.airbnb.lottie.model.content.b;
import com.airbnb.lottie.model.content.j;
import com.airbnb.lottie.parser.moshi.JsonReader;
import java.io.IOException;
import java.util.ArrayList;

/* access modifiers changed from: package-private */
/* compiled from: ShapeGroupParser */
public class H {
    private static JsonReader.a NAMES = JsonReader.a.of("nm", "hd", "it");

    private H() {
    }

    static j a(JsonReader jsonReader, C0035m mVar) throws IOException {
        ArrayList arrayList = new ArrayList();
        String str = null;
        boolean z = false;
        while (jsonReader.hasNext()) {
            int a2 = jsonReader.a(NAMES);
            if (a2 == 0) {
                str = jsonReader.nextString();
            } else if (a2 == 1) {
                z = jsonReader.nextBoolean();
            } else if (a2 != 2) {
                jsonReader.skipValue();
            } else {
                jsonReader.beginArray();
                while (jsonReader.hasNext()) {
                    b a3 = C0019g.a(jsonReader, mVar);
                    if (a3 != null) {
                        arrayList.add(a3);
                    }
                }
                jsonReader.endArray();
            }
        }
        return new j(str, arrayList, z);
    }
}
