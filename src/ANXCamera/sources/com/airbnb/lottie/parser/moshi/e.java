package com.airbnb.lottie.parser.moshi;

import com.airbnb.lottie.parser.moshi.LinkedHashTreeMap;

/* compiled from: LinkedHashTreeMap */
class e extends LinkedHashTreeMap<K, V>.e {
    final /* synthetic */ LinkedHashTreeMap.d this$1;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    e(LinkedHashTreeMap.d dVar) {
        super();
        this.this$1 = dVar;
    }

    @Override // java.util.Iterator
    public K next() {
        return nextNode().key;
    }
}
