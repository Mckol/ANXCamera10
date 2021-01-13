package com.airbnb.lottie.c;

import com.airbnb.lottie.C0035m;
import com.airbnb.lottie.a.b.i;
import com.airbnb.lottie.e.a;
import com.airbnb.lottie.parser.moshi.JsonReader;
import com.android.camera.data.data.config.SupportedConfigFactory;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/* access modifiers changed from: package-private */
/* compiled from: KeyframesParser */
public class s {
    static JsonReader.a NAMES = JsonReader.a.of(SupportedConfigFactory.CLOSE_BY_FILTER);

    private s() {
    }

    static <T> List<a<T>> a(JsonReader jsonReader, C0035m mVar, float f, L<T> l) throws IOException {
        ArrayList arrayList = new ArrayList();
        if (jsonReader.peek() == JsonReader.Token.STRING) {
            mVar.F("Lottie doesn't support expressions.");
            return arrayList;
        }
        jsonReader.beginObject();
        while (jsonReader.hasNext()) {
            if (jsonReader.a(NAMES) != 0) {
                jsonReader.skipValue();
            } else if (jsonReader.peek() == JsonReader.Token.BEGIN_ARRAY) {
                jsonReader.beginArray();
                if (jsonReader.peek() == JsonReader.Token.NUMBER) {
                    arrayList.add(r.a(jsonReader, mVar, f, l, false));
                } else {
                    while (jsonReader.hasNext()) {
                        arrayList.add(r.a(jsonReader, mVar, f, l, true));
                    }
                }
                jsonReader.endArray();
            } else {
                arrayList.add(r.a(jsonReader, mVar, f, l, false));
            }
        }
        jsonReader.endObject();
        e(arrayList);
        return arrayList;
    }

    public static <T> void e(List<? extends a<T>> list) {
        int i;
        T t;
        int size = list.size();
        int i2 = 0;
        while (true) {
            i = size - 1;
            if (i2 >= i) {
                break;
            }
            a aVar = (a) list.get(i2);
            i2++;
            a aVar2 = (a) list.get(i2);
            aVar.mb = Float.valueOf(aVar2.lb);
            if (aVar.endValue == null && (t = aVar2.startValue) != null) {
                aVar.endValue = t;
                if (aVar instanceof i) {
                    ((i) aVar).zc();
                }
            }
        }
        a aVar3 = (a) list.get(i);
        if ((aVar3.startValue == null || aVar3.endValue == null) && list.size() > 1) {
            list.remove(aVar3);
        }
    }
}
