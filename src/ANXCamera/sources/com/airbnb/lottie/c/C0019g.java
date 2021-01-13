package com.airbnb.lottie.c;

import androidx.annotation.Nullable;
import com.airbnb.lottie.C0035m;
import com.airbnb.lottie.model.content.b;
import com.airbnb.lottie.parser.moshi.JsonReader;
import com.android.camera.data.data.config.SupportedConfigFactory;
import com.arcsoft.supernight.SuperNightProcess;
import com.xiaomi.stat.d;
import java.io.IOException;

/* access modifiers changed from: package-private */
/* renamed from: com.airbnb.lottie.c.g  reason: case insensitive filesystem */
/* compiled from: ContentModelParser */
public class C0019g {
    private static JsonReader.a NAMES = JsonReader.a.of("ty", SupportedConfigFactory.CLOSE_BY_BURST_SHOOT);

    private C0019g() {
    }

    /* JADX INFO: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARNING: Code restructure failed: missing block: B:39:0x0094, code lost:
        if (r2.equals("gs") != false) goto L_0x00c0;
     */
    @Nullable
    static b a(JsonReader jsonReader, C0035m mVar) throws IOException {
        b bVar;
        String str;
        jsonReader.beginObject();
        char c2 = 2;
        int i = 2;
        while (true) {
            bVar = null;
            if (!jsonReader.hasNext()) {
                str = null;
                break;
            }
            int a2 = jsonReader.a(NAMES);
            if (a2 == 0) {
                str = jsonReader.nextString();
                break;
            } else if (a2 != 1) {
                jsonReader.sc();
                jsonReader.skipValue();
            } else {
                i = jsonReader.nextInt();
            }
        }
        if (str == null) {
            return null;
        }
        switch (str.hashCode()) {
            case 3239:
                if (str.equals("el")) {
                    c2 = 7;
                    break;
                }
                c2 = 65535;
                break;
            case 3270:
                if (str.equals("fl")) {
                    c2 = 3;
                    break;
                }
                c2 = 65535;
                break;
            case 3295:
                if (str.equals("gf")) {
                    c2 = 4;
                    break;
                }
                c2 = 65535;
                break;
            case 3307:
                if (str.equals("gr")) {
                    c2 = 0;
                    break;
                }
                c2 = 65535;
                break;
            case 3308:
                break;
            case 3488:
                if (str.equals(SupportedConfigFactory.CLOSE_BY_MANUAL_MODE)) {
                    c2 = 11;
                    break;
                }
                c2 = 65535;
                break;
            case SuperNightProcess.ASVL_PAF_RAW16_GRAY_16B /*{ENCODED_INT: 3633}*/:
                if (str.equals("rc")) {
                    c2 = '\b';
                    break;
                }
                c2 = 65535;
                break;
            case 3646:
                if (str.equals("rp")) {
                    c2 = '\f';
                    break;
                }
                c2 = 65535;
                break;
            case 3669:
                if (str.equals("sh")) {
                    c2 = 6;
                    break;
                }
                c2 = 65535;
                break;
            case 3679:
                if (str.equals(d.Y)) {
                    c2 = '\n';
                    break;
                }
                c2 = 65535;
                break;
            case 3681:
                if (str.equals(d.n)) {
                    c2 = 1;
                    break;
                }
                c2 = 65535;
                break;
            case 3705:
                if (str.equals("tm")) {
                    c2 = '\t';
                    break;
                }
                c2 = 65535;
                break;
            case 3710:
                if (str.equals("tr")) {
                    c2 = 5;
                    break;
                }
                c2 = 65535;
                break;
            default:
                c2 = 65535;
                break;
        }
        switch (c2) {
            case 0:
                bVar = H.a(jsonReader, mVar);
                break;
            case 1:
                bVar = J.a(jsonReader, mVar);
                break;
            case 2:
                bVar = n.a(jsonReader, mVar);
                break;
            case 3:
                bVar = G.a(jsonReader, mVar);
                break;
            case 4:
                bVar = C0025m.a(jsonReader, mVar);
                break;
            case 5:
                bVar = C0015c.a(jsonReader, mVar);
                break;
            case 6:
                bVar = I.a(jsonReader, mVar);
                break;
            case 7:
                bVar = C0017e.b(jsonReader, mVar, i);
                break;
            case '\b':
                bVar = C.a(jsonReader, mVar);
                break;
            case '\t':
                bVar = K.a(jsonReader, mVar);
                break;
            case '\n':
                bVar = B.a(jsonReader, mVar);
                break;
            case 11:
                bVar = x.b(jsonReader);
                mVar.F("Animation contains merge paths. Merge paths are only supported on KitKat+ and must be manually enabled by calling enableMergePathsForKitKatAndAbove().");
                break;
            case '\f':
                bVar = D.a(jsonReader, mVar);
                break;
            default:
                com.airbnb.lottie.d.d.warning("Unknown shape type " + str);
                break;
        }
        while (jsonReader.hasNext()) {
            jsonReader.skipValue();
        }
        jsonReader.endObject();
        return bVar;
    }
}
