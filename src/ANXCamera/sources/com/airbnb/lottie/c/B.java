package com.airbnb.lottie.c;

import android.graphics.PointF;
import com.airbnb.lottie.C0035m;
import com.airbnb.lottie.model.a.b;
import com.airbnb.lottie.model.a.m;
import com.airbnb.lottie.model.content.PolystarShape;
import com.airbnb.lottie.parser.moshi.JsonReader;
import com.xiaomi.stat.d;
import java.io.IOException;

/* access modifiers changed from: package-private */
/* compiled from: PolystarShapeParser */
public class B {
    private static final JsonReader.a NAMES = JsonReader.a.of("nm", "sy", "pt", "p", "r", "or", d.l, "ir", "is", "hd");

    private B() {
    }

    static PolystarShape a(JsonReader jsonReader, C0035m mVar) throws IOException {
        boolean z = false;
        String str = null;
        PolystarShape.Type type = null;
        b bVar = null;
        m<PointF, PointF> mVar2 = null;
        b bVar2 = null;
        b bVar3 = null;
        b bVar4 = null;
        b bVar5 = null;
        b bVar6 = null;
        while (jsonReader.hasNext()) {
            switch (jsonReader.a(NAMES)) {
                case 0:
                    str = jsonReader.nextString();
                    break;
                case 1:
                    type = PolystarShape.Type.B(jsonReader.nextInt());
                    break;
                case 2:
                    bVar = C0016d.a(jsonReader, mVar, false);
                    break;
                case 3:
                    mVar2 = C0013a.b(jsonReader, mVar);
                    break;
                case 4:
                    bVar2 = C0016d.a(jsonReader, mVar, false);
                    break;
                case 5:
                    bVar4 = C0016d.e(jsonReader, mVar);
                    break;
                case 6:
                    bVar6 = C0016d.a(jsonReader, mVar, false);
                    break;
                case 7:
                    bVar3 = C0016d.e(jsonReader, mVar);
                    break;
                case 8:
                    bVar5 = C0016d.a(jsonReader, mVar, false);
                    break;
                case 9:
                    z = jsonReader.nextBoolean();
                    break;
                default:
                    jsonReader.sc();
                    jsonReader.skipValue();
                    break;
            }
        }
        return new PolystarShape(str, type, bVar, mVar2, bVar2, bVar3, bVar4, bVar5, bVar6, z);
    }
}
