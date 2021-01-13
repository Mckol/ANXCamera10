package com.airbnb.lottie.c;

import android.graphics.Path;
import com.airbnb.lottie.C0035m;
import com.airbnb.lottie.model.a.a;
import com.airbnb.lottie.model.a.d;
import com.airbnb.lottie.model.content.i;
import com.airbnb.lottie.parser.moshi.JsonReader;
import com.android.camera.data.data.config.SupportedConfigFactory;
import java.io.IOException;

/* access modifiers changed from: package-private */
/* compiled from: ShapeFillParser */
public class G {
    private static final JsonReader.a NAMES = JsonReader.a.of("nm", SupportedConfigFactory.CLOSE_BY_SUPER_RESOLUTION, SupportedConfigFactory.CLOSE_BY_ULTRA_PIXEL_PORTRAIT, "fillEnabled", "r", "hd");

    private G() {
    }

    static i a(JsonReader jsonReader, C0035m mVar) throws IOException {
        boolean z = false;
        boolean z2 = false;
        int i = 1;
        String str = null;
        a aVar = null;
        d dVar = null;
        while (jsonReader.hasNext()) {
            int a2 = jsonReader.a(NAMES);
            if (a2 == 0) {
                str = jsonReader.nextString();
            } else if (a2 == 1) {
                aVar = C0016d.c(jsonReader, mVar);
            } else if (a2 == 2) {
                dVar = C0016d.f(jsonReader, mVar);
            } else if (a2 == 3) {
                z = jsonReader.nextBoolean();
            } else if (a2 == 4) {
                i = jsonReader.nextInt();
            } else if (a2 != 5) {
                jsonReader.sc();
                jsonReader.skipValue();
            } else {
                z2 = jsonReader.nextBoolean();
            }
        }
        return new i(str, z, i == 1 ? Path.FillType.WINDING : Path.FillType.EVEN_ODD, aVar, dVar, z2);
    }
}
