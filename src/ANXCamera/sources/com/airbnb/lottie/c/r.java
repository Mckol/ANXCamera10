package com.airbnb.lottie.c;

import android.graphics.PointF;
import android.view.animation.Interpolator;
import android.view.animation.LinearInterpolator;
import androidx.annotation.Nullable;
import androidx.collection.SparseArrayCompat;
import androidx.core.view.animation.PathInterpolatorCompat;
import com.airbnb.lottie.C0035m;
import com.airbnb.lottie.d.g;
import com.airbnb.lottie.d.h;
import com.airbnb.lottie.e.a;
import com.airbnb.lottie.parser.moshi.JsonReader;
import com.android.camera.data.data.config.SupportedConfigFactory;
import java.io.IOException;
import java.lang.ref.WeakReference;

/* access modifiers changed from: package-private */
/* compiled from: KeyframeParser */
public class r {
    private static final float Ef = 100.0f;
    private static SparseArrayCompat<WeakReference<Interpolator>> Ff;
    private static final Interpolator LINEAR_INTERPOLATOR = new LinearInterpolator();
    static JsonReader.a NAMES = JsonReader.a.of("t", "s", "e", SupportedConfigFactory.CLOSE_BY_ULTRA_PIXEL_PORTRAIT, SupportedConfigFactory.CLOSE_BY_ULTRA_WIDE, SupportedConfigFactory.CLOSE_BY_VIDEO, "to", "ti");

    r() {
    }

    private static <T> a<T> a(C0035m mVar, JsonReader jsonReader, float f, L<T> l) throws IOException {
        Interpolator interpolator;
        T t;
        jsonReader.beginObject();
        Interpolator interpolator2 = null;
        PointF pointF = null;
        PointF pointF2 = null;
        T t2 = null;
        T t3 = null;
        PointF pointF3 = null;
        PointF pointF4 = null;
        float f2 = 0.0f;
        while (true) {
            boolean z = false;
            while (true) {
                if (jsonReader.hasNext()) {
                    switch (jsonReader.a(NAMES)) {
                        case 0:
                            f2 = (float) jsonReader.nextDouble();
                        case 1:
                            t3 = l.a(jsonReader, f);
                        case 2:
                            t2 = l.a(jsonReader, f);
                        case 3:
                            pointF = q.b(jsonReader, f);
                        case 4:
                            pointF2 = q.b(jsonReader, f);
                        case 5:
                            if (jsonReader.nextInt() == 1) {
                                z = true;
                            }
                        case 6:
                            pointF3 = q.b(jsonReader, f);
                        case 7:
                            pointF4 = q.b(jsonReader, f);
                        default:
                            jsonReader.skipValue();
                    }
                } else {
                    jsonReader.endObject();
                    if (z) {
                        interpolator = LINEAR_INTERPOLATOR;
                        t = t3;
                    } else {
                        if (pointF == null || pointF2 == null) {
                            interpolator = LINEAR_INTERPOLATOR;
                        } else {
                            float f3 = -f;
                            pointF.x = g.clamp(pointF.x, f3, f);
                            pointF.y = g.clamp(pointF.y, -100.0f, (float) Ef);
                            pointF2.x = g.clamp(pointF2.x, f3, f);
                            pointF2.y = g.clamp(pointF2.y, -100.0f, (float) Ef);
                            int a2 = h.a(pointF.x, pointF.y, pointF2.x, pointF2.y);
                            WeakReference<Interpolator> interpolator3 = getInterpolator(a2);
                            if (interpolator3 != null) {
                                interpolator2 = interpolator3.get();
                            }
                            if (interpolator3 == null || interpolator2 == null) {
                                interpolator2 = PathInterpolatorCompat.create(pointF.x / f, pointF.y / f, pointF2.x / f, pointF2.y / f);
                                try {
                                    a(a2, new WeakReference(interpolator2));
                                } catch (ArrayIndexOutOfBoundsException unused) {
                                }
                            }
                            interpolator = interpolator2;
                        }
                        t = t2;
                    }
                    a<T> aVar = new a<>(mVar, t3, t, interpolator, f2, null);
                    aVar.lg = pointF3;
                    aVar.mg = pointF4;
                    return aVar;
                }
            }
        }
    }

    private static <T> a<T> a(JsonReader jsonReader, float f, L<T> l) throws IOException {
        return new a<>(l.a(jsonReader, f));
    }

    static <T> a<T> a(JsonReader jsonReader, C0035m mVar, float f, L<T> l, boolean z) throws IOException {
        return z ? a(mVar, jsonReader, f, l) : a(jsonReader, f, l);
    }

    private static void a(int i, WeakReference<Interpolator> weakReference) {
        synchronized (r.class) {
            Ff.put(i, weakReference);
        }
    }

    private static SparseArrayCompat<WeakReference<Interpolator>> bp() {
        if (Ff == null) {
            Ff = new SparseArrayCompat<>();
        }
        return Ff;
    }

    @Nullable
    private static WeakReference<Interpolator> getInterpolator(int i) {
        WeakReference<Interpolator> weakReference;
        synchronized (r.class) {
            weakReference = bp().get(i);
        }
        return weakReference;
    }
}
