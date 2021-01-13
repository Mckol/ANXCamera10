package com.airbnb.lottie.c;

import android.graphics.Color;
import android.graphics.Rect;
import com.airbnb.lottie.C0035m;
import com.airbnb.lottie.d.h;
import com.airbnb.lottie.e.a;
import com.airbnb.lottie.model.a.b;
import com.airbnb.lottie.model.a.j;
import com.airbnb.lottie.model.a.k;
import com.airbnb.lottie.model.a.l;
import com.airbnb.lottie.model.layer.Layer;
import com.airbnb.lottie.parser.moshi.JsonReader;
import com.android.camera.data.data.config.SupportedConfigFactory;
import com.xiaomi.stat.d;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

/* compiled from: LayerParser */
public class t {
    private static final JsonReader.a Gf = JsonReader.a.of(SupportedConfigFactory.CLOSE_BY_BURST_SHOOT, SupportedConfigFactory.CLOSE_BY_HHT);
    private static final JsonReader.a Hf = JsonReader.a.of("nm");
    private static final JsonReader.a NAMES = JsonReader.a.of("nm", "ind", "refId", "ty", "parent", "sw", "sh", "sc", "ks", "tt", "masksProperties", "shapes", "t", "ef", d.Y, d.n, "w", SupportedConfigFactory.CLOSE_BY_VIDEO, "ip", "op", "tm", "cl", "hd");

    private t() {
    }

    public static Layer a(JsonReader jsonReader, C0035m mVar) throws IOException {
        float f;
        ArrayList arrayList;
        float f2;
        Layer.MatteType matteType = Layer.MatteType.NONE;
        ArrayList arrayList2 = new ArrayList();
        ArrayList arrayList3 = new ArrayList();
        jsonReader.beginObject();
        Float valueOf = Float.valueOf(1.0f);
        Float valueOf2 = Float.valueOf(0.0f);
        Layer.MatteType matteType2 = matteType;
        int i = 0;
        int i2 = 0;
        int i3 = 0;
        int i4 = 0;
        int i5 = 0;
        boolean z = false;
        Layer.LayerType layerType = null;
        String str = null;
        l lVar = null;
        j jVar = null;
        k kVar = null;
        b bVar = null;
        float f3 = 0.0f;
        float f4 = 0.0f;
        long j = -1;
        float f5 = 1.0f;
        float f6 = 0.0f;
        long j2 = 0;
        String str2 = null;
        String str3 = "UNSET";
        while (jsonReader.hasNext()) {
            switch (jsonReader.a(NAMES)) {
                case 0:
                    str3 = jsonReader.nextString();
                    break;
                case 1:
                    j2 = (long) jsonReader.nextInt();
                    break;
                case 2:
                    str = jsonReader.nextString();
                    break;
                case 3:
                    int nextInt = jsonReader.nextInt();
                    if (nextInt >= Layer.LayerType.UNKNOWN.ordinal()) {
                        layerType = Layer.LayerType.UNKNOWN;
                        break;
                    } else {
                        layerType = Layer.LayerType.values()[nextInt];
                        break;
                    }
                case 4:
                    j = (long) jsonReader.nextInt();
                    break;
                case 5:
                    i = (int) (((float) jsonReader.nextInt()) * h.uc());
                    break;
                case 6:
                    i2 = (int) (((float) jsonReader.nextInt()) * h.uc());
                    break;
                case 7:
                    i3 = Color.parseColor(jsonReader.nextString());
                    break;
                case 8:
                    lVar = C0015c.a(jsonReader, mVar);
                    break;
                case 9:
                    matteType2 = Layer.MatteType.values()[jsonReader.nextInt()];
                    mVar.p(1);
                    break;
                case 10:
                    jsonReader.beginArray();
                    while (jsonReader.hasNext()) {
                        arrayList2.add(w.a(jsonReader, mVar));
                    }
                    mVar.p(arrayList2.size());
                    jsonReader.endArray();
                    break;
                case 11:
                    jsonReader.beginArray();
                    while (jsonReader.hasNext()) {
                        com.airbnb.lottie.model.content.b a2 = C0019g.a(jsonReader, mVar);
                        if (a2 != null) {
                            arrayList3.add(a2);
                        }
                    }
                    jsonReader.endArray();
                    break;
                case 12:
                    jsonReader.beginObject();
                    while (jsonReader.hasNext()) {
                        int a3 = jsonReader.a(Gf);
                        if (a3 == 0) {
                            jVar = C0016d.d(jsonReader, mVar);
                        } else if (a3 != 1) {
                            jsonReader.sc();
                            jsonReader.skipValue();
                        } else {
                            jsonReader.beginArray();
                            if (jsonReader.hasNext()) {
                                kVar = C0014b.a(jsonReader, mVar);
                            }
                            while (jsonReader.hasNext()) {
                                jsonReader.skipValue();
                            }
                            jsonReader.endArray();
                        }
                    }
                    jsonReader.endObject();
                    break;
                case 13:
                    jsonReader.beginArray();
                    ArrayList arrayList4 = new ArrayList();
                    while (jsonReader.hasNext()) {
                        jsonReader.beginObject();
                        while (jsonReader.hasNext()) {
                            if (jsonReader.a(Hf) != 0) {
                                jsonReader.sc();
                                jsonReader.skipValue();
                            } else {
                                arrayList4.add(jsonReader.nextString());
                            }
                        }
                        jsonReader.endObject();
                    }
                    jsonReader.endArray();
                    mVar.F("Lottie doesn't support layer effects. If you are using them for  fills, strokes, trim paths etc. then try adding them directly as contents  in your shape. Found: " + arrayList4);
                    break;
                case 14:
                    f5 = (float) jsonReader.nextDouble();
                    break;
                case 15:
                    f4 = (float) jsonReader.nextDouble();
                    break;
                case 16:
                    i4 = (int) (((float) jsonReader.nextInt()) * h.uc());
                    break;
                case 17:
                    i5 = (int) (((float) jsonReader.nextInt()) * h.uc());
                    break;
                case 18:
                    f3 = (float) jsonReader.nextDouble();
                    break;
                case 19:
                    f6 = (float) jsonReader.nextDouble();
                    break;
                case 20:
                    bVar = C0016d.a(jsonReader, mVar, false);
                    break;
                case 21:
                    str2 = jsonReader.nextString();
                    break;
                case 22:
                    z = jsonReader.nextBoolean();
                    break;
                default:
                    jsonReader.sc();
                    jsonReader.skipValue();
                    break;
            }
        }
        jsonReader.endObject();
        float f7 = f3 / f5;
        float f8 = f6 / f5;
        ArrayList arrayList5 = new ArrayList();
        if (f7 > 0.0f) {
            f = f5;
            arrayList = arrayList5;
            arrayList.add(new a(mVar, valueOf2, valueOf2, null, 0.0f, Float.valueOf(f7)));
            f2 = 0.0f;
        } else {
            f = f5;
            arrayList = arrayList5;
            f2 = 0.0f;
        }
        if (f8 <= f2) {
            f8 = mVar.db();
        }
        arrayList.add(new a(mVar, valueOf, valueOf, null, f7, Float.valueOf(f8)));
        arrayList.add(new a(mVar, valueOf2, valueOf2, null, f8, Float.valueOf(Float.MAX_VALUE)));
        if (str3.endsWith(".ai") || "ai".equals(str2)) {
            mVar.F("Convert your Illustrator layers to shape layers.");
        }
        return new Layer(arrayList3, mVar, str3, j2, layerType, j, str, arrayList2, lVar, i, i2, i3, f, f4, i4, i5, jVar, kVar, arrayList, matteType2, bVar, z);
    }

    public static Layer e(C0035m mVar) {
        Rect bounds = mVar.getBounds();
        return new Layer(Collections.emptyList(), mVar, "__container", -1, Layer.LayerType.PRE_COMP, -1, null, Collections.emptyList(), new l(), 0, 0, 0, 0.0f, 0.0f, bounds.width(), bounds.height(), null, null, Collections.emptyList(), Layer.MatteType.NONE, null, false);
    }
}
