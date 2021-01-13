package com.airbnb.lottie.c;

import androidx.annotation.Nullable;
import com.airbnb.lottie.C0035m;
import com.airbnb.lottie.d.h;
import com.airbnb.lottie.e.a;
import com.airbnb.lottie.model.a.b;
import com.airbnb.lottie.model.a.c;
import com.airbnb.lottie.model.a.d;
import com.airbnb.lottie.model.a.f;
import com.airbnb.lottie.model.a.g;
import com.airbnb.lottie.model.a.j;
import com.airbnb.lottie.parser.moshi.JsonReader;
import java.io.IOException;
import java.util.List;

/* renamed from: com.airbnb.lottie.c.d  reason: case insensitive filesystem */
/* compiled from: AnimatableValueParser */
public class C0016d {
    private C0016d() {
    }

    public static b a(JsonReader jsonReader, C0035m mVar, boolean z) throws IOException {
        return new b(a(jsonReader, z ? h.uc() : 1.0f, mVar, C0021i.INSTANCE));
    }

    static c a(JsonReader jsonReader, C0035m mVar, int i) throws IOException {
        return new c(a(jsonReader, mVar, new C0024l(i)));
    }

    @Nullable
    private static <T> List<a<T>> a(JsonReader jsonReader, float f, C0035m mVar, L<T> l) throws IOException {
        return s.a(jsonReader, mVar, f, l);
    }

    @Nullable
    private static <T> List<a<T>> a(JsonReader jsonReader, C0035m mVar, L<T> l) throws IOException {
        return s.a(jsonReader, mVar, 1.0f, l);
    }

    static com.airbnb.lottie.model.a.a c(JsonReader jsonReader, C0035m mVar) throws IOException {
        return new com.airbnb.lottie.model.a.a(a(jsonReader, mVar, C0018f.INSTANCE));
    }

    static j d(JsonReader jsonReader, C0035m mVar) throws IOException {
        return new j(a(jsonReader, mVar, C0020h.INSTANCE));
    }

    public static b e(JsonReader jsonReader, C0035m mVar) throws IOException {
        return a(jsonReader, mVar, true);
    }

    static d f(JsonReader jsonReader, C0035m mVar) throws IOException {
        return new d(a(jsonReader, mVar, o.INSTANCE));
    }

    static f g(JsonReader jsonReader, C0035m mVar) throws IOException {
        return new f(a(jsonReader, h.uc(), mVar, A.INSTANCE));
    }

    static g h(JsonReader jsonReader, C0035m mVar) throws IOException {
        return new g(a(jsonReader, mVar, E.INSTANCE));
    }

    static com.airbnb.lottie.model.a.h i(JsonReader jsonReader, C0035m mVar) throws IOException {
        return new com.airbnb.lottie.model.a.h(a(jsonReader, h.uc(), mVar, F.INSTANCE));
    }
}
