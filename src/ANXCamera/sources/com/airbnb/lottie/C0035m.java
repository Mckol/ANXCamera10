package com.airbnb.lottie;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Rect;
import androidx.annotation.Nullable;
import androidx.annotation.RawRes;
import androidx.annotation.RestrictTo;
import androidx.annotation.WorkerThread;
import androidx.collection.LongSparseArray;
import androidx.collection.SparseArrayCompat;
import com.airbnb.lottie.d.d;
import com.airbnb.lottie.model.b;
import com.airbnb.lottie.model.c;
import com.airbnb.lottie.model.g;
import com.airbnb.lottie.model.layer.Layer;
import com.airbnb.lottie.parser.moshi.JsonReader;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import org.json.JSONObject;

/* renamed from: com.airbnb.lottie.m  reason: case insensitive filesystem */
/* compiled from: LottieComposition */
public class C0035m {
    private Rect bounds;
    private final ca eb = new ca();
    private final HashSet<String> fb = new HashSet<>();
    private float frameRate;
    private Map<String, List<Layer>> gb;
    private Map<String, b> hb;
    private List<g> ib;
    private Map<String, S> images;
    private SparseArrayCompat<c> jb;
    private LongSparseArray<Layer> kb;
    private List<Layer> layers;
    private float lb;
    private float mb;
    private boolean nb;
    private int ob = 0;

    @Deprecated
    /* renamed from: com.airbnb.lottie.m$a */
    /* compiled from: LottieComposition */
    public static class a {

        /* renamed from: com.airbnb.lottie.m$a$a  reason: collision with other inner class name */
        /* compiled from: LottieComposition */
        private static final class C0005a implements T<C0035m>, AbstractC0011b {
            private boolean cancelled;
            private final aa listener;

            private C0005a(aa aaVar) {
                this.cancelled = false;
                this.listener = aaVar;
            }

            @Override // com.airbnb.lottie.AbstractC0011b
            public void cancel() {
                this.cancelled = true;
            }

            /* renamed from: d */
            public void a(C0035m mVar) {
                if (!this.cancelled) {
                    this.listener.a(mVar);
                }
            }
        }

        private a() {
        }

        @Nullable
        @WorkerThread
        @Deprecated
        public static C0035m E(String str) {
            return C0047y.o(str, null).getValue();
        }

        @Deprecated
        public static AbstractC0011b a(Context context, @RawRes int i, aa aaVar) {
            C0005a aVar = new C0005a(aaVar);
            C0047y.a(context, i).c(aVar);
            return aVar;
        }

        @Deprecated
        public static AbstractC0011b a(Context context, String str, aa aaVar) {
            C0005a aVar = new C0005a(aaVar);
            C0047y.h(context, str).c(aVar);
            return aVar;
        }

        @Deprecated
        public static AbstractC0011b a(JsonReader jsonReader, aa aaVar) {
            C0005a aVar = new C0005a(aaVar);
            C0047y.a(jsonReader, (String) null).c(aVar);
            return aVar;
        }

        @Deprecated
        public static AbstractC0011b a(InputStream inputStream, aa aaVar) {
            C0005a aVar = new C0005a(aaVar);
            C0047y.b(inputStream, (String) null).c(aVar);
            return aVar;
        }

        @Deprecated
        public static AbstractC0011b a(String str, aa aaVar) {
            C0005a aVar = new C0005a(aaVar);
            C0047y.n(str, null).c(aVar);
            return aVar;
        }

        @Nullable
        @WorkerThread
        @Deprecated
        public static C0035m a(Resources resources, JSONObject jSONObject) {
            return C0047y.b(jSONObject, (String) null).getValue();
        }

        @Deprecated
        @Nullable
        @WorkerThread
        public static C0035m a(JsonReader jsonReader) throws IOException {
            return C0047y.b(jsonReader, (String) null).getValue();
        }

        @Nullable
        @WorkerThread
        @Deprecated
        public static C0035m a(InputStream inputStream, boolean z) {
            if (z) {
                d.warning("Lottie now auto-closes input stream!");
            }
            return C0047y.c(inputStream, (String) null).getValue();
        }

        @Nullable
        @WorkerThread
        @Deprecated
        public static C0035m c(InputStream inputStream) {
            return C0047y.c(inputStream, (String) null).getValue();
        }

        @Nullable
        @WorkerThread
        @Deprecated
        public static C0035m g(Context context, String str) {
            return C0047y.i(context, str).getValue();
        }
    }

    @RestrictTo({RestrictTo.Scope.LIBRARY})
    public void F(String str) {
        d.warning(str);
        this.fb.add(str);
    }

    @Nullable
    public g G(String str) {
        this.ib.size();
        for (int i = 0; i < this.ib.size(); i++) {
            g gVar = this.ib.get(i);
            if (gVar.N(str)) {
                return gVar;
            }
        }
        return null;
    }

    @Nullable
    @RestrictTo({RestrictTo.Scope.LIBRARY})
    public List<Layer> H(String str) {
        return this.gb.get(str);
    }

    @RestrictTo({RestrictTo.Scope.LIBRARY})
    public void a(Rect rect, float f, float f2, float f3, List<Layer> list, LongSparseArray<Layer> longSparseArray, Map<String, List<Layer>> map, Map<String, S> map2, SparseArrayCompat<c> sparseArrayCompat, Map<String, b> map3, List<g> list2) {
        this.bounds = rect;
        this.lb = f;
        this.mb = f2;
        this.frameRate = f3;
        this.layers = list;
        this.kb = longSparseArray;
        this.gb = map;
        this.images = map2;
        this.jb = sparseArrayCompat;
        this.hb = map3;
        this.ib = list2;
    }

    public float bb() {
        return this.mb - this.lb;
    }

    public ca ca() {
        return this.eb;
    }

    @RestrictTo({RestrictTo.Scope.LIBRARY})
    public float db() {
        return this.mb;
    }

    @RestrictTo({RestrictTo.Scope.LIBRARY})
    public Layer e(long j) {
        return this.kb.get(j);
    }

    public Map<String, S> eb() {
        return this.images;
    }

    public List<g> fb() {
        return this.ib;
    }

    @RestrictTo({RestrictTo.Scope.LIBRARY})
    public int gb() {
        return this.ob;
    }

    public Rect getBounds() {
        return this.bounds;
    }

    public SparseArrayCompat<c> getCharacters() {
        return this.jb;
    }

    public float getDuration() {
        return (float) ((long) ((bb() / this.frameRate) * 1000.0f));
    }

    public Map<String, b> getFonts() {
        return this.hb;
    }

    public float getFrameRate() {
        return this.frameRate;
    }

    public List<Layer> getLayers() {
        return this.layers;
    }

    public ArrayList<String> getWarnings() {
        HashSet<String> hashSet = this.fb;
        return new ArrayList<>(Arrays.asList(hashSet.toArray(new String[hashSet.size()])));
    }

    @RestrictTo({RestrictTo.Scope.LIBRARY})
    public float hb() {
        return this.lb;
    }

    @RestrictTo({RestrictTo.Scope.LIBRARY})
    public boolean ib() {
        return this.nb;
    }

    public boolean jb() {
        return !this.images.isEmpty();
    }

    @RestrictTo({RestrictTo.Scope.LIBRARY})
    public void p(int i) {
        this.ob += i;
    }

    public void r(boolean z) {
        this.eb.setEnabled(z);
    }

    public String toString() {
        StringBuilder sb = new StringBuilder("LottieComposition:\n");
        for (Layer layer : this.layers) {
            sb.append(layer.toString("\t"));
        }
        return sb.toString();
    }

    @RestrictTo({RestrictTo.Scope.LIBRARY})
    public void z(boolean z) {
        this.nb = z;
    }
}
