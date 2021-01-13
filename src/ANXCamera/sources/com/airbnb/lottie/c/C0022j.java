package com.airbnb.lottie.c;

import com.airbnb.lottie.C0035m;
import com.airbnb.lottie.model.c;
import com.airbnb.lottie.model.content.j;
import com.airbnb.lottie.parser.moshi.JsonReader;
import com.google.android.apps.photos.api.PhotosOemApi;
import java.io.IOException;
import java.util.ArrayList;

/* renamed from: com.airbnb.lottie.c.j  reason: case insensitive filesystem */
/* compiled from: FontCharacterParser */
class C0022j {
    private static final JsonReader.a NAMES = JsonReader.a.of("ch", "size", "w", "style", "fFamily", PhotosOemApi.PATH_SPECIAL_TYPE_DATA);
    private static final JsonReader.a yf = JsonReader.a.of("shapes");

    private C0022j() {
    }

    static c a(JsonReader jsonReader, C0035m mVar) throws IOException {
        ArrayList arrayList = new ArrayList();
        jsonReader.beginObject();
        String str = null;
        String str2 = null;
        double d2 = 0.0d;
        double d3 = 0.0d;
        char c2 = 0;
        while (jsonReader.hasNext()) {
            int a2 = jsonReader.a(NAMES);
            if (a2 == 0) {
                c2 = jsonReader.nextString().charAt(0);
            } else if (a2 == 1) {
                d2 = jsonReader.nextDouble();
            } else if (a2 == 2) {
                d3 = jsonReader.nextDouble();
            } else if (a2 == 3) {
                str = jsonReader.nextString();
            } else if (a2 == 4) {
                str2 = jsonReader.nextString();
            } else if (a2 != 5) {
                jsonReader.sc();
                jsonReader.skipValue();
            } else {
                jsonReader.beginObject();
                while (jsonReader.hasNext()) {
                    if (jsonReader.a(yf) != 0) {
                        jsonReader.sc();
                        jsonReader.skipValue();
                    } else {
                        jsonReader.beginArray();
                        while (jsonReader.hasNext()) {
                            arrayList.add((j) C0019g.a(jsonReader, mVar));
                        }
                        jsonReader.endArray();
                    }
                }
                jsonReader.endObject();
            }
        }
        jsonReader.endObject();
        return new c(arrayList, c2, d2, d3, str, str2);
    }
}
