package com.airbnb.lottie.c;

import android.graphics.PointF;
import com.airbnb.lottie.C0035m;
import com.airbnb.lottie.model.a.b;
import com.airbnb.lottie.model.a.m;
import com.airbnb.lottie.model.content.f;
import com.airbnb.lottie.parser.moshi.JsonReader;
import java.io.IOException;

/* access modifiers changed from: package-private */
/* compiled from: RectangleShapeParser */
public class C {
    private static JsonReader.a NAMES = JsonReader.a.of("nm", "p", "s", "r", "hd");

    private C() {
    }

    static f a(JsonReader jsonReader, C0035m mVar) throws IOException {
        String str = null;
        m<PointF, PointF> mVar2 = null;
        com.airbnb.lottie.model.a.f fVar = null;
        b bVar = null;
        boolean z = false;
        while (jsonReader.hasNext()) {
            int a2 = jsonReader.a(NAMES);
            if (a2 == 0) {
                str = jsonReader.nextString();
            } else if (a2 == 1) {
                mVar2 = C0013a.b(jsonReader, mVar);
            } else if (a2 == 2) {
                fVar = C0016d.g(jsonReader, mVar);
            } else if (a2 == 3) {
                bVar = C0016d.e(jsonReader, mVar);
            } else if (a2 != 4) {
                jsonReader.skipValue();
            } else {
                z = jsonReader.nextBoolean();
            }
        }
        return new f(str, mVar2, fVar, bVar, z);
    }
}
