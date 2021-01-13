package com.airbnb.lottie.c;

import com.airbnb.lottie.C0035m;
import com.airbnb.lottie.model.a.a;
import com.airbnb.lottie.model.a.b;
import com.airbnb.lottie.model.a.d;
import com.airbnb.lottie.model.content.ShapeStroke;
import com.airbnb.lottie.parser.moshi.JsonReader;
import com.android.camera.data.data.config.SupportedConfigFactory;
import java.io.IOException;
import java.util.ArrayList;

/* access modifiers changed from: package-private */
/* compiled from: ShapeStrokeParser */
public class J {
    private static final JsonReader.a Bf = JsonReader.a.of("n", "v");
    private static JsonReader.a NAMES = JsonReader.a.of("nm", SupportedConfigFactory.CLOSE_BY_SUPER_RESOLUTION, "w", SupportedConfigFactory.CLOSE_BY_ULTRA_PIXEL_PORTRAIT, "lc", "lj", "ml", "hd", SupportedConfigFactory.CLOSE_BY_BURST_SHOOT);

    private J() {
    }

    /* JADX WARNING: Removed duplicated region for block: B:35:0x008d  */
    /* JADX WARNING: Removed duplicated region for block: B:40:0x009e  */
    static ShapeStroke a(JsonReader jsonReader, C0035m mVar) throws IOException {
        char c2;
        int i;
        ArrayList arrayList = new ArrayList();
        float f = 0.0f;
        String str = null;
        b bVar = null;
        a aVar = null;
        d dVar = null;
        b bVar2 = null;
        ShapeStroke.LineCapType lineCapType = null;
        ShapeStroke.LineJoinType lineJoinType = null;
        boolean z = false;
        while (jsonReader.hasNext()) {
            int i2 = 1;
            switch (jsonReader.a(NAMES)) {
                case 0:
                    str = jsonReader.nextString();
                    break;
                case 1:
                    aVar = C0016d.c(jsonReader, mVar);
                    break;
                case 2:
                    bVar2 = C0016d.e(jsonReader, mVar);
                    break;
                case 3:
                    dVar = C0016d.f(jsonReader, mVar);
                    break;
                case 4:
                    lineCapType = ShapeStroke.LineCapType.values()[jsonReader.nextInt() - 1];
                    break;
                case 5:
                    lineJoinType = ShapeStroke.LineJoinType.values()[jsonReader.nextInt() - 1];
                    break;
                case 6:
                    f = (float) jsonReader.nextDouble();
                    break;
                case 7:
                    z = jsonReader.nextBoolean();
                    break;
                case 8:
                    jsonReader.beginArray();
                    while (jsonReader.hasNext()) {
                        jsonReader.beginObject();
                        String str2 = null;
                        b bVar3 = null;
                        while (jsonReader.hasNext()) {
                            int a2 = jsonReader.a(Bf);
                            if (a2 == 0) {
                                str2 = jsonReader.nextString();
                            } else if (a2 != i2) {
                                jsonReader.sc();
                                jsonReader.skipValue();
                            } else {
                                bVar3 = C0016d.e(jsonReader, mVar);
                            }
                        }
                        jsonReader.endObject();
                        int hashCode = str2.hashCode();
                        if (hashCode != 100) {
                            if (hashCode != 103) {
                                if (hashCode == 111 && str2.equals(SupportedConfigFactory.CLOSE_BY_ULTRA_PIXEL_PORTRAIT)) {
                                    c2 = 0;
                                    if (c2 == 0) {
                                        i = 1;
                                        if (c2 == 1 || c2 == 2) {
                                            mVar.z(true);
                                            arrayList.add(bVar3);
                                        }
                                    } else {
                                        i = 1;
                                        bVar = bVar3;
                                    }
                                    i2 = i;
                                }
                            } else if (str2.equals(SupportedConfigFactory.CLOSE_BY_HDR)) {
                                c2 = 2;
                                if (c2 == 0) {
                                }
                                i2 = i;
                            }
                        } else if (str2.equals(SupportedConfigFactory.CLOSE_BY_BURST_SHOOT)) {
                            c2 = 1;
                            if (c2 == 0) {
                            }
                            i2 = i;
                        }
                        c2 = 65535;
                        if (c2 == 0) {
                        }
                        i2 = i;
                    }
                    jsonReader.endArray();
                    if (arrayList.size() != i2) {
                        break;
                    } else {
                        arrayList.add(arrayList.get(0));
                        break;
                    }
                default:
                    jsonReader.skipValue();
                    break;
            }
        }
        return new ShapeStroke(str, bVar, arrayList, aVar, dVar, bVar2, lineCapType, lineJoinType, f, z);
    }
}
