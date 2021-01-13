package com.airbnb.lottie.c;

import com.airbnb.lottie.parser.moshi.JsonReader;
import java.io.IOException;

/* access modifiers changed from: package-private */
/* compiled from: ValueParser */
public interface L<V> {
    V a(JsonReader jsonReader, float f) throws IOException;
}
