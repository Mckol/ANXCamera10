package com.airbnb.lottie;

import androidx.core.util.Pair;
import java.util.Comparator;

/* access modifiers changed from: package-private */
/* compiled from: PerformanceTracker */
public class ba implements Comparator<Pair<String, Float>> {
    final /* synthetic */ ca this$0;

    ba(ca caVar) {
        this.this$0 = caVar;
    }

    /* renamed from: a */
    public int compare(Pair<String, Float> pair, Pair<String, Float> pair2) {
        float floatValue = pair.second.floatValue();
        float floatValue2 = pair2.second.floatValue();
        if (floatValue2 > floatValue) {
            return 1;
        }
        return floatValue > floatValue2 ? -1 : 0;
    }
}
