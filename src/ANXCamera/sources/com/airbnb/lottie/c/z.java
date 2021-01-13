package com.airbnb.lottie.c;

import android.graphics.PointF;
import com.airbnb.lottie.parser.moshi.JsonReader;
import java.io.IOException;

/* compiled from: PathParser */
public class z implements L<PointF> {
    public static final z INSTANCE = new z();

    private z() {
    }

    @Override // com.airbnb.lottie.c.L
    public PointF a(JsonReader jsonReader, float f) throws IOException {
        return q.b(jsonReader, f);
    }
}
