package com.airbnb.lottie.c;

import android.graphics.Path;
import com.airbnb.lottie.C0035m;
import com.airbnb.lottie.model.a.c;
import com.airbnb.lottie.model.a.f;
import com.airbnb.lottie.model.content.GradientType;
import com.airbnb.lottie.model.content.d;
import com.airbnb.lottie.parser.moshi.JsonReader;
import com.android.camera.data.data.config.SupportedConfigFactory;
import java.io.IOException;

/* access modifiers changed from: package-private */
/* renamed from: com.airbnb.lottie.c.m  reason: case insensitive filesystem */
/* compiled from: GradientFillParser */
public class C0025m {
    private static final JsonReader.a Af = JsonReader.a.of("p", SupportedConfigFactory.CLOSE_BY_FILTER);
    private static final JsonReader.a NAMES = JsonReader.a.of("nm", SupportedConfigFactory.CLOSE_BY_HDR, SupportedConfigFactory.CLOSE_BY_ULTRA_PIXEL_PORTRAIT, "t", "s", "e", "r", "hd");

    private C0025m() {
    }

    static d a(JsonReader jsonReader, C0035m mVar) throws IOException {
        Path.FillType fillType = Path.FillType.WINDING;
        String str = null;
        GradientType gradientType = null;
        c cVar = null;
        com.airbnb.lottie.model.a.d dVar = null;
        f fVar = null;
        f fVar2 = null;
        boolean z = false;
        while (jsonReader.hasNext()) {
            switch (jsonReader.a(NAMES)) {
                case 0:
                    str = jsonReader.nextString();
                    break;
                case 1:
                    int i = -1;
                    jsonReader.beginObject();
                    while (jsonReader.hasNext()) {
                        int a2 = jsonReader.a(Af);
                        if (a2 == 0) {
                            i = jsonReader.nextInt();
                        } else if (a2 != 1) {
                            jsonReader.sc();
                            jsonReader.skipValue();
                        } else {
                            cVar = C0016d.a(jsonReader, mVar, i);
                        }
                    }
                    jsonReader.endObject();
                    break;
                case 2:
                    dVar = C0016d.f(jsonReader, mVar);
                    break;
                case 3:
                    gradientType = jsonReader.nextInt() == 1 ? GradientType.LINEAR : GradientType.RADIAL;
                    break;
                case 4:
                    fVar = C0016d.g(jsonReader, mVar);
                    break;
                case 5:
                    fVar2 = C0016d.g(jsonReader, mVar);
                    break;
                case 6:
                    fillType = jsonReader.nextInt() == 1 ? Path.FillType.WINDING : Path.FillType.EVEN_ODD;
                    break;
                case 7:
                    z = jsonReader.nextBoolean();
                    break;
                default:
                    jsonReader.sc();
                    jsonReader.skipValue();
                    break;
            }
        }
        return new d(str, gradientType, fillType, cVar, dVar, fVar, fVar2, null, null, z);
    }
}
