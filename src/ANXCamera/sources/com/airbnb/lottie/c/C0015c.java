package com.airbnb.lottie.c;

import android.graphics.PointF;
import com.airbnb.lottie.C0035m;
import com.airbnb.lottie.e.a;
import com.airbnb.lottie.model.a.b;
import com.airbnb.lottie.model.a.e;
import com.airbnb.lottie.model.a.g;
import com.airbnb.lottie.model.a.i;
import com.airbnb.lottie.model.a.l;
import com.airbnb.lottie.model.a.m;
import com.airbnb.lottie.parser.moshi.JsonReader;
import com.android.camera.data.data.config.SupportedConfigFactory;
import com.xiaomi.stat.d;
import java.io.IOException;

/* renamed from: com.airbnb.lottie.c.c  reason: case insensitive filesystem */
/* compiled from: AnimatableTransformParser */
public class C0015c {
    private static JsonReader.a NAMES = JsonReader.a.of(SupportedConfigFactory.CLOSE_BY_HHT, "p", "s", "rz", "r", SupportedConfigFactory.CLOSE_BY_ULTRA_PIXEL_PORTRAIT, "so", "eo", d.af, "sa");
    private static JsonReader.a xf = JsonReader.a.of(SupportedConfigFactory.CLOSE_BY_FILTER);

    private C0015c() {
    }

    public static l a(JsonReader jsonReader, C0035m mVar) throws IOException {
        boolean z;
        boolean z2 = false;
        boolean z3 = jsonReader.peek() == JsonReader.Token.BEGIN_OBJECT;
        if (z3) {
            jsonReader.beginObject();
        }
        b bVar = null;
        e eVar = null;
        m<PointF, PointF> mVar2 = null;
        g gVar = null;
        b bVar2 = null;
        b bVar3 = null;
        com.airbnb.lottie.model.a.d dVar = null;
        b bVar4 = null;
        b bVar5 = null;
        while (jsonReader.hasNext()) {
            switch (jsonReader.a(NAMES)) {
                case 0:
                    jsonReader.beginObject();
                    while (jsonReader.hasNext()) {
                        if (jsonReader.a(xf) != 0) {
                            jsonReader.sc();
                            jsonReader.skipValue();
                        } else {
                            eVar = C0013a.a(jsonReader, mVar);
                        }
                    }
                    jsonReader.endObject();
                    z2 = z2;
                    continue;
                case 1:
                    mVar2 = C0013a.b(jsonReader, mVar);
                    continue;
                case 2:
                    gVar = C0016d.h(jsonReader, mVar);
                    continue;
                case 3:
                    mVar.F("Lottie doesn't support 3D layers.");
                    break;
                case 4:
                    break;
                case 5:
                    dVar = C0016d.f(jsonReader, mVar);
                    continue;
                case 6:
                    bVar4 = C0016d.a(jsonReader, mVar, z2);
                    continue;
                case 7:
                    bVar5 = C0016d.a(jsonReader, mVar, z2);
                    continue;
                case 8:
                    bVar2 = C0016d.a(jsonReader, mVar, z2);
                    continue;
                case 9:
                    bVar3 = C0016d.a(jsonReader, mVar, z2);
                    continue;
                default:
                    jsonReader.sc();
                    jsonReader.skipValue();
                    continue;
            }
            b a2 = C0016d.a(jsonReader, mVar, z2);
            if (a2.getKeyframes().isEmpty()) {
                a2.getKeyframes().add(new a<>(mVar, Float.valueOf(0.0f), Float.valueOf(0.0f), null, 0.0f, Float.valueOf(mVar.db())));
            } else if (a2.getKeyframes().get(0).startValue == null) {
                z = false;
                a2.getKeyframes().set(0, new a<>(mVar, Float.valueOf(0.0f), Float.valueOf(0.0f), null, 0.0f, Float.valueOf(mVar.db())));
                z2 = z;
                bVar = a2;
            }
            z = false;
            z2 = z;
            bVar = a2;
        }
        if (z3) {
            jsonReader.endObject();
        }
        e eVar2 = a(eVar) ? null : eVar;
        m<PointF, PointF> mVar3 = a(mVar2) ? null : mVar2;
        b bVar6 = a(bVar) ? null : bVar;
        if (a(gVar)) {
            gVar = null;
        }
        return new l(eVar2, mVar3, gVar, bVar6, dVar, bVar4, bVar5, c(bVar2) ? null : bVar2, b(bVar3) ? null : bVar3);
    }

    private static boolean a(b bVar) {
        return bVar == null || (bVar.isStatic() && bVar.getKeyframes().get(0).startValue.floatValue() == 0.0f);
    }

    private static boolean a(e eVar) {
        return eVar == null || (eVar.isStatic() && eVar.getKeyframes().get(0).startValue.equals(0.0f, 0.0f));
    }

    private static boolean a(g gVar) {
        return gVar == null || (gVar.isStatic() && gVar.getKeyframes().get(0).startValue.equals(1.0f, 1.0f));
    }

    private static boolean a(m<PointF, PointF> mVar) {
        return mVar == null || (!(mVar instanceof i) && mVar.isStatic() && mVar.getKeyframes().get(0).startValue.equals(0.0f, 0.0f));
    }

    private static boolean b(b bVar) {
        return bVar == null || (bVar.isStatic() && bVar.getKeyframes().get(0).startValue.floatValue() == 0.0f);
    }

    private static boolean c(b bVar) {
        return bVar == null || (bVar.isStatic() && bVar.getKeyframes().get(0).startValue.floatValue() == 0.0f);
    }
}
