package com.airbnb.lottie.c;

import android.graphics.PointF;
import com.airbnb.lottie.C0035m;
import com.airbnb.lottie.model.a.f;
import com.airbnb.lottie.model.a.m;
import com.airbnb.lottie.model.content.a;
import com.airbnb.lottie.parser.moshi.JsonReader;
import com.android.camera.data.data.config.SupportedConfigFactory;
import java.io.IOException;

/* access modifiers changed from: package-private */
/* renamed from: com.airbnb.lottie.c.e  reason: case insensitive filesystem */
/* compiled from: CircleShapeParser */
public class C0017e {
    private static JsonReader.a NAMES = JsonReader.a.of("nm", "p", "s", "hd", SupportedConfigFactory.CLOSE_BY_BURST_SHOOT);

    private C0017e() {
    }

    static a b(JsonReader jsonReader, C0035m mVar, int i) throws IOException {
        boolean z = i == 3;
        boolean z2 = false;
        String str = null;
        m<PointF, PointF> mVar2 = null;
        f fVar = null;
        while (jsonReader.hasNext()) {
            int a2 = jsonReader.a(NAMES);
            if (a2 == 0) {
                str = jsonReader.nextString();
            } else if (a2 == 1) {
                mVar2 = C0013a.b(jsonReader, mVar);
            } else if (a2 == 2) {
                fVar = C0016d.g(jsonReader, mVar);
            } else if (a2 == 3) {
                z2 = jsonReader.nextBoolean();
            } else if (a2 != 4) {
                jsonReader.sc();
                jsonReader.skipValue();
            } else {
                z = jsonReader.nextInt() == 3;
            }
        }
        return new a(str, mVar2, fVar, z, z2);
    }
}
