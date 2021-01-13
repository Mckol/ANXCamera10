package com.airbnb.lottie.c;

import com.airbnb.lottie.C0035m;
import com.airbnb.lottie.model.a.a;
import com.airbnb.lottie.model.a.b;
import com.airbnb.lottie.model.a.k;
import com.airbnb.lottie.parser.moshi.JsonReader;
import com.android.camera.data.data.config.SupportedConfigFactory;
import com.xiaomi.stat.d;
import java.io.IOException;

/* renamed from: com.airbnb.lottie.c.b  reason: case insensitive filesystem */
/* compiled from: AnimatableTextPropertiesParser */
public class C0014b {
    private static JsonReader.a vf = JsonReader.a.of(SupportedConfigFactory.CLOSE_BY_HHT);
    private static JsonReader.a wf = JsonReader.a.of(d.ak, "sc", "sw", "t");

    private C0014b() {
    }

    public static k a(JsonReader jsonReader, C0035m mVar) throws IOException {
        jsonReader.beginObject();
        k kVar = null;
        while (jsonReader.hasNext()) {
            if (jsonReader.a(vf) != 0) {
                jsonReader.sc();
                jsonReader.skipValue();
            } else {
                kVar = j(jsonReader, mVar);
            }
        }
        jsonReader.endObject();
        return kVar == null ? new k(null, null, null, null) : kVar;
    }

    private static k j(JsonReader jsonReader, C0035m mVar) throws IOException {
        jsonReader.beginObject();
        a aVar = null;
        a aVar2 = null;
        b bVar = null;
        b bVar2 = null;
        while (jsonReader.hasNext()) {
            int a2 = jsonReader.a(wf);
            if (a2 == 0) {
                aVar = C0016d.c(jsonReader, mVar);
            } else if (a2 == 1) {
                aVar2 = C0016d.c(jsonReader, mVar);
            } else if (a2 == 2) {
                bVar = C0016d.e(jsonReader, mVar);
            } else if (a2 != 3) {
                jsonReader.sc();
                jsonReader.skipValue();
            } else {
                bVar2 = C0016d.e(jsonReader, mVar);
            }
        }
        jsonReader.endObject();
        return new k(aVar, aVar2, bVar, bVar2);
    }
}
