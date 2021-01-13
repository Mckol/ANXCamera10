package com.airbnb.lottie.c;

import android.graphics.Rect;
import androidx.collection.LongSparseArray;
import androidx.collection.SparseArrayCompat;
import com.airbnb.lottie.C0035m;
import com.airbnb.lottie.S;
import com.airbnb.lottie.d.d;
import com.airbnb.lottie.d.h;
import com.airbnb.lottie.model.b;
import com.airbnb.lottie.model.c;
import com.airbnb.lottie.model.g;
import com.airbnb.lottie.model.layer.Layer;
import com.airbnb.lottie.parser.moshi.JsonReader;
import com.android.camera.data.data.config.SupportedConfigFactory;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/* compiled from: LottieCompositionMoshiParser */
public class u {
    static JsonReader.a If = JsonReader.a.of("id", "layers", "w", SupportedConfigFactory.CLOSE_BY_VIDEO, "p", "u");
    private static final JsonReader.a Jf = JsonReader.a.of("list");
    private static final JsonReader.a Kf = JsonReader.a.of("cm", "tm", "dr");
    private static final JsonReader.a NAMES = JsonReader.a.of("w", SupportedConfigFactory.CLOSE_BY_VIDEO, "ip", "op", "fr", "v", "layers", "assets", "fonts", "chars", "markers");

    private static void a(JsonReader jsonReader, C0035m mVar, SparseArrayCompat<c> sparseArrayCompat) throws IOException {
        jsonReader.beginArray();
        while (jsonReader.hasNext()) {
            c a2 = C0022j.a(jsonReader, mVar);
            sparseArrayCompat.put(a2.hashCode(), a2);
        }
        jsonReader.endArray();
    }

    private static void a(JsonReader jsonReader, C0035m mVar, List<g> list) throws IOException {
        jsonReader.beginArray();
        while (jsonReader.hasNext()) {
            String str = null;
            jsonReader.beginObject();
            float f = 0.0f;
            float f2 = 0.0f;
            while (jsonReader.hasNext()) {
                int a2 = jsonReader.a(Kf);
                if (a2 == 0) {
                    str = jsonReader.nextString();
                } else if (a2 == 1) {
                    f = (float) jsonReader.nextDouble();
                } else if (a2 != 2) {
                    jsonReader.sc();
                    jsonReader.skipValue();
                } else {
                    f2 = (float) jsonReader.nextDouble();
                }
            }
            jsonReader.endObject();
            list.add(new g(str, f, f2));
        }
        jsonReader.endArray();
    }

    private static void a(JsonReader jsonReader, C0035m mVar, List<Layer> list, LongSparseArray<Layer> longSparseArray) throws IOException {
        jsonReader.beginArray();
        int i = 0;
        while (jsonReader.hasNext()) {
            Layer a2 = t.a(jsonReader, mVar);
            if (a2.getLayerType() == Layer.LayerType.IMAGE) {
                i++;
            }
            list.add(a2);
            longSparseArray.put(a2.getId(), a2);
            if (i > 4) {
                d.warning("You have " + i + " images. Lottie should primarily be used with shapes. If you are using Adobe Illustrator, convert the Illustrator layers to shape layers.");
            }
        }
        jsonReader.endArray();
    }

    private static void a(JsonReader jsonReader, C0035m mVar, Map<String, List<Layer>> map, Map<String, S> map2) throws IOException {
        jsonReader.beginArray();
        while (jsonReader.hasNext()) {
            ArrayList arrayList = new ArrayList();
            LongSparseArray longSparseArray = new LongSparseArray();
            jsonReader.beginObject();
            int i = 0;
            int i2 = 0;
            String str = null;
            String str2 = null;
            String str3 = null;
            while (jsonReader.hasNext()) {
                int a2 = jsonReader.a(If);
                if (a2 == 0) {
                    str = jsonReader.nextString();
                } else if (a2 == 1) {
                    jsonReader.beginArray();
                    while (jsonReader.hasNext()) {
                        Layer a3 = t.a(jsonReader, mVar);
                        longSparseArray.put(a3.getId(), a3);
                        arrayList.add(a3);
                    }
                    jsonReader.endArray();
                } else if (a2 == 2) {
                    i = jsonReader.nextInt();
                } else if (a2 == 3) {
                    i2 = jsonReader.nextInt();
                } else if (a2 == 4) {
                    str2 = jsonReader.nextString();
                } else if (a2 != 5) {
                    jsonReader.sc();
                    jsonReader.skipValue();
                } else {
                    str3 = jsonReader.nextString();
                }
            }
            jsonReader.endObject();
            if (str2 != null) {
                S s = new S(i, i2, str, str2, str3);
                map2.put(s.getId(), s);
            } else {
                map.put(str, arrayList);
            }
        }
        jsonReader.endArray();
    }

    private static void a(JsonReader jsonReader, Map<String, b> map) throws IOException {
        jsonReader.beginObject();
        while (jsonReader.hasNext()) {
            if (jsonReader.a(Jf) != 0) {
                jsonReader.sc();
                jsonReader.skipValue();
            } else {
                jsonReader.beginArray();
                while (jsonReader.hasNext()) {
                    b b2 = C0023k.b(jsonReader);
                    map.put(b2.getName(), b2);
                }
                jsonReader.endArray();
            }
        }
        jsonReader.endObject();
    }

    public static C0035m b(JsonReader jsonReader) throws IOException {
        ArrayList arrayList;
        HashMap hashMap;
        JsonReader jsonReader2 = jsonReader;
        float uc = h.uc();
        LongSparseArray<Layer> longSparseArray = new LongSparseArray<>();
        ArrayList arrayList2 = new ArrayList();
        HashMap hashMap2 = new HashMap();
        HashMap hashMap3 = new HashMap();
        HashMap hashMap4 = new HashMap();
        ArrayList arrayList3 = new ArrayList();
        SparseArrayCompat<c> sparseArrayCompat = new SparseArrayCompat<>();
        C0035m mVar = new C0035m();
        jsonReader.beginObject();
        float f = 0.0f;
        float f2 = 0.0f;
        float f3 = 0.0f;
        int i = 0;
        int i2 = 0;
        while (jsonReader.hasNext()) {
            switch (jsonReader2.a(NAMES)) {
                case 0:
                    i = jsonReader.nextInt();
                    continue;
                    jsonReader2 = jsonReader;
                case 1:
                    i2 = jsonReader.nextInt();
                    continue;
                    jsonReader2 = jsonReader;
                case 2:
                    hashMap = hashMap4;
                    arrayList = arrayList3;
                    f = (float) jsonReader.nextDouble();
                    break;
                case 3:
                    hashMap = hashMap4;
                    arrayList = arrayList3;
                    f2 = ((float) jsonReader.nextDouble()) - 0.01f;
                    break;
                case 4:
                    hashMap = hashMap4;
                    arrayList = arrayList3;
                    f3 = (float) jsonReader.nextDouble();
                    break;
                case 5:
                    String[] split = jsonReader.nextString().split("\\.");
                    if (!h.a(Integer.parseInt(split[0]), Integer.parseInt(split[1]), Integer.parseInt(split[2]), 4, 4, 0)) {
                        mVar.F("Lottie only supports bodymovin >= 4.4.0");
                    }
                    hashMap = hashMap4;
                    arrayList = arrayList3;
                    break;
                case 6:
                    a(jsonReader2, mVar, arrayList2, longSparseArray);
                    hashMap = hashMap4;
                    arrayList = arrayList3;
                    break;
                case 7:
                    a(jsonReader2, mVar, hashMap2, hashMap3);
                    hashMap = hashMap4;
                    arrayList = arrayList3;
                    break;
                case 8:
                    a(jsonReader2, hashMap4);
                    hashMap = hashMap4;
                    arrayList = arrayList3;
                    break;
                case 9:
                    a(jsonReader2, mVar, sparseArrayCompat);
                    hashMap = hashMap4;
                    arrayList = arrayList3;
                    break;
                case 10:
                    a(jsonReader2, mVar, arrayList3);
                    hashMap = hashMap4;
                    arrayList = arrayList3;
                    break;
                default:
                    hashMap = hashMap4;
                    arrayList = arrayList3;
                    jsonReader.sc();
                    jsonReader.skipValue();
                    break;
            }
            hashMap4 = hashMap;
            arrayList3 = arrayList;
            jsonReader2 = jsonReader;
        }
        mVar.a(new Rect(0, 0, (int) (((float) i) * uc), (int) (((float) i2) * uc)), f, f2, f3, arrayList2, longSparseArray, hashMap2, hashMap3, sparseArrayCompat, hashMap4, arrayList3);
        return mVar;
    }
}
