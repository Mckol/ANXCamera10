package com.airbnb.lottie.c;

import com.airbnb.lottie.C0035m;
import com.airbnb.lottie.model.a.b;
import com.airbnb.lottie.model.a.l;
import com.airbnb.lottie.model.content.g;
import com.airbnb.lottie.parser.moshi.JsonReader;
import com.android.camera.data.data.config.SupportedConfigFactory;
import java.io.IOException;

/* access modifiers changed from: package-private */
/* compiled from: RepeaterParser */
public class D {
    private static JsonReader.a NAMES = JsonReader.a.of("nm", SupportedConfigFactory.CLOSE_BY_SUPER_RESOLUTION, SupportedConfigFactory.CLOSE_BY_ULTRA_PIXEL_PORTRAIT, "tr", "hd");

    private D() {
    }

    static g a(JsonReader jsonReader, C0035m mVar) throws IOException {
        boolean z = false;
        String str = null;
        b bVar = null;
        b bVar2 = null;
        l lVar = null;
        while (jsonReader.hasNext()) {
            int a2 = jsonReader.a(NAMES);
            if (a2 == 0) {
                str = jsonReader.nextString();
            } else if (a2 == 1) {
                bVar = C0016d.a(jsonReader, mVar, false);
            } else if (a2 == 2) {
                bVar2 = C0016d.a(jsonReader, mVar, false);
            } else if (a2 == 3) {
                lVar = C0015c.a(jsonReader, mVar);
            } else if (a2 != 4) {
                jsonReader.skipValue();
            } else {
                z = jsonReader.nextBoolean();
            }
        }
        return new g(str, bVar, bVar2, lVar, z);
    }
}
