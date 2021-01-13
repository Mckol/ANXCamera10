package com.airbnb.lottie.c;

import android.graphics.PointF;
import com.airbnb.lottie.C0035m;
import com.airbnb.lottie.d.h;
import com.airbnb.lottie.e.a;
import com.airbnb.lottie.model.a.b;
import com.airbnb.lottie.model.a.e;
import com.airbnb.lottie.model.a.i;
import com.airbnb.lottie.model.a.m;
import com.airbnb.lottie.parser.moshi.JsonReader;
import com.android.camera.data.data.config.SupportedConfigFactory;
import java.io.IOException;
import java.util.ArrayList;

/* renamed from: com.airbnb.lottie.c.a  reason: case insensitive filesystem */
/* compiled from: AnimatablePathValueParser */
public class C0013a {
    private static JsonReader.a NAMES = JsonReader.a.of(SupportedConfigFactory.CLOSE_BY_FILTER, "x", "y");

    private C0013a() {
    }

    public static e a(JsonReader jsonReader, C0035m mVar) throws IOException {
        ArrayList arrayList = new ArrayList();
        if (jsonReader.peek() == JsonReader.Token.BEGIN_ARRAY) {
            jsonReader.beginArray();
            while (jsonReader.hasNext()) {
                arrayList.add(y.a(jsonReader, mVar));
            }
            jsonReader.endArray();
            s.e(arrayList);
        } else {
            arrayList.add(new a(q.b(jsonReader, h.uc())));
        }
        return new e(arrayList);
    }

    static m<PointF, PointF> b(JsonReader jsonReader, C0035m mVar) throws IOException {
        jsonReader.beginObject();
        e eVar = null;
        boolean z = false;
        b bVar = null;
        b bVar2 = null;
        while (jsonReader.peek() != JsonReader.Token.END_OBJECT) {
            int a2 = jsonReader.a(NAMES);
            if (a2 != 0) {
                if (a2 != 1) {
                    if (a2 != 2) {
                        jsonReader.sc();
                        jsonReader.skipValue();
                    } else if (jsonReader.peek() == JsonReader.Token.STRING) {
                        jsonReader.skipValue();
                    } else {
                        bVar2 = C0016d.e(jsonReader, mVar);
                    }
                } else if (jsonReader.peek() == JsonReader.Token.STRING) {
                    jsonReader.skipValue();
                } else {
                    bVar = C0016d.e(jsonReader, mVar);
                }
                z = true;
            } else {
                eVar = a(jsonReader, mVar);
            }
        }
        jsonReader.endObject();
        if (z) {
            mVar.F("Lottie doesn't support expressions.");
        }
        return eVar != null ? eVar : new i(bVar, bVar2);
    }
}
