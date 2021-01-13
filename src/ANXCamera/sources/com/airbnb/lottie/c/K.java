package com.airbnb.lottie.c;

import com.airbnb.lottie.C0035m;
import com.airbnb.lottie.model.a.b;
import com.airbnb.lottie.model.content.ShapeTrimPath;
import com.airbnb.lottie.parser.moshi.JsonReader;
import com.android.camera.data.data.config.SupportedConfigFactory;
import java.io.IOException;

/* access modifiers changed from: package-private */
/* compiled from: ShapeTrimPathParser */
public class K {
    private static JsonReader.a NAMES = JsonReader.a.of("s", "e", SupportedConfigFactory.CLOSE_BY_ULTRA_PIXEL_PORTRAIT, "nm", "m", "hd");

    private K() {
    }

    static ShapeTrimPath a(JsonReader jsonReader, C0035m mVar) throws IOException {
        boolean z = false;
        String str = null;
        ShapeTrimPath.Type type = null;
        b bVar = null;
        b bVar2 = null;
        b bVar3 = null;
        while (jsonReader.hasNext()) {
            int a2 = jsonReader.a(NAMES);
            if (a2 == 0) {
                bVar = C0016d.a(jsonReader, mVar, false);
            } else if (a2 == 1) {
                bVar2 = C0016d.a(jsonReader, mVar, false);
            } else if (a2 == 2) {
                bVar3 = C0016d.a(jsonReader, mVar, false);
            } else if (a2 == 3) {
                str = jsonReader.nextString();
            } else if (a2 == 4) {
                type = ShapeTrimPath.Type.A(jsonReader.nextInt());
            } else if (a2 != 5) {
                jsonReader.skipValue();
            } else {
                z = jsonReader.nextBoolean();
            }
        }
        return new ShapeTrimPath(str, type, bVar, bVar2, bVar3, z);
    }
}
