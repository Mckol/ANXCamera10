package com.airbnb.lottie.c;

import com.airbnb.lottie.C0035m;
import com.airbnb.lottie.a.b.i;
import com.airbnb.lottie.d.h;
import com.airbnb.lottie.parser.moshi.JsonReader;
import java.io.IOException;

/* access modifiers changed from: package-private */
/* compiled from: PathKeyframeParser */
public class y {
    private y() {
    }

    static i a(JsonReader jsonReader, C0035m mVar) throws IOException {
        return new i(mVar, r.a(jsonReader, mVar, h.uc(), z.INSTANCE, jsonReader.peek() == JsonReader.Token.BEGIN_OBJECT));
    }
}
