package com.airbnb.lottie.c;

import com.airbnb.lottie.C0035m;
import com.airbnb.lottie.model.a.b;
import com.airbnb.lottie.model.a.c;
import com.airbnb.lottie.model.a.d;
import com.airbnb.lottie.model.a.f;
import com.airbnb.lottie.model.content.GradientType;
import com.airbnb.lottie.model.content.ShapeStroke;
import com.airbnb.lottie.model.content.e;
import com.airbnb.lottie.parser.moshi.JsonReader;
import com.android.camera.data.data.config.SupportedConfigFactory;
import java.io.IOException;
import java.util.ArrayList;

/* access modifiers changed from: package-private */
/* compiled from: GradientStrokeParser */
public class n {
    private static final JsonReader.a Af = JsonReader.a.of("p", SupportedConfigFactory.CLOSE_BY_FILTER);
    private static final JsonReader.a Bf = JsonReader.a.of("n", "v");
    private static JsonReader.a NAMES = JsonReader.a.of("nm", SupportedConfigFactory.CLOSE_BY_HDR, SupportedConfigFactory.CLOSE_BY_ULTRA_PIXEL_PORTRAIT, "t", "s", "e", "w", "lc", "lj", "ml", "hd", SupportedConfigFactory.CLOSE_BY_BURST_SHOOT);

    private n() {
    }

    static e a(JsonReader jsonReader, C0035m mVar) throws IOException {
        c cVar;
        ArrayList arrayList = new ArrayList();
        float f = 0.0f;
        String str = null;
        GradientType gradientType = null;
        c cVar2 = null;
        d dVar = null;
        f fVar = null;
        f fVar2 = null;
        b bVar = null;
        ShapeStroke.LineCapType lineCapType = null;
        ShapeStroke.LineJoinType lineJoinType = null;
        b bVar2 = null;
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
                        if (a2 != 0) {
                            cVar = cVar2;
                            if (a2 != 1) {
                                jsonReader.sc();
                                jsonReader.skipValue();
                            } else {
                                cVar2 = C0016d.a(jsonReader, mVar, i);
                            }
                        } else {
                            cVar = cVar2;
                            i = jsonReader.nextInt();
                        }
                        cVar2 = cVar;
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
                    bVar = C0016d.e(jsonReader, mVar);
                    break;
                case 7:
                    lineCapType = ShapeStroke.LineCapType.values()[jsonReader.nextInt() - 1];
                    break;
                case 8:
                    lineJoinType = ShapeStroke.LineJoinType.values()[jsonReader.nextInt() - 1];
                    break;
                case 9:
                    f = (float) jsonReader.nextDouble();
                    break;
                case 10:
                    z = jsonReader.nextBoolean();
                    break;
                case 11:
                    jsonReader.beginArray();
                    while (jsonReader.hasNext()) {
                        jsonReader.beginObject();
                        String str2 = null;
                        b bVar3 = null;
                        while (jsonReader.hasNext()) {
                            int a3 = jsonReader.a(Bf);
                            if (a3 != 0) {
                                if (a3 != 1) {
                                    jsonReader.sc();
                                    jsonReader.skipValue();
                                } else {
                                    bVar3 = C0016d.e(jsonReader, mVar);
                                }
                                bVar2 = bVar2;
                            } else {
                                str2 = jsonReader.nextString();
                            }
                        }
                        jsonReader.endObject();
                        if (str2.equals(SupportedConfigFactory.CLOSE_BY_ULTRA_PIXEL_PORTRAIT)) {
                            bVar2 = bVar3;
                        } else {
                            if (str2.equals(SupportedConfigFactory.CLOSE_BY_BURST_SHOOT) || str2.equals(SupportedConfigFactory.CLOSE_BY_HDR)) {
                                mVar.z(true);
                                arrayList.add(bVar3);
                            }
                            bVar2 = bVar2;
                        }
                    }
                    jsonReader.endArray();
                    if (arrayList.size() == 1) {
                        arrayList.add(arrayList.get(0));
                    }
                    bVar2 = bVar2;
                    break;
                default:
                    jsonReader.sc();
                    jsonReader.skipValue();
                    break;
            }
        }
        return new e(str, gradientType, cVar2, dVar, fVar, fVar2, bVar, lineCapType, lineJoinType, f, arrayList, bVar2, z);
    }
}
