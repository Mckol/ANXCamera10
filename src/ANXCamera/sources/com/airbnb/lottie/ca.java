package com.airbnb.lottie;

import android.util.Log;
import androidx.collection.ArraySet;
import androidx.core.util.Pair;
import com.airbnb.lottie.d.f;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/* compiled from: PerformanceTracker */
public class ca {
    private final Set<a> Pb = new ArraySet();
    private final Map<String, f> Qb = new HashMap();
    private final Comparator<Pair<String, Float>> Rb = new ba(this);
    private boolean enabled = false;

    /* compiled from: PerformanceTracker */
    public interface a {
        void d(float f);
    }

    public void a(a aVar) {
        this.Pb.add(aVar);
    }

    public void b(a aVar) {
        this.Pb.remove(aVar);
    }

    public void c(String str, float f) {
        if (this.enabled) {
            f fVar = this.Qb.get(str);
            if (fVar == null) {
                fVar = new f();
                this.Qb.put(str, fVar);
            }
            fVar.add(f);
            if (str.equals("__container")) {
                for (a aVar : this.Pb) {
                    aVar.d(f);
                }
            }
        }
    }

    public void lb() {
        this.Qb.clear();
    }

    public List<Pair<String, Float>> mb() {
        if (!this.enabled) {
            return Collections.emptyList();
        }
        ArrayList arrayList = new ArrayList(this.Qb.size());
        for (Map.Entry<String, f> entry : this.Qb.entrySet()) {
            arrayList.add(new Pair(entry.getKey(), Float.valueOf(entry.getValue().getMean())));
        }
        Collections.sort(arrayList, this.Rb);
        return arrayList;
    }

    public void nb() {
        if (this.enabled) {
            List<Pair<String, Float>> mb = mb();
            Log.d(C0027e.TAG, "Render times:");
            for (int i = 0; i < mb.size(); i++) {
                Pair<String, Float> pair = mb.get(i);
                Log.d(C0027e.TAG, String.format("\t\t%30s:%.2f", pair.first, pair.second));
            }
        }
    }

    /* access modifiers changed from: package-private */
    public void setEnabled(boolean z) {
        this.enabled = z;
    }
}
