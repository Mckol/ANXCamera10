package com.airbnb.lottie.c;

import android.graphics.PointF;
import com.airbnb.lottie.d.g;
import com.airbnb.lottie.model.a;
import com.airbnb.lottie.model.content.h;
import com.airbnb.lottie.parser.moshi.JsonReader;
import com.android.camera.data.data.config.SupportedConfigFactory;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/* compiled from: ShapeDataParser */
public class F implements L<h> {
    public static final F INSTANCE = new F();
    private static final JsonReader.a NAMES = JsonReader.a.of(SupportedConfigFactory.CLOSE_BY_SUPER_RESOLUTION, "v", SupportedConfigFactory.CLOSE_BY_ULTRA_WIDE, SupportedConfigFactory.CLOSE_BY_ULTRA_PIXEL_PORTRAIT);

    private F() {
    }

    @Override // com.airbnb.lottie.c.L
    public h a(JsonReader jsonReader, float f) throws IOException {
        if (jsonReader.peek() == JsonReader.Token.BEGIN_ARRAY) {
            jsonReader.beginArray();
        }
        jsonReader.beginObject();
        List<PointF> list = null;
        List<PointF> list2 = null;
        List<PointF> list3 = null;
        boolean z = false;
        while (jsonReader.hasNext()) {
            int a2 = jsonReader.a(NAMES);
            if (a2 == 0) {
                z = jsonReader.nextBoolean();
            } else if (a2 == 1) {
                list = q.c(jsonReader, f);
            } else if (a2 == 2) {
                list2 = q.c(jsonReader, f);
            } else if (a2 != 3) {
                jsonReader.sc();
                jsonReader.skipValue();
            } else {
                list3 = q.c(jsonReader, f);
            }
        }
        jsonReader.endObject();
        if (jsonReader.peek() == JsonReader.Token.END_ARRAY) {
            jsonReader.endArray();
        }
        if (list == null || list2 == null || list3 == null) {
            throw new IllegalArgumentException("Shape data was missing information.");
        } else if (list.isEmpty()) {
            return new h(new PointF(), false, Collections.emptyList());
        } else {
            int size = list.size();
            PointF pointF = list.get(0);
            ArrayList arrayList = new ArrayList(size);
            for (int i = 1; i < size; i++) {
                PointF pointF2 = list.get(i);
                int i2 = i - 1;
                arrayList.add(new a(g.a(list.get(i2), list3.get(i2)), g.a(pointF2, list2.get(i)), pointF2));
            }
            if (z) {
                PointF pointF3 = list.get(0);
                int i3 = size - 1;
                arrayList.add(new a(g.a(list.get(i3), list3.get(i3)), g.a(pointF3, list2.get(0)), pointF3));
            }
            return new h(pointF, z, arrayList);
        }
    }
}
