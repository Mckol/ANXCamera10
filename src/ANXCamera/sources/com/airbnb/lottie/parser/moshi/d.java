package com.airbnb.lottie.parser.moshi;

import com.airbnb.lottie.parser.moshi.LinkedHashTreeMap;
import java.util.Map;

/* compiled from: LinkedHashTreeMap */
class d extends LinkedHashTreeMap<K, V>.e {
    final /* synthetic */ LinkedHashTreeMap.c this$1;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    d(LinkedHashTreeMap.c cVar) {
        super();
        this.this$1 = cVar;
    }

    @Override // java.util.Iterator
    public Map.Entry<K, V> next() {
        return nextNode();
    }
}
